package com.shanebeestudios.survival.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

/**
 * Custom block tags
 */
@SuppressWarnings("unused")
public class BlockTags {

    public static void initialize() {
    }

    public static Tag<Material> CONCRETE = getTag("concrete");
    public static Tag<Material> COOKING_BLOCK = getTag("cooking_block");
    public static Tag<Material> FARMABLE = getTag("farmable");
    public static Tag<Material> GLAZED_TERRACOTTA = getTag("glazed_terracotta");
    public static Tag<Material> ORES = getTag("ores");
    public static Tag<Material> ORE_TYPE_BLOCK = getTag("ore_type_block");
    public static Tag<Material> STONE_TYPE = getTag("stone_type");
    public static Tag<Material> STORAGE_BLOCK = getTag("storage_block");
    public static Tag<Material> UTILITY_BLOCK = getTag("utility_block");

    public static Tag<Material> REQUIRES_AXE = getTag("requires_axe");
    public static Tag<Material> REQUIRES_PICKAXE = getTag("requires_pickaxe");
    public static Tag<Material> REQUIRES_SHOVEL = getTag("requires_shovel");
    public static Tag<Material> REQUIRES_SHEARS = getTag("requires_shears");
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
