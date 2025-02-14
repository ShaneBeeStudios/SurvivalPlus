package tk.shanebee.survival.item.items.drinks;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class ColdMilk extends Item {

    public ColdMilk() {
        ItemStack itemStack = ItemType.POTION.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        ((PotionMeta) itemMeta).setBasePotionType(PotionType.WATER);
        ((PotionMeta) itemMeta).setColor(Color.fromRGB(ITEM_CONFIG.getColor("cold_milk")));
        itemMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        itemStack.setItemMeta(itemMeta);
        setupDefaults("cold_milk", itemStack);
    }

    @Override
    public void registerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape("   ", "12 ", "   ");
        recipe.setIngredient('1', Material.MILK_BUCKET);
        recipe.setIngredient('2', Material.GLASS_BOTTLE);
        Bukkit.addRecipe(recipe);
    }

}
