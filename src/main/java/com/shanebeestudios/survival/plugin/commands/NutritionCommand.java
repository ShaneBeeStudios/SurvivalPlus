package com.shanebeestudios.survival.plugin.commands;

import com.shanebeestudios.survival.api.data.Permissions;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import org.bukkit.entity.Player;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.api.gui.NutritionGUI;

public class NutritionCommand extends BaseCommand {

    public NutritionCommand(SurvivalPlugin plugin) {
        super(plugin);
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("nutrition")
            .withPermission(Permissions.COMMAND_NUTRITION.permission())
            .executesPlayer(info -> {
                Player player = info.sender();
                NutritionGUI gui = new NutritionGUI(this.plugin);
                gui.openInventory(player, 0);
            });
    }

}
