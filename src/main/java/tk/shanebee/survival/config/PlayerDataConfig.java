package tk.shanebee.survival.config;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;

import java.io.File;
import java.io.IOException;

public class PlayerDataConfig {

	private Survival plugin;
	private File playerDirectory = null;
	private PlayerManager playerManager;

	public PlayerDataConfig(Survival plugin) {
		this.plugin = plugin;
		loadPlayerDirectory();
		this.playerManager = plugin.getPlayerManager();
	}

	private void loadPlayerDirectory() {
		if (playerDirectory == null) {
			playerDirectory = new File(plugin.getDataFolder(), "playerData");
		}
		if (!playerDirectory.exists()) {
			//noinspection ResultOfMethodCallIgnored
			playerDirectory.mkdir();
		}
	}

	@SuppressWarnings("ConstantConditions")
	public boolean needsConversion() {
		int offlineSize = Bukkit.getOfflinePlayers().length;
		long fileSize = playerDirectory.listFiles().length;
		return offlineSize > 0 && offlineSize > fileSize;
	}

	public boolean hasPlayerDataFile(OfflinePlayer player) {
		File file = new File(playerDirectory, player.getUniqueId().toString() + ".yml");
		return file.exists();
	}

	public PlayerData getPlayerDataFromFile(OfflinePlayer player) {
		File file = new File(playerDirectory, player.getUniqueId().toString() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		return ((PlayerData) config.get("player-data"));
	}

	public void savePlayerDataToFile(PlayerData playerData) {
		File file = new File(playerDirectory, playerData.getUuid().toString() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("player-data", playerData);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
