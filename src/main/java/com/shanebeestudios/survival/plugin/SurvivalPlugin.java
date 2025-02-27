package com.shanebeestudios.survival.plugin;

import com.shanebeestudios.survival.api.util.ItemTags;
import com.shanebeestudios.survival.plugin.commands.SurvivalCommand;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.plugin.config.Lang;
import com.shanebeestudios.survival.plugin.config.PlayerDataConfig;
import com.shanebeestudios.survival.api.data.PlayerData;
import com.shanebeestudios.survival.plugin.listeners.EventManager;
import com.shanebeestudios.survival.plugin.managers.EffectManager;
import com.shanebeestudios.survival.plugin.managers.LootManager;
import com.shanebeestudios.survival.plugin.managers.MessageManager;
import com.shanebeestudios.survival.plugin.managers.PapiPlaceholders;
import com.shanebeestudios.survival.plugin.managers.PlayerManager;
import com.shanebeestudios.survival.plugin.managers.RecipeManager;
import com.shanebeestudios.survival.plugin.managers.ScoreBoardManager;
import com.shanebeestudios.survival.plugin.tasks.TaskManager;
import com.shanebeestudios.survival.api.util.BlockTags;
import com.shanebeestudios.survival.api.util.Utils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.exceptions.UnsupportedVersionException;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Main plugin class
 */
@SuppressWarnings("UnstableApiUsage")
public class SurvivalPlugin extends JavaPlugin implements Listener {

    static {
        ConfigurationSerialization.registerClass(PlayerData.class);
    }

    private static SurvivalPlugin INSTANCE;

    // Lists & Map
    private Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    // Configs
    private Config config;
    private Lang lang;
    private PlayerDataConfig playerDataConfig;

    // Managers
    private EffectManager effectManager;
    private ScoreBoardManager scoreBoardManager;
    private PlayerManager playerManager;
    private TaskManager taskManager;
    private LootManager lootManager;
    private RecipeManager recipeManager;
    private MessageManager messageManager;

    // Other
    private boolean loaded = true;

    /**
     * @hidden
     */
    @Override
    public void onLoad() {
        try {
            CommandAPI.onLoad(new CommandAPIBukkitConfig(this)
                .setNamespace("survivalplus")
                .verboseOutput(false)
                .silentLogs(true)
                .skipReloadDatapacks(true));
        } catch (UnsupportedVersionException ignore) {
            Utils.logMini("CommandAPI does not support this version of Minecraft, will update soon.");
        }
    }

    /**
     * @hidden
     */
    public void onEnable() {
        INSTANCE = this;
        long time = System.currentTimeMillis();

        // VERSION CHECK
        if (!Utils.isRunningMinecraft(1, 21, 4)) {
            Utils.logMini("<red>-----------------------------------------------------------");
            Utils.logMini("<red>Your version is not supported: <aqua>" + Bukkit.getMinecraftVersion());
            Utils.logMini("<yellow>This plugin only works on Minecraft <aqua>1.16+");
            Utils.logMini("<red>-----------------------------------------------------------");
            loaded = false;
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // PAPER CHECK
        // This shouldn't really happen since we're using paper-plugin.yml
        if (!Utils.isRunningPaper()) {
            Utils.logMini("<red>-----------------------------------------------------------");
            Utils.logMini("<grey>Your server software is not supported: <red>" + Bukkit.getName());
            Utils.logMini("<grey>This plugin will only work on <green>Paper.");
            Utils.logMini("<red>-----------------------------------------------------------");
            loaded = false;
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // LOAD CONFIG FILES
        loadSettings(Bukkit.getConsoleSender());

        // LOAD RESOURCE PACK
        if (this.config.settings_resource_pack_enabled) {
            if (this.config.settings_resource_pack_url.isEmpty()) {
                Utils.logMini("<red>Resource Pack is not set! Plugin disabling");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            } else {
                Utils.logMini("<grey>Resource pack <green>enabled");
            }
        } else {
            Utils.logMini("<yellow>Resource Pack disabled");
        }

        // LOAD TAGS
        BlockTags.initialize();
        ItemTags.initialize();

        // LOAD MANAGERS
        this.playerManager = new PlayerManager(this, this.playerDataMap);
        this.effectManager = new EffectManager(this);
        this.taskManager = new TaskManager(this);
        this.scoreBoardManager = new ScoreBoardManager(this);
        this.lootManager = new LootManager(this);
        this.recipeManager = new RecipeManager(this);
        this.messageManager = new MessageManager(this);

        // LOAD PLAYER DATA - (during a reload if players are still online)
        playerDataLoader(true);
        this.scoreBoardManager.resetStatusScoreboard(config.mechanics_status_scoreboard);

        // LOAD PLACEHOLDERS
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PapiPlaceholders(this).register();
            Utils.logMini("<grey>PlaceholderAPI placeholders <green>enabled");
        }

        // REGISTER EVENTS & COMMANDS
        registerCommands();
        EventManager eventManager = new EventManager(this);
        eventManager.registerEvents();

        // LOAD CUSTOM RECIPES
        // This is a helper for other plugins that wipe custom recipes - secret hidden config
        if (this.config.recipe_delay > 0) {
            Utils.logMini("<grey>Custom recipe loading delayed... will load in <aqua>" + this.config.recipe_delay + "<grey> second[s]");
            Bukkit.getScheduler().runTaskLater(this, () -> {
                this.recipeManager.loadCustomRecipes();
                Utils.logMini("<grey>Custom recipes <green>loaded");
            }, this.config.recipe_delay * 20L);

        } else {
            this.recipeManager.loadCustomRecipes();
            Utils.logMini("<grey>Custom recipes <green>loaded");
        }

        // LOAD METRICS
        new Metrics(this, 24831);

        Utils.logMini("<green>Successfully loaded <grey>in " + (System.currentTimeMillis() - time) + " milliseconds");

        // BETA WARNING
        if (this.getPluginMeta().getVersion().contains("Beta")) {
            Utils.logMini("<yellow>YOU ARE RUNNING A BETA VERSION, PLEASE USE WITH CAUTION!");
        }
    }

    /**
     * @hidden
     */
    public void onDisable() {
        if (!loaded) return;
        Utils.logMini("<yellow>Shutting down");
        getServer().getScheduler().cancelTasks(this);

        // Unload player data (decrease chance of memory leak)
        playerDataLoader(false);

        //Avoid WorkbenchShare glitch
        if (this.config.mechanics_shared_workbench) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasMetadata("shared_workbench")) {
                    Block workbench = (p.getMetadata("shared_workbench").getFirst().value() instanceof Block) ? (Block)
                        p.getMetadata("shared_workbench").getFirst().value() : null;

                    if (workbench != null && workbench.getType() == Material.CRAFTING_TABLE) {
                        if (workbench.hasMetadata("shared_players"))
                            workbench.removeMetadata("shared_players", SurvivalPlugin.INSTANCE);
                        else
                            p.getOpenInventory().getTopInventory().clear();
                        p.closeInventory();
                    }
                    p.removeMetadata("shared_workbench", SurvivalPlugin.INSTANCE);
                }
            }
        }
        INSTANCE = null;
        Utils.logMini("<yellow>Successfully disabled");
    }

    private void playerDataLoader(boolean load) {
        int size = Bukkit.getOnlinePlayers().size();
        if (load) {
            // Load player data - if players are online (useful during reload)
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (this.playerDataConfig.hasPlayerDataFile(player)) {
                    this.playerManager.loadPlayerData(player);
                } else {
                    this.playerManager.createNewPlayerData(player);
                }
            }
            if (size > 0) {
                Utils.logMini("Loading player data for <aqua>" + size + " player" + (size != 1 ? "s" : ""));
            }
        } else {
            // Unload player data - if players are still online
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerManager.unloadPlayerData(player);
            }
            // Clear/delete player data map to prevent memory leaks
            this.playerDataMap.clear();
            this.playerDataMap = null;
            Utils.logMini("Unloading player data for <aqua>" + size + " player" + (size != 1 ? "s" : ""));
        }
    }

    /**
     * Load config settings
     *
     * @param sender The person/console loading config
     */
    public void loadSettings(CommandSender sender) {
        if (this.config == null) {
            this.config = new Config(this);
        }
        this.config.loadDefaultSettings();
        if (this.lang == null) {
            this.lang = new Lang(this, this.config.lang);
        }
        this.lang.loadLangFile(sender);
        this.playerDataConfig = new PlayerDataConfig(this);
    }

    private void registerCommands() {
        if (CommandAPI.isLoaded()) {
            CommandAPI.onEnable();
            new SurvivalCommand(this, "survival");
        }
    }

    /**
     * Get instance of this plugin
     *
     * @return Instance of this plugin
     */
    public static SurvivalPlugin getInstance() {
        return INSTANCE;
    }

    /**
     * Get the effect manager
     *
     * @return Instance of the effect manager
     */
    public EffectManager getEffectManager() {
        return this.effectManager;
    }

    /**
     * Get the scoreboard manager
     *
     * @return Instance of the scoreboard manager
     */
    public ScoreBoardManager getScoreboardManager() {
        return this.scoreBoardManager;
    }

    /**
     * Get the player manager
     *
     * @return Instance of the player manager
     */
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    /**
     * Get the task manager
     *
     * @return Instance of the task manager
     */
    @SuppressWarnings("unused")
    public TaskManager getTaskManager() {
        return this.taskManager;
    }

    /**
     * Get an instance of the loot manager
     *
     * @return Instance of the loot manager
     */
    public LootManager getLootManager() {
        return lootManager;
    }

    /**
     * Get an instance of the recipe manager
     *
     * @return Instance of recipe manager
     */
    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    /**
     * Get an instance of the message manager
     *
     * @return Instance of message manager
     */
    public MessageManager getMessageManager() {
        return this.messageManager;
    }

    /**
     * Get the main SurvivalPlus config
     *
     * @return SurvivalPlus config
     */
    public Config getSurvivalConfig() {
        return this.config;
    }

    /**
     * Get an instance of the language config
     *
     * @return Language config
     */
    public Lang getLang() {
        return lang;
    }

    /**
     * Get an instance of the player data config
     *
     * @return Player data config
     */
    public PlayerDataConfig getPlayerDataConfig() {
        return playerDataConfig;
    }

}
