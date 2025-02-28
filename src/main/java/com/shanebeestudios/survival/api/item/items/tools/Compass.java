package com.shanebeestudios.survival.api.item.items.tools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import com.shanebeestudios.survival.api.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class Compass extends Item {

    public Compass() {
        ItemStack itemStack = ItemType.COMPASS.createItemStack();
        setupDefaults("compass", itemStack, true);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape(" i ", "iri", " i ");
        recipe.setIngredient('i', Material.IRON_INGOT);
        recipe.setIngredient('r', Material.REDSTONE);
        return recipe;
    }

}
