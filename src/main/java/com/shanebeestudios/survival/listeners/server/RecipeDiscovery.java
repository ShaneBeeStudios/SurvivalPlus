package com.shanebeestudios.survival.listeners.server;

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
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.item.Recipes;

public class RecipeDiscovery implements Listener {

    private final SurvivalPlugin plugin;
    private final boolean unlockAllRecipes;

    public RecipeDiscovery(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.unlockAllRecipes = plugin.getSurvivalConfig().survival_unlock_all_recipes;
    }

    // When a player first joins, give them a few recipes after 10 seconds
    @EventHandler
    private void onFirstJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (this.unlockAllRecipes) {
                this.plugin.getRecipeManager().unlockAllRecipes(player);
            } else {
                Recipes.HATCHET.unlock(player);
                Recipes.MATTOCK.unlock(player);
                Recipes.SHIV.unlock(player);
                Recipes.HAMMER.unlock(player);
                Recipes.GLASS_BOTTLE.unlock(player);
                Recipes.STICK.unlock(player);
                Recipes.BREAD.unlock(player);
                Recipes.STRING_FROM_WEB.unlock(player);
                Recipes.CLEAN_WATER_BOTTLES.unlock(player);
            }
            player.discoverRecipe(NamespacedKey.minecraft("bowl"));
        }, 100);
    }

    // When a player picks up items, unlock different item based recipes
    @EventHandler
    private void onPickupItems(EntityPickupItemEvent e) {
        if (this.unlockAllRecipes) return;
        if (!(e.getEntity() instanceof Player player)) return;
        Material item = e.getItem().getItemStack().getType();
        if (item == Material.DIAMOND) {
            Recipes.DIAMOND_BOOTS.unlock(player);
            Recipes.DIAMOND_CHESTPLATE.unlock(player);
            Recipes.DIAMOND_LEGGINGS.unlock(player);
            Recipes.DIAMOND_HELMET.unlock(player);
            Recipes.DIAMOND_HORSE_ARMOR.unlock(player);
            Recipes.VALKYRIES_AXE.unlock(player);
            Recipes.QUARTZ_PICKAXE.unlock(player);
            Recipes.ENDER_GIANT_BLADE.unlock(player);
            Recipes.DIAMOND_SICKLE.unlock(player);
        } else if (item == Material.FLINT) {
            Recipes.FIRESTRIKER.unlock(player);
            Recipes.GRAVEL.unlock(player);
            Recipes.FLINT_SICKLE.unlock(player);
        } else if (item == Material.FEATHER) {
            Recipes.MEDIC_KIT.unlock(player);
            Recipes.FISHING_ROD.unlock(player);
        } else if (item == Material.BLAZE_POWDER || item == Material.BLAZE_ROD) {
            Recipes.BLAZE_SWORD.unlock(player);
        } else if (item == Material.LEATHER) {
            Recipes.SADDLE.unlock(player);
            Recipes.LEATHER_HORSE_ARMOR.unlock(player);
        } else if (item == Material.GRAVEL) {
            Recipes.FLINT.unlock(player);
        } else if (item == Material.ROTTEN_FLESH) {
            Recipes.FERMENTED_SKIN.unlock(player);
        } else if (item == Material.STRING) {
            Recipes.COBWEB.unlock(player);
            Recipes.RECURVED_BOW.unlock(player);
        } else if (item == Material.SPIDER_EYE) {
            Recipes.FERMENTED_SPIDER_EYE.unlock(player);
        } else if (item == Material.POTATO) {
            Recipes.POISONOUS_POTATO.unlock(player);
        } else if (item == Material.COBBLESTONE) {
            Recipes.ANDESITE.unlock(player);
            Recipes.DIORITE.unlock(player);
            Recipes.GRANITE.unlock(player);
            Recipes.STONE_SICKLE.unlock(player);
        } else if (item == Material.QUARTZ) {
            Recipes.QUARTZ.unlock(player);
        } else if (item == Material.DIRT) {
            Recipes.CLAY.unlock(player);
        } else if (item == Material.EGG) {
            Recipes.COOKIE.unlock(player);
        } else if (Items.WATER_BOWL.is(e.getItem().getItemStack())) {
            Recipes.BOWL.unlock(player);
        } else if (item == Material.VINE) {
            Recipes.SLIMEBALL.unlock(player);
        } else if (item == Material.REDSTONE) {
            Recipes.COMPASS.unlock(player);
        } else if (item == Material.HONEYCOMB) {
            Recipes.BEEKEEPER_SUIT.unlock(player);
        }
    }

    // When a player smelts items, unlock different item based recipes
    @EventHandler
    private void onFurnaceExtract(FurnaceExtractEvent event) {
        if (this.unlockAllRecipes) return;
        Player player = event.getPlayer();
        if (event.getItemType() == Material.IRON_INGOT) {
            Recipes.IRON_BOOTS.unlock(player);
            Recipes.IRON_CHESTPLATE.unlock(player);
            Recipes.IRON_HELMET.unlock(player);
            Recipes.IRON_LEGGINGS.unlock(player);
            Recipes.IRON_HORSE_ARMOR.unlock(player);
            Recipes.IRON_INGOT.unlock(player);
            Recipes.IRON_SICKLE.unlock(player);
            Recipes.IRON_NUGGET.unlock(player);
        } else if (event.getItemType() == Material.GOLD_INGOT) {
            Recipes.GOLD_NUGGET.unlock(player);
            Recipes.GOLD_INGOT.unlock(player);
            Recipes.GOLD_CROWN.unlock(player);
            Recipes.GOLD_GREAVES.unlock(player);
            Recipes.GOLD_GUARD.unlock(player);
            Recipes.GOLD_SABATONS.unlock(player);
            Recipes.GOLD_HORSE_ARMOR.unlock(player);
            Recipes.ENCHANTED_GOLDEN_APPLE.unlock(player);
        }
    }

    // When a player breaks a block, unlock different item based recipes
    @EventHandler
    private void onPlayerBreakBlock(BlockBreakEvent e) {
        if (this.unlockAllRecipes) return;
        Player player = e.getPlayer();
        Material blockType = e.getBlock().getType();
        if (e.isCancelled()) return;
        if (Tag.LOGS.isTagged(blockType)) {
            Recipes.WORKBENCH.unlock(player);
            Recipes.CHEST.unlock(player);
            Recipes.UNLIT_CAMPFIRE.unlock(player);
        } else if (blockType == Material.OBSIDIAN) {
            Recipes.OBSIDIAN_MACE.unlock(player);
        } else if (blockType == Material.ICE || blockType == Material.BLUE_ICE || blockType == Material.FROSTED_ICE || blockType == Material.PACKED_ICE) {
            Recipes.ICE.unlock(player);
            Recipes.PACKED_ICE.unlock(player);
        }
    }

    // When a player crafts an item, unlock different item based recipes
    @EventHandler
    private void onCraft(CraftItemEvent e) {
        if (this.unlockAllRecipes) return;
        if (!(e.getWhoClicked() instanceof Player player)) return;

        ItemStack result = e.getRecipe().getResult();
        if (Items.FIRESTRIKER.is(result)) {
            Recipes.TORCH.unlock(player);
            Recipes.FURNACE.unlock(player);
        } else if (result.getType() == Material.FURNACE) {
            Recipes.FURNACE_GOLD_INGOT.unlock(player);
            Recipes.FURNACE_IRON_INGOT.unlock(player);
        } else if (result.getType() == Material.BLAST_FURNACE) {
            Recipes.BLAST_GOLD_INGOT.unlock(player);
            Recipes.BLAST_IRON_INGOT.unlock(player);
        } else if (result.getType() == Material.CROSSBOW) {
            Recipes.RECURVED_CROSSBOW.unlock(player);
        } else if (result.getType() == Material.LEATHER_HELMET || result.getType() == Material.LEATHER_CHESTPLATE
            || result.getType() == Material.LEATHER_LEGGINGS || result.getType() == Material.LEATHER_BOOTS) {
            Recipes.REINFORCED_LEATHER_HELMET.unlock(player);
            Recipes.REINFORCED_LEATHER_CHESTPLATE.unlock(player);
            Recipes.REINFORCED_LEATHER_LEGGINGS.unlock(player);
            Recipes.REINFORCED_LEATHER_BOOTS.unlock(player);
        } else if (result.getType() == Material.PAPER) {
            Recipes.NAMETAG.unlock(player);
            Recipes.MEDIC_KIT.unlock(player);
        } else if (result.getType() == Material.STRING) {
            Recipes.COBWEB.unlock(player);
            Recipes.RECURVED_BOW.unlock(player);
        } else if (result.getType() == Material.BRICK || result.getType() == Material.BRICKS) {
            Recipes.CLAY_BRICK.unlock(player);
        } else if (result.getType() == Material.FISHING_ROD) {
            Recipes.GRAPPLING_HOOK.unlock(player);
        } else if (result.getType() == Material.GLASS_BOTTLE) {
            Recipes.COFFEE.unlock(player);
            Recipes.COFFEE_BEAN.unlock(player);
            Recipes.HOT_MILK.unlock(player);
            Recipes.COLD_MILK.unlock(player);
        }
    }

}
