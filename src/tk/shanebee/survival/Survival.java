package tk.shanebee.survival;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import tk.shanebee.survival.commands.*;
import tk.shanebee.survival.listeners.EventManager;
import tk.shanebee.survival.managers.*;
import tk.shanebee.survival.metrics.Metrics;
import tk.shanebee.survival.tasks.TaskManager;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.NoPos;
import tk.shanebee.survival.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class Survival extends JavaPlugin implements Listener {
	/**
	 * Instance of this plugin
	 */
	public static Survival instance;

	public static Scoreboard board;
	public static Scoreboard mainBoard;
	public static FileConfiguration settings = new YamlConfiguration();
	public static int LocalChatDist = 64;
	private int AlertInterval = 20;
	private static List<Double> Rates = new ArrayList<>();
	/**
	 * Instance of the language file
	 */
	public static Lang lang;
	public static List<Material> allowedBlocks = new ArrayList<>();
	public static List<Player> usingPlayers = new ArrayList<>();
	public boolean snowGenOption = true;
	private String prefix;

	// Managers
	private BlockManager blockManager;
	private EffectManager effectManager;
	private ScoreBoardManager scoreBoardManager;
	private PlayerManager playerManager;
	private TaskManager taskManager;

	public void onEnable() {
		instance = this;

		File config_file = new File(this.getDataFolder(), "config.yml");
		if (!config_file.exists()) {
			this.saveResource("config.yml", true);
		}
		settings = YamlConfiguration.loadConfiguration(config_file);
		updateConfig();

		// LOAD LANG FILE
		lang = new Lang(this, settings.getString("Language"));
		lang.loadLangFile(Bukkit.getConsoleSender());
		prefix = lang.prefix;

		if (settings.getBoolean("NoPos")) {
			Bukkit.getPluginManager().registerEvents(new NoPos(), this);
			Utils.sendColoredConsoleMsg(prefix + "&7NoPos &aimplemented &7- F3 coordinates are disabled!");
		}

		for (World world : getServer().getWorlds()) {
			world.setGameRule(GameRule.DO_LIMITED_CRAFTING, settings.getBoolean("Survival.LimitedCrafting"));
		}

		// LOAD RESOURCE PACK
		String url = settings.getString("MultiWorld.ResourcePackURL");
		boolean resourcePack = settings.getBoolean("MultiWorld.EnableResourcePack");
		if (resourcePack) {
			if (url.isEmpty()) {
				Utils.sendColoredConsoleMsg(prefix + "&cResource Pack is not set! Plugin disabling");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			} else {
				Utils.sendColoredConsoleMsg(prefix + "&7Resource pack &aenabled");
			}
		} else Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.YELLOW + "Resource Pack disabled");

		LocalChatDist = settings.getInt("LocalChatDist");
		AlertInterval = settings.getInt("Mechanics.AlertInterval");
		if (AlertInterval <= 0) {
			Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED + "AlertInterval cannot be zero or below! Plugin disabled.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		Rates.add(settings.getDouble("Survival.DropRate.Flint"));
		Rates.add(settings.getDouble("Survival.DropRate.Stick"));
		Rates.add(settings.getDouble("Mechanics.Thirst.DrainRate"));
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

		for (String type : settings.getStringList("Mechanics.Chairs.AllowedBlocks"))
			allowedBlocks.add(Material.getMaterial(type));


		// LOAD SCOREBOARDS
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		mainBoard = Bukkit.getScoreboardManager().getMainScoreboard();
		scoreBoardManager = new ScoreBoardManager(this);
		scoreBoardManager.loadScoreboards(board, mainBoard);
		scoreBoardManager.resetStatusScoreboard(settings.getBoolean("Mechanics.StatusScoreboard"));

		// LOAD PLACEHOLDERS
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new Placeholders(this).register();
			Utils.sendColoredConsoleMsg(prefix + "&7PlaceholderAPI placeholders &aenabled");
		}

		// MANAGERS
		blockManager = new BlockManager(this);
		effectManager = new EffectManager(this);
		playerManager = new PlayerManager(this);
		taskManager = new TaskManager(this);

		// REGISTER EVENTS & COMMANDS
		registerCommands();
		EventManager eventManager = new EventManager(this, settings);
		eventManager.registerEvents();

		// LOAD CUSTOM RECIPES
		RecipeManager recipes = new RecipeManager(this, settings);
		recipes.loadCustomRecipes();
		Utils.sendColoredConsoleMsg(prefix + "&7Custom recipes &aloaded");

		if (settings.getBoolean("Mechanics.Thirst.Enabled"))
			taskManager.playerStatus();
		if (settings.getBoolean("Mechanics.BedFatigueLevel"))
			taskManager.daysNoSleep();
		if (settings.getBoolean("Mechanics.FoodDiversity"))
			taskManager.foodDiversity();

		// Load metrics
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this);

		Utils.sendColoredMsg(Bukkit.getConsoleSender(), prefix + ChatColor.GREEN + "Successfully loaded");
		if (this.getDescription().getVersion().contains("Beta")) {
			getLogger().warning(ChatColor.translateAlternateColorCodes('&',
					"&eYOU ARE RUNNING A BETA VERSION, PLEASE USE WITH CAUTION!"));
		}
	}

	public void onDisable() {
		Utils.sendColoredConsoleMsg(prefix + "&eShutting down");
		getServer().getScheduler().cancelTasks(this);
		//getServer().resetRecipes(); <-- why is this even here?
		usingPlayers = new ArrayList<>();

		// Remove limited crafting when server shuts down (import if server removes this plugin)
		for (World world : getServer().getWorlds()) {
			world.setGameRule(GameRule.DO_LIMITED_CRAFTING, false);
		}

		//Avoid WorkbenchShare glitch
		if (settings.getBoolean("Mechanics.SharedWorkbench")) {
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
		getCommand("togglechat").setExecutor(new ToggleChat());
		getCommand("togglechat").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
		getCommand("status").setExecutor(new Status(this));
		getCommand("reload-survival").setExecutor(new Reload());
		getCommand("reload-survival").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
		if (settings.getBoolean("Mechanics.SnowGenerationRevamp")) {
			getCommand("snowgen").setExecutor(new SnowGen());
			getCommand("snowgen").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
		}
		getCommand("giveitem").setExecutor(new GiveItem());
		getCommand("giveitem").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
	}

	private void updateConfig() {
		if (!settings.isSet("Mechanics.BurnoutTorches.Enabled") || !settings.isSet("Survival.Sickles.Flint")) {
			settings = getConfig();
			settings.options().copyDefaults(true);
			saveConfig();
		}
		if (settings.getString("MultiWorld.ResourcePackURL")
				.equalsIgnoreCase("https://shanebee.tk/survivalplus/resource-pack/SP-1.14v2.zip")) {
			settings = getConfig();
			settings.set("MultiWorld.ResourcePackURL", "https://shanebee.tk/survivalplus/resource-pack/SP-1.14v3.zip");
			saveConfig();
		}
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

	public int getAlertInterval() {
		return AlertInterval;
	}

}