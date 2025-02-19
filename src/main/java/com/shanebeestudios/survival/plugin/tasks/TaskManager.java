package com.shanebeestudios.survival.plugin.tasks;

import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;

/**
 * Internal task manager
 */
public class TaskManager {

	public TaskManager(SurvivalPlugin plugin) {
		final int alertInterval = plugin.getSurvivalConfig().MECHANICS_ALERT_INTERVAL;
		Config config = plugin.getSurvivalConfig();
		if (config.mechanics_energy_enabled) {
		    new EnergyDrain(plugin);
        }
		if (config.mechanics_food_diversity_enabled) {
			new NutrientsEffect(plugin);
			if (!config.mechanics_status_scoreboard && alertInterval > 0) {
				new NutrientsAlert(plugin);
			}
		}

		if (config.mechanics_weather_enabled) {
            new WeatherTask(plugin);
        }
        // Thirst
        if (config.mechanics_thirst_enabled) {
            new ThirstTask(plugin);
            if (!config.mechanics_status_scoreboard && alertInterval > 0) {
                new ThirstAlert(plugin);
            }
        }
	}

}
