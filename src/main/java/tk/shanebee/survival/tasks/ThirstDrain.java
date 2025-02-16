package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;

@SuppressWarnings("UnstableApiUsage")
class ThirstDrain extends BukkitRunnable {

    private final PlayerManager playerManager;
    private final double damageRate;

    ThirstDrain(SurvivalPlugin plugin) {
        this.playerManager = plugin.getPlayerManager();
        this.damageRate = plugin.getSurvivalConfig().mechanics_thirst_damage_rate;
        this.runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) continue;

            // Damage player when thirst is too low
            PlayerData playerData = playerManager.getPlayerData(player);

            if (playerData.getThirst() <= 0) {
                DamageSource damageSource = DamageSource.builder(DamageType.DRY_OUT).build();
                switch (player.getWorld().getDifficulty()) {
                    case EASY:
                        if (player.getHealth() > 10)
                            player.damage(this.damageRate, damageSource);
                        break;
                    case NORMAL:
                        if (player.getHealth() > 1)
                            player.damage(this.damageRate, damageSource);
                        break;
                    case HARD:
                        player.damage(this.damageRate, damageSource);
                        break;
                    default:
                }
            }
        }
    }

}
