package tk.shanebee.survival.generator;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnstableApiUsage", "UnusedReturnValue"})
public class BlockTagFileGenerator {

    public FileConfiguration generateBlockTags(File pluginDataFolder, String path) {
        File file = new File(pluginDataFolder, path);
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<String> header = new ArrayList<>();

        header.add("Block Tags");
        header.add("This file is used to create some tags the plugin uses.");
        header.add("Modify this to your liking but be very careful when you do.");
        header.add(" ");
        header.add("This accepts both Minecraft block types `minecraft:stone`");
        header.add("and block tags prefixed with `#`, ex: `#minecraft:logs` (minecraft or custom)");
        header.add(" ");
        header.add("The names of these sections double as tags.");
        header.add("Example `requires_shovel` = `#survival_plus:requires_shovel`");
        config.options().setHeader(header);

        ConfigurationSection blocks = config.getConfigurationSection("blocks");
        if (blocks == null) blocks = config.createSection("blocks");

        createConcreteTag(blocks);
        createCookingBlockTag(blocks);
        createFarmableTag(blocks);
        createGlazedTerracottaTag(blocks);
        createOresTag(blocks);
        createOreTypeBlockTag(blocks);
        createStoneTypeTag(blocks);
        createStorageBlockTag(blocks);
        createUtilityBlockTag(blocks);

        createRequiresAxeTag(blocks);
        createRequiresPickaxeTag(blocks);
        createRequiresShovelTag(blocks);
        createRequiresShearsTag(blocks);
        createRequiresHammerTag(blocks);


        try {
            config.save(file);
            return config;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createGlazedTerracottaTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();
        Registry.BLOCK.forEach(block -> {
            NamespacedKey namespacedKey = block.getKey();
            String key = namespacedKey.getKey();
            if (key.endsWith("_glazed_terracotta")) blocks.add(namespacedKey.toString());
        });
        section.set("glazed_terracotta", blocks);
    }

    private void createConcreteTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();
        Registry.BLOCK.forEach(block -> {
            NamespacedKey namespacedKey = block.getKey();
            String key = namespacedKey.getKey();
            if (key.endsWith("_concrete")) blocks.add(namespacedKey.toString());
        });
        section.set("concrete", blocks);
    }

    private void createStoneTypeTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();
        blocks.add("minecraft:stone");
        blocks.add("cobblestone");
        blocks.add("minecraft:mossy_cobblestone");
        blocks.add("minecraft:infested_cobblestone");
        blocks.add("minecraft:andesite");
        blocks.add("minecraft:polished_andesite");
        blocks.add("minecraft:diorite");
        blocks.add("minecraft:polished_diorite");
        blocks.add("minecraft:granite");
        blocks.add("minecraft:polished_granite");
        blocks.add("minecraft:bricks");
        blocks.add("minecraft:nether_bricks");
        blocks.add("minecraft:sandstone");
        blocks.add("minecraft:chiseled_sandstone");
        blocks.add("minecraft:smooth_sandstone");
        blocks.add("minecraft:cut_sandstone");
        blocks.add("minecraft:red_sandstone");
        blocks.add("minecraft:chiseled_red_sandstone");
        blocks.add("minecraft:cut_red_sandstone");
        blocks.add("minecraft:smooth_red_sandstone");
        blocks.add("minecraft:prismarine");
        blocks.add("minecraft:prismarine_bricks");
        blocks.add("minecraft:dark_prismarine");
        blocks.add("minecraft:netherrack");
        blocks.add("minecraft:end_stone");
        blocks.add("minecraft:end_stone_bricks");
        blocks.add("minecraft:purpur_block");
        blocks.add("minecraft:purpur_pillar");
        // nether blocks
        blocks.add("minecraft:basalt");
        blocks.add("minecraft:polished_basalt");
        blocks.add("minecraft:blackstone");
        blocks.add("minecraft:polished_blackstone");
        blocks.add("minecraft:chiseled_polished_blackstone");
        blocks.add("minecraft:chiseled_nether_bricks");
        blocks.add("minecraft:cracked_nether_bricks");
        blocks.add("minecraft:quartz_bricks");

        section.set("stone_type", blocks);
    }

    private void createCookingBlockTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();
        blocks.add("minecraft:furnace");
        blocks.add("minecraft:blast_furnace");
        blocks.add("minecraft:smoker");
        section.set("cooking_block", blocks);
    }

    private void createStorageBlockTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();

        blocks.add("#minecraft:shulker_boxes");
        blocks.add("minecraft:chest");
        blocks.add("minecraft:ender_chest");
        blocks.add("minecraft:trapped_chest");
        blocks.add("minecraft:barrel");

        section.set("storage_block", blocks);
    }

    private void createUtilityBlockTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();

        blocks.add("minecraft:cartography_table");
        blocks.add("minecraft:fletching_table");
        blocks.add("minecraft:lectern");
        blocks.add("minecraft:loom");
        blocks.add("minecraft:stonecutter");
        blocks.add("minecraft:grindstone");
        blocks.add("minecraft:smithing_table");
        blocks.add("minecraft:anvil");
        blocks.add("minecraft:enchanting_table");
        blocks.add("minecraft:jukebox");
        blocks.add("minecraft:note_block");
        blocks.add("minecraft:brewing_stand");
        blocks.add("minecraft:cauldron");
        blocks.add("minecraft:composter");
        blocks.add("minecraft:respawn_anchor");
        blocks.add("minecraft:lodestone");

        section.set("utility_block", blocks);
    }

    private void createOreTypeBlockTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();

        blocks.add("minecraft:coal_block");
        blocks.add("minecraft:diamond_block");
        blocks.add("minecraft:emerald_block");
        blocks.add("minecraft:gold_block");
        blocks.add("minecraft:iron_block");
        blocks.add("minecraft:lapis_block");
        blocks.add("minecraft:quartz_block");
        blocks.add("minecraft:redstone_block");
        blocks.add("minecraft:netherite_block");

        section.set("ore_type_block", blocks);
    }

    private void createOresTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();
        Registry.BLOCK.forEach(block -> {
            String key = block.getKey().toString();
            if (key.endsWith("_ore")) blocks.add(key);
        });

        section.set("ores", blocks);
    }

    private void createFarmableTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();

        blocks.add("minecraft:melon");
        blocks.add("minecraft:melon_stem");
        blocks.add("minecraft:pumpkin");
        blocks.add("minecraft:pumpkin_stem");
        blocks.add("minecraft:chorus_flower");
        blocks.add("minecraft:chorus_plant");
        blocks.add("minecraft:carrots");
        blocks.add("minecraft:potatoes");
        blocks.add("minecraft:beetroots");
        blocks.add("minecraft:wheat");
        blocks.add("minecraft:sweet_berry_bush");
        blocks.add("minecraft:cocoa");

        section.set("farmable", blocks);
    }

    private void createRequiresAxeTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();
        for (Material value : Tag.MINEABLE_AXE.getValues()) {
            if (!Tag.REPLACEABLE.isTagged(value)) blocks.add(value.getKey().toString());
        }
        section.set("requires_axe", blocks);
        section.setInlineComments("requires_axe", List.of("Blocks which require an axe to break."));

    }

    private void createRequiresPickaxeTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();
        blocks.add("#minecraft:mineable/pickaxe");
        section.set("requires_pickaxe", blocks);
        section.setInlineComments("requires_pickaxe", List.of("Blocks which require a pickaxe to break."));

    }

    private void createRequiresShovelTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();
        for (Material value : Tag.MINEABLE_SHOVEL.getValues()) {
            if (value != Material.GRAVEL) blocks.add(value.getKey().toString());
        }
        section.set("requires_shovel", blocks);
        section.setInlineComments("requires_shovel", List.of("Blocks which require a shovel to break."));

    }

    private void createRequiresShearsTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();
        blocks.add(Material.COBWEB.getKey().toString());
        blocks.add(Material.TRIPWIRE.getKey().toString());
        blocks.add(Material.TNT.getKey().toString());
        blocks.add(Material.MUSHROOM_STEM.getKey().toString());
        section.set("requires_shears", blocks);
        section.setInlineComments("requires_shears", List.of("Blocks which require shears to break."));
    }

    private void createRequiresHammerTag(@NotNull ConfigurationSection section) {
        List<String> blocks = new ArrayList<>();
        blocks.add("#minecraft:fence_gates");
        blocks.add("#minecraft:terracotta");
        blocks.add("#minecraft:shulker_boxes");
        blocks.add("#minecraft:beds");
        blocks.add("#minecraft:logs");
        blocks.add("#minecraft:stairs");
        blocks.add("#minecraft:slabs");
        blocks.add("#minecraft:planks");
        blocks.add("#minecraft:wooden_pressure_plates");
        blocks.add("#minecraft:wooden_fences");
        blocks.add("#minecraft:rails");
        blocks.add("#minecraft:banners");
        blocks.add("#minecraft:fences");
        blocks.add("#minecraft:signs");

        blocks.add("#survival_plus:glazed_terracotta");
        blocks.add("#survival_plus:concrete");
        blocks.add("#survival_plus:stone_type");
        blocks.add("#survival_plus:cooking_block");
        blocks.add("#survival_plus:storage_block");
        blocks.add("#survival_plus:utility_block");
        blocks.add("#survival_plus:ore_type_block");

        section.set("requires_hammer", blocks);
        section.setInlineComments("requires_hammer", List.of("Blocks which require a hammer to place."));
    }

}
