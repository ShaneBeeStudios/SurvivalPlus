package com.shanebeestudios.survival;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.exceptions.UnsupportedVersionException;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.shanebeestudios.survival.commands.SurvivalCommand;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.config.Lang;
import com.shanebeestudios.survival.config.PlayerDataConfig;
import com.shanebeestudios.survival.data.PlayerData;
import com.shanebeestudios.survival.listeners.EventManager;
import com.shanebeestudios.survival.managers.BlockManager;
import com.shanebeestudios.survival.managers.EffectManager;
import com.shanebeestudios.survival.managers.MerchantManager;
import com.shanebeestudios.survival.managers.PapiPlaceholders;
import com.shanebeestudios.survival.managers.PlayerManager;
import com.shanebeestudios.survival.managers.RecipeManager;
import com.shanebeestudios.survival.managers.ScoreBoardManager;
import com.shanebeestudios.survival.tasks.TaskManager;
import com.shanebeestudios.survival.util.BlockTags;
import com.shanebeestudios.survival.util.Utils;

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

	private static SurvivalPlugin instance;

	// Lists & Map
	private Map<UUID, PlayerData> playerDataMap = new HashMap<>();

	// Configs
	private Config config;
	private Lang lang;
	private PlayerDataConfig playerDataConfig;

	// Managers
	private BlockManager blockManager;
	private EffectManager effectManager;
	private ScoreBoardManager scoreBoardManager;
	private PlayerManager playerManager;
	private TaskManager taskManager;
	private MerchantManager merchantManager;
	private RecipeManager recipeManager;

	// Other
	private String prefix;
	private boolean loaded = true;
	private boolean snowGenOption = true;

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
		instance = this;
		long time = System.currentTimeMillis();

		// VERSION CHECK
		if (!Utils.isRunningMinecraft(1, 16)) {
			String ver = Bukkit.getServer().getBukkitVersion().split("-")[0];
            Utils.logMini("<red>-----------------------------------------------------------");
            Utils.logMini("<red>Your version is not supported: <aqua>" + ver);
            Utils.logMini("<yellow>This plugin only works on Minecraft <aqua>1.16+");
            Utils.logMini("<red>-----------------------------------------------------------");
			loaded = false;
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		// SPIGOT CHECK
        if (!Utils.isRunningSpigot()) {
            Utils.logMini("<red>-----------------------------------------------------------");
            Utils.logMini("<grey>Your server software is not supported: <red>" + Bukkit.getName());
            Utils.logMini("<grey>This plugin will only work on <green>Spigot <grey>or <green>Paper.");
            Utils.logMini("<red>-----------------------------------------------------------");
            loaded = false;
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

		// LOAD CONFIG FILES
		loadSettings(Bukkit.getConsoleSender());

		for (World world : getServer().getWorlds()) {
			world.setGameRule(GameRule.DO_LIMITED_CRAFTING, this.config.survival_limited_crafting);
		}

		// LOAD RESOURCE PACK
		if (config.settings_resource_pack_enabled) {
			if (config.settings_resource_pack_url.isEmpty()) {
				Utils.logMini("<red>Resource Pack is not set! Plugin disabling");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			} else {
				Utils.logMini("<grey>Resource pack <green>enabled");
			}
		} else {
            Utils.logMini("<yellow>Resource Pack disabled");
        }

		// LOAD MANAGERS
        BlockTags.initialize();
		blockManager = new BlockManager(this);
		playerManager = new PlayerManager(this, playerDataMap);
		effectManager = new EffectManager(this);
		taskManager = new TaskManager(this);
		scoreBoardManager = new ScoreBoardManager(this);
		merchantManager = new MerchantManager(this);
        recipeManager = new RecipeManager(this);

		// LOAD PLAYER DATA - (during a reload if players are still online)
		playerDataLoader(true);
		scoreBoardManager.resetStatusScoreboard(config.mechanics_status_scoreboard);

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
		if (config.recipe_delay > 0) {
		    Utils.logMini("<grey>Custom recipe loading delayed... will load in <aqua>" + config.recipe_delay + "<grey> second[s]");
		    Bukkit.getScheduler().runTaskLater(this, () -> {
                this.recipeManager.loadCustomRecipes();
                Utils.logMini("<grey>Custom recipes <green>loaded");
            }, config.recipe_delay * 20L);

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
		//getServer().resetRecipes(); <-- why is this even here?

		// Remove limited crafting when server shuts down (important if server removes this plugin)
		for (World world : getServer().getWorlds()) {
			world.setGameRule(GameRule.DO_LIMITED_CRAFTING, false);
		}

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
							workbench.removeMetadata("shared_players", SurvivalPlugin.instance);
						else
							p.getOpenInventory().getTopInventory().clear();
						p.closeInventory();
					}
					p.removeMetadata("shared_workbench", SurvivalPlugin.instance);
				}
			}
		}
		Utils.logMini("<yellow>Successfully disabled");
	}

	private void playerDataLoader(boolean load) {
		int size = Bukkit.getOnlinePlayers().size();
		if (load) {
			// Load player data - if players are online (useful during reload)
			for (Player player : Bukkit.getOnlinePlayers()) {
			    if (playerDataConfig.hasPlayerDataFile(player)) {
                    playerManager.loadPlayerData(player);
                } else {
			        playerManager.createNewPlayerData(player);
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
			playerDataMap.clear();
			playerDataMap = null;
			Utils.logMini("Unloading player data for <aqua>" + size + " player" + (size != 1 ? "s" : ""));
		}
	}

	/**
	 * Load config settings
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
		this.prefix = lang.prefix;
		this.playerDataConfig = new PlayerDataConfig(this);
	}

	@EventHandler
	private void onServerReload(ServerLoadEvent e) {
		if (e.getType() == ServerLoadEvent.LoadType.RELOAD) {
			for (Player player : getServer().getOnlinePlayers()) {
				Utils.sendColoredMini(player, prefix + "<red>DETECTED SERVER RELOAD");
				Utils.sendColoredMini(player, "    <gold>Recipes may have been impacted");
				Utils.sendColoredMini(player, "    <gold>Relog to update your recipes");
			}
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            Utils.sendColoredMini(console,prefix + "<red>DETECTED SERVER RELOAD");
			Utils.sendColoredMini(console, "    <grey>- <gold>Server reloads will impact recipes");
			Utils.sendColoredMini(console, "    <grey>- <gold>Players will need to relog to re-enable custom recipes");
			Utils.sendColoredMini(console, "    <grey>- <gold>A warning has been sent to each player that is online right now");
		}
	}

	private void registerCommands() {
        if (CommandAPI.isLoaded()) {
            CommandAPI.onEnable();
            new SurvivalCommand(this, "survival");
        }
	}

	/** Get instance of this plugin
	 * @return Instance of this plugin
	 */
	public static SurvivalPlugin getInstance() {
		return instance;
	}

	/** Get the block manager
	 * @return Instance of the block manager
	 */
	public BlockManager getBlockManager() {
		return this.blockManager;
	}

	/** Get the effect manager
	 * @return Instance of the effect manager
	 */
	public EffectManager getEffectManager() {
		return this.effectManager;
	}

	/** Get the scoreboard manager
	 * @return Instance of the scoreboard manager
	 */
	public ScoreBoardManager getScoreboardManager() {
		return this.scoreBoardManager;
	}

	/** Get the player manager
	 * @return Instance of the player manager
	 */
	public PlayerManager getPlayerManager() {
		return this.playerManager;
	}

	/** Get the task manager
	 * @return Instance of the task manager
	 */
	@SuppressWarnings("unused")
	public TaskManager getTaskManager() {
		return this.taskManager;
	}

    /** Get an instance of the merchant manager
     * @return Instance of the merchant manager
     */
    public MerchantManager getMerchantManager() {
        return merchantManager;
    }

    /** Get an instance of the recipe manager
     * @return Instance of recipe manager
     */
    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    /** Get the main SurvivalPlus config
	 * @return SurvivalPlus config
	 */
	public Config getSurvivalConfig() {
		return this.config;
	}

	/** Get an instance of the language config
	 * @return Language config
	 */
	public Lang getLang() {
		return lang;
	}

	public boolean isSnowGenOption() {
		return snowGenOption;
	}

	public void setSnowGenOption(boolean snowGenOption) {
		this.snowGenOption = snowGenOption;
	}

	public PlayerDataConfig getPlayerDataConfig() {
		return playerDataConfig;
	}
}
