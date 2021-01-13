package com.briangershon.ranktank;

import org.bukkit.ChatColor;

public class LeaderboardRow {
	private String playerName;
	private String rank;

	public LeaderboardRow(String playerName, String rank) {
		this.playerName = playerName;
		this.rank = rank;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public String getRank() {
		return this.rank;
	}

	public String getRankColor() {

		ChatColor c = ChatColor.GOLD;

		switch (this.rank.toLowerCase()) {
		case "member":
			c = ChatColor.RED;
			break;
		case "vip":
			c = ChatColor.GREEN;
			break;
		case "vip+":
			c = ChatColor.DARK_GREEN;
			break;
		case "crafty":
			c = ChatColor.DARK_PURPLE;
			break;
		case "crafty+":
			c = ChatColor.LIGHT_PURPLE;
			break;
		case "mvp":
			c = ChatColor.DARK_BLUE;
			break;
		case "mvp+":
			c = ChatColor.BLUE;
			break;
		case "mvp++":
			c = ChatColor.AQUA;
			break;
		}

		return c.toString();
	}

	public int getOrder() {
		int order = 0; // default for any rank not on this list

		switch (this.rank.toLowerCase()) {
		case "member":
			order = 1;
			break;
		case "vip":
			order = 2;
			break;
		case "vip+":
			order = 3;
			break;
		case "crafty":
			order = 4;
			break;
		case "crafty+":
			order = 5;
			break;
		case "mvp":
			order = 6;
			break;
		case "mvp+":
			order = 7;
			break;
		case "mvp++":
			order = 8;
			break;
		}

		return order;
	}

}
