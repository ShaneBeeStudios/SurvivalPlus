package com.shanebeestudios.survival.gui;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MenuType;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.view.FurnaceView;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.item.Items;
import com.shanebeestudios.survival.util.ItemUtils;

import java.util.Random;

@SuppressWarnings("UnstableApiUsage")
public class FireStrikerGUI implements Runnable, InventoryHolder {

    public static @Nullable FireStrikerGUI create(Player player, ItemStack itemStack) {
        FireStrikerGUI fireStrikerGUI = new FireStrikerGUI(player, itemStack);
        if (fireStrikerGUI.burnTime > 0) return fireStrikerGUI;
        return null;
    }

    private final int id;
    private final FurnaceView furnaceView;
    private final Inventory inv;
    private final Player player;
    private final ItemStack firestrikerItemStack;
    private final Random random = new Random();

    private final int maxCookTime;
    private final int maxBurnTime;
    private int cookTime;
    private int burnTime;

    private FireStrikerGUI(Player player, ItemStack itemStack) {
        SurvivalPlugin plugin = SurvivalPlugin.getInstance();
        this.furnaceView = MenuType.FURNACE.create(player, ItemUtils.getItemNameComponent(itemStack));
        this.inv = this.furnaceView.getTopInventory();
        this.player = player;
        this.firestrikerItemStack = itemStack;
        this.maxCookTime = plugin.getSurvivalConfig().item_mechanics_firestriker_cook_time;
        this.cookTime = 0;

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        this.maxBurnTime = Items.FIRESTRIKER.getMaxCooks();
        this.burnTime = ItemUtils.getDurability(itemStack);
        this.id = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 1).getTaskId();
    }

    @Override
    public void run() {
        tick();
    }

    private void tick() {
        if (canCook() && canBurn()) {
            if (cookTime < maxCookTime) {
                cookTime++;
            } else {
                cook();
                burn();
                cookTime = 0;
            }
        } else {
            if (cookTime > 1) {
                cookTime -= 2;
            } else {
                cookTime = 0;
            }
            if (!canBurn()) {
                burnTime = 0;
                updateFuel();
            }
        }
        updateView();
    }

    private void updateFuel() {
        ItemStack fuel = this.inv.getItem(1);
        if (fuel != null && Items.FIRESTRIKER.is(fuel)) {
            this.burnTime = this.maxBurnTime - ItemUtils.getDurability(fuel);
        }
    }

    private boolean canBurn() {
        ItemStack fuel = inv.getItem(1);
        return fuel != null && Items.FIRESTRIKER.is(fuel) && burnTime > 0;
    }

    private void burn() {
        ItemStack fuelItemStack = this.inv.getItem(1);
        assert fuelItemStack != null;
        ItemMeta itemMeta = fuelItemStack.getItemMeta();
        int damage = ((Damageable) itemMeta).getDamage();
        damage++;
        if (damage < this.maxBurnTime) {
            ((Damageable) itemMeta).setDamage(damage);
            fuelItemStack.setItemMeta(itemMeta);
            this.inv.setItem(1, fuelItemStack);
            this.burnTime--;
        } else {
            this.inv.setItem(1, null);
            this.player.getWorld().playSound(this.player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            this.burnTime = 0;
        }
    }

    private boolean canCook() {
        ItemStack input = inv.getItem(0);
        ItemStack output = inv.getItem(2);

        if (input == null) return false; // nothing to smelt

        Material possibleOutput = getOutput(input.getType());
        if (possibleOutput == null) return false; // The input can not be smelted

        if (output == null) { //output is empty
            return true;
        }
        Material out = output.getType();
        if (output.getAmount() >= out.getMaxStackSize()) {
            return false;
        }
        return out == Material.AIR || out == possibleOutput;
    }

    private void cook() {
        ItemStack input = inv.getItem(0);
        assert input != null;
        ItemStack output = inv.getItem(2);

        Material in = input.getType();
        Material out = getOutput(in);

        if (output == null || output.getType() == Material.AIR) {
            assert out != null;
            output = new ItemStack(out);
        } else {
            output.setAmount(output.getAmount() + 1);
        }
        inv.setItem(2, output);

        int inputAmount = input.getAmount();
        if (inputAmount > 1) {
            input.setAmount(inputAmount - 1);
        } else {
            input = null;
        }
        inv.setItem(0, input);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void updateView() {
        this.furnaceView.setCookTime(this.cookTime, this.maxCookTime);
        this.furnaceView.setBurnTime(this.burnTime, 8);
    }

    public void open() {
        inv.setItem(1, this.firestrikerItemStack);
        player.openInventory(this.furnaceView);
    }

    public void close() {
        Bukkit.getScheduler().cancelTask(this.id);
        Location location = player.getEyeLocation().clone().add(0.0, -0.7, 0.0);
        drop(location, inv.getItem(0));
        drop(location, inv.getItem(1));
        drop(location, inv.getItem(2));
        inv.clear();
    }

    private void drop(Location location, ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() != Material.AIR) {
            World world = location.getWorld();
            assert world != null;
            org.bukkit.entity.Item drop = world.dropItem(location, itemStack);
            drop.setVelocity(new Vector(0, 0, 0));
        }
    }

    private Material getOutput(Material material) {
        switch (material) {
            case PORKCHOP:
                return Material.COOKED_PORKCHOP;
            case BEEF:
                return Material.COOKED_BEEF;
            case CHICKEN:
                return Material.COOKED_CHICKEN;
            case SALMON:
                return Material.COOKED_SALMON;
            case COD:
                return Material.COOKED_COD;
            case POTATO:
                return Material.BAKED_POTATO;
            case MUTTON:
                return Material.COOKED_MUTTON;
            case RABBIT:
                return Material.COOKED_RABBIT;
            case SAND:
                return Material.GLASS;
            case CLAY_BALL:
                return Material.BRICK;
        }
        if (Tag.LOGS.isTagged(material)) {
            return Material.CHARCOAL;
        }
        return null;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inv;
    }

    public FurnaceView getFurnaceView() {
        return this.furnaceView;
    }

}
