package tk.shanebee.survival.item.items.tools;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.ShapedRecipe;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class Hatchet extends Item {

    public Hatchet() {
        ItemStack itemStack = ItemType.WOODEN_AXE.createItemStack();
        itemStack.unsetData(DataComponentTypes.REPAIRABLE);
        setupDefaults("hatchet", itemStack);
    }

    @Override
    public void registerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("ff", " s");
        recipe.setIngredient('f', Material.FLINT);
        recipe.setIngredient('s', Material.STICK);
        Bukkit.addRecipe(recipe);
    }

}
