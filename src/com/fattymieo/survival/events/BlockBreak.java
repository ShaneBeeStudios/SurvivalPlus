
package com.fattymieo.survival.events;

import java.util.Random;

import com.fattymieo.survival.util.Utils;
import com.lmax.disruptor.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.fattymieo.survival.Survival;

public class BlockBreak implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();

		ItemStack tool = player.getInventory().getItemInMainHand();

		Block block = event.getBlock();
		Material material = block.getType();

		if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
			if (!(tool.getType() == Material.GOLDEN_PICKAXE)) {
				if (Survival.settings.getBoolean("Survival.BreakOnlyWith.Shovel") &&
						!(tool.getType() == Material.STONE_SHOVEL || tool.getType() == Material.IRON_SHOVEL
								|| tool.getType() == Material.DIAMOND_SHOVEL)) {
					if (material == Material.GRASS
							|| material == Material.DIRT
							|| material == Material.FARMLAND
							|| material == Material.SOUL_SAND
							|| material == Material.SAND
							|| material == Material.CLAY
							|| material == Material.MYCELIUM
							|| material == Material.SNOW
							|| material == Material.SNOW_BLOCK
							|| material == Material.GRASS_PATH
					) {
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Survival.Words.get("Must use a Shovel on this task"));
					}

					//Flint
					if (material == Material.GRAVEL) {
						event.setCancelled(true);
						event.getBlock().setType(Material.AIR);

						Random rand = new Random();
						double chance = rand.nextDouble();

						if (chance <= Survival.settings.getDouble("Survival.DropRate.Flint"))
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.FLINT));
					}
				}

				if
				(Survival.settings.getBoolean("Survival.BreakOnlyWith.Axe") &&
						!(tool.getType() == Material.WOODEN_AXE || tool.getType() == Material.STONE_AXE
								|| tool.getType() == Material.IRON_AXE || tool.getType() == Material.GOLDEN_AXE
								|| tool.getType() == Material.DIAMOND_AXE)) {
					if (Tag.DOORS.isTagged(material)
							|| material == Material.CHEST
							|| material == Material.TRAPPED_CHEST
							|| material == Material.CRAFTING_TABLE

							|| Tag.PLANKS.isTagged(material)
							|| Tag.LOGS.isTagged(material)
							|| Tag.STAIRS.isTagged(material)
							|| Tag.SLABS.isTagged(material)
							|| material == Material.BOOKSHELF
							|| material == Material.LADDER
							|| material == Material.DARK_OAK_STAIRS
							|| material == Material.ACACIA_STAIRS
							|| Tag.WOODEN_PRESSURE_PLATES.isTagged(material)
							|| material == Material.SIGN
							|| material == Material.WALL_SIGN
							|| material == Material.OAK_FENCE
							|| material == Material.BIRCH_FENCE
							|| material == Material.JUNGLE_FENCE
							|| material == Material.SPRUCE_FENCE
							|| material == Material.DARK_OAK_FENCE
							|| material == Material.ACACIA_FENCE
							|| material == Material.OAK_FENCE_GATE
							|| material == Material.BIRCH_FENCE_GATE
							|| material == Material.JUNGLE_FENCE_GATE
							|| material == Material.SPRUCE_FENCE_GATE
							|| material == Material.DARK_OAK_FENCE_GATE
							|| material == Material.ACACIA_FENCE_GATE
							|| Tag.BANNERS.isTagged(material)
							|| material == Material.JUKEBOX
							|| material == Material.NOTE_BLOCK
							|| material == Material.DAYLIGHT_DETECTOR) {
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Survival.Words.get("Must use an Axe on this task"));
					}

					//Fix half door glitch
					if
					(
							Tag.DOORS.isTagged(material)
					) {
						if (block.getRelative(BlockFace.UP).getType() == material)
							block.getRelative(BlockFace.UP).getState().update(true);
						if (block.getRelative(BlockFace.DOWN).getType() == material)
							block.getRelative(BlockFace.DOWN).getState().update(true);
					}
				}
				if (Survival.settings.getBoolean("Survival.BreakOnlyWith.Pickaxe") &&
						!(tool.getType() == Material.WOODEN_PICKAXE
								|| tool.getType() == Material.STONE_PICKAXE
								|| tool.getType() == Material.IRON_PICKAXE
								|| tool.getType() == Material.DIAMOND_PICKAXE)) {
					if (material == Material.OBSIDIAN
							|| Utils.isOreBlock(material)
							|| Utils.isNaturalOreBlock(material)

							|| Utils.isDoorNotWood(material)

							|| Utils.isSlabNotWood(material)
							|| Utils.isStairsNotWood(material)

							|| Utils.isStoneTypeBlock(material)

							|| material == Material.SPAWNER

							|| material == Material.COBBLESTONE_WALL
							|| material == Material.NETHER_BRICK_FENCE

							|| material == Material.CLAY
							|| material == Material.ICE
							|| material == Material.PACKED_ICE
							|| material == Material.SEA_LANTERN
							|| material == Material.GLOWSTONE
							|| material == Material.PURPUR_BLOCK
							|| material == Material.PURPUR_PILLAR
							|| material == Material.END_ROD
							|| material == Material.FROSTED_ICE
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
							|| material == Material.HEAVY_WEIGHTED_PRESSURE_PLATE
							|| material == Material.LIGHT_WEIGHTED_PRESSURE_PLATE

							|| material == Material.BEACON

							|| Tag.RAILS.isTagged(material)
							|| Utils.isTerracotta(material)
							|| Utils.isGlazedTerracotta(material)
							|| Utils.isConcrete(material)) {
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Survival.Words.get("Must use a Pick on this task"));
					}
				}

				if (Survival.settings.getBoolean("Survival.BreakOnlyWith.Hoe") &&
						!(tool.getType() == Material.STONE_HOE
								|| tool.getType() == Material.IRON_HOE
								|| tool.getType() == Material.DIAMOND_HOE)) {
					if (material == Material.MELON
							|| material == Material.PUMPKIN
							|| material == Material.JACK_O_LANTERN
							|| material == Material.MELON_STEM
							|| material == Material.PUMPKIN_STEM
							|| material == Material.CHORUS_FLOWER
							|| material == Material.CARROT
							|| material == Material.POTATO
							|| material == Material.BEETROOT
							|| material == Material.WHEAT) {
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Survival.Words.get("Must use a Hoe on this task"));
					}
				}

				if (!(tool.getType() == Material.SHEARS)) {
					if (Survival.settings.getBoolean("Survival.BreakOnlyWith.Shears")) {
						if (material == Material.COBWEB
								|| material == Material.TRIPWIRE
								|| material == Material.TNT
								|| material == Material.MUSHROOM_STEM) {
							event.setCancelled(true);
							player.updateInventory();
							player.sendMessage(ChatColor.RED + Survival.Words.get("Must use a Shear on this task"));
						}
					}

					//Sticks
					if (Tag.LEAVES.isTagged(material)) {
						Random rand = new Random();
						double chance = rand.nextDouble();

						if (chance <= Survival.settings.getDouble("Survival.DropRate.Stick"))
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.STICK));
					}
				}
			} else {
				if (Utils.isOreBlock(material) || Utils.isNaturalOreBlock(material)) {
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(material));
				}

				//Special Case
				if (material == Material.REDSTONE_ORE) {
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.REDSTONE_ORE));
				}
			}
		}
	}

}
