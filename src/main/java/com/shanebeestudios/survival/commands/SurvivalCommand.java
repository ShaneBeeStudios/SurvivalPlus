package com.shanebeestudios.survival.commands;

import dev.jorel.commandapi.CommandTree;
import com.shanebeestudios.survival.SurvivalPlugin;

import java.util.ArrayList;
import java.util.List;

public class SurvivalCommand {

    public SurvivalCommand(SurvivalPlugin plugin, String commandName) {
        List<BaseCommand> arguments = new ArrayList<>();

        arguments.add(new GiveItemCommand(plugin));
        arguments.add(new HealCommand(plugin));
        arguments.add(new NutritionCommand(plugin));
        arguments.add(new PlayerDataCommand(plugin));
        arguments.add(new ReloadCommand(plugin));
        arguments.add(new StatCommand(plugin));

        if (plugin.getSurvivalConfig().settings_local_chat_distance >= 0) {
            arguments.add(new ToggleChatCommand(plugin));
        }

        // TODO comment out
        arguments.add(new DataGenCommand(plugin));
        arguments.add(new DebugCommand(plugin));

        register(commandName, arguments);
    }

    private void register(String commandName, List<BaseCommand> arguments) {
        CommandTree command = new CommandTree(commandName);
        for (BaseCommand argument : arguments) {
            command.then(argument.register());
        }

        command.register();
    }

}
