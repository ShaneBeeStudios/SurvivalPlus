package tk.shanebee.survival.item.items.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class FireStriker extends Item {

    public FireStriker() {
        ItemStack itemStack = ItemType.WOODEN_SHOVEL.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        float firestriker_spd = 4f;

        AttributeModifier i_firestrikerSpeed = new AttributeModifier(BASE_ATTACK_SPEED, firestriker_spd - 4, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_SPEED, i_firestrikerSpeed);

        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        setupDefaults("firestriker", itemStack);
    }

    @Override
    public void registerRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(this.recipeKey, this.getItemStack());
        recipe.addIngredient(Material.FLINT);
        recipe.addIngredient(new RecipeChoice.MaterialChoice(Tag.ITEMS_COALS));
        Bukkit.addRecipe(recipe);
    }

}
