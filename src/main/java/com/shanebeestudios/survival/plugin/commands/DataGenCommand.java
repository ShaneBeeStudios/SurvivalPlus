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
        this.tagFileGenerator = new TagFileGenerator();
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("datagen")
            .withPermission(Permissions.COMMAND_DATA_GEN.permission())
            .executes(info -> {
                this.tagFileGenerator.generateBlockTags(this.plugin.getDataFolder(), "generated/block-tags.yml");
                this.tagFileGenerator.generateItemTags(this.plugin.getDataFolder(), "generated/item-tags.yml");
                Utils.sendColoredMini(info.sender(), "Finished generating block-tags.yml");
            });
    }

}
