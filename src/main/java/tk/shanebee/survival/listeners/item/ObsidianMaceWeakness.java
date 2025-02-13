package tk.shanebee.survival.listeners.item;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.item.Items;
import tk.shanebee.survival.managers.EffectManager;
import tk.shanebee.survival.util.Utils;

public class ObsidianMaceWeakness implements Listener {

	private EffectManager effectManager;
	private Config config;

	public ObsidianMaceWeakness(Survival plugin) {
		this.effectManager = plugin.getEffectManager();
		this.config = plugin.getSurvivalConfig();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onAttack(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity && event.getCause() == DamageCause.ENTITY_ATTACK) {
			Player player = (Player) event.getDamager();
            if (Utils.isCitizensNPC(player)) return;
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			LivingEntity enemy = (LivingEntity) event.getEntity();

			if (Items.OBSIDIAN_MACE.is(mainItem)) {
				effectManager.applyObsidianMaceEffects(player, enemy);
			}
		}
	}

	// Prevent obsidian mace turning dirt/grass block into farmland
    @EventHandler
    private void onInteractBlock(PlayerInteractEvent event) {
	    if (!this.config.LEGENDARY_OBSIDIAN_MACE) return;

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack tool = event.getItem();
            if (event.getClickedBlock() == null || tool == null) return;

            Material clickedBlock = event.getClickedBlock().getType();

            if (Items.OBSIDIAN_MACE.is(tool)) {
                if (clickedBlock == Material.GRASS_BLOCK || clickedBlock == Material.DIRT) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
