package tk.shanebee.survival.item.items.armor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
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
            AttributeModifier i_leatherBootsArmor = new AttributeModifier(NamespacedKey.minecraft("armor.boots"), 2, Operation.ADD_NUMBER, EquipmentSlotGroup.FEET);
            itemMeta.addAttributeModifier(Attribute.ARMOR, i_leatherBootsArmor);
            itemStack.setItemMeta(itemMeta);
        }
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

    private ItemType itemType() {
        return switch (this.armorType) {
            case HELMET -> ItemType.LEATHER_HELMET;
            case CHESTPLATE -> ItemType.LEATHER_CHESTPLATE;
            case LEGGINGS -> ItemType.LEATHER_LEGGINGS;
            case BOOTS -> ItemType.LEATHER_BOOTS;
        };
    }

}
