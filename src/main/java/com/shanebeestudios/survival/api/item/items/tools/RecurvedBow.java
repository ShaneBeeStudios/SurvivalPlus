package com.shanebeestudios.survival.api.item.items.tools;

import com.shanebeestudios.survival.api.item.Item;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("UnstableApiUsage")
public class RecurvedBow extends Item {

    public RecurvedBow() {
        this("recurved_bow", ItemType.BOW.createItemStack());
    }

    public RecurvedBow(String key, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.PUNCH, 1, true);
        itemStack.setItemMeta(itemMeta);
        setupDefaults(key, itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape(" is", "pbs", " is");
        recipe.setIngredient('b', Material.BOW);
        recipe.setIngredient('p', Material.PISTON);
        recipe.setIngredient('i', Material.IRON_INGOT);
        recipe.setIngredient('s', Material.STRING);
        return recipe;
    }

}
