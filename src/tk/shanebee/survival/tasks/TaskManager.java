package tk.shanebee.survival.tasks;

import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Config;

/**
 * Internal task manager
 */
public class TaskManager {

	public TaskManager(Survival plugin) {
		Config config = plugin.getSurvivalConfig();
		if (config.MECHANICS_FATIGUE_LEVEL) {
			new FatigueDrain(plugin);
			if (config.MECHANICS_FATIGUE_REFRESH_TIME > 0) {
				new FatigueRefresh(plugin);
			}
		}
		if (config.MECHANICS_THIRST_ENABLED) {
			new ThirstDrain(plugin);
			new ThirstEffect(plugin);
			if (!config.MECHANICS_SCOREBOARD && plugin.getAlertInterval() > 0) {
				new ThirstAlert(plugin);
			}
		}
		if (config.MECHANICS_FOOD_DIVERSITY) {
			new NutrientsDrain(plugin);
			new NutrientsEffect(plugin);
			if (!config.MECHANICS_SCOREBOARD && plugin.getAlertInterval() > 0) {
				new NutrientsAlert(plugin);
			}
		}
	}

}
