package tk.shanebee.survival.listeners.item;

import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.item.Items;


public class WaterBottleListener implements Listener {

    private final Config config;

    public WaterBottleListener(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
    }

    @EventHandler // Fill a bottle resulting in a water bottle
    private void onFillWaterBottle(PlayerInteractEvent event) {
        if (!config.mechanics_thirst_purify_water) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        EquipmentSlot hand = event.getHand();
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;
        if (item == null || hand == null || item.getType() != Material.GLASS_BOTTLE) return;

        Block targetBlock = player.getTargetBlockExact(5, FluidCollisionMode.ALWAYS);
        if (targetBlock == null || !isWaterBlock(targetBlock)) return;
        event.setCancelled(true);

        ItemStack waterBottle = Items.getBiomeBasedWaterBottle(targetBlock.getBiome()).getItemStack();
        if (item.getAmount() > 1) {
            if (!player.getInventory().addItem(waterBottle).isEmpty()) {
                player.getWorld().dropItem(player.getLocation(), waterBottle);
            }
            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
                item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItem(hand, waterBottle);
        }
    }

    private boolean isWaterBlock(Block block) {
        if (block.getType() == Material.WATER) {
            return true;
        }
        return block.getBlockData() instanceof Waterlogged waterlogged && waterlogged.isWaterlogged();
    }

}
