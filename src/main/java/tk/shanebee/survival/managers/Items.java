package tk.shanebee.survival.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Custom SurvivalPlus items
 */
@SuppressWarnings("DeprecatedIsStillUsed")
public enum Items {

    // TOOLS
    HATCHET(Material.WOODEN_AXE, 1),
    MATTOCK(Material.WOODEN_PICKAXE, 1),
    SHIV(Material.WOODEN_HOE, 1),
    HAMMER(Material.WOODEN_SWORD, 1),
    FIRESTRIKER(Material.WOODEN_SHOVEL, 1),
    GRAPPLING_HOOK(Material.FISHING_ROD, 1),
    COMPASS(Material.COMPASS, 1),
    FLINT_SICKLE(Material.WOODEN_HOE, 4),
    STONE_SICKLE(Material.WOODEN_HOE, 2),
    IRON_SICKLE(Material.IRON_HOE, 1),
    DIAMOND_SICKLE(Material.DIAMOND_HOE, 1),
    MEDIC_KIT(Material.CLOCK, 1),
    RECURVE_BOW(Material.BOW, 1),
    RECURVE_CROSSBOW(Material.CROSSBOW, 1),

    // LEGENDARY TOOLS
    VALKYRIES_AXE(Material.DIAMOND_AXE, 1),
    QUARTZ_PICKAXE(Material.DIAMOND_PICKAXE, 1),
    OBSIDIAN_MACE(Material.DIAMOND_SHOVEL, 1),
    ENDER_GIANT_BLADE(Material.DIAMOND_HOE, 2),
    BLAZE_SWORD(Material.DIAMOND_SWORD, 1),

    // ARMOR
    REINFORCED_LEATHER_BOOTS(Material.CHAINMAIL_BOOTS, 1),
    REINFORCED_LEATHER_TUNIC(Material.CHAINMAIL_CHESTPLATE, 1),
    REINFORCED_LEATHER_TROUSERS(Material.CHAINMAIL_LEGGINGS, 1),
    REINFORCED_LEATHER_HELMET(Material.CHAINMAIL_HELMET, 1),
    GOLDEN_SABATONS(Material.GOLDEN_BOOTS, 0),
    GOLDEN_GUARD(Material.GOLDEN_CHESTPLATE, 0),
    GOLDEN_GREAVES(Material.GOLDEN_LEGGINGS, 0),
    GOLDEN_CROWN(Material.GOLDEN_HELMET, 0),
    IRON_BOOTS(Material.IRON_BOOTS, 0),
    IRON_CHESTPLATE(Material.IRON_CHESTPLATE, 0),
    IRON_LEGGINGS(Material.IRON_LEGGINGS, 0),
    IRON_HELMET(Material.IRON_HELMET, 0),
    DIAMOND_BOOTS(Material.DIAMOND_BOOTS, 0),
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, 0),
    DIAMOND_HELMET(Material.DIAMOND_HELMET, 0),
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, 0),
    BEEKEEPER_HELMET(Material.LEATHER_HELMET, 10881),
    BEEKEEPER_CHESTPLATE(Material.LEATHER_CHESTPLATE, 10881),
    BEEKEEPER_LEGGINGS(Material.LEATHER_LEGGINGS, 10881),
    BEEKEEPER_BOOTS(Material.LEATHER_BOOTS, 10881),

    // BLOCKS
    WORKBENCH(Material.CRAFTING_TABLE, 0),
    CAMPFIRE(Material.CAMPFIRE, 1),

    // MISC
    FERMENTED_SKIN(Material.RABBIT_HIDE, 0),
    COFFEE_BEAN(Material.COCOA_BEANS, 1),

    // FOOD
    SUSPICIOUS_MEAT(Material.SUSPICIOUS_STEW, 10881),

    // DRINKS
    DIRTY_WATER(Material.POTION, 1),
    CLEAN_WATER(Material.POTION, 2),
    PURIFIED_WATER(Material.POTION, 3),
    COFFEE(Material.POTION, 4),
    HOT_MILK(Material.POTION, 5),
    COLD_MILK(Material.POTION, 6),
    @Deprecated // Removed in 3.11.0 - Will remove old water bowl in future
    WATER_BOWL_OLD(Material.BEETROOT_SOUP, 1),
    WATER_BOWL(Material.POTION, 10881),

    // TODO Experimental
    PERSISTENT_TORCH(Material.TORCH, 1);

    private final Material materialType;
    private final int modelData;

    Items(Material mat, int customModelData) {
        this.modelData = customModelData;
        this.materialType = mat;
    }

    /**
     * Get a new ItemStack from this item.
     *
     * @return New ItemStack from this item.
     */
    public ItemStack getItem() {
        return ItemManager.get(this);
    }

    /**
     * Get the Bukkit Material Enum for this item
     *
     * @return Bukkit Material Enum
     */
    public Material getMaterialType() {
        return this.materialType;
    }

    /**
     * Get the custom model data tag for this item
     * <p>See <b>"custom_model_data"</b> in the <b><a href="https://minecraft.gamepedia.com/Model#Item_tags">Item Tags</a></b> section on the Minecraft wiki</p>
     *
     * @return Custom model data tag
     */
    public int getModelData() {
        return this.modelData;
    }

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

        private Items[] items;

        Tags(Items... items) {
            this.items = items;
        }

        /**
         * Get all items tagged in this group
         *
         * @return All items tagged in this group
         */
        public Items[] getItems() {
            return items;
        }

        /**
         * Check if an ItemStack is tagged in a group of custom {@link Items}
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