package tk.shanebee.survival.managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Config;

public class ScoreBoardManager {

    private Survival plugin;
    private Config config;
    private Scoreboard mainBoard;
    private FileConfiguration settings;

    public ScoreBoardManager(Survival plugin) {
        this.plugin = plugin;
        this.mainBoard = plugin.getMainBoard();
        this.config = plugin.getSurvivalConfig();
        this.settings = plugin.getSettings();
    }

    @SuppressWarnings("deprecation")
    public void loadScoreboards(Scoreboard board, Scoreboard mainBoard) {
        board.registerNewObjective("DualWieldMsg", "dummy");
        board.registerNewObjective("Charge", "dummy");
        board.registerNewObjective("Charging", "dummy");
        board.registerNewObjective("Spin", "dummy");
        board.registerNewObjective("DualWield", "dummy");
        board.registerNewObjective("Chat", "dummy");
        board.registerNewObjective("Healing", "dummy");
        board.registerNewObjective("HealTimes", "dummy");
        board.registerNewObjective("RecurveFiring", "dummy");
        board.registerNewObjective("RecurveCooldown", "dummy");
        try {
            mainBoard.registerNewObjective("Thirst", "dummy");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            mainBoard.registerNewObjective("Fatigue", "dummy");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            mainBoard.registerNewObjective("Carbs", "dummy");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            mainBoard.registerNewObjective("Protein", "dummy");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            mainBoard.registerNewObjective("Salts", "dummy");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            mainBoard.registerNewObjective("BoardHunger", "dummy");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            mainBoard.registerNewObjective("BoardThirst", "dummy");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            mainBoard.registerNewObjective("BoardFatigue", "dummy");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            mainBoard.registerNewObjective("BoardNutrients", "dummy");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /** Sets up a scoreboard for a player
     * <p>
     *     This is generally used internally
     * </p>
     * @param p The player to setup a scoreboard for
     */
    public void setupScorebard(Player p) {
        final Player player = p;
        final Scoreboard stats = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(stats);

        Runnable run = new Runnable() {
            Objective status = stats.registerNewObjective("Status", "dummy", "Status");

            Objective boardHunger = mainBoard.getObjective("BoardHunger");
            Objective boardThirst = mainBoard.getObjective("BoardThirst");
            Objective boardFatigue = mainBoard.getObjective("BoardFatigue");
            Objective boardNutrients = mainBoard.getObjective("BoardNutrients");

            public void run() {
                status.unregister();
                status = stats.registerNewObjective("Status", "dummy", "Status");
                status.setDisplaySlot(DisplaySlot.SIDEBAR);
                status.setDisplayName("Status");

                if (boardHunger.getScore(player.getName()).getScore() <= 0) {
                    Score hunger0 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(0));
                    hunger0.setScore(10);
                    Score hunger1 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(1));
                    hunger1.setScore(9);
                    Score hunger2 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(2));
                    hunger2.setScore(8);
                }

                if (settings.getBoolean("Mechanics.Thirst.Enabled") && boardThirst.getScore(player.getName()).getScore() <= 0) {
                    Score thirst0 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(0));
                    thirst0.setScore(7);
                    Score thirst1 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(1));
                    thirst1.setScore(6);
                    Score thirst2 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(2));
                    thirst2.setScore(5);
                }

                if (settings.getBoolean("Mechanics.BedFatigueLevel") && boardFatigue.getScore(player.getName()).getScore() <= 0) {
                    Score fatigue = status.getScore(plugin.getPlayerManager().ShowFatigue(player));
                    fatigue.setScore(4);
                }

                if (config.MECHANICS_FOOD_DIVERSITY && boardNutrients.getScore(player.getName()).getScore() <= 0) {
                    Score carbon = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(0));
                    carbon.setScore(3);
                    Score protein = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(1));
                    protein.setScore(2);
                    Score salts = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(2));
                    salts.setScore(1);
                }

                if (player.isOnline())
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this, 10L);
            }
        };

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, run, -1);
    }

    public void resetStatusScoreboard(boolean enabled) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (enabled)
                setupScorebard(player);
            else
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        }
    }
}
