package com.shanebeestudios.survival.api.item.items.legendary;

import com.shanebeestudios.survival.api.registry.Enchantments;
import com.shanebeestudios.survival.api.item.Item;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

@SuppressWarnings("UnstableApiUsage")
public class ObsidianMace extends Item {

    public ObsidianMace() {
        ItemStack itemStack = ItemType.MACE.createItemStack();

        itemStack.setData(DataComponentTypes.ENCHANTMENTS, ItemEnchantments.itemEnchantments()
            .add(Enchantments.OBSIDIAN_POWER, 1)
            .add(Enchantment.KNOCKBACK, 3)
            .add(Enchantment.UNBREAKING, 5)
            .add(Enchantment.BINDING_CURSE, 1)
            .build());

        itemStack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes()
            .addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE, 6f, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND))
            .addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED, -3.2f, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND))
            .addModifier(Attribute.KNOCKBACK_RESISTANCE, new AttributeModifier(NamespacedKey.minecraft("base_attack_resistance"), 0.5f, Operation.ADD_SCALAR, EquipmentSlotGroup.HAND))
            .addModifier(Attribute.MOVEMENT_SPEED, new AttributeModifier(BASE_MOVEMENT_SPEED, -0.2, Operation.ADD_SCALAR, EquipmentSlotGroup.HAND))
            .build());

        itemStack.setData(DataComponentTypes.RARITY, ItemRarity.EPIC);
        setupDefaults("obsidian_mace", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape(" oo", " eo", "s  ");
        recipe.setIngredient('o', Material.OBSIDIAN);
        recipe.setIngredient('e', Material.END_CRYSTAL);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

}
