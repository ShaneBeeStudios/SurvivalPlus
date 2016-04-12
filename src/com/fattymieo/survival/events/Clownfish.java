package com.fattymieo.survival.events;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import lib.ParticleEffect;

public class Clownfish implements Listener
{
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event)
	{
		Player player = event.getPlayer();
		if(event.getItem().getType() == Material.RAW_FISH && event.getItem().getDurability() == (short)2)
		{
			Random rand = new Random();
			Location originLoc = player.getLocation();
            ParticleEffect.PORTAL.display(0.5f, 0.5f, 0.5f, 0.5f, 10, originLoc, 64);
			player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
			
			if(player.getCompassTarget() != null)
			{
				Location teleportLoc = player.getCompassTarget();
				player.teleport(teleportLoc);
	            ParticleEffect.PORTAL.display(0.5f, 0.5f, 0.5f, 1, 200, teleportLoc, 64);
				player.getLocation().getWorld().playSound(teleportLoc, Sound.BLOCK_PORTAL_TRAVEL, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
			}
			else
			{
				Location teleportLoc = player.getWorld().getSpawnLocation();
				player.teleport(teleportLoc);
	            ParticleEffect.PORTAL.display(0.5f, 0.5f, 0.5f, 1, 200, teleportLoc, 64);
				player.getLocation().getWorld().playSound(teleportLoc, Sound.BLOCK_PORTAL_TRAVEL, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
			}
		}
	}
}
