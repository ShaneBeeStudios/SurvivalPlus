package com.shanebeestudios.survival.api.data;

import com.shanebeestudios.survival.api.util.Utils;

public enum Placeholders {
    PLAYER_HEALTH("player_health", "Player's health"),
    PLAYER_HUNGER("player_hunger", "Player's hunger"),
    PLAYER_SATURATION("player_saturation", "Player's saturation"),
    PLAYER_HUNGER_TOTAL("player_hunger_total", "Player's total hunger (including saturation)"),
    PLAYER_HUNGER_BAR_1("player_hunger_bar_1", "Shows player's hunger bar (hunger part)"),
    PLAYER_HUNGER_BAR_2("player_hunger_bar_2", "Shows player's hunger bar (saturation part)"),
    PLAYER_THIRST("player_thirst", "Player's thirst"),
    PLAYER_THIRST_BAR_1("player_thirst_bar_1", "Shows player's thirst bar (top part - first half out of 40)"),
    PLAYER_THIRST_BAR_2("player_thirst_bar_2", "Shows player's thirst bar (bottom part - second half out of 40)"),
    PLAYER_ENERGY("player_energy", "Player's energy"),
    PLAYER_ENERGY_BAR("player_energy_bar", "Shows player's energy bar"),
    PLAYER_NUTRIENTS_CARBS("player_nutrients_carbs", "Player's nutrients carbs"),
    PLAYER_NUTRIENTS_PROTEINS("player_nutrients_proteins", "Player's nutrients proteins"),
    PLAYER_NUTRIENTS_VITAMINS("player_nutrients_vitamins", "Player's nutrients vitamins"),
    PLAYER_NUTRIENTS_CARBS_BAR("player_nutrients_carbs_bar", "Shows player's nutrients carbs bar"),
    PLAYER_NUTRIENTS_PROTEINS_BAR("player_nutrients_proteins_bar", "Shows player's nutrients proteins bar"),
    PLAYER_NUTRIENTS_VITAMINS_BAR("player_nutrients_vitamins_bar", "Shows player's nutrients vitamins bar"),
    ;

    private final String key;
    private final String description;

    Placeholders(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public boolean is(String identifier) {
        return identifier.equalsIgnoreCase(this.key);
    }

    public static void debug() {
        Utils.logMini("PAPI Placeholders:");
        for (Placeholders value : Placeholders.values()) {
            Utils.logMini("<grey> - <white>'<aqua>survival_plus_%s<white>' = '<yellow>%s<white>'", value.key, value.description);
        }
    }

}
