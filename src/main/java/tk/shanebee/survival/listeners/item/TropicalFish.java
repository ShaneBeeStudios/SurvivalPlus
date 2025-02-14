package tk.shanebee.survival.listeners.item;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;

public class TropicalFish implements Listener {

    private final PlayerManager playerManager;
    private final Random random = new Random();

    public TropicalFish(Survival plugin) {
        this.playerManager = plugin.getPlayerManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
	private void onConsume(PlayerItemConsumeEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		if (event.getItem().getType() == Material.TROPICAL_FISH) {
			Location originLoc = player.getLocation();
            playSoundAndParticle(originLoc, Sound.ITEM_CHORUS_FRUIT_TELEPORT);

            PlayerData playerData = this.playerManager.getPlayerData(player);
            Location waypoint = playerData.getCompassWaypoint(player.getWorld());

            if (waypoint != null) {
				player.teleport(waypoint);
                playSoundAndParticle(waypoint, Sound.BLOCK_PORTAL_TRAVEL);
            } else {
                Location respawnLocation = player.getRespawnLocation();
                if (respawnLocation == null || respawnLocation.getWorld() != player.getWorld()) {
                    // Only teleport in the same world
                    respawnLocation = player.getWorld().getSpawnLocation();
                }
				player.teleport(respawnLocation);
                playSoundAndParticle(respawnLocation, Sound.BLOCK_PORTAL_TRAVEL);
            }
		}
	}

    private void playSoundAndParticle(Location location, Sound sound) {
        location.getWorld().spawnParticle(Particle.PORTAL, location, 200, 0.5, 0.5, 0.5);
        location.getWorld().playSound(location, sound, 1.0F, this.random.nextFloat() * 0.4F + 0.8F);

    }

}
