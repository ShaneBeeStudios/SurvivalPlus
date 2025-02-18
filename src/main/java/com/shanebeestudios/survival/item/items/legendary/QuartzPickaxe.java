package com.shanebeestudios.survival.item.items.legendary;

import io.papermc.paper.datacomponent.DataComponentTypes;
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
import org.bukkit.inventory.meta.ItemMeta;
import com.shanebeestudios.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class QuartzPickaxe extends Item {

    public QuartzPickaxe() {
        ItemStack itemStack = ItemType.DIAMOND_PICKAXE.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        int gPickaxe_dmg = 5;
        float gPickaxe_spd = 0.8f;

        AttributeModifier i_gPickDamage = new AttributeModifier(BASE_ATTACK_DAMAGE, gPickaxe_dmg - 1, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_DAMAGE, i_gPickDamage);

        AttributeModifier i_gPickSpeed = new AttributeModifier(BASE_ATTACK_SPEED, gPickaxe_spd - 4, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_SPEED, i_gPickSpeed);

        itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
        itemMeta.addEnchant(Enchantment.MENDING, 1, false);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
        itemStack.setItemMeta(itemMeta);

        itemStack.unsetData(DataComponentTypes.REPAIRABLE);
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
