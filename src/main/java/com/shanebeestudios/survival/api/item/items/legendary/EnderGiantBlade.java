package com.shanebeestudios.survival.api.item.items.legendary;

import com.shanebeestudios.survival.api.registry.Enchantments;
import com.shanebeestudios.survival.api.item.Item;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import io.papermc.paper.datacomponent.item.UseCooldown;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

@SuppressWarnings("UnstableApiUsage")
public class EnderGiantBlade extends Item {

    private final double chargeDamage;

    public EnderGiantBlade() {
        String key = "ender_giant_blade";
        ItemStack itemStack = ItemType.DIAMOND_SWORD.createItemStack();

        itemStack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes()
            .addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE, 8f, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND))
            .addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED, -2.5f, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND))
            .addModifier(Attribute.MOVEMENT_SPEED, new AttributeModifier(BASE_MOVEMENT_SPEED, -0.5f, Operation.ADD_SCALAR, EquipmentSlotGroup.OFFHAND))
            .build());
        itemStack.setData(DataComponentTypes.ENCHANTMENTS, ItemEnchantments.itemEnchantments()
            .add(Enchantments.ENDER_POWER, 3)
            .build());

        itemStack.setData(DataComponentTypes.USE_COOLDOWN, UseCooldown.useCooldown(10)
            .cooldownGroup(Key.key("survival_plus:" + key)).build());
        this.chargeDamage = ITEM_CONFIG.getDouble(key, "charge_damage", 3);
        setupDefaults(key, itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape(" dd", "Ded", "pD ");

        recipe.setIngredient('e', Material.ENDER_EYE);
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('p', new RecipeChoice.MaterialChoice(Tag.PLANKS));
        return recipe;
    }

    public double getChargeDamage() {
        return this.chargeDamage;
    }

}
