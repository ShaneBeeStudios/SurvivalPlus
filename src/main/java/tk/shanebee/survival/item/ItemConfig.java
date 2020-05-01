package tk.shanebee.survival.item;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Utils;

import java.io.File;
import java.io.IOException;

class ItemConfig {

    private final Survival plugin = Survival.getInstance();
    private FileConfiguration settings;
    private File configFile;
    private final String prefix = plugin.getLang().prefix;

    ItemConfig() {
        loadDefaultSettings();
    }

    private void loadDefaultSettings() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "items.yml");
        }
        if (!configFile.exists()) {
            plugin.saveResource("items.yml", false);
            settings = YamlConfiguration.loadConfiguration(configFile);
            Utils.sendColoredConsoleMsg(prefix + "&aNew items.yml created");
        } else {
            settings = YamlConfiguration.loadConfiguration(configFile);
        }
        Utils.sendColoredConsoleMsg(prefix + "&7items.yml &aloaded");
    }

    int getModelData(String key, int defaultValue) {
        int data;
        String path = "items." + key + ".model_data";
        if (settings.contains(path)) {
            data = settings.getInt(path);
        } else {
            data = defaultValue;
            settings.set(path, data);
            save();
        }
        return data;
    }

    void save() {
        try {
            settings.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
