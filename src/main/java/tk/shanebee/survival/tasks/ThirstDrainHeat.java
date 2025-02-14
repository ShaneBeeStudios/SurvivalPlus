package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.events.ThirstLevelChangeEvent;
import tk.shanebee.survival.managers.PlayerManager;

class ThirstDrainHeat extends BukkitRunnable {

    private final PlayerManager playerManager;

    ThirstDrainHeat(Survival plugin) {
        this.playerManager = plugin.getPlayerManager();
        this.runTaskTimer(plugin, 0, 20L * plugin.getSurvivalConfig().mechanics_thirst_heat_drain);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) continue;
            if (player.getWorld().getEnvironment() != Environment.NORMAL) continue;
            Block block = player.getLocation().getBlock();
            if (block.getTemperature() < 1.0) continue; // Only hot biomes
            if (block.getLightFromSky() < 15) continue; // Must be exposed to sunlight

            PlayerData playerData = this.playerManager.getPlayerData(player);
            int change = 1;
            // Call thirst level change event
            ThirstLevelChangeEvent event = new ThirstLevelChangeEvent(player, change, playerData.getThirst() - change);
            if (event.callEvent()) {
                playerData.increaseThirst(-change);
            }
        }
    }

}
