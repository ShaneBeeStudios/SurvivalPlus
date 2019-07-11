package tk.shanebee.survival.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.managers.Items;

public class InventoryUpdate implements Listener {

    @EventHandler
    private void onJoinUpdate(PlayerJoinEvent e) { // Update old items to new items
        Inventory inv = e.getPlayer().getInventory();
        if (needsUpdate(inv)) {
            itemCheck(inv);
        }
    }

    @EventHandler
    private void onInventoryOpenUpdate(InventoryOpenEvent e) { // Update old items to new items
        Inventory inv = e.getInventory();
        if (needsUpdate(inv)) {
            itemCheck(inv);
        }
    }

    private void itemCheck(Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            assert item != null;
            if (Items.compare(item, Items.IRON_SICKLE_OLD)) {
                itemUpdate(inv, i, item, Items.IRON_SICKLE);
            } else if (Items.compare(item, Items.QUARTZ_PICKAXE_OLD)) {
                itemUpdate(inv, i, item, Items.QUARTZ_PICKAXE);
            } else if (Items.compare(item, Items.VALKYRIES_AXE_OLD)) {
                itemUpdate(inv, i, item, Items.VALKYRIES_AXE);
            } else if (Items.compare(item, Items.OBSIDIAN_MACE_OLD)) {
                itemUpdate(inv, i, item, Items.OBSIDIAN_MACE);
            } else if (Items.compare(item, Items.ENDER_GIANT_BLADE_OLD)) {
            	itemUpdate(inv, i, item, Items.ENDER_GIANT_BLADE);
			}
        }
    }

    private void itemUpdate(Inventory inv, int slot, ItemStack oldItem, Items newItem) {
        assert oldItem.getItemMeta() != null;
        int damage = ((Damageable) oldItem.getItemMeta()).getDamage();
        ItemStack item = Items.get(newItem);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        ((Damageable) meta).setDamage(damage);
        item.setItemMeta(meta);
        inv.setItem(slot, item);
    }

    private boolean needsUpdate(Inventory inv) {
        return inv.contains(Material.WOODEN_HOE) || inv.contains(Material.GOLDEN_PICKAXE) || inv.contains(Material.GOLDEN_AXE) ||
                inv.contains(Material.GOLDEN_SHOVEL) || inv.contains(Material.GOLDEN_HOE);
    }

}
