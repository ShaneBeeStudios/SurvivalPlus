package com.shanebeestudios.survival.api.item.items.legendary;

import com.shanebeestudios.survival.api.data.Enchantments;
import com.shanebeestudios.survival.api.item.Item;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

@SuppressWarnings("UnstableApiUsage")
public class QuartzPickaxe extends Item {

    public QuartzPickaxe() {
        ItemStack itemStack = ItemType.DIAMOND_PICKAXE.createItemStack();

        itemStack.setData(DataComponentTypes.ENCHANTMENTS, ItemEnchantments.itemEnchantments()
            .add(Enchantment.SILK_TOUCH, 1)
            .add(Enchantments.QUARTZ_MINING, 1)
            .build());

        itemStack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes()
            .addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE, 2.0, Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND))
            .addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED, -3.0, Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND))
            .build());
        setupDefaults("quartz_pickaxe", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("qDd", "De ", "d s");

        recipe.setIngredient('q', Material.QUARTZ_BLOCK);
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('s', Material.STICK);
        recipe.setIngredient('e', Material.DRAGON_EGG);
        return recipe;
    }

}
