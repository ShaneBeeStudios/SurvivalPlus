package tk.shanebee.survival.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Objective;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.ScoreBoardManager;

class ScoreboardStats implements Listener {

	private Objective boardHunger;
	private Objective boardThirst;
	private Objective boardFatigue;
	private Objective boardNutrients;
	private ScoreBoardManager scoreboardManager;

	ScoreboardStats(Survival plugin) {
		this.boardHunger = plugin.getMainBoard().getObjective("BoardHunger");
		this.boardThirst = plugin.getMainBoard().getObjective("BoardThirst");
		this.boardFatigue = plugin.getMainBoard().getObjective("BoardFatigue");
		this.boardNutrients = plugin.getMainBoard().getObjective("BoardNutrients");
		this.scoreboardManager = plugin.getScoreboardManager();
	}

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!player.hasPlayedBefore()) {
			boardHunger.getScore(player.getName()).setScore(0);
			boardThirst.getScore(player.getName()).setScore(0);
			boardFatigue.getScore(player.getName()).setScore(1);
			boardNutrients.getScore(player.getName()).setScore(1);
		}
		scoreboardManager.setupScoreboard(player);
	}

	@EventHandler
	private void onPlayerChangeGamemode(PlayerGameModeChangeEvent e) {
		Player player = e.getPlayer();
		if (e.getNewGameMode() != GameMode.SURVIVAL && e.getNewGameMode() != GameMode.ADVENTURE) {
			boardHunger.getScore(player.getName()).setScore(1);
			boardThirst.getScore(player.getName()).setScore(1);
			boardFatigue.getScore(player.getName()).setScore(1);
			boardNutrients.getScore(player.getName()).setScore(1);
		} else {
			boardHunger.getScore(player.getName()).setScore(0);
			boardThirst.getScore(player.getName()).setScore(0);
			boardFatigue.getScore(player.getName()).setScore(1);
			boardNutrients.getScore(player.getName()).setScore(1);
		}
	}

}
