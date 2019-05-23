package tk.shanebee.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.managers.RecipeManager.Recipes;
import tk.shanebee.survival.util.Utils.RecipeDiscovered;

public class RecipeDiscovery implements Listener {

    // When a player first joins, give them a few recipes after 10 seconds
    @EventHandler
    private void onFirstJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (RecipeDiscovered.FIRST_JOIN.hasDiscovered(player)) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Survival.instance, () -> {
            player.discoverRecipes(Recipes.HATCHET.getKeys());
            player.discoverRecipes(Recipes.MATTOCK.getKeys());
            player.discoverRecipes(Recipes.SHIV.getKeys());
            player.discoverRecipes(Recipes.HAMMER.getKeys());
            player.discoverRecipes(Recipes.STONE_SICKLE.getKeys());
            player.discoverRecipes(Recipes.GLASS_BOTTLE.getKeys());
            player.discoverRecipes(Recipes.STICK.getKeys());
            player.discoverRecipes(Recipes.BREAD.getKeys());
            player.discoverRecipes(Recipes.STRING.getKeys());
            player.discoverRecipes(Recipes.WATER_BOTTLES.getKeys());
            player.discoverRecipe(NamespacedKey.minecraft("bowl"));
            RecipeDiscovered.FIRST_JOIN.setDiscovered(player);
        }, 200);
    }

    // When a player picks up items, unlock different item based recipes
    @EventHandler
    private void onPickupItems(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = ((Player) e.getEntity());
        Material item = e.getItem().getItemStack().getType();
        if (item == Material.DIAMOND) {
            if (!RecipeDiscovered.DIAMONDS.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.DIAMOND_BOOTS.getKeys());
                player.discoverRecipes(Recipes.DIAMOND_CHESTPLATE.getKeys());
                player.discoverRecipes(Recipes.DIAMOND_LEGGINGS.getKeys());
                player.discoverRecipes(Recipes.DIAMOND_HELMET.getKeys());
                player.discoverRecipes(Recipes.DIAMOND_HORSE_ARMOR.getKeys());
                player.discoverRecipes(Recipes.VALKYRIES_AXE.getKeys());
                player.discoverRecipes(Recipes.QUARTZ_PICKAXE.getKeys());
                player.discoverRecipes(Recipes.ENDER_GIANT_BLADE.getKeys());
                RecipeDiscovered.DIAMONDS.setDiscovered(player);
            }
        } else if (item == Material.FLINT) {
            if (!RecipeDiscovered.FLINT.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.FIRESTRIKER.getKeys());
                player.discoverRecipes(Recipes.GRAVEL.getKeys());
                RecipeDiscovered.FLINT.setDiscovered(player);
            }
        } else if (item == Material.FEATHER) {
            if (!RecipeDiscovered.FEATHER.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.MEDIC_KIT.getKeys());
                player.discoverRecipes(Recipes.FISHING_ROD.getKeys());
                RecipeDiscovered.FEATHER.setDiscovered(player);
            }
        } else if (item == Material.BLAZE_POWDER || item == Material.BLAZE_ROD) {
            if (!RecipeDiscovered.BLAZE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.BLAZE_SWORD.getKeys());
                RecipeDiscovered.BLAZE.setDiscovered(player);
            }
        } else if (item == Material.LEATHER) {
            if (!RecipeDiscovered.LEATHER.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.SADDLE.getKeys());
                player.discoverRecipes(Recipes.LEATHER_HORSE_ARMOR.getKeys());
                RecipeDiscovered.LEATHER.setDiscovered(player);
            }
        } else if (item == Material.GRAVEL) {
            if (!RecipeDiscovered.GRAVEL.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.FLINT.getKeys());
                RecipeDiscovered.GRAVEL.setDiscovered(player);
            }
        } else if (item == Material.ROTTEN_FLESH) {
            if (!RecipeDiscovered.ROTTEN_FLESH.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.FERMENTED_SKIN.getKeys());
                RecipeDiscovered.ROTTEN_FLESH.setDiscovered(player);
            }
        } else if (item == Material.STRING) {
            if (!RecipeDiscovered.STRING.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.COBWEB.getKeys());
                player.discoverRecipes(Recipes.RECURVED_BOW.getKeys());
                RecipeDiscovered.STRING.setDiscovered(player);
            }
        } else if (item == Material.SPIDER_EYE) {
            if (!RecipeDiscovered.SPIDER_EYE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.FERMENTED_SPIDER_EYE.getKeys());
                RecipeDiscovered.SPIDER_EYE.setDiscovered(player);
            }
        } else if (item == Material.POTATO) {
            if (!RecipeDiscovered.POTATO.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.POISONOUS_POTATO.getKeys());
                RecipeDiscovered.POTATO.setDiscovered(player);
            }
        } else if (item == Material.COBBLESTONE) {
            if (!RecipeDiscovered.COBBLESTONE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.ANDESITE.getKeys());
                player.discoverRecipes(Recipes.DIORITE.getKeys());
                player.discoverRecipes(Recipes.GRANITE.getKeys());
                RecipeDiscovered.COBBLESTONE.setDiscovered(player);
            }
        } else if (item == Material.QUARTZ) {
            if (!RecipeDiscovered.QUARTZ.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.QUARTZ.getKeys());
                RecipeDiscovered.QUARTZ.setDiscovered(player);
            }
        } else if (item == Material.DIRT) {
            if (!RecipeDiscovered.DIRT.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.CLAY.getKeys());
                RecipeDiscovered.DIRT.setDiscovered(player);
            }
        } else if (item == Material.EGG) {
            if (!RecipeDiscovered.EGG.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.COOKIE.getKeys());
                RecipeDiscovered.EGG.setDiscovered(player);
            }
        } else if (Items.compare(e.getItem().getItemStack(), Items.WATER_BOWL)) {
            if (!RecipeDiscovered.BOWL.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.BOWL.getKeys());
                RecipeDiscovered.BOWL.setDiscovered(player);
            }
        } else if (item == Material.VINE) {
            if (!RecipeDiscovered.VINE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.SLIMEBALL.getKeys());
                RecipeDiscovered.VINE.setDiscovered(player);
            }
        }
    }

    // When a player smelts items, unlock different item based recipes
    @EventHandler
    private void onFurnaceExtract(FurnaceExtractEvent event) {
        Player player = event.getPlayer();
        if (event.getItemType() == Material.IRON_INGOT) {
            if (!RecipeDiscovered.IRON.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.IRON_BLOCK.getKeys());
                player.discoverRecipes(Recipes.IRON_BOOTS.getKeys());
                player.discoverRecipes(Recipes.IRON_CHESTPLATE.getKeys());
                player.discoverRecipes(Recipes.IRON_HELMET.getKeys());
                player.discoverRecipes(Recipes.IRON_LEGGINGS.getKeys());
                player.discoverRecipes(Recipes.IRON_HORSE_ARMOR.getKeys());
                player.discoverRecipes(Recipes.IRON_INGOT.getKeys());
                player.discoverRecipes(Recipes.IRON_SICKLE.getKeys());
                player.discoverRecipes(Recipes.IRON_NUGGET.getKeys());
                RecipeDiscovered.IRON.setDiscovered(player);
            }
        } else if (event.getItemType() == Material.GOLD_INGOT) {
            if (!RecipeDiscovered.GOLD.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.GOLD_BLOCK.getKeys());
                player.discoverRecipes(Recipes.GOLD_NUGGET.getKeys());
                player.discoverRecipes(Recipes.GOLD_INGOT.getKeys());
                player.discoverRecipes(Recipes.GOLD_CROWN.getKeys());
                player.discoverRecipes(Recipes.GOLD_GREAVES.getKeys());
                player.discoverRecipes(Recipes.GOLD_GUARD.getKeys());
                player.discoverRecipes(Recipes.GOLD_SABATONS.getKeys());
                player.discoverRecipes(Recipes.GOLD_HORSE_ARMOR.getKeys());
                player.discoverRecipes(Recipes.ENCHANTED_GOLDEN_APPLE.getKeys());
                RecipeDiscovered.GOLD.setDiscovered(player);
            }
        }
    }

    // When a player breaks a block, unlock different item based recipes
    @EventHandler
    private void onPlayerBreakBlock(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Material item = e.getBlock().getType();
        if (e.isCancelled()) return;
        if (Tag.LOGS.isTagged(item)) {
            if (!RecipeDiscovered.LOGS.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.WORKBENCH.getKeys());
                player.discoverRecipes(Recipes.CHEST.getKeys());
                player.discoverRecipes(Recipes.UNLIT_CAMPFIRE.getKeys());
                RecipeDiscovered.LOGS.setDiscovered(player);
            }
        } else if (item == Material.OBSIDIAN) {
            if (!RecipeDiscovered.OBSIDIAN.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.OBSIDIAN_MACE.getKeys());
                player.discoverRecipes(Recipes.REPAIR_OBSIDIAN_MACE.getKeys());
                RecipeDiscovered.OBSIDIAN.setDiscovered(player);
            }
        } else if (item == Material.ICE || item == Material.BLUE_ICE || item == Material.FROSTED_ICE || item == Material.PACKED_ICE) {
            if (!RecipeDiscovered.ICE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.ICE.getKeys());
                player.discoverRecipes(Recipes.PACKED_ICE.getKeys());
                RecipeDiscovered.ICE.setDiscovered(player);
            }
        }
    }

    // When a player crafts an item, unlock different item based recipes
    @EventHandler
    private void onCraft(CraftItemEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = ((Player) e.getWhoClicked());
        ItemStack result = e.getRecipe().getResult();
        if (Items.compare(result, Items.FIRESTRIKER)) {
            if (!RecipeDiscovered.FIRESTRIKER.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.TORCH.getKeys());
                player.discoverRecipes(Recipes.FURNACE.getKeys());
                RecipeDiscovered.FIRESTRIKER.setDiscovered(player);
            }
        } else if (result.getType() == Material.FURNACE) {
            if (!RecipeDiscovered.FURNACE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.FURNACE_GOLD_INGOT.getKeys());
                player.discoverRecipes(Recipes.FURNACE_IRON_INGOT.getKeys());
                RecipeDiscovered.FURNACE.setDiscovered(player);
            }
        } else if (result.getType() == Material.BLAST_FURNACE) {
            if (!RecipeDiscovered.BLAST_FURNACE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.BLAST_GOLD_INGOT.getKeys());
                player.discoverRecipes(Recipes.BLAST_IRON_INGOT.getKeys());
                RecipeDiscovered.BLAST_FURNACE.setDiscovered(player);
            }
        } else if (result.getType() == Material.CROSSBOW) {
            if (!RecipeDiscovered.CROSSBOW.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.RECURVED_CROSSBOW.getKeys());
                RecipeDiscovered.CROSSBOW.setDiscovered(player);
            }
        } else if (result.getType() == Material.LEATHER_HELMET || result.getType() == Material.LEATHER_CHESTPLATE
                || result.getType() == Material.LEATHER_LEGGINGS || result.getType() == Material.LEATHER_BOOTS) {
            if (!RecipeDiscovered.LEATHER_ARMOR.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.REINFORCED_LEATHER_HELMET.getKeys());
                player.discoverRecipes(Recipes.REINFORCED_LEATHER_CHESTPLATE.getKeys());
                player.discoverRecipes(Recipes.REINFORCED_LEATHER_LEGGINGS.getKeys());
                player.discoverRecipes(Recipes.REINFORCED_LEATHER_BOOTS.getKeys());
                RecipeDiscovered.LEATHER_ARMOR.setDiscovered(player);
            }
        } else if (result.getType() == Material.PAPER) {
            if (!RecipeDiscovered.PAPER.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.NAMETAG.getKeys());
                player.discoverRecipes(Recipes.MEDIC_KIT.getKeys());
                RecipeDiscovered.PAPER.setDiscovered(player);
            }
        } else if (result.getType() == Material.STRING) {
            if (!RecipeDiscovered.STRING.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.COBWEB.getKeys());
                player.discoverRecipes(Recipes.RECURVED_BOW.getKeys());
                RecipeDiscovered.STRING.setDiscovered(player);
            }
        } else if (result.getType() == Material.BRICK || result.getType() == Material.BRICKS) {
            if (!RecipeDiscovered.BRICK.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.CLAY_BRICK.getKeys());
                RecipeDiscovered.BRICK.setDiscovered(player);
            }
        } else if (Items.compare(result, Items.BLAZE_SWORD)) {
            if (!RecipeDiscovered.BLAZE_SWORD.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.REPAIR_BLAZE_SWORD.getKeys());
                RecipeDiscovered.BLAZE_SWORD.setDiscovered(player);
            }
        } else if (Items.compare(result, Items.ENDER_GIANT_BLADE)) {
            if (!RecipeDiscovered.ENDER_GIANT_BLADE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.REPAIR_ENDER_GIANT_BLADE.getKeys());
                RecipeDiscovered.ENDER_GIANT_BLADE.setDiscovered(player);
            }
        } else if (Items.compare(result, Items.QUARTZ_PICKAXE)) {
            if (!RecipeDiscovered.QUARTZ_PICKAXE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.REPAIR_QUARTZ_PICKAXE.getKeys());
                RecipeDiscovered.QUARTZ_PICKAXE.setDiscovered(player);
            }
        } else if (Items.compare(result, Items.VALKYRIES_AXE)) {
            if (!RecipeDiscovered.VALKYRIES_AXE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.REPAIR_VALKYRIES_AXE.getKeys());
                RecipeDiscovered.VALKYRIES_AXE.setDiscovered(player);
            }
        } else if (result.getType() == Material.FISHING_ROD) {
            if (!RecipeDiscovered.FISHING_ROD.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.GRAPPLING_HOOK.getKeys());
                RecipeDiscovered.FISHING_ROD.setDiscovered(player);
            }
        } else if (result.getType() == Material.GLASS_BOTTLE) {
            if (!RecipeDiscovered.GLASS_BOTTLE.hasDiscovered(player)) {
                player.discoverRecipes(Recipes.COFFEE.getKeys());
                player.discoverRecipes(Recipes.COFFEE_BEAN.getKeys());
                player.discoverRecipes(Recipes.HOT_MILK.getKeys());
                player.discoverRecipes(Recipes.COLD_MILK.getKeys());
                RecipeDiscovered.GLASS_BOTTLE.setDiscovered(player);
            }
        }
    }

}
