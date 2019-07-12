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
import org.bukkit.scoreboard.Objective;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.managers.StatusManager;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

class Consume implements Listener {

	private Objective thirst = Survival.mainBoard.getObjective("Thirst");
	private Survival plugin;

	Consume(Survival plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onConsume(PlayerItemConsumeEvent event) {
		if (event.isCancelled()) return;
		final Player player = event.getPlayer();
		ItemStack item = event.getItem();
		switch (event.getItem().getType()) {
			case POTION:
				if (Survival.settings.getBoolean("Mechanics.Thirst.PurifyWater")) {
					if (checkWaterBottle(item)) {
						if (ItemManager.compare(item, Items.DIRTY_WATER)) {
							StatusManager.addThirst(player, 13);
							Random rand = new Random();
							if (rand.nextInt(10) + 1 <= 5) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
							}
						} else if (ItemManager.compare(item, Items.CLEAN_WATER)) {
							StatusManager.addThirst(player, 18);
							Random rand = new Random();
							if (rand.nextInt(10) + 1 <= 2) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
							}
						} else if (ItemManager.compare(item, Items.PURIFIED_WATER) || ItemManager.compare(item, Items.COFFEE)) {
							StatusManager.addThirst(player, 23);
						} else if (ItemManager.compare(item, Items.COLD_MILK)) {
							StatusManager.addThirst(player, 15);
						} else if (ItemManager.compare(item, Items.HOT_MILK)) {
							StatusManager.addThirst(player, 10);
							player.damage(2);
							player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 0), true);
							Utils.sendColoredMsg(player, Survival.lang.hot_milk_drink);
						}
					}
				} else {
					StatusManager.addThirst(player, 18);
				}
				break;
			case BEETROOT_SOUP: //Water Bowl
				event.setCancelled(true);
				if (ItemManager.compare(event.getPlayer().getInventory().getItemInMainHand(), Items.WATER_BOWL)) {
					StatusManager.addThirst(player, 10);
					player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL));

					if (Survival.settings.getBoolean("Mechanics.Thirst.PurifyWater")) {
						Random rand = new Random();
						if (rand.nextInt(10) + 1 <= 8) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
						}
					}
				}
				break;
			case MILK_BUCKET:
				StatusManager.addThirst(player, 30);
				break;
			case MELON:
				StatusManager.addThirst(player, 6);
				break;
			case MUSHROOM_STEW:
				StatusManager.addThirst(player, 12);
				break;
			default:
		}

		// OLD METHOD I think this can be removed, but let's leave it here to be safe for now
		if (thirst.getScore(player.getName()).getScore() > 40) {
			thirst.getScore(player.getName()).setScore(40);
		}

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, () -> {
			if (!Survival.settings.getBoolean("Mechanics.StatusScoreboard")) {
				player.sendMessage(plugin.getPlayerManager().ShowHunger(player).get(1) + plugin.getPlayerManager().ShowHunger(player).get(2) + " " + plugin.getPlayerManager().ShowHunger(player).get(0).toUpperCase());
				player.sendMessage(plugin.getPlayerManager().ShowThirst(player).get(1) + plugin.getPlayerManager().ShowThirst(player).get(2) + " " + plugin.getPlayerManager().ShowThirst(player).get(0).toUpperCase());
			}
		}, 1L);
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		StatusManager.setThirst(player, 30);
	}

	@EventHandler
	private void onFirstJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			int amt = Survival.settings.getInt("Mechanics.Thirst.Starting-Amount");
			StatusManager.setThirst(player, amt);
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
