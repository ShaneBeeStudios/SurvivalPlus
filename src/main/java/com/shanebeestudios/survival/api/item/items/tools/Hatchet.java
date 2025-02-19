package com.shanebeestudios.survival.api.item.items.tools;

import com.shanebeestudios.survival.api.item.Item;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.datacomponent.item.Tool;
import io.papermc.paper.registry.keys.tags.BlockTypeTagKeys;
import net.kyori.adventure.util.TriState;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

@SuppressWarnings("UnstableApiUsage")
public class Hatchet extends Item {

    public Hatchet() {
        ItemStack itemStack = ItemType.WOODEN_AXE.createItemStack();

        AttributeModifier attackDamage = new AttributeModifier(BASE_ATTACK_DAMAGE, 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        AttributeModifier attackSpeed = new AttributeModifier(BASE_ATTACK_SPEED, -3.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemStack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes()
            .addModifier(Attribute.ATTACK_DAMAGE, attackDamage)
            .addModifier(Attribute.ATTACK_SPEED, attackSpeed)
            .build());

        itemStack.setData(DataComponentTypes.TOOL, Tool.tool()
            .defaultMiningSpeed(0.0001f)
            .addRule(Tool.rule(getBlockTag(BlockTypeTagKeys.INCORRECT_FOR_WOODEN_TOOL), 0.0001f, TriState.FALSE))
            .addRule(Tool.rule(getBlockTag(BlockTypeTagKeys.LOGS), 0.35f, TriState.FALSE))
            .addRule(Tool.rule(getBlockTag(BlockTypeTagKeys.MINEABLE_AXE), 0.75f, TriState.TRUE))
            .build());
        setupDefaults("hatchet", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("ff", " s");
        recipe.setIngredient('f', Material.FLINT);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

}
