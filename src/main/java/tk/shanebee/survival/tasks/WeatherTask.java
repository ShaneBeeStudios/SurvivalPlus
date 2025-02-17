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
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.item.Items;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class WeatherTask extends BukkitRunnable {

    private final Config config;

    public WeatherTask(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
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
                setWalkSpeed(player, this.config.mechanics_weather_speed_snowstorm);
            } else if (isOnSnow(player) && !hasSnowBoots(player)) {
                setWalkSpeed(player, this.config.mechanics_weather_speed_snow);
            } else if (isInStorm(player) && !hasRainBoots(player)) {
                setWalkSpeed(player, this.config.mechanics_weather_speed_storm);
            } else if (player.isInRain() && !hasRainBoots(player)) {
                setWalkSpeed(player, this.config.mechanics_weather_speed_rain);
            } else {
                setWalkSpeed(player, this.config.mechanics_weather_speed_base);
            }
        } else {
            setWalkSpeed(player, this.config.mechanics_weather_speed_base);
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
