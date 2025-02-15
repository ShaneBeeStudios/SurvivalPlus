package tk.shanebee.survival.data;

/**
 * Player nutrient types
 */
public enum Nutrient {

	CARBS("Carbs"),
	PROTEIN("Protein"),
	VITAMINS("Vitamins");

	private final String name;

	Nutrient(String nutrient){
		name = nutrient;
	}

	public String getName() {
		return name;
	}

}
