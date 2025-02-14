package tk.shanebee.survival.item.items.tools;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class Mattock extends Item {

    public Mattock() {
        ItemStack itemStack = ItemType.WOODEN_PICKAXE.createItemStack();
        itemStack.unsetData(DataComponentTypes.REPAIRABLE);
        setupDefaults("mattock", itemStack);
    }

    @Override
    public void registerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape("fp", "sf");
        recipe.setIngredient('f', Material.FLINT);
        recipe.setIngredient('p', new RecipeChoice.MaterialChoice(Tag.PLANKS));
        recipe.setIngredient('s', Material.STICK);
        Bukkit.addRecipe(recipe);
    }

}
