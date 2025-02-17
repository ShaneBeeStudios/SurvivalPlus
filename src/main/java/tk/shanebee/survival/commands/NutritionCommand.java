package tk.shanebee.survival.commands;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import org.bukkit.entity.Player;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.gui.NutritionGUI;
import tk.shanebee.survival.item.Nutrition;

public class NutritionCommand extends BaseCommand {

    public NutritionCommand(SurvivalPlugin plugin) {
        super(plugin);
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("nutrition")
            .withPermission(Permissions.COMMAND_NUTRITION)
            .executesPlayer(info -> {
                Player player = info.sender();
                NutritionGUI gui = new NutritionGUI(this.plugin);
                gui.openInventory(player, 0);
            })
            .then(LiteralArgument.literal("debug")
                .withPermission(Permissions.COMMAND_NUTRITION_DEBUG)
                .executes(info -> {
                    Nutrition.debug();
                }));
    }

}
