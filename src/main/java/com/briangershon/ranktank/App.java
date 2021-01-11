package com.briangershon.ranktank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class App extends JavaPlugin {

	private File playerRanks;
	private YamlConfiguration modifyPlayerRanks;

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new SidebarListener(this), this);
		try {
			initializeConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initializeConfig() throws IOException {
		// not using configuration at the moment, but this will create a data folder for
		// us to save our custom playerranks.yml file
		this.getConfig().options().copyDefaults();
		this.saveDefaultConfig();

		playerRanks = new File(Bukkit.getServer().getPluginManager().getPlugin("RankTankPlugin").getDataFolder(),
				"playerranks.yml");
		if (!playerRanks.exists()) {
			playerRanks.createNewFile();
		}

		modifyPlayerRanks = YamlConfiguration.loadConfiguration(playerRanks);
	}

	public void addPlayerIfNew(Player player) throws IOException {
		// if Player doesn't already exist, add them to YAML file with default role
		ConfigurationSection playerConfig;
		playerConfig = modifyPlayerRanks.getConfigurationSection("players");
		if (playerConfig == null) {
			playerConfig = modifyPlayerRanks.createSection("players");
		}

		if (!playerConfig.contains(player.getUniqueId().toString())) {
			HashMap<String, String> playerMap = new HashMap<String, String>();
			playerMap.put("playerName", player.getName());
			playerMap.put("rank", "Member");
			playerConfig.createSection(player.getUniqueId().toString(), playerMap);
			modifyPlayerRanks.save(playerRanks);
		}
	}

	public List<String> getTopPlayers() {
		List<String> players = new ArrayList<>();

		ConfigurationSection playerConfig;
		playerConfig = modifyPlayerRanks.getConfigurationSection("players");
		if (playerConfig == null) {
			return players;
		}

		playerConfig.getKeys(false).forEach((uuid) -> {
			ConfigurationSection c = playerConfig.getConfigurationSection(uuid);
			String playerName = c.getString("playerName");
			String rank = c.getString("rank");
			players.add(playerName + " " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + rank);
		});

		return players;
	}

	public void buildSidebar(Player player) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("rank", "crit", "objname");
		obj.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Top Ranks");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		List<String> top = getTopPlayers();

		Score scoreLine15 = obj.getScore("");
		scoreLine15.setScore(15);

		int ln = 14;
		int i = 0;
		while (i < top.size() && ln >= 1) {
			Score scoreLine = obj.getScore(top.get(i));
			scoreLine.setScore(ln);
			i++;
			ln--;
		}

		player.setScoreboard(board);
	}
}
