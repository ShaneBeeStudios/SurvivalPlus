package com.fattymieo.survival.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;

import com.fattymieo.survival.Survival;

import lib.ParticleEffect;

public class MedicKit implements Listener
{
	Objective healing = Survival.board.getObjective("Healing");
	Objective healTimes = Survival.board.getObjective("HealTimes");
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamaged(EntityDamageByEntityEvent event)
	{
		if(event.isCancelled()) return;
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			healing.getScore(player.getName()).setScore(0);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClickEntity(PlayerInteractEntityEvent event)
	{
		if(event.isCancelled()) return;
		final Player player = event.getPlayer();
		final ItemStack mainItem = player.getInventory().getItemInMainHand();
		if((mainItem != null && mainItem.getType() == Material.WATCH))
		{
			if(healing.getScore(player.getName()).getScore() <= 0)
			{
				if(!player.isSneaking())
				{
					if(event.getRightClicked() instanceof Player)
					{
						final Player healed = (Player)event.getRightClicked();
						if(healing.getScore(healed.getName()).getScore() <= 0)
						{
							if(player.getLocation().distance(healed.getLocation()) <= 4)
							{
								healing.getScore(player.getName()).setScore(1);
								healing.getScore(healed.getName()).setScore(1);
								healed.teleport(Survival.lookAt(healed.getLocation(), player.getLocation()));
								player.sendMessage(Survival.Words.get("§aHealing ") + ChatColor.RESET + healed.getDisplayName() + Survival.Words.get("§a, keep ") + ChatColor.DARK_GREEN + Survival.Words.get("Medical Kit") + Survival.Words.get("§a on hand"));
								healed.sendMessage(Survival.Words.get("§aYou are being healed by ") + ChatColor.RESET + player.getDisplayName() + Survival.Words.get("§a, stay still"));
								
								healTimes.getScore(player.getName()).setScore(5);
								final Runnable task = new Runnable()
								{
									public void run()
									{
										int times = healTimes.getScore(player.getName()).getScore();
										if(player.getInventory().getItemInMainHand().getType() == Material.WATCH && player.getLocation().distance(healed.getLocation()) <= 4 && healing.getScore(player.getName()).getScore() > 0 && healing.getScore(healed.getName()).getScore() > 0)
										{
											if(times-- > 0)
											{
												player.teleport(Survival.lookAt(player.getLocation(), healed.getLocation()));
												
												Random rand = new Random();
												
												player.removePotionEffect(PotionEffectType.SLOW);
						                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 6, true, false));
						                        player.removePotionEffect(PotionEffectType.JUMP);
						                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 199, true, false));
						                        
						                        healed.getWorld().playSound(healed.getLocation(), Sound.ENTITY_LEASHKNOT_PLACE, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
												healed.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 0));
												
												Location particleLoc = healed.getLocation();
						                        particleLoc.setY(particleLoc.getY() + 1);
						                        ParticleEffect.VILLAGER_HAPPY.display(0.5f, 0.5f, 0.5f, 0, 10, particleLoc, 64);

												Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, this, 20L);
						                        healTimes.getScore(player.getName()).setScore(times);
											}

											else
											{
												healing.getScore(player.getName()).setScore(0);
												healing.getScore(healed.getName()).setScore(0);
												
												player.sendMessage(ChatColor.DARK_GREEN + Survival.Words.get("Healing complete"));
												healed.sendMessage(ChatColor.DARK_GREEN + Survival.Words.get("Healing complete"));
												
												player.getInventory().removeItem(i_MedicKit());
											}
										}
										else
										{
											healing.getScore(player.getName()).setScore(0);
											healing.getScore(healed.getName()).setScore(0);
											
											player.sendMessage(ChatColor.DARK_RED + Survival.Words.get("Healing interrupted"));
											healed.sendMessage(ChatColor.DARK_RED + Survival.Words.get("Healing interrupted"));
											
											player.getInventory().removeItem(i_MedicKit());
										}
									}
								};
								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, task, -1L);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onSelfClick(PlayerInteractEvent event)
	{
		if(event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			final Player player = event.getPlayer();
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			if(mainItem != null && mainItem.getType() == Material.WATCH)
			{
				if(healing.getScore(player.getName()).getScore() <= 0)
				{
					if(player.isSneaking())
					{
						healing.getScore(player.getName()).setScore(1);
						player.sendMessage(Survival.Words.get("§aHealing §ryourself") + Survival.Words.get("§a, keep ") + ChatColor.DARK_GREEN + Survival.Words.get("Medical Kit") + Survival.Words.get("§a on hand"));
						
						healTimes.getScore(player.getName()).setScore(5);
						final Runnable task = new Runnable()
						{
							public void run()
							{
								int times = healTimes.getScore(player.getName()).getScore();
								if(player.getInventory().getItemInMainHand().getType() == Material.WATCH && healing.getScore(player.getName()).getScore() > 0)
								{
									if(times-- > 0)
									{													
										Random rand = new Random();
										
										player.removePotionEffect(PotionEffectType.SLOW);
				                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 6, true, false));
				                        player.removePotionEffect(PotionEffectType.JUMP);
				                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 199, true, false));
				                        
				                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LEASHKNOT_PLACE, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
										player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 0));
										
										Location particleLoc = player.getLocation();
				                        particleLoc.setY(particleLoc.getY() + 1);
				                        ParticleEffect.VILLAGER_HAPPY.display(0.5f, 0.5f, 0.5f, 0, 10, particleLoc, 64);

										Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, this, 20L);
										healTimes.getScore(player.getName()).setScore(times);
									}

									else
									{
										healing.getScore(player.getName()).setScore(0);
										
										player.sendMessage(ChatColor.DARK_GREEN + Survival.Words.get("Healing complete"));
										
										player.getInventory().removeItem(i_MedicKit());
									}
								}
								else
								{
									healing.getScore(player.getName()).setScore(0);
									
									player.sendMessage(ChatColor.DARK_RED + Survival.Words.get("Healing interrupted"));
									
									player.getInventory().removeItem(i_MedicKit());
								}
							}
						};
						
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, task, -1L);
					}
				}
			}
		}
	}
	
	private ItemStack i_MedicKit()
	{
		ItemStack i_medicKit = new ItemStack(Material.WATCH, 1);
      	//ItemMeta medicKitMeta= i_medicKit.getItemMeta();
      	//medicKitMeta.setDisplayName(ChatColor.RESET + Survival.Words.get("Medical Kit"));
      	//i_medicKit.setItemMeta(medicKitMeta);
      	
      	return i_medicKit;
	}
}