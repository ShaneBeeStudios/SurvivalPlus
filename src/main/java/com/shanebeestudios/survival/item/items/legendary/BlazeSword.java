package com.shanebeestudios.survival.item.items.legendary;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import com.shanebeestudios.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class BlazeSword extends Item {

    public BlazeSword() {
        ItemStack itemStack = ItemType.DIAMOND_SWORD.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        int gSword_dmg = 6;
        float gSword_spd = 1.6f;
        int gSword_health = -6;

        AttributeModifier i_gSwordDamage = new AttributeModifier(BASE_ATTACK_DAMAGE, gSword_dmg - 1, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_DAMAGE, i_gSwordDamage);

        AttributeModifier i_gSwordSpeed = new AttributeModifier(BASE_ATTACK_SPEED, gSword_spd - 4, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_SPEED, i_gSwordSpeed);

        AttributeModifier i_gSwordHealth = new AttributeModifier(NamespacedKey.minecraft("base_max_health"), gSword_health, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.MAX_HEALTH, i_gSwordHealth);

        itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
        itemMeta.addEnchant(Enchantment.UNBREAKING, 3, false);
        itemStack.setItemMeta(itemMeta);

        itemStack.unsetData(DataComponentTypes.REPAIRABLE);
        setupDefaults("blaze_sword", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe blaze_sword = new ShapedRecipe(this.recipeKey, this.getItemStack());
        blaze_sword.shape("BgB", "BgB", "BbB");

        blaze_sword.setIngredient('g', Material.GOLD_INGOT);
        blaze_sword.setIngredient('b', Material.BLAZE_ROD);
        blaze_sword.setIngredient('B', Material.BLAZE_POWDER);
        return blaze_sword;
    }

}
