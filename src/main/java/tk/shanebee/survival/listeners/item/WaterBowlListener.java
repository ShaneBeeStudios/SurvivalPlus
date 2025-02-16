package tk.shanebee.survival.listeners.item;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.events.WaterBowlFillEvent;
import tk.shanebee.survival.item.Items;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

public class WaterBowlListener implements Listener {

    private final SurvivalPlugin plugin;
    private final boolean thirstEnabled;
    private final boolean clayEnabled;
    private final BukkitScheduler scheduler = Bukkit.getScheduler();
    private final Random random = new Random();

    public WaterBowlListener(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.thirstEnabled = plugin.getSurvivalConfig().mechanics_thirst_enabled;
        this.clayEnabled = plugin.getSurvivalConfig().recipes_clay;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onConsume(PlayerItemConsumeEvent event) {
        if (this.thirstEnabled || event.isCancelled()) return;

        if (Items.WATER_BOWL.is(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onExtinguishCampfire(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block clickedBlock = event.getClickedBlock();
        ItemStack tool = event.getItem();
        EquipmentSlot hand = event.getHand();
        Player player = event.getPlayer();

        if (clickedBlock == null) return;
        if (!Tag.CAMPFIRES.isTagged(clickedBlock.getType())) return;
        if (tool == null || !Items.WATER_BOWL.is(tool) || hand == null) return;

        if (!(clickedBlock.getBlockData() instanceof Lightable lightable)) return;
        if (!lightable.isLit()) return;

        lightable.setLit(false);
        clickedBlock.setBlockData(lightable);
        player.swingHand(hand);
        player.getInventory().setItem(hand, new ItemStack(Material.BOWL));
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
        Utils.spawnParticle(clickedBlock.getLocation().add(0.5, 0.5, 0.5), Particle.FALLING_WATER, 100, 0.25, 0.2, 0.25);
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
