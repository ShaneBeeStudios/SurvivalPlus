package com.shanebeestudios.survival.plugin.listeners.block;

import com.google.common.collect.ImmutableSet;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class WorkbenchShare implements Listener {

	private final SurvivalPlugin plugin;

	public WorkbenchShare(SurvivalPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
    @SuppressWarnings({"deprecation", "unchecked"})
	private void onPlayerInteract(PlayerInteractEvent e) {
		if (e.isCancelled()) return;
		final Player p = e.getPlayer();
		final Block block = e.getClickedBlock();

		if (block == null || block.getType() != Material.CRAFTING_TABLE) return;

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		Bukkit.getServer().getScheduler().runTask(plugin, () -> {
			if (!p.isOnline())
				return;

			if (!block.hasMetadata("shared_players"))
				block.setMetadata("shared_players", new FixedMetadataValue(plugin, new ArrayList<UUID>()));

			final List<UUID> list = (block.getMetadata("shared_players").getFirst().value() instanceof List<?>) ? (List<UUID>) block.getMetadata("shared_players").getFirst().value() : new ArrayList<>();

			final Inventory open = p.getOpenInventory().getTopInventory();

			if (open.getType() != InventoryType.WORKBENCH)
				return;

			// Workaround to get the accessed WorkBench
			final Block workbench = p.getTargetBlock(ImmutableSet.of(Material.SHORT_GRASS, Material.SNOW, Material.AIR), 8);

			if (workbench.getType() != Material.CRAFTING_TABLE) {
				// Close Inventory if player managed to access the workbench without actually use one.
				p.closeInventory();
				return;
			}

			assert list != null;
			list.add(p.getUniqueId());
			p.setMetadata("shared_workbench", new FixedMetadataValue(plugin, block));

			Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
				if (list.isEmpty())
					return;
				Player first = Bukkit.getPlayer(list.getFirst());
				assert first != null;
				Inventory pInv = first.getOpenInventory().getTopInventory();
				if (pInv.getType() != InventoryType.WORKBENCH)
					return;
				open.setContents(pInv.getContents());
				Bukkit.getServer().getScheduler().runTaskLater(plugin, p::updateInventory, 1);
			}, 1);
		});

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInventoryClick(InventoryClickEvent e) {
		onInventoryInteract(e);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInventoryDrag(InventoryDragEvent e) {
		onInventoryInteract(e);
	}

	@SuppressWarnings("unchecked")
    private void onInventoryInteract(InventoryInteractEvent e) {
		if (e.isCancelled()) return;
		if (!(e.getWhoClicked() instanceof Player player))
			return;

        if (!player.hasMetadata("shared_workbench"))
			return;

		if (e.getInventory().getType() == InventoryType.WORKBENCH) {
			// Workaround to get the accessed WorkBench
			final Block workbench = (player.getMetadata("shared_workbench").getFirst().value() instanceof Block) ? (Block) player.getMetadata("shared_workbench").getFirst().value() : null;

			assert workbench != null;
			if (!workbench.hasMetadata("shared_players") || workbench.getType() != Material.CRAFTING_TABLE) {
				player.getOpenInventory().getTopInventory().clear();
				player.closeInventory();
				player.removeMetadata("shared_workbench", plugin);
				return;
			}

			List<UUID> list = (workbench.getMetadata("shared_players").getFirst().value() instanceof List<?>) ? (List<UUID>) workbench.getMetadata("shared_players").getFirst().value() : new ArrayList<UUID>();

			final Inventory pInv = player.getOpenInventory().getTopInventory();
			if (pInv.getType() != InventoryType.WORKBENCH) {
				workbench.removeMetadata("shared_players", plugin);
				return;
			}

			assert list != null;
			Iterator<UUID> iterator = list.iterator();
			while (iterator.hasNext()) {
				UUID next = iterator.next();

				if (player.getUniqueId().equals(next))
					continue;

				final Player idPlayer = Bukkit.getPlayer(next);

				if (idPlayer == null || !idPlayer.isOnline()) {
					iterator.remove();
					continue;
				}

				final Inventory open = idPlayer.getOpenInventory().getTopInventory();

				if (open.getType() != InventoryType.WORKBENCH) {
					// Close Inventory if player managed to access the workbench without actually use one.
					iterator.remove();
					player.closeInventory();
					continue;
				}

				Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
					open.setContents(pInv.getContents());
					Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
						player.updateInventory();
						idPlayer.updateInventory();
					}, 1);
				}, 1);
			}
		}
	}

	@SuppressWarnings("unchecked")
    @EventHandler
	private void onInventoryClose(InventoryCloseEvent e) {
		if (!(e.getPlayer() instanceof Player))
			return;
		final Player p = (Player) e.getPlayer();

		if (!p.hasMetadata("shared_workbench"))
			return;
		if (e.getInventory().getType() == InventoryType.WORKBENCH) {
			// Workaround to get the accessed WorkBench
			final Block workbench = p.getTargetBlock((Set<Material>) null, 8);

			if (!workbench.hasMetadata("shared_players") || workbench.getType() != Material.CRAFTING_TABLE) {
				p.getOpenInventory().getTopInventory();
				p.getOpenInventory().getTopInventory().clear();
				p.removeMetadata("shared_workbench", plugin);

				return;
			}

			List<UUID> list = (workbench.getMetadata("shared_players").getFirst().value() instanceof List<?>) ? (List<UUID>) workbench.getMetadata("shared_players").getFirst().value() : new ArrayList<UUID>();

			assert list != null;
			list.remove(p.getUniqueId());

			if (list.isEmpty())
				workbench.removeMetadata("shared_players", plugin);
			else {
				e.getInventory().clear();
				workbench.setMetadata("shared_players", new FixedMetadataValue(plugin, list));
			}
		}
	}

	@SuppressWarnings("unchecked")
    @EventHandler
	private void onPlayerQuit(PlayerQuitEvent e) {
		final Player p = e.getPlayer();

		if (!p.hasMetadata("shared_workbench"))
			return;

		Block workbench = (p.getMetadata("shared_workbench").getFirst().value() instanceof Block) ? (Block) p.getMetadata("shared_workbench").getFirst().value() : null;

		if (workbench != null && workbench.hasMetadata("shared_players") && workbench.getType() == Material.CRAFTING_TABLE) {
			List<UUID> list = (workbench.getMetadata("shared_players").getFirst().value() instanceof List<?>) ? (List<UUID>) workbench.getMetadata("shared_players").getFirst().value() : new ArrayList<UUID>();

			assert list != null;
			list.remove(p.getUniqueId());

			if (list.isEmpty())
				workbench.removeMetadata("shared_players", plugin);
			else
				workbench.setMetadata("shared_players", new FixedMetadataValue(plugin, list));
		}

		p.removeMetadata("shared_workbench", plugin);
	}

	@SuppressWarnings("unchecked")
    @EventHandler(priority = EventPriority.HIGHEST)
	private void onBreakWorkbench(BlockBreakEvent e) {
		if (e.isCancelled()) return;
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		Block workbench = e.getBlock();

		if (!workbench.hasMetadata("shared_players") || workbench.getType() != Material.CRAFTING_TABLE)
			return;

		List<UUID> list = (workbench.getMetadata("shared_players").getFirst().value() instanceof List<?>) ? (List<UUID>) workbench.getMetadata("shared_players").getFirst().value() : new ArrayList<UUID>();

		assert list != null;
		Iterator<UUID> iterator = list.iterator();

		Inventory sharedInventory = Bukkit.createInventory(null, InventoryType.WORKBENCH);

		while (iterator.hasNext()) {
			UUID next = iterator.next();

			iterator.remove();

			final Player idPlayer = Bukkit.getPlayer(next);

			if (idPlayer != null) {
				idPlayer.removeMetadata("shared_inv", plugin);

				if (idPlayer.isOnline()) {
					final Inventory open = idPlayer.getOpenInventory().getTopInventory();

					if (open.getType() == InventoryType.WORKBENCH) {
						sharedInventory.setContents(open.getContents());
						open.clear();
						idPlayer.closeInventory();
					}
				}
			}
		}

		for (int i = 1; i < sharedInventory.getSize(); i++) {
			ItemStack item = sharedInventory.getItem(i);
			if (item != null)
				workbench.getWorld().dropItem(workbench.getLocation(), item);
		}


		workbench.removeMetadata("shared_players", plugin);
	}
}
