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
import tk.shanebee.survival.managers.Items;
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
						if (Items.compare(item, Items.DIRTY_WATER)) {
							thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 13);
							Random rand = new Random();
							if (rand.nextInt(10) + 1 <= 5) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
							}
						} else if (Items.compare(item, Items.CLEAN_WATER)) {
							thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 18);
							Random rand = new Random();
							if (rand.nextInt(10) + 1 <= 2) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
							}
						} else if (Items.compare(item, Items.PURIFIED_WATER) || Items.compare(item, Items.COFFEE)) {
							thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 23);
						} else if (Items.compare(item, Items.COLD_MILK)) {
							thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 15);
						} else if (Items.compare(item, Items.HOT_MILK)) {
							thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 10);
							player.damage(2);
							player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 0), true);
							Utils.sendColoredMsg(player, Survival.lang.hot_milk_drink);
						}
					}
				} else {
					thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 18);
				}
				break;
			case BEETROOT_SOUP: //Water Bowl
				event.setCancelled(true);
				if (Items.compare(event.getPlayer().getInventory().getItemInMainHand(), Items.WATER_BOWL)) {
					thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 10);
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
				thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 30);
				break;
			case MELON:
				thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 6);
				break;
			case MUSHROOM_STEW:
				thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 12);
				break;
			default:
		}

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
		thirst.getScore(player.getName()).setScore(30);
	}

	@EventHandler
	private void onFirstJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			int amt = Survival.settings.getInt("Mechanics.Thirst.Starting-Amount");
			thirst.getScore(player.getName()).setScore(amt <= 40 ? amt : 40);
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
