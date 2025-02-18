package com.shanebeestudios.survival.item.items.tools;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import com.shanebeestudios.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class Hatchet extends Item {

    public Hatchet() {
        ItemStack itemStack = ItemType.WOODEN_AXE.createItemStack();
        itemStack.unsetData(DataComponentTypes.REPAIRABLE);
        setupDefaults("hatchet", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("ff", " s");
        recipe.setIngredient('f', Material.FLINT);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

}
