package tk.shanebee.survival.item.items.drinks;

import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect.ApplyStatusEffects;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tk.shanebee.survival.item.Items;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class HotMilk extends DrinkItem {

    public HotMilk() {
        ItemStack itemStack = ItemType.STICK.createItemStack();
        ApplyStatusEffects effects = ConsumeEffect.applyStatusEffects(List.of(
            new PotionEffect(PotionEffectType.HUNGER, 100, 0, true, false, false),
                new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 0, true, false, false)),
            1.0f);
        setupDefaults("hot_milk", itemStack, List.of(effects));
    }

    @Override
    public Recipe getRecipe() {
        return new SmokingRecipe(this.recipeKey, this.getItemStack(),
            new RecipeChoice.ExactChoice(Items.COLD_MILK.getItemStack()), 0, 200);
    }

}
