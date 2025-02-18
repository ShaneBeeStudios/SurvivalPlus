package com.shanebeestudios.survival.managers;

import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.item.Item;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager for Merchant Recipes
 */
public class LootManager {

    private final Config config;

    public LootManager(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
    }

    /**
     * Update a merchants recipes
     * <p>Replaces existing MerchantRecipes with ones that use custom {@link Items}</p>
     *
     * @param merchant Merchant to update
     */
    public void updateMerchant(Merchant merchant) {
        for (int i = 0; i < merchant.getRecipes().size(); i++) {
            MerchantRecipe merchantRecipe = merchant.getRecipe(i);
            Material result = merchantRecipe.getResult().getType();
            LootReplacements lootReplacements = LootReplacements.getByMaterial(result);
            if (lootReplacements != null && canUpdate(result)) {
                merchant.setRecipe(i, lootReplacements.updateRecipe(merchantRecipe));
            }
        }
    }

    public void updateLoot(List<ItemStack> loot) {
        for (int i = 0; i < loot.size(); i++) {
            ItemStack itemStack = loot.get(i);
            Material type = itemStack.getType();
            LootReplacements replacement = LootReplacements.getByMaterial(type);
            if (replacement != null && canUpdate(type)) {
                loot.set(i, replacement.items.getItemStack());
            }
        }
    }

    private boolean canUpdate(Material material) {
        return switch (material) {
            case CHAINMAIL_HELMET, CHAINMAIL_CHESTPLATE, CHAINMAIL_LEGGINGS, CHAINMAIL_BOOTS ->
                this.config.mechanics_reinforced_armor;
            case GOLDEN_HELMET, GOLDEN_CHESTPLATE, GOLDEN_LEGGINGS, GOLDEN_BOOTS,
                 IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS,
                 DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS, DIAMOND_BOOTS,
                 NETHERITE_HELMET, NETHERITE_CHESTPLATE, NETHERITE_LEGGINGS, NETHERITE_BOOTS ->
                this.config.mechanics_slow_armor;
            case WOODEN_HOE -> this.config.survival_sickle_flint;
            case IRON_HOE -> this.config.survival_sickle_iron;
            case STONE_HOE -> this.config.survival_sickle_stone;
            case DIAMOND_HOE -> this.config.survival_sickle_diamond;
            case WOODEN_AXE, WOODEN_PICKAXE -> this.config.survival_enabled;
            default -> false;
        };
    }

    /**
     * Loot/Merchant overrides
     * <p>These will take vanilla LootTables/MerchantRecipes and replace them with custom {@link Items}s</p>
     */
    public enum LootReplacements {
        GOLDEN_HELMET(Material.GOLDEN_HELMET, Items.GOLDEN_CROWN),
        GOLDEN_CHESTPLATE(Material.GOLDEN_CHESTPLATE, Items.GOLDEN_GUARD),
        GOLDEN_LEGGINGS(Material.GOLDEN_LEGGINGS, Items.GOLDEN_GREAVES),
        GOLDEN_BOOTS(Material.GOLDEN_BOOTS, Items.GOLDEN_SABATONS),
        IRON_HELMET(Material.IRON_HELMET, Items.IRON_HELMET),
        IRON_CHESTPLATE(Material.IRON_CHESTPLATE, Items.IRON_CHESTPLATE),
        IRON_LEGGINGS(Material.IRON_LEGGINGS, Items.IRON_LEGGINGS),
        IRON_BOOTS(Material.IRON_BOOTS, Items.IRON_BOOTS),
        DIAMOND_HELMET(Material.DIAMOND_HELMET, Items.DIAMOND_HELMET),
        DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, Items.DIAMOND_CHESTPLATE),
        DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, Items.DIAMOND_LEGGINGS),
        DIAMOND_BOOTS(Material.DIAMOND_BOOTS, Items.DIAMOND_BOOTS),
        NETHERITE_HELMET(Material.NETHERITE_HELMET, Items.NETHERITE_HELMET),
        NETHERITE_CHESTPLATE(Material.NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE),
        NETHERITE_LEGGINGS(Material.NETHERITE_LEGGINGS, Items.NETHERITE_LEGGINGS),
        NETHERITE_BOOTS(Material.NETHERITE_BOOTS, Items.NETHERITE_BOOTS),
        REINFORCED_LEATHER_HELMET(Material.CHAINMAIL_HELMET, Items.REINFORCED_LEATHER_HELMET),
        REINFORCED_LEATHER_TUNIC(Material.CHAINMAIL_CHESTPLATE, Items.REINFORCED_LEATHER_TUNIC),
        REINFORCED_LEATHER_TROUSERS(Material.CHAINMAIL_LEGGINGS, Items.REINFORCED_LEATHER_TROUSERS),
        REINFORCED_LEATHER_BOOTS(Material.CHAINMAIL_BOOTS, Items.REINFORCED_LEATHER_BOOTS),
        FLINT_SICKLE(Material.WOODEN_HOE, Items.FLINT_SICKLE),
        STONE_SICKLE(Material.STONE_HOE, Items.STONE_SICKLE),
        IRON_SICKLE(Material.IRON_HOE, Items.IRON_SICKLE),
        DIAMOND_SICKLE(Material.DIAMOND_HOE, Items.DIAMOND_SICKLE),
        HATCHET(Material.WOODEN_AXE, Items.HATCHET),
        MATTOCK(Material.WOODEN_PICKAXE, Items.MATTOCK);

        private final Material material;
        private final Item items;
        private static final Map<Material, LootReplacements> recipeByMaterialMap;

        static {
            recipeByMaterialMap = new HashMap<>();
            for (LootReplacements lootReplacements : values()) {
                recipeByMaterialMap.put(lootReplacements.material, lootReplacements);
            }
        }

        LootReplacements(Material material, Item items) {
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
        public static LootReplacements getByMaterial(Material material) {
            if (recipeByMaterialMap.containsKey(material)) {
                return recipeByMaterialMap.get(material);
            }
            return null;
        }

    }

}
