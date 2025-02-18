package com.shanebeestudios.survival.commands;

import dev.jorel.commandapi.arguments.Argument;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.config.Lang;
import com.shanebeestudios.survival.managers.PlayerManager;

public abstract class BaseCommand {

    protected final SurvivalPlugin plugin;
    protected final Config config;
    protected final Lang lang;
    protected final PlayerManager playerManager;

    public BaseCommand(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
        this.lang = plugin.getLang();
        this.playerManager = plugin.getPlayerManager();
    }

    abstract Argument<?> register();

}
