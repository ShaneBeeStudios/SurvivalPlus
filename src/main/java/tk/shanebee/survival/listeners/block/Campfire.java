package tk.shanebee.survival.listeners.block;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.item.Items;

import java.util.Random;

public class Campfire implements Listener {

    private final Survival plugin;

    public Campfire(Survival plugin) {
        this.plugin = plugin;
    }

    // When placing a campfire, turn it off (Requiring a player to light it manually)
    @EventHandler
    private void onPlaceCampfire(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() != Material.CAMPFIRE) return;
        if (Items.CAMPFIRE.is(event.getItemInHand())) {
            Lightable camp = ((Lightable) event.getBlock().getBlockData());
            camp.setLit(false);
            event.getBlock().setBlockData(camp);

        } else {
            if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
            event.setCancelled(true);
        }
    }

    // Hit an unlit campfire with a stick to light it
    @EventHandler
    private void lightFire(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() == Material.CAMPFIRE) {
            if (event.getItem() != null && event.getItem().getType() == Material.STICK) {
                Block block = event.getClickedBlock();
                Lightable camp = ((Lightable) block.getBlockData());
                if (camp.isLit()) return;
                event.setCancelled(true);
                int i = new Random().nextInt(20);
                if (i == 10) {
                    camp.setLit(true);
                    block.setBlockData(camp);
                    ItemStack tool = event.getItem();
                    tool.setAmount(tool.getAmount() - 1);
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_GENERIC_BURN, 1, 1), 1);

                }
            }
        }
    }

    // Randomly put out the fire when cooking food
    @EventHandler
    private void fireFinishedCooking(BlockCookEvent event) {
        if (event.getBlock().getType() != Material.CAMPFIRE) return;
        int i = new Random().nextInt(8);

        if (i == 5) {
            Block block = event.getBlock();
            Lightable camp = ((Lightable) block.getBlockData());
            camp.setLit(false);
            block.setBlockData(camp);
            block.getLocation().getWorld().playSound(block.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
        }
    }

}
