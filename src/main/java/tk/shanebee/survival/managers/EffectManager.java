package tk.shanebee.survival.managers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Config;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

public class EffectManager {
	
	private Survival plugin;
	private Config config;
	private Scoreboard board;
	
	public EffectManager(Survival plugin) {
		this.plugin = plugin;
		this.config = plugin.getSurvivalConfig();
		this.board = plugin.getBoard();
		loadEffects();
	}

	private void loadEffects() {
		if (config.LEGENDARY_BLAZESWORD)
			BlazeSword();
		if (config.LEGENDARY_GIANTBLADE)
			GiantBlade();
		if (config.LEGENDARY_OBSIDIAN_MACE)
			ObsidianMace();
		if (config.LEGENDARY_VALKYRIE)
			Valkyrie();
		if (config.LEGENDARY_QUARTZPICKAXE)
			QuartzPickaxe();
	}

	/** Apply obsidian mace effects to player and enemy
	 * @param player Player to apply Regeneration to
	 * @param enemy Enemy to apply weakness and slowness to
	 */
	public void applyObsidianMaceEffects(Player player, LivingEntity enemy) {
		enemy.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0, false));
		enemy.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 48, 2, true));
		Location particleLoc = player.getLocation();
		particleLoc.setY(particleLoc.getY() + 2);
		Utils.spawnParticle(particleLoc, Particle.HEART, 2, 0.5, 0.5, 0.5);
	}

	private void BlazeSword() {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (ItemManager.compare(player.getInventory().getItemInMainHand(), Items.BLAZE_SWORD)) {
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20, 0, false));
					Location particleLoc = player.getLocation();
					particleLoc.setY(particleLoc.getY() + 1);
					assert particleLoc.getWorld() != null;
					particleLoc.getWorld().spawnParticle(Particle.FLAME, particleLoc, 10, 0.5, 0.5, 0.5);

					player.setFireTicks(20);
					if (player.getHealth() > 14)
						player.setHealth(14);
				}
			}
		}, 1L, 10L);

		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (ItemManager.compare(player.getInventory().getItemInMainHand(), Items.BLAZE_SWORD)) {
					Random rand = new Random();
					assert player.getLocation().getWorld() != null;
					player.getLocation().getWorld().playSound(
							player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
				}
			}
		}, 1L, 50L);
	}

	private void GiantBlade() {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				ItemStack mainItem = player.getInventory().getItemInMainHand();
				ItemStack offItem = player.getInventory().getItemInOffHand();
				if (ItemManager.compare(mainItem, Items.ENDER_GIANT_BLADE)) {
					Location particleLoc = player.getLocation();
					particleLoc.setY(particleLoc.getY() + 1);
					assert particleLoc.getWorld() != null;
					particleLoc.getWorld().spawnParticle(Particle.CRIT_MAGIC, particleLoc, 10, 0.5, 0.5, 0.5);
				}

				if (ItemManager.compare(offItem, Items.ENDER_GIANT_BLADE)) {
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 1, false));
					Location particleLoc = player.getLocation();
					particleLoc.setY(particleLoc.getY() + 1);
					assert particleLoc.getWorld() != null;
					particleLoc.getWorld().spawnParticle(Particle.CRIT_MAGIC, particleLoc, 10, 0.5, 0.5, 0.5);
				}

				Score dualWield = board.getObjective("DualWield").getScore(player.getName());

				if (((mainItem.getType() == Material.GOLDEN_HOE || mainItem.getType() == Material.GOLDEN_AXE)
						&& (offItem.getType() == Material.WOODEN_AXE
						|| offItem.getType() == Material.WOODEN_SWORD || offItem.getType() == Material.WOODEN_PICKAXE
						|| offItem.getType() == Material.WOODEN_SHOVEL || offItem.getType() == Material.WOODEN_HOE
						|| offItem.getType() == Material.STONE_AXE || offItem.getType() == Material.STONE_SWORD
						|| offItem.getType() == Material.STONE_PICKAXE || offItem.getType() == Material.STONE_SHOVEL
						|| offItem.getType() == Material.STONE_HOE || offItem.getType() == Material.IRON_AXE
						|| offItem.getType() == Material.IRON_SWORD || offItem.getType() == Material.IRON_PICKAXE
						|| offItem.getType() == Material.IRON_SHOVEL || offItem.getType() == Material.IRON_HOE
						|| offItem.getType() == Material.GOLDEN_AXE || offItem.getType() == Material.GOLDEN_SWORD
						|| offItem.getType() == Material.GOLDEN_PICKAXE || offItem.getType() == Material.GOLDEN_SHOVEL
						|| offItem.getType() == Material.GOLDEN_HOE || offItem.getType() == Material.DIAMOND_AXE
						|| offItem.getType() == Material.DIAMOND_SWORD || offItem.getType() == Material.DIAMOND_PICKAXE
						|| offItem.getType() == Material.DIAMOND_SHOVEL || offItem.getType() == Material.DIAMOND_HOE
						|| offItem.getType() == Material.BOW))
						|| ((offItem.getType() == Material.GOLDEN_HOE || offItem.getType() == Material.GOLDEN_AXE)
						&& (mainItem.getType() == Material.WOODEN_AXE
						|| mainItem.getType() == Material.WOODEN_SWORD || mainItem.getType() == Material.WOODEN_PICKAXE
						|| mainItem.getType() == Material.WOODEN_SHOVEL || mainItem.getType() == Material.WOODEN_HOE
						|| mainItem.getType() == Material.STONE_AXE || mainItem.getType() == Material.STONE_SWORD
						|| mainItem.getType() == Material.STONE_PICKAXE || mainItem.getType() == Material.STONE_SHOVEL
						|| mainItem.getType() == Material.STONE_HOE || mainItem.getType() == Material.IRON_AXE
						|| mainItem.getType() == Material.IRON_SWORD || mainItem.getType() == Material.IRON_PICKAXE
						|| mainItem.getType() == Material.IRON_SHOVEL || mainItem.getType() == Material.IRON_HOE
						|| mainItem.getType() == Material.GOLDEN_AXE || mainItem.getType() == Material.GOLDEN_SWORD
						|| mainItem.getType() == Material.GOLDEN_PICKAXE || mainItem.getType() == Material.GOLDEN_SHOVEL
						|| mainItem.getType() == Material.GOLDEN_HOE || mainItem.getType() == Material.DIAMOND_AXE
						|| mainItem.getType() == Material.DIAMOND_SWORD || mainItem.getType() == Material.DIAMOND_PICKAXE
						|| mainItem.getType() == Material.DIAMOND_SHOVEL || mainItem.getType() == Material.DIAMOND_HOE
						|| mainItem.getType() == Material.BOW))) {
					player.removePotionEffect(PotionEffectType.SLOW);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 6, true));
					player.removePotionEffect(PotionEffectType.JUMP);
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 199, true));
					dualWield.setScore(1);
				} else {
					dualWield.setScore(0);
				}
			}
		}, 1L, 10L);

	}

	private void ObsidianMace() {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (ItemManager.compare(player.getInventory().getItemInMainHand(), Items.OBSIDIAN_MACE)) {
					player.removePotionEffect(PotionEffectType.SLOW);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, false));
					Location particleLoc = player.getLocation();
					particleLoc.setY(particleLoc.getY() + 1);
					assert particleLoc.getWorld() != null;
					particleLoc.getWorld().spawnParticle(Particle.CRIT, particleLoc, 10, 0.5, 0.5, 0.5);
					particleLoc.getWorld().spawnParticle(Particle.PORTAL, particleLoc, 20, 0.5, 0.5, 0.5);
				}
			}
		}, 1L, 10L);
	}

	private void Valkyrie() {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (ItemManager.compare(player.getInventory().getItemInMainHand(), Items.VALKYRIES_AXE)) {
					Location particleLoc = player.getLocation();
					particleLoc.setY(particleLoc.getY() + 1);
					assert particleLoc.getWorld() != null;
					particleLoc.getWorld().spawnParticle(Particle.CRIT_MAGIC, particleLoc, 10, 0.5, 0.5, 0.5);
				}
			}
		}, 1L, 10L);
	}

	private void QuartzPickaxe() {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (ItemManager.compare(player.getInventory().getItemInMainHand(), Items.QUARTZ_PICKAXE)) {
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 9, false));
				}
			}
		}, 1L, 10L);
	}

}
