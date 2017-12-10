package com.fattymieo.survival.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import com.fattymieo.survival.Survival;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_12_R1.PacketPlayOutBed;

public class BedFatigue implements Listener
{
	Objective fatigue = Survival.mainBoard.getObjective("Fatigue");
	Map<Player, BlockPosition> sleeping = new HashMap<>();
	//List<Player> sleeping = new ArrayList<>();
	
	private void sleepPlayer(Player player, int x, int y, int z)
	{
		BlockPosition blockPos = new BlockPosition(x, y, z);
        PacketPlayOutBed bedpacket = new PacketPlayOutBed(((CraftPlayer)player).getHandle(), blockPos);
        for (Player p : Bukkit.getOnlinePlayers())
        {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(bedpacket);
        }
        //player.setBedSpawnLocation(new Location(player.getWorld(), x, y, z));
        sleeping.put(player, blockPos);
	}
	
	private void wakePlayer(Player player)
	{
		if (sleeping.containsKey(player))
        {
        	Bukkit.getConsoleSender().sendMessage(player.getName());
            PacketPlayOutAnimation aniPacket = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 2);
            for (Player o : Bukkit.getOnlinePlayers())
            {
                ((CraftPlayer) o).getHandle().playerConnection.sendPacket(aniPacket);
            }
            sleeping.remove(player);
            //player.teleport(player.getBedSpawnLocation());
        }
	}
	
	private void showAllSleeping(Player joined)
	{
		for (Map.Entry<Player, BlockPosition> entry : sleeping.entrySet())
		{
			//Player player = (Player)Bukkit.getOfflinePlayer(i);
        	//Bukkit.getConsoleSender().sendMessage(p.getName());
	        PacketPlayOutBed bedpacket = new PacketPlayOutBed(((CraftPlayer)entry.getKey()).getHandle(), entry.getValue());
	        ((CraftPlayer)joined).getHandle().playerConnection.sendPacket(bedpacket);
		}
	}
	
	@EventHandler
	public void onClickBed(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Block clicked = event.getClickedBlock();
			if(clicked.getType() == Material.BED_BLOCK)
			{
                event.setCancelled(true);
                sleepPlayer(event.getPlayer(), clicked.getX(), clicked.getY(), clicked.getZ());
			}
		}
	}

	@EventHandler
	public void onClickLeaveBed(PlayerTeleportEvent event)
	{
    	//Bukkit.getConsoleSender().sendMessage("GOT IT TELEPORT EVENT");
        //wakePlayer(event.getPlayer());
    }
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBedLeave(PlayerBedLeaveEvent e)
	{
    	Bukkit.getConsoleSender().sendMessage("GOT IT LEAVE BED");
		/*
		long time = e.getBed().getWorld().getTime();
		if(time % 24000 == 0)
		{
			Player player = e.getPlayer();
			fatigue.getScore(player).setScore(0);
		}*/
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		showAllSleeping(player);
		fatigue.getScore(player).setScore(0);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
	    showAllSleeping(player);
		if(!player.hasPlayedBefore())
		{
			fatigue.getScore(player).setScore(0);
		}
	}
}
