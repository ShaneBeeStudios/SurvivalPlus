package tk.shanebee.survival.item.items.armor;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DyedItemColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.ShapedRecipe;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class SnowBoots extends Item {

    public SnowBoots() {
        ItemStack itemStack = ItemType.LEATHER_BOOTS.createItemStack();
        itemStack.setData(DataComponentTypes.DYED_COLOR,
            DyedItemColor.dyedItemColor(Color.fromRGB(158, 201, 202),
                false));
        setupDefaults("snow_boots", itemStack);
    }

    @Override
    public void registerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("dld");
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('l', Material.LEATHER_BOOTS);
        Bukkit.addRecipe(recipe);
    }

}
