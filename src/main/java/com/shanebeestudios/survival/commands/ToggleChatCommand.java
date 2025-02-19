package com.shanebeestudios.survival.commands;

import com.shanebeestudios.survival.data.Permissions;
import dev.jorel.commandapi.BukkitStringTooltip;
import dev.jorel.commandapi.IStringTooltip;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.entity.Player;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.data.PlayerData;
import com.shanebeestudios.survival.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class ToggleChatCommand extends BaseCommand {

    private final List<IStringTooltip> typeTooltips = new ArrayList<>();

    public ToggleChatCommand(SurvivalPlugin plugin) {
        super(plugin);
        setupInfos("global", "Set chat to global chat");
        setupInfos("local", "Set chat to local chat");
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("chat")
            .withPermission(Permissions.COMMAND_CHAT.permission())
            //.then(new StringArgument("type")
            .then(new StringArgument("type")
                .replaceSuggestions(ArgumentSuggestions.stringsWithTooltips(this.typeTooltips))
                .executesPlayer(info -> {
                    Player player = info.sender();
                    String type = info.args().getByClassOrDefault("type", String.class, "global");
                    PlayerData playerData = this.playerManager.getPlayerData(player);
                    if (type.equalsIgnoreCase("global")) {
                        Utils.sendColoredMini(player, lang.toggle_chat_global);
                        playerData.setLocalChat(false);
                    } else if (type.equalsIgnoreCase("local")) {
                        Utils.sendColoredMini(player, lang.toggle_chat_local);
                        playerData.setLocalChat(true);
                    }
                }));
    }

    private void setupInfos(String stat, String tooltip) {
        this.typeTooltips.add(BukkitStringTooltip.ofString(stat, tooltip));
    }

}
