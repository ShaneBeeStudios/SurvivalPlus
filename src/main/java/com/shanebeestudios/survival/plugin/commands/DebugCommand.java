package com.shanebeestudios.survival.plugin.commands;

import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.api.data.Permissions;
import com.shanebeestudios.survival.api.data.Placeholders;
import com.shanebeestudios.survival.api.item.Items;
import com.shanebeestudios.survival.api.item.Nutrition;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;

public class DebugCommand extends BaseCommand{
    public DebugCommand(SurvivalPlugin plugin) {
        super(plugin);
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("debug")
            .withPermission(Permissions.COMMAND_DEBUG.permission())
            .then(LiteralArgument.literal("nutrition")
                .executesConsole(info -> {
                    Nutrition.debug();
                }))
            .then(LiteralArgument.literal("items")
                .then(LiteralArgument.literal("create")
                    .executesPlayer(info -> {
                        Items.debug(info.sender());
                    }))
                .then(LiteralArgument.literal("remove")
                    .executesPlayer(info -> {
                        Items.debug(null);
                    })))
            .then(LiteralArgument.literal("placeholders")
                .executesConsole(info -> {
                    Placeholders.debug();
                }))
            .then(LiteralArgument.literal("permissions")
                .executesConsole(info -> {
                    Permissions.debug();
                }));
    }

}
