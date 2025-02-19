package com.shanebeestudios.survival.plugin.tasks.tool;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.api.item.Items;

public class Valkyrie extends BukkitRunnable {

	private final SurvivalPlugin plugin;

	public Valkyrie(SurvivalPlugin plugin) {
		this.plugin = plugin;
		this.runTaskTimer(plugin, 1, 10);
	}

	@Override
	public void run() {
		for (Player player : this.plugin.getServer().getOnlinePlayers()) {
			if (Items.VALKYRIES_AXE.is(player.getInventory().getItemInMainHand())) {
				Location particleLoc = player.getLocation();
				particleLoc.setY(particleLoc.getY() + 1);
				assert particleLoc.getWorld() != null;
				particleLoc.getWorld().spawnParticle(Particle.CRIT, particleLoc, 10, 0.5, 0.5, 0.5);
			}
		}
	}

}
