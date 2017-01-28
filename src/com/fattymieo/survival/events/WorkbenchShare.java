package com.fattymieo.survival.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import com.fattymieo.survival.Survival;

public class WorkbenchShare implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e)
	{
		if(e.isCancelled()) return;
		final Player p = e.getPlayer();
		
		if(e.getClickedBlock() == null)
			return;
		
		final Block block = e.getClickedBlock();
		
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		
		if(block.getType() == Material.WORKBENCH)
		{
			Bukkit.getServer().getScheduler().runTask(Survival.instance, new Runnable()
			{
				@Override
				public void run()
				{
					if (!p.isOnline())
						return;

					if (!block.hasMetadata("shared_inventory"))
						block.setMetadata("shared_inventory", new FixedMetadataValue(Survival.instance, new ArrayList<UUID>()));
					
					@SuppressWarnings("unchecked")
					final List<UUID> list = (block.getMetadata("shared_inventory").get(0).value() instanceof List<?>) ? (List<UUID>) block.getMetadata("shared_inventory").get(0).value() : new ArrayList<UUID>();

					final Inventory open = p.getOpenInventory().getTopInventory();
					
					if (open == null || open.getType() != InventoryType.WORKBENCH)
						return;

					// Workaround to get the accessed WorkBench
					final Block workbench = p.getTargetBlock((Set<Material>) null, 8);
					
					if (workbench == null || workbench.getType() != Material.WORKBENCH)
					{
						// Close Inventory if player managed to access the workbench without actually use one.
						p.closeInventory();
						return;
					}

					list.add(p.getUniqueId());
					p.setMetadata("shared_inv", new FixedMetadataValue(Survival.instance, block));

					Bukkit.getServer().getScheduler().runTaskLater(Survival.instance, new Runnable()
					{
						@Override
						public void run()
						{
							if (list.isEmpty())
								return;
							Player first = Bukkit.getPlayer(list.get(0));
							Inventory pInv = first.getOpenInventory().getTopInventory();
							if (pInv == null || pInv.getType() != InventoryType.WORKBENCH)
								return;
							open.setContents(pInv.getContents());
							Bukkit.getServer().getScheduler().runTaskLater(Survival.instance, new Runnable()
							{
								@SuppressWarnings("deprecation")
								@Override
								public void run()
								{
									p.updateInventory();
								}
							}, 1);
						}
					}, 1);
				}
			});
		}
	}

	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e)
	{
		if (!(e.getWhoClicked() instanceof Player))
			return;
		
		final Player p = (Player) e.getWhoClicked();
		
		if (e.getInventory().getType() == InventoryType.WORKBENCH)
		{
			// Workaround to get the accessed WorkBench
			final Block workbench = p.getTargetBlock((Set<Material>) null, 8);

			if (!workbench.hasMetadata("shared_inventory") || workbench.getType() != Material.WORKBENCH)
			{
				if (!p.hasMetadata("shared_inv"))
					return;
				else
				{
					if (p.getOpenInventory().getTopInventory() != null)
						p.getOpenInventory().getTopInventory().clear();
					p.closeInventory();
					p.removeMetadata("shared_inv", Survival.instance);
				}
				return;
			}
			
			@SuppressWarnings("unchecked")
			List<UUID> list = (workbench.getMetadata("shared_inventory").get(0).value() instanceof List<?>) ? (List<UUID>)workbench.getMetadata("shared_inventory").get(0).value() : new ArrayList<UUID>();

			final Inventory pInv = p.getOpenInventory().getTopInventory();
			if (pInv == null || pInv.getType() != InventoryType.WORKBENCH)
			{
				workbench.removeMetadata("shared_inventory", Survival.instance);
				return;
			}

			Iterator<UUID> iterator = list.iterator();
			while (iterator.hasNext())
			{
				UUID next = iterator.next();
				
				if (p.getUniqueId().equals(next))
					continue;
				
				final Player idPlayer = Bukkit.getPlayer(next);
				
				if (next == null || idPlayer == null || !idPlayer.isOnline())
				{
					iterator.remove();
					continue;
				}
				
				final Inventory open = idPlayer.getOpenInventory().getTopInventory();
				
				if (open == null || open.getType() != InventoryType.WORKBENCH)
				{
					// Close Inventory if player managed to access the workbench without actually use one.
					p.closeInventory();
					iterator.remove();
					continue;
				}

				Bukkit.getServer().getScheduler().runTaskLater(Survival.instance, new Runnable()
				{
					@Override
					public void run()
					{
						open.setContents(pInv.getContents());
						Bukkit.getServer().getScheduler().runTaskLater(Survival.instance, new Runnable()
						{
							@SuppressWarnings("deprecation")
							@Override
							public void run()
							{
								p.updateInventory();
								idPlayer.updateInventory();
							}
						}, 1);
					}
				}, 1);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryDrag(final InventoryDragEvent e)
	{
		if(e.isCancelled()) return;
		if (!(e.getWhoClicked() instanceof Player))
			return;
		
		final Player p = (Player) e.getWhoClicked();
		
		if (e.getInventory().getType() == InventoryType.WORKBENCH)
		{
			// Workaround to get the accessed WorkBench
			final Block workbench = p.getTargetBlock((Set<Material>) null, 8);

			if (!workbench.hasMetadata("shared_inventory") || workbench.getType() != Material.WORKBENCH)
			{
				if (!p.hasMetadata("shared_inv"))
					return;
				else
				{
					if (p.getOpenInventory().getTopInventory() != null)
						p.getOpenInventory().getTopInventory().clear();
					p.closeInventory();
					p.removeMetadata("shared_inv", Survival.instance);
				}
				return;
			}
			
			@SuppressWarnings("unchecked")
			List<UUID> list = (workbench.getMetadata("shared_inventory").get(0).value() instanceof List<?>) ? (List<UUID>)workbench.getMetadata("shared_inventory").get(0).value() : new ArrayList<UUID>();

			final Inventory pInv = p.getOpenInventory().getTopInventory();
			if (pInv == null || pInv.getType() != InventoryType.WORKBENCH)
			{
				workbench.removeMetadata("shared_inventory", Survival.instance);
				return;
			}

			Iterator<UUID> iterator = list.iterator();
			while (iterator.hasNext())
			{
				UUID next = iterator.next();
				
				if (p.getUniqueId().equals(next))
					continue;
				
				final Player idPlayer = Bukkit.getPlayer(next);
				
				if (next == null || idPlayer == null || !idPlayer.isOnline())
				{
					iterator.remove();
					continue;
				}
				
				final Inventory open = idPlayer.getOpenInventory().getTopInventory();
				
				if (open == null || open.getType() != InventoryType.WORKBENCH)
				{
					// Close Inventory if player managed to access the workbench without actually use one.
					p.closeInventory();
					iterator.remove();
					continue;
				}

				Bukkit.getServer().getScheduler().runTaskLater(Survival.instance, new Runnable()
				{
					@Override
					public void run()
					{
						open.setContents(pInv.getContents());
						Bukkit.getServer().getScheduler().runTaskLater(Survival.instance, new Runnable()
						{
							@SuppressWarnings("deprecation")
							@Override
							public void run()
							{
								p.updateInventory();
								idPlayer.updateInventory();
							}
						}, 1);
					}
				}, 1);
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e)
	{
		if (!(e.getPlayer() instanceof Player))
			return;
		final Player p = (Player) e.getPlayer();
		
		if (e.getInventory().getType() == InventoryType.WORKBENCH)
		{
			// Workaround to get the accessed WorkBench
			final Block workbench = p.getTargetBlock((Set<Material>) null, 8);

			if (!workbench.hasMetadata("shared_inventory") || workbench.getType() != Material.WORKBENCH)
			{
				if (!p.hasMetadata("shared_inv"))
					return;
				else
				{
					if (p.getOpenInventory().getTopInventory() != null)
						p.getOpenInventory().getTopInventory().clear();
					p.removeMetadata("shared_inv", Survival.instance);
				}
				return;
			}

			@SuppressWarnings("unchecked")
			List<UUID> list = (workbench.getMetadata("shared_inventory").get(0).value() instanceof List<?>) ? (List<UUID>)workbench.getMetadata("shared_inventory").get(0).value() : new ArrayList<UUID>();
			
			list.remove(p.getUniqueId());

			for (UUID id : list)
			{
				Player idP = Bukkit.getPlayer(id);
				if (idP == null || !idP.isOnline())
				{
					list.remove(id);
					continue;
				}
			}
			
			if (list.isEmpty())
				workbench.removeMetadata("shared_inventory", Survival.instance);
			else
				e.getInventory().clear();
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreakWorkbench(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		Block workbench = e.getBlock();
		
		workbench.removeMetadata("shared_inventory", Survival.instance);
	}
}