package com.shanebeestudios.survival.tasks;

import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.data.Permissions;
import com.shanebeestudios.survival.data.PlayerData;
import com.shanebeestudios.survival.events.ThirstLevelChangeEvent;
import com.shanebeestudios.survival.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("UnstableApiUsage")
class ThirstTask extends BukkitRunnable {

    private final Config config;
    private final PlayerManager playerManager;
    private final DamageSource damageSource = DamageSource.builder(DamageType.DRY_OUT).build();

    ThirstTask(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
        this.playerManager = plugin.getPlayerManager();
        this.runTaskTimer(plugin, 100, 100);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (Permissions.BYPASS_STAT_THIRST.has(player)) continue;

            if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) continue;

            PlayerData playerData = this.playerManager.getPlayerData(player);
            Environment environment = player.getWorld().getEnvironment();
            if (environment == Environment.NORMAL) {
                Block block = player.getLocation().getBlock();
                if (block.getTemperature() >= 1.5 && block.getLightLevel() >= 14) {
                    drain(player, playerData, this.config.mechanics_thirst_heat_drain_rate);
                }
            } else if (environment == Environment.NETHER) {
                drain(player, playerData, this.config.mechanics_thirst_nether_drain_rate);
            }
            // Damage player when thirst is too low
            if (playerData.getThirst() <= 0) {
                switch (player.getWorld().getDifficulty()) {
                    case EASY:
                        if (player.getHealth() > 10)
                            player.damage(this.config.mechanics_thirst_damage_rate, this.damageSource);
                        break;
                    case NORMAL:
                        if (player.getHealth() > 1)
                            player.damage(this.config.mechanics_thirst_damage_rate, this.damageSource);
                        break;
                    case HARD:
                        player.damage(this.config.mechanics_thirst_damage_rate, this.damageSource);
                        break;
                }
            }
        }
    }

    private void drain(Player player, PlayerData playerData, double change) {
        // Call thirst level change event
        ThirstLevelChangeEvent event = new ThirstLevelChangeEvent(player, change, playerData.getThirst() - change);
        if (event.callEvent()) {
            playerData.increaseThirst(-change);
        }
    }

}
