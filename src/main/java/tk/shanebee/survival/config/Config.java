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
    public boolean welcome_guide_new_players;
    public int welcome_guide_delay;

    // SURVIVAL
    public boolean survival_enabled;
    public boolean SURVIVAL_LIMITED_CRAFTING;
    public boolean survival_unlock_all_recipes;
    public boolean survival_remove_wood_tools;
    public boolean survival_torch;
    public boolean SURVIVAL_UPDATE_MERCHANT_TRADES;

    public boolean break_only_with_sickle;
    public boolean BREAK_ONLY_WITH_SHOVEL;
    public boolean BREAK_ONLY_WITH_AXE;
    public boolean BREAK_ONLY_WITH_PICKAXE;
    public boolean BREAK_ONLY_WITH_SHEARS;
    public boolean PLACE_ONLY_WITH_HAMMER;

    public boolean survival_sickle_flint;
    public boolean survival_sickle_stone;
    public boolean survival_sickle_iron;
    public boolean survival_sickle_diamond;

    public double DROP_RATE_STICK;
    public double DROP_RATE_FLINT;

    // MECHANICS
    public boolean MECHANICS_SHARED_WORKBENCH;

    // ENERGY
    public boolean mechanics_energy_enabled;
    public double mechanics_energy_start;
    public double mechanics_energy_respawn;
    public boolean mechanics_energy_warning;
    public double mechanics_energy_drain_rate;
    public double mechanics_energy_drain_cold_rate;
    public double mechanics_energy_refresh_rate_bed;
    public double mechanics_energy_refresh_rate_chair;
    public double mechanics_energy_exhaustion;
    public boolean mechanics_energy_coffee_enabled;
    public boolean mechanics_energy_absorption;
    public boolean mechanics_energy_haste;

    public boolean mechanics_slow_armor;
    public boolean mechanics_reinforced_armor;
    public boolean MECHANICS_BOW;
    public boolean mechanics_recurved_bow;
    public boolean mechanics_grappling_hook;
    public boolean mechanics_medic_kit;
    public boolean mechanics_reduced_iron_nugget;
    public boolean mechanics_reduced_gold_nugget;

    public boolean mechanics_status_scoreboard;
    public int MECHANICS_ALERT_INTERVAL;

    public boolean MECHANICS_RAW_MEAT_HUNGER;
    public boolean mechanics_empty_potion;
    public boolean mechanics_poison_potato;
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
    public double mechanics_thirst_starting_amount;
    public double mechanics_thirst_respawn_amount;
    public boolean mechanics_thirst_purify_water;
    public boolean mechanics_thirst_melt_snow;
    public double mechanics_thirst_drain_rate;
    public int mechanics_thirst_heat_drain;
    public int mechanics_thirst_nether_drain;
    public double mechanics_thirst_damage_rate;
    public double mechanics_thirst_rep_beetroot_soup;
    public double mechanics_thirst_rep_melon_slice;
    public double mechanics_thirst_rep_mush_stew;
    public double mechanics_thirst_rep_milk_bucket;
    public double mechanics_thirst_rep_water;
    public double mechanics_thirst_rep_honey_bottle;
    public double mechanics_thirst_rep_other_water;

    public int mechanics_hunger_start_amount;
    public int mechanics_hunger_respawn_amount;

    public boolean mechanics_compass_waypoint;
    public boolean MECHANICS_COMPASS_WAYPOINT_WORLDS;
    public boolean MECHANICS_CLOWN_FISH;
    public boolean mechanics_fermented_skin;
    public boolean MECHANICS_LIVING_SLIME;
    public boolean mechanics_snowball_revamp;
    public boolean MECHANICS_SNOW_GEN_REVAMP;

    public boolean mechanics_farming_products_cookie;
    public boolean mechanics_farming_products_bread;

    public boolean mechanics_chairs_enabled;
    public int MECHANICS_CHAIRS_MAX_WIDTH;
    public List<String> MECHANICS_CHAIRS_BLOCKS;

    public boolean MECHANICS_BURNOUT_TORCH_ENABLED;
    public int MECHANICS_BURNOUT_TORCH_TIME;
    public boolean MECHANICS_BURNOUT_TORCH_RELIGHT;
    public boolean MECHANICS_BURNOUT_TORCH_DROP;
    public boolean MECHANICS_BURNOUT_TORCH_PERSIST;

    public boolean mechanics_weather_enabled;
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
    public boolean entity_mechanics_beekeeper_suit_enabled;
    public boolean ENTITY_MECHANICS_SUSPICIOUS_MEAT_ENABLED;
    public int ENTITY_MECHANICS_SUSPICIOUS_MEAT_CHANCE;
    public boolean ENTITY_MECHANICS_CHICKEN_BREEDING_ENABLED;
    public int ENTITY_MECHANICS_CHICKEN_BREEDING_MAX_EGGS;
    public boolean ENTITY_MECHANICS_CHICKEN_BREEDING_ALWAYS_BABY;
    public int ENTITY_MECHANICS_CHICKEN_BREEDING_BABY_TICKS;
    public boolean ENTITY_MECHANICS_PIGLIN_DROP_WATER;
    public boolean ENTITY_MECHANICS_PIGLIN_ALT_DROP;

    // RECIPES
    public boolean recipes_saddle;
    public boolean recipes_name_tag;
    public boolean recipes_packed_ice;
    public boolean recipes_leather_bard;
    public boolean recipes_iron_bard;
    public boolean recipes_gold_bard;
    public boolean recipes_diamond_bard;
    public boolean recipes_clay_brick;
    public boolean recipes_quartz_block;
    public boolean recipes_wool_string;
    public boolean recipes_web_string;
    public boolean recipes_ice;
    public boolean recipes_clay;
    public boolean recipes_diorite;
    public boolean recipes_granite;
    public boolean recipes_andesite;
    public boolean recipes_gravel;
    public boolean recipes_slimeball;
    public boolean recipes_cobweb;
    public boolean recipes_sapling_stick;
    public boolean recipes_fishing_rod;
    public boolean recipes_furnace;
    public boolean recipes_workbench;

    // LEGENDARY TOOLS
    public boolean legendary_valkyrie;
    public boolean legendary_quartzpickaxe;
    public boolean legendary_obsidian_mace;
    public boolean legendary_giantblade;
    public boolean legendary_blazesword;
    public boolean legendary_notch_apple;
    public boolean legendary_goldarmorbuff;

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
        this.welcome_guide_new_players = settings.getBoolean("WelcomeGuide.NewPlayersOnly");
        this.welcome_guide_delay = settings.getInt("WelcomeGuide.Delay");

        // SURVIVAL
        this.survival_enabled = settings.getBoolean("Survival.Enabled");
        this.SURVIVAL_LIMITED_CRAFTING = settings.getBoolean("Survival.LimitedCrafting");
        this.survival_unlock_all_recipes = settings.getBoolean("Survival.Unlock-all-recipes-on-join");
        this.survival_remove_wood_tools = settings.getBoolean("Survival.Remove-Wooden-Tools");
        this.survival_torch = settings.getBoolean("Survival.Torch");
        this.SURVIVAL_UPDATE_MERCHANT_TRADES = settings.getBoolean("Survival.UpdateMerchantTrades");

        this.break_only_with_sickle = settings.getBoolean("Survival.BreakOnlyWith.Sickle");
        this.BREAK_ONLY_WITH_SHOVEL = settings.getBoolean("Survival.BreakOnlyWith.Shovel");
        this.BREAK_ONLY_WITH_AXE = settings.getBoolean("Survival.BreakOnlyWith.Axe");
        this.BREAK_ONLY_WITH_PICKAXE = settings.getBoolean("Survival.BreakOnlyWith.Pickaxe");
        this.BREAK_ONLY_WITH_SHEARS = settings.getBoolean("Survival.BreakOnlyWith.Shears");
        this.PLACE_ONLY_WITH_HAMMER = settings.getBoolean("Survival.PlaceOnlyWith.Hammer");

        this.survival_sickle_flint = settings.getBoolean("Survival.Sickles.Flint");
        this.survival_sickle_stone = settings.getBoolean("Survival.Sickles.Stone");
        this.survival_sickle_iron = settings.getBoolean("Survival.Sickles.Iron");
        this.survival_sickle_diamond = settings.getBoolean("Survival.Sickles.Diamond");

        this.DROP_RATE_STICK = settings.getDouble("Survival.DropRate.Stick");
        this.DROP_RATE_FLINT = settings.getDouble("Survival.DropRate.Flint");

        // MECHANICS
        this.MECHANICS_SHARED_WORKBENCH = settings.getBoolean("Mechanics.SharedWorkbench");
        this.mechanics_energy_enabled = settings.getBoolean("Mechanics.Energy.enabled");
        this.mechanics_energy_start = settings.getDouble("Mechanics.Energy.start-level");
        this.mechanics_energy_respawn = settings.getDouble("Mechanics.Energy.respawn-level");
        this.mechanics_energy_warning = settings.getBoolean("Mechanics.Energy.warning");
        this.mechanics_energy_drain_rate = settings.getDouble("Mechanics.Energy.drain-rate");
        this.mechanics_energy_drain_cold_rate = settings.getDouble("Mechanics.Energy.cold-drain-rate");
        this.mechanics_energy_refresh_rate_bed = settings.getDouble("Mechanics.Energy.sleeping-refresh-rate");
        this.mechanics_energy_refresh_rate_chair = settings.getDouble("Mechanics.Energy.chair-refresh-rate");
        this.mechanics_energy_exhaustion = settings.getDouble("Mechanics.Energy.exhaustion");
        this.mechanics_energy_coffee_enabled = settings.getBoolean("Mechanics.Energy.coffee");
        this.mechanics_energy_absorption = settings.getBoolean("Mechanics.Energy.absorption");
        this.mechanics_energy_haste = settings.getBoolean("Mechanics.Energy.haste");

        this.mechanics_slow_armor = settings.getBoolean("Mechanics.SlowArmor");
        this.mechanics_reinforced_armor = settings.getBoolean("Mechanics.ReinforcedLeatherArmor");
        this.MECHANICS_BOW = settings.getBoolean("Mechanics.Bow");
        this.mechanics_recurved_bow = settings.getBoolean("Mechanics.RecurveBow");
        this.mechanics_grappling_hook = settings.getBoolean("Mechanics.GrapplingHook");
        this.mechanics_medic_kit = settings.getBoolean("Mechanics.MedicalKit");
        this.mechanics_reduced_iron_nugget = settings.getBoolean("Mechanics.ReducedIronNugget");
        this.mechanics_reduced_gold_nugget = settings.getBoolean("Mechanics.ReducedGoldNugget");

        this.mechanics_status_scoreboard = settings.getBoolean("Mechanics.StatusScoreboard");
        this.MECHANICS_ALERT_INTERVAL = settings.getInt("Mechanics.AlertInterval");

        this.MECHANICS_RAW_MEAT_HUNGER = settings.getBoolean("Mechanics.RawMeatHunger");
        this.mechanics_empty_potion = settings.getBoolean("Mechanics.EmptyPotions");
        this.mechanics_poison_potato = settings.getBoolean("Mechanics.PoisonousPotato");
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

        this.mechanics_thirst_rep_beetroot_soup = settings.getDouble("Mechanics.Thirst.replenish-level.beetroot-soup");
        this.mechanics_thirst_rep_melon_slice = settings.getDouble("Mechanics.Thirst.replenish-level.melon-slice");
        this.mechanics_thirst_rep_mush_stew = settings.getDouble("Mechanics.Thirst.replenish-level.mushroom-stew");
        this.mechanics_thirst_rep_milk_bucket = settings.getDouble("Mechanics.Thirst.replenish-level.milk-bucket");
        this.mechanics_thirst_rep_honey_bottle = settings.getDouble("Mechanics.Thirst.replenish-level.honey-bottle");
        this.mechanics_thirst_rep_other_water = settings.getDouble("Mechanics.Thirst.replenish-level.other-water");
        this.mechanics_thirst_rep_water = settings.getDouble("Mechanics.Thirst.replenish-level.water");

        this.mechanics_hunger_start_amount = settings.getInt("Mechanics.Hunger.Starting-Amount");
        this.mechanics_hunger_respawn_amount = settings.getInt("Mechanics.Hunger.Respawn-Amount");

        this.mechanics_compass_waypoint = settings.getBoolean("Mechanics.CompassWaypoint.enabled");
        this.MECHANICS_COMPASS_WAYPOINT_WORLDS = settings.getBoolean("Mechanics.CompassWaypoint.per-world");
        this.MECHANICS_CLOWN_FISH = settings.getBoolean("Mechanics.Clownfish");
        this.mechanics_fermented_skin = settings.getBoolean("Mechanics.FermentedSkin");
        this.MECHANICS_LIVING_SLIME = settings.getBoolean("Mechanics.LivingSlime");

        this.mechanics_snowball_revamp = settings.getBoolean("Mechanics.SnowballRevamp");
        this.MECHANICS_SNOW_GEN_REVAMP = settings.getBoolean("Mechanics.SnowGenerationRevamp");

        this.mechanics_farming_products_cookie = settings.getBoolean("Mechanics.FarmingProducts.Cookie");
        this.mechanics_farming_products_bread = settings.getBoolean("Mechanics.FarmingProducts.Bread");

        this.mechanics_chairs_enabled = settings.getBoolean("Mechanics.Chairs.Enabled");
        this.MECHANICS_CHAIRS_MAX_WIDTH = settings.getInt("Mechanics.Chairs.MaxChairWidth");
        this.MECHANICS_CHAIRS_BLOCKS = settings.getStringList("Mechanics.Chairs.AllowedBlocks");

        this.MECHANICS_BURNOUT_TORCH_ENABLED = settings.getBoolean("Mechanics.BurnoutTorches.Enabled");
        this.MECHANICS_BURNOUT_TORCH_TIME = settings.getInt("Mechanics.BurnoutTorches.BurnoutTime");
        this.MECHANICS_BURNOUT_TORCH_RELIGHT = settings.getBoolean("Mechanics.BurnoutTorches.Relightable");
        this.MECHANICS_BURNOUT_TORCH_DROP = settings.getBoolean("Mechanics.BurnoutTorches.DropTorch");
        this.MECHANICS_BURNOUT_TORCH_PERSIST = settings.getBoolean("Mechanics.BurnoutTorches.PersistentTorches");

        this.mechanics_weather_enabled = settings.getBoolean("Mechanics.Weather.Enabled");
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
        this.entity_mechanics_beekeeper_suit_enabled = settings.getBoolean("Entity-Mechanics.beekeeper-suit.enabled");
        this.ENTITY_MECHANICS_SUSPICIOUS_MEAT_ENABLED = settings.getBoolean("Entity-Mechanics.suspicious-meat.enabled");
        this.ENTITY_MECHANICS_SUSPICIOUS_MEAT_CHANCE = settings.getInt("Entity-Mechanics.suspicious-meat.chance");
        this.ENTITY_MECHANICS_CHICKEN_BREEDING_ENABLED = settings.getBoolean("Entity-Mechanics.chicken-breeding.enabled");
        this.ENTITY_MECHANICS_CHICKEN_BREEDING_MAX_EGGS = settings.getInt("Entity-Mechanics.chicken-breeding.max-eggs");
        this.ENTITY_MECHANICS_CHICKEN_BREEDING_ALWAYS_BABY = settings.getBoolean("Entity-Mechanics.chicken-breeding.always-baby");
        this.ENTITY_MECHANICS_CHICKEN_BREEDING_BABY_TICKS = settings.getInt("Entity-Mechanics.chicken-breeding.baby-ticks");
        this.ENTITY_MECHANICS_PIGLIN_DROP_WATER = settings.getBoolean("Entity-Mechanics.piglin-barter.drop-purified-water");
        this.ENTITY_MECHANICS_PIGLIN_ALT_DROP = settings.getBoolean("Entity-Mechanics.piglin-barter.alternate-bartering");

        // RECIPES
        this.recipes_saddle = settings.getBoolean("Recipes.Saddle");
        this.recipes_name_tag = settings.getBoolean("Recipes.Nametag");
        this.recipes_packed_ice = settings.getBoolean("Recipes.PackedIce");
        this.recipes_leather_bard = settings.getBoolean("Recipes.LeatherBard");
        this.recipes_iron_bard = settings.getBoolean("Recipes.IronBard");
        this.recipes_gold_bard = settings.getBoolean("Recipes.GoldBard");
        this.recipes_diamond_bard = settings.getBoolean("Recipes.DiamondBard");
        this.recipes_clay_brick = settings.getBoolean("Recipes.ClayBrick");
        this.recipes_quartz_block = settings.getBoolean("Recipes.QuartzBlock");
        this.recipes_wool_string = settings.getBoolean("Recipes.WoolString");
        this.recipes_web_string = settings.getBoolean("Recipes.WebString");
        this.recipes_ice = settings.getBoolean("Recipes.Ice");
        this.recipes_clay = settings.getBoolean("Recipes.Clay");
        this.recipes_diorite = settings.getBoolean("Recipes.Diorite");
        this.recipes_granite = settings.getBoolean("Recipes.Granite");
        this.recipes_andesite = settings.getBoolean("Recipes.Andesite");
        this.recipes_gravel = settings.getBoolean("Recipes.Gravel");
        this.recipes_slimeball = settings.getBoolean("Recipes.Slimeball");
        this.recipes_cobweb = settings.getBoolean("Recipes.Cobweb");
        this.recipes_sapling_stick = settings.getBoolean("Recipes.SaplingToSticks");
        this.recipes_fishing_rod = settings.getBoolean("Recipes.FishingRod");
        this.recipes_furnace = settings.getBoolean("Recipes.Furnace");
        this.recipes_workbench = settings.getBoolean("Recipes.Workbench");

        // LEGENDARY ITEMS
        this.legendary_valkyrie = settings.getBoolean("LegendaryItems.ValkyrieAxe");
        this.legendary_quartzpickaxe = settings.getBoolean("LegendaryItems.QuartzPickaxe");
        this.legendary_obsidian_mace = settings.getBoolean("LegendaryItems.ObsidianMace");
        this.legendary_giantblade = settings.getBoolean("LegendaryItems.GiantBlade");
        this.legendary_blazesword = settings.getBoolean("LegendaryItems.BlazeSword");
        this.legendary_notch_apple = settings.getBoolean("LegendaryItems.NotchApple");
        this.legendary_goldarmorbuff = settings.getBoolean("LegendaryItems.GoldArmorBuff");

        // HIDDEN CONFIG
        this.RECIPE_DELAY = settings.getInt("recipe-delay", 0);
    }

}
