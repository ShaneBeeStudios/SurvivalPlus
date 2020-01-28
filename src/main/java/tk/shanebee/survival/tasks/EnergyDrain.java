package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;

public class EnergyDrain extends BukkitRunnable {

    private PlayerManager playerManager;
    private double drainRate;
    private double increaseRate;

    public EnergyDrain(Survival plugin) {
        this.playerManager = plugin.getPlayerManager();
        Config config = plugin.getSurvivalConfig();

        drainRate = config.MECHANICS_ENERGY_DRAIN_RATE; // amount of energy to drain every 5 seconds
        increaseRate = config.MECHANICS_ENERGY_REFRESH_RATE; // amount of energy to gain every 5 seconds of sleeping
        this.runTaskTimer(plugin, -1, 5 * 20);
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
                playerData.increaseEnergy(-this.drainRate);
                effects(player, playerData);
            }
        }
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
        MINING_120 = new PotionEffect(PotionEffectType.SLOW_DIGGING, 120, 0);
        MINING_120_2 = new PotionEffect(PotionEffectType.SLOW_DIGGING, 120, 1);
        SLOW_120 = new PotionEffect(PotionEffectType.SLOW, 120, 0);
        WITHER_100 = new PotionEffect(PotionEffectType.WITHER, 100, 0);
        HASTE_120 = new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 0);
        ABSORPTION_500 = new PotionEffect(PotionEffectType.ABSORPTION, 500, 3);
    }

    private void effects(Player player, PlayerData playerData) {
        double energy = playerData.getEnergy();

        if (energy <= 1) {
            player.addPotionEffect(WITHER_100, true);
        } else if (energy <= 2.0) {
            player.addPotionEffect(SICK_120, true);
            player.addPotionEffect(NIGHT_120, true);
            player.addPotionEffect(BLIND_120, true);
            player.addPotionEffect(MINING_120_2, true);
            player.addPotionEffect(SLOW_120, true);
        } else if (energy <= 3.5) {
            player.addPotionEffect(SICK_40, true);
            player.addPotionEffect(NIGHT_10, true);
            player.addPotionEffect(BLIND_50, true);
            player.addPotionEffect(MINING_120_2, true);
        } else if (energy <= 5.5) {
            player.addPotionEffect(MINING_120_2, true);
        } else if (energy < 10.0) {
            player.addPotionEffect(MINING_120, true);
        } else if (energy > 19.5) {
            player.addPotionEffect(ABSORPTION_500, true);
        } else if (energy > 18.5) {
            player.addPotionEffect(HASTE_120, true);
        }
        // TODO messages
    }

}
