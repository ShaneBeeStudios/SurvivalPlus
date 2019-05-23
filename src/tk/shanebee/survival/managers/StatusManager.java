package tk.shanebee.survival.managers;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import tk.shanebee.survival.Survival;

import java.util.Objects;

/**
 * Manage a player's different status levels
 */
@SuppressWarnings("unused")
public class StatusManager {

    /**
     * Enums for Nutrient types
     */
    public enum Nutrients {
        CARBS("Carbs"),
        PROTEIN("Protein"),
        SALTS("Salts");

        private String name;

        Nutrients(String nutrient){
            name = nutrient;
        }
    }

    /** Set the thirst level of a player
     * @param player The player to set thirst for
     * @param level The level of thirst to set (Max = 40)
     */
    public static void setThirst(Player player, int level) {
        Score thirst = Objects.requireNonNull(Survival.mainBoard.getObjective("Thirst")).getScore(player.getName());
        thirst.setScore(level <= 40 ? level : 40);
    }

    /** Get the thirst level of a player
     * @param player The player to get a thirst level from
     * @return The thirst level of this player
     */
    public static int getThirst(Player player) {
        return Objects.requireNonNull(Survival.mainBoard.getObjective("Thirst")).getScore(player.getName()).getScore();
    }

    /** Set the nutrient levels of a player
     * @param player The player to set nutrients for
     * @param nutrient The nutrient to set
     * @param level The level to set
     */
    public static void setNutrients(Player player, Nutrients nutrient, int level) {
        Score nut = Objects.requireNonNull(Survival.mainBoard.getObjective(nutrient.name)).getScore(player.getName());
        nut.setScore(level);
    }

    /** Get the nutrient levels of a player
     * @param player The player to get nutrient levels for
     * @param nutrient The nutrient to check
     * @return The level of this nutrient
     */
    public static int getNutrients(Player player, Nutrients nutrient) {
        return Objects.requireNonNull(Survival.mainBoard.getObjective(nutrient.name)).getScore(player.getName()).getScore();
    }

    /** Set the fatigue level of a player
     * @param player The player to set fatigue for
     * @param level The level to set for the player (0 - 5)
     */
    public static void setFatigue(Player player, int level) {
        Score fatigue = Objects.requireNonNull(Survival.mainBoard.getObjective("Fatigue")).getScore(player.getName());
        if (level > 4)
            level = 4;
        if (level < 0)
            level = 0;
        fatigue.setScore(level);
    }

    /** Get the fatigue level of a player
     * @param player The player to get a fatigue level for
     * @return The fatigue level of this player (0 - 5)
     */
    public static int getFatigue(Player player) {
        return Objects.requireNonNull(Survival.mainBoard.getObjective("Fatigue")).getScore(player.getName()).getScore();
    }

    /** Set the hunger level of a player
     * <p>
     *     <b>NOTE:</b> This level is a mixture of the player's food/saturation levels
     * </p>
     * @param player The player to set hunger level for
     * @param level The level to set for the player
     */
    public static void setHunger(Player player, int level) {
        level = level <= 40 ? level : 40;
        player.setFoodLevel(level <= 20 ? level : 20);
        player.setSaturation(level >= 21 ? level - 20 : 0);
    }

    /** Get the hunger level of a player
     * <p>
     *     <b>NOTE:</b> This level is a mixture of the player's food/saturation levels
     * </p>
     * @param player The player to get hunger level for
     * @return The level of player's hunger
     */
    public static int getHunger(Player player) {
        return Math.round(player.getFoodLevel() + player.getSaturation());
    }

}
