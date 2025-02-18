package com.shanebeestudios.survival.item.items.tools;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import com.shanebeestudios.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class RecurveCrossbow extends Item {

    public RecurveCrossbow() {
        ItemStack itemStack = ItemType.CROSSBOW.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.PUNCH, 1, true);
        itemStack.setItemMeta(itemMeta);
        setupDefaults("recurve_crossbow", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape(" 12", "342", " 12");
        recipe.setIngredient('1', Material.DIAMOND);
        recipe.setIngredient('2', Material.PHANTOM_MEMBRANE);
        recipe.setIngredient('3', Material.PISTON);
        recipe.setIngredient('4', Material.CROSSBOW);
        return recipe;
    }

}
