package com.shanebeestudios.survival.plugin.listeners.block;

import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.managers.LootManager;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.InventoryHolder;

public class LootTableListener implements Listener {

    private final LootManager lootManager;

    public LootTableListener(SurvivalPlugin plugin) {
        this.lootManager = plugin.getLootManager();
    }

    @EventHandler
    private void onLootGenerate(LootGenerateEvent event) {
        InventoryHolder holder = event.getInventoryHolder();
        if (holder instanceof Container) {
            this.lootManager.updateLoot(event.getLoot());
        }
    }

}
