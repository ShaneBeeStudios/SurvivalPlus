package com.shanebeestudios.survival.plugin;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import org.jetbrains.annotations.NotNull;
import com.shanebeestudios.survival.api.generator.BlockTagGenerator;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class SurvivalBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        new BlockTagGenerator(context);
    }

}
