package tk.shanebee.survival;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import tk.shanebee.survival.commands.*;
import tk.shanebee.survival.events.*;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.managers.Placeholders;
import tk.shanebee.survival.managers.RecipeManager;
import tk.shanebee.survival.managers.ScoreBoardManager;
import tk.shanebee.survival.metrics.Metrics;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.NoPos;
import tk.shanebee.survival.util.Utils;

import java.io.File;
import java.util.*;

@SuppressWarnings("ConstantConditions")
public class Survival extends JavaPlugin implements Listener {
    /**
     * Instance of this plugin
     */
    public static Survival instance;
    public static ScoreboardManager manager;
    public static Scoreboard board;
    public static Scoreboard mainBoard;
    public static FileConfiguration settings = new YamlConfiguration();
    public static int LocalChatDist = 64;
    private static int AlertInterval = 20;
    private static List<Double> Rates = new ArrayList<>();
    /**
     *  Instance of the language file
     */
    public static Lang lang;
    public static List<Material> allowedBlocks = new ArrayList<>();
    public static List<Player> usingPlayers = new ArrayList<>();
    public static boolean snowGenOption = true;
    private String prefix;

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
            sendColoredConsoleMsg(prefix + "&7NoPos &aimplemented &7- F3 coordinates are disabled!");
        }

        for (World world : getServer().getWorlds()) {
            world.setGameRule(GameRule.DO_LIMITED_CRAFTING, settings.getBoolean("Survival.LimitedCrafting"));
        }

        // LOAD RESOURCE PACK
        String url = settings.getString("MultiWorld.ResourcePackURL");
        boolean resourcePack = settings.getBoolean("MultiWorld.EnableResourcePack");
        if (resourcePack) {
            if (url.isEmpty()) {
                sendColoredConsoleMsg(prefix + "&cResource Pack is not set! Plugin disabling");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            } else {
                sendColoredConsoleMsg(prefix + "&7Resource pack &aenabled");
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
        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        mainBoard = manager.getMainScoreboard();
        ScoreBoardManager sbm = new ScoreBoardManager();
        sbm.loadScoreboards(board, mainBoard);

        // LOAD PLACEHOLDERS
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
            sendColoredConsoleMsg(prefix + "&7PlaceholderAPI placeholders &aenabled");
        }

        // REGISTER EVENTS & COMMANDS
        registerCommands();
        registerEvents();

        // LOAD CUSTOM RECIPES
        RecipeManager recipes = new RecipeManager(this, settings);
        recipes.loadCustomRecipes();
        sendColoredConsoleMsg(prefix + "&7Custom recipes &aloaded");

        if (settings.getBoolean("LegendaryItems.BlazeSword"))
            BlazeSword();
        if (settings.getBoolean("LegendaryItems.GiantBlade"))
            GiantBlade();
        if (settings.getBoolean("LegendaryItems.ObsidianMace"))
            ObsidianMace();
        if (settings.getBoolean("LegendaryItems.ValkyrieAxe"))
            Valkyrie();
        if (settings.getBoolean("LegendaryItems.QuartzPickaxe"))
            QuartzPickaxe();
        if (settings.getBoolean("Mechanics.Thirst.Enabled"))
            PlayerStatus();
        if (settings.getBoolean("Mechanics.BedFatigueLevel"))
            DaysNoSleep();
        if (settings.getBoolean("Mechanics.FoodDiversity"))
            FoodDiversity();
        ResetStatusScoreboard(settings.getBoolean("Mechanics.StatusScoreboard"));

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
        sendColoredConsoleMsg(prefix + "&eShutting down");
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
        sendColoredConsoleMsg(prefix + "&eSuccessfully disabled");
    }

    @EventHandler
    public void onServerReload(ServerLoadEvent e) {
        if (e.getType() == ServerLoadEvent.LoadType.RELOAD) {
            for (Player player : getServer().getOnlinePlayers()) {
                sendColoredMessage(player, prefix + "&cDETECTED SERVER RELOAD");
                sendColoredMessage(player, "    &6Recipes may have been impacted");
                sendColoredMessage(player, "    &6Relog to update your recipes");
            }
            sendColoredConsoleMsg(prefix + "&cDETECTED SERVER RELOAD");
            sendColoredConsoleMsg("    &7- &6Server reloads will impact recipes");
            sendColoredConsoleMsg("    &7- &6Players will need to relog to re-enable custom recipes");
            sendColoredConsoleMsg("    &7- &6A warning has been sent to each player that is online right now");
        }
    }

    public static Location lookAt(Location loc, Location lookat) {
        //Clone the loc to prevent applied changes to the input loc
        loc = loc.clone();

        // Values of change in distance (make it relative)
        double dx = lookat.getX() - loc.getX();
        double dy = lookat.getY() - loc.getY();
        double dz = lookat.getZ() - loc.getZ();

        // Set yaw
        if (dx != 0) {
            // Set yaw start value based on dx
            if (dx < 0)
                loc.setYaw((float) (1.5 * Math.PI));
            else
                loc.setYaw((float) (0.5 * Math.PI));

            loc.setYaw(loc.getYaw() - (float) Math.atan(dz / dx));
        } else if (dz < 0)
            loc.setYaw((float) Math.PI);

        // Get the distance from dx/dz
        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        // Set pitch
        loc.setPitch((float) -Math.atan(dy / dxz));

        // Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

        return loc;
    }

    private void registerCommands() {
        getCommand("recipes").setExecutor(new Recipes());
        getCommand("togglechat").setExecutor(new ToggleChat());
        getCommand("togglechat").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
        getCommand("status").setExecutor(new Status());
        getCommand("reload-survival").setExecutor(new Reload());
        getCommand("reload-survival").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
        if (settings.getBoolean("Mechanics.SnowGenerationRevamp"))
            getCommand("snowgen").setExecutor(new SnowGen());
        getCommand("snowgen").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));


        getCommand("giveitem").setExecutor(new GiveItem());
        getCommand("giveitem").setPermissionMessage(Utils.getColoredString(prefix + lang.no_perm));
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new RecipeDiscovery(), this);

        if (settings.getBoolean("Survival.Enabled")) {
            pm.registerEvents(new BlockBreak(), this);
            pm.registerEvents(new BlockPlace(), this);
            pm.registerEvents(new FirestrikerClick(), this);
            pm.registerEvents(new ShivPoison(), this);
            pm.registerEvents(new WaterBowl(), this);
            pm.registerEvents(new Campfire(), this);
            //pm.registerEvents(new Backpack(), this);
        }
        pm.registerEvents(new NoAnvil(), this);
        if (settings.getBoolean("Mechanics.Bow"))
            pm.registerEvents(new Bow(), this);
        if (settings.getBoolean("Mechanics.GrapplingHook"))
            pm.registerEvents(new GrapplingHook(), this);
        if (settings.getBoolean("LegendaryItems.ObsidianMace"))
            pm.registerEvents(new ObsidianMaceWeakness(), this);
        if (settings.getBoolean("LegendaryItems.ValkyrieAxe"))
            pm.registerEvents(new Valkyrie(), this);
        if (settings.getBoolean("LegendaryItems.GiantBlade"))
            pm.registerEvents(new GiantBlade(), this);
        if (settings.getBoolean("LegendaryItems.BlazeSword"))
            pm.registerEvents(new BlazeSword(), this);
        if (LocalChatDist > -1)
            pm.registerEvents(new LocalChat(), this);
        if (settings.getBoolean("Mechanics.CompassWaypoint"))
            pm.registerEvents(new CompassWaypoint(), this);
        if (settings.getBoolean("Mechanics.MedicalKit"))
            pm.registerEvents(new MedicKit(), this);

        pm.registerEvents(new WaterBottleCrafting(), this);
        pm.registerEvents(new SpecialItemInteractCancel(), this);

        pm.registerEvents(new SetResourcePack(), this);

        if (settings.getBoolean("Mechanics.RawMeatHunger"))
            pm.registerEvents(new RawMeatHunger(), this);
        if (settings.getBoolean("Mechanics.Thirst.Enabled")) {
            pm.registerEvents(new Consume(), this);
            if (settings.getBoolean("Mechanics.Thirst.PurifyWater"))
                pm.registerEvents(new CauldronWaterBottle(), this);
        }
        if (settings.getBoolean("Mechanics.PoisonousPotato"))
            pm.registerEvents(new PoisonousPotato(), this);
        if (settings.getBoolean("Mechanics.SharedWorkbench"))
            pm.registerEvents(new WorkbenchShare(), this);
        if (settings.getBoolean("Mechanics.Chairs.Enabled"))
            pm.registerEvents(new Chairs(), this);
        if (settings.getBoolean("Mechanics.CookieHealthBoost"))
            pm.registerEvents(new CookieHealthBoost(), this);
        if (settings.getBoolean("Mechanics.BeetrootStrength"))
            pm.registerEvents(new BeetrootStrength(), this);
        if (settings.getBoolean("Mechanics.Clownfish"))
            pm.registerEvents(new Clownfish(), this);
        if (settings.getBoolean("Mechanics.LivingSlime"))
            pm.registerEvents(new LivingSlime(), this);
        if (settings.getBoolean("Mechanics.BedFatigueLevel"))
            pm.registerEvents(new BedFatigue(), this);
        if (settings.getBoolean("Mechanics.FoodDiversity"))
            pm.registerEvents(new FoodDiversityConsume(), this);
        if (settings.getBoolean("Mechanics.RecurveBow"))
            pm.registerEvents(new RecurvedBow(), this);
        if (settings.getBoolean("Mechanics.StatusScoreboard"))
            pm.registerEvents(new ScoreboardStats(), this);
        if (settings.getBoolean("Mechanics.SnowballRevamp"))
            pm.registerEvents(new SnowballThrow(), this);
        if (settings.getBoolean("Mechanics.SnowGenerationRevamp"))
            pm.registerEvents(new SnowGeneration(), this);
        pm.registerEvents(new ChickenSpawn(), this);
        if (settings.getBoolean("WelcomeGuide.Enabled"))
            pm.registerEvents(new Guide(), this);
        if (settings.getBoolean("Mechanics.BurnoutTorches.Enabled")) // TODO experimental feature, not 100% sure about this
            pm.registerEvents(new BurnoutTorches(this), this);
        pm.registerEvents(new InventoryUpdate(), this);
    }

    private void BlazeSword() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                if (Items.compare(player.getInventory().getItemInMainHand(), Items.BLAZE_SWORD)) {
                    player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20, 0, false));
                    Location particleLoc = player.getLocation();
                    particleLoc.setY(particleLoc.getY() + 1);
                    assert particleLoc.getWorld() != null;
                    particleLoc.getWorld().spawnParticle(Particle.FLAME, particleLoc, 10, 0.5, 0.5, 0.5);

                    player.setFireTicks(20);
                    if (player.getHealth() > 14)
                        player.setHealth(14);
                }
            }
        }, 1L, 10L);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                if (Items.compare(player.getInventory().getItemInMainHand(), Items.BLAZE_SWORD)) {
                    Random rand = new Random();
                    assert player.getLocation().getWorld() != null;
                    player.getLocation().getWorld().playSound(
                            player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                }
            }
        }, 1L, 50L);
    }

    private void GiantBlade() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                ItemStack mainItem = player.getInventory().getItemInMainHand();
                ItemStack offItem = player.getInventory().getItemInOffHand();
                if (Items.compare(mainItem, Items.ENDER_GIANT_BLADE)) {
                    Location particleLoc = player.getLocation();
                    particleLoc.setY(particleLoc.getY() + 1);
                    assert particleLoc.getWorld() != null;
                    particleLoc.getWorld().spawnParticle(Particle.CRIT_MAGIC, particleLoc, 10, 0.5, 0.5, 0.5);
                }

                if (Items.compare(offItem, Items.ENDER_GIANT_BLADE)) {
                    player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 1, false));
                    Location particleLoc = player.getLocation();
                    particleLoc.setY(particleLoc.getY() + 1);
                    assert particleLoc.getWorld() != null;
                    particleLoc.getWorld().spawnParticle(Particle.CRIT_MAGIC, particleLoc, 10, 0.5, 0.5, 0.5);
                }

                Score dualWield = board.getObjective("DualWield").getScore(player.getName());

                if (((mainItem.getType() == Material.GOLDEN_HOE || mainItem.getType() == Material.GOLDEN_AXE)
                        && (offItem.getType() == Material.WOODEN_AXE
                        || offItem.getType() == Material.WOODEN_SWORD || offItem.getType() == Material.WOODEN_PICKAXE
                        || offItem.getType() == Material.WOODEN_SHOVEL || offItem.getType() == Material.WOODEN_HOE
                        || offItem.getType() == Material.STONE_AXE || offItem.getType() == Material.STONE_SWORD
                        || offItem.getType() == Material.STONE_PICKAXE || offItem.getType() == Material.STONE_SHOVEL
                        || offItem.getType() == Material.STONE_HOE || offItem.getType() == Material.IRON_AXE
                        || offItem.getType() == Material.IRON_SWORD || offItem.getType() == Material.IRON_PICKAXE
                        || offItem.getType() == Material.IRON_SHOVEL || offItem.getType() == Material.IRON_HOE
                        || offItem.getType() == Material.GOLDEN_AXE || offItem.getType() == Material.GOLDEN_SWORD
                        || offItem.getType() == Material.GOLDEN_PICKAXE || offItem.getType() == Material.GOLDEN_SHOVEL
                        || offItem.getType() == Material.GOLDEN_HOE || offItem.getType() == Material.DIAMOND_AXE
                        || offItem.getType() == Material.DIAMOND_SWORD || offItem.getType() == Material.DIAMOND_PICKAXE
                        || offItem.getType() == Material.DIAMOND_SHOVEL || offItem.getType() == Material.DIAMOND_HOE
                        || offItem.getType() == Material.BOW))
                        ||
                        ((offItem.getType() == Material.GOLDEN_HOE || offItem.getType() == Material.GOLDEN_AXE)
                        && (mainItem.getType() == Material.WOODEN_AXE
                        || mainItem.getType() == Material.WOODEN_SWORD || mainItem.getType() == Material.WOODEN_PICKAXE
                        || mainItem.getType() == Material.WOODEN_SHOVEL || mainItem.getType() == Material.WOODEN_HOE
                        || mainItem.getType() == Material.STONE_AXE || mainItem.getType() == Material.STONE_SWORD
                        || mainItem.getType() == Material.STONE_PICKAXE || mainItem.getType() == Material.STONE_SHOVEL
                        || mainItem.getType() == Material.STONE_HOE || mainItem.getType() == Material.IRON_AXE
                        || mainItem.getType() == Material.IRON_SWORD || mainItem.getType() == Material.IRON_PICKAXE
                        || mainItem.getType() == Material.IRON_SHOVEL || mainItem.getType() == Material.IRON_HOE
                        || mainItem.getType() == Material.GOLDEN_AXE || mainItem.getType() == Material.GOLDEN_SWORD
                        || mainItem.getType() == Material.GOLDEN_PICKAXE || mainItem.getType() == Material.GOLDEN_SHOVEL
                        || mainItem.getType() == Material.GOLDEN_HOE || mainItem.getType() == Material.DIAMOND_AXE
                        || mainItem.getType() == Material.DIAMOND_SWORD || mainItem.getType() == Material.DIAMOND_PICKAXE
                        || mainItem.getType() == Material.DIAMOND_SHOVEL || mainItem.getType() == Material.DIAMOND_HOE
                        || mainItem.getType() == Material.BOW))) {
                    player.removePotionEffect(PotionEffectType.SLOW);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 6, true));
                    player.removePotionEffect(PotionEffectType.JUMP);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 199, true));
                    dualWield.setScore(1);
                } else {
                    dualWield.setScore(0);
                }
            }
        }, 1L, 10L);

    }

    private void ObsidianMace() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                if (Items.compare(player.getInventory().getItemInMainHand(), Items.OBSIDIAN_MACE)) {
                    player.removePotionEffect(PotionEffectType.SLOW);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, false));
                    Location particleLoc = player.getLocation();
                    particleLoc.setY(particleLoc.getY() + 1);
                    assert particleLoc.getWorld() != null;
                    particleLoc.getWorld().spawnParticle(Particle.CRIT, particleLoc, 10, 0.5, 0.5, 0.5);
                    particleLoc.getWorld().spawnParticle(Particle.PORTAL, particleLoc, 20, 0.5, 0.5, 0.5);
                }
            }
        }, 1L, 10L);
    }

    private void Valkyrie() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                if (Items.compare(player.getInventory().getItemInMainHand(), Items.VALKYRIES_AXE)) {
                    Location particleLoc = player.getLocation();
                    particleLoc.setY(particleLoc.getY() + 1);
                    assert particleLoc.getWorld() != null;
                    particleLoc.getWorld().spawnParticle(Particle.CRIT_MAGIC, particleLoc, 10, 0.5, 0.5, 0.5);
                }
            }
        }, 1L, 10L);
    }

    private void QuartzPickaxe() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                if (Items.compare(player.getInventory().getItemInMainHand(), Items.QUARTZ_PICKAXE)) {
                    player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 9, false));
                }
            }
        }, 1L, 10L);
    }

    private void PlayerStatus() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                    for (Player player : getServer().getOnlinePlayers()) {
                        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                            Score thirst = mainBoard.getObjective("Thirst").getScore(player.getName());
                            if (player.getExhaustion() >= 4) {
                                Random rand = new Random();
                                double chance = rand.nextDouble();
                                thirst.setScore(thirst.getScore() - (chance <= settings.getDouble("Mechanics.Thirst.DrainRate") ? 1 : 0));
                                if (thirst.getScore() < 0)
                                    thirst.setScore(0);
                            }
                        }
                    }
                }, -1L, 1L);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                    for (Player player : getServer().getOnlinePlayers()) {
                        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                            Score thirst = mainBoard.getObjective("Thirst").getScore(player.getName());

                            if (thirst.getScore() <= 0) {
                                switch (player.getWorld().getDifficulty()) {
                                    case EASY:
                                        if (player.getHealth() > 10)
                                            player.damage(1);
                                        break;
                                    case NORMAL:
                                        if (player.getHealth() > 1)
                                            player.damage(1);
                                        break;
                                    case HARD:
                                        player.damage(1);
                                        break;
                                    default:
                                }
                            }
                        }
                    }
                }, -1L, 320L);

        if (!settings.getBoolean("Mechanics.StatusScoreboard") && AlertInterval > 0) {
            getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                        for (Player player : getServer().getOnlinePlayers()) {
                            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                                int hunger = player.getFoodLevel();
                                if (hunger <= 6) {
                                    player.sendMessage(ChatColor.GOLD + lang.starved_eat);
                                }

                                Score thirst = mainBoard.getObjective("Thirst").getScore(player.getName());
                                if (thirst.getScore() <= 6) {
                                    player.sendMessage(ChatColor.AQUA + lang.dehydrated_drink);
                                }
                            }
                        }
                    }, -1L, AlertInterval * 20);
        }
    }

    private void FoodDiversity() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                    for (Player player : getServer().getOnlinePlayers()) {
                        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                            Score carbon = mainBoard.getObjective("Carbs").getScore(player.getName());
                            Score protein = mainBoard.getObjective("Protein").getScore(player.getName());
                            Score salts = mainBoard.getObjective("Salts").getScore(player.getName());
                            if (player.getExhaustion() >= 4) {
                                carbon.setScore(carbon.getScore() - 8);
                                if (carbon.getScore() < 0)
                                    carbon.setScore(0);

                                protein.setScore(protein.getScore() - 2);
                                if (protein.getScore() < 0)
                                    protein.setScore(0);

                                salts.setScore(salts.getScore() - 3);
                                if (salts.getScore() < 0)
                                    salts.setScore(0);
                            }
                        }
                    }
                },
                -1L, 1L);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                    for (Player player : getServer().getOnlinePlayers()) {
                        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                            Score carbon = mainBoard.getObjective("Carbs").getScore(player.getName());
                            Score protein = mainBoard.getObjective("Protein").getScore(player.getName());
                            Score salts = mainBoard.getObjective("Salts").getScore(player.getName());

                            if (carbon.getScore() <= 0) {
                                switch (player.getWorld().getDifficulty()) {
                                    case EASY:
                                        player.setExhaustion(player.getExhaustion() + 2);
                                        break;
                                    case NORMAL:
                                        player.setExhaustion(player.getExhaustion() + 4);
                                        break;
                                    case HARD:
                                        player.setExhaustion(player.getExhaustion() + 8);
                                        break;
                                    default:
                                }
                            }

                            if (salts.getScore() <= 0) {
                                player.setExhaustion(player.getExhaustion() + 1);
                                switch (player.getWorld().getDifficulty()) {
                                    case NORMAL:
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 0), true);
                                        break;
                                    case HARD:
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 1), true);
                                        break;
                                    default:
                                }
                            }

                            if (protein.getScore() <= 0) {
                                player.setExhaustion(player.getExhaustion() + 1);
                                switch (player.getWorld().getDifficulty()) {
                                    case NORMAL:
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 0), true);
                                        break;
                                    case HARD:
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 1), true);
                                        break;
                                    default:
                                }
                            }
                        }
                    }
                },
                -1L, 320L);

        if (!settings.getBoolean("Mechanics.StatusScoreboard") && AlertInterval > 0) {
            getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                        for (Player player : getServer().getOnlinePlayers()) {
                            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                                Score carbon = mainBoard.getObjective("Carbs").getScore(player.getName());
                                Score protein = mainBoard.getObjective("Protein").getScore(player.getName());
                                Score salts = mainBoard.getObjective("Salts").getScore(player.getName());

                                if (carbon.getScore() <= 480) {
                                    player.sendMessage(ChatColor.DARK_GREEN + lang.carbohydrates_lack);
                                }

                                if (salts.getScore() <= 180) {
                                    player.sendMessage(ChatColor.BLUE + lang.vitamins_lack);
                                }

                                if (protein.getScore() <= 120) {
                                    player.sendMessage(ChatColor.DARK_RED + lang.protein_lack);
                                }
                            }
                        }
                    },
                    -1L, AlertInterval * 20);
        }
    }

    public static List<String> ShowThirst(Player player) {
        Objective thirst = Survival.mainBoard.getObjective("Thirst");
        StringBuilder thirstBar = new StringBuilder();
        for (int i = 0; i < thirst.getScore(player.getName()).getScore(); i++) {
            thirstBar.append("|");
        }
        for (int i = thirst.getScore(player.getName()).getScore(); i < 20; i++) {
            thirstBar.append(".");
        }

        if (thirst.getScore(player.getName()).getScore() >= 40)
            thirstBar.insert(0, ChatColor.GREEN);
        else if (thirst.getScore(player.getName()).getScore() <= 6)
            thirstBar.insert(0, ChatColor.RED);
        else
            thirstBar.insert(0, ChatColor.AQUA);

        return Arrays.asList(ChatColor.AQUA + lang.thirst, (thirstBar.length() <= 22 ? thirstBar.toString() : thirstBar.substring(0, 22)),
                thirstBar.substring(0, 2) + (thirstBar.length() > 22 ? thirstBar.substring(22) : "") + ChatColor.RESET + ChatColor.RESET);
    }

    public static List<String> ShowHunger(Player player) {
        int hunger = player.getFoodLevel();
        int saturation = Math.round(player.getSaturation());
        StringBuilder hungerBar = new StringBuilder();
        StringBuilder saturationBar = new StringBuilder(ChatColor.YELLOW + "");
        for (int i = 0; i < hunger; i++) {
            hungerBar.append("|");
        }
        for (int i = hunger; i < 20; i++) {
            hungerBar.append(".");
        }
        for (int i = 0; i < saturation; i++) {
            saturationBar.append("|");
        }

        if (hunger >= 20)
            hungerBar.insert(0, ChatColor.GREEN);
        else if (hunger <= 6)
            hungerBar.insert(0, ChatColor.RED);
        else
            hungerBar.insert(0, ChatColor.GOLD);

        return Arrays.asList(ChatColor.GOLD + lang.hunger, hungerBar.toString() + ChatColor.RESET, saturationBar.toString());
    }

    public static List<String> ShowNutrients(Player player) {
        List<String> nutrients = new ArrayList<>();
        int carbon = mainBoard.getObjective("Carbs").getScore(player.getName()).getScore();
        int protein = mainBoard.getObjective("Protein").getScore(player.getName()).getScore();
        int salts = mainBoard.getObjective("Salts").getScore(player.getName()).getScore();

        String showCarbon = Integer.toString(carbon);
        if (carbon >= 480)
            showCarbon = ChatColor.GREEN + showCarbon;
        else
            showCarbon = ChatColor.RED + showCarbon;
        nutrients.add(showCarbon + " " + ChatColor.DARK_GREEN + lang.carbohydrates);

        String showProtein = Integer.toString(protein);
        if (protein >= 120)
            showProtein = ChatColor.GREEN + showProtein;
        else
            showProtein = ChatColor.RED + showProtein;
        nutrients.add(showProtein + " " + ChatColor.DARK_RED + lang.protein);

        String showSalts = Integer.toString(salts);
        if (salts >= 180)
            showSalts = ChatColor.GREEN + showSalts;
        else
            showSalts = ChatColor.RED + showSalts;
        nutrients.add(showSalts + " " + ChatColor.BLUE + lang.vitamins);

        return nutrients;
    }

    public static String ShowFatigue(Player player) {
        int fatigue = mainBoard.getObjective("Fatigue").getScore(player.getName()).getScore();

        if (fatigue <= 0)
            return ChatColor.YELLOW + lang.energized;
        else if (fatigue == 1)
            return ChatColor.LIGHT_PURPLE + lang.sleepy;
        else if (fatigue == 2)
            return ChatColor.RED + lang.overworked;
        else if (fatigue == 3)
            return ChatColor.WHITE + lang.distressed;
        else return ChatColor.DARK_GRAY + lang.collapsed_1;
    }

    private void DaysNoSleep() {
        final Objective fatigue = mainBoard.getObjective("Fatigue");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {

                    //if(overworld.getTime() >= 14000 && overworld.getTime() < 22000)
                    //{
                    if (fatigue.getScore(player.getName()).getScore() == 1)
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0), true);
                    else if (fatigue.getScore(player.getName()).getScore() == 2) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 0), true);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10, 0), true);
                    } else if (fatigue.getScore(player.getName()).getScore() == 3) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0), true);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 120, 0), true);
                    } else if (fatigue.getScore(player.getName()).getScore() >= 4) {
                        player.damage(100);
                    }
                    //}
                    World overworld = player.getWorld();

                    int fatigueLevel = settings.getInt("Mechanics.FatigueLevelIncreaseChance");
                    int random = new Random().nextInt(100) + 1;

                    if (overworld.getTime() >= 18000 && overworld.getTime() < 18100 && !player.isSleeping() &&
                            player.getStatistic(Statistic.TIME_SINCE_REST) >= 5000 && random <= fatigueLevel &&
                            Utils.getMinutesPlayed(player) >= 15) {
                        fatigue.getScore(player.getName()).setScore(fatigue.getScore(player.getName()).getScore() + 1);

                        if (fatigue.getScore(player.getName()).getScore() == 1)
                            Utils.sendColoredMsg(player, lang.feeling_sleepy_1);
                        else if (fatigue.getScore(player.getName()).getScore() == 2)
                            Utils.sendColoredMsg(player, lang.feeling_sleepy_2);
                        else if (fatigue.getScore(player.getName()).getScore() == 3)
                            Utils.sendColoredMsg(player, lang.feeling_sleepy_3);
                        else if (fatigue.getScore(player.getName()).getScore() >= 4)
                            Utils.sendColoredMsg(player, lang.collapsed_2);
                    }
                }
            }
        }, -1, 100);

        int refreshTime = settings.getInt("Mechanics.BedFatigueRefreshTime");

        if (refreshTime >= 1) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                for (Player player : getServer().getOnlinePlayers()) {
                    if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                        if (player.isSleeping()) {
                            if (fatigue.getScore(player.getName()).getScore() >= 1) {
                                fatigue.getScore(player.getName()).setScore(fatigue.getScore(player.getName()).getScore() - 1);
                                Utils.sendColoredMsg(player, Utils.getColoredString(Survival.lang.energy_rising));
                            }
                        }
                    }
                }
            }, -1, 20 * refreshTime);
        }
    }

    private void ResetStatusScoreboard(boolean enabled) {
        for (Player player : getServer().getOnlinePlayers()) {
            if (enabled)
                ScoreboardStats.SetupScorebard(player);
            else
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        }
    }

    @SuppressWarnings("unused")
    private void BackpackCheck() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                @SuppressWarnings("MismatchedReadAndWriteOfArray") ItemStack[] backpacks = new ItemStack[3];
                backpacks[0] = player.getInventory().getItem(19);
                backpacks[1] = player.getInventory().getItem(22);
                backpacks[2] = player.getInventory().getItem(25);

                int[] backpackSlots = new int[]{19, 22, 25};

                int[][] collection = new int[][]
                        {
                                {9, 10, 11, 18, 20, 27, 28, 29},
                                {12, 13, 14, 21, 23, 30, 31, 32},
                                {15, 16, 17, 24, 26, 33, 34, 35}
                        };

                for (int i = 0; i < 3; i++) {
                    ItemStack backpackItem = player.getInventory().getItem(backpackSlots[i]);

                    if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                        if (backpackItem == null || backpackItem.getType() == Material.AIR) {
                            player.getInventory().setItem(backpackSlots[i], GetLockedSlotItem());
                        }

                        if (backpackItem != null && backpackItem.getType() == Material.WOODEN_HOE) {
                            for (int j = 0; j < 8; j++) {
                                ItemStack item = player.getInventory().getItem(collection[i][j]);

                                if (CheckIfLockedSlot(item)) {
                                    player.getInventory().clear(collection[i][j]);
                                }
                            }
                        } else {
                            for (int j = 0; j < 8; j++) {
                                ItemStack item = player.getInventory().getItem(collection[i][j]);
                                if (item != null && !CheckIfLockedSlot(item)) {
                                    player.getWorld().dropItem(player.getLocation(), item);
                                }

                                player.getInventory().setItem(collection[i][j], GetLockedSlotItem());
                            }
                        }
                    } else {
                        for (int j = 0; j < 8; j++) {
                            ItemStack item = player.getInventory().getItem(collection[i][j]);

                            if (CheckIfLockedSlot(item)) {
                                player.getInventory().clear(collection[i][j]);
                            }
                        }
                    }
                }
            }
        }, -1, 100);
    }

    public static boolean CheckIfLockedSlot(ItemStack item) {
        if (item != null && item.getType() == Material.BARRIER) {
            ItemMeta meta = item.getItemMeta();

            List<String> lore = meta.getLore();
            return lore != null;
        }

        return false;
    }

    @SuppressWarnings("WeakerAccess")
    static ItemStack GetLockedSlotItem() {
        ItemStack lockedSlot = new ItemStack(Material.BARRIER);
        ItemMeta meta = lockedSlot.getItemMeta();

        meta.setDisplayName(ChatColor.RESET + lang.locked);

        List<String> lore = Collections.singletonList(
                ChatColor.RESET + "" + ChatColor.GRAY + lang.missing_component
        );
        meta.setLore(lore);

        lockedSlot.setItemMeta(meta);

        return lockedSlot;
    }

    @SuppressWarnings("unused")
    public static ItemStack GetBackpackSlotUIItem() {
        ItemStack backpackSlot = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = backpackSlot.getItemMeta();

        meta.setDisplayName(ChatColor.RESET + " ");

        List<String> lore = Collections.singletonList(
                ChatColor.RESET + "" + ChatColor.GRAY + "backpack"
        );
        meta.setLore(lore);

        backpackSlot.setItemMeta(meta);

        return backpackSlot;
    }

    public static void sendColoredMessage(Player player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void sendColoredConsoleMsg(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    private void updateConfig() {
        if (!settings.isSet("Mechanics.BurnoutTorches.Enabled")) {
            settings = getConfig();
            settings.options().copyDefaults(true);
            saveConfig();
            return;
        }
        if (!settings.isSet("Survival.Sickles.Flint")) {
            settings = getConfig();
            settings.options().copyDefaults(true);
            saveConfig();
        }
    }

}
