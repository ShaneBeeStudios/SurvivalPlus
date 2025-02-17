package tk.shanebee.survival.listeners.server;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.config.Config;

public class LocalChat implements Listener {

	private Config config;
	private PlayerManager playerManager;

	public LocalChat(SurvivalPlugin plugin) {
		this.config = plugin.getSurvivalConfig();
		this.playerManager = plugin.getPlayerManager();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		PlayerData playerData = playerManager.getPlayerData(player);
		String msg = event.getMessage();

		if (config.legendary_gold_armor_buff) {
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
		double maxDist = config.settings_local_chat_distance;
		for (Player other : Bukkit.getServer().getOnlinePlayers()) {
			if (other.getLocation().getWorld() == player.getLocation().getWorld()) {
				if (other.getLocation().distance(player.getLocation()) <= maxDist) {
					other.sendMessage(ChatColor.RESET + "<" + player.getDisplayName() + "> " + msg);
				}
			}
		}
	}

}
