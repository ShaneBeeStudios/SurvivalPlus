package tk.shanebee.survival.commands;

import dev.jorel.commandapi.arguments.Argument;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.managers.PlayerManager;

public abstract class BaseCommand {

    protected final Survival plugin;
    protected final Config config;
    protected final Lang lang;
    protected final PlayerManager playerManager;

    public BaseCommand(Survival plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
        this.lang = plugin.getLang();
        this.playerManager = plugin.getPlayerManager();
    }

    abstract Argument<?> register();

}
