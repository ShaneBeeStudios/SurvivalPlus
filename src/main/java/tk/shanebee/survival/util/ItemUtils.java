package tk.shanebee.survival.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Utility methods for {@link ItemStack ItemStacks}
 */
public class ItemUtils {

    /**
     * Set the durability of an ItemStack
     *
     * @param item       The ItemStack to set
     * @param durability The durability to set
     */
    public static void setDurability(ItemStack item, int durability) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        ((Damageable) meta).setDamage(durability);
        item.setItemMeta(meta);
    }

    /**
     * Check the durability of an ItemStack
     *
     * @param item The ItemStack to check
     * @return The durability of the ItemStack
     */
    public static int getDurability(ItemStack item) {
        assert item.getItemMeta() != null;
        return ((Damageable) item.getItemMeta()).getDamage();
    }

}
