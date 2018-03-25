package com.fattymieo.survival.events;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.util.Vector;

import com.fattymieo.survival.Survival;

public class GrapplingHook implements Listener
{
	@EventHandler
	public void onPlayerFish(PlayerFishEvent event)
	{
        Player p = event.getPlayer();
        
	    if(p.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD)
	    {
	    	if(p.getInventory().getItemInOffHand() == null || p.getInventory().getItemInOffHand().getType() == Material.AIR)
	    	{
		    	if(event.getState() == State.IN_GROUND)
		    	{
			        List<Entity> nearbyEntities = p.getNearbyEntities(50,50,50);
			        
			        Entity hook = null;
			        
			        for (Entity e : nearbyEntities) // loop through entities
			        {
			            if (e.getType() == EntityType.FISHING_HOOK) //Hook found
			            {
			                hook = e;
			                break;
			            }
			        }
			        
			        if (hook != null)
			        {
			            Location hookLoc = hook.getLocation();
			    		Location playerLoc = p.getLocation();
			    		
			    		playerLoc.setY(playerLoc.getY()+0.5);
			    		
			    		
			    		Vector vector = hookLoc.toVector().subtract(playerLoc.toVector());
			    		if(vector.getY() > 0)
				    		vector.setY(Math.sqrt(vector.getY()));
			    		
			    		p.teleport(playerLoc);
			    		p.setVelocity(vector.multiply(0.5));
			        }
		    	}
		    	else if(event.getState() == State.CAUGHT_ENTITY)
		    	{
		    		if(event.getCaught() != null)
		    		{
			    		Location playerLoc = p.getLocation();
			    		Location entityLoc = event.getCaught().getLocation();
	
			    		playerLoc.setY(playerLoc.getY()+0.5);
			    		entityLoc.setY(entityLoc.getY()+0.5);
			    		
			    		if(event.getCaught().getType() != EntityType.DROPPED_ITEM)
			    		{
				    		Vector vector = entityLoc.toVector().subtract(playerLoc.toVector());
				    		if(vector.getY() > 0)
					    		vector.setY(Math.sqrt(vector.getY())*4);
	
				    		p.teleport(playerLoc);
				    		p.setVelocity(vector.multiply(0.5).multiply(0.25));
			    		}
	
			    		Vector reverseVector = playerLoc.toVector().subtract(entityLoc.toVector());
	
			    		if(reverseVector.getY() > 0)
			    			reverseVector.setY(Math.sqrt(reverseVector.getY()));
			    		
			    		if(event.getCaught().getType() != EntityType.DROPPED_ITEM)
			    		{
				    		event.getCaught().teleport(entityLoc);
			    			event.getCaught().setVelocity(reverseVector.multiply(0.5).multiply(0.125));
			    		}
			    		else
			    		{
				    		if(reverseVector.getY() > 0)
				    			reverseVector.setY(Math.sqrt(reverseVector.getY())*0.5);
	
				    		event.getCaught().teleport(entityLoc);
			    			event.getCaught().setVelocity(reverseVector.multiply(0.5).multiply(0.00625));
			    		}
		    		}
		    	}
	    	}
	    	else
	    	{
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + Survival.Words.get("Must use a fishing hook with empty off hand"));
				p.updateInventory();
	    	}
	    }
		else
		{
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + Survival.Words.get("Must use a fishing hook with main hand"));
			p.updateInventory();
		}
    }
}