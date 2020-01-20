package org.golde.bukkit.lan;

import java.io.IOException;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	ThreadLanServerPing lanThing;
	
	@Override
	public void onEnable() {
		
		try {
			lanThing = new ThreadLanServerPing(getServer().getMotd(), getServer().getPort() + "");
			lanThing.start();
			getLogger().info("Started LAN broadcasting thread");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onDisable() {
		if(lanThing != null) {
			lanThing.interrupt();
			getLogger().info("Stopped LAN broadcasting thread");
		}
	}
}