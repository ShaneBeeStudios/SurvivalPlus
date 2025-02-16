package tk.shanebee.survival.listeners.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.util.BlockTags;
import tk.shanebee.survival.util.Utils;

public class CompassWaypoint implements Listener {

    private final Lang lang;
    private final PlayerManager playerManager;

    public CompassWaypoint(Survival plugin) {
        this.lang = plugin.getLang();
        this.playerManager = plugin.getPlayerManager();
    }

    @EventHandler
    private void onItemClick(PlayerInteractEvent event) {
        if (event.hasItem()) {
            Player player = event.getPlayer();
            ItemStack mainItem = player.getInventory().getItemInMainHand();
            ItemStack offItem = player.getInventory().getItemInOffHand();
            if (mainItem.getType() == Material.COMPASS || offItem.getType() == Material.COMPASS) {

                // Prevent the event firing twice
                if (mainItem.getType() == Material.COMPASS && event.getHand() == EquipmentSlot.OFF_HAND) return;
                else if (offItem.getType() == Material.COMPASS && event.getHand() == EquipmentSlot.HAND) return;

                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Block clickedBlock = event.getClickedBlock();
                    if (!player.isSneaking())
                        return; // To prevent accidentally resetting waypoint, player needs to sneak
                    assert clickedBlock != null;
                    switch (clickedBlock.getType()) {
                        case HOPPER:
                        case CRAFTING_TABLE:
                        case DROPPER:
                        case DISPENSER:
                            return;
                        default:
                    }
                    if (Tag.BEDS.isTagged(clickedBlock.getType())) return;
                    if (Tag.FENCE_GATES.isTagged(clickedBlock.getType())) return;
                    if (Tag.DOORS.isTagged(clickedBlock.getType())) return;
                    if (BlockTags.COOKING_BLOCK.isTagged(clickedBlock.getType())) return;
                    if (BlockTags.STORAGE_BLOCK.isTagged(clickedBlock.getType())) return;
                    if (BlockTags.UTILITY_BLOCK.isTagged(clickedBlock.getType())) return;

                    Location loc = clickedBlock.getRelative(event.getBlockFace()).getLocation();
                    Utils.sendColoredMini(player,  lang.compass_waypoint_set, locToString(loc));
                    loc.add(0.5, 0.5, 0.5);
                    this.playerManager.setWaypoint(player, loc, true);
                }

                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    PlayerData playerData = this.playerManager.getPlayerData(player);
                    Location waypoint = playerData.getCompassWaypoint(player.getWorld());
                    if (waypoint != null) {
                        int distance = (int) player.getLocation().distance(waypoint);
                        String s = locToString(waypoint);
                        Utils.sendColoredMini(player, lang.compass_waypoint_get, distance, s);
                    } else {
                        Utils.sendColoredMini(player, lang.compass_waypoint_unset);
                    }
                }
            }
        }
    }

    @EventHandler
    private void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = this.playerManager.getPlayerData(player);
        Location waypoint = playerData.getCompassWaypoint(player.getWorld());
        if (waypoint != null) {
            player.setCompassTarget(waypoint);
        }
    }

    private String locToString(Location loc) {
        return " (" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ")";
    }

}
