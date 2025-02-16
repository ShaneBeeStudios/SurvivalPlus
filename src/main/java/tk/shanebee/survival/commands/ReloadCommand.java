package tk.shanebee.survival.commands;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.util.Utils;

public class ReloadCommand extends BaseCommand {

    public ReloadCommand(SurvivalPlugin plugin) {
        super(plugin);
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("reload")
            .withPermission(Permissions.COMMAND_RELOAD)
            .executes(info -> {
                this.plugin.loadSettings(info.sender());
                Utils.sendColoredMini(info.sender(), this.lang.prefix + "<green>Reload complete");
            });
    }

}
