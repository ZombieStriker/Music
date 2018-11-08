package me.zombie_striker.music;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class JukeBoxEvents implements Listener {

	private Main p;

	public JukeBoxEvents(Main p) {
		this.p = p;
	}

	@EventHandler
	public void placeJuke(BlockPlaceEvent e) {
		if (e.getItemInHand() != null && e.getItemInHand().getType() == Material.JUKEBOX) {
			if (e.getPlayer().isSneaking()) {
				e.getPlayer().sendMessage(p.prefix + " Placing a radio Jukebox. Right click it to change the station.");
				JukeBox j = new JukeBox(e.getBlockPlaced().getLocation(), e.getPlayer().getUniqueId(), 5);
				p.jukeboxes.add(j);
				p.getConfig().set("Jukeboxes." + j.getConfigName() + ".location", j.jukeBox);
				p.getConfig().set("Jukeboxes." + j.getConfigName() + ".owner", e.getPlayer().getUniqueId().toString());
				p.getConfig().set("Jukeboxes." + j.getConfigName() + ".station", -1);
				p.getConfig().set("Jukeboxes." + j.getConfigName() + ".volume", 5);
				p.getConfig().set("Jukeboxes." + j.getConfigName() + ".active", false);
				j.setActive(false);
				p.saveConfig();
			}
		}
	}

	@EventHandler
	public void breakJukeBox(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.JUKEBOX) {
			for (JukeBox j : new ArrayList<>(p.jukeboxes)) {
				if (j.jukeBox.equals(e.getBlock().getLocation())) {
					p.jukeboxes.remove(j);
					p.getConfig().set("Jukeboxes." + j.getConfigName(), null);
					p.saveConfig();

					// Stop previous station music
					try {
						Loop l = p.getLoops(j.stationId);
						if (l != null)
							for (Entity pl : j.jukeBox.getWorld().getNearbyEntities(j.jukeBox, 40, 40, 40))
								if (pl instanceof Player)
									((Player) pl).stopSound(l.getThisSong());
					} catch (Error | Exception e4) {
					}

					e.getPlayer().sendMessage(p.prefix + ChatColor.RED + " Removing Jukebox!");
					break;
				}
			}
		}
	}

	@EventHandler
	public void clickJukebox(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null
				&& e.getClickedBlock().getType() == Material.JUKEBOX) {
			if (e.getPlayer().hasPermission("music.usejukebox"))
				for (JukeBox j : new ArrayList<>(p.jukeboxes)) {
					if (j.jukeBox != null && e.getClickedBlock() != null)
						if (j.jukeBox.equals(e.getClickedBlock().getLocation())) {
							if (j.owner.equals(e.getPlayer().getUniqueId())) {
								e.getPlayer().openInventory(p.getInventory(j));
								p.jukeboxSetters.put(e.getPlayer().getUniqueId(), j);
							} else {
								e.getPlayer().sendMessage(p.prefix + ChatColor.RED + " You do not own this jukebox!");
							}
							e.setCancelled(true);
						}
				}
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (p.chatterVolumeSetup.containsKey(e.getPlayer().getUniqueId())) {
			try {
				JukeBox j = p.chatterVolumeSetup.get(e.getPlayer().getUniqueId());
				int volume = -1;
				if (e.getMessage().equalsIgnoreCase("global")) {
					volume = -1;
				} else {
					volume = Integer.parseInt(e.getMessage());
					if (volume < 0)
						volume = -volume;
				}
				j.volume = volume;
				e.setCancelled(true);
				e.getPlayer().sendMessage(p.prefix + " Volume has been set to " + (volume == -1 ? "Global" : volume));
			} catch (Error | Exception e2) {
				e.getPlayer().sendMessage(p.prefix + " Invalid input:\"" + e.getMessage()
						+ "\". The input should be a number. (Default=5)");
				p.chatterVolumeSetup.remove(e.getPlayer().getUniqueId());
			}
		}
	}

	@EventHandler
	public void click(InventoryClickEvent e) {
		boolean clicked = false;
		try {
			clicked = e.getClickedInventory() != null && e.getClickedInventory().getTitle().equals(Main.inventorytitle);
		} catch (Exception | Error e2) {
			clicked = e.getInventory() != null && e.getInventory().getTitle().equals(Main.inventorytitle);
		}

		if (clicked) {
			e.setCancelled(true);
			if (e.getCurrentItem() != null) {
				JukeBox j = p.jukeboxSetters.get(e.getWhoClicked().getUniqueId());
				try {

					if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Main.changeVolButtonName)) {
						if (!e.getWhoClicked().hasPermission("music.changevolume")) {
							e.getWhoClicked().sendMessage(p.prefix + " You do not have permission to do this.");
							e.getWhoClicked().closeInventory();
							return;
						}
						e.getWhoClicked().closeInventory();
						e.getWhoClicked().sendMessage(p.prefix
								+ " What volume should this jukebox be set to? Currently set to : " + j.volume);
						if (e.getWhoClicked().hasPermission("music.changevolumetoglobal"))
							e.getWhoClicked().sendMessage(
									p.prefix + " To set this station to play across the world, type \"Global\" as");
						p.chatterVolumeSetup.put(e.getWhoClicked().getUniqueId(), j);
						p.jukeboxSetters.remove(e.getWhoClicked().getUniqueId());
						return;
					}

					int i = Integer.parseInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));

					// Stop previous station music
					try {
						Loop l = p.getLoops(j.stationId);
						if (l != null)
							for (Entity pl : j.jukeBox.getWorld().getNearbyEntities(j.jukeBox, 40, 40, 40))
								if (pl instanceof Player)
									((Player) pl).stopSound(l.getThisSong());
					} catch (Error | Exception e4) {
					}

					j.setActive(true);
					j.stationId = i;
					p.getConfig().set("Jukeboxes." + j.getConfigName() + ".station", i);
					p.getConfig().set("Jukeboxes." + j.getConfigName() + ".active", true);
					p.saveConfig();

					e.getWhoClicked().closeInventory();
					e.getWhoClicked().sendMessage(p.prefix + " Jukebox has been set to station " + i + ".");
					e.getWhoClicked().sendMessage(
							p.prefix + " (May take some time for the jukebox to catch-up to the station.)");
				} catch (Error | Exception e4) {
					// Stop previous station music
					try {
						Loop l = p.getLoops(j.stationId);
						if (l != null)
							for (Entity pl : j.jukeBox.getWorld().getNearbyEntities(j.jukeBox, 40, 40, 40))
								if (pl instanceof Player)
									((Player) pl).stopSound(l.getThisSong());
					} catch (Error | Exception e56) {
					}

					j.setActive(false);
					p.getConfig().set("Jukeboxes." + j.getConfigName() + ".active", false);
					p.saveConfig();

					e.getWhoClicked().closeInventory();
					e.getWhoClicked().sendMessage(p.prefix + " Jukebox has been turned off.");
				}
			}
		}
	}

}
