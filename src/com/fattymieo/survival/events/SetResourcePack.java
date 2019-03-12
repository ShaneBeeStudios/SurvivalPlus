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

public class SetResourcePack implements Listener {
	private final String url = Survival.settings.getString("MultiWorld.ResourcePackURL");
	private boolean resourcePack = Survival.settings.getBoolean("MultiWorld.EnableResourcePack");

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

	private void applyResourcePack(Player p) {
		final Player player = p;
		if (Survival.settings.getBoolean("MultiWorld.NotifyMessage")) {
			player.sendMessage(ChatColor.AQUA + "[SurvivalPlus]" + " " + ChatColor.GREEN + Survival.Words.get("Please apply the requested Resource Pack"));
			player.sendMessage(ChatColor.AQUA + "[SurvivalPlus]" + " " + ChatColor.GREEN + Survival.Words.get("Don't use any other Resource Packs"));
			player.sendMessage(ChatColor.AQUA + "[SurvivalPlus]" + " " + ChatColor.GREEN + Survival.Words.get("The resource pack is required for visual effects"));
		}

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
					},
					20L);
		}
	}

}