package com.shanebeestudios.survival.item.items.tools;

import com.shanebeestudios.survival.item.Item;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.datacomponent.item.Tool;
import io.papermc.paper.registry.keys.tags.BlockTypeTagKeys;
import net.kyori.adventure.util.TriState;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import org.bukkit.inventory.ShapedRecipe;

@SuppressWarnings("UnstableApiUsage")
public class Mattock extends Item {

    public Mattock() {
        ItemStack itemStack = ItemType.WOODEN_PICKAXE.createItemStack();

        AttributeModifier attackDamage = new AttributeModifier(BASE_ATTACK_DAMAGE, 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        AttributeModifier attackSpeed = new AttributeModifier(BASE_ATTACK_SPEED, -3.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemStack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes()
            .addModifier(Attribute.ATTACK_DAMAGE, attackDamage)
            .addModifier(Attribute.ATTACK_SPEED, attackSpeed)
            .build());

        itemStack.setData(DataComponentTypes.TOOL, Tool.tool()
            .defaultMiningSpeed(0.0001f)
            .addRule(Tool.rule(getBlockTag(BlockTypeTagKeys.INCORRECT_FOR_WOODEN_TOOL), 0.0001f, TriState.FALSE))
            .addRule(Tool.rule(getBlockTag(BlockTypeTagKeys.BASE_STONE_OVERWORLD), 0.25f, TriState.TRUE))
            .addRule(Tool.rule(getBlockTag(BlockTypeTagKeys.MINEABLE_PICKAXE), 0.75f, TriState.TRUE))
            .build());

        setupDefaults("mattock", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("fp", "sf");
        recipe.setIngredient('f', Material.FLINT);
        recipe.setIngredient('p', new MaterialChoice(Tag.PLANKS));
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

}
