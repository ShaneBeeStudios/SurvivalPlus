package tk.shanebee.survival.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.ExactChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmokingRecipe;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.item.Items;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("DataFlowIssue")
public class RecipeManager {

    private final Config config;

    public RecipeManager(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
    }

    /**
     * Load all custom server recipes
     */
    public void loadCustomRecipes() {
        removeRecipes();

        //  ENCHANTED GOLDEN APPLE RECIPE
        ShapedRecipe notchApple = new ShapedRecipe(Utils.getNamespacedKey("enchanted_golden_apple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1));
        notchApple.shape("@@@", "@*@", "@@@");

        notchApple.setIngredient('@', Material.GOLD_BLOCK);
        notchApple.setIngredient('*', Material.GOLDEN_APPLE);


        //  SADDLE RECIPE
        ShapedRecipe saddle = new ShapedRecipe(Utils.getNamespacedKey("saddle"), new ItemStack(Material.SADDLE, 1));

        saddle.shape("@@@", "*-*", "= =");

        saddle.setIngredient('@', Material.LEATHER);
        saddle.setIngredient('*', Material.LEAD);
        saddle.setIngredient('-', Material.IRON_INGOT);
        saddle.setIngredient('=', Material.IRON_NUGGET);


        //  NAMETAG RECIPE
        ShapedRecipe nametag = new ShapedRecipe(Utils.getNamespacedKey("nametag"), new ItemStack(Material.NAME_TAG, 1));

        nametag.shape(" -@", " *-", "*  ");

        nametag.setIngredient('@', Material.STRING);
        nametag.setIngredient('-', Material.IRON_INGOT);
        nametag.setIngredient('*', Material.PAPER);


        //  PACKED ICE RECIPE
        ShapedRecipe packedIce1 = new ShapedRecipe(Utils.getNamespacedKey("packed_ice"), new ItemStack(Material.PACKED_ICE, 1));

        packedIce1.shape("@@ ", "@@ ");
        packedIce1.setIngredient('@', Material.ICE);


        //  ICE RECIPE
        ShapedRecipe ice = new ShapedRecipe(Utils.getNamespacedKey("ice1"), new ItemStack(Material.ICE, 1));
        ShapelessRecipe ice2 = new ShapelessRecipe(Utils.getNamespacedKey("ice2"), new ItemStack(Material.ICE, 4));

        ice.shape("@@@", "@*@", "@@@");

        ice.setIngredient('@', Material.SNOWBALL);
        ice.setIngredient('*', Material.WATER_BUCKET);

        ice2.addIngredient(Material.PACKED_ICE);


        //  IRON HORSE ARMOR RECIPE
        ShapedRecipe iron_horse_armor = new ShapedRecipe(Utils.getNamespacedKey("iron_horse_armor"), new ItemStack(Material.IRON_HORSE_ARMOR, 1));

        iron_horse_armor.shape("  @", "#-#", "= =");

        iron_horse_armor.setIngredient('#', Material.IRON_BLOCK);
        iron_horse_armor.setIngredient('@', Material.IRON_INGOT);
        iron_horse_armor.setIngredient('-', Material.LEATHER_HORSE_ARMOR);
        iron_horse_armor.setIngredient('=', Material.IRON_NUGGET);


        //  GOLD HORSE ARMOR RECIPE
        ShapedRecipe goldHorse1 = new ShapedRecipe(Utils.getNamespacedKey("gold_horse_armor"), new ItemStack(Material.GOLDEN_HORSE_ARMOR, 1));

        goldHorse1.shape("  @", "#-#", "= =");

        goldHorse1.setIngredient('#', Material.GOLD_BLOCK);
        goldHorse1.setIngredient('@', Material.GOLD_INGOT);
        goldHorse1.setIngredient('-', Material.LEATHER_HORSE_ARMOR);
        goldHorse1.setIngredient('=', Material.GOLD_NUGGET);


        //  DIAMOND HORSE ARMOR RECIPE
        ShapedRecipe diamond_horse_armor = new ShapedRecipe(Utils.getNamespacedKey("diamond_horse_armor"), new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1));

        diamond_horse_armor.shape("  H", "@-@", "B B");

        diamond_horse_armor.setIngredient('@', Material.DIAMOND);
        diamond_horse_armor.setIngredient('-', Material.IRON_HORSE_ARMOR);
        diamond_horse_armor.setIngredient('H', Material.DIAMOND_HELMET);
        diamond_horse_armor.setIngredient('B', Material.DIAMOND_BOOTS);


        // LEATHER HORSE ARMOR RECIPE
        ShapedRecipe leather_horse_armor = new ShapedRecipe(Utils.getNamespacedKey("leather_horse_armor"), new ItemStack(Material.LEATHER_HORSE_ARMOR, 1));

        leather_horse_armor.shape("  C", "ABA", "A A");

        leather_horse_armor.setIngredient('A', Material.LEATHER);
        leather_horse_armor.setIngredient('B', Material.SADDLE);
        leather_horse_armor.setIngredient('C', Material.LEATHER_HELMET);


        //  CLAY BRICK RECIPE
        ShapelessRecipe clayBrick = new ShapelessRecipe(Utils.getNamespacedKey("clay_brick"), new ItemStack(Material.BRICK, 4));

        clayBrick.addIngredient(Material.BRICKS);


        //  QUARTZ BLOCK RECIPE
        ShapelessRecipe quartz = new ShapelessRecipe(Utils.getNamespacedKey("quartz"), new ItemStack(Material.QUARTZ, 4));

        quartz.addIngredient(Material.QUARTZ_BLOCK);


        //  STRING RECIPE
        ShapelessRecipe woolString = new ShapelessRecipe(Utils.getNamespacedKey("string1"), new ItemStack(Material.STRING, 4));
        ShapelessRecipe string = new ShapelessRecipe(Utils.getNamespacedKey("string2"), new ItemStack(Material.STRING, 2));

        woolString.addIngredient(new RecipeChoice.MaterialChoice(Tag.WOOL));

        string.addIngredient(Material.COBWEB);
        //  FURNACE RECIPE
        ShapedRecipe furnace = new ShapedRecipe(NamespacedKey.minecraft("furnace"), new ItemStack(Material.FURNACE, 1));

        furnace.shape("@@@", "@*@", "@@@");

        furnace.setIngredient('@', Material.BRICK);
        furnace.setIngredient('*', Items.FIRESTRIKER.getItemStack());


        //  CHEST RECIPE
        ShapedRecipe chest = new ShapedRecipe(Utils.getNamespacedKey("chest"), new ItemStack(Material.CHEST, 1));

        chest.shape("@@@", "@#@", "@@@");

        chest.setIngredient('@', new RecipeChoice.MaterialChoice(Tag.PLANKS));
        chest.setIngredient('#', Material.IRON_INGOT);


        //  CLAY RECIPE
        ShapedRecipe clay = new ShapedRecipe(Utils.getNamespacedKey("clay"), new ItemStack(Material.CLAY, 1));

        clay.shape("   ", "123", "   ");
        clay.setIngredient('1', Material.DIRT);
        clay.setIngredient('2', Material.SAND);
        clay.setIngredient('3', Items.WATER_BOWL.getItemStack());


        //  DIORITE RECIPE
        ShapelessRecipe diorite = new ShapelessRecipe(Utils.getNamespacedKey("diorite"), new ItemStack(Material.DIORITE, 1));

        diorite.addIngredient(new RecipeChoice.MaterialChoice(Material.BONE_MEAL, Material.WHITE_DYE));
        diorite.addIngredient(Material.COBBLESTONE);


        //  GRANITE RECIPE
        ShapelessRecipe granite = new ShapelessRecipe(Utils.getNamespacedKey("granite"), new ItemStack(Material.GRANITE, 1));

        granite.addIngredient(Material.NETHERRACK);
        granite.addIngredient(Material.COBBLESTONE);


        //  ANDESITE RECIPE
        ShapelessRecipe andesite = new ShapelessRecipe(Utils.getNamespacedKey("andesite"), new ItemStack(Material.ANDESITE, 1));

        andesite.addIngredient(Material.GRAVEL);
        andesite.addIngredient(Material.COBBLESTONE);


        //  GRAVEL RECIPE
        ShapedRecipe gravel = new ShapedRecipe(NamespacedKey.minecraft("gravel"), new ItemStack(Material.GRAVEL, 2));

        gravel.shape("@B", "B@");

        gravel.setIngredient('@', Material.SAND);
        gravel.setIngredient('B', Material.COBBLESTONE);

        //  TORCH RECIPE
        ShapedRecipe torch1 = new ShapedRecipe(Utils.getNamespacedKey("torch1"), new ItemStack(Material.TORCH, 8));
        ShapedRecipe torch2 = new ShapedRecipe(Utils.getNamespacedKey("torch2"), new ItemStack(Material.TORCH, 16));

        torch1.shape("AAA", "ABA", "AAA");
        torch1.setIngredient('B', Items.FIRESTRIKER.getItemStack());
        torch1.setIngredient('A', Material.STICK);
        //torch1.setGroup("TORCH");

        torch2.shape("ACA", "ABA", "AAA");
        torch2.setIngredient('C', new RecipeChoice.MaterialChoice(Tag.ITEMS_COALS));
        torch2.setIngredient('B', Items.FIRESTRIKER.getItemStack());
        torch2.setIngredient('A', Material.STICK);
        //torch2.setGroup("TORCH");


        //  FLINT RECIPE
        ShapelessRecipe flint = new ShapelessRecipe(NamespacedKey.minecraft("flint"), new ItemStack(Material.FLINT, 1));

        flint.addIngredient(Material.GRAVEL);


        //  FERMENTED SPIDER EYE RECIPE
        ShapelessRecipe fermented_spider_eye = new ShapelessRecipe(Utils.getNamespacedKey("fermented_spider_eye"),
            new ItemStack(Material.FERMENTED_SPIDER_EYE, 1));

        fermented_spider_eye.addIngredient(Material.SPIDER_EYE);
        fermented_spider_eye.addIngredient(Material.SUGAR);
        fermented_spider_eye.addIngredient(new RecipeChoice.MaterialChoice(Material.RED_MUSHROOM, Material.BROWN_MUSHROOM));


        //  POISONOUS POTATO RECIPE
        ShapelessRecipe poisonousPotato = new ShapelessRecipe(Utils.getNamespacedKey("poisonous_potato"),
            new ItemStack(Material.POISONOUS_POTATO, 1));

        poisonousPotato.addIngredient(Material.POTATO);
        poisonousPotato.addIngredient(new RecipeChoice.MaterialChoice(Material.BONE_MEAL, Material.WHITE_DYE));


        //  GLASS BOTTLE RECIPE
        ShapelessRecipe glassBottle = new ShapelessRecipe(Utils.getNamespacedKey("glass_bottle"), new ItemStack(Material.GLASS_BOTTLE, 1));

        glassBottle.addIngredient(Material.POTION);


        //  BOWL RECIPE
        ShapedRecipe bowl = new ShapedRecipe(Utils.getNamespacedKey("bowl"), new ItemStack(Material.BOWL, 1));

        bowl.shape("  ", " 1");
        bowl.setIngredient('1', Items.WATER_BOWL.getItemStack());

        // CLEAN WATER RECIPES
        FurnaceRecipe clean_water_furnace = new FurnaceRecipe(NamespacedKey.fromString("survival_plus:clean_water_furnace"),
            Items.CLEAN_WATER.getItemStack(), new ExactChoice(Items.DIRTY_WATER.getItemStack()), 0, 600);

        SmokingRecipe clean_water_smoker = new SmokingRecipe(NamespacedKey.fromString("survival_plus:clean_water_smoker"),
            Items.CLEAN_WATER.getItemStack(), new ExactChoice(Items.DIRTY_WATER.getItemStack()), 0, 300);

        CampfireRecipe clean_water_camp = new CampfireRecipe(NamespacedKey.fromString("survival_plus:clean_water_campfire"),
            Items.CLEAN_WATER.getItemStack(), new ExactChoice(Items.DIRTY_WATER.getItemStack()), 0, 2400);


        //  FISHING ROD RECIPE
        ShapedRecipe fishing_rod = new ShapedRecipe(Utils.getNamespacedKey("fishing_rod"), new ItemStack(Material.FISHING_ROD, 1));

        fishing_rod.shape("1- ", "1 -", "1@*");

        fishing_rod.setIngredient('1', Material.STICK);
        fishing_rod.setIngredient('@', Material.IRON_INGOT);
        fishing_rod.setIngredient('-', Material.STRING);
        fishing_rod.setIngredient('*', Material.FEATHER);


        //  IRON INGOT RECIPE
        ShapedRecipe ironIngot = new ShapedRecipe(Utils.getNamespacedKey("iron_ingot"), new ItemStack(Material.IRON_INGOT, 1));
        ironIngot.shape("@@", "@@");
        ironIngot.setIngredient('@', Material.IRON_NUGGET);

        //  IRON NUGGET RECIPE
        ShapelessRecipe ironNugget = new ShapelessRecipe(Utils.getNamespacedKey("iron_nugget"), new ItemStack(Material.IRON_NUGGET, 4));
        ironNugget.addIngredient(Material.IRON_INGOT);

        //  GOLD INGOT RECIPE
        ShapedRecipe goldIngot = new ShapedRecipe(Utils.getNamespacedKey("gold_ingot"), new ItemStack(Material.GOLD_INGOT, 1));
        goldIngot.shape("@@", "@@");
        goldIngot.setIngredient('@', Material.GOLD_NUGGET);

        //  GOLD NUGGET RECIPE
        ShapelessRecipe goldNugget = new ShapelessRecipe(Utils.getNamespacedKey("gold_nugget"), new ItemStack(Material.GOLD_NUGGET, 4));
        goldNugget.addIngredient(Material.GOLD_INGOT);

        //  SMELTING RECIPES
        FurnaceRecipe smelt_ironIngot = new FurnaceRecipe(Utils.getNamespacedKey("furnace_iron_ingot"),
            new ItemStack(Material.IRON_INGOT, 1), Material.IRON_ORE, 1, 400);
        FurnaceRecipe smelt_goldIngot = new FurnaceRecipe(Utils.getNamespacedKey("furnace_gold_ingot"),
            new ItemStack(Material.GOLD_INGOT, 1), Material.GOLD_ORE, 1, 400);
        BlastingRecipe blast_ironIngot = new BlastingRecipe(Utils.getNamespacedKey("blast_iron_ingot"),
            new ItemStack(Material.IRON_INGOT, 1), Material.IRON_ORE, 1, 100);
        BlastingRecipe blast_goldIngot = new BlastingRecipe(Utils.getNamespacedKey("blast_gold_ingot"),
            new ItemStack(Material.GOLD_INGOT, 1), Material.GOLD_ORE, 1, 100);


        //  BREAD RECIPE
        ShapedRecipe bread = new ShapedRecipe(Utils.getNamespacedKey("bread"), new ItemStack(Material.BREAD, 2));

        bread.shape(" E ", "WWW");

        bread.setIngredient('E', Material.EGG);
        bread.setIngredient('W', Material.WHEAT);


        //  COOKIE RECIPE
        ShapedRecipe cookie = new ShapedRecipe(Utils.getNamespacedKey("cookie"), new ItemStack(Material.COOKIE, 8));

        cookie.shape(" E ", "WCW", " S ");

        cookie.setIngredient('E', Material.EGG);
        cookie.setIngredient('W', Material.WHEAT);
        cookie.setIngredient('S', Material.SUGAR);
        cookie.setIngredient('C', Material.COCOA_BEANS);


        //  SLIME BALL RECIPE
        ShapelessRecipe slimeball = new ShapelessRecipe(Utils.getNamespacedKey("slimeball"), new ItemStack(Material.SLIME_BALL, 1));

        slimeball.addIngredient(Material.MILK_BUCKET);
        slimeball.addIngredient(8, Material.VINE);


        //  COBWEB RECIPE
        ShapelessRecipe cobweb = new ShapelessRecipe(Utils.getNamespacedKey("cobweb"), new ItemStack(Material.COBWEB, 1));

        cobweb.addIngredient(Material.SLIME_BALL);
        cobweb.addIngredient(2, Material.STRING);


        //  SAPLING RECIPE
        ShapelessRecipe stick = new ShapelessRecipe(Utils.getNamespacedKey("stick"), new ItemStack(Material.STICK, 4));

        stick.addIngredient(new RecipeChoice.MaterialChoice(Tag.SAPLINGS));


        // BEEKEEPER RECIPES
        if (this.config.entity_mechanics_beekeeper_suit_enabled) {
            Items.BEEKEEPER_HELMET.registerRecipe();
            Items.BEEKEEPER_CHESTPLATE.registerRecipe();
            Items.BEEKEEPER_LEGGINGS.registerRecipe();
            Items.BEEKEEPER_BOOTS.registerRecipe();
        }

        if (this.config.mechanics_weather_enabled) {
            Items.SNOW_BOOTS.registerRecipe();
            Items.RAIN_BOOTS.registerRecipe();
        }

        //Add recipes
        if (this.config.survival_enabled) {
            Items.HATCHET.registerRecipe();
            Items.MATTOCK.registerRecipe();
            Items.SHIV.registerRecipe();
            Items.HAMMER.registerRecipe();
            Items.FIRESTRIKER.registerRecipe();
            Bukkit.addRecipe(chest);
            Bukkit.addRecipe(flint);
            Items.CAMPFIRE.registerRecipe();
            if (this.config.break_only_with_sickle) {
                if (this.config.survival_sickle_flint)
                    Items.FLINT_SICKLE.registerRecipe();
                if (this.config.survival_sickle_stone)
                    Items.STONE_SICKLE.registerRecipe();
                if (this.config.survival_sickle_iron)
                    Items.IRON_SICKLE.registerRecipe();
                if (this.config.survival_sickle_diamond)
                    Items.DIAMOND_SICKLE.registerRecipe();
            }
            if (this.config.recipes_workbench) {
                Items.WORKBENCH.registerRecipe();
            }
            if (this.config.recipes_furnace) {
                Bukkit.addRecipe(furnace);
            }
        }
        if (this.config.survival_torch) {
            Bukkit.addRecipe(torch1);
            Bukkit.addRecipe(torch2);
        }
        if (this.config.recipes_web_string)
            Bukkit.addRecipe(string);
        if (this.config.recipes_sapling_stick) {
            Bukkit.addRecipe(stick);
        }

        if (this.config.legendary_valkyrie) {
            Items.VALKYRIES_AXE.registerRecipe();
        }
        if (this.config.legendary_quartzpickaxe) {
            Items.QUARTZ_PICKAXE.registerRecipe();
        }
        if (this.config.legendary_obsidian_mace) {
            Items.OBSIDIAN_MACE.registerRecipe();
        }
        if (this.config.legendary_giantblade) {
            Items.ENDER_GIANT_BLADE.registerRecipe();
        }
        if (this.config.legendary_blazesword) {
            Items.BLAZE_SWORD.registerRecipe();
        }
        if (this.config.legendary_notch_apple)
            Bukkit.addRecipe(notchApple);
        if (this.config.recipes_saddle)
            Bukkit.addRecipe(saddle);
        if (this.config.recipes_name_tag) {
            Bukkit.addRecipe(nametag);
        }
        if (this.config.recipes_packed_ice) {
            Bukkit.addRecipe(packedIce1);
            Bukkit.addRecipe(ice2);
        }
        if (this.config.recipes_iron_bard) {
            Bukkit.addRecipe(iron_horse_armor);
        }
        if (this.config.recipes_gold_bard) {
            Bukkit.addRecipe(goldHorse1);
        }
        if (this.config.recipes_diamond_bard) {
            Bukkit.addRecipe(diamond_horse_armor);
        }
        if (this.config.recipes_leather_bard) {
            Bukkit.addRecipe(leather_horse_armor);
        }
        if (this.config.recipes_clay_brick)
            Bukkit.addRecipe(clayBrick);
        if (this.config.recipes_quartz_block)
            Bukkit.addRecipe(quartz);
        if (this.config.recipes_wool_string)
            Bukkit.addRecipe(woolString);
        if (this.config.recipes_ice)
            Bukkit.addRecipe(ice);
        if (this.config.recipes_clay)
            Bukkit.addRecipe(clay);
        if (this.config.recipes_diorite)
            Bukkit.addRecipe(diorite);
        if (this.config.recipes_granite)
            Bukkit.addRecipe(granite);
        if (this.config.recipes_andesite)
            Bukkit.addRecipe(andesite);
        if (this.config.recipes_gravel) {
            Bukkit.addRecipe(gravel);
        }
        if (this.config.mechanics_fermented_skin) {
            Items.FERMENTED_SKIN.registerRecipe();
        }
        if (this.config.mechanics_poison_potato)
            Bukkit.addRecipe(poisonousPotato);
        if (this.config.mechanics_empty_potion) {
            Bukkit.addRecipe(glassBottle);
            Bukkit.addRecipe(bowl);
        }
        if (this.config.mechanics_reinforced_armor) {
            Items.REINFORCED_LEATHER_HELMET.registerRecipe();
            Items.REINFORCED_LEATHER_TUNIC.registerRecipe();
            Items.REINFORCED_LEATHER_TROUSERS.registerRecipe();
            Items.REINFORCED_LEATHER_BOOTS.registerRecipe();
        }
        if (this.config.legendary_goldarmorbuff) {
            Items.GOLDEN_CROWN.registerRecipe();
            Items.GOLDEN_GUARD.registerRecipe();
            Items.GOLDEN_GREAVES.registerRecipe();
            Items.GOLDEN_SABATONS.registerRecipe();
        }

        if (this.config.mechanics_slow_armor) {
            Items.IRON_BOOTS.registerRecipe();
            Items.IRON_CHESTPLATE.registerRecipe();
            Items.IRON_LEGGINGS.registerRecipe();
            Items.IRON_HELMET.registerRecipe();
            Items.DIAMOND_BOOTS.registerRecipe();
            Items.DIAMOND_CHESTPLATE.registerRecipe();
            Items.DIAMOND_LEGGINGS.registerRecipe();
            Items.DIAMOND_HELMET.registerRecipe();
            Items.NETHERITE_BOOTS.registerRecipe();
            Items.NETHERITE_CHESTPLATE.registerRecipe();
            Items.NETHERITE_LEGGINGS.registerRecipe();
            Items.NETHERITE_HELMET.registerRecipe();
        }

        if (this.config.mechanics_medic_kit) {
            Items.MEDIC_KIT.registerRecipe();
        }

        if (this.config.recipes_fishing_rod) {
            Bukkit.addRecipe(fishing_rod);
        }

        if (this.config.mechanics_reduced_iron_nugget) {
            Bukkit.addRecipe(ironNugget);
            Bukkit.addRecipe(ironIngot);
            Bukkit.addRecipe(smelt_ironIngot);
            Bukkit.addRecipe(blast_ironIngot);
        }

        if (this.config.mechanics_reduced_gold_nugget) {
            Bukkit.addRecipe(goldNugget);
            Bukkit.addRecipe(goldIngot);
            Bukkit.addRecipe(smelt_goldIngot);
            Bukkit.addRecipe(blast_goldIngot);
        }

        if (this.config.mechanics_farming_products_bread)
            Bukkit.addRecipe(bread);
        if (this.config.mechanics_farming_products_cookie)
            Bukkit.addRecipe(cookie);
        if (this.config.recipes_slimeball)
            Bukkit.addRecipe(slimeball);
        if (this.config.recipes_cobweb)
            Bukkit.addRecipe(cobweb);
        if (this.config.mechanics_recurved_bow) {
            Items.RECURVE_BOW.registerRecipe();
            Items.RECURVE_CROSSBOW.registerRecipe();
        }
        if (this.config.mechanics_grappling_hook)
            Items.GRAPPLING_HOOK.registerRecipe();
        if (this.config.mechanics_thirst_purify_water) {
            Bukkit.addRecipe(clean_water_furnace);
            Bukkit.addRecipe(clean_water_smoker);
            Bukkit.addRecipe(clean_water_camp);
        }
        if (this.config.mechanics_energy_coffee_enabled) {
            Items.COFFEE_BEAN.registerRecipe();
            Items.COLD_MILK.registerRecipe();
            Items.HOT_MILK.registerRecipe();
            Items.COFFEE.registerRecipe();
        }
        if (this.config.mechanics_compass_waypoint) {
            Items.COMPASS.registerRecipe();
        }
    }

    /**
     * Enums of all custom recipes
     */
    public enum Recipes {
        // CUSTOM TOOLS/ITEMS
        HATCHET("hatchet"),
        MATTOCK("mattock"),
        SHIV("shiv"),
        HAMMER("hammer"),
        WORKBENCH("workbench"),
        FIRESTRIKER("firestriker"),
        VALKYRIES_AXE("valkyrie_axe"),
        QUARTZ_PICKAXE("quartz_pickaxe"),
        OBSIDIAN_MACE("obsidian_mace"),
        ENDER_GIANT_BLADE("ender_giant_blade"),
        BLAZE_SWORD("blaze_sword"),
        FERMENTED_SKIN("fermented_skin"),
        MEDIC_KIT("medic_kit"),
        REINFORCED_LEATHER_BOOTS("reinforced_leather_boots"),
        REINFORCED_LEATHER_CHESTPLATE("reinforced_leather_chestplate"),
        REINFORCED_LEATHER_LEGGINGS("reinforced_leather_leggings"),
        REINFORCED_LEATHER_HELMET("reinforced_leather_helmet"),
        GOLD_SABATONS("gold_sabatons"),
        GOLD_GUARD("gold_guard"),
        GOLD_GREAVES("gold_greaves"),
        GOLD_CROWN("gold_crown"),
        RECURVED_BOW("recurved_bow"),
        RECURVED_CROSSBOW("recurved_crossbow"),
        UNLIT_CAMPFIRE("unlit_campfire"),
        FLINT_SICKLE("flint_sickle"),
        STONE_SICKLE("stone_sickle"),
        IRON_SICKLE("iron_sickle"),
        DIAMOND_SICKLE("diamond_sickle"),
        GRAPPLING_HOOK("grappling_hook"),
        WATER_BOTTLES("clean_water_furnace", "clean_water_smoker", "clean_water_campfire"),
        COFFEE_BEAN("coffee_bean"),
        COLD_MILK("cold_milk"),
        HOT_MILK("hot_milk"),
        COFFEE("coffee"),
        BEEKEEPER_SUIT("beekeeper_helmet", "beekeeper_chestplate", "beekeeper_leggings", "beekeeper_boots"),
        SNOW_BOOTS("snow_boots"),
        RAIN_BOOTS("rain_boots"),

        // VANILLA ITEMS
        ENCHANTED_GOLDEN_APPLE("enchanted_golden_apple"),
        SADDLE("saddle"),
        NAMETAG("nametag"),
        STRING("string1", "string2"),
        IRON_HORSE_ARMOR("iron_horse_armor"),
        GOLD_HORSE_ARMOR("gold_horse_armor"),
        DIAMOND_HORSE_ARMOR("diamond_horse_armor"),
        LEATHER_HORSE_ARMOR("leather_horse_armor"),
        TORCH("torch1", "torch2"),
        FLINT("flint"),
        FERMENTED_SPIDER_EYE("fermented_spider_eye"),
        POISONOUS_POTATO("poisonous_potato"),
        GLASS_BOTTLE("glass_bottle"),
        BOWL("bowl"),
        FISHING_ROD("fishing_rod"),
        IRON_INGOT("iron_ingot"),
        IRON_NUGGET("iron_nugget"),
        GOLD_INGOT("gold_ingot"),
        GOLD_NUGGET("gold_nugget"),
        BREAD("bread"),
        COOKIE("cookie"),
        SLIMEBALL("slimeball"),
        COBWEB("cobweb"),
        STICK("stick"),
        IRON_BOOTS("iron_boots"),
        IRON_LEGGINGS("iron_leggings"),
        IRON_CHESTPLATE("iron_chestplate"),
        IRON_HELMET("iron_helmet"),
        DIAMOND_BOOTS("diamond_boots"),
        DIAMOND_LEGGINGS("diamond_leggings"),
        DIAMOND_CHESTPLATE("diamond_chestplate"),
        DIAMOND_HELMET("diamond_helmet"),
        COMPASS("compass"),

        // VANILLA BLOCKS
        CLAY_BRICK("clay_brick"),
        QUARTZ("quartz"),
        FURNACE("furnace"),
        CHEST("chest"),
        CLAY("clay"),
        DIORITE("diorite"),
        ANDESITE("andesite"),
        GRANITE("granite"),
        GRAVEL("gravel"),
        ICE("ice1", "ice2"),
        PACKED_ICE("packed_ice"),

        // SMELTING RECIPES
        FURNACE_IRON_INGOT("furnace_iron_ingot"),
        FURNACE_GOLD_INGOT("furnace_gold_ingot"),
        BLAST_IRON_INGOT("blast_iron_ingot"),
        BLAST_GOLD_INGOT("blast_gold_ingot");

        private final Collection<NamespacedKey> keys;
        private static final Collection<NamespacedKey> allKeys;

        static {
            allKeys = new ArrayList<>();
            for (Recipes recipes : values()) {
                allKeys.addAll(recipes.keys);
            }
        }

        Recipes(String... keys) {
            ArrayList<NamespacedKey> list = new ArrayList<>();
            for (String key : keys) {
                list.add(Utils.getNamespacedKey(key));
            }
            this.keys = list;
        }

        /**
         * Get the {@link NamespacedKey}s for this recipe
         *
         * @return NamespacedKeys for this recipe
         */
        public Collection<NamespacedKey> getKeys() {
            return this.keys;
        }

        private static Collection<NamespacedKey> getAllKeys() {
            return allKeys;
        }
    }

    private void removeRecipes() {
        if (this.config.survival_enabled) {
            removeRecipeByKey("campfire");
            removeRecipeByKey("chest");
            if (this.config.survival_torch) {
                removeRecipeByKey("torch");
            }
            if (this.config.recipes_furnace) {
                removeRecipeByKey("furnace");
            }
            if (this.config.recipes_workbench) {
                removeRecipeByKey("crafting_table");
            }
        }
        if (this.config.survival_remove_wood_tools) {
            removeRecipeByKey("wooden_sword");
            removeRecipeByKey("wooden_hoe");
            removeRecipeByKey("wooden_shovel");
            removeRecipeByKey("wooden_pickaxe");
            removeRecipeByKey("wooden_axe");
        }
        if (this.config.mechanics_reduced_iron_nugget) {
            removeRecipeByKey("iron_ingot");
            removeRecipeByKey("iron_ingot_from_nuggets");
            removeRecipeByKey("iron_nugget");
            removeRecipeByKey("iron_nugget_from_smelting");
        }
        if (this.config.mechanics_reduced_gold_nugget) {
            removeRecipeByKey("gold_ingot");
            removeRecipeByKey("gold_ingot_from_nuggets");
            removeRecipeByKey("gold_nugget");
            removeRecipeByKey("gold_nugget_from_smelting");

        }
        if (this.config.mechanics_slow_armor) {
            removeRecipeByKey("diamond_helmet");
            removeRecipeByKey("diamond_chestplate");
            removeRecipeByKey("diamond_leggings");
            removeRecipeByKey("diamond_boots");
            removeRecipeByKey("iron_helmet");
            removeRecipeByKey("iron_chestplate");
            removeRecipeByKey("iron_leggings");
            removeRecipeByKey("iron_boots");
        }
        if (this.config.mechanics_snowball_revamp) {
            removeRecipeByKey("snow");
            removeRecipeByKey("snow_block");
        }
        if (this.config.mechanics_farming_products_cookie) {
            removeRecipeByKey("cookie");
        }
        if (this.config.mechanics_farming_products_bread) {
            removeRecipeByKey("bread");
        }
        if (this.config.legendary_goldarmorbuff) {
            removeRecipeByKey("golden_helmet");
            removeRecipeByKey("golden_chestplate");
            removeRecipeByKey("golden_boots");
            removeRecipeByKey("golden_leggings");
        }
        if (this.config.legendary_blazesword) {
            removeRecipeByKey("golden_sword");
        }
        if (this.config.legendary_giantblade) {
            removeRecipeByKey("golden_hoe");
        }
        if (this.config.legendary_quartzpickaxe) {
            removeRecipeByKey("golden_pickaxe");
        }
        if (this.config.legendary_obsidian_mace) {
            removeRecipeByKey("golden_shovel");
        }
        if (this.config.legendary_valkyrie) {
            removeRecipeByKey("golden_axe");
        }
        if (this.config.recipes_granite) {
            removeRecipeByKey("granite");
        }
        if (this.config.recipes_andesite) {
            removeRecipeByKey("andesite");
        }
        if (this.config.recipes_diorite) {
            removeRecipeByKey("diorite");
        }
        if (this.config.recipes_leather_bard) {
            removeRecipeByKey("leather_horse_armor");
        }
        if (this.config.recipes_fishing_rod) {
            removeRecipeByKey("fishing_rod");
        }
        if (this.config.mechanics_compass_waypoint) {
            removeRecipeByKey("compass");
        }
        if (this.config.recipes_packed_ice) {
            removeRecipeByKey("packed_ice");
        }
    }

    /**
     * Unlock all custom recipes for a player
     *
     * @param player Player to unlock recipes for
     */
    public void unlockAllRecipes(Player player) {
        player.discoverRecipes(Recipes.getAllKeys());
    }

    /**
     * Remove a vanilla Minecraft recipe from the server
     *
     * @param recipeKey Recipe to remove
     */
    @SuppressWarnings("WeakerAccess")
    public void removeRecipeByKey(String recipeKey) {
        Bukkit.removeRecipe(NamespacedKey.minecraft(recipeKey));
    }

}
