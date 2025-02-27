package com.shanebeestudios.survival.api.item.items.tools;

import com.shanebeestudios.survival.api.item.Item;
import com.shanebeestudios.survival.api.registry.BlockTags;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.datacomponent.item.Tool;
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
public class Sickle extends Item {

    private final Material recipeMaterial;

    public Sickle(String typeKey, Material recipeMaterial) {
        this.recipeMaterial = recipeMaterial;
        ItemStack itemStack = ItemType.WOODEN_HOE.createItemStack();

        double damage = switch (typeKey) {
            case "stone" -> 0;
            case "iron" -> 1.0;
            case "diamond" -> 2.0;
            default -> -0.5;
        };

        AttributeModifier attackDamage = new AttributeModifier(BASE_ATTACK_DAMAGE, damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        AttributeModifier attackSpeed = new AttributeModifier(BASE_ATTACK_SPEED, -3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);

        itemStack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes()
            .addModifier(Attribute.ATTACK_DAMAGE, attackDamage)
            .addModifier(Attribute.ATTACK_SPEED, attackSpeed)
            .build());

        itemStack.setData(DataComponentTypes.TOOL, Tool.tool()
            .defaultMiningSpeed(0.0001f)
            .addRule(Tool.rule(getBlockTag(BlockTags.REQUIRES_SICKLE), 1.0f, TriState.TRUE))
            .build());

        setupDefaults(typeKey + "_sickle", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape("oof", "  s", " s ");
        recipe.setIngredient('o', this.recipeMaterial);
        recipe.setIngredient('f', Material.FLINT);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

}
