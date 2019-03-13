package com.fattymieo.survival.managers;

import org.bukkit.scoreboard.Scoreboard;

public class ScoreBoardManager {

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
}
