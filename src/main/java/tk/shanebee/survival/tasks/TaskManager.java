package tk.shanebee.survival.tasks;

import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Config;

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
			new NutrientsDrain(plugin);
			new NutrientsEffect(plugin);
			if (!config.mechanics_status_scoreboard && alertInterval > 0) {
				new NutrientsAlert(plugin);
			}
		}

		if (config.MECHANICS_WEATHER_ENABLED) {
            new WeatherTask(plugin);
        }
        // Thirst
        if (config.mechanics_thirst_enabled) {
            new ThirstDrain(plugin);
            if (!config.mechanics_status_scoreboard && alertInterval > 0) {
                new ThirstAlert(plugin);
            }
        }
		if (config.mechanics_thirst_nether_drain > 0) {
		    new ThirstDrainNether(plugin);
        }
		if (config.mechanics_thirst_heat_drain > 0) {
		    new ThirstDrainHeat(plugin);
        }
	}

}
