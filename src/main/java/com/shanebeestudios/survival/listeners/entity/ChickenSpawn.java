package com.shanebeestudios.survival.listeners.entity;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Chicken;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.item.Items;

import java.util.Random;

public class ChickenSpawn implements Listener {

    private final Config config;
    private final Random random = new Random();

    public ChickenSpawn(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
    }

    @EventHandler
    private void onChickenSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Chicken chicken) {
            SpawnReason reason = event.getSpawnReason();
            if (reason == SpawnReason.BREEDING) {
                event.setCancelled(true);
                Location loc = event.getLocation();
                World world = loc.getWorld();
                assert world != null;
                world.dropItem(loc, getEgg());
                world.playSound(loc, Sound.ENTITY_CHICKEN_EGG, 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            } else if (reason == SpawnReason.EGG) {
                int babyTicks = this.config.entity_mechanics_chicken_breeding_baby_ticks;
                if (this.config.entity_mechanics_chicken_breeding_always_baby) {
                    chicken.setBaby();
                    chicken.setAge(-babyTicks);
                } else if (!chicken.isAdult()) {
                    chicken.setAge(-babyTicks);
                }
            }
        }
    }

    @EventHandler
    private void onEggThrown(PlayerEggThrowEvent event) {
        if (Items.BREEDING_EGG.is(event.getEgg().getItem())) {
            event.setHatching(true);
            event.setNumHatches((byte) 1);
        }
    }

    private ItemStack getEgg() {
        int maxEggs = this.config.entity_mechanics_chicken_breeding_max_eggs;
        int ran = maxEggs > 1 ? this.random.nextInt(maxEggs) + 1 : 1;
        return Items.BREEDING_EGG.getItemStack(ran);
    }

}
