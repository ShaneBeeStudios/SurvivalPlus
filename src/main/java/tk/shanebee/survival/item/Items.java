package tk.shanebee.survival.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.shanebee.survival.item.items.armor.ArmorPiece;
import tk.shanebee.survival.item.items.armor.ArmorPiece.ArmorMaterial;
import tk.shanebee.survival.item.items.armor.ArmorPiece.ArmorType;
import tk.shanebee.survival.item.items.armor.BeekeeperPiece;
import tk.shanebee.survival.item.items.armor.RainBoots;
import tk.shanebee.survival.item.items.armor.ReinforcedPiece;
import tk.shanebee.survival.item.items.armor.SnowBoots;
import tk.shanebee.survival.item.items.blocks.Campfire;
import tk.shanebee.survival.item.items.blocks.Workbench;
import tk.shanebee.survival.item.items.drinks.Coffee;
import tk.shanebee.survival.item.items.drinks.ColdMilk;
import tk.shanebee.survival.item.items.drinks.HotMilk;
import tk.shanebee.survival.item.items.drinks.Water;
import tk.shanebee.survival.item.items.drinks.WaterBowl;
import tk.shanebee.survival.item.items.food.SuspiciousMeat;
import tk.shanebee.survival.item.items.legendary.BlazeSword;
import tk.shanebee.survival.item.items.legendary.EnderGiantBlade;
import tk.shanebee.survival.item.items.legendary.ObsidianMace;
import tk.shanebee.survival.item.items.legendary.QuartzPickaxe;
import tk.shanebee.survival.item.items.legendary.ValkyriesAxe;
import tk.shanebee.survival.item.items.misc.BreedingEgg;
import tk.shanebee.survival.item.items.misc.CoffeeBean;
import tk.shanebee.survival.item.items.misc.FermentedSkin;
import tk.shanebee.survival.item.items.tools.Compass;
import tk.shanebee.survival.item.items.tools.FireStriker;
import tk.shanebee.survival.item.items.tools.GrapplingHook;
import tk.shanebee.survival.item.items.tools.Hammer;
import tk.shanebee.survival.item.items.tools.Hatchet;
import tk.shanebee.survival.item.items.tools.Mattock;
import tk.shanebee.survival.item.items.tools.MedicKit;
import tk.shanebee.survival.item.items.tools.RecurveBow;
import tk.shanebee.survival.item.items.tools.RecurveCrossbow;
import tk.shanebee.survival.item.items.tools.Shiv;
import tk.shanebee.survival.item.items.tools.Sickle;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Custom SurvivalPlus items
 */
@SuppressWarnings("UnstableApiUsage")
public class Items {

    static final Map<Key, Item> ALL_ITEMS = new HashMap<>();

    // TOOLS
    public static final Item HATCHET = new Hatchet();
    public static final Item MATTOCK = new Mattock();
    public static final Item SHIV = new Shiv();
    public static final Item HAMMER = new Hammer();
    public static final Item FIRESTRIKER = new FireStriker();
    public static final Item GRAPPLING_HOOK = new GrapplingHook();
    public static final Item COMPASS = new Compass();
    public static final Item FLINT_SICKLE = new Sickle("flint", Material.FLINT);
    public static final Item STONE_SICKLE = new Sickle("stone", Material.STONE);
    public static final Item IRON_SICKLE = new Sickle("iron", Material.IRON_INGOT);
    public static final Item DIAMOND_SICKLE = new Sickle("diamond", Material.DIAMOND);
    public static final Item MEDIC_KIT = new MedicKit();
    public static final Item RECURVE_BOW = new RecurveBow();
    public static final Item RECURVE_CROSSBOW = new RecurveCrossbow();

    // LEGENDARY TOOLS
    public static final Item VALKYRIES_AXE = new ValkyriesAxe();
    public static final Item QUARTZ_PICKAXE = new QuartzPickaxe();
    public static final Item OBSIDIAN_MACE = new ObsidianMace();
    public static final Item ENDER_GIANT_BLADE = new EnderGiantBlade();
    public static final Item BLAZE_SWORD = new BlazeSword();

    // ARMOR
    public static final Item REINFORCED_LEATHER_HELMET = new ReinforcedPiece(ArmorType.HELMET);
    public static final Item REINFORCED_LEATHER_TUNIC = new ReinforcedPiece(ArmorType.CHESTPLATE);
    public static final Item REINFORCED_LEATHER_TROUSERS = new ReinforcedPiece(ArmorType.LEGGINGS);
    public static final Item REINFORCED_LEATHER_BOOTS = new ReinforcedPiece(ArmorType.BOOTS);

    public static final Item GOLDEN_CROWN = new ArmorPiece(ArmorType.HELMET, ArmorMaterial.GOLDEN, 1.0, -0.0125);
    public static final Item GOLDEN_GUARD = new ArmorPiece(ArmorType.CHESTPLATE, ArmorMaterial.GOLDEN, 3.0, -0.02);
    public static final Item GOLDEN_GREAVES = new ArmorPiece(ArmorType.LEGGINGS, ArmorMaterial.GOLDEN, 2.0, -0.02);
    public static final Item GOLDEN_SABATONS = new ArmorPiece(ArmorType.BOOTS, ArmorMaterial.GOLDEN, 1.0, -0.0125);

    public static final Item IRON_HELMET = new ArmorPiece(ArmorType.HELMET, ArmorMaterial.IRON, 2.0, -0.02);
    public static final Item IRON_CHESTPLATE = new ArmorPiece(ArmorType.CHESTPLATE, ArmorMaterial.IRON, 6.0, -0.03);
    public static final Item IRON_LEGGINGS = new ArmorPiece(ArmorType.LEGGINGS, ArmorMaterial.IRON, 5.0, -0.03);
    public static final Item IRON_BOOTS = new ArmorPiece(ArmorType.BOOTS, ArmorMaterial.IRON, 2.0, -0.02);

    public static final Item DIAMOND_HELMET = new ArmorPiece(ArmorType.HELMET, ArmorMaterial.DIAMOND, 3, -0.02);
    public static final Item DIAMOND_CHESTPLATE = new ArmorPiece(ArmorType.CHESTPLATE, ArmorMaterial.DIAMOND, 8, -0.03);
    public static final Item DIAMOND_LEGGINGS = new ArmorPiece(ArmorType.LEGGINGS, ArmorMaterial.DIAMOND, 6, -0.03);
    public static final Item DIAMOND_BOOTS = new ArmorPiece(ArmorType.BOOTS, ArmorMaterial.DIAMOND, 3, -0.02);

    public static final Item NETHERITE_HELMET = new ArmorPiece(ArmorType.HELMET, ArmorMaterial.NETHERITE, 3.0, -0.02, 3.0, 0.1);
    public static final Item NETHERITE_CHESTPLATE = new ArmorPiece(ArmorType.CHESTPLATE, ArmorMaterial.NETHERITE, 8.0, -0.02, 3.0, 0.1);
    public static final Item NETHERITE_LEGGINGS = new ArmorPiece(ArmorType.LEGGINGS, ArmorMaterial.NETHERITE, 6.0, -0.02, 3.0, 0.1);
    public static final Item NETHERITE_BOOTS = new ArmorPiece(ArmorType.BOOTS, ArmorMaterial.NETHERITE, 3.0, -0.02, 3.0, 0.1);

    public static final Item BEEKEEPER_HELMET = new BeekeeperPiece(ArmorType.HELMET);
    public static final Item BEEKEEPER_CHESTPLATE = new BeekeeperPiece(ArmorType.CHESTPLATE);
    public static final Item BEEKEEPER_LEGGINGS = new BeekeeperPiece(ArmorType.LEGGINGS);
    public static final Item BEEKEEPER_BOOTS = new BeekeeperPiece(ArmorType.BOOTS);
    public static final Item SNOW_BOOTS = new SnowBoots();
    public static final Item RAIN_BOOTS = new RainBoots();

    // BLOCKS
    public static final Item WORKBENCH = new Workbench();
    public static final Item CAMPFIRE = new Campfire();

    // MISC
    public static final Item FERMENTED_SKIN = new FermentedSkin();
    public static final Item COFFEE_BEAN = new CoffeeBean();
    public static final Item BREEDING_EGG = new BreedingEgg();

    // FOOD
    public static final Item SUSPICIOUS_MEAT = new SuspiciousMeat();

    // DRINKS
    public static final Item DIRTY_WATER = new Water("dirty_water");
    public static final Item CLEAN_WATER = new Water("clean_water");
    public static final Item PURIFIED_WATER = new Water("purified_water");
    public static final Item COFFEE = new Coffee();
    public static final Item HOT_MILK = new HotMilk();
    public static final Item COLD_MILK = new ColdMilk();
    public static final Item WATER_BOWL = new WaterBowl();

    // TODO Experimental
    public static final Item PERSISTENT_TORCH = null;

    public static Set<Key> allItemKeys() {
        return ALL_ITEMS.keySet();
    }

    /**
     * Get an {@link Item} by {@link Key}
     *
     * @param key Key of item
     * @return Item if available
     */
    @SuppressWarnings("PatternValidation")
    @Nullable
    public static Item getByKey(@NotNull String key) {
        key = key.toLowerCase(Locale.ROOT);
        if (!key.contains("survival_plus:")) {
            key = "survival_plus:" + key;
        }
        return ALL_ITEMS.get(Key.key(key));
    }

    /**
     * Get an {@link Item} from an {@link ItemStack}
     *
     * @param itemStack ItemStack to grab item from
     * @return Item if ItemStack has linked item
     */
    @Nullable
    public static Item getFromStack(@NotNull ItemStack itemStack) {
        if (itemStack.hasData(DataComponentTypes.ITEM_MODEL)) {
            Key data = itemStack.getData(DataComponentTypes.ITEM_MODEL);
            if (data != null) return ALL_ITEMS.get(data);
        }
        return null;
    }

    /**
     * Tags for different {@link Items} groups
     */
    public enum Tags {
        /**
         * Any sickle
         */
        SICKLES(FLINT_SICKLE, STONE_SICKLE, IRON_SICKLE, DIAMOND_SICKLE),
        /**
         * Any reinforced leather armor
         */
        REINFORCED_LEATHER_ARMOR(REINFORCED_LEATHER_BOOTS, REINFORCED_LEATHER_TROUSERS,
            REINFORCED_LEATHER_TUNIC, REINFORCED_LEATHER_HELMET),
        /**
         * Any water bottle
         */
        WATER_BOTTLE(DIRTY_WATER, CLEAN_WATER, PURIFIED_WATER),
        /**
         * Any drinkable item
         */
        DRINKABLE(DIRTY_WATER, CLEAN_WATER, PURIFIED_WATER, WATER_BOWL,
            COLD_MILK, HOT_MILK, COFFEE),

        /**
         * Any legendary item
         */
        LEGENDARY(BLAZE_SWORD, OBSIDIAN_MACE, VALKYRIES_AXE, ENDER_GIANT_BLADE, QUARTZ_PICKAXE);

        private final Item[] items;

        Tags(Item... items) {
            this.items = items;
        }

        /**
         * Get all items tagged in this group
         *
         * @return All items tagged in this group
         */
        public Item[] getItems() {
            return items;
        }

        /**
         * Check if an ItemStack is tagged in a group of custom {@link Items}
         *
         * @param itemStack ItemStack to check
         * @return True if item matches tag
         */
        public boolean isTagged(ItemStack itemStack) {
            for (Item item : this.items) {
                if (item.is(itemStack)) return true;
            }
            return false;
        }

    }

}
