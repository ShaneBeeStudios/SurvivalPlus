package com.shanebeestudios.survival.plugin.listeners.block;

import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.api.data.Permissions;
import com.shanebeestudios.survival.api.item.Items;
import com.shanebeestudios.survival.plugin.managers.MessageManager;
import com.shanebeestudios.survival.plugin.managers.MessageManager.MessageType;
import com.shanebeestudios.survival.api.util.BlockTags;
import com.shanebeestudios.survival.api.util.ItemUtils;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Random;

public class BlockPlaceListener implements Listener {

    private final Config config;
    private final MessageManager messageManager;
    private final Random random = new Random();

    public BlockPlaceListener(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
        this.messageManager = plugin.getMessageManager();
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        if (!config.survival_place_only_with_hammer) return;

        Player player = event.getPlayer();
        if (Permissions.BYPASS_REQUIRED_TOOLS.has(player)) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        ItemStack mainTool = player.getInventory().getItemInMainHand();
        ItemStack offTool = player.getInventory().getItemInOffHand();
        Block block = event.getBlock();

        if (BlockTags.REQUIRES_HAMMER.isTagged(block.getType())) return;

        if (Items.HAMMER.is(offTool)) {
            int chance_reduceDur = this.random.nextInt(10) + 1;
            if (chance_reduceDur == 1) {
                ItemUtils.setDurability(offTool, ItemUtils.getDurability(offTool) + 1);
            }

            if (ItemUtils.getDurability(offTool) >= offTool.getType().getMaxDurability()) {
                player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
                player.getInventory().setItemInOffHand(null);
            }
        } else if (Items.HAMMER.is(mainTool)) {
            int chance_reduceDur = this.random.nextInt(10) + 1;
            if (chance_reduceDur == 1) {
                ItemUtils.setDurability(mainTool, ((Damageable) mainTool.getItemMeta()).getDamage() + 1);
            }

            if (ItemUtils.getDurability(mainTool) >= mainTool.getType().getMaxDurability()) {
                player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
                player.getInventory().setItemInMainHand(null);
            }
        } else {
            event.setCancelled(true);
            player.updateInventory();
            this.messageManager.sendMessage(player, MessageType.REQUIRES_HAMMER);
        }
    }

}
