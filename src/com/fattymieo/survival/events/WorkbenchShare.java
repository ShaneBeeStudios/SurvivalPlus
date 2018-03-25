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
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.fattymieo.survival.Survival;

public class WorkbenchShare implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent e)
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
					
					if (!block.hasMetadata("shared_players"))
						block.setMetadata("shared_players", new FixedMetadataValue(Survival.instance, new ArrayList<UUID>()));
					
					final List<UUID> list = (block.getMetadata("shared_players").get(0).value() instanceof List<?>) ? (List<UUID>) block.getMetadata("shared_players").get(0).value() : new ArrayList<UUID>();
					
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
					p.setMetadata("shared_workbench", new FixedMetadataValue(Survival.instance, block));

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

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e)
	{
		onInventoryInteract(e);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryDrag(InventoryDragEvent e)
	{
		onInventoryInteract(e);
	}
	
	public void onInventoryInteract(InventoryInteractEvent e)
	{
		if(e.isCancelled()) return;
		if (!(e.getWhoClicked() instanceof Player))
			return;
		
		final Player p = (Player) e.getWhoClicked();

		if (!p.hasMetadata("shared_workbench"))
			return;
		
		if (e.getInventory().getType() == InventoryType.WORKBENCH)
		{
			// Workaround to get the accessed WorkBench
			final Block workbench = (p.getMetadata("shared_workbench").get(0).value() instanceof Block) ? (Block)p.getMetadata("shared_workbench").get(0).value() : null;

			if (!workbench.hasMetadata("shared_players") || workbench.getType() != Material.WORKBENCH)
			{
				if (p.getOpenInventory().getTopInventory() != null)
					p.getOpenInventory().getTopInventory().clear();
				p.closeInventory();
				p.removeMetadata("shared_workbench", Survival.instance);
				return;
			}
			
			List<UUID> list = (workbench.getMetadata("shared_players").get(0).value() instanceof List<?>) ? (List<UUID>)workbench.getMetadata("shared_players").get(0).value() : new ArrayList<UUID>();

			final Inventory pInv = p.getOpenInventory().getTopInventory();
			if (pInv == null || pInv.getType() != InventoryType.WORKBENCH)
			{
				workbench.removeMetadata("shared_players", Survival.instance);
				return;
			}

			Iterator<UUID> iterator = list.iterator();
			while (iterator.hasNext())
			{
				UUID next = iterator.next();
				
				if (p.getUniqueId().equals(next))
					continue;
				
				final Player idPlayer = Bukkit.getPlayer(next);
				
				if (idPlayer == null || !idPlayer.isOnline())
				{
					iterator.remove();
					continue;
				}
				
				final Inventory open = idPlayer.getOpenInventory().getTopInventory();
				
				if (open == null || open.getType() != InventoryType.WORKBENCH)
				{
					// Close Inventory if player managed to access the workbench without actually use one.
					iterator.remove();
					p.closeInventory();
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

		if (!p.hasMetadata("shared_workbench"))
			return;
		if (e.getInventory().getType() == InventoryType.WORKBENCH)
		{
			// Workaround to get the accessed WorkBench
			final Block workbench = p.getTargetBlock((Set<Material>) null, 8);

			if (!workbench.hasMetadata("shared_players") || workbench.getType() != Material.WORKBENCH)
			{
				if (p.getOpenInventory().getTopInventory() != null)
					p.getOpenInventory().getTopInventory().clear();
				p.removeMetadata("shared_workbench", Survival.instance);
				
				return;
			}
			
			List<UUID> list = (workbench.getMetadata("shared_players").get(0).value() instanceof List<?>) ? (List<UUID>)workbench.getMetadata("shared_players").get(0).value() : new ArrayList<UUID>();
			
			list.remove(p.getUniqueId());
			
			if (list.isEmpty())
				workbench.removeMetadata("shared_players", Survival.instance);
			else
			{
				e.getInventory().clear();
				workbench.setMetadata("shared_players", new FixedMetadataValue(Survival.instance, list));
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		if (!(e.getPlayer() instanceof Player))
			return;
		final Player p = (Player) e.getPlayer();

		if (!p.hasMetadata("shared_workbench"))
			return;
		
		Block workbench = (p.getMetadata("shared_workbench").get(0).value() instanceof Block) ? (Block)p.getMetadata("shared_workbench").get(0).value() : null;
		
		if (workbench != null && workbench.hasMetadata("shared_players") && workbench.getType() == Material.WORKBENCH)
		{
			List<UUID> list = (workbench.getMetadata("shared_players").get(0).value() instanceof List<?>) ? (List<UUID>)workbench.getMetadata("shared_players").get(0).value() : new ArrayList<UUID>();

			list.remove(p.getUniqueId());
			
			if (list.isEmpty())
				workbench.removeMetadata("shared_players", Survival.instance);
			else
				workbench.setMetadata("shared_players", new FixedMetadataValue(Survival.instance, list));
		}
		
		p.removeMetadata("shared_workbench", Survival.instance);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreakWorkbench(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		Block workbench = e.getBlock();

		if (!workbench.hasMetadata("shared_players") || workbench.getType() != Material.WORKBENCH)
			return;
		
		List<UUID> list = (workbench.getMetadata("shared_players").get(0).value() instanceof List<?>) ? (List<UUID>)workbench.getMetadata("shared_players").get(0).value() : new ArrayList<UUID>();
		
		Iterator<UUID> iterator = list.iterator();
		
		Inventory sharedInventory = Bukkit.createInventory(null, InventoryType.WORKBENCH);
		
		while (iterator.hasNext())
		{
			UUID next = iterator.next();
			
			iterator.remove();
			
			final Player idPlayer = Bukkit.getPlayer(next);
			
			if (idPlayer != null)
			{
				idPlayer.removeMetadata("shared_inv", Survival.instance);
				
				if(idPlayer.isOnline())
				{
					final Inventory open = idPlayer.getOpenInventory().getTopInventory();
					
					if (open != null && open.getType() == InventoryType.WORKBENCH)
					{
						sharedInventory.setContents(open.getContents());
						open.clear();
						idPlayer.closeInventory();
					}
				}
			}
		}
		
		for(int i = 1; i < sharedInventory.getSize(); i++)
		{
			ItemStack item = sharedInventory.getItem(i);
			if(item != null)
				workbench.getWorld().dropItem(workbench.getLocation(), item);
		}
		
		workbench.removeMetadata("shared_players", Survival.instance);
	}
}