package com.shanebeestudios.survival.listeners.entity;

import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.managers.LootManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Merchant;

public class MerchantTrades implements Listener {

    private final LootManager lootManager;

    public MerchantTrades(SurvivalPlugin plugin) {
        this.lootManager = plugin.getLootManager();
    }

    @EventHandler
    private void onClickVillager(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof Merchant merchant) {
            this.lootManager.updateMerchant(merchant);
        }
    }

}
