package tk.shanebee.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.Items;

import java.util.Objects;


public class WaterBottleCrafting implements Listener {

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		final Player player = (Player) e.getWhoClicked();
		final CraftingInventory inv = e.getInventory();

		ItemStack[] bottles = inv.getMatrix();
		ItemStack result = inv.getResult();

		if (result != null && result.getType() != Material.GLASS_BOTTLE) {
			for (int i = 0; i < bottles.length; i++) {
				if (bottles[i] == null) continue;
				if (bottles[i].getType() == Material.POTION) {
					if (checkWaterBottle(bottles[i])) {
						final int slot = i + 1;
						Bukkit.getServer().getScheduler().runTaskLater(Survival.instance, () -> {
							inv.setItem(slot, new ItemStack(Material.GLASS_BOTTLE));
							player.updateInventory();
						}, 1);
					}
				}
			}
		}

		if (result != null && result.getType() != Material.BOWL) {
			for (int i = 0; i < bottles.length; i++) {
				if (bottles[i] == null) continue;
				if (bottles[i].getType() == Material.BEETROOT_SOUP) {
					final int slot = i + 1;
					Bukkit.getServer().getScheduler().runTaskLater(Survival.instance, () -> {
						inv.setItem(slot, new ItemStack(Material.BOWL));
						player.updateInventory();
					}, 1);
				}
			}
		}
	}

	@EventHandler
	public void onPrepareCraft(PrepareItemCraftEvent e) {
		CraftingInventory inv = e.getInventory();
		ItemStack result = inv.getResult();
		if (result != null && result.getType() != Material.GLASS_BOTTLE) {
			ItemStack[] bottles = inv.getMatrix();
			for (ItemStack bottle : bottles) {
				if (bottle == null) continue;
				if (bottle.getType().equals(Material.POTION)) {
					if (!checkWaterBottle(bottle)) {
						inv.setResult(null);
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void onFillWaterBottle(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		if (item != null && item.getType() == Material.GLASS_BOTTLE) {
			if (player.getTargetBlock(null, 5).getType() == Material.WATER) {
				e.setCancelled(true);
				player.getInventory().addItem(Items.get(Items.DIRTY_WATER));
				if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
					e.getItem().setAmount(e.getItem().getAmount() - 1);
			}
		}
	}

	private boolean checkWaterBottle(ItemStack bottle) {

		return ((PotionMeta) Objects.requireNonNull(bottle.getItemMeta())).getBasePotionData().getType() == PotionType.WATER;

	}

}
