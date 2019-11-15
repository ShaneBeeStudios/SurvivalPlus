package tk.shanebee.survival;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import tk.shanebee.survival.commands.*;
import tk.shanebee.survival.config.PlayerDataConfig;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.listeners.EventManager;
import tk.shanebee.survival.managers.*;
import tk.shanebee.survival.metrics.Metrics;
import tk.shanebee.survival.tasks.TaskManager;
import tk.shanebee.survival.util.Config;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.*;

@SuppressWarnings("ConstantConditions")
public class Survival extends JavaPlugin implements Listener {

	static {
		ConfigurationSerialization.registerClass(PlayerData.class, "PlayerData");
	}

	private static Survival instance;

	// Lists & Maps
	private List<Double> Rates = new ArrayList<>();
	private List<Material> chairBlocks = new ArrayList<>();
	private List<Player> usingPlayers = new ArrayList<>();
	private Map<UUID, PlayerData> playerDataMap = new HashMap<>();

	// Configs
	private Config config;
	private Lang lang;
	private PlayerDataConfig playerDataConfig;

	// Scoreboards
	private Scoreboard board;
	private Scoreboard mainBoard;

	// Managers
	private BlockManager blockManager;
	private EffectManager effectManager;
	private ScoreBoardManager scoreBoardManager;
	private PlayerManager playerManager;
	private TaskManager taskManager;

	// Other
	private String prefix;
	private boolean loaded = true;
	private boolean snowGenOption = true;

	public void onEnable() {
		instance = this;
		long time = System.currentTimeMillis();

		// VERSION CHECK
		if (!Utils.isRunningMinecraft(1, 14)) {
			String ver = Bukkit.getServer().getBukkitVersion().split("-")[0];
			getLogger().severe("-----------------------------------------------------------");
			getLogger().severe("Your version is not supported: " + Utils.getColoredString("&b" + ver));
			getLogger().severe(Utils.getColoredString("This plugin only works on Minecraft &b1.14+"));
			getLogger().severe("-----------------------------------------------------------");
			loaded = false;
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		// LOAD CONFIG FILES
		loadSettings(Bukkit.getConsoleSender());

		for (World world : getServer().getWorlds()) {
			world.setGameRule(GameRule.DO_LIMITED_CRAFTING, config.SURVIVAL_LIMITED_CRAFTING);
		}

		// LOAD RESOURCE PACK
		String url = config.RESOURCE_PACK_URL;
		boolean resourcePack = config.RESOURCE_PACK_ENABLED;
		if (resourcePack) {
			if (url.isEmpty()) {
				Utils.sendColoredConsoleMsg(prefix + "&cResource Pack is not set! Plugin disabling");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			} else {
				Utils.sendColoredConsoleMsg(prefix + "&7Resource pack &aenabled");
			}
		} else Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.YELLOW + "Resource Pack disabled");

		if (config.MECHANICS_ALERT_INTERVAL <= 0) {
			Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED + "AlertInterval cannot be zero or below! Plugin disabled.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		Rates.add(config.DROP_RATE_FLINT);
		Rates.add(config.DROP_RATE_STICK);
		Rates.add(config.MECHANICS_THIRST_DRAIN_RATE);
		for (double i : Rates) {
			if (i <= 0) {
				Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED +
						"Rate values cannot be zero or below! (Check config.yml) Plugin disabled.");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			} else if (i > 1) {
				Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED +
						"Rate values cannot be above 1! (Check config.yml) Plugin disabled.");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			}
		}

		// LOAD SCOREBOARDS
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		mainBoard = Bukkit.getScoreboardManager().getMainScoreboard();

		// LOAD PLACEHOLDERS
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new Placeholders(this).register();
			Utils.sendColoredConsoleMsg(prefix + "&7PlaceholderAPI placeholders &aenabled");
		}

		// LOAD MANAGERS
		blockManager = new BlockManager(this);
		playerManager = new PlayerManager(this, playerDataMap);
		effectManager = new EffectManager(this);
		taskManager = new TaskManager(this);
		scoreBoardManager = new ScoreBoardManager(this);
		scoreBoardManager.resetStatusScoreboard(config.MECHANICS_STATUS_SCOREBOARD);

		// LOAD PLAYER DATA - (during a reload if players are still online)
		playerDataLoader(true);

		// REGISTER EVENTS & COMMANDS
		registerCommands();
		EventManager eventManager = new EventManager(this);
		eventManager.registerEvents();

		// LOAD CUSTOM RECIPES
		RecipeManager recipes = new RecipeManager(this);
		recipes.loadCustomRecipes();
		Utils.sendColoredConsoleMsg(prefix + "&7Custom recipes &aloaded");

		// LOAD METRICS
		Metrics metrics = new Metrics(this);
		Utils.sendColoredConsoleMsg(prefix + "&7Metrics " + (metrics.isEnabled() ? "&aenabled" : "&cdisabled"));

		Utils.sendColoredMsg(Bukkit.getConsoleSender(), prefix + ChatColor.GREEN + "Successfully loaded &7in " +
				(System.currentTimeMillis() - time) + " milliseconds");

		// BETA WARNING
		if (this.getDescription().getVersion().contains("Beta")) {
			getLogger().warning(ChatColor.translateAlternateColorCodes('&',
					"&eYOU ARE RUNNING A BETA VERSION, PLEASE USE WITH CAUTION!"));
		}
	}

	public void onDisable() {
		if (!loaded) return;
		Utils.sendColoredConsoleMsg(prefix + "&eShutting down");
		getServer().getScheduler().cancelTasks(this);
		//getServer().resetRecipes(); <-- why is this even here?
		usingPlayers = new ArrayList<>();

		// Remove limited crafting when server shuts down (important if server removes this plugin)
		for (World world : getServer().getWorlds()) {
			world.setGameRule(GameRule.DO_LIMITED_CRAFTING, false);
		}

		// Unload player data (decrease chance of memory leak)
		playerDataLoader(false);

		//Avoid WorkbenchShare glitch
		if (config.MECHANICS_SHARED_WORKBENCH) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.hasMetadata("shared_workbench")) {
					Block workbench = (p.getMetadata("shared_workbench").get(0).value() instanceof Block) ? (Block)
							p.getMetadata("shared_workbench").get(0).value() : null;

					if (workbench != null && workbench.getType() == Material.CRAFTING_TABLE) {
						if (workbench.hasMetadata("shared_players"))
							workbench.removeMetadata("shared_players", Survival.instance);
						else
							p.getOpenInventory().getTopInventory().clear();
						p.closeInventory();
					}
					p.removeMetadata("shared_workbench", Survival.instance);
				}
			}
		}
		Utils.sendColoredConsoleMsg(prefix + "&eSuccessfully disabled");
	}

	private void playerDataLoader(boolean load) {
		int size = Bukkit.getOnlinePlayers().size();
		if (load) {
			// Load player data - if players are online (useful during reload)
			for (Player player : Bukkit.getOnlinePlayers()) {
				playerManager.loadPlayerData(player);
			}
			if (size > 0) {
				Utils.log("Loading player data for &b" + size + " player" + (size != 1 ? "s" : ""));
			}
		} else {
			// Unload player data - if players are still online
			for (Player player : Bukkit.getOnlinePlayers()) {
				playerManager.unloadPlayerData(player);
			}
			// Clear/delete player data map to prevent memory leaks
			playerDataMap.clear();
			playerDataMap = null;
			Utils.log("Unloading player data for &b" + size + " player" + (size != 1 ? "s" : ""));
		}
	}

	/**
	 * Load config settings
	 * @param sender The person/console loading config
	 */
	public void loadSettings(CommandSender sender) {
		this.config = new Config(this);
		this.lang = new Lang(this, config.LANG);
		this.lang.loadLangFile(sender);
		this.prefix = lang.prefix;
		for (String type : config.MECHANICS_CHAIRS_BLOCKS) {
			Material mat = Material.getMaterial(type);
			if (mat != null) {
				chairBlocks.add(mat);
			} else {
				Utils.log("&cInvalid chair block material: &7" + type);
			}
		}
		this.playerDataConfig = new PlayerDataConfig(this);
	}

	@EventHandler
	private void onServerReload(ServerLoadEvent e) {
		if (e.getType() == ServerLoadEvent.LoadType.RELOAD) {
			for (Player player : getServer().getOnlinePlayers()) {
				Utils.sendColoredMsg(player, prefix + "&cDETECTED SERVER RELOAD");
				Utils.sendColoredMsg(player, "    &6Recipes may have been impacted");
				Utils.sendColoredMsg(player, "    &6Relog to update your recipes");
			}
			Utils.sendColoredConsoleMsg(prefix + "&cDETECTED SERVER RELOAD");
			Utils.sendColoredConsoleMsg("    &7- &6Server reloads will impact recipes");
			Utils.sendColoredConsoleMsg("    &7- &6Players will need to relog to re-enable custom recipes");
			Utils.sendColoredConsoleMsg("    &7- &6A warning has been sent to each player that is online right now");
		}
	}

	private void registerCommands() {
		getCommand("recipes").setExecutor(new Recipes());
		getCommand("togglechat").setExecutor(new ToggleChat(this));
		getCommand("togglechat").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
		getCommand("status").setExecutor(new Status(this));
		getCommand("reload-survival").setExecutor(new Reload(this));
		getCommand("reload-survival").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
		if (config.MECHANICS_SNOW_GEN_REVAMP) {
			getCommand("snowgen").setExecutor(new SnowGen(this));
			getCommand("snowgen").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
		}
		getCommand("giveitem").setExecutor(new GiveItem(this));
		getCommand("giveitem").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
	}

	/** Get instance of this plugin
	 * @return Instance of this plugin
	 */
	public static Survival getInstance() {
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

	/** Get server scoreboard
	 * @return server Scoreboard
	 */
	public Scoreboard getBoard() {
		return board;
	}

	/** Get the main server scoreboard
	 * @return Main server scoreboard
	 */
	public Scoreboard getMainBoard() {
		return mainBoard;
	}

	public boolean isSnowGenOption() {
		return snowGenOption;
	}

	public void setSnowGenOption(boolean snowGenOption) {
		this.snowGenOption = snowGenOption;
	}

	/** Get acceptable chair blocks
	 * @return List of chair blocks
	 */
	public List<Material> getChairBlocks() {
		return chairBlocks;
	}

	/** Get a list of players using the plugin's resource pack
	 * @return List of players using the plugin's resource pack
	 */
	public List<Player> getUsingPlayers() {
		return usingPlayers;
	}

	public PlayerDataConfig getPlayerDataConfig() {
		return playerDataConfig;
	}
}