package com.shanebeestudios.survival.plugin.tasks;

import com.shanebeestudios.survival.api.data.PlayerData;
import com.shanebeestudios.survival.api.util.Utils;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Lang;
import com.shanebeestudios.survival.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

class ThirstAlert extends BukkitRunnable {

    private final PlayerManager playerManager;
    private final Lang lang;

    ThirstAlert(SurvivalPlugin plugin) {
        this.playerManager = plugin.getPlayerManager();
        this.lang = plugin.getLang();
        final int ALERT_INTERVAL = plugin.getSurvivalConfig().MECHANICS_ALERT_INTERVAL;
        this.runTaskTimer(plugin, -1, ALERT_INTERVAL * 20L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                PlayerData playerData = playerManager.getPlayerData(player);
                int hunger = player.getFoodLevel();
                if (hunger <= 6) {
                    Utils.sendColoredMini(player, "<gold>" + this.lang.starved_eat);
                }
                if (playerData.getThirst() <= 6) {
                    Utils.sendColoredMini(player, "<aqua>" + this.lang.dehydrated_drink);
                }
            }
        }
    }

}
