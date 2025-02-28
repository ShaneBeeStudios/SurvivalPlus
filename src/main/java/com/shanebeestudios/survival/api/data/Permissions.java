package com.shanebeestudios.survival.api.data;

import com.shanebeestudios.survival.api.util.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.permissions.DefaultPermissions;

import java.util.LinkedHashMap;
import java.util.Map;

public class Permissions {

    public record Permission(String permission, org.bukkit.permissions.Permission bukkitPermission) {

        public boolean has(CommandSender sender) {
            return sender.hasPermission(this.bukkitPermission.getName());
        }
    }

    static final Map<String, org.bukkit.permissions.Permission> PERMISSIONS = new LinkedHashMap<>();

    // Command permissions
    public static final Permission COMMAND_CHAT = getCommand("chat", "Toggle global/local chat", PermissionDefault.TRUE);
    public static final Permission COMMAND_DATA_GEN = getCommand("datagen", "Generate stuff for configs, <red>should not be used", PermissionDefault.OP);
    public static final Permission COMMAND_DEBUG = getCommand("debug", "Debug some internal things", PermissionDefault.OP);
    public static final Permission COMMAND_GIVEITEM = getCommand("giveitem", "Give a custom item", PermissionDefault.OP);
    public static final Permission COMMAND_HEAL = getCommand("heal", "Heal self", PermissionDefault.OP);
    public static final Permission COMMAND_HEAL_OTHERS = getCommand("heal.others", "Heal others", PermissionDefault.OP);
    public static final Permission COMMAND_NUTRITION = getCommand("nutrition", "Open nutrition GUI", PermissionDefault.TRUE);
    public static final Permission COMMAND_PLAYERDATA = getCommand("playerdata", "Adjust player data", PermissionDefault.OP);
    public static final Permission COMMAND_RELOAD = getCommand("reload", "Reload config files", PermissionDefault.OP);
    public static final Permission COMMAND_STATS = getCommand("stats", "Toggle stats", PermissionDefault.TRUE);

    // Bypass permissions
    public static final Permission BYPASS_STAT_ENERGY = getBypass("stat.energy", "Bypass energy stats");
    public static final Permission BYPASS_STAT_NUTRITION = getBypass("stat.nutrition", "Bypass nutrition stats");
    public static final Permission BYPASS_REQUIRED_TOOLS = getBypass("required_tools", "Bypass required tools");
    public static final Permission BYPASS_STAT_THIRST = getBypass("stat.thirst", "Bypass thirst stats");
    public static final Permission BYPASS_WEATHER = getBypass("weather", "Bypass weather effects");

    private static Permission getCommand(String perm, String description, PermissionDefault defaultPermission) {
        return getBase("command", perm, description, defaultPermission);
    }

    private static Permission getBypass(String perm, String description) {
        return getBase("bypass", perm, description, PermissionDefault.FALSE);
    }

    private static Permission getBase(String base, String perm, String description, PermissionDefault defaultPermission) {
        String stringPerm = "survivalplus." + base + "." + perm;
        org.bukkit.permissions.Permission bukkitPermission = DefaultPermissions.registerPermission(stringPerm, description, defaultPermission);
        PERMISSIONS.put(stringPerm, bukkitPermission);
        return new Permission(stringPerm, bukkitPermission);
    }

    public static void debug() {
        Utils.logMini("Permissions:");
        for (Map.Entry<String, org.bukkit.permissions.Permission> entry : PERMISSIONS.entrySet()) {
            String color = switch (entry.getValue().getDefault()) {
                case OP -> "yellow";
                case TRUE, NOT_OP -> "green";
                case FALSE -> "red";
            };
            Utils.logMini("  <white>'<#F09616>%s<white>':", entry.getKey());
            Utils.logMini("    <grey> Description: <aqua>%s", entry.getValue().getDescription());
            Utils.logMini("    <grey> Default: <%s>%s", color, entry.getValue().getDefault().toString());
        }
    }

}
