package com.fattymieo.survival.events;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RawMeatHunger implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onConsume(PlayerItemConsumeEvent event)
	{
		if(event.isCancelled()) return;
		Random rand = new Random();
		Player player = event.getPlayer();
		switch(event.getItem().getType())
		{
			case RAW_BEEF:
			case PORK:
			case MUTTON:
			case RAW_FISH:
			case RAW_CHICKEN:
			case ROTTEN_FLESH:
				int hungerChance = rand.nextInt(10) + 1;
				if(hungerChance >= 1 && hungerChance <= 8)
				{
					int dur = 600;
					for (PotionEffect effect : player.getActivePotionEffects())
					{
						if(effect.getType().equals(PotionEffectType.HUNGER))
						{
							dur += effect.getDuration();
					        player.removePotionEffect(effect.getType());
						}
					}
					player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, dur, 0, false));
				}
				break;
			default:
		}
	}
}
