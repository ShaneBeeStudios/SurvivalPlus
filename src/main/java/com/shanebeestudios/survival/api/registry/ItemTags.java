package com.shanebeestudios.survival.api.registry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

/**
 * Custom item tags
 * <p>These are created in the `item-tags.yml` file</p>
 */
public class ItemTags {

    private ItemTags() {
    }

    private static boolean initialized = false;

    /**
     * @hidden
     */
    public static void initialize() {
        if (initialized) {
            throw new IllegalStateException("BlockTags already initialized");
        }
        initialized = true;
    }

    public static final Tag<Material> PREVENT_DUAL_WIELD = getTag("prevent_dual_wield");

    private static @NotNull Tag<Material> getTag(String key) {
        NamespacedKey namespacedKey = NamespacedKey.fromString("survival_plus:" + key);
        assert namespacedKey != null;
        Tag<Material> tag = Bukkit.getTag(Tag.REGISTRY_ITEMS, namespacedKey, Material.class);
        if (tag == null) {
            throw new IllegalArgumentException("Could not find tag for " + namespacedKey.toString());
        }
        return tag;
    }

}
