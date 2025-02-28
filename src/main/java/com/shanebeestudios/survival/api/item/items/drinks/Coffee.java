package com.shanebeestudios.survival.api.item.items.drinks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import com.shanebeestudios.survival.api.item.Items;

@SuppressWarnings("UnstableApiUsage")
public class Coffee extends DrinkItem {

    public Coffee() {
        ItemStack itemStack = ItemType.STICK.createItemStack();
        setupDefaults("coffee", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack(2));

        recipe.shape("   ", "12 ", "34 ");
        recipe.setIngredient('1', new RecipeChoice.ExactChoice(Items.COFFEE_BEAN.getItemStack()));
        recipe.setIngredient('2', Material.COCOA_BEANS);
        recipe.setIngredient('3', new RecipeChoice.ExactChoice(Items.HOT_MILK.getItemStack()));
        recipe.setIngredient('4', new RecipeChoice.ExactChoice(Items.PURIFIED_WATER.getItemStack()));
        return recipe;
    }

}
