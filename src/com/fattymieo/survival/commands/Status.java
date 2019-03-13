package com.fattymieo.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

import com.fattymieo.survival.Survival;

public class Status implements CommandExecutor {

	private Objective boardHunger = Survival.mainBoard.getObjective("BoardHunger");
	private Objective boardThirst = Survival.mainBoard.getObjective("BoardThirst");
	private Objective boardFatigue = Survival.mainBoard.getObjective("BoardFatigue");
	private Objective boardNutrients = Survival.mainBoard.getObjective("BoardNutrients");

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("status")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Survival.Words.get("Works on players only"));
				return false;
			}

			Player player = (Player) sender;

			if (args.length == 0) {
				if (!Survival.settings.getBoolean("Mechanics.StatusScoreboard")) {
					player.sendMessage(Survival.ShowHunger(player).get(1) + Survival.ShowHunger(player).get(2) + " " + Survival.ShowHunger(player).get(0).toUpperCase());
					if (Survival.settings.getBoolean("Mechanics.Thirst.Enabled"))
						player.sendMessage(Survival.ShowThirst(player).get(1) + Survival.ShowThirst(player).get(2) + " " + Survival.ShowThirst(player).get(0).toUpperCase());
				} else {
					boardHunger.getScore(player.getName()).setScore(0);
					boardThirst.getScore(player.getName()).setScore(0);
					boardFatigue.getScore(player.getName()).setScore(1);
					boardNutrients.getScore(player.getName()).setScore(1);
				}
			}

			if (args.length == 1) {
				switch (args[0]) {
					case "all":
						if (!Survival.settings.getBoolean("Mechanics.StatusScoreboard")) {
							player.sendMessage(Survival.ShowHunger(player).get(1) + Survival.ShowHunger(player).get(2) + " " + Survival.ShowHunger(player).get(0).toUpperCase());
							if (Survival.settings.getBoolean("Mechanics.Thirst.Enabled"))
								player.sendMessage(Survival.ShowThirst(player).get(1) + Survival.ShowThirst(player).get(2) + " " + Survival.ShowThirst(player).get(0).toUpperCase());
							if (Survival.settings.getBoolean("Mechanics.BedFatigueLevel"))
								player.sendMessage(Survival.ShowFatigue(player));
							if (Survival.settings.getBoolean("Mechanics.FoodDiversity")) {
								for (String s : Survival.ShowNutrients(player))
									player.sendMessage(s);
							}
						} else {
							boardHunger.getScore(player.getName()).setScore(0);
							boardThirst.getScore(player.getName()).setScore(0);
							boardFatigue.getScore(player.getName()).setScore(0);
							boardNutrients.getScore(player.getName()).setScore(0);
						}
						break;
					case "hunger":
					case "h":
						if (!Survival.settings.getBoolean("Mechanics.StatusScoreboard")) {
							player.sendMessage(Survival.ShowHunger(player).get(1) + Survival.ShowHunger(player).get(2) + " " + Survival.ShowHunger(player).get(0).toUpperCase());
						} else
							boardHunger.getScore(player.getName()).setScore(Reverse(boardHunger.getScore(player.getName()).getScore()));
						break;
					case "thirst":
					case "t":
						if (!Survival.settings.getBoolean("Mechanics.StatusScoreboard")) {
							if (Survival.settings.getBoolean("Mechanics.Thirst.Enabled"))
								player.sendMessage(Survival.ShowThirst(player).get(1) + Survival.ShowThirst(player).get(2) + " " + Survival.ShowThirst(player).get(0).toUpperCase());
						} else
							boardThirst.getScore(player.getName()).setScore(Reverse(boardThirst.getScore(player.getName()).getScore()));
						break;
					case "fatigue":
					case "f":
						if (!Survival.settings.getBoolean("Mechanics.StatusScoreboard")) {
							if (Survival.settings.getBoolean("Mechanics.BedFatigueLevel"))
								player.sendMessage(Survival.ShowFatigue(player));
						} else
							boardFatigue.getScore(player.getName()).setScore(Reverse(boardFatigue.getScore(player.getName()).getScore()));
						break;
					case "nutrients":
					case "n":
						if (!Survival.settings.getBoolean("Mechanics.StatusScoreboard")) {
							if (Survival.settings.getBoolean("Mechanics.FoodDiversity")) {
								for (String s : Survival.ShowNutrients(player))
									player.sendMessage(s);
							}
						} else
							boardNutrients.getScore(player.getName()).setScore(Reverse(boardNutrients.getScore(player.getName()).getScore()));
						break;
					default:
						return false;
				}
			}

			return true;
		} else
			return false;
	}

	private int Reverse(int i) {
		if (i <= 0)
			i = 1;
		else if (i > 0)
			i = 0;
		return i;
	}

}
