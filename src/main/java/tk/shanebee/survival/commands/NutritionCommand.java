package tk.shanebee.survival.commands;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import org.bukkit.entity.Player;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.gui.NutritionGUI;
import tk.shanebee.survival.item.Nutrition;
import tk.shanebee.survival.util.Utils;

public class NutritionCommand extends BaseCommand{

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
            }).then(LiteralArgument.literal("debug")
                .withPermission(Permissions.COMMAND_NUTRITION_DEBUG)
                .executes(info -> {
                    itemTest();
                }));
    }

    // Used for debugging edible items
    private void itemTest() {
        Nutrition.getAllNutritions().forEach(nutrition -> {
            String key = nutrition.getKey().toString().replace(":", "<reset>:<green>");
            String item = nutrition.getItemStack().toString().replace("{", "<reset>{<aqua>").replace("}", "<reset>}<aqua>");
            Utils.logMini("Nutrition%s:", nutrition.isCustom() ? "<reset>(&cCUSTOM<reset>)&7" : "");
            Utils.logMini(" - Key: <green>%s", key);
            Utils.logMini(" - Item: &e%s", item);
        });
    }

}
