package tk.shanebee.survival.util;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;

import java.util.*;

@SuppressWarnings("WeakerAccess")
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
        if (isNonWoodSlab(material)) return true;
        if (isNonWoodStairs(material)) return true;
        return Tag.STONE_BRICKS.isTagged(material)
                || Tag.WALLS.isTagged(material);
    }

    public static boolean isNonWoodDoor(Material material) {
        return (Tag.DOORS.isTagged(material) && !Tag.WOODEN_DOORS.isTagged(material));
    }

    public static boolean isNonWoodSlab(Material material) {
        return (Tag.SLABS.isTagged(material) && !Tag.WOODEN_SLABS.isTagged(material));
    }

    public static boolean isNonWoodStairs(Material material) {
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

    public static boolean isFarmable(Material material) {
        switch (material) {
            case MELON:
            case MELON_STEM:
            case PUMPKIN:
            case PUMPKIN_STEM:
            case JACK_O_LANTERN:
            case CHORUS_FLOWER:
            case CHORUS_PLANT:
            case CARROTS:
            case POTATOES:
            case BEETROOTS:
            case WHEAT:
            case SWEET_BERRY_BUSH:
            case COCOA:
                return true;
            default:
                return false;
        }
    }

    public static boolean isShovel(Material material) {
        switch (material) {
            case STONE_SHOVEL:
            case IRON_SHOVEL:
            case DIAMOND_SHOVEL:
                //GOLD + WOODEN left out for a reason!
                return true;
            default:
                return false;
        }
    }

    public static boolean requiresShovel(Material material) {
        switch (material) {
            case GRASS_BLOCK:
            case DIRT:
            case PODZOL:
            case COARSE_DIRT:
            case GRASS_PATH:
            case FARMLAND:
            case SOUL_SAND:
            case SAND:
            case RED_SAND:
            case CLAY:
            case MYCELIUM:
            case SNOW:
            case SNOW_BLOCK:
                return true;
        }
        return Utils.isConcretePowder(material);
    }

    public static boolean isPickaxe(Material material) {
        switch (material) {
            case GOLDEN_PICKAXE:
            case WOODEN_PICKAXE:
            case DIAMOND_PICKAXE:
            case IRON_PICKAXE:
            case STONE_PICKAXE:
                return true;
            default:
                return false;
        }
    }

    public static boolean requiresPickaxe(Material material) {
        switch (material) {
            case NETHER_BRICK_FENCE:
            case SPAWNER:
            case SEA_LANTERN:
            case GLOWSTONE:
            case END_ROD:
            case DISPENSER:
            case DROPPER:
            case OBSERVER:
            case PISTON:
            case PISTON_HEAD:
            case STICKY_PISTON:
            case MOVING_PISTON:
            case DAYLIGHT_DETECTOR:
            case ENCHANTING_TABLE:
            case ANVIL:
            case ENDER_CHEST:
            case HOPPER:
            case CAULDRON:
            case BREWING_STAND:
            case STONE_PRESSURE_PLATE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case BEACON:
            case OBSIDIAN:
                return true;
        }
        if (Utils.isOreBlock(material)) return true;
        if (Utils.isNaturalOreBlock(material)) return true;
        if (Utils.isNonWoodDoor(material)) return true;
        if (Utils.isNonWoodSlab(material)) return true;
        if (Utils.isNonWoodStairs(material)) return true;
        if (Utils.isTerracotta(material)) return true;
        if (Utils.isGlazedTerracotta(material)) return true;
        if (Utils.isConcrete(material)) return true;
        if (Utils.isStoneTypeBlock(material)) return true;
        if (Utils.isCookingBlock(material)) return true;
        if (Tag.WALLS.isTagged(material)) return true;
        if (Tag.ICE.isTagged(material)) return true;
        if (Tag.CORAL_BLOCKS.isTagged(material)) return true;
        return Tag.RAILS.isTagged(material);
    }

    public static boolean isAxe(Material material) {
        switch (material) {
            case WOODEN_AXE:
            case STONE_AXE:
            case GOLDEN_AXE:
            case IRON_AXE:
            case DIAMOND_AXE:
                return true;
            default:
                return false;
        }
    }

    public static boolean requiresAxe(Material material) {
        switch (material) {
            case CHEST:
            case TRAPPED_CHEST:
            case CRAFTING_TABLE:
            case BOOKSHELF:
            case LADDER:
            case JUKEBOX:
            case NOTE_BLOCK:
            case DAYLIGHT_DETECTOR:
                return true;
        }
        if (Tag.WOODEN_DOORS.isTagged(material)) return true;
        if (Tag.PLANKS.isTagged(material)) return true;
        if (Tag.LOGS.isTagged(material)) return true;
        if (Tag.WOODEN_STAIRS.isTagged(material)) return true;
        if (Tag.WOODEN_SLABS.isTagged(material)) return true;
        if (Tag.WOODEN_FENCES.isTagged(material)) return true;
        if (Tag.WOODEN_PRESSURE_PLATES.isTagged(material)) return true;
        if (Tag.BANNERS.isTagged(material)) return true;
        if (Tag.SIGNS.isTagged(material)) return true;
        return Utils.isWoodGate(material);
    }

    public static boolean requiresShears(Material material) {
        switch (material) {
            case COBWEB:
            case TRIPWIRE:
            case TNT:
            case MUSHROOM_STEM:
                return true;
            default:
                return false;
        }
    }

    public static List<Material> getDrops(Material material, Boolean grown) {
        List<Material> mat = new ArrayList<>();
        switch (material) {
            case PUMPKIN:
                mat.add(Material.PUMPKIN);
                break;
            case JACK_O_LANTERN:
                mat.add(Material.JACK_O_LANTERN);
                break;
            case MELON_STEM:
                mat.add(Material.MELON_SEEDS);
                break;
            case MELON:
                mat.add(Material.MELON_SLICE);
                break;
            case PUMPKIN_STEM:
                mat.add(Material.PUMPKIN_SEEDS);
                break;
            case CHORUS_FLOWER:
                mat.add(Material.CHORUS_FLOWER);
                break;
            case CARROTS:
                mat.add(Material.CARROT);
                break;
            case POTATOES:
                mat.add(Material.POTATO);
                break;
            case BEETROOTS:
                if (grown) {
                    mat.add(Material.BEETROOT);
                    mat.add(Material.BEETROOT_SEEDS);
                }
                else mat.add(Material.BEETROOT_SEEDS);
                break;
            case WHEAT:
                if (grown) {
                    mat.add(Material.WHEAT);
                    mat.add(Material.WHEAT_SEEDS);
                }
                else mat.add(Material.WHEAT_SEEDS);
                break;
            case SWEET_BERRY_BUSH:
                mat.add(Material.SWEET_BERRIES);
                break;
            case COCOA:
                mat.add(Material.COCOA_BEANS);
                break;
            default:
                mat.add(Material.AIR);
        }
        return mat;
    }

    /** Send a colored string to a Player
     * <p>
     *     Does not require ChatColor methods
     * </p>
     * @param player The player to send a colored string to
     * @param msg The string to send including color codes
     */
    public static void sendColoredMsg(CommandSender player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void sendColoredConsoleMsg(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    /** Gets a colored string
     * @param string The string including color codes
     * @return Returns a formatted string
     */
    public static String getColoredString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /** Spawn a particle at a location for all players
     * @param location The location to spawn a particle at
     * @param particle The particle to spawn
     * @param amount The amount of particles
     * @param offsetX Offset by x
     * @param offsetY Offset by y
     * @param offsetZ Offset by z
     */
    public static void spawnParticle(Location location, Particle particle, int amount, double offsetX, double offsetY, double offsetZ) {
        assert location.getWorld() != null;
        location.getWorld().spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ);
    }

    /** Spawn a particle at a location for a player
     * @param location The location to spawn a particle at
     * @param particle The particle to spawn
     * @param amount The amount of particles
     * @param offsetX Offset by x
     * @param offsetY Offset by y
     * @param offsetZ Offset by z
     * @param player The player to spawn a particle for
     */
    public static void spawnParticle(Location location, Particle particle, int amount, double offsetX, double offsetY, double offsetZ, Player player) {
        player.spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ);
    }

    /** Set the durability of an ItemStack
     * @param item The ItemStack to set
     * @param durability The durability to set
     */
    public static void setDurability(ItemStack item, int durability) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        ((Damageable) meta).setDamage(durability);
        item.setItemMeta(meta);
    }

    /** Check the durability of an ItemStack
     * @param item The ItemStack to check
     * @return The durability of the ItemStack
     */
    public static int getDurability(ItemStack item) {
        assert item.getItemMeta() != null;
        return ((Damageable) item.getItemMeta()).getDamage();
    }

    public static List<ItemStack> getItemStackDura(Items item, int maxDurability) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < maxDurability; i++) {
            ItemStack stack = ItemManager.get(item);
            ItemMeta meta = stack.getItemMeta();
            assert meta != null;
            ((Damageable) meta).setDamage(i);
            stack.setItemMeta(meta);
            itemStacks.add(stack);
        }
        return itemStacks;
    }

    /** Gets the minutes a player has played on the server
     * @param player The player to check
     * @return The number of minutes they have played on the server
     */
    public static int getMinutesPlayed(Player player) {
        int played = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        return Math.round(played / 1200);
    }

    /** Check if server is running a minimum Minecraft version
     * @param major Major version to check (Most likely just going to be 1)
     * @param minor Minor version to check
     * @return True if running this version or higher
     */
    public static boolean isRunningMinecraft(int major, int minor) {
        return isRunningMinecraft(major, minor, 0);
    }

    /** Check if server is running a minimum Minecraft version
     * @param major Major version to check (Most likely just going to be 1)
     * @param minor Minor version to check
     * @param revision Revision to check
     * @return True if running this version or higher
     */
    public static boolean isRunningMinecraft(int major, int minor, int revision) {
        String[] version = Bukkit.getServer().getBukkitVersion().split("-")[0].split("\\.");
        int maj = Integer.valueOf(version[0]);
        int min = Integer.valueOf(version[1]);
        int rev;
        try {
            rev = Integer.valueOf(version[2]);
        } catch (Exception ignore) {
            rev = 0;
        }
        return maj >= major && min >= minor && rev >= revision;
    }

}
