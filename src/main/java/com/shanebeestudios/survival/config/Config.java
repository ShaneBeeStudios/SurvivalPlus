package com.shanebeestudios.survival.config;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.util.Utils;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private final SurvivalPlugin plugin;
    private FileConfiguration settings;
    private File configFile;

    public String lang;

    public String settings_resource_pack_url;
    public boolean settings_resource_pack_enabled;

    public int settings_local_chat_distance;

    public boolean welcome_guide_enabled;
    public boolean welcome_guide_new_players;
    public int welcome_guide_delay;

    // SURVIVAL
    public boolean survival_enabled;
    public boolean survival_limited_crafting;
    public boolean survival_unlock_all_recipes;
    public boolean survival_remove_wood_tools;
    public boolean survival_torch;
    public boolean survival_update_merchant_trades;

    public boolean break_only_with_sickle;
    public boolean survival_break_only_with_shovel;
    public boolean survival_break_only_with_axe;
    public boolean survival_break_only_with_pickaxe;
    public boolean survival_break_only_with_shears;
    public boolean survival_place_only_with_hammer;

    public boolean survival_sickle_flint;
    public boolean survival_sickle_stone;
    public boolean survival_sickle_iron;
    public boolean survival_sickle_diamond;

    public double survival_drop_rate_stick;
    public double survival_drop_rate_flint;

    // MECHANICS
    public boolean mechanics_shared_workbench;

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

    public boolean mechanics_raw_meat_hunger;
    public boolean mechanics_empty_potion;
    public boolean mechanics_poison_potato;
    public boolean mechanics_cookie_boost;
    public boolean mechanics_beet_strength;

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
    public boolean mechanics_compass_waypoint_worlds;
    public boolean mechanics_tropical_fish;
    public boolean mechanics_fermented_skin;
    public boolean mechanics_living_slime;
    public boolean mechanics_snowball_revamp;
    public boolean MECHANICS_SNOW_GEN_REVAMP;

    public boolean mechanics_farming_products_cookie;
    public boolean mechanics_farming_products_bread;

    public boolean mechanics_chairs_enabled;
    public int mechanics_chairs_max_width;
    public List<Material> mechanics_chairs_blocks;

    public boolean mechanics_weather_enabled;
    public double mechanics_weather_speed_base;
    public double mechanics_weather_speed_rain;
    public double mechanics_weather_speed_storm;
    public double mechanics_weather_speed_snow;
    public double mechanics_weather_speed_snowstorm;

    // ITEM MECHANICS
    public int item_mechanics_firestriker_cook_time;

    // ENTITY MECHANICS
    public boolean entity_mechanics_pigmen_chest_enabled;
    public int entity_mechanics_pigmen_chest_radius;
    public double entity_mechanics_pigmen_chest_speed;
    public boolean entity_mechanics_beekeeper_suit_enabled;
    public boolean entity_mechanics_suspicious_meat_enabled;
    public int entity_mechanics_suspicious_meat_chance;
    public boolean entity_mechanics_chicken_breeding_enabled;
    public int entity_mechanics_chicken_breeding_max_eggs;
    public boolean entity_mechanics_chicken_breeding_always_baby;
    public int entity_mechanics_chicken_breeding_baby_ticks;
    public boolean entity_mechanics_piglin_drop_water;
    public boolean entity_mechanics_piglin_alt_drop;

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
    public boolean legendary_quartz_pickaxe;
    public boolean legendary_obsidian_mace;
    public boolean legendary_giant_blade;
    public boolean legendary_blaze_sword;
    public boolean legendary_notch_apple;
    public boolean legendary_gold_armor_buff;

    // HIDDEN CONFIG
    public int recipe_delay;

    public Config(SurvivalPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadDefaultSettings() {
        if (this.configFile == null) {
            this.configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!this.configFile.exists()) {
            this.plugin.saveResource("config.yml", false);
            this.settings = YamlConfiguration.loadConfiguration(this.configFile);
            Utils.logMini("new config.yml created");
        } else {
            this.settings = YamlConfiguration.loadConfiguration(this.configFile);
        }
        matchConfig(this.settings, this.configFile);
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

        this.lang = settings.getString("Language");

        // SETTINGS
        this.settings_resource_pack_enabled = settings.getBoolean("Settings.enable-resource-pack");
        this.settings_resource_pack_url = settings.getString("Settings.resource-pack-url");
        this.settings_local_chat_distance = settings.getInt("settings.local-chat-distance");

        // WELCOME GUIDE
        this.welcome_guide_enabled = settings.getBoolean("WelcomeGuide.Enabled");
        this.welcome_guide_new_players = settings.getBoolean("WelcomeGuide.NewPlayersOnly");
        this.welcome_guide_delay = settings.getInt("WelcomeGuide.Delay");

        // SURVIVAL
        this.survival_enabled = settings.getBoolean("Survival.Enabled");
        this.survival_limited_crafting = settings.getBoolean("Survival.LimitedCrafting");
        this.survival_unlock_all_recipes = settings.getBoolean("Survival.Unlock-all-recipes-on-join");
        this.survival_remove_wood_tools = settings.getBoolean("Survival.Remove-Wooden-Tools");
        this.survival_torch = settings.getBoolean("Survival.Torch");
        this.survival_update_merchant_trades = settings.getBoolean("Survival.UpdateMerchantTrades");

        this.break_only_with_sickle = settings.getBoolean("Survival.BreakOnlyWith.Sickle");
        this.survival_break_only_with_shovel = settings.getBoolean("Survival.BreakOnlyWith.Shovel");
        this.survival_break_only_with_axe = settings.getBoolean("Survival.BreakOnlyWith.Axe");
        this.survival_break_only_with_pickaxe = settings.getBoolean("Survival.BreakOnlyWith.Pickaxe");
        this.survival_break_only_with_shears = settings.getBoolean("Survival.BreakOnlyWith.Shears");
        this.survival_place_only_with_hammer = settings.getBoolean("Survival.PlaceOnlyWith.Hammer");

        this.survival_sickle_flint = settings.getBoolean("Survival.Sickles.Flint");
        this.survival_sickle_stone = settings.getBoolean("Survival.Sickles.Stone");
        this.survival_sickle_iron = settings.getBoolean("Survival.Sickles.Iron");
        this.survival_sickle_diamond = settings.getBoolean("Survival.Sickles.Diamond");

        this.survival_drop_rate_stick = settings.getDouble("Survival.DropRate.Stick");
        this.survival_drop_rate_flint = settings.getDouble("Survival.DropRate.Flint");

        // MECHANICS
        this.mechanics_shared_workbench = settings.getBoolean("Mechanics.SharedWorkbench");
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

        this.mechanics_raw_meat_hunger = settings.getBoolean("Mechanics.RawMeatHunger");
        this.mechanics_empty_potion = settings.getBoolean("Mechanics.EmptyPotions");
        this.mechanics_poison_potato = settings.getBoolean("Mechanics.PoisonousPotato");
        this.mechanics_cookie_boost = settings.getBoolean("Mechanics.CookieHealthBoost");
        this.mechanics_beet_strength = settings.getBoolean("Mechanics.BeetrootStrength");

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
        this.mechanics_compass_waypoint_worlds = settings.getBoolean("Mechanics.CompassWaypoint.per-world");
        this.mechanics_tropical_fish = settings.getBoolean("Mechanics.TropicalFish");
        this.mechanics_fermented_skin = settings.getBoolean("Mechanics.FermentedSkin");
        this.mechanics_living_slime = settings.getBoolean("Mechanics.LivingSlime");

        this.mechanics_snowball_revamp = settings.getBoolean("Mechanics.SnowballRevamp");
        this.MECHANICS_SNOW_GEN_REVAMP = settings.getBoolean("Mechanics.SnowGenerationRevamp");

        this.mechanics_farming_products_cookie = settings.getBoolean("Mechanics.FarmingProducts.Cookie");
        this.mechanics_farming_products_bread = settings.getBoolean("Mechanics.FarmingProducts.Bread");

        this.mechanics_chairs_enabled = settings.getBoolean("Mechanics.Chairs.Enabled");
        this.mechanics_chairs_max_width = settings.getInt("Mechanics.Chairs.MaxChairWidth");
        this.mechanics_chairs_blocks = getChairBlocks();

        this.mechanics_weather_enabled = settings.getBoolean("Mechanics.Weather.Enabled");
        this.mechanics_weather_speed_base = settings.getDouble("Mechanics.Weather.speed.base");
        this.mechanics_weather_speed_rain = settings.getDouble("Mechanics.Weather.speed.rain");
        this.mechanics_weather_speed_storm = settings.getDouble("Mechanics.Weather.speed.storm");
        this.mechanics_weather_speed_snow = settings.getDouble("Mechanics.Weather.speed.snow");
        this.mechanics_weather_speed_snowstorm = settings.getDouble("Mechanics.Weather.speed.snowstorm");

        // ITEM MECHANICS
        this.item_mechanics_firestriker_cook_time = settings.getInt("Item-Mechanics.firestriker.cook-time");

        // ENTITY MECHANICS
        this.entity_mechanics_pigmen_chest_enabled = settings.getBoolean("Entity-Mechanics.zombified-piglin-chests.enabled");
        this.entity_mechanics_pigmen_chest_radius = settings.getInt("Entity-Mechanics.zombified-piglin-chests.distance");
        this.entity_mechanics_pigmen_chest_speed = settings.getDouble("Entity-Mechanics.zombified-piglin-chests.speed-modifier");
        this.entity_mechanics_beekeeper_suit_enabled = settings.getBoolean("Entity-Mechanics.beekeeper-suit.enabled");
        this.entity_mechanics_suspicious_meat_enabled = settings.getBoolean("Entity-Mechanics.suspicious-meat.enabled");
        this.entity_mechanics_suspicious_meat_chance = settings.getInt("Entity-Mechanics.suspicious-meat.chance");
        this.entity_mechanics_chicken_breeding_enabled = settings.getBoolean("Entity-Mechanics.chicken-breeding.enabled");
        this.entity_mechanics_chicken_breeding_max_eggs = settings.getInt("Entity-Mechanics.chicken-breeding.max-eggs");
        this.entity_mechanics_chicken_breeding_always_baby = settings.getBoolean("Entity-Mechanics.chicken-breeding.always-baby");
        this.entity_mechanics_chicken_breeding_baby_ticks = settings.getInt("Entity-Mechanics.chicken-breeding.baby-ticks");
        this.entity_mechanics_piglin_drop_water = settings.getBoolean("Entity-Mechanics.piglin-barter.drop-purified-water");
        this.entity_mechanics_piglin_alt_drop = settings.getBoolean("Entity-Mechanics.piglin-barter.alternate-bartering");

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
        this.legendary_quartz_pickaxe = settings.getBoolean("LegendaryItems.QuartzPickaxe");
        this.legendary_obsidian_mace = settings.getBoolean("LegendaryItems.ObsidianMace");
        this.legendary_giant_blade = settings.getBoolean("LegendaryItems.GiantBlade");
        this.legendary_blaze_sword = settings.getBoolean("LegendaryItems.BlazeSword");
        this.legendary_notch_apple = settings.getBoolean("LegendaryItems.NotchApple");
        this.legendary_gold_armor_buff = settings.getBoolean("LegendaryItems.GoldArmorBuff");

        // HIDDEN CONFIG
        this.recipe_delay = settings.getInt("recipe-delay", 0);
    }

    private List<Material> getChairBlocks() {
        List<Material> materials = new ArrayList<>();
        List<String> allowedByStrings = settings.getStringList("Mechanics.Chairs.AllowedBlocks");
        for (String string : allowedByStrings) {
            if (string.startsWith("#")) {
                NamespacedKey key;
                if (string.contains(":")) key = NamespacedKey.fromString(string.substring(1));
                else key = NamespacedKey.minecraft(string.substring(1));

                if (key != null) {
                    Tag<Material> tag = Bukkit.getTag(Tag.REGISTRY_BLOCKS, key, Material.class);
                    if (tag != null) {
                        for (Material material : tag.getValues()) {
                            if (Tag.STAIRS.isTagged(material)) {
                                materials.add(material);
                            } else {
                                Utils.logMini("<red>Invalid chair material<white>: <yellow>%s", key.toString());
                            }
                        }
                    }
                }
            } else {
                NamespacedKey key;
                if (string.contains(":")) key = NamespacedKey.fromString(string);
                else key = NamespacedKey.minecraft(string);

                if (key != null) {
                    Material material = Registry.MATERIAL.get(key);
                    if (material != null) {
                        if (Tag.STAIRS.isTagged(material)) {
                            materials.add(material);
                        } else {
                            Utils.logMini("<red>Invalid chair material<white>: <yellow>%s", key.toString());
                        }
                    }

                }
            }
        }
        return materials;
    }

}
