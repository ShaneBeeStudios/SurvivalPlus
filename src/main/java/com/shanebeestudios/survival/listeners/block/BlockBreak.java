package com.shanebeestudios.survival.listeners.block;

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
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.config.Lang;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.util.BlockTags;
import com.shanebeestudios.survival.util.ItemUtils;
import com.shanebeestudios.survival.util.Utils;

import java.util.Random;

public class BlockBreak implements Listener {

    private final Config config;
    private final Lang lang;

    public BlockBreak(SurvivalPlugin plugin) {
        this.lang = plugin.getLang();
        this.config = plugin.getSurvivalConfig();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();

        ItemStack tool = player.getInventory().getItemInMainHand();

        Block block = event.getBlock();
        Material material = block.getType();

        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            if (!Items.QUARTZ_PICKAXE.is(tool)) {
                if (this.config.survival_break_only_with_shovel) {
                    if (!Tag.ITEMS_SHOVELS.isTagged(tool.getType())) {
                        if (BlockTags.REQUIRES_SHOVEL.isTagged(material)) {
                            event.setCancelled(true);
                            player.updateInventory();
                            Utils.sendColoredMini(player, "<red>" + this.lang.task_must_use_shovel);
                        }
                        //Flint
                        if (material == Material.GRAVEL) {
                            event.setDropItems(false);

                            Random rand = new Random();
                            double chance = rand.nextDouble();

                            if (chance <= this.config.survival_drop_rate_flint)
                                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5, 0.1, 0.5), new ItemStack(Material.FLINT));
                        }
                    } else {
                        Block above = block.getRelative(BlockFace.UP);
                        switch (block.getType()) {
                            case GRASS_BLOCK:
                            case DIRT:
                            case PODZOL:
                            case COARSE_DIRT:
                            case FARMLAND:
                                if (BlockTags.FARMABLE.isTagged(above.getType())) {
                                    above.setType(Material.AIR);
                                }
                        }
                    }
                }

                if (this.config.survival_break_only_with_axe && !Tag.ITEMS_AXES.isTagged(tool.getType())) {
                    if (BlockTags.REQUIRES_AXE.isTagged(material)) {
                        event.setCancelled(true);
                        player.updateInventory();
                        Utils.sendColoredMini(player, "<red>" + this.lang.task_must_use_axe);
                    }

                    //Fix half door glitch
                    if (Tag.DOORS.isTagged(material)) {
                        if (block.getRelative(BlockFace.UP).getType() == material)
                            block.getRelative(BlockFace.UP).getState().update(true);
                        if (block.getRelative(BlockFace.DOWN).getType() == material)
                            block.getRelative(BlockFace.DOWN).getState().update(true);
                    }
                }
                if (this.config.survival_break_only_with_pickaxe && !Tag.ITEMS_PICKAXES.isTagged(tool.getType())) {
                    if (BlockTags.REQUIRES_PICKAXE.isTagged(material)) {
                        event.setCancelled(true);
                        player.updateInventory();
                        Utils.sendColoredMini(player, "<red>" + this.lang.task_must_use_pick);
                    }
                }

                if (this.config.break_only_with_sickle) {
                    if (BlockTags.FARMABLE.isTagged(material)) {
                        if (!Items.Tags.SICKLES.isTagged(tool)) {
                            event.setCancelled(true);
                            Utils.sendColoredMini(player, "<red>" + this.lang.task_must_use_sickle);
                        } else {
                            event.setDropItems(false);
                            Location loc = event.getBlock().getLocation();
                            int random = 1;
                            int multiplier = 1;
                            boolean fullyGrown = true;

                            if (event.getBlock().getBlockData() instanceof Ageable crop) {
                                fullyGrown = crop.getAge() == crop.getMaximumAge();
                            }

                            // Flint/Stone sickles drop a chance of 0-1 items (not grown) or 1-2 (grown)
                            if (Items.FLINT_SICKLE.is(tool)) {
                                multiplier = 4;
                                random = fullyGrown ? new Random().nextInt(2) + 1 : new Random().nextInt(2);
                            }
                            if (Items.STONE_SICKLE.is(tool)) {
                                multiplier = 2;
                                random = fullyGrown ? new Random().nextInt(2) + 1 : new Random().nextInt(2);
                            }
                            // Iron/Diamond sickles drop a chance of 1 (not grown) or 2-4 items (grown)
                            if (Items.IRON_SICKLE.is(tool) || Items.DIAMOND_SICKLE.is(tool)) {
                                random = fullyGrown ? new Random().nextInt(2) + 3 : 1;
                            }

                            for (Material drop : Utils.getDrops(material, fullyGrown)) {
                                if (drop != Material.AIR && random != 0) {
                                    assert loc.getWorld() != null;
                                    if (drop == Material.PUMPKIN) { // prevent duping pumpkins
                                        random = 1;
                                    }
                                    loc.getWorld().dropItemNaturally(loc.add(0.5, 0.1, 0.5), new ItemStack(drop, random));
                                }
                            }
                            if (tool.getType().getMaxDurability() < ItemUtils.getDurability(tool) + multiplier) {
                                player.getInventory().setItemInMainHand(null);
                                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                                return;
                            }
                            ItemUtils.setDurability(tool, ItemUtils.getDurability(tool) + multiplier);
                            player.updateInventory();
                        }
                    }
                }

                if (!(tool.getType() == Material.SHEARS)) {
                    if (this.config.survival_break_only_with_shears) {
                        if (BlockTags.REQUIRES_SHEARS.isTagged(material)) {
                            event.setCancelled(true);
                            player.updateInventory();
                            Utils.sendColoredMini(player, "<red>" + this.lang.task_must_use_shear);
                        }
                    }

                    //Sticks - Maybe this should be removed since 1.14+ leaves drop sticks?!?!?
                    if (Tag.LEAVES.isTagged(material)) {
                        Random rand = new Random();
                        double chance = rand.nextDouble();

                        if (chance <= this.config.survival_drop_rate_stick)
                            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5, 0.1, 0.5), new ItemStack(Material.STICK));
                    }
                }
                if (this.config.recipes_workbench && material == Material.CRAFTING_TABLE && !event.isCancelled()) {
                    event.setDropItems(false);
                    ItemStack workbench = Items.WORKBENCH.getItemStack();
                    block.getWorld().dropItem(block.getLocation(), workbench);
                }
            } else {
                if (BlockTags.ORE_TYPE_BLOCK.isTagged(material) || BlockTags.ORES.isTagged(material)) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5, 0.1, 0.5), new ItemStack(material));
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    private void onHarvest(PlayerInteractEvent e) {
        if (e.isCancelled()) return;
        if (!this.config.break_only_with_sickle) return;
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
                Utils.sendColoredMini(player, "<red>" + this.lang.task_must_use_sickle);
            } else {
                if (bush.getAge() >= 2) {
                    int berries = 0;
                    Location loc = block.getLocation();
                    assert loc.getWorld() != null;
                    int multiplier = 1;
                    e.setCancelled(true);
                    int random = new Random().nextInt(5) + 1;

                    if (Items.FLINT_SICKLE.is(tool)) {
                        if (bush.getAge() == 3) {
                            berries = 1;
                        }
                        multiplier = 4;
                    } else if (Items.STONE_SICKLE.is(tool)) {
                        if (bush.getAge() == 2) {
                            if (random <= 4) berries = 1;
                        } else if (bush.getAge() == 3) {
                            if (random <= 3) berries = 1;
                            else berries = 2;
                        }
                        multiplier = 2;
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
                    int durability = ItemUtils.getDurability(tool) + multiplier;
                    ItemUtils.setDurability(tool, durability);
                    player.playSound(loc, Sound.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, 1, 1);
                    if (durability >= tool.getType().getMaxDurability()) {
                        player.getInventory().setItemInMainHand(null);
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    }
                }
            }
        }
    }

    @EventHandler
    private void onWaterBreakCrops(BlockPhysicsEvent event) {
        if (!this.config.break_only_with_sickle) return;
        if (event.getSourceBlock().getType() == Material.WATER) {
            Material type = event.getBlock().getType();
            if (BlockTags.FARMABLE.isTagged(type)) {
                if (type == Material.MELON || type == Material.PUMPKIN) return;
                event.getBlock().setType(Material.AIR);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onTrample(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (!this.config.break_only_with_sickle) return;
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
