package com.fattymieo.survival.events;

import java.util.Collection;
import java.util.Random;

import com.fattymieo.survival.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.util.Vector;

import com.fattymieo.survival.Survival;

import lib.ParticleEffect;

public class GiantBlade implements Listener {

	private Objective charge = Survival.board.getObjective("Charge");
	private Objective charging = Survival.board.getObjective("Charging");
	private Objective dualWield = Survival.board.getObjective("DualWield");

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAttack(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			ItemStack offItem = player.getInventory().getItemInOffHand();

			if (dualWield.getScore(player.getName()).getScore() == 1) {
				event.setCancelled(true);
				return;
			}

			Random rand = new Random();

			if (offItem.getType() == Material.GOLDEN_HOE) {
				if (event.getDamager() instanceof LivingEntity && event.getCause() == DamageCause.ENTITY_ATTACK) {
					LivingEntity enemy = (LivingEntity) event.getDamager();
					enemy.damage(event.getDamage() * 40 / 100, player);
				}

				int chance_reduceDur = rand.nextInt(10) + 1;
				if (chance_reduceDur == 1) {
					offItem.setDurability((short) (offItem.getDurability() + 1));
				}

				if (offItem.getDurability() >= 32) {
					player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
					player.getInventory().setItemInOffHand(null);
				}
			}
		}
	}

	//To prevent double messages send to player.
	private Objective tech_dualWieldMsg = Survival.board.getObjective("DualWieldMsg");

	@EventHandler
	public void onItemClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack mainItem = player.getInventory().getItemInMainHand();
		ItemStack offItem = player.getInventory().getItemInOffHand();

		Score score_dualWieldMsg = tech_dualWieldMsg.getScore(player.getName());

		if (mainItem.getType() == Material.GOLDEN_HOE) {
			if (dualWield.getScore(player.getName()).getScore() == 0) {
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
					if (player.isSprinting()) {
						if (charge.getScore(player.getName()).getScore() == 0) {
							Random rand = new Random();

							ChargeForward(player, 3);

							if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
								player.setFoodLevel(player.getFoodLevel() - 1);

							int chance_reduceDur = rand.nextInt(10) + 1;
							switch (chance_reduceDur) {
								case 1:
									mainItem.setDurability((short) (mainItem.getDurability() + 1));
									break;
								default:
							}

							if (event.getItem().getDurability() >= 32) {
								player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
								player.getInventory().setItemInMainHand(null);
							}
							player.updateInventory();
						} else {
							player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.charge_unable));
						}
					}
				}
			} else {
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
					score_dualWieldMsg.setScore(score_dualWieldMsg.getScore() + 1);
				else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
					score_dualWieldMsg.setScore(score_dualWieldMsg.getScore() + 2);
				if (score_dualWieldMsg.getScore() >= 2) {
					player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.ender_giant_blade_unable_duel));
				}
			}
		} else if (offItem.getType() == Material.GOLDEN_HOE) {
			if (dualWield.getScore(player.getName()).getScore() != 0) {
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
					score_dualWieldMsg.setScore(score_dualWieldMsg.getScore() + 1);
				else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
					score_dualWieldMsg.setScore(score_dualWieldMsg.getScore() + 2);
				if (score_dualWieldMsg.getScore() >= 2) {
					player.sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.ender_giant_blade_unable_duel));
				}
			}
		}
		score_dualWieldMsg.setScore(0);
	}

	private void ChargeForward(Player player, int velocity) {
		player.sendMessage(ChatColor.BLUE + Utils.getColoredString(Survival.lang.charge));

		Score score = charge.getScore(player.getName());
		score.setScore(1);

		Location loc = player.getLocation();
		if (loc.getPitch() < 0)
			loc.setPitch(0);

		Vector vel = loc.getDirection();

		Vector newVel = vel.multiply(velocity);

		player.setVelocity(newVel);

		final Player chargingPlayer = player;
		charging.getScore(chargingPlayer.getName()).setScore(8);

		final Runnable task = new Runnable() {
			public void run() {
				damageNearbyEnemies(chargingPlayer, 8);

				Random rand = new Random();
				chargingPlayer.getLocation().getWorld().playSound(chargingPlayer.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 1.5F, rand.nextFloat() * 0.4F + 0.8F);
				ParticleEffect.EXPLOSION_NORMAL.display(0, 0, 0, 0, 10, chargingPlayer.getLocation(), 64);

				int times = charging.getScore(chargingPlayer.getName()).getScore();
				if (--times > 1)
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, this, 1L);
				charging.getScore(chargingPlayer.getName()).setScore(times);
			}
		};

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, task, -1L);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, new Runnable() {
					Score score = charge.getScore(chargingPlayer.getName());

					public void run() {
						score.setScore(0);
						chargingPlayer.sendMessage(ChatColor.GREEN + Utils.getColoredString(Survival.lang.charge_ready));
					}
				},
				100L);
	}

	private void damageNearbyEnemies(Player player, int dmg) {
		Collection<Entity> enemies = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 2, 2, 2);
		for (Entity e : enemies) {
			if (e instanceof LivingEntity && e != player) {
				LivingEntity enemy = (LivingEntity) e;
				enemy.damage(dmg, player);
			}
		}
	}

}
