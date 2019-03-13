package com.fattymieo.survival.unfinished;

import com.fattymieo.survival.Survival;
import com.fattymieo.survival.util.Utils;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.PacketPlayOutAnimation;
import net.minecraft.server.v1_13_R2.PacketPlayOutBed;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.scoreboard.Objective;

import java.util.HashMap;
import java.util.Map;

public class BedFatigueTEST implements Listener
{
	private Objective fatigue = Survival.mainBoard.getObjective("Fatigue");
	private Map<Player, BlockPosition> sleeping = new HashMap<>();
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
			if(Utils.isBed(clicked.getType()))
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
    	wakePlayer(e.getPlayer());
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
