package tk.shanebee.survival.listeners;

import tk.shanebee.survival.events.WaterBowlFillEvent;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import tk.shanebee.survival.Survival;

class WaterBowl implements Listener {

	private final boolean THIRST_ENABLED = Survival.settings.getBoolean("Mechanics.Thirst.Enabled");
	private final boolean CLAY_ENABLED = Survival.settings.getBoolean("Recipes.Clay");

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onConsume(PlayerItemConsumeEvent event) {
		if (!Survival.settings.getBoolean("Mechanics.Thirst.Enabled")) {
			if (event.isCancelled()) return;
			if (ItemManager.compare(event.getItem(), Items.WATER_BOWL)) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	private void onDrop(ItemSpawnEvent event) {
		if (event.isCancelled()) return;
		if (THIRST_ENABLED || CLAY_ENABLED) {
			final Item itemDrop = event.getEntity();
			if (itemDrop.getItemStack().getType() == Material.BOWL) {
				final Runnable task = () -> {
					if (itemDrop.getItemStack().getAmount() != 1) return;
					Location itemLocation = itemDrop.getLocation();
					if (itemLocation.getBlock().getType() == Material.WATER) {
						WaterBowlFillEvent bowlFillEvent = new WaterBowlFillEvent(itemDrop.getItemStack());
						Bukkit.getPluginManager().callEvent(bowlFillEvent);
						if (bowlFillEvent.isCancelled()) return;
						itemDrop.remove();
						itemDrop.getWorld().dropItem(itemLocation, ItemManager.get(Items.WATER_BOWL));
					}
				};
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, task, 20L);
			}
		}
	}

}
