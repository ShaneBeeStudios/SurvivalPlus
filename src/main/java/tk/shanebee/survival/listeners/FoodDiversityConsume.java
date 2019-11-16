package tk.shanebee.survival.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.Nutrient;
import tk.shanebee.survival.data.Nutrition;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;

class FoodDiversityConsume implements Listener {

	private PlayerManager playerManager;

	FoodDiversityConsume(Survival plugin) {
		this.playerManager = plugin.getPlayerManager();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onConsume(PlayerItemConsumeEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		Material material = event.getItem().getType();

		Nutrition nutrition = Nutrition.getByMaterial(material);
		if (nutrition != null) {
			addStats(player, nutrition);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onConsumeCake(PlayerInteractEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		if (event.hasBlock() && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block cake = event.getClickedBlock();
			assert cake != null;
			if (cake.getType().equals(Material.CAKE)) {
				if (player.getFoodLevel() < 20 && (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)) {
					addStats(player, Nutrition.CAKE);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onDamage(EntityDamageEvent e) {
		if (e.isCancelled()) return;
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();

			e.setDamage(e.getDamage() * addMultiplier(player));
		}
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		switch (player.getWorld().getDifficulty()) {
			case PEACEFUL:
			case EASY:
				setStats(player, 960, 240, 360);
				break;
			case NORMAL:
				setStats(player, 480, 120, 180);
				break;
			case HARD:
				setStats(player, 96, 24, 36);
				break;
		}
	}

	private void addStats(Player player, Nutrient nutrient, int point) {
		PlayerData playerData = playerManager.getPlayerData(player);
		playerData.setNutrient(nutrient, playerData.getNutrient(nutrient) + point);
	}

	private void addStats(Player player, Nutrition nutrition) {
		addStats(player, Nutrient.CARBS, nutrition.getCarbs());
		addStats(player, Nutrient.PROTEIN, nutrition.getProteins());
		addStats(player, Nutrient.SALTS, nutrition.getVitamins());
	}

	private void setStats(Player player, int carbs, int proteins, int vitamins) {
		PlayerData playerData = playerManager.getPlayerData(player);
		playerData.setNutrient(Nutrient.CARBS, carbs);
		playerData.setNutrient(Nutrient.PROTEIN, proteins);
		playerData.setNutrient(Nutrient.SALTS, vitamins);
	}

	private double addMultiplier(Player player) {
		PlayerData playerData = playerManager.getPlayerData(player);
		double damageMultiplier = 1;

		if (playerData.getNutrient(Nutrient.PROTEIN) <= 75) {
			switch (player.getWorld().getDifficulty()) {
				case EASY:
					damageMultiplier *= 1.25;
					break;
				case NORMAL:
					damageMultiplier *= 1.5;
					break;
				case HARD:
					damageMultiplier *= 2;
					break;
				default:
			}
		}
		if (playerData.getNutrient(Nutrient.SALTS) <= 100) {
			switch (player.getWorld().getDifficulty()) {
				case EASY:
					damageMultiplier *= 1.25;
					break;
				case NORMAL:
					damageMultiplier *= 1.5;
					break;
				case HARD:
					damageMultiplier *= 2;
					break;
				default:
			}
		}
		return damageMultiplier;
	}

}
