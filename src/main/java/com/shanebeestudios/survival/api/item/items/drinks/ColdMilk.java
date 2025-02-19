package com.shanebeestudios.survival.api.item.items.drinks;

import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class ColdMilk extends DrinkItem {

    public ColdMilk() {
        ItemStack itemStack = ItemType.STICK.createItemStack();
        setupDefaults("cold_milk", itemStack, List.of(
            ConsumeEffect.clearAllStatusEffects()
        ));
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape("   ", "12 ", "   ");
        recipe.setIngredient('1', Material.MILK_BUCKET);
        recipe.setIngredient('2', Material.GLASS_BOTTLE);
        return recipe;
    }

}
