package me.zombie_striker.music;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

public class Loop {

	int id = 0;
	List<String> song;
	int activeSong=-1;
	//List<Location> location = new ArrayList<>();
	boolean isActive = true;
	
	int radius = 1;
	
	boolean isRandom = false;
	
	private UUID owner;
	
	public Loop(Main p, int id, List<String> song,/* final int x, final int y,final int z,final String world,*/UUID owner,int radius){
		this.id = id;
		this.song = song;
		this.owner = owner;
		this.radius = radius;
		/*Tid =Bukkit.getScheduler().scheduleSyncRepeatingTask(p, new Runnable(){
			public void run(){
				if(Bukkit.getWorld(world)!=null){
					//location.add(new Location(Bukkit.getWorld(world),x,y,z));
					cancelTask();
				}
			}
		}, 1, 20);*/
	}
	public void setRadius(int i){
		radius = i;
	}
	public Loop(int id, List<String> song, Location loc,UUID owner, int radius){
		this.id = id;
		this.song = song;
		this.owner = owner;
		//this.location.add(loc);
		this.radius = radius;
	}
	public UUID getOwner(){
		return this.owner;
	}
	public void setSongs(List<String> s){
		this.song = s;
	}
//	public List<Location> getLocations(){
//		return location;
//	}
	public int getInt(){
		return id;
	}
	public List<String> getSongs(){
		return song;
	}
	public String getThisSong(){
		return song.get(activeSong);
	}
	public void setActiveSong(int i){
		this.activeSong=i;
	}
	public int getActiveSong(){
		return this.activeSong;
	}
	public boolean isActive(){
		return isActive;
	}
	public void setActive(boolean b){
		this.isActive = b;
	}
}
