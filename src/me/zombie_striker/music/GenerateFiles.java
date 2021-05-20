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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class GenerateFiles {

	private Main p;
	
	private String version = "initSetupCompleteV3.0";

	public GenerateFiles(Main p) {
		this.p = p;
	}

	public void run() {
		this.generateMusicFolder();
		this.generateSubMusicSounds();
		this.generateReadMeTXT();
		this.generateTemplateTXT();
		// this.generateStreamsTXT();

		p.getConfig().set(version, true);
		p.saveConfig();
	}
/*
	public void generateResourceTXT() {
		if (!(new File(p.getDataFolder() + "/ResourcePacks.txt")).exists()
				|| !p.getConfig().contains(version)) {
			try {
				new File(p.getDataFolder() + "/ResourcePacks.txt")
						.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File StreamFile = new File(p.getDataFolder() + "/ResourcePacks.txt");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						StreamFile));
				// bw.write("DEFAULT");
				// bw.newLine();
				// bw.write("dev.bukkit.org/media/files/880/400/Music_-_HowThisWorks.zip");
				// bw.newLine();
				bw.write("MusicPack 2.0");
				bw.newLine();
				bw.write("https://www.dropbox.com/s/lisspq2w76nn43p/MusicPack2.0.zip?dl=1");
				bw.newLine();
				bw.write("[&&END&&]");
				bw.flush();
				bw.close();
				Bukkit.getConsoleSender().sendMessage(
						p.prefix + "Successfully created ResourcePacks.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

	/*
	 * public void generateStreamsTXT() {
	 * 
	 * if (!(new File(p.getDataFolder() + "/Streams.txt")).exists()) { try { new
	 * File(p.getDataFolder() + "/Streams.txt").createNewFile(); } catch
	 * (IOException e) { e.printStackTrace(); } File StreamFile = new
	 * File(p.getDataFolder() + "/Streams.txt"); try { BufferedWriter bw = new
	 * BufferedWriter(new FileWriter( StreamFile)); bw.write("220"); bw.flush();
	 * bw.close(); Bukkit.getConsoleSender().sendMessage( p.prefix +
	 * "Successfully created Streams.txt"); } catch (IOException e) {
	 * e.printStackTrace(); } } }
	 */

	public void generateSubMusicSounds() {

		p.musicdirectory = new File(p.getDataFolder() + "/Music");
		if (!p.musicdirectory.exists()
				|| !p.getConfig().contains(version)) {
			p.musicdirectory.mkdir();

			addNotes("Closer", "closer", "21.1");
			addNotes("ScienceCrusher", "sciencecrusher", "60.7");
			addNotes("Tavern", "tavern", "33.4");
			addNotes("TetrisTrance", "tetristrance", "13.5");
			addNotes("USSR", "ussr", "67.4");
			addNotes("WaterDrops", "waterdrop", "4");
			addNotes("AfterSchoolSpecial", "afterschoolspecial", "77.0");
			addNotes("Forest", "forest", "9.0");
			addNotes("WindowsXP", "winxpsong", "61.544");
			addNotes("Piano", "piano", "5.142");
			addNotes("HisTheme", "histheme", "79.0");
			addNotes("Smashlovania", "smashlovania", "60.0");
			addNotes("ThomasTheDootEngine", "thomasthedootengine", "37.0");
			addNotes("WaterFall", "waterfall", "9.0");
			addNotes("wind", "wind", "11.0");

			Bukkit.getConsoleSender().sendMessage(
					p.prefix + "Successfully created all sound files");
		}

	}

	private void addNotes(String file, String name, String time) {
		try {
			File f = new File(p.musicdirectory + "/" + file + ".txt");
			f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write("Time:" + time);
			bw.newLine();
			bw.write("Songname:" + name);
			bw.newLine();
			bw.newLine();
			bw.write("##Note: \"Songname\" is not needed for all sounds files. You only need it for sounds where the name of the ogg is different than the name of thix text file.");
			bw.newLine();
			bw.write("## Because the name of the file is the same as the name of the sound, you do not need \"Songname:\". ");
			bw.newLine();
			bw.flush();
			Bukkit.getConsoleSender().sendMessage(
					p.prefix + "Successfully created \"" + file + ".txt\"");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Bukkit.getConsoleSender().sendMessage(
					p.prefix + ChatColor.RED + "FAILED to created \"" + file
							+ ".txt\"");
			e.printStackTrace();
		}
	}

	public void generateMusicFolder() {

		File fmusicdirectory = p.getDataFolder();
		if (!fmusicdirectory.exists())
			fmusicdirectory.mkdir();

	}

	public void generateReadMeTXT() {

		if (!(new File(p.getDataFolder() + "/README.txt")).exists()
				|| !p.getConfig().contains(version)) {
			try {
				new File(p.getDataFolder() + "/README.txt").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File StreamFile = new File(p.getDataFolder() + "/README.txt");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						StreamFile));
				bw.write("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n"
						+ "___   ___  ___   ___  ____    _     ____\n"
						+ "|  \\_/  |  | |   | | /    |  | |   /   | "
						+ "|       |  | |   | | | |      _   .| |\n"
						+ "|       |  | |   | | \\___ \\  | |   | |\n"
						+ "| /\\/\\  |  | |   | |     | | | |   | |\n"
						+ "| | | | |   \\_____/   |____/ |_|    \\__|\n"
						+ "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n"
						+ "(I'm terrible at ASCII art :P)\n"
						+ "\n"
						+ "==Music Plugin==\n"
						+ "\n"
						+ "Version 2.7.12\n"
						+ "\n"
						+ "====================================================================================================\n"
						+ "                               Installation / Updating:\n"
						+ "\n"
						+ "-IMPORTANT: Always read the README.txt to make sure nothing has changed.\n"
						+ "\n"
						+ "-Drop the Music.jar Into your plugins folder\n"
						+ "-Drop the Music File into your plugins folder\n"
						+ "-Start up/reload your server to create some data files.\n"
						+ "\n"
						+ "You should not need to delete any files in order for this plugin to work.\n"
						+ "\n"
						+ "====================================================================================================\n"
						+ "                                 Adding your own music:\n"
						+ "\n"
						+ "I made it very simple for people to add their own music. Here is a step-by-step proccess on how to add new sounds to your server:\n"
						+ "\n"
						+ "NOTE: If you do not know how to add custom sounds to a resouncepack, watch this:\n"
						+ "https://www.youtube.com/watch?v=8jXLhhiaKWQ\n"
						+ "\n"
						+ "	#I'm going to assume you created your own resourcepack with the sounds you want.\n"
						+ "\n"
						+ "---)THROUGH COMMAND ###(Recommended)###\n"
						+ "\n"
						+ "	-1) Use the /playSound to test if your sound works. Continue only when you can hear your sound\n"
						+ "\n"
						+ "	-2) If you're an OP on your server, use /Loop createSound (Display name) (The name you used for /playsound) (The exact time. E.g. \"17\" or \"19.567\")\n"
						+ "\n"
						+ "	-3) Wait for the message \"Song creation successful\"\n"
						+ "\n"
						+ "	-4) Test it out by using /loop playonce (Display name)\n"
						+ "\n"
						+ "If you have followed the steps correctly, then you should be able to hear your sound.\n"
						+ "\n"
						+ "\n"
						+ "\n"
						+ "---)MANUALLY\n"
						+ "\n"
						+ "	-1)Go into the plugins/Music/Music file. Here you should see Lobby.txt, Windmill.txt and Piano.txt\n"
						+ "\n"
						+ "	-2)Create your own .txt file. The name of the file will be the name of the song (To get a song from the Boop.txt, you would\n"
						+ "	 need to use /Music play Boop.) The name of the file does NOT need to be the name of the song in the resourcepack.\n"
						+ "\n"
						+ "	-3)Once you have created the text file, you need to open it. You can use any text editor such as Notepad, Notepadd++ ect.\n"
						+ "\n"
						+ "	-4)Now that you have that text file open, you will need to write 'Time:' and then the time between between loops in \n"
						+ "		seconds. [Update] the newest version now accepts decimals and does not rely on Quarter seconds. Instead, put in the exact time the song should play for."
						+ "\n"
						+ "	-5)After putting down the time, put down 'Songname' and then the name of the song. So if you have a sound called 'Song',\n"
						+ "		You would put Songname:Song\n"
						+ "\n"
						+ "	#NOTE: If any of this is confusing, look at [TEMPLATE SONG].txt\n"
						+ "	\n"
						+ "Once you have done these steps, and you have named the text file, you can now test it out. Just \n"
						+ "join the server, Do \"/loop listsongs\"or \"/music listsongs\", and look through that list to see if your loop came up.\n"
						+ "\n"
						+ "####Note: Music plugin also has the feature to add the links to the resourcepacks, that way players can download all the sounds at once\n"
						+ "	Just go into the plugins/Music folder and look for Resourcepacks.txt and add this line !!!ABOVE THE LINE THAT HAS[&&END&&]!!!:\n"
						+ "\n" + "NameOfResourcepack\n" + "http.LINK.zip");
				bw.flush();
				bw.close();
				Bukkit.getConsoleSender().sendMessage(
						p.prefix + "Successfully created README.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void generateTemplateTXT() {

		if (!(new File(p.getDataFolder() + "/[TEMPLATE SONG].txt")).exists()
				|| !p.getConfig().contains(version)) {
			try {
				new File(p.getDataFolder() + "/[TEMPLATE SONG].txt")
						.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File StreamFile = new File(p.getDataFolder()
					+ "/[TEMPLATE SONG].txt");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						StreamFile));
				bw.write("Time:[AMOUNT OF TIME SONG PLAYS FOR TIMES]");
				bw.newLine();
				bw.write("Songname:[Song Name (E.g. pop)]");
				bw.flush();
				bw.close();
				Bukkit.getConsoleSender().sendMessage(
						p.prefix + "Successfully created Streams.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
