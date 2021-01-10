package com.briangershon.ranktank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class App extends JavaPlugin {
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new SidebarListener(this), this);
	}

	public void buildSidebar(Player player) {
		getLogger().info("Build Sidebar for " + player.getName());
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("rank", "crit", "objname");
		obj.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Top Ranks");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score lineOne = obj
				.getScore("corrupted_c0de: " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "MVP++");
		lineOne.setScore(1);
		Score lineTwo = obj
				.getScore("Hydralorian: " + ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Member");
		lineTwo.setScore(2);
		player.setScoreboard(board);
	}
}
