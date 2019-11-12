package tk.shanebee.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.util.StringUtil;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.util.Config;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Status implements CommandExecutor, TabCompleter {

	private Objective boardHunger;
	private Objective boardThirst;
	private Objective boardFatigue;
	private Objective boardNutrients;
	private Config config;
	private Lang lang;
	private PlayerManager playerManager;

	public Status(Survival plugin) {
		this.config = plugin.getSurvivalConfig();
		this.lang = plugin.getLang();
		this.playerManager = plugin.getPlayerManager();
		boardHunger = plugin.getMainBoard().getObjective("BoardHunger");
		boardThirst = plugin.getMainBoard().getObjective("BoardThirst");
		boardFatigue = plugin.getMainBoard().getObjective("BoardFatigue");
		boardNutrients = plugin.getMainBoard().getObjective("BoardNutrients");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = "&7[&3SurvivalPlus&7] ";
		if (command.getName().equalsIgnoreCase("status")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Utils.getColoredString(lang.players_only));
				return false;
			}

			Player player = (Player) sender;

			if (args.length == 0) {
				if (!config.MECHANICS_STATUS_SCOREBOARD) {
					player.sendMessage(playerManager.ShowHunger(player).get(1) + playerManager.ShowHunger(player).get(2) + " " +
							playerManager.ShowHunger(player).get(0).toUpperCase());
					if (config.MECHANICS_THIRST_ENABLED)
						player.sendMessage(playerManager.ShowThirst(player).get(1) + playerManager.ShowThirst(player).get(2) + " " +
								playerManager.ShowThirst(player).get(0).toUpperCase());
				} else {
					//boardHunger.getScore(player.getName()).setScore(0);
					//boardThirst.getScore(player.getName()).setScore(0);
					//boardFatigue.getScore(player.getName()).setScore(1);
					//boardNutrients.getScore(player.getName()).setScore(1);

					// New help message
					sendColoredMessage(player, prefix + "&6HealthBoard");
					sendColoredMessage(player, "  &b/stat all &7- Show your entire health board");
					sendColoredMessage(player, "  &b/stat hunger &7- Turn on/off hunger");
					sendColoredMessage(player, "  &b/stat thirst &7- Turn on/off thirst");
					sendColoredMessage(player, "  &b/stat fatigue &7- Turn on/off fatigue");
					sendColoredMessage(player, "  &b/stat nutrients &7- Turn on/off nutrients");
				}
			}

			if (args.length == 1) {
				switch (args[0]) {
					case "all":
						if (!config.MECHANICS_STATUS_SCOREBOARD) {
							player.sendMessage(playerManager.ShowHunger(player).get(1) + playerManager.ShowHunger(player).get(2) + " " +
									playerManager.ShowHunger(player).get(0).toUpperCase());
							if (config.MECHANICS_THIRST_ENABLED)
								player.sendMessage(playerManager.ShowThirst(player).get(1) + playerManager.ShowThirst(player).get(2) + " " +
										playerManager.ShowThirst(player).get(0).toUpperCase());
							if (config.MECHANICS_BED_FATIGUE_ENABLED)
								player.sendMessage(playerManager.ShowFatigue(player));
							if (config.MECHANICS_FOOD_DIVERSITY_ENABLED) {
								for (String s : playerManager.ShowNutrients(player))
									player.sendMessage(s);
							}
						} else {
							boardHunger.getScore(player.getName()).setScore(0);
							if (config.MECHANICS_THIRST_ENABLED)
								boardThirst.getScore(player.getName()).setScore(0);
							if (config.MECHANICS_BED_FATIGUE_ENABLED)
								boardFatigue.getScore(player.getName()).setScore(0);
							if (config.MECHANICS_FOOD_DIVERSITY_ENABLED)
								boardNutrients.getScore(player.getName()).setScore(0);
						}
						break;
					case "none":
					case "off":
						if (config.MECHANICS_STATUS_SCOREBOARD) {
							boardHunger.getScore(player.getName()).setScore(1);
							boardThirst.getScore(player.getName()).setScore(1);
							boardFatigue.getScore(player.getName()).setScore(1);
							boardNutrients.getScore(player.getName()).setScore(1);
						}
						break;
					case "hunger":
					case "h":
						if (!config.MECHANICS_STATUS_SCOREBOARD) {
							player.sendMessage(playerManager.ShowHunger(player).get(1) + playerManager.ShowHunger(player).get(2) + " " +
									playerManager.ShowHunger(player).get(0).toUpperCase());
						} else
							boardHunger.getScore(player.getName()).setScore(Reverse(boardHunger.getScore(player.getName()).getScore()));
						break;
					case "thirst":
					case "t":
						if (!config.MECHANICS_STATUS_SCOREBOARD) {
							if (config.MECHANICS_THIRST_ENABLED)
								player.sendMessage(playerManager.ShowThirst(player).get(1) + playerManager.ShowThirst(player).get(2) + " " +
										playerManager.ShowThirst(player).get(0).toUpperCase());
						} else
							boardThirst.getScore(player.getName()).setScore(Reverse(boardThirst.getScore(player.getName()).getScore()));
						break;
					case "fatigue":
					case "f":
						if (!config.MECHANICS_STATUS_SCOREBOARD) {
							if (config.MECHANICS_BED_FATIGUE_ENABLED)
								player.sendMessage(playerManager.ShowFatigue(player));
						} else
							boardFatigue.getScore(player.getName()).setScore(Reverse(boardFatigue.getScore(player.getName()).getScore()));
						break;
					case "nutrients":
					case "n":
						if (!config.MECHANICS_STATUS_SCOREBOARD) {
							if (config.MECHANICS_FOOD_DIVERSITY_ENABLED) {
								for (String s : playerManager.ShowNutrients(player))
									player.sendMessage(s);
							}
						} else
							boardNutrients.getScore(player.getName()).setScore(Reverse(boardNutrients.getScore(player.getName()).getScore()));
						break;
					default:
						sendColoredMessage(player, prefix + "&6HealthBoard");
						sendColoredMessage(player, "  &b/stat all &7- Show your entire health board");
						sendColoredMessage(player, "  &b/stat hunger &7- Turn on/off hunger");
						sendColoredMessage(player, "  &b/stat thirst &7- Turn on/off thirst");
						sendColoredMessage(player, "  &b/stat fatigue &7- Turn on/off fatigue");
						sendColoredMessage(player, "  &b/stat none &7- Turn off your entire health board");
						sendColoredMessage(player, "  &b/stat nutrients &7- Turn on/off nutrients");
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

	private void sendColoredMessage(Player player, String msg) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
		StringBuilder builder = new StringBuilder();
		for (String arg : args) {
			builder.append(arg).append(" ");
		}
		String[] list = {"all", "hunger", "thirst", "fatigue", "nutrients", "none", "help"};

		String arg = builder.toString().trim();
		ArrayList<String> matches = new ArrayList<>();
		for (String name : list) {
			if (StringUtil.startsWithIgnoreCase(name, arg)) {
				matches.add(name);
			}
		}
		return matches;
	}

}
