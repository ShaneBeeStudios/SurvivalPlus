package tk.shanebee.survival.item.items.tools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class MedicKit extends Item {

    public MedicKit() {
        ItemStack itemStack = ItemType.CLOCK.createItemStack();
        setupDefaults("medic_kit", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape(" g ", "fgp", " g ");

        recipe.setIngredient('g', Material.GOLD_INGOT);
        recipe.setIngredient('f', Material.FEATHER);
        recipe.setIngredient('g', Material.GLISTERING_MELON_SLICE);
        recipe.setIngredient('p', Material.PAPER);
        return recipe;
    }

}
