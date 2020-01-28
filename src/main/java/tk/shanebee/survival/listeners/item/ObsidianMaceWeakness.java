package tk.shanebee.survival.listeners.item;

import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.EffectManager;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.util.Utils;
import org.bukkit.Location;
import org.bukkit.Particle;
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

public class ObsidianMaceWeakness implements Listener {

	private EffectManager effectManager;

	public ObsidianMaceWeakness(Survival plugin) {
		this.effectManager = plugin.getEffectManager();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onAttack(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity && event.getCause() == DamageCause.ENTITY_ATTACK) {
			Player player = (Player) event.getDamager();
            if (Utils.isCitizensNPC(player)) return;
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			LivingEntity enemy = (LivingEntity) event.getEntity();

			if (ItemManager.compare(mainItem, Items.OBSIDIAN_MACE)) {
				effectManager.applyObsidianMaceEffects(player, enemy);
			}
		}
	}

}