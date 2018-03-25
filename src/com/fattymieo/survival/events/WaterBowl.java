package com.fattymieo.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.fattymieo.survival.Survival;

public class WaterBowl implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onConsume(PlayerItemConsumeEvent event)
	{
		if(!Survival.settings.getBoolean("Mechanics.Thirst.Enabled"))
		{
			if(event.isCancelled()) return;
			if(event.getItem().getType() == Material.BEETROOT_SOUP)
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDrop(ItemSpawnEvent event)
	{
		if(event.isCancelled()) return;
		final Item itemDrop = event.getEntity();
		if(itemDrop.getItemStack().getType() == Material.BOWL)
		{
		    final Runnable task = new Runnable()
		    {
		    	public void run()
		    	{
		    		if(itemDrop.getItemStack().getAmount() != 1) return;
		    		Location itemLocation = itemDrop.getLocation();
		    		if(itemLocation.getBlock().getType() == Material.WATER || itemLocation.getBlock().getType() == Material.STATIONARY_WATER)
		    		{
		    			itemDrop.remove();
		    			
		    	      	ItemStack i_beetroot = new ItemStack(Material.BEETROOT_SOUP, 1);
		    	      	ItemMeta beetrootMeta= i_beetroot.getItemMeta();
		    	      	beetrootMeta.setDisplayName(ChatColor.RESET + Survival.Words.get("Water Bowl"));
		    	      	i_beetroot.setItemMeta(beetrootMeta);
		    	      	
		    			itemDrop.getWorld().dropItem(itemLocation, i_beetroot);
		    		}
	            }
		    };
		    
		    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, task, 20L);
		}
	}
}
