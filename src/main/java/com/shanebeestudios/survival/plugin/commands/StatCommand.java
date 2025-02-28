package com.shanebeestudios.survival.plugin.commands;

import com.shanebeestudios.survival.api.data.Permissions;
import dev.jorel.commandapi.BukkitStringTooltip;
import dev.jorel.commandapi.IStringTooltip;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.api.data.Info;
import com.shanebeestudios.survival.api.data.Nutrient;
import com.shanebeestudios.survival.api.data.PlayerData;
import com.shanebeestudios.survival.api.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StatCommand extends BaseCommand {

    private final List<IStringTooltip> infoTooltips = new ArrayList<>();
    private final List<IStringTooltip> typeTooltips = new ArrayList<>();

    public StatCommand(SurvivalPlugin plugin) {
        super(plugin);

        setupInfos("all", "Manage all stats");
        setupInfos("hunger", "Manage hunger stat");
        if (this.config.mechanics_thirst_enabled) {
            setupInfos("thirst", "Manage thirst stat");
        }
        if (this.config.mechanics_energy_enabled) {
            setupInfos("energy", "Manage energy stat");
        }
        if (this.config.mechanics_food_diversity_enabled) {
            setupInfos("nutrients", "Manage nutrient stat");
        }

        if (this.config.mechanics_status_scoreboard) {
            setupTypes("toggle", "Toggle a stat");
            setupTypes("on", "Turn a stat on");
            setupTypes("off", "Turn a stat off");
        }
        setupTypes("show", "Show a stat in chat");
    }

    @Override
    Argument<?> register() {
        return LiteralArgument.literal("stats")
            .withPermission(Permissions.COMMAND_STATS.permission())
            .then(new StringArgument("info")
                .includeSuggestions(ArgumentSuggestions.stringsWithTooltips(this.infoTooltips))
                .then(new StringArgument("type")
                    .includeSuggestions(ArgumentSuggestions.stringsWithTooltips(this.typeTooltips))
                    .executesPlayer(commandInfo -> {
                        CommandArguments args = commandInfo.args();
                        Player player = commandInfo.sender();

                        String infoName = args.getByClassOrDefault("info", String.class, "all");
                        String type = args.getByClassOrDefault("type", String.class, "show");

                        Info info = getStat(infoName);

                        if (type.equalsIgnoreCase("show")) {
                            showStat(player, info);
                        } else {
                            manageStat(player, info, type);
                        }
                    })))
            ;
    }

    private void showStat(@NotNull Player player, @Nullable Info info) {
        if (info == null) {
            for (Info value : Info.values()) {
                showStat(player, value);
            }
            return;
        }

        PlayerData playerData = this.playerManager.getPlayerData(player);
        String message = switch (info) {
            case HUNGER -> String.format("<grey>%s<white>: <aqua>%.2f", this.lang.hunger, playerData.getHunger());
            case THIRST -> String.format("<grey>%s<white>: <aqua>%.2f", this.lang.thirst, playerData.getThirst());
            case ENERGY -> String.format("<grey>%s<white>: <aqua>%.2f", this.lang.energy, playerData.getEnergy());
            case NUTRIENTS -> String.format("<grey>%s<white>: " +
                    "<#A0E853>%s <white>= <aqua>%s, " +
                    "<#CE784D>%s <white>= <aqua>%s, " +
                    "<#53DDE8>%s <white>= <aqua>%s",
                this.lang.nutrients,
                this.lang.carbohydrates,
                playerData.getNutrient(Nutrient.CARBS),
                this.lang.protein,
                playerData.getNutrient(Nutrient.PROTEIN),
                this.lang.vitamins,
                playerData.getNutrient(Nutrient.VITAMINS));

            //nutrients.add("<#A0E853>" + this.lang.carbohydrates);
            //        nutrients.add("<#CE784D>" + this.lang.protein);
            //        nutrients.add("<#53DDE8>" + this.lang.vitamins);
        };

        Utils.sendColoredMini(player, message);
    }

    private void manageStat(@NotNull Player player, @Nullable Info info, @NotNull String type) {
        if (!this.config.mechanics_status_scoreboard) return;
        PlayerData playerData = this.playerManager.getPlayerData(player);

        if (info == null) {
            for (Info value : Info.values()) {
                switch (type) {
                    case "toggle" -> playerData.setInfoDisplayed(value, !playerData.isInfoDisplayed(value));
                    case "on" -> playerData.setInfoDisplayed(value, true);
                    case "off" -> playerData.setInfoDisplayed(value, false);
                }
            }
        } else {
            switch (type) {
                case "toggle" -> playerData.setInfoDisplayed(info, !playerData.isInfoDisplayed(info));
                case "on" -> playerData.setInfoDisplayed(info, true);
                case "off" -> playerData.setInfoDisplayed(info, false);
            }
        }
    }

    private Info getStat(String stat) {
        try {
            return Info.valueOf(stat.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void setupInfos(String stat, String tooltip) {
        this.infoTooltips.add(BukkitStringTooltip.ofString(stat, tooltip));
    }

    private void setupTypes(String type, String tooltip) {
        this.typeTooltips.add(BukkitStringTooltip.ofString(type, tooltip));
    }

}
