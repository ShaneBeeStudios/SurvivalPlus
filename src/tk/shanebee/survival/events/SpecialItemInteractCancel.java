package tk.shanebee.survival.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tk.shanebee.survival.Survival;

public class SpecialItemInteractCancel implements Listener {

	@EventHandler
	private void onInteractBlock(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			switch (event.getMaterial()) {
				case WOODEN_HOE:
					if (Survival.settings.getBoolean("Survival.Enabled")) {
						switch (event.getClickedBlock().getType()) {
							case DIRT:
							case GRASS:
								event.setCancelled(true);
								break;

							default:
						}
					}
					break;
				case GOLDEN_HOE:
					if (Survival.settings.getBoolean("LegendaryItems.GiantBlade")) {
						switch (event.getClickedBlock().getType()) {
							case DIRT:
							case GRASS:
								event.setCancelled(true);
								break;

							default:
						}
					}
					break;
				case WOODEN_SHOVEL:
					if (Survival.settings.getBoolean("Survival.Enabled")) {
						switch (event.getClickedBlock().getType()) {
							case GRASS:
								event.setCancelled(true);
								break;

							default:
						}
					}
					break;
				case GOLDEN_SHOVEL:
					if (Survival.settings.getBoolean("LegendaryItems.ObsidianMace")) {
						switch (event.getClickedBlock().getType()) {
							case GRASS:
								event.setCancelled(true);
								break;

							default:
						}
					}
					break;
				default:
			}
		}
	}

}
