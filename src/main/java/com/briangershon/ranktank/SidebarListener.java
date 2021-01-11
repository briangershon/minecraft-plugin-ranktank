package com.briangershon.ranktank;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SidebarListener implements Listener {
	private App app;

	public SidebarListener(App app) {
		this.app = app;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		try {
			app.addPlayerIfNew(player);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		app.buildSidebar(player);
	}

}
