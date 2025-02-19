package com.shanebeestudios.survival.plugin.listeners.entity;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Chest;
import org.bukkit.entity.Mob;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;

import java.util.ArrayList;
import java.util.List;

public class ChestPigmen implements Listener {

    private final List<Material> goldItems;
    private final int radius;
    private final NamespacedKey key = NamespacedKey.fromString("survival_plus:chest_pigmen");
    private final AttributeModifier mod;


    public ChestPigmen(SurvivalPlugin plugin) {
        this.goldItems = new ArrayList<>();
        for (Material material : Registry.MATERIAL) {
            if (material.isItem() && material.getKey().toString().contains("gold")) {
                this.goldItems.add(material);
            }
        }
        this.radius = plugin.getSurvivalConfig().entity_mechanics_pigmen_chest_radius;
        double speedModifier = plugin.getSurvivalConfig().entity_mechanics_pigmen_chest_speed;
        assert this.key != null;
        this.mod = speedModifier > 0 ? new AttributeModifier(this.key, speedModifier, Operation.ADD_SCALAR) : null;

    }

    @EventHandler
    private void onOpenChest(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        //if (player.getWorld().getEnvironment() != World.Environment.NETHER) return;
        if (event.getClickedBlock() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock().getType() != Material.CHEST)
            return;
        Chest chest = ((Chest) event.getClickedBlock().getState());
        if (chestContainsGold(chest)) {
            player.getNearbyEntities(this.radius, this.radius, this.radius).forEach(entity -> {
                if (entity instanceof PigZombie pigZombie) {
                    pigZombie.setTarget(player);
                    moveFaster(pigZombie);
                }
            });
        }
    }

    private boolean chestContainsGold(Chest block) {
        for (ItemStack item : block.getInventory().getContents()) {
            if (item == null) continue;
            if (this.goldItems.contains(item.getType())) return true;
        }
        return false;
    }

    @SuppressWarnings("DataFlowIssue")
    private void moveFaster(Mob mob) {
        if (this.mod == null) return;

        AttributeInstance attribute = mob.getAttribute(Attribute.MOVEMENT_SPEED);
        if (attribute != null && attribute.getModifier(this.key.key()) == null) {
            attribute.addTransientModifier(this.mod);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @EventHandler
    private void onStopTarget(EntityTargetEvent event) {
        // Remove speed when they stop targeting the player
        if (event.getEntity() instanceof PigZombie pigZombie && event.getTarget() == null) {
            AttributeInstance attribute = pigZombie.getAttribute(Attribute.MOVEMENT_SPEED);
            if (attribute != null && attribute.getModifier(this.key.key()) != null) {
                attribute.removeModifier(this.mod);
            }
        }
    }

}
