package com.shanebeestudios.survival.listeners.block;

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
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.config.Lang;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.util.BlockTags;
import com.shanebeestudios.survival.util.ItemUtils;
import com.shanebeestudios.survival.util.Utils;

import java.util.Random;

public class BlockPlace implements Listener {

    private final Config config;
    private final Lang lang;

    public BlockPlace(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
        this.lang = plugin.getLang();
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();

        ItemStack mainTool = player.getInventory().getItemInMainHand();
        ItemStack offTool = player.getInventory().getItemInOffHand();

        Block block = event.getBlock();

        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            if (config.survival_place_only_with_hammer) {
                if (BlockTags.REQUIRES_HAMMER.isTagged(block.getType())) {
                    if (Items.HAMMER.is(offTool)) {
                        Random rand = new Random();
                        int chance_reduceDur = rand.nextInt(10) + 1;
                        if (chance_reduceDur == 1) {
                            ItemUtils.setDurability(offTool, ItemUtils.getDurability(offTool) + 1);
                        }

                        if (ItemUtils.getDurability(offTool) >= offTool.getType().getMaxDurability()) {
                            player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                            player.getInventory().setItemInOffHand(null);
                        }
                    } else if (Items.HAMMER.is(mainTool)) {
                        Random rand = new Random();
                        int chance_reduceDur = rand.nextInt(10) + 1;
                        if (chance_reduceDur == 1) {
                            ItemUtils.setDurability(mainTool, ((Damageable) mainTool.getItemMeta()).getDamage() + 1);
                        }

                        if (ItemUtils.getDurability(mainTool) >= mainTool.getType().getMaxDurability()) {
                            player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                            player.getInventory().setItemInMainHand(null);
                        }
                    } else {
                        event.setCancelled(true);
                        player.updateInventory();
                        Utils.sendColoredMini(player, "<red>" + lang.task_must_use_hammer);
                    }
                }
            }
        }
    }

}
