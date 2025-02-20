package com.shanebeestudios.survival.api.generator;

import com.shanebeestudios.survival.api.util.Utils;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.tag.PreFlattenTagRegistrar;
import io.papermc.paper.tag.TagEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.block.BlockType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class BlockTagGenerator {

    private FileConfiguration config;

    public BlockTagGenerator(BootstrapContext context) {
        loadConfig(context.getDataDirectory());
        bootstrap(context);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void loadConfig(Path dataFolder) {
        File file = new File(dataFolder.toFile(), "block-tags.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            URL resource = getClass().getClassLoader().getResource("block-tags.yml");
            try {
                FileUtils.copyURLToFile(resource, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    @SuppressWarnings("NullableProblems")
    private void bootstrap(BootstrapContext context) {
        ComponentLogger logger = context.getLogger();
        LifecycleEventManager<BootstrapContext> manager = context.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.BLOCK), event -> {
            final PreFlattenTagRegistrar<BlockType> registrar = event.registrar();

            logger.info(Utils.getMini("<grey>Tag Creation:"));
            ConfigurationSection survivalPlusSection = this.config.getConfigurationSection("survival_plus");
            assert survivalPlusSection != null;
            for (String key : survivalPlusSection.getKeys(false)) {
                createTagFromSection(key, registrar);
                logger.info(Utils.getMini("<grey>Generating block tag <white>'<aqua>survival_plus:%s<white>'", key));
            }
            ConfigurationSection minecraftSection = this.config.getConfigurationSection("minecraft");
            if (minecraftSection == null) return;

            logger.info(Utils.getMini("<grey>Tag Mutation:"));
            for (String key : minecraftSection.getKeys(false)) {
                addToTagFromSection(key, registrar);
                logger.info(Utils.getMini("<grey>Adding value to block tag <white>'<aqua>minecraft:%s<white>'", key));

            }
        });
    }

    @SuppressWarnings("PatternValidation")
    private void createTagFromSection(String key, PreFlattenTagRegistrar<BlockType> registrar) {
        List<TagEntry<BlockType>> entries = new ArrayList<>();
        for (String s : this.config.getStringList("survival_plus." + key)) {
            entries.add(getTagEntry(s));
        }

        registrar.setTag(TagKey.create(registrar.registryKey(), Key.key("survival_plus:" + key)), entries);
    }

    @SuppressWarnings("PatternValidation")
    private void addToTagFromSection(String key, PreFlattenTagRegistrar<BlockType> registrar) {
        List<TagEntry<BlockType>> entries = new ArrayList<>();
        for (String s : this.config.getStringList("minecraft." + key)) {
            entries.add(getTagEntry(s));
        }
        if (entries.isEmpty()) return;

        registrar.addToTag(TagKey.create(registrar.registryKey(), Key.key("minecraft:" + key)), entries);
    }

    @SuppressWarnings("PatternValidation")
    private TagEntry<BlockType> getTagEntry(String string) {
        if (string.startsWith("#")) {
            TagKey<BlockType> tagKey = TagKey.create(RegistryKey.BLOCK, Key.key(string.substring(1)));
            return TagEntry.tagEntry(tagKey);
        }
        TypedKey<BlockType> blockKey = TypedKey.create(RegistryKey.BLOCK, Key.key(string));
        return TagEntry.valueEntry(blockKey);
    }

}
