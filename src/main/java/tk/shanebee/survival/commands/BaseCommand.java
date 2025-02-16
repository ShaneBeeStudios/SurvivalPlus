package tk.shanebee.survival.commands;

import dev.jorel.commandapi.arguments.Argument;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.managers.PlayerManager;

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
