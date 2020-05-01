package tk.shanebee.survival.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import tk.shanebee.survival.managers.ItemManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom SurvivalPlus items
 */
public class Item {

    private static final ItemConfig ITEM_CONFIG = new ItemConfig();
    private static final Map<String, Item> ALL_ITEMS = new HashMap<>();

    // TOOLS
    public static final Item HATCHET = get("hatchet", Material.WOODEN_AXE, 1);
    public static final Item MATTOCK = get("mattock", Material.WOODEN_PICKAXE, 1);
    public static final Item SHIV = get("shiv", Material.WOODEN_HOE, 1);
    public static final Item HAMMER = get("hammer", Material.WOODEN_SWORD, 1);
    public static final Item FIRESTRIKER = get("firestriker", Material.WOODEN_SHOVEL, 1);
    public static final Item GRAPPLING_HOOK = get("grappling_hook", Material.FISHING_ROD, 1);
    public static final Item COMPASS = get("compass", Material.COMPASS, 1);
    public static final Item FLINT_SICKLE = get("flint_sickle", Material.WOODEN_HOE, 4);
    public static final Item STONE_SICKLE = get("stone_sickle", Material.WOODEN_HOE, 2);
    public static final Item IRON_SICKLE = get("iron_sickle", Material.IRON_HOE, 1);
    public static final Item DIAMOND_SICKLE = get("diamond_sickle", Material.DIAMOND_HOE, 1);
    public static final Item MEDIC_KIT = get("medic_kit", Material.CLOCK, 1);
    public static final Item RECURVE_BOW = get("recurve_bow", Material.BOW, 1);
    public static final Item RECURVE_CROSSBOW = get("recurve_crossbow", Material.CROSSBOW, 1);

    // LEGENDARY TOOLS
    public static final Item VALKYRIES_AXE = get("valkyries_axe", Material.DIAMOND_AXE, 1);
    public static final Item QUARTZ_PICKAXE = get("quartz_pickaxe", Material.DIAMOND_PICKAXE, 1);
    public static final Item OBSIDIAN_MACE = get("obsidian_mace", Material.DIAMOND_SHOVEL, 1);
    public static final Item ENDER_GIANT_BLADE = get("ender_giant_blade", Material.DIAMOND_HOE, 2);
    public static final Item BLAZE_SWORD = get("blaze_sword", Material.DIAMOND_SWORD, 1);

    // ARMOR
    public static final Item REINFORCED_LEATHER_BOOTS = get("reinforced_leather_boots", Material.CHAINMAIL_BOOTS, 1);
    public static final Item REINFORCED_LEATHER_TUNIC = get("reinforced_leather_tunic", Material.CHAINMAIL_CHESTPLATE, 1);
    public static final Item REINFORCED_LEATHER_TROUSERS = get("reinforced_leather_trousers", Material.CHAINMAIL_LEGGINGS, 1);
    public static final Item REINFORCED_LEATHER_HELMET = get("reinforced_leather_helmet", Material.CHAINMAIL_HELMET, 1);
    public static final Item GOLDEN_SABATONS = get("golden_sabatons", Material.GOLDEN_BOOTS, 0);
    public static final Item GOLDEN_GUARD = get("golden_guard", Material.GOLDEN_CHESTPLATE, 0);
    public static final Item GOLDEN_GREAVES = get("gulden_greaves", Material.GOLDEN_LEGGINGS, 0);
    public static final Item GOLDEN_CROWN = get("golden_crown", Material.GOLDEN_HELMET, 0);
    public static final Item IRON_BOOTS = get("iron_boots", Material.IRON_BOOTS, 0);
    public static final Item IRON_CHESTPLATE = get("iron_chestplate", Material.IRON_CHESTPLATE, 0);
    public static final Item IRON_LEGGINGS = get("iron_leggings", Material.IRON_LEGGINGS, 0);
    public static final Item IRON_HELMET = get("iron_helmet", Material.IRON_HELMET, 0);
    public static final Item DIAMOND_BOOTS = get("diamond_boots", Material.DIAMOND_BOOTS, 0);
    public static final Item DIAMOND_CHESTPLATE = get("diamond_chestplate", Material.DIAMOND_CHESTPLATE, 0);
    public static final Item DIAMOND_HELMET = get("diamond_helmet", Material.DIAMOND_HELMET, 0);
    public static final Item DIAMOND_LEGGINGS = get("diamond_leggings", Material.DIAMOND_LEGGINGS, 0);
    public static final Item BEEKEEPER_HELMET = get("beekeeper_helmet", Material.LEATHER_HELMET, 10881);
    public static final Item BEEKEEPER_CHESTPLATE = get("beekeeper_chestplate", Material.LEATHER_CHESTPLATE, 10881);
    public static final Item BEEKEEPER_LEGGINGS = get("beekeeper_leggings", Material.LEATHER_LEGGINGS, 10881);
    public static final Item BEEKEEPER_BOOTS = get("beekeeper_boots", Material.LEATHER_BOOTS, 10881);

    // BLOCKS
    public static final Item WORKBENCH = get("workbench", Material.CRAFTING_TABLE, 0);
    public static final Item CAMPFIRE = get("campfire", Material.CAMPFIRE, 1);

    // MISC
    public static final Item FERMENTED_SKIN = get("fermented_skin", Material.RABBIT_HIDE, 0);
    public static final Item COFFEE_BEAN = get("coffee_bean", Material.COCOA_BEANS, 1);

    // FOOD
    public static final Item SUSPICIOUS_MEAT = get("suspicious_meat", Material.SUSPICIOUS_STEW, 10881);

    // DRINKS
    public static final Item DIRTY_WATER = get("dirty_water", Material.POTION, 1);
    public static final Item CLEAN_WATER = get("clean_water", Material.POTION, 2);
    public static final Item PURIFIED_WATER = get("purified_water", Material.POTION, 3);
    public static final Item COFFEE = get("coffee", Material.POTION, 4);
    public static final Item HOT_MILK = get("hot_milk", Material.POTION, 5);
    public static final Item COLD_MILK = get("cold_milk", Material.POTION, 6);

    /**
     * @deprecated Use {@link #WATER_BOWL} instead
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated // Removed in 3.11.0 - Will remove old water bowl in future
    public static final Item WATER_BOWL_OLD = get("water_bowl_old", Material.BEETROOT_SOUP, 1);
    public static final Item WATER_BOWL = get("water_bowl", Material.POTION, 10881);

    // TODO Experimental
    public static final Item PERSISTENT_TORCH = get("persistent_torch", Material.TORCH, 1);

    private static Item get(String key, Material material, int defaultData) {
        int data = ITEM_CONFIG.getModelData(key, defaultData);
        Item item = new Item(key, material, data);
        ALL_ITEMS.put(key, item);
        return item;
    }

    /**
     * Get an Item based on a key
     *
     * @param value Key for item
     * @return Item based on key
     */
    public static Item valueOf(String value) {
        if (ALL_ITEMS.containsKey(value.toLowerCase())) {
            return ALL_ITEMS.get(value.toLowerCase());
        }
        return null;
    }

    /**
     * Get a collection of all registered Items
     *
     * @return Collection of all registered Items
     */
    public static Collection<Item> values() {
        return ALL_ITEMS.values();
    }

    // OBJECT

    private final String key;
    private final Material materialType;
    private final int modelData;

    Item(@NotNull String key, @NotNull Material mat, int customModelData) {
        this.key = key;
        this.modelData = customModelData;
        this.materialType = mat;
    }

    /**
     * Get the key of this item
     *
     * @return Key of this item
     */
    @SuppressWarnings("unused")
    public String getKey() {
        return key;
    }

    /**
     * Get the Material of this item
     *
     * @return Material of this item
     */
    public Material getMaterialType() {
        return materialType;
    }

    /**
     * Get the CustomModelData of this item
     *
     * @return CustomModelData of this item
     */
    public int getModelData() {
        return modelData;
    }

    /**
     * Get a new ItemStack based on this item
     *
     * @return New ItemStack based on this item
     */
    public ItemStack getItem() {
        return ItemManager.get(this);
    }

    /**
     * Compare this item with an ItemStack
     *
     * @param itemStack ItemStack to check
     * @return True if matched
     */
    public boolean compare(@NotNull ItemStack itemStack) {
        if (itemStack.getType() == materialType) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta != null && meta.hasCustomModelData()) {
                return meta.getCustomModelData() == modelData;
            } else {
                return modelData == 0;
            }
        }
        return false;
    }

    /**
     * Tags for different {@link Item} groups
     */
    @SuppressWarnings("unused")
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
         * Check if an ItemStack is tagged in a group of custom {@link Item}
         *
         * @param item ItemStack to check
         * @return True if item matches tag
         */
        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        public boolean isTagged(ItemStack item) {
            return ItemManager.compare(item, items);
        }

    }

}
