package tk.shanebee.survival.item.items.drinks;

import org.bukkit.Color;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class Water extends Item {

    public Water(String key) {
        ItemStack itemStack = ItemType.POTION.createItemStack();

        ItemMeta itemMeta = itemStack.getItemMeta();
        ((PotionMeta) itemMeta).setBasePotionType(PotionType.WATER);
        ((PotionMeta) itemMeta).setColor(Color.fromRGB(ITEM_CONFIG.getColor(key)));
        itemMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        itemStack.setItemMeta(itemMeta);
        setupDefaults(key, itemStack, true);
    }

}
