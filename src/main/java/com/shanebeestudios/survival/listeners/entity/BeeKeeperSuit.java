package com.shanebeestudios.survival.listeners.entity;

import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.util.Utils;

public class BeeKeeperSuit implements Listener {

	@EventHandler
	private void onSting(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
		if (entity instanceof Player player && damager instanceof Bee bee && !Utils.isCitizensNPC(player)) {
			if (hasBeekeeperSuit(player)) {
				event.setCancelled(true);
				bee.setTarget(null);
				bee.setAnger(0);
			}
		}
	}

	@EventHandler
	private void onPoison(EntityPotionEffectEvent event) {
	    Entity entity = event.getEntity();
		if (entity instanceof Player player && !Utils.isCitizensNPC(player)) {
		    if (event.getCause() != Cause.ATTACK) return;
		    if (event.getModifiedType() != PotionEffectType.POISON) return;
		    if (event.getAction() != Action.ADDED) return;
			if (hasBeekeeperSuit(player)) {
			    event.setCancelled(true);
            }
		}
	}

	@EventHandler
	private void onTarget(EntityTargetLivingEntityEvent event) {
	    Entity target = event.getTarget();
	    Entity entity = event.getEntity();
		if (target instanceof Player player && entity instanceof Bee && !Utils.isCitizensNPC(player)) {
			if (hasBeekeeperSuit(player)) {
                event.setCancelled(true);
            }
		}
	}

	private boolean hasBeekeeperSuit(Player player) {
		PlayerInventory inv = player.getInventory();
		if (inv.getHelmet() == null || inv.getChestplate() == null || inv.getLeggings() == null || inv.getBoots() == null) {
			return false;
		}
        return Items.BEEKEEPER_HELMET.is(inv.getHelmet()) &&
            Items.BEEKEEPER_CHESTPLATE.is(inv.getChestplate()) &&
            Items.BEEKEEPER_LEGGINGS.is(inv.getLeggings()) &&
            Items.BEEKEEPER_BOOTS.is(inv.getBoots());
    }

}
