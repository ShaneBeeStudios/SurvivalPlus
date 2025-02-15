package tk.shanebee.survival.listeners.server;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.UUID;

public class ResourcePackListener implements Listener {

    private final String resourcePackUrl;
    private final UUID resourcePackID = UUID.fromString("cd60c108-b291-4d5a-a9e8-1404defeed1b");
    private final Component kickMessage;
    private final Component resourcePackMessage;

    public ResourcePackListener(Survival plugin) {
        Config config = plugin.getSurvivalConfig();
        Lang lang = plugin.getLang();
        this.resourcePackUrl = config.settings_resource_pack_url;
        this.kickMessage = Utils.getMini(lang.resource_pack_fail_download);
        this.resourcePackMessage = Utils.getMini( lang.resource_pack_apply);
    }


    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try {
            player.setResourcePack(this.resourcePackID, this.resourcePackUrl, "", this.resourcePackMessage, true);
        } catch (IllegalArgumentException ex) {
            Utils.logMini("[ResourcePackListener] Invalid resource pack URL: %s", this.resourcePackUrl);
            Utils.logMini("[ResourcePackListener] Error Message: %s", ex.getMessage());
            kick(player);
        }
    }

    @EventHandler
    private void resourcePackEvent(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        switch (event.getStatus()) {
            case DECLINED:
                Utils.logMini("<red>Player '%s' denied the resource pack and was kicked!", player.getName());
                break;
            case FAILED_DOWNLOAD, FAILED_RELOAD:
                Utils.logMini("<red>Player '%s' failed to download the resource pack!", player.getName());
                kick(player);
                break;
            case INVALID_URL:
                Utils.logMini("<red>Player '%s' failed to download the resource pack due to invalid url: '%s'",
                    player.getName(), this.resourcePackUrl);
                kick(player);
                break;
            case DISCARDED:

        }
    }

    private void kick(Player player) {
        player.kick(this.kickMessage);
    }

}
