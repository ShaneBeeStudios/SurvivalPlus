package com.fattymieo.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Recipes implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Works on players only.");
			return false;
		}
		
		Player player = (Player) sender;
		String[] helpstring =
		{
			ChatColor.AQUA + "Survival Recipes:",
			"Recipe1 - Do something special!",
			"Recipe2 - Do something special!"
		};
		
		player.sendMessage(helpstring);
		return true;
	}
}
