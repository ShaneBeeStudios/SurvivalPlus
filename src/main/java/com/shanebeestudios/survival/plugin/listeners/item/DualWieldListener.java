package com.shanebeestudios.survival.plugin.listeners.item;

import com.shanebeestudios.survival.api.data.PlayerData;
import com.shanebeestudios.survival.api.data.Stat;
import com.shanebeestudios.survival.api.item.Item;
import com.shanebeestudios.survival.api.item.Items;
import com.shanebeestudios.survival.api.registry.ItemTags;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.managers.MessageManager;
import com.shanebeestudios.survival.plugin.managers.MessageManager.MessageType;
import com.shanebeestudios.survival.plugin.managers.PlayerManager;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.Nullable;

public class DualWieldListener implements Listener {

    private final PlayerManager playerManager;
    private final MessageManager messageManager;

    public DualWieldListener(SurvivalPlugin plugin) {
        this.playerManager = plugin.getPlayerManager();
        this.messageManager = plugin.getMessageManager();
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        checkDuelWield(player, inventory.getItemInMainHand(), inventory.getItemInOffHand());
    }

    @EventHandler
    private void onPlayerInvSlotChange(PlayerInventorySlotChangeEvent event) {
        int slot = event.getSlot();
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        if (slot == player.getInventory().getHeldItemSlot()) {
            checkDuelWield(player, event.getNewItemStack(), inventory.getItemInOffHand());
        } else if (slot == 40) {
            checkDuelWield(player, inventory.getItemInMainHand(), event.getNewItemStack());
        }
    }

    @EventHandler
    private void onPlayerSwapHandItem(PlayerSwapHandItemsEvent event) {
        checkDuelWield(event.getPlayer(), event.getMainHandItem(), event.getOffHandItem());
    }

    @EventHandler
    private void onPlayerChangeHand(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        checkDuelWield(player, inventory.getItem(event.getNewSlot()), inventory.getItemInOffHand());
    }

    // Prevent dual wielding
    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            PlayerData playerData = this.playerManager.getPlayerData(player);
            if (playerData.getStat(Stat.DUAL_WIELD) > 0) {
                event.setCancelled(true);
                PlayerInventory inventory = player.getInventory();
                Item main = Items.getFromStack(inventory.getItemInMainHand());
                Item off = Items.getFromStack(inventory.getItemInOffHand());
                String name = "this item";
                if (main != null) {
                    name = main.getName();
                } else if (off != null) {
                    name = off.getName();
                }
                this.messageManager.sendMessage(player, MessageType.DUAL_WIELD_NO, name);
            }
        }
    }

    private void checkDuelWield(Player player, @Nullable ItemStack mainStack, @Nullable ItemStack offStack) {
        Item mainItem = mainStack != null ? Items.getFromStack(mainStack) : null;
        Item offItem = offStack != null ? Items.getFromStack(offStack) : null;

        int duelWield = 0;
        if (mainItem != null && mainItem.isPreventDuelWield() &&
            offStack != null && ItemTags.PREVENT_DUAL_WIELD.isTagged(offStack.getType())) {
            duelWield = 1;
        } else if (offItem != null && offItem.isPreventDuelWield() &&
            mainStack != null && ItemTags.PREVENT_DUAL_WIELD.isTagged(mainStack.getType())) {
            duelWield = 1;
        }
        PlayerData playerData = this.playerManager.getPlayerData(player);
        playerData.setStat(Stat.DUAL_WIELD, duelWield);
    }

}
