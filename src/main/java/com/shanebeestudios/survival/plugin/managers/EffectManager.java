package com.shanebeestudios.survival.plugin.managers;

import com.shanebeestudios.survival.api.util.Utils;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.plugin.tasks.tool.GiantBlade;
import com.shanebeestudios.survival.plugin.tasks.tool.ObsidianMace;
import com.shanebeestudios.survival.plugin.tasks.tool.QuartzPickaxe;
import com.shanebeestudios.survival.plugin.tasks.tool.Valkyrie;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectManager {

    private final SurvivalPlugin plugin;
    private final Config config;

    // Effect Tasks
    private GiantBlade giantBlade = null;
    private ObsidianMace obsidianMace = null;
    private QuartzPickaxe quartzPickaxe = null;
    private Valkyrie valkyrie = null;

    public EffectManager(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
        loadEffects();
    }

    private void loadEffects() {
        if (config.legendary_giant_blade)
            this.giantBlade = new GiantBlade(plugin);
        if (config.legendary_obsidian_mace)
            this.obsidianMace = new ObsidianMace(plugin);
        if (config.legendary_valkyrie)
            this.valkyrie = new Valkyrie(plugin);
        if (config.legendary_quartz_pickaxe)
            this.quartzPickaxe = new QuartzPickaxe(plugin);
    }

    /**
     * Stop all effect tasks
     */
    @SuppressWarnings("unused")
    public void cancelTasks() {
        if (giantBlade != null)
            giantBlade.cancel();
        if (obsidianMace != null)
            obsidianMace.cancel();
        if (quartzPickaxe != null)
            quartzPickaxe.cancel();
        if (valkyrie != null)
            valkyrie.cancel();
    }

    /**
     * Apply obsidian mace effects to player and enemy
     *
     * @param player Player to apply Regeneration to
     * @param enemy  Enemy to apply weakness and slowness to
     */
    public void applyObsidianMaceEffects(Player player, LivingEntity enemy) {
        enemy.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0, false));
        enemy.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 0, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 48, 2, true));
        Location particleLoc = player.getLocation();
        particleLoc.setY(particleLoc.getY() + 2);
        Utils.spawnParticle(particleLoc, Particle.HEART, 2, 0.5, 0.5, 0.5);
    }

}
