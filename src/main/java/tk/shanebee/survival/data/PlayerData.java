package tk.shanebee.survival.data;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

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
	private int proteins;
	private int carbs;
	private int salts;
	private int fatigue;

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
	public OfflinePlayer getPlayer() {
		return Bukkit.getOfflinePlayer(uuid);
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

	/** Internal serializer for yaml config
	 * @return Map for config
	 */
	@SuppressWarnings("NullableProblems")
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("uuid", uuid.toString());
		result.put("thirst", thirst);
		result.put("proteins", proteins);
		result.put("carbs", carbs);
		result.put("salts", salts);
		result.put("fatigue", fatigue);
		return result;
	}

	/** Internal deserializer for yaml config
	 * @param args Args from yaml config
	 * @return New PlayerData loaded from config
	 */
	public static PlayerData deserialize(Map<String, Object> args) {
		UUID uuid = UUID.fromString(args.get("uuid").toString());
		int thirst = ((Integer) args.get("thirst"));
		int proteins = ((Integer) args.get("proteins"));
		int carbs = ((Integer) args.get("carbs"));
		int salts = ((Integer) args.get("salts"));
		int fatigue = ((Integer) args.get("fatigue"));
		return new PlayerData(uuid, thirst, proteins, carbs, salts, fatigue);
	}

}
