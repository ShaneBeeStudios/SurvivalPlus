package tk.shanebee.survival.listeners.item;

import io.papermc.paper.entity.LookAnchor;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.data.Stat;
import tk.shanebee.survival.item.Items;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.util.ItemUtils;
import tk.shanebee.survival.util.PlayerUtils;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class MedicKit implements Listener {

    private final SurvivalPlugin plugin;
    private final Lang lang;
    private final PlayerManager playerManager;
    private final Random random = new Random();

    public MedicKit(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.lang = plugin.getLang();
        this.playerManager = plugin.getPlayerManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onDamaged(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getEntity() instanceof Player player) {
            PlayerData playerData = this.playerManager.getPlayerData(player);
            playerData.setStat(Stat.HEALING, 0);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onClickEntity(PlayerInteractEntityEvent event) {
        if (event.isCancelled()) return;
        final Player player = event.getPlayer();
        PlayerData playerData = this.playerManager.getPlayerData(player);
        ItemStack mainItem = player.getInventory().getItemInMainHand();

        if (!Items.MEDIC_KIT.is(mainItem) || !Items.MEDIC_KIT.canHeal(mainItem)) return;
        if (playerData.getStat(Stat.HEALING) > 0) return;
        if (player.isSneaking()) return;
        if (!(event.getRightClicked() instanceof Player patient)) return;
        if (!canBeHealed(patient)) return;

        PlayerData patientData = this.playerManager.getPlayerData(patient);

        if (patientData.getStat(Stat.HEALING) > 0) return;

        playerData.setStat(Stat.HEALING, 1);
        patientData.setStat(Stat.HEALING, 1);

        Utils.sendColoredMini(player, this.lang.healing_other, patient.getDisplayName());
        Utils.sendColoredMini(patient, this.lang.healing_being_healed, player.getDisplayName());

        Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {
            @Override
            public void run() {
                heal(player, patient, playerData, patientData, this);
            }
        }, 20);
    }

    @EventHandler
    private void onSelfClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            final Player player = event.getPlayer();
            ItemStack mainItem = player.getInventory().getItemInMainHand();
            if (!canBeHealed(player)) return;
            if (!Items.MEDIC_KIT.is(mainItem) || !Items.MEDIC_KIT.canHeal(mainItem)) return;

            PlayerData playerData = this.playerManager.getPlayerData(player);
            if (playerData.getStat(Stat.HEALING) > 0) return;
            if (!player.isSneaking()) return;

            playerData.setStat(Stat.HEALING, 1);
            Utils.sendColoredMini(player, this.lang.healing_self);

            Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {
                @Override
                public void run() {
                    heal(player, player, playerData, playerData, this);
                }
            }, 20);
        }
    }

    private void heal(@NotNull Player doctor, @NotNull Player patient, @NotNull PlayerData doctorData, @NotNull PlayerData patientData, @NotNull Runnable task) {
        int times = doctorData.getStat(Stat.HEAL_TIMES);
        boolean healingOther = doctor != patient;
        ItemStack medicKitItemStack = doctor.getInventory().getItemInMainHand();
        if (Items.MEDIC_KIT.is(medicKitItemStack) && Items.MEDIC_KIT.canHeal(medicKitItemStack) && canBeHealed(patient)) {
            World world = doctor.getWorld();
            if (healingOther) {
                doctor.lookAt(patient, LookAnchor.EYES, LookAnchor.EYES);
                patient.lookAt(doctor, LookAnchor.EYES, LookAnchor.EYES);
            }
            PlayerUtils.freezePlayer(doctor, true);
            PlayerUtils.freezePlayer(patient, true);
            float volume = healingOther ? (float) doctor.getLocation().distance(patient.getLocation()) : 1f;
            world.playSound(doctor.getLocation(), Sound.ENTITY_BREEZE_CHARGE, volume, this.random.nextFloat() * 0.4F + 0.8F);

            healPlayer(patient, 2.0);
            ItemUtils.damageItem(doctor, medicKitItemStack, 1);

            Utils.spawnParticle(patient.getLocation(), Particle.HAPPY_VILLAGER, 10, 0.25, 2, 0.25);
            if (healingOther) {
                Utils.spawnParticle(doctor.getLocation(), Particle.HAPPY_VILLAGER, 10, 0.25, 2, 0.25);
            }

            // Repeat
            Bukkit.getServer().getScheduler().runTaskLater(this.plugin, task, 20L);
            doctorData.setStat(Stat.HEAL_TIMES, times);
        } else {
            doctorData.setStat(Stat.HEALING, 0);
            if (healingOther) {
                patientData.setStat(Stat.HEALING, 0);
                Utils.sendColoredMini(patient, this.lang.healing_complete);
                PlayerUtils.freezePlayer(patient, false);
            }

            Utils.sendColoredMini(doctor, this.lang.healing_complete);
            PlayerUtils.freezePlayer(doctor, false);

            doctor.getInventory().removeItem(Items.MEDIC_KIT.getItemStack());
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void healPlayer(Player player, double amount) {
        AttributeInstance attribute = player.getAttribute(Attribute.MAX_HEALTH);
        assert attribute != null;
        if (attribute.getValue() - player.getHealth() > amount) {
            player.setHealth(player.getHealth() + amount);
        }
    }

    private boolean canBeHealed(Player player) {
        AttributeInstance attribute = player.getAttribute(Attribute.MAX_HEALTH);
        assert attribute != null;
        return player.getHealth() < (attribute.getValue() * 0.9);
    }

}
