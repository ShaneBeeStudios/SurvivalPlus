package tk.shanebee.survival.item.items.legendary;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class ValkyriesAxe extends Item {

    public ValkyriesAxe() {
        ItemStack itemStack = ItemType.DIAMOND_AXE.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        int gAxe_spd = 1;
        int gAxe_dmg = 8; // TODO, why is this here?

        AttributeModifier i_gAxeSpeed = new AttributeModifier(BASE_ATTACK_SPEED, gAxe_spd - 4, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_SPEED, i_gAxeSpeed);

        itemMeta.addEnchant(Enchantment.UNBREAKING, 5, true);
        itemStack.setItemMeta(itemMeta);

        itemStack.unsetData(DataComponentTypes.REPAIRABLE);
        setupDefaults("valkyries_axe", itemStack);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());
        recipe.shape("ddd", "dnd", " s ");
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('n', Material.NETHER_STAR);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

}
