package tk.shanebee.survival.managers;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.tasks.Healthboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreBoardManager {

    private Survival plugin;
    private Config config;
    private Map<UUID, Healthboard> playerBoards = new HashMap<>();

    public ScoreBoardManager(Survival plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
    }

    /** Sets up a scoreboard for a player
     * <p>
     *     This is generally used internally
     * </p>
     * @param player Player to setup a scoreboard for
     */
    public void setupScoreboard(Player player) {
        /*
        Runnable run = new Runnable() {
            Objective status = stats.registerNewObjective("Status", "dummy", "Status");

            public void run() {
                status.unregister();
                status = stats.registerNewObjective("Status", "dummy", "Status");

                PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
                if (!player.isOnline()) return;

                GameMode mode = player.getGameMode();
                if (mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE) {
                    status.setDisplaySlot(DisplaySlot.SIDEBAR);
                    status.setDisplayName("Status");

                    if (playerData.isInfoDisplayed(Info.HUNGER)) {
                        Score hunger0 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(0));
                        hunger0.setScore(10);
                        Score hunger1 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(1));
                        hunger1.setScore(9);
                        Score hunger2 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(2));
                        hunger2.setScore(8);
                    }

                    if (config.MECHANICS_THIRST_ENABLED && playerData.isInfoDisplayed(Info.THIRST)) {
                        Score thirst0 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(0));
                        thirst0.setScore(7);
                        Score thirst1 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(1));
                        thirst1.setScore(6);
                        Score thirst2 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(2));
                        thirst2.setScore(5);
                    }

                    if (config.MECHANICS_BED_FATIGUE_ENABLED && playerData.isInfoDisplayed(Info.FATIGUE)) {
                        Score fatigue = status.getScore(plugin.getPlayerManager().ShowFatigue(player));
                        fatigue.setScore(4);
                    }

                    if (config.MECHANICS_FOOD_DIVERSITY_ENABLED && playerData.isInfoDisplayed(Info.NUTRIENTS)) {
                        Score carbon = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(0));
                        carbon.setScore(3);
                        Score protein = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(1));
                        protein.setScore(2);
                        Score salts = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(2));
                        salts.setScore(1);
                    }
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this, 10L);
            }
        };

         */
        playerBoards.put(player.getUniqueId(), new Healthboard(plugin, player));
    }

    public void resetStatusScoreboard(boolean enabled) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (enabled)
                setupScoreboard(player);
            else
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        }
    }

    public void unloadScoreboard(Player player) {
        playerBoards.get(player.getUniqueId()).cancel();
        playerBoards.remove(player.getUniqueId());
    }

}
