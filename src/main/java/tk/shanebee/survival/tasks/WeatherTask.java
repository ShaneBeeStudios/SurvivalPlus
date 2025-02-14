package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.item.Items;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class WeatherTask extends BukkitRunnable {

    private final double baseSpeed;
    private final double rainSpeed;
    private final double stormSpeed;
    private final double snowSpeed;
    private final double snowstormSpeed;

    public WeatherTask(Survival plugin) {
        Config config = plugin.getSurvivalConfig();
        this.baseSpeed = config.MECHANICS_WEATHER_SPEED_BASE;
        this.rainSpeed = config.MECHANICS_WEATHER_SPEED_RAIN;
        this.stormSpeed = config.MECHANICS_WEATHER_SPEED_STORM;
        this.snowSpeed = config.MECHANICS_WEATHER_SPEED_SNOW;
        this.snowstormSpeed = config.MECHANICS_WEATHER_SPEED_SNOWSTORM;
        this.runTaskTimer(plugin, 20, 10);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            handleWeather(player);
        }
    }

    private void handleWeather(Player player) {
        World world = player.getWorld();
        GameMode mode = player.getGameMode();
        if (world.getEnvironment() == Environment.NORMAL && (mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE)) {
            if (isInSnowstorm(player) && !hasSnowBoots(player)) {
                setWalkSpeed(player, this.snowstormSpeed);
            } else if (isOnSnow(player) && !hasSnowBoots(player)) {
                setWalkSpeed(player, this.snowSpeed);
            } else if (isInStorm(player) && !hasRainBoots(player)) {
                setWalkSpeed(player, this.stormSpeed);
            } else if (player.isInRain() && !hasRainBoots(player)) {
                setWalkSpeed(player, this.rainSpeed);
            } else {
                setWalkSpeed(player, this.baseSpeed);
            }
        } else {
            setWalkSpeed(player, this.baseSpeed);
        }
    }

    private boolean isOnSnow(Player player) {
        Block block = player.getLocation().getBlock();
        Block blockBelow = block.getRelative(BlockFace.DOWN);
        Material at = block.getType();
        Material down = blockBelow.getType();
        Material down2 = blockBelow.getRelative(BlockFace.DOWN).getType();
        if (at == Material.SNOW) {
            return true;
        } else if (at == Material.AIR) {
            return down == Material.SNOW || down == Material.SNOW_BLOCK || down2 == Material.SNOW || down2 == Material.SNOW_BLOCK;
        }
        return false;
    }

    private boolean isInSnowstorm(Player player) {
        World world = player.getWorld();
        Block block = player.getLocation().getBlock();
        double temp = block.getTemperature();
        byte lightFromSky = block.getLightFromSky();

        return world.hasStorm() && temp < 0.15 && lightFromSky == 15;
    }

    private boolean isInStorm(Player player) {
        return player.isInRain() && player.getWorld().isThundering();
    }

    private void setWalkSpeed(Player player, double speed) {
        AttributeInstance attribute = player.getAttribute(Attribute.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.setBaseValue(speed);
        }
    }

    private boolean hasRainBoots(Player player) {
        ItemStack boots = player.getInventory().getBoots();
        return boots != null && Items.RAIN_BOOTS.is(boots);
    }

    private boolean hasSnowBoots(Player player) {
        ItemStack boots = player.getInventory().getBoots();
        return boots != null && Items.SNOW_BOOTS.is(boots);
    }

}
