package tk.shanebee.survival.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.config.PlayerDataConfig;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.managers.ScoreBoardManager;

public class PlayerDataListener implements Listener {

    private final Survival plugin;
    private final PlayerManager playerManager;
    private final PlayerDataConfig playerDataConfig;
    private final ScoreBoardManager scoreboardManager;
    private final Config config;

    public PlayerDataListener(Survival plugin) {
        this.plugin = plugin;
        this.playerManager = plugin.getPlayerManager();
        this.playerDataConfig = plugin.getPlayerDataConfig();
        this.scoreboardManager = plugin.getScoreboardManager();
        this.config = plugin.getSurvivalConfig();
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData;
        if (!playerDataConfig.hasPlayerDataFile(player)) {
            playerData = playerManager.createNewPlayerData(player);
        } else {
            playerData = playerManager.loadPlayerData(player);
        }
        if (config.MECHANICS_STATUS_SCOREBOARD)
            scoreboardManager.setupScoreboard(player);

        // Appears you can only set a compass target after a delay
        if (config.MECHANICS_COMPASS_WAYPOINT) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setCompassTarget(playerData.getCompassWaypoint(player.getWorld()));
                }
            }.runTaskLater(this.plugin, 1);
        }

    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerManager.unloadPlayerData(player);
        scoreboardManager.unloadScoreboard(player);
    }

}
