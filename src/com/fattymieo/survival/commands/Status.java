package com.fattymieo.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fattymieo.survival.Survival;

public class Status implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(command.getName().equalsIgnoreCase("status"))
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage(Survival.Words.get("Works on players only"));
				return false;
			}

			Player player = (Player) sender;
			
			if(args.length == 0)
			{
				Survival.ShowHunger(player);
				if(Survival.settings.getBoolean("Mechanics.Thirst.Enabled"))
					Survival.ShowThirst(player);
			}
			
			if(args.length == 1)
			{
				switch(args[0])
				{
					case "all":
						Survival.ShowHunger(player);
						if(Survival.settings.getBoolean("Mechanics.Thirst.Enabled"))
							Survival.ShowThirst(player);
						if(Survival.settings.getBoolean("Mechanics.BedFatigueLevel"))
							Survival.ShowFatigue(player);
						if(Survival.settings.getBoolean("Mechanics.FoodDiversity"))
							Survival.ShowNutrients(player);
						break;
					case "hunger":
					case "h":
						Survival.ShowHunger(player);
						break;
					case "thirst":
					case "t":
						if(Survival.settings.getBoolean("Mechanics.Thirst.Enabled"))
							Survival.ShowThirst(player);
						break;
					case "fatigue":
					case "f":
						if(Survival.settings.getBoolean("Mechanics.BedFatigueLevel"))
							Survival.ShowFatigue(player);
						break;
					case "nutrients":
					case "n":
						if(Survival.settings.getBoolean("Mechanics.FoodDiversity"))
							Survival.ShowNutrients(player);
						break;
					default:
						return false;
				}
			}

			return true;
		}
		else return false;
	}
}
