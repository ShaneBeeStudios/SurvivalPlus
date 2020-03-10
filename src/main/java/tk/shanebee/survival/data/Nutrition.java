package tk.shanebee.survival.data;

import org.bukkit.Material;
import tk.shanebee.survival.item.Item;

/**
 * Nutritional values for foods
 */
public enum Nutrition {

	// FRUITS AND VEGGIES
	POTATO(25, 0, 10, Material.POTATO),
	BAKED_POTATO(200, 0, 60, Material.BAKED_POTATO),
	POISONOUS_POTATO(50, 0, 8, Material.POISONOUS_POTATO),
	APPLE(50, 0, 70, Material.APPLE),
	GOLDEN_APPLE(50, 0, 70, Material.GOLDEN_APPLE),
	ENCHANTED_GOLDEN_APPLE(50, 0, 70, Material.ENCHANTED_GOLDEN_APPLE),
	CARROT(0, 0, 105, Material.CARROT),
	GOLDEN_CARROT(0, 0, 25, Material.GOLDEN_CARROT),
	CHORUS_FRUIT(25, 0, 35, Material.CHORUS_FRUIT),
	MELON_SLICE(25, 0, 35, Material.MELON_SLICE),
	BEETROOT(0, 0, 35, Material.BEETROOT),
	DRIED_KELP(15, 50, 50, Material.DRIED_KELP),
	SWEET_BERRIES(40, 0, 60, Material.SWEET_BERRIES),

	// PREPARED FOODS
	BREAD(300, 25, 12, Material.BREAD),
	PUMPKIN_PIE(300, 50, 60, Material.PUMPKIN_PIE),
	RABBIT_STEW(200, 225, 240, Material.RABBIT_STEW),
	MUSHROOM_STEW(0, 50, 200, Material.MUSHROOM_STEW),
	SUSPICIOUS_STEW(0, 50, 210, Material.SUSPICIOUS_STEW),
	BEETROOT_SOUP(0, 50, 200, Material.BEETROOT_SOUP),
	COOKIE(107, 11, 3, Material.COOKIE),
	CAKE(171, 114, 3, Material.CAKE),

	// MEATS
	BEEF(0, 50, 0, Material.BEEF),
	CHICKEN(0, 50, 0, Material.CHICKEN),
	MUTTON(0, 50, 0, Material.MUTTON),
	PORKCHOP(0, 50, 0, Material.PORKCHOP),
	RABBIT(0, 50, 0, Material.RABBIT),
	COOKED_BEEF(0, 200, 0, Material.COOKED_BEEF),
	COOKED_CHICKEN(0, 200, 0, Material.COOKED_CHICKEN),
	COOKED_MUTTON(0, 200, 0, Material.COOKED_MUTTON),
	COOKED_PORKCHOP(0, 200, 0, Material.COOKED_PORKCHOP),
	COOKED_RABBIT(0, 200, 0, Material.COOKED_RABBIT),
	COD(0, 75, 0, Material.COD),
	SALMON(0, 75, 0, Material.SALMON),
	PUFFERFISH(0, 75, 0, Material.PUFFERFISH),
	TROPICAL_FISH(0, 75, 0, Material.TROPICAL_FISH),
	COOKED_COD(0, 225, 0, Material.COOKED_COD),
	COOKED_SALMON(0, 225, 0, Material.COOKED_SALMON),

	// MISC
	SPIDER_EYE(0, 50, 0, Material.SPIDER_EYE),
	ROTTEN_FLESH(0, 25, 25, Material.ROTTEN_FLESH),
	MILK_BUCKET(0, 250, 0, Material.MILK_BUCKET);

	private final int carbs;
	private final int proteins;
	private final int vitamins;
	private Material material = null;
	private Item item = null;

	Nutrition(int carbs, int proteins, int vitamins, Material material) {
		this.carbs = carbs;
		this.proteins = proteins;
		this.vitamins = vitamins;
		this.material = material;
	}

	@SuppressWarnings("unused") // Will be used in the future for custom food items
	Nutrition(int carbs, int proteins, int vitamins, Item item) {
		this.carbs = carbs;
		this.proteins = proteins;
		this.vitamins = vitamins;
		this.item = item;
	}

	/** Get the carbs for this nutritional item
	 * @return Carbs for this nutritional item
	 */
	public int getCarbs() {
		return carbs;
	}

	/** Get the proteins for this nutritional item
	 * @return Proteins for this nutritional item
	 */
	public int getProteins() {
		return proteins;
	}

	/** Get the vitamins for this nutritional item
	 * @return Vitamins for this nutritional item
	 */
	public int getVitamins() {
		return vitamins;
	}

	/** Get the material of this nutritional item
	 * @return Material of this nutritional item
	 */
	public Material getMaterial() {
		return material;
	}

	/** Get the item of this nutritional item
	 * @return Item of this nutritional item
	 * @deprecated Currently unused
	 */
	@Deprecated
	public Item getItem() {
		return item;
	}

	/** Get from a material
	 * @param material Material to grab a nutrition from
	 * @return Nutrition for material (null if nutrition does not exist for this material)
	 */
	public static Nutrition getByMaterial(Material material) {
		for (Nutrition nutrition : values()) {
			if (nutrition.getMaterial() == material) {
				return nutrition;
			}
		}
		return null;
	}

	/** Get from a material
	 * @param item Item to grab a nutrition from
	 * @return Nutrition for item (null if nutrition does not exist for this item)
	 * @deprecated Currently unused
	 */
	@Deprecated
	public static Nutrition getByItem(Item item) {
		for (Nutrition nutrition : values()) {
			if (nutrition.getItem() == item) {
				return nutrition;
			}
		}
		return null;
	}

}
