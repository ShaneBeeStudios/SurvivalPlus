package tk.shanebee.survival.item.items.armor;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
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

        NamespacedKey modKey = NamespacedKey.minecraft("armor." + armorType.key);

        AttributeModifier armorMod = new AttributeModifier(modKey, armor, Operation.ADD_NUMBER, armorType.slotGroup);
        itemMeta.addAttributeModifier(Attribute.ARMOR, armorMod);

        AttributeModifier speedMod = new AttributeModifier(modKey, moveSpeed, Operation.ADD_SCALAR, armorType.slotGroup);
        itemMeta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedMod);

        if (toughness > 0) {
            AttributeModifier toughnessMod = new AttributeModifier(modKey, toughness, Operation.ADD_NUMBER, armorType.slotGroup);
            itemMeta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, toughnessMod);
        }

        if (knockback > 0) {
            AttributeModifier knockbackMod = new AttributeModifier(modKey, knockback, Operation.ADD_NUMBER, armorType.slotGroup);
            itemMeta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE, knockbackMod);
        }

        itemStack.setItemMeta(itemMeta);

        // Golden crown override
        if (armorMaterial == ArmorMaterial.GOLDEN && armorType == ArmorType.HELMET) {
            itemStack.setData(DataComponentTypes.EQUIPPABLE,
                Equippable.equippable(EquipmentSlot.HEAD)
                    .assetId(Key.key("survival_plus:gold"))
                    .build());
        }

        setupDefaults(armorMaterial.getKey(armorType), itemStack, this.armorMaterial != ArmorMaterial.GOLDEN && this.armorMaterial != ArmorMaterial.LEATHER);
    }

    @SuppressWarnings({"DataFlowIssue", "deprecation"})
    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        if (this.armorMaterial == ArmorMaterial.GOLDEN && this.armorType == ArmorType.HELMET) {
            recipe.shape("#e#", "###");
            recipe.setIngredient('e', Material.EMERALD);
        } else {
            recipe.shape(this.armorType.shape);
        }
        recipe.setIngredient('#', this.armorMaterial.recipeMaterial.asMaterial());
        return recipe;
    }

    public enum ArmorType {
        HELMET("helmet", "crown", EquipmentSlotGroup.HEAD, "###", "# #"),
        CHESTPLATE("chestplate", "guard", EquipmentSlotGroup.CHEST, "# #", "###", "###"),
        LEGGINGS("leggings", "greaves", EquipmentSlotGroup.LEGS, "###", "# #", "# #"),
        BOOTS("boots", "sabatons", EquipmentSlotGroup.FEET, "# #", "# #");

        private final String key;
        private final String name;
        private final EquipmentSlotGroup slotGroup;
        private final String[] shape;

        ArmorType(String key, String name, EquipmentSlotGroup slotGroup, String... shape) {
            this.key = key;
            this.name = name;
            this.slotGroup = slotGroup;
            this.shape = shape;
        }

        public String getKey() {
            return this.key;
        }

        public EquipmentSlotGroup getSlotGroup() {
            return this.slotGroup;
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
