package tk.shanebee.survival.commands;

import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reload implements CommandExecutor {

	private final Survival plugin;
	private final Lang lang;

	public Reload(Survival plugin) {
		this.plugin = plugin;
		this.lang = plugin.getLang();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		plugin.loadSettings(sender);
		Utils.sendColoredMsg(sender, lang.prefix + "&aReload complete");
		return true;
	}

}
