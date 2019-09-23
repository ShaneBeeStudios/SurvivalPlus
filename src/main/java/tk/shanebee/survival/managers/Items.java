package tk.shanebee.survival.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Custom SurvivalPlus items
 */
@SuppressWarnings("DeprecatedIsStillUsed")
public enum Items {

    HATCHET(Material.WOODEN_AXE, 1),
    MATTOCK(Material.WOODEN_PICKAXE, 1),
    SHIV(Material.WOODEN_HOE, 1),
    HAMMER(Material.WOODEN_SWORD, 1),
    @Deprecated // remove in the future
    VALKYRIES_AXE_OLD(Material.GOLDEN_AXE, 1),
    VALKYRIES_AXE(Material.DIAMOND_AXE, 1),
    @Deprecated // remove in the future
    QUARTZ_PICKAXE_OLD(Material.GOLDEN_PICKAXE, 1),
    QUARTZ_PICKAXE(Material.DIAMOND_PICKAXE, 1),
    @Deprecated // remove in the future
    OBSIDIAN_MACE_OLD(Material.GOLDEN_SHOVEL, 1),
    OBSIDIAN_MACE(Material.DIAMOND_SHOVEL, 1),
    @Deprecated // remove in the future
    ENDER_GIANT_BLADE_OLD(Material.GOLDEN_HOE, 1),
	ENDER_GIANT_BLADE(Material.DIAMOND_HOE, 2),
    @Deprecated // remove in the future
    BLAZE_SWORD_OLD(Material.GOLDEN_SWORD, 1),
	BLAZE_SWORD(Material.DIAMOND_SWORD, 1),
    WORKBENCH(Material.CRAFTING_TABLE, 0),
    FIRESTRIKER(Material.WOODEN_SHOVEL, 1),
    FERMENTED_SKIN(Material.RABBIT_HIDE, 0),
    MEDIC_KIT(Material.CLOCK, 1),
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
    RECURVE_BOW(Material.BOW, 1),
    RECURVE_CROSSBOW(Material.CROSSBOW, 1),
    DIRTY_WATER(Material.POTION, 1),
    CLEAN_WATER(Material.POTION, 2),
    PURIFIED_WATER(Material.POTION, 3),
    COFFEE(Material.POTION, 4),
    HOT_MILK(Material.POTION, 5),
    COLD_MILK(Material.POTION, 6),
    COFFEE_BEAN(Material.COCOA_BEANS, 1),
    WATER_BOWL(Material.BEETROOT_SOUP, 1),
    CAMPFIRE(Material.CAMPFIRE, 1),

    FLINT_SICKLE(Material.WOODEN_HOE, 4),
    STONE_SICKLE(Material.WOODEN_HOE, 2),
    @Deprecated // remove in the future
    IRON_SICKLE_OLD(Material.WOODEN_HOE, 3),
    IRON_SICKLE(Material.IRON_HOE, 1),
    DIAMOND_SICKLE(Material.DIAMOND_HOE, 1),

    GRAPPLING_HOOK(Material.FISHING_ROD, 1),


    // TODO Experimental
    PERSISTENT_TORCH(Material.TORCH, 1);

    private final Material materialType;
    private final int modelData;

    Items(Material mat, int customModelData) {
        this.modelData = customModelData;
        this.materialType = mat;
    }

    /** Get the Bukkit Material Enum for this item
     * @return Bukkit Material Enum
     */
    public Material getMaterialType() {
        return this.materialType;
    }

    /** Get the custom model data tag for this item
     * <p>See <b>"custom_model_data"</b> in the <b><a href="https://minecraft.gamepedia.com/Model#Item_tags">Item Tags</a></b> section on the Minecraft wiki</p>
     * @return Custom model data tag
     */
    public int getModelData() {
        return this.modelData;
    }

    public enum Tags {
        /**
         * Any sickle
         */
        SICKLES,
        /**
         * Any reinforced leather armor
         */
        REINFORCED_LEATHER_ARMOR,
        /**
         * Any water bottle
         */
        WATER_BOTTLE,
        /**
         * Any drinkable item
         */
        DRINKABLE;

        /** Check if an ItemStack is tagged in a group of custom {@link Items}
         * @param item ItemStack to check
         * @return True if item matches tag
         */
        public boolean isTagged(ItemStack item) {
            switch (this) {
                case SICKLES:
                    return ItemManager.compare(item, FLINT_SICKLE, STONE_SICKLE, IRON_SICKLE, DIAMOND_SICKLE);
                case REINFORCED_LEATHER_ARMOR:
                    return ItemManager.compare(item, REINFORCED_LEATHER_BOOTS, REINFORCED_LEATHER_TROUSERS,
                            REINFORCED_LEATHER_TUNIC, REINFORCED_LEATHER_HELMET);
                case WATER_BOTTLE:
                    return ItemManager.compare(item, DIRTY_WATER, CLEAN_WATER, PURIFIED_WATER);
                case DRINKABLE:
                    return ItemManager.compare(item, DIRTY_WATER, CLEAN_WATER, PURIFIED_WATER, WATER_BOWL,
                            COLD_MILK, HOT_MILK, COFFEE);
                default:
                    return false;
            }
        }
    }

}