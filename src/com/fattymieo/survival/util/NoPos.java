package com.fattymieo.survival.util;

/**
 * Originally by Rolyndev's plugin, NoPos
 * Modified and implemented by FattyMieo
 * Thanks to Rolyndev for allowing implementation!
**/
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NoPos implements Listener
{
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		disableF3(e.getPlayer());
	}
  
	private static void disableF3(Player player)
	{
		try
		{
			Class<?> packetClass = getNMSClass("PacketPlayOutEntityStatus");
			Constructor<?> packetConstructor = packetClass.getConstructor(getNMSClass("Entity"), Byte.TYPE);
			Object packet = packetConstructor.newInstance(getHandle(player), (byte) 22);
			Method sendPacket = getNMSClass("PlayerConnection").getMethod("sendPacket", getNMSClass("Packet"));
			sendPacket.invoke(getConnection(player), packet);
		}
		catch (Exception e)
		{
			Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.RED + e.getMessage());
		}
	}
  
	public static void enableF3(Player player)
	{
		try
		{
			Class<?> packetClass = getNMSClass("PacketPlayOutEntityStatus");
			Constructor<?> packetConstructor = packetClass.getConstructor(getNMSClass("Entity"), Byte.TYPE);
			Object packet = packetConstructor.newInstance(getHandle(player), (byte) 23);
			Method sendPacket = getNMSClass("PlayerConnection").getMethod("sendPacket", getNMSClass("Packet"));
			sendPacket.invoke(getConnection(player), packet);
		}
		catch (Exception e)
		{
			Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.RED + e.getMessage());
		}
	}
  
	private static Class<?> getNMSClass(String nmsClassString)
	throws ClassNotFoundException
	{
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String name = "net.minecraft.server." + version + nmsClassString;
		Class<?> nmsClass = Class.forName(name);
		return nmsClass;
	}
  
	private static Object getConnection(Player player)
	throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Field conField = getHandle(player).getClass().getField("playerConnection");
		Object con = conField.get(getHandle(player));
		return con;
	}
  
	private static Object getHandle(Player player)
	throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Method getHandle = player.getClass().getMethod("getHandle");
		Object nmsPlayer = getHandle.invoke(player);
		return nmsPlayer;
	}
}
