package tk.shanebee.survival.util;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.Metadatable;
import tk.shanebee.survival.SurvivalPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Utils {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer COMPONENT_SERIALIZER = LegacyComponentSerializer.legacySection();

    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f0-9]){6}>");

    /**
     * Get the drops for a certain material
     *
     * @param material Material that will be broken
     * @param grown    If the block is grown
     * @return List of materials this material will drop
     */
    public static List<Material> getDrops(Material material, Boolean grown) {
        List<Material> mat = new ArrayList<>();
        switch (material) {
            case PUMPKIN:
                mat.add(Material.PUMPKIN);
                break;
            case MELON_STEM:
                mat.add(Material.MELON_SEEDS);
                break;
            case MELON:
                mat.add(Material.MELON_SLICE);
                break;
            case PUMPKIN_STEM:
                mat.add(Material.PUMPKIN_SEEDS);
                break;
            case CHORUS_FLOWER:
                mat.add(Material.CHORUS_FLOWER);
                break;
            case CARROTS:
                mat.add(Material.CARROT);
                break;
            case POTATOES:
                mat.add(Material.POTATO);
                break;
            case BEETROOTS:
                if (grown) {
                    mat.add(Material.BEETROOT);
                }
                mat.add(Material.BEETROOT_SEEDS);
                break;
            case WHEAT:
                if (grown) {
                    mat.add(Material.WHEAT);
                }
                mat.add(Material.WHEAT_SEEDS);
                break;
            case SWEET_BERRY_BUSH:
                mat.add(Material.SWEET_BERRIES);
                break;
            case COCOA:
                mat.add(Material.COCOA_BEANS);
                break;
            default:
                mat.add(Material.AIR);
        }
        return mat;
    }

    public static Component getMini(String format, Object... args) {
        String msg = args != null ? String.format(format, args) : format;
        return MINI_MESSAGE.deserialize(msg);
    }

    public static void sendColoredMini(Audience receiver, String format, Object... args) {
        String f = String.format(format, args);
        receiver.sendMessage(MINI_MESSAGE.deserialize(f));
    }

    /**
     * Convert a component into a string
     *
     * @param component Component to convert
     * @return String version of component
     */
    public static String reverseComponent(Component component) {
        return COMPONENT_SERIALIZER.serialize(component);
    }

    /**
     * Log a prefixed message to console
     *
     * @param msg Message to log to console
     */
    public static void logMini(String msg) {
        SurvivalPlugin plugin = SurvivalPlugin.getInstance();
        String prefix = "<grey>[<aqua>Survival<dark_aqua>Plus<grey>] ";
        if (plugin != null && plugin.getLang() != null) {
            prefix = plugin.getLang().prefix;
        }
        sendColoredMini(Bukkit.getConsoleSender(), prefix + msg);
    }

    /**
     * Log a prefixed formatted message to console
     * <p>Formatted in the same style as {@link String#format(String, Object...)}
     *
     * @param format  Message format
     * @param objects Objects in format
     */
    public static void logMini(String format, Object... objects) {
        logMini(String.format(format, objects));
    }

    /**
     * Spawn a particle at a location for all players
     *
     * @param location The location to spawn a particle at
     * @param particle The particle to spawn
     * @param amount   The amount of particles
     * @param offsetX  Offset by x
     * @param offsetY  Offset by y
     * @param offsetZ  Offset by z
     */
    public static void spawnParticle(Location location, Particle particle, int amount, double offsetX, double offsetY, double offsetZ) {
        assert location.getWorld() != null;
        location.getWorld().spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ);
    }

    /**
     * Spawn a particle at a location for a player
     *
     * @param location The location to spawn a particle at
     * @param particle The particle to spawn
     * @param amount   The amount of particles
     * @param offsetX  Offset by x
     * @param offsetY  Offset by y
     * @param offsetZ  Offset by z
     * @param player   The player to spawn a particle for
     */
    public static void spawnParticle(Location location, Particle particle, int amount, double offsetX, double offsetY, double offsetZ, Player player) {
        player.spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ);
    }

    /**
     * Gets the minutes a player has played on the server
     *
     * @param player The player to check
     * @return The number of minutes they have played on the server
     */
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public static int getMinutesPlayed(Player player) {
        int played = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        return Math.round(played / 1200);
    }

    /**
     * Check if server is running a minimum Minecraft version
     *
     * @param major Major version to check (Most likely just going to be 1)
     * @param minor Minor version to check
     * @return True if running this version or higher
     */
    public static boolean isRunningMinecraft(int major, int minor) {
        return isRunningMinecraft(major, minor, 0);
    }

    /**
     * Check if server is running a minimum Minecraft version
     *
     * @param major    Major version to check (Most likely just going to be 1)
     * @param minor    Minor version to check
     * @param revision Revision to check
     * @return True if running this version or higher
     */
    public static boolean isRunningMinecraft(int major, int minor, int revision) {
        String[] version = Bukkit.getServer().getBukkitVersion().split("-")[0].split("\\.");
        int maj = Integer.parseInt(version[0]);
        int min = Integer.parseInt(version[1]);
        int rev;
        try {
            rev = Integer.parseInt(version[2]);
        } catch (Exception ignore) {
            rev = 0;
        }
        return maj > major || min > minor || (min == minor && rev >= revision);
    }

    public static boolean isRunningSpigot() {
        return classExists("org.spigotmc.CustomTimingsHandler");
    }

    /**
     * Check if a class exists
     *
     * @param className The {@link Class#getCanonicalName() canonical name} of the class
     * @return True if the class exists
     */
    public static boolean classExists(final String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    /**
     * Check if a method exists
     *
     * @param c              Class the method belongs to
     * @param methodName     Name of method
     * @param parameterTypes Parameter types for this method
     * @return True if the method exists
     */
    public static boolean methodExists(final Class<?> c, final String methodName, final Class<?>... parameterTypes) {
        try {
            c.getDeclaredMethod(methodName, parameterTypes);
            return true;
        } catch (NoSuchMethodException ex) {
            return false;
        }
    }

    /**
     * Check if this entity is a Citizens NPC
     *
     * @param entity Entity to check
     * @return True if entity is an NPC
     */
    public static boolean isCitizensNPC(Metadatable entity) {
        return entity.hasMetadata("NPC");
    }

    /**
     * Get a {@link NamespacedKey} linked to this plugin
     *
     * @param key Key to create
     * @return New NamespacedKey linked to this plugin
     */
    public static NamespacedKey getNamespacedKey(String key) {
        return new NamespacedKey("survival_plus", key);
    }

    /**
     * Check if the player is at the highest block (exposed to sun)
     *
     * @param player Player to check
     * @return True if player is exposed to sun
     */
    public static boolean isAtHighest(Player player) {
        Location location = player.getLocation();
        World world = player.getWorld();
        return location.getY() > world.getHighestBlockYAt(location);
    }

}
