package com.shanebeestudios.survival.item.items.tools;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import com.shanebeestudios.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class Mattock extends Item {

    public Mattock() {
        ItemStack itemStack = ItemType.WOODEN_PICKAXE.createItemStack();
        itemStack.unsetData(DataComponentTypes.REPAIRABLE);
        setupDefaults("mattock", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape("fp", "sf");
        recipe.setIngredient('f', Material.FLINT);
        recipe.setIngredient('p', new RecipeChoice.MaterialChoice(Tag.PLANKS));
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

}
