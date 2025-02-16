package tk.shanebee.survival.tasks;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.data.HealthBoard;
import tk.shanebee.survival.data.Info;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;

import java.util.List;

public class HealthBoardTask extends BukkitRunnable {

    private final SurvivalPlugin plugin;
    private final Config config;
    private final PlayerManager playerManager;
    private final Player player;
    private final PlayerData playerData;
    private final HealthBoard healthBoard;

    // Board stuff
    private boolean hungerEnabled;
    private boolean thirstEnabled;
    private boolean energyEnabled;
    private boolean nutrientsEnabled;

    public HealthBoardTask(SurvivalPlugin plugin, Player player) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
        this.playerManager = plugin.getPlayerManager();
        this.player = player;
        this.playerData = plugin.getPlayerManager().getPlayerData(this.player);
        this.healthBoard = plugin.getScoreboardManager().getBoard(this.player);
        this.hungerEnabled = playerData.isInfoDisplayed(Info.HUNGER);
        this.thirstEnabled = playerData.isInfoDisplayed(Info.THIRST);
        this.energyEnabled = playerData.isInfoDisplayed(Info.ENERGY);
        this.nutrientsEnabled = playerData.isInfoDisplayed(Info.NUTRIENTS);

        this.healthBoard.setTitle(plugin.getLang().healthboard_title);

        this.runTaskTimerAsynchronously(plugin, -1, 10);
    }

    @Override
    public void run() {
        if (!this.player.isOnline()) {
            this.cancel();
            return;
        }

        GameMode mode = this.player.getGameMode();
        if (mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR) {
            // If the player is in creative/spectator and board is on, turn it off
            if (this.healthBoard.isOn()) {
                this.healthBoard.toggle(false);
            }
        } else {
            // Else if player is in survival/adventure and board is off, turn it on
            if (!this.healthBoard.isOn()) {
                this.healthBoard.toggle(true);
            }
        }

        // Refresh board options
        this.hungerEnabled = playerData.isInfoDisplayed(Info.HUNGER);
        this.thirstEnabled = playerData.isInfoDisplayed(Info.THIRST);
        this.energyEnabled = playerData.isInfoDisplayed(Info.ENERGY);
        this.nutrientsEnabled = playerData.isInfoDisplayed(Info.NUTRIENTS);

        // If all options on the board are disabled, turn board off
        if (!hungerEnabled && !thirstEnabled && !energyEnabled && !nutrientsEnabled) {
            if (this.healthBoard.isOn()) {
                this.healthBoard.toggle(false);
            }
            return;
        }

        if (this.hungerEnabled) {
            List<String> hunger = this.playerManager.getHungerVisual(this.player);
            this.healthBoard.setLine(1, hunger.get(0));
            this.healthBoard.setLine(2, hunger.get(1));
            this.healthBoard.setLine(3, hunger.get(2));
            this.healthBoard.setLine(4, "");
        } else {
            this.healthBoard.deleteLine(1);
            this.healthBoard.deleteLine(2);
            this.healthBoard.deleteLine(3);
            this.healthBoard.deleteLine(4);
        }

        if (config.mechanics_thirst_enabled && thirstEnabled) {
            List<String> thirst = this.playerManager.getThirstVisual(this.player);
            this.healthBoard.setLine(5, thirst.get(0));
            this.healthBoard.setLine(6, thirst.get(1));
            this.healthBoard.setLine(7, thirst.get(2));
            this.healthBoard.setLine(8, "");
        } else {
            this.healthBoard.deleteLine(5);
            this.healthBoard.deleteLine(6);
            this.healthBoard.deleteLine(7);
            this.healthBoard.deleteLine(8);
        }

        if (config.mechanics_energy_enabled && energyEnabled) {
            this.healthBoard.setLine(9, this.playerManager.getEnergyVisual(this.player).get(0));
            this.healthBoard.setLine(10, this.playerManager.getEnergyVisual(this.player).get(1));
            this.healthBoard.setLine(11, "");
        } else {
            this.healthBoard.deleteLine(9);
            this.healthBoard.deleteLine(10);
            this.healthBoard.deleteLine(11);
        }

        if (config.mechanics_food_diversity_enabled && nutrientsEnabled) {
            List<String> nutrients = this.playerManager.getNutrientsVisual(this.player);
            this.healthBoard.setLine(12, nutrients.get(0), nutrients.get(3));
            this.healthBoard.setLine(13, nutrients.get(1), nutrients.get(4));
            this.healthBoard.setLine(14, nutrients.get(2), nutrients.get(5));
        } else {
            this.healthBoard.deleteLine(12);
            this.healthBoard.deleteLine(13);
            this.healthBoard.deleteLine(14);
        }
        this.healthBoard.update();
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        this.plugin.getScoreboardManager().removeBoard(this.player);
    }

}
