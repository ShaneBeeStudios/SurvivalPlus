package com.shanebeestudios.survival.listeners.entity;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.goals.AvoidPlayerGoal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AvoidPlayerListener implements Listener {

    private final Config config;

    public AvoidPlayerListener(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
    }

    @EventHandler
    private void onEntityJoinWorld(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Mob mob)) return;

        if (this.config.entity_mechanics_mobs_avoid_players.contains(entity.getType())) {
            Bukkit.getMobGoals().addGoal(mob, 0, new AvoidPlayerGoal(mob));
        }
    }

}
