package tk.shanebee.survival.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Utils;

public class PlayerManager {

	private static String url = Survival.settings.getString("MultiWorld.ResourcePackURL");

	/** Set the waypoint of a player's compass to their location
	 * @param player The player to set a waypoint for
	 * @param particle If particles should show at the location a waypoint is set
	 */
	@SuppressWarnings("unused")
	public void setWaypoint(Player player, boolean particle) {
		setWaypoint(player, player.getLocation(), particle);
	}

	/** Set the waypoint of a player's compass
	 * @param player The player to set a waypoint for
	 * @param location The location of the waypoint
	 * @param particle If the particles should show at the location a waypoint is set
	 */
	public void setWaypoint(Player player, Location location, boolean particle) {
		player.setCompassTarget(location);
		if (particle)
			Utils.spawnParticle(location, Particle.CLOUD, 25, 0.5, 0.5, 0.5, player);
	}

	/** Apply SurvivalPlus' resource pack to a player
	 * @param player The player to apply the resource pack to
	 * @param delay A delay in ticks
	 */
	public void applyResourcePack(Player player, int delay) {
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

}
