package com.shanebeestudios.survival.tasks;

import com.shanebeestudios.survival.data.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.data.Nutrient;
import com.shanebeestudios.survival.data.PlayerData;
import com.shanebeestudios.survival.managers.PlayerManager;
import com.shanebeestudios.survival.util.Utils;

class NutrientsEffect extends BukkitRunnable {

    private final Config config;
    private final PlayerManager playerManager;
    private PotionEffect VITAMINS_NORMAL = null;
    private PotionEffect VITAMINS_HARD = null;
    private PotionEffect PROTEIN_NORMAL = null;
    private PotionEffect PROTEIN_HARD = null;

    NutrientsEffect(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
        this.playerManager = plugin.getPlayerManager();
        loadEffects();
        this.runTaskTimer(plugin, -1, 320);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Permissions.BYPASS_STAT_NUTRITION.has(player)) continue;
            if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) continue;

            World world = player.getWorld();
            PlayerData playerData = playerManager.getPlayerData(player);

            if (playerData.getNutrient(Nutrient.CARBS) <= 0) {
                switch (world.getDifficulty()) {
                    case EASY:
                        player.setExhaustion(player.getExhaustion() + Math.max(this.config.mechanics_food_effects_carbs_ex_amp_easy, 0));
                        break;
                    case NORMAL:
                        player.setExhaustion(player.getExhaustion() + Math.max(this.config.mechanics_food_effects_carbs_ex_amp_medium, 0));
                        break;
                    case HARD:
                        player.setExhaustion(player.getExhaustion() + Math.max(this.config.mechanics_food_effects_carbs_ex_amp_hard, 0));
                        break;
                    default:
                }
            }

            if (playerData.getNutrient(Nutrient.VITAMINS) <= 0) {
                player.setExhaustion(player.getExhaustion() + Math.max(this.config.mechanics_food_effects_vitamins_ex_amp, 0));
                switch (world.getDifficulty()) {
                    case NORMAL:
                        if (VITAMINS_NORMAL != null) {
                            player.addPotionEffect(VITAMINS_NORMAL, true);
                        }
                        break;
                    case HARD:
                        if (VITAMINS_HARD != null) {
                            player.addPotionEffect(VITAMINS_HARD, true);
                        }
                        break;
                    default:
                }
            }

            if (playerData.getNutrient(Nutrient.PROTEIN) <= 0) {
                player.setExhaustion(player.getExhaustion() + Math.max(config.mechanics_food_effects_protein_ex_amp, 0));
                switch (world.getDifficulty()) {
                    case NORMAL:
                        if (PROTEIN_NORMAL != null) {
                            player.addPotionEffect(PROTEIN_NORMAL, true);
                        }
                        break;
                    case HARD:
                        if (PROTEIN_HARD != null) {
                            player.addPotionEffect(PROTEIN_HARD, true);
                        }
                        break;
                    default:
                }
            }
        }
    }

    private void loadEffects() {
        PotionEffectType vitamins_normal_type = getType(config.mechanics_food_effects_vitamins_se_normal_effect);
        if (vitamins_normal_type != null) {
            int vitamins_normal_amp = this.config.mechanics_food_effects_vitamins_se_normal_amp;
            int vitamins_normal_dur = this.config.mechanics_food_effects_vitamins_se_normal_duration;
            VITAMINS_NORMAL = new PotionEffect(vitamins_normal_type, vitamins_normal_dur * 20, vitamins_normal_amp, true, false, false);
        }
        PotionEffectType vitamins_hard_type = getType(config.mechanics_food_effects_vitamins_se_hard_effect);
        if (vitamins_hard_type != null) {
            int vitamins_hard_amp = this.config.mechanics_food_effects_vitamins_se_hard_amp;
            int vitamins_hard_dur = this.config.mechanics_food_effects_vitamins_se_hard_duration;
            VITAMINS_HARD = new PotionEffect(vitamins_hard_type, vitamins_hard_dur * 20, vitamins_hard_amp, true, false, false);
        }

        PotionEffectType protein_normal_type = getType(config.mechanics_food_effects_protein_se_normal_effect);
        if (protein_normal_type != null) {
            int protein_normal_amp = this.config.mechanics_food_effects_protein_se_normal_amp;
            int protein_normal_dur = this.config.mechanics_food_effects_protein_se_normal_duration;
            PROTEIN_NORMAL = new PotionEffect(protein_normal_type, protein_normal_dur * 20, protein_normal_amp, true, false, false);
        }
        PotionEffectType protein_hard_type = getType(config.mechanics_food_effects_protein_se_hard_effect);
        if (protein_hard_type != null) {
            int protein_hard_amp = this.config.mechanics_food_effects_protein_se_hard_amp;
            int protein_hard_dur = this.config.mechanics_food_effects_protein_se_hard_duration;
            PROTEIN_HARD = new PotionEffect(protein_hard_type, protein_hard_dur * 20, protein_hard_amp, true, false, false);
        }
    }

    private @Nullable PotionEffectType getType(String potionType) {
        NamespacedKey key = NamespacedKey.fromString(potionType);
        if (key != null) {
            PotionEffectType potionEffectType = Registry.EFFECT.get(key);
            if (potionEffectType != null) return potionEffectType;
        }
        Utils.logMini("<red>Invalid potion effect type: '%s'", potionType);
        return null;
    }

}
