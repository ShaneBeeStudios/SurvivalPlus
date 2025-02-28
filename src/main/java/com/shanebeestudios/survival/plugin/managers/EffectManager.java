package com.shanebeestudios.survival.plugin.managers;

import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.plugin.tasks.tool.Valkyrie;

public class EffectManager {

    private final SurvivalPlugin plugin;
    private final Config config;

    // Effect Tasks
    private Valkyrie valkyrie = null;

    public EffectManager(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
        loadEffects();
    }

    private void loadEffects() {
        if (config.legendary_valkyrie)
            this.valkyrie = new Valkyrie(plugin);
    }

    /**
     * Stop all effect tasks
     */
    @SuppressWarnings("unused")
    public void cancelTasks() {
        if (valkyrie != null)
            valkyrie.cancel();
    }

}
