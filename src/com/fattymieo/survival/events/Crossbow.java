package com.fattymieo.survival.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.util.Vector;

import com.fattymieo.survival.Survival;
//Use Longbow
public class Crossbow implements Listener
{
	Objective crossbowCD = Survival.board.getObjective("CrossbowCooldown");
	Objective firing = Survival.board.getObjective("CrossbowFiring");
	
	Objective tech_corssbowMsg = Survival.board.registerNewObjective("CrossbowMsg", "dummy");
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemClick(PlayerInteractEvent event)
	{
		final Player player = event.getPlayer();
		ItemStack mainItem = player.getInventory().getItemInMainHand();
		ItemStack offItem = player.getInventory().getItemInOffHand();

		Score score_crossbowMsg = tech_corssbowMsg.getScore(player);
		if(mainItem != null && mainItem.getType() == Material.GOLD_AXE)
		{
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
			{
				if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					switch(event.getClickedBlock().getType())
					{
						case ENCHANTMENT_TABLE:
						case ANVIL:
						case BREWING_STAND:
						case SPRUCE_DOOR:
						case BIRCH_DOOR:
						case WOOD_DOOR:
						case JUNGLE_DOOR:
						case ACACIA_DOOR:
						case DARK_OAK_DOOR:
						case IRON_DOOR:
						case TRAPPED_CHEST:
						case CHEST:
						case BED:
						case NOTE_BLOCK:
						case FENCE_GATE:
						case SPRUCE_FENCE_GATE:
						case BIRCH_FENCE_GATE:
						case JUNGLE_FENCE_GATE:
						case DARK_OAK_FENCE_GATE:
						case ACACIA_FENCE_GATE:
						case TRAP_DOOR:
						case IRON_TRAPDOOR:
						case FURNACE:
						case BURNING_FURNACE:
						case HOPPER:
						case WORKBENCH:
						case DROPPER:
						case DISPENSER:
							return;
						default:
					}
				}
				
				if(firing.getScore(player).getScore() <= 0)
				{
					if(offItem != null)
					{
						Random rand = new Random();
						Arrow arrowType = null;
						switch(offItem.getType())
						{
							case ARROW:
								arrowType = player.launchProjectile(Arrow.class);
								break;
							case SPECTRAL_ARROW:
								arrowType = player.launchProjectile(SpectralArrow.class);
								arrowType.setGlowing(true);
								break;
							default:
								if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
									score_crossbowMsg.setScore(score_crossbowMsg.getScore() + 1);
								else if(event.getAction() == Action.RIGHT_CLICK_AIR)
									score_crossbowMsg.setScore(score_crossbowMsg.getScore() + 2);
								if(score_crossbowMsg.getScore() == 2)
								{
									player.sendMessage(ChatColor.RED + Survival.Words.get("Must load Arrows on off hand"));
									score_crossbowMsg.setScore(0);
								}
								return;
						}
						final Arrow arrow = arrowType;
						final Vector velocity = player.getLocation().getDirection().multiply(4);
						arrow.setVelocity(velocity);
						arrow.setCritical(true);
	
	                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
	                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
	                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_BULLET_HURT, 0.5F, rand.nextFloat() * 0.4F + 0.8F);
	                    
	                    offItem.setAmount(offItem.getAmount() - 1);
	                    if(offItem.getAmount() <= 0)
	                    	player.getInventory().setItemInOffHand(null);
						
						crossbowCD.getScore(player).setScore(20);
						
						final Runnable task = new Runnable()
						{
							public void run()
							{
								int times = crossbowCD.getScore(player).getScore();
								
								if(!arrow.isOnGround())
								{
									arrow.setVelocity(velocity);
									if(times-- > 0)
									{							
										Bukkit.getScheduler().scheduleSyncDelayedTask(Survival.instance, this, 5);
										crossbowCD.getScore(player).setScore(times);
									}
									else
										crossbowCD.getScore(player).setScore(0);
								}
								else
									crossbowCD.getScore(player).setScore(0);
							}
						};
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(Survival.instance, task, -1);
						
						firing.getScore(player).setScore(1);
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(Survival.instance, new Runnable()
						{
							public void run()
							{
								firing.getScore(player).setScore(0);
								player.sendMessage(ChatColor.GREEN + Survival.Words.get("Ready to Fire"));
							}
						}, 100);
					}
				}
				else 
				{
					score_crossbowMsg.setScore(score_crossbowMsg.getScore() + 1);
					if(score_crossbowMsg.getScore() == 2)
					{
						player.sendMessage(ChatColor.RED + Survival.Words.get("Crossbow is being reloaded"));
						score_crossbowMsg.setScore(0);
					}
				}
			}
		}
	}
}
