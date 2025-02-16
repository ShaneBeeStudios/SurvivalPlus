package tk.shanebee.survival.listeners.entity;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.item.Items;

import java.util.Random;

public class ChickenSpawn implements Listener {

    private final Random random = new Random();
    private final int maxEggs;
    private final boolean alwaysBaby;
    private final int babyTicks;

    public ChickenSpawn(SurvivalPlugin plugin) {
        Config config = plugin.getSurvivalConfig();
        this.maxEggs = config.ENTITY_MECHANICS_CHICKEN_BREEDING_MAX_EGGS;
        this.alwaysBaby = config.ENTITY_MECHANICS_CHICKEN_BREEDING_ALWAYS_BABY;
        this.babyTicks = config.ENTITY_MECHANICS_CHICKEN_BREEDING_BABY_TICKS;
    }

    @EventHandler
    private void onChickenSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.CHICKEN) {
            SpawnReason reason = event.getSpawnReason();
            if (reason == SpawnReason.BREEDING) {
                event.setCancelled(true);
                Location loc = event.getLocation();
                World world = loc.getWorld();
                assert world != null;
                world.dropItem(loc, getEgg());
                world.playSound(loc, Sound.ENTITY_CHICKEN_EGG, 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            } else if (reason == SpawnReason.EGG) {
                Chicken chicken = ((Chicken) event.getEntity());
                if (this.alwaysBaby) {
                    chicken.setBaby();
                    chicken.setAge(-this.babyTicks);
                } else if (!chicken.isAdult()) {
                    chicken.setAge(-this.babyTicks);
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
        int ran = maxEggs > 1 ? this.random.nextInt(maxEggs) + 1 : 1;
        return Items.BREEDING_EGG.getItemStack(ran);
    }

}
