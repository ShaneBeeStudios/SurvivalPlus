package tk.shanebee.survival.item.items.drinks;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class WaterBowl extends Item {

    public WaterBowl() {
        ItemStack itemStack = ItemType.POTION.createItemStack();
        PotionMeta itemMeta = ((PotionMeta) itemStack.getItemMeta());
        itemMeta.setBasePotionType(PotionType.WATER);
        itemStack.setItemMeta(itemMeta);
        setupDefaults("water_bowl", itemStack);
    }

}
