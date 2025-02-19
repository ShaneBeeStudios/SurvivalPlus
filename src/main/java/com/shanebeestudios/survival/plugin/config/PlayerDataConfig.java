package com.shanebeestudios.survival.plugin.config;

import com.shanebeestudios.survival.api.data.PlayerData;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerDataConfig {

    private final SurvivalPlugin plugin;
    private File playerDirectory = null;

    public PlayerDataConfig(SurvivalPlugin plugin) {
        this.plugin = plugin;
        loadPlayerDirectory();
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

    public boolean hasPlayerDataFile(OfflinePlayer player) {
        File file = new File(playerDirectory, player.getUniqueId() + ".yml");
        return file.exists();
    }

    public PlayerData getPlayerDataFromFile(OfflinePlayer player) {
        File file = new File(playerDirectory, player.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        return ((PlayerData) config.get("player-data"));
    }

    public void savePlayerDataToFile(PlayerData playerData) {
        File file = new File(playerDirectory, playerData.getUuid().toString() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("player-data", playerData);
        saveFile(config, file);
    }

    private void saveFile(YamlConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
