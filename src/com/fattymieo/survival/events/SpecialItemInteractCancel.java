package com.fattymieo.survival.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.fattymieo.survival.Survival;

public class SpecialItemInteractCancel implements Listener
{
	@EventHandler
	public void onInteractBlock(PlayerInteractEvent event)
	{
		switch(event.getAction())
		{
			case RIGHT_CLICK_BLOCK:
				switch(event.getMaterial())
				{
					case WOOD_HOE:
					if(Survival.settings.getBoolean("Survival.Enabled"))
					{
						switch(event.getClickedBlock().getType())
						{
							case DIRT:
							case GRASS:
								event.setCancelled(true);
							break;
							
							default:
						}
					}
					break;
					case GOLD_HOE:
						if(Survival.settings.getBoolean("LegendaryItems.GiantBlade"))
						{
							switch(event.getClickedBlock().getType())
							{
								case DIRT:
								case GRASS:
									event.setCancelled(true);
								break;
								
								default:
							}
						}
					break;
					
					case WOOD_SPADE:
						if(Survival.settings.getBoolean("Survival.Enabled"))
						{
							switch(event.getClickedBlock().getType())
							{
								case GRASS:
									event.setCancelled(true);
								break;
								
								default:
							}
						}
					break;
					case GOLD_SPADE:
						if(Survival.settings.getBoolean("LegendaryItems.ObsidianMace"))
						{
							switch(event.getClickedBlock().getType())
							{
								case GRASS:
									event.setCancelled(true);
								break;
								
								default:
							}
						}
					break;
					
					default:
				}
				break;
				
				default:
		}
	}
}
