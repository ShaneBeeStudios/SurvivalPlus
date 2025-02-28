package com.shanebeestudios.survival.api.item.items.blocks;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import com.shanebeestudios.survival.api.item.Item;
import com.shanebeestudios.survival.api.item.Items;

@SuppressWarnings("UnstableApiUsage")
public class Workbench extends Item {

    public Workbench() {
        ItemStack itemStack = ItemType.CRAFTING_TABLE.createItemStack();
        setupDefaults("workbench", itemStack, true);
    }

    @Override
    public Recipe getRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(this.recipeKey, this.getItemStack());

        recipe.addIngredient(new RecipeChoice.MaterialChoice(Tag.LOGS));
        recipe.addIngredient(Material.LEATHER);
        recipe.addIngredient(Material.STRING);
        recipe.addIngredient(Items.HAMMER.getItemStack());
        return recipe;
    }

}
