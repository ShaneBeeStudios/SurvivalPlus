package tk.shanebee.survival.listeners;

import java.util.Collection;
import java.util.Random;

import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.util.Utils;
import org.bukkit.*;
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
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.util.Vector;

import tk.shanebee.survival.Survival;

class GiantBlade implements Listener {

	private Objective charge = Survival.board.getObjective("Charge");
	private Objective charging = Survival.board.getObjective("Charging");
	private Objective dualWield = Survival.board.getObjective("DualWield");

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onAttack(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			ItemStack offItem = player.getInventory().getItemInOffHand();

			if (dualWield.getScore(player.getName()).getScore() == 1) {
				event.setCancelled(true);
				return;
			}

			Random rand = new Random();

			if (Items.compare(offItem, Items.ENDER_GIANT_BLADE)) {
				if (event.getDamager() instanceof LivingEntity && event.getCause() == DamageCause.ENTITY_ATTACK) {
					LivingEntity enemy = (LivingEntity) event.getDamager();
					enemy.damage(event.getDamage() * 40 / 100, player);
				}

				int chance_reduceDur = rand.nextInt(10) + 1;
				if (chance_reduceDur == 1) {
					((Damageable) offItem.getItemMeta()).setDamage(((Damageable) offItem.getItemMeta()).getDamage() + 1);
				}

				if (((Damageable) offItem.getItemMeta()).getDamage() >= 32) {
					player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
					player.getInventory().setItemInOffHand(null);
				}
			}
		}
	}

	//To prevent double messages send to player.
	private Objective tech_dualWieldMsg = Survival.board.getObjective("DualWieldMsg");

	@EventHandler
	private void onItemClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack mainItem = player.getInventory().getItemInMainHand();
		ItemStack offItem = player.getInventory().getItemInOffHand();

		Score score_dualWieldMsg = tech_dualWieldMsg.getScore(player.getName());

		if (Items.compare(mainItem, Items.ENDER_GIANT_BLADE)) {
			if (dualWield.getScore(player.getName()).getScore() == 0) {
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
					if (player.isSprinting()) {
						if (charge.getScore(player.getName()).getScore() == 0) {
							Random rand = new Random();

							ChargeForward(player);

							if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
								player.setFoodLevel(player.getFoodLevel() - 1);

							int chance_reduceDur = rand.nextInt(10) + 1;
							if (chance_reduceDur == 1) {
								((Damageable) mainItem.getItemMeta()).setDamage(((Damageable) mainItem.getItemMeta()).getDamage() + 1);
							}

							if (((Damageable) event.getItem().getItemMeta()).getDamage() >= 32) {
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
		} else if (Items.compare(offItem, Items.ENDER_GIANT_BLADE)) {
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

	private void ChargeForward(Player player) {
		player.sendMessage(ChatColor.BLUE + Utils.getColoredString(Survival.lang.charge));

		Score score = charge.getScore(player.getName());
		score.setScore(1);

		Location loc = player.getLocation();
		if (loc.getPitch() < 0)
			loc.setPitch(0);

		Vector vel = loc.getDirection();

		Vector newVel = vel.multiply(3);

		player.setVelocity(newVel);

		final Player chargingPlayer = player;
		charging.getScore(chargingPlayer.getName()).setScore(8);

		final Runnable task = new Runnable() {
			public void run() {
				damageNearbyEnemies(chargingPlayer);

				Random rand = new Random();
				chargingPlayer.getLocation().getWorld().playSound(chargingPlayer.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 1.5F, rand.nextFloat() * 0.4F + 0.8F);
				Utils.spawnParticle(chargingPlayer.getLocation(), Particle.EXPLOSION_NORMAL, 10, 0,0,0);

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

	private void damageNearbyEnemies(Player player) {
		Collection<Entity> enemies = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 2, 2, 2);
		for (Entity e : enemies) {
			if (e instanceof LivingEntity && e != player) {
				LivingEntity enemy = (LivingEntity) e;
				enemy.damage(8, player);
			}
		}
	}

}
