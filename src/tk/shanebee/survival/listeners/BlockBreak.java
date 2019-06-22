
package tk.shanebee.survival.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

class BlockBreak implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onBlockBreak(BlockBreakEvent event) {
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
							|| Tag.WOODEN_STAIRS.isTagged(material)
							|| Tag.WOODEN_SLABS.isTagged(material)
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
							|| Utils.isNonWoodDoor(material)
							|| Utils.isNonWoodSlab(material)
							|| Utils.isNonWoodStairs(material)

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

				if (Survival.settings.getBoolean("Survival.BreakOnlyWith.Sickle")) {
					if (material == Material.MELON
							|| material == Material.PUMPKIN || material == Material.JACK_O_LANTERN
							|| material == Material.MELON_STEM || material == Material.PUMPKIN_STEM
							|| material == Material.CHORUS_FLOWER || material == Material.CARROTS
							|| material == Material.POTATOES || material == Material.BEETROOTS
							|| material == Material.WHEAT || material == Material.SWEET_BERRY_BUSH) {

						if (!Items.Tags.SICKLES.isTagged(tool)) {
							event.setCancelled(true);
							player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.task_must_use_sickle));
						} else {
							event.setDropItems(false);
							Location loc = event.getBlock().getLocation();
							int random = 1;
							int multiplier = 1;
							boolean grown = true;
							Material drop = Utils.getDrops(material, grown);

							if (event.getBlock().getBlockData() instanceof Ageable) {
								Ageable crop = ((Ageable) event.getBlock().getBlockData());
								grown = crop.getAge() == crop.getMaximumAge();
							}

							// Flint/Stone sickles drop a chance of 0-1 items (not grown) or 1-2 (grown)
							if (Items.compare(tool, Items.FLINT_SICKLE)) {
								multiplier = 4;
								random = grown ? new Random().nextInt(2) + 1 : new Random().nextInt(2);
							}
							if (Items.compare(tool, Items.STONE_SICKLE)) {
								multiplier = 2;
								random = grown ? new Random().nextInt(2) + 1 : new Random().nextInt(2);
							}
							// Iron/Diamond sickles drop a chance of 1 (not grown) or 2-4 items (grown)
							if (Items.compare(tool, Items.IRON_SICKLE, Items.DIAMOND_SICKLE)) {
								random = grown ? new Random().nextInt(2) + 2 : 1;
							}

							if (drop != Material.AIR && random != 0) {
								assert loc.getWorld() != null;
								loc.getWorld().dropItemNaturally(loc, new ItemStack(drop, random));
							}
							if (tool.getType().getMaxDurability() < Utils.getDurability(tool) + multiplier) {
								player.getInventory().setItemInMainHand(null);
								player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
								return;
							}
							Utils.setDurability(tool, Utils.getDurability(tool) + multiplier);
							player.updateInventory();
						}
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

	@EventHandler
	private void onHarvest(PlayerInteractEvent e) {
		if (!Survival.settings.getBoolean("Survival.BreakOnlyWith.Sickle")) return;
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR
				|| e.getAction() == Action.LEFT_CLICK_BLOCK) return;
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		ItemStack tool = player.getInventory().getItemInMainHand();
		assert block != null;
		if (block.getType() == Material.SWEET_BERRY_BUSH) {
			Ageable bush = ((Ageable) block.getBlockData());
			if (e.getItem() != null && e.getItem().getType() == Material.BONE_MEAL) {
				if (bush.getAge() == 3) {
					e.setCancelled(true);
					return;
				} else return;
			}
			if (!Items.Tags.SICKLES.isTagged(tool)) {
				e.setCancelled(true);
				player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.task_must_use_sickle));
			} else {
				if (bush.getAge() >= 2) {
					int berries = 0;
					Location loc = block.getLocation();
					assert loc.getWorld() != null;
					int multiplier = 1;
					e.setCancelled(true);
					int random = new Random().nextInt(5) + 1;

					if (Items.compare(tool, Items.FLINT_SICKLE)) {
						if (bush.getAge() == 3) {
							berries = 1;
						}
						multiplier = 4;
					} else if (Items.compare(tool, Items.STONE_SICKLE)) {
						if (bush.getAge() == 2) {
							if (random <= 4)
								berries = 1;
						} else if (bush.getAge() == 3) {
							if (random <= 3)
								berries = 1;
							else
								berries = 2;
						}
						multiplier = 2;
					} else if (Items.compare(tool, Items.IRON_SICKLE, Items.DIAMOND_SICKLE)) {
						if (bush.getAge() == 2) {
							if (random <= 3)
								berries = 1;
							else
								berries = 2;
						} else if (bush.getAge() == 3) {
							if (random <= 4)
								berries = 2;
							else
								berries = 4;
						}
					}
					if (berries != 0)
						loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.SWEET_BERRIES, berries));

					bush.setAge(1);
					block.setBlockData(bush);
					Utils.setDurability(tool, Utils.getDurability(tool) + multiplier);
					player.playSound(loc, Sound.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, 1, 1);
				}
			}
		}
	}

}
