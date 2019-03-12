package com.fattymieo.survival.events;

import com.fattymieo.survival.Survival;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;

import java.util.List;
import java.util.Random;

public class Consume implements Listener
{
	Objective thirst = Survival.mainBoard.getObjective("Thirst");
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onConsume(PlayerItemConsumeEvent event)
	{
		if(event.isCancelled()) return;
		final Player player = event.getPlayer();
		switch(event.getItem().getType())
		{
			case POTION:
				thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 18);
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
			case BEETROOT_SOUP: //Water Bowl
				thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 18);
				event.setCancelled(true);
				player.getInventory().remove(event.getItem());
				player.getInventory().addItem(new ItemStack(Material.BOWL));
				if(Survival.settings.getBoolean("Mechanics.Thirst.PurifyWater"))
				{
					Random rand = new Random();
					if(rand.nextInt(10) + 1 <= 6)
					{
						player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
					}
				}
				break;
			case MILK_BUCKET:
				thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 30);
				break;
			case MELON:
				thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 6);
				break;
			case MUSHROOM_STEW:
				thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 12);
				break;
			default:
		}
		
		if(thirst.getScore(player.getName()).getScore() > 40)
		{
			thirst.getScore(player.getName()).setScore(40);
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
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		thirst.getScore(player.getName()).setScore(30);
	}
	
	@EventHandler
	public void onFirstJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(!player.hasPlayedBefore())
		{
			thirst.getScore(player.getName()).setScore(30);
		}
	}
	
	public boolean checkWaterBottle(ItemStack bottle)
	{
		net.minecraft.server.v1_13_R2.ItemStack nmsStack_bottle = CraftItemStack.asNMSCopy(bottle);
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
