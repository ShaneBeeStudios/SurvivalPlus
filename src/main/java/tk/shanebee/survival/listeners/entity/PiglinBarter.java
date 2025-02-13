package tk.shanebee.survival.listeners.entity;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.item.Items;

import java.util.Random;

public class PiglinBarter implements Listener {

    private final boolean SLOW_ARMOR;
    private final boolean THIRST_ENABLED;
    private final boolean DROP_WATER;
    private final boolean ALT_DROPS;
    private final Random RANDOM;

    public PiglinBarter(Survival plugin) {
        Config config = plugin.getSurvivalConfig();
        this.SLOW_ARMOR = config.MECHANICS_SLOW_ARMOR;
        this.THIRST_ENABLED = config.MECHANICS_THIRST_ENABLED;
        this.DROP_WATER = config.ENTITY_MECHANICS_PIGLIN_DROP_WATER;
        this.ALT_DROPS = config.ENTITY_MECHANICS_PIGLIN_ALT_DROP;
        this.RANDOM = new Random();
    }

    @EventHandler
    private void onPiglinDrop(EntityDropItemEvent event) {
        if (event.getEntityType() != EntityType.PIGLIN) return;

        org.bukkit.entity.Item itemDrop = event.getItemDrop();
        ItemStack itemDropStack = itemDrop.getItemStack();
        Material itemDropMaterial = itemDropStack.getType();

        // If water bottle is dropped, let's change it
        if (itemDropMaterial == Material.POTION && THIRST_ENABLED && DROP_WATER) {
            PotionMeta meta = ((PotionMeta) itemDropStack.getItemMeta());
            assert meta != null;
            if (meta.getBasePotionType() == PotionType.WATER) {
                if (RANDOM.nextFloat() < 0.25f) {
                    itemDrop.setItemStack(Items.PURIFIED_WATER.getItemStack());
                } else {
                    itemDrop.setItemStack(Items.CLEAN_WATER.getItemStack());
                }
                return;
            }
        }

        // If alt drops are disabled let's get outta here
        if (!ALT_DROPS) return;

        // If slow armor is enabled let's always drop custom iron boots
        if (itemDropMaterial == Material.IRON_BOOTS && SLOW_ARMOR) {
            ItemStack boots = Items.IRON_BOOTS.getItemStack();
            boots.addEnchantment(Enchantment.SOUL_SPEED, RANDOM.nextInt(3) + 1);
            itemDrop.setItemStack(boots);
            return;
        }

        // If anything else we have some random drops
        ItemStack altItem = switch (itemDropMaterial) {
            case LEATHER -> Items.SUSPICIOUS_MEAT.getItemStack();
            case NETHER_BRICK -> Items.COFFEE_BEAN.getItemStack(RANDOM.nextInt(4) + 1);
            case GRAVEL -> Items.FIRESTRIKER.getItemStack();
            case SOUL_SAND -> Items.CAMPFIRE.getItemStack();
            case POTION -> Items.MEDIC_KIT.getItemStack();
            case SPLASH_POTION -> Items.GRAPPLING_HOOK.getItemStack();
            case ENCHANTED_BOOK -> Items.RECURVE_CROSSBOW.getItemStack();
            default -> null;
        };
        if (altItem != null && RANDOM.nextFloat() > 0.5f) {
            itemDrop.setItemStack(altItem);
        }
    }

}
