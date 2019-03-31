package com.fattymieo.survival.managers;

import com.fattymieo.survival.Survival;
import com.fattymieo.survival.util.Utils;
import com.lmax.disruptor.util.Util;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public enum Items {

    HATCHET,
    MATTOCK,
    SHIV,
    HAMMER,
    VALKYRIES_AXE,
    QUARTZ_PICKAXE,
    OBSIDIAN_MACE,
    ENDER_GIANT_BLADE,
    BLAZE_SWORD,
    WORKBENCH,
    FIRESTRIKER,
    FERMENTED_SKIN,
    MEDIC_KIT,
    REINFORCED_LEATHER_BOOTS,
    REINFORCED_LEATHER_TUNIC,
    REINFORCED_LEATHER_TROUSERS,
    REINFORCED_LEATHER_HELMET,
    GOLDEN_SABATONS,
    GOLDEN_GUARD,
    GOLDEN_GREAVES,
    GOLDEN_CROWN,
    IRON_BOOTS,
    IRON_CHESTPLATE,
    IRON_LEGGINGS,
    IRON_HELMET,
    DIAMOND_BOOTS,
    DIAMOND_CHESTPLATE,
    DIAMOND_HELMET,
    DIAMOND_LEGGINGS,
    RECURVE_BOW;

    public static ItemStack get(Items item) {
        switch (item) {
            case HATCHET:
                ItemStack i_hatchet = new ItemStack(Material.WOODEN_AXE, 1);
                ItemMeta hatchetMeta = i_hatchet.getItemMeta();
                hatchetMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.hatchet));
                i_hatchet.setItemMeta(hatchetMeta);
                return i_hatchet;
            case MATTOCK:
                ItemStack i_mattock = new ItemStack(Material.WOODEN_PICKAXE, 1);
                ItemMeta mattockMeta = i_mattock.getItemMeta();
                mattockMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.mattock));
                i_mattock.setItemMeta(mattockMeta);
                return i_mattock;
            case SHIV:
                ItemStack i_shiv = new ItemStack(Material.WOODEN_HOE, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_shiv = CraftItemStack.asNMSCopy(i_shiv);
                NBTTagCompound compound_shiv = nmsStack_shiv.getTag();
                if (compound_shiv == null) {
                    compound_shiv = new NBTTagCompound();
                    nmsStack_shiv.setTag(compound_shiv);
                    compound_shiv = nmsStack_shiv.getTag();
                }
                NBTTagList modifiers_shiv = new NBTTagList();
                int shiv_dmg = 4;
                float shiv_spd = 1.8f;

                NBTTagCompound damage_shiv = new NBTTagCompound();
                damage_shiv.set("Slot", new NBTTagString("mainhand"));
                damage_shiv.set("AttributeName", new NBTTagString("generic.attackDamage"));
                damage_shiv.set("Name", new NBTTagString("generic.attackDamage"));
                damage_shiv.set("Amount", new NBTTagInt(shiv_dmg - 1 /*Attack Damage*/));
                damage_shiv.set("Operation", new NBTTagInt(0));
                damage_shiv.set("UUIDLeast", new NBTTagInt(894654));
                damage_shiv.set("UUIDMost", new NBTTagInt(2872));
                modifiers_shiv.add(damage_shiv);

                NBTTagCompound atkSpd_shiv = new NBTTagCompound();
                atkSpd_shiv.set("Slot", new NBTTagString("mainhand"));
                atkSpd_shiv.set("AttributeName", new NBTTagString("generic.attackSpeed"));
                atkSpd_shiv.set("Name", new NBTTagString("generic.attackSpeed"));
                atkSpd_shiv.set("Amount", new NBTTagFloat(shiv_spd - 4 /*Attack Speed*/));
                atkSpd_shiv.set("Operation", new NBTTagInt(0));
                atkSpd_shiv.set("UUIDLeast", new NBTTagInt(675696));
                atkSpd_shiv.set("UUIDMost", new NBTTagInt(34216));
                modifiers_shiv.add(atkSpd_shiv);

                compound_shiv.setInt("HideFlags", 2);

                compound_shiv.set("AttributeModifiers", modifiers_shiv);
                nmsStack_shiv.setTag(compound_shiv);
                i_shiv = CraftItemStack.asBukkitCopy(nmsStack_shiv);

                ItemMeta shivMeta = i_shiv.getItemMeta();
                shivMeta.setDisplayName(Utils.getColoredString(Survival.lang.shiv));
                shivMeta.setLore
                        (
                                Arrays.asList
                                        (
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.poisoned_enemy),
                                                "",
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.in_main_hand),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_speed),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_damage),
                                                "",
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.in_off_hand),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.poisoned_retain),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.reduce_50)
                                        )
                        );
                i_shiv.setItemMeta(shivMeta);
                return i_shiv;
            case HAMMER:
                ItemStack i_hammer = new ItemStack(Material.WOODEN_SWORD, 1);
                ItemMeta hammerMeta = i_hammer.getItemMeta();
                hammerMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.hammer));
                i_hammer.setItemMeta(hammerMeta);
                return i_hammer;
            case VALKYRIES_AXE:
                ItemStack i_gAxe = new ItemStack(Material.GOLDEN_AXE, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_gAxe = CraftItemStack.asNMSCopy(i_gAxe);
                NBTTagCompound compound_gAxe = nmsStack_gAxe.getTag();
                if (compound_gAxe == null) {
                    compound_gAxe = new NBTTagCompound();
                    nmsStack_gAxe.setTag(compound_gAxe);
                    compound_gAxe = nmsStack_gAxe.getTag();
                }
                NBTTagList modifiers_gAxe = new NBTTagList();
                int gAxe_spd = 1;
                int gAxe_dmg = 8;

                NBTTagCompound atkSpd_gAxe = new NBTTagCompound();
                atkSpd_gAxe.set("Slot", new NBTTagString("mainhand"));
                atkSpd_gAxe.set("AttributeName", new NBTTagString("generic.attackSpeed"));
                atkSpd_gAxe.set("Name", new NBTTagString("generic.attackSpeed"));
                atkSpd_gAxe.set("Amount", new NBTTagFloat(gAxe_spd - 4 /*Attack Speed*/));
                atkSpd_gAxe.set("Operation", new NBTTagInt(0));
                atkSpd_gAxe.set("UUIDLeast", new NBTTagInt(11));
                atkSpd_gAxe.set("UUIDMost", new NBTTagInt(2));
                modifiers_gAxe.add(atkSpd_gAxe);

                compound_gAxe.setInt("HideFlags", 2);

                compound_gAxe.set("AttributeModifiers", modifiers_gAxe);
                nmsStack_gAxe.setTag(compound_gAxe);
                i_gAxe = CraftItemStack.asBukkitCopy(nmsStack_gAxe);

                ItemMeta gAxeMeta = i_gAxe.getItemMeta();
                gAxeMeta.setDisplayName(ChatColor.RESET + "" + Utils.getColoredString(Survival.lang.valkyrie_axe));
                gAxeMeta.setLore
                        (
                                Arrays.asList
                                        (
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.valkyrie_axe_unable_dual),
                                                "",
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.in_main_hand),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_speed),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_damage),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.valkyrie_axe_spin),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.valkyrie_axe_cooldown),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.decrease_hunger_value)
                                        )
                        );
                gAxeMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
                i_gAxe.setItemMeta(gAxeMeta);
                return i_gAxe;
            case QUARTZ_PICKAXE:
                ItemStack i_gPickaxe = new ItemStack(Material.GOLDEN_PICKAXE, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_gPickaxe = CraftItemStack.asNMSCopy(i_gPickaxe);
                NBTTagCompound compound_gPickaxe = nmsStack_gPickaxe.getTag();
                if (compound_gPickaxe == null) {
                    compound_gPickaxe = new NBTTagCompound();
                    nmsStack_gPickaxe.setTag(compound_gPickaxe);
                    compound_gPickaxe = nmsStack_gPickaxe.getTag();
                }
                NBTTagList modifiers_gPickaxe = new NBTTagList();
                int gPickaxe_dmg = 5;
                float gPickaxe_spd = 0.8f;

                NBTTagCompound damage_gPickaxe = new NBTTagCompound();
                damage_gPickaxe.set("Slot", new NBTTagString("mainhand"));
                damage_gPickaxe.set("AttributeName", new NBTTagString("generic.attackDamage"));
                damage_gPickaxe.set("Name", new NBTTagString("generic.attackDamage"));
                damage_gPickaxe.set("Amount", new NBTTagInt(gPickaxe_dmg - 1 /*Attack Damage*/));
                damage_gPickaxe.set("Operation", new NBTTagInt(0));
                damage_gPickaxe.set("UUIDLeast", new NBTTagInt(11154));
                damage_gPickaxe.set("UUIDMost", new NBTTagInt(441));
                modifiers_gPickaxe.add(damage_gPickaxe);

                NBTTagCompound atkSpd_gPickaxe = new NBTTagCompound();
                atkSpd_gPickaxe.set("Slot", new NBTTagString("mainhand"));
                atkSpd_gPickaxe.set("AttributeName", new NBTTagString("generic.attackSpeed"));
                atkSpd_gPickaxe.set("Name", new NBTTagString("generic.attackSpeed"));
                atkSpd_gPickaxe.set("Amount", new NBTTagFloat(gPickaxe_spd - 4 /*Attack Speed*/));
                atkSpd_gPickaxe.set("Operation", new NBTTagInt(0));
                atkSpd_gPickaxe.set("UUIDLeast", new NBTTagInt(11));
                atkSpd_gPickaxe.set("UUIDMost", new NBTTagInt(2));
                modifiers_gPickaxe.add(atkSpd_gPickaxe);

                compound_gPickaxe.setInt("HideFlags", 2);

                compound_gPickaxe.set("AttributeModifiers", modifiers_gPickaxe);
                nmsStack_gPickaxe.setTag(compound_gPickaxe);
                i_gPickaxe = CraftItemStack.asBukkitCopy(nmsStack_gPickaxe);

                ItemMeta gPickaxeMeta = i_gPickaxe.getItemMeta();
                gPickaxeMeta.setDisplayName(Utils.getColoredString(Survival.lang.quartz_breaker));
                gPickaxeMeta.setLore
                        (
                                Arrays.asList
                                        (
                                                "",
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.in_main_hand),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_speed),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_damage),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.haste)
                                        )
                        );
                gPickaxeMeta.addEnchant(org.bukkit.enchantments.Enchantment.SILK_TOUCH, 1, false);
                i_gPickaxe.setItemMeta(gPickaxeMeta);
                return i_gPickaxe;
            case OBSIDIAN_MACE:
                ItemStack i_gSpade = new ItemStack(Material.GOLDEN_SHOVEL, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_gSpade = CraftItemStack.asNMSCopy(i_gSpade);
                NBTTagCompound compound_gSpade = nmsStack_gSpade.getTag();
                if (compound_gSpade == null) {
                    compound_gSpade = new NBTTagCompound();
                    nmsStack_gSpade.setTag(compound_gSpade);
                    compound_gSpade = nmsStack_gSpade.getTag();
                }
                NBTTagList modifiers_gSpade = new NBTTagList();
                int gSpade_dmg = 4;
                float gSpade_spd = 0.8f;
                float gSpade_knockbackPercent = 0.5f;

                NBTTagCompound damage_gSpade = new NBTTagCompound();
                damage_gSpade.set("Slot", new NBTTagString("mainhand"));
                damage_gSpade.set("AttributeName", new NBTTagString("generic.attackDamage"));
                damage_gSpade.set("Name", new NBTTagString("generic.attackDamage"));
                damage_gSpade.set("Amount", new NBTTagInt(gSpade_dmg - 1 /*Attack Damage*/));
                damage_gSpade.set("Operation", new NBTTagInt(0));
                damage_gSpade.set("UUIDLeast", new NBTTagInt(11154));
                damage_gSpade.set("UUIDMost", new NBTTagInt(441));
                modifiers_gSpade.add(damage_gSpade);

                NBTTagCompound atkSpd_gSpade = new NBTTagCompound();
                atkSpd_gSpade.set("Slot", new NBTTagString("mainhand"));
                atkSpd_gSpade.set("AttributeName", new NBTTagString("generic.attackSpeed"));
                atkSpd_gSpade.set("Name", new NBTTagString("generic.attackSpeed"));
                atkSpd_gSpade.set("Amount", new NBTTagFloat(gSpade_spd - 4 /*Attack Speed*/));
                atkSpd_gSpade.set("Operation", new NBTTagInt(0));
                atkSpd_gSpade.set("UUIDLeast", new NBTTagInt(11));
                atkSpd_gSpade.set("UUIDMost", new NBTTagInt(2));
                modifiers_gSpade.add(atkSpd_gSpade);

                NBTTagCompound knockback_gSpade = new NBTTagCompound();
                knockback_gSpade.set("Slot", new NBTTagString("mainhand"));
                knockback_gSpade.set("AttributeName", new NBTTagString("generic.knockbackResistance"));
                knockback_gSpade.set("Name", new NBTTagString("generic.knockbackResistance"));
                knockback_gSpade.set("Amount", new NBTTagFloat(gSpade_knockbackPercent /*Knockback Resistance*/));
                knockback_gSpade.set("Operation", new NBTTagInt(1));
                knockback_gSpade.set("UUIDLeast", new NBTTagInt(451215));
                knockback_gSpade.set("UUIDMost", new NBTTagInt(6156));
                modifiers_gSpade.add(knockback_gSpade);

                compound_gSpade.setInt("HideFlags", 2);

                compound_gSpade.set("AttributeModifiers", modifiers_gSpade);
                nmsStack_gSpade.setTag(compound_gSpade);
                i_gSpade = CraftItemStack.asBukkitCopy(nmsStack_gSpade);

                ItemMeta gSpadeMeta = i_gSpade.getItemMeta();
                gSpadeMeta.setDisplayName(Utils.getColoredString(Survival.lang.obsidian_mace));
                gSpadeMeta.setLore
                        (
                                Arrays.asList
                                        (
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.cripple_hit),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.drain_hit),
                                                "",
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.in_main_hand),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_speed),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_damage),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.exhausted_slow),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.expire_disarm),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.knockback_resistance)
                                        )
                        );
                gSpadeMeta.addEnchant(org.bukkit.enchantments.Enchantment.KNOCKBACK, 3, true);
                gSpadeMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
                i_gSpade.setItemMeta(gSpadeMeta);
                return i_gSpade;
            case ENDER_GIANT_BLADE:
                ItemStack i_gHoe = new ItemStack(Material.GOLDEN_HOE, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_gHoe = CraftItemStack.asNMSCopy(i_gHoe);
                NBTTagCompound compound_gHoe = nmsStack_gHoe.getTag();
                if (compound_gHoe == null) {
                    compound_gHoe = new NBTTagCompound();
                    nmsStack_gHoe.setTag(compound_gHoe);
                    compound_gHoe = nmsStack_gHoe.getTag();
                }
                NBTTagList modifiers_gHoe = new NBTTagList();
                int gHoe_dmg = 8;
                int gHoe_spd = 1;
                float gHoe_move = -0.5f;

                NBTTagCompound damage_gHoe = new NBTTagCompound();
                damage_gHoe.set("Slot", new NBTTagString("mainhand"));
                damage_gHoe.set("AttributeName", new NBTTagString("generic.attackDamage"));
                damage_gHoe.set("Name", new NBTTagString("generic.attackDamage"));
                damage_gHoe.set("Amount", new NBTTagInt(gHoe_dmg - 1 /*Attack Damage*/));
                damage_gHoe.set("Operation", new NBTTagInt(0));
                damage_gHoe.set("UUIDLeast", new NBTTagInt(485412));
                damage_gHoe.set("UUIDMost", new NBTTagInt(5544));
                modifiers_gHoe.add(damage_gHoe);

                NBTTagCompound atkSpd_gHoe = new NBTTagCompound();
                atkSpd_gHoe.set("Slot", new NBTTagString("mainhand"));
                atkSpd_gHoe.set("AttributeName", new NBTTagString("generic.attackSpeed"));
                atkSpd_gHoe.set("Name", new NBTTagString("generic.attackSpeed"));
                atkSpd_gHoe.set("Amount", new NBTTagFloat(gHoe_spd - 4 /*Attack Speed*/));
                atkSpd_gHoe.set("Operation", new NBTTagInt(0));
                atkSpd_gHoe.set("UUIDLeast", new NBTTagInt(884654));
                atkSpd_gHoe.set("UUIDMost", new NBTTagInt(9872));
                modifiers_gHoe.add(atkSpd_gHoe);

                NBTTagCompound moveSpd_gHoe = new NBTTagCompound();
                moveSpd_gHoe.set("Slot", new NBTTagString("offhand"));
                moveSpd_gHoe.set("AttributeName", new NBTTagString("generic.movementSpeed"));
                moveSpd_gHoe.set("Name", new NBTTagString("generic.movementSpeed"));
                moveSpd_gHoe.set("Amount", new NBTTagFloat(gHoe_move /*Movement Speed*/));
                moveSpd_gHoe.set("Operation", new NBTTagInt(1));
                moveSpd_gHoe.set("UUIDLeast", new NBTTagInt(88454));
                moveSpd_gHoe.set("UUIDMost", new NBTTagInt(872));
                modifiers_gHoe.add(moveSpd_gHoe);

                compound_gHoe.setInt("HideFlags", 2);

                compound_gHoe.set("AttributeModifiers", modifiers_gHoe);
                nmsStack_gHoe.setTag(compound_gHoe);
                i_gHoe = CraftItemStack.asBukkitCopy(nmsStack_gHoe);

                ItemMeta gHoeMeta = i_gHoe.getItemMeta();
                gHoeMeta.setDisplayName(Utils.getColoredString(Survival.lang.ender_giant_blade));
                gHoeMeta.setLore
                        (
                                Arrays.asList
                                        (
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.ender_giant_blade_unable_duel),
                                                "",
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.in_main_hand),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_speed),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_damage),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.right_click_sprinting),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.ender_giant_blade_charge),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.ender_giant_blade_cooldown),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.decrease_hunger_value),
                                                "",
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.in_off_hand),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.half_shield_resistance),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.reflecting_coming)
                                        )
                        );
                gHoeMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
                i_gHoe.setItemMeta(gHoeMeta);
                return i_gHoe;
            case BLAZE_SWORD:
                ItemStack i_gSword = new ItemStack(Material.GOLDEN_SWORD, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_gSword = CraftItemStack.asNMSCopy(i_gSword);
                NBTTagCompound compound_gSword = nmsStack_gSword.getTag();
                if (compound_gSword == null) {
                    compound_gSword = new NBTTagCompound();
                    nmsStack_gSword.setTag(compound_gSword);
                    compound_gSword = nmsStack_gSword.getTag();
                }
                NBTTagList modifiers_gSword = new NBTTagList();
                int gSword_dmg = 6;
                float gSword_spd = 1.6f;
                int gSword_health = -6;

                NBTTagCompound damage_gSword = new NBTTagCompound();
                damage_gSword.set("Slot", new NBTTagString("mainhand"));
                damage_gSword.set("AttributeName", new NBTTagString("generic.attackDamage"));
                damage_gSword.set("Name", new NBTTagString("generic.attackDamage"));
                damage_gSword.set("Amount", new NBTTagInt(gSword_dmg - 1 /*Attack Damage*/));
                damage_gSword.set("Operation", new NBTTagInt(0));
                damage_gSword.set("UUIDLeast", new NBTTagInt(11154));
                damage_gSword.set("UUIDMost", new NBTTagInt(441));
                modifiers_gSword.add(damage_gSword);

                NBTTagCompound atkSpd_gSword = new NBTTagCompound();
                atkSpd_gSword.set("Slot", new NBTTagString("mainhand"));
                atkSpd_gSword.set("AttributeName", new NBTTagString("generic.attackSpeed"));
                atkSpd_gSword.set("Name", new NBTTagString("generic.attackSpeed"));
                atkSpd_gSword.set("Amount", new NBTTagFloat(gSword_spd - 4 /*Attack Speed*/));
                atkSpd_gSword.set("Operation", new NBTTagInt(0));
                atkSpd_gSword.set("UUIDLeast", new NBTTagInt(11));
                atkSpd_gSword.set("UUIDMost", new NBTTagInt(2));
                modifiers_gSword.add(atkSpd_gSword);

                NBTTagCompound health_gSword = new NBTTagCompound();
                health_gSword.set("Slot", new NBTTagString("mainhand"));
                health_gSword.set("AttributeName", new NBTTagString("generic.maxHealth"));
                health_gSword.set("Name", new NBTTagString("generic.maxHealth"));
                health_gSword.set("Amount", new NBTTagFloat(gSword_health /*Health*/));
                health_gSword.set("Operation", new NBTTagInt(0));
                health_gSword.set("UUIDLeast", new NBTTagInt(574145));
                health_gSword.set("UUIDMost", new NBTTagInt(54));
                modifiers_gSword.add(health_gSword);

                compound_gSword.setInt("HideFlags", 2);

                compound_gSword.set("AttributeModifiers", modifiers_gSword);
                nmsStack_gSword.setTag(compound_gSword);
                i_gSword = CraftItemStack.asBukkitCopy(nmsStack_gSword);

                ItemMeta gSwordMeta = i_gSword.getItemMeta();
                gSwordMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.blaze_sword));
                gSwordMeta.setLore
                        (
                                Arrays.asList
                                        (
                                                "",
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.in_main_hand),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_speed),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.attack_damage),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.blaze_sword_fire_resistance),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.blaze_sword_fiery),
                                                "",
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.right_click_sneaking),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.blaze_sword_spread_fire),
                                                ChatColor.RESET + Utils.getColoredString(Survival.lang.blaze_sword_cost)
                                        )
                        );
                gSwordMeta.addEnchant(org.bukkit.enchantments.Enchantment.FIRE_ASPECT, 2, true);
                i_gSword.setItemMeta(gSwordMeta);
                return i_gSword;
            case WORKBENCH:
                ItemStack workbench = new ItemStack(Material.CRAFTING_TABLE, 1);
                ItemMeta workbenchMeta = workbench.getItemMeta();
                workbenchMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.workbench));
                workbench.setItemMeta(workbenchMeta);
                return workbench;
            case FIRESTRIKER:
                ItemStack i_firestriker = new ItemStack(Material.WOODEN_SHOVEL, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_firestriker = CraftItemStack.asNMSCopy(i_firestriker);
                NBTTagCompound compound_firestriker = nmsStack_firestriker.getTag();
                if (compound_firestriker == null) {
                    compound_firestriker = new NBTTagCompound();
                    nmsStack_firestriker.setTag(compound_firestriker);
                    compound_firestriker = nmsStack_firestriker.getTag();
                }
                NBTTagList modifiers_firestriker = new NBTTagList();
                float firestriker_spd = 4f;

                NBTTagCompound atkSpd_firestriker = new NBTTagCompound();
                atkSpd_firestriker.set("Slot", new NBTTagString("mainhand"));
                atkSpd_firestriker.set("AttributeName", new NBTTagString("generic.attackSpeed"));
                atkSpd_firestriker.set("Name", new NBTTagString("generic.attackSpeed"));
                atkSpd_firestriker.set("Amount", new NBTTagFloat(firestriker_spd - 4 /*Attack Speed*/));
                atkSpd_firestriker.set("Operation", new NBTTagInt(0));
                atkSpd_firestriker.set("UUIDLeast", new NBTTagInt(312465));
                atkSpd_firestriker.set("UUIDMost", new NBTTagInt(4124));
                modifiers_firestriker.add(atkSpd_firestriker);

                compound_firestriker.setInt("HideFlags", 2);

                compound_firestriker.set("AttributeModifiers", modifiers_firestriker);
                nmsStack_firestriker.setTag(compound_firestriker);
                i_firestriker = CraftItemStack.asBukkitCopy(nmsStack_firestriker);

                ItemMeta firestrikerMeta = i_firestriker.getItemMeta();
                firestrikerMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.firestriker));
                i_firestriker.setItemMeta(firestrikerMeta);
                return i_firestriker;
            case FERMENTED_SKIN:
                ItemStack i_fermentedSkin = new ItemStack(Material.RABBIT_HIDE, 1);
                ItemMeta fermentedSkinMeta = i_fermentedSkin.getItemMeta();
                fermentedSkinMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.fermented_skin));
                i_fermentedSkin.setItemMeta(fermentedSkinMeta);
                return i_fermentedSkin;
            case MEDIC_KIT:
                ItemStack i_medicKit = new ItemStack(Material.CLOCK, 1);
                ItemMeta medicKitMeta = i_medicKit.getItemMeta();
                medicKitMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.medical_kit));
                i_medicKit.setItemMeta(medicKitMeta);
                return i_medicKit;
            case REINFORCED_LEATHER_BOOTS:
                ItemStack i_leatherBoots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_leatherBoots = CraftItemStack.asNMSCopy(i_leatherBoots);
                NBTTagCompound compound_leatherBoots = nmsStack_leatherBoots.getTag();
                if (compound_leatherBoots == null) {
                    compound_leatherBoots = new NBTTagCompound();
                    nmsStack_leatherBoots.setTag(compound_leatherBoots);
                    compound_leatherBoots = nmsStack_leatherBoots.getTag();
                }
                NBTTagList modifiers_leatherBoots = new NBTTagList();
                int leatherBoots_armor = 2;

                NBTTagCompound armor_leatherBoots = new NBTTagCompound();
                armor_leatherBoots.set("Slot", new NBTTagString("feet"));
                armor_leatherBoots.set("AttributeName", new NBTTagString("generic.armor"));
                armor_leatherBoots.set("Name", new NBTTagString("generic.armor"));
                armor_leatherBoots.set("Amount", new NBTTagInt(leatherBoots_armor /*Armour*/));
                armor_leatherBoots.set("Operation", new NBTTagInt(0));
                armor_leatherBoots.set("UUIDLeast", new NBTTagInt(32213));
                armor_leatherBoots.set("UUIDMost", new NBTTagInt(3232));
                modifiers_leatherBoots.add(armor_leatherBoots);

                compound_leatherBoots.set("AttributeModifiers", modifiers_leatherBoots);
                nmsStack_leatherBoots.setTag(compound_leatherBoots);
                i_leatherBoots = CraftItemStack.asBukkitCopy(nmsStack_leatherBoots);

                ItemMeta leatherBootsMeta = i_leatherBoots.getItemMeta();
                leatherBootsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.reinforced_boots));

                i_leatherBoots.setItemMeta(leatherBootsMeta);
                return i_leatherBoots;
            case REINFORCED_LEATHER_TUNIC:
                ItemStack i_leatherChestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);

                ItemMeta leatherChestplateMeta = i_leatherChestplate.getItemMeta();
                leatherChestplateMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.reinforced_tunic));

                i_leatherChestplate.setItemMeta(leatherChestplateMeta);
                return i_leatherChestplate;

            case REINFORCED_LEATHER_TROUSERS:
                ItemStack i_leatherLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);

                ItemMeta leatherLeggingsMeta = i_leatherLeggings.getItemMeta();
                leatherLeggingsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.reinforced_pants));

                i_leatherLeggings.setItemMeta(leatherLeggingsMeta);
                return i_leatherLeggings;
            case REINFORCED_LEATHER_HELMET:
                //Reinforced Leather Helmet
                ItemStack i_leatherHelmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);

                ItemMeta leatherHelmetMeta = i_leatherHelmet.getItemMeta();
                leatherHelmetMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.reinforced_hat));

                i_leatherHelmet.setItemMeta(leatherHelmetMeta);
                return i_leatherHelmet;
            case GOLDEN_SABATONS:
                ItemStack i_goldBoots = new ItemStack(Material.GOLDEN_BOOTS, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_goldBoots = CraftItemStack.asNMSCopy(i_goldBoots);
                NBTTagCompound compound_goldBoots = nmsStack_goldBoots.getTag();
                if (compound_goldBoots == null) {
                    compound_goldBoots = new NBTTagCompound();
                    nmsStack_goldBoots.setTag(compound_goldBoots);
                    compound_goldBoots = nmsStack_goldBoots.getTag();
                }
                NBTTagList modifiers_goldBoots = new NBTTagList();
                int goldBoots_armor = 1;

                NBTTagCompound armor_goldBoots = new NBTTagCompound();
                armor_goldBoots.set("Slot", new NBTTagString("feet"));
                armor_goldBoots.set("AttributeName", new NBTTagString("generic.armor"));
                armor_goldBoots.set("Name", new NBTTagString("generic.armor"));
                armor_goldBoots.set("Amount", new NBTTagInt(goldBoots_armor /*Armour*/));
                armor_goldBoots.set("Operation", new NBTTagInt(0));
                armor_goldBoots.set("UUIDLeast", new NBTTagInt(322453));
                armor_goldBoots.set("UUIDMost", new NBTTagInt(323));
                modifiers_goldBoots.add(armor_goldBoots);

                compound_goldBoots.set("AttributeModifiers", modifiers_goldBoots);
                nmsStack_goldBoots.setTag(compound_goldBoots);
                i_goldBoots = CraftItemStack.asBukkitCopy(nmsStack_goldBoots);

                ItemMeta goldBootsMeta = i_goldBoots.getItemMeta();
                goldBootsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.golden_sabatons));
                goldBootsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_FALL, 4, true);

                i_goldBoots.setItemMeta(goldBootsMeta);
                return i_goldBoots;
            case GOLDEN_GUARD:
                ItemStack i_goldChestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_goldChestplate = CraftItemStack.asNMSCopy(i_goldChestplate);
                NBTTagCompound compound_goldChestplate = nmsStack_goldChestplate.getTag();
                if (compound_goldChestplate == null) {
                    compound_goldChestplate = new NBTTagCompound();
                    nmsStack_goldChestplate.setTag(compound_goldChestplate);
                    compound_goldChestplate = nmsStack_goldChestplate.getTag();
                }
                NBTTagList modifiers_goldChestplate = new NBTTagList();
                int goldChestplate_armor = 3;

                NBTTagCompound armor_goldChestplate = new NBTTagCompound();
                armor_goldChestplate.set("Slot", new NBTTagString("chest"));
                armor_goldChestplate.set("AttributeName", new NBTTagString("generic.armor"));
                armor_goldChestplate.set("Name", new NBTTagString("generic.armor"));
                armor_goldChestplate.set("Amount", new NBTTagInt(goldChestplate_armor /*Armour*/));
                armor_goldChestplate.set("Operation", new NBTTagInt(0));
                armor_goldChestplate.set("UUIDLeast", new NBTTagInt(322453));
                armor_goldChestplate.set("UUIDMost", new NBTTagInt(323));
                modifiers_goldChestplate.add(armor_goldChestplate);

                compound_goldChestplate.set("AttributeModifiers", modifiers_goldChestplate);
                nmsStack_goldChestplate.setTag(compound_goldChestplate);
                i_goldChestplate = CraftItemStack.asBukkitCopy(nmsStack_goldChestplate);

                ItemMeta goldChestplateMeta = i_goldChestplate.getItemMeta();
                goldChestplateMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.golden_guard));
                goldChestplateMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS, 4, true);

                i_goldChestplate.setItemMeta(goldChestplateMeta);
                return i_goldChestplate;
            case GOLDEN_GREAVES:
                ItemStack i_goldLeggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_goldLeggings = CraftItemStack.asNMSCopy(i_goldLeggings);
                NBTTagCompound compound_goldLeggings = nmsStack_goldLeggings.getTag();
                if (compound_goldLeggings == null) {
                    compound_goldLeggings = new NBTTagCompound();
                    nmsStack_goldLeggings.setTag(compound_goldLeggings);
                    compound_goldLeggings = nmsStack_goldLeggings.getTag();
                }
                NBTTagList modifiers_goldLeggings = new NBTTagList();
                int goldLeggings_armor = 2;

                NBTTagCompound armor_goldLeggings = new NBTTagCompound();
                armor_goldLeggings.set("Slot", new NBTTagString("legs"));
                armor_goldLeggings.set("AttributeName", new NBTTagString("generic.armor"));
                armor_goldLeggings.set("Name", new NBTTagString("generic.armor"));
                armor_goldLeggings.set("Amount", new NBTTagInt(goldLeggings_armor /*Armour*/));
                armor_goldLeggings.set("Operation", new NBTTagInt(0));
                armor_goldLeggings.set("UUIDLeast", new NBTTagInt(322453));
                armor_goldLeggings.set("UUIDMost", new NBTTagInt(323));
                modifiers_goldLeggings.add(armor_goldLeggings);

                compound_goldLeggings.set("AttributeModifiers", modifiers_goldLeggings);
                nmsStack_goldLeggings.setTag(compound_goldLeggings);
                i_goldLeggings = CraftItemStack.asBukkitCopy(nmsStack_goldLeggings);

                ItemMeta goldLeggingsMeta = i_goldLeggings.getItemMeta();
                goldLeggingsMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.golden_greaves));
                goldLeggingsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS, 4, true);

                i_goldLeggings.setItemMeta(goldLeggingsMeta);
                return i_goldLeggings;
            case GOLDEN_CROWN:
                //Golden Crown
                ItemStack i_goldHelmet = new ItemStack(Material.GOLDEN_HELMET, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_goldHelmet = CraftItemStack.asNMSCopy(i_goldHelmet);
                NBTTagCompound compound_goldHelmet = nmsStack_goldHelmet.getTag();
                if (compound_goldHelmet == null) {
                    compound_goldHelmet = new NBTTagCompound();
                    nmsStack_goldHelmet.setTag(compound_goldHelmet);
                    compound_goldHelmet = nmsStack_goldHelmet.getTag();
                }
                NBTTagList modifiers_goldHelmet = new NBTTagList();
                int goldHelmet_armor = 1;

                NBTTagCompound armor_goldHelmet = new NBTTagCompound();
                armor_goldHelmet.set("Slot", new NBTTagString("head"));
                armor_goldHelmet.set("AttributeName", new NBTTagString("generic.armor"));
                armor_goldHelmet.set("Name", new NBTTagString("generic.armor"));
                armor_goldHelmet.set("Amount", new NBTTagInt(goldHelmet_armor /*Armour*/));
                armor_goldHelmet.set("Operation", new NBTTagInt(0));
                armor_goldHelmet.set("UUIDLeast", new NBTTagInt(322453));
                armor_goldHelmet.set("UUIDMost", new NBTTagInt(323));
                modifiers_goldHelmet.add(armor_goldHelmet);

                compound_goldHelmet.set("AttributeModifiers", modifiers_goldHelmet);
                nmsStack_goldHelmet.setTag(compound_goldHelmet);
                i_goldHelmet = CraftItemStack.asBukkitCopy(nmsStack_goldHelmet);

                ItemMeta goldHelmetMeta = i_goldHelmet.getItemMeta();
                goldHelmetMeta.setDisplayName(ChatColor.RESET + Utils.getColoredString(Survival.lang.golden_crown));
                goldHelmetMeta.addEnchant(org.bukkit.enchantments.Enchantment.MENDING, 1, true);

                i_goldHelmet.setItemMeta(goldHelmetMeta);
                return i_goldHelmet;
            case IRON_BOOTS:
                ItemStack i_ironBoots = new ItemStack(Material.IRON_BOOTS, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_ironBoots = CraftItemStack.asNMSCopy(i_ironBoots);
                NBTTagCompound compound_ironBoots = nmsStack_ironBoots.getTag();
                if (compound_ironBoots == null) {
                    compound_ironBoots = new NBTTagCompound();
                    nmsStack_ironBoots.setTag(compound_ironBoots);
                    compound_ironBoots = nmsStack_ironBoots.getTag();
                }
                NBTTagList modifiers_ironBoots = new NBTTagList();
                int ironBoots_armor = 2;
                float ironBoots_moveSpd = -0.020f;

                NBTTagCompound armor_ironBoots = new NBTTagCompound();
                armor_ironBoots.set("Slot", new NBTTagString("feet"));
                armor_ironBoots.set("AttributeName", new NBTTagString("generic.armor"));
                armor_ironBoots.set("Name", new NBTTagString("generic.armor"));
                armor_ironBoots.set("Amount", new NBTTagInt(ironBoots_armor /*Armour*/));
                armor_ironBoots.set("Operation", new NBTTagInt(0));
                armor_ironBoots.set("UUIDLeast", new NBTTagInt(322453));
                armor_ironBoots.set("UUIDMost", new NBTTagInt(323));
                modifiers_ironBoots.add(armor_ironBoots);

                NBTTagCompound moveSpd_ironBoots = new NBTTagCompound();
                moveSpd_ironBoots.set("Slot", new NBTTagString("feet"));
                moveSpd_ironBoots.set("AttributeName", new NBTTagString("generic.movementSpeed"));
                moveSpd_ironBoots.set("Name", new NBTTagString("generic.movementSpeed"));
                moveSpd_ironBoots.set("Amount", new NBTTagFloat(ironBoots_moveSpd /*Movement Speed*/));
                moveSpd_ironBoots.set("Operation", new NBTTagInt(1));
                moveSpd_ironBoots.set("UUIDLeast", new NBTTagInt(234345));
                moveSpd_ironBoots.set("UUIDMost", new NBTTagInt(2122));
                modifiers_ironBoots.add(moveSpd_ironBoots);

                compound_ironBoots.set("AttributeModifiers", modifiers_ironBoots);
                nmsStack_ironBoots.setTag(compound_ironBoots);
                i_ironBoots = CraftItemStack.asBukkitCopy(nmsStack_ironBoots);
                return i_ironBoots;
            case IRON_CHESTPLATE:
                ItemStack i_ironChestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_ironChestplate = CraftItemStack.asNMSCopy(i_ironChestplate);
                NBTTagCompound compound_ironChestplate = nmsStack_ironChestplate.getTag();
                if (compound_ironChestplate == null) {
                    compound_ironChestplate = new NBTTagCompound();
                    nmsStack_ironChestplate.setTag(compound_ironChestplate);
                    compound_ironChestplate = nmsStack_ironChestplate.getTag();
                }
                NBTTagList modifiers_ironChestplate = new NBTTagList();
                int ironChestplate_armor = 6;
                float ironChestplate_moveSpd = -0.030f;

                NBTTagCompound armor_ironChestplate = new NBTTagCompound();
                armor_ironChestplate.set("Slot", new NBTTagString("chest"));
                armor_ironChestplate.set("AttributeName", new NBTTagString("generic.armor"));
                armor_ironChestplate.set("Name", new NBTTagString("generic.armor"));
                armor_ironChestplate.set("Amount", new NBTTagInt(ironChestplate_armor /*Armour*/));
                armor_ironChestplate.set("Operation", new NBTTagInt(0));
                armor_ironChestplate.set("UUIDLeast", new NBTTagInt(322453));
                armor_ironChestplate.set("UUIDMost", new NBTTagInt(323));
                modifiers_ironChestplate.add(armor_ironChestplate);

                NBTTagCompound moveSpd_ironChestplate = new NBTTagCompound();
                moveSpd_ironChestplate.set("Slot", new NBTTagString("chest"));
                moveSpd_ironChestplate.set("AttributeName", new NBTTagString("generic.movementSpeed"));
                moveSpd_ironChestplate.set("Name", new NBTTagString("generic.movementSpeed"));
                moveSpd_ironChestplate.set("Amount", new NBTTagFloat(ironChestplate_moveSpd /*Movement Speed*/));
                moveSpd_ironChestplate.set("Operation", new NBTTagInt(1));
                moveSpd_ironChestplate.set("UUIDLeast", new NBTTagInt(234345));
                moveSpd_ironChestplate.set("UUIDMost", new NBTTagInt(2122));
                modifiers_ironChestplate.add(moveSpd_ironChestplate);

                compound_ironChestplate.set("AttributeModifiers", modifiers_ironChestplate);
                nmsStack_ironChestplate.setTag(compound_ironChestplate);
                i_ironChestplate = CraftItemStack.asBukkitCopy(nmsStack_ironChestplate);
                return i_ironChestplate;
            case IRON_LEGGINGS:
                //Iron Leggings
                ItemStack i_ironLeggings = new ItemStack(Material.IRON_LEGGINGS, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_ironLeggings = CraftItemStack.asNMSCopy(i_ironLeggings);
                NBTTagCompound compound_ironLeggings = nmsStack_ironLeggings.getTag();
                if (compound_ironLeggings == null) {
                    compound_ironLeggings = new NBTTagCompound();
                    nmsStack_ironLeggings.setTag(compound_ironLeggings);
                    compound_ironLeggings = nmsStack_ironLeggings.getTag();
                }
                NBTTagList modifiers_ironLeggings = new NBTTagList();
                int ironLeggings_armor = 5;
                float ironLeggings_moveSpd = -0.030f;

                NBTTagCompound armor_ironLeggings = new NBTTagCompound();
                armor_ironLeggings.set("Slot", new NBTTagString("legs"));
                armor_ironLeggings.set("AttributeName", new NBTTagString("generic.armor"));
                armor_ironLeggings.set("Name", new NBTTagString("generic.armor"));
                armor_ironLeggings.set("Amount", new NBTTagInt(ironLeggings_armor /*Armour*/));
                armor_ironLeggings.set("Operation", new NBTTagInt(0));
                armor_ironLeggings.set("UUIDLeast", new NBTTagInt(322453));
                armor_ironLeggings.set("UUIDMost", new NBTTagInt(323));
                modifiers_ironLeggings.add(armor_ironLeggings);

                NBTTagCompound moveSpd_ironLeggings = new NBTTagCompound();
                moveSpd_ironLeggings.set("Slot", new NBTTagString("legs"));
                moveSpd_ironLeggings.set("AttributeName", new NBTTagString("generic.movementSpeed"));
                moveSpd_ironLeggings.set("Name", new NBTTagString("generic.movementSpeed"));
                moveSpd_ironLeggings.set("Amount", new NBTTagFloat(ironLeggings_moveSpd /*Movement Speed*/));
                moveSpd_ironLeggings.set("Operation", new NBTTagInt(1));
                moveSpd_ironLeggings.set("UUIDLeast", new NBTTagInt(234345));
                moveSpd_ironLeggings.set("UUIDMost", new NBTTagInt(2122));
                modifiers_ironLeggings.add(moveSpd_ironLeggings);

                compound_ironLeggings.set("AttributeModifiers", modifiers_ironLeggings);
                nmsStack_ironLeggings.setTag(compound_ironLeggings);
                i_ironLeggings = CraftItemStack.asBukkitCopy(nmsStack_ironLeggings);
                return i_ironLeggings;
            case IRON_HELMET:
                //Iron Helmet
                ItemStack i_ironHelmet = new ItemStack(Material.IRON_HELMET, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_ironHelmet = CraftItemStack.asNMSCopy(i_ironHelmet);
                NBTTagCompound compound_ironHelmet = nmsStack_ironHelmet.getTag();
                if (compound_ironHelmet == null) {
                    compound_ironHelmet = new NBTTagCompound();
                    nmsStack_ironHelmet.setTag(compound_ironHelmet);
                    compound_ironHelmet = nmsStack_ironHelmet.getTag();
                }
                NBTTagList modifiers_ironHelmet = new NBTTagList();
                int ironHelmet_armor = 2;
                float ironHelmet_moveSpd = -0.020f;

                NBTTagCompound armor_ironHelmet = new NBTTagCompound();
                armor_ironHelmet.set("Slot", new NBTTagString("head"));
                armor_ironHelmet.set("AttributeName", new NBTTagString("generic.armor"));
                armor_ironHelmet.set("Name", new NBTTagString("generic.armor"));
                armor_ironHelmet.set("Amount", new NBTTagInt(ironHelmet_armor /*Armour*/));
                armor_ironHelmet.set("Operation", new NBTTagInt(0));
                armor_ironHelmet.set("UUIDLeast", new NBTTagInt(322453));
                armor_ironHelmet.set("UUIDMost", new NBTTagInt(323));
                modifiers_ironHelmet.add(armor_ironHelmet);

                NBTTagCompound moveSpd_ironHelmet = new NBTTagCompound();
                moveSpd_ironHelmet.set("Slot", new NBTTagString("head"));
                moveSpd_ironHelmet.set("AttributeName", new NBTTagString("generic.movementSpeed"));
                moveSpd_ironHelmet.set("Name", new NBTTagString("generic.movementSpeed"));
                moveSpd_ironHelmet.set("Amount", new NBTTagFloat(ironHelmet_moveSpd /*Movement Speed*/));
                moveSpd_ironHelmet.set("Operation", new NBTTagInt(1));
                moveSpd_ironHelmet.set("UUIDLeast", new NBTTagInt(234345));
                moveSpd_ironHelmet.set("UUIDMost", new NBTTagInt(2122));
                modifiers_ironHelmet.add(moveSpd_ironHelmet);

                compound_ironHelmet.set("AttributeModifiers", modifiers_ironHelmet);
                nmsStack_ironHelmet.setTag(compound_ironHelmet);
                i_ironHelmet = CraftItemStack.asBukkitCopy(nmsStack_ironHelmet);
                return i_ironHelmet;
            case DIAMOND_BOOTS:
                //Diamond Boots
                ItemStack i_diamondBoots = new ItemStack(Material.DIAMOND_BOOTS, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_diamondBoots = CraftItemStack.asNMSCopy(i_diamondBoots);
                NBTTagCompound compound_diamondBoots = nmsStack_diamondBoots.getTag();
                if (compound_diamondBoots == null) {
                    compound_diamondBoots = new NBTTagCompound();
                    nmsStack_diamondBoots.setTag(compound_diamondBoots);
                    compound_diamondBoots = nmsStack_diamondBoots.getTag();
                }
                NBTTagList modifiers_diamondBoots = new NBTTagList();
                int diamondBoots_armor = 3;
                float diamondBoots_moveSpd = -0.020f;

                NBTTagCompound armor_diamondBoots = new NBTTagCompound();
                armor_diamondBoots.set("Slot", new NBTTagString("feet"));
                armor_diamondBoots.set("AttributeName", new NBTTagString("generic.armor"));
                armor_diamondBoots.set("Name", new NBTTagString("generic.armor"));
                armor_diamondBoots.set("Amount", new NBTTagInt(diamondBoots_armor /*Armour*/));
                armor_diamondBoots.set("Operation", new NBTTagInt(0));
                armor_diamondBoots.set("UUIDLeast", new NBTTagInt(322453));
                armor_diamondBoots.set("UUIDMost", new NBTTagInt(323));
                modifiers_diamondBoots.add(armor_diamondBoots);

                NBTTagCompound moveSpd_diamondBoots = new NBTTagCompound();
                moveSpd_diamondBoots.set("Slot", new NBTTagString("feet"));
                moveSpd_diamondBoots.set("AttributeName", new NBTTagString("generic.movementSpeed"));
                moveSpd_diamondBoots.set("Name", new NBTTagString("generic.movementSpeed"));
                moveSpd_diamondBoots.set("Amount", new NBTTagFloat(diamondBoots_moveSpd /*Movement Speed*/));
                moveSpd_diamondBoots.set("Operation", new NBTTagInt(1));
                moveSpd_diamondBoots.set("UUIDLeast", new NBTTagInt(234345));
                moveSpd_diamondBoots.set("UUIDMost", new NBTTagInt(2122));
                modifiers_diamondBoots.add(moveSpd_diamondBoots);

                compound_diamondBoots.set("AttributeModifiers", modifiers_diamondBoots);
                nmsStack_diamondBoots.setTag(compound_diamondBoots);
                i_diamondBoots = CraftItemStack.asBukkitCopy(nmsStack_diamondBoots);
                return i_diamondBoots;
            case DIAMOND_CHESTPLATE:
                //Diamond Chestplate
                ItemStack i_diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_diamondChestplate = CraftItemStack.asNMSCopy(i_diamondChestplate);
                NBTTagCompound compound_diamondChestplate = nmsStack_diamondChestplate.getTag();
                if (compound_diamondChestplate == null) {
                    compound_diamondChestplate = new NBTTagCompound();
                    nmsStack_diamondChestplate.setTag(compound_diamondChestplate);
                    compound_diamondChestplate = nmsStack_diamondChestplate.getTag();
                }
                NBTTagList modifiers_diamondChestplate = new NBTTagList();
                int diamondChestplate_armor = 8;
                float diamondChestplate_moveSpd = -0.030f;

                NBTTagCompound armor_diamondChestplate = new NBTTagCompound();
                armor_diamondChestplate.set("Slot", new NBTTagString("chest"));
                armor_diamondChestplate.set("AttributeName", new NBTTagString("generic.armor"));
                armor_diamondChestplate.set("Name", new NBTTagString("generic.armor"));
                armor_diamondChestplate.set("Amount", new NBTTagInt(diamondChestplate_armor /*Armour*/));
                armor_diamondChestplate.set("Operation", new NBTTagInt(0));
                armor_diamondChestplate.set("UUIDLeast", new NBTTagInt(322453));
                armor_diamondChestplate.set("UUIDMost", new NBTTagInt(323));
                modifiers_diamondChestplate.add(armor_diamondChestplate);

                NBTTagCompound moveSpd_diamondChestplate = new NBTTagCompound();
                moveSpd_diamondChestplate.set("Slot", new NBTTagString("chest"));
                moveSpd_diamondChestplate.set("AttributeName", new NBTTagString("generic.movementSpeed"));
                moveSpd_diamondChestplate.set("Name", new NBTTagString("generic.movementSpeed"));
                moveSpd_diamondChestplate.set("Amount", new NBTTagFloat(diamondChestplate_moveSpd /*Movement Speed*/));
                moveSpd_diamondChestplate.set("Operation", new NBTTagInt(1));
                moveSpd_diamondChestplate.set("UUIDLeast", new NBTTagInt(234345));
                moveSpd_diamondChestplate.set("UUIDMost", new NBTTagInt(2122));
                modifiers_diamondChestplate.add(moveSpd_diamondChestplate);

                compound_diamondChestplate.set("AttributeModifiers", modifiers_diamondChestplate);
                nmsStack_diamondChestplate.setTag(compound_diamondChestplate);
                i_diamondChestplate = CraftItemStack.asBukkitCopy(nmsStack_diamondChestplate);
                return i_diamondChestplate;
            case DIAMOND_LEGGINGS:
                ItemStack i_diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_diamondLeggings = CraftItemStack.asNMSCopy(i_diamondLeggings);
                NBTTagCompound compound_diamondLeggings = nmsStack_diamondLeggings.getTag();
                if (compound_diamondLeggings == null) {
                    compound_diamondLeggings = new NBTTagCompound();
                    nmsStack_diamondLeggings.setTag(compound_diamondLeggings);
                    compound_diamondLeggings = nmsStack_diamondLeggings.getTag();
                }
                NBTTagList modifiers_diamondLeggings = new NBTTagList();
                int diamondLeggings_armor = 6;
                float diamondLeggings_moveSpd = -0.030f;

                NBTTagCompound armor_diamondLeggings = new NBTTagCompound();
                armor_diamondLeggings.set("Slot", new NBTTagString("legs"));
                armor_diamondLeggings.set("AttributeName", new NBTTagString("generic.armor"));
                armor_diamondLeggings.set("Name", new NBTTagString("generic.armor"));
                armor_diamondLeggings.set("Amount", new NBTTagInt(diamondLeggings_armor /*Armour*/));
                armor_diamondLeggings.set("Operation", new NBTTagInt(0));
                armor_diamondLeggings.set("UUIDLeast", new NBTTagInt(322453));
                armor_diamondLeggings.set("UUIDMost", new NBTTagInt(323));
                modifiers_diamondLeggings.add(armor_diamondLeggings);

                NBTTagCompound moveSpd_diamondLeggings = new NBTTagCompound();
                moveSpd_diamondLeggings.set("Slot", new NBTTagString("legs"));
                moveSpd_diamondLeggings.set("AttributeName", new NBTTagString("generic.movementSpeed"));
                moveSpd_diamondLeggings.set("Name", new NBTTagString("generic.movementSpeed"));
                moveSpd_diamondLeggings.set("Amount", new NBTTagFloat(diamondLeggings_moveSpd /*Movement Speed*/));
                moveSpd_diamondLeggings.set("Operation", new NBTTagInt(1));
                moveSpd_diamondLeggings.set("UUIDLeast", new NBTTagInt(234345));
                moveSpd_diamondLeggings.set("UUIDMost", new NBTTagInt(2122));
                modifiers_diamondLeggings.add(moveSpd_diamondLeggings);

                compound_diamondLeggings.set("AttributeModifiers", modifiers_diamondLeggings);
                nmsStack_diamondLeggings.setTag(compound_diamondLeggings);
                i_diamondLeggings = CraftItemStack.asBukkitCopy(nmsStack_diamondLeggings);
                return i_diamondLeggings;

            case DIAMOND_HELMET:
                ItemStack i_diamondHelmet = new ItemStack(Material.DIAMOND_HELMET, 1);

                net.minecraft.server.v1_13_R2.ItemStack nmsStack_diamondHelmet = CraftItemStack.asNMSCopy(i_diamondHelmet);
                NBTTagCompound compound_diamondHelmet = nmsStack_diamondHelmet.getTag();
                if (compound_diamondHelmet == null) {
                    compound_diamondHelmet = new NBTTagCompound();
                    nmsStack_diamondHelmet.setTag(compound_diamondHelmet);
                    compound_diamondHelmet = nmsStack_diamondHelmet.getTag();
                }
                NBTTagList modifiers_diamondHelmet = new NBTTagList();
                int diamondHelmet_armor = 3;
                float diamondHelmet_moveSpd = -0.020f;

                NBTTagCompound armor_diamondHelmet = new NBTTagCompound();
                armor_diamondHelmet.set("Slot", new NBTTagString("head"));
                armor_diamondHelmet.set("AttributeName", new NBTTagString("generic.armor"));
                armor_diamondHelmet.set("Name", new NBTTagString("generic.armor"));
                armor_diamondHelmet.set("Amount", new NBTTagInt(diamondHelmet_armor /*Armour*/));
                armor_diamondHelmet.set("Operation", new NBTTagInt(0));
                armor_diamondHelmet.set("UUIDLeast", new NBTTagInt(322453));
                armor_diamondHelmet.set("UUIDMost", new NBTTagInt(323));
                modifiers_diamondHelmet.add(armor_diamondHelmet);

                NBTTagCompound moveSpd_diamondHelmet = new NBTTagCompound();
                moveSpd_diamondHelmet.set("Slot", new NBTTagString("head"));
                moveSpd_diamondHelmet.set("AttributeName", new NBTTagString("generic.movementSpeed"));
                moveSpd_diamondHelmet.set("Name", new NBTTagString("generic.movementSpeed"));
                moveSpd_diamondHelmet.set("Amount", new NBTTagFloat(diamondHelmet_moveSpd /*Movement Speed*/));
                moveSpd_diamondHelmet.set("Operation", new NBTTagInt(1));
                moveSpd_diamondHelmet.set("UUIDLeast", new NBTTagInt(234345));
                moveSpd_diamondHelmet.set("UUIDMost", new NBTTagInt(2122));
                modifiers_diamondHelmet.add(moveSpd_diamondHelmet);

                compound_diamondHelmet.set("AttributeModifiers", modifiers_diamondHelmet);
                nmsStack_diamondHelmet.setTag(compound_diamondHelmet);
                i_diamondHelmet = CraftItemStack.asBukkitCopy(nmsStack_diamondHelmet);
                return i_diamondHelmet;
            case RECURVE_BOW:
                //Recurve Bow
                ItemStack i_recurveBow = new ItemStack(Material.BOW, 1);

                ItemMeta recurveBowMeta = i_recurveBow.getItemMeta();
                recurveBowMeta.setLore(Collections.singletonList(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE +
                        Utils.getColoredString(Survival.lang.recurved)));
                recurveBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
                i_recurveBow.setItemMeta(recurveBowMeta);
                return i_recurveBow;

            default:
                return new ItemStack(Material.AIR);

        }
    }

}
