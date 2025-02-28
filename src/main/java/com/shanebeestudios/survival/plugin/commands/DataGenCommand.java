package com.shanebeestudios.survival.plugin.commands;

import com.shanebeestudios.survival.api.data.Permissions;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.api.generator.TagFileGenerator;
import com.shanebeestudios.survival.api.util.Utils;

public class DataGenCommand extends BaseCommand {

    private final TagFileGenerator tagFileGenerator;

    public DataGenCommand(SurvivalPlugin plugin) {
        super(plugin);
        this.tagFileGenerator = new TagFileGenerator(plugin);
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("datagen")
            .withPermission(Permissions.COMMAND_DATA_GEN.permission())
            .executes(info -> {
                this.tagFileGenerator.generate();
                Utils.sendColoredMini(info.sender(), "Finished generating block-tags.yml");
            });
    }

}
