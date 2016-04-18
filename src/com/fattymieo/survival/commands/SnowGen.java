package com.fattymieo.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.fattymieo.survival.events.SnowGeneration;

public class SnowGen implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(Bukkit.getServer().getOnlinePlayers().size() <= 1)
		{
			if(sender instanceof Player)
			{
				sender.sendMessage(ChatColor.AQUA + "[SurvivalPlus] " + ChatColor.RED + "WARNING!" + ChatColor.YELLOW + " Snow Generation is running for generated chunks!");
				sender.getServer().getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.RED + "WARNING!" + ChatColor.YELLOW + " Snow Generation is running for generated chunks!");
			}
			else if(sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage("[SurvivalPlus] " + ChatColor.RED + "WARNING!" + ChatColor.YELLOW + " Snow Generation is running for generated chunks!");
			}
			
			SnowGeneration snowGen = new SnowGeneration();
			
			for(final World world : Bukkit.getServer().getWorlds())
			{
				for(final Chunk chunk : world.getLoadedChunks())
				{
					snowGen.checkChunk(chunk);
				}
			}
			
			if(sender instanceof Player)
			{
				sender.sendMessage(ChatColor.AQUA + "[SurvivalPlus] " + ChatColor.GREEN + "Snow Generation is completed.");
				sender.getServer().getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.GREEN + "Snow Generation is completed.");
			}
			else if(sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage("[SurvivalPlus] " + ChatColor.GREEN + "Snow Generation is completed.");
			}
			
			return true;
		}
		else
		{
			if(sender instanceof Player)
			{
				sender.sendMessage(ChatColor.AQUA + "[SurvivalPlus] " + ChatColor.RED + "WARNING!" + ChatColor.YELLOW + " Snow Generation will run through all generated chunks, lag spikes may occur!");
				sender.sendMessage(ChatColor.AQUA + "[SurvivalPlus] " + ChatColor.YELLOW + "Run this command while nobody is in the server.");
			}
			else if(sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage("[SurvivalPlus] " + ChatColor.RED + "WARNING!" + ChatColor.YELLOW + " Snow Generation will run through all generated chunks, lag spikes may occur!");
				sender.sendMessage("[SurvivalPlus] " + ChatColor.YELLOW + "Run this command while nobody is in the server.");
			}
			
			return false;
		}
	}
}
