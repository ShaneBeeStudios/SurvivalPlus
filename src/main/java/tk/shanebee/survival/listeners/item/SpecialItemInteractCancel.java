package tk.shanebee.survival.listeners.item;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;

public class SpecialItemInteractCancel implements Listener {

	private Config config;
	
	public SpecialItemInteractCancel(Survival plugin) {
		this.config = plugin.getSurvivalConfig();
	}

	@EventHandler
	private void onInteractBlock(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack tool = event.getItem();
            if (event.getClickedBlock() == null || tool == null) return;

            Material clickedBlock = event.getClickedBlock().getType();
			switch (event.getMaterial()) {
				case GOLDEN_HOE:
					if (config.LEGENDARY_GIANTBLADE) {
						switch (clickedBlock) {
							case DIRT:
							case GRASS:
								event.setCancelled(true);
								break;

							default:
						}
					}
					break;
				case GOLDEN_SHOVEL:
					if (config.LEGENDARY_OBSIDIAN_MACE) {
						switch (clickedBlock) {
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
