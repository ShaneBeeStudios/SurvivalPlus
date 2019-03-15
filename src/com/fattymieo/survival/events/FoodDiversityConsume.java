package com.fattymieo.survival.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Objective;

import com.fattymieo.survival.Survival;

public class FoodDiversityConsume implements Listener {

	private Objective carbon = Survival.mainBoard.getObjective("Carbs");
	private Objective protein = Survival.mainBoard.getObjective("Protein");
	private Objective salts = Survival.mainBoard.getObjective("Salts");

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onConsume(PlayerItemConsumeEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		switch (event.getItem().getType()) {
			case PUMPKIN_PIE:
				addStats(player, carbon, 300);
				addStats(player, protein, 50);
				addStats(player, salts, 60);
				break;
			case RABBIT_STEW:
				addStats(player, carbon, 200);
				addStats(player, protein, 225);
				addStats(player, salts, 240);
				break;
			case BREAD:
				addStats(player, carbon, 300);
				addStats(player, protein, 25);
				addStats(player, salts, 12);
				break;
			//case CAKE: (PlayerInteractEvent)
			case POTATO:
				addStats(player, carbon, 25);
				addStats(player, protein, 0);
				addStats(player, salts, 4);
				break;
			case BAKED_POTATO:
				addStats(player, carbon, 200);
				addStats(player, protein, 0);
				addStats(player, salts, 35);
				break;
			case POISONOUS_POTATO:
				addStats(player, carbon, 50);
				addStats(player, protein, 0);
				addStats(player, salts, 8);
				break;
			case APPLE:
			case GOLDEN_APPLE:
				addStats(player, carbon, 50);
				addStats(player, protein, 0);
				addStats(player, salts, 70);
				break;
			case CHORUS_FRUIT:
				addStats(player, carbon, 25);
				addStats(player, protein, 0);
				addStats(player, salts, 35);
				break;
			case MELON:
				addStats(player, carbon, 25);
				addStats(player, protein, 0);
				addStats(player, salts, 35);
				break;
			case MUSHROOM_STEW:
				addStats(player, carbon, 0);
				addStats(player, protein, 50);
				addStats(player, salts, 200);
				break;
			case COOKIE:
				addStats(player, carbon, 107);
				addStats(player, protein, 11);
				addStats(player, salts, 3);
				break;
			case MILK_BUCKET:
				addStats(player, carbon, 0);
				addStats(player, protein, 250);
				addStats(player, salts, 0);
				break;
			case BEETROOT:
				addStats(player, carbon, 0);
				addStats(player, protein, 0);
				addStats(player, salts, 35);
				break;
			case CARROT:
				addStats(player, carbon, 0);
				addStats(player, protein, 0);
				addStats(player, salts, 105);
				break;
			case GOLDEN_CARROT:
				addStats(player, carbon, 0);
				addStats(player, protein, 0);
				addStats(player, salts, 25);
				break;
			case COOKED_COD:
			case COOKED_SALMON:
				addStats(player, carbon, 0);
				addStats(player, protein, 225);
				addStats(player, salts, 0);
				break;
			case COOKED_BEEF:
			case COOKED_CHICKEN:
			case COOKED_MUTTON:
			case COOKED_PORKCHOP:
			case COOKED_RABBIT:
				addStats(player, carbon, 0);
				addStats(player, protein, 200);
				addStats(player, salts, 0);
				break;
			case SALMON:
			case COD:
				addStats(player, carbon, 0);
				addStats(player, protein, 75);
				addStats(player, salts, 0);
				break;
			case BEEF:
			case CHICKEN:
			case MUTTON:
			case PORKCHOP:
			case RABBIT:
			case ROTTEN_FLESH:
				addStats(player, carbon, 0);
				addStats(player, protein, 50);
				addStats(player, salts, 0);
				break;
			case SPIDER_EYE:
				addStats(player, carbon, 0);
				addStats(player, protein, 50);
				addStats(player, salts, 0);
				break;
			default:
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onConsumeCake(PlayerInteractEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		if (event.hasBlock() && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block cake = event.getClickedBlock();
			if (cake.getType().equals(Material.CAKE)) {
				if (player.getFoodLevel() < 20 && (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)) {
					addStats(player, carbon, 171);
					addStats(player, protein, 114);
					addStats(player, salts, 3);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent e) {
		if (e.isCancelled()) return;
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();

			e.setDamage(e.getDamage() * addMultiplier(player));
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		carbon.getScore(player.getName()).setScore(960);
		protein.getScore(player.getName()).setScore(240);
		salts.getScore(player.getName()).setScore(360);
	}

	@EventHandler
	public void onFirstJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			carbon.getScore(player.getName()).setScore(960);
			protein.getScore(player.getName()).setScore(240);
			salts.getScore(player.getName()).setScore(360);
		}
	}

	private void addStats(Player player, Objective nutrient, int point) {
		nutrient.getScore(player.getName()).setScore(nutrient.getScore(player.getName()).getScore() + point);
	}

	private double addMultiplier(Player player) {
		double damageMultiplier = 1;

		if (protein.getScore(player.getName()).getScore() <= 0) {
			switch (player.getWorld().getDifficulty()) {
				case EASY:
					damageMultiplier *= 1.25;
					break;
				case NORMAL:
					damageMultiplier *= 1.5;
					break;
				case HARD:
					damageMultiplier *= 2;
					break;
				default:
			}
		}
		if (salts.getScore(player.getName()).getScore() <= 0) {
			switch (player.getWorld().getDifficulty()) {
				case EASY:
					damageMultiplier *= 1.25;
					break;
				case NORMAL:
					damageMultiplier *= 1.5;
					break;
				case HARD:
					damageMultiplier *= 2;
					break;
				default:
			}
		}

		return damageMultiplier;
	}

}
