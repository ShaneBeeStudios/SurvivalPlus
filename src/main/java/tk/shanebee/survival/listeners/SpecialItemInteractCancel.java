package tk.shanebee.survival.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Config;

class SpecialItemInteractCancel implements Listener {

	private Config config;
	
	SpecialItemInteractCancel(Survival plugin) {
		this.config = plugin.getSurvivalConfig();
	}

	@EventHandler
	private void onInteractBlock(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			switch (event.getMaterial()) {
				case WOODEN_HOE:
					if (config.SURVIVAL_ENABLED) {
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
					if (config.LEGENDARY_GIANTBLADE) {
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
					if (config.SURVIVAL_ENABLED) {
						switch (event.getClickedBlock().getType()) {
							case GRASS:
								event.setCancelled(true);
								break;

							default:
						}
					}
					break;
				case GOLDEN_SHOVEL:
					if (config.LEGENDARY_OBSIDIAN_MACE) {
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
