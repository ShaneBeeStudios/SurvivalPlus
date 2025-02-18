package com.shanebeestudios.survival;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import org.jetbrains.annotations.NotNull;
import com.shanebeestudios.survival.generator.BlockTagGenerator;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class SurvivalBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        new BlockTagGenerator(context);
    }

}
