package com.shanebeestudios.survival.listeners.item;

import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.events.ShootRecurvedBowEvent;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.item.items.tools.RecurvedBow;
import com.shanebeestudios.survival.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class RecurvedBowListener implements Listener {

    private final SurvivalPlugin plugin;
    private final Random random = new Random();

    public RecurvedBowListener(SurvivalPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (Utils.isCitizensNPC(player)) return;

        ItemStack mainItem = event.getBow();

        assert mainItem != null;
        if (!(Items.getFromStack(mainItem) instanceof RecurvedBow recurvedBow)) return;

        if (event.getForce() >= 3F) { // Max = 3
            final Entity arrow = event.getProjectile();
            final Vector velocity = player.getLocation().getDirection().add(new Vector(0, 0.025, 0)).multiply(4);
            // Call new ShootRecurvedBowEvent
            ShootRecurvedBowEvent shootEvent = new ShootRecurvedBowEvent(player, mainItem, recurvedBow);
            if (!shootEvent.callEvent()) {
                event.setCancelled(true);
                return;
            }

            arrow.setVelocity(velocity);

            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_BULLET_HURT, 0.5F, this.random.nextFloat() * 0.4F + 0.8F);

            Bukkit.getScheduler().runTask(this.plugin, new Runnable() {
                int times = 4;

                public void run() {
                    if (!arrow.isOnGround()) {
                        arrow.setVelocity(velocity);
                        if (times-- > 0)
                            Bukkit.getScheduler().runTaskLater(plugin, this, 5);
                    }
                }
            });
        } else {
            event.setCancelled(true);
            player.updateInventory();
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 0.5F, this.random.nextFloat() * 0.4F + 0.8F);
        }
    }

}
