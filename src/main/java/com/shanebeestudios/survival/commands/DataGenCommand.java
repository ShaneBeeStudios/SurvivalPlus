package com.shanebeestudios.survival.commands;

import com.shanebeestudios.survival.data.Permissions;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.generator.BlockTagFileGenerator;
import com.shanebeestudios.survival.util.Utils;

public class DataGenCommand extends BaseCommand {

    private final BlockTagFileGenerator blockTagFileGenerator;

    public DataGenCommand(SurvivalPlugin plugin) {
        super(plugin);
        this.blockTagFileGenerator = new BlockTagFileGenerator();
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("datagen")
            .withPermission(Permissions.COMMAND_DATA_GEN.permission())
            .executes(info -> {
                this.blockTagFileGenerator.generateBlockTags(this.plugin.getDataFolder(), "generated/block-tags.yml");
                Utils.sendColoredMini(info.sender(), "Finished generating block-tags.yml");
            });
    }


}
