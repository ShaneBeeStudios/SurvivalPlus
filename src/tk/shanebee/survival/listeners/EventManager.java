package tk.shanebee.survival.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import tk.shanebee.survival.Survival;

/**
 * Internal use only
 */
public class EventManager {

	private Survival plugin;
	private FileConfiguration settings;
	private int localChat = Survival.LocalChatDist;

	public EventManager(Survival plugin, FileConfiguration settings) {
		this.plugin = plugin;
		this.settings = settings;
	}

	public void registerEvents() {
		PluginManager pm = plugin.getServer().getPluginManager();
		pm.registerEvents(this.plugin, this.plugin);
		pm.registerEvents(new RecipeDiscovery(), this.plugin);

		if (settings.getBoolean("Survival.Enabled")) {
			pm.registerEvents(new BlockBreak(), this.plugin);
			pm.registerEvents(new BlockPlace(), this.plugin);
			pm.registerEvents(new FirestrikerClick(), this.plugin);
			pm.registerEvents(new ShivPoison(), this.plugin);
			pm.registerEvents(new WaterBowl(), this.plugin);
			pm.registerEvents(new Campfire(), this.plugin);
			//pm.registerEvents(new Backpack(), this.plugin); needs to be reworked
		}
		pm.registerEvents(new NoAnvil(), this.plugin);
		if (settings.getBoolean("Mechanics.Bow"))
			pm.registerEvents(new Bow(), this.plugin);
		if (settings.getBoolean("Mechanics.GrapplingHook"))
			pm.registerEvents(new GrapplingHook(), this.plugin);
		if (settings.getBoolean("LegendaryItems.ObsidianMace"))
			pm.registerEvents(new ObsidianMaceWeakness(), this.plugin);
		if (settings.getBoolean("LegendaryItems.ValkyrieAxe"))
			pm.registerEvents(new Valkyrie(), this.plugin);
		if (settings.getBoolean("LegendaryItems.GiantBlade"))
			pm.registerEvents(new GiantBlade(), this.plugin);
		if (settings.getBoolean("LegendaryItems.BlazeSword"))
			pm.registerEvents(new BlazeSword(), this.plugin);
		if (localChat > -1)
			pm.registerEvents(new LocalChat(), this.plugin);
		if (settings.getBoolean("Mechanics.CompassWaypoint"))
			pm.registerEvents(new CompassWaypoint(), this.plugin);
		if (settings.getBoolean("Mechanics.MedicalKit"))
			pm.registerEvents(new MedicKit(), this.plugin);

		pm.registerEvents(new WaterBottleCrafting(), this.plugin);
		pm.registerEvents(new SpecialItemInteractCancel(), this.plugin);

		pm.registerEvents(new SetResourcePack(), this.plugin);

		if (settings.getBoolean("Mechanics.RawMeatHunger"))
			pm.registerEvents(new RawMeatHunger(), this.plugin);
		if (settings.getBoolean("Mechanics.Thirst.Enabled")) {
			pm.registerEvents(new Consume(this.plugin), this.plugin);
			if (settings.getBoolean("Mechanics.Thirst.PurifyWater"))
				pm.registerEvents(new CauldronWaterBottle(), this.plugin);
		}
		if (settings.getBoolean("Mechanics.PoisonousPotato"))
			pm.registerEvents(new PoisonousPotato(), this.plugin);
		if (settings.getBoolean("Mechanics.SharedWorkbench"))
			pm.registerEvents(new WorkbenchShare(), this.plugin);
		if (settings.getBoolean("Mechanics.Chairs.Enabled"))
			pm.registerEvents(new Chairs(), this.plugin);
		if (settings.getBoolean("Mechanics.CookieHealthBoost"))
			pm.registerEvents(new CookieHealthBoost(), this.plugin);
		if (settings.getBoolean("Mechanics.BeetrootStrength"))
			pm.registerEvents(new BeetrootStrength(), this.plugin);
		if (settings.getBoolean("Mechanics.Clownfish"))
			pm.registerEvents(new Clownfish(), this.plugin);
		if (settings.getBoolean("Mechanics.LivingSlime"))
			pm.registerEvents(new LivingSlime(), this.plugin);
		if (settings.getBoolean("Mechanics.BedFatigueLevel"))
			pm.registerEvents(new BedFatigue(), this.plugin);
		if (settings.getBoolean("Mechanics.FoodDiversity"))
			pm.registerEvents(new FoodDiversityConsume(), this.plugin);
		if (settings.getBoolean("Mechanics.RecurveBow"))
			pm.registerEvents(new RecurvedBow(), this.plugin);
		if (settings.getBoolean("Mechanics.StatusScoreboard"))
			pm.registerEvents(new ScoreboardStats(), this.plugin);
		if (settings.getBoolean("Mechanics.SnowballRevamp"))
			pm.registerEvents(new SnowballThrow(), this.plugin);
		if (settings.getBoolean("Mechanics.SnowGenerationRevamp"))
			pm.registerEvents(new SnowGeneration(), this.plugin);
		pm.registerEvents(new ChickenSpawn(), this.plugin);
		if (settings.getBoolean("WelcomeGuide.Enabled"))
			pm.registerEvents(new Guide(), this.plugin);
		if (settings.getBoolean("Mechanics.BurnoutTorches.Enabled")) // TODO experimental feature, not 100% sure about this
			pm.registerEvents(new BurnoutTorches(this.plugin), this.plugin);
		pm.registerEvents(new InventoryUpdate(), this.plugin);
	}

}
