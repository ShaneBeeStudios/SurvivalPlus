package tk.shanebee.survival.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import tk.shanebee.survival.Survival;

public class RecurvedBow implements Listener {

	@EventHandler
	public void onShoot(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			ItemStack mainItem = event.getBow();

			if (mainItem.getItemMeta().getLore() != null) {
				Random rand = new Random();
				if (event.getForce() >= 1.0F) {
					final Entity arrow = event.getProjectile();
					final Vector velocity = player.getLocation().getDirection().add(new Vector(0, 0.025, 0)).multiply(4);
					arrow.setVelocity(velocity);

					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_BULLET_HURT, 0.5F, rand.nextFloat() * 0.4F + 0.8F);

					final Runnable task = new Runnable() {
						int times = 4;

						public void run() {
							if (!arrow.isOnGround()) {
								arrow.setVelocity(velocity);
								if (times-- > 0)
									Bukkit.getScheduler().scheduleSyncDelayedTask(Survival.instance, this, 5);
							}
						}
					};

					Bukkit.getScheduler().scheduleSyncDelayedTask(Survival.instance, task, -1);
				} else {
					event.setCancelled(true);
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 0.5F, rand.nextFloat() * 0.4F + 0.8F);
				}
			}
		}
	}

}
