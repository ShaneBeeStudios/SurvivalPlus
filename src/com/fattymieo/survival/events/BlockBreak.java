
package com.fattymieo.survival.events;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.fattymieo.survival.Survival;

public class BlockBreak implements Listener
{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		
		ItemStack tool = player.getItemInHand();
		
		Block block = event.getBlock();
		Material material = block.getType();
		
		if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
		{
			if(!(tool.getType() == Material.GOLD_PICKAXE))
			{
				if
				(
					Survival.settings.getBoolean("Survival.BreakOnlyWith.Shovel") &&
					!(
						   tool.getType() == Material.STONE_SPADE
						|| tool.getType() == Material.IRON_SPADE
						|| tool.getType() == Material.DIAMOND_SPADE
					)
				)
				{
					if
					(
						   material == Material.GRASS
						|| material == Material.DIRT
						|| material == Material.SOIL
						|| material == Material.SOUL_SAND
						|| material == Material.SAND
						|| material == Material.CLAY
						|| material == Material.MYCEL
						|| material == Material.SNOW
						|| material == Material.SNOW_BLOCK
						|| material == Material.GRASS_PATH
					)
					{
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Survival.Words.get("Must use a Shovel on this task"));
					}
		
					if(material == Material.GRAVEL)
					{
						event.setCancelled(true);
						event.getBlock().setType(Material.AIR);
						
						Random rand = new Random();
						int times_flint = 0;
						int chance_flint = rand.nextInt(4) - 1;
						
						switch(chance_flint)
						{
							case 1:
							case 2:
								times_flint = 1;
								break;
							default:
								times_flint = 0;
						}
						
						for(int i = 0; i < times_flint;i++)
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),new ItemStack(Material.FLINT));
					}
				}
				
				if
				(
					Survival.settings.getBoolean("Survival.BreakOnlyWith.Axe") &&
					!(
						   tool.getType() == Material.WOOD_AXE
						|| tool.getType() == Material.STONE_AXE
						|| tool.getType() == Material.IRON_AXE
						|| tool.getType() == Material.GOLD_AXE
						|| tool.getType() == Material.DIAMOND_AXE
					)
				)
				{
					if
					(
						   material == Material.WOODEN_DOOR
						|| material == Material.TRAP_DOOR
						|| material == Material.CHEST
						|| material == Material.TRAPPED_CHEST
						|| material == Material.WORKBENCH
						|| material == Material.FENCE
						|| material == Material.FENCE_GATE
						|| material == Material.WOOD
						|| material == Material.LOG
						|| material == Material.LOG_2
						|| material == Material.WOOD_STEP
						|| material == Material.WOOD_DOUBLE_STEP
						|| material == Material.WOOD_STAIRS
						|| material == Material.BOOKSHELF
						|| material == Material.LADDER
						|| material == Material.BIRCH_WOOD_STAIRS
						|| material == Material.JUNGLE_WOOD_STAIRS
						|| material == Material.SPRUCE_WOOD_STAIRS
						|| material == Material.DARK_OAK_STAIRS
						|| material == Material.ACACIA_STAIRS
						|| material == Material.WOOD_PLATE
						|| material == Material.SIGN
						|| material == Material.SIGN_POST
						|| material == Material.WALL_SIGN
						|| material == Material.BED_BLOCK
						|| material == Material.BIRCH_DOOR
						|| material == Material.JUNGLE_DOOR
						|| material == Material.SPRUCE_DOOR
						|| material == Material.DARK_OAK_DOOR
						|| material == Material.ACACIA_DOOR
						|| material == Material.BIRCH_FENCE
						|| material == Material.JUNGLE_FENCE
						|| material == Material.SPRUCE_FENCE
						|| material == Material.DARK_OAK_FENCE
						|| material == Material.ACACIA_FENCE
						|| material == Material.BIRCH_FENCE_GATE
						|| material == Material.JUNGLE_FENCE_GATE
						|| material == Material.SPRUCE_FENCE_GATE
						|| material == Material.DARK_OAK_FENCE_GATE
						|| material == Material.ACACIA_FENCE_GATE
						|| material == Material.BANNER
						|| material == Material.STANDING_BANNER
						|| material == Material.WALL_BANNER
						|| material == Material.JUKEBOX
						|| material == Material.NOTE_BLOCK
						|| material == Material.DAYLIGHT_DETECTOR
						|| material == Material.DAYLIGHT_DETECTOR_INVERTED
					)
					{
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Survival.Words.get("Must use an Axe on this task"));
					}
				}
				
				if
				(
					Survival.settings.getBoolean("Survival.BreakOnlyWith.Pickaxe") &&
					!(
						   tool.getType() == Material.WOOD_PICKAXE
						|| tool.getType() == Material.STONE_PICKAXE
						|| tool.getType() == Material.IRON_PICKAXE
						|| tool.getType() == Material.DIAMOND_PICKAXE
					)
				)
				{
					if
					(
						   material == Material.OBSIDIAN
						|| material == Material.COAL_BLOCK
						|| material == Material.DIAMOND_BLOCK
						|| material == Material.EMERALD_BLOCK
						|| material == Material.IRON_BLOCK
						|| material == Material.GOLD_BLOCK
						|| material == Material.LAPIS_BLOCK
						|| material == Material.REDSTONE_BLOCK
						|| material == Material.QUARTZ_BLOCK
						|| material == Material.IRON_BARDING
						|| material == Material.IRON_DOOR_BLOCK
						|| material == Material.MOB_SPAWNER
						|| material == Material.COAL_ORE
						|| material == Material.DIAMOND_ORE
						|| material == Material.EMERALD_ORE
						|| material == Material.GLOWING_REDSTONE_ORE
						|| material == Material.GOLD_ORE
						|| material == Material.IRON_ORE
						|| material == Material.LAPIS_ORE
						|| material == Material.QUARTZ_ORE
						|| material == Material.REDSTONE_ORE
						|| material == Material.IRON_TRAPDOOR
						|| material == Material.BRICK
						|| material == Material.BRICK_STAIRS
						|| material == Material.COBBLESTONE
						|| material == Material.COBBLESTONE_STAIRS
						|| material == Material.MOSSY_COBBLESTONE
						|| material == Material.COBBLE_WALL
						|| material == Material.NETHER_BRICK
						|| material == Material.NETHER_BRICK_STAIRS
						|| material == Material.NETHER_FENCE
						|| material == Material.NETHERRACK
						|| material == Material.STONE_SLAB2
						|| material == Material.DOUBLE_STONE_SLAB2
						|| material == Material.STONE
						|| material == Material.SMOOTH_BRICK
						|| material == Material.SMOOTH_STAIRS
						|| material == Material.PRISMARINE
						|| material == Material.HARD_CLAY
						|| material == Material.STAINED_CLAY
						|| material == Material.CLAY_BRICK
						|| material == Material.QUARTZ_STAIRS
						|| material == Material.SANDSTONE
						|| material == Material.SANDSTONE_STAIRS
						|| material == Material.RED_SANDSTONE
						|| material == Material.RED_SANDSTONE_STAIRS
						|| material == Material.ICE
						|| material == Material.PACKED_ICE
						|| material == Material.SEA_LANTERN
						|| material == Material.GLOWSTONE
						|| material == Material.STEP
						|| material == Material.DOUBLE_STEP
						|| material == Material.PURPUR_BLOCK
						|| material == Material.PURPUR_DOUBLE_SLAB
						|| material == Material.PURPUR_PILLAR
						|| material == Material.PURPUR_SLAB
						|| material == Material.PURPUR_STAIRS
						|| material == Material.END_ROD
						|| material == Material.FROSTED_ICE
						|| material == Material.DISPENSER
						|| material == Material.DROPPER
						|| material == Material.FURNACE
						|| material == Material.BURNING_FURNACE
						|| material == Material.ENCHANTMENT_TABLE
						|| material == Material.ANVIL
						|| material == Material.ENDER_CHEST
						|| material == Material.HOPPER
						|| material == Material.CAULDRON
						|| material == Material.BREWING_STAND
						|| material == Material.STONE_PLATE
						|| material == Material.GOLD_PLATE
						|| material == Material.IRON_PLATE
						|| material == Material.RAILS
						|| material == Material.ACTIVATOR_RAIL
						|| material == Material.DETECTOR_RAIL
						|| material == Material.POWERED_RAIL
						|| material == Material.BEACON
					)
					{
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Survival.Words.get("Must use a Pick on this task"));
					}
				}
				
				if
				(
					Survival.settings.getBoolean("Survival.BreakOnlyWith.Hoe") &&
					!(
						   tool.getType() == Material.STONE_HOE
						|| tool.getType() == Material.IRON_HOE
						|| tool.getType() == Material.DIAMOND_HOE
					)
				)
				{
					if
					(
						   material == Material.MELON_BLOCK
						|| material == Material.PUMPKIN
						|| material == Material.JACK_O_LANTERN
						|| material == Material.MELON_STEM
						|| material == Material.PUMPKIN_STEM
						|| material == Material.CHORUS_FLOWER
						|| material == Material.CARROT
						|| material == Material.POTATO
						|| material == Material.BEETROOT_BLOCK
						|| material == Material.CROPS
					)
					{
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Survival.Words.get("Must use a Hoe on this task"));
					}
				}
				
				if
				(
					!(
						   tool.getType() == Material.SHEARS
					)
				)
				{
					if(Survival.settings.getBoolean("Survival.BreakOnlyWith.Shears"))
					{
						if
						(
							   material == Material.WEB
							|| material == Material.TRIPWIRE
							|| material == Material.TNT
							|| material == Material.HUGE_MUSHROOM_1
							|| material == Material.HUGE_MUSHROOM_2
						)
						{
							event.setCancelled(true);
							player.updateInventory();
							player.sendMessage(ChatColor.RED + Survival.Words.get("Must use a Shear on this task"));
						}
					}

					//Sticks
					if
					(
						   material == Material.LEAVES
						|| material == Material.LEAVES_2
					)
					{
						Random rand = new Random();
						int times = 0;
						int chance = rand.nextInt(4) - 1;
						
						switch(chance)
						{
							case 1:
								times = 1;
								break;
							case 2:
								times = 2;
								break;
							default:
								times = 0;
						}
						
						for(int i = 0; i < times;i++)
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),new ItemStack(Material.STICK));
					}
				}
			}
			else
			{
				if
				(
					   material == Material.DIAMOND_BLOCK
					|| material == Material.EMERALD_BLOCK
					|| material == Material.GOLD_BLOCK
					|| material == Material.IRON_BLOCK
					|| material == Material.GOLD_ORE
					|| material == Material.DIAMOND_ORE
					|| material == Material.EMERALD_ORE
					|| material == Material.IRON_ORE
					|| material == Material.LAPIS_BLOCK
					|| material == Material.LAPIS_ORE
					|| material == Material.REDSTONE_ORE
				)
				{
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),new ItemStack(material));
				}
				
				//Special Case
				if(material == Material.GLOWING_REDSTONE_ORE)
				{
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),new ItemStack(Material.REDSTONE_ORE));
				}
			}
		}
	}
}
