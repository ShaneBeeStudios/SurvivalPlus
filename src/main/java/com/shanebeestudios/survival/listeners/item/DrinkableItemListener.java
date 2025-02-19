package com.shanebeestudios.survival.listeners.item;

import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.data.Permissions;
import com.shanebeestudios.survival.data.PlayerData;
import com.shanebeestudios.survival.events.ThirstLevelChangeEvent;
import com.shanebeestudios.survival.item.Item;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.item.items.drinks.DrinkItem;
import com.shanebeestudios.survival.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class DrinkableItemListener implements Listener {

    private final SurvivalPlugin plugin;
    private final Config config;
    private final PlayerManager playerManager;

    public DrinkableItemListener(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
        this.playerManager = plugin.getPlayerManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onConsume(PlayerItemConsumeEvent event) {
        final Player player = event.getPlayer();
        if (Permissions.BYPASS_STAT_THIRST.has(player)) return;

        PlayerData playerData = playerManager.getPlayerData(player);
        ItemStack itemStack = event.getItem();
        double change = 0;
        Item item = Items.getFromStack(itemStack);
        if (item instanceof DrinkItem drinkItem) {
            change = drinkItem.getThirstLevel();
        } else {
            switch (event.getItem().getType()) {
                case APPLE:
                    change = this.config.mechanics_thirst_rep_apple;
                    break;
                case BEETROOT_SOUP:
                    change = this.config.mechanics_thirst_rep_beetroot_soup;
                    break;
                case POTION:
                    if (this.config.mechanics_thirst_purify_water) {
                        change = this.config.mechanics_thirst_rep_other_water;
                    } else {
                        change = this.config.mechanics_thirst_rep_water;
                    }
                    break;
                case MILK_BUCKET:
                    change = this.config.mechanics_thirst_rep_milk_bucket;
                    break;
                case MELON_SLICE:
                    change = this.config.mechanics_thirst_rep_melon_slice;
                    break;
                case MUSHROOM_STEW:
                    change = this.config.mechanics_thirst_rep_mush_stew;
                    break;
                case HONEY_BOTTLE:
                    change = this.config.mechanics_thirst_rep_honey_bottle;
                    break;
            }
        }
        if (change <= 0) return;

        ThirstLevelChangeEvent thirstEvent = new ThirstLevelChangeEvent(player, change, playerData.getThirst() + change);
        if (thirstEvent.callEvent()) {
            playerData.setThirst(playerData.getThirst() + change);
        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (!this.config.mechanics_status_scoreboard) {
                player.sendMessage(this.plugin.getPlayerManager().getHungerVisual(player).get(1) + this.plugin.getPlayerManager().getHungerVisual(player).get(2) + " " + plugin.getPlayerManager().getHungerVisual(player).get(0).toUpperCase());
                player.sendMessage(this.plugin.getPlayerManager().getThirstVisual(player).get(1) + this.plugin.getPlayerManager().getThirstVisual(player).get(2) + " " + plugin.getPlayerManager().getThirstVisual(player).get(0).toUpperCase());
            }
        }, 1L);
    }

}
