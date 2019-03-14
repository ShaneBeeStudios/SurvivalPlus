package com.fattymieo.survival.managers;

import com.fattymieo.survival.Survival;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.*;

public class RecipeManager {

    public Map<String, String> Words;
    private NamespacedKey key;
    public FileConfiguration settings;
    private Survival survival;

    public RecipeManager(Survival survival, FileConfiguration settings, NamespacedKey key, Map<String, String> words) {
        this.survival = survival;
        this.Words = words;
        this.settings = settings;
        this.key = key;

    }

    @SuppressWarnings("deprecation")
    public void loadCustomRecipes() {
        removeRecipes();












        //Reinforced Leather Boots
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
        leatherBootsMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Reinforced Leather Boots"));

        i_leatherBoots.setItemMeta(leatherBootsMeta);

        //Reinforced Leather Tunic
        ItemStack i_leatherChestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);

        ItemMeta leatherChestplateMeta = i_leatherChestplate.getItemMeta();
        leatherChestplateMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Reinforced Leather Tunic"));

        i_leatherChestplate.setItemMeta(leatherChestplateMeta);

        //Reinforced Leather Trousers
        ItemStack i_leatherLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);

        ItemMeta leatherLeggingsMeta = i_leatherLeggings.getItemMeta();
        leatherLeggingsMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Reinforced Leather Trousers"));

        i_leatherLeggings.setItemMeta(leatherLeggingsMeta);

        //Reinforced Leather Helmet
        ItemStack i_leatherHelmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);

        ItemMeta leatherHelmetMeta = i_leatherHelmet.getItemMeta();
        leatherHelmetMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Reinforced Leather Hat"));

        i_leatherHelmet.setItemMeta(leatherHelmetMeta);

        //Golden Sabatons
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
        goldBootsMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Golden Sabatons"));
        goldBootsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_FALL, 4, true);

        i_goldBoots.setItemMeta(goldBootsMeta);

        //Golden Guard
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
        goldChestplateMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Golden Guard"));
        goldChestplateMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS, 4, true);

        i_goldChestplate.setItemMeta(goldChestplateMeta);

        //Golden Greaves
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
        goldLeggingsMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Golden Greaves"));
        goldLeggingsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS, 4, true);

        i_goldLeggings.setItemMeta(goldLeggingsMeta);

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
        goldHelmetMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Golden Crown"));
        goldHelmetMeta.addEnchant(org.bukkit.enchantments.Enchantment.MENDING, 1, true);

        i_goldHelmet.setItemMeta(goldHelmetMeta);

        //Iron Boots
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

        //Iron Chestplate
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

        //Diamond Leggings
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

        //Diamond Helmet
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





        //Recurve Bow
        ItemStack i_recurveBow = new ItemStack(Material.BOW, 1);

        ItemMeta recurveBowMeta = i_recurveBow.getItemMeta();
        recurveBowMeta.setLore(Collections.singletonList(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + Survival.getColoredLang("Recurved")));
        recurveBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
        i_recurveBow.setItemMeta(recurveBowMeta);

        //Recipes





        ShapedRecipe leatherBoots = new ShapedRecipe(new NamespacedKey(survival, "leatherboots"), i_leatherBoots);
        ShapedRecipe leatherChestplate = new ShapedRecipe(new NamespacedKey(survival, "leatherchestplate"), i_leatherChestplate);
        ShapedRecipe leatherLeggings = new ShapedRecipe(new NamespacedKey(survival, "leatherleggings"), i_leatherLeggings);
        ShapedRecipe leatherHelmet = new ShapedRecipe(new NamespacedKey(survival, "leatherhelmet"), i_leatherHelmet);
        ShapedRecipe goldBoots = new ShapedRecipe(new NamespacedKey(survival, "goldboots"), i_goldBoots);
        ShapedRecipe goldChestplate = new ShapedRecipe(new NamespacedKey(survival, "goldchestplate"), i_goldChestplate);
        ShapedRecipe goldLeggings = new ShapedRecipe(new NamespacedKey(survival, "goldleggings"), i_goldLeggings);
        ShapedRecipe goldHelmet = new ShapedRecipe(new NamespacedKey(survival, "goldhelmet"), i_goldHelmet);
        ShapedRecipe ironBoots = new ShapedRecipe(new NamespacedKey(survival, "ironboots"), i_ironBoots);
        ShapedRecipe ironChestplate = new ShapedRecipe(new NamespacedKey(survival, "ironchestplate"), i_ironChestplate);
        ShapedRecipe ironLeggings = new ShapedRecipe(new NamespacedKey(survival, "ironleggings"), i_ironLeggings);
        ShapedRecipe ironHelmet = new ShapedRecipe(new NamespacedKey(survival, "ironhelmet"), i_ironHelmet);
        ShapedRecipe diamondBoots = new ShapedRecipe(new NamespacedKey(survival, "diamondboots"), i_diamondBoots);
        ShapedRecipe diamondChestplate = new ShapedRecipe(new NamespacedKey(survival, "diamondchestplate"), i_diamondChestplate);
        ShapedRecipe diamondLeggings = new ShapedRecipe(new NamespacedKey(survival, "diamondleggings"), i_diamondLeggings);
        ShapedRecipe diamondHelmet = new ShapedRecipe(new NamespacedKey(survival, "diamondhelmet"), i_diamondHelmet);

        ShapedRecipe recurveBow1 = new ShapedRecipe(new NamespacedKey(survival, "recurvebow1"), i_recurveBow);
        ShapedRecipe recurveBow2 = new ShapedRecipe(new NamespacedKey(survival, "recurvebow2"), i_recurveBow);

        // Todo   HATCHET RECIPE
        ItemStack i_hatchet = new ItemStack(Material.WOODEN_AXE, 1);
        ItemMeta hatchetMeta = i_hatchet.getItemMeta();
        hatchetMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Hatchet"));
        i_hatchet.setItemMeta(hatchetMeta);

        ShapedRecipe hatchet1 = new ShapedRecipe(new NamespacedKey(survival, "hatchet1"), i_hatchet);
        ShapedRecipe hatchet2 = new ShapedRecipe(new NamespacedKey(survival, "hatchet2"), i_hatchet);

        hatchet1.shape("@@", " 1");

        hatchet1.setIngredient('@', Material.FLINT);
        hatchet1.setIngredient('1', Material.STICK);
        hatchet1.setGroup("HATCHET");

        hatchet2.shape("@@", "1 ");

        hatchet2.setIngredient('@', Material.FLINT);
        hatchet2.setIngredient('1', Material.STICK);
        hatchet2.setGroup("HATCHET");


        // Todo   MATTOCK RECIPE
        ItemStack i_mattock = new ItemStack(Material.WOODEN_PICKAXE, 1);
        ItemMeta mattockMeta = i_mattock.getItemMeta();
        mattockMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Mattock"));
        i_mattock.setItemMeta(mattockMeta);

        ShapedRecipe mattock1 = new ShapedRecipe(new NamespacedKey(survival, "mattock1"), i_mattock);
        ShapedRecipe mattock2 = new ShapedRecipe(new NamespacedKey(survival, "mattock2"), i_mattock);
        ShapedRecipe mattock3 = new ShapedRecipe(new NamespacedKey(survival, "mattock3"), i_mattock);
        ShapedRecipe mattock4 = new ShapedRecipe(new NamespacedKey(survival, "mattock4"), i_mattock);
        ShapedRecipe mattock5 = new ShapedRecipe(new NamespacedKey(survival, "mattock5"), i_mattock);
        ShapedRecipe mattock6 = new ShapedRecipe(new NamespacedKey(survival, "mattock6"), i_mattock);

        mattock1.shape("@-", "1@");
        mattock1.setIngredient('@', Material.FLINT);
        mattock1.setIngredient('-', Material.OAK_PLANKS);
        mattock1.setIngredient('1', Material.STICK);
        mattock1.setGroup("MATTOCK");

        mattock2.shape("@-", "1@");
        mattock2.setIngredient('@', Material.FLINT);
        mattock2.setIngredient('-', Material.BIRCH_PLANKS);
        mattock2.setIngredient('1', Material.STICK);
        mattock2.setGroup("MATTOCK");

        mattock3.shape("@-", "1@");
        mattock3.setIngredient('@', Material.FLINT);
        mattock3.setIngredient('-', Material.SPRUCE_PLANKS);
        mattock3.setIngredient('1', Material.STICK);
        mattock3.setGroup("MATTOCK");

        mattock4.shape("@-", "1@");
        mattock4.setIngredient('@', Material.FLINT);
        mattock4.setIngredient('-', Material.JUNGLE_PLANKS);
        mattock4.setIngredient('1', Material.STICK);
        mattock4.setGroup("MATTOCK");

        mattock5.shape("@-", "1@");
        mattock5.setIngredient('@', Material.FLINT);
        mattock5.setIngredient('-', Material.ACACIA_PLANKS);
        mattock5.setIngredient('1', Material.STICK);
        mattock5.setGroup("MATTOCK");

        mattock6.shape("@-", "1@");
        mattock6.setIngredient('@', Material.FLINT);
        mattock6.setIngredient('-', Material.DARK_OAK_PLANKS);
        mattock6.setIngredient('1', Material.STICK);
        mattock6.setGroup("MATTOCK");

        // Todo   SHIV RECIPE
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
        shivMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Shiv"));
        shivMeta.setLore
                (
                        Arrays.asList
                                (
                                        ChatColor.RESET + "" + Survival.getColoredLang("Poisoned: Poison enemy when hit"),
                                        "",
                                        ChatColor.RESET + "" + ChatColor.GRAY + Survival.getColoredLang("When in main hand:"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + shiv_spd + " " + Survival.getColoredLang("Attack Speed"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + shiv_dmg + " " + Survival.getColoredLang("Attack Damage"),
                                        "",
                                        ChatColor.RESET + "" + ChatColor.GRAY + Survival.getColoredLang("When in off hand:"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Poisoning Effect retains"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Reduce chance by 50%")
                                )
                );
        i_shiv.setItemMeta(shivMeta);

        ShapedRecipe shiv1 = new ShapedRecipe(new NamespacedKey(survival, "shiv1"), i_shiv);
        ShapedRecipe shiv2 = new ShapedRecipe(new NamespacedKey(survival, "shiv2"), i_shiv);
        ShapedRecipe shiv3 = new ShapedRecipe(new NamespacedKey(survival, "shiv3"), i_shiv);
        ShapedRecipe shiv4 = new ShapedRecipe(new NamespacedKey(survival, "shiv4"), i_shiv);

        shiv1.shape("*@", "1&");

        shiv1.setIngredient('@', Material.FLINT);
        shiv1.setIngredient('1', Material.STICK);
        shiv1.setIngredient('*', Material.STRING);
        shiv1.setIngredient('&', Material.SPIDER_EYE);
        shiv1.setGroup("SHIV");

        shiv2.shape("@*", "&1");

        shiv2.setIngredient('@', Material.FLINT);
        shiv2.setIngredient('1', Material.STICK);
        shiv2.setIngredient('*', Material.STRING);
        shiv2.setIngredient('&', Material.SPIDER_EYE);
        shiv2.setGroup("SHIV");

        shiv3.shape("&@", "1*");

        shiv3.setIngredient('@', Material.FLINT);
        shiv3.setIngredient('1', Material.STICK);
        shiv3.setIngredient('*', Material.STRING);
        shiv3.setIngredient('&', Material.SPIDER_EYE);
        shiv3.setGroup("SHIV");

        shiv4.shape("@&", "*1");

        shiv4.setIngredient('@', Material.FLINT);
        shiv4.setIngredient('1', Material.STICK);
        shiv4.setIngredient('*', Material.STRING);
        shiv4.setIngredient('&', Material.SPIDER_EYE);
        shiv4.setGroup("SHIV");

        // Todo   HAMMER RECIPE
        ItemStack i_hammer = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta hammerMeta = i_hammer.getItemMeta();
        hammerMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Hammer"));
        i_hammer.setItemMeta(hammerMeta);

        ShapedRecipe hammer1 = new ShapedRecipe(new NamespacedKey(survival, "hammer1"), i_hammer);
        ShapedRecipe hammer2 = new ShapedRecipe(new NamespacedKey(survival, "hammer2"), i_hammer);

        hammer1.shape("@ ", "1@");

        hammer1.setIngredient('@', Material.COBBLESTONE);
        hammer1.setIngredient('1', Material.STICK);
        hammer1.setGroup("HAMMER");

        hammer2.shape(" @", "@1");

        hammer2.setIngredient('@', Material.COBBLESTONE);
        hammer2.setIngredient('1', Material.STICK);
        hammer2.setGroup("HAMMER");

        // Todo   STRING RECIPE
        ShapelessRecipe string = new ShapelessRecipe(new NamespacedKey(survival, "string"), new ItemStack(Material.STRING, 2));
        string.addIngredient(Material.COBWEB);

        // Todo   VALKYRIE's AXE RECIPE
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
        gAxeMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + Survival.getColoredLang("Valkyrie's Axe"));
        gAxeMeta.setLore
                (
                        Arrays.asList
                                (
                                        ChatColor.RESET + "" + Survival.getColoredLang("Unable to dual-wield with Valkyrie's Axe"),
                                        "",
                                        ChatColor.RESET + "" + ChatColor.GRAY + Survival.getColoredLang("When in main hand:"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + gAxe_spd + " " + Survival.getColoredLang("Attack Speed"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + gAxe_dmg + " " + Survival.getColoredLang("Attack Damage"),
                                        ChatColor.RESET + "" + "  " + Survival.getColoredLang("Spin: Spin your axe in circle, attack all nearby enemies"),
                                        ChatColor.RESET + "" + "  " + Survival.getColoredLang("Cooldown: 1 second"),
                                        ChatColor.RESET + "" + "  " + Survival.getColoredLang("Decreases hunger value")
                                )
                );
        gAxeMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
        i_gAxe.setItemMeta(gAxeMeta);
        ShapedRecipe gAxe = new ShapedRecipe(new NamespacedKey(survival, "gaxe"), i_gAxe);

        gAxe.shape("@@@", "@*@", " 1 ");

        gAxe.setIngredient('@', Material.DIAMOND);
        gAxe.setIngredient('*', Material.NETHER_STAR);
        gAxe.setIngredient('1', Material.STICK);

        // Todo   QUARTZ PICKAXE RECIPE
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
        gPickaxeMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + Survival.getColoredLang("Quartz Breaker"));
        gPickaxeMeta.setLore
                (
                        Arrays.asList
                                (
                                        "",
                                        ChatColor.RESET + "" + ChatColor.GRAY + Survival.getColoredLang("When in main hand:"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + gPickaxe_spd + " " + Survival.getColoredLang("Attack Speed"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + gPickaxe_dmg + " " + Survival.getColoredLang("Attack Damage"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Haste")
                                )
                );
        gPickaxeMeta.addEnchant(org.bukkit.enchantments.Enchantment.SILK_TOUCH, 1, false);
        i_gPickaxe.setItemMeta(gPickaxeMeta);

        ShapedRecipe gPickaxe1 = new ShapedRecipe(new NamespacedKey(survival, "gpickaxe1"), i_gPickaxe);
        ShapedRecipe gPickaxe2 = new ShapedRecipe(new NamespacedKey(survival, "gpickaxe2"), i_gPickaxe);
        gPickaxe1.shape("@B-", "B# ", "- 1");

        gPickaxe1.setIngredient('@', Material.QUARTZ_BLOCK);
        gPickaxe1.setIngredient('-', Material.DIAMOND);
        gPickaxe1.setIngredient('B', Material.DIAMOND_BLOCK);
        gPickaxe1.setIngredient('1', Material.STICK);
        gPickaxe1.setIngredient('#', Material.DRAGON_EGG);
        gPickaxe1.setGroup("QUARTZ PICKAXE");

        gPickaxe2.shape("-B@", " #B", "1 -");

        gPickaxe2.setIngredient('@', Material.QUARTZ_BLOCK);
        gPickaxe2.setIngredient('-', Material.DIAMOND);
        gPickaxe2.setIngredient('B', Material.DIAMOND_BLOCK);
        gPickaxe2.setIngredient('1', Material.STICK);
        gPickaxe2.setIngredient('#', Material.DRAGON_EGG);
        gPickaxe2.setGroup("QUARTZ PICKAXE");


        // Todo    OBSIDIAN MACE RECIPE
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
        gSpadeMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + Survival.getColoredLang("Obsidian Mace"));
        gSpadeMeta.setLore
                (
                        Arrays.asList
                                (
                                        ChatColor.RESET + "" + Survival.getColoredLang("Cripple: Enemies hit become weakened"),
                                        ChatColor.RESET + "" + Survival.getColoredLang("Drain: Gains 2 hearts per hit"),
                                        "",
                                        ChatColor.RESET + "" + ChatColor.GRAY + Survival.getColoredLang("When in main hand:"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + gSpade_spd + " " + Survival.getColoredLang("Attack Speed"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + gSpade_dmg + " " + Survival.getColoredLang("Attack Damage"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Exhausted: Slowness II"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Expires after disarming for 5 seconds"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("+50% Knockback Resistance")
                                )
                );
        gSpadeMeta.addEnchant(org.bukkit.enchantments.Enchantment.KNOCKBACK, 3, true);
        gSpadeMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
        i_gSpade.setItemMeta(gSpadeMeta);

        ShapedRecipe gSpade1 = new ShapedRecipe(new NamespacedKey(survival, "gspade1"), i_gSpade);
        ShapedRecipe gSpade2 = new ShapedRecipe(new NamespacedKey(survival, "gspade2"), i_gSpade);

        gSpade1.shape(" @@", " &@", "1  ");

        gSpade1.setIngredient('@', Material.OBSIDIAN);
        gSpade1.setIngredient('&', Material.END_CRYSTAL);
        gSpade1.setIngredient('1', Material.STICK);
        gSpade1.setGroup("OBSIDIAN MACE");

        gSpade2.shape("@@ ", "@& ", "  1");

        gSpade2.setIngredient('@', Material.OBSIDIAN);
        gSpade2.setIngredient('&', Material.END_CRYSTAL);
        gSpade2.setIngredient('1', Material.STICK);
        gSpade2.setGroup("OBSIDIAN MACE");


        // Todo   ENDER GIANT BLADE RECIPE
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
        gHoeMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + Survival.getColoredLang("Ender Giant Blade"));
        gHoeMeta.setLore
                (
                        Arrays.asList
                                (
                                        ChatColor.RESET + "" + Survival.getColoredLang("Unable to dual-wield with Giant Blade"),
                                        "",
                                        ChatColor.RESET + "" + ChatColor.GRAY + Survival.getColoredLang("When in main hand:"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + gHoe_spd + " " + Survival.getColoredLang("Attack Speed"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + gHoe_dmg + " " + Survival.getColoredLang("Attack Damage"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + Survival.getColoredLang("Right Click when sprinting:"),
                                        ChatColor.RESET + "" + "  " + Survival.getColoredLang("Charge: Sprint forward, attack enemies infront"),
                                        ChatColor.RESET + "" + "  " + Survival.getColoredLang("Cooldown: 5 seconds"),
                                        ChatColor.RESET + "" + "  " + Survival.getColoredLang("Decreases hunger value"),
                                        "",
                                        ChatColor.RESET + "" + ChatColor.GRAY + Survival.getColoredLang("When in off hand:"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Half-Shield: Gains Resistance II"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Reflecting incoming damage by 40%")
                                )
                );
        gHoeMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
        i_gHoe.setItemMeta(gHoeMeta);

        ShapedRecipe gHoe1 = new ShapedRecipe(new NamespacedKey(survival, "ghoe1"), i_gHoe);
        ShapedRecipe gHoe2 = new ShapedRecipe(new NamespacedKey(survival, "ghoe2"), i_gHoe);

        gHoe1.shape(" @@", "B*@", "1B ");

        gHoe1.setIngredient('*', Material.ENDER_EYE);
        gHoe1.setIngredient('@', Material.DIAMOND);
        gHoe1.setIngredient('B', Material.DIAMOND_BLOCK);
        gHoe1.setIngredient('1', Material.OAK_PLANKS);
        gHoe1.setGroup("ENDER GIANT BLADE");

        gHoe2.shape("@@ ", "@*B", " B1");

        gHoe2.setIngredient('*', Material.ENDER_EYE);
        gHoe2.setIngredient('@', Material.DIAMOND);
        gHoe2.setIngredient('B', Material.DIAMOND_BLOCK);
        gHoe2.setIngredient('1', Material.OAK_PLANKS);
        gHoe2.setGroup("ENDER GIANT BLADE");


        // Todo    BLAZE SWORD RECIPE
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
        gSwordMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + Survival.getColoredLang("Blaze Sword"));
        gSwordMeta.setLore
                (
                        Arrays.asList
                                (
                                        "",
                                        ChatColor.RESET + "" + ChatColor.GRAY + Survival.getColoredLang("When in main hand:"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + gSword_spd + " " + Survival.getColoredLang("Attack Speed"),
                                        ChatColor.RESET + "" + ChatColor.GRAY + " " + gSword_dmg + " " + Survival.getColoredLang("Attack Damage"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Fire Resistance"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Fiery: -3 Hearts"),
                                        "",
                                        ChatColor.RESET + "" + ChatColor.GRAY + Survival.getColoredLang("Right Click when sneaking:"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Spread fire on the ground"),
                                        ChatColor.RESET + "" + " " + Survival.getColoredLang("Costs 1 Durability")
                                )
                );
        gSwordMeta.addEnchant(org.bukkit.enchantments.Enchantment.FIRE_ASPECT, 2, true);
        i_gSword.setItemMeta(gSwordMeta);

        ShapedRecipe gSword = new ShapedRecipe(new NamespacedKey(survival, "gsword"), i_gSword);
        gSword.shape("*@*", "*@*", "*1*");

        gSword.setIngredient('@', Material.GOLD_INGOT);
        gSword.setIngredient('1', Material.BLAZE_ROD);
        gSword.setIngredient('*', Material.BLAZE_POWDER);


        // Todo    NOTCH APPLE RECIPE
        ShapedRecipe notchApple = new ShapedRecipe(new NamespacedKey(survival, "notchapple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1));
        notchApple.shape("@@@", "@*@", "@@@");

        notchApple.setIngredient('@', Material.GOLD_BLOCK);
        notchApple.setIngredient('*', Material.APPLE);


        // Todo    SADDLE RECIPE
        ShapedRecipe saddle = new ShapedRecipe(new NamespacedKey(survival, "saddle"), new ItemStack(Material.SADDLE, 1));

        saddle.shape("@@@", "*-*", "= =");

        saddle.setIngredient('@', Material.LEATHER);
        saddle.setIngredient('*', Material.LEAD);
        saddle.setIngredient('-', Material.IRON_INGOT);
        saddle.setIngredient('=', Material.IRON_NUGGET);


        // Todo    NAMETAG RECIPE
        ShapedRecipe nametag1 = new ShapedRecipe(new NamespacedKey(survival, "nametag1"), new ItemStack(Material.NAME_TAG, 1));
        ShapedRecipe nametag2 = new ShapedRecipe(new NamespacedKey(survival, "nametag2"), new ItemStack(Material.NAME_TAG, 1));

        nametag1.shape(" -@", " *-", "*  ");

        nametag1.setIngredient('@', Material.STRING);
        nametag1.setIngredient('-', Material.IRON_INGOT);
        nametag1.setIngredient('*', Material.PAPER);
        nametag1.setGroup("NAMETAG");

        nametag2.shape("@- ", "-* ", "  *");

        nametag2.setIngredient('@', Material.STRING);
        nametag2.setIngredient('-', Material.IRON_INGOT);
        nametag2.setIngredient('*', Material.PAPER);
        nametag2.setGroup("NAMETAG");


        // Todo    PACKED ICE RECIPE
        ShapedRecipe packedIce1 = new ShapedRecipe(new NamespacedKey(survival, "packedice1"), new ItemStack(Material.PACKED_ICE, 1));
        ShapelessRecipe packedIce2 = new ShapelessRecipe(new NamespacedKey(survival, "packedice2"), new ItemStack(Material.ICE, 4));

        packedIce1.shape("@@ ", "@@ ");

        packedIce1.setIngredient('@', Material.ICE);

        packedIce2.addIngredient(Material.PACKED_ICE);

        // Todo    ICE RECIPE
        ShapedRecipe ice = new ShapedRecipe(new NamespacedKey(survival, "ice"), new ItemStack(Material.ICE, 1));

        ice.shape("@@@", "@*@", "@@@");

        ice.setIngredient('@', Material.SNOWBALL);
        ice.setIngredient('*', Material.WATER_BUCKET);


        // Todo    IRON HORSE ARMOR RECIPE
        ShapedRecipe ironHorse1 = new ShapedRecipe(new NamespacedKey(survival, "ironhorse1"), new ItemStack(Material.IRON_HORSE_ARMOR, 1));
        ShapedRecipe ironHorse2 = new ShapedRecipe(new NamespacedKey(survival, "ironhorse2"), new ItemStack(Material.IRON_HORSE_ARMOR, 1));

        ironHorse1.shape("  @", "#-#", "= =");

        ironHorse1.setIngredient('#', Material.IRON_BLOCK);
        ironHorse1.setIngredient('@', Material.IRON_INGOT);
        ironHorse1.setIngredient('-', Material.SADDLE);
        ironHorse1.setIngredient('=', Material.IRON_NUGGET);
        ironHorse1.setGroup("IRON HORSE ARMOR");

        ironHorse2.shape("@  ", "#-#", "= =");

        ironHorse2.setIngredient('#', Material.IRON_BLOCK);
        ironHorse2.setIngredient('@', Material.IRON_INGOT);
        ironHorse2.setIngredient('-', Material.SADDLE);
        ironHorse2.setIngredient('=', Material.IRON_NUGGET);
        ironHorse2.setGroup("IRON HORSE ARMOR");


        // Todo    GOLD HORSE ARMOR RECIPE
        ShapedRecipe goldHorse1 = new ShapedRecipe(new NamespacedKey(survival, "goldhorse1"), new ItemStack(Material.GOLDEN_HORSE_ARMOR, 1));
        ShapedRecipe goldHorse2 = new ShapedRecipe(new NamespacedKey(survival, "goldhorse2"), new ItemStack(Material.GOLDEN_HORSE_ARMOR, 1));

        goldHorse1.shape("  @", "#-#", "= =");

        goldHorse1.setIngredient('#', Material.GOLD_BLOCK);
        goldHorse1.setIngredient('@', Material.GOLD_INGOT);
        goldHorse1.setIngredient('-', Material.SADDLE);
        goldHorse1.setIngredient('=', Material.GOLD_NUGGET);
        goldHorse1.setGroup("GOLD HORSE ARMOR");

        goldHorse2.shape("@  ", "#-#", "= =");

        goldHorse2.setIngredient('#', Material.GOLD_BLOCK);
        goldHorse2.setIngredient('@', Material.GOLD_INGOT);
        goldHorse2.setIngredient('-', Material.SADDLE);
        goldHorse2.setIngredient('=', Material.GOLD_NUGGET);
        goldHorse2.setGroup("GOLD HORSE ARMOR");


        // Todo    DIAMOND HORSE ARMOR RECIPE
        ShapedRecipe diamondHorse1 = new ShapedRecipe(new NamespacedKey(survival, "diamondhorse1"), new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1));
        ShapedRecipe diamondHorse2 = new ShapedRecipe(new NamespacedKey(survival, "diamondhorse2"), new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1));

        diamondHorse1.shape("  H", "@-@", "B B");

        diamondHorse1.setIngredient('@', Material.DIAMOND);
        diamondHorse1.setIngredient('-', Material.IRON_HORSE_ARMOR);
        diamondHorse1.setIngredient('H', Material.DIAMOND_HELMET);
        diamondHorse1.setIngredient('B', Material.DIAMOND_BOOTS);
        diamondHorse1.setGroup("DIAMOND HORSE ARMOR");

        diamondHorse2.shape("H  ", "@-@", "B B");

        diamondHorse2.setIngredient('@', Material.DIAMOND);
        diamondHorse2.setIngredient('-', Material.IRON_HORSE_ARMOR);
        diamondHorse2.setIngredient('H', Material.DIAMOND_HELMET);
        diamondHorse2.setIngredient('B', Material.DIAMOND_BOOTS);
        diamondHorse2.setGroup("DIAMOND HORSE ARMOR");


        // Todo    CLAY BRICK RECIPE
        ShapelessRecipe clayBrick = new ShapelessRecipe(new NamespacedKey(survival, "claybrick"), new ItemStack(Material.BRICK, 4));

        clayBrick.addIngredient(Material.BRICK);


        // Todo    QUARTZ BLOCK RECIPE
        ShapelessRecipe quartz = new ShapelessRecipe(new NamespacedKey(survival, "quartz"), new ItemStack(Material.QUARTZ, 4));

        quartz.addIngredient(Material.QUARTZ_BLOCK);


        // Todo    STRING RECIPE
        ShapelessRecipe woolString = new ShapelessRecipe(new NamespacedKey(survival, "woolstring"), new ItemStack(Material.STRING, 4));

        woolString.addIngredient(new Wool(DyeColor.WHITE));


        // Todo    REPAIR RECIPE
        ShapelessRecipe repair_gSword = new ShapelessRecipe(new NamespacedKey(survival, "repair_gsword"), i_gSword);
        repair_gSword.addIngredient(Material.GOLDEN_SWORD);
        repair_gSword.addIngredient(2, Material.BLAZE_POWDER);


        // Todo    REPAIR RECIPE
        ShapelessRecipe repair_gHoe = new ShapelessRecipe(new NamespacedKey(survival, "repair_ghoe"), i_gHoe);

        repair_gHoe.addIngredient(Material.GOLDEN_HOE);
        repair_gHoe.addIngredient(Material.ENDER_PEARL);
        repair_gHoe.addIngredient(Material.DIAMOND_BLOCK);


        // Todo    REPAIR RECIPE
        ShapelessRecipe repair_gPickaxe = new ShapelessRecipe(new NamespacedKey(survival, "repair_gpickaxe"), i_gPickaxe);

        repair_gPickaxe.addIngredient(Material.GOLDEN_PICKAXE);
        repair_gPickaxe.addIngredient(1, Material.QUARTZ_BLOCK);
        repair_gPickaxe.addIngredient(1, Material.DIAMOND_BLOCK);


        // Todo    REPAIR RECIPE
        ShapelessRecipe repair_gAxe = new ShapelessRecipe(new NamespacedKey(survival, "repair_gaxe"), i_gAxe);

        repair_gAxe.addIngredient(Material.GOLDEN_AXE);
        repair_gAxe.addIngredient(Material.NETHER_STAR);


        // Todo    REPAIR RECIPE
        ShapelessRecipe repair_gSpade = new ShapelessRecipe(new NamespacedKey(survival, "repair_gspade"), i_gSpade);
        repair_gSpade.addIngredient(Material.GOLDEN_SHOVEL, -1);
        repair_gSpade.addIngredient(1, Material.END_CRYSTAL);


        // Todo WORKBENCH RECIPE
        ItemStack workbench = new ItemStack(Material.CRAFTING_TABLE, 1);
        ItemMeta workbenchMeta = workbench.getItemMeta();
        workbenchMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Workbench"));
        workbench.setItemMeta(workbenchMeta);

        ShapelessRecipe workbench1 = new ShapelessRecipe(new NamespacedKey(survival, "workbench1"), workbench);

        workbench1.addIngredient(Material.OAK_LOG);
        workbench1.addIngredient(Material.LEATHER);
        workbench1.addIngredient(Material.STRING);
        workbench1.addIngredient(Material.WOODEN_SWORD);


        // Todo    FURNACE RECIPE
        ShapedRecipe furnace = new ShapedRecipe(new NamespacedKey(survival, "furnace"), new ItemStack(Material.FURNACE, 1));

        furnace.shape("@@@", "@*@", "@@@");

        furnace.setIngredient('@', Material.BRICK);
        furnace.setIngredient('*', Material.WOODEN_SHOVEL);


        // Todo    CHEST RECIPE
        ShapedRecipe chest = new ShapedRecipe(new NamespacedKey(survival, "chest"), new ItemStack(Material.CHEST, 1));

        chest.shape("@@@", "@#@", "@@@");

        chest.setIngredient('@', Material.OAK_LOG);
        chest.setIngredient('#', Material.IRON_INGOT);


        // Todo    CLAY RECIPE
        ShapelessRecipe clay = new ShapelessRecipe(new NamespacedKey(survival, "clay"), new ItemStack(Material.CLAY, 1));

        clay.addIngredient(Material.DIRT);
        clay.addIngredient(Material.SAND);
        clay.addIngredient(Material.BEETROOT_SOUP);


        // Todo    DIORITE RECIPE
        ShapelessRecipe diorite = new ShapelessRecipe(new NamespacedKey(survival, "diorite"), new ItemStack(Material.DIORITE, 1));

        diorite.addIngredient(Material.BONE_MEAL); // TODO Change in 1.14 -> WHITE_DYE
        diorite.addIngredient(Material.COBBLESTONE);


        // Todo    GRANITE RECIPE
        ShapelessRecipe granite = new ShapelessRecipe(new NamespacedKey(survival, "granite"), new ItemStack(Material.GRANITE, 1));

        granite.addIngredient(Material.NETHERRACK);
        granite.addIngredient(Material.COBBLESTONE);


        // Todo    ANDESITE RECIPE
        ShapelessRecipe andesite = new ShapelessRecipe(new NamespacedKey(survival, "andesite"), new ItemStack(Material.ANDESITE, 1));

        andesite.addIngredient(Material.GRAVEL);
        andesite.addIngredient(Material.COBBLESTONE);


        // Todo    GRAVEL RECIPE
        ShapedRecipe gravel1 = new ShapedRecipe(new NamespacedKey(survival, "gravel1"), new ItemStack(Material.GRAVEL, 2));
        ShapedRecipe gravel2 = new ShapedRecipe(new NamespacedKey(survival, "gravel2"), new ItemStack(Material.GRAVEL, 2));

        gravel1.shape("@B", "B@");

        gravel1.setIngredient('@', Material.SAND);
        gravel1.setIngredient('B', Material.COBBLESTONE);
        gravel1.setGroup("GRAVEL");

        gravel2.shape("B@", "@B");

        gravel2.setIngredient('@', Material.SAND);
        gravel2.setIngredient('B', Material.COBBLESTONE);
        gravel2.setGroup("GRAVEL");


        // Todo    FIRESSTRIKER RECIPE
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
        firestrikerMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Survival.getColoredLang("Firestriker")));
        i_firestriker.setItemMeta(firestrikerMeta);

        ShapelessRecipe firestriker1 = new ShapelessRecipe(new NamespacedKey(survival, "firestriker1"), i_firestriker);
        ShapelessRecipe firestriker2 = new ShapelessRecipe(new NamespacedKey(survival, "firestriker2"), i_firestriker);

        firestriker1.addIngredient(Material.FLINT);
        firestriker1.addIngredient(Material.COAL);
        firestriker1.setGroup("FIRESTRIKER");

        firestriker2.addIngredient(Material.FLINT);
        firestriker2.addIngredient(Material.CHARCOAL);
        firestriker2.setGroup("FIRESTRIKER");


        // Todo    TORCH RECIPE
        ShapedRecipe torch1 = new ShapedRecipe(new NamespacedKey(survival, "torch1"), new ItemStack(Material.TORCH, 8));
        ShapedRecipe torch2 = new ShapedRecipe(new NamespacedKey(survival, "torch2"), new ItemStack(Material.TORCH, 16));
        ShapedRecipe torch3 = new ShapedRecipe(new NamespacedKey(survival, "torch3"), new ItemStack(Material.TORCH, 16));

        torch1.shape("AAA", "ABA", "AAA");
        torch1.setIngredient('B', new RecipeChoice.ExactChoice(i_firestriker));
        torch1.setIngredient('A', Material.STICK);
        torch1.setGroup("TORCH");

        torch2.shape("ACA", "ABA", "AAA");
        torch2.setIngredient('C', Material.COAL);
        torch2.setIngredient('B', new RecipeChoice.ExactChoice(i_firestriker));
        torch2.setIngredient('A', Material.STICK);
        torch2.setGroup("TORCH");

        torch3.shape("ACA", "ABA", "AAA");
        torch3.setIngredient('C', Material.CHARCOAL);
        torch3.setIngredient('B', new RecipeChoice.ExactChoice(i_firestriker));
        torch3.setIngredient('A', Material.STICK);
        torch3.setGroup("TORCH");


        // Todo    FLINT RECIPE
        ShapelessRecipe flint = new ShapelessRecipe(new NamespacedKey(survival, "flint"), new ItemStack(Material.FLINT, 1));

        flint.addIngredient(Material.GRAVEL);


        // Todo    FERMENTED SPIDER EYE RECIPE
        ShapelessRecipe fermentedSpiderEye1 = new ShapelessRecipe(new NamespacedKey(survival, "fermentedspidereye1"),
                new ItemStack(Material.FERMENTED_SPIDER_EYE, 1));
        ShapelessRecipe fermentedSpiderEye2 = new ShapelessRecipe(new NamespacedKey(survival, "fermentedspidereye2"),
                new ItemStack(Material.FERMENTED_SPIDER_EYE, 1));

        fermentedSpiderEye1.addIngredient(Material.SPIDER_EYE);
        fermentedSpiderEye1.addIngredient(Material.SUGAR);
        fermentedSpiderEye1.addIngredient(Material.RED_MUSHROOM);
        fermentedSpiderEye1.setGroup("FERMENTED SPIDER EYE");

        fermentedSpiderEye2.addIngredient(Material.SPIDER_EYE);
        fermentedSpiderEye2.addIngredient(Material.SUGAR);
        fermentedSpiderEye2.addIngredient(Material.RED_MUSHROOM);
        fermentedSpiderEye2.setGroup("FERMENTED SPIDER EYE");


        // Todo    FERMENTED SKIN RECIPE
        ItemStack i_fermentedSkin = new ItemStack(Material.RABBIT_HIDE, 1);
        ItemMeta fermentedSkinMeta = i_fermentedSkin.getItemMeta();
        fermentedSkinMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Fermented Skin"));
        i_fermentedSkin.setItemMeta(fermentedSkinMeta);

        ShapelessRecipe fermentedSkin1 = new ShapelessRecipe(new NamespacedKey(survival, "fermentedskin1"), i_fermentedSkin);
        ShapelessRecipe fermentedSkin2 = new ShapelessRecipe(new NamespacedKey(survival, "fermentedskin2"), i_fermentedSkin);

        fermentedSkin1.addIngredient(Material.ROTTEN_FLESH);
        fermentedSkin1.addIngredient(Material.SUGAR);
        fermentedSkin1.addIngredient(Material.BROWN_MUSHROOM);
        fermentedSkin1.setGroup("FERMENTED SKIN");

        fermentedSkin2.addIngredient(Material.ROTTEN_FLESH);
        fermentedSkin2.addIngredient(Material.SUGAR);
        fermentedSkin2.addIngredient(Material.RED_MUSHROOM);
        fermentedSkin2.setGroup("FERMENTED SKIN");


        // Todo    POISONOUS POTATO RECIPE
        ShapelessRecipe poisonousPotato = new ShapelessRecipe(new NamespacedKey(survival, "poisonouspotato"),
                new ItemStack(Material.POISONOUS_POTATO, 1));

        poisonousPotato.addIngredient(Material.POTATO);
        poisonousPotato.addIngredient(Material.BONE_MEAL); // TODO Add WHITE_DYE recipe in 1.14


        // Todo    GLASS BOTTLE RECIPE
        ShapelessRecipe glassBottle = new ShapelessRecipe(new NamespacedKey(survival, "glassbottle"), new ItemStack(Material.GLASS_BOTTLE, 1));

        glassBottle.addIngredient(Material.POTION);


        // Todo    BOWL RECIPE
        ShapelessRecipe bowl = new ShapelessRecipe(new NamespacedKey(survival, "bowl"), new ItemStack(Material.BOWL, 1));

        bowl.addIngredient(Material.BEETROOT_SOUP);


        // Todo    MEDIC KIT RECIPE
        ItemStack i_medicKit = new ItemStack(Material.CLOCK, 1);
        ItemMeta medicKitMeta = i_medicKit.getItemMeta();
        medicKitMeta.setDisplayName(ChatColor.RESET + Survival.getColoredLang("Medical Kit"));
        i_medicKit.setItemMeta(medicKitMeta);

        ShapedRecipe medicKit1 = new ShapedRecipe(new NamespacedKey(survival, "medickit1"), i_medicKit);
        ShapedRecipe medicKit2 = new ShapedRecipe(new NamespacedKey(survival, "medickit2"), i_medicKit);
        ShapedRecipe medicKit3 = new ShapedRecipe(new NamespacedKey(survival, "medickit3"), i_medicKit);
        ShapedRecipe medicKit4 = new ShapedRecipe(new NamespacedKey(survival, "medickit4"), i_medicKit);
        ShapedRecipe medicKit5 = new ShapedRecipe(new NamespacedKey(survival, "medickit5"), i_medicKit);
        ShapedRecipe medicKit6 = new ShapedRecipe(new NamespacedKey(survival, "medickit6"), i_medicKit);

        medicKit1.shape(" @ ", "ABC", " @ ");

        medicKit1.setIngredient('@', Material.GOLD_INGOT);
        medicKit1.setIngredient('A', Material.FEATHER);
        medicKit1.setIngredient('B', Material.GLISTERING_MELON_SLICE);
        medicKit1.setIngredient('C', Material.PAPER);
        medicKit1.setGroup("MEDIC KIT");

        medicKit2.shape(" @ ", "ACB", " @ ");

        medicKit2.setIngredient('@', Material.GOLD_INGOT);
        medicKit2.setIngredient('A', Material.FEATHER);
        medicKit2.setIngredient('B', Material.GLISTERING_MELON_SLICE);
        medicKit2.setIngredient('C', Material.PAPER);
        medicKit2.setGroup("MEDIC KIT");

        medicKit3.shape(" @ ", "BAC", " @ ");

        medicKit3.setIngredient('@', Material.GOLD_INGOT);
        medicKit3.setIngredient('A', Material.FEATHER);
        medicKit3.setIngredient('B', Material.GLISTERING_MELON_SLICE);
        medicKit3.setIngredient('C', Material.PAPER);
        medicKit3.setGroup("MEDIC KIT");

        medicKit4.shape(" @ ", "BCA", " @ ");

        medicKit4.setIngredient('@', Material.GOLD_INGOT);
        medicKit4.setIngredient('A', Material.FEATHER);
        medicKit4.setIngredient('B', Material.GLISTERING_MELON_SLICE);
        medicKit4.setIngredient('C', Material.PAPER);
        medicKit4.setGroup("MEDIC KIT");

        medicKit5.shape(" @ ", "CAB", " @ ");

        medicKit5.setIngredient('@', Material.GOLD_INGOT);
        medicKit5.setIngredient('A', Material.FEATHER);
        medicKit5.setIngredient('B', Material.GLISTERING_MELON_SLICE);
        medicKit5.setIngredient('C', Material.PAPER);
        medicKit5.setGroup("MEDIC KIT");

        medicKit6.shape(" @ ", "CBA", " @ ");

        medicKit6.setIngredient('@', Material.GOLD_INGOT);
        medicKit6.setIngredient('A', Material.FEATHER);
        medicKit6.setIngredient('B', Material.GLISTERING_MELON_SLICE);
        medicKit6.setIngredient('C', Material.PAPER);
        medicKit6.setGroup("MEDIC KIT");


        // Todo    FISHING ROD RECIPE
        ShapedRecipe fishingRod1 = new ShapedRecipe(new NamespacedKey(survival, "fishingrod1"), new ItemStack(Material.FISHING_ROD, 1));
        ShapedRecipe fishingRod2 = new ShapedRecipe(new NamespacedKey(survival, "fishingrod2"), new ItemStack(Material.FISHING_ROD, 1));

        fishingRod1.shape("1- ", "1 -", "1@*");

        fishingRod1.setIngredient('1', Material.STICK);
        fishingRod1.setIngredient('@', Material.IRON_INGOT);
        fishingRod1.setIngredient('-', Material.STRING);
        fishingRod1.setIngredient('*', Material.FEATHER);
        fishingRod1.setGroup("FISHING ROD");

        fishingRod2.shape(" -1", "- 1", "*@1");

        fishingRod2.setIngredient('1', Material.STICK);
        fishingRod2.setIngredient('@', Material.IRON_INGOT);
        fishingRod2.setIngredient('-', Material.STRING);
        fishingRod2.setIngredient('*', Material.FEATHER);
        fishingRod2.setGroup("FISHING ROD");


        // Todo    IRON INGOT RECIPE
        ShapedRecipe ironIngot = new ShapedRecipe(new NamespacedKey(survival, "ironingot"), new ItemStack(Material.IRON_INGOT, 1));
        ironIngot.shape("@@", "@@");
        ironIngot.setIngredient('@', Material.IRON_NUGGET);

        // Todo    IRON NUGGET RECIPE
        ShapelessRecipe ironNugget = new ShapelessRecipe(new NamespacedKey(survival, "ironnugget"), new ItemStack(Material.IRON_NUGGET, 4));
        ironNugget.addIngredient(Material.IRON_INGOT);

        // Todo    IRON BLOCK RECIPE
        ShapelessRecipe ironBlock = new ShapelessRecipe(new NamespacedKey(survival, "ironblock"), new ItemStack(Material.IRON_INGOT, 9));
        ironBlock.addIngredient(Material.IRON_BLOCK);

        // Todo    GOLD INGOT RECIPE
        ShapedRecipe goldIngot = new ShapedRecipe(new NamespacedKey(survival, "goldingot"), new ItemStack(Material.GOLD_INGOT, 1));
        goldIngot.shape("@@", "@@");
        goldIngot.setIngredient('@', Material.GOLD_NUGGET);

        // Todo    GOLD NUGGET RECIPE
        ShapelessRecipe goldNugget = new ShapelessRecipe(new NamespacedKey(survival, "goldnugget"), new ItemStack(Material.GOLD_NUGGET, 4));
        goldNugget.addIngredient(Material.GOLD_INGOT);

        // Todo    GOLD BLOCK RECIPE
        ShapelessRecipe goldBlock = new ShapelessRecipe(new NamespacedKey(survival, "goldblock"), new ItemStack(Material.GOLD_INGOT, 9));
        goldBlock.addIngredient(Material.GOLD_BLOCK);

        // Todo    SMELTING RECIPES (what the ferk, why is this even here?)
        org.bukkit.inventory.FurnaceRecipe smelt_ironIngot = new org.bukkit.inventory.FurnaceRecipe(
                new ItemStack(Material.IRON_INGOT, 1), Material.IRON_ORE);
        org.bukkit.inventory.FurnaceRecipe smelt_goldIngot = new org.bukkit.inventory.FurnaceRecipe(
                new ItemStack(Material.GOLD_INGOT, 1), Material.GOLD_ORE);


        // Todo    BREAD RECIPE
        ShapedRecipe bread = new ShapedRecipe(new NamespacedKey(survival, "bread"), new ItemStack(Material.BREAD, 2));

        bread.shape(" E ", "WWW");

        bread.setIngredient('E', Material.EGG);
        bread.setIngredient('W', Material.WHEAT);


        // Todo    COOKIE RECIPE
        ShapedRecipe cookie = new ShapedRecipe(new NamespacedKey(survival, "cookie"), new ItemStack(Material.COOKIE, 8));

        cookie.shape(" E ", "WCW", " S ");

        cookie.setIngredient('E', Material.EGG);
        cookie.setIngredient('W', Material.WHEAT);
        cookie.setIngredient('S', Material.SUGAR);
        cookie.setIngredient('C', Material.COCOA_BEANS);


        // Todo    SLIME BALL RECIPE
        ShapelessRecipe slimeball = new ShapelessRecipe(new NamespacedKey(survival, "slimeball"), new ItemStack(Material.SLIME_BALL, 1));

        slimeball.addIngredient(Material.MILK_BUCKET);
        slimeball.addIngredient(8, Material.VINE);


        // Todo    COBWEB RECIPE
        ShapelessRecipe cobweb = new ShapelessRecipe(new NamespacedKey(survival, "cobweb"), new ItemStack(Material.COBWEB, 1));

        cobweb.addIngredient(Material.SLIME_BALL);
        cobweb.addIngredient(2, Material.STRING);


        // Todo    SAPLING RECIPE
        ShapelessRecipe sapling1 = new ShapelessRecipe(new NamespacedKey(survival, "sapling1"), new ItemStack(Material.STICK, 4));
        ShapelessRecipe sapling2 = new ShapelessRecipe(new NamespacedKey(survival, "sapling2"), new ItemStack(Material.STICK, 4));
        ShapelessRecipe sapling3 = new ShapelessRecipe(new NamespacedKey(survival, "sapling3"), new ItemStack(Material.STICK, 4));
        ShapelessRecipe sapling4 = new ShapelessRecipe(new NamespacedKey(survival, "sapling4"), new ItemStack(Material.STICK, 4));
        ShapelessRecipe sapling5 = new ShapelessRecipe(new NamespacedKey(survival, "sapling5"), new ItemStack(Material.STICK, 4));
        ShapelessRecipe sapling6 = new ShapelessRecipe(new NamespacedKey(survival, "sapling6"), new ItemStack(Material.STICK, 4));

        sapling1.addIngredient(Material.OAK_SAPLING);
        sapling1.setGroup("STICK");
        sapling2.addIngredient(Material.BIRCH_SAPLING);
        sapling2.setGroup("STICK");
        sapling3.addIngredient(Material.SPRUCE_SAPLING);
        sapling3.setGroup("STICK");
        sapling4.addIngredient(Material.JUNGLE_SAPLING);
        sapling4.setGroup("STICK");
        sapling5.addIngredient(Material.ACACIA_SAPLING);
        sapling5.setGroup("STICK");
        sapling6.addIngredient(Material.DARK_OAK_SAPLING);
        sapling6.setGroup("STICK");



        leatherBoots.shape("@*@");

        leatherBoots.setIngredient('@', Material.IRON_INGOT);
        leatherBoots.setIngredient('*', Material.LEATHER_BOOTS);

        leatherChestplate.shape(" @ ", "@*@", " @ ");

        leatherChestplate.setIngredient('@', Material.IRON_INGOT);
        leatherChestplate.setIngredient('*', Material.LEATHER_CHESTPLATE);

        leatherLeggings.shape(" @ ", "@*@", " @ ");

        leatherLeggings.setIngredient('@', Material.IRON_INGOT);
        leatherLeggings.setIngredient('*', Material.LEATHER_LEGGINGS);

        leatherHelmet.shape("@*@");

        leatherHelmet.setIngredient('@', Material.IRON_INGOT);
        leatherHelmet.setIngredient('*', Material.LEATHER_HELMET);

        goldBoots.shape("@ @", "@ @");

        goldBoots.setIngredient('@', Material.GOLD_INGOT);

        goldChestplate.shape("@ @", "@@@", "@@@");

        goldChestplate.setIngredient('@', Material.GOLD_INGOT);

        goldLeggings.shape("@@@", "@ @", "@ @");

        goldLeggings.setIngredient('@', Material.GOLD_INGOT);

        goldHelmet.shape("@*@", "@@@");

        goldHelmet.setIngredient('@', Material.GOLD_INGOT);
        goldHelmet.setIngredient('*', Material.EMERALD);

        ironBoots.shape("@ @", "@ @");

        ironBoots.setIngredient('@', Material.IRON_INGOT);

        ironChestplate.shape("@ @", "@@@", "@@@");

        ironChestplate.setIngredient('@', Material.IRON_INGOT);

        ironLeggings.shape("@@@", "@ @", "@ @");

        ironLeggings.setIngredient('@', Material.IRON_INGOT);

        ironHelmet.shape("@@@", "@ @");

        ironHelmet.setIngredient('@', Material.IRON_INGOT);

        diamondBoots.shape("@ @", "@ @");

        diamondBoots.setIngredient('@', Material.DIAMOND);

        diamondChestplate.shape("@ @", "@@@", "@@@");

        diamondChestplate.setIngredient('@', Material.DIAMOND);

        diamondLeggings.shape("@@@", "@ @", "@ @");

        diamondLeggings.setIngredient('@', Material.DIAMOND);

        diamondHelmet.shape("@@@", "@ @");

        diamondHelmet.setIngredient('@', Material.DIAMOND);

        recurveBow1.shape(" @1", "#^1", " @1");

        recurveBow1.setIngredient('^', Material.BOW);
        recurveBow1.setIngredient('#', Material.PISTON);
        recurveBow1.setIngredient('@', Material.IRON_INGOT);
        recurveBow1.setIngredient('1', Material.STRING);

        recurveBow2.shape("1@ ", "1^#", "1@ ");

        recurveBow2.setIngredient('^', Material.BOW);
        recurveBow2.setIngredient('#', Material.OAK_LOG);
        recurveBow2.setIngredient('@', Material.IRON_INGOT);
        recurveBow2.setIngredient('1', Material.STRING);

        //Add recipes
        if (settings.getBoolean("Survival.Enabled")) {
            survival.getServer().addRecipe(hatchet1);
            survival.getServer().addRecipe(hatchet2);
            survival.getServer().addRecipe(mattock1);
            survival.getServer().addRecipe(mattock2);
            survival.getServer().addRecipe(mattock3);
            survival.getServer().addRecipe(mattock4);
            survival.getServer().addRecipe(mattock5);
            survival.getServer().addRecipe(mattock6);
            survival.getServer().addRecipe(shiv1);
            survival.getServer().addRecipe(shiv2);
            survival.getServer().addRecipe(shiv3);
            survival.getServer().addRecipe(shiv4);
            survival.getServer().addRecipe(hammer1);
            survival.getServer().addRecipe(hammer2);
            survival.getServer().addRecipe(firestriker1);
            survival.getServer().addRecipe(firestriker2);
            survival.getServer().addRecipe(workbench1);
            survival.getServer().addRecipe(furnace);
            survival.getServer().addRecipe(chest);
            survival.getServer().addRecipe(flint);
        }
        if (settings.getBoolean("Survival.Torch")) {
            survival.getServer().addRecipe(torch1);
            survival.getServer().addRecipe(torch2);
            survival.getServer().addRecipe(torch3);
        }
        if (settings.getBoolean("Recipes.WebString"))
            survival.getServer().addRecipe(string);
        if (settings.getBoolean("Recipes.SaplingToSticks")) {
            survival.getServer().addRecipe(sapling1);
            survival.getServer().addRecipe(sapling2);
            survival.getServer().addRecipe(sapling3);
            survival.getServer().addRecipe(sapling4);
            survival.getServer().addRecipe(sapling5);
            survival.getServer().addRecipe(sapling6);
        }

        if (settings.getBoolean("LegendaryItems.ValkyrieAxe")) {
            survival.getServer().addRecipe(gAxe);
            if (settings.getBoolean("LegendaryItems.CanRepair"))
                survival.getServer().addRecipe(repair_gAxe);
        }
        if (settings.getBoolean("LegendaryItems.QuartzPickaxe")) {
            survival.getServer().addRecipe(gPickaxe1);
            survival.getServer().addRecipe(gPickaxe2);
            if (settings.getBoolean("LegendaryItems.CanRepair"))
                survival.getServer().addRecipe(repair_gPickaxe);
        }
        if (settings.getBoolean("LegendaryItems.ObsidianMace")) {
            survival.getServer().addRecipe(gSpade1);
            survival.getServer().addRecipe(gSpade2);
            if (settings.getBoolean("LegendaryItems.CanRepair"))
                survival.getServer().addRecipe(repair_gSpade);
        }
        if (settings.getBoolean("LegendaryItems.GiantBlade")) {
            survival.getServer().addRecipe(gHoe1);
            survival.getServer().addRecipe(gHoe2);
            if (settings.getBoolean("LegendaryItems.CanRepair"))
                survival.getServer().addRecipe(repair_gHoe);
        }
        if (settings.getBoolean("LegendaryItems.BlazeSword")) {
            survival.getServer().addRecipe(gSword);
            if (settings.getBoolean("LegendaryItems.CanRepair"))
                survival.getServer().addRecipe(repair_gSword);
        }
        if (settings.getBoolean("LegendaryItems.NotchApple"))
            survival.getServer().addRecipe(notchApple);
        if (settings.getBoolean("Recipes.Saddle"))
            survival.getServer().addRecipe(saddle);
        if (settings.getBoolean("Recipes.Nametag")) {
            survival.getServer().addRecipe(nametag1);
            survival.getServer().addRecipe(nametag2);
        }
        if (settings.getBoolean("Recipes.PackedIce")) {
            survival.getServer().addRecipe(packedIce1);
            survival.getServer().addRecipe(packedIce2);
        }
        if (settings.getBoolean("Recipes.IronBard")) {
            survival.getServer().addRecipe(ironHorse1);
            survival.getServer().addRecipe(ironHorse2);
        }
        if (settings.getBoolean("Recipes.GoldBard")) {
            survival.getServer().addRecipe(goldHorse1);
            survival.getServer().addRecipe(goldHorse2);
        }
        if (settings.getBoolean("Recipes.DiamondBard")) {
            survival.getServer().addRecipe(diamondHorse1);
            survival.getServer().addRecipe(diamondHorse2);
        }
        if (settings.getBoolean("Recipes.ClayBrick"))
            survival.getServer().addRecipe(clayBrick);
        if (settings.getBoolean("Recipes.QuartzBlock"))
            survival.getServer().addRecipe(quartz);
        if (settings.getBoolean("Recipes.WoolString"))
            survival.getServer().addRecipe(woolString);
        if (settings.getBoolean("Recipes.Ice"))
            survival.getServer().addRecipe(ice);
        if (settings.getBoolean("Recipes.Clay"))
            survival.getServer().addRecipe(clay);
        if (settings.getBoolean("Recipes.Diorite"))
            survival.getServer().addRecipe(diorite);
        if (settings.getBoolean("Recipes.Granite"))
            survival.getServer().addRecipe(granite);
        if (settings.getBoolean("Recipes.Andesite"))
            survival.getServer().addRecipe(andesite);
        if (settings.getBoolean("Recipes.Gravel")) {
            survival.getServer().addRecipe(gravel1);
            survival.getServer().addRecipe(gravel2);
        }
        if (settings.getBoolean("Mechanics.RedMushroomFermentation")) {
            survival.getServer().addRecipe(fermentedSpiderEye1);
            survival.getServer().addRecipe(fermentedSpiderEye2);
        }
        if (settings.getBoolean("Mechanics.FermentedSkin")) {
            survival.getServer().addRecipe(fermentedSkin1);
            if (settings.getBoolean("Mechanics.RedMushroomFermentation"))
                survival.getServer().addRecipe(fermentedSkin2);
        }
        if (settings.getBoolean("Mechanics.PoisonousPotato"))
            survival.getServer().addRecipe(poisonousPotato);
        if (settings.getBoolean("Mechanics.EmptyPotions")) {
            survival.getServer().addRecipe(glassBottle);
            survival.getServer().addRecipe(bowl);
        }
        if (settings.getBoolean("Mechanics.ReinforcedLeatherArmor")) {
            survival.getServer().addRecipe(leatherBoots);
            survival.getServer().addRecipe(leatherChestplate);
            survival.getServer().addRecipe(leatherLeggings);
            survival.getServer().addRecipe(leatherHelmet);
        }
        if (settings.getBoolean("LegendaryItems.GoldArmorBuff")) {
            survival.getServer().addRecipe(goldBoots);
            survival.getServer().addRecipe(goldChestplate);
            survival.getServer().addRecipe(goldLeggings);
            survival.getServer().addRecipe(goldHelmet);
        }

        if (settings.getBoolean("Mechanics.SlowArmor")) {
            survival.getServer().addRecipe(ironBoots);
            survival.getServer().addRecipe(ironChestplate);
            survival.getServer().addRecipe(ironLeggings);
            survival.getServer().addRecipe(ironHelmet);
            survival.getServer().addRecipe(diamondBoots);
            survival.getServer().addRecipe(diamondChestplate);
            survival.getServer().addRecipe(diamondLeggings);
            survival.getServer().addRecipe(diamondHelmet);
        }

        if (settings.getBoolean("Mechanics.MedicalKit")) {
            survival.getServer().addRecipe(medicKit1);
            survival.getServer().addRecipe(medicKit2);
            survival.getServer().addRecipe(medicKit3);
            survival.getServer().addRecipe(medicKit4);
            survival.getServer().addRecipe(medicKit5);
            survival.getServer().addRecipe(medicKit6);
        }

        if (settings.getBoolean("Recipes.FishingRod")) {
            survival.getServer().addRecipe(fishingRod1);
            survival.getServer().addRecipe(fishingRod2);
        }

        if (settings.getBoolean("Mechanics.ReducedIronNugget")) {
            survival.getServer().addRecipe(ironNugget);
            survival.getServer().addRecipe(ironIngot);
            survival.getServer().addRecipe(ironBlock);
            survival.getServer().addRecipe(smelt_ironIngot);
        }

        if (settings.getBoolean("Mechanics.ReducedGoldNugget")) {
            survival.getServer().addRecipe(goldNugget);
            survival.getServer().addRecipe(goldIngot);
            survival.getServer().addRecipe(goldBlock);
            survival.getServer().addRecipe(smelt_goldIngot);
        }

        if (settings.getBoolean("Mechanics.FarmingProducts.Bread"))
            survival.getServer().addRecipe(bread);
        if (settings.getBoolean("Mechanics.FarmingProducts.Cookie"))
            survival.getServer().addRecipe(cookie);
        if (settings.getBoolean("Recipes.Slimeball"))
            survival.getServer().addRecipe(slimeball);
        if (settings.getBoolean("Recipes.Cobweb"))
            survival.getServer().addRecipe(cobweb);
        if (settings.getBoolean("Mechanics.RecurveBow")) {
            survival.getServer().addRecipe(recurveBow1);
            survival.getServer().addRecipe(recurveBow2);
        }
    }

    private void removeRecipes() {
        List<Recipe> backup = new ArrayList<>();

        Iterator<Recipe> a = survival.getServer().recipeIterator();

        while (a.hasNext()) {
            Recipe recipe = a.next();
            backup.add(recipe);
        }

        Iterator<Recipe> it = backup.iterator();

        while (it.hasNext()) {
            Recipe recipe = it.next();
            if (recipe != null) {
                switch (recipe.getResult().getType()) {
                    case WOODEN_HOE:
                    case WOODEN_AXE:
                    case WOODEN_PICKAXE:
                    case WOODEN_SHOVEL:
                    case WOODEN_SWORD:
                    case FURNACE:
                    case CRAFTING_TABLE:
                    case CHEST:
                    case BEETROOT_SOUP:
                        if (settings.getBoolean("Survival.Enabled"))
                            it.remove();
                        break;
                    case TORCH:
                        if (settings.getBoolean("Survival.Enabled") && settings.getBoolean("Survival.Torch"))
                            it.remove();
                        break;

                    case GOLDEN_HOE:
                        if (settings.getBoolean("LegendaryItems.GiantBlade"))
                            it.remove();
                        break;
                    case GOLDEN_AXE:
                        if (settings.getBoolean("LegendaryItems.ValkyrieAxe"))
                            it.remove();
                        break;
                    case GOLDEN_PICKAXE:
                        if (settings.getBoolean("LegendaryItems.QuartzPickaxe"))
                            it.remove();
                        break;
                    case GOLDEN_SHOVEL:
                        if (settings.getBoolean("LegendaryItems.ObsidianMace"))
                            it.remove();
                        break;
                    case GOLDEN_SWORD:
                        if (settings.getBoolean("LegendaryItems.BlazeSword"))
                            it.remove();
                        break;

                    case GOLDEN_BOOTS:
                    case GOLDEN_CHESTPLATE:
                    case GOLDEN_HELMET:
                    case GOLDEN_LEGGINGS:
                        if (settings.getBoolean("LegendaryItems.GoldArmorBuff"))
                            it.remove();
                        break;

                    case IRON_BOOTS:
                    case IRON_CHESTPLATE:
                    case IRON_HELMET:
                    case IRON_LEGGINGS:
                    case DIAMOND_BOOTS:
                    case DIAMOND_CHESTPLATE:
                    case DIAMOND_HELMET:
                    case DIAMOND_LEGGINGS:
                        if (settings.getBoolean("Mechanics.SlowArmor"))
                            it.remove();
                        break;

                    case FISHING_ROD:
                        if (settings.getBoolean("Recipes.FishingRod"))
                            it.remove();
                        break;

                    case IRON_NUGGET:
                    case IRON_INGOT:
                        if (settings.getBoolean("Mechanics.ReducedIronNugget"))
                            it.remove();
                        break;

                    case GOLD_NUGGET:
                    case GOLD_INGOT:
                        if (settings.getBoolean("Mechanics.ReducedGoldNugget"))
                            it.remove();
                        break;

                    case BREAD:
                        if (settings.getBoolean("Mechanics.FarmingProducts.Bread"))
                            it.remove();
                        break;
                    case COOKIE:
                        if (settings.getBoolean("Mechanics.FarmingProducts.Cookie"))
                            it.remove();
                        break;
                    case ANDESITE:
                        if (settings.getBoolean("Recipes.Andesite"))
                            it.remove();
                        break;
                    case DIORITE:
                        if (settings.getBoolean("Recipes.Diorite"))
                            it.remove();
                        break;
                    case GRANITE:
                        if (settings.getBoolean("Recipes.Granite"))
                            it.remove();
                        break;
                    case SNOW:
                    case SNOW_BLOCK:
                        if (settings.getBoolean("Mechanics.SnowballRevamp"))
                            it.remove();
                        break;
                    default:
                }
            }
        }

        survival.getServer().clearRecipes();

        for (Recipe r : backup) {
            survival.getServer().addRecipe(r);
        }
    }
}
