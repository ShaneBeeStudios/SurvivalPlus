package com.fattymieo.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.fattymieo.survival.Survival;

public class Reload implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Bukkit.getPluginManager().disablePlugin(Survival.instance);
		Bukkit.getPluginManager().enablePlugin(Survival.instance);
		Survival.instance.reloadConfig();

		if(sender instanceof Player)
		{
			sender.sendMessage(ChatColor.AQUA + "[SurvivalPlus] " + ChatColor.GREEN + "Reload complete.");
			sender.getServer().getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.GREEN + "Reload complete.");
		}
		else if(sender instanceof ConsoleCommandSender)
		{
			sender.sendMessage("[SurvivalPlus] " + ChatColor.GREEN + "Reload complete.");
		}
		return true;
	}
}
