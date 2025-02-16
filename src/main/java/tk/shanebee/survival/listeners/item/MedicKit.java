package tk.shanebee.survival.listeners.item;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.data.Stat;
import tk.shanebee.survival.item.Items;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

public class MedicKit implements Listener {

	private SurvivalPlugin plugin;
	private Lang lang;
	private PlayerManager playerManager;

	public MedicKit(SurvivalPlugin plugin) {
		this.plugin = plugin;
		this.lang = plugin.getLang();
		this.playerManager = plugin.getPlayerManager();
	}



	@EventHandler(priority = EventPriority.HIGHEST)
	private void onDamaged(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			PlayerData playerData = playerManager.getPlayerData(player);
			playerData.setStat(Stat.HEALING, 0);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onClickEntity(PlayerInteractEntityEvent event) {
		if (event.isCancelled()) return;
		final Player player = event.getPlayer();
		PlayerData playerData = playerManager.getPlayerData(player);
		final ItemStack mainItem = player.getInventory().getItemInMainHand();

		if (Items.MEDIC_KIT.is(mainItem)) {
			if (playerData.getStat(Stat.HEALING) <= 0) {
				if (!player.isSneaking()) {
					if (event.getRightClicked() instanceof Player) {
						final Player healed = (Player) event.getRightClicked();
						PlayerData healedData = playerManager.getPlayerData(healed);

						if (healedData.getStat(Stat.HEALING) <= 0) {
							if (player.getLocation().distance(healed.getLocation()) <= 4) {
								playerData.setStat(Stat.HEALING, 1);
								healedData.setStat(Stat.HEALING, 1);
								healed.teleport(playerManager.lookAt(healed.getLocation(), player.getLocation()));
								player.sendMessage(Utils.getColoredString(lang.healing) + ChatColor.RESET + healed.getDisplayName() + Utils.getColoredString(lang.keep) + ChatColor.DARK_GREEN + Utils.getColoredString(lang.medical_kit) + Utils.getColoredString(lang.on_hand));
								healed.sendMessage(Utils.getColoredString(lang.being_healed) + ChatColor.RESET + player.getDisplayName() + Utils.getColoredString(lang.stay_still));

								playerData.setStat(Stat.HEAL_TIMES, 5);
								final Runnable task = new Runnable() {
									public void run() {
										int times = playerData.getStat(Stat.HEAL_TIMES);
										if (player.getInventory().getItemInMainHand().getType() == Material.CLOCK && player.getLocation().distance(healed.getLocation()) <= 4 && playerData.getStat(Stat.HEALING) > 0 && healedData.getStat(Stat.HEALING) > 0) {
											if (times-- > 0) {
												player.teleport(playerManager.lookAt(player.getLocation(), healed.getLocation()));

												Random rand = new Random();

												player.removePotionEffect(PotionEffectType.SLOWNESS);
												player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20, 6, true, false));
												player.removePotionEffect(PotionEffectType.JUMP_BOOST);
												player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 20, 199, true, false));

												healed.getWorld().playSound(healed.getLocation(), Sound.ENTITY_LEASH_KNOT_PLACE, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
												healed.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 0));

												Location particleLoc = healed.getLocation();
												particleLoc.setY(particleLoc.getY() + 1);
												Utils.spawnParticle(particleLoc, Particle.HAPPY_VILLAGER, 10, 0.5, 0.5, 0.5);

												Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 20L);
												playerData.setStat(Stat.HEAL_TIMES, times);
											} else {
												playerData.setStat(Stat.HEALING, 0);
												healedData.setStat(Stat.HEALING, 0);

												player.sendMessage(ChatColor.DARK_GREEN + Utils.getColoredString(lang.healing_complete));
												healed.sendMessage(ChatColor.DARK_GREEN + Utils.getColoredString(lang.healing_complete));

												player.getInventory().removeItem(Items.MEDIC_KIT.getItemStack());
											}
										} else {
											playerData.setStat(Stat.HEALING, 0);
											healedData.setStat(Stat.HEALING, 0);

											player.sendMessage(ChatColor.DARK_RED + Utils.getColoredString(lang.healing_interrupted));
											healed.sendMessage(ChatColor.DARK_RED + Utils.getColoredString(lang.healing_interrupted));

											player.getInventory().removeItem(Items.MEDIC_KIT.getItemStack());
										}
									}
								};
								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, task, -1L);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	private void onSelfClick(PlayerInteractEvent event) {
		if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			final Player player = event.getPlayer();
			PlayerData playerData = playerManager.getPlayerData(player);
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			if (Items.MEDIC_KIT.is(mainItem)) {
				if (playerData.getStat(Stat.HEALING) <= 0) {
					if (player.isSneaking()) {
						playerData.setStat(Stat.HEALING, 1);
						player.sendMessage(Utils.getColoredString(lang.healing_self) + Utils.getColoredString(lang.keep) + ChatColor.DARK_GREEN + Utils.getColoredString(lang.medical_kit) + Utils.getColoredString(lang.on_hand));

						playerData.setStat(Stat.HEAL_TIMES, 5);
						final Runnable task = new Runnable() {
							public void run() {
								int times = playerData.getStat(Stat.HEAL_TIMES);
								if (Items.MEDIC_KIT.is(player.getInventory().getItemInMainHand()) && playerData.getStat(Stat.HEALING) > 0) {
									if (times-- > 0) {
										Random rand = new Random();

										player.removePotionEffect(PotionEffectType.SLOWNESS);
										player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20, 6, true, false));
										player.removePotionEffect(PotionEffectType.JUMP_BOOST);
										player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 20, 199, true, false));

										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LEASH_KNOT_PLACE, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
										player.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 0));

										Location particleLoc = player.getLocation();
										particleLoc.setY(particleLoc.getY() + 1);
										Utils.spawnParticle(particleLoc, Particle.HAPPY_VILLAGER, 10, 0.5, 0.5, 0.5);

										Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 20L);
										playerData.setStat(Stat.HEAL_TIMES, times);
									} else {
										playerData.setStat(Stat.HEALING, 0);

										player.sendMessage(ChatColor.DARK_GREEN + Utils.getColoredString(lang.healing_complete));

										player.getInventory().removeItem(Items.MEDIC_KIT.getItemStack());
									}
								} else {
									playerData.setStat(Stat.HEALING, 0);

									player.sendMessage(ChatColor.DARK_RED + Utils.getColoredString(lang.healing_interrupted));

									player.getInventory().removeItem(Items.MEDIC_KIT.getItemStack());
								}
							}
						};

						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, task, -1L);
					}
				}
			}
		}
	}


}
