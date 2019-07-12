package tk.shanebee.survival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.managers.StatusManager;

class BedFatigue implements Listener {

	@EventHandler
	private void onBedLeave(PlayerBedLeaveEvent e) {
		long time = e.getBed().getWorld().getTime();
		if (time % 24000 == 0) {
			Player player = e.getPlayer();
			StatusManager.setFatigue(player, 0);
		}
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		StatusManager.setFatigue(player, 0);
	}

	@EventHandler
	private void onFirstJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			StatusManager.setFatigue(player, 0);
		}
	}

	@EventHandler
	private void onDrinkCoffee(PlayerItemConsumeEvent e) {
		ItemStack item = e.getItem();
		if (ItemManager.compare(item, Items.COFFEE)) {
			StatusManager.setFatigue(e.getPlayer(), 0);
		}
	}

	// Removes empty water bottles from crafting grid when brewing coffee
	@EventHandler
	private void onCraftCoffee(CraftItemEvent e) {
		if (ItemManager.compare(e.getRecipe().getResult(), Items.COFFEE)) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Survival.instance, () -> e.getInventory().remove(Material.GLASS_BOTTLE), 2);
		}
	}

}