package tk.shanebee.survival.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.config.PlayerDataConfig;
import tk.shanebee.survival.data.Nutrient;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manager for players
 * <p>Get an instance of this class from <b>{@link Survival#getPlayerManager()}</b></p>
 */
public class PlayerManager implements Listener {

    private final String url;
    private final Lang lang;
    private final Survival plugin;
    private final PlayerDataConfig playerDataConfig;
    private final int thirstStartingAmount;
    private final int hungerStartingAmount;
    private final double energyStartingAmount;
    private final int proteinStartingAmount;
    private final int carbsStartingAmount;
    private final int saltsStartingAmount;
    private final int maxProtein;
    private final int maxCarbs;
    private final int maxSalts;

    // Store all the active PlayerData
    private final Map<UUID, PlayerData> playerDataMap;

    public PlayerManager(Survival plugin, Map<UUID, PlayerData> playerDataMap) {
        this.plugin = plugin;
        this.playerDataMap = playerDataMap;
        this.lang = plugin.getLang();
        this.url = plugin.getSurvivalConfig().RESOURCE_PACK_URL;
        this.playerDataConfig = plugin.getPlayerDataConfig();
        Config config = plugin.getSurvivalConfig();
        this.thirstStartingAmount = config.mechanics_thirst_starting_amount;
        this.hungerStartingAmount = config.MECHANICS_HUNGER_START_AMOUNT;
        this.energyStartingAmount = config.MECHANICS_ENERGY_START;
        this.proteinStartingAmount = config.MECHANICS_FOOD_START_PROTEINS;
        this.carbsStartingAmount = config.MECHANICS_FOOD_START_CARBS;
        this.saltsStartingAmount = config.MECHANICS_FOOD_START_SALTS;
        this.maxCarbs = config.MECHANICS_FOOD_MAX_CARBS;
        this.maxProtein = config.MECHANICS_FOOD_MAX_PROTEINS;
        this.maxSalts = config.MECHANICS_FOOD_MAX_SALTS;
    }

    /**
     * Get PlayerData for a player
     *
     * @param player Player to get data for
     * @return PlayerData for player
     */
    public PlayerData getPlayerData(Player player) {
        return this.playerDataMap.get(player.getUniqueId());
    }

    /**
     * Get a collection of all PlayerData
     *
     * @return Collection of all PlayerData
     */
    @SuppressWarnings("unused")
    public Collection<PlayerData> getAllPlayerData() {
        return this.playerDataMap.values();
    }

    /**
     * Create player data for a new player
     *
     * @param player Player to create data for
     * @return Newly created player data
     */
    public PlayerData createNewPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        setHunger(player, hungerStartingAmount);

        PlayerData playerData = new PlayerData(uuid, this.thirstStartingAmount, this.proteinStartingAmount, this.carbsStartingAmount, this.saltsStartingAmount, this.energyStartingAmount);
        this.playerDataMap.put(uuid, playerData);
        savePlayerData(playerData);
        return playerData;
    }

    private void setHunger(Player player, int value) {
        value = Math.min(value, 40);
        int hunger = Math.min(value, 20);
        int saturation = value > 20 ? value - 20 : 0;
        player.setFoodLevel(hunger);
        player.setSaturation(saturation);
    }

    /**
     * Save PlayerData to file
     *
     * @param data PlayerData to save
     */
    private void savePlayerData(PlayerData data) {
        this.playerDataConfig.savePlayerDataToFile(data);
    }

    /**
     * Load PlayerData from file into map
     *
     * @param player Player to load data for
     * @return Loaded player data
     */
    public PlayerData loadPlayerData(Player player) {
        PlayerData playerData = this.playerDataConfig.getPlayerDataFromFile(player);
        this.playerDataMap.put(player.getUniqueId(), playerData);
        return playerData;
    }

    /**
     * Save/Unload player data
     * <p>This will mainly be used internally for when a player leaves the server,
     * their data will be saved to file then removed from the PlayerData map</p>
     *
     * @param player Player to save/unload data for
     */
    public void unloadPlayerData(Player player) {
        PlayerData playerData = getPlayerData(player);
        this.playerDataConfig.savePlayerDataToFile(playerData);
        this.playerDataMap.remove(player.getUniqueId());
    }

    /**
     * Set the waypoint of a player's compass to their location
     *
     * @param player   The player to set a waypoint for
     * @param particle If particles should show at the location a waypoint is set
     */
    @SuppressWarnings("unused")
    public void setWaypoint(Player player, boolean particle) {
        setWaypoint(player, player.getLocation(), particle);
    }

    /**
     * Set the waypoint of a player's compass
     *
     * @param player   The player to set a waypoint for
     * @param location The location of the waypoint
     * @param particle If the particles should show at the location a waypoint is set
     */
    public void setWaypoint(Player player, Location location, boolean particle) {
        PlayerData playerData = getPlayerData(player);
        playerData.setCompassWaypoint(location);
        if (particle)
            Utils.spawnParticle(location, Particle.CLOUD, 25, 0.5, 0.5, 0.5, player);
        savePlayerData(playerData);
    }

    /**
     * Apply SurvivalPlus' resource pack to a player
     *
     * @param player The player to apply the resource pack to
     * @param delay  A delay in ticks
     */
    public void applyResourcePack(Player player, int delay) {
        if (this.url != null) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                try {
                    player.setResourcePack(url);
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage("ResourcePackURL is null or URL is too long! Plugin disabled.");
                    Bukkit.getPluginManager().disablePlugin(this.plugin);
                    return;
                }
                this.plugin.getUsingPlayers().add(player);
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

    public List<String> getThirstVisual(Player player) {
        PlayerData data = getPlayerData(player);
        int thirst = data.getThirst();
        double grad = ((double) thirst / 40) - 1;

        // green - green - green - yellow - orange - red
        StringBuilder thirstLineOne = new StringBuilder("<transition:#02FF4E:#02FF4E:#02FF4E:#FBFF02:#FF9F02:#FF0202:" + grad + ">");
        StringBuilder thirstLineTwo = new StringBuilder("<transition:#02FF4E:#02FF4E:#02FF4E:#FBFF02:#FF9F02:#FF0202:" + grad + ">");

        if (thirst > 20) {
            thirstLineOne.append("|".repeat(20));
            thirstLineTwo.append("|".repeat(thirst - 20));
            thirstLineTwo.append(".".repeat(20 - (thirst - 20)));
        } else {
            thirstLineOne.append("|".repeat(thirst));
            thirstLineOne.append(".".repeat(20 - thirst));
            thirstLineTwo.append(".".repeat(20));
        }

        return Arrays.asList("<aqua><bold>" + this.lang.thirst, thirstLineOne.toString(), thirstLineTwo.toString());
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public List<String> getHungerVisual(Player player) {
        int hunger = Math.clamp(player.getFoodLevel(), 0, 20);
        int saturation = Math.clamp((int) player.getSaturation(), 0, 20);
        double grad = ((double) hunger / 20) - 1;

        // green - green - yellow - red
        StringBuilder hungerBar = new StringBuilder("<transition:#02FF4E:#02FF4E:#FF9F02:#FF0202:" + grad + ">");
        StringBuilder saturationBar = new StringBuilder("<yellow>");

        hungerBar.append("|".repeat(Math.max(0, hunger)));
        hungerBar.append(".".repeat(Math.max(0, 20 - hunger)));
        saturationBar.append("|".repeat(Math.max(0, saturation)));
        saturationBar.append(".".repeat(Math.max(0, 20 - saturation)));

        return Arrays.asList("<#63F9A7><bold>" + this.lang.hunger, hungerBar.toString(), saturationBar.toString());
    }

    public List<String> getNutrientsVisual(Player player) {
        List<String> nutrients = new ArrayList<>();
        PlayerData data = getPlayerData(player);

        int carbs = data.getNutrient(Nutrient.CARBS);
        int protein = data.getNutrient(Nutrient.PROTEIN);
        int salts = data.getNutrient(Nutrient.SALTS);

        nutrients.add("<#A0E853>" + this.lang.carbohydrates);
        nutrients.add("<#CE784D>" + this.lang.protein);
        nutrients.add("<#53DDE8>" + this.lang.vitamins);

        double carbGrad = ((double) carbs / this.maxCarbs) - 1;
        double proteinGrad = ((double) protein / this.maxProtein) - 1;
        double saltsGrad = ((double) salts / this.maxSalts) - 1;

        // green - green - green - yellow - red
        nutrients.add("<transition:#02FF4E:#02FF4E:#02FF4E:#FF9F02:#FF0202:" + carbGrad + ">" + carbs);
        nutrients.add("<transition:#02FF4E:#02FF4E:#02FF4E:#FF9F02:#FF0202:" + proteinGrad + ">" + protein);
        nutrients.add("<transition:#02FF4E:#02FF4E:#02FF4E:#FF9F02:#FF0202:" + saltsGrad + ">" + salts);

        return nutrients;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public List<String> getEnergyVisual(Player player) {
        PlayerData playerData = getPlayerData(player);
        double energy = Math.floor(playerData.getEnergy());
        double grad = (energy / 20) - 1;

        // green - green - green - yellow - red
        StringBuilder energyBar = new StringBuilder("<transition:#02FF4E:#02FF4E:#02FF4E:#FF9F02:#FF0202:" + grad + ">");
        energyBar.append("|".repeat((int) Math.max(0, Math.ceil(energy))));
        energyBar.append(".".repeat((int) Math.max(0, 20 - Math.ceil(energy))));

        return Arrays.asList("<#F963F2><bold>" + this.lang.energy, energyBar.toString());
    }

    /**
     * Check if player is holding arrows in their offhand
     *
     * @param player The player to check
     * @return Whether or not the player has arrows in their offhand
     */
    public boolean isArrowOffHand(Player player) {
        Material mainHand = player.getInventory().getItemInMainHand().getType();
        Material offHand = player.getInventory().getItemInOffHand().getType();
        if (mainHand == Material.CROSSBOW)
            return offHand == Material.ARROW || offHand == Material.SPECTRAL_ARROW
                || offHand == Material.TIPPED_ARROW || offHand == Material.FIREWORK_ROCKET;
        return offHand == Material.ARROW || offHand == Material.SPECTRAL_ARROW || offHand == Material.TIPPED_ARROW;
    }

}
