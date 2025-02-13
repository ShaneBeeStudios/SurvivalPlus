package tk.shanebee.survival.item.items.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.ShapedRecipe;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class Compass extends Item {

    public Compass() {
        ItemStack itemStack = ItemType.COMPASS.createItemStack();
        setupDefaults("compass", itemStack);
    }

    @Override
    public void registerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape(" i ", "iri", " i ");
        recipe.setIngredient('i', Material.IRON_INGOT);
        recipe.setIngredient('r', Material.REDSTONE);
        Bukkit.addRecipe(recipe);
    }

}
