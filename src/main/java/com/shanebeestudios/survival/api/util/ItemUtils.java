package com.shanebeestudios.survival.api.util;

import com.shanebeestudios.survival.api.item.Item;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Random;

/**
 * Utility methods for {@link ItemStack ItemStacks}
 */
@SuppressWarnings("UnstableApiUsage")
public class ItemUtils {

    private static final Random RANDOM = new Random();

    /**
     * Check the durability of an ItemStack
     *
     * @param item The ItemStack to check
     * @return The durability of the ItemStack
     */
    public static int getDurability(ItemStack item) {
        if (item.hasData(DataComponentTypes.MAX_DAMAGE) && item.hasData(DataComponentTypes.DAMAGE)) {
            Integer maxDamage = item.getData(DataComponentTypes.MAX_DAMAGE);
            Integer damage = item.getData(DataComponentTypes.DAMAGE);
            assert maxDamage != null;
            assert damage != null;
            return maxDamage - damage;
        }
        return 0;
    }

    /**
     * Get the max damage of an ItemStack
     *
     * @param item Item to get max from
     * @return Max damage of item
     */
    public static int getMaxDamage(ItemStack item) {
        if (item.hasData(DataComponentTypes.MAX_DAMAGE)) {
            Integer maxDamage = item.getData(DataComponentTypes.MAX_DAMAGE);
            return maxDamage == null ? 0 : maxDamage;
        }
        return 0;
    }

    public static void damageItem(Player player, ItemStack item, int damage) {
        player.damageItemStack(item, damage);
        if (getDurability(item) <= 0) {
            item.setAmount(0);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
        }
    }

    @SuppressWarnings({"UnstableApiUsage", "deprecation"})
    public static String getItemName(ItemStack itemStack) {
        if (itemStack.hasData(DataComponentTypes.CUSTOM_NAME)) {
            Component data = itemStack.getData(DataComponentTypes.CUSTOM_NAME);
            if (data != null) return Utils.reverseComponent(data);
        } else if (itemStack.hasData(DataComponentTypes.ITEM_NAME)) {
            Component data = itemStack.getData(DataComponentTypes.ITEM_NAME);
            if (data != null) return Utils.reverseComponent(data);
        }
        return itemStack.getItemMeta().getDisplayName();
    }

    @SuppressWarnings("UnstableApiUsage")
    public static Component getItemNameComponent(ItemStack itemStack) {
        if (itemStack.hasData(DataComponentTypes.CUSTOM_NAME)) {
            Component data = itemStack.getData(DataComponentTypes.CUSTOM_NAME);
            if (data != null) return data;
        } else if (itemStack.hasData(DataComponentTypes.ITEM_NAME)) {
            Component data = itemStack.getData(DataComponentTypes.ITEM_NAME);
            if (data != null) return data;
        }
        return itemStack.getItemMeta().displayName();
    }

    /**
     * Apply the enchantments from an {@link Item} to an existing ItemStack
     *
     * @param itemStack Current ItemStack to apply enchantments to
     * @param item      Item to grab data from
     */
    public static void applyEnchantments(ItemStack itemStack, Item item) {
        ItemStack from = item.getItemStack();
        ItemMeta metaTo = itemStack.getItemMeta();
        ItemMeta metaFrom = from.getItemMeta();
        Map<Enchantment, Integer> enchants = metaTo.getEnchants();
        for (Enchantment enchantment : enchants.keySet()) {
            metaFrom.addEnchant(enchantment, enchants.get(enchantment), true);
        }
        itemStack.setItemMeta(metaFrom);
    }

}
