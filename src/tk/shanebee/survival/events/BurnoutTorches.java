package tk.shanebee.survival.events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/** Burnout torches after a given amount of time
 *  <p>
 *  When torches are placed, they will burn out after x amount of seconds
 *  When you break one of the burnt out torches it will drop a stick
 *  </p>
 */
public class BurnoutTorches implements Listener {

    // TODO Experimental Feature

    private FileConfiguration data;
    private File data_file;
    private Survival main;

    public BurnoutTorches(Survival main) {
        this.main = main;
        loadDataFile(main.getServer().getConsoleSender());
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
        Player player = e.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        Block block = e.getClickedBlock();
        if (block == null || (block.getType() != Material.REDSTONE_TORCH && block.getType() != Material.REDSTONE_WALL_TORCH)) return;
        if (tool.getType() != Material.FLINT_AND_STEEL && !Items.compare(tool, Items.FIRESTRIKER)) return;
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
            Utils.setDurability(tool,Utils.getDurability(tool) + 1);
        else {
            player.getInventory().setItemInMainHand(null);
            assert player.getLocation().getWorld() != null;
            player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
        }
        burnoutTorch(block);
    }

    @EventHandler
    private void onPlaceTorch(BlockPlaceEvent e) {
        Block block = e.getBlock();
        ItemStack mainHand = e.getItemInHand();
        if (block.getType() == Material.TORCH || block.getType() == Material.WALL_TORCH) {
            if (!Items.compare(mainHand, Items.PERSISTENT_TORCH)) {
                burnoutTorch(block);
            } else {
                setPersistent(block);
            }
        }
    }

    @EventHandler
    private void onBreakTorch(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location loc = e.getBlock().getLocation();
        assert loc.getWorld() != null;
        if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) return;
        if (block.getType() == Material.REDSTONE_WALL_TORCH || block.getType() == Material.REDSTONE_TORCH) {
            if (((Lightable) block.getBlockData()).isLit()) return;
            e.setDropItems(false);
            loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.STICK));
        } else if (block.getType() == Material.TORCH || block.getType() == Material.WALL_TORCH) {
            if (isPersistent(block)) {
                e.setDropItems(false);
                loc.getWorld().dropItemNaturally(loc, Items.get(Items.PERSISTENT_TORCH));
                unsetPersistent(block);
            }
        }
    }

    private void burnoutTorch(Block block) {
        int burnout = 5; //TODO use config here
        burnoutTorch(block, burnout);
    }

    /** Sets a torch to burn out after x time
     *
     * @param seconds The time to wait for burnout in seconds
     * @param block The torch to burn out
     */
    @SuppressWarnings("WeakerAccess")
    public void burnoutTorch(Block block, int seconds) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Survival.instance, () -> {
            if (block.getType() != Material.TORCH && block.getType() != Material.WALL_TORCH) return;
            if (block.getType() == Material.TORCH)
                block.setType(Material.REDSTONE_TORCH);
            else {
                BlockFace face = ((Directional) block.getBlockData()).getFacing();
                block.setType(Material.REDSTONE_WALL_TORCH);
                Directional dir = ((Directional) block.getBlockData());
                dir.setFacing(face);
                block.setBlockData(dir);
            }
            Lightable torch = ((Lightable) block.getBlockData());
            torch.setLit(false);
            block.setBlockData(torch);
        }, 20 * seconds);
    }

    private void loadDataFile(CommandSender sender) {
        String loaded;
        data_file = new File(main.getDataFolder(), "data.yml");
        if (!data_file.exists()) {
            main.saveResource("data.yml", true);
            loaded = "&aNew data.yml created";
        } else {
            loaded = "&7data.yml &aloaded";
        }
        data = YamlConfiguration.loadConfiguration(data_file);
        Utils.sendColoredMsg(sender, Survival.lang.prefix + loaded);
    }

    /** Adds a persistent torch to the data.yml file
     *
     * @param block The torch to make persistent
     */
    @SuppressWarnings("WeakerAccess")
    public void setPersistent(Block block) {
        List<String> list = data.getStringList("Persistent Torches");
        list.add(locToString(block.getLocation()));
        data.set("Persistent Torches", list);
        try {
            data.save(data_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Removes a persistent torch from the data.yml file
     *
     * @param block The torch to remove as persistent
     */
    @SuppressWarnings("WeakerAccess")
    public void unsetPersistent(Block block) {
        List<String> list = data.getStringList("Persistent Torches");
        list.remove(locToString(block.getLocation()));
        data.set("Persistent Torches", list);
        try {
            data.save(data_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Checks if a torch is persistent
     *
     * @param block The torch to check
     * @return Whether its persistent or not
     */
    @SuppressWarnings("WeakerAccess")
    public boolean isPersistent(Block block) {
        return containsLoc(block.getLocation());
    }

    private String locToString(Location loc) {
        assert loc.getWorld() != null;
        return "world:" + loc.getWorld().getName() + " x:" + loc.getX() + " y:" + loc.getY() + " z:" + loc.getZ();
    }

    private boolean containsLoc(Location loc) {
        List<String> list = data.getStringList("Persistent Torches");
        return list.contains(locToString(loc));
    }

}