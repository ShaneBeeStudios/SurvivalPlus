package tk.shanebee.survival.events;

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
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

public class BlockPlace implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();

		ItemStack mainTool = player.getInventory().getItemInMainHand();
		ItemStack offTool = player.getInventory().getItemInOffHand();

		Block block = event.getBlock();

		if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
			if (Survival.settings.getBoolean("Survival.PlaceOnlyWith.Hammer")) {
				if (checkArtifact(block)) {
					if (Items.compare(offTool, Items.HAMMER)) {
						Random rand = new Random();
						int chance_reduceDur = rand.nextInt(10) + 1;
						if (chance_reduceDur == 1) {
							Utils.setDurability(offTool, Utils.getDurability(offTool) + 1);
						}

						if (Utils.getDurability(offTool) >= offTool.getType().getMaxDurability()) {
							player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							player.getInventory().setItemInOffHand(null);
						}
					} else if (Items.compare(mainTool, Items.HAMMER)) {
						Random rand = new Random();
						int chance_reduceDur = rand.nextInt(10) + 1;
						if (chance_reduceDur == 1) {
							Utils.setDurability(mainTool, ((Damageable) mainTool.getItemMeta()).getDamage() + 1);
						}

						if (Utils.getDurability(mainTool) >= mainTool.getType().getMaxDurability()) {
							player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							player.getInventory().setItemInOffHand(null);
						}
					} else {
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.task_must_use_hammer));
					}
				}
			}
		}
	}

	private boolean checkArtifact(Block block) {
		Material material = block.getType();
		return Tag.DOORS.isTagged(material)

				|| Utils.isWoodGate(material)
				|| Utils.isTerracotta(material)
				|| Utils.isGlazedTerracotta(material)
				|| Utils.isConcrete(material)
				|| Utils.isConcretePowder(material)
				|| Utils.isStoneTypeBlock(material)
				|| Utils.isCookingBlock(material)
				|| Utils.isStorageBlock(material)
				|| Utils.isUtilityBlock(material)
				|| Utils.isShulkerBox(material)
				|| Utils.isOreBlock(material)

				|| Tag.BEDS.isTagged(material)
				|| Tag.LOGS.isTagged(material)
				|| Tag.STAIRS.isTagged(material)
				|| Tag.SLABS.isTagged(material)
				|| Tag.PLANKS.isTagged(material)
				|| Tag.WOODEN_PRESSURE_PLATES.isTagged(material)
				|| Tag.WOODEN_FENCES.isTagged(material)
				|| Tag.RAILS.isTagged(material)
				|| Tag.SIGNS.isTagged(material)
				|| Tag.BANNERS.isTagged(material)
				|| Tag.FENCES.isTagged(material)
				|| Tag.FENCES.isTagged(material)
				|| Tag.SIGNS.isTagged(material)

				|| material == Material.BOOKSHELF
				|| material == Material.LADDER
				|| material == Material.SEA_LANTERN
				|| material == Material.GLOWSTONE
				|| material == Material.END_ROD
				|| material == Material.DISPENSER
				|| material == Material.DROPPER
				|| material == Material.HOPPER
				|| material == Material.STONE_PRESSURE_PLATE
				|| material == Material.LIGHT_WEIGHTED_PRESSURE_PLATE
				|| material == Material.HEAVY_WEIGHTED_PRESSURE_PLATE
				|| material == Material.DAYLIGHT_DETECTOR
				|| material == Material.PISTON
				|| material == Material.STICKY_PISTON
				|| material == Material.REDSTONE_LAMP
				|| material == Material.REPEATER
				|| material == Material.COMPARATOR
				|| material == Material.TRIPWIRE_HOOK
				|| material == Material.BEACON
				|| material == Material.IRON_BARS;
	}

}