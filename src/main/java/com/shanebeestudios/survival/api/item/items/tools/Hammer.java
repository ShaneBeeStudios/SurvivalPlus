package com.shanebeestudios.survival.api.item.items.tools;

import com.shanebeestudios.survival.api.item.Item;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Enchantable;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.datacomponent.item.Tool;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class Hammer extends Item {

    @SuppressWarnings("UnstableApiUsage")
    public Hammer() {
        ItemStack itemStack = ItemType.STICK.createItemStack();

        itemStack.setData(DataComponentTypes.TOOL, Tool.tool()
            .defaultMiningSpeed(0.0001f)
            .build());

        itemStack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes()
            .addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE, 2.5d, Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND))
            .addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED, -3.0d, Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND))
            .build());

        itemStack.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        itemStack.setData(DataComponentTypes.ENCHANTABLE, Enchantable.enchantable(1));

        setupDefaults("hammer", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("c ", "sc");
        recipe.setIngredient('c', Material.COBBLESTONE);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

}
