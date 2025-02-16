package tk.shanebee.survival.tasks.tool;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.item.Items;

import java.util.Random;

public class BlazeSwordSound extends BukkitRunnable {

    private final SurvivalPlugin plugin;

    public BlazeSwordSound(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimer(plugin, 1, 50);
    }

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (Items.BLAZE_SWORD.is(player.getInventory().getItemInMainHand())) {
                Random rand = new Random();
                World world = player.getWorld();
                world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
            }
        }
    }

}
