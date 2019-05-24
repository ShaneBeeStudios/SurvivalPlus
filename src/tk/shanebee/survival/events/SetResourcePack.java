package tk.shanebee.survival.events;

import tk.shanebee.survival.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tk.shanebee.survival.Survival;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;

public class SetResourcePack implements Listener {

	private static String url = Survival.settings.getString("MultiWorld.ResourcePackURL");
	private boolean resourcePack = Survival.settings.getBoolean("MultiWorld.EnableResourcePack");
	private String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&3SurvivalPlus&7] ");

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent event) {
		if (resourcePack)
			applyResourcePack(event.getPlayer(), 20);
	}

	/* Not sure why this was added, leaving here for now just in case its actually needed
	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		if (resourcePack)
			applyResourcePack(event.getPlayer());
	}
	 */

	@EventHandler
	private void onPlayerLeave(PlayerQuitEvent event) {
		Survival.usingPlayers.remove(event.getPlayer());
	}

	/** Apply SurvivalPlus' resource pack to a player
	 * @param player The player to apply the resource pack to
	 * @param delay A delay in ticks
	 */
	@SuppressWarnings({"WeakerAccess"})
	public static void applyResourcePack(Player player, int delay) {
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
			}, delay);
		}
	}

	@EventHandler
	private void resourcePackEvent(PlayerResourcePackStatusEvent e) {
		Player player = e.getPlayer();
		if (Survival.settings.getBoolean("MultiWorld.NotifyMessage"))
			if (e.getStatus() == Status.DECLINED) {
				player.sendMessage(" ");
				player.sendMessage(prefix + ChatColor.RED + Utils.getColoredString(Survival.lang.resource_pack_declined));
				player.sendMessage("   " + ChatColor.GOLD + Utils.getColoredString(Survival.lang.resource_pack_apply));
				player.sendMessage("   " + ChatColor.GOLD + Utils.getColoredString(Survival.lang.resource_pack_required));
			} else if (e.getStatus() == Status.ACCEPTED) {
				player.sendMessage(prefix + ChatColor.GREEN + Utils.getColoredString(Survival.lang.resource_pack_accepted));
			}
	}

}