package com.shanebeestudios.survival.listeners.item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import com.shanebeestudios.survival.item.Items;

@SuppressWarnings("UnstableApiUsage")
public class CauldronWaterBottle implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onItemClick(PlayerInteractEvent event) {
        if (!event.hasItem() || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();

        EquipmentSlot hand = event.getHand();
        ItemStack mainItem = player.getInventory().getItemInMainHand();
        Block clickedBlock = event.getClickedBlock();
        if (hand == null || clickedBlock == null) return;

        Material clickedBlockType = clickedBlock.getType();

        // Click cauldron with bottle to extract water
        if (clickedBlockType == Material.WATER_CAULDRON && mainItem.getType() == Material.GLASS_BOTTLE) {
            event.setCancelled(true);

            Levelled cauldronData = (Levelled) clickedBlock.getBlockData();
            if (cauldronData.getLevel() == 1) {
                clickedBlock.setType(Material.CAULDRON);
            } else {
                cauldronData.setLevel(cauldronData.getLevel() - 1);
                clickedBlock.setBlockData(cauldronData);
            }

            ItemStack waterBottle;
            if (clickedBlock.getRelative(BlockFace.DOWN).getType() == Material.FIRE) {
                waterBottle = Items.PURIFIED_WATER.getItemStack();
            } else {
                waterBottle = Items.getBiomeBasedWaterBottle(clickedBlock.getBiome()).getItemStack();
            }

            player.playSound(clickedBlock.getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);

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

        // Click cauldron with water bottle to fill
        else if (clickedBlockType == Material.WATER_CAULDRON || clickedBlockType == Material.CAULDRON && Items.Tags.WATER_BOTTLE.isTagged(mainItem)) {
            if (clickedBlockType == Material.WATER_CAULDRON) {
                Levelled cauldronData = (Levelled) clickedBlock.getBlockData();
                if (cauldronData.getLevel() >= cauldronData.getMaximumLevel()) return;
                cauldronData.setLevel(cauldronData.getLevel() + 1);
                clickedBlock.setBlockData(cauldronData);
            } else {
                clickedBlock.setType(Material.WATER_CAULDRON);
            }
            event.setCancelled(true);
            mainItem.setAmount(0);
            player.getInventory().addItem(ItemType.GLASS_BOTTLE.createItemStack());
        }
    }

}
