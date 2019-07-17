package tk.shanebee.survival.util;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	private FileConfiguration config;

	public boolean breakOnlyWithSickle;

	public Config(FileConfiguration config) {
		this.config = config;
		loadConfig();
	}

	private void loadConfig() {
		this.breakOnlyWithSickle = config.getBoolean("Survival.BreakOnlyWith.Sickle");
	}

}
