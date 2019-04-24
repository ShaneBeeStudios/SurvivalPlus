package tk.shanebee.survival.events;

import tk.shanebee.survival.managers.Items;
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

import java.util.Random;

public class Campfire implements Listener {

    // When placing a campfire, turn it off (Requiring a player to light it manually)
    @EventHandler
    public void onPlaceCampfire(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType() != Material.CAMPFIRE) return;
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (Items.compare(e.getItemInHand(), Items.CAMPFIRE)) {
            Block block = e.getBlock();
            Lightable camp = ((Lightable) block.getBlockData());
            camp.setLit(false);
            block.setBlockData(camp);
        } else {
            e.setCancelled(true);
        }
    }

    // Hit an unlit campfire with a stick to light it
    @EventHandler
    public void lightFire(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.CAMPFIRE) return;
        if (e.getItem() != null && e.getItem().getType() == Material.STICK) {
            Block block = e.getClickedBlock();
            Lightable camp = ((Lightable) block.getBlockData());
            if (camp.isLit()) return;
            e.setCancelled(true);

            int i = new Random().nextInt(20);
            if (i == 10) {
                camp.setLit(true);
                block.setBlockData(camp);

                ItemStack tool = e.getItem();
                tool.setAmount(tool.getAmount() - 1);
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
            }
        }
    }

    // Randomly put out the fire when cooking food
    @EventHandler
    public void fireFinishedCooking(BlockCookEvent e) {
        if (e.getBlock().getType() != Material.CAMPFIRE) return;
        int i = new Random().nextInt(8);

        if (i == 5) {
            Block block = e.getBlock();
            Lightable camp = ((Lightable) block.getBlockData());
            camp.setLit(false);
            block.setBlockData(camp);
            block.getLocation().getWorld().playSound(block.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
        }
    }

}
