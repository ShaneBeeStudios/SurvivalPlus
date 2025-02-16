package tk.shanebee.survival.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import tk.shanebee.survival.config.Lang;

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

    /**
     * Send a colored string to a Player
     * <p>
     * Does not require ChatColor methods
     * </p>
     *
     * @param player The player to send a colored string to
     * @param msg    The string to send including color codes
     */
    public static void sendColoredMsg(CommandSender player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void sendColoredMini(CommandSender player, String format, Object... args) {
        String f = String.format(format, args);
        player.sendMessage(MINI_MESSAGE.deserialize(f));
    }

    public static Component getMini(String format, Object... args) {
        String msg = args != null ? String.format(format, args) : format;
        return MINI_MESSAGE.deserialize(msg);
    }

    public static String reverseComponent(Component component) {
        return COMPONENT_SERIALIZER.serialize(component);
    }

    /**
     * Send a colored console message
     * <p>This will NOT include plugin prefix</p>
     *
     * @param msg Message to send
     */
    public static void sendColoredConsoleMsg(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    /**
     * Log a message to console
     * <p>This will include plugin prefix</p>
     *
     * @param msg Message to log to console
     */
    public static void log(String msg) {
        Lang lang = SurvivalPlugin.getInstance().getLang();
        String prefix = "&7[&bSurvival&3Plus&7] ";
        if (lang != null) {
            prefix = lang.prefix;
        }
        sendColoredConsoleMsg(prefix + msg);
    }

    /**
     * Log a formatted message to console
     * <p>Formatted in the same style as {@link String#format(String, Object...)}
     * <br>This will include plugin prefix</p>
     *
     * @param format  Message format
     * @param objects Objects in format
     */
    public static void log(String format, Object... objects) {
        log(String.format(format, objects));
    }

    /**
     * Log a message to console
     * <p>This will include plugin prefix</p>
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
     * Log a formatted message to console
     * <p>Formatted in the same style as {@link String#format(String, Object...)}
     * <br>This will include plugin prefix</p>
     *
     * @param format  Message format
     * @param objects Objects in format
     */
    public static void logMini(String format, Object... objects) {
        logMini(String.format(format, objects));
    }

    /**
     * Gets a colored string
     *
     * @param string The string including color codes/HEX color codes
     * @return Returns a formatted string
     */
    public static String getColoredString(String string) {
        if (isRunningMinecraft(1, 16)) {
            Matcher matcher = HEX_PATTERN.matcher(string);
            while (matcher.find()) {
                final net.md_5.bungee.api.ChatColor hexColor = net.md_5.bungee.api.ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
                final String before = string.substring(0, matcher.start());
                final String after = string.substring(matcher.end());
                string = before + hexColor + after;
                matcher = HEX_PATTERN.matcher(string);
            }
        }
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', string);
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
