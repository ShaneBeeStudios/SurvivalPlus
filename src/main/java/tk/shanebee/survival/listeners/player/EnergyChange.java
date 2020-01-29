package tk.shanebee.survival.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.events.EnergyLevelChangeEvent;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.util.Utils;

public class EnergyChange implements Listener {

	private Survival plugin;
	private PlayerManager playerManager;
	private Config config;
	private Lang lang;

	public EnergyChange(Survival plugin) {
		this.plugin = plugin;
		this.playerManager = plugin.getPlayerManager();
		this.config = plugin.getSurvivalConfig();
		this.lang = plugin.getLang();
	}

	@EventHandler
	private void onBedLeave(PlayerBedLeaveEvent e) {
		long time = e.getBed().getWorld().getTime();
		if (time < 1100) {
			Player player = e.getPlayer();
			PlayerData playerData = playerManager.getPlayerData(player);

			EnergyLevelChangeEvent energyEvent = new EnergyLevelChangeEvent(player, 20.0 - playerData.getEnergy(), 20.0);
			Bukkit.getPluginManager().callEvent(energyEvent);
			if (energyEvent.isCancelled()) return;
			playerData.setEnergy(20);
		}
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		PlayerData playerData = playerManager.getPlayerData(player);

		EnergyLevelChangeEvent energyEvent = new EnergyLevelChangeEvent(player, 20.0 - playerData.getEnergy(), 20.0);
		Bukkit.getPluginManager().callEvent(energyEvent);
		if (energyEvent.isCancelled()) return;
        playerData.setEnergy(20);
	}

	@EventHandler
	private void onDrinkCoffee(PlayerItemConsumeEvent e) {
		ItemStack item = e.getItem();
		Player player = e.getPlayer();
		PlayerData playerData = playerManager.getPlayerData(player);

		if (ItemManager.compare(item, Items.COFFEE)) {
			EnergyLevelChangeEvent energyEvent = new EnergyLevelChangeEvent(player, 20.0 - playerData.getEnergy(), 20.0);
			Bukkit.getPluginManager().callEvent(energyEvent);
			if (energyEvent.isCancelled()) return;
            playerData.setEnergy(20);
		}
	}

	// Removes empty water bottles from crafting grid when brewing coffee
	@EventHandler
	private void onCraftCoffee(CraftItemEvent e) {
		if (ItemManager.compare(e.getRecipe().getResult(), Items.COFFEE)) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> e.getInventory().remove(Material.GLASS_BOTTLE), 2);
		}
	}

	// Decrease energy when player does exhaustive tasks
	@EventHandler
    private void onSaturationReached(FoodLevelChangeEvent event) {
        double modifier = config.MECHANICS_ENERGY_EXHAUSTION;
        if (modifier <= 0) return;
	    if (!(event.getEntity() instanceof Player)) return;
	    Player player = (Player) event.getEntity();
	    if (event.getFoodLevel() > player.getFoodLevel()) return;
        GameMode mode = player.getGameMode();
	    if (mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE) {
            PlayerData playerData = playerManager.getPlayerData(player);
            EnergyLevelChangeEvent energyEvent = new EnergyLevelChangeEvent(player, -modifier, playerData.getEnergy() - modifier);
            Bukkit.getPluginManager().callEvent(energyEvent);
            if (energyEvent.isCancelled()) return;
            playerData.increaseEnergy(-modifier);
        }
    }

	// Send messages when energy level decreases
    @EventHandler
    private void onEnergyDrop(EnergyLevelChangeEvent event) {
	    if (!config.MECHANICS_ENERGY_WARNING) return;
	    if (event.getChanged() < 0) {
            Player player = event.getPlayer();
            PlayerData playerData = playerManager.getPlayerData(player);
            double level = event.getEnergyLevel();
            double newLevel = playerData.getEnergy();

	        if (targetMatch(10.0, level, newLevel)) {
                Utils.sendColoredMsg(player, lang.energy_level_10);
            } else if (targetMatch(6.5, level, newLevel)) {
                Utils.sendColoredMsg(player, lang.energy_level_6_5);
            } else if (targetMatch(3.5, level, newLevel)) {
                Utils.sendColoredMsg(player, lang.energy_level_3_5);
            } else if (targetMatch(2, level, newLevel)) {
                Utils.sendColoredMsg(player, lang.energy_level_2);
            } else if (targetMatch(1, level, newLevel)) {
                Utils.sendColoredMsg(player, lang.energy_level_1);
            }
        }
    }

    // Check if the change passed a certain amount
    private boolean targetMatch(double target, double level, double newLevel) {
	    return level <= target && newLevel > target;
    }

    @EventHandler
    private void onSkipNight(TimeSkipEvent event) {
	    if (event.getSkipReason() == TimeSkipEvent.SkipReason.NIGHT_SKIP) event.setCancelled(true);
    }

}