package tk.shanebee.survival.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

class BlockPlace implements Listener {

	private Config config;
	private Lang lang;

	BlockPlace(Survival plugin) {
		this.config = plugin.getSurvivalConfig();
		this.lang = plugin.getLang();
	}

	@SuppressWarnings("ConstantConditions")
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onBlockPlace(BlockPlaceEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();

		ItemStack mainTool = player.getInventory().getItemInMainHand();
		ItemStack offTool = player.getInventory().getItemInOffHand();

		Block block = event.getBlock();

		if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
			if (config.PLACE_ONLY_WITH_HAMMER) {
				if (Utils.requiresHammer(block.getType())) {
					if (ItemManager.compare(offTool, Items.HAMMER)) {
						Random rand = new Random();
						int chance_reduceDur = rand.nextInt(10) + 1;
						if (chance_reduceDur == 1) {
							Utils.setDurability(offTool, Utils.getDurability(offTool) + 1);
						}

						if (Utils.getDurability(offTool) >= offTool.getType().getMaxDurability()) {
							player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							player.getInventory().setItemInOffHand(null);
						}
					} else if (ItemManager.compare(mainTool, Items.HAMMER)) {
						Random rand = new Random();
						int chance_reduceDur = rand.nextInt(10) + 1;
						if (chance_reduceDur == 1) {
							Utils.setDurability(mainTool, ((Damageable) mainTool.getItemMeta()).getDamage() + 1);
						}

						if (Utils.getDurability(mainTool) >= mainTool.getType().getMaxDurability()) {
							player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							player.getInventory().setItemInMainHand(null);
						}
					} else {
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Utils.getColoredString(lang.task_must_use_hammer));
					}
				}
			}
		}
	}

}