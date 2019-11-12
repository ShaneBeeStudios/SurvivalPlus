package tk.shanebee.survival.commands;

import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

import tk.shanebee.survival.Survival;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ToggleChat implements CommandExecutor, TabCompleter {

	private Lang lang;
	private Objective chat;
	
	public ToggleChat(Survival plugin) {
		this.lang = plugin.getLang();
		this.chat = plugin.getBoard().getObjective("Chat");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = "&7[&3SurvivalPlus&7] ";
		if (command.getName().equalsIgnoreCase("togglechat")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Utils.getColoredString(lang.players_only));
				return true;
			}
			Player player = (Player) sender;
			//if (LocalChatDist > -1)
			if (Survival.LocalChatDist <= -1) {
				player.sendMessage(Utils.getColoredString(lang.toggle_chat_disabled));
				return true;
			}

			if (args.length == 1) {

				switch (args[0]) {
					case "local":
					case "l":
						player.sendMessage(Utils.getColoredString(lang.toggle_chat_local));
						chat.getScore(player.getName()).setScore(0);
						break;
					case "global":
					case "g":
						player.sendMessage(Utils.getColoredString(lang.toggle_chat_global));
						chat.getScore(player.getName()).setScore(1);
						break;
					default:
						return false;
				}
			} else if (args.length == 0) {
				if (chat.getScore(player.getName()).getScore() == 0) {
					player.sendMessage(Utils.getColoredString(lang.toggle_chat_global));
					chat.getScore(player.getName()).setScore(1);
				} else {
					player.sendMessage(Utils.getColoredString(lang.toggle_chat_local));
					chat.getScore(player.getName()).setScore(0);
				}
			} else {
				sender.sendMessage(ChatColor.RED + Utils.getColoredString(lang.invalid_arg));
				return false;
			}

			return true;
		} else {
			sender.sendMessage("Command: " + command.getName());
			return true;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
		StringBuilder builder = new StringBuilder();
		for (String arg : args) {
			builder.append(arg).append(" ");
		}
		String[] list = {"local", "global"};
		String[] list2 = {""};
		String arg = builder.toString().trim();
		ArrayList<String> matches = new ArrayList<>();
		for (String name : (Survival.LocalChatDist > -1) ? list : list2) {
			if (StringUtil.startsWithIgnoreCase(name, arg)) {
				matches.add(name);
			}
		}
		return matches;
	}

}
