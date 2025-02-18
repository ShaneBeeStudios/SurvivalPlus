package com.shanebeestudios.survival.listeners.item;

import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.managers.MessageManager;
import com.shanebeestudios.survival.managers.MessageManager.MessageType;
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

public class BowListener implements Listener {

    private final MessageManager messageManager;

    public BowListener(SurvivalPlugin plugin) {
        this.messageManager = plugin.getMessageManager();
    }

    @EventHandler
    private void onShootWithoutArrows(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            if (event.getBow() != null && mainHand.getType() == event.getBow().getType()) {
                if (SurvivalPlugin.getInstance().getPlayerManager().isArrowOffHand(player)) {
                    event.setCancelled(false);
                } else {
                    if (mainHand.getType() != Material.CROSSBOW) {
                        event.setCancelled(true);
                        this.messageManager.sendMessage(player, MessageType.ARROWS_OFFHAND);
                        player.updateInventory();
                    }
                }
            } else {
                event.setCancelled(true);
                this.messageManager.sendMessage(player, MessageType.BOW_MAIN_HAND);
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
            if (mainHand.getItemMeta() != null && ((CrossbowMeta) mainHand.getItemMeta()).hasChargedProjectiles())
                return;
            if (!SurvivalPlugin.getInstance().getPlayerManager().isArrowOffHand(player)) {
                event.setCancelled(true);
                this.messageManager.sendMessage(player, MessageType.ARROWS_OFFHAND_CROSSBOW);
            }
        } else if (offHand.getType() == Material.CROSSBOW) {
            if (event.getHand() == EquipmentSlot.HAND) return;
            event.setCancelled(true);
            this.messageManager.sendMessage(player, MessageType.BOW_MAIN_HAND);
        }
    }

}
