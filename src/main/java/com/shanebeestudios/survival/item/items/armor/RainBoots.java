package com.shanebeestudios.survival.item.items.armor;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DyedItemColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import com.shanebeestudios.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class RainBoots extends Item {

    public RainBoots() {
        ItemStack itemStack = ItemType.LEATHER_BOOTS.createItemStack();
        itemStack.setData(DataComponentTypes.DYED_COLOR,
            DyedItemColor.dyedItemColor(Color.fromRGB(214, 231, 3),
                false));
        setupDefaults("rain_boots", itemStack);

    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("ili");
        recipe.setIngredient('i', Material.IRON_INGOT);
        recipe.setIngredient('l', Material.LEATHER_BOOTS);
        return recipe;
    }

}
