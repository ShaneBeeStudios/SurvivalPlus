package com.shanebeestudios.survival.api.item.items.tools;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import com.shanebeestudios.survival.api.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class FireStriker extends Item {

    private final int maxCooks;

    public FireStriker() {
        ItemStack itemStack = ItemType.STICK.createItemStack();
        this.maxCooks = ITEM_CONFIG.getInt("firestriker", "max_cooks", 8);
        itemStack.setData(DataComponentTypes.MAX_DAMAGE, this.maxCooks);
        itemStack.setData(DataComponentTypes.DAMAGE, 0);
        itemStack.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemStack.setItemMeta(itemMeta);
        setupDefaults("firestriker", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(this.recipeKey, this.getItemStack());
        recipe.addIngredient(Material.FLINT);
        recipe.addIngredient(new RecipeChoice.MaterialChoice(Tag.ITEMS_COALS));
        return recipe;
    }

    public int getMaxCooks() {
        return this.maxCooks;
    }

}
