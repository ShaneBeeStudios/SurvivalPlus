package com.shanebeestudios.survival.commands;

import com.shanebeestudios.survival.util.Utils;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.permissions.DefaultPermissions;

import java.util.LinkedHashMap;
import java.util.Map;

public class Permissions {

    static final Map<String, Permission> PERMISSIONS = new LinkedHashMap<>();

    static final String COMMAND_CHAT = getCommand("chat", "Toggle global/local chat", PermissionDefault.TRUE);
    static final String COMMAND_DATA_GEN = getCommand("datagen", "Generate stuff for configs, <red>should not be used", PermissionDefault.OP);
    static final String COMMAND_DEBUG = getCommand("debug", "Debug some internal things", PermissionDefault.OP);
    static final String COMMAND_GIVEITEM = getCommand("giveitem", "Give a custom item", PermissionDefault.OP);
    static final String COMMAND_HEAL = getCommand("heal", "Heal self", PermissionDefault.OP);
    static final String COMMAND_HEAL_OTHERS = getCommand("heal.others", "Heal others", PermissionDefault.OP);
    static final String COMMAND_NUTRITION = getCommand("nutrition", "Open nutrition GUI", PermissionDefault.TRUE);
    static final String COMMAND_PLAYERDATA = getCommand("playerdata", "Adjust player data", PermissionDefault.OP);
    static final String COMMAND_RELOAD = getCommand("reload", "Reload config files", PermissionDefault.OP);
    static final String COMMAND_STATS = getCommand("stats", "Toggle stats", PermissionDefault.TRUE);

    private static String getCommand(String perm, String description, PermissionDefault defaultPermission) {
        String stringPerm = "survivalplus.command." + perm;
        Permission permission = DefaultPermissions.registerPermission(stringPerm, description, defaultPermission);
        PERMISSIONS.put(stringPerm, permission);
        return stringPerm;
    }

    public static void debug() {
        Utils.logMini("Permissions:");
        for (Map.Entry<String, Permission> entry : PERMISSIONS.entrySet()) {
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
