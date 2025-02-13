package tk.shanebee.survival.item.items.food;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.SuspiciousStewEffects;
import io.papermc.paper.potion.SuspiciousEffectEntry;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.potion.PotionEffectType;
import tk.shanebee.survival.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("UnstableApiUsage")
public class SuspiciousMeat extends Item {

    private static final List<PotionEffectType> POTION_EFFECTS = new ArrayList<>();

    @SuppressWarnings("UnstableApiUsage")
    public SuspiciousMeat() {
        ItemStack itemStack = ItemType.APPLE.createItemStack();
        setupDefaults("suspicious_meat", itemStack);
    }

    @Override
    public ItemStack getItemStack() {
        // Add random effect when item is requested
        ItemStack itemStack = super.getItemStack();
        itemStack.setData(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffects.suspiciousStewEffects().add(getRandomEffect()).build());
        return itemStack;
    }

    static {
        // BAD
        POTION_EFFECTS.add(PotionEffectType.BAD_OMEN);
        POTION_EFFECTS.add(PotionEffectType.NAUSEA);
        POTION_EFFECTS.add(PotionEffectType.POISON);
        POTION_EFFECTS.add(PotionEffectType.UNLUCK);
        POTION_EFFECTS.add(PotionEffectType.HUNGER);
        POTION_EFFECTS.add(PotionEffectType.INSTANT_DAMAGE);
        POTION_EFFECTS.add(PotionEffectType.SLOWNESS);
        // GOOD
        POTION_EFFECTS.add(PotionEffectType.DOLPHINS_GRACE);
        POTION_EFFECTS.add(PotionEffectType.ABSORPTION);
        POTION_EFFECTS.add(PotionEffectType.HASTE);
        POTION_EFFECTS.add(PotionEffectType.LUCK);
        POTION_EFFECTS.add(PotionEffectType.HEALTH_BOOST);
        POTION_EFFECTS.add(PotionEffectType.REGENERATION);
        POTION_EFFECTS.add(PotionEffectType.SPEED);
    }

    private static SuspiciousEffectEntry getRandomEffect() {
        Random random = new Random();
        int randomEffect = random.nextInt(POTION_EFFECTS.size());
        int randomDuration = random.nextInt(200) + 200;
        return SuspiciousEffectEntry.create(POTION_EFFECTS.get(randomEffect), randomDuration);
    }

}
