package tk.shanebee.survival.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.survival.util.Utils;

@SuppressWarnings({"UnstableApiUsage", "PatternValidation"})
public abstract class Item {

    protected static final ItemConfig ITEM_CONFIG = new ItemConfig();
    private static final MiniMessage MINI = MiniMessage.miniMessage();

    // Minecraft default keys
    protected static final NamespacedKey BASE_ATTACK_DAMAGE = NamespacedKey.minecraft("base_attack_damage");
    protected static final NamespacedKey BASE_ATTACK_SPEED = NamespacedKey.minecraft("base_attack_speed");
    protected static final NamespacedKey BASE_ATTACK_TOUGH = NamespacedKey.minecraft("base_attack_toughness");
    protected static final NamespacedKey BASE_ATTACK_KNOCKBACK = NamespacedKey.minecraft("base_attack_knockback");
    // Non-official keys
    protected static final NamespacedKey BASE_MOVEMENT_SPEED = NamespacedKey.minecraft("base_movement_speed");

    private Key key;
    protected NamespacedKey recipeKey;
    private ItemStack itemStack;
    private double repairPercent; // TODO figure this out

    public ItemStack getItemStack() {
        return this.itemStack.clone();
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

        // Item Name
        String itemName = ITEM_CONFIG.getName(key);
        if (itemName != null) {
            itemStack.setData(DataComponentTypes.ITEM_NAME, MINI.deserialize(itemName));
        } else if (!vanillaModel) {
            Utils.log("&cFailed to load item name for item &r'&b" + key + "&r'");
        }

        // Max Damage
        int maxDamage = ITEM_CONFIG.getMaxDamage(key);
        if (maxDamage > 0) {
            itemStack.setData(DataComponentTypes.MAX_DAMAGE, maxDamage);
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

    public void registerRecipe() {
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
