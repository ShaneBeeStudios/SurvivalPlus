package com.shanebeestudios.survival.tasks;

import com.shanebeestudios.survival.data.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.config.Lang;
import com.shanebeestudios.survival.data.PlayerData;
import com.shanebeestudios.survival.managers.PlayerManager;
import com.shanebeestudios.survival.util.Utils;

public class EnergyDrain extends BukkitRunnable {

    private final PlayerManager playerManager;
    private final Config config;
    private final Lang lang;
    private final double absorb;
    private final double haste;

    public EnergyDrain(SurvivalPlugin plugin) {
        this.playerManager = plugin.getPlayerManager();
        this.config = plugin.getSurvivalConfig();
        this.lang = plugin.getLang();

        this.absorb = this.config.mechanics_energy_absorption ? 20 - (this.config.mechanics_energy_drain_rate * 12) : 200; // Roughly 1 minute of absorption hearts after full energy
        this.haste = this.config.mechanics_energy_haste ? 20 - (this.config.mechanics_energy_drain_rate * 25): 200; // Roughly 2 minutes of haste after full energy
        this.runTaskTimer(plugin, 5 * 20, 5 * 20);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Permissions.BYPASS_STAT_ENERGY.has(player)) continue;

            PlayerData playerData = playerManager.getPlayerData(player);
            GameMode mode = player.getGameMode();
            if (mode == GameMode.SPECTATOR || mode == GameMode.CREATIVE) continue;

            if (player.isSleeping()) {
                playerData.increaseEnergy(this.config.mechanics_energy_refresh_rate_bed);
            } else if (isSitting(player)) {
                playerData.increaseEnergy(this.config.mechanics_energy_refresh_rate_chair);
            } else {
                double oldLevel = playerData.getEnergy();
                double rate = this.config.mechanics_energy_drain_rate;
                if (this.config.mechanics_energy_drain_cold_rate > 0 && player.getWorld().getEnvironment() == Environment.NORMAL) {
                    Block block = player.getLocation().getBlock();
                    // In a cold biome and under direct sun or close to
                    if (block.getTemperature() < 0.15 && block.getLightFromSky() > 13) {
                        rate += this.config.mechanics_energy_drain_cold_rate;
                    }
                }
                playerData.increaseEnergy(-rate);
                double newLevel = playerData.getEnergy();
                if (this.config.mechanics_energy_warning) {
                    if (targetMatch(10.0, oldLevel, newLevel)) {
                        Utils.sendColoredMini(player, this.lang.energy_level_10);
                    } else if (targetMatch(6.5, oldLevel, newLevel)) {
                        Utils.sendColoredMini(player, this.lang.energy_level_6_5);
                    } else if (targetMatch(3.5, oldLevel, newLevel)) {
                        Utils.sendColoredMini(player, this.lang.energy_level_3_5);
                    } else if (targetMatch(2, oldLevel, newLevel)) {
                        Utils.sendColoredMini(player, this.lang.energy_level_2);
                    } else if (targetMatch(1, oldLevel, newLevel)) {
                        Utils.sendColoredMini(player, this.lang.energy_level_1);
                    }
                }
                effects(player, playerData);
            }
        }
    }

    // Check if the change passed a certain amount
    private boolean targetMatch(double target, double level, double newLevel) {
        return level > target && newLevel <= target;
    }

    // BAD EFFECTS
    private static final PotionEffect SICK_40;
    private static final PotionEffect SICK_120;
    private static final PotionEffect DARK_50;
    private static final PotionEffect DARK_120;
    private static final PotionEffect MINING_120;
    private static final PotionEffect MINING_120_2;
    private static final PotionEffect MINING_120_3;
    private static final PotionEffect SLOW_120;
    private static final PotionEffect WITHER_100;

    // GOOD EFFECTS
    private static final PotionEffect HASTE_120;
    private static final PotionEffect ABSORPTION_500;

    static {
        SICK_40 = new PotionEffect(PotionEffectType.NAUSEA, 40, 0);
        SICK_120 = new PotionEffect(PotionEffectType.NAUSEA, 120, 0);
        DARK_50 = new PotionEffect(PotionEffectType.DARKNESS, 50, 0);
        DARK_120 = new PotionEffect(PotionEffectType.DARKNESS, 120, 0);
        MINING_120 = new PotionEffect(PotionEffectType.MINING_FATIGUE, 120, 0, false, false);
        MINING_120_2 = new PotionEffect(PotionEffectType.MINING_FATIGUE, 120, 1, false, false);
        MINING_120_3 = new PotionEffect(PotionEffectType.MINING_FATIGUE, 120, 2, false, false);
        SLOW_120 = new PotionEffect(PotionEffectType.SLOWNESS, 120, 0, false, false);
        WITHER_100 = new PotionEffect(PotionEffectType.WITHER, 100, 0, false, true, false);
        HASTE_120 = new PotionEffect(PotionEffectType.HASTE, 120, 0, false, false, false);
        ABSORPTION_500 = new PotionEffect(PotionEffectType.ABSORPTION, 500, 1, false, false);
    }

    // TODO I want to eventually redo this.
    // maybe with attributes instead?
    private void effects(Player player, PlayerData playerData) {
        double energy = playerData.getEnergy();

        if (energy <= 1) {
            player.addPotionEffect(WITHER_100);
        } else if (energy <= 2.0) {
            player.addPotionEffect(SICK_120);
            player.addPotionEffect(DARK_120);
            player.addPotionEffect(MINING_120_3);
            player.addPotionEffect(SLOW_120);
        } else if (energy <= 3.5) {
            player.addPotionEffect(SICK_40);
            player.addPotionEffect(DARK_50);
            player.addPotionEffect(MINING_120_3);
        } else if (energy <= 6.5) {
            player.addPotionEffect(MINING_120_3);
        } else if (energy < 8) {
            player.addPotionEffect(MINING_120_2);
        } else if (energy < 10.0) {
            player.addPotionEffect(MINING_120);
        } else if (energy > this.absorb) {
            if (!player.hasPotionEffect(ABSORPTION_500.getType())) {
                player.addPotionEffect(ABSORPTION_500);
            }
            player.addPotionEffect(HASTE_120);
        } else if (energy > this.haste) {
            player.addPotionEffect(HASTE_120);
        }
    }

    @SuppressWarnings("deprecation")
    private boolean isSitting(Player player) {
        if (!this.config.mechanics_chairs_enabled) return false;
        Entity vehicle = player.getVehicle();
        if (vehicle instanceof ArmorStand) {
            String name = vehicle.getCustomName();
            return name != null && name.equalsIgnoreCase("Chair");

        }
        return false;
    }

}
