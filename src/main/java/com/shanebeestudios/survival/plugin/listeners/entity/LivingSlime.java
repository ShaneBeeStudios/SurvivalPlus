package com.shanebeestudios.survival.plugin.listeners.entity;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.api.util.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LivingSlime implements Listener {

	private final SurvivalPlugin plugin;

	public LivingSlime(SurvivalPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	private void onGhastTearSlimeBlock(ItemSpawnEvent e) {
		if (e.getEntityType() == EntityType.ITEM) {
			Item itemEntity = e.getEntity();
			if (itemEntity.getItemStack().getType() == Material.GHAST_TEAR) {
				Bukkit.getScheduler().runTaskLater(this.plugin, initRunnable(itemEntity), 20);
			}
		}
	}

	private Runnable initRunnable(Item itemEntity) {
		return () -> {
			List<Block> slimeBlocks = new ArrayList<>();
			slimeBlocks.add(itemEntity.getLocation().add(0, -1, 0).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(0, -1, 1).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(0, -1, -1).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(1, -1, 0).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(-1, -1, 0).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(0, 0, 1).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(0, 0, -1).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(1, 0, 0).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(-1, 0, 0).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(1, 0, 1).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(1, 0, -1).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(-1, 0, 1).getBlock());
			slimeBlocks.add(itemEntity.getLocation().add(-1, 0, -1).getBlock());

			ItemStack itemStack = itemEntity.getItemStack();
			Iterator<Block> blockIterator = slimeBlocks.iterator();
			Block slimeBlock;
			while (blockIterator.hasNext()) {
				slimeBlock = blockIterator.next();
				if (slimeBlock != null && slimeBlock.getType() == Material.SLIME_BLOCK && itemEntity.isOnGround()) {
					if (itemStack.getAmount() > 1)
						itemStack.setAmount(itemStack.getAmount() - 1);

					if (itemStack.getAmount() <= 0)
						itemEntity.remove();

					slimeBlock.setType(Material.AIR);

					Slime slime = itemEntity.getWorld().spawn(slimeBlock.getLocation(), Slime.class);
					slime.setSize(2);

					Utils.spawnParticle(slimeBlock.getLocation().add(0.5, 0.5, 0.5), Particle.CLOUD, 20, 0.5, 0.5, 0.5);
					break;
				}
			}
		};
	}

}
