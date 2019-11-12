package tk.shanebee.survival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import org.bukkit.scoreboard.Scoreboard;
import tk.shanebee.survival.Survival;

class LocalChat implements Listener {

	private Scoreboard board;
	private FileConfiguration settings;

	LocalChat(Survival plugin) {
		this.board = plugin.getBoard();
		this.settings = plugin.getSettings();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		String msg = event.getMessage();

		if (settings.getBoolean("LegendaryItems.GoldArmorBuff")) {
			if (player.getInventory().getHelmet() != null) {
				if (player.getInventory().getHelmet().getType() == Material.GOLDEN_HELMET) {
					event.setCancelled(false);
					event.setFormat(ChatColor.GOLD + "<%1$s> " + ChatColor.YELLOW + "%2$s");
					return;
				}
			}
		}

		int channel = board.getObjective("Chat").getScore(player.getName()).getScore();
		if (channel > 0) {
			event.setFormat(ChatColor.GREEN + "<%1$s> " + ChatColor.RESET + "%2$s");
			return;
		}

		event.setCancelled(true);

		Bukkit.getConsoleSender().sendMessage("<" + player.getDisplayName() + "> " + msg);
		for (Player other : Bukkit.getServer().getOnlinePlayers()) {
			if (other.getLocation().getWorld() == player.getLocation().getWorld()) {
				double maxDist = 64;
				if (other.getLocation().distance(player.getLocation()) <= maxDist) {
					other.sendMessage(ChatColor.RESET + "<" + player.getDisplayName() + "> " + msg);
				}
			}
		}
	}

}