package com.shanebeestudios.survival.item.items.tools;

import com.shanebeestudios.survival.item.Item;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

@SuppressWarnings("UnstableApiUsage")
public class Shiv extends Item {

    public Shiv() {
        ItemStack itemStack = ItemType.WOODEN_SWORD.createItemStack();

        AttributeModifier attackDamage = new AttributeModifier(BASE_ATTACK_DAMAGE, 3, Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        AttributeModifier attackSpeed = new AttributeModifier(BASE_ATTACK_SPEED, -2.2, Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);

        itemStack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes()
            .addModifier(Attribute.ATTACK_DAMAGE, attackDamage)
            .addModifier(Attribute.ATTACK_SPEED, attackSpeed)
            .build());

        setupDefaults("shiv", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("*f", "se");
        recipe.setIngredient('f', Material.FLINT);
        recipe.setIngredient('s', Material.STICK);
        recipe.setIngredient('*', Material.STRING);
        recipe.setIngredient('e', Material.SPIDER_EYE);
        return recipe;
    }

}
