package com.shanebeestudios.survival.plugin.listeners.server;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.plugin.config.Lang;
import com.shanebeestudios.survival.api.util.Utils;

public class Guide implements Listener {

    private final SurvivalPlugin plugin;
    private final Lang lang;
    private final Config config;

    public Guide(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.lang = plugin.getLang();
        this.config = plugin.getSurvivalConfig();
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().hasPlayedBefore() && this.config.welcome_guide_new_players) return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Player player = e.getPlayer();

//            Component component = Utils.getMini(this.lang.survival_guide_msg);
//            Component hover = Utils.getMini(this.lang.survival_guide_hover_msg);
//            Component link = Utils.getMini(this.lang.survival_guide_click_msg)
//                .hoverEvent(HoverEvent.showText(hover))
//                .clickEvent(ClickEvent.openUrl(this.lang.survival_guide_link));
//            player.sendMessage(component);
            Utils.sendColoredMini(player, this.lang.survival_guide_msg);
        }, 20L * this.config.welcome_guide_delay);
    }

}
