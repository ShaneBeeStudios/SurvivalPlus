package tk.shanebee.survival.util;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.survival.Survival;

import java.io.File;

public class Config {

	private Survival plugin;
	private FileConfiguration settings;

	public boolean breakOnlyWithSickle;
	public boolean breakOnlyWithShovel;
	public boolean breakOnlyWithAxe;
	public boolean breakOnlyWithPickaxe;
	public boolean breakOnlyWithShears;

	public double dropRate_stick;
	public double dropRate_flint;


	public Config(Survival plugin, FileConfiguration settings) {
		this.plugin = plugin;
		this.settings = settings;
		loadDefaultSettings();
	}

	private void loadDefaultSettings() {
		plugin.saveDefaultConfig();
		settings = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
		boolean change = false;
		Configuration defaults = plugin.getConfig().getDefaults();
		assert defaults != null;
		for (String defaultKey : defaults.getKeys(true)) {
			if (!settings.contains(defaultKey)) {
				settings.set(defaultKey, defaults.get(defaultKey));
				change = true;
			}
		}
		if (change) {
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveConfig();
		}
		updateResourcePack();
		settings = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
		loadSettings();
		Survival.settings = settings;
	}

	private void updateResourcePack() {
		if (settings.getString("MultiWorld.ResourcePackURL")
				.equalsIgnoreCase("https://shanebee.tk/survivalplus/resource-pack/SP-1.14v2.zip")) {
			plugin.getConfig().set("MultiWorld.ResourcePackURL", "https://shanebee.tk/survivalplus/resource-pack/SP-1.14v3.zip");
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveConfig();
		}
	}

	private void loadSettings() {
		this.breakOnlyWithSickle = settings.getBoolean("Survival.BreakOnlyWith.Sickle");
		this.breakOnlyWithShovel = settings.getBoolean("Survival.BreakOnlyWith.Shovel");
		this.breakOnlyWithAxe = settings.getBoolean("Survival.BreakOnlyWith.Axe");
		this.breakOnlyWithPickaxe = settings.getBoolean("Survival.BreakOnlyWith.Pickaxe");
		this.breakOnlyWithShears = settings.getBoolean("Survival.BreakOnlyWith.Shears");

		this.dropRate_stick = settings.getDouble("Survival.DropRate.Stick");
		this.dropRate_flint = settings.getDouble("Survival.DropRate.Flint");
	}

}
