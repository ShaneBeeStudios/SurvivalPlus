package tk.shanebee.survival.listeners.item;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitScheduler;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.events.WaterBowlFillEvent;
import tk.shanebee.survival.item.Items;

public class WaterBowl implements Listener {

    private final Survival plugin;
    private final boolean thirstEnabled;
    private final boolean clayEnabled;
    private final BukkitScheduler scheduler = Bukkit.getScheduler();

    public WaterBowl(Survival plugin) {
        this.plugin = plugin;
        this.thirstEnabled = plugin.getSurvivalConfig().mechanics_thirst_enabled;
        this.clayEnabled = plugin.getSurvivalConfig().RECIPES_CLAY;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onConsume(PlayerItemConsumeEvent event) {
        if (this.thirstEnabled || event.isCancelled()) return;

        if (Items.WATER_BOWL.is(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onDrop(ItemSpawnEvent event) {
        if (event.isCancelled()) return;
        if (!this.thirstEnabled && !this.clayEnabled) return;

        final org.bukkit.entity.Item itemDrop = event.getEntity();
        if (itemDrop.getItemStack().getType() == Material.BOWL) {
            this.scheduler.runTaskLater(this.plugin, () -> {
                Location itemLocation = itemDrop.getLocation();
                if (itemLocation.getBlock().getType() != Material.WATER) return;

                WaterBowlFillEvent bowlFillEvent = new WaterBowlFillEvent(itemDrop.getItemStack());
                if (!bowlFillEvent.callEvent()) return;

                World world = itemDrop.getWorld();
                int amount = itemDrop.getItemStack().getAmount();
                itemDrop.remove();
                for (int i = 0; i < amount; i++) {
                    world.dropItem(itemLocation, Items.WATER_BOWL.getItemStack());
                }
            }, 20);
        }
    }

}
