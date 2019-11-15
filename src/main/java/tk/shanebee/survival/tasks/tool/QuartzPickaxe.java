package tk.shanebee.survival.tasks.tool;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;

public class QuartzPickaxe extends BukkitRunnable {

	private Survival plugin;
	private int id;

	public QuartzPickaxe(Survival plugin) {
		this.plugin = plugin;
		this.id = this.runTaskTimer(plugin, 1, 10).getTaskId();
	}

	@Override
	public void run() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (ItemManager.compare(player.getInventory().getItemInMainHand(), Items.QUARTZ_PICKAXE)) {
				player.removePotionEffect(PotionEffectType.FAST_DIGGING);
				player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 9, false));
			}
		}
	}

}
