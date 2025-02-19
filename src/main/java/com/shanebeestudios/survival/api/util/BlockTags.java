package com.shanebeestudios.survival.api.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

/**
 * Custom block tags
 * <p>These are created in the `block-tags.yml` file</p>
 */
@SuppressWarnings("unused")
public class BlockTags {

    private BlockTags() {
    }

    private static boolean initialized = false;

    /**
     * @hidden
     */
    public static void initialize() {
        if (initialized) {
            throw new IllegalStateException("BlockTags already initialized");
        }
        initialized = true;
    }

    /**
     * Concrete blocks
     */
    public static Tag<Material> CONCRETE = getTag("concrete");
    /**
     * Cooking blocks (furnace, smoker, blast furnace)
     */
    public static Tag<Material> COOKING_BLOCK = getTag("cooking_block");
    /**
     * All glazed terracotta
     */
    public static Tag<Material> GLAZED_TERRACOTTA = getTag("glazed_terracotta");
    /**
     * Ores
     */
    public static Tag<Material> ORES = getTag("ores");
    /**
     * Blocks from ore blocks (such as diamond block, coal block, iron block)
     */
    public static Tag<Material> ORE_TYPE_BLOCK = getTag("ore_type_block");
    /**
     * Blocks that represent stone types
     */
    public static Tag<Material> STONE_TYPE = getTag("stone_type");
    /**
     * Blocks that can hold items
     */
    public static Tag<Material> STORAGE_BLOCK = getTag("storage_block");
    /**
     * Blocks that a player can utilize
     */
    public static Tag<Material> UTILITY_BLOCK = getTag("utility_block");
    /**
     * Blocks that require an axe to break
     */
    public static Tag<Material> REQUIRES_AXE = getTag("requires_axe");
    /**
     * Blocks that require a pickaxe to break
     */
    public static Tag<Material> REQUIRES_PICKAXE = getTag("requires_pickaxe");
    /**
     * Blocks that require a shovel to break
     */
    public static Tag<Material> REQUIRES_SHOVEL = getTag("requires_shovel");
    /**
     * Blocks that require shears to break
     */
    public static Tag<Material> REQUIRES_SHEARS = getTag("requires_shears");
    /**
     * Blocks that requires a sickle to break
     */
    public static Tag<Material> REQUIRES_SICKLE = getTag("requires_sickle");
    /**
     * Blocks that require a hammer to build
     */
    public static Tag<Material> REQUIRES_HAMMER = getTag("requires_hammer");

    private static Tag<Material> getTag(String key) {
        NamespacedKey namespacedKey = NamespacedKey.fromString("survival_plus:" + key);
        assert namespacedKey != null;
        Tag<Material> tag = Bukkit.getTag(Tag.REGISTRY_BLOCKS, namespacedKey, Material.class);
        if (tag == null) {
            Utils.logMini("<red>Could not find tag for <white>'<aqua>%s<white>'", namespacedKey.toString());
            return null;
        }
        return tag;
    }

}
