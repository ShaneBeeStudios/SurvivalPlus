package tk.shanebee.survival.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.Nutrition;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class NutritionGUI implements InventoryHolder, Listener {

	private final Inventory inv;
	private Lang lang;

	public NutritionGUI(Survival plugin) {
		this.lang = plugin.getLang();
		this.inv = Bukkit.createInventory(this, 9 * 5, Utils.getColoredString(lang.nutrition_gui));
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@NotNull
	@Override
	public Inventory getInventory() {
		return inv;
	}

	private void initializeItems() {
		int i = 0;
		for (Nutrition nutrition : Nutrition.values()) {
			ItemStack item = new ItemStack(nutrition.getMaterial());
			ItemMeta meta = item.getItemMeta();
			assert meta != null;

			List<String> lore = new ArrayList<>();
			lore.add(" ");
			lore.add(Utils.getColoredString("&2" + lang.carbohydrates + ": &7" + nutrition.getCarbs()));
			lore.add(Utils.getColoredString("&4" + lang.protein + ": &7" + nutrition.getProteins()));
			lore.add(Utils.getColoredString("&5" + lang.vitamins + ": &7" + nutrition.getVitamins()));
			meta.setLore(lore);

			item.setItemMeta(meta);
			this.inv.setItem(i++, item);
		}
	}

	public void openInventory(Player player) {
		player.openInventory(inv);
		initializeItems();
	}

	@EventHandler
	private void onClick(InventoryClickEvent event) {
		if (inv.getHolder() != this) return;
		if (event.getInventory().getHolder() != inv.getHolder()) return;

		event.setCancelled(true);
		Player player = ((Player) event.getWhoClicked());

		if (event.getSlot() < 0) {
			player.closeInventory();
			player.updateInventory();
		}
		if (event.getClickedInventory() != this.inv) {
			player.closeInventory();
			player.updateInventory();
		}
	}

}
