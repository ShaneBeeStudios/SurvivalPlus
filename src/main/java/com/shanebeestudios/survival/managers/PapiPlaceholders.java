package com.shanebeestudios.survival.managers;

import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.data.Placeholders;
import com.shanebeestudios.survival.data.Nutrient;
import com.shanebeestudios.survival.data.PlayerData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class PapiPlaceholders extends PlaceholderExpansion {

    private final SurvivalPlugin plugin;
    private final PlayerManager playerManager;

    public PapiPlaceholders(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.playerManager = plugin.getPlayerManager();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "survival_plus";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getPluginMeta().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        PlayerData playerData = playerManager.getPlayerData(player);

        // Shows player's health, kinda useless but here it is
        if (Placeholders.PLAYER_HEALTH.is(identifier)) {
            return String.format("%.2f", player.getHealth());
        }
        // Shows a player's total hunger (including saturation)
        if (Placeholders.PLAYER_HUNGER_TOTAL.is(identifier)) {
            return String.valueOf(player.getFoodLevel() + player.getSaturation());
        }
        // Shows player's hunger
        if (Placeholders.PLAYER_HUNGER.is(identifier)) {
            return String.valueOf(player.getFoodLevel());
        }
        // Shows player's saturation
        if (Placeholders.PLAYER_SATURATION.is(identifier)) {
            return String.valueOf(player.getSaturation());
        }
        // Shows player's hunger bar (hunger part)
        if (Placeholders.PLAYER_HUNGER_BAR_1.is(identifier)) {
            return this.playerManager.getHungerVisual(player).get(1);
        }
        // Shows player's hunger bar (saturation part)
        if (Placeholders.PLAYER_HUNGER_BAR_2.is(identifier)) {
            return this.playerManager.getHungerVisual(player).get(2);
        }
        // Shows player's thirst
        if (Placeholders.PLAYER_THIRST.is(identifier)) {
            return String.valueOf(playerData.getThirst());
        }
        // Shows player's thirst bar (top part - first half out of 40)
        if (Placeholders.PLAYER_THIRST_BAR_1.is(identifier)) {
            return this.playerManager.getThirstVisual(player).get(1);
        }
        // Shows player's thirst bar (bottom part - second half out of 40)
        if (Placeholders.PLAYER_THIRST_BAR_2.is(identifier)) {
            return this.playerManager.getThirstVisual(player).get(2);
        }
        // Shows player's energy level (as a number)
        if (Placeholders.PLAYER_ENERGY.is(identifier)) {
            return String.format("%.2f", playerData.getEnergy());
        }
        // Shows player's energy level (as a colored bar)
        if (Placeholders.PLAYER_ENERGY_BAR.is(identifier)) {
            return this.playerManager.getEnergyVisual(player).get(1);
        }

        // Shows player's nutrients bars (<nutrient> <amount>)
        if (Placeholders.PLAYER_NUTRIENTS_CARBS_BAR.is(identifier)) {
            List<String> nutrientsVisual = this.playerManager.getNutrientsVisual(player);
            return nutrientsVisual.get(0) + " " + nutrientsVisual.get(3);
        }
        if (Placeholders.PLAYER_NUTRIENTS_PROTEINS_BAR.is(identifier)) {
            List<String> nutrientsVisual = this.playerManager.getNutrientsVisual(player);
            return nutrientsVisual.get(1) + " " + nutrientsVisual.get(4);
        }
        if (Placeholders.PLAYER_NUTRIENTS_VITAMINS_BAR.is(identifier)) {
            List<String> nutrientsVisual = this.playerManager.getNutrientsVisual(player);
            return nutrientsVisual.get(2) + " " + nutrientsVisual.get(5);
        }

        // Shows player's nutrients (just the <amount>)
        if (Placeholders.PLAYER_NUTRIENTS_CARBS.is(identifier)) {
            return String.valueOf(playerData.getNutrient(Nutrient.CARBS));
        }
        if (Placeholders.PLAYER_NUTRIENTS_PROTEINS.is(identifier)) {
            return String.valueOf(playerData.getNutrient(Nutrient.PROTEIN));
        }
        if (Placeholders.PLAYER_NUTRIENTS_VITAMINS.is(identifier)) {
            return String.valueOf(playerData.getNutrient(Nutrient.VITAMINS));
        }
        return null;
    }

}
