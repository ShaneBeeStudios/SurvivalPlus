package com.shanebeestudios.survival.api.item.items.tools;

import com.shanebeestudios.survival.api.item.Item;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Tool;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Hammer extends Item {

    @SuppressWarnings("UnstableApiUsage")
    public Hammer() {
        ItemStack itemStack = ItemType.WOODEN_AXE.createItemStack();
        ItemMeta hammerMeta = itemStack.getItemMeta();
        itemStack.setItemMeta(hammerMeta);

        itemStack.setData(DataComponentTypes.TOOL, Tool.tool()
            .defaultMiningSpeed(0.0001f)
            .build());

        setupDefaults("hammer", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("c ", "sc");
        recipe.setIngredient('c', Material.COBBLESTONE);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

}
