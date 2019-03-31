package com.fattymieo.survival.util;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {

    public static boolean isConcrete(Material material) {
        switch (material) {
            case CYAN_CONCRETE:
            case BLACK_CONCRETE:
            case BLUE_CONCRETE:
            case BROWN_CONCRETE:
            case GRAY_CONCRETE:
            case GREEN_CONCRETE:
            case LIGHT_BLUE_CONCRETE:
            case LIGHT_GRAY_CONCRETE:
            case LIME_CONCRETE:
            case MAGENTA_CONCRETE:
            case ORANGE_CONCRETE:
            case PINK_CONCRETE:
            case PURPLE_CONCRETE:
            case RED_CONCRETE:
            case WHITE_CONCRETE:
            case YELLOW_CONCRETE:
                return true;
        }
        return false;
    }

    public static boolean isConcretePowder(Material material) {
        switch (material) {
            case BLACK_CONCRETE_POWDER:
            case BLUE_CONCRETE_POWDER:
            case BROWN_CONCRETE_POWDER:
            case CYAN_CONCRETE_POWDER:
            case GRAY_CONCRETE_POWDER:
            case GREEN_CONCRETE_POWDER:
            case LIME_CONCRETE_POWDER:
            case MAGENTA_CONCRETE_POWDER:
            case ORANGE_CONCRETE_POWDER:
            case PINK_CONCRETE_POWDER:
            case PURPLE_CONCRETE_POWDER:
            case RED_CONCRETE_POWDER:
            case WHITE_CONCRETE_POWDER:
            case YELLOW_CONCRETE_POWDER:
            case LIGHT_BLUE_CONCRETE_POWDER:
            case LIGHT_GRAY_CONCRETE_POWDER:
                return true;
        }
        return false;
    }

    public static boolean isGlazedTerracotta(Material material) {
        switch (material) {
            case BLACK_GLAZED_TERRACOTTA:
            case BLUE_GLAZED_TERRACOTTA:
            case BROWN_GLAZED_TERRACOTTA:
            case CYAN_GLAZED_TERRACOTTA:
            case GRAY_GLAZED_TERRACOTTA:
            case GREEN_GLAZED_TERRACOTTA:
            case LIGHT_BLUE_GLAZED_TERRACOTTA:
            case LIME_GLAZED_TERRACOTTA:
            case MAGENTA_GLAZED_TERRACOTTA:
            case ORANGE_GLAZED_TERRACOTTA:
            case PINK_GLAZED_TERRACOTTA:
            case PURPLE_GLAZED_TERRACOTTA:
            case RED_GLAZED_TERRACOTTA:
            case LIGHT_GRAY_GLAZED_TERRACOTTA:
            case WHITE_GLAZED_TERRACOTTA:
            case YELLOW_GLAZED_TERRACOTTA:
                return true;
        }
        return false;
    }

    public static boolean isTerracotta(Material material) {
        switch (material) {
            case BLACK_TERRACOTTA:
            case BLUE_TERRACOTTA:
            case BROWN_TERRACOTTA:
            case CYAN_TERRACOTTA:
            case GRAY_TERRACOTTA:
            case GREEN_TERRACOTTA:
            case LIGHT_BLUE_TERRACOTTA:
            case LIME_TERRACOTTA:
            case MAGENTA_TERRACOTTA:
            case ORANGE_TERRACOTTA:
            case PINK_TERRACOTTA:
            case PURPLE_TERRACOTTA:
            case RED_TERRACOTTA:
            case LIGHT_GRAY_TERRACOTTA:
            case WHITE_TERRACOTTA:
            case YELLOW_TERRACOTTA:
                return true;
        }
        return false;
    }

    public static boolean isNaturalOreBlock(Material material) {
        switch (material) {
            case COAL_ORE:
            case DIAMOND_ORE:
            case EMERALD_ORE:
            case GOLD_ORE:
            case IRON_ORE:
            case LAPIS_ORE:
            case NETHER_QUARTZ_ORE:
            case REDSTONE_ORE:
                return true;
        }
        return false;
    }

    public static boolean isOreBlock(Material material) {
        switch (material) {
            case COAL_BLOCK:
            case DIAMOND_BLOCK:
            case EMERALD_BLOCK:
            case GOLD_BLOCK:
            case IRON_BLOCK:
            case LAPIS_BLOCK:
            case QUARTZ_BLOCK:
            case REDSTONE_BLOCK:
                return true;
        }
        return false;
    }

    public static boolean isStoneTypeBlock(Material material) {
        switch (material) {
            case STONE:
            case COBBLESTONE:
            case MOSSY_COBBLESTONE:
            case INFESTED_COBBLESTONE:
            case ANDESITE:
            case POLISHED_ANDESITE:
            case DIORITE:
            case POLISHED_DIORITE:
            case GRANITE:
            case POLISHED_GRANITE:
            case BRICK:
            case NETHER_BRICK:
            case SANDSTONE:
            case RED_SANDSTONE:
            case PRISMARINE:
            case NETHERRACK:
                return true;
        }
        return false;
    }

    public static boolean isDoorNotWood(Material material) {
        return (Tag.DOORS.isTagged(material) && !Tag.WOODEN_DOORS.isTagged(material));
    }

    public static boolean isSlabNotWood(Material material) {
        return (Tag.SLABS.isTagged(material) && !Tag.WOODEN_SLABS.isTagged(material));
    }

    public static boolean isStairsNotWood(Material material) {
        return (Tag.STAIRS.isTagged(material) && !Tag.WOODEN_STAIRS.isTagged(material));
    }

    public static boolean isWoodFence(Material material) {
        switch (material) {
            case ACACIA_FENCE:
            case BIRCH_FENCE:
            case DARK_OAK_FENCE:
            case JUNGLE_FENCE:
            case OAK_FENCE:
            case SPRUCE_FENCE:
                return true;
        }
        return false;
    }

    public static boolean isWoodGate(Material material) {
        switch (material) {
            case ACACIA_FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case OAK_FENCE_GATE:
            case SPRUCE_FENCE_GATE:
                return true;
        }
        return false;
    }

    public static boolean isBed(Material material) {
        switch (material) {
            case BLACK_BED:
            case BLUE_BED:
            case BROWN_BED:
            case CYAN_BED:
            case GRAY_BED:
            case GREEN_BED:
            case LIME_BED:
            case MAGENTA_BED:
            case ORANGE_BED:
            case PINK_BED:
            case PURPLE_BED:
            case RED_BED:
            case WHITE_BED:
            case YELLOW_BED:
            case LIGHT_BLUE_BED:
            case LIGHT_GRAY_BED:
                return true;
        }
        return false;
    }

    public static boolean isWall(Material material) {
        switch (material) {
            case COBBLESTONE_WALL:
            case MOSSY_COBBLESTONE_WALL:
                return true;
        }
        return false;
    }

    public static void sendColoredMsg(CommandSender player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static String getColoredString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void spawnParticle(Location location, Particle particle, int amount, double offsetX, double offsetY, double offsetZ) {
        location.getWorld().spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ);
    }

    public static void spawnParticle(Location location, Particle particle, int amount, double offsetX, double offsetY, double offsetZ, Player player) {
        player.spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ);
    }

    public static void setDurability(ItemStack item, int durability) {
        ItemMeta meta = item.getItemMeta();
        ((Damageable) meta).setDamage(durability);
        item.setItemMeta(meta);
    }

    public static int getDurability(ItemStack item) {
        return ((Damageable) item.getItemMeta()).getDamage();
    }

}
