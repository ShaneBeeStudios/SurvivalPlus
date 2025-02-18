package com.shanebeestudios.survival.commands;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import org.bukkit.entity.Player;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.data.PlayerData;
import com.shanebeestudios.survival.util.Utils;

import java.util.Collection;

public class PlayerDataCommand extends BaseCommand {

    public PlayerDataCommand(SurvivalPlugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("unchecked")
    @Override
    Argument<?> register() {
        return LiteralArgument.literal("playerdata")
            .withPermission(Permissions.COMMAND_PLAYERDATA)
            .then(new EntitySelectorArgument.ManyPlayers("players")
                .then(new MultiLiteralArgument("type", PlayerData.DataType.getNames())
                    .then(new MultiLiteralArgument("change", "add", "remove", "set")
                        .then(new DoubleArgument("amount")
                            .executes(info -> {
                                Collection<Player> players = (Collection<Player>) info.args().get("players");
                                String type = info.args().getByClass("type", String.class);
                                String change = info.args().getByClass("change", String.class);
                                Double value = info.args().getByClass("amount", Double.class);
                                PlayerData.DataType dataType = PlayerData.DataType.getByName(type);

                                if (dataType == null || players == null || change == null || value == null) return;

                                players.forEach(player -> {
                                    double changeValue = value;
                                    PlayerData playerData = this.playerManager.getPlayerData(player);
                                    if (playerData == null) {
                                        Utils.sendColoredMini(info.sender(), "<red>Invalid player data for <aqua>" + player.getName());
                                        return;
                                    }
                                    changeValue = switch (change) {
                                        case "add" -> value + playerData.getData(dataType);
                                        case "remove" -> playerData.getData(dataType) - value;
                                        default -> changeValue;
                                    };
                                    playerData.setData(dataType, changeValue);
                                });
                            })))));
    }

}
