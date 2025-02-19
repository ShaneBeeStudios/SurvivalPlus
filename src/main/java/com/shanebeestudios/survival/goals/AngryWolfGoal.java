package com.shanebeestudios.survival.goals;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.tag.Tag;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.key.Key;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;

@SuppressWarnings({"UnstableApiUsage", "NullableProblems"})
public class AngryWolfGoal implements Goal<@NotNull Wolf> {

    public enum Type {
        ALWAYS(),
        NIGHT(),
        DISABLED();

        public static Type getByKey(String key) {
            return switch (key) {
                case "always" -> ALWAYS;
                case "night" -> NIGHT;
                default -> DISABLED;
            };
        }
    }

    private static final Registry<ItemType> ITEM_REGISTRY = RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM);

    @SuppressWarnings("DataFlowIssue")
    private static final GoalKey<@NotNull Wolf> GOAL_KEY = GoalKey.of(Wolf.class, NamespacedKey.fromString("survival_plus:angry_wolf_goal"));

    private final Wolf wolf;
    private final Type type;
    private Player target;
    private @Nullable Tag<ItemType> foodTag;

    @SuppressWarnings("PatternValidation")
    public AngryWolfGoal(Wolf wolf, Type type) {
        this.wolf = wolf;
        this.type = type;
        TagKey<ItemType> tagKey = TagKey.create(RegistryKey.ITEM, Key.key(wolf.getType().key() + "_food"));
        if (ITEM_REGISTRY.hasTag(tagKey)) {
            this.foodTag = ITEM_REGISTRY.getTag(tagKey);
        }
    }

    @Override
    public boolean shouldActivate() {
        if (this.wolf.isAngry()) return false; // He's already angry
        if (this.type == Type.NIGHT && this.wolf.getWorld().isDayTime()) return false;

        Optional<Player> any = this.wolf.getNearbyEntities(10, 7, 10)
            .stream()
            .filter(entity -> entity.getType() == EntityType.PLAYER)
            .map(entity -> (Player) entity)
            .filter(player -> player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
            .findAny();
        if (any.isEmpty()) return false;

        this.target = any.get();

        return shouldAttack();
    }

    private boolean shouldAttack() {
        if (this.wolf.isTamed()) return false;
        if (this.type == Type.NIGHT && this.wolf.getWorld().isDayTime()) return false;
        if (this.wolf.getLocation().distanceSquared(this.target.getLocation()) > (10 * 10)) return false;

        if (this.foodTag != null) {
            PlayerInventory inventory = this.target.getInventory();
            TypedKey<ItemType> hand = TypedKey.create(RegistryKey.ITEM, inventory.getItemInMainHand().getType().key());
            TypedKey<ItemType> off = TypedKey.create(RegistryKey.ITEM, inventory.getItemInOffHand().getType().key());
            return !this.foodTag.contains(hand) && !this.foodTag.contains(off);
        }
        return true;
    }

    @Override
    public boolean shouldStayActive() {
        return shouldAttack();
    }

    @Override
    public void start() {
        this.wolf.setTarget(this.target);
        this.wolf.setAngry(true);
    }

    @Override
    public void stop() {
        this.target = null;
        this.wolf.setAngry(false);
        this.wolf.setTarget(null);
    }

    @Override
    public @NotNull GoalKey<@NotNull Wolf> getKey() {
        return GOAL_KEY;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.TARGET);
    }

}
