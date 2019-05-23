package tk.shanebee.survival.util;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.managers.Items;

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
            ItemStack stack = Items.get(item);
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

    /** Recipe discovery enums for different discovery events
     *
     */
    public enum RecipeDiscovered {
        FIRST_JOIN("first_join"),
        DIAMONDS("diamonds"),
        IRON("iron"),
        GOLD("gold"),
        LOGS("logs"),
        FIRESTRIKER("firestriker"),
        FURNACE("furnace"),
        OBSIDIAN("obsidian"),
        ICE("ice"),
        GRAVEL("gravel"),
        COBBLESTONE("cobblestone"),
        BRICK("brick"),
        DIRT("dirt"),
        BLAST_FURNACE("blast-furnace"),
        FLINT("flint"),
        EGG("egg"),
        BOWL("bowl"),
        VINE("vine"),
        QUARTZ("quartz"),
        PAPER("paper"),
        STRING("string"),
        FEATHER("feather"),
        SPIDER_EYE("spider-eye"),
        ROTTEN_FLESH("rotten-flesh"),
        POTATO("potato"),
        BLAZE("blaze"),
        BLAZE_SWORD("blaze-sword"),
        ENDER_GIANT_BLADE("ender_giant_blade"),
        QUARTZ_PICKAXE("quartz_pickaxe"),
        VALKYRIES_AXE("valkyries_axe"),
        LEATHER("leather"),
        LEATHER_ARMOR("leather-armor"),
        CROSSBOW("crossbow"),
        FISHING_ROD("fishing_rod"),
        GLASS_BOTTLE("glass_bottle");

        private final String type;

        RecipeDiscovered(String type) {
            this.type = type;
        }

        /** Check if a player has discovered a group of recipes
         * @param player The player to check
         * @return If the player has discovered this group of recipes
         */
        public boolean hasDiscovered(Player player) {
            return player.getScoreboardTags().contains("survival-recipetype-" + type);
        }

        /** Set a recipe group as discovered for a player
         * @param player The player to set the group as discovered
         */
        public void setDiscovered(Player player) {
            player.addScoreboardTag("survival-recipetype-" + type);
        }
    }

}
