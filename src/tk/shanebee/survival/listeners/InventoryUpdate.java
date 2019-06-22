package tk.shanebee.survival.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.managers.Items;

class InventoryUpdate implements Listener {

    @EventHandler
    private void onJoinUpdate(PlayerJoinEvent e) { // Update old iron sickles to new iron sickles
        Player player = e.getPlayer();
        if (player.getInventory().contains(Material.WOODEN_HOE)) {
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack item = player.getInventory().getItem(i);
                if (item == null || item.getType() != Material.WOODEN_HOE) continue;
                if (Items.compare(item, Items.IRON_SICKLE_OLD)) {
                    assert item.getItemMeta() != null;
                    int damage = ((Damageable) item.getItemMeta()).getDamage();

                    ItemStack newSickle = new ItemStack(Items.get(Items.IRON_SICKLE));
                    ItemMeta meta = newSickle.getItemMeta();
                    assert meta != null;
                    ((Damageable) meta).setDamage(damage);
                    newSickle.setItemMeta(meta);

                    player.getInventory().setItem(i, newSickle);
                }
            }
        }
    }

    @EventHandler
    private void onInventoryOpenUpdate(InventoryOpenEvent e) { // Update old iron sickles to new iron sickles
        if (e.getInventory().contains(Material.WOODEN_HOE)) {
            for (int i = 0; i < e.getInventory().getSize(); i++) {
                ItemStack item = e.getInventory().getItem(i);
                if (item == null || item.getType() != Material.WOODEN_HOE) continue;
                if (Items.compare(item, Items.IRON_SICKLE_OLD)) {
                    assert item.getItemMeta() != null;
                    int damage = ((Damageable) item.getItemMeta()).getDamage();

                    ItemStack newSickle = new ItemStack(Items.get(Items.IRON_SICKLE));
                    ItemMeta meta = newSickle.getItemMeta();
                    assert meta != null;
                    ((Damageable) meta).setDamage(damage);
                    newSickle.setItemMeta(meta);

                    e.getInventory().setItem(i, newSickle);
                }
            }
        }
    }

}
