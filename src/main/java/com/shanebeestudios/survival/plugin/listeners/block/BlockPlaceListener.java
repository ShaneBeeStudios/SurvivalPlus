package com.shanebeestudios.survival.plugin.listeners.block;

import com.shanebeestudios.survival.api.data.Permissions;
import com.shanebeestudios.survival.api.item.Items;
import com.shanebeestudios.survival.api.util.BlockTags;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.plugin.managers.MessageManager;
import com.shanebeestudios.survival.plugin.managers.MessageManager.MessageType;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {

    private final Config config;
    private final MessageManager messageManager;

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
        if (!BlockTags.REQUIRES_HAMMER.isTagged(event.getBlock().getType())) return;

        ItemStack offTool = player.getInventory().getItemInOffHand();
        if (Items.HAMMER.is(offTool)) {
            offTool.damage(1, player);
        } else {
            event.setCancelled(true);
            player.updateInventory();
            this.messageManager.sendMessage(player, MessageType.REQUIRES_HAMMER);
        }
    }

}
