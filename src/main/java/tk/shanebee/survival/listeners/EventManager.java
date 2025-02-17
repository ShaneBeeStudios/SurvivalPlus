package tk.shanebee.survival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import tk.shanebee.survival.SurvivalPlugin;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.listeners.block.BlockBreak;
import tk.shanebee.survival.listeners.block.BlockPlace;
import tk.shanebee.survival.listeners.block.BurnoutTorches;
import tk.shanebee.survival.listeners.block.Campfire;
import tk.shanebee.survival.listeners.block.Chairs;
import tk.shanebee.survival.listeners.block.SnowGeneration;
import tk.shanebee.survival.listeners.block.SnowballThrow;
import tk.shanebee.survival.listeners.block.WorkbenchShare;
import tk.shanebee.survival.listeners.entity.BeeKeeperSuit;
import tk.shanebee.survival.listeners.entity.ChestPigmen;
import tk.shanebee.survival.listeners.entity.ChickenSpawn;
import tk.shanebee.survival.listeners.entity.EntityDeath;
import tk.shanebee.survival.listeners.entity.LivingSlime;
import tk.shanebee.survival.listeners.entity.MerchantTrades;
import tk.shanebee.survival.listeners.entity.PiglinBarter;
import tk.shanebee.survival.listeners.item.BeetrootStrength;
import tk.shanebee.survival.listeners.item.BlazeSword;
import tk.shanebee.survival.listeners.item.Bow;
import tk.shanebee.survival.listeners.item.CauldronWaterBottle;
import tk.shanebee.survival.listeners.item.CompassWaypoint;
import tk.shanebee.survival.listeners.item.CookieHealthBoost;
import tk.shanebee.survival.listeners.item.FirestrikerListener;
import tk.shanebee.survival.listeners.item.FoodDiversityConsume;
import tk.shanebee.survival.listeners.item.GiantBlade;
import tk.shanebee.survival.listeners.item.GrapplingHook;
import tk.shanebee.survival.listeners.item.MedicKit;
import tk.shanebee.survival.listeners.item.ObsidianMaceWeakness;
import tk.shanebee.survival.listeners.item.PoisonousPotato;
import tk.shanebee.survival.listeners.item.RawMeatHunger;
import tk.shanebee.survival.listeners.item.RecurvedBow;
import tk.shanebee.survival.listeners.item.RepairCrafting;
import tk.shanebee.survival.listeners.item.ShivPoison;
import tk.shanebee.survival.listeners.item.ThirstListener;
import tk.shanebee.survival.listeners.item.TropicalFish;
import tk.shanebee.survival.listeners.item.Valkyrie;
import tk.shanebee.survival.listeners.item.WaterBottleListener;
import tk.shanebee.survival.listeners.item.WaterBowlListener;
import tk.shanebee.survival.listeners.player.EnergyChange;
import tk.shanebee.survival.listeners.player.PlayerDataListener;
import tk.shanebee.survival.listeners.server.Guide;
import tk.shanebee.survival.listeners.server.LocalChat;
import tk.shanebee.survival.listeners.server.RecipeDiscovery;
import tk.shanebee.survival.listeners.server.ResourcePackListener;

/**
 * Internal use only
 */
public class EventManager {

	private final SurvivalPlugin plugin;
	private final int LOCAL_CHAT;
	private final Config config;

	public EventManager(SurvivalPlugin plugin) {
		this.plugin = plugin;
		this.config = plugin.getSurvivalConfig();
		this.LOCAL_CHAT = config.settings_local_chat_distance;
	}

	public void registerEvents() {
		PluginManager pm = plugin.getServer().getPluginManager();
		pm.registerEvents(this.plugin, this.plugin);
		pm.registerEvents(new RecipeDiscovery(plugin), this.plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerDataListener(this.plugin), this.plugin);

		if (config.survival_enabled) {
			pm.registerEvents(new BlockBreak(plugin), this.plugin);
			pm.registerEvents(new BlockPlace(plugin), this.plugin);
			pm.registerEvents(new FirestrikerListener(plugin), this.plugin);
			pm.registerEvents(new ShivPoison(this.plugin), this.plugin);
			pm.registerEvents(new WaterBowlListener(plugin), this.plugin);
			pm.registerEvents(new Campfire(plugin), this.plugin);
			//pm.registerEvents(new Backpack(), this.plugin); needs to be reworked
		}
		if (config.MECHANICS_BOW)
			pm.registerEvents(new Bow(plugin), this.plugin);
		if (config.mechanics_grappling_hook)
			pm.registerEvents(new GrapplingHook(plugin), this.plugin);
		if (config.legendary_obsidian_mace)
			pm.registerEvents(new ObsidianMaceWeakness(plugin), this.plugin);
		if (config.legendary_valkyrie)
			pm.registerEvents(new Valkyrie(plugin), this.plugin);
		if (config.legendary_giant_blade)
			pm.registerEvents(new GiantBlade(plugin), this.plugin);
		if (config.legendary_blaze_sword)
			pm.registerEvents(new BlazeSword(), this.plugin);
		if (LOCAL_CHAT > -1)
			pm.registerEvents(new LocalChat(plugin), this.plugin);
		if (config.mechanics_compass_waypoint)
			pm.registerEvents(new CompassWaypoint(this.plugin), this.plugin);
		if (config.mechanics_medic_kit)
			pm.registerEvents(new MedicKit(plugin), this.plugin);

		pm.registerEvents(new WaterBottleListener(plugin), this.plugin);

        if (config.settings_resource_pack_enabled) {
            pm.registerEvents(new ResourcePackListener(plugin), this.plugin);
        }

		if (config.mechanics_raw_meat_hunger)
			pm.registerEvents(new RawMeatHunger(), this.plugin);
		if (config.mechanics_thirst_enabled) {
			pm.registerEvents(new ThirstListener(this.plugin), this.plugin);
			if (config.mechanics_thirst_purify_water)
				pm.registerEvents(new CauldronWaterBottle(), this.plugin);
		}
		if (config.mechanics_poison_potato)
			pm.registerEvents(new PoisonousPotato(), this.plugin);
		if (config.mechanics_shared_workbench)
			pm.registerEvents(new WorkbenchShare(plugin), this.plugin);
		if (config.mechanics_chairs_enabled)
			pm.registerEvents(new Chairs(plugin), this.plugin);
		if (config.mechanics_cookie_boost)
			pm.registerEvents(new CookieHealthBoost(), this.plugin);
		if (config.mechanics_beet_strength)
			pm.registerEvents(new BeetrootStrength(), this.plugin);
		if (config.mechanics_tropical_fish)
			pm.registerEvents(new TropicalFish(this.plugin), this.plugin);
		if (config.mechanics_living_slime)
			pm.registerEvents(new LivingSlime(plugin), this.plugin);
		if (config.mechanics_energy_enabled)
			pm.registerEvents(new EnergyChange(plugin), this.plugin);
		if (config.mechanics_food_diversity_enabled)
			pm.registerEvents(new FoodDiversityConsume(plugin), this.plugin);
		if (config.mechanics_recurved_bow)
			pm.registerEvents(new RecurvedBow(plugin), this.plugin);
		if (config.mechanics_snowball_revamp)
			pm.registerEvents(new SnowballThrow(), this.plugin);
		if (config.MECHANICS_SNOW_GEN_REVAMP)
			pm.registerEvents(new SnowGeneration(plugin), this.plugin);
		if (config.entity_mechanics_chicken_breeding_enabled)
		    pm.registerEvents(new ChickenSpawn(this.plugin), this.plugin);
		if (config.welcome_guide_enabled)
			pm.registerEvents(new Guide(plugin), this.plugin);
		if (config.MECHANICS_BURNOUT_TORCH_ENABLED) // TODO experimental feature, not 100% sure about this
			pm.registerEvents(new BurnoutTorches(this.plugin), this.plugin);

		if (config.entity_mechanics_pigmen_chest_enabled)
			pm.registerEvents(new ChestPigmen(this.plugin), this.plugin);

        if (config.entity_mechanics_beekeeper_suit_enabled) {
            Bukkit.getPluginManager().registerEvents(new BeeKeeperSuit(), this.plugin);
        }
        if (config.survival_update_merchant_trades) {
            pm.registerEvents(new MerchantTrades(this.plugin), this.plugin);
        }
        pm.registerEvents(new PiglinBarter(this.plugin), this.plugin);
        // Config handled within this event
        pm.registerEvents(new EntityDeath(this.plugin), this.plugin);
        pm.registerEvents(new RepairCrafting(), this.plugin);

	}

}
