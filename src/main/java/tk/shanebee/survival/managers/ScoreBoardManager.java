package tk.shanebee.survival.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.Info;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.util.Config;

public class ScoreBoardManager {

    private Survival plugin;
    private Config config;

    public ScoreBoardManager(Survival plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
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
        /* temp removing old scoreboard stats/data
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
         */
    }

    /** Sets up a scoreboard for a player
     * <p>
     *     This is generally used internally
     * </p>
     * @param p The player to setup a scoreboard for
     */
    public void setupScoreboard(Player p) {
        final Player player = p;
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(p);

        final Scoreboard stats = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(stats);

        Runnable run = new Runnable() {
            Objective status = stats.registerNewObjective("Status", "dummy", "Status");

            public void run() {
                status.unregister();
                if (!player.isOnline()) return;
                status = stats.registerNewObjective("Status", "dummy", "Status");
                status.setDisplaySlot(DisplaySlot.SIDEBAR);
                status.setDisplayName("Status");

                if (playerData.showInfo(Info.HUNGER)) {
                    Score hunger0 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(0));
                    hunger0.setScore(10);
                    Score hunger1 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(1));
                    hunger1.setScore(9);
                    Score hunger2 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(2));
                    hunger2.setScore(8);
                }

                if (config.MECHANICS_THIRST_ENABLED && playerData.showInfo(Info.THIRST)) {
                    Score thirst0 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(0));
                    thirst0.setScore(7);
                    Score thirst1 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(1));
                    thirst1.setScore(6);
                    Score thirst2 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(2));
                    thirst2.setScore(5);
                }

                if (config.MECHANICS_BED_FATIGUE_ENABLED && playerData.showInfo(Info.FATIGUE)) {
                    Score fatigue = status.getScore(plugin.getPlayerManager().ShowFatigue(player));
                    fatigue.setScore(4);
                }

                if (config.MECHANICS_FOOD_DIVERSITY_ENABLED && playerData.showInfo(Info.NUTRIENTS)) {
                    Score carbon = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(0));
                    carbon.setScore(3);
                    Score protein = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(1));
                    protein.setScore(2);
                    Score salts = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(2));
                    salts.setScore(1);
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this, 10L);
            }
        };

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, run, -1);
    }

    public void resetStatusScoreboard(boolean enabled) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (enabled)
                setupScoreboard(player);
            else
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        }
    }

}
