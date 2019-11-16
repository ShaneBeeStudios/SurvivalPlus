package tk.shanebee.survival.listeners;

import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.util.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import tk.shanebee.survival.Survival;

class CompassWaypoint implements Listener {

	private Lang lang;
	private PlayerManager playerManager;

	public CompassWaypoint(Survival plugin) {
		this.lang = plugin.getLang();
		this.playerManager = plugin.getPlayerManager();
	}

	@EventHandler
	private void onItemClick(PlayerInteractEvent event) {
		if (event.hasItem()) {
			Player player = event.getPlayer();
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			ItemStack offItem = player.getInventory().getItemInOffHand();
			if (player.isSneaking()) {
				if (mainItem.getType() == Material.COMPASS || offItem.getType() == Material.COMPASS) {
					if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						assert event.getClickedBlock() != null;
						switch (event.getClickedBlock().getType()) {
							case HOPPER:
							case CRAFTING_TABLE:
							case DROPPER:
							case DISPENSER:
								return;
							default:
						}
						if (Tag.BEDS.isTagged(event.getClickedBlock().getType())) return;
						if (Utils.isWoodGate(event.getClickedBlock().getType())) return;
						if (Tag.DOORS.isTagged(event.getClickedBlock().getType())) return;
						if (Utils.isCookingBlock(event.getClickedBlock().getType())) return;
						if (Utils.isStorageBlock(event.getClickedBlock().getType())) return;
						if (Utils.isUtilityBlock(event.getClickedBlock().getType())) return;

						Location loc = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
						player.sendMessage(ChatColor.LIGHT_PURPLE + Utils.getColoredString(lang.compass_pointed) + " (" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ").");
						loc.add(0.5, 0.5, 0.5);
						playerManager.setWaypoint(player, loc, true);
					}

					if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
						Location loc = player.getLocation().getBlock().getLocation();
						player.sendMessage(ChatColor.LIGHT_PURPLE + Utils.getColoredString(lang.compass_coords) + " (" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ")");
					}
				}
			}
		}
	}

}