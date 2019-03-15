package com.fattymieo.survival.commands;

import com.fattymieo.survival.Survival;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reload implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = Survival.getColoredLang("Prefix");

		Survival.instance.reloadConfig();
		sender.sendMessage(prefix + ChatColor.GREEN + "Config loaded");

		Survival.instance.loadLangFile(sender);
		sender.sendMessage(prefix + ChatColor.GREEN + "Reload complete");
		return true;
	}

}
