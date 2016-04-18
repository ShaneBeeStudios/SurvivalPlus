package com.fattymieo.survival.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

public class SnowballThrow implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onThrowingSnowball(ProjectileHitEvent e)
	{
		if(e.getEntity() instanceof Snowball)
		{
			Snowball snowball = (Snowball)e.getEntity();
			
			BlockIterator iterator = new BlockIterator(snowball.getWorld(), snowball.getLocation().toVector(), snowball.getVelocity().normalize(), 0.0D, 4);
			Block actual = null;
			while (iterator.hasNext())
			{
				actual = iterator.next();
				 
				if (actual.getTypeId() != 0)
					break;
			}
		    
			switch(actual.getType())
			{
				case SNOW:
			    	if (actual.getData() == 6)
			    		actual.setType(Material.SNOW_BLOCK);
			    	else
			    		actual.setData((byte)(actual.getData() + 1));
			    	break;
				case LONG_GRASS:
				case YELLOW_FLOWER:
				case RED_ROSE:
				case DEAD_BUSH:
				case DOUBLE_PLANT:
			 		actual.setType(Material.SNOW);
					break;
				default:
					if(actual.getType().isSolid())
					{
						Block aboveHit = actual.getRelative(BlockFace.UP);
						switch(aboveHit.getType())
						{
							case AIR:
								aboveHit.setType(Material.SNOW);
								break;
							default:
						}
					}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreakSnow(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
		{
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			switch(mainItem.getType())
			{
				case WOOD_SPADE:
				case GOLD_SPADE:
				case STONE_SPADE:
				case DIAMOND_SPADE:
				case IRON_SPADE:
					Block block = e.getBlock();
					switch(block.getType())
					{
						case SNOW:
							e.setCancelled(true);
							mainItem.setDurability((short) (mainItem.getDurability() + 1));
							if(mainItem.getDurability() >= mainItem.getType().getMaxDurability() + 1)
								player.getInventory().setItemInMainHand(null);
				            player.updateInventory();
				            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SNOW_BALL, block.getData() + 1));
				            block.setType(Material.AIR);
							break;
						case SNOW_BLOCK:
							e.setCancelled(true);
							mainItem.setDurability((short) (mainItem.getDurability() + 1));
							if(mainItem.getDurability() >= mainItem.getType().getMaxDurability() + 1)
								player.getInventory().setItemInMainHand(null);
				            player.updateInventory();
				            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SNOW_BALL, 8));
				            block.setType(Material.AIR);
				            break;
						default:
					}
					break;
				default:
			}
		}
	}
}
