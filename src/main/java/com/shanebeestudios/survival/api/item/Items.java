package com.shanebeestudios.survival.api.item;

import com.google.common.collect.Lists;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.shanebeestudios.survival.api.item.items.armor.ArmorPiece;
import com.shanebeestudios.survival.api.item.items.armor.ArmorPiece.ArmorMaterial;
import com.shanebeestudios.survival.api.item.items.armor.ArmorPiece.ArmorType;
import com.shanebeestudios.survival.api.item.items.armor.BeekeeperPiece;
import com.shanebeestudios.survival.api.item.items.armor.RainBoots;
import com.shanebeestudios.survival.api.item.items.armor.ReinforcedPiece;
import com.shanebeestudios.survival.api.item.items.armor.SnowBoots;
import com.shanebeestudios.survival.api.item.items.blocks.Campfire;
import com.shanebeestudios.survival.api.item.items.blocks.Workbench;
import com.shanebeestudios.survival.api.item.items.drinks.Coffee;
import com.shanebeestudios.survival.api.item.items.drinks.ColdMilk;
import com.shanebeestudios.survival.api.item.items.drinks.HotMilk;
import com.shanebeestudios.survival.api.item.items.drinks.Water;
import com.shanebeestudios.survival.api.item.items.food.SuspiciousMeat;
import com.shanebeestudios.survival.api.item.items.legendary.BlazeSword;
import com.shanebeestudios.survival.api.item.items.legendary.EnderGiantBlade;
import com.shanebeestudios.survival.api.item.items.legendary.ObsidianMace;
import com.shanebeestudios.survival.api.item.items.legendary.QuartzPickaxe;
import com.shanebeestudios.survival.api.item.items.legendary.ValkyriesAxe;
import com.shanebeestudios.survival.api.item.items.misc.BreedingEgg;
import com.shanebeestudios.survival.api.item.items.misc.CoffeeBean;
import com.shanebeestudios.survival.api.item.items.misc.FermentedSkin;
import com.shanebeestudios.survival.api.item.items.tools.Compass;
import com.shanebeestudios.survival.api.item.items.tools.FireStriker;
import com.shanebeestudios.survival.api.item.items.tools.GrapplingHook;
import com.shanebeestudios.survival.api.item.items.tools.Hammer;
import com.shanebeestudios.survival.api.item.items.tools.Hatchet;
import com.shanebeestudios.survival.api.item.items.tools.Mattock;
import com.shanebeestudios.survival.api.item.items.tools.MedicKit;
import com.shanebeestudios.survival.api.item.items.tools.RecurvedBow;
import com.shanebeestudios.survival.api.item.items.tools.RecurvedCrossbow;
import com.shanebeestudios.survival.api.item.items.tools.Shiv;
import com.shanebeestudios.survival.api.item.items.tools.Sickle;
import com.shanebeestudios.survival.api.util.Utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Custom SurvivalPlus {@link Item Items}
 */
@SuppressWarnings("UnstableApiUsage")
public class Items {

    static final Map<Key, Item> ALL_ITEMS = new LinkedHashMap<>();

    // TOOLS
    public static final Item HATCHET = new Hatchet();
    public static final Item MATTOCK = new Mattock();
    public static final Item SHIV = new Shiv();
    public static final Item HAMMER = new Hammer();
    public static final FireStriker FIRESTRIKER = new FireStriker();
    public static final Item GRAPPLING_HOOK = new GrapplingHook();
    public static final Item COMPASS = new Compass();
    public static final Item FLINT_SICKLE = new Sickle("flint", Material.FLINT);
    public static final Item STONE_SICKLE = new Sickle("stone", Material.COBBLESTONE);
    public static final Item IRON_SICKLE = new Sickle("iron", Material.IRON_INGOT);
    public static final Item DIAMOND_SICKLE = new Sickle("diamond", Material.DIAMOND);
    public static final MedicKit MEDIC_KIT = new MedicKit();
    public static final RecurvedBow RECURVED_BOW = new RecurvedBow();
    public static final RecurvedCrossbow RECURVED_CROSSBOW = new RecurvedCrossbow();

    // LEGENDARY TOOLS
    public static final Item VALKYRIES_AXE = new ValkyriesAxe();
    public static final Item QUARTZ_PICKAXE = new QuartzPickaxe();
    public static final Item OBSIDIAN_MACE = new ObsidianMace();
    public static final EnderGiantBlade ENDER_GIANT_BLADE = new EnderGiantBlade();
    public static final Item BLAZE_SWORD = new BlazeSword();

    // ARMOR
    public static final ReinforcedPiece REINFORCED_LEATHER_HELMET = new ReinforcedPiece(ArmorType.HELMET);
    public static final ReinforcedPiece REINFORCED_LEATHER_TUNIC = new ReinforcedPiece(ArmorType.CHESTPLATE);
    public static final ReinforcedPiece REINFORCED_LEATHER_TROUSERS = new ReinforcedPiece(ArmorType.LEGGINGS);
    public static final ReinforcedPiece REINFORCED_LEATHER_BOOTS = new ReinforcedPiece(ArmorType.BOOTS);

    public static final ArmorPiece GOLDEN_CROWN = new ArmorPiece(ArmorType.HELMET, ArmorMaterial.GOLDEN, 1.0, -0.0125);
    public static final ArmorPiece GOLDEN_GUARD = new ArmorPiece(ArmorType.CHESTPLATE, ArmorMaterial.GOLDEN, 3.0, -0.02);
    public static final ArmorPiece GOLDEN_GREAVES = new ArmorPiece(ArmorType.LEGGINGS, ArmorMaterial.GOLDEN, 2.0, -0.02);
    public static final ArmorPiece GOLDEN_SABATONS = new ArmorPiece(ArmorType.BOOTS, ArmorMaterial.GOLDEN, 1.0, -0.0125);

    public static final ArmorPiece IRON_HELMET = new ArmorPiece(ArmorType.HELMET, ArmorMaterial.IRON, 2.0, -0.02);
    public static final ArmorPiece IRON_CHESTPLATE = new ArmorPiece(ArmorType.CHESTPLATE, ArmorMaterial.IRON, 6.0, -0.03);
    public static final ArmorPiece IRON_LEGGINGS = new ArmorPiece(ArmorType.LEGGINGS, ArmorMaterial.IRON, 5.0, -0.03);
    public static final ArmorPiece IRON_BOOTS = new ArmorPiece(ArmorType.BOOTS, ArmorMaterial.IRON, 2.0, -0.02);

    public static final ArmorPiece DIAMOND_HELMET = new ArmorPiece(ArmorType.HELMET, ArmorMaterial.DIAMOND, 3, -0.02);
    public static final ArmorPiece DIAMOND_CHESTPLATE = new ArmorPiece(ArmorType.CHESTPLATE, ArmorMaterial.DIAMOND, 8, -0.03);
    public static final ArmorPiece DIAMOND_LEGGINGS = new ArmorPiece(ArmorType.LEGGINGS, ArmorMaterial.DIAMOND, 6, -0.03);
    public static final ArmorPiece DIAMOND_BOOTS = new ArmorPiece(ArmorType.BOOTS, ArmorMaterial.DIAMOND, 3, -0.02);

    public static final ArmorPiece NETHERITE_HELMET = new ArmorPiece(ArmorType.HELMET, ArmorMaterial.NETHERITE, 3.0, -0.02, 3.0, 0.1);
    public static final ArmorPiece NETHERITE_CHESTPLATE = new ArmorPiece(ArmorType.CHESTPLATE, ArmorMaterial.NETHERITE, 8.0, -0.02, 3.0, 0.1);
    public static final ArmorPiece NETHERITE_LEGGINGS = new ArmorPiece(ArmorType.LEGGINGS, ArmorMaterial.NETHERITE, 6.0, -0.02, 3.0, 0.1);
    public static final ArmorPiece NETHERITE_BOOTS = new ArmorPiece(ArmorType.BOOTS, ArmorMaterial.NETHERITE, 3.0, -0.02, 3.0, 0.1);

    public static final BeekeeperPiece BEEKEEPER_HELMET = new BeekeeperPiece(ArmorType.HELMET);
    public static final BeekeeperPiece BEEKEEPER_CHESTPLATE = new BeekeeperPiece(ArmorType.CHESTPLATE);
    public static final BeekeeperPiece BEEKEEPER_LEGGINGS = new BeekeeperPiece(ArmorType.LEGGINGS);
    public static final BeekeeperPiece BEEKEEPER_BOOTS = new BeekeeperPiece(ArmorType.BOOTS);
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
    public static final Water DIRTY_WATER = Water.dirty();
    public static final Water CLEAN_WATER = Water.clean();
    public static final Water PURIFIED_WATER = Water.purified();
    public static final Water SALTY_WATER = Water.salty();
    public static final Water MURKY_WATER = Water.murky();
    public static final Water WATER_BOWL = Water.waterBowl();
    public static final Coffee COFFEE = new Coffee();
    public static final HotMilk HOT_MILK = new HotMilk();
    public static final ColdMilk COLD_MILK = new ColdMilk();

    /**
     * Currently null, don't use
     */
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
     * Get a water bottle based on a biome
     *
     * @param biome Biome to check for bottle
     * @return Water bottle based on biome
     */
    public static Water getBiomeBasedWaterBottle(Biome biome) {
        String string = biome.getKey().getKey();
        if (string.contains("ocean")) {
            return Items.SALTY_WATER;
        } else if (string.contains("swamp")) {
            return Items.MURKY_WATER;
        } else if (string.contains("lush")) {
            return Items.PURIFIED_WATER;
        }
        return Items.DIRTY_WATER;
    }

    private static final List<Entity> DEBUG_DISPLAYS = Lists.newArrayList();

    /**
     * Spawn {@link ItemDisplay ItemDisplays} for each item at the provided player
     * <p>Leaving player null will remove the previous spawned entities</p>
     *
     * @param player Player to spawn at, or null to clear previous spawns
     */
    public static void debug(@Nullable Player player) {
        if (player == null) {
            DEBUG_DISPLAYS.forEach(Entity::remove);
            DEBUG_DISPLAYS.clear();
            return;
        }
        Location location = player.getLocation().getBlock().getLocation().clone().add(0, 1, 0);

        double x = 0;
        double y = 0;
        World world = player.getWorld();
        for (Item item : ALL_ITEMS.values()) {
            Location loc = location.clone().add(x, y, 0);
            ItemDisplay itemDisplay = world.spawn(loc, ItemDisplay.class, display -> {
                display.setItemStack(item.getItemStack());
                display.customName(Utils.getMini(item.getName()));
                display.setCustomNameVisible(true);
            });
            DEBUG_DISPLAYS.add(itemDisplay);
            x += 3;
            if (x > 20) {
                x = 0;
                y += 1.5;
            }
        }
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
        WATER_BOTTLE(DIRTY_WATER, MURKY_WATER, SALTY_WATER, CLEAN_WATER, PURIFIED_WATER),
        /**
         * Any drinkable item
         */
        DRINKABLE(DIRTY_WATER, MURKY_WATER, SALTY_WATER, CLEAN_WATER, PURIFIED_WATER, WATER_BOWL,
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
