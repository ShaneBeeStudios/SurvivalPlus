package tk.shanebee.survival.tasks.tool;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.item.Items;

public class QuartzPickaxe extends BukkitRunnable {

	private final SurvivalPlugin plugin;

	public QuartzPickaxe(SurvivalPlugin plugin) {
		this.plugin = plugin;
		this.runTaskTimer(plugin, 1, 10);
	}

	@Override
	public void run() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (Items.QUARTZ_PICKAXE.is(player.getInventory().getItemInMainHand())) {
				player.removePotionEffect(PotionEffectType.HASTE);
				player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 20, 9, false));
			}
		}
	}

}
