package com.shanebeestudios.survival.tasks.tool;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.data.PlayerData;
import com.shanebeestudios.survival.data.Stat;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.managers.PlayerManager;

public class GiantBlade extends BukkitRunnable {

	private final SurvivalPlugin plugin;
	private final PlayerManager playerManager;
	private final ImmutableSet<Material> MAIN_SET;
	private final ImmutableSet<Material> OFF_SET;
	private final PotionEffect DAMAGE;
	private final PotionEffect SLOW;
	private final PotionEffect JUMP;

	public GiantBlade(SurvivalPlugin plugin) {
		this.plugin = plugin;
		this.playerManager = plugin.getPlayerManager();
		this.MAIN_SET = ImmutableSet.<Material>builder()
				.add(Material.GOLDEN_HOE).add(Material.GOLDEN_AXE).build();
		this.OFF_SET = ImmutableSet.<Material>builder()
				.add(Material.WOODEN_AXE).add(Material.WOODEN_SWORD).add(Material.WOODEN_PICKAXE)
				.add(Material.WOODEN_SHOVEL).add(Material.WOODEN_HOE).add(Material.STONE_AXE)
				.add(Material.STONE_SWORD).add(Material.STONE_PICKAXE).add(Material.STONE_SHOVEL)
				.add(Material.STONE_HOE).add(Material.IRON_AXE).add(Material.IRON_SWORD)
				.add(Material.IRON_PICKAXE).add(Material.IRON_SHOVEL).add(Material.IRON_HOE)
				.add(Material.GOLDEN_AXE).add(Material.GOLDEN_SWORD).add(Material.GOLDEN_PICKAXE)
				.add(Material.GOLDEN_SHOVEL).add(Material.GOLDEN_HOE).add(Material.DIAMOND_AXE)
				.add(Material.DIAMOND_SWORD).add(Material.DIAMOND_PICKAXE).add(Material.DIAMOND_SHOVEL)
				.add(Material.DIAMOND_HOE).add(Material.BOW).build();

		this.DAMAGE = new PotionEffect(PotionEffectType.RESISTANCE, 20, 1, false);
		this.SLOW = new PotionEffect(PotionEffectType.SLOWNESS, 20, 6, true);
		this.JUMP = new PotionEffect(PotionEffectType.JUMP_BOOST, 20, 199, true);

		this.runTaskTimer(plugin, 1, 10);
	}

	@Override
	public void run() { //TODO this guy needs some serious work
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			ItemStack mainItem = player.getInventory().getItemInMainHand();
			ItemStack offItem = player.getInventory().getItemInOffHand();
			Material mainType = mainItem.getType();
			Material offType = offItem.getType();

			if (Items.ENDER_GIANT_BLADE.is(mainItem)) {
				Location particleLoc = player.getLocation();
				particleLoc.setY(particleLoc.getY() + 1);
				assert particleLoc.getWorld() != null;
				particleLoc.getWorld().spawnParticle(Particle.CRIT, particleLoc, 10, 0.5, 0.5, 0.5);
			}

			if (Items.ENDER_GIANT_BLADE.is(offItem)) {
				player.removePotionEffect(PotionEffectType.RESISTANCE);
				player.addPotionEffect(this.DAMAGE);
				Location particleLoc = player.getLocation();
				particleLoc.setY(particleLoc.getY() + 1);
				assert particleLoc.getWorld() != null;
				particleLoc.getWorld().spawnParticle(Particle.CRIT, particleLoc, 10, 0.5, 0.5, 0.5);
			}

			PlayerData playerData = playerManager.getPlayerData(player);
			if ((MAIN_SET.contains(mainType) && OFF_SET.contains(offType)) || (MAIN_SET.contains(offType) && OFF_SET.contains(mainType))) {
				player.removePotionEffect(PotionEffectType.SLOWNESS);
				player.addPotionEffect(this.SLOW);
				player.removePotionEffect(PotionEffectType.JUMP_BOOST);
				player.addPotionEffect(this.JUMP);
				playerData.setStat(Stat.DUAL_WIELD, 1);
			} else {
				playerData.setStat(Stat.DUAL_WIELD, 0);
			}
		}
	}

}
