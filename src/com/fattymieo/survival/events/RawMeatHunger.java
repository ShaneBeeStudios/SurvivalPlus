package com.fattymieo.survival.events;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RawMeatHunger implements Listener
{
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event)
	{
		Random rand = new Random();
		Player player = event.getPlayer();
		switch(event.getItem().getType())
		{
			case RAW_BEEF:
			case PORK:
			case MUTTON:
			case RAW_FISH:
				int hungerChance = rand.nextInt(10) + 1;
				if(hungerChance >= 1 && hungerChance <= 3)
					player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 0, false));
				break;
			default:
		}
	}
}
