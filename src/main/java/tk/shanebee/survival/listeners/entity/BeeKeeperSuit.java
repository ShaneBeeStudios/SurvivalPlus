package tk.shanebee.survival.listeners.entity;

import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;

public class BeeKeeperSuit implements Listener {

	@EventHandler
	private void onSting(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Bee) {
			Player player = ((Player) event.getEntity());
			if (hasBeekeeperSuit(player)) {
				Bee bee = ((Bee) event.getDamager());
				event.setCancelled(true);
				bee.setTarget(null);
				bee.setAnger(0);
			}
		}
	}

	@EventHandler
	private void onPoison(EntityPotionEffectEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = ((Player) event.getEntity());
			player.sendMessage("Poisoned: " + event.getHandlers());
		}
	}

	@EventHandler
	private void onTarget(EntityTargetLivingEntityEvent event) {
		if (event.getTarget() instanceof Player && event.getEntity() instanceof Bee) {
			Player player = ((Player) event.getTarget());
			if (!hasBeekeeperSuit(player)) return;
			player.sendMessage("You are being targeted by: " + event.getEntity());
			event.setCancelled(true);
		}
	}

	private boolean hasBeekeeperSuit(Player player) {
		PlayerInventory inv = player.getInventory();
		if (inv.getHelmet() == null || inv.getChestplate() == null || inv.getLeggings() == null || inv.getBoots() == null) {
			return false;
		}
		return ItemManager.compare(inv.getHelmet(), Items.BEEKEEPER_HELMET) && ItemManager.compare(inv.getChestplate(), Items.BEEKEEPER_CHESTPLATE) &&
				ItemManager.compare(inv.getLeggings(), Items.BEEKEEPER_LEGGINGS) && ItemManager.compare(inv.getBoots(), Items.BEEKEEPER_BOOTS);
	}

}
