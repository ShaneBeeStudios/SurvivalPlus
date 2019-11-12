package tk.shanebee.survival.managers;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerManager {

	private String url;
	private Scoreboard mainBoard;
	private Lang lang;
	private Survival plugin;

	public PlayerManager(Survival plugin) {
		this.plugin = plugin;
		this.mainBoard = plugin.getMainBoard();
		this.lang = plugin.getLang();
		this.url = plugin.getSurvivalConfig().RESOURCE_PACK_URL;
	}

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
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
				try {
					player.setResourcePack(url);
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage("ResourcePackURL is null or URL is too long! Plugin disabled.");
					Bukkit.getPluginManager().disablePlugin(plugin);
					return;
				}
				plugin.getUsingPlayers().add(player);
			}, delay);
		}
	}

	public Location lookAt(Location loc, Location lookat) {
		//Clone the loc to prevent applied changes to the input loc
		loc = loc.clone();

		// Values of change in distance (make it relative)
		double dx = lookat.getX() - loc.getX();
		double dy = lookat.getY() - loc.getY();
		double dz = lookat.getZ() - loc.getZ();

		// Set yaw
		if (dx != 0) {
			// Set yaw start value based on dx
			if (dx < 0)
				loc.setYaw((float) (1.5 * Math.PI));
			else
				loc.setYaw((float) (0.5 * Math.PI));

			loc.setYaw(loc.getYaw() - (float) Math.atan(dz / dx));
		} else if (dz < 0)
			loc.setYaw((float) Math.PI);

		// Get the distance from dx/dz
		double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

		// Set pitch
		loc.setPitch((float) -Math.atan(dy / dxz));

		// Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
		loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
		loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

		return loc;
	}

	public List<String> ShowThirst(Player player) {
		StringBuilder thirstBar = new StringBuilder();
		for (int i = 0; i < StatusManager.getThirst(player); i++) {
			thirstBar.append("|");
		}
		for (int i = StatusManager.getThirst(player); i < 20; i++) {
			thirstBar.append(".");
		}

		if (StatusManager.getThirst(player) >= 40)
			thirstBar.insert(0, ChatColor.GREEN);
		else if (StatusManager.getThirst(player) <= 6)
			thirstBar.insert(0, ChatColor.RED);
		else
			thirstBar.insert(0, ChatColor.AQUA);

		return Arrays.asList(ChatColor.AQUA + lang.thirst, (thirstBar.length() <= 22 ? thirstBar.toString() : thirstBar.substring(0, 22)),
				thirstBar.substring(0, 2) + (thirstBar.length() > 22 ? thirstBar.substring(22) : "") + ChatColor.RESET + ChatColor.RESET);
	}

	public List<String> ShowHunger(Player player) {
		int hunger = player.getFoodLevel();
		int saturation = Math.round(player.getSaturation());
		StringBuilder hungerBar = new StringBuilder();
		StringBuilder saturationBar = new StringBuilder(ChatColor.YELLOW + "");
		for (int i = 0; i < hunger; i++) {
			hungerBar.append("|");
		}
		for (int i = hunger; i < 20; i++) {
			hungerBar.append(".");
		}
		for (int i = 0; i < saturation; i++) {
			saturationBar.append("|");
		}

		if (hunger >= 20)
			hungerBar.insert(0, ChatColor.GREEN);
		else if (hunger <= 6)
			hungerBar.insert(0, ChatColor.RED);
		else
			hungerBar.insert(0, ChatColor.GOLD);

		return Arrays.asList(ChatColor.GOLD + lang.hunger, hungerBar.toString() + ChatColor.RESET, saturationBar.toString());
	}

	public List<String> ShowNutrients(Player player) {
		List<String> nutrients = new ArrayList<>();
		int carbon = mainBoard.getObjective("Carbs").getScore(player.getName()).getScore();
		int protein = mainBoard.getObjective("Protein").getScore(player.getName()).getScore();
		int salts = mainBoard.getObjective("Salts").getScore(player.getName()).getScore();

		String showCarbon = Integer.toString(carbon);
		if (carbon >= 480)
			showCarbon = ChatColor.GREEN + showCarbon;
		else
			showCarbon = ChatColor.RED + showCarbon;
		nutrients.add(showCarbon + " " + ChatColor.DARK_GREEN + lang.carbohydrates);

		String showProtein = Integer.toString(protein);
		if (protein >= 120)
			showProtein = ChatColor.GREEN + showProtein;
		else
			showProtein = ChatColor.RED + showProtein;
		nutrients.add(showProtein + " " + ChatColor.DARK_RED + lang.protein);

		String showSalts = Integer.toString(salts);
		if (salts >= 180)
			showSalts = ChatColor.GREEN + showSalts;
		else
			showSalts = ChatColor.RED + showSalts;
		nutrients.add(showSalts + " " + ChatColor.BLUE + lang.vitamins);

		return nutrients;
	}

	public String ShowFatigue(Player player) {
		int fatigue = mainBoard.getObjective("Fatigue").getScore(player.getName()).getScore();

		if (fatigue <= 0)
			return ChatColor.YELLOW + lang.energized;
		else if (fatigue == 1)
			return ChatColor.LIGHT_PURPLE + lang.sleepy;
		else if (fatigue == 2)
			return ChatColor.RED + lang.overworked;
		else if (fatigue == 3)
			return ChatColor.WHITE + lang.distressed;
		else return ChatColor.DARK_GRAY + lang.collapsed_1;
	}

	/** Check if player is holding arrows in their offhand
	 * @param player The player to check
	 * @return Whether or not the player has arrows in their offhand
	 */
	public boolean isArrowOffHand(Player player){
		Material mainHand = player.getInventory().getItemInMainHand().getType();
		Material offHand = player.getInventory().getItemInOffHand().getType();
		if (mainHand == Material.CROSSBOW)
			return offHand == Material.ARROW || offHand == Material.SPECTRAL_ARROW
					|| offHand == Material.TIPPED_ARROW || offHand == Material.FIREWORK_ROCKET;
		return offHand == Material.ARROW || offHand == Material.SPECTRAL_ARROW || offHand == Material.TIPPED_ARROW;
	}

}
