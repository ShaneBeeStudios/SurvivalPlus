package com.shanebeestudios.survival.plugin.listeners.item;

import com.shanebeestudios.survival.api.registry.DamageTypes;
import com.shanebeestudios.survival.api.data.PlayerData;
import com.shanebeestudios.survival.api.data.Stat;
import com.shanebeestudios.survival.api.item.Items;
import com.shanebeestudios.survival.api.util.Utils;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Lang;
import com.shanebeestudios.survival.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Random;

public class GiantBladeListener implements Listener {

    private final SurvivalPlugin plugin;
    private final Lang lang;
    private final PlayerManager playerManager;
    private final Random random = new Random();

    public GiantBladeListener(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.lang = plugin.getLang();
        this.playerManager = plugin.getPlayerManager();
    }

    @EventHandler
    private void onItemClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.RIGHT_CLICK_AIR) return;
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        ItemStack mainItem = player.getInventory().getItemInMainHand();
        if (!player.isSprinting()) return;
        if (player.hasCooldown(mainItem)) return;

        PlayerData playerData = playerManager.getPlayerData(player);
        if (playerData.getStat(Stat.DUAL_WIELD) > 0) return;
        if (!Items.ENDER_GIANT_BLADE.is(mainItem)) return;

        chargeForward(player, mainItem);
    }

    private void chargeForward(Player player, ItemStack itemStack) {
        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
            player.setFoodLevel(player.getFoodLevel() - 1);

        PlayerData playerData = playerManager.getPlayerData(player);
        Utils.sendColoredMini(player, "<aqua>" + this.lang.charge);

        player.setCooldown(itemStack, 200);
        playerData.setStat(Stat.CHARGING, 10);

        final Runnable task = new Runnable() {
            public void run() {
                push(player);
                damageNearbyEnemies(player);
                effects(player);

                int times = playerData.getStat(Stat.CHARGING);
                if (--times > 0) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this, 1L);
                }
                playerData.setStat(Stat.CHARGING, times);
            }
        };

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, task, 0);
    }

    private void push(Player player) {
        Location playerLocation = player.getLocation();
        if (playerLocation.getPitch() < 0) playerLocation.setPitch(0);
        Vector newVelocity = playerLocation.getDirection().clone().multiply(2);
        player.setVelocity(newVelocity);
    }

    private void effects(Player player) {
        Location location = player.getLocation();
        location.getWorld().playSound(location, Sound.ENTITY_SHULKER_BULLET_HIT, 1.5F, this.random.nextFloat() * 0.4F + 0.8F);
        Utils.spawnParticle(location, Particle.EXPLOSION, 10, 0, 0, 0);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void damageNearbyEnemies(Player player) {
        Collection<LivingEntity> enemies = player.getLocation().getWorld().getNearbyLivingEntities(player.getLocation(), 2, 2, 2);
        for (LivingEntity enemy : enemies) {
            if (enemy == player) continue;
            DamageSource damageSource = DamageSource.builder(DamageTypes.ENDER_POWER)
                .withDirectEntity(player)
                .build();
            enemy.damage(Items.ENDER_GIANT_BLADE.getChargeDamage(), damageSource);
        }
    }

}
