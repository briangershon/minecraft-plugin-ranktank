package com.briangershon.ranktank;

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
		app.buildSidebar(player);
	}

}
