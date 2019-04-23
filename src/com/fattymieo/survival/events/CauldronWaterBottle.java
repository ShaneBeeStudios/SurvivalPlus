package com.fattymieo.survival.events;

import com.fattymieo.survival.Survival;
import com.fattymieo.survival.util.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Cauldron;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Collections;
import java.util.List;

public class CauldronWaterBottle implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemClick(PlayerInteractEvent event) {
		if (event.isCancelled()) return;
		if (event.hasItem()) {
			Player player = event.getPlayer();
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				ItemStack mainItem = player.getInventory().getItemInMainHand();
				if (mainItem.getType() == Material.GLASS_BOTTLE) {
					if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (event.getClickedBlock().getType() == Material.CAULDRON) {
							Levelled cauldron = (Levelled) (event.getClickedBlock().getBlockData());
							if (cauldron.getLevel() > 0) {
								Block fire = event.getClickedBlock().getRelative(BlockFace.DOWN);
								event.setCancelled(true);

								event.getClickedBlock().getState().setData(new Cauldron());
								cauldron.setLevel(cauldron.getLevel() - 1);
								event.getClickedBlock().setBlockData(cauldron);

								ItemStack waterBottle = new ItemStack(Material.POTION);
								ItemMeta meta = waterBottle.getItemMeta();

								if (fire.getType() == Material.FIRE) {
									List<String> lore = Collections.singletonList(
											ChatColor.RESET + Utils.getColoredString("&7" + Survival.lang.purified_water)
									);
									meta.setLore(lore);
								}
								((PotionMeta) meta).setColor(Color.AQUA);
								((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.WATER));
								meta.setDisplayName(ChatColor.AQUA + "Water Bottle");
								meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
								waterBottle.setItemMeta(meta);
								player.playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);

								if (mainItem.getAmount() > 1) {
									mainItem.setAmount(mainItem.getAmount() - 1);
									if (player.getInventory().firstEmpty() != -1)
										player.getInventory().addItem(waterBottle);
									else
										player.getWorld().dropItem(player.getLocation(), waterBottle);
								} else {
									player.getInventory().setItemInMainHand(waterBottle);
								}
							}
						}
					}
				}
			}
		}
	}

}