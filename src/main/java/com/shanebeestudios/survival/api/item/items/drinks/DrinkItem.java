package com.shanebeestudios.survival.api.item.items.drinks;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.UseRemainder;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.shanebeestudios.survival.api.item.Item;

import java.util.List;

/**
 * Represents a drinkable {@link Item} that has a thirst level
 */
@SuppressWarnings("UnstableApiUsage")
public abstract class DrinkItem extends Item {

    private double thirstLevel;

    @Override
    protected void setupDefaults(String key, ItemStack itemStack) {
        this.thirstLevel = ITEM_CONFIG.getDouble(key, "thirst_level", 1);
        super.setupDefaults(key, itemStack);
    }

    @Override
    protected void setupDefaults(String key, ItemStack itemStack, boolean vanillaModel) {
        this.thirstLevel = ITEM_CONFIG.getDouble(key, "thirst_level", 1);
        super.setupDefaults(key, itemStack, vanillaModel);
    }

    protected void setupDefaults(String key, ItemStack itemStack, @NotNull List<ConsumeEffect> effects) {
        setupDefaults(key, itemStack, effects, null);
    }

    protected void setupDefaults(String key, ItemStack itemStack, @NotNull List<ConsumeEffect> effects, @Nullable ItemStack remainder) {
        Consumable.Builder consumable = Consumable.consumable();
        consumable.hasConsumeParticles(false);
        consumable.sound(Key.key("minecraft:entity.generic.drink"));
        consumable.animation(ItemUseAnimation.DRINK);
        if (!effects.isEmpty()) {
            consumable.addEffects(effects);
        }

        itemStack.setData(DataComponentTypes.CONSUMABLE, consumable.build());
        itemStack.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        UseRemainder useRemainder = UseRemainder.useRemainder(remainder != null ? remainder : new ItemStack(Material.GLASS_BOTTLE));
        itemStack.setData(DataComponentTypes.USE_REMAINDER, useRemainder);
        setupDefaults(key, itemStack);
    }

    public double getThirstLevel() {
        return this.thirstLevel;
    }

}
