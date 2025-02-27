package com.shanebeestudios.survival.plugin.data;

import com.shanebeestudios.survival.api.util.Utils;
import com.shanebeestudios.survival.plugin.SurvivalBootstrap;
import io.papermc.paper.datapack.DatapackRegistrar;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.keys.tags.EnchantmentTagKeys;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.tag.PostFlattenTagRegistrar;
import io.papermc.paper.tag.PreFlattenTagRegistrar;
import io.papermc.paper.tag.TagEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.block.BlockType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemType;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @hidden Internal only
 */
@SuppressWarnings({"UnstableApiUsage", "PatternValidation", "NullableProblems"})
public class TagGenerator {

    private final FileConfiguration blockTagConfig;
    private final FileConfiguration itemTagConfig;

    public TagGenerator(BootstrapContext context) {
        this.blockTagConfig = loadConfig(context.getDataDirectory(), "block-tags.yml");
        this.itemTagConfig = loadConfig(context.getDataDirectory(), "item-tags.yml");
        loadDatapack(context);
        loadTags(context);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private FileConfiguration loadConfig(Path dataFolder, String ymlFile) {
        File file = new File(dataFolder.toFile(), ymlFile);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            URL resource = getClass().getClassLoader().getResource(ymlFile);
            try {
                FileUtils.copyURLToFile(resource, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    private void loadDatapack(BootstrapContext context) {
        LifecycleEventManager<BootstrapContext> manager = context.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.DATAPACK_DISCOVERY.newHandler(event -> {
            DatapackRegistrar registrar = event.registrar();
            try {
                URI datapack = Objects.requireNonNull(SurvivalBootstrap.class.getResource("/datapack")).toURI();
                registrar.discoverPack(datapack, "survival_plus");
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private void loadTags(BootstrapContext context) {
        ComponentLogger logger = context.getLogger();
        LifecycleEventManager<BootstrapContext> manager = context.getLifecycleManager();

        // Create block tags
        manager.registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.BLOCK), event -> {
            final PreFlattenTagRegistrar<BlockType> registrar = event.registrar();
            createTags(logger, registrar, this.blockTagConfig);
        });

        // Create item tags
        manager.registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.ITEM), event -> {
            final PreFlattenTagRegistrar<ItemType> registrar = event.registrar();
            createTags(logger, registrar, this.itemTagConfig);
        });

        // Put our enchantments at the top of the tooltip list
        manager.registerEventHandler(LifecycleEvents.TAGS.postFlatten(RegistryKey.ENCHANTMENT), event -> {
            PostFlattenTagRegistrar<Enchantment> registrar = event.registrar();
            Collection<TypedKey<Enchantment>> tag = registrar.getTag(EnchantmentTagKeys.TOOLTIP_ORDER);

            List<TypedKey<Enchantment>> newTags = new ArrayList<>();
            newTags.add(TypedKey.create(RegistryKey.ENCHANTMENT, Key.key("survival_plus:blazing")));
            newTags.add(TypedKey.create(RegistryKey.ENCHANTMENT, Key.key("survival_plus:obsidian_power")));
            newTags.add(TypedKey.create(RegistryKey.ENCHANTMENT, Key.key("survival_plus:quartz_mining")));
            registrar.setTag(EnchantmentTagKeys.TOOLTIP_ORDER, newTags);
            registrar.addToTag(EnchantmentTagKeys.TOOLTIP_ORDER, tag);
        });
    }

    private <T> void createTags(ComponentLogger logger, PreFlattenTagRegistrar<T> registrar, FileConfiguration config) {
        String registerName = StringUtils.capitalize(registrar.registryKey().key().value());
        logger.info(Utils.getMini("<grey>%s Tag Creation:", registerName));
        ConfigurationSection survivalPlusSection = config.getConfigurationSection("survival_plus");
        assert survivalPlusSection != null;
        for (String key : survivalPlusSection.getKeys(false)) {
            createTagFromSection(key, registrar, survivalPlusSection);
            logger.info(Utils.getMini("<grey>Generating tag <white>'<aqua>survival_plus:%s<white>'", key));
        }
        ConfigurationSection minecraftSection = config.getConfigurationSection("minecraft");
        if (minecraftSection == null) return;

        logger.info(Utils.getMini("<grey>%s Tag Mutation:", registerName));
        for (String key : minecraftSection.getKeys(false)) {
            addToTagFromSection(key, registrar, survivalPlusSection);
            logger.info(Utils.getMini("<grey>Adding value to tag <white>'<aqua>minecraft:%s<white>'", key));
        }
    }

    private <T> void createTagFromSection(String key, PreFlattenTagRegistrar<T> registrar, ConfigurationSection survivalPlusSection) {
        List<TagEntry<T>> entries = new ArrayList<>();
        for (String s : survivalPlusSection.getStringList( key)) {
            entries.add(getTagEntry(s, registrar.registryKey()));
        }

        registrar.setTag(TagKey.create(registrar.registryKey(), Key.key("survival_plus:" + key)), entries);
    }

    private <T> void addToTagFromSection(String key, PreFlattenTagRegistrar<T> registrar, ConfigurationSection survivalPlusSection) {
        List<TagEntry<T>> entries = new ArrayList<>();
        for (String s : survivalPlusSection.getStringList("minecraft." + key)) {
            entries.add(getTagEntry(s, registrar.registryKey()));
        }
        if (entries.isEmpty()) return;

        registrar.addToTag(TagKey.create(registrar.registryKey(), Key.key("minecraft:" + key)), entries);
    }

    private <T> TagEntry<T> getTagEntry(String string, RegistryKey<T> key) {
        if (string.startsWith("#")) {
            TagKey<T> tagKey = TagKey.create(key, Key.key(string.substring(1)));
            return TagEntry.tagEntry(tagKey);
        }
        TypedKey<T> blockKey = TypedKey.create(key, Key.key(string));
        return TagEntry.valueEntry(blockKey);
    }

}
