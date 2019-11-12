package tk.shanebee.survival.commands;

import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reload implements CommandExecutor {

	private Survival plugin;
	private Lang lang;

	public Reload(Survival plugin) {
		this.plugin = plugin;
		this.lang = plugin.getLang();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = lang.prefix;

		plugin.loadSettings();
		Utils.sendColoredMsg(sender, prefix + "&7Config &areloaded");

		lang.loadLangFile(sender);
		Utils.sendColoredMsg(sender, prefix + "&7Lang file &areloaded");
		Utils.sendColoredMsg(sender, prefix + "&aReload complete");
		return true;
	}

}
