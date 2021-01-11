package com.briangershon.ranktank;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankTankCommand implements CommandExecutor {
	private App app;
	
	public RankTankCommand(App app) {
		this.app = app;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		if (args.length != 2) {
			player.sendMessage("/" + cmd.getName() + " expected two arguments: player's name, plus new rank.");
			return false;
		}

		
		if (!player.hasPermission("ranktank.set")) {
			player.sendMessage("Permission denied. 'ranktank.set' permission not set. Operators have this permission by default.");
			return false;
		}

		try {
			app.updatePlayerRank(args[0], args[1], player);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		app.buildSidebar(player);

		return false;
	}

}
