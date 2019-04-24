package tk.shanebee.survival.events;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;

public class ChickenSpawn implements Listener {

	@EventHandler
	public void onChickenSpawn(CreatureSpawnEvent e) {
		if (e.getEntityType() == EntityType.CHICKEN) {
			if (e.getSpawnReason() == SpawnReason.BREEDING) {
				e.setCancelled(true);
				Random rand = new Random();
				Location loc = e.getLocation();
				loc.getWorld().dropItem(loc, new ItemStack(Material.EGG, rand.nextInt(4) + 1));
				loc.getWorld().playSound(loc, Sound.ENTITY_CHICKEN_EGG, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
			}
		}
	}

	@EventHandler
	public void onEggThrown(PlayerEggThrowEvent e) {
		e.setHatching(true);
		e.setNumHatches((byte) 1);
	}

}