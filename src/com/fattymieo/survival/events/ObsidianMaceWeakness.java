package com.fattymieo.survival.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lib.ParticleEffect;

public class ObsidianMaceWeakness implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAttack(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity && event.getCause() == DamageCause.ENTITY_ATTACK) {
			Player player = (Player) event.getDamager();
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			LivingEntity enemy = (LivingEntity) event.getEntity();

			if (mainItem.getType() == Material.GOLDEN_SHOVEL) {
				enemy.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0, false));
				enemy.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0, false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 48, 2, true));
				Location particleLoc = player.getLocation();
				particleLoc.setY(particleLoc.getY() + 2);
				ParticleEffect.HEART.display(0.5f, 0, 0.5f, 1, 2, particleLoc, 64);
			}
		}
	}

}