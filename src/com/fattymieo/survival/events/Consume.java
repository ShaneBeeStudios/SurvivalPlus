package com.fattymieo.survival.events;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;

import com.fattymieo.survival.Survival;

import net.minecraft.server.v1_9_R1.NBTTagCompound;

public class Consume implements Listener
{
	Objective thirst = Survival.mainBoard.getObjective("Thirst");
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event)
	{
		final Player player = event.getPlayer();
		switch(event.getItem().getType())
		{
			case POTION:
				thirst.getScore(player).setScore(thirst.getScore(player).getScore() + 18);
				if(Survival.settings.getBoolean("Mechanics.Thirst.PurifyWater"))
				{
					if(checkWaterBottle(event.getItem()))
					{
						List<String> lore = event.getItem().getItemMeta().getLore();
						if(lore == null)
						{
							Random rand = new Random();
							if(rand.nextInt(10) + 1 <= 6)
							{
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
							}
						}
					}
				}
				break;
			case MILK_BUCKET:
				thirst.getScore(player).setScore(thirst.getScore(player).getScore() + 30);
				break;
			case MELON:
				thirst.getScore(player).setScore(thirst.getScore(player).getScore() + 6);
				break;
			case MUSHROOM_SOUP:
				thirst.getScore(player).setScore(thirst.getScore(player).getScore() + 12);
				break;
			default:
		}
		
		if(thirst.getScore(player).getScore() > 40)
		{
			thirst.getScore(player).setScore(40);
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, new Runnable(){
	    	public void run()
	    	{
	    		if(!Survival.settings.getBoolean("Mechanics.StatusScoreboard"))
	    		{
	    			player.sendMessage(Survival.ShowHunger(player).get(1) + Survival.ShowHunger(player).get(2) + " " + Survival.ShowHunger(player).get(0).toUpperCase());
	    			player.sendMessage(Survival.ShowThirst(player).get(1) + Survival.ShowThirst(player).get(2) + " " + Survival.ShowThirst(player).get(0).toUpperCase());
	    		}
            }
	    }, 1L);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		thirst.getScore(player).setScore(30);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFirstJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(!player.hasPlayedBefore())
		{
			thirst.getScore(player).setScore(30);
		}
	}
	
	public boolean checkWaterBottle(ItemStack bottle)
	{
		net.minecraft.server.v1_9_R1.ItemStack nmsStack_bottle = CraftItemStack.asNMSCopy(bottle);
        NBTTagCompound compound_bottle = nmsStack_bottle.getTag();
        if (compound_bottle != null)
        {
        	switch(compound_bottle.getString("Potion"))
        	{
    			case "minecraft:water":
    			case "minecraft:mundane":
    			case "minecraft:thick":
    			case "minecraft:awkward":
    			case "minecraft:empty":
    				return true;
        		default:
        			return false;
        	}
        }
        return false;
	}
}
