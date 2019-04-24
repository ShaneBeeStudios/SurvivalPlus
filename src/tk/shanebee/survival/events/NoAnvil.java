package tk.shanebee.survival.events;

import tk.shanebee.survival.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import tk.shanebee.survival.Survival;

public class NoAnvil implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Inventory inv = e.getInventory();

		if (inv instanceof AnvilInventory) {
			AnvilInventory anvil = (AnvilInventory) inv;
			InventoryView view = e.getView();
			int rawSlot = e.getRawSlot();

			// compare raw slot to the inventory view to make sure we are in the upper inventory
			if (rawSlot == view.convertSlot(rawSlot)) {
				// 2 = result slot
				if (rawSlot == 2) {
					// item in the left slot
					ItemStack item = anvil.getContents()[0];

					if (item != null) {
						if (item.getType() == Material.GOLDEN_AXE
								|| item.getType() == Material.GOLDEN_PICKAXE
								|| item.getType() == Material.GOLDEN_SHOVEL
								|| item.getType() == Material.GOLDEN_HOE
								|| item.getType() == Material.GOLDEN_SWORD
								|| item.getType() == Material.WOODEN_AXE
								|| item.getType() == Material.WOODEN_PICKAXE
								|| item.getType() == Material.WOODEN_SHOVEL
								|| item.getType() == Material.WOODEN_HOE
								|| item.getType() == Material.WOODEN_SWORD) {
							e.setCancelled(true);
							e.getWhoClicked().closeInventory();
							e.getWhoClicked().sendMessage(ChatColor.RED + Utils.getColoredString(Survival.lang.no_rename) + item.getItemMeta().getDisplayName() + ChatColor.RED + Utils.getColoredString(Survival.lang.period));
						}
					}
				}
			}
		}
	}

}