package com.shanebeestudios.survival.plugin.listeners.item;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Lightable;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.api.gui.FireStrikerGUI;
import com.shanebeestudios.survival.api.item.Items;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FirestrikerListener implements Listener {

    private final Map<InventoryView, FireStrikerGUI> firestrikerViewMap = new HashMap<>();
    private final Random random = new Random();

    @SuppressWarnings("unused")
    public FirestrikerListener(SurvivalPlugin plugin) {
    }

    @EventHandler
    private void onItemClick(PlayerInteractEvent event) {
        if (event.hasItem()) {
            Player player = event.getPlayer();
            ItemStack tool = event.getItem();
            Action action = event.getAction();
            EquipmentSlot hand = event.getHand();
            if (tool == null || hand == null) return;

            if (Items.FIRESTRIKER.is(tool)) {
                if (player.isSneaking() && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
                    event.setCancelled(true);
                    FireStrikerGUI fireStriker = FireStrikerGUI.create(player, tool.clone());
                    if (fireStriker != null) {
                        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_AXE_WAX_OFF, 5.0F, this.random.nextFloat() * 0.4F + 0.8F);
                        this.firestrikerViewMap.put(fireStriker.getFurnaceView(), fireStriker);
                        fireStriker.open();
                        tool.setAmount(0);
                        player.updateInventory();
                    } else {
                        tool.setAmount(0);
                        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 5.0F, this.random.nextFloat() * 0.4F + 0.8F);
                    }
                } else if (action == Action.RIGHT_CLICK_BLOCK) {
                    Block clickedBlock = event.getClickedBlock();
                    if (clickedBlock == null) return;

                    Material blockType = clickedBlock.getType();
                    switch (blockType) {
                        case ENCHANTING_TABLE:
                        case ANVIL:
                        case BREWING_STAND:
                        case TRAPPED_CHEST:
                        case CHEST:
                        case NOTE_BLOCK:
                        case FURNACE:
                        case HOPPER:
                        case CRAFTING_TABLE:
                        case DROPPER:
                        case DISPENSER:
                        case REDSTONE_WALL_TORCH:
                        case REDSTONE_TORCH:
                            return;
                    }
                    if (Tag.BEDS.isTagged(blockType)) return;
                    if (Tag.FENCE_GATES.isTagged(blockType)) return;
                    if (Tag.DOORS.isTagged(blockType)) return;
                    if (Tag.TRAPDOORS.isTagged(blockType)) return;
                    if (blockType == Material.CAMPFIRE || blockType == Material.SOUL_CAMPFIRE) {
                        Lightable campfire = ((Lightable) clickedBlock.getBlockData());
                        if (!campfire.isLit()) {
                            player.swingHand(hand);
                            campfire.setLit(true);
                            clickedBlock.setBlockData(campfire);
                            damageItem(player, tool);
                        }
                        return;
                    }
                    Location loc = clickedBlock.getRelative(event.getBlockFace()).getLocation();
                    if (ignite(player, loc)) {
                        player.swingHand(hand);
                        damageItem(player, tool);
                    }
                }
            }
        }
    }

    private boolean ignite(Player igniter, Location loc) {
        Random rand = new Random();

        loc.add(0.5, 0.5, 0.5);

        BlockIgniteEvent igniteEvent = new BlockIgniteEvent(loc.getBlock(), IgniteCause.FLINT_AND_STEEL, igniter);
        if (!igniteEvent.callEvent()) {
            return false;
        }

        BlockState blockState = loc.getBlock().getState();

        BlockPlaceEvent placeEvent = new BlockPlaceEvent(loc.getBlock(),
            blockState, loc.getBlock(), igniter.getInventory().getItemInMainHand(), igniter, true, EquipmentSlot.HAND);
        Bukkit.getServer().getPluginManager().callEvent(placeEvent);

        if (placeEvent.isCancelled() || !placeEvent.canBuild()) {
            placeEvent.getBlockPlaced().getState().setType(Material.AIR);
            return false;
        }


        loc.getWorld().playSound(loc, Sound.ITEM_FLINTANDSTEEL_USE, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
        loc.getBlock().setType(Material.FIRE);

        return true;
    }

    private void damageItem(Player player, ItemStack itemStack) {
        if (player.damageItemStack(itemStack, 1).isEmpty()) {
            player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 5.0F, this.random.nextFloat() * 0.4F + 0.8F);
        }
    }

    @EventHandler
    private void onCloseInventory(InventoryCloseEvent event) {
        HumanEntity player = event.getPlayer();
        InventoryView openInventory = player.getOpenInventory();
        FireStrikerGUI fireStrikerGUI = this.firestrikerViewMap.get(openInventory);
        if (fireStrikerGUI != null) {
            fireStrikerGUI.close();
            this.firestrikerViewMap.remove(openInventory);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onAttack(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getDamager() instanceof Player player && event.getEntity() instanceof LivingEntity && event.getCause() == DamageCause.ENTITY_ATTACK) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (Items.FIRESTRIKER.is(itemStack)) {
                player.damageItemStack(itemStack, 1);
            }
        }
    }

}
