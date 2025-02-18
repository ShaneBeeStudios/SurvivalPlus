package com.shanebeestudios.survival.listeners.entity;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.item.Items;

import java.util.List;
import java.util.Random;

public class EntityDeath implements Listener {

    private final Config config;

    public EntityDeath(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
        if (!this.config.entity_mechanics_suspicious_meat_enabled) return; // May need to move if we add more items to drop in the future
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        if (killer != null) {
            int random = new Random().nextInt(100) + 1;
            if (random > this.config.entity_mechanics_suspicious_meat_chance) return;
            switch (entity.getType()) {
                case ZOMBIE:
                case DROWNED:
                case HUSK:
                case ZOMBIE_VILLAGER:
                case ZOMBIE_HORSE:
                    replaceDrops(event.getDrops());
            }
        }
    }

    private void replaceDrops(List<ItemStack> items) {
        items.removeIf(item -> item.getType() == Material.ROTTEN_FLESH);
        items.add(Items.SUSPICIOUS_MEAT.getItemStack());
    }

}
