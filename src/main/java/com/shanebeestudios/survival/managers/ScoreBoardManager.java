package com.shanebeestudios.survival.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.data.HealthBoard;
import com.shanebeestudios.survival.tasks.HealthBoardTask;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreBoardManager {

    private final SurvivalPlugin plugin;
    private final Map<UUID, HealthBoardTask> healthBoardTaskMap = new HashMap<>();
    private final Map<Player, HealthBoard> boardMap = new HashMap<>();

    public ScoreBoardManager(SurvivalPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * @hidden Should not be used outside this plugin
     */
    @ApiStatus.Internal
    public void setupScoreboard(Player player) {
        this.healthBoardTaskMap.put(player.getUniqueId(), new HealthBoardTask(this.plugin, player));
    }

    /**
     * @hidden Should not be used outside this plugin
     */
    @ApiStatus.Internal
    public void resetStatusScoreboard(boolean enabled) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (enabled) {
                setupScoreboard(player);
            } else {
                this.removeBoard(player);
            }
        }
    }

    /**
     * @hidden Should not be used outside this plugin
     */
    @ApiStatus.Internal
    public void unloadScoreboard(Player player) {
        UUID uuid = player.getUniqueId();
        if (this.healthBoardTaskMap.containsKey(uuid)) {
            this.healthBoardTaskMap.get(uuid).cancel();
            this.healthBoardTaskMap.remove(uuid);
        }
    }

    /**
     * Get the Board for a specific player
     * <br>
     * If no Board is available, a new one will be created
     *
     * @param player Player to grab scoreboard for
     * @return Board of player
     */
    public HealthBoard getBoard(Player player) {
        if (this.boardMap.containsKey(player)) {
            return this.boardMap.get(player);
        } else {
            HealthBoard healthBoard = new HealthBoard(player);
            this.boardMap.put(player, healthBoard);
            return healthBoard;
        }
    }

    /**
     * Remove a Board for a player
     * <br>
     * Useful when the player leaves the server
     *
     * @param player Player to remove Board for
     */
    public void removeBoard(Player player) {
        if (this.boardMap.containsKey(player)) {
            HealthBoard healthBoard = this.boardMap.get(player);
            healthBoard.toggle(false);
        }
        this.boardMap.remove(player);
    }

}
