package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.data.Info;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.util.Utils;

public class Healthboard extends BukkitRunnable {

	private final Player player;
	private final Config config;
	private final Scoreboard stats;
	private Objective status;
	private final PlayerManager pm;
	private final PlayerData playerData;

	private boolean visible = true;
	private Team hungerT0;
	private final String hungerS0;
	private Team hungerT1;
	private final String hungerS1;
	private Team hungerT2;
	private final String hungerS2;

	private Team thirstT0;
	private final String thirstS0;
	private Team thirstT1;
	private final String thirstS1;
	private Team thirstT2;
	private final String thirstS2;

	private Team energyT0;
    private Team energyT1;
    private final String energyS0;
    private final String energyS1;

	private Team nutritionT0;
	private final String nutritionS0;
	private Team nutritionT1;
	private final String nutritionS1;
	private Team nutritionT2;
	private final String nutritionS2;

	private boolean hunger;
	private boolean thirst;
	private boolean energy;
	private boolean nutrients;
	private final String title;

	@SuppressWarnings("ConstantConditions")
	public Healthboard(Survival plugin, Player player) {
		this.config = plugin.getSurvivalConfig();
		this.player = player;
		this.pm = plugin.getPlayerManager();
		this.playerData = pm.getPlayerData(player);
		this.stats = Bukkit.getScoreboardManager().getNewScoreboard();
		player.setScoreboard(stats);

		this.hungerS0 = Utils.getColoredString("&a");
		this.hungerS1 = Utils.getColoredString("&b");
		this.hungerS2 = Utils.getColoredString("&c");
		this.thirstS0 = Utils.getColoredString("&d");
		this.thirstS1 = Utils.getColoredString("&e");
		this.thirstS2 = Utils.getColoredString("&f");
		this.energyS0 = Utils.getColoredString("&0");
        this.energyS1 = Utils.getColoredString("&4");
		this.nutritionS0 = Utils.getColoredString("&1");
		this.nutritionS1 = Utils.getColoredString("&2");
		this.nutritionS2 = Utils.getColoredString("&3");

		this.hunger = playerData.isInfoDisplayed(Info.HUNGER);
		this.thirst = playerData.isInfoDisplayed(Info.THIRST);
		this.energy = playerData.isInfoDisplayed(Info.ENERGY);
		this.nutrients = playerData.isInfoDisplayed(Info.NUTRIENTS);
		this.title = Utils.getColoredString(plugin.getLang().healthboard_title);

		refresh(false);
		setLines();

		this.runTaskTimer(plugin, -1, 10);
	}

	@Override
	public void run() {
		if (!player.isOnline()) return;

		GameMode mode = player.getGameMode();
		if (mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE) {
			if (!visible) {
				visible = true;
				refresh(false);
			}
		}

		// Turn off scoreboard if player is in creative/spectator mode
		if (mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR) {
			if (visible) {
				visible = false;
				if (status != null)
					status.unregister();
			}
			return;
		}

		if (hunger != playerData.isInfoDisplayed(Info.HUNGER)) {
			refresh(true);
			hunger = playerData.isInfoDisplayed(Info.HUNGER);
		}
		if (thirst != playerData.isInfoDisplayed(Info.THIRST)) {
			refresh(true);
			thirst = playerData.isInfoDisplayed(Info.THIRST);
		}
		if (energy != playerData.isInfoDisplayed(Info.ENERGY)) {
			refresh(true);
			energy = playerData.isInfoDisplayed(Info.ENERGY);
		}
		if (nutrients != playerData.isInfoDisplayed(Info.NUTRIENTS)) {
			refresh(true);
			nutrients = playerData.isInfoDisplayed(Info.NUTRIENTS);
		}

		if (hunger) {
			hungerT0.setPrefix(pm.ShowHunger(player).get(0));
			hungerT1.setPrefix(pm.ShowHunger(player).get(1));
			hungerT2.setPrefix(pm.ShowHunger(player).get(2));
			status.getScore(hungerS0).setScore(11);
			status.getScore(hungerS1).setScore(10);
			status.getScore(hungerS2).setScore(9);
		}

		if (config.MECHANICS_THIRST_ENABLED && thirst) {
			thirstT0.setPrefix(pm.ShowThirst(player).get(0));
			thirstT1.setPrefix(pm.ShowThirst(player).get(1));
			thirstT2.setPrefix(pm.ShowThirst(player).get(2));
			status.getScore(thirstS0).setScore(8);
			status.getScore(thirstS1).setScore(7);
			status.getScore(thirstS2).setScore(6);
		}

		if (config.MECHANICS_ENERGY_ENABLED && energy) {
			energyT0.setPrefix(pm.showEnergy(player).get(0));
			energyT1.setPrefix(pm.showEnergy(player).get(1));
			status.getScore(energyS0).setScore(5);
            status.getScore(energyS1).setScore(4);
		}

		if (config.MECHANICS_FOOD_DIVERSITY_ENABLED && nutrients) {
			nutritionT0.setPrefix(pm.ShowNutrients(player).get(0));
			nutritionT1.setPrefix(pm.ShowNutrients(player).get(1));
			nutritionT2.setPrefix(pm.ShowNutrients(player).get(2));
			status.getScore(nutritionS0).setScore(3);
			status.getScore(nutritionS1).setScore(2);
			status.getScore(nutritionS2).setScore(1);
		}
	}

	private void refresh(boolean wipeBoard) {
		if (wipeBoard)
			status.unregister();
		status = stats.registerNewObjective("Status", "dummy", "Status");
		status.setDisplaySlot(DisplaySlot.SIDEBAR);
		status.setDisplayName(this.title);
	}

	private void setLines() {
		hungerT0 = stats.registerNewTeam("hungerT0");
		hungerT1 = stats.registerNewTeam("hungerT1");
		hungerT2 = stats.registerNewTeam("hungerT2");
		thirstT0 = stats.registerNewTeam("thirstT0");
		thirstT1 = stats.registerNewTeam("thirstT1");
		thirstT2 = stats.registerNewTeam("thirstT2");
		energyT0 = stats.registerNewTeam("energyT0");
        energyT1 = stats.registerNewTeam("energyT1");
		nutritionT0 = stats.registerNewTeam("nutritionT0");
		nutritionT1 = stats.registerNewTeam("nutritionT1");
		nutritionT2 = stats.registerNewTeam("nutritionT2");

		hungerT0.addEntry(hungerS0);
		hungerT1.addEntry(hungerS1);
		hungerT2.addEntry(hungerS2);
		thirstT0.addEntry(thirstS0);
		thirstT1.addEntry(thirstS1);
		thirstT2.addEntry(thirstS2);
		energyT0.addEntry(energyS0);
        energyT1.addEntry(energyS1);
		nutritionT0.addEntry(nutritionS0);
		nutritionT1.addEntry(nutritionS1);
		nutritionT2.addEntry(nutritionS2);
	}

}
