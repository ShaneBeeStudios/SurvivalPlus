package com.shanebeestudios.survival.plugin.managers;

import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Lang;
import com.shanebeestudios.survival.api.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager for sending delayed messages to players
 * <p>Messages are sent at most every 5 seconds,
 * preventing players from being bombarded with the
 * same message repeatedly</p>
 */
public class MessageManager {

    public enum MessageType {
        ARROWS_OFFHAND {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.arrows_off_hand;
            }
        },
        ARROWS_OFFHAND_CROSSBOW {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.arrows_off_hand_crossbow;
            }
        },
        BOW_MAIN_HAND {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.bow_main_hand;
            }
        },
        FISH_MAIN_HAND {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.fishing_main_hand;
            }
        },
        FISH_OFF_HAND {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.fishing_off_hand;
            }
        },
        GRAPPLING_HOOK_MAIN_HAND {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.grappling_main_hand;
            }
        },
        GRAPPLING_HOOK_OFF_HAND {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.grappling_off_hand;
            }
        },
        REQUIRES_AXE {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.task_must_use_axe;
            }
        },
        REQUIRES_HAMMER {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.task_must_use_hammer;
            }
        },
        REQUIRES_PICKAXE {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.task_must_use_pick;
            }
        },
        REQUIRES_SHEARS {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.task_must_use_shear;
            }
        },
        REQUIRES_SHOVEL {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.task_must_use_shovel;
            }
        },
        REQUIRES_SICKLE {
            @Override
            String getMessage(Lang lang) {
                return "<red>" + lang.task_must_use_sickle;
            }
        };

        MessageType() {
        }

        abstract String getMessage(Lang lang);
    }

    private final Lang lang;
    private final Map<MessageType, List<Player>> messages = new HashMap<>();

    public MessageManager(SurvivalPlugin plugin) {
        this.lang = plugin.getLang();
        for (MessageType value : MessageType.values()) {
            this.messages.put(value, new ArrayList<>());
        }
        Bukkit.getScheduler().runTaskTimer(plugin, () ->
                this.messages.forEach((key, playerList) -> playerList.clear()),
            100, 100);
    }

    public void sendMessage(Player player, MessageType messageType) {
        if (this.messages.get(messageType).contains(player)) return;
        this.messages.get(messageType).add(player);
        Utils.sendColoredMini(player, messageType.getMessage(this.lang));
    }

}
