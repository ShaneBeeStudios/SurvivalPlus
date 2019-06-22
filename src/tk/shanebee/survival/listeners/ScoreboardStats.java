package tk.shanebee.survival.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Objective;
import tk.shanebee.survival.Survival;

class ScoreboardStats implements Listener {

	private Objective boardHunger = Survival.mainBoard.getObjective("BoardHunger");
	private Objective boardThirst = Survival.mainBoard.getObjective("BoardThirst");
	private Objective boardFatigue = Survival.mainBoard.getObjective("BoardFatigue");
	private Objective boardNutrients = Survival.mainBoard.getObjective("BoardNutrients");

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!player.hasPlayedBefore()) {
			boardHunger.getScore(player.getName()).setScore(0);
			boardThirst.getScore(player.getName()).setScore(0);
			boardFatigue.getScore(player.getName()).setScore(1);
			boardNutrients.getScore(player.getName()).setScore(1);
		}
		Survival.instance.getScoreboardManager().setupScorebard(player);
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
