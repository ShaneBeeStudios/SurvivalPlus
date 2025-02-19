package com.shanebeestudios.survival.listeners.entity;

import com.destroystokyo.paper.entity.ai.MobGoals;
import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.goals.AngryWolfGoal;
import com.shanebeestudios.survival.goals.AvoidPlayerGoal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MobGoalListener implements Listener {

    private final Config config;
    private final MobGoals mobGoals;

    public MobGoalListener(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
        this.mobGoals = Bukkit.getMobGoals();
    }

    @EventHandler
    private void onEntityJoinWorld(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Mob mob && this.config.entity_mechanics_mobs_avoid_players.contains(entity.getType())) {
            avoidPlayers(mob);
        } else if (entity instanceof Wolf wolf && this.config.entity_mechanics_angry_wolves != AngryWolfGoal.Type.DISABLED) {
            angryWolves(wolf);
        }
    }

    private void avoidPlayers(Mob mob) {
        this.mobGoals.addGoal(mob, 0, new AvoidPlayerGoal(mob));
    }

    private void angryWolves(Wolf wolf) {
        this.mobGoals.addGoal(wolf, 0, new AngryWolfGoal(wolf, this.config.entity_mechanics_angry_wolves));
    }

}
