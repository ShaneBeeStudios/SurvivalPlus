package com.shanebeestudios.survival.plugin;

import com.shanebeestudios.survival.plugin.registry.TagGenerator;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class SurvivalBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        new TagGenerator(context);
    }

}
