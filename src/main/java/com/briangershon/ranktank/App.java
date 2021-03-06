package com.briangershon.ranktank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
		getCommand("ranktank").setExecutor(new RankTankCommand(this));
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

	public void updatePlayerRank(String playerName, String newRank, Player currentPlayer) throws IOException {
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

		if (offlinePlayer == null) {
			currentPlayer.sendMessage("Offline " + playerName + " does not exist in Minecraft. Unable to set rank.");
			return;
		}

		UUID uuid = offlinePlayer.getUniqueId();

		ConfigurationSection playerConfig;
		playerConfig = modifyPlayerRanks.getConfigurationSection("players");
		if (playerConfig == null) {
			playerConfig = modifyPlayerRanks.createSection("players");
		}

		if (playerConfig.contains(uuid.toString())) {
			HashMap<String, String> playerMap = new HashMap<String, String>();
			playerMap.put("playerName", playerName);
			playerMap.put("rank", newRank);
			playerConfig.createSection(uuid.toString(), playerMap);
			modifyPlayerRanks.save(playerRanks);
			currentPlayer.sendMessage("Updated " + playerName + " to " + newRank + " rank.");
		} else {
			currentPlayer.sendMessage(playerName + " has not joined this server yet. Unable to set rank.");
			return;
		}
	}

	public List<String> getTopPlayers() {
		List<String> players = new ArrayList<>();

		ConfigurationSection playerConfig;
		playerConfig = modifyPlayerRanks.getConfigurationSection("players");
		if (playerConfig == null) {
			return players;
		}

		Leaderboard board = new Leaderboard();

		playerConfig.getKeys(false).forEach((uuid) -> {
			ConfigurationSection c = playerConfig.getConfigurationSection(uuid);
			String playerName = c.getString("playerName");
			String rank = c.getString("rank");
			board.addRow(playerName, rank);
		});

		board.getTopPlayers().forEach((leaderboardRow) -> {
			String row = ChatColor.RED.toString() + "[" + ChatColor.WHITE.toString() + leaderboardRow.getPlayerName() + ChatColor.RED.toString() + "] " + leaderboardRow.getRankColor()
					+ ChatColor.BOLD.toString() + leaderboardRow.getRank();
			// limit to 40 characters which is the max for a Score line
			if (row.length() < 40) {
				players.add(row);
			} else {
				players.add(row.substring(0, 40));
			}
		});

		return players;
	}

	public void buildSidebar(Player player) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("rank", "crit", "objname");
		obj.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "| " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Top Ranks" + ChatColor.RED.toString() + ChatColor.BOLD.toString() + " |");
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
