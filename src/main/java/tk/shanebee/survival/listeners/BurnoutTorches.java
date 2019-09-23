package tk.shanebee.survival.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.ItemManager;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.managers.BlockManager;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

class BurnoutTorches implements Listener {

    // TODO Experimental Feature

    private boolean relightable = Survival.settings.getBoolean("Mechanics.BurnoutTorches.Relightable");
    private boolean persistentTorches = Survival.settings.getBoolean("Mechanics.BurnoutTorches.PersistentTorches");
    private boolean dropTorch = Survival.settings.getBoolean("Mechanics.BurnoutTorches.DropTorch");

    private BlockManager torchManager;

    BurnoutTorches(Survival main) {
        this.torchManager = main.getBlockManager();
    }

    @EventHandler
    private void onBlockUpdate(BlockPhysicsEvent e) {
        Block block = e.getBlock();
        if (block.getType() == Material.REDSTONE_TORCH || block.getType() == Material.REDSTONE_WALL_TORCH) {
            if (!((Lightable) block.getBlockData()).isLit()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onRelight(PlayerInteractEvent e) {
        if (!relightable) return;
        Player player = e.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        Block block = e.getClickedBlock();
        if (block == null || (block.getType() != Material.REDSTONE_TORCH && block.getType() != Material.REDSTONE_WALL_TORCH))
            return;
        if (tool.getType() != Material.FLINT_AND_STEEL && !ItemManager.compare(tool, Items.FIRESTRIKER)) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        e.setCancelled(true);
        if (block.getType() == Material.REDSTONE_WALL_TORCH) {
            BlockFace face = ((Directional) block.getBlockData()).getFacing();
            block.setType(Material.WALL_TORCH);
            Directional dir = ((Directional) block.getBlockData());
            dir.setFacing(face);
            block.setBlockData(dir);
        } else {
            block.setType(Material.TORCH);
        }
        Random rand = new Random();
        Location loc = block.getLocation();
        assert loc.getWorld() != null;
        loc.getWorld().playSound(loc, Sound.ITEM_FLINTANDSTEEL_USE, 1.0F, rand.nextFloat() * 0.4F + 0.8F);

        if ((Utils.getDurability(tool) + 1) < tool.getType().getMaxDurability())
            Utils.setDurability(tool, Utils.getDurability(tool) + 1);
        else {
            player.getInventory().setItemInMainHand(null);
            assert player.getLocation().getWorld() != null;
            player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
        }
        torchManager.setNonPersistent(block);
        torchManager.burnoutTorch(block);
    }

    @EventHandler
    private void onPlaceTorch(BlockPlaceEvent e) {
        Block block = e.getBlock();
        ItemStack mainHand = e.getItemInHand();
        // TODO add check for creative mode (don't burnout creative torches?!?!)
        if (block.getType() == Material.TORCH || block.getType() == Material.WALL_TORCH) {
            if (!ItemManager.compare(mainHand, Items.PERSISTENT_TORCH)) {
                torchManager.burnoutTorch(block);
                torchManager.setNonPersistent(block);
            }
        }
    }

    @EventHandler
    private void onBreakTorch(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location loc = e.getBlock().getLocation();
        assert loc.getWorld() != null;
        if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) {
            if (torchManager.isNonPersistent(block)) {
                torchManager.unsetNonPersistent(block);
            }
            return;
        }
        if (block.getType() == Material.REDSTONE_WALL_TORCH || block.getType() == Material.REDSTONE_TORCH) {
            if (((Lightable) block.getBlockData()).isLit()) return;
            e.setDropItems(false);
            loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.STICK));
        } else if (block.getType() == Material.TORCH || block.getType() == Material.WALL_TORCH) {
            if (persistentTorches && !torchManager.isNonPersistent(block)) {
                e.setDropItems(false);
                loc.getWorld().dropItemNaturally(loc, ItemManager.get(Items.PERSISTENT_TORCH));
            } else {
                torchManager.unsetNonPersistent(block);
                if (!dropTorch) {
                    e.setDropItems(false);
                    loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.STICK));
                }
            }
        }
    }

}