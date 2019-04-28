package tk.shanebee.survival.util;

import tk.shanebee.survival.managers.Items;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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

    public static boolean isCookingBlock(Material material) {
        switch (material) {
            case FURNACE:
            case BLAST_FURNACE:
            case SMOKER:
                return true;
        }
        return false;
    }

    public static boolean isUtilityBlock(Material material) {
        switch (material) {
            case CARTOGRAPHY_TABLE:
            case FLETCHING_TABLE:
            case LECTERN:
            case LOOM:
            case STONECUTTER:
            case GRINDSTONE:
            case SMITHING_TABLE:
            case ANVIL:
            case ENCHANTING_TABLE:
            case JUKEBOX:
            case NOTE_BLOCK:
            case BREWING_STAND:
            case CAULDRON:
            case COMPOSTER:
                return true;
        }
        return false;
    }

    public static boolean isShulkerBox(Material material) {
        switch (material) {
            case SHULKER_BOX:
            case BLACK_SHULKER_BOX:
            case BLUE_SHULKER_BOX:
            case BROWN_SHULKER_BOX:
            case CYAN_SHULKER_BOX:
            case GRAY_SHULKER_BOX:
            case GREEN_SHULKER_BOX:
            case LIGHT_BLUE_SHULKER_BOX:
            case LIGHT_GRAY_SHULKER_BOX:
            case LIME_SHULKER_BOX:
            case MAGENTA_SHULKER_BOX:
            case ORANGE_SHULKER_BOX:
            case PINK_SHULKER_BOX:
            case PURPLE_SHULKER_BOX:
            case RED_SHULKER_BOX:
            case WHITE_SHULKER_BOX:
            case YELLOW_SHULKER_BOX:
                return true;
        }
        return false;
    }

    public static boolean isStorageBlock(Material material) {
        switch (material) {
            case CHEST:
            case ENDER_CHEST:
            case TRAPPED_CHEST:
            case BARREL:
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
            case CHISELED_SANDSTONE:
            case SMOOTH_SANDSTONE:
            case CUT_SANDSTONE:
            case RED_SANDSTONE:
            case CHISELED_RED_SANDSTONE:
            case CUT_RED_SANDSTONE:
            case SMOOTH_RED_SANDSTONE:
            case PRISMARINE:
            case NETHERRACK:
            case END_STONE:
            case END_STONE_BRICKS:
            case PURPUR_BLOCK:
            case PURPUR_PILLAR:
                return true;
                default:
        }
        if (isSlabNotWood(material)) return true;
        if (isStairsNotWood(material)) return true;
        if (Tag.STONE_BRICKS.isTagged(material)
        || Tag.WALLS.isTagged(material)) return true;
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


    public static Material getDrops(Material material, Boolean grown) {
        switch (material) {
            case PUMPKIN:
                return Material.PUMPKIN;
            case JACK_O_LANTERN:
                return Material.JACK_O_LANTERN;
            case MELON_STEM:
                return Material.MELON_SEEDS;
            case PUMPKIN_STEM:
                return Material.PUMPKIN_SEEDS;
            case CHORUS_FLOWER:
                return Material.CHORUS_FLOWER;
            case CARROTS:
                return Material.CARROT;
            case POTATOES:
                return Material.POTATO;
            case BEETROOTS:
                if (grown) return Material.BEETROOT;
                else return Material.BEETROOT_SEEDS;
            case WHEAT:
                if (grown) return Material.WHEAT;
                else return Material.WHEAT_SEEDS;
            case SWEET_BERRY_BUSH:
                return Material.SWEET_BERRIES;

                default:
                    return Material.AIR;

        }
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

    public static List<ItemStack> getItemStackDura(Items item, int maxDurability) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < maxDurability; i++) {
            ItemStack stack = Items.get(item);
            ItemMeta meta = stack.getItemMeta();
            ((Damageable) meta).setDamage(i);
            stack.setItemMeta(meta);
            itemStacks.add(stack);
        }
        return itemStacks;
    }

}
