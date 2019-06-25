package tk.shanebee.survival.managers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.*;
import org.bukkit.inventory.RecipeChoice.ExactChoice;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class RecipeManager {


    private FileConfiguration settings;
    private static Survival survival;

    public RecipeManager(Survival survival, FileConfiguration settings) {
        RecipeManager.survival = survival;
        this.settings = settings;
    }

    /**
     *  Load all custom server recipes
     */
    @SuppressWarnings("deprecation")
    public void loadCustomRecipes() {
        removeRecipes();

        // HATCHET RECIPE
        ShapedRecipe hatchet1 = new ShapedRecipe(new NamespacedKey(survival, "hatchet1"), Items.get(Items.HATCHET));
        ShapedRecipe hatchet2 = new ShapedRecipe(new NamespacedKey(survival, "hatchet2"), Items.get(Items.HATCHET));

        hatchet1.shape("@@", " 1");

        hatchet1.setIngredient('@', Material.FLINT);
        hatchet1.setIngredient('1', Material.STICK);
        hatchet1.setGroup("HATCHET");

        hatchet2.shape("@@", "1 ");

        hatchet2.setIngredient('@', Material.FLINT);
        hatchet2.setIngredient('1', Material.STICK);
        hatchet2.setGroup("HATCHET");


        // MATTOCK RECIPE
        ShapedRecipe mattock = new ShapedRecipe(new NamespacedKey(survival, "mattock"), Items.get(Items.MATTOCK));

        mattock.shape("@-", "1@");
        mattock.setIngredient('@', Material.FLINT);
        mattock.setIngredient('-', new RecipeChoice.MaterialChoice(Tag.PLANKS));
        mattock.setIngredient('1', Material.STICK);



        // SHIV RECIPE
        ShapedRecipe shiv = new ShapedRecipe(new NamespacedKey(survival, "shiv"), Items.get(Items.SHIV));

        shiv.shape("*@", "1&");

        shiv.setIngredient('@', Material.FLINT);
        shiv.setIngredient('1', Material.STICK);
        shiv.setIngredient('*', Material.STRING);
        shiv.setIngredient('&', Material.SPIDER_EYE);


        // HAMMER RECIPE
        ShapedRecipe hammer = new ShapedRecipe(new NamespacedKey(survival, "hammer"), Items.get(Items.HAMMER));

        hammer.shape("@ ", "1@");

        hammer.setIngredient('@', Material.COBBLESTONE);
        hammer.setIngredient('1', Material.STICK);


        // VALKYRIE's AXE RECIPE
        ShapedRecipe valkyries_axe = new ShapedRecipe(new NamespacedKey(survival, "valkyrie_axe"), Items.get(Items.VALKYRIES_AXE));

        valkyries_axe.shape("@@@", "@*@", " 1 ");

        valkyries_axe.setIngredient('@', Material.DIAMOND);
        valkyries_axe.setIngredient('*', Material.NETHER_STAR);
        valkyries_axe.setIngredient('1', Material.STICK);


        // QUARTZ PICKAXE RECIPE
        ShapedRecipe quartz_pickaxe = new ShapedRecipe(new NamespacedKey(survival, "quartz_pickaxe"), Items.get(Items.QUARTZ_PICKAXE));
        quartz_pickaxe.shape("@B-", "B# ", "- 1");

        quartz_pickaxe.setIngredient('@', Material.QUARTZ_BLOCK);
        quartz_pickaxe.setIngredient('-', Material.DIAMOND);
        quartz_pickaxe.setIngredient('B', Material.DIAMOND_BLOCK);
        quartz_pickaxe.setIngredient('1', Material.STICK);
        quartz_pickaxe.setIngredient('#', Material.DRAGON_EGG);


        //  OBSIDIAN MACE RECIPE
        ShapedRecipe obsidian_mace = new ShapedRecipe(new NamespacedKey(survival, "obsidian_mace"), Items.get(Items.OBSIDIAN_MACE));

        obsidian_mace.shape(" @@", " &@", "1  ");

        obsidian_mace.setIngredient('@', Material.OBSIDIAN);
        obsidian_mace.setIngredient('&', Material.END_CRYSTAL);
        obsidian_mace.setIngredient('1', Material.STICK);


        // ENDER GIANT BLADE RECIPE
        ShapedRecipe ender_giant_blade = new ShapedRecipe(new NamespacedKey(survival, "ender_giant_blade"), Items.get(Items.ENDER_GIANT_BLADE));

        ender_giant_blade.shape(" @@", "B*@", "1B ");

        ender_giant_blade.setIngredient('*', Material.ENDER_EYE);
        ender_giant_blade.setIngredient('@', Material.DIAMOND);
        ender_giant_blade.setIngredient('B', Material.DIAMOND_BLOCK);
        ender_giant_blade.setIngredient('1', new RecipeChoice.MaterialChoice(Tag.PLANKS));


        //  BLAZE SWORD RECIPE
        ShapedRecipe blaze_sword = new ShapedRecipe(new NamespacedKey(survival, "blaze_sword"), Items.get(Items.BLAZE_SWORD));
        blaze_sword.shape("*@*", "*@*", "*1*");

        blaze_sword.setIngredient('@', Material.GOLD_INGOT);
        blaze_sword.setIngredient('1', Material.BLAZE_ROD);
        blaze_sword.setIngredient('*', Material.BLAZE_POWDER);


        //  NOTCH APPLE RECIPE
        ShapedRecipe notchApple = new ShapedRecipe(new NamespacedKey(survival, "enchanted_golden_apple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1));
        notchApple.shape("@@@", "@*@", "@@@");

        notchApple.setIngredient('@', Material.GOLD_BLOCK);
        notchApple.setIngredient('*', Material.APPLE);


        //  SADDLE RECIPE
        ShapedRecipe saddle = new ShapedRecipe(new NamespacedKey(survival, "saddle"), new ItemStack(Material.SADDLE, 1));

        saddle.shape("@@@", "*-*", "= =");

        saddle.setIngredient('@', Material.LEATHER);
        saddle.setIngredient('*', Material.LEAD);
        saddle.setIngredient('-', Material.IRON_INGOT);
        saddle.setIngredient('=', Material.IRON_NUGGET);


        //  NAMETAG RECIPE
        ShapedRecipe nametag = new ShapedRecipe(new NamespacedKey(survival, "nametag"), new ItemStack(Material.NAME_TAG, 1));

        nametag.shape(" -@", " *-", "*  ");

        nametag.setIngredient('@', Material.STRING);
        nametag.setIngredient('-', Material.IRON_INGOT);
        nametag.setIngredient('*', Material.PAPER);


        //  PACKED ICE RECIPE
        ShapedRecipe packedIce1 = new ShapedRecipe(new NamespacedKey(survival, "packed_ice"), new ItemStack(Material.PACKED_ICE, 1));

        packedIce1.shape("@@ ", "@@ ");
        packedIce1.setIngredient('@', Material.ICE);


        //  ICE RECIPE
        ShapedRecipe ice = new ShapedRecipe(new NamespacedKey(survival, "ice1"), new ItemStack(Material.ICE, 1));
        ShapelessRecipe ice2 = new ShapelessRecipe(new NamespacedKey(survival, "ice2"), new ItemStack(Material.ICE, 4));

        ice.shape("@@@", "@*@", "@@@");

        ice.setIngredient('@', Material.SNOWBALL);
        ice.setIngredient('*', Material.WATER_BUCKET);

        ice2.addIngredient(Material.PACKED_ICE);


        //  IRON HORSE ARMOR RECIPE
        ShapedRecipe iron_horse_armor = new ShapedRecipe(new NamespacedKey(survival, "iron_horse_armor"), new ItemStack(Material.IRON_HORSE_ARMOR, 1));

        iron_horse_armor.shape("  @", "#-#", "= =");

        iron_horse_armor.setIngredient('#', Material.IRON_BLOCK);
        iron_horse_armor.setIngredient('@', Material.IRON_INGOT);
        iron_horse_armor.setIngredient('-', Material.LEATHER_HORSE_ARMOR);
        iron_horse_armor.setIngredient('=', Material.IRON_NUGGET);


        //  GOLD HORSE ARMOR RECIPE
        ShapedRecipe goldHorse1 = new ShapedRecipe(new NamespacedKey(survival, "gold_horse_armor"), new ItemStack(Material.GOLDEN_HORSE_ARMOR, 1));

        goldHorse1.shape("  @", "#-#", "= =");

        goldHorse1.setIngredient('#', Material.GOLD_BLOCK);
        goldHorse1.setIngredient('@', Material.GOLD_INGOT);
        goldHorse1.setIngredient('-', Material.LEATHER_HORSE_ARMOR);
        goldHorse1.setIngredient('=', Material.GOLD_NUGGET);


        //  DIAMOND HORSE ARMOR RECIPE
        ShapedRecipe diamond_horse_armor = new ShapedRecipe(new NamespacedKey(survival, "diamond_horse_armor"), new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1));

        diamond_horse_armor.shape("  H", "@-@", "B B");

        diamond_horse_armor.setIngredient('@', Material.DIAMOND);
        diamond_horse_armor.setIngredient('-', Material.IRON_HORSE_ARMOR);
        diamond_horse_armor.setIngredient('H', Material.DIAMOND_HELMET);
        diamond_horse_armor.setIngredient('B', Material.DIAMOND_BOOTS);


        // LEATHER HORSE ARMOR RECIPE
        ShapedRecipe leather_horse_armor = new ShapedRecipe(new NamespacedKey(survival, "leather_horse_armor"), new ItemStack(Material.LEATHER_HORSE_ARMOR, 1));

        leather_horse_armor.shape("  C", "ABA", "A A");

        leather_horse_armor.setIngredient('A', Material.LEATHER);
        leather_horse_armor.setIngredient('B', Material.SADDLE);
        leather_horse_armor.setIngredient('C', Material.LEATHER_HELMET);


        //  CLAY BRICK RECIPE
        ShapelessRecipe clayBrick = new ShapelessRecipe(new NamespacedKey(survival, "clay_brick"), new ItemStack(Material.BRICK, 4));

        clayBrick.addIngredient(Material.BRICKS);


        //  QUARTZ BLOCK RECIPE
        ShapelessRecipe quartz = new ShapelessRecipe(new NamespacedKey(survival, "quartz"), new ItemStack(Material.QUARTZ, 4));

        quartz.addIngredient(Material.QUARTZ_BLOCK);


        //  STRING RECIPE
        ShapelessRecipe woolString = new ShapelessRecipe(new NamespacedKey(survival, "string1"), new ItemStack(Material.STRING, 4));
        ShapelessRecipe string = new ShapelessRecipe(new NamespacedKey(survival, "string2"), new ItemStack(Material.STRING, 2));

        woolString.addIngredient(new RecipeChoice.MaterialChoice(Tag.WOOL));

        string.addIngredient(Material.COBWEB);


        //  REPAIR RECIPE
        ShapedRecipe repair_blaze_sword = new ShapedRecipe(new NamespacedKey(survival, "repair_blaze_sword"), Items.get(Items.BLAZE_SWORD));
        repair_blaze_sword.shape("123");

        repair_blaze_sword.setIngredient('1', new ExactChoice(Utils.getItemStackDura(Items.BLAZE_SWORD, 31)));
        repair_blaze_sword.setIngredient('2', Material.BLAZE_POWDER);
        repair_blaze_sword.setIngredient('3', Material.BLAZE_POWDER);



        //  REPAIR RECIPE
        ShapedRecipe repair_ender_giant_blaze = new ShapedRecipe(new NamespacedKey(survival, "repair_ender_giant_blaze"), Items.get(Items.ENDER_GIANT_BLADE));
        repair_ender_giant_blaze.shape("123");
        repair_ender_giant_blaze.setIngredient('1', new ExactChoice(Utils.getItemStackDura(Items.ENDER_GIANT_BLADE, 31)));
        repair_ender_giant_blaze.setIngredient('2', Material.ENDER_PEARL);
        repair_ender_giant_blaze.setIngredient('3', Material.DIAMOND_BLOCK);


        //  REPAIR RECIPE
        ShapedRecipe repair_quartz_pickaxe = new ShapedRecipe(new NamespacedKey(survival, "repair_quartz_pickaxe"), Items.get(Items.QUARTZ_PICKAXE));
        repair_quartz_pickaxe.shape("123");
        repair_quartz_pickaxe.setIngredient('1', new ExactChoice(Utils.getItemStackDura(Items.QUARTZ_PICKAXE, 31)));
        repair_quartz_pickaxe.setIngredient('2', Material.QUARTZ_BLOCK);
        repair_quartz_pickaxe.setIngredient('3', Material.DIAMOND_BLOCK);


        //  REPAIR RECIPE
        ShapedRecipe repair_valkyries_axe = new ShapedRecipe(new NamespacedKey(survival, "repair_valkyries_axe"), Items.get(Items.VALKYRIES_AXE));
        repair_valkyries_axe.shape("12 ");
        repair_valkyries_axe.setIngredient('1', new ExactChoice(Utils.getItemStackDura(Items.VALKYRIES_AXE, 31)));
        repair_valkyries_axe.setIngredient('2', Material.NETHER_STAR);


        //  REPAIR RECIPE
        ShapedRecipe repair_obsidian_mace = new ShapedRecipe(new NamespacedKey(survival, "repair_obsidian_mace"), Items.get(Items.OBSIDIAN_MACE));
        repair_obsidian_mace.shape("12 ");
        repair_obsidian_mace.setIngredient('1', new ExactChoice(Utils.getItemStackDura(Items.OBSIDIAN_MACE, 31)));
        repair_obsidian_mace.setIngredient('2', Material.END_CRYSTAL);


        // WORKBENCH RECIPE
        ShapelessRecipe workbench = new ShapelessRecipe(new NamespacedKey(survival, "workbench"), Items.get(Items.WORKBENCH));

        workbench.addIngredient(new RecipeChoice.MaterialChoice(Tag.LOGS));
        workbench.addIngredient(Material.LEATHER);
        workbench.addIngredient(Material.STRING);
        workbench.addIngredient(new ExactChoice(Items.get(Items.HAMMER)));


        //  FURNACE RECIPE
        ShapedRecipe furnace = new ShapedRecipe(new NamespacedKey(survival, "furnace"), new ItemStack(Material.FURNACE, 1));

        furnace.shape("@@@", "@*@", "@@@");

        furnace.setIngredient('@', Material.BRICK);
        furnace.setIngredient('*', new ExactChoice(Items.get(Items.FIRESTRIKER)));


        //  CHEST RECIPE
        ShapedRecipe chest = new ShapedRecipe(new NamespacedKey(survival, "chest"), new ItemStack(Material.CHEST, 1));

        chest.shape("@@@", "@#@", "@@@");

        chest.setIngredient('@', new RecipeChoice.MaterialChoice(Tag.LOGS));
        chest.setIngredient('#', Material.IRON_INGOT);


        //  CLAY RECIPE
        ShapedRecipe clay = new ShapedRecipe(new NamespacedKey(survival, "clay"), new ItemStack(Material.CLAY, 1));

        clay.shape("   ", "123", "   ");
        clay.setIngredient('1', Material.DIRT);
        clay.setIngredient('2', Material.SAND);
        clay.setIngredient('3', new ExactChoice(Items.get(Items.WATER_BOWL)));


        //  DIORITE RECIPE
        ShapelessRecipe diorite = new ShapelessRecipe(new NamespacedKey(survival, "diorite"), new ItemStack(Material.DIORITE, 1));

        diorite.addIngredient(new RecipeChoice.MaterialChoice(Material.BONE_MEAL, Material.WHITE_DYE));
        diorite.addIngredient(Material.COBBLESTONE);


        //  GRANITE RECIPE
        ShapelessRecipe granite = new ShapelessRecipe(new NamespacedKey(survival, "granite"), new ItemStack(Material.GRANITE, 1));

        granite.addIngredient(Material.NETHERRACK);
        granite.addIngredient(Material.COBBLESTONE);


        //  ANDESITE RECIPE
        ShapelessRecipe andesite = new ShapelessRecipe(new NamespacedKey(survival, "andesite"), new ItemStack(Material.ANDESITE, 1));

        andesite.addIngredient(Material.GRAVEL);
        andesite.addIngredient(Material.COBBLESTONE);


        //  GRAVEL RECIPE
        ShapedRecipe gravel = new ShapedRecipe(new NamespacedKey(survival, "gravel"), new ItemStack(Material.GRAVEL, 2));

        gravel.shape("@B", "B@");

        gravel.setIngredient('@', Material.SAND);
        gravel.setIngredient('B', Material.COBBLESTONE);


        //  FIRESTRIKER RECIPE
        ShapelessRecipe firestriker = new ShapelessRecipe(new NamespacedKey(survival, "firestriker"), Items.get(Items.FIRESTRIKER));

        firestriker.addIngredient(Material.FLINT);
        firestriker.addIngredient(new RecipeChoice.MaterialChoice(Tag.ITEMS_COALS));


        //  TORCH RECIPE
        ShapedRecipe torch1 = new ShapedRecipe(new NamespacedKey(survival, "torch1"), new ItemStack(Material.TORCH, 8));
        ShapedRecipe torch2 = new ShapedRecipe(new NamespacedKey(survival, "torch2"), new ItemStack(Material.TORCH, 16));

        torch1.shape("AAA", "ABA", "AAA");
        torch1.setIngredient('B', new ExactChoice(Items.get(Items.FIRESTRIKER)));
        torch1.setIngredient('A', Material.STICK);
        //torch1.setGroup("TORCH");

        torch2.shape("ACA", "ABA", "AAA");
        torch2.setIngredient('C', new RecipeChoice.MaterialChoice(Tag.ITEMS_COALS));
        torch2.setIngredient('B', new ExactChoice(Items.get(Items.FIRESTRIKER)));
        torch2.setIngredient('A', Material.STICK);
        //torch2.setGroup("TORCH");


        //  FLINT RECIPE
        ShapelessRecipe flint = new ShapelessRecipe(new NamespacedKey(survival, "flint"), new ItemStack(Material.FLINT, 1));

        flint.addIngredient(Material.GRAVEL);


        //  FERMENTED SPIDER EYE RECIPE
        ShapelessRecipe fermented_spider_eye = new ShapelessRecipe(new NamespacedKey(survival, "fermented_spider_eye"),
                new ItemStack(Material.FERMENTED_SPIDER_EYE, 1));

        fermented_spider_eye.addIngredient(Material.SPIDER_EYE);
        fermented_spider_eye.addIngredient(Material.SUGAR);
        fermented_spider_eye.addIngredient(new RecipeChoice.MaterialChoice(Material.RED_MUSHROOM, Material.BROWN_MUSHROOM));


        //  FERMENTED SKIN RECIPE
        ShapelessRecipe fermented_skin = new ShapelessRecipe(new NamespacedKey(survival, "fermented_skin"), Items.get(Items.FERMENTED_SKIN));

        fermented_skin.addIngredient(Material.ROTTEN_FLESH);
        fermented_skin.addIngredient(Material.SUGAR);
        fermented_skin.addIngredient(new RecipeChoice.MaterialChoice(Material.BROWN_MUSHROOM, Material.RED_MUSHROOM));


        //  POISONOUS POTATO RECIPE
        ShapelessRecipe poisonousPotato = new ShapelessRecipe(new NamespacedKey(survival, "poisonous_potato"),
                new ItemStack(Material.POISONOUS_POTATO, 1));

        poisonousPotato.addIngredient(Material.POTATO);
        poisonousPotato.addIngredient(new RecipeChoice.MaterialChoice(Material.BONE_MEAL, Material.WHITE_DYE));


        //  GLASS BOTTLE RECIPE
        ShapelessRecipe glassBottle = new ShapelessRecipe(new NamespacedKey(survival, "glass_bottle"), new ItemStack(Material.GLASS_BOTTLE, 1));

        glassBottle.addIngredient(Material.POTION);


        //  BOWL RECIPE
        ShapedRecipe bowl = new ShapedRecipe(new NamespacedKey(survival, "bowl"), new ItemStack(Material.BOWL, 1));

        bowl.shape("  ", " 1");
        bowl.setIngredient('1', new ExactChoice(Items.get(Items.WATER_BOWL)));

        // CLEAN WATER RECIPES
        FurnaceRecipe clean_water_furnace = new FurnaceRecipe(new NamespacedKey(survival, "clean_water_furnace"),
                Items.get(Items.CLEAN_WATER), new ExactChoice(Items.get(Items.DIRTY_WATER)), 0, 600);

        SmokingRecipe clean_water_smoker = new SmokingRecipe(new NamespacedKey(survival, "clean_water_smoker"),
                Items.get(Items.CLEAN_WATER), new ExactChoice(Items.get(Items.DIRTY_WATER)), 0, 300);

        CampfireRecipe clean_water_camp = new CampfireRecipe(new NamespacedKey(survival, "clean_water_campfire"),
                Items.get(Items.CLEAN_WATER), new ExactChoice(Items.get(Items.DIRTY_WATER)), 0, 2400);


        //  MEDIC KIT RECIPE
        ShapedRecipe medic_kit = new ShapedRecipe(new NamespacedKey(survival, "medic_kit"), Items.get(Items.MEDIC_KIT));

        medic_kit.shape(" @ ", "ABC", " @ ");

        medic_kit.setIngredient('@', Material.GOLD_INGOT);
        medic_kit.setIngredient('A', Material.FEATHER);
        medic_kit.setIngredient('B', Material.GLISTERING_MELON_SLICE);
        medic_kit.setIngredient('C', Material.PAPER);



        //  FISHING ROD RECIPE
        ShapedRecipe fishing_rod = new ShapedRecipe(new NamespacedKey(survival, "fishing_rod"), new ItemStack(Material.FISHING_ROD, 1));

        fishing_rod.shape("1- ", "1 -", "1@*");

        fishing_rod.setIngredient('1', Material.STICK);
        fishing_rod.setIngredient('@', Material.IRON_INGOT);
        fishing_rod.setIngredient('-', Material.STRING);
        fishing_rod.setIngredient('*', Material.FEATHER);


        //  IRON INGOT RECIPE
        ShapedRecipe ironIngot = new ShapedRecipe(new NamespacedKey(survival, "iron_ingot"), new ItemStack(Material.IRON_INGOT, 1));
        ironIngot.shape("@@", "@@");
        ironIngot.setIngredient('@', Material.IRON_NUGGET);

        //  IRON NUGGET RECIPE
        ShapelessRecipe ironNugget = new ShapelessRecipe(new NamespacedKey(survival, "iron_nugget"), new ItemStack(Material.IRON_NUGGET, 4));
        ironNugget.addIngredient(Material.IRON_INGOT);

        //  IRON BLOCK RECIPE
        ShapelessRecipe ironBlock = new ShapelessRecipe(new NamespacedKey(survival, "iron_block"), new ItemStack(Material.IRON_INGOT, 9));
        ironBlock.addIngredient(Material.IRON_BLOCK);

        //  GOLD INGOT RECIPE
        ShapedRecipe goldIngot = new ShapedRecipe(new NamespacedKey(survival, "gold_ingot"), new ItemStack(Material.GOLD_INGOT, 1));
        goldIngot.shape("@@", "@@");
        goldIngot.setIngredient('@', Material.GOLD_NUGGET);

        //  GOLD NUGGET RECIPE
        ShapelessRecipe goldNugget = new ShapelessRecipe(new NamespacedKey(survival, "gold_nugget"), new ItemStack(Material.GOLD_NUGGET, 4));
        goldNugget.addIngredient(Material.GOLD_INGOT);

        //  GOLD BLOCK RECIPE
        ShapelessRecipe goldBlock = new ShapelessRecipe(new NamespacedKey(survival, "gold_block"), new ItemStack(Material.GOLD_INGOT, 9));
        goldBlock.addIngredient(Material.GOLD_BLOCK);

        //  SMELTING RECIPES
        FurnaceRecipe smelt_ironIngot = new FurnaceRecipe(new NamespacedKey(survival, "furnace_iron_ingot"),
                new ItemStack(Material.IRON_INGOT, 1), Material.IRON_ORE, 1, 400);
        FurnaceRecipe smelt_goldIngot = new FurnaceRecipe(new NamespacedKey(survival, "furnace_gold_ingot"),
                new ItemStack(Material.GOLD_INGOT, 1), Material.GOLD_ORE, 1, 400);
        BlastingRecipe blast_ironIngot = new BlastingRecipe(new NamespacedKey(survival, "blast_iron_ingot"),
                new ItemStack(Material.IRON_INGOT, 1), Material.IRON_ORE, 1, 100);
        BlastingRecipe blast_goldIngot = new BlastingRecipe(new NamespacedKey(survival, "blast_gold_ingot"),
                new ItemStack(Material.GOLD_INGOT, 1), Material.GOLD_ORE, 1, 100);


        //  BREAD RECIPE
        ShapedRecipe bread = new ShapedRecipe(new NamespacedKey(survival, "bread"), new ItemStack(Material.BREAD, 2));

        bread.shape(" E ", "WWW");

        bread.setIngredient('E', Material.EGG);
        bread.setIngredient('W', Material.WHEAT);


        //  COOKIE RECIPE
        ShapedRecipe cookie = new ShapedRecipe(new NamespacedKey(survival, "cookie"), new ItemStack(Material.COOKIE, 8));

        cookie.shape(" E ", "WCW", " S ");

        cookie.setIngredient('E', Material.EGG);
        cookie.setIngredient('W', Material.WHEAT);
        cookie.setIngredient('S', Material.SUGAR);
        cookie.setIngredient('C', Material.COCOA_BEANS);


        //  SLIME BALL RECIPE
        ShapelessRecipe slimeball = new ShapelessRecipe(new NamespacedKey(survival, "slimeball"), new ItemStack(Material.SLIME_BALL, 1));

        slimeball.addIngredient(Material.MILK_BUCKET);
        slimeball.addIngredient(8, Material.VINE);


        //  COBWEB RECIPE
        ShapelessRecipe cobweb = new ShapelessRecipe(new NamespacedKey(survival, "cobweb"), new ItemStack(Material.COBWEB, 1));

        cobweb.addIngredient(Material.SLIME_BALL);
        cobweb.addIngredient(2, Material.STRING);


        //  SAPLING RECIPE
        ShapelessRecipe stick = new ShapelessRecipe(new NamespacedKey(survival, "stick"), new ItemStack(Material.STICK, 4));

        stick.addIngredient(new RecipeChoice.MaterialChoice(Tag.SAPLINGS));


        // REINFORCED LEATHER BOOTS RECIPE
        ShapedRecipe reinforced_leather_boots = new ShapedRecipe(new NamespacedKey(survival, "reinforced_leather_boots"),
                Items.get(Items.REINFORCED_LEATHER_BOOTS));
        reinforced_leather_boots.shape("@*@");

        reinforced_leather_boots.setIngredient('@', Material.IRON_INGOT);
        reinforced_leather_boots.setIngredient('*', Material.LEATHER_BOOTS);


        // REINFORCED LEATHER TUNIC RECIPE
        ShapedRecipe reinforced_leather_chestplate = new ShapedRecipe(new NamespacedKey(survival, "reinforced_leather_chestplate"),
                Items.get(Items.REINFORCED_LEATHER_TUNIC));
        reinforced_leather_chestplate.shape(" @ ", "@*@", " @ ");

        reinforced_leather_chestplate.setIngredient('@', Material.IRON_INGOT);
        reinforced_leather_chestplate.setIngredient('*', Material.LEATHER_CHESTPLATE);


        // REINFORCED LEATHER TROUSERS RECIPE
        ShapedRecipe reinforced_leather_leggings = new ShapedRecipe(new NamespacedKey(survival, "reinforced_leather_leggings"),
                Items.get(Items.REINFORCED_LEATHER_TROUSERS));
        reinforced_leather_leggings.shape(" @ ", "@*@", " @ ");

        reinforced_leather_leggings.setIngredient('@', Material.IRON_INGOT);
        reinforced_leather_leggings.setIngredient('*', Material.LEATHER_LEGGINGS);


        // REINFORCED LEATHER HELMET RECIPE
        ShapedRecipe reinforced_leather_helmet = new ShapedRecipe(new NamespacedKey(survival, "reinforced_leather_helmet"),
                Items.get(Items.REINFORCED_LEATHER_HELMET));
        reinforced_leather_helmet.shape("@*@");

        reinforced_leather_helmet.setIngredient('@', Material.IRON_INGOT);
        reinforced_leather_helmet.setIngredient('*', Material.LEATHER_HELMET);


        // GOLDEN SABATONS RECIPE
        ShapedRecipe gold_sabatons = new ShapedRecipe(new NamespacedKey(survival, "gold_sabatons"), Items.get(Items.GOLDEN_SABATONS));
        gold_sabatons.shape("@ @", "@ @");

        gold_sabatons.setIngredient('@', Material.GOLD_INGOT);


        // GOLDEN GUARD RECIPE
        ShapedRecipe gold_guard = new ShapedRecipe(new NamespacedKey(survival, "gold_guard"), Items.get(Items.GOLDEN_GUARD));
        gold_guard.shape("@ @", "@@@", "@@@");

        gold_guard.setIngredient('@', Material.GOLD_INGOT);


        // GOLDEN GREAVES RECIPE
        ShapedRecipe gold_greaves = new ShapedRecipe(new NamespacedKey(survival, "gold_greaves"), Items.get(Items.GOLDEN_GREAVES));
        gold_greaves.shape("@@@", "@ @", "@ @");

        gold_greaves.setIngredient('@', Material.GOLD_INGOT);


        // GOLDEN CROWN RECIPE
        ShapedRecipe gold_crown = new ShapedRecipe(new NamespacedKey(survival, "gold_crown"), Items.get(Items.GOLDEN_CROWN));
        gold_crown.shape("@*@", "@@@");

        gold_crown.setIngredient('@', Material.GOLD_INGOT);
        gold_crown.setIngredient('*', Material.EMERALD);


        // IRON BOOTS RECIPE
        ShapedRecipe ironBoots = new ShapedRecipe(new NamespacedKey(survival, "iron_boots"), Items.get(Items.IRON_BOOTS));
        ironBoots.shape("@ @", "@ @");

        ironBoots.setIngredient('@', Material.IRON_INGOT);


        // IRON CHESTPLATE RECIPE
        ShapedRecipe ironChestplate = new ShapedRecipe(new NamespacedKey(survival, "iron_chestplate"), Items.get(Items.IRON_CHESTPLATE));
        ironChestplate.shape("@ @", "@@@", "@@@");

        ironChestplate.setIngredient('@', Material.IRON_INGOT);


        // IRON LEGGINGS RECIPE
        ShapedRecipe ironLeggings = new ShapedRecipe(new NamespacedKey(survival, "iron_leggings"), Items.get(Items.IRON_LEGGINGS));
        ironLeggings.shape("@@@", "@ @", "@ @");

        ironLeggings.setIngredient('@', Material.IRON_INGOT);


        // IRON HELMET RECIPE
        ShapedRecipe ironHelmet = new ShapedRecipe(new NamespacedKey(survival, "iron_helmet"), Items.get(Items.IRON_HELMET));
        ironHelmet.shape("@@@", "@ @");

        ironHelmet.setIngredient('@', Material.IRON_INGOT);


        // DIAMOND BOOTS RECIPE
        ShapedRecipe diamondBoots = new ShapedRecipe(new NamespacedKey(survival, "diamond_boots"), Items.get(Items.DIAMOND_BOOTS));
        diamondBoots.shape("@ @", "@ @");

        diamondBoots.setIngredient('@', Material.DIAMOND);


        // DIAMOND CHESTPLATE RECIPE
        ShapedRecipe diamondChestplate = new ShapedRecipe(new NamespacedKey(survival, "diamond_chestplate"), Items.get(Items.DIAMOND_CHESTPLATE));
        diamondChestplate.shape("@ @", "@@@", "@@@");

        diamondChestplate.setIngredient('@', Material.DIAMOND);


        // DIAMOND LEGGINGS RECIPE
        ShapedRecipe diamondLeggings = new ShapedRecipe(new NamespacedKey(survival, "diamond_leggings"), Items.get(Items.DIAMOND_LEGGINGS));
        diamondLeggings.shape("@@@", "@ @", "@ @");

        diamondLeggings.setIngredient('@', Material.DIAMOND);


        // DIAMOND HELMET RECIPE
        ShapedRecipe diamondHelmet = new ShapedRecipe(new NamespacedKey(survival, "diamond_helmet"), Items.get(Items.DIAMOND_HELMET));
        diamondHelmet.shape("@@@", "@ @");

        diamondHelmet.setIngredient('@', Material.DIAMOND);


        // RECURVED BOW RECIPE
        ShapedRecipe recurvedBow = new ShapedRecipe(new NamespacedKey(survival, "recurved_bow"), Items.get(Items.RECURVE_BOW));

        recurvedBow.shape(" @1", "#^1", " @1");
        recurvedBow.setIngredient('^', Material.BOW);
        recurvedBow.setIngredient('#', Material.PISTON);
        recurvedBow.setIngredient('@', Material.IRON_INGOT);
        recurvedBow.setIngredient('1', Material.STRING);


        // RECURVED CROSSBOW
        ShapedRecipe recurvedCrossbow = new ShapedRecipe(new NamespacedKey(survival, "recurved_crossbow"), Items.get(Items.RECURVE_CROSSBOW));

        recurvedCrossbow.shape(" 12", "342", " 12");
        recurvedCrossbow.setIngredient('1', Material.DIAMOND);
        recurvedCrossbow.setIngredient('2', Material.PHANTOM_MEMBRANE);
        recurvedCrossbow.setIngredient('3', Material.PISTON);
        recurvedCrossbow.setIngredient('4', Material.CROSSBOW);

        // NEW CAMPFIRE RECIPE
        ShapedRecipe unlit_campfire = new ShapedRecipe(new NamespacedKey(survival, "unlit_campfire"), Items.get(Items.CAMPFIRE));

        unlit_campfire.shape(" 1 ", "121", "333");
        unlit_campfire.setIngredient('1', Material.STICK);
        unlit_campfire.setIngredient('2', new RecipeChoice.MaterialChoice(Tag.ITEMS_COALS));
        unlit_campfire.setIngredient('3', new RecipeChoice.MaterialChoice(Tag.LOGS));

        // NEW FLINT SICKLE RECIPE
        ShapedRecipe flint_sickle = new ShapedRecipe(new NamespacedKey(survival, "flint_sickle"), Items.get(Items.FLINT_SICKLE));

        flint_sickle.shape("11 ", " 2 ", " 3 ");
        flint_sickle.setIngredient('1', Material.FLINT);
        flint_sickle.setIngredient('2', Material.STICK);
        flint_sickle.setIngredient('3', Material.STICK);

        // STONE SICKLE RECIPE
        ShapedRecipe stone_sickle = new ShapedRecipe(new NamespacedKey(survival, "stone_sickle"), Items.get(Items.STONE_SICKLE));

        stone_sickle.shape("11 ", " 2 ", " 3 ");
        stone_sickle.setIngredient('1', Material.COBBLESTONE);
        stone_sickle.setIngredient('2', new ExactChoice(Items.get(Items.HATCHET)));
        stone_sickle.setIngredient('3', Material.STICK);

        // NEW IRON SICKLE RECIPE
        ShapedRecipe iron_sickle = new ShapedRecipe(new NamespacedKey(survival, "iron_sickle"), Items.get(Items.IRON_SICKLE));

        iron_sickle.shape("11 ", " 2 ", " 3 ");
        iron_sickle.setIngredient('1', Material.IRON_NUGGET);
        iron_sickle.setIngredient('2', new ExactChoice(Items.get(Items.STONE_SICKLE)));
        iron_sickle.setIngredient('3', Material.STICK);

        // NEW DIAMOND SICKLE RECIPE
        ShapedRecipe diamond_sickle = new ShapedRecipe(new NamespacedKey(survival, "diamond_sickle"), Items.get(Items.DIAMOND_SICKLE));
        diamond_sickle.shape("11 ", " 2 ", " 3 ");
        diamond_sickle.setIngredient('1', Material.DIAMOND);
        diamond_sickle.setIngredient('2', new ExactChoice(Items.get(Items.STONE_SICKLE)));
        diamond_sickle.setIngredient('3', Material.STICK);

        // NEW GRAPPLING HOOK RECIPE
        ShapedRecipe grappling_hook = new ShapedRecipe(new NamespacedKey(survival, "grappling_hook"), Items.get(Items.GRAPPLING_HOOK));

        grappling_hook.shape(" 3 ", "121", " 3 ");
        grappling_hook.setIngredient('1', Material.FISHING_ROD);
        grappling_hook.setIngredient('2', Material.STRING);
        grappling_hook.setIngredient('3', Material.IRON_INGOT);

        // NEW COFFEE RECIPES
        SmokingRecipe coffee_bean = new SmokingRecipe(new NamespacedKey(survival, "coffee_bean"), Items.get(Items.COFFEE_BEAN),
                Material.COCOA_BEANS, 0, 200);

        SmokingRecipe hot_milk = new SmokingRecipe(new NamespacedKey(survival, "hot_milk"), Items.get(Items.HOT_MILK),
                new ExactChoice(Items.get(Items.COLD_MILK)), 0, 200);

        ItemStack COFFEE = Items.get(Items.COFFEE);
        COFFEE.setAmount(2);
        ShapedRecipe coffee = new ShapedRecipe(new NamespacedKey(survival, "coffee"), COFFEE);

        coffee.shape("   ", "12 ", "34 ");
        coffee.setIngredient('1', new ExactChoice(Items.get(Items.COFFEE_BEAN)));
        coffee.setIngredient('2', Material.COCOA_BEANS);
        coffee.setIngredient('3', new ExactChoice(Items.get(Items.HOT_MILK)));
        coffee.setIngredient('4', new ExactChoice(Items.get(Items.PURIFIED_WATER)));

        ShapedRecipe cold_milk = new ShapedRecipe(new NamespacedKey(survival, "cold_milk"), Items.get(Items.COLD_MILK));

        cold_milk.shape("   ", "12 ", "   ");
        cold_milk.setIngredient('1', Material.MILK_BUCKET);
        cold_milk.setIngredient('2', Material.GLASS_BOTTLE);





        //Add recipes
        if (settings.getBoolean("Survival.Enabled")) {
            survival.getServer().addRecipe(hatchet1);
            survival.getServer().addRecipe(hatchet2);
            survival.getServer().addRecipe(mattock);
            survival.getServer().addRecipe(shiv);
            survival.getServer().addRecipe(hammer);
            survival.getServer().addRecipe(firestriker);
            survival.getServer().addRecipe(workbench);
            survival.getServer().addRecipe(furnace);
            survival.getServer().addRecipe(chest);
            survival.getServer().addRecipe(flint);
            survival.getServer().addRecipe(unlit_campfire);
            if (settings.getBoolean("Survival.BreakOnlyWith.Sickle")) {
                if (settings.getBoolean("Survival.Sickles.Flint"))
                    survival.getServer().addRecipe(flint_sickle);
                if (settings.getBoolean("Survival.Sickles.Stone"))
                    survival.getServer().addRecipe(stone_sickle);
                if (settings.getBoolean("Survival.Sickles.Iron"))
                    survival.getServer().addRecipe(iron_sickle);
                if (settings.getBoolean("Survival.Sickles.Diamond"))
                    survival.getServer().addRecipe(diamond_sickle);
            }
        }
        if (settings.getBoolean("Survival.Torch")) {
            survival.getServer().addRecipe(torch1);
            survival.getServer().addRecipe(torch2);
        }
        if (settings.getBoolean("Recipes.WebString"))
            survival.getServer().addRecipe(string);
        if (settings.getBoolean("Recipes.SaplingToSticks")) {
            survival.getServer().addRecipe(stick);
        }

        if (settings.getBoolean("LegendaryItems.ValkyrieAxe")) {
            survival.getServer().addRecipe(valkyries_axe);
            if (settings.getBoolean("LegendaryItems.CanRepair"))
                survival.getServer().addRecipe(repair_valkyries_axe);
        }
        if (settings.getBoolean("LegendaryItems.QuartzPickaxe")) {
            survival.getServer().addRecipe(quartz_pickaxe);
            if (settings.getBoolean("LegendaryItems.CanRepair"))
                survival.getServer().addRecipe(repair_quartz_pickaxe);
        }
        if (settings.getBoolean("LegendaryItems.ObsidianMace")) {
            survival.getServer().addRecipe(obsidian_mace);
            if (settings.getBoolean("LegendaryItems.CanRepair"))
                survival.getServer().addRecipe(repair_obsidian_mace);
        }
        if (settings.getBoolean("LegendaryItems.GiantBlade")) {
            survival.getServer().addRecipe(ender_giant_blade);
            if (settings.getBoolean("LegendaryItems.CanRepair"))
                survival.getServer().addRecipe(repair_ender_giant_blaze);
        }
        if (settings.getBoolean("LegendaryItems.BlazeSword")) {
            survival.getServer().addRecipe(blaze_sword);
            if (settings.getBoolean("LegendaryItems.CanRepair"))
                survival.getServer().addRecipe(repair_blaze_sword);
        }
        if (settings.getBoolean("LegendaryItems.NotchApple"))
            survival.getServer().addRecipe(notchApple);
        if (settings.getBoolean("Recipes.Saddle"))
            survival.getServer().addRecipe(saddle);
        if (settings.getBoolean("Recipes.Nametag")) {
            survival.getServer().addRecipe(nametag);
        }
        if (settings.getBoolean("Recipes.PackedIce")) {
            survival.getServer().addRecipe(packedIce1);
            survival.getServer().addRecipe(ice2);
        }
        if (settings.getBoolean("Recipes.IronBard")) {
            survival.getServer().addRecipe(iron_horse_armor);
        }
        if (settings.getBoolean("Recipes.GoldBard")) {
            survival.getServer().addRecipe(goldHorse1);
        }
        if (settings.getBoolean("Recipes.DiamondBard")) {
            survival.getServer().addRecipe(diamond_horse_armor);
        }
        if (settings.getBoolean("Recipes.LeatherBard")) {
            survival.getServer().addRecipe(leather_horse_armor);
        }
        if (settings.getBoolean("Recipes.ClayBrick"))
            survival.getServer().addRecipe(clayBrick);
        if (settings.getBoolean("Recipes.QuartzBlock"))
            survival.getServer().addRecipe(quartz);
        if (settings.getBoolean("Recipes.WoolString"))
            survival.getServer().addRecipe(woolString);
        if (settings.getBoolean("Recipes.Ice"))
            survival.getServer().addRecipe(ice);
        if (settings.getBoolean("Recipes.Clay"))
            survival.getServer().addRecipe(clay);
        if (settings.getBoolean("Recipes.Diorite"))
            survival.getServer().addRecipe(diorite);
        if (settings.getBoolean("Recipes.Granite"))
            survival.getServer().addRecipe(granite);
        if (settings.getBoolean("Recipes.Andesite"))
            survival.getServer().addRecipe(andesite);
        if (settings.getBoolean("Recipes.Gravel")) {
            survival.getServer().addRecipe(gravel);
        }
        if (settings.getBoolean("Mechanics.RedMushroomFermentation")) {
            survival.getServer().addRecipe(fermented_spider_eye);
        }
        if (settings.getBoolean("Mechanics.FermentedSkin")) {
            survival.getServer().addRecipe(fermented_skin);
        }
        if (settings.getBoolean("Mechanics.PoisonousPotato"))
            survival.getServer().addRecipe(poisonousPotato);
        if (settings.getBoolean("Mechanics.EmptyPotions")) {
            survival.getServer().addRecipe(glassBottle);
            survival.getServer().addRecipe(bowl);
        }
        if (settings.getBoolean("Mechanics.ReinforcedLeatherArmor")) {
            survival.getServer().addRecipe(reinforced_leather_boots);
            survival.getServer().addRecipe(reinforced_leather_chestplate);
            survival.getServer().addRecipe(reinforced_leather_leggings);
            survival.getServer().addRecipe(reinforced_leather_helmet);
        }
        if (settings.getBoolean("LegendaryItems.GoldArmorBuff")) {
            survival.getServer().addRecipe(gold_sabatons);
            survival.getServer().addRecipe(gold_guard);
            survival.getServer().addRecipe(gold_greaves);
            survival.getServer().addRecipe(gold_crown);
        }

        if (settings.getBoolean("Mechanics.SlowArmor")) {
            survival.getServer().addRecipe(ironBoots);
            survival.getServer().addRecipe(ironChestplate);
            survival.getServer().addRecipe(ironLeggings);
            survival.getServer().addRecipe(ironHelmet);
            survival.getServer().addRecipe(diamondBoots);
            survival.getServer().addRecipe(diamondChestplate);
            survival.getServer().addRecipe(diamondLeggings);
            survival.getServer().addRecipe(diamondHelmet);
        }

        if (settings.getBoolean("Mechanics.MedicalKit")) {
            survival.getServer().addRecipe(medic_kit);
        }

        if (settings.getBoolean("Recipes.FishingRod")) {
            survival.getServer().addRecipe(fishing_rod);
        }

        if (settings.getBoolean("Mechanics.ReducedIronNugget")) {
            survival.getServer().addRecipe(ironNugget);
            survival.getServer().addRecipe(ironIngot);
            survival.getServer().addRecipe(ironBlock);
            survival.getServer().addRecipe(smelt_ironIngot);
            survival.getServer().addRecipe(blast_ironIngot);
        }

        if (settings.getBoolean("Mechanics.ReducedGoldNugget")) {
            survival.getServer().addRecipe(goldNugget);
            survival.getServer().addRecipe(goldIngot);
            survival.getServer().addRecipe(goldBlock);
            survival.getServer().addRecipe(smelt_goldIngot);
            survival.getServer().addRecipe(blast_goldIngot);
        }

        if (settings.getBoolean("Mechanics.FarmingProducts.Bread"))
            survival.getServer().addRecipe(bread);
        if (settings.getBoolean("Mechanics.FarmingProducts.Cookie"))
            survival.getServer().addRecipe(cookie);
        if (settings.getBoolean("Recipes.Slimeball"))
            survival.getServer().addRecipe(slimeball);
        if (settings.getBoolean("Recipes.Cobweb"))
            survival.getServer().addRecipe(cobweb);
        if (settings.getBoolean("Mechanics.RecurveBow")) {
            survival.getServer().addRecipe(recurvedBow);
            survival.getServer().addRecipe(recurvedCrossbow);
        }
        if (settings.getBoolean("Mechanics.GrapplingHook"))
            survival.getServer().addRecipe(grappling_hook);
        if (settings.getBoolean("Mechanics.Thirst.PurifyWater")) {
            survival.getServer().addRecipe(clean_water_furnace);
            survival.getServer().addRecipe(clean_water_smoker);
            survival.getServer().addRecipe(clean_water_camp);
        }
        if (settings.getBoolean("Mechanics.Coffee")) {
            survival.getServer().addRecipe(coffee_bean);
            survival.getServer().addRecipe(cold_milk);
            survival.getServer().addRecipe(hot_milk);
            survival.getServer().addRecipe(coffee);
        }
    }

    private void removeRecipes() {
        List<Recipe> backup = new ArrayList<>();

        Iterator<Recipe> a = survival.getServer().recipeIterator();

        while (a.hasNext()) {
            Recipe recipe = a.next();
            backup.add(recipe);
        }

        Iterator<Recipe> it = backup.iterator();

        while (it.hasNext()) {
            Recipe recipe = it.next();
            if (recipe != null) {
                switch (recipe.getResult().getType()) {
                    case WOODEN_HOE:
                    case WOODEN_AXE:
                    case WOODEN_PICKAXE:
                    case WOODEN_SHOVEL:
                    case WOODEN_SWORD:
                    case FURNACE:
                    case CRAFTING_TABLE:
                    case CHEST:
                    case CAMPFIRE:
                        if (settings.getBoolean("Survival.Enabled"))
                            it.remove();
                        break;
                    case BEETROOT_SOUP:
                        if (settings.getBoolean("Mechanics.Thirst.Enabled"))
                            it.remove();
                        break;
                    case TORCH:
                        if (settings.getBoolean("Survival.Enabled") && settings.getBoolean("Survival.Torch"))
                            it.remove();
                        break;
                    case GOLDEN_HOE:
                        if (settings.getBoolean("LegendaryItems.GiantBlade"))
                            it.remove();
                        break;
                    case GOLDEN_AXE:
                        if (settings.getBoolean("LegendaryItems.ValkyrieAxe"))
                            it.remove();
                        break;
                    case GOLDEN_PICKAXE:
                        if (settings.getBoolean("LegendaryItems.QuartzPickaxe"))
                            it.remove();
                        break;
                    case GOLDEN_SHOVEL:
                        if (settings.getBoolean("LegendaryItems.ObsidianMace"))
                            it.remove();
                        break;
                    case GOLDEN_SWORD:
                        if (settings.getBoolean("LegendaryItems.BlazeSword"))
                            it.remove();
                        break;

                    case GOLDEN_BOOTS:
                    case GOLDEN_CHESTPLATE:
                    case GOLDEN_HELMET:
                    case GOLDEN_LEGGINGS:
                        if (settings.getBoolean("LegendaryItems.GoldArmorBuff"))
                            it.remove();
                        break;

                    case IRON_BOOTS:
                    case IRON_CHESTPLATE:
                    case IRON_HELMET:
                    case IRON_LEGGINGS:
                    case DIAMOND_BOOTS:
                    case DIAMOND_CHESTPLATE:
                    case DIAMOND_HELMET:
                    case DIAMOND_LEGGINGS:
                        if (settings.getBoolean("Mechanics.SlowArmor"))
                            it.remove();
                        break;

                    case FISHING_ROD:
                        if (settings.getBoolean("Recipes.FishingRod"))
                            it.remove();
                        break;

                    case IRON_NUGGET:
                    case IRON_INGOT:
                        if (settings.getBoolean("Mechanics.ReducedIronNugget"))
                            it.remove();
                        break;

                    case GOLD_NUGGET:
                    case GOLD_INGOT:
                        if (settings.getBoolean("Mechanics.ReducedGoldNugget"))
                            it.remove();
                        break;

                    case BREAD:
                        if (settings.getBoolean("Mechanics.FarmingProducts.Bread"))
                            it.remove();
                        break;
                    case COOKIE:
                        if (settings.getBoolean("Mechanics.FarmingProducts.Cookie"))
                            it.remove();
                        break;
                    case ANDESITE:
                        if (settings.getBoolean("Recipes.Andesite"))
                            it.remove();
                        break;
                    case DIORITE:
                        if (settings.getBoolean("Recipes.Diorite"))
                            it.remove();
                        break;
                    case GRANITE:
                        if (settings.getBoolean("Recipes.Granite"))
                            it.remove();
                        break;
                    case SNOW:
                    case SNOW_BLOCK:
                        if (settings.getBoolean("Mechanics.SnowballRevamp"))
                            it.remove();
                        break;
                    case CLOCK:
                        if (settings.getBoolean("Mechanics.MedicalKit"))
                            it.remove();
                        break;
                    case LEATHER_HORSE_ARMOR:
                        if (settings.getBoolean("Recipes.LeatherBard"))
                            it.remove();
                    default:
                }
            }
        }

        survival.getServer().clearRecipes();

        for (Recipe r : backup) {
            survival.getServer().addRecipe(r);
        }
    }

    /** Enums of all custom recipes
     *
     */
    public enum Recipes {
        // CUSTOM TOOLS/ITEMS
        HATCHET("hatchet1", "hatchet2"),
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
        WATER_BOTTLES("clean_water_furnace", "clean_water_smoker", "clean_water_campfire" ),
        COFFEE_BEAN("coffee_bean"),
        COLD_MILK("cold_milk"),
        HOT_MILK("hot_milk"),
        COFFEE("coffee"),

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
        IRON_BLOCK("iron_block"),
        GOLD_BLOCK("gold_block"),

        // REPAIR RECIPES
        REPAIR_BLAZE_SWORD("repair_blaze_sword"),
        REPAIR_ENDER_GIANT_BLADE("repair_ender_giant_blade"),
        REPAIR_QUARTZ_PICKAXE("repair_quartz_pickaxe"),
        REPAIR_VALKYRIES_AXE("repair_valkyries_axe"),
        REPAIR_OBSIDIAN_MACE("repair_obsidian_mace"),

        // SMELTING RECIPES
        FURNACE_IRON_INGOT("furnace_iron_ingot"),
        FURNACE_GOLD_INGOT("furnace_gold_ingot"),
        BLAST_IRON_INGOT("blast_iron_ingot"),
        BLAST_GOLD_INGOT("blast_gold_ingot");

        private final Collection<NamespacedKey> keys;

        Recipes(String... keys) {
            ArrayList<NamespacedKey> list = new ArrayList<>();
            for(String key : keys) {
                assert false;
                list.add(new NamespacedKey(survival, key));
            }
            this.keys = list;
        }

        public Collection<NamespacedKey> getKeys() {
            return this.keys;
        }
    }

}
