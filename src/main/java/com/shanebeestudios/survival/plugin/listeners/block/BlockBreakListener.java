package com.shanebeestudios.survival.plugin.listeners.block;

import com.shanebeestudios.survival.api.data.Permissions;
import com.shanebeestudios.survival.api.item.Items;
import com.shanebeestudios.survival.api.registry.BlockTags;
import com.shanebeestudios.survival.api.util.ItemUtils;
import com.shanebeestudios.survival.api.util.Utils;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.plugin.managers.MessageManager;
import com.shanebeestudios.survival.plugin.managers.MessageManager.MessageType;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class BlockBreakListener implements Listener {

    private final Config config;
    private final MessageManager messageManager;

    public BlockBreakListener(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
        this.messageManager = plugin.getMessageManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        ItemStack tool = player.getInventory().getItemInMainHand();

        Block block = event.getBlock();
        Material material = block.getType();

        if (Permissions.BYPASS_REQUIRED_TOOLS.has(player)) return;

        if (this.config.survival_break_only_with_shovel) {
            if (!Tag.ITEMS_SHOVELS.isTagged(tool.getType())) {
                // Gravel drop flint
                if (material == Material.GRAVEL) {
                    event.setDropItems(false);

                    Random rand = new Random();
                    double chance = rand.nextDouble();

                    if (chance <= this.config.survival_drop_rate_flint)
                        block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.1, 0.5), new ItemStack(Material.FLINT));
                    return;
                } else if (BlockTags.REQUIRES_SHOVEL.isTagged(material)) {
                    event.setCancelled(true);
                    player.updateInventory();
                    this.messageManager.sendMessage(player, MessageType.REQUIRES_SHOVEL);
                    return;
                }
            } else if (this.config.survival_break_only_with_sickle) {
                // Prevent bypassing sickle by breaking block below
                Block above = block.getRelative(BlockFace.UP);
                if (BlockTags.REQUIRES_SICKLE.isTagged(above.getType())) {
                    above.setType(Material.AIR);
                }
                return;
            }
        }

        if (this.config.survival_break_only_with_sickle && BlockTags.REQUIRES_SICKLE.isTagged(material)) {
            if (!Items.Tags.SICKLES.isTagged(tool)) {
                event.setCancelled(true);
                this.messageManager.sendMessage(player, MessageType.REQUIRES_SICKLE);
            } else {
                event.setDropItems(false);
                Location loc = event.getBlock().getLocation();
                int random = 1;
                int damageItemAmount = 1;
                boolean isFullyGrown = true;

                if (event.getBlock().getBlockData() instanceof Ageable ageable) {
                    isFullyGrown = ageable.getAge() == ageable.getMaximumAge();
                }

                // Flint/Stone sickles drop a chance of 0-1 items (not grown) or 1-2 (grown)
                if (Items.FLINT_SICKLE.is(tool)) {
                    damageItemAmount = 4;
                    random = isFullyGrown ? new Random().nextInt(2) + 1 : new Random().nextInt(2);
                } else if (Items.STONE_SICKLE.is(tool)) {
                    damageItemAmount = 2;
                    random = isFullyGrown ? new Random().nextInt(2) + 1 : new Random().nextInt(2);
                }
                // Iron/Diamond sickles drop a chance of 1 (not grown) or 2-4 items (grown)
                else if (Items.IRON_SICKLE.is(tool) || Items.DIAMOND_SICKLE.is(tool)) {
                    random = isFullyGrown ? new Random().nextInt(2) + 3 : 1;
                }

                for (Material drop : Utils.getDrops(material, isFullyGrown)) {
                    if (drop != Material.AIR && random != 0) {
                        assert loc.getWorld() != null;
                        if (drop == Material.PUMPKIN) { // prevent duping pumpkins
                            random = 1;
                        }
                        loc.getWorld().dropItemNaturally(loc.add(0.5, 0.1, 0.5), new ItemStack(drop, random));
                    }
                }
                ItemUtils.damageItem(player, tool, damageItemAmount);
            }
            return;
        }

        if (this.config.survival_break_only_with_axe && BlockTags.REQUIRES_AXE.isTagged(material)) {
            if (!Tag.ITEMS_AXES.isTagged(tool.getType())) {
                event.setCancelled(true);
                player.updateInventory();
                this.messageManager.sendMessage(player, MessageType.REQUIRES_AXE);
                return;
            }
        }

        if (this.config.survival_break_only_with_pickaxe && BlockTags.REQUIRES_PICKAXE.isTagged(material)) {
            if (!Tag.ITEMS_PICKAXES.isTagged(tool.getType())) {
                event.setCancelled(true);
                player.updateInventory();
                this.messageManager.sendMessage(player, MessageType.REQUIRES_PICKAXE);
                return;
            }
        }

        if (this.config.survival_break_only_with_shears && tool.getType() != Material.SHEARS) {
            //Sticks - Maybe this should be removed since 1.14+ leaves drop sticks?!?!?
            if (Tag.LEAVES.isTagged(material)) {
                Random rand = new Random();
                double chance = rand.nextDouble();

                if (chance <= this.config.survival_drop_rate_stick)
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5, 0.1, 0.5), new ItemStack(Material.STICK));
                return;
            }
            if (BlockTags.REQUIRES_SHEARS.isTagged(material)) {
                event.setCancelled(true);
                player.updateInventory();
                this.messageManager.sendMessage(player, MessageType.REQUIRES_SHEARS);
                return;
            }
        }

        if (this.config.recipes_workbench && material == Material.CRAFTING_TABLE && !event.isCancelled()) {
            event.setDropItems(false);
            ItemStack workbench = Items.WORKBENCH.getItemStack();
            block.getWorld().dropItem(block.getLocation(), workbench);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    private void onHarvest(PlayerInteractEvent e) {
        if (e.isCancelled()) return;
        if (!this.config.survival_break_only_with_sickle) return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
            return;
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        ItemStack tool = player.getInventory().getItemInMainHand();
        assert block != null;
        if (block.getType() == Material.SWEET_BERRY_BUSH) {
            Ageable bush = ((Ageable) block.getBlockData());
            if (e.getItem() != null && e.getItem().getType() == Material.BONE_MEAL) {
                if (bush.getAge() == 3) {
                    e.setCancelled(true);
                    return;
                } else return;
            }
            if (!Items.Tags.SICKLES.isTagged(tool)) {
                e.setCancelled(true);
                this.messageManager.sendMessage(player, MessageType.REQUIRES_SICKLE);
            } else {
                if (bush.getAge() >= 2) {
                    int berries = 0;
                    Location loc = block.getLocation();
                    assert loc.getWorld() != null;
                    e.setCancelled(true);
                    int random = new Random().nextInt(5) + 1;

                    if (Items.FLINT_SICKLE.is(tool)) {
                        if (bush.getAge() == 3) {
                            berries = 1;
                        }
                    } else if (Items.STONE_SICKLE.is(tool)) {
                        if (bush.getAge() == 2) {
                            if (random <= 4) berries = 1;
                        } else if (bush.getAge() == 3) {
                            if (random <= 3) berries = 1;
                            else berries = 2;
                        }
                    } else if (Items.IRON_SICKLE.is(tool) || Items.DIAMOND_SICKLE.is(tool)) {
                        if (bush.getAge() == 2) {
                            if (random <= 3) berries = 1;
                            else berries = 2;
                        } else if (bush.getAge() == 3) {
                            if (random <= 4) berries = 2;
                            else berries = 4;
                        }
                    }
                    if (berries != 0)
                        loc.getWorld().dropItemNaturally(loc.add(0.5, 0.1, 0.5), new ItemStack(Material.SWEET_BERRIES, berries));

                    bush.setAge(1);
                    block.setBlockData(bush);
                    player.playSound(loc, Sound.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, 1, 1);
                    ItemUtils.damageItem(player, tool, 1);
                }
            }
        }
    }

    @EventHandler
    private void onWaterBreakCrops(BlockPhysicsEvent event) {
        if (!this.config.survival_break_only_with_sickle) return;
        if (event.getSourceBlock().getType() == Material.WATER) {
            Material type = event.getBlock().getType();
            if (BlockTags.REQUIRES_SICKLE.isTagged(type)) {
                if (type == Material.MELON || type == Material.PUMPKIN) return;
                event.getBlock().setType(Material.AIR);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onTrample(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (!this.config.survival_break_only_with_sickle) return;
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock() == null) return;
            if (event.getClickedBlock().getType() == Material.FARMLAND) {
                Location loc = event.getClickedBlock().getLocation();
                assert loc.getWorld() != null;
                loc.getWorld().playEffect(loc, Effect.STEP_SOUND, event.getClickedBlock().getRelative(BlockFace.UP).getType());
                event.getClickedBlock().getRelative(BlockFace.UP).setType(Material.AIR);
            }
        }
    }

}
