package tk.shanebee.survival.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.item.Item;
import tk.shanebee.survival.item.Items;
import tk.shanebee.survival.util.ItemUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for Merchant Recipes
 */
public class MerchantManager {

    private final Config config;

    public MerchantManager(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
    }

    /**
     * Update a merchants recipes
     * <p>Replaces existing MerchantRecipes with ones that use custom {@link Items}</p>
     *
     * @param merchant Merchant to update
     */
    public void updateRecipes(Merchant merchant) {
        for (int i = 0; i < merchant.getRecipes().size(); i++) {
            MerchantRecipe merchantRecipe = merchant.getRecipe(i);
            Material result = merchantRecipe.getResult().getType();
            Recipe recipe = Recipe.getByMaterial(result);
            if (recipe != null && canUpdate(result)) {
                merchant.setRecipe(i, recipe.updateRecipe(merchantRecipe));
            }
        }
    }

    private boolean canUpdate(Material material) {
        return switch (material) {
            case CHAINMAIL_HELMET, CHAINMAIL_CHESTPLATE, CHAINMAIL_LEGGINGS, CHAINMAIL_BOOTS ->
                this.config.mechanics_reinforced_armor;
            case IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS, DIAMOND_HELMET, DIAMOND_CHESTPLATE,
                 DIAMOND_LEGGINGS, DIAMOND_BOOTS -> this.config.mechanics_slow_armor;
            case STONE_HOE -> this.config.survival_sickle_stone;
            case DIAMOND_HOE -> this.config.survival_sickle_diamond;
            default -> false;
        };
    }

    /**
     * Merchant recipes overrides
     * <p>These will take vanilla recipes and replace them with custom {@link Items}s</p>
     */
    public enum Recipe {
        IRON_HELMET(Material.IRON_HELMET, Items.IRON_HELMET),
        IRON_CHESTPLATE(Material.IRON_CHESTPLATE, Items.IRON_CHESTPLATE),
        IRON_LEGGINGS(Material.IRON_LEGGINGS, Items.IRON_LEGGINGS),
        IRON_BOOTS(Material.IRON_BOOTS, Items.IRON_BOOTS),
        DIAMOND_HELMET(Material.DIAMOND_HELMET, Items.DIAMOND_HELMET),
        DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, Items.DIAMOND_CHESTPLATE),
        DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, Items.DIAMOND_LEGGINGS),
        DIAMOND_BOOTS(Material.DIAMOND_BOOTS, Items.DIAMOND_BOOTS),
        REINFORCED_LEATHER_HELMET(Material.CHAINMAIL_HELMET, Items.REINFORCED_LEATHER_HELMET),
        REINFORCED_LEATHER_TUNIC(Material.CHAINMAIL_CHESTPLATE, Items.REINFORCED_LEATHER_TUNIC),
        REINFORCED_LEATHER_TROUSERS(Material.CHAINMAIL_LEGGINGS, Items.REINFORCED_LEATHER_TROUSERS),
        REINFORCED_LEATHER_BOOTS(Material.CHAINMAIL_BOOTS, Items.REINFORCED_LEATHER_BOOTS),
        STONE_SICKLE(Material.STONE_HOE, Items.STONE_SICKLE),
        DIAMOND_SICKLE(Material.DIAMOND_HOE, Items.DIAMOND_SICKLE);

        private final Material material;
        private final Item items;
        private static final Map<Material, Recipe> recipeByMaterialMap;

        static {
            recipeByMaterialMap = new HashMap<>();
            for (Recipe recipe : values()) {
                recipeByMaterialMap.put(recipe.material, recipe);
            }
        }

        Recipe(Material material, Item items) {
            this.material = material;
            this.items = items;
        }

        /**
         * Get an updated MerchantRecipe based on an existing MerchantRecipe
         *
         * @param oldRecipe Old MerchantRecipe to replace
         * @return Updated MerchantRecipe using custom items
         */
        public MerchantRecipe updateRecipe(MerchantRecipe oldRecipe) {
            ItemStack old = oldRecipe.getResult().clone();

            ItemUtils.applyEnchantments(old, this.items);
            MerchantRecipe recipe = new MerchantRecipe(old, oldRecipe.getUses(), oldRecipe.getMaxUses(),
                oldRecipe.hasExperienceReward(), oldRecipe.getVillagerExperience(),
                oldRecipe.getPriceMultiplier());
            recipe.setIngredients(oldRecipe.getIngredients());
            return recipe;
        }

        /**
         * Get a Recipe by material
         *
         * @param material Material to get recipe from
         * @return Recipe based on material
         */
        public static Recipe getByMaterial(Material material) {
            if (recipeByMaterialMap.containsKey(material)) {
                return recipeByMaterialMap.get(material);
            }
            return null;
        }

    }

}
