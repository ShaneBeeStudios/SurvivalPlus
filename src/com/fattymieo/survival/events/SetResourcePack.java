package com.fattymieo.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.fattymieo.survival.Survival;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;

public class SetResourcePack implements Listener {

	private final String url = Survival.settings.getString("MultiWorld.ResourcePackURL");
	private boolean resourcePack = Survival.settings.getBoolean("MultiWorld.EnableResourcePack");
	private String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&3SurvivalPlus&7] ");

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (resourcePack)
			applyResourcePack(event.getPlayer());
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		if (resourcePack)
			applyResourcePack(event.getPlayer());
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Survival.usingPlayers.remove(event.getPlayer());
	}

	@SuppressWarnings("deprecation")
	private void applyResourcePack(Player player) {
		if (url != null) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, () -> {
				try {
					player.setResourcePack(url);
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage("ResourcePackURL is null or URL is too long! Plugin disabled.");
					Bukkit.getPluginManager().disablePlugin(Survival.instance);
					return;
				}
				Survival.usingPlayers.add(player);
			}, 20L);
		}
	}

	@EventHandler
	public void resourcePackEvent(PlayerResourcePackStatusEvent e) {
		Player player = e.getPlayer();
		if (Survival.settings.getBoolean("MultiWorld.NotifyMessage"))
			if (e.getStatus() == Status.DECLINED) {
				player.sendMessage(" ");
				player.sendMessage(prefix + ChatColor.RED + Survival.Words.get("Resource pack declined"));
				player.sendMessage("   " + ChatColor.GOLD+ Survival.Words.get("Please apply the requested Resource Pack"));
				player.sendMessage("   " + ChatColor.GOLD + Survival.Words.get("The resource pack is required for visual effects"));
			} else if (e.getStatus() == Status.ACCEPTED) {
				player.sendMessage(prefix + ChatColor.GREEN + Survival.Words.get("Resource pack accepted"));

		}

	}

}