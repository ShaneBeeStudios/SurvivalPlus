package com.shanebeestudios.survival.item.items.tools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import com.shanebeestudios.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class GrapplingHook extends Item {

    public GrapplingHook() {
        ItemStack itemStack = ItemType.FISHING_ROD.createItemStack();
        setupDefaults("grappling_hook", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape(" i ", "fsf", " i ");
        recipe.setIngredient('f', Material.FISHING_ROD);
        recipe.setIngredient('s', Material.STRING);
        recipe.setIngredient('i', Material.IRON_INGOT);
        return recipe;
    }

}
