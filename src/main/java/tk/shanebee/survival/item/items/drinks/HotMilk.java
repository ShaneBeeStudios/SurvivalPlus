package tk.shanebee.survival.item.items.drinks;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import tk.shanebee.survival.item.Item;
import tk.shanebee.survival.item.Items;

public class HotMilk extends Item {

    public HotMilk() {
        ItemStack itemStack = ItemType.POTION.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        ((PotionMeta) itemMeta).setBasePotionType(PotionType.WATER);
        itemMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        itemStack.setItemMeta(itemMeta);
        setupDefaults("hot_milk", itemStack, true);
    }

    @Override
    public void registerRecipe() {
        SmokingRecipe recipe = new SmokingRecipe(this.recipeKey, this.getItemStack(),
            new RecipeChoice.ExactChoice(Items.COLD_MILK.getItemStack()), 0, 200);
        Bukkit.addRecipe(recipe);
    }

}
