package com.briangershon.ranktank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leaderboard {

	private List<LeaderboardRow> rows = new ArrayList<>();

	public void addRow(String playerName, String rank) {
		rows.add(new LeaderboardRow(playerName, rank));
	}

	public List<LeaderboardRow> getTopPlayers() {
		Collections.sort(rows, (r1, r2) -> {
			return r2.getOrder() - r1.getOrder();
		});
		return this.rows;
	}
}
