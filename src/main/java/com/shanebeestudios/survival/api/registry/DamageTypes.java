package com.shanebeestudios.survival.api.registry;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Key;
import org.bukkit.damage.DamageType;

/**
 * Custom {@link DamageType DamageTypes}
 */
@SuppressWarnings("UnstableApiUsage")
public class DamageTypes {

    public static final DamageType ENDER_POWER = get("ender_power");

    private static DamageType get(String key) {
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE)
            .get(Key.key("survival_plus:" + key));
    }

}
