package com.shanebeestudios.survival.api.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Utility methods for {@link Player Players}
 */
public class PlayerUtils {

    /**
     * Freeze/Unfreeze a player
     * <p>Prevents them from walking/jumping</p>
     *
     * @param player Player to freeze
     * @param freeze Whether to freeze or unfreeze
     */
    public static void freezePlayer(@NotNull Player player, boolean freeze) {
        AttributeInstance moveAttribute = player.getAttribute(Attribute.MOVEMENT_SPEED);
        assert moveAttribute != null;

        if (freeze) {
            moveAttribute.setBaseValue(0);
        } else {
            // Default player value from Minecraft
            moveAttribute.setBaseValue(0.10000000149011612D);
        }

        AttributeInstance jumpAttribute = player.getAttribute(Attribute.JUMP_STRENGTH);
        assert jumpAttribute != null;

        if (freeze) {
            jumpAttribute.setBaseValue(0);
        } else {
            jumpAttribute.setBaseValue(jumpAttribute.getDefaultValue());
        }
    }

}
