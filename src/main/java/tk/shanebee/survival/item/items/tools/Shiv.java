package tk.shanebee.survival.item.items.tools;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class Shiv extends Item {

    public Shiv() {
        ItemStack itemStack = ItemType.WOODEN_HOE.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        int shiv_dmg = 4;
        float shiv_spd = 1.8f;

        AttributeModifier i_shivDamage = new AttributeModifier(BASE_ATTACK_DAMAGE, shiv_dmg - 1, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_DAMAGE, i_shivDamage);

        AttributeModifier i_shivSpeed = new AttributeModifier(BASE_ATTACK_SPEED, shiv_spd - 4, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_SPEED, i_shivSpeed);

        itemStack.setItemMeta(itemMeta);

        itemStack.unsetData(DataComponentTypes.REPAIRABLE);
        setupDefaults("shiv", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("*f", "se");
        recipe.setIngredient('f', Material.FLINT);
        recipe.setIngredient('s', Material.STICK);
        recipe.setIngredient('*', Material.STRING);
        recipe.setIngredient('e', Material.SPIDER_EYE);
        return recipe;
    }

}
