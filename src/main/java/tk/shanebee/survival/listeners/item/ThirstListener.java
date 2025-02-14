package tk.shanebee.survival.listeners.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.events.ThirstLevelChangeEvent;
import tk.shanebee.survival.item.Items;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.managers.StatusManager;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThirstListener implements Listener {

    private final Survival plugin;
    private final Config config;
    private final Lang lang;
    private final PlayerManager playerManager;
    private final Random random = new Random();
    private final double drain;

    public ThirstListener(Survival plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
        this.lang = plugin.getLang();
        this.playerManager = plugin.getPlayerManager();
        this.drain = config.mechanics_thirst_drain_rate;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onConsume(PlayerItemConsumeEvent event) {
        if (event.isCancelled()) return;
        final Player player = event.getPlayer();
        PlayerData playerData = playerManager.getPlayerData(player);
        ItemStack item = event.getItem();
        int change = 0;
        switch (event.getItem().getType()) {
            case POTION:
                if (config.mechanics_thirst_purify_water) {
                    if (checkWaterBottle(item)) {
                        if (Items.DIRTY_WATER.is(item)) {
                            change = config.mechanics_thirst_rep_dirty_water;
                            Random rand = new Random();
                            if (rand.nextInt(10) + 1 <= 5) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 0));
                            }
                        } else if (Items.CLEAN_WATER.is(item)) {
                            change = config.mechanics_thirst_rep_clean_water;
                            Random rand = new Random();
                            if (rand.nextInt(10) + 1 <= 2) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 0));
                            }
                        } else if (Items.PURIFIED_WATER.is(item)) {
                            change = config.mechanics_thirst_rep_pure_water;
                        } else if (Items.COFFEE.is(item)) {
                            change = config.mechanics_thirst_rep_coffee;
                        } else if (Items.COLD_MILK.is(item)) {
                            change = config.mechanics_thirst_rep_cold_milk;
                        } else if (Items.HOT_MILK.is(item)) {
                            change = config.mechanics_thirst_rep_hot_milk;
                            player.damage(2);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 0));
                            Utils.sendColoredMsg(player, lang.hot_milk_drink);
                        } else if (Items.WATER_BOWL.is(item)) {
                            event.setCancelled(true);
                            change = handleWaterBowl(player);
                        } else {
                            change = config.mechanics_thirst_rep_other_water;
                        }
                    }
                } else {
                    change = config.mechanics_thirst_rep_water;
                }
                break;
            case MILK_BUCKET:
                change = config.mechanics_thirst_rep_milk_bucket;
                break;
            case MELON_SLICE:
                change = config.mechanics_thirst_rep_melon_slice;
                break;
            case MUSHROOM_STEW:
                change = config.mechanics_thirst_rep_mush_stew;
                break;
            case HONEY_BOTTLE:
                change = config.mechanics_thirst_rep_honey_bottle;
                break;
            case SUSPICIOUS_STEW:
                if (Items.SUSPICIOUS_MEAT.is(item)) {
                    // Remove the bowl from the player's hand afterward
                    BukkitRunnable runnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            PlayerInventory inv = player.getInventory();
                            if (inv.getItemInMainHand().getType() == Material.BOWL) {
                                inv.setItemInMainHand(null);
                            } else if (inv.getItemInOffHand().getType() == Material.BOWL) {
                                inv.setItemInOffHand(null);
                            }
                        }
                    };
                    runnable.runTaskLater(plugin, 1);
                    return;
                }
        }
        ThirstLevelChangeEvent thirstEvent = new ThirstLevelChangeEvent(player, change, playerData.getThirst() + change);
        Bukkit.getPluginManager().callEvent(thirstEvent);
        if (!thirstEvent.isCancelled()) {
            playerData.setThirst(playerData.getThirst() + change);
        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (!config.MECHANICS_STATUS_SCOREBOARD) {
                player.sendMessage(plugin.getPlayerManager().getHungerVisual(player).get(1) + plugin.getPlayerManager().getHungerVisual(player).get(2) + " " + plugin.getPlayerManager().getHungerVisual(player).get(0).toUpperCase());
                player.sendMessage(plugin.getPlayerManager().getThirstVisual(player).get(1) + plugin.getPlayerManager().getThirstVisual(player).get(2) + " " + plugin.getPlayerManager().getThirstVisual(player).get(0).toUpperCase());
            }
        }, 1L);
    }

    private int handleWaterBowl(Player player) {
        int change = config.mechanics_thirst_rep_water_bowl;
        player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL));
        if (config.mechanics_thirst_purify_water) {
            Random rand = new Random();
            if (rand.nextInt(10) + 1 <= 8) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 0));
            }
        }
        return change;
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


    @EventHandler //if player catches a water bottle/potion give them dirty water instead
    private void onFish(PlayerFishEvent event) {
        if (!config.mechanics_thirst_purify_water) return;
        if (event.isCancelled()) return;
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Entity caught = event.getCaught();
            if (caught instanceof Item item) {
                ItemStack stack = item.getItemStack();
                if (stack.getType() == Material.POTION && checkWaterBottle(stack)) {
                    item.setItemStack(Items.DIRTY_WATER.getItemStack());
                }
            }
        }
    }

    // This map is to tell if the player actually DIED before respawning
    // Using the portal in the end causes the respawn event to fire
    // when the player re-enters the overworld
    private final List<Player> HUNGER_CHANGE = new ArrayList<>();

    @EventHandler
    private void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (HUNGER_CHANGE.contains(player)) {
            HUNGER_CHANGE.remove(player);

            PlayerData playerData = playerManager.getPlayerData(player);
            int thirst = config.mechanics_thirst_respawn_amount;
            playerData.setThirst(thirst);
            playerManager.getPlayerData(player).setThirst(thirst);

            int hunger = config.MECHANICS_HUNGER_RESPAWN_AMOUNT;
            Bukkit.getScheduler().runTaskLater(plugin, () -> StatusManager.setHunger(player, hunger), 1);
        }
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!HUNGER_CHANGE.contains(player)) {
            HUNGER_CHANGE.add(player);
        }
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
