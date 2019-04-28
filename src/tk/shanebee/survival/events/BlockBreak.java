
package tk.shanebee.survival.events;

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
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

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
					if (material == Material.GRASS_BLOCK
							|| material == Material.DIRT
							|| material == Material.PODZOL
							|| material == Material.COARSE_DIRT
							|| material == Material.GRASS_PATH
							|| material == Material.FARMLAND
							|| material == Material.SOUL_SAND
							|| material == Material.SAND
							|| material == Material.RED_SAND
							|| material == Material.CLAY
							|| material == Material.MYCELIUM
							|| material == Material.SNOW
							|| material == Material.SNOW_BLOCK
							|| Utils.isConcretePowder(material)

					) {
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.task_must_use_shovel));
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
							|| Tag.WOODEN_PRESSURE_PLATES.isTagged(material)
							|| Tag.WOODEN_FENCES.isTagged(material)
							|| Utils.isWoodGate(material)
							|| Tag.BANNERS.isTagged(material)
							|| material == Material.JUKEBOX
							|| material == Material.NOTE_BLOCK
							|| material == Material.DAYLIGHT_DETECTOR
							|| Tag.SIGNS.isTagged(material)) {
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.task_must_use_axe));
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
								|| tool.getType() == Material.GOLDEN_PICKAXE
								|| tool.getType() == Material.STONE_PICKAXE
								|| tool.getType() == Material.IRON_PICKAXE
								|| tool.getType() == Material.DIAMOND_PICKAXE)) {
					if (material == Material.OBSIDIAN
							|| Utils.isOreBlock(material)
							|| Utils.isNaturalOreBlock(material)
							|| Utils.isDoorNotWood(material)
							|| Utils.isSlabNotWood(material)
							|| Utils.isStairsNotWood(material)

							|| Utils.isTerracotta(material)
							|| Utils.isGlazedTerracotta(material)
							|| Utils.isConcrete(material)

							|| Utils.isStoneTypeBlock(material)
							|| Utils.isCookingBlock(material)


							|| Tag.WALLS.isTagged(material)
							|| Tag.ICE.isTagged(material)
							|| Tag.CORAL_BLOCKS.isTagged(material)

							|| material == Material.NETHER_BRICK_FENCE
							|| material == Material.SPAWNER

							|| material == Material.SEA_LANTERN
							|| material == Material.GLOWSTONE
							|| material == Material.END_ROD
							|| material == Material.DISPENSER
							|| material == Material.DROPPER
							|| material == Material.OBSERVER
							|| material == Material.PISTON
							|| material == Material.PISTON_HEAD
							|| material == Material.STICKY_PISTON
							|| material == Material.MOVING_PISTON
							|| material == Material.DAYLIGHT_DETECTOR
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

							|| Tag.RAILS.isTagged(material)) {
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.task_must_use_pick));
					}
				}

				// TODO change this up for sickle (coming soon)
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
						player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.task_must_use_hoe));
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
							player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.task_must_use_shear));
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
			}
		}
	}

}
