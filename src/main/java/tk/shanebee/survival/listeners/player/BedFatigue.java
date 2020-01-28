package tk.shanebee.survival.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.events.FatigueLevelChangeEvent;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.managers.PlayerManager;

public class BedFatigue implements Listener {

	private Survival plugin;
	private PlayerManager playerManager;

	public BedFatigue(Survival plugin) {
		this.plugin = plugin;
		this.playerManager = plugin.getPlayerManager();
	}

	@EventHandler
	private void onBedLeave(PlayerBedLeaveEvent e) {
		long time = e.getBed().getWorld().getTime();
		if (time < 1100) {
			Player player = e.getPlayer();
			PlayerData playerData = playerManager.getPlayerData(player);

			FatigueLevelChangeEvent fatigueEvent = new FatigueLevelChangeEvent(player, playerData.getFatigue(), 0);
			Bukkit.getPluginManager().callEvent(fatigueEvent);
			if (fatigueEvent.isCancelled()) return;
			playerData.setFatigue(0);
		}
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		PlayerData playerData = playerManager.getPlayerData(player);

		FatigueLevelChangeEvent fatigueEvent = new FatigueLevelChangeEvent(player, playerData.getFatigue(), 0);
		Bukkit.getPluginManager().callEvent(fatigueEvent);
		if (fatigueEvent.isCancelled()) return;
		playerData.setFatigue(0);
	}

	@EventHandler
	private void onDrinkCoffee(PlayerItemConsumeEvent e) {
		ItemStack item = e.getItem();
		Player player = e.getPlayer();
		PlayerData playerData = playerManager.getPlayerData(player);

		if (ItemManager.compare(item, Items.COFFEE)) {
			FatigueLevelChangeEvent fatigueEvent = new FatigueLevelChangeEvent(player, playerData.getFatigue(), 0);
			Bukkit.getPluginManager().callEvent(fatigueEvent);
			if (fatigueEvent.isCancelled()) return;
			playerData.setFatigue(0);
		}
	}

	// Removes empty water bottles from crafting grid when brewing coffee
	@EventHandler
	private void onCraftCoffee(CraftItemEvent e) {
		if (ItemManager.compare(e.getRecipe().getResult(), Items.COFFEE)) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> e.getInventory().remove(Material.GLASS_BOTTLE), 2);
		}
	}

}