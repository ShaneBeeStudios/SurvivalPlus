package com.shanebeestudios.survival.plugin.listeners.player;

import com.shanebeestudios.survival.api.data.Permissions;
import com.shanebeestudios.survival.api.data.PlayerData;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ThirstListener implements Listener {

    private final SurvivalPlugin plugin;
    private final Config config;
    private final PlayerManager playerManager;

    public ThirstListener(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
        this.playerManager = plugin.getPlayerManager();
    }

    @EventHandler
    private void onExhaustionReached(EntityExhaustionEvent event) {
        Player player = (Player) event.getEntity();
        if (Permissions.BYPASS_STAT_THIRST.has(player)) return;

        float exhaustion = event.getExhaustion();
        if (player.getExhaustion() + exhaustion < 4.0f) return;

        PlayerData playerData = this.playerManager.getPlayerData(player);

        double change = this.config.mechanics_thirst_drain_rate;

        // Prevent calling thirst event if there is no change
        if (change == 0) return;

        playerData.increaseThirst(-change);
    }


    @EventHandler
    private void onRespawn(PlayerRespawnEvent event) {
        if (event.getRespawnReason() != PlayerRespawnEvent.RespawnReason.DEATH) return;

        Player player = event.getPlayer();
        if (Permissions.BYPASS_STAT_THIRST.has(player)) return;

        PlayerData playerData = this.playerManager.getPlayerData(player);
        double thirst = this.config.mechanics_thirst_respawn_amount;
        playerData.setThirst(thirst);

        double hunger = this.config.mechanics_hunger_respawn_amount;
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> playerData.setHunger(hunger), 1);
    }

}
