package com.shanebeestudios.survival.api.item;

import com.shanebeestudios.survival.api.util.Utils;
import com.shanebeestudios.survival.plugin.config.ItemConfig;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DyedItemColor;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnstableApiUsage", "PatternValidation"})
public abstract class Item {

    protected static final ItemConfig ITEM_CONFIG = new ItemConfig();
    private static final MiniMessage MINI = MiniMessage.miniMessage();

    // Minecraft default keys
    protected static final NamespacedKey BASE_ATTACK_DAMAGE = NamespacedKey.minecraft("base_attack_damage");
    protected static final NamespacedKey BASE_ATTACK_SPEED = NamespacedKey.minecraft("base_attack_speed");
    // Non-official keys
    protected static final NamespacedKey BASE_MOVEMENT_SPEED = NamespacedKey.minecraft("base_movement_speed");

    private Key key;
    protected NamespacedKey recipeKey;
    private ItemStack itemStack;
    private String name;

    private double repairPercent;
    private int repairCost;

    /**
     * Get an ItemStack from this item
     *
     * @return Cloned ItemStack of this item
     */
    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    /**
     * Get an ItemStack from this item
     *
     * @param amount Stack amount
     * @return Cloned ItemStack of this item
     */
    public ItemStack getItemStack(int amount) {
        ItemStack clone = this.itemStack.clone();
        clone.setAmount(amount);
        return clone;
    }

    protected void setupDefaults(String key, ItemStack itemStack) {
        setupDefaults(key, itemStack, false);
    }

    @SuppressWarnings("PatternValidation")
    protected void setupDefaults(String key, ItemStack itemStack, boolean vanillaModel) {
        this.key = Key.key("survival_plus", key);
        this.recipeKey = NamespacedKey.fromString(this.key.toString());
        if (!vanillaModel) {
            itemStack.setData(DataComponentTypes.ITEM_MODEL, this.key);
        }

        // Color
        int color = ITEM_CONFIG.getColor(key);
        if (color != 0) {
            DyedItemColor dyedItemColor = DyedItemColor.dyedItemColor(Color.fromRGB(color), false);
            itemStack.setData(DataComponentTypes.DYED_COLOR, dyedItemColor);
        }

        // Item Name
        String itemName = ITEM_CONFIG.getName(key);
        if (itemName != null) {
            if (itemStack.hasData(DataComponentTypes.POTION_CONTENTS)) {
                // Stupid workaround because potion names override item_name
                itemStack.setData(DataComponentTypes.CUSTOM_NAME, MINI.deserialize("<!italic>" + itemName));
            } else {
                itemStack.setData(DataComponentTypes.ITEM_NAME, MINI.deserialize(itemName));
            }
        } else if (!vanillaModel) {
            Utils.logMini("<red>Failed to load item name for item <white>'<aqua>" + key + "<white>'");
        }
        this.name = itemName;

        // Lore
        List<String> lore = ITEM_CONFIG.getLore(key);
        if (lore != null && !lore.isEmpty()) {
            List<Component> loreComponents = new ArrayList<>();
            for (String line : lore) {
                loreComponents.add(MINI.deserialize("<!italic>" + line));
            }
            itemStack.lore(loreComponents);
        }

        // Max Damage
        int maxDamage = ITEM_CONFIG.getMaxDamage(key);
        if (maxDamage > 0) {
            itemStack.setData(DataComponentTypes.MAX_DAMAGE, maxDamage);
            itemStack.setData(DataComponentTypes.DAMAGE, 0);
        }

        // Repair Cost
        this.repairCost = ITEM_CONFIG.getRepairCost(key);
        if (this.repairCost > 0) {
            itemStack.setData(DataComponentTypes.REPAIR_COST, this.repairCost);
        }

        // Repair Percent
        this.repairPercent = ITEM_CONFIG.getRepairPercent(key);
        this.itemStack = itemStack;
        Items.ALL_ITEMS.put(this.key, this);
    }

    @SuppressWarnings("NullableProblems")
    protected RegistryKeySet<BlockType> getBlockTag(TagKey<BlockType> tagKey) {
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.BLOCK).getTag(tagKey);
    }

    @SuppressWarnings("NullableProblems")
    protected RegistryKeySet<BlockType> getBlockTag(Tag<Material> tag) {
        TagKey<BlockType> tagKey = TagKey.create(RegistryKey.BLOCK, tag.key());
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.BLOCK).getTag(tagKey);
    }

    /**
     * Get the recipe of this item
     *
     * @return Recipe of this item if registered
     */
    public @Nullable Recipe getRecipe() {
        return null;
    }

    /**
     * Check if an {@link ItemStack} matches this item
     *
     * @param itemStack ItemStack to compare
     * @return True if the item matches
     */
    public boolean is(ItemStack itemStack) {
        if (itemStack.hasData(DataComponentTypes.ITEM_MODEL)) {
            Key data = itemStack.getData(DataComponentTypes.ITEM_MODEL);
            return data != null && data.equals(this.key);
        }
        return false;
    }

    /**
     * Get the repair percent of this item
     * <p>This is used to determine output durability during repairs</p>
     *
     * @return Repair percent of item
     */
    public double getRepairPercent() {
        return this.repairPercent;
    }

    /**
     * Get the repair cost of this item
     *
     * @return Repair cost of item
     */
    public int getRepairCost() {
        return this.repairCost;
    }

    /**
     * Get the {@link Key} of this item
     *
     * @return Key of this item
     */
    public Key getKey() {
        return this.key;
    }

    /**
     * Get the name of this item
     *
     * @return Name of item
     */
    public @Nullable String getName() {
        return this.name;
    }

}
