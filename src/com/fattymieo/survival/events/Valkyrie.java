package com.fattymieo.survival.events;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import com.fattymieo.survival.Survival;

import lib.ParticleEffect;

public class Valkyrie implements Listener
{	
	int schedule = 0;
    
    private Objective spin = Survival.board.getObjective("Spin");
    private Objective dualWield = Survival.board.getObjective("DualWield");
    
	//To prevent double messages send to player.
	private Objective tech_dualWieldMsg = Survival.board.getObjective("DualWieldMsg");
	
	@EventHandler
	public void onItemClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		ItemStack mainItem = player.getInventory().getItemInMainHand();
		
		Score score_dualWieldMsg = tech_dualWieldMsg.getScore(player.getName());
		
		if(mainItem.getType() == Material.GOLDEN_AXE)
		{
			if(dualWield.getScore(player.getName()).getScore() == 0)
			{
				if(event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR)
				{
					if(spin.getScore(player.getName()).getScore() == 0)
					{
						if(player.getFoodLevel() > 6)
						{
							Random rand = new Random();
							
							Spin(player);
							
							if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
								player.setFoodLevel(player.getFoodLevel() - 1);
							
							int chance_reduceDur = rand.nextInt(10) + 1;
							switch(chance_reduceDur)
							{
								case 1:
									//mainItem.setDurability((short)(mainItem.getDurability() + 1));
									// in favor of new item meta based durability
									ItemMeta meta = mainItem.getItemMeta();
									((Damageable) meta).setDamage(((Damageable) meta).getDamage() + 1);
									mainItem.setItemMeta(meta);
									break;
								default:
							}
							
							if(((Damageable) mainItem.getItemMeta()).getDamage() >= 32)
							{
								player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
								player.getInventory().setItemInMainHand(null);
							}
							player.updateInventory();
						}
						else
						{
							player.sendMessage(ChatColor.RED + Survival.Words.get("Lack of energy, unable to spin"));
						}
					}
				}
			}
			else 
			{
				if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
					score_dualWieldMsg.setScore(score_dualWieldMsg.getScore() + 1);
				else if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
					score_dualWieldMsg.setScore(score_dualWieldMsg.getScore() + 2);
				if(score_dualWieldMsg.getScore() == 2)
				{
					player.sendMessage(ChatColor.RED + Survival.Words.get("Unable to dual-wield with Valkyrie's Axe"));
				}
			}
		}
		score_dualWieldMsg.setScore(0);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAttack(EntityDamageByEntityEvent event)
	{
		if(event.isCancelled()) return;
		if(event.getDamager() instanceof Player)
		{
			Player player = (Player)event.getDamager();
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			
			if(dualWield.getScore(player.getName()).getScore() == 0)
			{
				if(mainItem.getType() == Material.GOLDEN_AXE)
				{
					if(spin.getScore(player.getName()).getScore() == 0)
					{
						if(player.getFoodLevel() > 6)
						{
							Random rand = new Random();
							
							Spin(player);
							
							if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
								player.setFoodLevel(player.getFoodLevel() - 1);
							
							int chance_reduceDur = rand.nextInt(10) + 1;
							switch(chance_reduceDur)
							{
								case 1:
									mainItem.setDurability((short)(mainItem.getDurability() + 1));
									break;
								default:
							}
							
							if(mainItem.getDurability() >= 32)
							{
								player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
								player.getInventory().setItemInMainHand(null);
							}
							player.updateInventory();
						}
						else
						{
							player.sendMessage(ChatColor.RED + Survival.Words.get("Lack of energy, unable to spin"));
						}
					}
				}
			}
			else
			{
				event.setCancelled(true);
				return;
			}
		}
	}
	
	private void Spin(final Player player)
	{		
		Score score = spin.getScore(player.getName());
		score.setScore(1);
	    
        particleCircle(player, 50, 2.5f, ParticleEffect.CRIT);
        particleCircle(player, 25, 2f, ParticleEffect.CRIT);
        particleCircle(player, 10, 2.5f, ParticleEffect.CRIT_MAGIC);
        
		Random rand = new Random();
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5F, rand.nextFloat() * 0.4F + 0.8F);
	    
		damageNearbyEnemies(player, 8);
	    
	    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, new Runnable()
	    {
			Score score = spin.getScore(player.getName());
	    	public void run()
	    	{
	    		score.setScore(0);
            }
	    },
	    20L);
	}
	
	private void particleCircle(Player player ,int particles, float radius, ParticleEffect particle)
	{
	    Location location = player.getEyeLocation();
		for (int i = 0; i < particles; i++) {
            double angle, x, z;
            angle = 2 * Math.PI * i / particles;
            x = Math.cos(angle) * radius;
            z = Math.sin(angle) * radius;
            location.add(x, -0.3, z);
            particle.display(0.05f, 0.05f, 0.05f, 0, 1, location, 64);
            location.subtract(x, -0.3, z);
        }
	}
	
	private void damageNearbyEnemies(Player player, int dmg)
	{
		Collection<Entity> enemies = player.getLocation().getWorld().getNearbyEntities(player.getLocation().add(0, 0.5, 0), 3.5f, 1.5f, 3.5f);
	    for(Entity e : enemies)
	    {
	    	if(e instanceof LivingEntity && e != (Entity)player)
	    	{
	    		LivingEntity enemy = (LivingEntity)e;
	    		enemy.damage(dmg, (Entity)player);
	    	}
	    }
	}
}
