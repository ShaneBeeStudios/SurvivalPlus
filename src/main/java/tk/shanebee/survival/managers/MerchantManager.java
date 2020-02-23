package tk.shanebee.survival.managers;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for Merchant Recipes
 */
@SuppressWarnings("unused") // TODO remove once done
public class MerchantManager {

    private final Config config;

    public MerchantManager(Survival plugin) {
        this.config = plugin.getSurvivalConfig();
    }

    /**
     * Update a merchants recipes
     * <p>Replaces existing MerchantRecipes with ones that use custom {@link Items}</p>
     *
     * @param entity Merchant to update
     */
    public void updateRecipes(Entity entity) {
        if (entity instanceof Merchant) {
            Merchant merchant = ((Merchant) entity);

            for (int i = 0; i < merchant.getRecipes().size(); i++) {
                MerchantRecipe merchantRecipe = merchant.getRecipe(i);
                Material result = merchantRecipe.getResult().getType();
                Recipe recipe = Recipe.getByMaterial(result);
                if (recipe != null && canUpdate(result)) {
                    merchant.setRecipe(i, recipe.updateRecipe(merchantRecipe));
                }
            }
        }
    }

    private boolean canUpdate(Material material) {
        switch (material) {
            case LEATHER_HELMET:
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_BOOTS:
                if (!this.config.MECHANICS_REINFORCED_ARMOR) {
                    return false;
                }
            case IRON_HELMET:
            case IRON_CHESTPLATE:
            case IRON_LEGGINGS:
            case IRON_BOOTS:
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
                if (!this.config.MECHANICS_SLOW_ARMOR) {
                    return false;
                }
            case STONE_HOE:
                if (!this.config.SURVIVAL_SICKLE_STONE) {
                    return false;
                }
            case DIAMOND_HOE:
                if (!this.config.SURVIVAL_SICKLE_DIAMOND) {
                    return false;
                }
        }
        return true;
    }

    /**
     * Merchant recipes overrides
     * <p>These will take vanilla recipes and replace them with custom {@link Items}</p>
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
        REINFORCED_LEATHER_HELMET(Material.LEATHER_HELMET, Items.REINFORCED_LEATHER_HELMET),
        REINFORCED_LEATHER_TUNIC(Material.LEATHER_CHESTPLATE, Items.REINFORCED_LEATHER_TUNIC),
        REINFORCED_LEATHER_TROUSERS(Material.LEATHER_LEGGINGS, Items.REINFORCED_LEATHER_TROUSERS),
        REINFORCED_LEATHER_BOOTS(Material.LEATHER_BOOTS, Items.REINFORCED_LEATHER_BOOTS),
        STONE_SICKLE(Material.STONE_HOE, Items.STONE_SICKLE),
        DIAMOND_SICKLE(Material.DIAMOND_HOE, Items.DIAMOND_SICKLE);

        private Material material;
        private Items item;
        private static Map<Material, Recipe> recipeByMaterialMap;

        static {
            recipeByMaterialMap = new HashMap<>();
            for (Recipe recipe : values()) {
                recipeByMaterialMap.put(recipe.material, recipe);
            }
        }

        Recipe(Material material, Items item) {
            this.material = material;
            this.item = item;
        }

        /**
         * Get an updated MerchantRecipe based on an existing MerchantRecipe
         *
         * @param oldRecipe Old MerchantRecipe to replace
         * @return Updated MerchantRecipe using custom items
         */
        public MerchantRecipe updateRecipe(MerchantRecipe oldRecipe) {
            ItemStack old = oldRecipe.getResult().clone();

            ItemManager.applyAttribute(old, this.item);
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
