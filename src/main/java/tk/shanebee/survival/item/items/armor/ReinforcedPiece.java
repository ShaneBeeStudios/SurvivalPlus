package tk.shanebee.survival.item.items.armor;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.item.Item;
import tk.shanebee.survival.item.items.armor.ArmorPiece.ArmorType;

@SuppressWarnings("UnstableApiUsage")
public class ReinforcedPiece extends Item {

    private final ArmorType armorType;

    public ReinforcedPiece(ArmorType armorType) {
        this.armorType = armorType;
        ItemStack itemStack = itemType().createItemStack();
        if (armorType == ArmorType.BOOTS) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            AttributeModifier i_leatherBootsArmor = new AttributeModifier(NamespacedKey.minecraft("armor." + armorType.getKey()), 2, Operation.ADD_NUMBER, EquipmentSlotGroup.FEET);
            itemMeta.addAttributeModifier(Attribute.ARMOR, i_leatherBootsArmor);
            itemStack.setItemMeta(itemMeta);
        }
        itemStack.setData(DataComponentTypes.EQUIPPABLE,
            Equippable.equippable(getSlotGroup())
                .assetId(Key.key("survival_plus:reinforced_leather"))
                .build());
        setupDefaults(key(), itemStack);
    }

    @Override
    public void registerRecipe() {
        switch (this.armorType) {
            case HELMET -> {
                ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
                recipe.shape("@*@");
                recipe.setIngredient('@', Material.IRON_INGOT);
                recipe.setIngredient('*', Material.LEATHER_HELMET);
                Bukkit.addRecipe(recipe);
            }
            case CHESTPLATE -> {
                ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
                recipe.shape(" @ ", "@*@", " @ ");
                recipe.setIngredient('@', Material.IRON_INGOT);
                recipe.setIngredient('*', Material.LEATHER_CHESTPLATE);
                Bukkit.addRecipe(recipe);
            }
            case LEGGINGS -> {
                ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
                recipe.shape(" @ ", "@*@", " @ ");
                recipe.setIngredient('@', Material.IRON_INGOT);
                recipe.setIngredient('*', Material.LEATHER_LEGGINGS);
                Bukkit.addRecipe(recipe);
            }
            case BOOTS -> {
                ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
                recipe.shape("@*@");
                recipe.setIngredient('@', Material.IRON_INGOT);
                recipe.setIngredient('*', Material.LEATHER_BOOTS);
                Bukkit.addRecipe(recipe);
            }
        }
    }

    private String key() {
        return "reinforced_leather_" + switch (this.armorType) {
            case HELMET -> "helmet";
            case CHESTPLATE -> "tunic";
            case LEGGINGS -> "trousers";
            case BOOTS -> "boots";
        };
    }

    private EquipmentSlot getSlotGroup() {
        return switch (this.armorType) {
            case HELMET -> EquipmentSlot.HEAD;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case BOOTS -> EquipmentSlot.FEET;
        };
    }

    private ItemType itemType() {
        return switch (this.armorType) {
            case HELMET -> ItemType.LEATHER_HELMET;
            case CHESTPLATE -> ItemType.LEATHER_CHESTPLATE;
            case LEGGINGS -> ItemType.LEATHER_LEGGINGS;
            case BOOTS -> ItemType.LEATHER_BOOTS;
        };
    }

}
