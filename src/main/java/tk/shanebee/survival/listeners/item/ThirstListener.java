package tk.shanebee.survival.listeners.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.events.ThirstLevelChangeEvent;
import tk.shanebee.survival.item.Item;
import tk.shanebee.survival.item.Items;
import tk.shanebee.survival.item.items.drinks.DrinkItem;
import tk.shanebee.survival.managers.PlayerManager;

import java.util.Random;

public class ThirstListener implements Listener {

    private final SurvivalPlugin plugin;
    private final Config config;
    private final PlayerManager playerManager;
    private final Random random = new Random();
    private final double drain;

    public ThirstListener(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
        this.playerManager = plugin.getPlayerManager();
        this.drain = config.mechanics_thirst_drain_rate;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onConsume(PlayerItemConsumeEvent event) {
        if (event.isCancelled()) return;
        final Player player = event.getPlayer();
        PlayerData playerData = playerManager.getPlayerData(player);
        ItemStack itemStack = event.getItem();
        double change = 0;
        Item item = Items.getFromStack(itemStack);
        if (item instanceof DrinkItem drinkItem) {
            change = drinkItem.getThirstLevel();
        } else {
            switch (event.getItem().getType()) {
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
        ThirstLevelChangeEvent thirstEvent = new ThirstLevelChangeEvent(player, change, playerData.getThirst() + change);
        if (thirstEvent.callEvent()) {
            playerData.setThirst(playerData.getThirst() + change);
        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (!config.mechanics_status_scoreboard) {
                player.sendMessage(plugin.getPlayerManager().getHungerVisual(player).get(1) + plugin.getPlayerManager().getHungerVisual(player).get(2) + " " + plugin.getPlayerManager().getHungerVisual(player).get(0).toUpperCase());
                player.sendMessage(plugin.getPlayerManager().getThirstVisual(player).get(1) + plugin.getPlayerManager().getThirstVisual(player).get(2) + " " + plugin.getPlayerManager().getThirstVisual(player).get(0).toUpperCase());
            }
        }, 1L);
    }

    @EventHandler
    private void onExhaustionReached(EntityExhaustionEvent event) {
        Player player = (Player) event.getEntity();
        if (player.getExhaustion() >= 3.97) { // highest amount the event gets to (sometimes)
            PlayerData playerData = this.playerManager.getPlayerData(player);

            int change = this.random.nextDouble() <= this.drain ? 1 : 0;

            // Prevent calling thirst event if there is no change
            if (change == 0) return;

            // Call thirst level change event
            ThirstLevelChangeEvent thirstEvent = new ThirstLevelChangeEvent(player, change, playerData.getThirst() - change);
            if (thirstEvent.callEvent()) {
                playerData.increaseThirst(-change);
            }
        }
    }


    @EventHandler // if player catches a water bottle/potion give them dirty water instead
    private void onFish(PlayerFishEvent event) {
        if (event.isCancelled() || !config.mechanics_thirst_purify_water) return;
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;

        Entity caught = event.getCaught();
        if (caught instanceof org.bukkit.entity.Item item) {
            ItemStack stack = item.getItemStack();
            if (stack.getType() == Material.POTION && checkWaterBottle(stack)) {
                item.setItemStack(Items.getBiomeBasedWaterBottle(caught.getLocation().getBlock().getBiome()).getItemStack());
            }
        }
    }

    @EventHandler
    private void onRespawn(PlayerRespawnEvent event) {
        if (event.getRespawnReason() != PlayerRespawnEvent.RespawnReason.DEATH) return;
        Player player = event.getPlayer();
        PlayerData playerData = playerManager.getPlayerData(player);
        double thirst = config.mechanics_thirst_respawn_amount;
        playerData.setThirst(thirst);
        playerManager.getPlayerData(player).setThirst(thirst);

        double hunger = this.config.mechanics_hunger_respawn_amount;
        Bukkit.getScheduler().runTaskLater(plugin, () -> playerData.setHunger(hunger), 1);
    }

    private boolean checkWaterBottle(ItemStack bottle) {
        ItemMeta meta = bottle.getItemMeta();
        assert meta != null;
        return switch (((PotionMeta) meta).getBasePotionType()) {
            case WATER, MUNDANE, THICK, AWKWARD -> true;
            case null, default -> false;
        };
    }

}
