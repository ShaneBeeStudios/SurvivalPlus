package tk.shanebee.survival.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.survival.Survival;

import java.io.File;
import java.io.IOException;

public class Lang {

    private Survival main;

    public String prefix;
    public String no_perm;
    public String survival_guide_msg;
    public String survival_guide_click_msg;
    public String survival_guide_hover_msg;
    public String survival_guide_link;

    public String resource_pack_accepted;
    public String resource_pack_declined;
    public String resource_pack_apply;
    public String resource_pack_required;

    public String task_must_use_shovel;
    public String task_must_use_axe;
    public String task_must_use_pick;
    public String task_must_use_sickle;
    public String task_must_use_shear;
    public String task_must_use_hammer;

    public String no_rename;
    public String period;
    public String charge;
    public String charge_ready;
    public String charge_unable;

    public String lack_of_energy;
    public String arrows_off_hand;
    public String arrows_off_hand_crossbow;
    public String bow_main_hand;
    public String recurved_bow;
    public String recurved_crossbow;
    public String recurved;

    public String fishing_off_hand;
    public String fishing_main_hand;
    public String grappling_off_hand;
    public String grappling_main_hand;
    public String compass_pointed;
    public String compass_coords;
    public String players_only;
    public String toggle_chat_local;
    public String toggle_chat_global;
    public String toggle_chat_disabled;
    public String invalid_arg;

    public String starved_eat;
    public String dehydrated_drink;
    public String hunger;
    public String thirst;
    public String carbohydrates;
    public String carbohydrates_lack;
    public String protein;
    public String protein_lack;
    public String vitamins;
    public String vitamins_lack;

    public String healing;
    public String healing_self;
    public String keep;
    public String on_hand;
    public String being_healed;
    public String stay_still;
    public String healing_complete;
    public String healing_interrupted;
    public String energized;
    public String sleepy;
    public String overworked;
    public String distressed;
    public String collapsed_1;
    public String collapsed_2;
    public String feeling_sleepy_1;
    public String feeling_sleepy_2;
    public String feeling_sleepy_3;
    public String energy_rising;
    public String locked;
    public String missing_component;
    public String in_main_hand;
    public String in_off_hand;
    public String attack_speed;
    public String attack_damage;
    public String right_click_sprinting;
    public String right_click_sneaking;
    public String decrease_hunger_value;
    public String hatchet;
    public String mattock;
    public String firestriker;
    public String firestriker_lore;
    public String firestriker_damaged;
    public String shiv;
    public String poisoned_enemy;
    public String poisoned_retain;
    public String reduce_50;
    public String grappling_hook;
    public String hammer;
    public String workbench;
    public String valkyrie_axe;
    public String valkyrie_axe_unable_dual;
    public String valkyrie_axe_spin;
    public String valkyrie_axe_cooldown;
    public String quartz_breaker;
    public String haste;
    public String obsidian_mace;
    public String cripple_hit;
    public String drain_hit;
    public String exhausted_slow;
    public String expire_disarm;
    public String knockback_resistance;
    public String ender_giant_blade;
    public String ender_giant_blade_unable_duel;
    public String ender_giant_blade_charge;
    public String ender_giant_blade_cooldown;
    public String half_shield_resistance;
    public String reflecting_coming;
    public String blaze_sword;
    public String blaze_sword_fire_resistance;
    public String blaze_sword_fiery;
    public String blaze_sword_spread_fire;
    public String blaze_sword_cost;
    public String reinforced_boots;
    public String reinforced_tunic;
    public String reinforced_pants;
    public String reinforced_hat;
    public String golden_sabatons;
    public String golden_guard;
    public String golden_greaves;
    public String golden_crown;
    public String fermented_skin;
    public String medical_kit;
    public String water_bowl;
    public String dirty_water;
    public String dirty_water_lore;
    public int dirty_water_color;
    public String clean_water;
    public String clean_water_lore;
    public int clean_water_color;
    public String purified_water;
    public String purified_water_lore;
    public int purified_water_color;
    public String coffee_bean_name;
    public String coffee_name;
    public int coffee_color;
    public String cold_milk_name;
    public int cold_milk_color;
    public String hot_milk_name;
    public int hot_milk_color;

    public String stone_sickle;
    public String iron_sickle;
    public String campfire_name;
    public String campfire_lore;



    public Lang(Survival main) {
        this.main = main;
    }

    public void loadLangFile(CommandSender sender) {
        String loaded;
        FileConfiguration lang;
        File lang_file = new File(main.getDataFolder(), "lang_EN.yml");
        if (!lang_file.exists()) {
            main.saveResource("lang_EN.yml", true);
            loaded = "&aNew lang_EN.yml created";
        } else {
            loaded = "&7lang_EN.yml &aloaded";
            updateLang(YamlConfiguration.loadConfiguration(lang_file), lang_file);
        }
        lang = YamlConfiguration.loadConfiguration(lang_file);

        prefix = lang.getString("prefix");
        no_perm = lang.getString("no-perm");
        survival_guide_msg = lang.getString("survival-guide-msg");
        survival_guide_click_msg = lang.getString("survival-guide-click-msg");
        survival_guide_hover_msg = lang.getString("survival-guide-hover-msg");
        survival_guide_link = lang.getString("survival-guide-link");
        resource_pack_accepted = lang.getString("resource-pack-accepted");
        resource_pack_declined = lang.getString("resource-pack-declined");
        resource_pack_apply = lang.getString("resource-pack-apply");
        resource_pack_required = lang.getString("resource-pack-required");
        task_must_use_shovel = lang.getString("task-must-use-shovel");
        task_must_use_axe = lang.getString("task-must-use-axe");
        task_must_use_pick = lang.getString("task-must-use-pick");
        task_must_use_sickle = lang.getString("task-must-use-sickle");
        task_must_use_shear = lang.getString("task-must-use-shear");
        task_must_use_hammer = lang.getString("task-must-use-hammer");
        no_rename = lang.getString("no-rename");
        period = lang.getString("period");
        charge = lang.getString("charge");
        charge_ready = lang.getString("charge-ready");
        charge_unable = lang.getString("charge-unable");
        lack_of_energy = lang.getString("lack-of-energy");
        arrows_off_hand = lang.getString("arrows-off-hand");
        arrows_off_hand_crossbow = lang.getString("arrows-off-hand-crossbow");
        bow_main_hand = lang.getString("bow-main-hand");
        recurved_bow = lang.getString("recurved-bow");
        recurved_crossbow = lang.getString("recurved-crossbow");
        recurved = lang.getString("recurved");
        fishing_off_hand = lang.getString("fishing-off-hand");
        fishing_main_hand = lang.getString("fishing-main-hand");
        grappling_off_hand = lang.getString("grappling-off-hand");
        grappling_main_hand = lang.getString("grappling-main-hand");
        compass_pointed = lang.getString("compass-pointed");
        compass_coords = lang.getString("compass-coords");
        players_only = lang.getString("players-only");
        toggle_chat_local = lang.getString("toggle-chat-local");
        toggle_chat_global = lang.getString("toggle-chat-global");
        toggle_chat_disabled = lang.getString("toggle-chat-disabled");
        invalid_arg = lang.getString("invalid-arg");
        starved_eat = lang.getString("starved-eat");
        dehydrated_drink = lang.getString("dehydrated-drink");
        hunger = lang.getString("hunger");
        thirst = lang.getString("thirst");
        carbohydrates = lang.getString("carbohydrates");
        carbohydrates_lack = lang.getString("carbohydrates-lack");
        protein = lang.getString("protein");
        protein_lack = lang.getString("protein-lack");
        vitamins = lang.getString("vitamins");
        vitamins_lack = lang.getString("vitamins-lack");
        healing = lang.getString("healing");
        healing_self = lang.getString("healing-self");
        keep = lang.getString("keep");
        on_hand = lang.getString("on-hand");
        being_healed = lang.getString("being-healed");
        stay_still = lang.getString("stay-still");
        healing_complete = lang.getString("healing-complete");
        healing_interrupted = lang.getString("healing-interrupted");
        energized = lang.getString("energized");
        sleepy = lang.getString("sleepy");
        overworked = lang.getString("overworked");
        distressed = lang.getString("distressed");
        collapsed_1 = lang.getString("collapsed-1");
        collapsed_2 = lang.getString("collapsed-2");
        feeling_sleepy_1 = lang.getString("feeling-sleepy-1");
        feeling_sleepy_2 = lang.getString("feeling-sleepy-2");
        feeling_sleepy_3 = lang.getString("feeling-sleepy-3");
        energy_rising = lang.getString("energy-rising");
        locked = lang.getString("locked");
        missing_component = lang.getString("missing-component");
        in_main_hand = lang.getString("in-main-hand");
        in_off_hand = lang.getString("in-off-hand");
        attack_speed = lang.getString("attack-speed");
        attack_damage = lang.getString("attack-damage");
        right_click_sneaking = lang.getString("right-click-sneaking");
        right_click_sprinting = lang.getString("right-click-sprinting");
        decrease_hunger_value = lang.getString("decrease-hunger-value");
        hatchet = lang.getString("hatchet");
        mattock = lang.getString("mattock");
        firestriker = lang.getString("firestriker");
        firestriker_lore = lang.getString("firestriker-lore");
        firestriker_damaged = lang.getString("firestriker-damaged");
        shiv = lang.getString("shiv");
        poisoned_enemy = lang.getString("poisoned-enemy");
        poisoned_retain = lang.getString("poisoned-retain");
        reduce_50 = lang.getString("reduce-50");
        grappling_hook = lang.getString("grappling-hook");
        hammer = lang.getString("hammer");
        workbench = lang.getString("workbench");
        valkyrie_axe = lang.getString("valkyrie-axe");
        valkyrie_axe_unable_dual = lang.getString("valkyrie-axe-unable-dual");
        valkyrie_axe_spin = lang.getString("valkyrie-axe-spin");
        valkyrie_axe_cooldown = lang.getString("valkyrie-axe-cooldown");
        quartz_breaker = lang.getString("quartz-breaker");
        haste = lang.getString("haste");
        obsidian_mace = lang.getString("obsidian-mace");
        cripple_hit = lang.getString("cripple-hit");
        drain_hit = lang.getString("drain-hit");
        exhausted_slow = lang.getString("exhausted-slow");
        expire_disarm = lang.getString("expire-disarm");
        knockback_resistance = lang.getString("knockback-resistance");
        ender_giant_blade = lang.getString("ender-giant-blade");
        ender_giant_blade_unable_duel = lang.getString("ender-giant-blade-unable-duel");
        ender_giant_blade_charge = lang.getString("ender-giant-blade-charge");
        ender_giant_blade_cooldown = lang.getString("ender-giant-blade-cooldown");
        half_shield_resistance = lang.getString("half-shield-resistance");
        reflecting_coming = lang.getString("reflecting-coming");
        blaze_sword = lang.getString("blaze-sword");
        blaze_sword_fire_resistance = lang.getString("blaze-sword-fire-resistance");
        blaze_sword_fiery = lang.getString("blaze-sword-fiery");
        blaze_sword_spread_fire = lang.getString("blaze-sword-spread-fire");
        blaze_sword_cost = lang.getString("blaze-sword-cost");
        reinforced_boots = lang.getString("reinforced-boots");
        reinforced_tunic = lang.getString("reinforced-tunic");
        reinforced_pants = lang.getString("reinforced-pants");
        reinforced_hat = lang.getString("reinforced-hat");
        golden_sabatons = lang.getString("golden-sabatons");
        golden_guard = lang.getString("golden-guard");
        golden_greaves = lang.getString("golden-greaves");
        golden_crown = lang.getString("golden-crown");
        fermented_skin = lang.getString("fermented-skin");
        medical_kit = lang.getString("medical-kit");
        water_bowl = lang.getString("water-bowl");
        dirty_water = lang.getString("dirty-water");
        dirty_water_lore = lang.getString("dirty-water-lore");
        dirty_water_color = lang.getInt("dirty-water-color");
        clean_water = lang.getString("clean-water");
        clean_water_lore = lang.getString("clean-water-lore");
        clean_water_color = lang.getInt("clean-water-color");
        purified_water = lang.getString("purified-water");
        purified_water_lore = lang.getString("purified-water-lore");
        purified_water_color = lang.getInt("purified-water-color");
        coffee_bean_name = lang.getString("coffee-bean-name");
        coffee_name = lang.getString("coffee-name");
        coffee_color = lang.getInt("coffee-color");
        cold_milk_name = lang.getString("cold-milk-name");
        cold_milk_color = lang.getInt("cold-milk-color");
        hot_milk_name = lang.getString("hot-milk-name");
        hot_milk_color = lang.getInt("hot-milk-color");

        stone_sickle = lang.getString("stone_sickle");
        iron_sickle = lang.getString("iron_sickle");
        campfire_name = lang.getString("campfire-name");
        campfire_lore = lang.getString("campfire-lore");

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + loaded));
    }

    private void updateLang(YamlConfiguration lang, File file) {
        // Coffee update - 3.2.0
        if (!lang.isSet("coffee-bean-name")) {
            lang.set("energy-rising", "Your energy is rising");
            lang.set("coffee-bean-name", "Coffee Bean");
            lang.set("coffee-name", "&3Coffee");
            lang.set("coffee-color", 9461779);
            lang.set("cold-milk-name", "&bCold Milk");
            lang.set("cold-milk-color", 16250871);
            lang.set("hot-milk-name", "&cHot Milk");
            lang.set("hot-milk-color", 15456977);
            saveLang(lang, file);
        }
        // Guide update - 3.3.0
        if (!lang.isSet("survival-guide-msg")) {
            lang.set("survival-guide-msg", "&6To see our survival guide ");
            lang.set("survival-guide-click-msg", "&bClick Here");
            lang.set("survival-guide-hover-msg", "Click for Guide");
            lang.set("survival-guide-link", "https://bitbucket.org/ShaneBeeStudios/SurvivalPlus/wiki/Getting-Started");
            saveLang(lang, file);
        }
        if (!lang.isSet("firestriker-lore")) {
            lang.set("firestriker-lore", "&7Right-Click to burn things||&bSneak-Right-Click for portable smelter||&7 -Put smeltable into input slot||&7 -Click output slot to smelt");
            saveLang(lang, file);
        }
    }

    private void saveLang(YamlConfiguration lang, File file) {
        try {
            lang.save(file);
            String prefix = lang.getString("prefix");
            Utils.sendColoredMsg(Bukkit.getConsoleSender(), prefix + "&7lang_EN.yml &aUpdated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
