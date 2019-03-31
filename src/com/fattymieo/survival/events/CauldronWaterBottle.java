package com.fattymieo.survival.events;

import java.util.Arrays;
import java.util.List;

import com.fattymieo.survival.util.Utils;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Cauldron;

import com.fattymieo.survival.Survival;

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
								//event.getClickedBlock().setData((byte)((int)event.getClickedBlock().getData() - 1));
								cauldron.setLevel(cauldron.getLevel() + 1);
								event.getClickedBlock().setBlockData(cauldron); // TODO not sure if this will work, def needs testing


								ItemStack waterBottle = new ItemStack(Material.POTION, 1);

								net.minecraft.server.v1_13_R2.ItemStack nmsStack_bottle = CraftItemStack.asNMSCopy(waterBottle);
								NBTTagCompound compound_bottle = nmsStack_bottle.getTag();
								compound_bottle.setString("Potion", "minecraft:water");
								compound_bottle.setInt("HideFlags", 32);
								nmsStack_bottle.setTag(compound_bottle);
								waterBottle = CraftItemStack.asBukkitCopy(nmsStack_bottle);

								ItemMeta meta = waterBottle.getItemMeta();

								if (fire != null && fire.getType() == Material.FIRE) {
									List<String> lore = Arrays.asList
											(
													ChatColor.RESET + Utils.getColoredString("&7" + Survival.lang.purified_water)
											);
									meta.setLore(lore);
								}
								waterBottle.setItemMeta(meta);

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