package com.fattymieo.survival.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import com.fattymieo.survival.Survival;

import lib.ParticleEffect;

public class LivingSlime implements Listener
{
	@EventHandler
	public void onGhastTearSlimeBlock(ItemSpawnEvent e)
	{
		if(e.getEntityType() == EntityType.DROPPED_ITEM)
		{
			Item i = (Item)e.getEntity();
			switch(i.getItemStack().getType())
			{
				case GHAST_TEAR:
					Bukkit.getScheduler().runTaskLater(Survival.instance, initRunnable(i), 20);
					break;
				default:
			}
		}
	}
	
	private Runnable initRunnable(Item i)
	{
		final Item f_i = i;
		Runnable run = new Runnable()
		{
			public void run()
			{
				List<Block> slimeBlocks = new ArrayList<Block>();
				slimeBlocks.add(f_i.getLocation().add(0, -1, 0).getBlock());
				slimeBlocks.add(f_i.getLocation().add(0, -1, 1).getBlock());
				slimeBlocks.add(f_i.getLocation().add(0, -1, -1).getBlock());
				slimeBlocks.add(f_i.getLocation().add(1, -1, 0).getBlock());
				slimeBlocks.add(f_i.getLocation().add(-1, -1, 0).getBlock());
				slimeBlocks.add(f_i.getLocation().add(0, 0, 1).getBlock());
				slimeBlocks.add(f_i.getLocation().add(0, 0, -1).getBlock());
				slimeBlocks.add(f_i.getLocation().add(1, 0, 0).getBlock());
				slimeBlocks.add(f_i.getLocation().add(-1, 0, 0).getBlock());
				slimeBlocks.add(f_i.getLocation().add(1, 0, 1).getBlock());
				slimeBlocks.add(f_i.getLocation().add(1, 0, -1).getBlock());
				slimeBlocks.add(f_i.getLocation().add(-1, 0, 1).getBlock());
				slimeBlocks.add(f_i.getLocation().add(-1, 0, -1).getBlock());
				
				ItemStack i_f_i = ((Item)f_i).getItemStack();
				Iterator<Block> it = slimeBlocks.iterator();
				Block slimeBlock = null;
				while(it.hasNext())
				{
					slimeBlock = it.next();
					if(slimeBlock != null && slimeBlock.getType() == Material.SLIME_BLOCK && f_i.isOnGround())
					{						
						if(i_f_i.getAmount() > 1)
							i_f_i.setAmount(i_f_i.getAmount() - 1);
						else
							f_i.remove();
						
						if(i_f_i.getAmount() <= 0)
							f_i.remove();
						
						slimeBlock.setType(Material.AIR);
						
						Slime slime = (Slime)f_i.getWorld().spawnEntity(slimeBlock.getLocation(), EntityType.SLIME);
						slime.setSize(2);
						
	                    ParticleEffect.CLOUD.display(0.5f, 0.5f, 0.5f, 0.1f, 20, slimeBlock.getLocation().add(0.5, 0.5, 0.5), 64);
	                    break;
					}
				}
			}
		};
		return run;
	}
}
