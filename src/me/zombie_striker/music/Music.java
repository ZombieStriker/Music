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
 */package me.zombie_striker.music;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Music {

	private static Main main;

	public Music(Main m) {
		main = m;
	}

	public Music getInstance() {
		return this;
	}
	
	public static boolean isOpen(int streamID){
		return !main.getConfig().contains("Loop."+streamID+".s");
	}

	public static String getSong(int streamID){
		return main.getConfig().getString("Loop."+streamID+".s");
	}
	public static String getOwner(int streamID){
		return main.getConfig().getString("Loop."+streamID+".p");
	}
	public static boolean hasSound(String songname) {
		File fo = new File(main.getDataFolder() + "/Music");
		for (File ff : fo.listFiles()) {
			if (ff.getName().replace(".txt", "").trim().equals(songname))
				return true;
		}
		return false;
	}

	public static void playSound(String songname, int streamID,
			Location location, String owner) {
		int number = -1;
		String song = songname;
		boolean hasSong = hasSound(songname);
		if (!hasSong) {
			System.out.println("##########-MUSIC PLUGIN#####");
			System.out
					.println("Error, the name of the song specified has not been found.");
			System.out.println("############################");
			return;
		}
		if(streamID >= 0){
			number = streamID;
		}else{
			System.out.println("##########-MUSIC PLUGIN#####");
			System.out
					.println("Error, The Stream ID provided \""+streamID+"\" is negitive.");
			System.out.println("############################");
			return;
		}
		main.getConfig().set("Loop." + number + ".l.x", location.getBlockX());
		main.getConfig().set("Loop." + number + ".l.y", location.getBlockY());
		main.getConfig().set("Loop." + number + ".l.z", location.getBlockZ());
		main.getConfig().set("Loop." + number + ".l.w",
				location.getWorld().getName());
		main.getConfig().set("Loop." + number + ".s", song);
		main.time.put(number, 0L);
		main.getConfig().set("Loop." + number + ".p", owner);
		main.time.put(number, 0L);
		main.saveConfig();
	}

	public static void playSoundOnce(String songname, Location location) {
		String song = songname;
		boolean hasSong = false;
		File fo = new File(main.getDataFolder() + "/Music");
		for (File ff : fo.listFiles()) {
			if (ff.getName().replace(".txt", "").trim().equals(songname))
				hasSong = true;
		}
		if (!hasSong) {
			System.out
					.println("Error, the name of the song specified has not been found.");
			return;
		}

		for (Player p : location.getWorld().getPlayers())
			p.playSound(location, song, 10, 1);
	}

	public static void removeSound(int StreamID) {
		int number = 0;
		try {
			number = StreamID;
		} catch (Exception E) {
			E.printStackTrace();
			return;
		}
		main.getConfig().set("Loop." + number + ".l.x", null);
		main.getConfig().set("Loop." + number + ".l.y", null);
		main.getConfig().set("Loop." + number + ".l.z", null);
		main.getConfig().set("Loop." + number + ".l.w", null);
		main.getConfig().set("Loop." + number + ".s", null);
		main.getConfig().set("Loop.time" + number, null);
		main.getConfig().set("Loop." + number + ".p", null);
		main.getConfig().set("Loop." + number + "", null);
		main.time.put(number, 0L);
		main.saveConfig();
	}
}
