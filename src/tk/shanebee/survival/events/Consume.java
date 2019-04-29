package tk.shanebee.survival.events;

import tk.shanebee.survival.Survival;
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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;

import java.util.List;
import java.util.Random;

public class Consume implements Listener {

	private Objective thirst = Survival.mainBoard.getObjective("Thirst");

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onConsume(PlayerItemConsumeEvent event) {
		if (event.isCancelled()) return;
		final Player player = event.getPlayer();
		switch (event.getItem().getType()) {
			case POTION:
				// TODO add in new water items (purified/clean)
				thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 18);
				if (Survival.settings.getBoolean("Mechanics.Thirst.PurifyWater")) {
					if (checkWaterBottle(event.getItem())) {
						List<String> lore = event.getItem().getItemMeta().getLore();
						assert lore != null;
						if (!lore.toString().contains(Survival.lang.purified_water)) {
							Random rand = new Random();
							if (rand.nextInt(10) + 1 <= 6) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0), true);
							}
						}
					}
				}
				break;
			case BEETROOT_SOUP: //Water Bowl
				event.setCancelled(true);
				if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BEETROOT_SOUP) {
					thirst.getScore(player.getName()).setScore(thirst.getScore(player.getName()).getScore() + 18);
					player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL));

					if (Survival.settings.getBoolean("Mechanics.Thirst.PurifyWater")) {
						Random rand = new Random();
						if (rand.nextInt(10) + 1 <= 6) {
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
				player.sendMessage(Survival.ShowHunger(player).get(1) + Survival.ShowHunger(player).get(2) + " " + Survival.ShowHunger(player).get(0).toUpperCase());
				player.sendMessage(Survival.ShowThirst(player).get(1) + Survival.ShowThirst(player).get(2) + " " + Survival.ShowThirst(player).get(0).toUpperCase());
			}
		}, 1L);
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		thirst.getScore(player.getName()).setScore(30);
	}

	@EventHandler
	public void onFirstJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			thirst.getScore(player.getName()).setScore(30);
		}
	}

	private boolean checkWaterBottle(ItemStack bottle) {
		switch (((PotionMeta) bottle.getItemMeta()).getBasePotionData().getType()) {
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
