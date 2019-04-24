package tk.shanebee.survival.commands;

import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.util.Utils;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class GiveItem implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        String prefix = Utils.getColoredString(Survival.lang.prefix);
        if (args.length != 2) return true;
        OfflinePlayer player = Bukkit.getPlayer(args[0]);
        Items itemName;
        if (player != null) {
            try {
                itemName = Items.valueOf(args[1].toUpperCase());
                Location loc = ((Player) player).getLocation();
                loc.setY(loc.getY() + 1);
                Bukkit.getWorld(((Player) player).getWorld().getName()).dropItem(loc, Items.get(itemName));

                if (sender instanceof Player) {

                    Utils.sendColoredMsg(sender, prefix + "&6You gave &b" + itemName + " &6to &b" + player.getName());
                } else {
                    Utils.sendColoredMsg(sender, prefix + "&6CONSOLE gave &b" + itemName + " &6to &b" + player.getName());
                }
            } catch (IllegalArgumentException ignore) {
                Utils.sendColoredMsg(sender, prefix + "&b" + args[1] + "&c is not an item");
            }
        } else {
            Utils.sendColoredMsg(sender, prefix + "&b" + args[0] + " &cis not online");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0 || args.length >= 3) {
            return ImmutableList.of();
        }
        if (args.length <= 1) return null;
        ArrayList<String> matches = new ArrayList<>();
        for (Items name : Items.values()) {
            if (StringUtil.startsWithIgnoreCase(name.toString(), args[1])) {
                matches.add(name.toString());
            }
        }
        return matches;
    }

}
