package tk.shanebee.survival.listeners.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.item.Items;
import tk.shanebee.survival.util.Utils;

import java.util.List;

public class GrapplingHook implements Listener {

    private final Lang lang;

    public GrapplingHook(SurvivalPlugin plugin) {
        this.lang = plugin.getLang();
    }

    @EventHandler
    private void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.getType() == Material.FISHING_ROD) {
            player.getInventory().getItemInOffHand();
            if (offHand.getType() == Material.AIR) {

                if (Items.GRAPPLING_HOOK.is(mainHand)) {
                    if (event.getState() == State.IN_GROUND) {
                        List<Entity> nearbyEntities = player.getNearbyEntities(50, 50, 50);

                        Entity hook = null;

                        for (Entity e : nearbyEntities) // loop through entities
                        {
                            if (e.getType() == EntityType.FISHING_BOBBER) //Hook found
                            {
                                hook = e;
                                break;
                            }
                        }

                        if (hook != null) {
                            Location hookLoc = hook.getLocation();
                            Location playerLoc = player.getLocation();

                            playerLoc.setY(playerLoc.getY() + 0.5);


                            Vector vector = hookLoc.toVector().subtract(playerLoc.toVector());
                            if (vector.getY() > 0)
                                vector.setY(Math.sqrt(vector.getY()));

                            player.teleport(playerLoc);
                            player.setVelocity(vector.multiply(0.5));
                        }
                    } else if (event.getState() == State.CAUGHT_ENTITY) {
                        if (event.getCaught() != null) {
                            Location playerLoc = player.getLocation();
                            Location entityLoc = event.getCaught().getLocation();

                            playerLoc.setY(playerLoc.getY() + 0.5);
                            entityLoc.setY(entityLoc.getY() + 0.5);

                            if (event.getCaught().getType() != EntityType.ITEM) {
                                Vector vector = entityLoc.toVector().subtract(playerLoc.toVector());
                                if (vector.getY() > 0)
                                    vector.setY(Math.sqrt(vector.getY()) * 4);

                                player.teleport(playerLoc);
                                player.setVelocity(vector.multiply(0.5).multiply(0.25));
                            }

                            Vector reverseVector = playerLoc.toVector().subtract(entityLoc.toVector());

                            if (reverseVector.getY() > 0)
                                reverseVector.setY(Math.sqrt(reverseVector.getY()));

                            if (event.getCaught().getType() != EntityType.ITEM) {
                                event.getCaught().teleport(entityLoc);
                                event.getCaught().setVelocity(reverseVector.multiply(0.5).multiply(0.125));
                            } else {
                                if (reverseVector.getY() > 0)
                                    reverseVector.setY(Math.sqrt(reverseVector.getY()) * 0.5);

                                event.getCaught().teleport(entityLoc);
                                event.getCaught().setVelocity(reverseVector.multiply(0.5).multiply(0.00625));
                            }
                        }
                    } else if (event.getState() == State.BITE || event.getState() == State.CAUGHT_FISH) {
                        event.setCancelled(true);
                        player.updateInventory();
                    }
                }
            } else {
                event.setCancelled(true);
                if (Items.GRAPPLING_HOOK.is(mainHand))
                    Utils.sendColoredMini(player, "<red>" + this.lang.grappling_off_hand);
                else
                    Utils.sendColoredMini(player, "<red>" + this.lang.fishing_off_hand);
                player.updateInventory();
            }
        } else {
            event.setCancelled(true);
            if (Items.GRAPPLING_HOOK.is(offHand))
                Utils.sendColoredMini(player, "<red>" + this.lang.grappling_main_hand);
            else
                Utils.sendColoredMini(player, "<red>" + this.lang.fishing_main_hand);
            player.updateInventory();
        }
    }

}
