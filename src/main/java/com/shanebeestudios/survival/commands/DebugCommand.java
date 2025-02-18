package com.shanebeestudios.survival.commands;

import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.item.Nutrition;

public class DebugCommand extends BaseCommand{
    public DebugCommand(SurvivalPlugin plugin) {
        super(plugin);
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("debug")
            .withPermission(CommandPermission.OP)
            .then(LiteralArgument.literal("nutrition")
                .executes(info -> {
                    Nutrition.debug();
                }))
            .then(LiteralArgument.literal("items")
                .then(LiteralArgument.literal("create")
                    .executesPlayer(info -> {
                        Items.debug(info.sender());
                    }))
                .then(LiteralArgument.literal("remove")
                    .executes(info -> {
                        Items.debug(null);
                    })));
    }

}
