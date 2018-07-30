package me.zombie_striker.music;

import java.util.UUID;

import org.bukkit.Location;

public class JukeBox {

	public Location jukeBox;
	public int stationId=0;
	public boolean active;
	public UUID owner;
	
	public int volume = 5;
	
	public JukeBox(Location block,UUID owner,int volume) {
		this.jukeBox = block;
		this.owner = owner;
		this.volume = volume;
	}
	public void changeStation(int id){
		this.stationId = id;
	}
	public int getStation(){
		return stationId;
	}
	public boolean getActive(){
		return active;
	}
	
	public void setActive(boolean b){
		this.active = b;
	}
	
	public String getConfigName(){
		return jukeBox.getWorld().getName()+"-"+jukeBox.getBlockX()+"-"+jukeBox.getBlockY()+"-"+jukeBox.getBlockZ();
	}

}
