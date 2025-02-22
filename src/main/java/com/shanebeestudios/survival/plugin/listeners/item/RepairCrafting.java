package com.shanebeestudios.survival.plugin.listeners.item;

import com.shanebeestudios.survival.api.item.Item;
import com.shanebeestudios.survival.api.item.Items;
import com.shanebeestudios.survival.api.util.ItemUtils;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Keyed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class RepairCrafting implements Listener {

    @EventHandler
    private void onCraft(PrepareItemCraftEvent event) {
        Recipe recipe = event.getRecipe();
        if (recipe instanceof Keyed keyed && keyed.getKey().getNamespace().equalsIgnoreCase("survival_plus")) {
            // If this is a legit recipe, let's get outta here
            return;
        }
        CraftingInventory inventory = event.getInventory();

        List<ItemStack> items = new ArrayList<>();
        for (ItemStack itemStack : inventory.getMatrix()) {
            if (itemStack != null) {
                items.add(itemStack);
            }
        }

        if (items.size() == 2) {
            ItemStack iOne = items.get(0);
            ItemStack iTwo = items.get(1);
            Item itemOne = Items.getFromStack(iOne);
            Item itemTwo = Items.getFromStack(iTwo);
            if (itemOne != null && itemOne == itemTwo) {
                ItemStack result = repair(iOne, iTwo, itemOne.getRepairPercent());
                if (inventory.getType() == InventoryType.CRAFTING || itemOne.getRepairCost() > 0) {
                    // No repairing in player inventory
                    // Cost > 0 signifies it requires an anvil
                    result = null;
                }
                if (result != null) {
                    // Since we're using a crafting table we're going to reset defaults
                    // ie: remove enchantments
                    result = resetItem(itemOne, result);
                }
                inventory.setResult(result);

            } else if ((itemOne != null && itemTwo == null) || (itemOne == null && itemTwo != null)) {
                // Prevent repairing custom items with vanilla items
                inventory.setResult(null);

            }
        }
        items.clear();
    }

    @EventHandler
    private void onAnvilRepair(PrepareAnvilEvent event) {
        AnvilInventory inventory = event.getInventory();
        ItemStack slotOne = inventory.getContents()[0];
        ItemStack slotTwo = inventory.getContents()[1];
        if (slotOne != null && slotTwo != null) {
            if (slotOne.getType() != slotTwo.getType()) {
                // If two different items, lets get outta here
                // ie: enchanting
                return;
            }
            Item itemOne = Items.getFromStack(slotOne);
            Item itemTwo = Items.getFromStack(slotTwo);
            // Let's make sure we're joining two of the same item
            if (itemOne != null && itemOne == itemTwo) {
                ItemStack result = repair(slotOne, slotTwo, itemOne.getRepairPercent());
                event.setResult(result);
            }
        }
    }

    private ItemStack repair(ItemStack itemStackOne, ItemStack itemStackTwo, double repairPercent) {
        if (repairPercent <= 0) return null;

        ItemStack result = itemStackOne.clone();
        int max = ItemUtils.getMaxDamage(itemStackOne);
        int dura1 = ItemUtils.getDurability(itemStackOne);
        int dura2 = ItemUtils.getDurability(itemStackTwo);
        int repair = (int) Math.min((dura1 + dura2 + Math.floor((double) max / 20)) * repairPercent, max);

        result.setData(DataComponentTypes.DAMAGE, max - repair);
        return result;
    }

    @SuppressWarnings("DataFlowIssue")
    private ItemStack resetItem(Item baseItem, ItemStack itemStack) {
        if (!itemStack.hasData(DataComponentTypes.DAMAGE)) return null;

        ItemStack newItemStack = baseItem.getItemStack();
        newItemStack.setData(DataComponentTypes.DAMAGE, itemStack.getData(DataComponentTypes.DAMAGE));

        return newItemStack;
    }

}
