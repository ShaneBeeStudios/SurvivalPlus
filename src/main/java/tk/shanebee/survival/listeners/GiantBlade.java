package tk.shanebee.survival.listeners;

import java.util.Collection;
import java.util.Random;

import org.bukkit.inventory.EquipmentSlot;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.data.Stat;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.config.Lang;
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
import org.bukkit.util.Vector;

import tk.shanebee.survival.Survival;

class GiantBlade implements Listener {

	private Survival plugin;
	private Lang lang;
	private PlayerManager playerManager;

	GiantBlade(Survival plugin) {
		this.plugin = plugin;
		this.lang = plugin.getLang();
		this.playerManager = plugin.getPlayerManager();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onAttack(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			PlayerData playerData = playerManager.getPlayerData(player);
			ItemStack offItem = player.getInventory().getItemInOffHand();

			if (playerData.getStat(Stat.DUAL_WIELD) == 1) {
				event.setCancelled(true);
				return;
			}

			Random rand = new Random();

			if (ItemManager.compare(offItem, Items.ENDER_GIANT_BLADE)) {
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

	@EventHandler
	private void onItemClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		PlayerData playerData = playerManager.getPlayerData(player);
		ItemStack mainItem = player.getInventory().getItemInMainHand();
		ItemStack offItem = player.getInventory().getItemInOffHand();

		if (ItemManager.compare(mainItem, Items.ENDER_GIANT_BLADE)) {
			if (playerData.getStat(Stat.DUAL_WIELD) == 0) {
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
					if (event.getHand() != EquipmentSlot.HAND) return; // prevent double message

					if (player.isSprinting()) {
						if (playerData.getStat(Stat.CHARGE) == 0) {
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
							player.sendMessage(ChatColor.RED + Utils.getColoredString(lang.charge_unable));
						}
					}
				}
			} else {
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
					playerData.setStat(Stat.DUAL_WIELD_MSG, playerData.getStat(Stat.DUAL_WIELD_MSG) + 1);
				else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
					playerData.setStat(Stat.DUAL_WIELD_MSG, playerData.getStat(Stat.DUAL_WIELD_MSG) + 2);
				if (playerData.getStat(Stat.DUAL_WIELD_MSG) >= 2) {
					player.sendMessage(ChatColor.RED + Utils.getColoredString(lang.ender_giant_blade_unable_duel));
				}
			}
		} else if (ItemManager.compare(offItem, Items.ENDER_GIANT_BLADE)) {
			if (playerData.getStat(Stat.DUAL_WIELD) != 0) {
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
					playerData.setStat(Stat.DUAL_WIELD_MSG, playerData.getStat(Stat.DUAL_WIELD_MSG) + 1);
				else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
					playerData.setStat(Stat.DUAL_WIELD_MSG, playerData.getStat(Stat.DUAL_WIELD_MSG) + 2);
				if (playerData.getStat(Stat.DUAL_WIELD_MSG) >= 2) {
					player.sendMessage(ChatColor.RED + Utils.getColoredString(lang.ender_giant_blade_unable_duel));
				}
			}
		}
		playerData.setStat(Stat.DUAL_WIELD_MSG, 0);
	}

	private void ChargeForward(Player player) {
		PlayerData playerData = playerManager.getPlayerData(player);
		player.sendMessage(ChatColor.BLUE + Utils.getColoredString(lang.charge));

		playerData.setStat(Stat.CHARGE, 1);


		Location loc = player.getLocation();
		if (loc.getPitch() < 0)
			loc.setPitch(0);

		Vector vel = loc.getDirection();

		Vector newVel = vel.multiply(3);

		player.setVelocity(newVel);

		final Player chargingPlayer = player;
		playerData.setStat(Stat.CHARGING, 8);

		final Runnable task = new Runnable() {
			public void run() {
				damageNearbyEnemies(chargingPlayer);

				Random rand = new Random();
				chargingPlayer.getLocation().getWorld().playSound(chargingPlayer.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 1.5F, rand.nextFloat() * 0.4F + 0.8F);
				Utils.spawnParticle(chargingPlayer.getLocation(), Particle.EXPLOSION_NORMAL, 10, 0, 0, 0);

				int times = playerData.getStat(Stat.CHARGING);
				if (--times > 1)
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1L);
				playerData.setStat(Stat.CHARGING, times);
			}
		};

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, task, -1L);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
			playerData.setStat(Stat.CHARGE, 0);
			chargingPlayer.sendMessage(ChatColor.GREEN + Utils.getColoredString(lang.charge_ready));
		}, 100L);
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
