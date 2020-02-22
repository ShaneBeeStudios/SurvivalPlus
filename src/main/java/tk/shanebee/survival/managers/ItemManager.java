package tk.shanebee.survival.managers;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manager for custom <b>SurvivalPlus</b> items
 */
@SuppressWarnings("ConstantConditions")
public class ItemManager {
	
	private static Lang lang = Survival.getInstance().getLang();

	/**
	 * Get a custom SurvivalPlus item
	 *
	 * @param item The item you would like to get
	 * @return An ItemStack from a custom item enum
	 */
	public static ItemStack get(Items item) {
		switch (item) {
			case HATCHET:
				ItemStack i_hatchet = new ItemStack(Items.HATCHET.getMaterialType(), 1);
				ItemMeta hatchetMeta = i_hatchet.getItemMeta();
				hatchetMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.hatchet));
				hatchetMeta.setCustomModelData(Items.HATCHET.getModelData());
				i_hatchet.setItemMeta(hatchetMeta);
				return i_hatchet;
			case MATTOCK:
				ItemStack i_mattock = new ItemStack(Items.MATTOCK.getMaterialType(), 1);
				ItemMeta mattockMeta = i_mattock.getItemMeta();
				mattockMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.mattock));
				mattockMeta.setCustomModelData(Items.MATTOCK.getModelData());
				i_mattock.setItemMeta(mattockMeta);
				return i_mattock;

			case SHIV:
				ItemStack i_shiv = new ItemStack(Items.SHIV.getMaterialType(), 1);
				ItemMeta i_shivMeta = i_shiv.getItemMeta();
				i_shivMeta.setCustomModelData(Items.SHIV.getModelData());

				int shiv_dmg = 4;
				float shiv_spd = 1.8f;

				AttributeModifier i_shivDamage = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c56"),
						"generic.attackDamage", shiv_dmg - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
				i_shivMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, i_shivDamage);

				AttributeModifier i_shivSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c57"),
						"generic.attackSpeed", shiv_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
				i_shivMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_shivSpeed);

				i_shivMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

				i_shivMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.shiv));
				i_shivMeta.setLore(Arrays.asList(
						ChatColor.RESET + Utils.getColoredString(lang.poisoned_enemy),
						"",
						ChatColor.GRAY + Utils.getColoredString(lang.in_main_hand),
						ChatColor.DARK_GREEN + " " + shiv_spd + " " + Utils.getColoredString(lang.attack_speed),
						ChatColor.DARK_GREEN + " " + shiv_dmg + " " + Utils.getColoredString(lang.attack_damage),
						"",
						ChatColor.GRAY + Utils.getColoredString(lang.in_off_hand),
						ChatColor.GRAY + " " + Utils.getColoredString(lang.poisoned_retain),
						ChatColor.GRAY + " " + Utils.getColoredString(lang.reduce_50)
						)
				);
				i_shiv.setItemMeta(i_shivMeta);
				return i_shiv;

			case HAMMER:
				ItemStack i_hammer = new ItemStack(Items.HAMMER.getMaterialType(), 1);
				ItemMeta hammerMeta = i_hammer.getItemMeta();
				hammerMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.hammer));
				hammerMeta.setCustomModelData(Items.HAMMER.getModelData());
				i_hammer.setItemMeta(hammerMeta);
				return i_hammer;

			case VALKYRIES_AXE:
				ItemStack i_gAxe = new ItemStack(Items.VALKYRIES_AXE.getMaterialType(), 1);
				ItemMeta i_gAxeMeta = i_gAxe.getItemMeta();
				i_gAxeMeta.setCustomModelData(Items.VALKYRIES_AXE.getModelData());

				int gAxe_spd = 1;
				int gAxe_dmg = 8;

				AttributeModifier i_gAxeSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c58"),
						"generic.attackSpeed", gAxe_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
				i_gAxeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_gAxeSpeed);

				i_gAxeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

				i_gAxeMeta.setDisplayName(ChatColor.RESET + "" + Utils.getColoredString(lang.valkyrie_axe));
				i_gAxeMeta.setLore(Arrays.asList(
						ChatColor.RESET + Utils.getColoredString(lang.valkyrie_axe_unable_dual),
						"",
						ChatColor.GRAY + Utils.getColoredString(lang.in_main_hand),
						ChatColor.DARK_GREEN + " " + gAxe_spd + " " + Utils.getColoredString(lang.attack_speed),
						ChatColor.DARK_GREEN + " " + gAxe_dmg + " " + Utils.getColoredString(lang.attack_damage),
						ChatColor.RESET + Utils.getColoredString(lang.valkyrie_axe_spin),
						ChatColor.RESET + "  " + Utils.getColoredString(lang.valkyrie_axe_cooldown),
						ChatColor.RESET + "  " + Utils.getColoredString(lang.decrease_hunger_value)
						)
				);
				i_gAxeMeta.addEnchant(Enchantment.DURABILITY, 5, true);
				i_gAxe.setItemMeta(i_gAxeMeta);
				return i_gAxe;

			case QUARTZ_PICKAXE:
				ItemStack i_gPickaxe = new ItemStack(Items.QUARTZ_PICKAXE.getMaterialType(), 1);
				ItemMeta i_gPickaxeMeta = i_gPickaxe.getItemMeta();
				i_gPickaxeMeta.setCustomModelData(Items.QUARTZ_PICKAXE.getModelData());

				int gPickaxe_dmg = 5;
				float gPickaxe_spd = 0.8f;

				AttributeModifier i_gPickDamage = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c59"),
						"generic.attackDamage", gPickaxe_dmg - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
				i_gPickaxeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, i_gPickDamage);

				AttributeModifier i_gPickSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c60"),
						"generic.attackSpeed", gPickaxe_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
				i_gPickaxeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_gPickSpeed);

				i_gPickaxeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

				i_gPickaxeMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.quartz_breaker));
				i_gPickaxeMeta.setLore(Arrays.asList(
						"",
						ChatColor.GRAY + Utils.getColoredString(lang.in_main_hand),
						ChatColor.DARK_GREEN + " " + gPickaxe_spd + " " + Utils.getColoredString(lang.attack_speed),
						ChatColor.DARK_GREEN + " " + gPickaxe_dmg + " " + Utils.getColoredString(lang.attack_damage),
						ChatColor.GRAY + " " + Utils.getColoredString(lang.haste)
						)
				);
				i_gPickaxeMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
				i_gPickaxeMeta.addEnchant(Enchantment.MENDING, 1, false);
				i_gPickaxeMeta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
				i_gPickaxe.setItemMeta(i_gPickaxeMeta);
				return i_gPickaxe;

			case OBSIDIAN_MACE:
				ItemStack i_gSpade = new ItemStack(Items.OBSIDIAN_MACE.getMaterialType(), 1);
				ItemMeta i_gSpadeMeta = i_gSpade.getItemMeta();
				i_gSpadeMeta.setCustomModelData(Items.OBSIDIAN_MACE.getModelData());

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

				i_gSpadeMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.obsidian_mace));
				i_gSpadeMeta.setLore(Arrays.asList(
						ChatColor.RESET + Utils.getColoredString(lang.cripple_hit),
						ChatColor.RESET + Utils.getColoredString(lang.drain_hit),
						"",
						ChatColor.GRAY + Utils.getColoredString(lang.in_main_hand),
						ChatColor.DARK_GREEN + " " + gSpade_spd + " " + Utils.getColoredString(lang.attack_speed),
						ChatColor.DARK_GREEN + " " + gSpade_dmg + " " + Utils.getColoredString(lang.attack_damage),
						ChatColor.RESET + " " + Utils.getColoredString(lang.exhausted_slow),
						ChatColor.RESET + " " + Utils.getColoredString(lang.expire_disarm),
						ChatColor.RESET + " " + Utils.getColoredString(lang.knockback_resistance)
						)
				);
				i_gSpadeMeta.addEnchant(Enchantment.KNOCKBACK, 3, true);
				i_gSpadeMeta.addEnchant(Enchantment.DURABILITY, 5, true);
				i_gSpadeMeta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
				i_gSpade.setItemMeta(i_gSpadeMeta);
				return i_gSpade;

			case ENDER_GIANT_BLADE:
				ItemStack i_gHoe = new ItemStack(Items.ENDER_GIANT_BLADE.getMaterialType(), 1);
				ItemMeta i_gHoeMeta = i_gHoe.getItemMeta();
				i_gHoeMeta.setCustomModelData(Items.ENDER_GIANT_BLADE.getModelData());

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

				i_gHoeMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.ender_giant_blade));
				i_gHoeMeta.setLore(Arrays.asList(
						ChatColor.RESET + Utils.getColoredString(lang.ender_giant_blade_unable_duel),
						"",
						ChatColor.GRAY + Utils.getColoredString(lang.in_main_hand),
						ChatColor.DARK_GREEN + " " + gHoe_spd + " " + Utils.getColoredString(lang.attack_speed),
						ChatColor.DARK_GREEN + " " + gHoe_dmg + " " + Utils.getColoredString(lang.attack_damage),
						ChatColor.GRAY + " " + Utils.getColoredString(lang.right_click_sprinting),
						ChatColor.RESET + "  " + Utils.getColoredString(lang.ender_giant_blade_charge),
						ChatColor.RESET + "  " + Utils.getColoredString(lang.ender_giant_blade_cooldown),
						ChatColor.RESET + "  " + Utils.getColoredString(lang.decrease_hunger_value),
						"",
						ChatColor.GRAY + Utils.getColoredString(lang.in_off_hand),
						ChatColor.RESET + " " + Utils.getColoredString(lang.half_shield_resistance),
						ChatColor.RESET + " " + Utils.getColoredString(lang.reflecting_coming)
						)
				);
				i_gHoeMeta.addEnchant(Enchantment.DURABILITY, 5, true);
				i_gHoe.setItemMeta(i_gHoeMeta);
				return i_gHoe;

			case BLAZE_SWORD:
				ItemStack i_gSword = new ItemStack(Items.BLAZE_SWORD.getMaterialType(), 1);
				ItemMeta i_gSwordMeta = i_gSword.getItemMeta();
				i_gSwordMeta.setCustomModelData(Items.BLAZE_SWORD.getModelData());

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

				i_gSwordMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.blaze_sword));
				i_gSwordMeta.setLore(Arrays.asList(
						"",
						ChatColor.GRAY + Utils.getColoredString(lang.in_main_hand),
						ChatColor.DARK_GREEN + " " + gSword_spd + " " + Utils.getColoredString(lang.attack_speed),
						ChatColor.DARK_GREEN + " " + gSword_dmg + " " + Utils.getColoredString(lang.attack_damage),
						ChatColor.RESET + " " + Utils.getColoredString(lang.blaze_sword_fire_resistance),
						ChatColor.RESET + " " + Utils.getColoredString(lang.blaze_sword_fiery),
						"",
						ChatColor.GRAY + Utils.getColoredString(lang.right_click_sneaking),
						ChatColor.RESET + " " + Utils.getColoredString(lang.blaze_sword_spread_fire),
						ChatColor.RESET + " " + Utils.getColoredString(lang.blaze_sword_cost)
						)
				);
				i_gSwordMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
				i_gSwordMeta.addEnchant(Enchantment.DURABILITY, 3, false);
				i_gSword.setItemMeta(i_gSwordMeta);
				return i_gSword;

			case WORKBENCH:
				ItemStack workbench = new ItemStack(Items.WORKBENCH.getMaterialType(), 1);
				ItemMeta workbenchMeta = workbench.getItemMeta();
				workbenchMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.workbench));
				workbench.setItemMeta(workbenchMeta);
				return workbench;

			case FIRESTRIKER:
				ItemStack i_firestriker = new ItemStack(Items.FIRESTRIKER.getMaterialType(), 1);
				ItemMeta i_firestrikerMeta = i_firestriker.getItemMeta();
				i_firestrikerMeta.setCustomModelData(Items.FIRESTRIKER.getModelData());

				float firestriker_spd = 4f;

				AttributeModifier i_firestrikerSpeed = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c55"),
						"generic.attackSpeed",
						firestriker_spd - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

				i_firestrikerMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, i_firestrikerSpeed);
				i_firestrikerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				i_firestrikerMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.firestriker));
				String lore = Utils.getColoredString(lang.firestriker_lore);
				i_firestrikerMeta.setLore(Arrays.asList(lore.split("\\|\\|")));
				i_firestriker.setItemMeta(i_firestrikerMeta);
				return i_firestriker;

			case FERMENTED_SKIN:
				ItemStack i_fermentedSkin = new ItemStack(Items.FERMENTED_SKIN.getMaterialType(), 1);
				ItemMeta fermentedSkinMeta = i_fermentedSkin.getItemMeta();
				fermentedSkinMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.fermented_skin));
				i_fermentedSkin.setItemMeta(fermentedSkinMeta);
				return i_fermentedSkin;

			case MEDIC_KIT:
				ItemStack i_medicKit = new ItemStack(Items.MEDIC_KIT.getMaterialType(), 1);
				ItemMeta medicKitMeta = i_medicKit.getItemMeta();
				medicKitMeta.setCustomModelData(Items.MEDIC_KIT.getModelData());
				medicKitMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.medical_kit));
				i_medicKit.setItemMeta(medicKitMeta);
				return i_medicKit;

			case REINFORCED_LEATHER_BOOTS:
				ItemStack i_leatherBoots = new ItemStack(Items.REINFORCED_LEATHER_BOOTS.getMaterialType(), 1);
				ItemMeta i_leatherBootsMeta = i_leatherBoots.getItemMeta();
				i_leatherBootsMeta.setCustomModelData(Items.REINFORCED_LEATHER_BOOTS.getModelData());

				AttributeModifier i_leatherBootsArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c70"),
						"generic.armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
				i_leatherBootsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_leatherBootsArmor);

				i_leatherBootsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.reinforced_boots));

				i_leatherBoots.setItemMeta(i_leatherBootsMeta);
				return i_leatherBoots;

			case REINFORCED_LEATHER_TUNIC:
				ItemStack i_leatherChestplate = new ItemStack(Items.REINFORCED_LEATHER_TUNIC.getMaterialType(), 1);

				ItemMeta leatherChestplateMeta = i_leatherChestplate.getItemMeta();
				leatherChestplateMeta.setCustomModelData(Items.REINFORCED_LEATHER_TUNIC.getModelData());
				leatherChestplateMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.reinforced_tunic));

				i_leatherChestplate.setItemMeta(leatherChestplateMeta);
				return i_leatherChestplate;

			case REINFORCED_LEATHER_TROUSERS:
				ItemStack i_leatherLeggings = new ItemStack(Items.REINFORCED_LEATHER_TROUSERS.getMaterialType(), 1);

				ItemMeta leatherLeggingsMeta = i_leatherLeggings.getItemMeta();
				leatherLeggingsMeta.setCustomModelData(Items.REINFORCED_LEATHER_TROUSERS.getModelData());
				leatherLeggingsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.reinforced_pants));

				i_leatherLeggings.setItemMeta(leatherLeggingsMeta);
				return i_leatherLeggings;

			case REINFORCED_LEATHER_HELMET:
				//Reinforced Leather Helmet
				ItemStack i_leatherHelmet = new ItemStack(Items.REINFORCED_LEATHER_HELMET.getMaterialType(), 1);

				ItemMeta leatherHelmetMeta = i_leatherHelmet.getItemMeta();
				leatherHelmetMeta.setCustomModelData(Items.REINFORCED_LEATHER_HELMET.getModelData());
				leatherHelmetMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.reinforced_hat));

				i_leatherHelmet.setItemMeta(leatherHelmetMeta);
				return i_leatherHelmet;

			case GOLDEN_SABATONS:
				ItemStack i_goldBoots = new ItemStack(Items.GOLDEN_SABATONS.getMaterialType(), 1);
				ItemMeta i_goldBootsMeta = i_goldBoots.getItemMeta();

				AttributeModifier i_goldBootsArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c71"),
						"generic.armor", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
				i_goldBootsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_goldBootsArmor);

				i_goldBootsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.golden_sabatons));
				i_goldBootsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_FALL, 4, true);

				i_goldBoots.setItemMeta(i_goldBootsMeta);
				return i_goldBoots;

			case GOLDEN_GUARD:
				ItemStack i_goldChestplate = new ItemStack(Items.GOLDEN_GUARD.getMaterialType(), 1);
				ItemMeta i_goldChestplateMeta = i_goldChestplate.getItemMeta();

				AttributeModifier i_goldChestArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c72"),
						"generic.armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
				i_goldChestplateMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_goldChestArmor);

				i_goldChestplateMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.golden_guard));
				i_goldChestplateMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS, 4, true);

				i_goldChestplate.setItemMeta(i_goldChestplateMeta);
				return i_goldChestplate;

			case GOLDEN_GREAVES:
				ItemStack i_goldLeggings = new ItemStack(Items.GOLDEN_GREAVES.getMaterialType(), 1);
				ItemMeta i_goldLeggingsMeta = i_goldLeggings.getItemMeta();

				AttributeModifier i_goldLeggingsArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c73"),
						"generic.armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
				i_goldLeggingsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_goldLeggingsArmor);

				i_goldLeggingsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.golden_greaves));
				i_goldLeggingsMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);

				i_goldLeggings.setItemMeta(i_goldLeggingsMeta);
				return i_goldLeggings;

			case GOLDEN_CROWN:
				ItemStack i_goldHelmet = new ItemStack(Items.GOLDEN_CROWN.getMaterialType(), 1);
				ItemMeta i_goldHelmetMeta = i_goldHelmet.getItemMeta();

				AttributeModifier i_goldHelmetArmor = new AttributeModifier(UUID.fromString("95c4f950-1631-4cc4-9f67-f45d8f087c74"),
						"generic.armor", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
				i_goldHelmetMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, i_goldHelmetArmor);

				i_goldHelmetMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.golden_crown));
				i_goldHelmetMeta.addEnchant(org.bukkit.enchantments.Enchantment.MENDING, 1, true);

				i_goldHelmet.setItemMeta(i_goldHelmetMeta);
				return i_goldHelmet;

			case IRON_BOOTS:
				ItemStack i_ironBoots = new ItemStack(Items.IRON_BOOTS.getMaterialType(), 1);
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
				ItemStack i_ironChestplate = new ItemStack(Items.IRON_CHESTPLATE.getMaterialType(), 1);
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
				ItemStack i_ironLeggings = new ItemStack(Items.IRON_LEGGINGS.getMaterialType(), 1);
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
				ItemStack i_ironHelmet = new ItemStack(Items.IRON_HELMET.getMaterialType(), 1);
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
				ItemStack i_diamondBoots = new ItemStack(Items.DIAMOND_BOOTS.getMaterialType(), 1);
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
				ItemStack i_diamondChestplate = new ItemStack(Items.DIAMOND_CHESTPLATE.getMaterialType(), 1);
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
				ItemStack i_diamondLeggings = new ItemStack(Items.DIAMOND_LEGGINGS.getMaterialType(), 1);
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
				ItemStack i_diamondHelmet = new ItemStack(Items.DIAMOND_HELMET.getMaterialType(), 1);
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
				ItemStack i_recurveBow = new ItemStack(Items.RECURVE_BOW.getMaterialType(), 1);

				ItemMeta recurveBowMeta = i_recurveBow.getItemMeta();
				recurveBowMeta.setCustomModelData(Items.RECURVE_BOW.getModelData());
				recurveBowMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.recurved_bow));
				recurveBowMeta.setLore(Collections.singletonList(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE +
						Utils.getColoredString(lang.recurved)));
				recurveBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
				i_recurveBow.setItemMeta(recurveBowMeta);
				return i_recurveBow;
			case RECURVE_CROSSBOW:
				ItemStack recurveCrossbow = new ItemStack(Items.RECURVE_CROSSBOW.getMaterialType(), 1);
				ItemMeta recurveCrossbowMeta = recurveCrossbow.getItemMeta();
				recurveCrossbowMeta.setCustomModelData(Items.RECURVE_BOW.getModelData());
				recurveCrossbowMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.recurved_crossbow));
				recurveCrossbowMeta.setLore(Collections.singletonList(Utils.getColoredString(lang.recurved)));
				recurveCrossbowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
				recurveCrossbow.setItemMeta(recurveCrossbowMeta);
				return recurveCrossbow;
			case DIRTY_WATER:
				ItemStack dirty_water = new ItemStack(Items.CLEAN_WATER.getMaterialType());
				ItemMeta dirtyMeta = dirty_water.getItemMeta();
				dirtyMeta.setCustomModelData(Items.DIRTY_WATER.getModelData());
				((PotionMeta) dirtyMeta).setBasePotionData(new PotionData(PotionType.WATER));
				((PotionMeta) dirtyMeta).setColor(Color.fromRGB(lang.dirty_water_color));
				dirtyMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.dirty_water));
				dirtyMeta.setLore(Collections.singletonList(Utils.getColoredString(lang.dirty_water_lore)));
				dirtyMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				dirty_water.setItemMeta(dirtyMeta);
				return dirty_water;
			case CLEAN_WATER:
				ItemStack clean_water = new ItemStack(Items.CLEAN_WATER.getMaterialType());
				ItemMeta cleanMeta = clean_water.getItemMeta();
				cleanMeta.setCustomModelData(Items.CLEAN_WATER.getModelData());
				((PotionMeta) cleanMeta).setBasePotionData(new PotionData(PotionType.WATER));
				((PotionMeta) cleanMeta).setColor(Color.fromRGB(lang.clean_water_color));
				cleanMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.clean_water));
				cleanMeta.setLore(Collections.singletonList(Utils.getColoredString(lang.clean_water_lore)));
				cleanMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				clean_water.setItemMeta(cleanMeta);
				return clean_water;
			case PURIFIED_WATER:
				ItemStack purified_water = new ItemStack(Items.PURIFIED_WATER.getMaterialType());
				ItemMeta meta = purified_water.getItemMeta();
				meta.setCustomModelData(Items.PURIFIED_WATER.getModelData());
				((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.WATER));
				((PotionMeta) meta).setColor(Color.fromRGB(lang.purified_water_color));
				meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.purified_water));
				meta.setLore(Collections.singletonList(Utils.getColoredString(lang.purified_water_lore)));
				meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				purified_water.setItemMeta(meta);
				return purified_water;
			case WATER_BOWL:
				ItemStack water_bowl = new ItemStack(Items.WATER_BOWL.getMaterialType());
				ItemMeta water_bowlMeta = water_bowl.getItemMeta();
				water_bowlMeta.setCustomModelData(Items.WATER_BOWL.getModelData());
				water_bowlMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.water_bowl));
				water_bowl.setItemMeta(water_bowlMeta);
				return water_bowl;
			case CAMPFIRE:
				ItemStack campfire = new ItemStack(Items.CAMPFIRE.getMaterialType());
				ItemMeta campfireMeta = campfire.getItemMeta();
				campfireMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.campfire_name));
				campfireMeta.setLore(Arrays.asList(Utils.getColoredString(lang.campfire_lore).split("\\|\\|")));
				campfireMeta.setCustomModelData(Items.CAMPFIRE.getModelData());
				BlockData data = Material.CAMPFIRE.createBlockData();
				((Campfire) data).setLit(false);
				((BlockDataMeta) campfireMeta).setBlockData(data);
				campfire.setItemMeta(campfireMeta);
				return campfire;
			case STONE_SICKLE:
				ItemStack stone_sickle = new ItemStack(Items.STONE_SICKLE.getMaterialType());
				ItemMeta stone_sickleMeta = stone_sickle.getItemMeta();
				stone_sickleMeta.setCustomModelData(Items.STONE_SICKLE.getModelData());
				stone_sickleMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.stone_sickle));
				stone_sickle.setItemMeta(stone_sickleMeta);
				return stone_sickle;
			case IRON_SICKLE:
				ItemStack iron_sickle_new = new ItemStack(Items.IRON_SICKLE.getMaterialType());
				ItemMeta iron_sickle_new_meta = iron_sickle_new.getItemMeta();
				iron_sickle_new_meta.setCustomModelData(Items.IRON_SICKLE.getModelData());
				iron_sickle_new_meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.iron_sickle));
				iron_sickle_new.setItemMeta(iron_sickle_new_meta);
				return iron_sickle_new;
			case FLINT_SICKLE:
				ItemStack flint_sickle = new ItemStack(Items.FLINT_SICKLE.getMaterialType());
				ItemMeta flint_sickle_meta = flint_sickle.getItemMeta();
				flint_sickle_meta.setCustomModelData(Items.FLINT_SICKLE.getModelData());
				flint_sickle_meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.flint_sickle));
				flint_sickle.setItemMeta(flint_sickle_meta);
				return flint_sickle;
			case DIAMOND_SICKLE:
				ItemStack diamond_sickle = new ItemStack(Items.DIAMOND_SICKLE.getMaterialType());
				ItemMeta diamond_sickle_meta = diamond_sickle.getItemMeta();
				diamond_sickle_meta.setCustomModelData(Items.DIAMOND_SICKLE.getModelData());
				diamond_sickle_meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.diamond_sickle));
				diamond_sickle.setItemMeta(diamond_sickle_meta);
				return diamond_sickle;
			case GRAPPLING_HOOK:
				ItemStack grappling_hook = new ItemStack(Items.GRAPPLING_HOOK.getMaterialType());
				ItemMeta grappling_meta = grappling_hook.getItemMeta();
				grappling_meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.grappling_hook));
				grappling_meta.setCustomModelData(Items.GRAPPLING_HOOK.getModelData());
				grappling_hook.setItemMeta(grappling_meta);
				return grappling_hook;
			case COFFEE:
				ItemStack coffee = new ItemStack(Items.COFFEE.getMaterialType());
				ItemMeta coffee_meta = coffee.getItemMeta();
				coffee_meta.setCustomModelData(Items.COFFEE.getModelData());
				((PotionMeta) coffee_meta).setBasePotionData(new PotionData(PotionType.WATER));
				((PotionMeta) coffee_meta).setColor(Color.fromRGB(lang.coffee_color));
				coffee_meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.coffee_name));
				coffee_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				coffee.setItemMeta(coffee_meta);
				return coffee;
			case HOT_MILK:
				ItemStack hot_milk = new ItemStack(Items.HOT_MILK.getMaterialType());
				ItemMeta hot_milk_meta = hot_milk.getItemMeta();
				hot_milk_meta.setCustomModelData(Items.HOT_MILK.getModelData());
				((PotionMeta) hot_milk_meta).setBasePotionData(new PotionData(PotionType.WATER));
				((PotionMeta) hot_milk_meta).setColor(Color.fromRGB(lang.hot_milk_color));
				hot_milk_meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.hot_milk_name));
				hot_milk_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				hot_milk.setItemMeta(hot_milk_meta);
				return hot_milk;
			case COLD_MILK:
				ItemStack cold_milk = new ItemStack(Items.COLD_MILK.getMaterialType());
				ItemMeta cold_milk_meta = cold_milk.getItemMeta();
				cold_milk_meta.setCustomModelData(Items.COLD_MILK.getModelData());
				((PotionMeta) cold_milk_meta).setBasePotionData(new PotionData(PotionType.WATER));
				((PotionMeta) cold_milk_meta).setColor(Color.fromRGB(lang.cold_milk_color));
				cold_milk_meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.cold_milk_name));
				cold_milk_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				cold_milk.setItemMeta(cold_milk_meta);
				return cold_milk;
			case COFFEE_BEAN:
				ItemStack coffee_bean = new ItemStack(Items.COFFEE_BEAN.getMaterialType());
				ItemMeta coffee_bean_meta = coffee_bean.getItemMeta();
				coffee_bean_meta.setCustomModelData(Items.COFFEE_BEAN.getModelData());
				coffee_bean_meta.setDisplayName(ChatColor.RESET + Utils.getColoredString(lang.coffee_bean_name));
				coffee_bean.setItemMeta(coffee_bean_meta);
				return coffee_bean;
			case COMPASS:
				ItemStack compass = new ItemStack(Items.COMPASS.getMaterialType());
				ItemMeta compass_meta = compass.getItemMeta();
				List<String> compass_lore = new ArrayList<>();
				for (int i = 0; i < lang.compass_lore.size(); i++) {
					compass_lore.add(Utils.getColoredString(lang.compass_lore.get(i)));
				}
				compass_meta.setLore(compass_lore);
				compass.setItemMeta(compass_meta);
				return compass;

			case PERSISTENT_TORCH:
				// TODO Experimental
				ItemStack persistent_torch = new ItemStack(Items.PERSISTENT_TORCH.getMaterialType());
				ItemMeta p_torch_meta = persistent_torch.getItemMeta();
				p_torch_meta.setCustomModelData(Items.PERSISTENT_TORCH.getModelData());
				p_torch_meta.setLore(Collections.singletonList(ChatColor.AQUA + "Persistent"));
				persistent_torch.setItemMeta(p_torch_meta);
				return persistent_torch;

			case BEEKEEPER_HELMET:
				ItemStack beeHelmet = new ItemStack(Items.BEEKEEPER_HELMET.getMaterialType());
				ItemMeta bhMeta = beeHelmet.getItemMeta();
				bhMeta.setCustomModelData(Items.BEEKEEPER_HELMET.getModelData());
				bhMeta.setDisplayName(Utils.getColoredString(lang.bee_helmet_name));
				bhMeta.setLore(Collections.singletonList(Utils.getColoredString(lang.bee_suit_lore)));
				((LeatherArmorMeta) bhMeta).setColor(Color.WHITE);
				beeHelmet.setItemMeta(bhMeta);
				return beeHelmet;

			case BEEKEEPER_CHESTPLATE:
				ItemStack beeChest = new ItemStack(Items.BEEKEEPER_CHESTPLATE.getMaterialType());
				ItemMeta bcMeta = beeChest.getItemMeta();
				bcMeta.setCustomModelData(Items.BEEKEEPER_CHESTPLATE.getModelData());
				bcMeta.setDisplayName(Utils.getColoredString(lang.bee_chest_name));
				bcMeta.setLore(Collections.singletonList(Utils.getColoredString(lang.bee_suit_lore)));
				((LeatherArmorMeta) bcMeta).setColor(Color.WHITE);
				beeChest.setItemMeta(bcMeta);
				return beeChest;

			case BEEKEEPER_LEGGINGS:
				ItemStack beeLegs = new ItemStack(Items.BEEKEEPER_LEGGINGS.getMaterialType());
				ItemMeta blMeta = beeLegs.getItemMeta();
				blMeta.setCustomModelData(Items.BEEKEEPER_LEGGINGS.getModelData());
				blMeta.setDisplayName(Utils.getColoredString(lang.bee_legs_name));
				blMeta.setLore(Collections.singletonList(Utils.getColoredString(lang.bee_suit_lore)));
				((LeatherArmorMeta) blMeta).setColor(Color.WHITE);
				beeLegs.setItemMeta(blMeta);
				return beeLegs;

			case BEEKEEPER_BOOTS:
				ItemStack beeBoots = new ItemStack(Items.BEEKEEPER_BOOTS.getMaterialType());
				ItemMeta bbMeta = beeBoots.getItemMeta();
				bbMeta.setCustomModelData(Items.BEEKEEPER_BOOTS.getModelData());
				bbMeta.setDisplayName(Utils.getColoredString(lang.bee_boots_name));
				bbMeta.setLore(Collections.singletonList(Utils.getColoredString(lang.bee_suit_lore)));
				((LeatherArmorMeta) bbMeta).setColor(Color.WHITE);
				beeBoots.setItemMeta(bbMeta);
				return beeBoots;

			default:
				return new ItemStack(Material.AIR);
		}
	}

	/**
	 * Compare an ItemStack with a custom {@link Items}
	 * <p>
	 * <b>NOTE:</b> Will only compare a custom item's {@link Material} and CustomModelData tag
	 * </p>
	 *
	 * @param itemStack The ItemStack to check
	 * @param type      The custom item enum to check
	 * @return Whether these two items match or not
	 */
	public static boolean compare(ItemStack itemStack, Items type) {
		if (itemStack.getType() == type.getMaterialType()) {
			if (itemStack.getItemMeta() != null && itemStack.getItemMeta().hasCustomModelData()) {
				return itemStack.getItemMeta().getCustomModelData() == type.getModelData();
			} else {
				return type.getModelData() == 0;
			}
		}
		return false;
	}

	/**
	 * Compare an ItemStack with several custom {@link Items}
	 *
	 * @param itemStack The ItemStack to check
	 * @param type      The custom item enums to check
	 * @return Whether these items match or not
	 */
	public static boolean compare(ItemStack itemStack, Items... type) {
		for (Items item : type) {
			if (compare(itemStack, item)) {
				return true;
			}
		}
		return false;
	}

    /**
     * Apply the attributes from an {@link Items} to an existing ItemStack
     *
     * @param itemStack Current ItemStack to apply attributes to
     * @param items Item to grab data from
     */
	public static void applyAttribute(ItemStack itemStack, Items items) {
	    ItemStack from = items.getItem();
	    ItemMeta metaTo = itemStack.getItemMeta();
	    ItemMeta metaFrom = from.getItemMeta();
        Map<Enchantment, Integer> enchants = metaTo.getEnchants();
        for (Enchantment enchantment : enchants.keySet()) {
            metaFrom.addEnchant(enchantment, enchants.get(enchantment), true);
        }
        itemStack.setItemMeta(metaFrom);
    }

}
