package com.shanebeestudios.survival.listeners.entity;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.config.Config;
import com.shanebeestudios.survival.item.Items;

import java.util.Random;

public class PiglinBarter implements Listener {

    private final Config config;
    private final Random random = new Random();

    public PiglinBarter(SurvivalPlugin plugin) {
        this.config = plugin.getSurvivalConfig();
    }

    @EventHandler
    private void onPiglinDrop(EntityDropItemEvent event) {
        if (event.getEntityType() != EntityType.PIGLIN) return;

        org.bukkit.entity.Item itemDrop = event.getItemDrop();
        ItemStack itemDropStack = itemDrop.getItemStack();
        Material itemDropMaterial = itemDropStack.getType();

        // If water bottle is dropped, let's change it
        if (itemDropMaterial == Material.POTION && this.config.mechanics_thirst_enabled && this.config.entity_mechanics_piglin_drop_water) {
            PotionMeta meta = ((PotionMeta) itemDropStack.getItemMeta());
            assert meta != null;
            if (meta.getBasePotionType() == PotionType.WATER) {
                if (this.random.nextFloat() < 0.25f) {
                    itemDrop.setItemStack(Items.PURIFIED_WATER.getItemStack());
                } else {
                    itemDrop.setItemStack(Items.CLEAN_WATER.getItemStack());
                }
                return;
            }
        }

        // If alt drops are disabled let's get out of here
        if (!this.config.entity_mechanics_piglin_alt_drop) return;

        // If slow armor is enabled let's always drop custom iron boots
        if (itemDropMaterial == Material.IRON_BOOTS && this.config.mechanics_slow_armor) {
            ItemStack boots = Items.IRON_BOOTS.getItemStack();
            boots.addEnchantment(Enchantment.SOUL_SPEED, this.random.nextInt(3) + 1);
            itemDrop.setItemStack(boots);
            return;
        }

        // If anything else we have some random drops

        if (this.random.nextFloat() > 0.5f) {
            ItemStack altItem = switch (itemDropMaterial) {
                case LEATHER -> Items.SUSPICIOUS_MEAT.getItemStack();
                case NETHER_BRICK -> Items.COFFEE_BEAN.getItemStack(this.random.nextInt(4) + 1);
                case GRAVEL -> Items.FIRESTRIKER.getItemStack();
                case SOUL_SAND -> Items.CAMPFIRE.getItemStack();
                case POTION -> Items.MEDIC_KIT.getItemStack();
                case SPLASH_POTION -> Items.GRAPPLING_HOOK.getItemStack();
                case ENCHANTED_BOOK -> Items.RECURVED_CROSSBOW.getItemStack();
                default -> null;
            };
            if (altItem != null) itemDrop.setItemStack(altItem);
        }
    }

}
