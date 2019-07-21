package tk.shanebee.survival.util;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.survival.Survival;

import java.io.File;

public class Config {

	private Survival plugin;
	private FileConfiguration settings;

	public boolean BREAK_ONLY_WITH_SICKLE;
	public boolean BREAK_ONLY_WITH_SHOVEL;
	public boolean BREAK_ONLY_WITH_AXE;
	public boolean BREAK_ONLY_WITH_PICKAXE;
	public boolean BREAK_ONLY_WITH_SHEARS;

	public double DROP_RATE_STICK;
	public double DROP_RATE_FLINT;

	public boolean STATUS_SCOREBOARD;

	public int THIRST_START_AMOUNT;
	public int THIRST_RESPAWN_AMOUNT;
	public boolean THIRST_PURIFY_WATER;
	public int HUNGER_START_AMOUNT;
	public int HUNGER_RESPAWN_AMOUNT;


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
		this.BREAK_ONLY_WITH_SICKLE = settings.getBoolean("Survival.BreakOnlyWith.Sickle");
		this.BREAK_ONLY_WITH_SHOVEL = settings.getBoolean("Survival.BreakOnlyWith.Shovel");
		this.BREAK_ONLY_WITH_AXE = settings.getBoolean("Survival.BreakOnlyWith.Axe");
		this.BREAK_ONLY_WITH_PICKAXE = settings.getBoolean("Survival.BreakOnlyWith.Pickaxe");
		this.BREAK_ONLY_WITH_SHEARS = settings.getBoolean("Survival.BreakOnlyWith.Shears");

		this.DROP_RATE_STICK = settings.getDouble("Survival.DropRate.Stick");
		this.DROP_RATE_FLINT = settings.getDouble("Survival.DropRate.Flint");

		this.STATUS_SCOREBOARD = settings.getBoolean("Mechanics.StatusScoreboard");

		this.THIRST_START_AMOUNT = settings.getInt("Mechanics.Thirst.Starting-Amount");
		this.THIRST_RESPAWN_AMOUNT = settings.getInt("Mechanics.Thirst.Respawn-Amount");
		this.THIRST_PURIFY_WATER = settings.getBoolean("Mechanics.Thirst.PurifyWater");
		this.HUNGER_START_AMOUNT = settings.getInt("Mechanics.Hunger.Starting-Amount");
		this.HUNGER_RESPAWN_AMOUNT = settings.getInt("Mechanics.Hunger.Respawn-Amount");
	}

}
