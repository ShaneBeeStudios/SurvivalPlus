package tk.shanebee.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.Info;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.util.Config;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class Status implements CommandExecutor, TabCompleter {

	private Config config;
	private Lang lang;
	private PlayerManager playerManager;

	public Status(Survival plugin) {
		this.config = plugin.getSurvivalConfig();
		this.lang = plugin.getLang();
		this.playerManager = plugin.getPlayerManager();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = lang.prefix;
		if (command.getName().equalsIgnoreCase("status")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Utils.getColoredString(lang.players_only));
				return false;
			}

			Player player = (Player) sender;
			PlayerData playerData = playerManager.getPlayerData(player);

			if (args.length == 0) {
				if (!config.MECHANICS_STATUS_SCOREBOARD) {
					player.sendMessage(playerManager.ShowHunger(player).get(1) +
							playerManager.ShowHunger(player).get(2) + " " +
							playerManager.ShowHunger(player).get(0).toUpperCase());
					if (config.MECHANICS_THIRST_ENABLED)
						player.sendMessage(playerManager.ShowThirst(player).get(1) +
								playerManager.ShowThirst(player).get(2) + " " +
								playerManager.ShowThirst(player).get(0).toUpperCase());
				} else {
					// New help message
					Utils.sendColoredMsg(player, prefix + "&6HealthBoard");
					Utils.sendColoredMsg(player, "  &b/stat all &7- Show your entire health board");
					Utils.sendColoredMsg(player, "  &b/stat none &7- Turn off your entire health board");
					Utils.sendColoredMsg(player, "  &b/stat hunger &7- Turn on/off hunger");
					Utils.sendColoredMsg(player, "  &b/stat thirst &7- Turn on/off thirst");
					Utils.sendColoredMsg(player, "  &b/stat fatigue &7- Turn on/off fatigue");
					Utils.sendColoredMsg(player, "  &b/stat nutrients &7- Turn on/off nutrients");
				}
			}

			if (args.length == 1) {
				switch (args[0]) {
					case "all":
						if (!config.MECHANICS_STATUS_SCOREBOARD) {
							player.sendMessage(playerManager.ShowHunger(player).get(1) +
									playerManager.ShowHunger(player).get(2) + " " +
									playerManager.ShowHunger(player).get(0).toUpperCase());
							if (config.MECHANICS_THIRST_ENABLED)
								player.sendMessage(playerManager.ShowThirst(player).get(1) +
										playerManager.ShowThirst(player).get(2) + " " +
										playerManager.ShowThirst(player).get(0).toUpperCase());
							if (config.MECHANICS_BED_FATIGUE_ENABLED)
								player.sendMessage(playerManager.ShowFatigue(player));
							if (config.MECHANICS_FOOD_DIVERSITY_ENABLED) {
								for (String s : playerManager.ShowNutrients(player))
									player.sendMessage(s);
							}
						} else {
							playerData.setShowInfo(Info.HUNGER, true);
							if (config.MECHANICS_THIRST_ENABLED)
								playerData.setShowInfo(Info.THIRST, true);
							if (config.MECHANICS_BED_FATIGUE_ENABLED)
								playerData.setShowInfo(Info.FATIGUE, true);
							if (config.MECHANICS_FOOD_DIVERSITY_ENABLED)
								playerData.setShowInfo(Info.NUTRIENTS, true);
						}
						break;
					case "none":
					case "off":
						playerData.setShowInfo(Info.HUNGER, false);
						playerData.setShowInfo(Info.THIRST, false);
						playerData.setShowInfo(Info.FATIGUE, false);
						playerData.setShowInfo(Info.NUTRIENTS, false);
						break;
					case "hunger":
					case "h":
						if (!config.MECHANICS_STATUS_SCOREBOARD) {
							player.sendMessage(playerManager.ShowHunger(player).get(1) +
									playerManager.ShowHunger(player).get(2) + " " +
									playerManager.ShowHunger(player).get(0).toUpperCase());
						} else
							playerData.setShowInfo(Info.HUNGER, !playerData.showInfo(Info.HUNGER));
						break;
					case "thirst":
					case "t":
						if (!config.MECHANICS_STATUS_SCOREBOARD) {
							if (config.MECHANICS_THIRST_ENABLED)
								player.sendMessage(playerManager.ShowThirst(player).get(1) +
										playerManager.ShowThirst(player).get(2) + " " +
										playerManager.ShowThirst(player).get(0).toUpperCase());
						} else
							playerData.setShowInfo(Info.THIRST, !playerData.showInfo(Info.THIRST));
						break;
					case "fatigue":
					case "f":
						if (!config.MECHANICS_STATUS_SCOREBOARD) {
							if (config.MECHANICS_BED_FATIGUE_ENABLED)
								player.sendMessage(playerManager.ShowFatigue(player));
						} else
							playerData.setShowInfo(Info.FATIGUE, !playerData.showInfo(Info.FATIGUE));
						break;
					case "nutrients":
					case "n":
						if (!config.MECHANICS_STATUS_SCOREBOARD) {
							if (config.MECHANICS_FOOD_DIVERSITY_ENABLED) {
								for (String s : playerManager.ShowNutrients(player))
									player.sendMessage(s);
							}
						} else
							playerData.setShowInfo(Info.NUTRIENTS, !playerData.showInfo(Info.NUTRIENTS));
						break;
					default:
						Utils.sendColoredMsg(player, prefix + "&6HealthBoard");
						Utils.sendColoredMsg(player, "  &b/stat all &7- Show your entire health board");
						Utils.sendColoredMsg(player, "  &b/stat none &7- Turn off your entire health board");
						Utils.sendColoredMsg(player, "  &b/stat hunger &7- Turn on/off hunger");
						Utils.sendColoredMsg(player, "  &b/stat thirst &7- Turn on/off thirst");
						Utils.sendColoredMsg(player, "  &b/stat fatigue &7- Turn on/off fatigue");
						Utils.sendColoredMsg(player, "  &b/stat nutrients &7- Turn on/off nutrients");
				}
			}
			return true;
		} else
			return false;
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
