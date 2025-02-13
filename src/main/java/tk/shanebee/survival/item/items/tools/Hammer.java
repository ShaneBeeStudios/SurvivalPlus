package tk.shanebee.survival.item.items.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.item.Item;

public class Hammer extends Item {

    @SuppressWarnings("UnstableApiUsage")
    public Hammer() {
        ItemStack itemStack = ItemType.WOODEN_SWORD.createItemStack();
        ItemMeta hammerMeta = itemStack.getItemMeta();
        itemStack.setItemMeta(hammerMeta);
        setupDefaults("hammer", itemStack);
    }

    @Override
    public void registerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("c ", "sc");
        recipe.setIngredient('c', Material.COBBLESTONE);
        recipe.setIngredient('s', Material.STICK);
        Bukkit.addRecipe(recipe);
    }

}
