package tk.shanebee.survival.util;

import tk.shanebee.survival.Survival;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Lang {

    private Survival main;

    public String prefix;
    public String no_perm;
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
    public String fatigue_level;
    public String energized;
    public String sleepy;
    public String overworked;
    public String distressed;
    public String collapsed_1;
    public String collapsed_2;
    public String feeling_sleepy_1;
    public String feeling_sleepy_2;
    public String feeling_sleepy_3;
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
    public String firestriker_damaged;
    public String shiv;
    public String poisoned_enemy;
    public String poisoned_retain;
    public String reduce_50;
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
    public String purified_water;

    public String stone_sickle;
    public String iron_sickle;



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
        }
        lang = YamlConfiguration.loadConfiguration(lang_file);

        prefix = lang.getString("prefix");
        no_perm = lang.getString("no-perm");
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
        fatigue_level = lang.getString("fatigure-level");
        energized = lang.getString("energized");
        sleepy = lang.getString("sleepy");
        overworked = lang.getString("overworked");
        distressed = lang.getString("distressed");
        collapsed_1 = lang.getString("collapsed-1");
        collapsed_2 = lang.getString("collapsed-2");
        feeling_sleepy_1 = lang.getString("feeling-sleepy-1");
        feeling_sleepy_2 = lang.getString("feeling-sleepy-2");
        feeling_sleepy_3 = lang.getString("feeling-sleepy-3");
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
        firestriker_damaged = lang.getString("firestriker-damaged");
        shiv = lang.getString("shiv");
        poisoned_enemy = lang.getString("poisoned-enemy");
        poisoned_retain = lang.getString("poisoned-retain");
        reduce_50 = lang.getString("reduce-50");
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
        purified_water = lang.getString("purified-water");

        stone_sickle = lang.getString("stone_sickle");
        iron_sickle = lang.getString("iron_sickle");


        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + loaded));
    }

}
