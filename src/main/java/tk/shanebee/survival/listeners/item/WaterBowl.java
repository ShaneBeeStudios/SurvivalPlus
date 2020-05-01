package tk.shanebee.survival.listeners.item;

import tk.shanebee.survival.events.WaterBowlFillEvent;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import tk.shanebee.survival.Survival;

public class WaterBowl implements Listener {

	private final Survival plugin;
	private final boolean THIRST_ENABLED;
	private final boolean CLAY_ENABLED;

	public WaterBowl(Survival plugin) {
		this.plugin = plugin;
		this.THIRST_ENABLED = plugin.getSurvivalConfig().MECHANICS_THIRST_ENABLED;
		this.CLAY_ENABLED = plugin.getSurvivalConfig().RECIPES_CLAY;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onConsume(PlayerItemConsumeEvent event) {
		if (!THIRST_ENABLED) {
			if (event.isCancelled()) return;
			if (ItemManager.compare(event.getItem(), Item.WATER_BOWL)) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	private void onDrop(ItemSpawnEvent event) {
		if (event.isCancelled()) return;
		if (THIRST_ENABLED || CLAY_ENABLED) {
			final org.bukkit.entity.Item itemDrop = event.getEntity();
			if (itemDrop.getItemStack().getType() == Material.BOWL) {
				final Runnable task = () -> {
					Location itemLocation = itemDrop.getLocation();
					if (itemLocation.getBlock().getType() == Material.WATER) {
						WaterBowlFillEvent bowlFillEvent = new WaterBowlFillEvent(itemDrop.getItemStack());
						Bukkit.getPluginManager().callEvent(bowlFillEvent);
						if (bowlFillEvent.isCancelled()) return;
						int amount = itemDrop.getItemStack().getAmount();
						itemDrop.remove();
						for (int i = 0; i < amount; i++) {
                            itemDrop.getWorld().dropItem(itemLocation, Item.WATER_BOWL.getItem());
                        }
					}
				};
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, task, 20L);
			}
		}
	}

}
