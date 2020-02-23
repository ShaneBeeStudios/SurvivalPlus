package tk.shanebee.survival.listeners.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Merchant;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.MerchantManager;

public class MerchantTrades implements Listener {

    private MerchantManager merchantManager;

    public MerchantTrades(Survival plugin) {
        this.merchantManager = plugin.getMerchantManager();
    }

    @EventHandler
    private void onClickVillager(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof Merchant) {
            this.merchantManager.updateRecipes(entity);
        }
    }

}
