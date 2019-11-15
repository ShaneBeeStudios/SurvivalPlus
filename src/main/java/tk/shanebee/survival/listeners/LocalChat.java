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
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.util.Config;

class LocalChat implements Listener {

	private Config config;
	private PlayerManager playerManager;

	LocalChat(Survival plugin) {
		this.config = plugin.getSurvivalConfig();
		this.playerManager = plugin.getPlayerManager();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		PlayerData playerData = playerManager.getPlayerData(player);
		String msg = event.getMessage();

		if (config.LEGENDARY_GOLDARMORBUFF) {
			if (player.getInventory().getHelmet() != null) {
				if (player.getInventory().getHelmet().getType() == Material.GOLDEN_HELMET) {
					event.setCancelled(false);
					event.setFormat(ChatColor.GOLD + "<%1$s> " + ChatColor.YELLOW + "%2$s");
					return;
				}
			}
		}

		// GLOBAL CHAT
		if (!playerData.isLocalChat()) {
			event.setFormat(ChatColor.GREEN + "<%1$s> " + ChatColor.RESET + "%2$s");
			return;
		}

		// LOCAL CHAT
		event.setCancelled(true);

		Bukkit.getConsoleSender().sendMessage("<" + player.getDisplayName() + "> " + msg);
		double maxDist = config.LOCAL_CHAT_DISTANCE;
		for (Player other : Bukkit.getServer().getOnlinePlayers()) {
			if (other.getLocation().getWorld() == player.getLocation().getWorld()) {
				if (other.getLocation().distance(player.getLocation()) <= maxDist) {
					other.sendMessage(ChatColor.RESET + "<" + player.getDisplayName() + "> " + msg);
				}
			}
		}
	}

}