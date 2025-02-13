package tk.shanebee.survival.item.items.armor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class ArmorPiece extends Item {

    private final ArmorType armorType;
    private final ArmorMaterial armorMaterial;

    public ArmorPiece(ArmorType armorType, ArmorMaterial armorMaterial, double armor, double moveSpeed) {
        this(armorType, armorMaterial, armor, moveSpeed, 0, 0);
    }

    public ArmorPiece(ArmorType armorType, ArmorMaterial armorMaterial, double armor, double moveSpeed, double toughness, double knockback) {
        this.armorType = armorType;
        this.armorMaterial = armorMaterial;

        ItemStack itemStack = armorMaterial.getItemType(armorType).createItemStack();

        ItemMeta itemMeta = itemStack.getItemMeta();

        AttributeModifier armorMod = new AttributeModifier(NamespacedKey.minecraft("armor." + armorType.key), armor, Operation.ADD_NUMBER, EquipmentSlotGroup.FEET);
        itemMeta.addAttributeModifier(Attribute.ARMOR, armorMod);

        AttributeModifier speedMod = new AttributeModifier(BASE_MOVEMENT_SPEED, moveSpeed, Operation.ADD_SCALAR, EquipmentSlotGroup.FEET);
        itemMeta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedMod);

        if (toughness > 0) {
            AttributeModifier toughnessMod = new AttributeModifier(BASE_ATTACK_TOUGH, toughness, Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
            itemMeta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, toughnessMod);
        }

        if (knockback > 0) {
            AttributeModifier knockbackMod = new AttributeModifier(BASE_ATTACK_KNOCKBACK, knockback, Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
            itemMeta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE, knockbackMod);
        }

        itemStack.setItemMeta(itemMeta);
        setupDefaults(armorMaterial.getKey(armorType), itemStack);
    }

    @SuppressWarnings({"DataFlowIssue", "deprecation"})
    @Override
    public void registerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        if (this.armorMaterial == ArmorMaterial.GOLDEN && this.armorType == ArmorType.HELMET) {
            recipe.shape("#e#", "###");
            recipe.setIngredient('e', Material.EMERALD);
        } else {
            recipe.shape(this.armorType.shape);
        }
        recipe.setIngredient('#', this.armorMaterial.recipeMaterial.asMaterial());
        Bukkit.addRecipe(recipe);
    }

    public enum ArmorType {
        HELMET("helmet", "crown", "###", "# #"),
        CHESTPLATE("chestplate", "guard", "# #", "###", "###"),
        LEGGINGS("leggings", "greaves", "###", "# #", "# #"),
        BOOTS("boots", "sabatons", "# #", "# #");

        private final String key;
        private final String name;
        private final String[] shape;

        ArmorType(String key, String name, String... shape) {
            this.key = key;
            this.name = name;
            this.shape = shape;
        }

        public String getKey() {
            return this.key;
        }
    }

    public enum ArmorMaterial {
        LEATHER("leather", null),
        GOLDEN("golden", ItemType.GOLD_INGOT),
        IRON("iron", ItemType.IRON_INGOT),
        DIAMOND("diamond", ItemType.DIAMOND),
        NETHERITE("netherite", ItemType.NETHERITE_INGOT);

        private final String key;
        private final ItemType recipeMaterial;

        ArmorMaterial(String key, ItemType recipeMaterial) {
            this.key = key;
            this.recipeMaterial = recipeMaterial;
        }

        public ItemType getItemType(ArmorType armorType) {
            String typeKey = this.key + "_" + armorType.key;
            return Registry.ITEM.get(NamespacedKey.minecraft(typeKey));
        }

        public String getKey(ArmorType armorType) {
            return this.key + "_" + (this == GOLDEN ? armorType.name : armorType.key);
        }
    }

}
