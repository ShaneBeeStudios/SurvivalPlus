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
public class RainBoots extends Item {

    public RainBoots() {
        ItemStack itemStack = ItemType.LEATHER_BOOTS.createItemStack();
        itemStack.setData(DataComponentTypes.DYED_COLOR,
            DyedItemColor.dyedItemColor(Color.fromRGB(214, 231, 3),
                false));
        setupDefaults("rain_boots", itemStack, true);

    }

    @Override
    public void registerRecipe() {
        ShapedRecipe rainBoots = new ShapedRecipe(this.recipeKey, this.getItemStack());
        rainBoots.shape("ili");
        rainBoots.setIngredient('i', Material.IRON_INGOT);
        rainBoots.setIngredient('l', Material.LEATHER_BOOTS);
        Bukkit.addRecipe(rainBoots);

    }

}
