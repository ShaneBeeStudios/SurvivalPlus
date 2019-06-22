package tk.shanebee.survival.listeners;

import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import tk.shanebee.survival.Survival;

class NoAnvil implements Listener {

	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
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
						if (Items.compare(item, Items.VALKYRIES_AXE)
								|| Items.compare(item, Items.QUARTZ_PICKAXE) || Items.compare(item, Items.OBSIDIAN_MACE)
								|| Items.compare(item, Items.ENDER_GIANT_BLADE) || Items.compare(item, Items.BLAZE_SWORD)
								|| Items.compare(item, Items.HATCHET) || Items.compare(item, Items.MATTOCK)
								|| Items.compare(item, Items.FIRESTRIKER) || Items.compare(item, Items.SHIV)
								|| Items.compare(item, Items.HAMMER)) {
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