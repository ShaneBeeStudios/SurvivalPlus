package com.fattymieo.survival.events;

import com.fattymieo.survival.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.fattymieo.survival.Survival;

import lib.ParticleEffect;

public class CompassWaypoint implements Listener {

	@EventHandler
	public void onItemClick(PlayerInteractEvent event) {
		if (event.hasItem()) {
			Player player = event.getPlayer();
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			ItemStack offItem = player.getInventory().getItemInOffHand();
			if (player.isSneaking()) {
				if (mainItem.getType() == Material.COMPASS || offItem.getType() == Material.COMPASS) {
					if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						switch (event.getClickedBlock().getType()) {
							case ENCHANTING_TABLE:
							case ANVIL:
							case BREWING_STAND:
							case SPRUCE_DOOR:
							case BIRCH_DOOR:
							case OAK_DOOR:
							case JUNGLE_DOOR:
							case ACACIA_DOOR:
							case DARK_OAK_DOOR:
							case IRON_DOOR:
							case TRAPPED_CHEST:
							case CHEST:
							case NOTE_BLOCK:
							case SPRUCE_FENCE_GATE:
							case BIRCH_FENCE_GATE:
							case JUNGLE_FENCE_GATE:
							case DARK_OAK_FENCE_GATE:
							case ACACIA_FENCE_GATE:
							case IRON_TRAPDOOR:
							case FURNACE:
							case HOPPER:
							case CRAFTING_TABLE:
							case DROPPER:
							case DISPENSER:
								return;
							default:
						}
						if (Utils.isBed(event.getClickedBlock().getType())) return;
						if (Utils.isWoodGate(event.getClickedBlock().getType())) return;
						if (Tag.TRAPDOORS.isTagged(event.getClickedBlock().getType())) return;

						Location loc = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
						player.sendMessage(ChatColor.LIGHT_PURPLE + Utils.getColoredString(Survival.lang.compass_pointed) + " (" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ").");
						loc.add(0.5, 0.5, 0.5);
						player.setCompassTarget(loc);
						ParticleEffect.CLOUD.display(0.5f, 0.5f, 0.5f, 0, 25, loc, player);
					}

					if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
						Location loc = player.getLocation().getBlock().getLocation();
						player.sendMessage(ChatColor.LIGHT_PURPLE + Utils.getColoredString(Survival.lang.compass_coords) + " (" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ")");
					}
				}
			}
		}
	}

}