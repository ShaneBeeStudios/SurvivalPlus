package tk.shanebee.survival.managers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.item.Item;
import tk.shanebee.survival.item.Items;

import java.util.Map;

/**
 * Manager for custom <b>SurvivalPlus</b> items
 */
@SuppressWarnings({"ConstantConditions", "removal"})
public class ItemManager {

    /**
     * Apply the attributes from an {@link Items} to an existing ItemStack
     *
     * @param itemStack Current ItemStack to apply attributes to
     * @param items     Item to grab data from
     */
    public static void applyAttribute(ItemStack itemStack, Item items) {
        ItemStack from = items.getItemStack();
        ItemMeta metaTo = itemStack.getItemMeta();
        ItemMeta metaFrom = from.getItemMeta();
        Map<Enchantment, Integer> enchants = metaTo.getEnchants();
        for (Enchantment enchantment : enchants.keySet()) {
            metaFrom.addEnchant(enchantment, enchants.get(enchantment), true);
        }
        itemStack.setItemMeta(metaFrom);
    }

}
