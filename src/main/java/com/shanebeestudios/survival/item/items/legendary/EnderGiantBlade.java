package com.shanebeestudios.survival.item.items.legendary;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import com.shanebeestudios.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class EnderGiantBlade extends Item {

    public EnderGiantBlade() {
        ItemStack itemStack = ItemType.DIAMOND_SWORD.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        int gHoe_dmg = 8;
        int gHoe_spd = 1;
        float gHoe_move = -0.5f;

        AttributeModifier i_gHoeDamage = new AttributeModifier(BASE_ATTACK_DAMAGE, gHoe_dmg - 1, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_DAMAGE, i_gHoeDamage);

        AttributeModifier i_gHoeSpeed = new AttributeModifier(BASE_ATTACK_SPEED, gHoe_spd - 4, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_SPEED, i_gHoeSpeed);

        AttributeModifier i_gHoeMove = new AttributeModifier(BASE_MOVEMENT_SPEED, gHoe_move, Operation.ADD_SCALAR, EquipmentSlotGroup.OFFHAND);
        itemMeta.addAttributeModifier(Attribute.MOVEMENT_SPEED, i_gHoeMove);

        itemMeta.addEnchant(Enchantment.UNBREAKING, 5, true);
        itemStack.setItemMeta(itemMeta);

        itemStack.unsetData(DataComponentTypes.REPAIRABLE);
        setupDefaults("ender_giant_blade", itemStack);
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

}
