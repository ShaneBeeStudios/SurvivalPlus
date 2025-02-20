package com.shanebeestudios.survival.api.data;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Key;
import org.bukkit.enchantments.Enchantment;

/**
 * Custom {@link Enchantment Enchantments}
 */
public class Enchantments {

    public static final Enchantment BLAZING = get("blazing");
    public static final Enchantment OBSIDIAN_POWER = get("obsidian_power");

    @SuppressWarnings("PatternValidation")
    private static Enchantment get(String key) {
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT)
            .get(Key.key("survival_plus:" + key));
    }

}
