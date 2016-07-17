package com.fattymieo.survival.events;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class CauldronWaterBottle implements Listener
{	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemClick(PlayerInteractEvent event)
	{
		if(event.hasItem())
		{
			Player player = event.getPlayer();
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			if(mainItem.getType() == Material.GLASS_BOTTLE)
			{
				if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					if(event.getClickedBlock().getType() == Material.CAULDRON && event.getClickedBlock().getData() != (byte)0)
					{
						Block fire = event.getClickedBlock().getRelative(BlockFace.DOWN);
						if(fire != null)
						{
							if(fire.getType() == Material.FIRE)
							{
								event.setCancelled(true);
								
								event.getClickedBlock().setData((byte)((int)event.getClickedBlock().getData() - 1));
								
								ItemStack waterBottle = new ItemStack(Material.POTION, 1);
								
								net.minecraft.server.v1_10_R1.ItemStack nmsStack_bottle = CraftItemStack.asNMSCopy(waterBottle);
						        NBTTagCompound compound_bottle = nmsStack_bottle.getTag();
						        compound_bottle.setString("Potion","minecraft:water");
						        nmsStack_bottle.setTag(compound_bottle);
						        waterBottle = CraftItemStack.asBukkitCopy(nmsStack_bottle);
						        
								ItemMeta meta = waterBottle.getItemMeta();
								List<String> lore = Arrays.asList
								(
									ChatColor.RESET + "" + ChatColor.GRAY + "Purified"
								);
								meta.setLore(lore);
								waterBottle.setItemMeta(meta);
								
								if(mainItem.getAmount() > 1)
								{
									mainItem.setAmount(mainItem.getAmount() - 1);
									if(player.getInventory().firstEmpty() != -1)
										player.getInventory().addItem(waterBottle);
									else
										player.getWorld().dropItem(player.getLocation(), waterBottle);
								}
								else
								{
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