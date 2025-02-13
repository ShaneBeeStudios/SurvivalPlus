package tk.shanebee.survival.item.items.legendary;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class ObsidianMace extends Item {

    public ObsidianMace() {
        ItemStack itemStack = ItemType.DIAMOND_SHOVEL.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        int gSpade_dmg = 4;
        float gSpade_spd = 0.8f;
        float gSpade_knockbackPercent = 0.5f;

        AttributeModifier i_gSpadeDamage = new AttributeModifier(BASE_ATTACK_DAMAGE, gSpade_dmg - 1, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_DAMAGE, i_gSpadeDamage);

        AttributeModifier i_gSpadeSpeed = new AttributeModifier(BASE_ATTACK_SPEED, gSpade_spd - 4, Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.ATTACK_SPEED, i_gSpadeSpeed);

        AttributeModifier i_gSpadeKnock = new AttributeModifier(NamespacedKey.minecraft("base_attack_resistance"), gSpade_knockbackPercent, Operation.ADD_SCALAR, EquipmentSlotGroup.HAND);
        itemMeta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE, i_gSpadeKnock);

        itemMeta.addEnchant(Enchantment.KNOCKBACK, 3, true);
        itemMeta.addEnchant(Enchantment.UNBREAKING, 5, true);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
        setupDefaults("obsidian_mace", itemStack);
    }

    @Override
    public void registerRecipe() {
        ShapedRecipe obsidian_mace = new ShapedRecipe(this.recipeKey, this.getItemStack());

        obsidian_mace.shape(" oo", " eo", "s  ");

        obsidian_mace.setIngredient('o', Material.OBSIDIAN);
        obsidian_mace.setIngredient('e', Material.END_CRYSTAL);
        obsidian_mace.setIngredient('s', Material.STICK);
        Bukkit.addRecipe(obsidian_mace);

    }

}
