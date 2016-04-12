package com.fattymieo.survival.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.fattymieo.survival.Survival;

import lib.ParticleEffect;

public class CompassWaypoint implements Listener
{	
	@EventHandler
	public void onItemClick(PlayerInteractEvent event)
	{
		if(event.hasItem())
		{
			Player player = event.getPlayer();
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			ItemStack offItem = player.getInventory().getItemInOffHand();
			if(player.isSneaking())
			{
				if(mainItem.getType() == Material.COMPASS || offItem.getType() == Material.COMPASS)
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
						loc.add(0.5, 0.5, 0.5);
						player.setCompassTarget(loc);
                        ParticleEffect.CLOUD.display(0.5f, 0.5f, 0.5f, 0, 25, loc, player);
						player.sendMessage(ChatColor.LIGHT_PURPLE + Survival.Words.get("Compass has pointed at") + " (" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ").");
					}

					if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
					{
						Location loc = player.getCompassTarget();
						player.sendMessage(ChatColor.LIGHT_PURPLE + Survival.Words.get("Waypoint's coordinate is") + " (" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ")");
					}
				}
			}
		}
	}
}