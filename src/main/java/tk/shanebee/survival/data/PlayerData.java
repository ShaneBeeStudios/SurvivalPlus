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
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class PlayerData implements ConfigurationSerializable {

	private UUID uuid;
	private int thirst;

	// Nutrients
	private int proteins;
	private int carbs;
	private int salts;
	private int fatigue;

	// Dunno yet
	private int chat = 0;

	// Stats
	private int charge = 0;
	private int charging = 0;
	private int spin = 0;
	private int dualWield = 0;
	private int healing = 0;
	private int healTimes = 0;
	private int recurveFiring = 0;
	private int recurveCooldown = 0;

	// Scoreboard
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
	private void setStats(int charge, int charging, int spin, int dual, int healing, int healTime, int recurveFiring, int recurveCooldown) {
		this.charge = charge;
		this.charging = charging;
		this.spin = spin;
		this.dualWield = dual;
		this.healing = healing;
		this.healTimes = healTime;
		this.recurveFiring = recurveFiring;
		this.recurveCooldown = recurveCooldown;
	}

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

	public void setChat(int chat) {
		this.chat = chat;
	}

	public int getChat() {
		return chat;
	}

	/** Get the visibility of a specific scoreboard feature
	 * @param score Scoreboard feature
	 * @return True if this feature is visible on the player's scoreboard
	 */
	public boolean showInfo(Info score) {
		switch (score) {
			case HUNGER:
				return score_hunger;
			case THIRST:
				return score_thirst;
			case FATIGUE:
				return score_fatigue;
			case NUTRIENTS:
				return score_nutrients;
			default:
				throw new IllegalStateException("Unexpected value: " + score);
		}
	}

	/** Set the visibility of a specific scoreboard feature
	 * @param score Scoreboard feature to show
	 * @param visible Whether the feature should be visible or not
	 */
	public void setShowInfo(Info score, boolean visible) {
		switch (score) {
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
				throw new IllegalStateException("Unexpected value: " + score);
		}
	}

	/** Set the visibility of all scoreboard features
	 * <p>This is mainly used internally for data transfers</p>
	 * @param hunger Whether hunger should be shown on the player's scoreboard
	 * @param thirst Whether thirst should be shown on the player's scoreboard
	 * @param fatigue Whether fatigue should be shown on the player's scoreboard
	 * @param nutrients Whether nutrients should be shown on the player's scoreboard
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
		result.put("stats.charge", charge);
		result.put("stats.charging", charging);
		result.put("stats.spin", spin);
		result.put("stats.dualWield", dualWield);
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

		int charge = ((Integer) args.get("stats.charge"));
		int charging = ((Integer) args.get("stats.charging"));
		int spin = ((Integer) args.get("stats.spin"));
		int dual = ((Integer) args.get("stats.dualWield"));
		int healing = ((Integer) args.get("stats.healing"));
		int healTimes = ((Integer) args.get("stats.healTimes"));
		int recurveFiring = ((Integer) args.get("stats.recurveFiring"));
		int recurveCooldown = ((Integer) args.get("stats.recurveCooldown"));
		data.setStats(charge, charging, spin, dual, healing, healTimes, recurveFiring, recurveCooldown);

		boolean score_hunger = (boolean) args.get("score.hunger");
		boolean score_thirst = (boolean) args.get("score.thirst");
		boolean score_fatigue = (boolean) args.get("score.fatigue");
		boolean score_nutrients = (boolean) args.get("score.nutrients");
		data.setShowInfo(score_hunger, score_thirst, score_fatigue, score_nutrients);

		return data;
	}

}
