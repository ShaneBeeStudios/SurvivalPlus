package tk.shanebee.survival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.events.ThirstLevelChangeEvent;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.managers.StatusManager;
import tk.shanebee.survival.util.Config;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

class Consume implements Listener {

	private Survival plugin;
	private Config config;
	private Lang lang;

	Consume(Survival plugin) {
		this.plugin = plugin;
		this.config = plugin.getSurvivalConfig();
		this.lang = plugin.getLang();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onConsume(PlayerItemConsumeEvent event) {
		if (event.isCancelled()) return;
		final Player player = event.getPlayer();
		ItemStack item = event.getItem();
		int change = 0;
		switch (event.getItem().getType()) {
			case POTION:
				if (config.THIRST_PURIFY_WATER) {
					if (checkWaterBottle(item)) {
						if (ItemManager.compare(item, Items.DIRTY_WATER)) {
							change = 13;
							Random rand = new Random();
							if (rand.nextInt(10) + 1 <= 5) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
							}
						} else if (ItemManager.compare(item, Items.CLEAN_WATER)) {
							change = 18;
							Random rand = new Random();
							if (rand.nextInt(10) + 1 <= 2) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
							}
						} else if (ItemManager.compare(item, Items.PURIFIED_WATER) || ItemManager.compare(item, Items.COFFEE)) {
							change = 23;
						} else if (ItemManager.compare(item, Items.COLD_MILK)) {
							change = 15;
						} else if (ItemManager.compare(item, Items.HOT_MILK)) {
							change = 10;
							player.damage(2);
							player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 0), true);
							Utils.sendColoredMsg(player, lang.hot_milk_drink);
						}
					}
				} else {
					change = 18;
				}
				break;
			case BEETROOT_SOUP: //Water Bowl
				event.setCancelled(true);
				if (ItemManager.compare(event.getPlayer().getInventory().getItemInMainHand(), Items.WATER_BOWL)) {
					change = 10;
					player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL));

					if (config.THIRST_PURIFY_WATER) {
						Random rand = new Random();
						if (rand.nextInt(10) + 1 <= 8) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
						}
					}
				}
				break;
			case MILK_BUCKET:
				change = 30;
				break;
			case MELON_SLICE:
				change = 6;
				break;
			case MUSHROOM_STEW:
				change = 12;
				break;
			default:
		}
		ThirstLevelChangeEvent thirstEvent = new ThirstLevelChangeEvent(player, change, StatusManager.getThirst(player) + change);
		Bukkit.getPluginManager().callEvent(thirstEvent);
		if (!thirstEvent.isCancelled()) {
			StatusManager.addThirst(player, change);
		}

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
			if (!config.STATUS_SCOREBOARD) {
				player.sendMessage(plugin.getPlayerManager().ShowHunger(player).get(1) + plugin.getPlayerManager().ShowHunger(player).get(2) + " " + plugin.getPlayerManager().ShowHunger(player).get(0).toUpperCase());
				player.sendMessage(plugin.getPlayerManager().ShowThirst(player).get(1) + plugin.getPlayerManager().ShowThirst(player).get(2) + " " + plugin.getPlayerManager().ShowThirst(player).get(0).toUpperCase());
			}
		}, 1L);
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		int thirst = config.THIRST_RESPAWN_AMOUNT;
		StatusManager.setThirst(player, thirst);
		int hunger = config.HUNGER_RESPAWN_AMOUNT;
		Bukkit.getScheduler().runTaskLater(plugin, () -> StatusManager.setHunger(player, hunger), 1);

	}

	@EventHandler
	private void onFirstJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			int thirst = config.THIRST_START_AMOUNT;
			StatusManager.setThirst(player, thirst);
			int hunger = config.HUNGER_START_AMOUNT;
			Bukkit.getScheduler().runTaskLater(plugin, () -> StatusManager.setHunger(player, hunger), 1);
		}
	}

	private boolean checkWaterBottle(ItemStack bottle) {
		ItemMeta meta = bottle.getItemMeta();
		assert meta != null;
		switch (((PotionMeta) meta).getBasePotionData().getType()) {
			case WATER:
			case MUNDANE:
			case THICK:
			case AWKWARD:
				return true;
			default:
				return false;
		}
	}

}
