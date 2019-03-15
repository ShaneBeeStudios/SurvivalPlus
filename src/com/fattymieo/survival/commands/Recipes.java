package com.fattymieo.survival.commands;

import com.fattymieo.survival.Survival;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Recipes implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = "&7[&3SurvivalPlus&7] ";
		if (!(sender instanceof Player)) {
			Survival.sendColoredConsoleMsg(prefix + "&cPlayer only command");
			return true;
		}
		Player player = (Player) sender;
		Survival.sendColoredMessage(player, prefix + "&6Recipes");
		Survival.sendColoredMessage(player, "  &7Recipes can be found in your crafting guide in your inventory/crafting table");
		return true;
	}

}
