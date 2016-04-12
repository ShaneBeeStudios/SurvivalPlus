package com.fattymieo.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

import com.fattymieo.survival.Survival;

public class ToggleChat implements CommandExecutor
{
	Objective chat = Survival.board.getObjective("Chat");
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(command.getName().equalsIgnoreCase("togglechat"))
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage(Survival.Words.get("Works on players only"));
				return false;
			}
			
			if(args.length == 1)
			{
				Player player = (Player) sender;
				switch(args[0])
				{
					case "local":
					case "l":
						player.sendMessage(Survival.Words.get("§bToggled to §aLocal§b Chat"));
						chat.getScore(player).setScore(0);
						break;
					case "global":
					case "g":
						player.sendMessage(Survival.Words.get("§bToggled to §2Global§b Chat"));
						chat.getScore(player).setScore(1);
						break;
					default:
						return false;
				}
			}
			else if(args.length == 0)
			{
				Player player = (Player) sender;
				switch(chat.getScore(player).getScore())
				{
					case 0:
						player.sendMessage(Survival.Words.get("§bToggled to §2Global§b Chat"));
						chat.getScore(player).setScore(1);
						break;
					default:
						player.sendMessage(Survival.Words.get("§bToggled to §aLocal§b Chat"));
						chat.getScore(player).setScore(0);
						break;
				}
			}
			else
			{
				sender.sendMessage(ChatColor.RED + Survival.Words.get("Invalid Arguments"));
				return false;
			}

			return true;
		}
		else return false;
	}
}
