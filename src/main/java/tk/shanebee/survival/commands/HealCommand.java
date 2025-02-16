package tk.shanebee.survival.commands;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.util.Utils;

import java.util.Collection;
import java.util.StringJoiner;

public class HealCommand extends BaseCommand {

    public HealCommand(SurvivalPlugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("unchecked")
    @Override
    Argument<?> register() {
        return LiteralArgument.literal("heal")
            .withPermission(Permissions.COMMAND_HEAL)
            .executesPlayer(info -> {
                heal(info.sender());
                Utils.sendColoredMini(info.sender(), this.lang.cmd_heal_self);
            })
            .then(new EntitySelectorArgument.ManyPlayers("players")
                .withPermission(Permissions.COMMAND_HEAL_OTHERS)
                .executes(info -> {
                    CommandSender sender = info.sender();
                    Collection<Player> players = (Collection<Player>) info.args().get("players");
                    assert players != null;

                    StringJoiner joiner = new StringJoiner(", ");
                    players.forEach(player -> {
                        joiner.add(player.getName());
                        heal(player);
                        Utils.sendColoredMini(player, lang.cmd_heal_by, sender.getName());
                    });
                    Utils.sendColoredMini(sender, this.lang.cmd_heal_other, joiner.toString());
                }));
    }

    @SuppressWarnings("DataFlowIssue")
    private void heal(Player player) {
        PlayerData playerData = this.plugin.getPlayerManager().getPlayerData(player);

        player.setHealth(player.getAttribute(Attribute.MAX_HEALTH).getValue());
        playerData.setHunger(this.config.mechanics_hunger_respawn_amount);

        if (this.config.mechanics_thirst_enabled) {
            playerData.setThirst(this.config.mechanics_thirst_respawn_amount);
        }
        if (this.config.mechanics_food_diversity_enabled) {
            int carbs = this.config.mechanics_food_respawn_carbs;
            int proteins = this.config.mechanics_food_respawn_proteins;
            int salts = this.config.mechanics_food_respawn_vitamins;
            playerData.setNutrients(carbs, proteins, salts);
        }
        if (this.config.mechanics_energy_enabled) {
            playerData.setEnergy(this.config.mechanics_energy_respawn);
        }
        player.clearActivePotionEffects();
    }

}
