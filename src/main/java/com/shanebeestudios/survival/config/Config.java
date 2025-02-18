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
        this.lang = this.settings.getString("Language");

        // SETTINGS
        this.settings_resource_pack_enabled = this.settings.getBoolean("settings.enable-resource-pack");
        this.settings_resource_pack_url = this.settings.getString("settings.resource-pack-url");
        this.settings_local_chat_distance = this.settings.getInt("settings.local-chat-distance");

        // WELCOME GUIDE
        this.welcome_guide_enabled = this.settings.getBoolean("welcome-guide.enabled");
        this.welcome_guide_new_players = this.settings.getBoolean("welcome-guide.new-players-only");
        this.welcome_guide_delay = this.settings.getInt("welcome-guide.delay");

        // SURVIVAL
        this.survival_enabled = this.settings.getBoolean("survival.enabled");
        this.survival_limited_crafting = this.settings.getBoolean("survival.LimitedCrafting"); // TODO remove
        this.survival_unlock_all_recipes = this.settings.getBoolean("survival.unlock-all-recipes-on-join");
        this.survival_remove_wood_tools = this.settings.getBoolean("survival.remove-wooden-tools");
        this.survival_torch = this.settings.getBoolean("survival.torch"); // TODO move to recipes area?!?!?
        this.survival_update_merchant_trades = this.settings.getBoolean("survival.update-merchant-trades");

        this.break_only_with_sickle = this.settings.getBoolean("survival.break-only-with.sickle");
        this.survival_break_only_with_shovel = this.settings.getBoolean("survival.break-only-with.shovel");
        this.survival_break_only_with_axe = this.settings.getBoolean("survival.break-only-with.axe");
        this.survival_break_only_with_pickaxe = this.settings.getBoolean("survival.break-only-with.pickaxe");
        this.survival_break_only_with_shears = this.settings.getBoolean("survival.break-only-with.shears");
        this.survival_place_only_with_hammer = this.settings.getBoolean("survival.place-only_with.hammer");

        this.survival_sickle_flint = this.settings.getBoolean("survival.sickles.flint");
        this.survival_sickle_stone = this.settings.getBoolean("survival.sickles.stone");
        this.survival_sickle_iron = this.settings.getBoolean("survival.sickles.iron");
        this.survival_sickle_diamond = this.settings.getBoolean("survival.sickles.diamond");

        this.survival_drop_rate_stick = this.settings.getDouble("survival.drop-rate.stick");
        this.survival_drop_rate_flint = this.settings.getDouble("survival.drop-rate.flint");

        // MECHANICS
        this.mechanics_slow_armor = this.settings.getBoolean("mechanics.slow-armor");
        this.mechanics_reinforced_armor = this.settings.getBoolean("mechanics.reinforced-leather-armor");
        this.MECHANICS_BOW = this.settings.getBoolean("mechanics.bow");
        this.mechanics_recurved_bow = this.settings.getBoolean("mechanics.recurve-bow");
        this.mechanics_grappling_hook = this.settings.getBoolean("mechanics.grappling-hook");
        this.mechanics_medic_kit = this.settings.getBoolean("mechanics.medical-kit");
        this.mechanics_reduced_iron_nugget = this.settings.getBoolean("mechanics.reduced-iron-nugget");
        this.mechanics_reduced_gold_nugget = this.settings.getBoolean("mechanics.reduced-gold-nugget");

        this.mechanics_status_scoreboard = this.settings.getBoolean("mechanics.status-scoreboard");
        this.MECHANICS_ALERT_INTERVAL = this.settings.getInt("mechanics.alert-interval");

        this.mechanics_raw_meat_hunger = this.settings.getBoolean("mechanics.raw-meat-hunger");
        this.mechanics_empty_potion = this.settings.getBoolean("mechanics.empty-potions");
        this.mechanics_poison_potato = this.settings.getBoolean("mechanics.poisonous-potato");
        this.mechanics_cookie_boost = this.settings.getBoolean("mechanics.cookie-health-boost");
        this.mechanics_beet_strength = this.settings.getBoolean("mechanics.beetroot-strength");

        this.mechanics_food_diversity_enabled = this.settings.getBoolean("mechanics.food-diversity.enabled");
        this.mechanics_food_max_level = this.settings.getInt("mechanics.food-diversity.max-level");
        this.mechanics_food_diversity_enabled = this.settings.getBoolean("mechanics.food-diversity.enabled");
        this.mechanics_food_start_carbs = this.settings.getInt("mechanics.food-diversity.start-level.carbs");
        this.mechanics_food_start_vitamins = this.settings.getInt("mechanics.food-diversity.start-level.vitamins");
        this.mechanics_food_start_protein = this.settings.getInt("mechanics.food-diversity.start-level.proteins");
        this.mechanics_food_diversity_enabled = this.settings.getBoolean("mechanics.food-diversity.enabled");
        this.mechanics_food_respawn_carbs = this.settings.getInt("mechanics.food-diversity.respawn-level.carbs");
        this.mechanics_food_respawn_vitamins = this.settings.getInt("mechanics.food-diversity.respawn-level.vitamins");
        this.mechanics_food_respawn_proteins = this.settings.getInt("mechanics.food-diversity.respawn-level.proteins");
        this.mechanics_food_effects_carbs_ex_amp_easy = this.settings.getInt("mechanics.food-diversity.effects.carbs.exhaustion-amplifier.easy");
        this.mechanics_food_effects_carbs_ex_amp_medium = this.settings.getInt("mechanics.food-diversity.effects.carbs.exhaustion-amplifier.normal");
        this.mechanics_food_effects_carbs_ex_amp_hard = this.settings.getInt("mechanics.food-diversity.effects.carbs.exhaustion-amplifier.hard");
        this.mechanics_food_effects_vitamins_ex_amp = this.settings.getInt("mechanics.food-diversity.effects.vitamins.exhaustion-amplifier");
        this.mechanics_food_effects_vitamins_se_normal_effect = this.settings.getString("mechanics.food-diversity.effects.vitamins.status-effects.normal.effect");
        this.mechanics_food_effects_vitamins_se_normal_amp = this.settings.getInt("mechanics.food-diversity.effects.vitamins.status-effects.normal.amplifier");
        this.mechanics_food_effects_vitamins_se_normal_duration = this.settings.getInt("mechanics.food-diversity.effects.vitamins.status-effects.normal.duration");
        this.mechanics_food_effects_vitamins_se_hard_effect = this.settings.getString("mechanics.food-diversity.effects.vitamins.status-effects.hard.effect");
        this.mechanics_food_effects_vitamins_se_hard_amp = this.settings.getInt("mechanics.food-diversity.effects.vitamins.status-effects.hard.amplifier");
        this.mechanics_food_effects_vitamins_se_hard_duration = this.settings.getInt("mechanics.food-diversity.effects.vitamins.status-effects.hard.duration");

        this.mechanics_food_effects_protein_ex_amp = this.settings.getInt("mechanics.food-diversity.effects.proteins.exhaustion-amplifier");
        this.mechanics_food_effects_protein_se_normal_effect = this.settings.getString("mechanics.food-diversity.effects.proteins.status-effects.normal.effect");
        this.mechanics_food_effects_protein_se_normal_amp = this.settings.getInt("mechanics.food-diversity.effects.proteins.status-effects.normal.amplifier");
        this.mechanics_food_effects_protein_se_normal_duration = this.settings.getInt("mechanics.food-diversity.effects.proteins.status-effects.normal.duration");
        this.mechanics_food_effects_protein_se_hard_effect = this.settings.getString("mechanics.food-diversity.effects.proteins.status-effects.hard.effect");
        this.mechanics_food_effects_protein_se_hard_amp = this.settings.getInt("mechanics.food-diversity.effects.proteins.status-effects.hard.amplifier");
        this.mechanics_food_effects_protein_se_hard_duration = this.settings.getInt("mechanics.food-diversity.effects.proteins.status-effects.hard.duration");

        this.mechanics_thirst_enabled = this.settings.getBoolean("mechanics.thirst.enabled");
        this.mechanics_thirst_starting_amount = this.settings.getInt("mechanics.thirst.starting-amount");
        this.mechanics_thirst_respawn_amount = this.settings.getInt("mechanics.thirst.respawn-amount");
        this.mechanics_thirst_purify_water = this.settings.getBoolean("mechanics.thirst.purify-water");
        this.mechanics_thirst_melt_snow = this.settings.getBoolean("mechanics.thirst.melt-snow");
        this.mechanics_thirst_drain_rate = this.settings.getDouble("mechanics.thirst.drain-rate");
        this.mechanics_thirst_heat_drain = this.settings.getInt("mechanics.thirst.heat-drain");
        this.mechanics_thirst_nether_drain = this.settings.getInt("mechanics.thirst.nether-drain");
        this.mechanics_thirst_damage_rate = this.settings.getDouble("mechanics.thirst.damage-rate");

        this.mechanics_thirst_rep_beetroot_soup = this.settings.getDouble("mechanics.thirst.replenish-level.beetroot-soup");
        this.mechanics_thirst_rep_melon_slice = this.settings.getDouble("mechanics.thirst.replenish-level.melon-slice");
        this.mechanics_thirst_rep_mush_stew = this.settings.getDouble("mechanics.thirst.replenish-level.mushroom-stew");
        this.mechanics_thirst_rep_milk_bucket = this.settings.getDouble("mechanics.thirst.replenish-level.milk-bucket");
        this.mechanics_thirst_rep_honey_bottle = this.settings.getDouble("mechanics.thirst.replenish-level.honey-bottle");
        this.mechanics_thirst_rep_other_water = this.settings.getDouble("mechanics.thirst.replenish-level.other-water");
        this.mechanics_thirst_rep_water = this.settings.getDouble("mechanics.thirst.replenish-level.water");

        this.mechanics_shared_workbench = this.settings.getBoolean("mechanics.shared-workbench");
        this.mechanics_energy_enabled = this.settings.getBoolean("mechanics.energy.enabled");
        this.mechanics_energy_start = this.settings.getDouble("mechanics.energy.start-level");
        this.mechanics_energy_respawn = this.settings.getDouble("mechanics.energy.respawn-level");
        this.mechanics_energy_warning = this.settings.getBoolean("mechanics.energy.warning");
        this.mechanics_energy_drain_rate = this.settings.getDouble("mechanics.energy.drain-rate");
        this.mechanics_energy_drain_cold_rate = this.settings.getDouble("mechanics.energy.cold-drain-rate");
        this.mechanics_energy_refresh_rate_bed = this.settings.getDouble("mechanics.energy.sleeping-refresh-rate");
        this.mechanics_energy_refresh_rate_chair = this.settings.getDouble("mechanics.energy.chair-refresh-rate");
        this.mechanics_energy_exhaustion = this.settings.getDouble("mechanics.energy.exhaustion");
        this.mechanics_energy_coffee_enabled = this.settings.getBoolean("mechanics.energy.coffee");
        this.mechanics_energy_absorption = this.settings.getBoolean("mechanics.energy.absorption");
        this.mechanics_energy_haste = this.settings.getBoolean("mechanics.energy.haste");

        this.mechanics_hunger_start_amount = this.settings.getInt("mechanics.hunger.starting-amount");
        this.mechanics_hunger_respawn_amount = this.settings.getInt("mechanics.hunger.respawn-amount");

        this.mechanics_compass_waypoint = this.settings.getBoolean("mechanics.compass-waypoint.enabled");
        this.mechanics_compass_waypoint_worlds = this.settings.getBoolean("mechanics.compass-waypoint.per-world");

        this.mechanics_tropical_fish = this.settings.getBoolean("mechanics.tropical-fish");
        this.mechanics_fermented_skin = this.settings.getBoolean("mechanics.fermented-skin");
        this.mechanics_living_slime = this.settings.getBoolean("mechanics.living-slime");

        this.mechanics_snowball_revamp = this.settings.getBoolean("mechanics.snowball-revamp");
        this.MECHANICS_SNOW_GEN_REVAMP = this.settings.getBoolean("mechanics.SnowGenerationRevamp");

        this.mechanics_farming_products_cookie = this.settings.getBoolean("mechanics.farming-products.cookie");
        this.mechanics_farming_products_bread = this.settings.getBoolean("mechanics.farming-products.bread");

        this.mechanics_chairs_enabled = this.settings.getBoolean("mechanics.chairs.enabled");
        this.mechanics_chairs_max_width = this.settings.getInt("mechanics.chairs.max-chair-width");
        this.mechanics_chairs_blocks = getChairBlocks();

        this.mechanics_weather_enabled = this.settings.getBoolean("mechanics.weather.enabled");
        this.mechanics_weather_speed_base = this.settings.getDouble("mechanics.weather.speed.base");
        this.mechanics_weather_speed_rain = this.settings.getDouble("mechanics.weather.speed.rain");
        this.mechanics_weather_speed_storm = this.settings.getDouble("mechanics.weather.speed.storm");
        this.mechanics_weather_speed_snow = this.settings.getDouble("mechanics.weather.speed.snow");
        this.mechanics_weather_speed_snowstorm = this.settings.getDouble("mechanics.weather.speed.snowstorm");

        // ITEM MECHANICS
        this.item_mechanics_firestriker_cook_time = this.settings.getInt("item-mechanics.firestriker.cook-time");

        // ENTITY MECHANICS
        this.entity_mechanics_pigmen_chest_enabled = this.settings.getBoolean("entity-mechanics.zombified-piglin-chests.enabled");
        this.entity_mechanics_pigmen_chest_radius = this.settings.getInt("entity-mechanics.zombified-piglin-chests.distance");
        this.entity_mechanics_pigmen_chest_speed = this.settings.getDouble("entity-mechanics.zombified-piglin-chests.speed-modifier");
        this.entity_mechanics_beekeeper_suit_enabled = this.settings.getBoolean("entity-mechanics.beekeeper-suit.enabled");
        this.entity_mechanics_suspicious_meat_enabled = this.settings.getBoolean("entity-mechanics.suspicious-meat.enabled");
        this.entity_mechanics_suspicious_meat_chance = this.settings.getInt("entity-mechanics.suspicious-meat.chance");
        this.entity_mechanics_chicken_breeding_enabled = this.settings.getBoolean("entity-mechanics.chicken-breeding.enabled");
        this.entity_mechanics_chicken_breeding_max_eggs = this.settings.getInt("entity-mechanics.chicken-breeding.max-eggs");
        this.entity_mechanics_chicken_breeding_always_baby = this.settings.getBoolean("entity-mechanics.chicken-breeding.always-baby");
        this.entity_mechanics_chicken_breeding_baby_ticks = this.settings.getInt("entity-mechanics.chicken-breeding.baby-ticks");
        this.entity_mechanics_piglin_drop_water = this.settings.getBoolean("entity-mechanics.piglin-barter.drop-purified-water");
        this.entity_mechanics_piglin_alt_drop = this.settings.getBoolean("entity-mechanics.piglin-barter.alternate-bartering");

        // RECIPES
        this.recipes_saddle = this.settings.getBoolean("recipes.saddle");
        this.recipes_name_tag = this.settings.getBoolean("recipes.nametag");
        this.recipes_packed_ice = this.settings.getBoolean("recipes.packed-ice");
        this.recipes_leather_bard = this.settings.getBoolean("recipes.leather-bard");
        this.recipes_iron_bard = this.settings.getBoolean("recipes.iron-bard");
        this.recipes_gold_bard = this.settings.getBoolean("recipes.gold-bard");
        this.recipes_diamond_bard = this.settings.getBoolean("recipes.diamond-bard");
        this.recipes_clay_brick = this.settings.getBoolean("recipes.clay-brick");
        this.recipes_quartz_block = this.settings.getBoolean("recipes.quartz-block");
        this.recipes_wool_string = this.settings.getBoolean("recipes.wool-string");
        this.recipes_web_string = this.settings.getBoolean("recipes.web-string");
        this.recipes_ice = this.settings.getBoolean("recipes.ice");
        this.recipes_clay = this.settings.getBoolean("recipes.clay");
        this.recipes_diorite = this.settings.getBoolean("recipes.diorite");
        this.recipes_granite = this.settings.getBoolean("recipes.granite");
        this.recipes_andesite = this.settings.getBoolean("recipes.andesite");
        this.recipes_gravel = this.settings.getBoolean("recipes.gravel");
        this.recipes_slimeball = this.settings.getBoolean("recipes.slimeball");
        this.recipes_cobweb = this.settings.getBoolean("recipes.cobweb");
        this.recipes_sapling_stick = this.settings.getBoolean("recipes.sapling-to-sticks");
        this.recipes_fishing_rod = this.settings.getBoolean("recipes.fishing-rod");
        this.recipes_furnace = this.settings.getBoolean("recipes.furnace");
        this.recipes_workbench = this.settings.getBoolean("recipes.workbench");

        // LEGENDARY ITEMS
        this.legendary_valkyrie = this.settings.getBoolean("legendary-items.valkyrie-axe");
        this.legendary_quartz_pickaxe = this.settings.getBoolean("legendary-items.quartz-pickaxe");
        this.legendary_obsidian_mace = this.settings.getBoolean("legendary-items.obsidian-mace");
        this.legendary_giant_blade = this.settings.getBoolean("legendary-items.giant-blade");
        this.legendary_blaze_sword = this.settings.getBoolean("legendary-items.blaze-sword");
        this.legendary_notch_apple = this.settings.getBoolean("legendary-items.notch-apple");
        this.legendary_gold_armor_buff = this.settings.getBoolean("legendary-items.gold-armor-buff");

        // HIDDEN CONFIG
        this.recipe_delay = this.settings.getInt("recipe-delay", 0);
    }

    private List<Material> getChairBlocks() {
        List<Material> materials = new ArrayList<>();
        List<String> allowedByStrings = this.settings.getStringList("mechanics.chairs.allowed-blocks");
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
