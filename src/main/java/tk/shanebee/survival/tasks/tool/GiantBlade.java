package tk.shanebee.survival.tasks.tool;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.data.Stat;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.managers.PlayerManager;

public class GiantBlade extends BukkitRunnable {

	private Survival plugin;
	private PlayerManager playerManager;
	private int id;

	public GiantBlade(Survival plugin) {
		this.plugin = plugin;
		this.playerManager = plugin.getPlayerManager();
		this.id = this.runTaskTimer(plugin, 1, 10).getTaskId();
	}

	@Override
	public void run() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			PlayerData playerData = playerManager.getPlayerData(player);
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

			//Score dualWield = board.getObjective("DualWield").getScore(player.getName());

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
				playerData.setStat(Stat.DUAL_WIELD, 1);
			} else {
				playerData.setStat(Stat.DUAL_WIELD, 0);
			}
		}
	}

}
