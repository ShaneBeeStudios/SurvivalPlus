package tk.shanebee.survival.item;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmokingRecipe;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Enums of all custom recipes
 */
public class Recipes {

    private static final Collection<NamespacedKey> ALL_RECIPE_KEYS = new HashSet<>();
    private static boolean INITIALIZED = false;

    // CUSTOM TOOLS/ITEMS
    public static Recipes HATCHET;
    public static Recipes MATTOCK;
    public static Recipes SHIV;
    public static Recipes HAMMER;
    public static Recipes WORKBENCH;
    public static Recipes FIRESTRIKER;
    public static Recipes VALKYRIES_AXE;
    public static Recipes QUARTZ_PICKAXE;
    public static Recipes OBSIDIAN_MACE;
    public static Recipes ENDER_GIANT_BLADE;
    public static Recipes BLAZE_SWORD;
    public static Recipes FERMENTED_SKIN;
    public static Recipes MEDIC_KIT;
    public static Recipes REINFORCED_LEATHER_BOOTS;
    public static Recipes REINFORCED_LEATHER_CHESTPLATE;
    public static Recipes REINFORCED_LEATHER_LEGGINGS;
    public static Recipes REINFORCED_LEATHER_HELMET;
    public static Recipes GOLD_SABATONS;
    public static Recipes GOLD_GUARD;
    public static Recipes GOLD_GREAVES;
    public static Recipes GOLD_CROWN;
    public static Recipes RECURVED_BOW;
    public static Recipes RECURVED_CROSSBOW;
    public static Recipes UNLIT_CAMPFIRE;
    public static Recipes FLINT_SICKLE;
    public static Recipes STONE_SICKLE;
    public static Recipes IRON_SICKLE;
    public static Recipes DIAMOND_SICKLE;
    public static Recipes GRAPPLING_HOOK;
    public static Recipes CLEAN_WATER_BOTTLES;
    public static Recipes COFFEE_BEAN;
    public static Recipes COLD_MILK;
    public static Recipes HOT_MILK;
    public static Recipes COFFEE;
    public static Recipes BEEKEEPER_SUIT;
    public static Recipes SNOW_BOOTS;
    public static Recipes RAIN_BOOTS;

    // VANILLA ITEMS
    public static Recipes ENCHANTED_GOLDEN_APPLE;
    public static Recipes SADDLE;
    public static Recipes NAMETAG;
    public static Recipes STRING_FROM_WEB;
    public static Recipes STRING_FROM_WOOL;
    public static Recipes IRON_HORSE_ARMOR;
    public static Recipes GOLD_HORSE_ARMOR;
    public static Recipes DIAMOND_HORSE_ARMOR;
    public static Recipes LEATHER_HORSE_ARMOR;
    public static Recipes TORCH;
    public static Recipes FLINT;
    public static Recipes FERMENTED_SPIDER_EYE;
    public static Recipes POISONOUS_POTATO;
    public static Recipes GLASS_BOTTLE;
    public static Recipes BOWL;
    public static Recipes FISHING_ROD;
    public static Recipes IRON_INGOT;
    public static Recipes IRON_NUGGET;
    public static Recipes GOLD_INGOT;
    public static Recipes GOLD_NUGGET;
    public static Recipes BREAD;
    public static Recipes COOKIE;
    public static Recipes SLIMEBALL;
    public static Recipes COBWEB;
    public static Recipes STICK;
    public static Recipes IRON_BOOTS;
    public static Recipes IRON_LEGGINGS;
    public static Recipes IRON_CHESTPLATE;
    public static Recipes IRON_HELMET;
    public static Recipes DIAMOND_BOOTS;
    public static Recipes DIAMOND_LEGGINGS;
    public static Recipes DIAMOND_CHESTPLATE;
    public static Recipes DIAMOND_HELMET;
    public static Recipes NETHERITE_BOOTS;
    public static Recipes NETHERITE_LEGGINGS;
    public static Recipes NETHERITE_CHESTPLATE;
    public static Recipes NETHERITE_HELMET;
    public static Recipes COMPASS;

    // VANILLA BLOCKS
    public static Recipes CLAY_BRICK;
    public static Recipes QUARTZ;
    public static Recipes FURNACE;
    public static Recipes CHEST;
    public static Recipes CLAY;
    public static Recipes DIORITE;
    public static Recipes ANDESITE;
    public static Recipes GRANITE;
    public static Recipes GRAVEL;
    public static Recipes ICE;
    public static Recipes PACKED_ICE;

    // SMELTING RECIPES
    public static Recipes FURNACE_IRON_INGOT;
    public static Recipes FURNACE_GOLD_INGOT;
    public static Recipes BLAST_IRON_INGOT;
    public static Recipes BLAST_GOLD_INGOT;

    private static Recipes register(boolean register, Recipe... recipes) {
        return new Recipes(register, recipes);
    }

    public static void init(Config config) {
        if (INITIALIZED) {
            throw new IllegalStateException("Recipes already initialized");
        }
        INITIALIZED = true;
        BEEKEEPER_SUIT = register(config.entity_mechanics_beekeeper_suit_enabled, Items.BEEKEEPER_HELMET.getRecipe(), Items.BEEKEEPER_CHESTPLATE.getRecipe(), Items.BEEKEEPER_LEGGINGS.getRecipe(), Items.BEEKEEPER_BOOTS.getRecipe());
        HATCHET = register(config.survival_enabled, Items.HATCHET.getRecipe());
        MATTOCK = register(config.survival_enabled, Items.MATTOCK.getRecipe());
        SHIV = register(config.survival_enabled, Items.SHIV.getRecipe());
        HAMMER = register(config.survival_enabled, Items.HAMMER.getRecipe());
        FIRESTRIKER = register(config.survival_enabled, Items.FIRESTRIKER.getRecipe());
        FLINT_SICKLE = register(config.survival_enabled && config.break_only_with_sickle && config.survival_sickle_flint, Items.FLINT_SICKLE.getRecipe());
        STONE_SICKLE = register(config.survival_enabled && config.break_only_with_sickle && config.survival_sickle_stone, Items.STONE_SICKLE.getRecipe());
        IRON_SICKLE = register(config.survival_enabled && config.break_only_with_sickle && config.survival_sickle_iron, Items.IRON_SICKLE.getRecipe());
        DIAMOND_SICKLE = register(config.survival_enabled && config.break_only_with_sickle && config.survival_sickle_diamond, Items.DIAMOND_SICKLE.getRecipe());
        WORKBENCH = register(config.survival_enabled && config.recipes_workbench, Items.WORKBENCH.getRecipe());
        VALKYRIES_AXE = register(config.legendary_valkyrie, Items.VALKYRIES_AXE.getRecipe());
        QUARTZ_PICKAXE = register(config.legendary_quartz_pickaxe, Items.QUARTZ_PICKAXE.getRecipe());
        OBSIDIAN_MACE = register(config.legendary_obsidian_mace, Items.OBSIDIAN_MACE.getRecipe());
        ENDER_GIANT_BLADE = register(config.legendary_giant_blade, Items.ENDER_GIANT_BLADE.getRecipe());
        BLAZE_SWORD = register(config.legendary_blaze_sword, Items.BLAZE_SWORD.getRecipe());
        SNOW_BOOTS = register(config.mechanics_weather_enabled, Items.SNOW_BOOTS.getRecipe());
        RAIN_BOOTS = register(config.mechanics_weather_enabled, Items.RAIN_BOOTS.getRecipe());
        FERMENTED_SKIN = register(config.mechanics_fermented_skin, Items.FERMENTED_SKIN.getRecipe());
        REINFORCED_LEATHER_HELMET = register(config.mechanics_reinforced_armor, Items.REINFORCED_LEATHER_HELMET.getRecipe());
        REINFORCED_LEATHER_CHESTPLATE = register(config.mechanics_reinforced_armor, Items.REINFORCED_LEATHER_TUNIC.getRecipe());
        REINFORCED_LEATHER_LEGGINGS = register(config.mechanics_reinforced_armor, Items.REINFORCED_LEATHER_TROUSERS.getRecipe());
        REINFORCED_LEATHER_BOOTS = register(config.mechanics_reinforced_armor, Items.REINFORCED_LEATHER_BOOTS.getRecipe());
        GOLD_CROWN = register(config.legendary_gold_armor_buff, Items.GOLDEN_CROWN.getRecipe());
        GOLD_GUARD = register(config.legendary_gold_armor_buff, Items.GOLDEN_GUARD.getRecipe());
        GOLD_GREAVES = register(config.legendary_gold_armor_buff, Items.GOLDEN_GREAVES.getRecipe());
        GOLD_SABATONS = register(config.legendary_gold_armor_buff, Items.GOLDEN_SABATONS.getRecipe());
        IRON_HELMET = register(config.mechanics_slow_armor, Items.IRON_HELMET.getRecipe());
        IRON_CHESTPLATE = register(config.mechanics_slow_armor, Items.IRON_CHESTPLATE.getRecipe());
        IRON_LEGGINGS = register(config.mechanics_slow_armor, Items.IRON_LEGGINGS.getRecipe());
        IRON_BOOTS = register(config.mechanics_slow_armor, Items.IRON_BOOTS.getRecipe());
        DIAMOND_HELMET = register(config.mechanics_slow_armor, Items.DIAMOND_HELMET.getRecipe());
        DIAMOND_CHESTPLATE = register(config.mechanics_slow_armor, Items.DIAMOND_CHESTPLATE.getRecipe());
        DIAMOND_LEGGINGS = register(config.mechanics_slow_armor, Items.DIAMOND_LEGGINGS.getRecipe());
        DIAMOND_BOOTS = register(config.mechanics_slow_armor, Items.DIAMOND_BOOTS.getRecipe());
        // TODO discovery for netherite
        NETHERITE_HELMET = register(config.mechanics_slow_armor, Items.NETHERITE_HELMET.getRecipe());
        NETHERITE_CHESTPLATE = register(config.mechanics_slow_armor, Items.NETHERITE_CHESTPLATE.getRecipe());
        NETHERITE_LEGGINGS = register(config.mechanics_slow_armor, Items.NETHERITE_LEGGINGS.getRecipe());
        NETHERITE_BOOTS = register(config.mechanics_slow_armor, Items.NETHERITE_BOOTS.getRecipe());
        MEDIC_KIT = register(config.mechanics_medic_kit, Items.MEDIC_KIT.getRecipe());
        RECURVED_BOW = register(config.mechanics_recurved_bow, Items.RECURVE_BOW.getRecipe());
        RECURVED_CROSSBOW = register(config.mechanics_recurved_bow, Items.RECURVE_CROSSBOW.getRecipe());
        UNLIT_CAMPFIRE = register(true, Items.CAMPFIRE.getRecipe());// TODO config?!?!
        GRAPPLING_HOOK = register(config.mechanics_grappling_hook, Items.GRAPPLING_HOOK.getRecipe());
        COFFEE_BEAN = register(config.mechanics_energy_coffee_enabled, Items.COFFEE_BEAN.getRecipe());
        COLD_MILK = register(config.mechanics_energy_coffee_enabled, Items.COLD_MILK.getRecipe());
        HOT_MILK = register(config.mechanics_energy_coffee_enabled, Items.HOT_MILK.getRecipe());
        COFFEE = register(config.mechanics_energy_coffee_enabled, Items.COFFEE.getRecipe());
        COMPASS = register(config.mechanics_compass_waypoint, Items.COMPASS.getRecipe());

        ShapedRecipe notchApple = new ShapedRecipe(Utils.getNamespacedKey("enchanted_golden_apple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1));
        notchApple.shape("@@@", "@*@", "@@@");
        notchApple.setIngredient('@', Material.GOLD_BLOCK);
        notchApple.setIngredient('*', Material.GOLDEN_APPLE);
        ENCHANTED_GOLDEN_APPLE = register(config.legendary_notch_apple, notchApple);

        ShapedRecipe saddle = new ShapedRecipe(Utils.getNamespacedKey("saddle"), new ItemStack(Material.SADDLE, 1));
        saddle.shape("@@@", "*-*", "= =");
        saddle.setIngredient('@', Material.LEATHER);
        saddle.setIngredient('*', Material.LEAD);
        saddle.setIngredient('-', Material.IRON_INGOT);
        saddle.setIngredient('=', Material.IRON_NUGGET);
        SADDLE = register(config.recipes_saddle, saddle);

        ShapedRecipe nametag = new ShapedRecipe(Utils.getNamespacedKey("nametag"), new ItemStack(Material.NAME_TAG, 1));
        nametag.shape(" -@", " *-", "*  ");
        nametag.setIngredient('@', Material.STRING);
        nametag.setIngredient('-', Material.IRON_INGOT);
        nametag.setIngredient('*', Material.PAPER);
        NAMETAG = register(config.recipes_name_tag, nametag);

        ShapedRecipe packedIce = new ShapedRecipe(Utils.getNamespacedKey("packed_ice"), new ItemStack(Material.PACKED_ICE, 1));
        packedIce.shape("@@ ", "@@ ");
        packedIce.setIngredient('@', Material.ICE);
        PACKED_ICE = register(config.recipes_packed_ice, packedIce);


        ShapedRecipe ice = new ShapedRecipe(Utils.getNamespacedKey("ice1"), new ItemStack(Material.ICE, 1));
        ShapelessRecipe ice2 = new ShapelessRecipe(Utils.getNamespacedKey("ice2"), new ItemStack(Material.ICE, 4));
        ice.shape("@@@", "@*@", "@@@");
        ice.setIngredient('@', Material.SNOWBALL);
        ice.setIngredient('*', Material.WATER_BUCKET);
        ice2.addIngredient(Material.PACKED_ICE);
        ICE = register(config.recipes_ice, ice, ice2);

        ShapedRecipe iron_horse_armor = new ShapedRecipe(Utils.getNamespacedKey("iron_horse_armor"), new ItemStack(Material.IRON_HORSE_ARMOR, 1));
        iron_horse_armor.shape("  @", "#-#", "= =");
        iron_horse_armor.setIngredient('#', Material.IRON_BLOCK);
        iron_horse_armor.setIngredient('@', Material.IRON_INGOT);
        iron_horse_armor.setIngredient('-', Material.LEATHER_HORSE_ARMOR);
        iron_horse_armor.setIngredient('=', Material.IRON_NUGGET);
        IRON_HORSE_ARMOR = register(config.recipes_iron_bard, iron_horse_armor);

        ShapedRecipe gold_horse_armor = new ShapedRecipe(Utils.getNamespacedKey("gold_horse_armor"), new ItemStack(Material.GOLDEN_HORSE_ARMOR, 1));
        gold_horse_armor.shape("  @", "#-#", "= =");
        gold_horse_armor.setIngredient('#', Material.GOLD_BLOCK);
        gold_horse_armor.setIngredient('@', Material.GOLD_INGOT);
        gold_horse_armor.setIngredient('-', Material.LEATHER_HORSE_ARMOR);
        gold_horse_armor.setIngredient('=', Material.GOLD_NUGGET);
        GOLD_HORSE_ARMOR = register(config.recipes_gold_bard, gold_horse_armor);

        ShapedRecipe diamond_horse_armor = new ShapedRecipe(Utils.getNamespacedKey("diamond_horse_armor"), new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1));
        diamond_horse_armor.shape("  H", "@-@", "B B");
        diamond_horse_armor.setIngredient('@', Material.DIAMOND);
        diamond_horse_armor.setIngredient('-', Material.IRON_HORSE_ARMOR);
        diamond_horse_armor.setIngredient('H', Material.DIAMOND_HELMET);
        diamond_horse_armor.setIngredient('B', Material.DIAMOND_BOOTS);
        DIAMOND_HORSE_ARMOR = register(config.recipes_diamond_bard, diamond_horse_armor);

        ShapedRecipe leather_horse_armor = new ShapedRecipe(Utils.getNamespacedKey("leather_horse_armor"), new ItemStack(Material.LEATHER_HORSE_ARMOR, 1));
        leather_horse_armor.shape("  C", "ABA", "A A");
        leather_horse_armor.setIngredient('A', Material.LEATHER);
        leather_horse_armor.setIngredient('B', Material.SADDLE);
        leather_horse_armor.setIngredient('C', Material.LEATHER_HELMET);
        LEATHER_HORSE_ARMOR = register(config.recipes_leather_bard, leather_horse_armor);

        ShapelessRecipe clay_brick = new ShapelessRecipe(Utils.getNamespacedKey("clay_brick"), new ItemStack(Material.BRICK, 4));
        clay_brick.addIngredient(Material.BRICKS);
        CLAY_BRICK = register(config.recipes_clay_brick, clay_brick);

        ShapelessRecipe quartz = new ShapelessRecipe(Utils.getNamespacedKey("quartz"), new ItemStack(Material.QUARTZ, 4));
        quartz.addIngredient(Material.QUARTZ_BLOCK);
        QUARTZ = register(config.recipes_quartz_block, quartz);

        ShapelessRecipe string_from_wool = new ShapelessRecipe(Utils.getNamespacedKey("string_from_wool"), new ItemStack(Material.STRING, 4));
        ShapelessRecipe string_from_cobweb = new ShapelessRecipe(Utils.getNamespacedKey("string_from_cobweb"), new ItemStack(Material.STRING, 2));
        string_from_wool.addIngredient(new RecipeChoice.MaterialChoice(Tag.WOOL));
        string_from_cobweb.addIngredient(Material.COBWEB);
        STRING_FROM_WEB = register(config.recipes_web_string, string_from_cobweb);
        STRING_FROM_WOOL = register(config.recipes_wool_string, string_from_wool);

        ShapedRecipe furnace = new ShapedRecipe(Utils.getNamespacedKey("furnace"), new ItemStack(Material.FURNACE, 1));
        furnace.shape("@@@", "@*@", "@@@");
        furnace.setIngredient('@', Material.BRICK);
        furnace.setIngredient('*', Items.FIRESTRIKER.getItemStack());
        FURNACE = register(config.survival_enabled && config.recipes_furnace, furnace);

        ShapedRecipe chest = new ShapedRecipe(Utils.getNamespacedKey("chest"), new ItemStack(Material.CHEST, 1));
        chest.shape("@@@", "@#@", "@@@");
        chest.setIngredient('@', new RecipeChoice.MaterialChoice(Tag.PLANKS));
        chest.setIngredient('#', Material.IRON_INGOT);
        CHEST = register(config.survival_enabled, chest);

        ShapedRecipe clay = new ShapedRecipe(Utils.getNamespacedKey("clay"), new ItemStack(Material.CLAY, 1));
        clay.shape("   ", "123", "   ");
        clay.setIngredient('1', Material.DIRT);
        clay.setIngredient('2', Material.SAND);
        clay.setIngredient('3', Items.WATER_BOWL.getItemStack());
        CLAY = register(config.recipes_clay, clay);

        ShapelessRecipe diorite = new ShapelessRecipe(Utils.getNamespacedKey("diorite"), new ItemStack(Material.DIORITE, 1));
        diorite.addIngredient(new RecipeChoice.MaterialChoice(Material.BONE_MEAL, Material.WHITE_DYE));
        diorite.addIngredient(Material.COBBLESTONE);
        DIORITE = register(config.recipes_diorite, diorite);

        ShapelessRecipe granite = new ShapelessRecipe(Utils.getNamespacedKey("granite"), new ItemStack(Material.GRANITE, 1));
        granite.addIngredient(Material.NETHERRACK);
        granite.addIngredient(Material.COBBLESTONE);
        GRANITE = register(config.recipes_granite, granite);

        ShapelessRecipe andesite = new ShapelessRecipe(Utils.getNamespacedKey("andesite"), new ItemStack(Material.ANDESITE, 1));
        andesite.addIngredient(Material.GRAVEL);
        andesite.addIngredient(Material.COBBLESTONE);
        ANDESITE = register(config.recipes_andesite, andesite);

        ShapedRecipe gravel = new ShapedRecipe(NamespacedKey.minecraft("gravel"), new ItemStack(Material.GRAVEL, 2));
        gravel.shape("@B", "B@");
        gravel.setIngredient('@', Material.SAND);
        gravel.setIngredient('B', Material.COBBLESTONE);
        GRAVEL = register(config.recipes_gravel, gravel);

        ShapedRecipe torch_from_firestriker = new ShapedRecipe(Utils.getNamespacedKey("torch_from_firestriker"), new ItemStack(Material.TORCH, 8));
        ShapedRecipe torch = new ShapedRecipe(Utils.getNamespacedKey("torch"), new ItemStack(Material.TORCH, 16));
        torch_from_firestriker.shape("AAA", "ABA", "AAA");
        torch_from_firestriker.setIngredient('B', Items.FIRESTRIKER.getItemStack());
        torch_from_firestriker.setIngredient('A', Material.STICK);
        torch_from_firestriker.setGroup("torch");

        torch.shape("ACA", "ABA", "AAA");
        torch.setIngredient('C', new RecipeChoice.MaterialChoice(Tag.ITEMS_COALS));
        torch.setIngredient('B', Items.FIRESTRIKER.getItemStack());
        torch.setIngredient('A', Material.STICK);
        torch.setGroup("torch");
        TORCH = register(config.survival_torch, torch_from_firestriker, torch);

        ShapelessRecipe flint = new ShapelessRecipe(NamespacedKey.minecraft("flint"), new ItemStack(Material.FLINT, 1));
        flint.addIngredient(Material.GRAVEL);
        FLINT = register(config.survival_enabled, flint);

        ShapelessRecipe fermented_spider_eye = new ShapelessRecipe(Utils.getNamespacedKey("fermented_spider_eye"), new ItemStack(Material.FERMENTED_SPIDER_EYE, 1));
        fermented_spider_eye.addIngredient(Material.SPIDER_EYE);
        fermented_spider_eye.addIngredient(Material.SUGAR);
        fermented_spider_eye.addIngredient(new RecipeChoice.MaterialChoice(Material.RED_MUSHROOM, Material.BROWN_MUSHROOM));
        FERMENTED_SPIDER_EYE = register(config.mechanics_fermented_skin, fermented_spider_eye); // TODO not sure about the config here

        ShapelessRecipe poisonousPotato = new ShapelessRecipe(Utils.getNamespacedKey("poisonous_potato"), new ItemStack(Material.POISONOUS_POTATO, 1));
        poisonousPotato.addIngredient(Material.POTATO);
        poisonousPotato.addIngredient(new RecipeChoice.MaterialChoice(Material.BONE_MEAL, Material.WHITE_DYE));
        POISONOUS_POTATO = register(config.mechanics_poison_potato, poisonousPotato);

        ShapelessRecipe glassBottle = new ShapelessRecipe(Utils.getNamespacedKey("glass_bottle"), new ItemStack(Material.GLASS_BOTTLE, 1));
        glassBottle.addIngredient(Material.POTION);
        GLASS_BOTTLE = register(config.mechanics_empty_potion, glassBottle);

        ShapedRecipe bowl = new ShapedRecipe(Utils.getNamespacedKey("bowl"), new ItemStack(Material.BOWL, 1));
        bowl.shape("  ", " 1");
        bowl.setIngredient('1', Items.WATER_BOWL.getItemStack());
        BOWL = register(config.mechanics_empty_potion, bowl);

        FurnaceRecipe clean_water_furnace = new FurnaceRecipe(Utils.getNamespacedKey("clean_water_furnace"),
            Items.CLEAN_WATER.getItemStack(), new RecipeChoice.ExactChoice(Items.DIRTY_WATER.getItemStack()), 0, 600);
        SmokingRecipe clean_water_smoker = new SmokingRecipe(Utils.getNamespacedKey("clean_water_smoker"),
            Items.CLEAN_WATER.getItemStack(), new RecipeChoice.ExactChoice(Items.DIRTY_WATER.getItemStack()), 0, 300);
        CampfireRecipe clean_water_camp = new CampfireRecipe(Utils.getNamespacedKey("clean_water_campfire"),
            Items.CLEAN_WATER.getItemStack(), new RecipeChoice.ExactChoice(Items.DIRTY_WATER.getItemStack()), 0, 2400);
        CLEAN_WATER_BOTTLES = register(config.mechanics_thirst_purify_water, clean_water_camp, clean_water_smoker, clean_water_furnace);

        ShapedRecipe fishing_rod = new ShapedRecipe(Utils.getNamespacedKey("fishing_rod"), new ItemStack(Material.FISHING_ROD, 1));
        fishing_rod.shape("1- ", "1 -", "1@*");
        fishing_rod.setIngredient('1', Material.STICK);
        fishing_rod.setIngredient('@', Material.IRON_INGOT);
        fishing_rod.setIngredient('-', Material.STRING);
        fishing_rod.setIngredient('*', Material.FEATHER);
        FISHING_ROD = register(config.recipes_fishing_rod, fishing_rod);

        ShapedRecipe iron_ingot = new ShapedRecipe(Utils.getNamespacedKey("iron_ingot"), new ItemStack(Material.IRON_INGOT, 1));
        iron_ingot.shape("@@", "@@");
        iron_ingot.setIngredient('@', Material.IRON_NUGGET);
        IRON_INGOT = register(config.mechanics_reduced_iron_nugget, iron_ingot);

        ShapelessRecipe iron_nugget = new ShapelessRecipe(Utils.getNamespacedKey("iron_nugget"), new ItemStack(Material.IRON_NUGGET, 4));
        iron_nugget.addIngredient(Material.IRON_INGOT);
        IRON_NUGGET = register(config.mechanics_reduced_iron_nugget, iron_nugget);

        ShapedRecipe gold_ingot = new ShapedRecipe(Utils.getNamespacedKey("gold_ingot"), new ItemStack(Material.GOLD_INGOT, 1));
        gold_ingot.shape("@@", "@@");
        gold_ingot.setIngredient('@', Material.GOLD_NUGGET);
        GOLD_INGOT = register(config.mechanics_reduced_gold_nugget, gold_ingot);

        ShapelessRecipe gold_nugget = new ShapelessRecipe(Utils.getNamespacedKey("gold_nugget"), new ItemStack(Material.GOLD_NUGGET, 4));
        gold_nugget.addIngredient(Material.GOLD_INGOT);
        GOLD_NUGGET = register(config.mechanics_reduced_gold_nugget, gold_nugget);

        FurnaceRecipe smelt_ironIngot = new FurnaceRecipe(Utils.getNamespacedKey("furnace_iron_ingot"),
            new ItemStack(Material.IRON_INGOT, 1), Material.IRON_ORE, 1, 400);
        FurnaceRecipe smelt_goldIngot = new FurnaceRecipe(Utils.getNamespacedKey("furnace_gold_ingot"),
            new ItemStack(Material.GOLD_INGOT, 1), Material.GOLD_ORE, 1, 400);
        BlastingRecipe blast_ironIngot = new BlastingRecipe(Utils.getNamespacedKey("blast_iron_ingot"),
            new ItemStack(Material.IRON_INGOT, 1), Material.IRON_ORE, 1, 100);
        BlastingRecipe blast_goldIngot = new BlastingRecipe(Utils.getNamespacedKey("blast_gold_ingot"),
            new ItemStack(Material.GOLD_INGOT, 1), Material.GOLD_ORE, 1, 100);
        FURNACE_IRON_INGOT = register(config.mechanics_reduced_iron_nugget, smelt_ironIngot);
        FURNACE_GOLD_INGOT = register(config.mechanics_reduced_gold_nugget, smelt_goldIngot);
        BLAST_IRON_INGOT = register(config.mechanics_reduced_iron_nugget, blast_ironIngot);
        BLAST_GOLD_INGOT = register(config.mechanics_reduced_gold_nugget, blast_goldIngot);

        ShapedRecipe bread = new ShapedRecipe(Utils.getNamespacedKey("bread"), new ItemStack(Material.BREAD, 2));
        bread.shape(" E ", "WWW");
        bread.setIngredient('E', Material.EGG);
        bread.setIngredient('W', Material.WHEAT);
        BREAD = register(config.mechanics_farming_products_bread, bread);

        ShapedRecipe cookie = new ShapedRecipe(Utils.getNamespacedKey("cookie"), new ItemStack(Material.COOKIE, 8));
        cookie.shape(" E ", "WCW", " S ");
        cookie.setIngredient('E', Material.EGG);
        cookie.setIngredient('W', Material.WHEAT);
        cookie.setIngredient('S', Material.SUGAR);
        cookie.setIngredient('C', Material.COCOA_BEANS);
        COOKIE = register(config.mechanics_farming_products_cookie, cookie);

        ShapelessRecipe slimeball = new ShapelessRecipe(Utils.getNamespacedKey("slimeball"), new ItemStack(Material.SLIME_BALL, 1));
        slimeball.addIngredient(Material.MILK_BUCKET);
        slimeball.addIngredient(8, Material.VINE);
        SLIMEBALL = register(config.recipes_slimeball, slimeball);

        ShapelessRecipe cobweb = new ShapelessRecipe(Utils.getNamespacedKey("cobweb"), new ItemStack(Material.COBWEB, 1));
        cobweb.addIngredient(Material.SLIME_BALL);
        cobweb.addIngredient(2, Material.STRING);
        COBWEB = register(config.recipes_cobweb, cobweb);

        ShapelessRecipe stick = new ShapelessRecipe(Utils.getNamespacedKey("stick"), new ItemStack(Material.STICK, 4));
        stick.addIngredient(new RecipeChoice.MaterialChoice(Tag.SAPLINGS));
        STICK = register(config.recipes_sapling_stick, stick);
    }

    private final Collection<NamespacedKey> keys;

    Recipes(boolean register, Recipe... recipes) {
        ArrayList<NamespacedKey> list = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe instanceof Keyed keyedRecipe) {
                list.add(keyedRecipe.getKey());
                ALL_RECIPE_KEYS.add(keyedRecipe.getKey());
                if (register) Bukkit.addRecipe(recipe);
            }
        }
        this.keys = list;
    }

    public List<NamespacedKey> getKeys() {
        return new ArrayList<>(this.keys);
    }

    public void unlock(Player player) {
        player.discoverRecipes(this.keys);
    }

    public static Collection<NamespacedKey> getAllRecipeKeys() {
        return ALL_RECIPE_KEYS;
    }

}
