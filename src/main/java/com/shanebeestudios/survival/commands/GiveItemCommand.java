package com.shanebeestudios.survival.commands;

import com.shanebeestudios.survival.data.Permissions;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.item.Item;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

public class GiveItemCommand extends BaseCommand {

    private final List<String> names = new ArrayList<>();

    public GiveItemCommand(SurvivalPlugin plugin) {
        super(plugin);
        Items.allItemKeys().forEach(key -> this.names.add(key.value()));
    }

    @SuppressWarnings("unchecked")
    @Override
    Argument<?> register() {
        return LiteralArgument.literal("giveitem")
            .withPermission(Permissions.COMMAND_GIVEITEM.permission())
            .then(new EntitySelectorArgument.ManyPlayers("players")
                .then(new StringArgument("item")
                    .includeSuggestions(ArgumentSuggestions.strings(this.names))
                    .then(new IntegerArgument("amount", 1, 99)
                        .setOptional(true)
                        .executes(info -> {
                            CommandSender sender = info.sender();
                            CommandArguments args = info.args();
                            Collection<Player> players = (Collection<Player>) args.get("players");
                            String itemKey = args.getByClass("item", String.class);
                            int amount = args.getByClassOrDefault("amount", Integer.class, 1);

                            assert itemKey != null;
                            assert players != null;

                            Item item = Items.getByKey(itemKey);

                            if (item != null) {
                                ItemStack itemStack = item.getItemStack(amount);

                                StringJoiner joiner = new StringJoiner("<white>,<aqua> ");
                                players.forEach(player -> {
                                    giveItem(player, itemStack);
                                    joiner.add(player.getName());
                                });
                                String itemName = getItemName(itemStack);
                                if (itemName == null) itemName = item.getKey().toString();

                                sendMessage(sender, itemStack, itemName, amount, joiner.toString());
                            } else {
                                Utils.sendColoredMini(sender, this.lang.prefix + "<yellow>Invalid item <red>%s", itemKey);
                            }
                        }))));
    }

    private void giveItem(Player player, ItemStack item) {
        HashMap<Integer, ItemStack> returnMap = player.getInventory().addItem(item);
        if (!returnMap.isEmpty()) {
            World world = player.getWorld();
            Location location = player.getLocation();
            returnMap.values().forEach(itemStack -> world.dropItemNaturally(location, itemStack));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private @Nullable String getItemName(ItemStack itemStack) {
        if (itemStack.hasData(DataComponentTypes.CUSTOM_NAME)) {
            return Utils.reverseComponent(itemStack.getData(DataComponentTypes.CUSTOM_NAME));
        } else if (itemStack.hasData(DataComponentTypes.ITEM_NAME)) {
            return Utils.reverseComponent(itemStack.getData(DataComponentTypes.ITEM_NAME));
        }
        return null;
    }

    private void sendMessage(CommandSender sender, ItemStack itemStack, String itemName, int amount, String players) {
        String who = sender instanceof Player ? "You" : "CONSOLE";
        Component message = Utils.getMini(this.lang.prefix + "%s gave <aqua>%s<grey> of ", who, amount)
            .append(Utils.getMini("<white>[<aqua>%s<white>]<grey>", itemName).hoverEvent(itemStack.asHoverEvent()))
            .append(Utils.getMini(" to <aqua>%s<grey>", players));
        sender.sendMessage(message);
    }

}
