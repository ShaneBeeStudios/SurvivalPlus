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
import tk.shanebee.survival.util.Config;

class WaterBowl implements Listener {

	private Survival plugin;
	private final boolean THIRST_ENABLED;
	private final boolean CLAY_ENABLED;

	WaterBowl(Survival plugin) {
		this.plugin = plugin;
		this.THIRST_ENABLED = plugin.getSurvivalConfig().MECHANICS_THIRST_ENABLED;
		this.CLAY_ENABLED = plugin.getSurvivalConfig().RECIPES_CLAY;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onConsume(PlayerItemConsumeEvent event) {
		if (!THIRST_ENABLED) {
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
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, task, 20L);
			}
		}
	}

}
