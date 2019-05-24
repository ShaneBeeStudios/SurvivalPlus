package tk.shanebee.survival.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tk.shanebee.survival.managers.Items;

import java.util.Random;

public class ShivPoison implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onAttack(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity && event.getCause() == DamageCause.ENTITY_ATTACK) {
			Player player = (Player) event.getDamager();
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			ItemStack offItem = player.getInventory().getItemInOffHand();
			LivingEntity enemy = (LivingEntity) event.getEntity();

			Random rand = new Random();

			if (Items.compare(mainItem, Items.SHIV)) {
				enemy.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0, false));
				assert mainItem.getItemMeta() != null;
				if (((Damageable) mainItem.getItemMeta()).getDamage() >= 59) {
					player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
					player.getInventory().setItemInMainHand(null);
				}
			}

			if (Items.compare(offItem, Items.SHIV)) {
				int chance_poison = rand.nextInt(4) + 1;
				switch (chance_poison) {
					case 1:
					case 2:
						enemy.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0, false));
						break;
					default:
				}
				ItemMeta meta = offItem.getItemMeta();
				assert meta != null;
				((Damageable) meta).setDamage(((Damageable) meta).getDamage() + 1);
				offItem.setItemMeta(meta);
				if (((Damageable) offItem.getItemMeta()).getDamage() >= 59) {
					player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
					player.getInventory().setItemInOffHand(null);
				}
			}
		}
	}

}
