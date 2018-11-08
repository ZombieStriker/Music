/*
 *  Copyright (C) 2016 Zombie_Striker
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program;
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307 USA
 */
package me.zombie_striker.music;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class MusicCommand implements CommandExecutor, TabCompleter {

	private String prefix;
	private Main p;

	public MusicCommand(Main p) {
		prefix = p.prefix;
		this.p = p;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> Names = new ArrayList<String>();
		if (command.getName().equalsIgnoreCase("music")) {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("setUpStation") || args[0].equalsIgnoreCase("playonce")
						|| args[0].equalsIgnoreCase("addToQueue")) {
					File fo = new File(p.getDataFolder() + "/Music");

					for (File ff : fo.listFiles()) {
						if (ff.getName().replace(".txt", "").trim().toLowerCase().startsWith(args[1].toLowerCase())) {
							Names.add(ff.getName().replace(".txt", "").trim());
						}
					}
					return Names;

				}
				if (args[0].equalsIgnoreCase("toggleRandomizeSongs")) {
					for (Loop l : p.loops) {
						if (l.getOwner().equals(((Player) sender).getUniqueId()))
							Names.add(l.getInt() + "");
					}
					return Names;
				}
				if (args[0].equalsIgnoreCase("clearQueue")) {
					for (Loop l : p.loops) {
						if (l.getOwner().equals(((Player) sender).getUniqueId()))
							Names.add(l.getInt() + "");
					}
					return Names;
				}
				if (args[0].equalsIgnoreCase("listStationSongs")) {
					for (Loop l : p.loops) {
						if (l.getOwner().equals(((Player) sender).getUniqueId()))
							Names.add(l.getInt() + "");
					}
					return Names;
				}
				if (args[0].equalsIgnoreCase("removeFromQueue")) {
					Names.add("1");
					Names.add("2");
					Names.add("3");
					Names.add("ect.");
					return Names;
				}
			} else if (args.length == 1) {
				if ("toggleRandomizeSongs".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("toggleRandomizeSongs");
				if ("getPack".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("getPack");
				if ("setUpStation".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("setUpStation");
				if ("addToQueue".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("addToQueue");
				if ("playonce".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("playonce");
				if ("setRadius".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("setRadius");
				if (sender.isOp())
					if ("createsong".toLowerCase().startsWith(args[0].toLowerCase()))
						Names.add("createSong");
				if ("removeFromQueue".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("removeFromQueue");
				if ("clearQueue".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("clearQueue");
				if ("removeStation".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("removeStation");
				if ("help".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("help");
				if ("listStations".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("listStations");
				if ("listStationSongs".toLowerCase().startsWith(args[0].toLowerCase()))
					Names.add("listStationSongs");
				return Names;
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("setUpStation")) {
					for (int i = 0; i < 6; i++) {
						Names.add(i + "");
					}
					return Names;
				}
				if (args[0].equalsIgnoreCase("removeFromStation")) {
					Names.add("1");
					Names.add("2");
					Names.add("3");
					return Names;
				}
			}
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (!sender.hasPermission("music.commands")) {
				sender.sendMessage(ChatColor.RED + " You do not have permission to use this command.");
				return true;
			}

			/**
			 * Help messages
			 */
			if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				p.showPlayerHelpMessage(player);
				return true;
			} else

			if (args[0].equalsIgnoreCase("listStationSongs")) {
				if (args.length < 2) {
					sender.sendMessage(prefix + " Useage: /music listStationSongs <StationId>");
					return true;
				}
				int number = 0;
				try {
					number = Integer.parseInt(args[1]);
				} catch (Exception E) {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "Try to make sure you put in a station Id, currently you're looking for station "
							+ args[1]);
					return false;
				}
				for (Loop l : p.loops) {
					if (l.getInt() == number) {
						sender.sendMessage(prefix + " Songs and Ids for station-" + number);
						int k = 0;
						for (String s : l.getSongs()) {
							sender.sendMessage("-" + k + " : " + s);
							k++;
						}
						return true;
					}
				}
				sender.sendMessage(prefix + " The station " + number + " is empty.");
				return true;
			} else
			/**
			 * sets music radius
			 */

			if (args[0].equalsIgnoreCase("setRadius")) {
				if (args.length < 3) {
					sender.sendMessage(prefix + " Useage : /music setRadius <StationID> <radius>");
					return true;
				}
				Loop station = null;
				try {
					int id = Integer.parseInt(args[1]);
					for (Loop l : p.loops) {
						if (l.getInt() == id && l.getOwner().equals(player.getUniqueId())) {
							station = l;
							break;
						}
					}
				} catch (Exception e) {
					sender.sendMessage(prefix + " You need to privde a valid station");
					return true;
				}
				if (station == null) {
					sender.sendMessage(prefix + " This station does not exist. Please provide a valid station id");
					return true;
				}
				int radius = -1;
				try {
					radius = Integer.parseInt(args[2]);
				} catch (Exception e) {
					sender.sendMessage(prefix + " You must provide a valid radius. Any number below "
							+ (sender.isOp() ? 100 : 10) + " will work.");
					return true;
				}
				if (radius > (sender.isOp() ? 100 : 10)) {
					sender.sendMessage(prefix + " The radius provided is too large. Please use a smaller radius.");
					return true;
				}
				p.getConfig().set("Loop." + station.getInt() + ".r", radius);
				station.setRadius(radius);
				p.saveConfig();
				sender.sendMessage(prefix + " Radius for station " + station.getInt() + " set to " + radius);
			} else
			/**
			 * Used to remove station
			 */

			if (args[0].equalsIgnoreCase("removeFromStation")) {
				if (args.length < 3) {
					sender.sendMessage(prefix + " Useage: /music removeFromStation <Queue ID> <StationId>");
					return true;
				}
				int id = 0;
				try {
					id = Integer.parseInt(args[1]);
				} catch (Exception E) {
					player.sendMessage(
							ChatColor.BLUE + prefix + ChatColor.WHITE + "Try to make sure you put in a song id");
					return false;
				}
				int number = 0;
				try {
					number = Integer.parseInt(args[2]);
				} catch (Exception E) {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "Try to make sure you put in a station Id, currently you're looking for station "
							+ args[2]);
					return false;
				}
				if (player.isOp() || p.getConfig().getString("Loop." + number + ".p").equals(player.getName())
						|| p.getConfig().getString("Loop." + number + ".p").equals(player.getUniqueId().toString())) {
					List<String> songs = null;
					for (Loop loop : p.loops) {
						if (loop.getInt() == number) {
							loop.getSongs().remove(id);
							break;
						}
					}
					p.getConfig().set("Loop." + number, songs);
					p.saveConfig();
					player.sendMessage("Removing song " + id + " from station-" + number + " . ");
				} else {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "You can't edit other people's stations. Do '/music list' to find an empty station.");
				}
			} else
			/**
			 * Used to rwhole station
			 */

			if (args[0].equalsIgnoreCase("removeStation")) {
				if (args.length < 2) {
					sender.sendMessage(prefix + " Useage: /music clearQueue <StationId>");
					return true;
				}
				int number = 0;
				try {
					number = Integer.parseInt(args[1]);
				} catch (Exception E) {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "Try to make sure you put in a station Id, currently you're looking for station "
							+ args[1]);
					return false;
				}
				if (player.isOp() || p.getConfig().getString("Loop." + number + ".p").equals(player.getName())
						|| p.getConfig().getString("Loop." + number + ".p").equals(player.getUniqueId().toString())) {

					p.getConfig().set("Loop." + number, null);
					p.saveConfig();
					for (Loop loop : p.loops) {
						if (loop.getInt() == number) {
							loop.setActive(false);
							break;
						}
					}
					player.sendMessage(p.prefix + " Removing Station " + number + ".");
				} else {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "You can't edit other people's stations. Do '/music list' to find an empty station.");
				}
			} else
			/**
			 * Used to rwhole station
			 */

			if (args[0].equalsIgnoreCase("clearQueue")) {
				if (args.length < 2) {
					sender.sendMessage(prefix + " Useage: /music clearQueue <StationId>");
					return true;
				}
				int number = 0;
				try {
					number = Integer.parseInt(args[1]);
				} catch (Exception E) {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "Try to make sure you put in a station Id, currently you're looking for station "
							+ args[1]);
					return false;
				}
				if (player.isOp() || p.getConfig().getString("Loop." + number + ".p").equals(player.getName())
						|| p.getConfig().getString("Loop." + number + ".p").equals(player.getUniqueId().toString())) {

					List<String> songs = new ArrayList<>();
					p.getConfig().set("Loop." + number, songs);
					p.saveConfig();
					for (Loop loop : p.loops) {
						if (loop.getInt() == number) {
							loop.setSongs(songs);
							break;
						}
					}
					player.sendMessage(p.prefix + " Clearing station " + number + ".");
				} else {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "You can't edit other people's stations. Do '/music list' to find an empty station.");
				}
			} else
			/**
			 * Used to create a song file
			 */
			if (args[0].equalsIgnoreCase("createSong")) {
				if (player.isOp()) {
					if (args.length >= 4) {
						String display = args[1];
						String name = args[2];
						int time = (int) (Double.parseDouble(args[3]) * 4);
						File newFile = new File(p.musicdirectory.getPath() + "/" + display + ".txt");
						if (newFile.exists()) {
							player.sendMessage(prefix
									+ "That song has already been registered. Look in the \"plugins/Music/Music\" folder of the main server.");
							return false;
						}
						try {
							newFile.createNewFile();
							BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
							// bw.write("DEFAULT");
							// bw.newLine();
							// bw.write("dev.bukkit.org/media/files/880/400/Music_-_HowThisWorks.zip");
							// bw.newLine();
							bw.write("Time:" + time);
							bw.newLine();
							bw.write("Songname:" + name);
							bw.newLine();
							bw.write("[&&END&&]");
							bw.flush();
							bw.close();

							p.updateSongs();
							player.sendMessage(prefix + "Song creation was successful!");
						} catch (IOException e) {
							e.printStackTrace();
							player.sendMessage(prefix + " [Error]Could not create file.");
							return false;
						}
					} else {
						player.sendMessage(prefix + " Useage: /music createSong <DisplayName> <Song> <Time>");
					}
				} else {
					player.sendMessage(prefix + ChatColor.RED + "You do not have the permission to create a new song.");
				}
			} else

			/**
			 * Get auth
			 */
			if (args[0].equalsIgnoreCase("Author1")) {
				player.sendMessage(ChatColor.BLUE + prefix + " The author of this plugin is " + ChatColor.WHITE
						+ " Zombie_Striker");
			} else
			/**
			 * List available songs
			 */

			if (args[0].equalsIgnoreCase("listsongs")) {
				int songs = 1;
				File fo = new File(p.getDataFolder() + "/Music");
				for (File ff : fo.listFiles()) {
					sender.sendMessage(
							prefix + ChatColor.WHITE + " #" + songs + " : " + ff.getName().replace(".txt", "").trim());
					songs++;
				}
			} else
			/**
			 * get resource pack
			 */
			if (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("getpack")) {
				player.sendMessage(ChatColor.DARK_AQUA + StringUtils.repeat("=", 40));
				player.sendMessage(ChatColor.BLUE
						+ "These are the file(s) full of music. Click the link and and download these to your .minecraft/resourcepacks file.");
				player.sendMessage(ChatColor.DARK_AQUA + StringUtils.repeat("=", 40));
				File Fff = new File(p.getDataFolder(), "ResourcePacks.txt");
				if (!Fff.exists()) {
					player.sendMessage(ChatColor.RED + "ERROR:  File does not exist. Reload server.");
					return false;
				}
				try {
					InputStream is = null;
					is = new FileInputStream(Fff);
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					for (int i = 0; i < 40; i++) {
						String s = br.readLine();
						if (s.contains("[&&END&&]")) {
							break;
						}
						String s2 = br.readLine();
						player.sendMessage(
								ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "* NAME : " + ChatColor.AQUA + s);
						player.sendMessage(ChatColor.BOLD + s2);
					}
					br.close();
					p.getConfig().set("UsersWithPacks." + sender.getName(), p.songversion);
					p.saveConfig();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else
			/**
			 * List stations
			 */
			if (args[0].equalsIgnoreCase("listStations")) {
				int jl = 1;
				try {
					jl = Integer.parseInt(args[1]);
				} catch (Exception e) {
				}

				if (jl > (p.Streams / 20)) {
					player.sendMessage(ChatColor.GOLD + "We currently only have " + (p.loops.size() / 20) + " pages.");
					return false;
				} else {
					player.sendMessage(ChatColor.GOLD + "Page " + jl + " out of " + (p.loops.size() / 20) + ".");
					for (Loop l : p.loops) {

						player.sendMessage(ChatColor.BLUE + "Station " + l.getInt() + " is owned by " + ChatColor.WHITE
								+ Bukkit.getOfflinePlayer(l.getOwner()).getName());
					}
				}

			} else

			/**
			 * set up station
			 * 
			 */

			if (args[0].equalsIgnoreCase("play") || args[0].equalsIgnoreCase("setUpStation")) {
				if (args.length >= 2) {
					int number = -1;
					List<String> song = new ArrayList<>();
					song.add(args[1]);
					boolean hasSong = false;
					File fo = new File(p.getDataFolder() + "/Music");
					for (File ff : fo.listFiles()) {
						if (ff.getName().replace(".txt", "").trim().equals(args[1]))
							hasSong = true;
					}
					if (!hasSong) {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "That song is not on the list, make sure caps are correct");
						return false;
					}
					try {
						number = Integer.parseInt(args[2]);
					} catch (Exception E) {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "Try to make sure you put in numbers for the ID");
						return false;
					}
					if (player.isOp() || p.getConfig().get("Loop." + number + ".p") == null
							|| p.getConfig().getString("Loop." + number + ".p").equals(player.getName())
							|| p.getConfig().getString("Loop." + number + ".p")
									.equals(player.getUniqueId().toString())) {
						/*
						 * p.getConfig().set("Loop." + number + ".l.x",
						 * player.getLocation().getBlockX()); p.getConfig().set("Loop." + number +
						 * ".l.y", player.getLocation().getBlockY()); p.getConfig().set("Loop." + number
						 * + ".l.z", player.getLocation().getBlockZ()); p.getConfig().set("Loop." +
						 * number + ".l.w", player.getLocation().getWorld().getName());
						 */

						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE + "Putting Song " + args[1]
								+ " for Station " + number + ". ");

						p.getConfig().set("Loop." + number + ".s", song);
						p.getConfig().set("Loop." + number + ".p", player.getUniqueId().toString());
						p.getConfig().set("Loop." + number + ".r", 1);
						p.saveConfig();
						for (Loop l : p.loops) {
							if (l.getInt() == number) {
								l.setSongs(song);
								l.setRadius(1);
								return true;
							}
						}
						p.loops.add(new Loop(number, song, player.getLocation(), player.getUniqueId(), 1));
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE + "You can now select Station- "
								+ number + " for Radio-Jukeboxes.");
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "Sneak while placing a Jukebox to place a Radio-Jukeboxe.");
					} else {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "You can't edit other user's stations. Do '/music list' and find an empty station.");
					}
				} else {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "You need to fill in these arguments : /music setUpStation <SongName> <Station> (use /music list and look for an ID that has not been taken)");
				}
			} else

			/**
			 * randomize station
			 */

			if (args[0].equalsIgnoreCase("toggleRandomizeSongs")) {
				if (args.length >= 1) {
					int number = -1;
					try {
						number = Integer.parseInt(args[1]);
					} catch (Exception E) {
						player.sendMessage(
								ChatColor.BLUE + prefix + ChatColor.WHITE + "Plase provide a valid stationID");
						return false;
					}
					Loop loop = null;
					for (Loop l : p.loops) {
						if (l.getInt() == number) {
							if (l.getOwner().equals(player.getUniqueId())) {
								loop = l;
								break;
							} else {
								sender.sendMessage(prefix + " You do not own this station");
								return true;
							}
						}
					}
					if (loop == null) {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE + "The station " + number
								+ " has not been set up!");
						return true;
					}

					if (player.isOp() || p.getConfig().getString("Loop." + number + ".p").equals(player.getName())
							|| p.getConfig().getString("Loop." + number + ".p").equals(player.getUniqueId().toString())
							|| p.getConfig().get("Loop." + number + ".p") == null) {
						loop.isRandom = !loop.isRandom;
						p.getConfig().set("Loop." + number + ".rand", loop.isRandom);
						p.saveConfig();

						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ (loop.isRandom ? "Randomizing" : "Normalizing") + " Station " + number + ". ");
					} else {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "You can't edit other people's station. Do '/music list' and find an empty station.");
					}
				} else {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "You need to fill in these arguments : /music addToQueue <SongName> <Station ID> (use /music list and find one that says null)");
				}
			} else
			/**
			 * Add loop to station
			 */

			if (args[0].equalsIgnoreCase("addToQueue")) {
				if (args.length >= 2) {
					int number = -1;
					String song = args[1];
					boolean hasSong = false;
					File fo = new File(p.getDataFolder() + "/Music");
					for (File ff : fo.listFiles()) {
						if (ff.getName().replace(".txt", "").trim().equals(args[1]))
							hasSong = true;
					}
					if (!hasSong) {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "That song is not on the list, make sure caps are correct");
						return false;
					}
					try {
						number = Integer.parseInt(args[2]);
					} catch (Exception E) {
						player.sendMessage(
								ChatColor.BLUE + prefix + ChatColor.WHITE + "Try to make sure you put in numbers");
						return false;
					}
					boolean found = false;
					for (Loop l : p.loops) {
						if (l.getInt() == number) {
							if (l.getOwner().equals(player.getUniqueId())) {
								found = true;
								break;
							} else {
								sender.sendMessage("You do not own this station");
								return true;
							}
						}
					}
					if (!found) {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE + "The station " + number
								+ " has not been set up!");
						return true;
					}

					if (player.isOp() || p.getConfig().getString("Loop." + number + ".p").equals(player.getName())
							|| p.getConfig().getString("Loop." + number + ".p").equals(player.getUniqueId().toString())
							|| p.getConfig().get("Loop." + number + ".p") == null) {
						List<String> songsC = p.getConfig().getStringList("Loop." + number + ".s");
						songsC.add(song);
						p.getConfig().set("Loop." + number + ".s", songsC);
						p.getConfig().set("Loop." + number + ".p", player.getUniqueId().toString());
						p.saveConfig();
						for (Loop l : p.loops) {
							if (l.getInt() == number)
								l.setSongs(songsC);
						}
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE + "Adding " + args[1]
								+ " to Station " + number + ". ");
					} else {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "You can't edit other people's station. Do '/music list' and find an empty station.");
					}
				} else {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "You need to fill in these arguments : /music addToQueue <SongName> <Station ID> (use /music list and find one that says null)");
				}
			} else

			/**
			 * Play once
			 * 
			 */

			if (args[0].equalsIgnoreCase("playonce")) {
				if (args.length >= 2) {
					String song = args[1];
					boolean hasSong = false;
					if (p.songname.containsKey(song)) {
						hasSong = true;
					}
					if (!hasSong) {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "That song is not on the list, make sure caps are correct");
						return false;
					}

					for (Player p : ((Player) sender).getWorld().getPlayers())
						p.playSound(((Player) sender).getLocation(), this.p.songname.get(song), 1, 1);
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE + "Playing Song " + song + " once. ");
				} else {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "You need to fill in these arguments : /music playOnce SongName ");
				}
			} else

			/**
			 * remove song from station
			 */
			if (args[0].equalsIgnoreCase("removeFromQueue")) {
				if (args.length >= 3) {
					int id = 0;
					try {
						id = Integer.parseInt(args[1]);
					} catch (Exception E) {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "Try to make sure you put in a track id, currently, you are looking for the track to remove"
								+ args[1]);
						return false;
					}
					int number = 0;
					try {
						number = Integer.parseInt(args[2]);
					} catch (Exception E) {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "Try to make sure you put in a station Id, currently you're looking for station "
								+ args[1]);
						return false;
					}
					boolean found = false;
					for (Loop l : p.loops) {
						if (l.getInt() == number) {
							if (player.isOp() || l.getOwner().equals(player.getUniqueId())) {
								found = true;
								break;
							} else {
								sender.sendMessage("You do not own this station");
								return true;
							}
						}
					}
					if (!found) {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE + "The station " + number
								+ " has not been set up!");
						return true;
					}

					if (player.isOp() || p.getConfig().getString("Loop." + number + ".p").equals(player.getName()) || p
							.getConfig().getString("Loop." + number + ".p").equals(player.getUniqueId().toString())) {
						String[] gg = null;
						for (Loop l : p.loops) {
							if (l.getInt() == number) {
								l.getSongs().remove(id);
							}
						}
						p.getConfig().set("Loop." + number + ".s", gg);
						p.saveConfig();
						player.sendMessage("Removing song " + id + " from station " + number + ". ");
					} else {
						player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
								+ "You can't edit other people's stations. Do '/music list' to find an empty station.");
					}
				} else {
					player.sendMessage(ChatColor.BLUE + prefix + ChatColor.WHITE
							+ "You need to fill in these arguments : /music removeFromQueue [LoopID] [Station] (/music list to find one that you own)");
				}
			} else {
				p.showPlayerHelpMessage(player);
			}
		} else {
			sender.sendMessage(prefix + " All commands must be sent as a player.");
		}
		return true;
	}

}
