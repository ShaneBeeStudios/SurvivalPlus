package tk.shanebee.survival.item.items.misc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class FermentedSkin extends Item {

    public FermentedSkin() {
        ItemStack itemStack = ItemType.RABBIT_HIDE.createItemStack(); // TODO rotten flesh instead? maybe an apple?!?!
        setupDefaults("fermented_skin", itemStack);

    }

    @Override
    public Recipe getRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(this.recipeKey, this.getItemStack());
        recipe.addIngredient(Material.ROTTEN_FLESH);
        recipe.addIngredient(Material.SUGAR);
        recipe.addIngredient(new RecipeChoice.MaterialChoice(Material.BROWN_MUSHROOM, Material.RED_MUSHROOM));
        return recipe;
    }

}
