package tk.shanebee.survival.data;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Holder of data for player
 * <p>You can get an instance of PlayerData from <b>{@link tk.shanebee.survival.managers.PlayerManager}</b></p>
 */
@SuppressWarnings({"unused", "FieldCanBeLocal", "SameParameterValue"})
public class PlayerData implements ConfigurationSerializable {

	private UUID uuid;
	private int thirst;

	// Nutrients
	private int proteins;
	private int carbs;
	private int salts;
	private int fatigue;

	// Dunno yet
	private boolean localChat = false;

	// Stats
	private int charge = 0;
	private int charging = 0;
	private int spin = 0;
	private int dualWield = 0;
	private int dualWieldMsg = 0;
	private int healing = 0;
	private int healTimes = 0;
	private int recurveFiring = 0;
	private int recurveCooldown = 0;

	// Scoreboard info
	private boolean score_hunger = false;
	private boolean score_thirst = false;
	private boolean score_fatigue = false;
	private boolean score_nutrients = false;

	public PlayerData(OfflinePlayer player, int thirst, int proteins, int carbs, int salts, int fatigue) {
		this(player.getUniqueId(), thirst, proteins, carbs, salts, fatigue);
	}

	public PlayerData(UUID uuid, int thirst, int proteins, int carbs, int salts, int fatigue) {
		this.uuid = uuid;
		this.thirst = thirst;
		this.proteins = proteins;
		this.carbs = carbs;
		this.salts = salts;
		this.fatigue = fatigue;
	}

	/** Get the player from this data
	 * @return Player from this data
	 */
	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	/** Get the UUID of the player from this data
	 * @return UUID of player from this data
	 */
	public UUID getUuid() {
		return uuid;
	}

	/** Get the thirst of this data
	 * @return Thirst of this data
	 */
	public int getThirst() {
		return thirst;
	}

	/** Set the thirst for this data
	 * @param thirst Level of thirst to set
	 */
	public void setThirst(int thirst) {
		this.thirst = Math.min(thirst, 40);
	}

	/** Increase the thirst for this data
	 * @param thirst Level of thirst to add
	 */
	public void increaseThirst(int thirst) {
		this.thirst += thirst;
	}

	/** Get the nutrients from this data
	 * @param nutrient Nutrient to get
	 * @return Level of the nutrient
	 */
	public int getNutrient(Nutrient nutrient) {
		switch (nutrient) {
			case PROTEIN:
				return proteins;
			case CARBS:
				return carbs;
			case SALTS:
				return salts;
			default:
				throw new IllegalStateException("Unexpected value: " + nutrient);
		}
	}

	/** Set the nutrients for this data
	 * @param nutrient Nutrient to set
	 * @param value Level to set
	 */
	public void setNutrient(Nutrient nutrient, int value) {
		switch (nutrient) {
			case PROTEIN:
				this.proteins = value;
				break;
			case CARBS:
				this.carbs = value;
				break;
			case SALTS:
				this.salts = value;
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + nutrient);
		}
	}

	/** Increase a nutrient for this data
	 * @param nutrient Nutrient to increase
	 * @param value Level of increase
	 */
	public void increaseNutrient(Nutrient nutrient, int value) {
		switch (nutrient) {
			case PROTEIN:
				this.proteins += value;
				break;
			case CARBS:
				this.carbs += value;
				break;
			case SALTS:
				this.salts += value;
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + nutrient);
		}
	}

	/** Get the fatigue of this data
	 * @return Fatigue of this data
	 */
	public int getFatigue() {
		return fatigue;
	}

	/** Set the fatigue level for this data
	 * <p>Value must be between 0 and 4</p>
	 * @param fatigue Level to set
	 */
	public void setFatigue(int fatigue) {
		if (fatigue > 4) {
			this.fatigue = 4;
			return;
		} else if (fatigue < 0) {
			this.fatigue = 0;
			return;
		}
		this.fatigue = fatigue;
	}

	/** Set a stat for this data
	 * @param stat Stat to set
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

	// Internal use only - set all stats at once
	private void setStats(int charge, int charging, int spin, int dual, int dualMsg, int healing, int healTime, int recurveFiring, int recurveCooldown) {
		this.charge = charge;
		this.charging = charging;
		this.spin = spin;
		this.dualWield = dual;
		this.dualWieldMsg = dualMsg;
		this.healing = healing;
		this.healTimes = healTime;
		this.recurveFiring = recurveFiring;
		this.recurveCooldown = recurveCooldown;
	}

	/** Get a stat from this data
	 * @param stat Stat to retrieve
	 * @return Value of stat
	 */
	public int getStat(Stat stat) {
		switch (stat) {
			case CHARGE:
				return this.charge;
			case CHARGING:
				return this.charging;
			case SPIN:
				return this.spin;
			case DUAL_WIELD:
				return this.dualWield;
			case HEALING:
				return this.healing;
			case HEAL_TIMES:
				return this.healTimes;
			case RECURVE_FIRING:
				return this.recurveFiring;
			case RECURVE_COOLDOWN:
				return this.recurveCooldown;
			default:
				throw new IllegalStateException("Unexpected value: " + stat);
		}
	}

	/** Set whether the player is using local chat
	 * @param localChat Whether the player is using local chat
	 */
	public void setLocalChat(boolean localChat) {
		this.localChat = localChat;
	}

	/** Check if the player is using local chat
	 * @return True if local chat is activated
	 */
	public boolean isLocalChat() {
		return localChat;
	}

	/** Get the visibility of a specific healthboard feature
	 * @param info Healthboard feature
	 * @return True if this feature is visible on the player's scoreboard
	 */
	public boolean showInfo(Info info) {
		switch (info) {
			case HUNGER:
				return score_hunger;
			case THIRST:
				return score_thirst;
			case FATIGUE:
				return score_fatigue;
			case NUTRIENTS:
				return score_nutrients;
			default:
				throw new IllegalStateException("Unexpected value: " + info);
		}
	}

	/** Set the visibility of a specific healthboard feature
	 * @param info Healthboard feature to show
	 * @param visible Whether the feature should be visible or not
	 */
	public void setShowInfo(Info info, boolean visible) {
		switch (info) {
			case HUNGER:
				this.score_hunger = visible;
				break;
			case THIRST:
				this.score_thirst = visible;
				break;
			case FATIGUE:
				this.score_fatigue = visible;
				break;
			case NUTRIENTS:
				this.score_nutrients = visible;
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + info);
		}
	}

	/** Set the visibility of all healthboard features
	 * <p>This is mainly used internally for data transfers</p>
	 * @param hunger Whether hunger should be shown on the player's healthboard
	 * @param thirst Whether thirst should be shown on the player's healthboard
	 * @param fatigue Whether fatigue should be shown on the player's healthboard
	 * @param nutrients Whether nutrients should be shown on the player's healthboard
	 */
	public void setShowInfo(boolean hunger, boolean thirst, boolean fatigue, boolean nutrients) {
		this.score_hunger = hunger;
		this.score_thirst = thirst;
		this.score_fatigue = fatigue;
		this.score_nutrients = nutrients;
	}

	/** Internal serializer for yaml config
	 * @return Map for config
	 */
	@SuppressWarnings("NullableProblems")
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("uuid", uuid.toString());
		result.put("thirst", thirst);
		result.put("fatigue", fatigue);
		result.put("nutrients.proteins", proteins);
		result.put("nutrients.carbs", carbs);
		result.put("nutrients.salts", salts);
		result.put("local-chat", localChat);
		result.put("stats.charge", charge);
		result.put("stats.charging", charging);
		result.put("stats.spin", spin);
		result.put("stats.dualWield", dualWield);
		result.put("stats.dualWieldMsg", dualWieldMsg);
		result.put("stats.healing", healing);
		result.put("stats.healTimes", healTimes);
		result.put("stats.recurveFiring", recurveFiring);
		result.put("stats.recurveCooldown", recurveCooldown);
		result.put("score.hunger", score_hunger);
		result.put("score.thirst", score_thirst);
		result.put("score.fatigue", score_fatigue);
		result.put("score.nutrients", score_nutrients);
		return result;
	}

	/** Internal deserializer for yaml config
	 * @param args Args from yaml config
	 * @return New PlayerData loaded from config
	 */
	public static PlayerData deserialize(Map<String, Object> args) {
		UUID uuid = UUID.fromString(args.get("uuid").toString());
		int thirst = ((Integer) args.get("thirst"));
		int fatigue = ((Integer) args.get("fatigue"));
		int proteins = ((Integer) args.get("nutrients.proteins"));
		int carbs = ((Integer) args.get("nutrients.carbs"));
		int salts = ((Integer) args.get("nutrients.salts"));

		PlayerData data = new PlayerData(uuid, thirst, proteins, carbs, salts, fatigue);

		boolean localChat = getBool(args, "local-chat", false);
		data.setLocalChat(localChat);

		int charge = getInt(args, "stats.charge", 0);
		int charging = getInt(args, "stats.charging", 0);
		int spin = getInt(args, "stats.spin", 0);
		int dual = getInt(args, "stats.dualWield", 0);
		int dualMsg = getInt(args, "stats.dualWieldMsg", 0);
		int healing = getInt(args, "stats.healing", 0);
		int healTimes = getInt(args, "stats.healTimes", 0);
		int recurveFiring = getInt(args, "stats.recurveFiring", 0);
		int recurveCooldown = getInt(args, "stats.recurveCooldown", 0);
		data.setStats(charge, charging, spin, dual, dualMsg, healing,
				healTimes, recurveFiring, recurveCooldown);

		boolean score_hunger = getBool(args, "score.hunger", true);
		boolean score_thirst = getBool(args, "score.thirst", true);
		boolean score_fatigue = getBool(args, "score.fatigue", true);
		boolean score_nutrients = getBool(args, "score.nutrients", false);
		data.setShowInfo(score_hunger, score_thirst, score_fatigue, score_nutrients);

		return data;
	}

	// Methods for grabbing sections from map, defaults if section isn't set
	private static int getInt(Map<String, Object> args, String val, int def) {
		if (args.containsKey(val))
			return ((int) args.get(val));
		return def;
	}

	private static boolean getBool(Map<String, Object> args, String val, boolean def) {
		if (args.containsKey(val))
			return (boolean) args.get(val);
		return def;
	}

}
