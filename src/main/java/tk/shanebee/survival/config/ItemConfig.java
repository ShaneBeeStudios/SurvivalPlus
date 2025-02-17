package tk.shanebee.survival.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.item.Nutrition;
import tk.shanebee.survival.util.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ItemConfig {

    public static ItemConfig INSTANCE;
    private final SurvivalPlugin plugin = SurvivalPlugin.getInstance();
    private FileConfiguration settings;
    private File configFile;

    public ItemConfig() {
        INSTANCE = this;
        loadDefaultSettings();
        Nutrition.setup();
        save();
        Utils.logMini("<grey>items.yml <green>loaded");
    }

    private void loadDefaultSettings() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "items.yml");
        }
        if (!configFile.exists()) {
            plugin.saveResource("items.yml", false);
            settings = YamlConfiguration.loadConfiguration(configFile);
            Utils.logMini("<green>New items.yml created");
        } else {
            settings = YamlConfiguration.loadConfiguration(configFile);
        }
        matchConfig(this.settings, this.configFile);
    }

    private void matchConfig(FileConfiguration config, File file) {
        try {
            boolean hasUpdated = false;
            InputStream is = plugin.getResource(file.getName());
            assert is != null;
            InputStreamReader isr = new InputStreamReader(is);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(isr);
            for (String key : defConfig.getConfigurationSection("").getKeys(true)) {
                if (!config.contains(key)) {
                    config.set(key, defConfig.get(key));
                    hasUpdated = true;
                }
            }
            if (hasUpdated)
                config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getNutritionValues(String key, int carbs, int proteins, int vitamins) {
        String path = "nutritions." + key + ".";
        String[] paths = new String[]{path + "carbs", path + "proteins", path + "vitamins"};
        int[] nutritions = new int[]{carbs, proteins, vitamins};
        if (settings.contains(paths[0])) {
            nutritions[0] = settings.getInt(paths[0]);
        } else {
            settings.set(paths[0], carbs);
        }
        if (settings.contains(paths[1])) {
            nutritions[1] = settings.getInt(paths[1]);
        } else {
            settings.set(paths[1], proteins);
        }
        if (settings.contains(paths[2])) {
            nutritions[2] = settings.getInt(paths[2]);
        } else {
            settings.set(paths[2], vitamins);
        }
        return nutritions;
    }

    public String getName(String key) {
        return this.settings.getString("items." + key + ".name");
    }

    public List<String> getLore(String key) {
        String path = "items." + key + ".lore";
        if (this.settings.contains(path)) {
            return this.settings.getStringList(path);
        }
        return null;
    }

    public int getMaxDamage(String key) {
        String path = "items." + key + ".max_damage";
        if (this.settings.contains(path)) {
            return this.settings.getInt(path);
        }
        return 0;
    }

    public int getRepairCost(String key) {
        String path = "items." + key + ".repair_cost";
        if (this.settings.contains(path)) {
            return this.settings.getInt(path);
        }
        return 0;
    }

    public double getRepairPercent(String key) {
        String path = "items." + key + ".repair_percent";
        if (this.settings.contains(path)) {
            return this.settings.getDouble(path);
        }
        return 0;
    }

    public int getColor(String key) {
        String path = "items." + key + ".color";
        if (this.settings.contains(path)) {
            return this.settings.getInt(path);
        }
        return 0;
    }

    public int getInt(String itemKey, String valueKey, int defaultValue) {
        String path = "items." + itemKey + "." + valueKey;
        if (this.settings.contains(path)) {
            return this.settings.getInt(path);
        }
        return defaultValue;
    }

    public double getDouble(String itemKey, String valueKey, double defaultValue) {
        String path = "items." + itemKey + "." + valueKey;
        if (this.settings.contains(path)) {
            return this.settings.getDouble(path);
        }
        return defaultValue;
    }

    public String getString(String itemKey, String valueKey, String defaultValue) {
        String path = "items." + itemKey + "." + valueKey;
        if (this.settings.contains(path)) {
            return this.settings.getString(path);
        }
        return defaultValue;
    }

    void save() {
        try {
            settings.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
