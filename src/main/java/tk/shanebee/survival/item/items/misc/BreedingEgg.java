package tk.shanebee.survival.item.items.misc;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class BreedingEgg extends Item {

    public BreedingEgg() {
        ItemStack itemStack = ItemType.EGG.createItemStack();
        setupDefaults("breeding_egg", itemStack);
    }

}
