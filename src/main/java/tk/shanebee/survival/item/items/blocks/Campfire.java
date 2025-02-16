package tk.shanebee.survival.item.items.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;
import tk.shanebee.survival.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class Campfire extends Item {

    public Campfire() {
        ItemStack campfire = ItemType.CAMPFIRE.createItemStack();
        ItemMeta campfireMeta = campfire.getItemMeta();
        BlockData data = Bukkit.createBlockData("minecraft:campfire[lit=false]");
        ((BlockDataMeta) campfireMeta).setBlockData(data);
        campfire.setItemMeta(campfireMeta);
        setupDefaults("unlit_campfire", campfire);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.recipeKey, this.getItemStack());

        recipe.shape(" s ", "scs", "lll");
        recipe.setIngredient('s', Material.STICK);
        recipe.setIngredient('c', new MaterialChoice(Tag.ITEMS_COALS));
        recipe.setIngredient('l', new MaterialChoice(Tag.LOGS));
        return recipe;
    }

}
