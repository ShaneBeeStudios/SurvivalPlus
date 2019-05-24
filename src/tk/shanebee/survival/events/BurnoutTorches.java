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
import java.util.Objects;
import java.util.Random;

/** <b>Burnout torches after a given amount of time</b>
 *  <p>
 *  When torches are placed, they will burn out after x amount of seconds <br>
 *  When you break one of the burnt out torches it will drop a stick
 *  </p>
 */
public class BurnoutTorches implements Listener {

    // TODO Experimental Feature

    private int seconds = Survival.settings.getInt("Mechanics.BurnoutTorches.BurnoutTime");
    private boolean relightable = Survival.settings.getBoolean("Mechanics.BurnoutTorches.Relightable");

    private FileConfiguration data;
    private File data_file;
    private Survival main;

    public BurnoutTorches(Survival main) {
        this.main = main;
        loadDataFile(main.getServer().getConsoleSender());
        toBurnout();
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
        // TODO add check for creative mode (don't burnout creative torches?!?!)
        if (block.getType() == Material.TORCH || block.getType() == Material.WALL_TORCH) {
            if (!Items.compare(mainHand, Items.PERSISTENT_TORCH)) {
                burnoutTorch(block);
                setNonPersistent(block);
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
            if (isNonPersistent(block)) {
                unsetNonPersistent(block);
            }
            return;
        }
        if (block.getType() == Material.REDSTONE_WALL_TORCH || block.getType() == Material.REDSTONE_TORCH) {
            if (((Lightable) block.getBlockData()).isLit()) return;
            e.setDropItems(false);
            loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.STICK));
        } else if (block.getType() == Material.TORCH || block.getType() == Material.WALL_TORCH) {
            if (!isNonPersistent(block)) {
                e.setDropItems(false);
                loc.getWorld().dropItemNaturally(loc, Items.get(Items.PERSISTENT_TORCH));
            } else {
                unsetNonPersistent(block);
            }
        }
    }

    private void burnoutTorch(Block block) {
        burnoutTorch(block, seconds);
    }

    /** Sets a torch to burn out after x seconds
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
            unsetNonPersistent(block);
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

    /** Adds a non persistent torch to the data.yml file
     *
     * @param block The torch to make non persistent
     */
    @SuppressWarnings("WeakerAccess")
    public void setNonPersistent(Block block) {
        List<String> list = data.getStringList("NonPersistent Torches");
        long time = System.currentTimeMillis();
        time = time + (1000 * seconds);
        list.add(locToString(block.getLocation()) + " time:" + time);
        data.set("NonPersistent Torches", list);
        try {
            data.save(data_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Removes a non persistent torch from the data.yml file
     *
     * @param block The torch to remove as non persistent
     */
    @SuppressWarnings("WeakerAccess")
    public void unsetNonPersistent(Block block) {
        List<String> list = data.getStringList("NonPersistent Torches");
        for (Object string : list.toArray()) {
            if (stringMatchLoc(((String) string), block.getLocation())) {
                list.remove(string);
            }
        }
        data.set("NonPersistent Torches", list);
        try {
            data.save(data_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Checks if a torch is non persistent
     *
     * @param block The torch to check
     * @return Whether its persistent or not
     */
    @SuppressWarnings("WeakerAccess")
    public boolean isNonPersistent(Block block) {
        return containsLoc(block.getLocation());
    }

    private String locToString(Location loc) {
        assert loc.getWorld() != null;
        return ("world:" + loc.getWorld().getName() + " x:" + loc.getX() + " y:" + loc.getY() + " z:" + loc.getZ())
                .replace(".0", "");
    }

    private boolean stringMatchLoc(String string, Location location) {
        String[] loc = string.split(" ");
        assert location.getWorld() != null;
        String world = location.getWorld().getName();
        String x = String.valueOf(location.getX()).replace(".0", "");
        String y = String.valueOf(location.getY()).replace(".0", "");
        String z = String.valueOf(location.getZ()).replace(".0", "");
        if (loc[0].equalsIgnoreCase("world:" + world)) {
            if (loc[1].equalsIgnoreCase("x:" + x)) {
                if (loc[2].equalsIgnoreCase("y:" + y)) {
                    return loc[3].equalsIgnoreCase("z:" + z);
                }
            }
        }
        return false;
    }

    private boolean containsLoc(Location loc) {
        List<String> list = data.getStringList("NonPersistent Torches");
        for (String torch : list) {
            if (stringMatchLoc(torch, loc))
                return true;
        }
        return false;
    }

    private void toBurnout() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            List<String> list = data.getStringList("NonPersistent Torches");
            for (String string : list) {
                String[] loc = string.split(" ");
                long time = Long.valueOf(loc[4].replace("time:", ""));
                if (time < System.currentTimeMillis()) {
                    String world = loc[0].replace("world:", "");
                    int x = Integer.valueOf(loc[1].replace("x:", ""));
                    int y = Integer.valueOf(loc[2].replace("y:", ""));
                    int z = Integer.valueOf(loc[3].replace("z:", ""));
                    Block block = Objects.requireNonNull(Bukkit.getServer().getWorld(world)).getBlockAt(x, y, z);
                    if (block.getType() == Material.TORCH || block.getType() == Material.WALL_TORCH) {
                        burnoutTorch(block, 20);
                    }
                }
            }
        }, 20 * 60, 20 * 60);
    }

}