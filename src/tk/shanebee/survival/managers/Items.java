package tk.shanebee.survival.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.util.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public enum Items {

    HATCHET(Material.WOODEN_AXE, 1),
    MATTOCK(Material.WOODEN_PICKAXE, 1),
    SHIV(Material.WOODEN_HOE, 1),
    HAMMER(Material.WOODEN_SWORD, 1),
    VALKYRIES_AXE(Material.GOLDEN_AXE, 1),
    QUARTZ_PICKAXE(Material.GOLDEN_PICKAXE, 1),
    OBSIDIAN_MACE(Material.GOLDEN_SHOVEL, 1),
    ENDER_GIANT_BLADE(Material.GOLDEN_HOE, 1),
    BLAZE_SWORD(Material.GOLDEN_SWORD, 1),
    WORKBENCH(Material.CRAFTING_TABLE, 0),
    FIRESTRIKER(Material.WOODEN_SHOVEL, 1),
    FERMENTED_SKIN(Material.RABBIT_HIDE, 0),
    MEDIC_KIT(Material.CLOCK, 1),
    REINFORCED_LEATHER_BOOTS(Material.CHAINMAIL_BOOTS, 1),
    REINFORCED_LEATHER_TUNIC(Material.CHAINMAIL_CHESTPLATE, 1),
    REINFORCED_LEATHER_TROUSERS(Material.CHAINMAIL_LEGGINGS, 1),
    REINFORCED_LEATHER_HELMET(Material.CHAINMAIL_HELMET, 1),
    GOLDEN_SABATONS(Material.GOLDEN_BOOTS, 0),
    GOLDEN_GUARD(Material.GOLDEN_CHESTPLATE, 0),
    GOLDEN_GREAVES(Material.GOLDEN_LEGGINGS, 0),
    GOLDEN_CROWN(Material.GOLDEN_HELMET, 0),
    IRON_BOOTS(Material.IRON_BOOTS, 0),
    IRON_CHESTPLATE(Material.IRON_CHESTPLATE, 0),
    IRON_LEGGINGS(Material.IRON_LEGGINGS, 0),
    IRON_HELMET(Material.IRON_HELMET, 0),
    DIAMOND_BOOTS(Material.DIAMOND_BOOTS, 0),
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, 0),
    DIAMOND_HELMET(Material.DIAMOND_HELMET, 0),
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, 0),
    RECURVE_BOW(Material.BOW, 1),
    RECURVE_CROSSBOW(Material.CROSSBOW, 1),
    DIRTY_WATER(Material.POTION, 0),
    CLEAN_WATER(Material.POTION, 1),
    PURIFIED_WATER(Material.POTION, 2),
    WATER_BOWL(Material.BEETROOT_SOUP, 1),
    CAMPFIRE(Material.CAMPFIRE, 1),

    STONE_SICKLE(Material.WOODEN_HOE, 2),
    IRON_SICKLE(Material.WOODEN_HOE, 3),
    GRAPPLING_HOOK(Material.FISHING_ROD, 1);

    private final Material materialType;
    private final int modelData;

    Items(Material mat, int customModelData) {
        this.modelData = customModelData;
        this.materialType = mat;
    }


    public static ItemStack get(Items item) {
        switch (item) {
            case HATCHET:
                ItemStack i_hatchet = new ItemStack(HATCHET.materialType, 1);
                ItemMeta hatchetMeta = i_hatchet.getItemMeta();
                hatchetMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.hatchet));
                hatchetMeta.setCustomModelData(HATCHET.modelData);
                i_hatchet.setItemMeta(hatchetMeta);
                return i_hatchet;
            case MATTOCK:
                ItemStack i_mattock = new ItemStack(MATTOCK.materialType, 1);
                ItemMeta mattockMeta = i_mattock.getItemMeta();
                mattockMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.mattock));
                mattockMeta.setCustomModelData(MATTOCK.modelData);
                i_mattock.setItemMeta(mattockMeta);
                return i_mattock;

            case SHIV:
                ItemStack i_shiv = new ItemStack(SHIV.materialType, 1);
                ItemMeta i_shivMeta = i_shiv.getItemMeta();
                i_shivMeta.setCustomModelData(SHIV.modelData);

                int shiv_dmg = 4;
                float shiv_spd = 1.8f;

                AttributeModifier i_shivDamage = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c56"),
                        "generic.attackDamage", shiv_dmg - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_shivMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, i_shivDamage);

                AttributeModifier i_shivSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c57"),
                        "generic.attackSpeed", shiv_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_shivMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_shivSpeed);

                i_shivMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                i_shivMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.shiv));
                i_shivMeta.setLore(Arrays.asList(
                        ChatColor.RESET + Utils.getColoredString(Survival.lang.poisoned_enemy),
                        "",
                        ChatColor.GRAY + Utils.getColoredString(Survival.lang.in_main_hand),
                        ChatColor.DARK_GREEN + " " + shiv_spd + " " + Utils.getColoredString(Survival.lang.attack_speed),
                        ChatColor.DARK_GREEN + " " + shiv_dmg + " " + Utils.getColoredString(Survival.lang.attack_damage),
                        "",
                        ChatColor.GRAY + Utils.getColoredString(Survival.lang.in_off_hand),
                        ChatColor.GRAY + " " + Utils.getColoredString(Survival.lang.poisoned_retain),
                        ChatColor.GRAY + " " + Utils.getColoredString(Survival.lang.reduce_50)
                        )
                );
                i_shiv.setItemMeta(i_shivMeta);
                return i_shiv;

            case HAMMER:
                ItemStack i_hammer = new ItemStack(HAMMER.materialType, 1);
                ItemMeta hammerMeta = i_hammer.getItemMeta();
                hammerMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.hammer));
                hammerMeta.setCustomModelData(HAMMER.modelData);
                i_hammer.setItemMeta(hammerMeta);
                return i_hammer;

            case VALKYRIES_AXE:
                ItemStack i_gAxe = new ItemStack(VALKYRIES_AXE.materialType, 1);
                ItemMeta i_gAxeMeta = i_gAxe.getItemMeta();
                i_gAxeMeta.setCustomModelData(VALKYRIES_AXE.modelData);

                int gAxe_spd = 1;
                int gAxe_dmg = 8;

                AttributeModifier i_gAxeSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c58"),
                        "generic.attackSpeed", gAxe_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_gAxeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_gAxeSpeed);

                i_gAxeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                i_gAxeMeta.setDisplayName(ChatColor.RESET + "" + Utils.getColoredString(Survival.lang.valkyrie_axe));
                i_gAxeMeta.setLore(Arrays.asList(
                        ChatColor.RESET + Utils.getColoredString(Survival.lang.valkyrie_axe_unable_dual),
                        "",
                        ChatColor.GRAY + Utils.getColoredString(Survival.lang.in_main_hand),
                        ChatColor.DARK_GREEN + " " + gAxe_spd + " " + Utils.getColoredString(Survival.lang.attack_speed),
                        ChatColor.DARK_GREEN + " " + gAxe_dmg + " " + Utils.getColoredString(Survival.lang.attack_damage),
                        ChatColor.RESET + Utils.getColoredString(Survival.lang.valkyrie_axe_spin),
                        ChatColor.RESET + "  " + Utils.getColoredString(Survival.lang.valkyrie_axe_cooldown),
                        ChatColor.RESET + "  " + Utils.getColoredString(Survival.lang.decrease_hunger_value)
                        )
                );
                i_gAxeMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
                i_gAxe.setItemMeta(i_gAxeMeta);
                return i_gAxe;

            case QUARTZ_PICKAXE:
                ItemStack i_gPickaxe = new ItemStack(QUARTZ_PICKAXE.materialType, 1);
                ItemMeta i_gPickaxeMeta = i_gPickaxe.getItemMeta();
                i_gPickaxeMeta.setCustomModelData(QUARTZ_PICKAXE.modelData);

                int gPickaxe_dmg = 5;
                float gPickaxe_spd = 0.8f;

                AttributeModifier i_gPickDamage = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c59"),
                        "generic.attackDamage", gPickaxe_dmg - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_gPickaxeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, i_gPickDamage);

                AttributeModifier i_gPickSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c60"),
                        "generic.attackSpeed", gPickaxe_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_gPickaxeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_gPickSpeed);

                i_gPickaxeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                i_gPickaxeMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.quartz_breaker));
                i_gPickaxeMeta.setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + Utils.getColoredString(Survival.lang.in_main_hand),
                        ChatColor.DARK_GREEN + " " + gPickaxe_spd + " " + Utils.getColoredString(Survival.lang.attack_speed),
                        ChatColor.DARK_GREEN + " " + gPickaxe_dmg + " " + Utils.getColoredString(Survival.lang.attack_damage),
                        ChatColor.GRAY + " " + Utils.getColoredString(Survival.lang.haste)
                        )
                );
                i_gPickaxeMeta.addEnchant(org.bukkit.enchantments.Enchantment.SILK_TOUCH, 1, false);
                i_gPickaxe.setItemMeta(i_gPickaxeMeta);
                return i_gPickaxe;

            case OBSIDIAN_MACE:
                ItemStack i_gSpade = new ItemStack(OBSIDIAN_MACE.materialType, 1);
                ItemMeta i_gSpadeMeta = i_gSpade.getItemMeta();
                i_gSpadeMeta.setCustomModelData(OBSIDIAN_MACE.modelData);

                int gSpade_dmg = 4;
                float gSpade_spd = 0.8f;
                float gSpade_knockbackPercent = 0.5f;

                AttributeModifier i_gSpadeDamage = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c61"),
                        "generic.attackDamage", gSpade_dmg - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_gSpadeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, i_gSpadeDamage);

                AttributeModifier i_gSpadeSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c62"),
                        "generic.attackSpeed", gSpade_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_gSpadeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_gSpadeSpeed);

                AttributeModifier i_gSpadeKnock = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c63"),
                        "generic.knockbackResistance", gSpade_knockbackPercent, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND);
                i_gSpadeMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, i_gSpadeKnock);

                i_gSpadeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                i_gSpadeMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.obsidian_mace));
                i_gSpadeMeta.setLore(Arrays.asList(
                        ChatColor.RESET + Utils.getColoredString(Survival.lang.cripple_hit),
                        ChatColor.RESET + Utils.getColoredString(Survival.lang.drain_hit),
                        "",
                        ChatColor.GRAY + Utils.getColoredString(Survival.lang.in_main_hand),
                        ChatColor.DARK_GREEN + " " + gSpade_spd + " " + Utils.getColoredString(Survival.lang.attack_speed),
                        ChatColor.DARK_GREEN + " " + gSpade_dmg + " " + Utils.getColoredString(Survival.lang.attack_damage),
                        ChatColor.RESET + " " + Utils.getColoredString(Survival.lang.exhausted_slow),
                        ChatColor.RESET + " " + Utils.getColoredString(Survival.lang.expire_disarm),
                        ChatColor.RESET + " " + Utils.getColoredString(Survival.lang.knockback_resistance)
                        )
                );
                i_gSpadeMeta.addEnchant(org.bukkit.enchantments.Enchantment.KNOCKBACK, 3, true);
                i_gSpadeMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
                i_gSpade.setItemMeta(i_gSpadeMeta);
                return i_gSpade;

            case ENDER_GIANT_BLADE:
                ItemStack i_gHoe = new ItemStack(ENDER_GIANT_BLADE.materialType, 1);
                ItemMeta i_gHoeMeta = i_gHoe.getItemMeta();
                i_gHoeMeta.setCustomModelData(ENDER_GIANT_BLADE.modelData);

                int gHoe_dmg = 8;
                int gHoe_spd = 1;
                float gHoe_move = -0.5f;

                AttributeModifier i_gHoeDamage = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c64"),
                        "generic.attackDamage", gHoe_dmg - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_gHoeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, i_gHoeDamage);

                AttributeModifier i_gHoeSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c65"),
                        "generic.attackSpeed", gHoe_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_gHoeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_gHoeSpeed);

                AttributeModifier i_gHoeMove = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c66"),
                        "generic.movementSpeed", gHoe_move, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.OFF_HAND);
                i_gHoeMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, i_gHoeMove);

                i_gHoeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                i_gHoeMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.ender_giant_blade));
                i_gHoeMeta.setLore(Arrays.asList(
                        ChatColor.RESET + Utils.getColoredString(Survival.lang.ender_giant_blade_unable_duel),
                        "",
                        ChatColor.GRAY + Utils.getColoredString(Survival.lang.in_main_hand),
                        ChatColor.DARK_GREEN + " " + gHoe_spd + " " + Utils.getColoredString(Survival.lang.attack_speed),
                        ChatColor.DARK_GREEN + " " + gHoe_dmg + " " + Utils.getColoredString(Survival.lang.attack_damage),
                        ChatColor.GRAY + " " + Utils.getColoredString(Survival.lang.right_click_sprinting),
                        ChatColor.RESET + "  " + Utils.getColoredString(Survival.lang.ender_giant_blade_charge),
                        ChatColor.RESET + "  " + Utils.getColoredString(Survival.lang.ender_giant_blade_cooldown),
                        ChatColor.RESET + "  " + Utils.getColoredString(Survival.lang.decrease_hunger_value),
                        "",
                        ChatColor.GRAY + Utils.getColoredString(Survival.lang.in_off_hand),
                        ChatColor.RESET + " " + Utils.getColoredString(Survival.lang.half_shield_resistance),
                        ChatColor.RESET + " " + Utils.getColoredString(Survival.lang.reflecting_coming)
                        )
                );
                i_gHoeMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
                i_gHoe.setItemMeta(i_gHoeMeta);
                return i_gHoe;

            case BLAZE_SWORD:
                ItemStack i_gSword = new ItemStack(BLAZE_SWORD.materialType, 1);
                ItemMeta i_gSwordMeta = i_gSword.getItemMeta();
                i_gSwordMeta.setCustomModelData(BLAZE_SWORD.modelData);

                int gSword_dmg = 6;
                float gSword_spd = 1.6f;
                int gSword_health = -6;

                AttributeModifier i_gSwordDamage = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c67"),
                        "generic.attackDamage", gSword_dmg - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_gSwordMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, i_gSwordDamage);

                AttributeModifier i_gSwordSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c68"),
                        "generic.attackSpeed", gSword_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_gSwordMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_gSwordSpeed);

                AttributeModifier i_gSwordHealth = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c69"),
                        "generic.maxHealth", gSword_health, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                i_gSwordMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, i_gSwordHealth);

                i_gSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                i_gSwordMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.blaze_sword));
                i_gSwordMeta.setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + Utils.getColoredString(Survival.lang.in_main_hand),
                        ChatColor.DARK_GREEN + " " + gSword_spd + " " + Utils.getColoredString(Survival.lang.attack_speed),
                        ChatColor.DARK_GREEN + " " + gSword_dmg + " " + Utils.getColoredString(Survival.lang.attack_damage),
                        ChatColor.RESET + " " + Utils.getColoredString(Survival.lang.blaze_sword_fire_resistance),
                        ChatColor.RESET + " " + Utils.getColoredString(Survival.lang.blaze_sword_fiery),
                        "",
                        ChatColor.GRAY + Utils.getColoredString(Survival.lang.right_click_sneaking),
                        ChatColor.RESET + " " + Utils.getColoredString(Survival.lang.blaze_sword_spread_fire),
                        ChatColor.RESET + " " + Utils.getColoredString(Survival.lang.blaze_sword_cost)
                        )
                );
                i_gSwordMeta.addEnchant(org.bukkit.enchantments.Enchantment.FIRE_ASPECT, 2, true);
                i_gSword.setItemMeta(i_gSwordMeta);
                return i_gSword;

            case WORKBENCH:
                ItemStack workbench = new ItemStack(WORKBENCH.materialType, 1);
                ItemMeta workbenchMeta = workbench.getItemMeta();
                workbenchMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.workbench));
                workbench.setItemMeta(workbenchMeta);
                return workbench;

            case FIRESTRIKER:
                ItemStack i_firestriker = new ItemStack(FIRESTRIKER.materialType, 1);
                ItemMeta i_firestrikerMeta = i_firestriker.getItemMeta();
                i_firestrikerMeta.setCustomModelData(FIRESTRIKER.modelData);

                float firestriker_spd = 4f;

                AttributeModifier i_firestrikerSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c55"),
                        "generic.attackSpeed",
                        firestriker_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

                i_firestrikerMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_firestrikerSpeed);
                i_firestrikerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                i_firestrikerMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.firestriker));
                i_firestriker.setItemMeta(i_firestrikerMeta);
                return i_firestriker;

            case FERMENTED_SKIN:
                ItemStack i_fermentedSkin = new ItemStack(FERMENTED_SKIN.materialType, 1);
                ItemMeta fermentedSkinMeta = i_fermentedSkin.getItemMeta();
                fermentedSkinMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.fermented_skin));
                i_fermentedSkin.setItemMeta(fermentedSkinMeta);
                return i_fermentedSkin;

            case MEDIC_KIT:
                ItemStack i_medicKit = new ItemStack(MEDIC_KIT.materialType, 1);
                ItemMeta medicKitMeta = i_medicKit.getItemMeta();
                medicKitMeta.setCustomModelData(MEDIC_KIT.modelData);
                medicKitMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.medical_kit));
                i_medicKit.setItemMeta(medicKitMeta);
                return i_medicKit;

            case REINFORCED_LEATHER_BOOTS:
                ItemStack i_leatherBoots = new ItemStack(REINFORCED_LEATHER_BOOTS.materialType, 1);
                ItemMeta i_leatherBootsMeta = i_leatherBoots.getItemMeta();
                i_leatherBootsMeta.setCustomModelData(REINFORCED_LEATHER_BOOTS.modelData);

                AttributeModifier i_leatherBootsArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c70"),
                        "generic.armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
                i_leatherBootsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_leatherBootsArmor);

                i_leatherBootsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.reinforced_boots));

                i_leatherBoots.setItemMeta(i_leatherBootsMeta);
                return i_leatherBoots;

            case REINFORCED_LEATHER_TUNIC:
                ItemStack i_leatherChestplate = new ItemStack(REINFORCED_LEATHER_TUNIC.materialType, 1);

                ItemMeta leatherChestplateMeta = i_leatherChestplate.getItemMeta();
                leatherChestplateMeta.setCustomModelData(REINFORCED_LEATHER_TUNIC.modelData);
                leatherChestplateMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.reinforced_tunic));

                i_leatherChestplate.setItemMeta(leatherChestplateMeta);
                return i_leatherChestplate;

            case REINFORCED_LEATHER_TROUSERS:
                ItemStack i_leatherLeggings = new ItemStack(REINFORCED_LEATHER_TROUSERS.materialType, 1);

                ItemMeta leatherLeggingsMeta = i_leatherLeggings.getItemMeta();
                leatherLeggingsMeta.setCustomModelData(REINFORCED_LEATHER_TROUSERS.modelData);
                leatherLeggingsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.reinforced_pants));

                i_leatherLeggings.setItemMeta(leatherLeggingsMeta);
                return i_leatherLeggings;

            case REINFORCED_LEATHER_HELMET:
                //Reinforced Leather Helmet
                ItemStack i_leatherHelmet = new ItemStack(REINFORCED_LEATHER_HELMET.materialType, 1);

                ItemMeta leatherHelmetMeta = i_leatherHelmet.getItemMeta();
                leatherHelmetMeta.setCustomModelData(REINFORCED_LEATHER_HELMET.modelData);
                leatherHelmetMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.reinforced_hat));

                i_leatherHelmet.setItemMeta(leatherHelmetMeta);
                return i_leatherHelmet;

            case GOLDEN_SABATONS:
                ItemStack i_goldBoots = new ItemStack(GOLDEN_SABATONS.materialType, 1);
                ItemMeta i_goldBootsMeta = i_goldBoots.getItemMeta();

                AttributeModifier i_goldBootsArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c71"),
                        "generic.armor", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
                i_goldBootsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_goldBootsArmor);

                i_goldBootsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.golden_sabatons));
                i_goldBootsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_FALL, 4, true);

                i_goldBoots.setItemMeta(i_goldBootsMeta);
                return i_goldBoots;

            case GOLDEN_GUARD:
                ItemStack i_goldChestplate = new ItemStack(GOLDEN_GUARD.materialType, 1);
                ItemMeta i_goldChestplateMeta = i_goldChestplate.getItemMeta();

                AttributeModifier i_goldChestArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c72"),
                        "generic.armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
                i_goldChestplateMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_goldChestArmor);

                i_goldChestplateMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.golden_guard));
                i_goldChestplateMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS, 4, true);

                i_goldChestplate.setItemMeta(i_goldChestplateMeta);
                return i_goldChestplate;

            case GOLDEN_GREAVES:
                ItemStack i_goldLeggings = new ItemStack(GOLDEN_GREAVES.materialType, 1);
                ItemMeta i_goldLeggingsMeta = i_goldLeggings.getItemMeta();

                AttributeModifier i_goldLeggingsArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c73"),
                        "generic.armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
                i_goldLeggingsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_goldLeggingsArmor);

                i_goldLeggingsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.golden_greaves));
                i_goldLeggingsMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);

                i_goldLeggings.setItemMeta(i_goldLeggingsMeta);
                return i_goldLeggings;

            case GOLDEN_CROWN:
                ItemStack i_goldHelmet = new ItemStack(GOLDEN_CROWN.materialType, 1);
                ItemMeta i_goldHelmetMeta = i_goldHelmet.getItemMeta();

                AttributeModifier i_goldHelmetArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c74"),
                        "generic.armor", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
                i_goldHelmetMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_goldHelmetArmor);

                i_goldHelmetMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.golden_crown));
                i_goldHelmetMeta.addEnchant(org.bukkit.enchantments.Enchantment.MENDING, 1, true);

                i_goldHelmet.setItemMeta(i_goldHelmetMeta);
                return i_goldHelmet;

            case IRON_BOOTS:
                ItemStack i_ironBoots = new ItemStack(IRON_BOOTS.materialType, 1);
                ItemMeta i_ironBootsMeta = i_ironBoots.getItemMeta();

                AttributeModifier i_ironBootsArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c75"),
                        "generic.armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
                i_ironBootsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_ironBootsArmor);
                AttributeModifier i_ironBootsSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c76"),
                        "generic.movementSpeed", -0.02, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET);
                i_ironBootsMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, i_ironBootsSpeed);

                i_ironBoots.setItemMeta(i_ironBootsMeta);
                return i_ironBoots;

            case IRON_CHESTPLATE:
                ItemStack i_ironChestplate = new ItemStack(IRON_CHESTPLATE.materialType, 1);
                ItemMeta i_ironChestplateMeta = i_ironChestplate.getItemMeta();

                AttributeModifier i_ironChestMove = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c77"),
                        "generic.movementSpeed", -0.03, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST);
                i_ironChestplateMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, i_ironChestMove);

                AttributeModifier i_ironChestArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c78"),
                        "generic.armor", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
                i_ironChestplateMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_ironChestArmor);

                i_ironChestplate.setItemMeta(i_ironChestplateMeta);
                return i_ironChestplate;

            case IRON_LEGGINGS:
                ItemStack i_ironLeggings = new ItemStack(IRON_LEGGINGS.materialType, 1);
                ItemMeta i_ironLeggingsMeta = i_ironLeggings.getItemMeta();

                AttributeModifier i_ironLeggingsArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c79"),
                        "generic.armor", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
                i_ironLeggingsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_ironLeggingsArmor);

                AttributeModifier i_ironLeggingsSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c80"),
                        "generic.movementSpeed", -0.03, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.LEGS);
                i_ironLeggingsMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, i_ironLeggingsSpeed);

                i_ironLeggings.setItemMeta(i_ironLeggingsMeta);
                return i_ironLeggings;

            case IRON_HELMET:
                ItemStack i_ironHelmet = new ItemStack(IRON_HELMET.materialType, 1);
                ItemMeta i_ironHelmetMeta = i_ironHelmet.getItemMeta();

                AttributeModifier i_ironHelmetArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c81"),
                        "generic.armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
                i_ironHelmetMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_ironHelmetArmor);

                AttributeModifier i_ironHelmetSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c82"),
                        "generic.movementSpeed", -0.02, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD);
                i_ironHelmetMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, i_ironHelmetSpeed);

                i_ironHelmet.setItemMeta(i_ironHelmetMeta);
                return i_ironHelmet;

            case DIAMOND_BOOTS:
                ItemStack i_diamondBoots = new ItemStack(DIAMOND_BOOTS.materialType, 1);
                ItemMeta i_diamondBootsMeta = i_diamondBoots.getItemMeta();

                AttributeModifier i_diamondBootsArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c83"),
                        "generic.armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
                i_diamondBootsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_diamondBootsArmor);

                AttributeModifier i_diamondBootsSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c84"),
                        "generic.movementSpeed", -0.02, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET);
                i_diamondBootsMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, i_diamondBootsSpeed);

                i_diamondBoots.setItemMeta(i_diamondBootsMeta);
                return i_diamondBoots;

            case DIAMOND_CHESTPLATE:
                ItemStack i_diamondChestplate = new ItemStack(DIAMOND_CHESTPLATE.materialType, 1);
                ItemMeta i_diamondChestplateMeta = i_diamondChestplate.getItemMeta();

                AttributeModifier i_diamondChestArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c85"),
                        "generic.armor", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
                i_diamondChestplateMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_diamondChestArmor);

                AttributeModifier i_diamondChestSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c86"),
                        "generic.movementSpeed", -0.03, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST);
                i_diamondChestplateMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, i_diamondChestSpeed);

                i_diamondChestplate.setItemMeta(i_diamondChestplateMeta);
                return i_diamondChestplate;

            case DIAMOND_LEGGINGS:
                ItemStack i_diamondLeggings = new ItemStack(DIAMOND_LEGGINGS.materialType, 1);
                ItemMeta i_diamondLeggingsMeta = i_diamondLeggings.getItemMeta();

                AttributeModifier i_diamondLegArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c87"),
                        "generic.armor", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
                i_diamondLeggingsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_diamondLegArmor);

                AttributeModifier i_diamondLegSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c88"),
                        "generic.movementSpeed", -0.03, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.LEGS);
                i_diamondLeggingsMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, i_diamondLegSpeed);

                i_diamondLeggings.setItemMeta(i_diamondLeggingsMeta);
                return i_diamondLeggings;

            case DIAMOND_HELMET:
                ItemStack i_diamondHelmet = new ItemStack(DIAMOND_HELMET.materialType, 1);
                ItemMeta i_diamondHelmetMeta = i_diamondHelmet.getItemMeta();

                AttributeModifier i_diamondHelmetArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c89"),
                        "generic.armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
                i_diamondHelmetMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_diamondHelmetArmor);

                AttributeModifier i_diamondHelmetSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c90"),
                        "generic.movementSpeed", -0.02, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD);
                i_diamondHelmetMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, i_diamondHelmetSpeed);

                i_diamondHelmet.setItemMeta(i_diamondHelmetMeta);
                return i_diamondHelmet;

            case RECURVE_BOW:
                ItemStack i_recurveBow = new ItemStack(RECURVE_BOW.materialType, 1);

                ItemMeta recurveBowMeta = i_recurveBow.getItemMeta();
                recurveBowMeta.setCustomModelData(1);
                recurveBowMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.recurved_bow));
                recurveBowMeta.setLore(Collections.singletonList(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE +
                        Utils.getColoredString(Survival.lang.recurved)));
                recurveBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
                i_recurveBow.setItemMeta(recurveBowMeta);
                return i_recurveBow;
            case RECURVE_CROSSBOW:
                ItemStack recurveCrossbow = new ItemStack(Material.CROSSBOW, 1);
                ItemMeta recurveCrossbowMeta = recurveCrossbow.getItemMeta();
                recurveCrossbowMeta.setCustomModelData(1);
                recurveCrossbowMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.recurved_crossbow));
                recurveCrossbowMeta.setLore(Collections.singletonList(Utils.getColoredString(Survival.lang.recurved)));
                recurveCrossbowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
                recurveCrossbow.setItemMeta(recurveCrossbowMeta);
                return recurveCrossbow;
            case DIRTY_WATER:
                ItemStack dirty_water = new ItemStack(CLEAN_WATER.materialType);
                ItemMeta dirtyMeta = dirty_water.getItemMeta();
                ((PotionMeta) dirtyMeta).setBasePotionData(new PotionData(PotionType.WATER));
                ((PotionMeta) dirtyMeta).setColor(Color.fromRGB(Survival.lang.dirty_water_color));
                dirtyMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.dirty_water));
                dirtyMeta.setLore(Collections.singletonList(Utils.getColoredString(Survival.lang.dirty_water_lore)));
                dirtyMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                dirty_water.setItemMeta(dirtyMeta);
                return dirty_water;
            case CLEAN_WATER:
                ItemStack clean_water = new ItemStack(CLEAN_WATER.materialType);
                ItemMeta cleanMeta = clean_water.getItemMeta();
                ((PotionMeta) cleanMeta).setBasePotionData(new PotionData(PotionType.WATER));
                ((PotionMeta) cleanMeta).setColor(Color.fromRGB(Survival.lang.clean_water_color));
                cleanMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.clean_water));
                cleanMeta.setLore(Collections.singletonList(Utils.getColoredString(Survival.lang.clean_water_lore)));
                cleanMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                clean_water.setItemMeta(cleanMeta);
                return clean_water;
            case PURIFIED_WATER:
                ItemStack purified_water = new ItemStack(PURIFIED_WATER.materialType);
                ItemMeta meta = purified_water.getItemMeta();
                ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.WATER));
                ((PotionMeta) meta).setColor(Color.fromRGB(Survival.lang.purified_water_color));
                meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.purified_water));
                meta.setLore(Collections.singletonList(Utils.getColoredString(Survival.lang.purified_water_lore)));
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                purified_water.setItemMeta(meta);
                return purified_water;
            case WATER_BOWL:
                ItemStack water_bowl = new ItemStack(WATER_BOWL.materialType);
                ItemMeta water_bowlMeta = water_bowl.getItemMeta();
                water_bowlMeta.setCustomModelData(1);
                water_bowlMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.water_bowl));
                water_bowl.setItemMeta(water_bowlMeta);
                return water_bowl;
            case CAMPFIRE:
                ItemStack campfire = new ItemStack(CAMPFIRE.materialType);
                ItemMeta campfireMeta = campfire.getItemMeta();
                campfireMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.campfire_name));
                campfireMeta.setLore(Arrays.asList(Utils.getColoredString(Survival.lang.campfire_lore).split("\\|\\|")));
                campfireMeta.setCustomModelData(1);
                campfire.setItemMeta(campfireMeta);

                return campfire;
            case STONE_SICKLE:
                ItemStack stone_sickle = new ItemStack(STONE_SICKLE.materialType);
                ItemMeta stone_sickleMeta = stone_sickle.getItemMeta();
                stone_sickleMeta.setCustomModelData(STONE_SICKLE.modelData);
                stone_sickleMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.stone_sickle));
                stone_sickle.setItemMeta(stone_sickleMeta);

                return stone_sickle;

            case IRON_SICKLE:
                ItemStack iron_sickle = new ItemStack(IRON_SICKLE.materialType);
                ItemMeta iron_sickleMeta = iron_sickle.getItemMeta();
                iron_sickleMeta.setCustomModelData(IRON_SICKLE.modelData);
                iron_sickleMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.iron_sickle));
                iron_sickle.setItemMeta(iron_sickleMeta);

                return iron_sickle;

            case GRAPPLING_HOOK:
                ItemStack grappling_hook = new ItemStack(GRAPPLING_HOOK.materialType);
                ItemMeta grappling_meta = grappling_hook.getItemMeta();
                grappling_meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.grappling_hook));
                grappling_meta.setCustomModelData(GRAPPLING_HOOK.modelData);
                grappling_hook.setItemMeta(grappling_meta);

                return grappling_hook;

            default:
                return new ItemStack(Material.AIR);
        }
    }

    public static boolean compare(ItemStack itemStack, Items type) {
        if (itemStack.getType() == type.materialType) {
            if (itemStack.getItemMeta() != null && itemStack.getItemMeta().hasCustomModelData()) {
                return itemStack.getItemMeta().getCustomModelData() == type.modelData;
            } else {
                return type.modelData == 0;
            }
        }
        return false;
    }

}