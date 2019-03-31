package com.fattymieo.survival.events;

import com.fattymieo.survival.Survival;
import com.fattymieo.survival.util.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

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
					if (offTool.getType() == Material.WOODEN_SWORD) {
						Random rand = new Random();
						int chance_reduceDur = rand.nextInt(10) + 1;
						if (chance_reduceDur == 1) {
							Utils.setDurability(offTool, Utils.getDurability(offTool) + 1);
						}

						if (Utils.getDurability(offTool) >= 59) {
							player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							player.getInventory().setItemInOffHand(null);
						}
					} else if (mainTool.getType() == Material.WOODEN_SWORD) {
						Random rand = new Random();
						int chance_reduceDur = rand.nextInt(10) + 1;
						if (chance_reduceDur == 1) {
							Utils.setDurability(mainTool, ((Damageable) mainTool.getItemMeta()).getDamage() + 1);
						}

						if (Utils.getDurability(mainTool) >= 59) {
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

				|| material == Material.CHEST
				|| material == Material.TRAPPED_CHEST


				|| Utils.isWoodFence(material)
				|| Utils.isWoodGate(material)
				|| Utils.isBed(material)
				|| Utils.isTerracotta(material)
				|| Utils.isGlazedTerracotta(material)
				|| Utils.isWall(material)
				|| Utils.isConcrete(material)
				|| Utils.isConcretePowder(material)

				|| Tag.LOGS.isTagged(material)
				|| Tag.STAIRS.isTagged(material)
				|| Tag.SLABS.isTagged(material)
				|| Tag.PLANKS.isTagged(material)
				|| Tag.WOODEN_PRESSURE_PLATES.isTagged(material)
				|| Tag.RAILS.isTagged(material)

				|| material == Material.JUKEBOX
				|| material == Material.BOOKSHELF
				|| material == Material.NOTE_BLOCK
				|| material == Material.LADDER
				|| material == Material.SIGN
				|| material == Material.WALL_SIGN
				|| Tag.BANNERS.isTagged(material)
				|| material == Material.IRON_TRAPDOOR
				|| material == Material.BRICK
				|| material == Material.NETHER_BRICK
				|| material == Material.NETHER_BRICK_FENCE
				|| material == Material.STONE_BRICKS
				|| material == Material.PRISMARINE
				|| material == Material.SANDSTONE
				|| material == Material.RED_SANDSTONE
				|| material == Material.SEA_LANTERN
				|| material == Material.GLOWSTONE
				|| material == Material.PURPUR_BLOCK
				|| material == Material.PURPUR_PILLAR
				|| material == Material.END_ROD
				|| material == Material.DISPENSER
				|| material == Material.DROPPER
				|| material == Material.FURNACE
				|| material == Material.ENCHANTING_TABLE
				|| material == Material.ANVIL
				|| material == Material.ENDER_CHEST
				|| material == Material.HOPPER
				|| material == Material.CAULDRON
				|| material == Material.BREWING_STAND
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