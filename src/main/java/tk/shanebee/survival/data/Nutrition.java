package tk.shanebee.survival.data;

import org.bukkit.Material;

public enum Nutrition {

	PUMPKIN_PIE(300, 50, 60, Material.PUMPKIN_PIE),
	RABBIT_STEW(200, 225, 240, Material.RABBIT_STEW),
	BREAD(300, 25, 12, Material.BREAD),
	POTATO(25, 0, 10, Material.POTATO),
	BAKED_POTATO(200, 0, 60, Material.BAKED_POTATO),
	POISONOUS_POTATO(50, 0, 8, Material.POISONOUS_POTATO),
	APPLE(50, 0, 70, Material.APPLE),
	GOLDEN_APPLE(50, 0, 70, Material.GOLDEN_APPLE),
	ENCHANTED_GOLDEN_APPLE(50, 0, 70, Material.ENCHANTED_GOLDEN_APPLE),
	SWEET_BERRIES(40, 0, 60, Material.SWEET_BERRIES),
	CHORUS_FRUIT(25, 0, 35, Material.CHORUS_FRUIT),
	MELON(25, 0, 35, Material.MELON),
	MUSHROOM_STEW(0, 50, 200, Material.MUSHROOM_STEM),
	BEETROOT_SOUP(0, 50, 200, Material.BEETROOT_SOUP),
	COOKIE(107, 11, 3, Material.COOKIE),
	MILK_BUCKET(0, 250, 0, Material.MILK_BUCKET),
	BEETROOT(0, 0, 35, Material.BEETROOT),
	CARROT(0, 0, 105, Material.CARROT),
	GOLDEN_CARROT(0, 0, 25, Material.GOLDEN_CARROT),
	COOKED_COD(0, 225, 0, Material.COOKED_COD),
	COOKED_SALMON(0, 225, 0, Material.COOKED_SALMON),
	COOKED_BEEF(0, 200, 0, Material.COOKED_BEEF),
	COOKED_CHICKEN(0, 200, 0, Material.COOKED_CHICKEN),
	COOKED_MUTTON(0, 200, 0, Material.COOKED_MUTTON),
	COOKED_PORKCHOP(0, 200, 0, Material.COOKED_PORKCHOP),
	COOKED_RABBIT(0, 200, 0, Material.COOKED_RABBIT),
	SALMON(0, 75, 0, Material.SALMON),
	COD(0, 75, 0, Material.COD),
	PUFFERFISH(0, 75, 0, Material.PUFFERFISH),
	TROPICAL_FISH(0, 75, 0, Material.TROPICAL_FISH),
	BEEF(0, 50, 0, Material.BEEF),
	CHICKEN(0, 50, 0, Material.CHICKEN),
	MUTTON(0, 50, 0, Material.MUTTON),
	PORKCHOP(0, 50, 0, Material.PORKCHOP),
	RABBIT(0, 50, 0, Material.RABBIT),
	SPIDER_EYE(0, 50, 0, Material.SPIDER_EYE),
	ROTTEN_FLESH(0, 25, 25, Material.ROTTEN_FLESH),
	DRIED_KELP(15, 50, 50, Material.DRIED_KELP),
	CAKE(171, 114, 3, Material.CAKE);

	private int carbs;
	private int proteins;
	private int vitamins;
	private Material material;

	Nutrition(int carbs, int proteins, int vitamins, Material material) {
		this.carbs = carbs;
		this.proteins = proteins;
		this.vitamins = vitamins;
		this.material = material;
	}

	public int getCarbs() {
		return carbs;
	}

	public int getProteins() {
		return proteins;
	}

	public int getVitamins() {
		return vitamins;
	}

	public Material getMaterial() {
		return material;
	}

	public static Nutrition getByMaterial(Material material) {
		for (Nutrition nutrition : values()) {
			if (nutrition.getMaterial() == material) {
				return nutrition;
			}
		}
		return null;
	}

}
