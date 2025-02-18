package com.shanebeestudios.survival.item.items.armor;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import com.shanebeestudios.survival.item.Item;
import com.shanebeestudios.survival.item.items.armor.ArmorPiece.ArmorMaterial;
import com.shanebeestudios.survival.item.items.armor.ArmorPiece.ArmorType;

@SuppressWarnings("UnstableApiUsage")
public class BeekeeperPiece extends Item {

    private final ArmorType armorType;

    public BeekeeperPiece(ArmorType armorType) {
        this.armorType = armorType;
        String key = "beekeeper_" + armorType.getKey();
        ItemStack itemStack = ArmorMaterial.LEATHER.getItemType(armorType).createItemStack();
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta) {
            int color = ITEM_CONFIG.getColor(key);
            if (color > 0) {
                leatherArmorMeta.setColor(Color.fromRGB(color));
                leatherArmorMeta.addItemFlags(ItemFlag.HIDE_DYE);
                itemStack.setItemMeta(leatherArmorMeta);
            }
        }
        setupDefaults(key, itemStack);
    }

    @Override
    public Recipe getRecipe() {
        switch (this.armorType) {
            case HELMET -> {
                ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
                recipe.shape("121", "3 3", "   ");
                recipe.setIngredient('1', Material.HONEYCOMB);
                recipe.setIngredient('2', Material.IRON_INGOT);
                recipe.setIngredient('3', Material.LEATHER);
                return recipe;
            }
            case CHESTPLATE -> {
                ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
                recipe.shape("1 1", "232", "323");
                recipe.setIngredient('1', Material.HONEYCOMB);
                recipe.setIngredient('2', Material.IRON_INGOT);
                recipe.setIngredient('3', Material.LEATHER);
                return recipe;
            }
            case LEGGINGS -> {
                ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
                recipe.shape("131", "3 3", "2 2");
                recipe.setIngredient('1', Material.HONEYCOMB);
                recipe.setIngredient('2', Material.IRON_INGOT);
                recipe.setIngredient('3', Material.LEATHER);
                return recipe;
            }
            case BOOTS -> {
                ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
                recipe.shape("   ", "1 1", "3 3");
                recipe.setIngredient('1', Material.HONEYCOMB);
                recipe.setIngredient('3', Material.LEATHER);
                return recipe;
            }
        }
        return null;
    }

}
