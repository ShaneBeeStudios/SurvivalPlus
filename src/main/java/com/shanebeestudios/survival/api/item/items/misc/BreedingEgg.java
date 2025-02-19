package com.shanebeestudios.survival.api.item.items.misc;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import com.shanebeestudios.survival.api.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class BreedingEgg extends Item {

    public BreedingEgg() {
        ItemStack itemStack = ItemType.EGG.createItemStack();
        setupDefaults("breeding_egg", itemStack);
    }

}
