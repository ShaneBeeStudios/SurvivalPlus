package tk.shanebee.survival.item.items.misc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmokingRecipe;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class CoffeeBean extends Item {

    public CoffeeBean() {
        ItemStack itemStack = ItemType.COCOA_BEANS.createItemStack();
        setupDefaults("coffee_bean", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        return new SmokingRecipe(this.recipeKey, this.getItemStack(),
            Material.COCOA_BEANS, 0, 200);
    }

}
