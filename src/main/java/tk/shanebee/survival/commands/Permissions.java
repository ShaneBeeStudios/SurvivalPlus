package tk.shanebee.survival.commands;

public class Permissions {

    static final String COMMAND_CHAT = getCommand("chat");
    static final String COMMAND_GIVEITEM = getCommand("giveitem");
    static final String COMMAND_HEAL = getCommand("heal");
    static final String COMMAND_HEAL_OTHERS = getCommand("heal.others");
    static final String COMMAND_NUTRITION = getCommand("nutrition");
    static final String COMMAND_NUTRITION_DEBUG = getCommand("nutrition.debug");
    static final String COMMAND_RELOAD = getCommand("reload");
    static final String COMMAND_PLAYERDATA = getCommand("playerdata");

    private static String getCommand(String permission) {
        return "survivalplus.command." + permission;
    }

}
