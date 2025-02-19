package com.shanebeestudios.survival.goals;

import com.destroystokyo.paper.entity.Pathfinder;
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
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

@SuppressWarnings({"NullableProblems", "UnstableApiUsage"})
public class AvoidPlayerGoal implements Goal<@NotNull Mob> {

    private static final Registry<ItemType> ITEM_REGISTRY = RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM);

    @SuppressWarnings("DataFlowIssue")
    private static final GoalKey<@NotNull Mob> GOAL_KEY = GoalKey.of(Mob.class, NamespacedKey.fromString("survival_plus:avoid_player_goal"));

    private final Random random = new Random();
    private final Mob mob;
    private Player avoid;
    private Pathfinder pathfinder;
    private Pathfinder.PathResult path;
    private @Nullable Tag<ItemType> foodTag;
    private double speed = 1.6;

    @SuppressWarnings("PatternValidation")
    public AvoidPlayerGoal(Mob mob) {
        this.mob = mob;
        this.pathfinder = mob.getPathfinder();
        TagKey<ItemType> tagKey = TagKey.create(RegistryKey.ITEM, Key.key(mob.getType().key() + "_food"));
        if (ITEM_REGISTRY.hasTag(tagKey)) {
            this.foodTag = ITEM_REGISTRY.getTag(tagKey);
        }
    }

    @Override
    public boolean shouldActivate() {
        Optional<Player> any = this.mob.getNearbyEntities(7, 7, 7)
            .stream()
            .filter(entity -> entity.getType() == EntityType.PLAYER)
            .map(entity -> (Player) entity)
            .filter(player -> player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
            .findAny();
        if (any.isEmpty()) return false;

        this.avoid = any.get();
        if (!shouldAvoid()) return false;
        this.pathfinder = this.mob.getPathfinder();
        // TODO paper bug causes this to error
//        if (this.mob instanceof Animals animal && !animal.isAdult()) {
//            Goal<Animals> goal = Bukkit.getMobGoals().getGoal(animal, VanillaGoal.FOLLOW_PARENT);
//            if (goal != null) {
//                goal.stop();
//                Optional<Entity> parent = this.mob.getNearbyEntities(10, 4, 10).stream().filter(entity -> entity != this.mob
//                    && entity.getType() == this.mob.getType()).findFirst();
//                if (parent.isPresent()) {
//                    Pathfinder.PathResult pathToParent = this.pathfinder.findPath(parent.get().getLocation());
//                    if (pathToParent != null) {
//                        this.path = pathToParent;
//                        return true;
//                    }
//                }
//            }
//        }
        Location mobLoc = this.mob.getLocation();
        Vector direction = mobLoc.toVector().subtract(this.avoid.getLocation().toVector()).normalize().multiply(10);
        Location newLoc = mobLoc.add(direction);
        int x = random.nextInt(2, 5);
        int z = random.nextInt(2, 5);
        x = random.nextBoolean() ? x : -x;
        z = random.nextBoolean() ? z : -z;
        Location add = newLoc.add(x, 0, z).getWorld().getHighestBlockAt(newLoc).getLocation().add(0, 1, 0);
        this.path = this.pathfinder.findPath(add);
        return this.path != null;
    }

    private boolean shouldAvoid() {
        if (this.avoid instanceof Player player && this.foodTag != null) {
            PlayerInventory inventory = player.getInventory();
            TypedKey<ItemType> hand = TypedKey.create(RegistryKey.ITEM, inventory.getItemInMainHand().getType().key());
            TypedKey<ItemType> off = TypedKey.create(RegistryKey.ITEM, inventory.getItemInOffHand().getType().key());
            return !this.foodTag.contains(hand) && !this.foodTag.contains(off);
        }
        return !this.avoid.isSneaking();
    }

    @Override
    public boolean shouldStayActive() {
        int statistic = this.avoid.getStatistic(Statistic.PLAY_ONE_MINUTE);
        this.speed = statistic > 48000 ? 1.6 : 1.25;
        return this.shouldAvoid() && this.path != null && this.pathfinder.hasPath();
    }

    @Override
    public void start() {
        this.pathfinder.moveTo(this.path, this.speed);
    }

    @Override
    public void stop() {
        this.avoid = null;
        this.path = null;
    }

    @Override
    public @NotNull GoalKey<@NotNull Mob> getKey() {
        return GOAL_KEY;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE);
    }

}
