package com.shanebeestudios.survival.api.generator;

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
import com.shanebeestudios.survival.api.util.Utils;

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

            logger.info(Utils.getMini("<grey>Bootstrap:"));
            ConfigurationSection blocksSection = this.config.getConfigurationSection("blocks");
            assert blocksSection != null;
            for (String key : blocksSection.getKeys(false)) {
                createTagFromSection(key, registrar);
                logger.info(Utils.getMini("<grey>Generating block tag <white>'<aqua>%s<white>'", key));
            }
            logger.info(Utils.getMini("<grey>Finished bootstrapping"));
        });
    }

    @SuppressWarnings("PatternValidation")
    private void createTagFromSection(String key, PreFlattenTagRegistrar<BlockType> registrar) {
        List<TagEntry<BlockType>> entries = new ArrayList<>();
        for (String s : this.config.getStringList("blocks." + key)) {
            entries.add(getTagEntry(s));
        }

        registrar.setTag(TagKey.create(registrar.registryKey(), Key.key("survival_plus:" + key)), entries);
    }

    private TagEntry<BlockType> getTagEntry(String string) {
        if (string.startsWith("#")) return getTagKeyEntry(string.substring(1));
        return getBlockEntry(string);
    }

    @SuppressWarnings("PatternValidation")
    private TagEntry<BlockType> getTagKeyEntry(String string) {
        TagKey<BlockType> tagKey = TagKey.create(RegistryKey.BLOCK, Key.key(string));
        return TagEntry.tagEntry(tagKey);
    }

    @SuppressWarnings("PatternValidation")
    private TagEntry<BlockType> getBlockEntry(String string) {
        TypedKey<BlockType> blockKey = TypedKey.create(RegistryKey.BLOCK, Key.key(string));
        return TagEntry.valueEntry(blockKey);
    }

}
