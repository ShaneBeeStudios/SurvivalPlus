package com.fattymieo.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import com.fattymieo.survival.Survival;

public class SnowGeneration implements Listener
{	
	@EventHandler(ignoreCancelled = true)
	public void chunkLoad(final ChunkLoadEvent event)
	{
		if(Survival.snowGenOption)
		{
			if(event.isNewChunk())
			{
				Bukkit.getScheduler().runTask(Survival.instance, new Runnable()
				{
					public void run()
					{
						checkChunk(event.getChunk());
					}
				});
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void checkChunk(final Chunk chunk)
	{
		final ChunkSnapshot chunkSnap = chunk.getChunkSnapshot(true, false, false);
		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				final int y = chunkSnap.getHighestBlockYAt(x, z);
				
				if(chunkSnap.getBlockTypeId(x, y, z) == Material.SNOW.getId())
					placeSnow(chunk, chunkSnap, x, y, z);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void snowForm(final BlockFormEvent event)
	{
		if(Survival.snowGenOption)
		{
			if(event.getNewState().getType() != Material.SNOW)
				return;
			
			Bukkit.getScheduler().runTask(Survival.instance, new Runnable()
			{
				public void run()
				{
					placeSnow(event.getBlock());
				}
			});
		}
	}
	
	private void placeSnow(final Block block)
	{
		final Location loc = block.getLocation();
		final Chunk chunk = block.getChunk();
		
		placeSnow(chunk, chunk.getChunkSnapshot(true, false, false), Math.abs((chunk.getX() * 16) - loc.getBlockX()), loc.getBlockY(), Math.abs((chunk.getZ() * 16) - loc.getBlockZ()));
	}

	@SuppressWarnings("deprecation")
	private void placeSnow(final Chunk chunk, final ChunkSnapshot chunkSnap, final int x, int y, final int z)
	{
		if(y <= 1)
			return;
		
		int type = chunkSnap.getBlockTypeId(x, --y, z);
		
		if(type != Material.LEAVES.getId())
			return;
		
		int lastType = type;
		
		while(true)
		{
			type = chunkSnap.getBlockTypeId(x, --y, z);
			
			switch(Material.getMaterial(type))
			{
				case AIR: // ignore air and snow
				case SNOW:
					break;
				
				case LEAVES: // check leaves if they have air above them to place snow
				{
					if(lastType == 0)
					{
						try{chunk.getBlock(x, y + 1, z).setType(Material.SNOW);}
						catch(Exception e){}
					}
					
					break;
				}
				
				// snowable blocks and the stop
				case STONE:
				case GRASS:
				case DIRT:
				case COBBLESTONE:
				case WOOD:
				case BEDROCK:
				case SAND:
				case GRAVEL:
				case GOLD_ORE:
				case IRON_ORE:
				case COAL_ORE:
				case LOG:
				case SPONGE:
				case GLASS:
				case LAPIS_ORE:
				case LAPIS_BLOCK:
				case DISPENSER:
				case SANDSTONE:
				case NOTE_BLOCK:
//				case PISTON_BASE:
//				case PISTON_STICKY_BASE:
//				case PISTON_MOVING_PIECE:
//				case PISTON_EXTENSION:
				case WOOL:
				case GOLD_BLOCK:
				case IRON_BLOCK:
				case DOUBLE_STEP:
				case BRICK:
				case TNT:
				case BOOKSHELF:
				case MOSSY_COBBLESTONE:
				case OBSIDIAN:
				case MOB_SPAWNER:
				case DIAMOND_ORE:
				case DIAMOND_BLOCK:
				case WORKBENCH:
				case FURNACE:
//				case BURNING_FURNACE:
				case REDSTONE_ORE:
//				case ICE:
				case SNOW_BLOCK:
				case CLAY:
				case JUKEBOX:
				case PUMPKIN:
				case NETHERRACK:
				case SOUL_SAND:
				case GLOWSTONE:
//				case JACK_O_LANTERN:
				case SMOOTH_BRICK:
				case MELON_BLOCK:
				case NETHER_BRICK:
				case ENDER_STONE:
				case REDSTONE_LAMP_OFF:
//				case REDSTONE_LAMP_ON:
				case WOOD_DOUBLE_STEP:
				case EMERALD_BLOCK:
				case EMERALD_ORE:
				{
					if(lastType == 0)
					{
						try{chunk.getBlock(x, y + 1, z).setType(Material.SNOW);}
						catch(Exception e){}
					}
					
					return;
				}
				
				default: // everything else stops
					return;
			}
			
			lastType = type;
		}
	}
}
