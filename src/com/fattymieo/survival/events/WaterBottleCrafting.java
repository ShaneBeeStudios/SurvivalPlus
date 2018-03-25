package com.fattymieo.survival.events;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import com.fattymieo.survival.Survival;

import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class WaterBottleCrafting implements Listener
{
	@EventHandler
	public void onCraft(CraftItemEvent e)
	{
		final Player player = (Player) e.getWhoClicked();
		final CraftingInventory inv = e.getInventory();
		
		ItemStack[] bottles = inv.getMatrix();
		ItemStack result = inv.getResult();
		
		if(result != null && result.getType() != Material.GLASS_BOTTLE)
		{
			for(int i = 0; i < bottles.length; i++)
			{
				if(bottles[i] == null) continue;
				if(bottles[i].getType() == Material.POTION)
				{
					if(checkWaterBottle(bottles[i]))
					{
						final int slot = i + 1;
						Bukkit.getServer().getScheduler().runTaskLater(Survival.instance, new Runnable()
						{
							public void run()
							{
								inv.setItem(slot, new ItemStack(Material.GLASS_BOTTLE));
								player.updateInventory();
							}
						}, 1);
					}
				}
			}
		}
		
		if(result != null && result.getType() != Material.BOWL)
		{
			for(int i = 0; i < bottles.length; i++)
			{
				if(bottles[i] == null) continue;
				if(bottles[i].getType() == Material.BEETROOT_SOUP)
				{
					final int slot = i + 1;
					Bukkit.getServer().getScheduler().runTaskLater(Survival.instance, new Runnable()
					{
						public void run()
						{
							inv.setItem(slot, new ItemStack(Material.BOWL));
							player.updateInventory();
						}
					}, 1);
				}
			}
		}
	}
	
	@EventHandler
	public void onPrepareCraft(PrepareItemCraftEvent e)
	{
		CraftingInventory inv = e.getInventory();
		ItemStack result = inv.getResult();
		if(result != null && result.getType() != Material.GLASS_BOTTLE)
		{
			List<ItemStack> bottles = Arrays.asList(inv.getMatrix());
			Iterator<ItemStack> it = bottles.iterator();
			
			while(it.hasNext())
			{
				ItemStack bottle = it.next();
				if(bottle == null) continue;
				if(bottle.getType().equals(Material.POTION))
				{
					if(!checkWaterBottle(bottle))
					{
						inv.setResult(null);
						return;
					}
				}
			}
		}
	}
	
	public boolean checkWaterBottle(ItemStack bottle)
	{
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_bottle = CraftItemStack.asNMSCopy(bottle);
        NBTTagCompound compound_bottle = nmsStack_bottle.getTag();
        if (compound_bottle != null)
        {
        	if(compound_bottle.getString("Potion").equals("minecraft:water"))
				return true;
        	else
        		return false;
        }
        return false;
	}
}
