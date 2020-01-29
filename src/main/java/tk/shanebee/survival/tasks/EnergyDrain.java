package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.util.Utils;

public class EnergyDrain extends BukkitRunnable {

    private PlayerManager playerManager;
    private Config config;
    private Lang lang;
    private double drainRate;
    private double increaseRate;
    private double absorb;
    private double haste;

    public EnergyDrain(Survival plugin) {
        this.playerManager = plugin.getPlayerManager();
        this.config = plugin.getSurvivalConfig();
        this.lang = plugin.getLang();

        this.drainRate = config.MECHANICS_ENERGY_DRAIN_RATE; // amount of energy to drain every 5 seconds
        this.increaseRate = config.MECHANICS_ENERGY_REFRESH_RATE; // amount of energy to gain every 5 seconds of sleeping
        this.absorb = 20 - (drainRate * 12); // Roughly 1 minute of absorption hearts after full energy
        this.haste = 20 - (drainRate * 25); // Roughly 2 minutes of haste after full energy
        this.runTaskTimer(plugin, 5 * 20, 5 * 20);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = playerManager.getPlayerData(player);
            GameMode mode = player.getGameMode();
            if (mode == GameMode.SPECTATOR || mode == GameMode.CREATIVE) continue;
            if (player.isSleeping()) {
                playerData.increaseEnergy(this.increaseRate);
            } else {
                double oldLevel = playerData.getEnergy();
                playerData.increaseEnergy(-this.drainRate);
                double newLevel = playerData.getEnergy();
                if (config.MECHANICS_ENERGY_WARNING) {
                    if (targetMatch(10.0, oldLevel, newLevel)) {
                        Utils.sendColoredMsg(player, lang.energy_level_10);
                    } else if (targetMatch(6.5, oldLevel, newLevel)) {
                        Utils.sendColoredMsg(player, lang.energy_level_6_5);
                    } else if (targetMatch(3.5, oldLevel, newLevel)) {
                        Utils.sendColoredMsg(player, lang.energy_level_3_5);
                    } else if (targetMatch(2, oldLevel, newLevel)) {
                        Utils.sendColoredMsg(player, lang.energy_level_2);
                    } else if (targetMatch(1, oldLevel, newLevel)) {
                        Utils.sendColoredMsg(player, lang.energy_level_1);
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
    private static final PotionEffect BLIND_50;
    private static final PotionEffect BLIND_120;
    private static final PotionEffect NIGHT_10;
    private static final PotionEffect NIGHT_120;
    private static final PotionEffect MINING_120;
    private static final PotionEffect MINING_120_2;
    private static final PotionEffect MINING_120_3;
    private static final PotionEffect SLOW_120;
    private static final PotionEffect WITHER_100;

    // GOOD EFFECTS
    private static final PotionEffect HASTE_120;
    private static final PotionEffect ABSORPTION_500;

    static {
        SICK_40 = new PotionEffect(PotionEffectType.CONFUSION, 40, 0);
        SICK_120 = new PotionEffect(PotionEffectType.CONFUSION, 120, 0);
        BLIND_50 = new PotionEffect(PotionEffectType.BLINDNESS, 50, 0);
        BLIND_120 = new PotionEffect(PotionEffectType.BLINDNESS, 120, 0);
        NIGHT_10 = new PotionEffect(PotionEffectType.NIGHT_VISION, 10, 0);
        NIGHT_120 = new PotionEffect(PotionEffectType.NIGHT_VISION, 120, 0);
        MINING_120 = new PotionEffect(PotionEffectType.SLOW_DIGGING, 120, 0, false, false);
        MINING_120_2 = new PotionEffect(PotionEffectType.SLOW_DIGGING, 120, 1, false, false);
        MINING_120_3 = new PotionEffect(PotionEffectType.SLOW_DIGGING, 120, 2, false, false);
        SLOW_120 = new PotionEffect(PotionEffectType.SLOW, 120, 0, false, false);
        WITHER_100 = new PotionEffect(PotionEffectType.WITHER, 100, 0);
        HASTE_120 = new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 0, false, false, true);
        ABSORPTION_500 = new PotionEffect(PotionEffectType.ABSORPTION, 500, 1, false, false);
    }

    private void effects(Player player, PlayerData playerData) {
        double energy = playerData.getEnergy();

        if (energy <= 1) {
            player.addPotionEffect(WITHER_100, true);
        } else if (energy <= 2.0) {
            player.addPotionEffect(SICK_120, true);
            player.addPotionEffect(NIGHT_120, true);
            player.addPotionEffect(BLIND_120, true);
            player.addPotionEffect(MINING_120_3, true);
            player.addPotionEffect(SLOW_120, true);
        } else if (energy <= 3.5) {
            player.addPotionEffect(SICK_40, true);
            player.addPotionEffect(NIGHT_10, true);
            player.addPotionEffect(BLIND_50, true);
            player.addPotionEffect(MINING_120_3, true);
        } else if (energy <= 6.5) {
            player.addPotionEffect(MINING_120_3, true);
        } else if (energy < 8) {
            player.addPotionEffect(MINING_120_2, true);
        } else if (energy < 10.0) {
            player.addPotionEffect(MINING_120, true);
        } else if (energy > this.absorb) {
            if (!player.hasPotionEffect(ABSORPTION_500.getType())) {
                player.addPotionEffect(ABSORPTION_500, true);
            }
            player.addPotionEffect(HASTE_120, true);
        } else if (energy > this.haste) {
            player.addPotionEffect(HASTE_120, true);
        }
    }

}
