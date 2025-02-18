package com.shanebeestudios.survival.item.items.tools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

@SuppressWarnings("UnstableApiUsage")
public class RecurvedCrossbow extends RecurvedBow {

    public RecurvedCrossbow() {
        super("recurved_crossbow", ItemType.CROSSBOW.createItemStack());
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape(" dm", "pcm", " dm");
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('m', Material.PHANTOM_MEMBRANE);
        recipe.setIngredient('p', Material.PISTON);
        recipe.setIngredient('c', Material.CROSSBOW);
        return recipe;
    }

}
