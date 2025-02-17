package tk.shanebee.survival.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DyedItemColor;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import tk.shanebee.survival.config.ItemConfig;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnstableApiUsage", "PatternValidation"})
public abstract class Item {

    protected static final ItemConfig ITEM_CONFIG = new ItemConfig();
    private static final MiniMessage MINI = MiniMessage.miniMessage();

    // Minecraft default keys
    protected static final NamespacedKey BASE_ATTACK_DAMAGE = NamespacedKey.minecraft("base_attack_damage");
    protected static final NamespacedKey BASE_ATTACK_SPEED = NamespacedKey.minecraft("base_attack_speed");
    // Non-official keys
    protected static final NamespacedKey BASE_MOVEMENT_SPEED = NamespacedKey.minecraft("base_movement_speed");

    private Key key;
    protected NamespacedKey recipeKey;
    private ItemStack itemStack;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private double repairPercent; // TODO figure this out

    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    public ItemStack getItemStack(int amount) {
        ItemStack clone = this.itemStack.clone();
        clone.setAmount(amount);
        return clone;
    }

    protected void setupDefaults(String key, ItemStack itemStack) {
        setupDefaults(key, itemStack, false);
    }

    @SuppressWarnings("PatternValidation")
    protected void setupDefaults(String key, ItemStack itemStack, boolean vanillaModel) {
        this.key = Key.key("survival_plus", key);
        this.recipeKey = NamespacedKey.fromString(this.key.toString());
        if (!vanillaModel) {
            itemStack.setData(DataComponentTypes.ITEM_MODEL, this.key);
        }

        // Color
        int color = ITEM_CONFIG.getColor(key);
        if (color != 0) {
            DyedItemColor dyedItemColor = DyedItemColor.dyedItemColor(Color.fromRGB(color), false);
            itemStack.setData(DataComponentTypes.DYED_COLOR, dyedItemColor);
        }

        // Item Name
        String itemName = ITEM_CONFIG.getName(key);
        if (itemName != null) {
            if (itemStack.hasData(DataComponentTypes.POTION_CONTENTS)) {
                // Stupid workaround because potion names override item_name
                itemStack.setData(DataComponentTypes.CUSTOM_NAME, MINI.deserialize("<!italic>" + itemName));
            } else {
                itemStack.setData(DataComponentTypes.ITEM_NAME, MINI.deserialize(itemName));
            }
        } else if (!vanillaModel) {
            Utils.logMini("<red>Failed to load item name for item <white>'<aqua>" + key + "<white>'");
        }

        // Lore
        List<String> lore = ITEM_CONFIG.getLore(key);
        if (lore != null && !lore.isEmpty()) {
            List<Component> loreComponents = new ArrayList<>();
            for (String line : lore) {
                loreComponents.add(MINI.deserialize("<!italic>" + line));
            }
            itemStack.lore(loreComponents);
        }

        // Max Damage
        int maxDamage = ITEM_CONFIG.getMaxDamage(key);
        if (maxDamage > 0) {
            itemStack.setData(DataComponentTypes.MAX_DAMAGE, maxDamage);
            itemStack.setData(DataComponentTypes.DAMAGE, 0);
        }

        // Repair Cost
        int repairCost = ITEM_CONFIG.getRepairCost(key);
        if (repairCost > 0) {
            itemStack.setData(DataComponentTypes.REPAIR_COST, repairCost);
        }

        // Repair Percent
        this.repairPercent = ITEM_CONFIG.getRepairPercent(key);
        this.itemStack = itemStack;
        Items.ALL_ITEMS.put(this.key, this);
    }

    public Recipe getRecipe() {
        return null;
    }

    public boolean is(ItemStack itemStack) {
        if (itemStack.hasData(DataComponentTypes.ITEM_MODEL)) {
            Key data = itemStack.getData(DataComponentTypes.ITEM_MODEL);
            return data != null && data.equals(this.key);
        }
        return false;
    }

    public Key getKey() {
        return this.key;
    }

}
