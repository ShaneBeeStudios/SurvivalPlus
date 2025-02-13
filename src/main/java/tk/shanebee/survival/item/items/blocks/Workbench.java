package tk.shanebee.survival.item.items.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import tk.shanebee.survival.item.Item;
import tk.shanebee.survival.item.Items;

@SuppressWarnings("UnstableApiUsage")
public class Workbench extends Item {

    public Workbench() {
        ItemStack itemStack = ItemType.CRAFTING_TABLE.createItemStack();
        setupDefaults("workbench", itemStack);
    }

    @Override
    public void registerRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(this.recipeKey, this.getItemStack());

        recipe.addIngredient(new RecipeChoice.MaterialChoice(Tag.LOGS));
        recipe.addIngredient(Material.LEATHER);
        recipe.addIngredient(Material.STRING);
        recipe.addIngredient(Items.HAMMER.getItemStack());
        Bukkit.addRecipe(recipe);
    }

}
