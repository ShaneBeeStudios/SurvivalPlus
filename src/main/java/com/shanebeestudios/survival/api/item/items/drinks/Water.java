package com.shanebeestudios.survival.api.item.items.drinks;

import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * {@link com.shanebeestudios.survival.api.item.Item} class for water based drinks
 */
@SuppressWarnings("UnstableApiUsage")
public class Water extends DrinkItem {

    public Water(String key) {
        this(key, List.of());
    }

    public Water(String key, List<ConsumeEffect> effects) {
        this(key, effects, null);
    }

    public Water(String key, List<ConsumeEffect> effects, ItemStack remainder) {
        ItemStack itemStack = ItemType.STICK.createItemStack();
        setupDefaults(key, itemStack, effects, remainder);
    }

    /**
     * @hidden
     */
    public static Water dirty() {
        PotionEffect poison = new PotionEffect(PotionEffectType.POISON, 100, 0);
        PotionEffect nausea = new PotionEffect(PotionEffectType.NAUSEA, 200, 0);
        return new Water("dirty_water", List.of(
            ConsumeEffect.applyStatusEffects(List.of(poison), 0.5f),
            ConsumeEffect.applyStatusEffects(List.of(nausea), 1.0f)
        ));
    }

    /**
     * @hidden
     */
    public static Water salty() {
        PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER, 400, 0);
        PotionEffect nausea = new PotionEffect(PotionEffectType.NAUSEA, 100, 0);
        PotionEffect slowness = new PotionEffect(PotionEffectType.SLOWNESS, 400, 0);
        return new Water("salty_water", List.of(
            ConsumeEffect.applyStatusEffects(List.of(hunger), 0.75f),
            ConsumeEffect.applyStatusEffects(List.of(nausea), 0.5f),
            ConsumeEffect.applyStatusEffects(List.of(slowness), 0.5f)
        ));
    }

    /**
     * @hidden
     */
    public static Water murky() {
        PotionEffect poison = new PotionEffect(PotionEffectType.POISON, 200, 2);
        PotionEffect nausea = new PotionEffect(PotionEffectType.NAUSEA, 1000, 0);
        PotionEffect weakness = new PotionEffect(PotionEffectType.WEAKNESS, 1000, 0);
        PotionEffect slowness = new PotionEffect(PotionEffectType.SLOWNESS, 400, 0);
        return new Water("murky_water", List.of(
            ConsumeEffect.applyStatusEffects(List.of(poison), 0.8f),
            ConsumeEffect.applyStatusEffects(List.of(nausea), 0.5f),
            ConsumeEffect.applyStatusEffects(List.of(weakness), 1.0f),
            ConsumeEffect.applyStatusEffects(List.of(slowness), 0.2f)
        ));
    }

    /**
     * @hidden
     */
    public static Water clean() {
        return new Water("clean_water");
    }

    /**
     * @hidden
     */
    public static Water purified() {
        PotionEffect health = new PotionEffect(PotionEffectType.HEALTH_BOOST, 100, 2);
        return new Water("purified_water", List.of(
            ConsumeEffect.applyStatusEffects(List.of(health), 0.5f)
        ));
    }

    /**
     * @hidden
     */
    public static Water waterBowl() {
        return new Water("water_bowl", List.of(), new ItemStack(Material.BOWL));
    }

}
