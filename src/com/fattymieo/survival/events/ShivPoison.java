package com.fattymieo.survival.events;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ShivPoison implements Listener
{
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity && event.getCause() == DamageCause.ENTITY_ATTACK)
		{
			Player player = (Player)event.getDamager();
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			ItemStack offItem = player.getInventory().getItemInOffHand();
			LivingEntity enemy = (LivingEntity)event.getEntity();

			Random rand = new Random();
			
			if(mainItem.getType() == Material.WOOD_HOE)
			{
				enemy.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0, false));
				if(mainItem.getDurability() >= 59)
				{
					player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
					player.getInventory().setItemInMainHand(null);
				}
			}
			
			if(offItem.getType() == Material.WOOD_HOE)
			{
				int chance_poison = rand.nextInt(4) + 1;
				switch(chance_poison)
				{
					case 1:
					case 2:
					enemy.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0, false));
					break;
					default:
				}
				offItem.setDurability((short)(offItem.getDurability() + 1));
				if(offItem.getDurability() >= 59)
				{
					player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
					player.getInventory().setItemInOffHand(null);
				}
			}
		}
	}
}
