package tk.shanebee.survival.util;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.survival.Survival;

import java.io.File;

public class Config {

	private Survival plugin;
	private FileConfiguration settings;

	public String RESOURCE_PACK_URL;

	public boolean SURVIVAL_ENABLED;
	public boolean SURVIVAL_TORCH;

	public boolean BREAK_ONLY_WITH_SICKLE;
	public boolean BREAK_ONLY_WITH_SHOVEL;
	public boolean BREAK_ONLY_WITH_AXE;
	public boolean BREAK_ONLY_WITH_PICKAXE;
	public boolean BREAK_ONLY_WITH_SHEARS;
	public boolean PLACE_ONLY_WITH_HAMMER;

	public double DROP_RATE_STICK;
	public double DROP_RATE_FLINT;

	public boolean STATUS_SCOREBOARD;

	public int THIRST_START_AMOUNT;
	public int THIRST_RESPAWN_AMOUNT;
	public boolean THIRST_PURIFY_WATER;
	public int HUNGER_START_AMOUNT;
	public int HUNGER_RESPAWN_AMOUNT;

	public boolean MECHANICS_SCOREBOARD;

	public boolean MECHANICS_REDUCED_IRON_NUGGET;
	public boolean MECHANICS_REDUCED_GOLD_NUGGET;
	public boolean MECHANICS_SLOW_ARMOR;
	public boolean MECHANICS_SNOWBALL_REVAMP;
	public boolean MECHANICS_MEDIC_KIT;

	public boolean MECHANICS_FARMING_PRODUCTS_COOKIE;
	public boolean MECHANICS_FARMING_PRODUCTS_BREAD;

	public boolean MECHANICS_THIRST_ENABLED;
	public double MECHANICS_THIRST_DRAIN_RATE;

	public boolean MECHANICS_FOOD_DIVERSITY;

	public boolean MECHANICS_FATIGUE_LEVEL;
	public int MECHANICS_FATIGUE_INCREASE_CHANCE;
	public int MECHANICS_FATIGUE_REFRESH_TIME;

	public boolean RECIPES_FISHING_ROD;
	public boolean RECIPES_GRANITE;
	public boolean RECIPES_ANDESITE;
	public boolean RECIPES_DIORITE;
	public boolean RECIPES_LEATHER_BARD;
	public boolean RECIPES_FURNACE;
	public boolean RECIPES_WORKBENCH;

	public boolean LEGENDARY_GOLDARMORBUFF;
	public boolean LEGENDARY_BLAZESWORD;
	public boolean LEGENDARY_GIANTBLADE;
	public boolean LEGENDARY_QUARTZPICKAXE;
	public boolean LEGENDARY_VALKYRIE;
	public boolean LEGENDARY_OBSIDIAN_MACE;


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
				.equalsIgnoreCase("https://shanebee.tk/survivalplus/resource-pack/SP-1.14v2.zip") || settings.getString("MultiWorld.ResourcePackURL")
				.equalsIgnoreCase("https://shanebee.tk/survivalplus/resource-pack/SP-1.14v3.zip")) {
			plugin.getConfig().set("MultiWorld.ResourcePackURL", "https://www.dropbox.com/s/hucla3rj6qs93zd/SP-1.14v3.zip?dl=1");
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveConfig();
		}
	}

	private void loadSettings() {

		this.RESOURCE_PACK_URL = settings.getString("MultiWorld.ResourcePackURL");
		this.SURVIVAL_ENABLED = settings.getBoolean("Survival.Enabled");
		this.SURVIVAL_TORCH = settings.getBoolean("Survival.Torch");

		this.BREAK_ONLY_WITH_SICKLE = settings.getBoolean("Survival.BreakOnlyWith.Sickle");
		this.BREAK_ONLY_WITH_SHOVEL = settings.getBoolean("Survival.BreakOnlyWith.Shovel");
		this.BREAK_ONLY_WITH_AXE = settings.getBoolean("Survival.BreakOnlyWith.Axe");
		this.BREAK_ONLY_WITH_PICKAXE = settings.getBoolean("Survival.BreakOnlyWith.Pickaxe");
		this.BREAK_ONLY_WITH_SHEARS = settings.getBoolean("Survival.BreakOnlyWith.Shears");
		this.PLACE_ONLY_WITH_HAMMER = settings.getBoolean("Survival.PlaceOnlyWith.Hammer");

		this.DROP_RATE_STICK = settings.getDouble("Survival.DropRate.Stick");
		this.DROP_RATE_FLINT = settings.getDouble("Survival.DropRate.Flint");

		this.STATUS_SCOREBOARD = settings.getBoolean("Mechanics.StatusScoreboard");

		this.THIRST_START_AMOUNT = settings.getInt("Mechanics.Thirst.Starting-Amount");
		this.THIRST_RESPAWN_AMOUNT = settings.getInt("Mechanics.Thirst.Respawn-Amount");
		this.THIRST_PURIFY_WATER = settings.getBoolean("Mechanics.Thirst.PurifyWater");
		this.HUNGER_START_AMOUNT = settings.getInt("Mechanics.Hunger.Starting-Amount");
		this.HUNGER_RESPAWN_AMOUNT = settings.getInt("Mechanics.Hunger.Respawn-Amount");

		this.MECHANICS_SCOREBOARD = settings.getBoolean("Mechanics.StatusScoreboard");
		this.MECHANICS_REDUCED_IRON_NUGGET = settings.getBoolean("Mechanics.ReducedIronNugget");
		this.MECHANICS_REDUCED_GOLD_NUGGET = settings.getBoolean("Mechanics.ReducedGoldNugget");
		this.MECHANICS_SLOW_ARMOR = settings.getBoolean("Mechanics.SlowArmor");
		this.MECHANICS_SNOWBALL_REVAMP = settings.getBoolean("Mechanics.SnowballRevamp");
		this.MECHANICS_MEDIC_KIT = settings.getBoolean("Mechanics.MedicalKit");

		this.MECHANICS_FARMING_PRODUCTS_COOKIE = settings.getBoolean("Mechanics.FarmingProducts.Cookie");
		this.MECHANICS_FARMING_PRODUCTS_BREAD = settings.getBoolean("Mechanics.FarmingProducts.Bread");

		this.MECHANICS_THIRST_ENABLED = settings.getBoolean("Mechanics.Thirst.Enabled");
		this.MECHANICS_THIRST_DRAIN_RATE = settings.getDouble("Mechanics.Thirst.DrainRate");

		this.MECHANICS_FOOD_DIVERSITY = settings.getBoolean("Mechanics.FoodDiversity");

		this.MECHANICS_FATIGUE_LEVEL = settings.getBoolean("Mechanics.BedFatigueLevel");
		this.MECHANICS_FATIGUE_INCREASE_CHANCE = settings.getInt("Mechanics.FatigueLevelIncreaseChance");
		this.MECHANICS_FATIGUE_REFRESH_TIME = settings.getInt("Mechanics.BedFatigueRefreshTime");

		this.RECIPES_FISHING_ROD = settings.getBoolean("Recipes.FishingRod");
		this.RECIPES_GRANITE = settings.getBoolean("Recipes.Granite");
		this.RECIPES_ANDESITE = settings.getBoolean("Recipes.Andesite");
		this.RECIPES_DIORITE = settings.getBoolean("Recipes.Diorite");
		this.RECIPES_LEATHER_BARD = settings.getBoolean("Recipes.LeatherBard");
		this.RECIPES_FURNACE = settings.getBoolean("Recipes.Furnace");
		this.RECIPES_WORKBENCH = settings.getBoolean("Recipes.Workbench");

		this.LEGENDARY_GOLDARMORBUFF = settings.getBoolean("LegendaryItems.GoldArmorBuff");
		this.LEGENDARY_BLAZESWORD = settings.getBoolean("LegendaryItems.BlazeSword");
		this.LEGENDARY_GIANTBLADE = settings.getBoolean("LegendaryItems.GiantBlade");
		this.LEGENDARY_QUARTZPICKAXE = settings.getBoolean("LegendaryItems.QuartzPickaxe");
		this.LEGENDARY_VALKYRIE = settings.getBoolean("LegendaryItems.ValkyrieAxe");
		this.LEGENDARY_OBSIDIAN_MACE = settings.getBoolean("LegendaryItems.ObsidianMace");
	}

}
