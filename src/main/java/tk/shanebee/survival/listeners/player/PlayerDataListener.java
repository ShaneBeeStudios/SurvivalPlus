package tk.shanebee.survival.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.PlayerDataConfig;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.managers.ScoreBoardManager;
import tk.shanebee.survival.config.Config;

public class PlayerDataListener implements Listener {

	private PlayerManager playerManager;
	private PlayerDataConfig playerDataConfig;
	private ScoreBoardManager scoreboardManager;
	private Config config;

	public PlayerDataListener(Survival plugin) {
		this.playerManager = plugin.getPlayerManager();
		this.playerDataConfig = plugin.getPlayerDataConfig();
		this.scoreboardManager = plugin.getScoreboardManager();
		this.config = plugin.getSurvivalConfig();
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!playerDataConfig.hasPlayerDataFile(player)) {
			playerManager.createNewPlayerData(player);
		} else {
			playerManager.loadPlayerData(player);
		}
		if (config.MECHANICS_STATUS_SCOREBOARD)
			scoreboardManager.setupScoreboard(player);
	}

	@EventHandler
	private void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		playerManager.unloadPlayerData(player);
		scoreboardManager.unloadScoreboard(player);
	}

}
