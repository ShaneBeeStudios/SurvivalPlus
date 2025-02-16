package tk.shanebee.survival.item.items.tools;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class RecurveBow extends Item {

    public RecurveBow() {
        ItemStack itemStack = ItemType.BOW.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.PUNCH, 1, true);
        itemStack.setItemMeta(itemMeta);
        setupDefaults("recurve_bow", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape(" @1", "#^1", " @1");
        recipe.setIngredient('^', Material.BOW);
        recipe.setIngredient('#', Material.PISTON);
        recipe.setIngredient('@', Material.IRON_INGOT);
        recipe.setIngredient('1', Material.STRING);
        return recipe;
    }

}
