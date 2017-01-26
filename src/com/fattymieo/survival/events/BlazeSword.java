package com.fattymieo.survival.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlazeSword implements Listener
{	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemClick(PlayerInteractEvent event)
	{
		if(event.isCancelled()) return;
		if(event.hasItem())
		{
			Player player = event.getPlayer();
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			if(mainItem.getType() == Material.GOLD_SWORD)
			{
				if(player.isSneaking())
				{
					if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
					{
						if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
						{
							switch(event.getClickedBlock().getType())
							{
								case ENCHANTMENT_TABLE:
								case ANVIL:
								case BREWING_STAND:
								case SPRUCE_DOOR:
								case BIRCH_DOOR:
								case WOOD_DOOR:
								case JUNGLE_DOOR:
								case ACACIA_DOOR:
								case DARK_OAK_DOOR:
								case IRON_DOOR:
								case TRAPPED_CHEST:
								case CHEST:
								case BED:
								case NOTE_BLOCK:
								case FENCE_GATE:
								case SPRUCE_FENCE_GATE:
								case BIRCH_FENCE_GATE:
								case JUNGLE_FENCE_GATE:
								case DARK_OAK_FENCE_GATE:
								case ACACIA_FENCE_GATE:
								case TRAP_DOOR:
								case IRON_TRAPDOOR:
								case FURNACE:
								case BURNING_FURNACE:
								case HOPPER:
								case WORKBENCH:
								case DROPPER:
								case DISPENSER:
									return;
								default:
							}
							Location loc = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
							ignite(player, loc, IgniteCause.FLINT_AND_STEEL);
						}
						
						if(event.getAction() == Action.RIGHT_CLICK_AIR)
						{
							Location loc = player.getLocation();
							loc.add(-0.5, -0.5, -0.5);
							ignite(player, loc, IgniteCause.FLINT_AND_STEEL);
						}
						
						mainItem.setDurability((short)(mainItem.getDurability() + 1));
						if(mainItem.getDurability() >= 32)
						{
							Random rand = new Random();
							player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							player.getInventory().setItemInMainHand(null);
						}
						player.updateInventory();
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean ignite(Player igniter, Location loc, IgniteCause cause) {
	    Random rand = new Random();

	    loc.add(0.5, 0.5, 0.5);
	    
	    BlockIgniteEvent igniteEvent = new BlockIgniteEvent(loc.getBlock(), 
	            cause, (org.bukkit.entity.Entity) igniter);
	    Bukkit.getServer().getPluginManager().callEvent(igniteEvent);
	    if (igniteEvent.isCancelled()) {
	        return false;
	    }
	    
	    List<Location> locations = new ArrayList<Location>();
	    
	    for(double x = loc.getX() - 2;x <= loc.getX() + 2;x++)
	    {
	    	for(double y = loc.getY() - 1;y <= loc.getY() + 1;y++)
	    	{
	    		for(double z = loc.getZ() - 2;z <= loc.getZ() + 2;z++)
	    		{
	    			locations.add(new Location(loc.getWorld(),x,y,z));
	    		}
	    	}
	    }
	    
	    for(Location l : locations)
	    {
		    BlockIgniteEvent igniteEvent2 = new BlockIgniteEvent(l.getBlock(), 
		            cause, (org.bukkit.entity.Entity) igniter);
		    Bukkit.getServer().getPluginManager().callEvent(igniteEvent2);
		    if (igniteEvent2.isCancelled()) {
		        continue;
		    }
		    
		    BlockState blockState = l.getBlock().getState();
		    
		    BlockPlaceEvent placeEvent = new BlockPlaceEvent(l.getBlock(), 
		            blockState, l.getBlock(), igniter.getItemInHand(), igniter, true);
		    Bukkit.getServer().getPluginManager().callEvent(placeEvent);
		    
		    if (placeEvent.isCancelled() || !placeEvent.canBuild()) {
		        continue;
		    }
	    	
	    	if(l.getBlock() == null || l.getBlock().getType() == Material.AIR)
	    		l.getBlock().setType(Material.FIRE);
	    }

    	loc.getWorld().playSound(loc, Sound.ITEM_FIRECHARGE_USE, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
	    
	    return true;
	}
}