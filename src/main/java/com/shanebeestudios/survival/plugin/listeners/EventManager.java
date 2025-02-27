package com.shanebeestudios.survival.plugin.listeners;

import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Config;
import com.shanebeestudios.survival.plugin.listeners.block.BlockBreakListener;
import com.shanebeestudios.survival.plugin.listeners.block.BlockPlaceListener;
import com.shanebeestudios.survival.plugin.listeners.block.Campfire;
import com.shanebeestudios.survival.plugin.listeners.block.Chairs;
import com.shanebeestudios.survival.plugin.listeners.block.LootTableListener;
import com.shanebeestudios.survival.plugin.listeners.block.SnowballThrow;
import com.shanebeestudios.survival.plugin.listeners.block.WorkbenchShare;
import com.shanebeestudios.survival.plugin.listeners.entity.BeeKeeperSuit;
import com.shanebeestudios.survival.plugin.listeners.entity.ChestPigmen;
import com.shanebeestudios.survival.plugin.listeners.entity.ChickenSpawn;
import com.shanebeestudios.survival.plugin.listeners.entity.EntityDeath;
import com.shanebeestudios.survival.plugin.listeners.entity.LivingSlime;
import com.shanebeestudios.survival.plugin.listeners.entity.MerchantTrades;
import com.shanebeestudios.survival.plugin.listeners.entity.MobGoalListener;
import com.shanebeestudios.survival.plugin.listeners.entity.PiglinBarter;
import com.shanebeestudios.survival.plugin.listeners.item.BeetrootStrength;
import com.shanebeestudios.survival.plugin.listeners.item.BowListener;
import com.shanebeestudios.survival.plugin.listeners.item.CompassWaypoint;
import com.shanebeestudios.survival.plugin.listeners.item.CookieHealthBoost;
import com.shanebeestudios.survival.plugin.listeners.item.DrinkableItemListener;
import com.shanebeestudios.survival.plugin.listeners.item.DualWieldListener;
import com.shanebeestudios.survival.plugin.listeners.item.FirestrikerListener;
import com.shanebeestudios.survival.plugin.listeners.item.FoodDiversityConsume;
import com.shanebeestudios.survival.plugin.listeners.item.GiantBladeListener;
import com.shanebeestudios.survival.plugin.listeners.item.GrapplingHookListener;
import com.shanebeestudios.survival.plugin.listeners.item.MedicKit;
import com.shanebeestudios.survival.plugin.listeners.item.PoisonousPotato;
import com.shanebeestudios.survival.plugin.listeners.item.RawMeatHunger;
import com.shanebeestudios.survival.plugin.listeners.item.RecurvedBowListener;
import com.shanebeestudios.survival.plugin.listeners.item.RepairCrafting;
import com.shanebeestudios.survival.plugin.listeners.item.ShivPoison;
import com.shanebeestudios.survival.plugin.listeners.item.TropicalFish;
import com.shanebeestudios.survival.plugin.listeners.item.Valkyrie;
import com.shanebeestudios.survival.plugin.listeners.item.WaterBowlListener;
import com.shanebeestudios.survival.plugin.listeners.item.WaterPurifiedListener;
import com.shanebeestudios.survival.plugin.listeners.player.EnergyChange;
import com.shanebeestudios.survival.plugin.listeners.player.PlayerDataListener;
import com.shanebeestudios.survival.plugin.listeners.player.ThirstListener;
import com.shanebeestudios.survival.plugin.listeners.server.Guide;
import com.shanebeestudios.survival.plugin.listeners.server.LocalChat;
import com.shanebeestudios.survival.plugin.listeners.server.RecipeDiscovery;
import com.shanebeestudios.survival.plugin.listeners.server.ResourcePackListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * Internal use only
 */
public class EventManager {

    private final SurvivalPlugin plugin;
    private final Config config;

    public EventManager(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getSurvivalConfig();
    }

    public void registerEvents() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(this.plugin, this.plugin);
        pluginManager.registerEvents(new RecipeDiscovery(this.plugin), this.plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerDataListener(this.plugin), this.plugin);

        if (this.config.survival_enabled) {
            pluginManager.registerEvents(new BlockBreakListener(this.plugin), this.plugin);
            pluginManager.registerEvents(new BlockPlaceListener(this.plugin), this.plugin);
            pluginManager.registerEvents(new FirestrikerListener(this.plugin), this.plugin);
            pluginManager.registerEvents(new ShivPoison(this.plugin), this.plugin);
            pluginManager.registerEvents(new WaterBowlListener(this.plugin), this.plugin);
            pluginManager.registerEvents(new Campfire(this.plugin), this.plugin);
        }
        if (this.config.mechanics_bow)
            pluginManager.registerEvents(new BowListener(this.plugin), this.plugin);
        if (this.config.mechanics_grappling_hook)
            pluginManager.registerEvents(new GrapplingHookListener(this.plugin), this.plugin);
        if (this.config.legendary_valkyrie)
            pluginManager.registerEvents(new Valkyrie(this.plugin), this.plugin);
        if (this.config.legendary_giant_blade)
            pluginManager.registerEvents(new GiantBladeListener(this.plugin), this.plugin);
        if (this.config.settings_local_chat_distance > -1)
            pluginManager.registerEvents(new LocalChat(this.plugin), this.plugin);
        if (this.config.mechanics_compass_waypoint)
            pluginManager.registerEvents(new CompassWaypoint(this.plugin), this.plugin);
        if (this.config.mechanics_medic_kit)
            pluginManager.registerEvents(new MedicKit(this.plugin), this.plugin);
        if (this.config.settings_resource_pack_enabled) {
            pluginManager.registerEvents(new ResourcePackListener(this.plugin), this.plugin);
        }
        if (this.config.mechanics_raw_meat_hunger)
            pluginManager.registerEvents(new RawMeatHunger(), this.plugin);
        if (this.config.mechanics_thirst_enabled) {
            pluginManager.registerEvents(new ThirstListener(this.plugin), this.plugin);
            pluginManager.registerEvents(new DrinkableItemListener(this.plugin), this.plugin);
            if (this.config.mechanics_thirst_purify_water) {
                pluginManager.registerEvents(new WaterPurifiedListener(this.plugin), this.plugin);
            }
        }
        if (this.config.mechanics_poison_potato)
            pluginManager.registerEvents(new PoisonousPotato(), this.plugin);
        if (this.config.mechanics_shared_workbench)
            pluginManager.registerEvents(new WorkbenchShare(this.plugin), this.plugin);
        if (this.config.mechanics_chairs_enabled)
            pluginManager.registerEvents(new Chairs(this.plugin), this.plugin);
        if (this.config.mechanics_cookie_boost)
            pluginManager.registerEvents(new CookieHealthBoost(), this.plugin);
        if (this.config.mechanics_beet_strength)
            pluginManager.registerEvents(new BeetrootStrength(), this.plugin);
        if (this.config.mechanics_tropical_fish)
            pluginManager.registerEvents(new TropicalFish(this.plugin), this.plugin);
        if (this.config.mechanics_living_slime)
            pluginManager.registerEvents(new LivingSlime(this.plugin), this.plugin);
        if (this.config.mechanics_energy_enabled)
            pluginManager.registerEvents(new EnergyChange(this.plugin), this.plugin);
        if (this.config.mechanics_food_diversity_enabled)
            pluginManager.registerEvents(new FoodDiversityConsume(this.plugin), this.plugin);
        if (this.config.mechanics_recurved_bow)
            pluginManager.registerEvents(new RecurvedBowListener(this.plugin), this.plugin);
        if (this.config.mechanics_snowball_revamp)
            pluginManager.registerEvents(new SnowballThrow(), this.plugin);
        if (this.config.entity_mechanics_chicken_breeding_enabled)
            pluginManager.registerEvents(new ChickenSpawn(this.plugin), this.plugin);
        if (this.config.welcome_guide_enabled)
            pluginManager.registerEvents(new Guide(this.plugin), this.plugin);

        if (this.config.entity_mechanics_pigmen_chest_enabled)
            pluginManager.registerEvents(new ChestPigmen(this.plugin), this.plugin);

        if (this.config.entity_mechanics_beekeeper_suit_enabled) {
            Bukkit.getPluginManager().registerEvents(new BeeKeeperSuit(), this.plugin);
        }
        if (this.config.survival_update_merchant_trades) {
            pluginManager.registerEvents(new MerchantTrades(this.plugin), this.plugin);
        }
        if (this.config.survival_update_loot_tables) {
            pluginManager.registerEvents(new LootTableListener(this.plugin), this.plugin);
        }
        pluginManager.registerEvents(new PiglinBarter(this.plugin), this.plugin);
        // Config handled within this event
        pluginManager.registerEvents(new EntityDeath(this.plugin), this.plugin);
        pluginManager.registerEvents(new RepairCrafting(), this.plugin);
        pluginManager.registerEvents(new MobGoalListener(this.plugin), this.plugin);

        // TODO config?
        pluginManager.registerEvents(new DualWieldListener(this.plugin), this.plugin);
    }

}
