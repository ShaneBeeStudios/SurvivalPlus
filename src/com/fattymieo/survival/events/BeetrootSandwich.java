package com.fattymieo.survival.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class BeetrootSandwich implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onConsume(PlayerItemConsumeEvent event)
	{
		if(event.isCancelled()) return;
		Player player = event.getPlayer();
		if(event.getItem().getType() == Material.BEETROOT_SOUP)
		{
			player.setFoodLevel(player.getFoodLevel() + 4);
			player.setSaturation(player.getSaturation() + 4.8f);
		}
	}
}
