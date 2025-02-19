package com.shanebeestudios.survival.listeners.item;

import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.item.Items;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;


@SuppressWarnings("UnstableApiUsage")
public class WaterPurifiedListener implements Listener {

    @SuppressWarnings("unused")
    public WaterPurifiedListener(SurvivalPlugin plugin) {
    }

    @EventHandler // Fill a bottle resulting in a water bottle
    private void onFillWaterBottle(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        EquipmentSlot hand = event.getHand();
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;
        if (item == null || hand == null || item.getType() != Material.GLASS_BOTTLE) return;

        Block targetBlock = player.getTargetBlockExact(5, FluidCollisionMode.ALWAYS);
        if (targetBlock == null || !isWaterBlock(targetBlock)) return;
        event.setCancelled(true);

        ItemStack waterBottle = Items.getBiomeBasedWaterBottle(targetBlock.getBiome()).getItemStack();
        if (item.getAmount() > 1) {
            if (!player.getInventory().addItem(waterBottle).isEmpty()) {
                player.getWorld().dropItem(player.getLocation(), waterBottle);
            }
            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
                item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItem(hand, waterBottle);
        }
    }

    @EventHandler // if player catches a water bottle/potion give them dirty water instead
    private void onFish(PlayerFishEvent event) {
        if (event.isCancelled()) return;
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;

        Entity caught = event.getCaught();
        if (caught instanceof org.bukkit.entity.Item item) {
            ItemStack stack = item.getItemStack();
            if (stack.getType() == Material.POTION && checkWaterBottle(stack)) {
                item.setItemStack(Items.getBiomeBasedWaterBottle(caught.getLocation().getBlock().getBiome()).getItemStack());
            }
        }
    }

    private boolean checkWaterBottle(ItemStack bottle) {
        ItemMeta meta = bottle.getItemMeta();
        assert meta != null;
        return switch (((PotionMeta) meta).getBasePotionType()) {
            case WATER, MUNDANE, THICK, AWKWARD -> true;
            case null, default -> false;
        };
    }

    private boolean isWaterBlock(Block block) {
        if (block.getType() == Material.WATER) {
            return true;
        }
        return block.getBlockData() instanceof Waterlogged waterlogged && waterlogged.isWaterlogged();
    }

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
