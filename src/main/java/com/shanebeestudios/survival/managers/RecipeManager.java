package com.shanebeestudios.survival.managers;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.item.Recipes;

public class RecipeManager {

    private final Config config;

    public RecipeManager(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
    }

    /**
     * Load all custom server recipes
     */
    public void loadCustomRecipes() {
        removeRecipes();
        Recipes.init(this.config);
    }

    private void removeRecipes() {
        if (this.config.survival_enabled) {
            removeRecipeByKey("campfire");
            removeRecipeByKey("chest");
            if (this.config.survival_torch) {
                removeRecipeByKey("torch");
            }
            if (this.config.recipes_furnace) {
                removeRecipeByKey("furnace");
            }
            if (this.config.recipes_workbench) {
                removeRecipeByKey("crafting_table");
            }
        }
        if (this.config.survival_remove_wood_tools) {
            removeRecipeByKey("wooden_sword");
            removeRecipeByKey("wooden_hoe");
            removeRecipeByKey("wooden_shovel");
            removeRecipeByKey("wooden_pickaxe");
            removeRecipeByKey("wooden_axe");
        }
        if (this.config.mechanics_reduced_iron_nugget) {
            removeRecipeByKey("iron_ingot");
            removeRecipeByKey("iron_ingot_from_nuggets");
            removeRecipeByKey("iron_nugget");
            removeRecipeByKey("iron_nugget_from_smelting");
        }
        if (this.config.mechanics_reduced_gold_nugget) {
            removeRecipeByKey("gold_ingot");
            removeRecipeByKey("gold_ingot_from_nuggets");
            removeRecipeByKey("gold_nugget");
            removeRecipeByKey("gold_nugget_from_smelting");

        }
        if (this.config.mechanics_slow_armor) {
            removeRecipeByKey("diamond_helmet");
            removeRecipeByKey("diamond_chestplate");
            removeRecipeByKey("diamond_leggings");
            removeRecipeByKey("diamond_boots");
            removeRecipeByKey("iron_helmet");
            removeRecipeByKey("iron_chestplate");
            removeRecipeByKey("iron_leggings");
            removeRecipeByKey("iron_boots");
        }
        if (this.config.mechanics_snowball_revamp) {
            removeRecipeByKey("snow");
            removeRecipeByKey("snow_block");
        }
        if (this.config.mechanics_farming_products_cookie) {
            removeRecipeByKey("cookie");
        }
        if (this.config.mechanics_farming_products_bread) {
            removeRecipeByKey("bread");
        }
        if (this.config.legendary_gold_armor_buff) {
            removeRecipeByKey("golden_helmet");
            removeRecipeByKey("golden_chestplate");
            removeRecipeByKey("golden_boots");
            removeRecipeByKey("golden_leggings");
        }
        if (this.config.legendary_blaze_sword) {
            removeRecipeByKey("golden_sword");
        }
        if (this.config.legendary_giant_blade) {
            removeRecipeByKey("golden_hoe");
        }
        if (this.config.legendary_quartz_pickaxe) {
            removeRecipeByKey("golden_pickaxe");
        }
        if (this.config.legendary_obsidian_mace) {
            removeRecipeByKey("golden_shovel");
        }
        if (this.config.legendary_valkyrie) {
            removeRecipeByKey("golden_axe");
        }
        if (this.config.recipes_granite) {
            removeRecipeByKey("granite");
        }
        if (this.config.recipes_andesite) {
            removeRecipeByKey("andesite");
        }
        if (this.config.recipes_diorite) {
            removeRecipeByKey("diorite");
        }
        if (this.config.recipes_leather_bard) {
            removeRecipeByKey("leather_horse_armor");
        }
        if (this.config.recipes_fishing_rod) {
            removeRecipeByKey("fishing_rod");
        }
        if (this.config.mechanics_compass_waypoint) {
            removeRecipeByKey("compass");
        }
        if (this.config.recipes_packed_ice) {
            removeRecipeByKey("packed_ice");
        }
    }

    /**
     * Unlock all custom recipes for a player
     *
     * @param player Player to unlock recipes for
     */
    public void unlockAllRecipes(Player player) {
        player.discoverRecipes(Recipes.getAllRecipeKeys());
    }

    /**
     * Remove a vanilla Minecraft recipe from the server
     *
     * @param recipeKey Recipe to remove
     */
    @SuppressWarnings("WeakerAccess")
    public void removeRecipeByKey(String recipeKey) {
        Bukkit.removeRecipe(NamespacedKey.minecraft(recipeKey));
    }

}
