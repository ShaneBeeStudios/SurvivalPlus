package tk.shanebee.survival.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.PlayerDataConfig;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;

class PlayerDataListener implements Listener {

	private PlayerManager playerManager;
	private PlayerDataConfig playerDataConfig;

	PlayerDataListener(Survival plugin) {
		this.playerManager = plugin.getPlayerManager();
		this.playerDataConfig = plugin.getPlayerDataConfig();
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!playerDataConfig.hasPlayerDataFile(player)) {
			playerManager.createNewPlayerData(player);
		}
	}

	@EventHandler
	private void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		PlayerData data = playerManager.getPlayerData(player);
		playerManager.savePlayerData(data);
	}

}
