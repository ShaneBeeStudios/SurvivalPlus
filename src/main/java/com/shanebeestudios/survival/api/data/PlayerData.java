package com.shanebeestudios.survival.api.data;

import com.shanebeestudios.survival.api.events.EnergyLevelChangeEvent;
import com.shanebeestudios.survival.api.events.ThirstLevelChangeEvent;
import com.shanebeestudios.survival.api.util.Math;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Holder of data for player
 * <p>You can get an instance of PlayerData from <b>{@link PlayerManager}</b></p>
 */
@SuppressWarnings({"unused", "FieldCanBeLocal", "SameParameterValue"})
@SerializableAs("PlayerData")
public class PlayerData implements ConfigurationSerializable {

    private final Config config = SurvivalPlugin.getInstance().getSurvivalConfig();
    private final Player player;
    private final UUID uuid;
    private Map<String, Location> compassMap = new HashMap<>();

    // Nutrients
    private int carbs;
    private int proteins;
    private int vitamins;
    private double energy;
    private double thirst;

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
    private boolean localChat = false;

    // Scoreboard info
    private boolean score_hunger = true;
    private boolean score_thirst = true;
    private boolean score_energy = true;
    private boolean score_nutrients = true;

    /**
     * @hidden Shouldn't be using this constructor outside the plugin
     */
    @ApiStatus.Internal
    public PlayerData(Player player, double thirst, int proteins, int carbs, int vitamins, double energy) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.thirst = Math.clamp(thirst, 0, 40);
        this.proteins = proteins;
        this.carbs = carbs;
        this.vitamins = vitamins;
        this.energy = Math.clamp(energy, 0, 20);
    }

    /**
     * Get the player from this data
     *
     * @return Player from this data
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get the UUID of the player from this data
     *
     * @return UUID of player from this data
     */
    public UUID getUuid() {
        return this.uuid;
    }

    /**
     * Get the thirst of this data
     *
     * @return Thirst of this data
     */
    public double getThirst() {
        return this.thirst;
    }

    /**
     * Set the thirst for this data
     *
     * @param thirst Level of thirst to set
     */
    public void setThirst(double thirst) {
        this.thirst = Math.clamp(thirst, 0, 40);
    }

    /**
     * Increase the thirst for this data
     *
     * @param change Level of thirst to add
     */
    public void increaseThirst(double change) {
        int immunityMinutes = this.config.mechanics_thirst_immunity_minutes;
        if (change < 0 && immunityMinutes > 0) {
            int secondsPlayed = this.player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;
            if (immunityMinutes > secondsPlayed) return;
        }
        ThirstLevelChangeEvent thirstEvent = new ThirstLevelChangeEvent(this.player, change, getThirst() + change);
        if (!thirstEvent.callEvent()) return;
        setThirst(this.thirst + change);
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
     * @param change    Level of increase
     */
    public void increaseNutrient(Nutrient nutrient, int change) {
        int immunityMinutes = this.config.mechanics_food_immunity_minutes;
        if (change < 0 && immunityMinutes > 0) {
            int secondsPlayed = this.player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;
            if (immunityMinutes > secondsPlayed) return;
        }
        switch (nutrient) {
            case PROTEIN:
                this.proteins = Math.clamp(this.proteins + change, 0, this.config.mechanics_food_max_level);
                break;
            case CARBS:
                this.carbs = Math.clamp(this.carbs + change, 0, this.config.mechanics_food_max_level);
                break;
            case VITAMINS:
                this.vitamins = Math.clamp(this.vitamins + change, 0, this.config.mechanics_food_max_level);
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
     * @param change Energy amount to increase
     */
    public void increaseEnergy(double change) {
        int immunityMinutes = this.config.mechanics_energy_immunity_minutes;
        if (change < 0 && immunityMinutes > 0) {
            int secondsPlayed = this.player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;
            if (immunityMinutes > secondsPlayed) return;
        }
        EnergyLevelChangeEvent energyEvent = new EnergyLevelChangeEvent(player, change, getEnergy() + change);
        if (!energyEvent.callEvent()) return;
        setEnergy(this.energy + change);
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
        if (!this.config.mechanics_compass_waypoint_worlds) {
            this.compassMap.clear();
        }
        this.compassMap.put(world.getName(), location);
        this.player.setCompassTarget(location);
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
     * @hidden
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
     * @hidden
     */
    @SuppressWarnings("unchecked")
    public static PlayerData deserialize(Map<String, Object> args) {
        UUID uuid = UUID.fromString(args.get("uuid").toString());
        double thirst = getDouble(args, "thirst", 20.0);
        double energy = getDouble(args, "energy", 20.0);
        int proteins = getInt(args, "nutrients.proteins", 500);
        int carbs = getInt(args, "nutrients.carbs", 500);
        int vitamins = getInt(args, "nutrients.vitamins", 500);

        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            throw new IllegalArgumentException("Player not found for uuid: " + uuid);
        }

        PlayerData data = new PlayerData(player, thirst, proteins, carbs, vitamins, energy);

        boolean localChat = getBool(args, "local-chat", false);
        data.setLocalChat(localChat);

        boolean score_hunger = getBool(args, "score.hunger", true);
        boolean score_thirst = getBool(args, "score.thirst", true);
        boolean score_energy = getBool(args, "score.energy", true);
        boolean score_nutrients = getBool(args, "score.nutrients", false);
        data.setInfoDisplayed(score_hunger, score_thirst, score_energy, score_nutrients);

        if (args.containsKey("compass")) {
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
        return this.player.getFoodLevel() + this.player.getSaturation();
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
        this.player.setFoodLevel((int) hun);
        this.player.setSaturation((float) sat);
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
