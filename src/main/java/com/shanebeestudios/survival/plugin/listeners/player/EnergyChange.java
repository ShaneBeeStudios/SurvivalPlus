package com.shanebeestudios.survival.plugin.listeners.player;

import com.shanebeestudios.survival.api.data.PlayerData;
import com.shanebeestudios.survival.api.events.EnergyLevelChangeEvent;
import com.shanebeestudios.survival.api.item.Items;
import com.shanebeestudios.survival.api.util.Utils;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.plugin.config.Lang;
import com.shanebeestudios.survival.plugin.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.inventory.ItemStack;

public class EnergyChange implements Listener {

    private final PlayerManager playerManager;
    private final Config config;
    private final Lang lang;

    public EnergyChange(SurvivalPlugin plugin) {
        this.playerManager = plugin.getPlayerManager();
        this.config = plugin.getSurvivalConfig();
        this.lang = plugin.getLang();
    }

    @EventHandler // Reset energy on respawn
    private void onRespawn(PlayerRespawnEvent event) {
        if (event.getRespawnReason() != PlayerRespawnEvent.RespawnReason.DEATH) return;

        Player player = event.getPlayer();
        if (Utils.isCitizensNPC(player)) return;

        PlayerData playerData = this.playerManager.getPlayerData(player);

        double respawnAmount = this.config.mechanics_energy_respawn;
        double change = respawnAmount - playerData.getEnergy();
        EnergyLevelChangeEvent energyEvent = new EnergyLevelChangeEvent(player, change, respawnAmount);
        if (!energyEvent.callEvent()) return;
        playerData.setEnergy(respawnAmount);
    }

    @EventHandler // Give energy when drinking coffee
    private void onDrinkCoffee(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        PlayerData playerData = this.playerManager.getPlayerData(player);

        if (Items.COFFEE.is(item)) {
            EnergyLevelChangeEvent energyEvent = new EnergyLevelChangeEvent(player, 20.0 - playerData.getEnergy(), 20.0);
            if (!energyEvent.callEvent()) return;
            playerData.setEnergy(20.0);
        }
    }

    @EventHandler // Decrease energy when player does exhaustive tasks
    private void onExhausted(EntityExhaustionEvent event) {
        Player player = (Player) event.getEntity();

        float exhaustion = event.getExhaustion();
        if (player.getExhaustion() + exhaustion < 4.0f) return;

        double modifier = this.config.mechanics_energy_exhaustion;
        if (modifier <= 0) return;

        this.playerManager.getPlayerData(player).increaseEnergy(-modifier);
    }

    // Send messages when energy level decreases
    @EventHandler(ignoreCancelled = true)
    private void onEnergyDrop(EnergyLevelChangeEvent event) {
        if (!this.config.mechanics_energy_warning) return;
        if (event.getChanged() > 0) {
            Player player = event.getPlayer();
            PlayerData playerData = playerManager.getPlayerData(player);
            double level = event.getEnergyLevel();
            double newLevel = playerData.getEnergy();

            if (targetMatch(10.0, level, newLevel)) {
                Utils.sendColoredMini(player, this.lang.energy_level_10);
            } else if (targetMatch(6.5, level, newLevel)) {
                Utils.sendColoredMini(player, this.lang.energy_level_6_5);
            } else if (targetMatch(3.5, level, newLevel)) {
                Utils.sendColoredMini(player, this.lang.energy_level_3_5);
            } else if (targetMatch(2, level, newLevel)) {
                Utils.sendColoredMini(player, this.lang.energy_level_2);
            } else if (targetMatch(1, level, newLevel)) {
                Utils.sendColoredMini(player, this.lang.energy_level_1);
            }
        }
    }

    // Check if the change passed a certain amount
    private boolean targetMatch(double target, double level, double newLevel) {
        return level <= target && newLevel > target;
    }

    // Increase players energy when they wake up after the night skips
    @EventHandler
    private void onSkipNight(TimeSkipEvent event) {
        if (event.getSkipReason() != TimeSkipEvent.SkipReason.NIGHT_SKIP) return;

        for (Player player : event.getWorld().getPlayers()) {
            if (!player.isSleeping()) continue;
            PlayerData playerData = this.playerManager.getPlayerData(player);

            EnergyLevelChangeEvent energyEvent = new EnergyLevelChangeEvent(player, 20.0 - playerData.getEnergy(), 20.0);
            if (!energyEvent.callEvent()) return;
            playerData.setEnergy(20);
        }
    }

}
