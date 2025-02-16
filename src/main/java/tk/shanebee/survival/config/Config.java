package tk.shanebee.survival.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.util.Utils;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Config {

    private final SurvivalPlugin plugin;
    private FileConfiguration settings;
    private File configFile;

    public String LANG;

    public String settings_resource_pack_url;
    public boolean settings_resource_pack_enabled;

    public int settings_local_chat_distance;

    public boolean WELCOME_GUIDE_ENABLED;
    public boolean WELCOME_GUIDE_NEW_PLAYERS;
    public int WELCOME_GUIDE_DELAY;

    // SURVIVAL
    public boolean SURVIVAL_ENABLED;
    public boolean SURVIVAL_LIMITED_CRAFTING;
    public boolean SURVIVAL_UNLOCK_ALL_RECIPES;
    public boolean SURVIVAL_REMOVE_WOOD_TOOLS;
    public boolean SURVIVAL_TORCH;
    public boolean SURVIVAL_UPDATE_MERCHANT_TRADES;

    public boolean BREAK_ONLY_WITH_SICKLE;
    public boolean BREAK_ONLY_WITH_SHOVEL;
    public boolean BREAK_ONLY_WITH_AXE;
    public boolean BREAK_ONLY_WITH_PICKAXE;
    public boolean BREAK_ONLY_WITH_SHEARS;
    public boolean PLACE_ONLY_WITH_HAMMER;

    public boolean SURVIVAL_SICKLE_FLINT;
    public boolean SURVIVAL_SICKLE_STONE;
    public boolean SURVIVAL_SICKLE_IRON;
    public boolean SURVIVAL_SICKLE_DIAMOND;

    public double DROP_RATE_STICK;
    public double DROP_RATE_FLINT;

    // MECHANICS
    public boolean MECHANICS_SHARED_WORKBENCH;
    public boolean MECHANICS_PREVENT_NIGHT_SKIP;

    // ENERGY
    public boolean mechanics_energy_enabled;
    public double mechanics_energy_start;
    public double mechanics_energy_respawn;
    public boolean MECHANICS_ENERGY_WARNING;
    public double MECHANICS_ENERGY_DRAIN_RATE;
    public double MECHANICS_ENERGY_DRAIN_COLD_RATE;
    public double MECHANICS_ENERGY_REFRESH_RATE_BED;
    public double MECHANICS_ENERGY_REFRESH_RATE_CHAIR;
    public double MECHANICS_ENERGY_EXHAUSTION;
    public boolean MECHANICS_ENERGY_COFFEE_ENABLED;
    public boolean MECHANICS_ENERGY_ABSORPTION;
    public boolean MECHANICS_ENERGY_HASTE;

    public boolean MECHANICS_SLOW_ARMOR;
    public boolean MECHANICS_REINFORCED_ARMOR;
    public boolean MECHANICS_BOW;
    public boolean MECHANICS_RECURVED_BOW;
    public boolean MECHANICS_GRAPPLING_HOOK;
    public boolean MECHANICS_MEDIC_KIT;
    public boolean MECHANICS_REDUCED_IRON_NUGGET;
    public boolean MECHANICS_REDUCED_GOLD_NUGGET;

    public boolean mechanics_status_scoreboard;
    public int MECHANICS_ALERT_INTERVAL;

    public boolean MECHANICS_RAW_MEAT_HUNGER;
    public boolean MECHANICS_EMPTY_POTION;
    public boolean MECHANICS_POISON_POTATO;
    public boolean MECHANICS_COOKIE_BOOST;
    public boolean MECHANICS_BEET_STRENGTH;

    public boolean mechanics_food_diversity_enabled;
    public int mechanics_food_max_level;
    public int mechanics_food_start_carbs;
    public int mechanics_food_start_protein;
    public int mechanics_food_start_vitamins;
    public int mechanics_food_respawn_proteins;
    public int mechanics_food_respawn_vitamins;
    public int mechanics_food_respawn_carbs;
    public int mechanics_food_effects_carbs_ex_amp_easy;
    public int mechanics_food_effects_carbs_ex_amp_medium;
    public int mechanics_food_effects_carbs_ex_amp_hard;
    public int mechanics_food_effects_vitamins_ex_amp;
    public String mechanics_food_effects_vitamins_se_normal_effect;
    public int mechanics_food_effects_vitamins_se_normal_amp;
    public int mechanics_food_effects_vitamins_se_normal_duration;
    public String mechanics_food_effects_vitamins_se_hard_effect;
    public int mechanics_food_effects_vitamins_se_hard_amp;
    public int mechanics_food_effects_vitamins_se_hard_duration;
    public int mechanics_food_effects_protein_ex_amp;
    public String mechanics_food_effects_protein_se_normal_effect;
    public int mechanics_food_effects_protein_se_normal_amp;
    public int mechanics_food_effects_protein_se_normal_duration;
    public String mechanics_food_effects_protein_se_hard_effect;
    public int mechanics_food_effects_protein_se_hard_amp;
    public int mechanics_food_effects_protein_se_hard_duration;

    // THIRST
    public boolean mechanics_thirst_enabled;
    public int mechanics_thirst_starting_amount;
    public int mechanics_thirst_respawn_amount;
    public boolean mechanics_thirst_purify_water;
    public boolean mechanics_thirst_melt_snow;
    public double mechanics_thirst_drain_rate;
    public int mechanics_thirst_heat_drain;
    public int mechanics_thirst_nether_drain;
    public double mechanics_thirst_damage_rate;
    public int mechanics_thirst_rep_beetroot_soup;
    public int mechanics_thirst_rep_melon_slice;
    public int mechanics_thirst_rep_mush_stew;
    public int mechanics_thirst_rep_water_bowl;
    public int mechanics_thirst_rep_dirty_water;
    public int mechanics_thirst_rep_clean_water;
    public int mechanics_thirst_rep_pure_water;
    public int mechanics_thirst_rep_coffee;
    public int mechanics_thirst_rep_cold_milk;
    public int mechanics_thirst_rep_hot_milk;
    public int mechanics_thirst_rep_milk_bucket;
    public int mechanics_thirst_rep_water;
    public int mechanics_thirst_rep_honey_bottle;
    public int mechanics_thirst_rep_other_water;

    public int mechanics_hunger_start_amount;
    public int mechanics_hunger_respawn_amount;

    public boolean MECHANICS_COMPASS_WAYPOINT;
    public boolean MECHANICS_COMPASS_WAYPOINT_WORLDS;
    public boolean MECHANICS_CLOWN_FISH;
    public boolean MECHANICS_FERMENTED_SKIN;
    public boolean MECHANICS_LIVING_SLIME;
    public boolean MECHANICS_SNOWBALL_REVAMP;
    public boolean MECHANICS_SNOW_GEN_REVAMP;

    public boolean MECHANICS_FARMING_PRODUCTS_COOKIE;
    public boolean MECHANICS_FARMING_PRODUCTS_BREAD;

    public boolean MECHANICS_CHAIRS_ENABLED;
    public int MECHANICS_CHAIRS_MAX_WIDTH;
    public List<String> MECHANICS_CHAIRS_BLOCKS;

    public boolean MECHANICS_BURNOUT_TORCH_ENABLED;
    public int MECHANICS_BURNOUT_TORCH_TIME;
    public boolean MECHANICS_BURNOUT_TORCH_RELIGHT;
    public boolean MECHANICS_BURNOUT_TORCH_DROP;
    public boolean MECHANICS_BURNOUT_TORCH_PERSIST;

    public boolean MECHANICS_WEATHER_ENABLED;
    public double MECHANICS_WEATHER_SPEED_BASE;
    public double MECHANICS_WEATHER_SPEED_RAIN;
    public double MECHANICS_WEATHER_SPEED_STORM;
    public double MECHANICS_WEATHER_SPEED_SNOW;
    public double MECHANICS_WEATHER_SPEED_SNOWSTORM;

    // ITEM MECHANICS
    public int ITEM_FIRESTRIKER_COOK_TIME;

    // ENTITY MECHANICS
    public boolean ENTITY_MECHANICS_PIGMEN_CHEST_ENABLED;
    public int ENTITY_MECHANICS_PIGMEN_CHEST_RADIUS;
    public double ENTITY_MECHANICS_PIGMEN_CHEST_SPEED;
    public boolean ENTITY_MECHANICS_BEEKEEPER_SUIT_ENABLED;
    public boolean ENTITY_MECHANICS_SUSPICIOUS_MEAT_ENABLED;
    public int ENTITY_MECHANICS_SUSPICIOUS_MEAT_CHANCE;
    public boolean ENTITY_MECHANICS_CHICKEN_BREEDING_ENABLED;
    public int ENTITY_MECHANICS_CHICKEN_BREEDING_MAX_EGGS;
    public boolean ENTITY_MECHANICS_CHICKEN_BREEDING_ALWAYS_BABY;
    public int ENTITY_MECHANICS_CHICKEN_BREEDING_BABY_TICKS;
    public boolean ENTITY_MECHANICS_PIGLIN_DROP_WATER;
    public boolean ENTITY_MECHANICS_PIGLIN_ALT_DROP;

    // RECIPES
    public boolean RECIPES_SADDLE;
    public boolean RECIPES_NAME_TAG;
    public boolean RECIPES_PACKED_ICE;
    public boolean RECIPES_LEATHER_BARD;
    public boolean RECIPES_IRON_BARD;
    public boolean RECIPES_GOLD_BARD;
    public boolean RECIPES_DIAMOND_BARD;
    public boolean RECIPES_CLAY_BRICK;
    public boolean RECIPES_QUARTZ_BLOCK;
    public boolean RECIPES_WOOL_STRING;
    public boolean RECIPES_WEB_STRING;
    public boolean RECIPES_ICE;
    public boolean RECIPES_CLAY;
    public boolean RECIPES_DIORITE;
    public boolean RECIPES_GRANITE;
    public boolean RECIPES_ANDESITE;
    public boolean RECIPES_GRAVEL;
    public boolean RECIPES_SLIMEBALL;
    public boolean RECIPES_COBWEB;
    public boolean RECIPES_SAPLING_STICK;
    public boolean RECIPES_FISHING_ROD;
    public boolean RECIPES_FURNACE;
    public boolean RECIPES_WORKBENCH;

    // LEGENDARY TOOLS
    public boolean LEGENDARY_VALKYRIE;
    public boolean LEGENDARY_QUARTZPICKAXE;
    public boolean LEGENDARY_OBSIDIAN_MACE;
    public boolean LEGENDARY_GIANTBLADE;
    public boolean LEGENDARY_BLAZESWORD;
    public boolean LEGENDARY_NOTCH_APPLE;
    public boolean LEGENDARY_GOLDARMORBUFF;

    // HIDDEN CONFIG
    public int RECIPE_DELAY;

    public Config(SurvivalPlugin plugin) {
        this.plugin = plugin;
        loadDefaultSettings();
    }

    public void loadDefaultSettings() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
            settings = YamlConfiguration.loadConfiguration(configFile);
            Utils.logMini("new config.yml created");
        } else {
            settings = YamlConfiguration.loadConfiguration(configFile);
        }
        matchConfig(settings, configFile);
        loadSettings();
        Utils.logMini("<grey>config.yml <green>loaded");
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
                if (!defConfig.contains(key) && !key.equalsIgnoreCase("recipe-delay")) {
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

    @SuppressWarnings("unused")
    public FileConfiguration getSettings() {
        return this.settings;
    }

    private void loadSettings() {

        this.LANG = settings.getString("Language");

        // SETTINGS
        this.settings_resource_pack_enabled = settings.getBoolean("Settings.enable-resource-pack");
        this.settings_resource_pack_url = settings.getString("Settings.resource-pack-url");
        this.settings_local_chat_distance = settings.getInt("settings.local-chat-distance");

        // WELCOME GUIDE
        this.WELCOME_GUIDE_ENABLED = settings.getBoolean("WelcomeGuide.Enabled");
        this.WELCOME_GUIDE_NEW_PLAYERS = settings.getBoolean("WelcomeGuide.NewPlayersOnly");
        this.WELCOME_GUIDE_DELAY = settings.getInt("WelcomeGuide.Delay");

        // SURVIVAL
        this.SURVIVAL_ENABLED = settings.getBoolean("Survival.Enabled");
        this.SURVIVAL_LIMITED_CRAFTING = settings.getBoolean("Survival.LimitedCrafting");
        this.SURVIVAL_UNLOCK_ALL_RECIPES = settings.getBoolean("Survival.Unlock-all-recipes-on-join");
        this.SURVIVAL_REMOVE_WOOD_TOOLS = settings.getBoolean("Survival.Remove-Wooden-Tools");
        this.SURVIVAL_TORCH = settings.getBoolean("Survival.Torch");
        this.SURVIVAL_UPDATE_MERCHANT_TRADES = settings.getBoolean("Survival.UpdateMerchantTrades");

        this.BREAK_ONLY_WITH_SICKLE = settings.getBoolean("Survival.BreakOnlyWith.Sickle");
        this.BREAK_ONLY_WITH_SHOVEL = settings.getBoolean("Survival.BreakOnlyWith.Shovel");
        this.BREAK_ONLY_WITH_AXE = settings.getBoolean("Survival.BreakOnlyWith.Axe");
        this.BREAK_ONLY_WITH_PICKAXE = settings.getBoolean("Survival.BreakOnlyWith.Pickaxe");
        this.BREAK_ONLY_WITH_SHEARS = settings.getBoolean("Survival.BreakOnlyWith.Shears");
        this.PLACE_ONLY_WITH_HAMMER = settings.getBoolean("Survival.PlaceOnlyWith.Hammer");

        this.SURVIVAL_SICKLE_FLINT = settings.getBoolean("Survival.Sickles.Flint");
        this.SURVIVAL_SICKLE_STONE = settings.getBoolean("Survival.Sickles.Stone");
        this.SURVIVAL_SICKLE_IRON = settings.getBoolean("Survival.Sickles.Iron");
        this.SURVIVAL_SICKLE_DIAMOND = settings.getBoolean("Survival.Sickles.Diamond");

        this.DROP_RATE_STICK = settings.getDouble("Survival.DropRate.Stick");
        this.DROP_RATE_FLINT = settings.getDouble("Survival.DropRate.Flint");

        // MECHANICS
        this.MECHANICS_SHARED_WORKBENCH = settings.getBoolean("Mechanics.SharedWorkbench");
        this.MECHANICS_PREVENT_NIGHT_SKIP = settings.getBoolean("Mechanics.Prevent-Night-Skip");
        this.mechanics_energy_enabled = settings.getBoolean("Mechanics.Energy.enabled");
        this.mechanics_energy_start = settings.getDouble("Mechanics.Energy.start-level");
        this.mechanics_energy_respawn = settings.getDouble("Mechanics.Energy.respawn-level");
        this.MECHANICS_ENERGY_WARNING = settings.getBoolean("Mechanics.Energy.warning");
        this.MECHANICS_ENERGY_DRAIN_RATE = settings.getDouble("Mechanics.Energy.drain-rate");
        this.MECHANICS_ENERGY_DRAIN_COLD_RATE = settings.getDouble("Mechanics.Energy.cold-drain-rate");
        this.MECHANICS_ENERGY_REFRESH_RATE_BED = settings.getDouble("Mechanics.Energy.sleeping-refresh-rate");
        this.MECHANICS_ENERGY_REFRESH_RATE_CHAIR = settings.getDouble("Mechanics.Energy.chair-refresh-rate");
        this.MECHANICS_ENERGY_EXHAUSTION = settings.getDouble("Mechanics.Energy.exhaustion");
        this.MECHANICS_ENERGY_COFFEE_ENABLED = settings.getBoolean("Mechanics.Energy.coffee");
        this.MECHANICS_ENERGY_ABSORPTION = settings.getBoolean("Mechanics.Energy.absorption");
        this.MECHANICS_ENERGY_HASTE = settings.getBoolean("Mechanics.Energy.haste");

        this.MECHANICS_SLOW_ARMOR = settings.getBoolean("Mechanics.SlowArmor");
        this.MECHANICS_REINFORCED_ARMOR = settings.getBoolean("Mechanics.ReinforcedLeatherArmor");
        this.MECHANICS_BOW = settings.getBoolean("Mechanics.Bow");
        this.MECHANICS_RECURVED_BOW = settings.getBoolean("Mechanics.RecurveBow");
        this.MECHANICS_GRAPPLING_HOOK = settings.getBoolean("Mechanics.GrapplingHook");
        this.MECHANICS_MEDIC_KIT = settings.getBoolean("Mechanics.MedicalKit");
        this.MECHANICS_REDUCED_IRON_NUGGET = settings.getBoolean("Mechanics.ReducedIronNugget");
        this.MECHANICS_REDUCED_GOLD_NUGGET = settings.getBoolean("Mechanics.ReducedGoldNugget");

        this.mechanics_status_scoreboard = settings.getBoolean("Mechanics.StatusScoreboard");
        this.MECHANICS_ALERT_INTERVAL = settings.getInt("Mechanics.AlertInterval");

        this.MECHANICS_RAW_MEAT_HUNGER = settings.getBoolean("Mechanics.RawMeatHunger");
        this.MECHANICS_EMPTY_POTION = settings.getBoolean("Mechanics.EmptyPotions");
        this.MECHANICS_POISON_POTATO = settings.getBoolean("Mechanics.PoisonousPotato");
        this.MECHANICS_COOKIE_BOOST = settings.getBoolean("Mechanics.CookieHealthBoost");
        this.MECHANICS_BEET_STRENGTH = settings.getBoolean("Mechanics.BeetrootStrength");

        this.mechanics_food_diversity_enabled = settings.getBoolean("Mechanics.FoodDiversity.enabled");
        this.mechanics_food_max_level = settings.getInt("Mechanics.FoodDiversity.max-level");
        this.mechanics_food_diversity_enabled = settings.getBoolean("Mechanics.FoodDiversity.enabled");
        this.mechanics_food_start_carbs = settings.getInt("Mechanics.FoodDiversity.start-level.carbs");
        this.mechanics_food_start_vitamins = settings.getInt("Mechanics.FoodDiversity.start-level.vitamins");
        this.mechanics_food_start_protein = settings.getInt("Mechanics.FoodDiversity.start-level.proteins");
        this.mechanics_food_diversity_enabled = settings.getBoolean("Mechanics.FoodDiversity.enabled");
        this.mechanics_food_respawn_carbs = settings.getInt("Mechanics.FoodDiversity.respawn-level.carbs");
        this.mechanics_food_respawn_vitamins = settings.getInt("Mechanics.FoodDiversity.respawn-level.vitamins");
        this.mechanics_food_respawn_proteins = settings.getInt("Mechanics.FoodDiversity.respawn-level.proteins");
        this.mechanics_food_effects_carbs_ex_amp_easy = settings.getInt("Mechanics.FoodDiversity.effects.carbs.exhaustion-amplifier.easy");
        this.mechanics_food_effects_carbs_ex_amp_medium = settings.getInt("Mechanics.FoodDiversity.effects.carbs.exhaustion-amplifier.normal");
        this.mechanics_food_effects_carbs_ex_amp_hard = settings.getInt("Mechanics.FoodDiversity.effects.carbs.exhaustion-amplifier.hard");
        this.mechanics_food_effects_vitamins_ex_amp = settings.getInt("Mechanics.FoodDiversity.effects.vitamins.exhaustion-amplifier");
        this.mechanics_food_effects_vitamins_se_normal_effect = settings.getString("Mechanics.FoodDiversity.effects.vitamins.status-effects.normal.effect");
        this.mechanics_food_effects_vitamins_se_normal_amp = settings.getInt("Mechanics.FoodDiversity.effects.vitamins.status-effects.normal.amplifier");
        this.mechanics_food_effects_vitamins_se_normal_duration = settings.getInt("Mechanics.FoodDiversity.effects.vitamins.status-effects.normal.duration");
        this.mechanics_food_effects_vitamins_se_hard_effect = settings.getString("Mechanics.FoodDiversity.effects.vitamins.status-effects.hard.effect");
        this.mechanics_food_effects_vitamins_se_hard_amp = settings.getInt("Mechanics.FoodDiversity.effects.vitamins.status-effects.hard.amplifier");
        this.mechanics_food_effects_vitamins_se_hard_duration = settings.getInt("Mechanics.FoodDiversity.effects.vitamins.status-effects.hard.duration");

        this.mechanics_food_effects_protein_ex_amp = settings.getInt("Mechanics.FoodDiversity.effects.proteins.exhaustion-amplifier");
        this.mechanics_food_effects_protein_se_normal_effect = settings.getString("Mechanics.FoodDiversity.effects.proteins.status-effects.normal.effect");
        this.mechanics_food_effects_protein_se_normal_amp = settings.getInt("Mechanics.FoodDiversity.effects.proteins.status-effects.normal.amplifier");
        this.mechanics_food_effects_protein_se_normal_duration = settings.getInt("Mechanics.FoodDiversity.effects.proteins.status-effects.normal.duration");
        this.mechanics_food_effects_protein_se_hard_effect = settings.getString("Mechanics.FoodDiversity.effects.proteins.status-effects.hard.effect");
        this.mechanics_food_effects_protein_se_hard_amp = settings.getInt("Mechanics.FoodDiversity.effects.proteins.status-effects.hard.amplifier");
        this.mechanics_food_effects_protein_se_hard_duration = settings.getInt("Mechanics.FoodDiversity.effects.proteins.status-effects.hard.duration");

        this.mechanics_thirst_enabled = settings.getBoolean("Mechanics.Thirst.enabled");
        this.mechanics_thirst_starting_amount = settings.getInt("Mechanics.Thirst.starting-amount");
        this.mechanics_thirst_respawn_amount = settings.getInt("Mechanics.Thirst.respawn-amount");
        this.mechanics_thirst_purify_water = settings.getBoolean("Mechanics.Thirst.purify-water");
        this.mechanics_thirst_melt_snow = settings.getBoolean("Mechanics.Thirst.melt-snow");
        this.mechanics_thirst_drain_rate = settings.getDouble("Mechanics.Thirst.drain-rate");
        this.mechanics_thirst_heat_drain = settings.getInt("Mechanics.Thirst.heat-drain");
        this.mechanics_thirst_nether_drain = settings.getInt("Mechanics.Thirst.nether-drain");
        this.mechanics_thirst_damage_rate = settings.getDouble("Mechanics.Thirst.damage-rate");

        this.mechanics_thirst_rep_beetroot_soup = settings.getInt("Mechanics.Thirst.replenish-level.beetroot-soup");
        this.mechanics_thirst_rep_melon_slice = settings.getInt("Mechanics.Thirst.replenish-level.melon-slice");
        this.mechanics_thirst_rep_mush_stew = settings.getInt("Mechanics.Thirst.replenish-level.mushroom-stew");
        this.mechanics_thirst_rep_water_bowl = settings.getInt("Mechanics.Thirst.replenish-level.water-bowl");
        this.mechanics_thirst_rep_dirty_water = settings.getInt("Mechanics.Thirst.replenish-level.dirty-water");
        this.mechanics_thirst_rep_clean_water = settings.getInt("Mechanics.Thirst.replenish-level.clean-water");
        this.mechanics_thirst_rep_pure_water = settings.getInt("Mechanics.Thirst.replenish-level.purified-water");
        this.mechanics_thirst_rep_coffee = settings.getInt("Mechanics.Thirst.replenish-level.coffee");
        this.mechanics_thirst_rep_cold_milk = settings.getInt("Mechanics.Thirst.replenish-level.cold-milk");
        this.mechanics_thirst_rep_hot_milk = settings.getInt("Mechanics.Thirst.replenish-level.hot-milk");
        this.mechanics_thirst_rep_milk_bucket = settings.getInt("Mechanics.Thirst.replenish-level.milk-bucket");
        this.mechanics_thirst_rep_honey_bottle = settings.getInt("Mechanics.Thirst.replenish-level.honey-bottle");
        this.mechanics_thirst_rep_other_water = settings.getInt("Mechanics.Thirst.replenish-level.other-water");
        this.mechanics_thirst_rep_water = settings.getInt("Mechanics.Thirst.replenish-level.water");

        this.mechanics_hunger_start_amount = settings.getInt("Mechanics.Hunger.Starting-Amount");
        this.mechanics_hunger_respawn_amount = settings.getInt("Mechanics.Hunger.Respawn-Amount");

        this.MECHANICS_COMPASS_WAYPOINT = settings.getBoolean("Mechanics.CompassWaypoint.enabled");
        this.MECHANICS_COMPASS_WAYPOINT_WORLDS = settings.getBoolean("Mechanics.CompassWaypoint.per-world");
        this.MECHANICS_CLOWN_FISH = settings.getBoolean("Mechanics.Clownfish");
        this.MECHANICS_FERMENTED_SKIN = settings.getBoolean("Mechanics.FermentedSkin");
        this.MECHANICS_LIVING_SLIME = settings.getBoolean("Mechanics.LivingSlime");

        this.MECHANICS_SNOWBALL_REVAMP = settings.getBoolean("Mechanics.SnowballRevamp");
        this.MECHANICS_SNOW_GEN_REVAMP = settings.getBoolean("Mechanics.SnowGenerationRevamp");

        this.MECHANICS_FARMING_PRODUCTS_COOKIE = settings.getBoolean("Mechanics.FarmingProducts.Cookie");
        this.MECHANICS_FARMING_PRODUCTS_BREAD = settings.getBoolean("Mechanics.FarmingProducts.Bread");

        this.MECHANICS_CHAIRS_ENABLED = settings.getBoolean("Mechanics.Chairs.Enabled");
        this.MECHANICS_CHAIRS_MAX_WIDTH = settings.getInt("Mechanics.Chairs.MaxChairWidth");
        this.MECHANICS_CHAIRS_BLOCKS = settings.getStringList("Mechanics.Chairs.AllowedBlocks");

        this.MECHANICS_BURNOUT_TORCH_ENABLED = settings.getBoolean("Mechanics.BurnoutTorches.Enabled");
        this.MECHANICS_BURNOUT_TORCH_TIME = settings.getInt("Mechanics.BurnoutTorches.BurnoutTime");
        this.MECHANICS_BURNOUT_TORCH_RELIGHT = settings.getBoolean("Mechanics.BurnoutTorches.Relightable");
        this.MECHANICS_BURNOUT_TORCH_DROP = settings.getBoolean("Mechanics.BurnoutTorches.DropTorch");
        this.MECHANICS_BURNOUT_TORCH_PERSIST = settings.getBoolean("Mechanics.BurnoutTorches.PersistentTorches");

        this.MECHANICS_WEATHER_ENABLED = settings.getBoolean("Mechanics.Weather.Enabled");
        this.MECHANICS_WEATHER_SPEED_BASE = settings.getDouble("Mechanics.Weather.speed.base");
        this.MECHANICS_WEATHER_SPEED_RAIN = settings.getDouble("Mechanics.Weather.speed.rain");
        this.MECHANICS_WEATHER_SPEED_STORM = settings.getDouble("Mechanics.Weather.speed.storm");
        this.MECHANICS_WEATHER_SPEED_SNOW = settings.getDouble("Mechanics.Weather.speed.snow");
        this.MECHANICS_WEATHER_SPEED_SNOWSTORM = settings.getDouble("Mechanics.Weather.speed.snowstorm");

        // ITEM MECHANICS
        this.ITEM_FIRESTRIKER_COOK_TIME = settings.getInt("Item-Mechanics.firestriker.cook-time");

        // ENTITY MECHANICS
        this.ENTITY_MECHANICS_PIGMEN_CHEST_ENABLED = settings.getBoolean("Entity-Mechanics.zombified-piglin-chests.enabled");
        this.ENTITY_MECHANICS_PIGMEN_CHEST_RADIUS = settings.getInt("Entity-Mechanics.zombified-piglin-chests.distance");
        this.ENTITY_MECHANICS_PIGMEN_CHEST_SPEED = settings.getDouble("Entity-Mechanics.zombified-piglin-chests.speed-modifier");
        this.ENTITY_MECHANICS_BEEKEEPER_SUIT_ENABLED = settings.getBoolean("Entity-Mechanics.beekeeper-suit.enabled");
        this.ENTITY_MECHANICS_SUSPICIOUS_MEAT_ENABLED = settings.getBoolean("Entity-Mechanics.suspicious-meat.enabled");
        this.ENTITY_MECHANICS_SUSPICIOUS_MEAT_CHANCE = settings.getInt("Entity-Mechanics.suspicious-meat.chance");
        this.ENTITY_MECHANICS_CHICKEN_BREEDING_ENABLED = settings.getBoolean("Entity-Mechanics.chicken-breeding.enabled");
        this.ENTITY_MECHANICS_CHICKEN_BREEDING_MAX_EGGS = settings.getInt("Entity-Mechanics.chicken-breeding.max-eggs");
        this.ENTITY_MECHANICS_CHICKEN_BREEDING_ALWAYS_BABY = settings.getBoolean("Entity-Mechanics.chicken-breeding.always-baby");
        this.ENTITY_MECHANICS_CHICKEN_BREEDING_BABY_TICKS = settings.getInt("Entity-Mechanics.chicken-breeding.baby-ticks");
        this.ENTITY_MECHANICS_PIGLIN_DROP_WATER = settings.getBoolean("Entity-Mechanics.piglin-barter.drop-purified-water");
        this.ENTITY_MECHANICS_PIGLIN_ALT_DROP = settings.getBoolean("Entity-Mechanics.piglin-barter.alternate-bartering");

        // RECIPES
        this.RECIPES_SADDLE = settings.getBoolean("Recipes.Saddle");
        this.RECIPES_NAME_TAG = settings.getBoolean("Recipes.Nametag");
        this.RECIPES_PACKED_ICE = settings.getBoolean("Recipes.PackedIce");
        this.RECIPES_LEATHER_BARD = settings.getBoolean("Recipes.LeatherBard");
        this.RECIPES_IRON_BARD = settings.getBoolean("Recipes.IronBard");
        this.RECIPES_GOLD_BARD = settings.getBoolean("Recipes.GoldBard");
        this.RECIPES_DIAMOND_BARD = settings.getBoolean("Recipes.DiamondBard");
        this.RECIPES_CLAY_BRICK = settings.getBoolean("Recipes.ClayBrick");
        this.RECIPES_QUARTZ_BLOCK = settings.getBoolean("Recipes.QuartzBlock");
        this.RECIPES_WOOL_STRING = settings.getBoolean("Recipes.WoolString");
        this.RECIPES_WEB_STRING = settings.getBoolean("Recipes.WebString");
        this.RECIPES_ICE = settings.getBoolean("Recipes.Ice");
        this.RECIPES_CLAY = settings.getBoolean("Recipes.Clay");
        this.RECIPES_DIORITE = settings.getBoolean("Recipes.Diorite");
        this.RECIPES_GRANITE = settings.getBoolean("Recipes.Granite");
        this.RECIPES_ANDESITE = settings.getBoolean("Recipes.Andesite");
        this.RECIPES_GRAVEL = settings.getBoolean("Recipes.Gravel");
        this.RECIPES_SLIMEBALL = settings.getBoolean("Recipes.Slimeball");
        this.RECIPES_COBWEB = settings.getBoolean("Recipes.Cobweb");
        this.RECIPES_SAPLING_STICK = settings.getBoolean("Recipes.SaplingToSticks");
        this.RECIPES_FISHING_ROD = settings.getBoolean("Recipes.FishingRod");
        this.RECIPES_FURNACE = settings.getBoolean("Recipes.Furnace");
        this.RECIPES_WORKBENCH = settings.getBoolean("Recipes.Workbench");

        // LEGENDARY ITEMS
        this.LEGENDARY_VALKYRIE = settings.getBoolean("LegendaryItems.ValkyrieAxe");
        this.LEGENDARY_QUARTZPICKAXE = settings.getBoolean("LegendaryItems.QuartzPickaxe");
        this.LEGENDARY_OBSIDIAN_MACE = settings.getBoolean("LegendaryItems.ObsidianMace");
        this.LEGENDARY_GIANTBLADE = settings.getBoolean("LegendaryItems.GiantBlade");
        this.LEGENDARY_BLAZESWORD = settings.getBoolean("LegendaryItems.BlazeSword");
        this.LEGENDARY_NOTCH_APPLE = settings.getBoolean("LegendaryItems.NotchApple");
        this.LEGENDARY_GOLDARMORBUFF = settings.getBoolean("LegendaryItems.GoldArmorBuff");

        // HIDDEN CONFIG
        this.RECIPE_DELAY = settings.getInt("recipe-delay", 0);
    }

}
