package com.shanebeestudios.survival.plugin.commands;

import com.shanebeestudios.survival.api.data.Permissions;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.api.util.Utils;

public class ReloadCommand extends BaseCommand {

    public ReloadCommand(SurvivalPlugin plugin) {
        super(plugin);
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("reload")
            .withPermission(Permissions.COMMAND_RELOAD.permission())
            .executes(info -> {
                this.plugin.loadSettings(info.sender());
                Utils.sendColoredMini(info.sender(), this.lang.prefix + "<green>Reload complete");
            });
    }

}
