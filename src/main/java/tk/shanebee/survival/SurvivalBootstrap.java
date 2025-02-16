package tk.shanebee.survival;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import org.jetbrains.annotations.NotNull;
import tk.shanebee.survival.generator.BlockTagGenerator;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class SurvivalBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        new BlockTagGenerator(context);
    }

}
