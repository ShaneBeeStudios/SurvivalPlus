package tk.shanebee.survival.listeners.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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

public class Consume implements Listener {

	private final Survival plugin;
	private final Config config;
	private final Lang lang;
	private final PlayerManager playerManager;

	public Consume(Survival plugin) {
		this.plugin = plugin;
		this.config = plugin.getSurvivalConfig();
		this.lang = plugin.getLang();
		this.playerManager = plugin.getPlayerManager();
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
				if (config.MECHANICS_THIRST_PURIFY_WATER) {
					if (checkWaterBottle(item)) {
						if (Items.DIRTY_WATER.is(item)) {
							change = config.MECHANICS_THIRST_REP_DIRTY_WATER;
							Random rand = new Random();
							if (rand.nextInt(10) + 1 <= 5) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
								player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 0));
							}
						} else if (Items.CLEAN_WATER.is(item)) {
							change = config.MECHANICS_THIRST_REP_CLEAN_WATER;
							Random rand = new Random();
							if (rand.nextInt(10) + 1 <= 2) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
								player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 0));
							}
						} else if (Items.PURIFIED_WATER.is(item)) {
							change = config.MECHANICS_THIRST_REP_PURE_WATER;
						} else if (Items.COFFEE.is(item)) {
							change = config.MECHANICS_THIRST_REP_COFFEE;
						} else if (Items.COLD_MILK.is(item)) {
							change = config.MECHANICS_THIRST_REP_COLD_MILK;
						} else if (Items.HOT_MILK.is(item)) {
							change = config.MECHANICS_THIRST_REP_HOT_MILK;
							player.damage(2);
							player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 0));
							Utils.sendColoredMsg(player, lang.hot_milk_drink);
						} else if (Items.WATER_BOWL.is(item)) {
						    event.setCancelled(true);
						    change = handleWaterBowl(player);
                        } else {
						    change = config.MECHANICS_THIRST_REP_OTHER_WATER;
                        }
					}
				} else {
					change = config.MECHANICS_THIRST_REP_WATER;
				}
				break;
			case MILK_BUCKET:
				change = config.MECHANICS_THIRST_REP_MILK_BUCKET;
				break;
			case MELON_SLICE:
				change = config.MECHANICS_THIRST_REP_MELON_SLICE;
				break;
			case MUSHROOM_STEW:
				change = config.MECHANICS_THIRST_REP_MUSH_STEW;
				break;
            case HONEY_BOTTLE:
                change = config.MECHANICS_THIRST_REP_HONEY_BOTTLE;
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
				player.sendMessage(plugin.getPlayerManager().ShowHunger(player).get(1) + plugin.getPlayerManager().ShowHunger(player).get(2) + " " + plugin.getPlayerManager().ShowHunger(player).get(0).toUpperCase());
				player.sendMessage(plugin.getPlayerManager().ShowThirst(player).get(1) + plugin.getPlayerManager().ShowThirst(player).get(2) + " " + plugin.getPlayerManager().ShowThirst(player).get(0).toUpperCase());
			}
		}, 1L);
	}

	private int handleWaterBowl(Player player) {
        int change = config.MECHANICS_THIRST_REP_WATER_BOWL;
        player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL));
        if (config.MECHANICS_THIRST_PURIFY_WATER) {
            Random rand = new Random();
            if (rand.nextInt(10) + 1 <= 8) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 0));
            }
        }
        return change;
    }

    @EventHandler //if player catches a water bottle/potion give them dirty water instead
    private void onFish(PlayerFishEvent event) {
	    if (!config.MECHANICS_THIRST_PURIFY_WATER) return;
	    if (event.isCancelled()) return;
	    if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Entity caught = event.getCaught();
            if (caught instanceof Item) {
                Item item = ((Item) caught);
                ItemStack stack = item.getItemStack();
                if (stack.getType() == Material.POTION && checkWaterBottle(stack)) {
                    item.setItemStack(Items.CLEAN_WATER.getItemStack());
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
            int thirst = config.MECHANICS_THIRST_RESPAWN_AMOUNT;
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
