package com.fattymieo.survival.events;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.fattymieo.survival.Survival;

public class BlockPlace implements Listener
{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();

		ItemStack mainTool = player.getInventory().getItemInMainHand();
		ItemStack offTool = player.getInventory().getItemInOffHand();
		
		Block block = event.getBlock();
		
		if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
		{
			if(Survival.settings.getBoolean("Survival.PlaceOnlyWith.Hammer"))
			{
				if(checkArtifact(block))
				{
					if(offTool.getType() == Material.WOOD_SWORD)
					{
						Random rand = new Random();
						int chance_reduceDur = rand.nextInt(10) + 1;
						switch(chance_reduceDur)
						{
							case 1:
								offTool.setDurability((short)(offTool.getDurability() + 1));
								break;
							default:
						}
					
						if(offTool.getDurability() >= 59)
						{
							player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							player.getInventory().setItemInOffHand(null);
						}
					}
					else if(mainTool.getType() == Material.WOOD_SWORD)
					{
						Random rand = new Random();
						int chance_reduceDur = rand.nextInt(10) + 1;
						switch(chance_reduceDur)
						{
							case 1:
								mainTool.setDurability((short)(mainTool.getDurability() + 1));
								break;
							default:
						}
					
						if(mainTool.getDurability() >= 59)
						{
							player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							player.getInventory().setItemInOffHand(null);
						}
					}
					else
					{
						event.setCancelled(true);
						player.updateInventory();
						player.sendMessage(ChatColor.RED + Survival.Words.get("Must use a Hammer on this task"));
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private boolean checkArtifact(Block block)
	{
		Material material = block.getType();
		if
		(
			   material == Material.WOODEN_DOOR
			|| material == Material.TRAP_DOOR
			|| material == Material.CHEST
			|| material == Material.TRAPPED_CHEST
			|| material == Material.WORKBENCH
			|| material == Material.FENCE
			|| material == Material.FENCE_GATE
			|| material == Material.JUKEBOX
			|| material == Material.WOOD
			|| material == Material.WOOD_STEP
			|| material == Material.WOOD_DOUBLE_STEP
			|| material == Material.WOOD_STAIRS
			|| material == Material.BOOKSHELF
			|| material == Material.NOTE_BLOCK
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
			|| material == Material.IRON_DOOR_BLOCK
			|| material == Material.IRON_TRAPDOOR
			|| material == Material.BRICK
			|| material == Material.BRICK_STAIRS
			|| material == Material.COBBLESTONE_STAIRS
			|| material == Material.COBBLE_WALL
			|| material == Material.NETHER_BRICK
			|| material == Material.NETHER_BRICK_STAIRS
			|| material == Material.NETHER_FENCE
			|| material == Material.STONE_SLAB2
			|| material == Material.DOUBLE_STONE_SLAB2
			|| material == Material.SMOOTH_BRICK
			|| material == Material.SMOOTH_STAIRS
			|| material == Material.PRISMARINE
			|| material == Material.CLAY_BRICK
			|| material == Material.QUARTZ_STAIRS
			|| (material == Material.SANDSTONE && block.getData() != (byte) 0)
			|| material == Material.SANDSTONE_STAIRS
			|| (material == Material.RED_SANDSTONE && block.getData() != (byte) 0)
			|| material == Material.RED_SANDSTONE_STAIRS
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
			|| material == Material.DISPENSER
			|| material == Material.DROPPER
			|| material == Material.FURNACE
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
			|| material == Material.DAYLIGHT_DETECTOR
			|| material == Material.PISTON_BASE
			|| material == Material.PISTON_STICKY_BASE
			|| material == Material.REDSTONE_LAMP_ON
			|| material == Material.REDSTONE_LAMP_OFF
			|| material == Material.REDSTONE_COMPARATOR
			|| material == Material.REDSTONE_COMPARATOR_OFF
			|| material == Material.REDSTONE_COMPARATOR_ON
			|| material == Material.DIODE
			|| material == Material.DIODE_BLOCK_OFF
			|| material == Material.DIODE_BLOCK_ON
			|| material == Material.TRIPWIRE_HOOK
		)
			return true;
		else
			return false;
	}
}
