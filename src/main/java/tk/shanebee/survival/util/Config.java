package tk.shanebee.survival.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.survival.Survival;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {

	private Survival plugin;
	private FileConfiguration settings;
	private File configFile;
	private String prefix;

	@SuppressWarnings("WeakerAccess")
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
	public int MECHANICS_FOOD_EFFECTS_CARBS_EX_AMP_EASY;
	public int MECHANICS_FOOD_EFFECTS_CARBS_EX_AMP_MEDIUM;
	public int MECHANICS_FOOD_EFFECTS_CARBS_EX_AMP_HARD;
	public int MECHANICS_FOOD_EFFECTS_SALTS_EX_AMP;
	public String MECHANICS_FOOD_EFFECTS_SALTS_SE_NORMAL_EFFECT;
	public int MECHANICS_FOOD_EFFECTS_SALTS_SE_NORMAL_AMP;
	public int MECHANICS_FOOD_EFFECTS_SALTS_SE_NORMAL_DURATION;
	public String MECHANICS_FOOD_EFFECTS_SALTS_SE_HARD_EFFECT;
	public int MECHANICS_FOOD_EFFECTS_SALTS_SE_HARD_AMP;
	public int MECHANICS_FOOD_EFFECTS_SALTS_SE_HARD_DURATION;
	public int MECHANICS_FOOD_EFFECTS_PROTEIN_EX_AMP;
	public String MECHANICS_FOOD_EFFECTS_PROTEIN_SE_NORMAL_EFFECT;
	public int MECHANICS_FOOD_EFFECTS_PROTEIN_SE_NORMAL_AMP;
	public int MECHANICS_FOOD_EFFECTS_PROTEIN_SE_NORMAL_DURATION;
	public String MECHANICS_FOOD_EFFECTS_PROTEIN_SE_HARD_EFFECT;
	public int MECHANICS_FOOD_EFFECTS_PROTEIN_SE_HARD_AMP;
	public int MECHANICS_FOOD_EFFECTS_PROTEIN_SE_HARD_DURATION;

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

	public boolean ENTITY_MECHANICS_PIGMEN_CHEST_ENABLED;
	public int ENTITY_MECHANICS_PIGMEN_CHEST_RADIUS;
	public double ENTITY_MECHANICS_PIGMEN_CHEST_SPEED;


	public Config(Survival plugin) {
		this.plugin = plugin;
		this.prefix = "&7[&3Survival&bPlus&7] "; //temp prefix used before lang.yml loads
		loadDefaultSettings();
	}

	private void loadDefaultSettings() {
		if (configFile == null) {
			configFile = new File(plugin.getDataFolder(), "config.yml");
		}
		if (!configFile.exists()) {
			plugin.saveResource("config.yml", false);
			settings = YamlConfiguration.loadConfiguration(configFile);
			Utils.sendColoredConsoleMsg(prefix + "new config.yml created");
		} else {
			settings = YamlConfiguration.loadConfiguration(configFile);
		}
		matchConfig(settings, configFile);
		loadSettings();
		Utils.sendColoredConsoleMsg(prefix + "&7config.yml &aloaded");
	}

	// Used to update config
	@SuppressWarnings("ConstantConditions")
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
			for (String key : config.getConfigurationSection("").getKeys(true)) {
				if (!defConfig.contains(key)) {
					config.set(key, null);
					hasUpdated = true;
				}
			}
			if (hasUpdated)
				config.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FileConfiguration getSettings() {
		return this.settings;
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

		this.MECHANICS_FOOD_DIVERSITY = settings.getBoolean("Mechanics.FoodDiversity.enabled");
		this.MECHANICS_FOOD_EFFECTS_CARBS_EX_AMP_EASY = settings.getInt("Mechanics.FoodDiversity.effects.carbs.exhaustion-amplifier.easy");
		this.MECHANICS_FOOD_EFFECTS_CARBS_EX_AMP_MEDIUM = settings.getInt("Mechanics.FoodDiversity.effects.carbs.exhaustion-amplifier.normal");
		this.MECHANICS_FOOD_EFFECTS_CARBS_EX_AMP_HARD = settings.getInt("Mechanics.FoodDiversity.effects.carbs.exhaustion-amplifier.hard");
		this.MECHANICS_FOOD_EFFECTS_SALTS_EX_AMP = settings.getInt("Mechanics.FoodDiversity.effects.salts.exhaustion-amplifier");
		this.MECHANICS_FOOD_EFFECTS_SALTS_SE_NORMAL_EFFECT = settings.getString("Mechanics.FoodDiversity.effects.salts.status-effects.normal.effect");
		this.MECHANICS_FOOD_EFFECTS_SALTS_SE_NORMAL_AMP = settings.getInt("Mechanics.FoodDiversity.effects.salts.status-effects.normal.amplifier");
		this.MECHANICS_FOOD_EFFECTS_SALTS_SE_NORMAL_DURATION = settings.getInt("Mechanics.FoodDiversity.effects.salts.status-effects.normal.duration");
		this.MECHANICS_FOOD_EFFECTS_SALTS_SE_HARD_EFFECT = settings.getString("Mechanics.FoodDiversity.effects.salts.status-effects.hard.effect");
		this.MECHANICS_FOOD_EFFECTS_SALTS_SE_HARD_AMP = settings.getInt("Mechanics.FoodDiversity.effects.salts.status-effects.hard.amplifier");
		this.MECHANICS_FOOD_EFFECTS_SALTS_SE_HARD_DURATION = settings.getInt("Mechanics.FoodDiversity.effects.salts.status-effects.hard.duration");

		this.MECHANICS_FOOD_EFFECTS_PROTEIN_EX_AMP = settings.getInt("Mechanics.FoodDiversity.effects.proteins.exhaustion-amplifier");
		this.MECHANICS_FOOD_EFFECTS_PROTEIN_SE_NORMAL_EFFECT = settings.getString("Mechanics.FoodDiversity.effects.proteins.status-effects.normal.effect");
		this.MECHANICS_FOOD_EFFECTS_PROTEIN_SE_NORMAL_AMP = settings.getInt("Mechanics.FoodDiversity.effects.proteins.status-effects.normal.amplifier");
		this.MECHANICS_FOOD_EFFECTS_PROTEIN_SE_NORMAL_DURATION = settings.getInt("Mechanics.FoodDiversity.effects.proteins.status-effects.normal.duration");
		this.MECHANICS_FOOD_EFFECTS_PROTEIN_SE_HARD_EFFECT = settings.getString("Mechanics.FoodDiversity.effects.proteins.status-effects.hard.effect");
		this.MECHANICS_FOOD_EFFECTS_PROTEIN_SE_HARD_AMP = settings.getInt("Mechanics.FoodDiversity.effects.proteins.status-effects.hard.amplifier");
		this.MECHANICS_FOOD_EFFECTS_PROTEIN_SE_HARD_DURATION = settings.getInt("Mechanics.FoodDiversity.effects.proteins.status-effects.hard.duration");

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

		this.ENTITY_MECHANICS_PIGMEN_CHEST_ENABLED = settings.getBoolean("Entity-Mechanics.pigmen-chests.enabled");
		this.ENTITY_MECHANICS_PIGMEN_CHEST_RADIUS = settings.getInt("Entity-Mechanics.pigmen-chests.distance");
		this.ENTITY_MECHANICS_PIGMEN_CHEST_SPEED = settings.getDouble("Entity-Mechanics.pigmen-chests.speed-modifier");
	}

}
