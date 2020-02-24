package tk.shanebee.survival.listeners.entity;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.managers.Items;

import java.util.List;
import java.util.Random;

public class EntityDeath implements Listener {

    private Config config;
    private final int SUSPICIOUS_MEAT_CHANCE;

    public EntityDeath(Survival plugin) {
        this.config = plugin.getSurvivalConfig();
        this.SUSPICIOUS_MEAT_CHANCE = Math.max(0, this.config.ENTITY_MECHANICS_SUSPICIOUS_MEAT_CHANCE);
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
        if (!this.config.ENTITY_MECHANICS_SUSPICIOUS_MEAT_ENABLED) return; // May need to move if we add more items to drop in the future
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        if (killer != null) {
            int random = new Random().nextInt(100) + 1;
            if (random > this.SUSPICIOUS_MEAT_CHANCE) return;
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
        while (items.iterator().hasNext()) {
            ItemStack item = items.iterator().next();
            if (item.getType() == Material.ROTTEN_FLESH) {
                items.remove(item);
            }
        }
        items.add(Items.SUSPICIOUS_MEAT.getItem());
    }

}
