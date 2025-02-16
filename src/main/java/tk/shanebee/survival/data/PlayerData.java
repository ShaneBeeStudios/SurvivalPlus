package tk.shanebee.survival.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.util.Math;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Holder of data for player
 * <p>You can get an instance of PlayerData from <b>{@link tk.shanebee.survival.managers.PlayerManager}</b></p>
 */
@SuppressWarnings({"unused", "FieldCanBeLocal", "SameParameterValue"})
@SerializableAs("PlayerData")
public class PlayerData implements ConfigurationSerializable {

    private final Config config = Survival.getInstance().getSurvivalConfig();
    private final UUID uuid;
    private int thirst;
    private Map<String, Location> compassMap = new HashMap<>();

    // Nutrients
    private int carbs;
    private int proteins;
    private int vitamins;
    private double energy;

    // Dunno yet
    private boolean localChat = false;

    // Stats
    private int charge = 0;
    private int charging = 0;
    private int spin = 0;
    private int dualWield = 0;
    private final int dualWieldMsg = 0;
    private int healing = 0;
    private int healTimes = 0;
    private int recurveFiring = 0;
    private int recurveCooldown = 0;

    // Scoreboard info
    private boolean score_hunger = true;
    private boolean score_thirst = true;
    private boolean score_energy = true;
    private boolean score_nutrients = true;

    public PlayerData(OfflinePlayer player, int thirst, int proteins, int carbs, int vitamins, double energy) {
        this(player.getUniqueId(), thirst, proteins, carbs, vitamins, energy);
    }

    public PlayerData(UUID uuid, int thirst, int proteins, int carbs, int vitamins, double energy) {
        this.uuid = uuid;
        this.thirst = thirst;
        this.proteins = proteins;
        this.carbs = carbs;
        this.vitamins = vitamins;
        this.energy = energy;
    }

    /**
     * Get the player from this data
     *
     * @return Player from this data
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Get the UUID of the player from this data
     *
     * @return UUID of player from this data
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Get the thirst of this data
     *
     * @return Thirst of this data
     */
    public int getThirst() {
        return thirst;
    }

    /**
     * Set the thirst for this data
     *
     * @param thirst Level of thirst to set
     */
    public void setThirst(int thirst) {
        this.thirst = Math.clamp(thirst, 0, 40);
    }

    /**
     * Increase the thirst for this data
     *
     * @param thirst Level of thirst to add
     */
    public void increaseThirst(int thirst) {
        this.thirst = Math.clamp(this.thirst + thirst, 0, 40);
    }

    /**
     * Get the nutrients from this data
     *
     * @param nutrient Nutrient to get
     * @return Level of the nutrient
     */
    public int getNutrient(Nutrient nutrient) {
        return switch (nutrient) {
            case PROTEIN -> proteins;
            case CARBS -> carbs;
            case VITAMINS -> vitamins;
        };
    }

    /**
     * Set the nutrients for this data
     *
     * @param nutrient Nutrient to set
     * @param value    Level to set
     */
    public void setNutrient(Nutrient nutrient, int value) {
        switch (nutrient) {
            case PROTEIN:
                this.proteins = Math.clamp(value, 0, this.config.mechanics_food_max_level);
                break;
            case CARBS:
                this.carbs = Math.clamp(value, 0, this.config.mechanics_food_max_level);
                break;
            case VITAMINS:
                this.vitamins = Math.clamp(value, 0, this.config.mechanics_food_max_level);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + nutrient);
        }
    }

    /**
     * Set all the nutrients for this data
     *
     * @param carbs    Level of carbs to set
     * @param proteins Level of proteins to set
     * @param vitamins Level of vitamins to set
     */
    public void setNutrients(int carbs, int proteins, int vitamins) {
        setNutrient(Nutrient.CARBS, carbs);
        setNutrient(Nutrient.PROTEIN, proteins);
        setNutrient(Nutrient.VITAMINS, vitamins);
    }

    /**
     * Increase a nutrient for this data
     *
     * @param nutrient Nutrient to increase
     * @param value    Level of increase
     */
    public void increaseNutrient(Nutrient nutrient, int value) {
        switch (nutrient) {
            case PROTEIN:
                this.proteins = Math.clamp(this.proteins + value, 0, this.config.mechanics_food_max_level);
                break;
            case CARBS:
                this.carbs = Math.clamp(this.carbs + value, 0, this.config.mechanics_food_max_level);
                break;
            case VITAMINS:
                this.vitamins = Math.clamp(this.vitamins + value, 0, this.config.mechanics_food_max_level);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + nutrient);
        }
    }

    /**
     * Get the energy of this data
     *
     * @return Energy of this data
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * Set the energy level for this data
     * <p>Value must be between 0.0 and 20.0</p>
     *
     * @param energy Energy level to set
     */
    public void setEnergy(double energy) {
        this.energy = Math.clamp(energy, 0.0, 20.0);
    }

    /**
     * Increase the energy level for this data
     *
     * @param energy Energy amount to increase
     */
    public void increaseEnergy(double energy) {
        setEnergy(this.energy + energy);
    }

    /**
     * Set a stat for this data
     *
     * @param stat  Stat to set
     * @param value Value of stat to set
     */
    public void setStat(Stat stat, int value) {
        switch (stat) {
            case CHARGE:
                this.charge = value;
                break;
            case CHARGING:
                this.charging = value;
                break;
            case SPIN:
                this.spin = value;
                break;
            case DUAL_WIELD:
                this.dualWield = value;
                break;
            case HEALING:
                this.healing = value;
                break;
            case HEAL_TIMES:
                this.healTimes = value;
                break;
            case RECURVE_FIRING:
                this.recurveFiring = value;
                break;
            case RECURVE_COOLDOWN:
                this.recurveCooldown = value;
        }
    }

    /**
     * Get a stat from this data
     *
     * @param stat Stat to retrieve
     * @return Value of stat
     */
    public int getStat(Stat stat) {
        return switch (stat) {
            case CHARGE -> this.charge;
            case CHARGING -> this.charging;
            case SPIN -> this.spin;
            case DUAL_WIELD -> this.dualWield;
            case HEALING -> this.healing;
            case HEAL_TIMES -> this.healTimes;
            case RECURVE_FIRING -> this.recurveFiring;
            case RECURVE_COOLDOWN -> this.recurveCooldown;
            default -> throw new IllegalArgumentException("Unexpected value: " + stat);
        };
    }

    /**
     * Set whether the player is using local chat
     *
     * @param localChat Whether the player is using local chat
     */
    public void setLocalChat(boolean localChat) {
        this.localChat = localChat;
    }

    /**
     * Check if the player is using local chat
     *
     * @return True if local chat is activated
     */
    public boolean isLocalChat() {
        return localChat;
    }

    /**
     * Get the visibility of a specific healthboard info
     *
     * @param info Healthboard info
     * @return True if this info is displayed on the player's scoreboard
     */
    public boolean isInfoDisplayed(Info info) {
        return switch (info) {
            case HUNGER -> this.score_hunger;
            case THIRST -> this.score_thirst;
            case ENERGY -> this.score_energy;
            case NUTRIENTS -> this.score_nutrients;
            default -> throw new IllegalArgumentException("Unexpected value: " + info);
        };
    }

    /**
     * Set the visibility of a specific healthboard info
     *
     * @param info    Healthboard info to display
     * @param visible Whether the info should be displayed or not
     */
    public void setInfoDisplayed(Info info, boolean visible) {
        switch (info) {
            case HUNGER:
                this.score_hunger = visible;
                break;
            case THIRST:
                this.score_thirst = visible;
                break;
            case ENERGY:
                this.score_energy = visible;
                break;
            case NUTRIENTS:
                this.score_nutrients = visible;
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + info);
        }
    }

    /**
     * Set the visibility of all healthboard info
     * <p>This is mainly used internally for data transfers</p>
     *
     * @param hunger    Whether hunger should be displayed on the player's healthboard
     * @param thirst    Whether thirst should be displayed on the player's healthboard
     * @param energy    Whether energy should be displayed on the player's healthboard
     * @param nutrients Whether nutrients should be displayed on the player's healthboard
     */
    public void setInfoDisplayed(boolean hunger, boolean thirst, boolean energy, boolean nutrients) {
        this.score_hunger = hunger;
        this.score_thirst = thirst;
        this.score_energy = energy;
        this.score_nutrients = nutrients;
    }

    /**
     * Set a compass waypoint for this player data
     * <p>Will also update the player's compass target</p>
     *
     * @param location Location of waypoint
     */
    public void setCompassWaypoint(Location location) {
        World world = location.getWorld();
        if (world == null) return;
        if (!config.MECHANICS_COMPASS_WAYPOINT_WORLDS) {
            this.compassMap.clear();
        }
        this.compassMap.put(world.getName(), location);
        getPlayer().setCompassTarget(location);
    }

    /**
     * Get the compass waypoint for this player data
     *
     * @param world World to grab waypoint from
     * @return Location of waypoint
     */
    public Location getCompassWaypoint(World world) {
        if (this.compassMap.containsKey(world.getName())) {
            return this.compassMap.get(world.getName());
        }
        return null;
    }

    /**
     * Internal serializer for yaml config
     *
     * @return Map for config
     */
    @SuppressWarnings("NullableProblems")
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("uuid", this.uuid.toString());
        result.put("thirst", this.thirst);
        result.put("energy", this.energy);
        result.put("nutrients.proteins", this.proteins);
        result.put("nutrients.carbs", this.carbs);
        result.put("nutrients.vitamins", this.vitamins);
        result.put("local-chat", this.localChat);
        result.put("score.hunger", this.score_hunger);
        result.put("score.thirst", this.score_thirst);
        result.put("score.energy", this.score_energy);
        result.put("score.nutrients", this.score_nutrients);
        result.put("compass", this.compassMap);
        return result;
    }

    /**
     * Internal deserializer for yaml config
     *
     * @param args Args from yaml config
     * @return New PlayerData loaded from config
     */
    public static PlayerData deserialize(Map<String, Object> args) {
        UUID uuid = UUID.fromString(args.get("uuid").toString());
        int thirst = ((Integer) args.get("thirst"));
        double energy = getDouble(args, "energy", 20.0);
        int proteins = ((Integer) args.get("nutrients.proteins"));
        int carbs = ((Integer) args.get("nutrients.carbs"));
        int vitamins = ((Integer) args.get("nutrients.vitamins"));

        PlayerData data = new PlayerData(uuid, thirst, proteins, carbs, vitamins, energy);

        boolean localChat = getBool(args, "local-chat", false);
        data.setLocalChat(localChat);

        boolean score_hunger = getBool(args, "score.hunger", true);
        boolean score_thirst = getBool(args, "score.thirst", true);
        boolean score_energy = getBool(args, "score.energy", true);
        boolean score_nutrients = getBool(args, "score.nutrients", false);
        data.setInfoDisplayed(score_hunger, score_thirst, score_energy, score_nutrients);

        if (args.containsKey("compass")) {
            //noinspection unchecked
            data.compassMap = (Map<String, Location>) args.get("compass");
        }

        return data;
    }

    // Methods for grabbing sections from map, defaults if section isn't set
    private static int getInt(Map<String, Object> args, String val, int def) {
        if (args.containsKey(val))
            return ((int) args.get(val));
        return def;
    }

    private static double getDouble(Map<String, Object> args, String val, double def) {
        if (args.containsKey(val))
            return ((double) args.get(val));
        return def;
    }

    private static boolean getBool(Map<String, Object> args, String val, boolean def) {
        if (args.containsKey(val))
            return (boolean) args.get(val);
        return def;
    }

    /**
     * Get the hunger level of this player data
     * <p>This is a combination of food level + saturation level</p>
     *
     * @return Hunger level from this player data
     */
    public double getHunger() {
        Player player = getPlayer();
        return player.getFoodLevel() + player.getSaturation();
    }

    /**
     * Set the hunger level of this player data
     * <p>This is a combination of food level + saturation level</p>
     *
     * @param hunger Hunger level to set
     */
    public void setHunger(double hunger) {
        double sat = 0.0;
        double hun = 0.0;
        if (hunger > 40) {
            hun = 20;
            sat = 20;
        } else if (hunger > 20) {
            hun = 20;
            sat = hunger - 20;
        } else if (hunger >= 0) {
            hun = hunger;
        }
        Player player = getPlayer();
        player.setFoodLevel((int) hun);
        player.setSaturation((float) sat);
    }

    /**
     * Set a data value for this player data
     *
     * @param type  DataType to set
     * @param value Value to set
     */
    public void setData(DataType type, Number value) {
        switch (type) {
            case THIRST:
                setThirst(value.intValue());
                break;
            case ENERGY:
                setEnergy(value.doubleValue());
                break;
            case PROTEINS:
                setNutrient(Nutrient.PROTEIN, value.intValue());
                break;
            case CARBS:
                setNutrient(Nutrient.CARBS, value.intValue());
                break;
            case VITAMINS:
                setNutrient(Nutrient.VITAMINS, value.intValue());
                break;
            case HUNGER:
                setHunger(value.doubleValue());
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    /**
     * Get a data value from this player data
     *
     * @param type DataType to get
     * @return Value from this player data
     */
    public double getData(DataType type) {
        return switch (type) {
            case THIRST -> getThirst();
            case ENERGY -> getEnergy();
            case PROTEINS -> getNutrient(Nutrient.PROTEIN);
            case CARBS -> getNutrient(Nutrient.CARBS);
            case VITAMINS -> getNutrient(Nutrient.VITAMINS);
            case HUNGER -> getHunger();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }

    public enum DataType {
        THIRST,
        ENERGY,
        PROTEINS,
        VITAMINS,
        CARBS,
        HUNGER;

        public static DataType getByName(String name) {
            try {
                return valueOf(name.toUpperCase());
            } catch (Exception ignore) {
                return null;
            }
        }

        public static String[] getNames() {
            String[] names = new String[values().length];
            for (int i = 0; i < values().length; i++) {
                names[i] = values()[i].toString().toLowerCase(Locale.ROOT);
            }
            return names;
        }
    }

}
