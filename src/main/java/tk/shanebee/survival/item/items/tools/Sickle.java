package tk.shanebee.survival.item.items.tools;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class Sickle extends Item {

    private final Material recipeMaterial;

    public Sickle(String typeKey, Material recipeMaterial) {
        this.recipeMaterial = recipeMaterial;
        ItemStack itemStack = ItemType.WOODEN_HOE.createItemStack();
        itemStack.unsetData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        setupDefaults(typeKey + "_sickle", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape("oof", "  s", " s ");
        recipe.setIngredient('o', this.recipeMaterial);
        recipe.setIngredient('f', Material.FLINT);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

}
