package tk.shanebee.survival.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Utils;

public class Bow implements Listener {

	@EventHandler
	private void onShootWithoutArrows(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			ItemStack mainHand = player.getInventory().getItemInMainHand();
			if (event.getBow() != null && mainHand.getType() == event.getBow().getType()) {
				if (isArrowOffHand(player)) {
					event.setCancelled(false);
				} else {
					if (mainHand.getType() != Material.CROSSBOW) {
						event.setCancelled(true);
						Utils.sendColoredMsg(player, Survival.lang.arrows_off_hand);
						player.updateInventory();
					}
				}
			} else {
				event.setCancelled(true);
				Utils.sendColoredMsg(player, Survival.lang.bow_main_hand);
				player.updateInventory();
			}
		}
	}

	@EventHandler
	private void onLoadCrossbow(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		ItemStack offHand = player.getInventory().getItemInOffHand();
		if (mainHand.getType() == Material.CROSSBOW && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) return;
			if (mainHand.getItemMeta() != null && ((CrossbowMeta) mainHand.getItemMeta()).hasChargedProjectiles()) return;
			if (!isArrowOffHand(player)) {
				event.setCancelled(true);
				Utils.sendColoredMsg(player, Survival.lang.arrows_off_hand_crossbow);
			}
		} else if (offHand.getType() == Material.CROSSBOW) {
			if (event.getHand() == EquipmentSlot.HAND) return;
			event.setCancelled(true);
			Utils.sendColoredMsg(player, Survival.lang.bow_main_hand);
		}
	}

	/** Check if player is holding arrows in their offhand
	 * @param player The player to check
	 * @return Whether or not the player has arrows in their offhand
	 */
	@SuppressWarnings("WeakerAccess")
	public static boolean isArrowOffHand(Player player){
		Material mainHand = player.getInventory().getItemInMainHand().getType();
		Material offHand = player.getInventory().getItemInOffHand().getType();
		if (mainHand == Material.CROSSBOW)
			return offHand == Material.ARROW || offHand == Material.SPECTRAL_ARROW
					|| offHand == Material.TIPPED_ARROW || offHand == Material.FIREWORK_ROCKET;
		return offHand == Material.ARROW || offHand == Material.SPECTRAL_ARROW || offHand == Material.TIPPED_ARROW;
	}

}
