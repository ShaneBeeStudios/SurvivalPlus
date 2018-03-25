package com.fattymieo.survival;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Coal;
import org.bukkit.material.Dye;
import org.bukkit.material.Wood;
import org.bukkit.material.Wool;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.fattymieo.survival.commands.*;
import com.fattymieo.survival.events.*;

import lib.ParticleEffect;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagFloat;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;

//Special thanks to DarkBlade12 for ParticleEffect Library

public class Survival extends JavaPlugin
{
    public static String Version = "2.1.1";
	public static Survival instance;
    public static NamespacedKey key;
    public static ScoreboardManager manager;
    public static Scoreboard board;
    public static Scoreboard mainBoard;
    public static FileConfiguration config = new YamlConfiguration();
    public static File configFile;
    public static List<File> langFiles;
    public static List<FileConfiguration> langFileConfigs;
    public static FileConfiguration settings = new YamlConfiguration();
    public static String Language = "EN";
    public static int LocalChatDist = 64;
    public static int AlertInterval = 20;
    public static List<Double> Rates = new ArrayList<>();
    public static Map<String, String> Words;
	public static List<Material> allowedBlocks = new ArrayList<Material>();
	public static List<Player> usingPlayers = new ArrayList<Player>();
	public static boolean snowGenOption = true;
	
	public void onEnable()
	{		
		instance = this;
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		key = new NamespacedKey(this, getDescription().getName());
	    
	    settings = getConfig();
	    settings.options().copyDefaults(true);
	    saveConfig();

		configFile = new File(getDataFolder(), "config.yml");
		if(!Version.equals(settings.getString("Version")))
			Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.RED + "config.yml has different version from current version, recommended to recheck.");
		
		if(settings.getBoolean("NoPos"))
		{
			Bukkit.getPluginManager().registerEvents(new NoPos(), this);
			Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.YELLOW + "NoPos implemented, F3 coordinates are disabled!");
		}
	    
		//settings = YamlConfiguration.loadConfiguration(getResource("config.yml"));
		String url = settings.getString("MultiWorld.ResourcePackURL");
		boolean resourcePack = settings.getBoolean("MultiWorld.EnableResourcePack");
		if(resourcePack)
		{
			if(url.isEmpty() || url == null)
			{
				Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.RED + "Resource Pack is not set! Plugin disabled.");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			}
		}
		Language = settings.getString("Language");
		LocalChatDist = settings.getInt("LocalChatDist");
		
		AlertInterval = settings.getInt("Mechanics.AlertInterval");
		if(AlertInterval <= 0)
		{
			Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.RED + "AlertInterval cannot be zero or below! Plugin disabled.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		Rates.add(settings.getDouble("Survival.DropRate.Flint"));
		Rates.add(settings.getDouble("Survival.DropRate.Stick"));
		Rates.add(settings.getDouble("Mechanics.Thirst.DrainRate"));
		for(double i : Rates)
		{
			if(i <= 0)
			{
				Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.RED + "Rate values cannot be zero or below! (Check config.yml) Plugin disabled.");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			}
			else if(i > 1)
			{
				Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.RED + "Rate values cannot be above 1! (Check config.yml) Plugin disabled.");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			}
				
		}
		
		for(String type : settings.getStringList("Mechanics.Chairs.AllowedBlocks"))
			allowedBlocks.add(Material.getMaterial(type));

	    saveResource("lang_EN.yml", true);
	    saveResource("lang_ZH_simplified.yml", true);
	    saveResource("lang_ZH_traditional.yml", true);
	    
	    switch(Language)
		{
		    case "ZH":
		    case "ZH_Simplified":
		    	Language = "ZH_simplified";
		    	break;
		    case "ZH_Traditional":
		    	Language = "ZH_simplified";
		    	break;
		    default:
	    }
		
	    Map<String, Object> lang_data;
	    
	    File lang_file = new File(getDataFolder(), "lang_" + Language + ".yml");
	    if(!lang_file.exists())
	    {
	    	Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + ChatColor.YELLOW + "Unable to locate lang_" + Language + ".yml, using default language (EN).");
			Language = "EN";
	    	lang_file = new File(getDataFolder(), "lang_EN.yml");
	    }
	    
		lang_data = YamlConfiguration.loadConfiguration(lang_file).getValues(true);
		
		Words = copyToStringValueMap(lang_data);
		
		logger.info("Selected Language: " + Language);
		
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		mainBoard = manager.getMainScoreboard();
		board.registerNewObjective("DualWieldMsg", "dummy");
	    board.registerNewObjective("Charge", "dummy");
	    board.registerNewObjective("Charging", "dummy");
	    board.registerNewObjective("Spin", "dummy");
	    board.registerNewObjective("DualWield", "dummy");
	    board.registerNewObjective("Chat", "dummy");
	    board.registerNewObjective("Healing", "dummy");
	    board.registerNewObjective("HealTimes", "dummy");
	    board.registerNewObjective("RecurveFiring", "dummy");
	    board.registerNewObjective("RecurveCooldown", "dummy");
	    try{mainBoard.registerNewObjective("Thirst", "dummy");}
	    catch (IllegalArgumentException e){}
	    try{mainBoard.registerNewObjective("Fatigue", "dummy");}
	    catch (IllegalArgumentException e){}
	    try{mainBoard.registerNewObjective("Carbs", "dummy");}
	    catch (IllegalArgumentException e){}
	    try{mainBoard.registerNewObjective("Protein", "dummy");}
	    catch (IllegalArgumentException e){}
	    try{mainBoard.registerNewObjective("Salts", "dummy");}
	    catch (IllegalArgumentException e){}
	    try{mainBoard.registerNewObjective("BoardHunger", "dummy");}
	    catch (IllegalArgumentException e){}
	    try{mainBoard.registerNewObjective("BoardThirst", "dummy");}
	    catch (IllegalArgumentException e){}
	    try{mainBoard.registerNewObjective("BoardFatigue", "dummy");}
	    catch (IllegalArgumentException e){}
	    try{mainBoard.registerNewObjective("BoardNutrients", "dummy");}
	    catch (IllegalArgumentException e){}
		
		registerCommands();
		registerEvents();
		removeRecipes();
		customRecipes();
		if(settings.getBoolean("LegendaryItems.BlazeSword"))
			BlazeSword();
		if(settings.getBoolean("LegendaryItems.GiantBlade"))
			GiantBlade();
		if(settings.getBoolean("LegendaryItems.ObsidianMace"))
			ObsidianMace();
		if(settings.getBoolean("LegendaryItems.ValkyrieAxe"))
			Valkyrie();
		if(settings.getBoolean("LegendaryItems.QuartzPickaxe"))
			QuartzPickaxe();
		if(settings.getBoolean("Mechanics.Thirst.Enabled"))
			PlayerStatus();
		if(settings.getBoolean("Mechanics.BedFatigueLevel"))
			DaysNoSleep();
		if(settings.getBoolean("Mechanics.FoodDiversity"))
			FoodDiversity();
		ResetStatusScoreboard(settings.getBoolean("Mechanics.StatusScoreboard"));
		//BackpackCheck(); //Testing Backpack

		logger.info(pdfFile.getName() + " has been enabled.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		getServer().getScheduler().cancelTasks(this);
		getServer().resetRecipes();
		usingPlayers = new ArrayList<Player>();
		//Avoid WorkbenchShare glitch
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if (p.hasMetadata("shared_workbench"))
			{
				Block workbench = (p.getMetadata("shared_workbench").get(0).value() instanceof Block) ? (Block)p.getMetadata("shared_workbench").get(0).value() : null;
				
				if(workbench != null && workbench.getType() == Material.WORKBENCH)
				{
					if (workbench.hasMetadata("shared_players"))
						workbench.removeMetadata("shared_players", Survival.instance);
					else
						p.getOpenInventory().getTopInventory().clear();
					
					p.closeInventory();	
				}
				
				p.removeMetadata("shared_workbench", Survival.instance);
			}
		}
		
		logger.info(pdfFile.getName() + " has been disabled.");
	}
	
	public static HashMap<String, String> copyToStringValueMap(Map<String, Object> input)
	{
	    HashMap<String, String> ret = new HashMap<>();
	    for (Map.Entry<String, Object> entry : input.entrySet())
	    {
	        ret.put(entry.getKey(), (String) entry.getValue());
	    }
	    return ret;
	}
	
	public static Location lookAt(Location loc, Location lookat)
	{
        //Clone the loc to prevent applied changes to the input loc
        loc = loc.clone();
 
        // Values of change in distance (make it relative)
        double dx = lookat.getX() - loc.getX();
        double dy = lookat.getY() - loc.getY();
        double dz = lookat.getZ() - loc.getZ();
 
        // Set yaw
        if (dx != 0)
        {
            // Set yaw start value based on dx
            if (dx < 0)
                loc.setYaw((float) (1.5 * Math.PI));
            else
                loc.setYaw((float) (0.5 * Math.PI));
            
            loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
        }
        else if (dz < 0)
            loc.setYaw((float) Math.PI);
 
        // Get the distance from dx/dz
        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));
 
        // Set pitch
        loc.setPitch((float) -Math.atan(dy / dxz));
 
        // Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);
 
        return loc;
    }
	
	public void registerCommands()
	{
		getCommand("recipes").setExecutor(new Recipes());
		if(LocalChatDist > -1)
			getCommand("togglechat").setExecutor(new ToggleChat());
		getCommand("status").setExecutor(new Status());
		getCommand("reload-survival").setExecutor(new Reload());
		if(settings.getBoolean("Mechanics.SnowGenerationRevamp"))
			getCommand("snowgen").setExecutor(new SnowGen());
	}
	
	public void registerEvents()
	{
		PluginManager pm = getServer().getPluginManager();
		
		if(settings.getBoolean("Survival.Enabled"))
		{
			pm.registerEvents(new BlockBreak(), this);
			pm.registerEvents(new BlockPlace(), this);
			pm.registerEvents(new FirestrikerClick(), this);
			pm.registerEvents(new ShivPoison(), this);
			pm.registerEvents(new WaterBowl(), this);
			//pm.registerEvents(new Backpack(), this);
		}
		pm.registerEvents(new NoAnvil(), this);
		if(settings.getBoolean("Mechanics.Bow"))
			pm.registerEvents(new Bow(), this);
		if(settings.getBoolean("Mechanics.GrapplingHook"))
			pm.registerEvents(new GrapplingHook(), this);
		if(settings.getBoolean("LegendaryItems.ObsidianMace"))
			pm.registerEvents(new ObsidianMaceWeakness(), this);
		if(settings.getBoolean("LegendaryItems.ValkyrieAxe"))
			pm.registerEvents(new Valkyrie(), this);
		if(settings.getBoolean("LegendaryItems.GiantBlade"))
			pm.registerEvents(new GiantBlade(), this);
		if(settings.getBoolean("LegendaryItems.BlazeSword"))
			pm.registerEvents(new BlazeSword(), this);
		if(LocalChatDist > -1)
			pm.registerEvents(new LocalChat(), this);
		if(settings.getBoolean("Mechanics.CompassWaypoint"))
			pm.registerEvents(new CompassWaypoint(), this);
		if(settings.getBoolean("Mechanics.MedicalKit"))
			pm.registerEvents(new MedicKit(), this);

		pm.registerEvents(new WaterBottleCrafting(), this);
		pm.registerEvents(new SpecialItemInteractCancel(), this);
		
		pm.registerEvents(new SetResourcePack(), this);

		if(settings.getBoolean("Mechanics.RawMeatHunger"))
			pm.registerEvents(new RawMeatHunger(), this);
		if(settings.getBoolean("Mechanics.Thirst.Enabled"))
		{
			pm.registerEvents(new Consume(), this);
			if(settings.getBoolean("Mechanics.Thirst.PurifyWater"))
				pm.registerEvents(new CauldronWaterBottle(), this);
		}
		if(settings.getBoolean("Mechanics.PoisonousPotato"))
			pm.registerEvents(new PoisonousPotato(), this);
		if(settings.getBoolean("Mechanics.SharedWorkbench"))
			pm.registerEvents(new WorkbenchShare(), this);
		if(settings.getBoolean("Mechanics.Chairs.Enabled"))
			pm.registerEvents(new Chairs(), this);
		if(settings.getBoolean("Mechanics.CookieHealthBoost"))
			pm.registerEvents(new CookieHealthBoost(), this);
		if(settings.getBoolean("Mechanics.BeetrootStrength"))
			pm.registerEvents(new BeetrootStrength(), this);
		if(settings.getBoolean("Mechanics.Clownfish"))
			pm.registerEvents(new Clownfish(), this);
		if(settings.getBoolean("Mechanics.LivingSlime"))
			pm.registerEvents(new LivingSlime(), this);
		if(settings.getBoolean("Mechanics.BedFatigueLevel"))
			pm.registerEvents(new BedFatigue(), this);
		if(settings.getBoolean("Mechanics.FoodDiversity"))
			pm.registerEvents(new FoodDiversityConsume(), this);
		if(settings.getBoolean("Mechanics.RecurveBow"))
			pm.registerEvents(new RecurvedBow(), this);
		if(settings.getBoolean("Mechanics.StatusScoreboard"))
			pm.registerEvents(new ScoreboardStats(), this);
		if(settings.getBoolean("Mechanics.SnowballRevamp"))
			pm.registerEvents(new SnowballThrow(), this);
		if(settings.getBoolean("Mechanics.SnowGenerationRevamp"))
			pm.registerEvents(new SnowGeneration(), this);
		pm.registerEvents(new ChickenSpawn(), this);
	}
	
	public void removeRecipes()
	{
		List<Recipe> backup = new ArrayList<Recipe>();
		
	    Iterator<Recipe> a = getServer().recipeIterator();

	    while(a.hasNext())
	    {
	        Recipe recipe = a.next();
	        backup.add(recipe);
	    }
	    
	    Iterator<Recipe> it = backup.iterator();
	    
		while(it.hasNext())
		{
			Recipe recipe = it.next();
			if (recipe != null)
			{
				switch(recipe.getResult().getType())
				{
					case WOOD_HOE:
					case WOOD_AXE:
					case WOOD_PICKAXE:
					case WOOD_SPADE:
					case WOOD_SWORD:
					case FURNACE:
					case WORKBENCH:
					case CHEST:
					case BEETROOT_SOUP:
						if(settings.getBoolean("Survival.Enabled"))
							it.remove();
						break;
					case TORCH:
						if(settings.getBoolean("Survival.Enabled") && settings.getBoolean("Survival.Torch"))
							it.remove();
						break;
					
					case GOLD_HOE:
						if(settings.getBoolean("LegendaryItems.GiantBlade"))
							it.remove();
						break;
					case GOLD_AXE:
						if(settings.getBoolean("LegendaryItems.ValkyrieAxe"))
							it.remove();
						break;
					case GOLD_PICKAXE:
						if(settings.getBoolean("LegendaryItems.QuartzPickaxe"))
							it.remove();
						break;
					case GOLD_SPADE:
						if(settings.getBoolean("LegendaryItems.ObsidianMace"))
							it.remove();
						break;
					case GOLD_SWORD:
						if(settings.getBoolean("LegendaryItems.BlazeSword"))
							it.remove();
						break;
					
					case GOLD_BOOTS:
					case GOLD_CHESTPLATE:
					case GOLD_HELMET:
					case GOLD_LEGGINGS:
						if(settings.getBoolean("LegendaryItems.GoldArmorBuff"))
							it.remove();
						break;
					
					case IRON_BOOTS:
					case IRON_CHESTPLATE:
					case IRON_HELMET:
					case IRON_LEGGINGS:
					case DIAMOND_BOOTS:
					case DIAMOND_CHESTPLATE:
					case DIAMOND_HELMET:
					case DIAMOND_LEGGINGS:
						if(settings.getBoolean("Mechanics.SlowArmor"))
							it.remove();
						break;
						
					case FISHING_ROD:
						if(settings.getBoolean("Recipes.FishingRod"))
							it.remove();
						break;
						
					case IRON_NUGGET:
					case IRON_INGOT:
						if(settings.getBoolean("Mechanics.ReducedIronNugget"))
							it.remove();
						break;
						
					case GOLD_NUGGET:
					case GOLD_INGOT:
						if(settings.getBoolean("Mechanics.ReducedGoldNugget"))
							it.remove();
						break;
						
					case BREAD:
						if(settings.getBoolean("Mechanics.FarmingProducts.Bread"))
							it.remove();
						break;
					case COOKIE:
						if(settings.getBoolean("Mechanics.FarmingProducts.Cookie"))
							it.remove();
						break;
						
					case STONE:
						switch(recipe.getResult().getDurability())
						{
							case (short)1:
								if(settings.getBoolean("Recipes.Granite"))
									it.remove();
							break;
							case (short)3:
								if(settings.getBoolean("Recipes.Diorite"))
									it.remove();
							break;
							case (short)5:
								if(settings.getBoolean("Recipes.Andesite"))
									it.remove();
							break;
						}
						break;
					case SNOW:
					case SNOW_BLOCK:
						if(settings.getBoolean("Mechanics.SnowballRevamp"))
							it.remove();
						break;
					default:
				}
			}
		}
		
		getServer().clearRecipes();
		
		for (Recipe r : backup)
		{
			getServer().addRecipe(r);
		}
	}
	
	public void customRecipes()
	{	
		//Hatchet
		ItemStack i_hatchet = new ItemStack(Material.WOOD_AXE, 1);
		ItemMeta hatchetMeta= i_hatchet.getItemMeta();
		hatchetMeta.setDisplayName(ChatColor.RESET + Words.get("Hatchet"));
		i_hatchet.setItemMeta(hatchetMeta);
		
		//Mattock
		ItemStack i_mattock = new ItemStack(Material.WOOD_PICKAXE, 1);
		ItemMeta mattockMeta= i_mattock.getItemMeta();
		mattockMeta.setDisplayName(ChatColor.RESET + Words.get("Mattock"));
		i_mattock.setItemMeta(mattockMeta);
		
		//Firestriker
		ItemStack i_firestriker = new ItemStack(Material.WOOD_SPADE, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_firestriker = CraftItemStack.asNMSCopy(i_firestriker);
        NBTTagCompound compound_firestriker = nmsStack_firestriker.getTag();
        if (compound_firestriker == null)
        {
        	compound_firestriker = new NBTTagCompound();
            nmsStack_firestriker.setTag(compound_firestriker);
            compound_firestriker = nmsStack_firestriker.getTag();
        }
        NBTTagList modifiers_firestriker = new NBTTagList();
        float firestriker_spd = 4f;
        
        NBTTagCompound atkSpd_firestriker = new NBTTagCompound();
        atkSpd_firestriker.set("Slot", new NBTTagString("mainhand"));
        atkSpd_firestriker.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        atkSpd_firestriker.set("Name", new NBTTagString("generic.attackSpeed"));
        atkSpd_firestriker.set("Amount", new NBTTagFloat(firestriker_spd - 4 /*Attack Speed*/));
        atkSpd_firestriker.set("Operation", new NBTTagInt(0));
        atkSpd_firestriker.set("UUIDLeast", new NBTTagInt(312465));
        atkSpd_firestriker.set("UUIDMost", new NBTTagInt(4124));
        modifiers_firestriker.add(atkSpd_firestriker);
        
        compound_firestriker.setInt("HideFlags", 2);
        
        compound_firestriker.set("AttributeModifiers", modifiers_firestriker);
        nmsStack_firestriker.setTag(compound_firestriker);
        i_firestriker = CraftItemStack.asBukkitCopy(nmsStack_firestriker);
		
		ItemMeta firestrikerMeta= i_firestriker.getItemMeta();
		firestrikerMeta.setDisplayName(ChatColor.RESET + Words.get("Firestriker"));
		i_firestriker.setItemMeta(firestrikerMeta);
		
		//Shiv
		ItemStack i_shiv = new ItemStack(Material.WOOD_HOE, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_shiv = CraftItemStack.asNMSCopy(i_shiv);
        NBTTagCompound compound_shiv = nmsStack_shiv.getTag();
        if (compound_shiv == null)
        {
        	compound_shiv = new NBTTagCompound();
            nmsStack_shiv.setTag(compound_shiv);
            compound_shiv = nmsStack_shiv.getTag();
        }
        NBTTagList modifiers_shiv = new NBTTagList();
        int shiv_dmg = 4;
        float shiv_spd = 1.8f;
        
        NBTTagCompound damage_shiv = new NBTTagCompound();
        damage_shiv.set("Slot", new NBTTagString("mainhand"));
        damage_shiv.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damage_shiv.set("Name", new NBTTagString("generic.attackDamage"));
        damage_shiv.set("Amount", new NBTTagInt(shiv_dmg - 1 /*Attack Damage*/));
        damage_shiv.set("Operation", new NBTTagInt(0));
        damage_shiv.set("UUIDLeast", new NBTTagInt(894654));
        damage_shiv.set("UUIDMost", new NBTTagInt(2872));
        modifiers_shiv.add(damage_shiv);
        
        NBTTagCompound atkSpd_shiv = new NBTTagCompound();
        atkSpd_shiv.set("Slot", new NBTTagString("mainhand"));
        atkSpd_shiv.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        atkSpd_shiv.set("Name", new NBTTagString("generic.attackSpeed"));
        atkSpd_shiv.set("Amount", new NBTTagFloat(shiv_spd - 4 /*Attack Speed*/));
        atkSpd_shiv.set("Operation", new NBTTagInt(0));
        atkSpd_shiv.set("UUIDLeast", new NBTTagInt(675696));
        atkSpd_shiv.set("UUIDMost", new NBTTagInt(34216));
        modifiers_shiv.add(atkSpd_shiv);

        compound_shiv.setInt("HideFlags", 2);
        
        compound_shiv.set("AttributeModifiers", modifiers_shiv);
        nmsStack_shiv.setTag(compound_shiv);
        i_shiv = CraftItemStack.asBukkitCopy(nmsStack_shiv);
        
		ItemMeta shivMeta= i_shiv.getItemMeta();
		shivMeta.setDisplayName(ChatColor.RESET + Words.get("Shiv"));
		shivMeta.setLore
		(
			Arrays.asList
			(
				ChatColor.RESET + "" + Words.get("§2Poisoned: Poison enemy when hit"),
				"",
				ChatColor.RESET + "" + ChatColor.GRAY + Words.get("When in main hand:"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + shiv_spd + " " + Words.get("Attack Speed"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + shiv_dmg + " " + Words.get("Attack Damage"),
				"",
				ChatColor.RESET + "" + ChatColor.GRAY + Words.get("When in off hand:"),
				ChatColor.RESET + "" + " " + Words.get("§2Poisoning Effect §7retains"),
				ChatColor.RESET + "" + " " + Words.get("§7> Reduce chance by 50%")
			)
		);
		i_shiv.setItemMeta(shivMeta);
		
		//Hammer
		ItemStack i_hammer = new ItemStack(Material.WOOD_SWORD, 1);
		ItemMeta hammerMeta= i_hammer.getItemMeta();
		hammerMeta.setDisplayName(ChatColor.RESET + Words.get("Hammer"));
		i_hammer.setItemMeta(hammerMeta);
		
		//Valkyrie's Axe
		ItemStack i_gAxe = new ItemStack(Material.GOLD_AXE, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_gAxe = CraftItemStack.asNMSCopy(i_gAxe);
        NBTTagCompound compound_gAxe = nmsStack_gAxe.getTag();
        if (compound_gAxe == null)
        {
        	compound_gAxe = new NBTTagCompound();
            nmsStack_gAxe.setTag(compound_gAxe);
            compound_gAxe = nmsStack_gAxe.getTag();
        }
        NBTTagList modifiers_gAxe = new NBTTagList();
        int gAxe_spd = 1;
        int gAxe_dmg = 8;
        
        NBTTagCompound atkSpd_gAxe = new NBTTagCompound();
        atkSpd_gAxe.set("Slot", new NBTTagString("mainhand"));
        atkSpd_gAxe.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        atkSpd_gAxe.set("Name", new NBTTagString("generic.attackSpeed"));
        atkSpd_gAxe.set("Amount", new NBTTagFloat(gAxe_spd - 4 /*Attack Speed*/));
        atkSpd_gAxe.set("Operation", new NBTTagInt(0));
        atkSpd_gAxe.set("UUIDLeast", new NBTTagInt(11));
        atkSpd_gAxe.set("UUIDMost", new NBTTagInt(2));
        modifiers_gAxe.add(atkSpd_gAxe);
        
        compound_gAxe.setInt("HideFlags", 2);
        
        compound_gAxe.set("AttributeModifiers", modifiers_gAxe);
        nmsStack_gAxe.setTag(compound_gAxe);
        i_gAxe = CraftItemStack.asBukkitCopy(nmsStack_gAxe);
		
		ItemMeta gAxeMeta= i_gAxe.getItemMeta();
		gAxeMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + Words.get("Valkyrie's Axe"));
		gAxeMeta.setLore
		(
				Arrays.asList
				(
					ChatColor.RESET + "" + Words.get("§cUnable to dual-wield with Valkyrie's Axe"),
					"",
					ChatColor.RESET + "" + ChatColor.GRAY + Words.get("When in main hand:"),
					ChatColor.RESET + "" + ChatColor.GRAY + " " + gAxe_spd + " " + Words.get("Attack Speed"),
					ChatColor.RESET + "" + ChatColor.GRAY + " " + gAxe_dmg + " " + Words.get("Attack Damage"),
					ChatColor.RESET + "" + "  " + Words.get("§aSpin: Spin your axe in circle, attack all nearby enemies"),
					ChatColor.RESET + "" + "  " + Words.get("§7> Cooldown: 1 second"),
					ChatColor.RESET + "" + "  " + Words.get("§7> Decreases hunger value")
				)
			);
		gAxeMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		i_gAxe.setItemMeta(gAxeMeta);
		
		//Quartz Pickaxe
		ItemStack i_gPickaxe = new ItemStack(Material.GOLD_PICKAXE, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_gPickaxe = CraftItemStack.asNMSCopy(i_gPickaxe);
        NBTTagCompound compound_gPickaxe = nmsStack_gPickaxe.getTag();
        if (compound_gPickaxe == null)
        {
        	compound_gPickaxe = new NBTTagCompound();
            nmsStack_gPickaxe.setTag(compound_gPickaxe);
            compound_gPickaxe = nmsStack_gPickaxe.getTag();
        }
        NBTTagList modifiers_gPickaxe = new NBTTagList();
        int gPickaxe_dmg = 5;
        float gPickaxe_spd = 0.8f;
        
        NBTTagCompound damage_gPickaxe = new NBTTagCompound();
        damage_gPickaxe.set("Slot", new NBTTagString("mainhand"));
        damage_gPickaxe.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damage_gPickaxe.set("Name", new NBTTagString("generic.attackDamage"));
        damage_gPickaxe.set("Amount", new NBTTagInt(gPickaxe_dmg - 1 /*Attack Damage*/));
        damage_gPickaxe.set("Operation", new NBTTagInt(0));
        damage_gPickaxe.set("UUIDLeast", new NBTTagInt(11154));
        damage_gPickaxe.set("UUIDMost", new NBTTagInt(441));
        modifiers_gPickaxe.add(damage_gPickaxe);
        
        NBTTagCompound atkSpd_gPickaxe = new NBTTagCompound();
        atkSpd_gPickaxe.set("Slot", new NBTTagString("mainhand"));
        atkSpd_gPickaxe.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        atkSpd_gPickaxe.set("Name", new NBTTagString("generic.attackSpeed"));
        atkSpd_gPickaxe.set("Amount", new NBTTagFloat(gPickaxe_spd - 4 /*Attack Speed*/));
        atkSpd_gPickaxe.set("Operation", new NBTTagInt(0));
        atkSpd_gPickaxe.set("UUIDLeast", new NBTTagInt(11));
        atkSpd_gPickaxe.set("UUIDMost", new NBTTagInt(2));
        modifiers_gPickaxe.add(atkSpd_gPickaxe);
        
        compound_gPickaxe.setInt("HideFlags", 2);
        
        compound_gPickaxe.set("AttributeModifiers", modifiers_gPickaxe);
        nmsStack_gPickaxe.setTag(compound_gPickaxe);
        i_gPickaxe = CraftItemStack.asBukkitCopy(nmsStack_gPickaxe);
        
		ItemMeta gPickaxeMeta= i_gPickaxe.getItemMeta();
		gPickaxeMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + Words.get("Quartz Breaker"));
		gPickaxeMeta.setLore
		(
			Arrays.asList
			(
				"",
				ChatColor.RESET + "" + ChatColor.GRAY + Words.get("When in main hand:"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + gPickaxe_spd + " " + Words.get("Attack Speed"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + gPickaxe_dmg + " " +  Words.get("Attack Damage"),
				ChatColor.RESET + "" + " " + Words.get("§eHaste")
			)
		);
		gPickaxeMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
		i_gPickaxe.setItemMeta(gPickaxeMeta);
		
		//Obsidian Mace
		ItemStack i_gSpade = new ItemStack(Material.GOLD_SPADE, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_gSpade = CraftItemStack.asNMSCopy(i_gSpade);
        NBTTagCompound compound_gSpade = nmsStack_gSpade.getTag();
        if (compound_gSpade == null)
        {
        	compound_gSpade = new NBTTagCompound();
            nmsStack_gSpade.setTag(compound_gSpade);
            compound_gSpade = nmsStack_gSpade.getTag();
        }
        NBTTagList modifiers_gSpade = new NBTTagList();
        int gSpade_dmg = 4;
        float gSpade_spd = 0.8f;
        float gSpade_knockbackPercent = 0.5f;
        
        NBTTagCompound damage_gSpade = new NBTTagCompound();
        damage_gSpade.set("Slot", new NBTTagString("mainhand"));
        damage_gSpade.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damage_gSpade.set("Name", new NBTTagString("generic.attackDamage"));
        damage_gSpade.set("Amount", new NBTTagInt(gSpade_dmg - 1 /*Attack Damage*/));
        damage_gSpade.set("Operation", new NBTTagInt(0));
        damage_gSpade.set("UUIDLeast", new NBTTagInt(11154));
        damage_gSpade.set("UUIDMost", new NBTTagInt(441));
        modifiers_gSpade.add(damage_gSpade);
        
        NBTTagCompound atkSpd_gSpade = new NBTTagCompound();
        atkSpd_gSpade.set("Slot", new NBTTagString("mainhand"));
        atkSpd_gSpade.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        atkSpd_gSpade.set("Name", new NBTTagString("generic.attackSpeed"));
        atkSpd_gSpade.set("Amount", new NBTTagFloat(gSpade_spd - 4 /*Attack Speed*/));
        atkSpd_gSpade.set("Operation", new NBTTagInt(0));
        atkSpd_gSpade.set("UUIDLeast", new NBTTagInt(11));
        atkSpd_gSpade.set("UUIDMost", new NBTTagInt(2));
        modifiers_gSpade.add(atkSpd_gSpade);
        
        NBTTagCompound knockback_gSpade = new NBTTagCompound();
        knockback_gSpade.set("Slot", new NBTTagString("mainhand"));
        knockback_gSpade.set("AttributeName", new NBTTagString("generic.knockbackResistance"));
        knockback_gSpade.set("Name", new NBTTagString("generic.knockbackResistance"));
        knockback_gSpade.set("Amount", new NBTTagFloat(gSpade_knockbackPercent /*Knockback Resistance*/));
        knockback_gSpade.set("Operation", new NBTTagInt(1));
        knockback_gSpade.set("UUIDLeast", new NBTTagInt(451215));
        knockback_gSpade.set("UUIDMost", new NBTTagInt(6156));
        modifiers_gSpade.add(knockback_gSpade);
        
        compound_gSpade.setInt("HideFlags", 2);
        
        compound_gSpade.set("AttributeModifiers", modifiers_gSpade);
        nmsStack_gSpade.setTag(compound_gSpade);
        i_gSpade = CraftItemStack.asBukkitCopy(nmsStack_gSpade);
		
		ItemMeta gSpadeMeta= i_gSpade.getItemMeta();
		gSpadeMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + Words.get("Obsidian Mace"));
		gSpadeMeta.setLore
		(
			Arrays.asList
			(
				ChatColor.RESET + "" + Words.get("§dCripple: Enemies hit become §8weakened"),
				ChatColor.RESET + "" + Words.get("§aDrain: Gains §b2 hearts§a per hit"),
				"",
				ChatColor.RESET + "" + ChatColor.GRAY + Words.get("When in main hand:"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + gSpade_spd + " " + Words.get("Attack Speed"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + gSpade_dmg + " " + Words.get("Attack Damage"),
				ChatColor.RESET + "" + " " + Words.get("§8Exhausted: §cSlowness II"),
				ChatColor.RESET + "" + " " + Words.get("§7> Expires after disarming for 5 seconds"),
				ChatColor.RESET + "" + " " + Words.get("§9+50% Knockback Resistance")
			)
		);
		gSpadeMeta.addEnchant(Enchantment.KNOCKBACK, 3, true);
		gSpadeMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		i_gSpade.setItemMeta(gSpadeMeta);
		
		//Ender Giant Blade
		ItemStack i_gHoe = new ItemStack(Material.GOLD_HOE, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_gHoe = CraftItemStack.asNMSCopy(i_gHoe);
        NBTTagCompound compound_gHoe = nmsStack_gHoe.getTag();
        if (compound_gHoe == null)
        {
        	compound_gHoe = new NBTTagCompound();
            nmsStack_gHoe.setTag(compound_gHoe);
            compound_gHoe = nmsStack_gHoe.getTag();
        }
        NBTTagList modifiers_gHoe = new NBTTagList();
        int gHoe_dmg = 8;
        int gHoe_spd = 1;
        float gHoe_move = -0.5f;

        NBTTagCompound damage_gHoe = new NBTTagCompound();
        damage_gHoe.set("Slot", new NBTTagString("mainhand"));
        damage_gHoe.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damage_gHoe.set("Name", new NBTTagString("generic.attackDamage"));
        damage_gHoe.set("Amount", new NBTTagInt(gHoe_dmg - 1 /*Attack Damage*/));
        damage_gHoe.set("Operation", new NBTTagInt(0));
        damage_gHoe.set("UUIDLeast", new NBTTagInt(485412));
        damage_gHoe.set("UUIDMost", new NBTTagInt(5544));
        modifiers_gHoe.add(damage_gHoe);
        
        NBTTagCompound atkSpd_gHoe = new NBTTagCompound();
        atkSpd_gHoe.set("Slot", new NBTTagString("mainhand"));
        atkSpd_gHoe.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        atkSpd_gHoe.set("Name", new NBTTagString("generic.attackSpeed"));
        atkSpd_gHoe.set("Amount", new NBTTagFloat(gHoe_spd - 4 /*Attack Speed*/));
        atkSpd_gHoe.set("Operation", new NBTTagInt(0));
        atkSpd_gHoe.set("UUIDLeast", new NBTTagInt(884654));
        atkSpd_gHoe.set("UUIDMost", new NBTTagInt(9872));
        modifiers_gHoe.add(atkSpd_gHoe);
        
        NBTTagCompound moveSpd_gHoe = new NBTTagCompound();
        moveSpd_gHoe.set("Slot", new NBTTagString("offhand"));
        moveSpd_gHoe.set("AttributeName", new NBTTagString("generic.movementSpeed"));
        moveSpd_gHoe.set("Name", new NBTTagString("generic.movementSpeed"));
        moveSpd_gHoe.set("Amount", new NBTTagFloat(gHoe_move /*Movement Speed*/));
        moveSpd_gHoe.set("Operation", new NBTTagInt(1));
        moveSpd_gHoe.set("UUIDLeast", new NBTTagInt(88454));
        moveSpd_gHoe.set("UUIDMost", new NBTTagInt(872));
        modifiers_gHoe.add(moveSpd_gHoe);
        
        compound_gHoe.setInt("HideFlags", 2);
        
        compound_gHoe.set("AttributeModifiers", modifiers_gHoe);
        nmsStack_gHoe.setTag(compound_gHoe);
        i_gHoe = CraftItemStack.asBukkitCopy(nmsStack_gHoe);
		
		ItemMeta gHoeMeta= i_gHoe.getItemMeta();
		gHoeMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + Words.get("Ender Giant Blade"));
		gHoeMeta.setLore
		(
			Arrays.asList
			(
				ChatColor.RESET + "" + Words.get("§cUnable to dual-wield with Giant Blade"),
				"",
				ChatColor.RESET + "" + ChatColor.GRAY + Words.get("When in main hand:"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + gHoe_spd + " " + Words.get("Attack Speed"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + gHoe_dmg + " " + Words.get("Attack Damage"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + Words.get("Right Click when sprinting:"),
				ChatColor.RESET + "" + "  " + Words.get("§aCharge: Sprint forward, attack enemies infront"),
				ChatColor.RESET + "" + "  " + Words.get("§7> Cooldown: 5 seconds"),
				ChatColor.RESET + "" + "  " + Words.get("§7> Decreases hunger value"),
				"",
				ChatColor.RESET + "" + ChatColor.GRAY + Words.get("When in off hand:"),
				ChatColor.RESET + "" + " " + Words.get("§aHalf-Shield: Gains §4Resistance II"),
				ChatColor.RESET + "" + " " + Words.get("§7> Reflecting incoming damage by 40%")
			)
		);
		gHoeMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		i_gHoe.setItemMeta(gHoeMeta);
		
		//Blaze Sword
		ItemStack i_gSword = new ItemStack(Material.GOLD_SWORD, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_gSword = CraftItemStack.asNMSCopy(i_gSword);
        NBTTagCompound compound_gSword = nmsStack_gSword.getTag();
        if (compound_gSword == null)
        {
        	compound_gSword = new NBTTagCompound();
            nmsStack_gSword.setTag(compound_gSword);
            compound_gSword = nmsStack_gSword.getTag();
        }
        NBTTagList modifiers_gSword = new NBTTagList();
        int gSword_dmg = 6;
        float gSword_spd = 1.6f;
        int gSword_health = -6;
        
        NBTTagCompound damage_gSword = new NBTTagCompound();
        damage_gSword.set("Slot", new NBTTagString("mainhand"));
        damage_gSword.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damage_gSword.set("Name", new NBTTagString("generic.attackDamage"));
        damage_gSword.set("Amount", new NBTTagInt(gSword_dmg - 1 /*Attack Damage*/));
        damage_gSword.set("Operation", new NBTTagInt(0));
        damage_gSword.set("UUIDLeast", new NBTTagInt(11154));
        damage_gSword.set("UUIDMost", new NBTTagInt(441));
        modifiers_gSword.add(damage_gSword);
        
        NBTTagCompound atkSpd_gSword = new NBTTagCompound();
        atkSpd_gSword.set("Slot", new NBTTagString("mainhand"));
        atkSpd_gSword.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        atkSpd_gSword.set("Name", new NBTTagString("generic.attackSpeed"));
        atkSpd_gSword.set("Amount", new NBTTagFloat(gSword_spd - 4 /*Attack Speed*/));
        atkSpd_gSword.set("Operation", new NBTTagInt(0));
        atkSpd_gSword.set("UUIDLeast", new NBTTagInt(11));
        atkSpd_gSword.set("UUIDMost", new NBTTagInt(2));
        modifiers_gSword.add(atkSpd_gSword);
        
        NBTTagCompound health_gSword = new NBTTagCompound();
        health_gSword.set("Slot", new NBTTagString("mainhand"));
        health_gSword.set("AttributeName", new NBTTagString("generic.maxHealth"));
        health_gSword.set("Name", new NBTTagString("generic.maxHealth"));
        health_gSword.set("Amount", new NBTTagFloat(gSword_health /*Health*/));
        health_gSword.set("Operation", new NBTTagInt(0));
        health_gSword.set("UUIDLeast", new NBTTagInt(574145));
        health_gSword.set("UUIDMost", new NBTTagInt(54));
        modifiers_gSword.add(health_gSword);

        compound_gSword.setInt("HideFlags", 2);
        
        compound_gSword.set("AttributeModifiers", modifiers_gSword);
        nmsStack_gSword.setTag(compound_gSword);
        i_gSword = CraftItemStack.asBukkitCopy(nmsStack_gSword);
		
		ItemMeta gSwordMeta= i_gSword.getItemMeta();
		gSwordMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + Words.get("Blaze Sword"));
		gSwordMeta.setLore
		(
			Arrays.asList
		(
				"",
				ChatColor.RESET + "" + ChatColor.GRAY + Words.get("When in main hand:"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + gSword_spd + " " + Words.get("Attack Speed"),
				ChatColor.RESET + "" + ChatColor.GRAY + " " + gSword_dmg + " " + Words.get("Attack Damage"),
				ChatColor.RESET + "" + " " + Words.get("§6Fire Resistance"),
				ChatColor.RESET + "" + " " + Words.get("§cFiery: -3 Hearts"),
				"",
				ChatColor.RESET + "" + ChatColor.GRAY + Words.get("Right Click when sneaking:"),
				ChatColor.RESET + "" + " " + Words.get("§6Spread fire on the ground"),
				ChatColor.RESET + "" + " " + Words.get("§7> Costs 1 Durability")
			)
		);
		gSwordMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
		i_gSword.setItemMeta(gSwordMeta);
		
		//Reinforced Leather Boots
		ItemStack i_leatherBoots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_leatherBoots = CraftItemStack.asNMSCopy(i_leatherBoots);
        NBTTagCompound compound_leatherBoots = nmsStack_leatherBoots.getTag();
        if (compound_leatherBoots == null)
        {
        	compound_leatherBoots = new NBTTagCompound();
            nmsStack_leatherBoots.setTag(compound_leatherBoots);
            compound_leatherBoots = nmsStack_leatherBoots.getTag();
        }
        NBTTagList modifiers_leatherBoots = new NBTTagList();
        int leatherBoots_armor = 2;
        
        NBTTagCompound armor_leatherBoots = new NBTTagCompound();
        armor_leatherBoots.set("Slot", new NBTTagString("feet"));
        armor_leatherBoots.set("AttributeName", new NBTTagString("generic.armor"));
        armor_leatherBoots.set("Name", new NBTTagString("generic.armor"));
        armor_leatherBoots.set("Amount", new NBTTagInt(leatherBoots_armor /*Armour*/));
        armor_leatherBoots.set("Operation", new NBTTagInt(0));
        armor_leatherBoots.set("UUIDLeast", new NBTTagInt(32213));
        armor_leatherBoots.set("UUIDMost", new NBTTagInt(3232));
        modifiers_leatherBoots.add(armor_leatherBoots);
        
        compound_leatherBoots.set("AttributeModifiers", modifiers_leatherBoots);
        nmsStack_leatherBoots.setTag(compound_leatherBoots);
        i_leatherBoots = CraftItemStack.asBukkitCopy(nmsStack_leatherBoots);
		
		ItemMeta leatherBootsMeta= i_leatherBoots.getItemMeta();
		leatherBootsMeta.setDisplayName(ChatColor.RESET + Words.get("Reinforced Leather Boots"));
		
		i_leatherBoots.setItemMeta(leatherBootsMeta);
		
		//Reinforced Leather Tunic
		ItemStack i_leatherChestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
		
		ItemMeta leatherChestplateMeta= i_leatherChestplate.getItemMeta();
		leatherChestplateMeta.setDisplayName(ChatColor.RESET + Words.get("Reinforced Leather Tunic"));
		
		i_leatherChestplate.setItemMeta(leatherChestplateMeta);
		
		//Reinforced Leather Trousers
		ItemStack i_leatherLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
		
		ItemMeta leatherLeggingsMeta= i_leatherLeggings.getItemMeta();
		leatherLeggingsMeta.setDisplayName(ChatColor.RESET + Words.get("Reinforced Leather Trousers"));
		
		i_leatherLeggings.setItemMeta(leatherLeggingsMeta);
		
		//Reinforced Leather Helmet
		ItemStack i_leatherHelmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
		
		ItemMeta leatherHelmetMeta= i_leatherHelmet.getItemMeta();
		leatherHelmetMeta.setDisplayName(ChatColor.RESET + Words.get("Reinforced Leather Hat"));
		
		i_leatherHelmet.setItemMeta(leatherHelmetMeta);
		
		//Golden Sabatons
		ItemStack i_goldBoots = new ItemStack(Material.GOLD_BOOTS, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_goldBoots = CraftItemStack.asNMSCopy(i_goldBoots);
        NBTTagCompound compound_goldBoots = nmsStack_goldBoots.getTag();
        if (compound_goldBoots == null)
        {
        	compound_goldBoots = new NBTTagCompound();
            nmsStack_goldBoots.setTag(compound_goldBoots);
            compound_goldBoots = nmsStack_goldBoots.getTag();
        }
        NBTTagList modifiers_goldBoots = new NBTTagList();
        int goldBoots_armor = 1;
        
        NBTTagCompound armor_goldBoots = new NBTTagCompound();
        armor_goldBoots.set("Slot", new NBTTagString("feet"));
        armor_goldBoots.set("AttributeName", new NBTTagString("generic.armor"));
        armor_goldBoots.set("Name", new NBTTagString("generic.armor"));
        armor_goldBoots.set("Amount", new NBTTagInt(goldBoots_armor /*Armour*/));
        armor_goldBoots.set("Operation", new NBTTagInt(0));
        armor_goldBoots.set("UUIDLeast", new NBTTagInt(322453));
        armor_goldBoots.set("UUIDMost", new NBTTagInt(323));
        modifiers_goldBoots.add(armor_goldBoots);
        
        compound_goldBoots.set("AttributeModifiers", modifiers_goldBoots);
        nmsStack_goldBoots.setTag(compound_goldBoots);
        i_goldBoots = CraftItemStack.asBukkitCopy(nmsStack_goldBoots);
        
		ItemMeta goldBootsMeta= i_goldBoots.getItemMeta();
		goldBootsMeta.setDisplayName(ChatColor.RESET + Words.get("Golden Sabatons"));
		goldBootsMeta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
		
		i_goldBoots.setItemMeta(goldBootsMeta);
        
		//Golden Guard
  		ItemStack i_goldChestplate = new ItemStack(Material.GOLD_CHESTPLATE, 1);
  		
  		net.minecraft.server.v1_12_R1.ItemStack nmsStack_goldChestplate = CraftItemStack.asNMSCopy(i_goldChestplate);
	    NBTTagCompound compound_goldChestplate = nmsStack_goldChestplate.getTag();
	    if (compound_goldChestplate == null)
	    {
	    	compound_goldChestplate = new NBTTagCompound();
	        nmsStack_goldChestplate.setTag(compound_goldChestplate);
	        compound_goldChestplate = nmsStack_goldChestplate.getTag();
	    }
	    NBTTagList modifiers_goldChestplate = new NBTTagList();
	    int goldChestplate_armor = 3;

	    NBTTagCompound armor_goldChestplate = new NBTTagCompound();
	    armor_goldChestplate.set("Slot", new NBTTagString("chest"));
	    armor_goldChestplate.set("AttributeName", new NBTTagString("generic.armor"));
	    armor_goldChestplate.set("Name", new NBTTagString("generic.armor"));
	    armor_goldChestplate.set("Amount", new NBTTagInt(goldChestplate_armor /*Armour*/));
	    armor_goldChestplate.set("Operation", new NBTTagInt(0));
	    armor_goldChestplate.set("UUIDLeast", new NBTTagInt(322453));
	    armor_goldChestplate.set("UUIDMost", new NBTTagInt(323));
	    modifiers_goldChestplate.add(armor_goldChestplate);
	    
	    compound_goldChestplate.set("AttributeModifiers", modifiers_goldChestplate);
	    nmsStack_goldChestplate.setTag(compound_goldChestplate);
	    i_goldChestplate = CraftItemStack.asBukkitCopy(nmsStack_goldChestplate);
	    
		ItemMeta goldChestplateMeta= i_goldChestplate.getItemMeta();
		goldChestplateMeta.setDisplayName(ChatColor.RESET + Words.get("Golden Guard"));
		goldChestplateMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
		
		i_goldChestplate.setItemMeta(goldChestplateMeta);
        
		//Golden Greaves
  		ItemStack i_goldLeggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
  		
  		net.minecraft.server.v1_12_R1.ItemStack nmsStack_goldLeggings = CraftItemStack.asNMSCopy(i_goldLeggings);
	    NBTTagCompound compound_goldLeggings = nmsStack_goldLeggings.getTag();
	    if (compound_goldLeggings == null)
	    {
	    	compound_goldLeggings = new NBTTagCompound();
	        nmsStack_goldLeggings.setTag(compound_goldLeggings);
	        compound_goldLeggings = nmsStack_goldLeggings.getTag();
	    }
	    NBTTagList modifiers_goldLeggings = new NBTTagList();
	    int goldLeggings_armor = 2;
	    
	    NBTTagCompound armor_goldLeggings = new NBTTagCompound();
	    armor_goldLeggings.set("Slot", new NBTTagString("legs"));
	    armor_goldLeggings.set("AttributeName", new NBTTagString("generic.armor"));
	    armor_goldLeggings.set("Name", new NBTTagString("generic.armor"));
	    armor_goldLeggings.set("Amount", new NBTTagInt(goldLeggings_armor /*Armour*/));
	    armor_goldLeggings.set("Operation", new NBTTagInt(0));
	    armor_goldLeggings.set("UUIDLeast", new NBTTagInt(322453));
	    armor_goldLeggings.set("UUIDMost", new NBTTagInt(323));
	    modifiers_goldLeggings.add(armor_goldLeggings);
	    
	    compound_goldLeggings.set("AttributeModifiers", modifiers_goldLeggings);
	    nmsStack_goldLeggings.setTag(compound_goldLeggings);
	    i_goldLeggings = CraftItemStack.asBukkitCopy(nmsStack_goldLeggings);
	    
		ItemMeta goldLeggingsMeta= i_goldLeggings.getItemMeta();
		goldLeggingsMeta.setDisplayName(ChatColor.RESET + Words.get("Golden Greaves"));
		goldLeggingsMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
		
		i_goldLeggings.setItemMeta(goldLeggingsMeta);
		
		//Golden Crown
		ItemStack i_goldHelmet = new ItemStack(Material.GOLD_HELMET, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_goldHelmet = CraftItemStack.asNMSCopy(i_goldHelmet);
        NBTTagCompound compound_goldHelmet = nmsStack_goldHelmet.getTag();
        if (compound_goldHelmet == null)
        {
        	compound_goldHelmet = new NBTTagCompound();
            nmsStack_goldHelmet.setTag(compound_goldHelmet);
            compound_goldHelmet = nmsStack_goldHelmet.getTag();
        }
        NBTTagList modifiers_goldHelmet = new NBTTagList();
        int goldHelmet_armor = 1;

        NBTTagCompound armor_goldHelmet = new NBTTagCompound();
        armor_goldHelmet.set("Slot", new NBTTagString("head"));
        armor_goldHelmet.set("AttributeName", new NBTTagString("generic.armor"));
        armor_goldHelmet.set("Name", new NBTTagString("generic.armor"));
        armor_goldHelmet.set("Amount", new NBTTagInt(goldHelmet_armor /*Armour*/));
        armor_goldHelmet.set("Operation", new NBTTagInt(0));
        armor_goldHelmet.set("UUIDLeast", new NBTTagInt(322453));
        armor_goldHelmet.set("UUIDMost", new NBTTagInt(323));
        modifiers_goldHelmet.add(armor_goldHelmet);
        
        compound_goldHelmet.set("AttributeModifiers", modifiers_goldHelmet);
        nmsStack_goldHelmet.setTag(compound_goldHelmet);
        i_goldHelmet = CraftItemStack.asBukkitCopy(nmsStack_goldHelmet);
		
		ItemMeta goldHelmetMeta= i_goldHelmet.getItemMeta();
		goldHelmetMeta.setDisplayName(ChatColor.RESET + Words.get("Golden Crown"));
		goldHelmetMeta.addEnchant(Enchantment.MENDING, 1, true);
		
		i_goldHelmet.setItemMeta(goldHelmetMeta);
		
		//Iron Boots
		ItemStack i_ironBoots = new ItemStack(Material.IRON_BOOTS, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_ironBoots = CraftItemStack.asNMSCopy(i_ironBoots);
        NBTTagCompound compound_ironBoots = nmsStack_ironBoots.getTag();
        if (compound_ironBoots == null)
        {
        	compound_ironBoots = new NBTTagCompound();
            nmsStack_ironBoots.setTag(compound_ironBoots);
            compound_ironBoots = nmsStack_ironBoots.getTag();
        }
        NBTTagList modifiers_ironBoots = new NBTTagList();
        int ironBoots_armor = 2;
        float ironBoots_moveSpd = -0.020f;

        NBTTagCompound armor_ironBoots = new NBTTagCompound();
        armor_ironBoots.set("Slot", new NBTTagString("feet"));
        armor_ironBoots.set("AttributeName", new NBTTagString("generic.armor"));
        armor_ironBoots.set("Name", new NBTTagString("generic.armor"));
        armor_ironBoots.set("Amount", new NBTTagInt(ironBoots_armor /*Armour*/));
        armor_ironBoots.set("Operation", new NBTTagInt(0));
        armor_ironBoots.set("UUIDLeast", new NBTTagInt(322453));
        armor_ironBoots.set("UUIDMost", new NBTTagInt(323));
        modifiers_ironBoots.add(armor_ironBoots);

        NBTTagCompound moveSpd_ironBoots = new NBTTagCompound();
        moveSpd_ironBoots.set("Slot", new NBTTagString("feet"));
        moveSpd_ironBoots.set("AttributeName", new NBTTagString("generic.movementSpeed"));
        moveSpd_ironBoots.set("Name", new NBTTagString("generic.movementSpeed"));
        moveSpd_ironBoots.set("Amount", new NBTTagFloat(ironBoots_moveSpd /*Movement Speed*/));
        moveSpd_ironBoots.set("Operation", new NBTTagInt(1));
        moveSpd_ironBoots.set("UUIDLeast", new NBTTagInt(234345));
        moveSpd_ironBoots.set("UUIDMost", new NBTTagInt(2122));
        modifiers_ironBoots.add(moveSpd_ironBoots);
        
        compound_ironBoots.set("AttributeModifiers", modifiers_ironBoots);
        nmsStack_ironBoots.setTag(compound_ironBoots);
        i_ironBoots = CraftItemStack.asBukkitCopy(nmsStack_ironBoots);
        
        //Iron Chestplate
  		ItemStack i_ironChestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
  		
  		net.minecraft.server.v1_12_R1.ItemStack nmsStack_ironChestplate = CraftItemStack.asNMSCopy(i_ironChestplate);
	    NBTTagCompound compound_ironChestplate = nmsStack_ironChestplate.getTag();
	    if (compound_ironChestplate == null)
	    {
	    	compound_ironChestplate = new NBTTagCompound();
	        nmsStack_ironChestplate.setTag(compound_ironChestplate);
	        compound_ironChestplate = nmsStack_ironChestplate.getTag();
	    }
	    NBTTagList modifiers_ironChestplate = new NBTTagList();
	    int ironChestplate_armor = 6;
	    float ironChestplate_moveSpd = -0.030f;
	    
	    NBTTagCompound armor_ironChestplate = new NBTTagCompound();
	    armor_ironChestplate.set("Slot", new NBTTagString("chest"));
	    armor_ironChestplate.set("AttributeName", new NBTTagString("generic.armor"));
	    armor_ironChestplate.set("Name", new NBTTagString("generic.armor"));
	    armor_ironChestplate.set("Amount", new NBTTagInt(ironChestplate_armor /*Armour*/));
	    armor_ironChestplate.set("Operation", new NBTTagInt(0));
	    armor_ironChestplate.set("UUIDLeast", new NBTTagInt(322453));
	    armor_ironChestplate.set("UUIDMost", new NBTTagInt(323));
	    modifiers_ironChestplate.add(armor_ironChestplate);

	    NBTTagCompound moveSpd_ironChestplate = new NBTTagCompound();
	    moveSpd_ironChestplate.set("Slot", new NBTTagString("chest"));
	    moveSpd_ironChestplate.set("AttributeName", new NBTTagString("generic.movementSpeed"));
	    moveSpd_ironChestplate.set("Name", new NBTTagString("generic.movementSpeed"));
	    moveSpd_ironChestplate.set("Amount", new NBTTagFloat(ironChestplate_moveSpd /*Movement Speed*/));
	    moveSpd_ironChestplate.set("Operation", new NBTTagInt(1));
	    moveSpd_ironChestplate.set("UUIDLeast", new NBTTagInt(234345));
	    moveSpd_ironChestplate.set("UUIDMost", new NBTTagInt(2122));
	    modifiers_ironChestplate.add(moveSpd_ironChestplate);
	    
	    compound_ironChestplate.set("AttributeModifiers", modifiers_ironChestplate);
	    nmsStack_ironChestplate.setTag(compound_ironChestplate);
	    i_ironChestplate = CraftItemStack.asBukkitCopy(nmsStack_ironChestplate);
        
        //Iron Leggings
  		ItemStack i_ironLeggings = new ItemStack(Material.IRON_LEGGINGS, 1);
  		
  		net.minecraft.server.v1_12_R1.ItemStack nmsStack_ironLeggings = CraftItemStack.asNMSCopy(i_ironLeggings);
	    NBTTagCompound compound_ironLeggings = nmsStack_ironLeggings.getTag();
	    if (compound_ironLeggings == null)
	    {
	    	compound_ironLeggings = new NBTTagCompound();
	        nmsStack_ironLeggings.setTag(compound_ironLeggings);
	        compound_ironLeggings = nmsStack_ironLeggings.getTag();
	    }
	    NBTTagList modifiers_ironLeggings = new NBTTagList();
	    int ironLeggings_armor = 5;
	    float ironLeggings_moveSpd = -0.030f;
	    
	    NBTTagCompound armor_ironLeggings = new NBTTagCompound();
	    armor_ironLeggings.set("Slot", new NBTTagString("legs"));
	    armor_ironLeggings.set("AttributeName", new NBTTagString("generic.armor"));
	    armor_ironLeggings.set("Name", new NBTTagString("generic.armor"));
	    armor_ironLeggings.set("Amount", new NBTTagInt(ironLeggings_armor /*Armour*/));
	    armor_ironLeggings.set("Operation", new NBTTagInt(0));
	    armor_ironLeggings.set("UUIDLeast", new NBTTagInt(322453));
	    armor_ironLeggings.set("UUIDMost", new NBTTagInt(323));
	    modifiers_ironLeggings.add(armor_ironLeggings);

	    NBTTagCompound moveSpd_ironLeggings = new NBTTagCompound();
	    moveSpd_ironLeggings.set("Slot", new NBTTagString("legs"));
	    moveSpd_ironLeggings.set("AttributeName", new NBTTagString("generic.movementSpeed"));
	    moveSpd_ironLeggings.set("Name", new NBTTagString("generic.movementSpeed"));
	    moveSpd_ironLeggings.set("Amount", new NBTTagFloat(ironLeggings_moveSpd /*Movement Speed*/));
	    moveSpd_ironLeggings.set("Operation", new NBTTagInt(1));
	    moveSpd_ironLeggings.set("UUIDLeast", new NBTTagInt(234345));
	    moveSpd_ironLeggings.set("UUIDMost", new NBTTagInt(2122));
	    modifiers_ironLeggings.add(moveSpd_ironLeggings);
	    
	    compound_ironLeggings.set("AttributeModifiers", modifiers_ironLeggings);
	    nmsStack_ironLeggings.setTag(compound_ironLeggings);
	    i_ironLeggings = CraftItemStack.asBukkitCopy(nmsStack_ironLeggings);
		
		//Iron Helmet
		ItemStack i_ironHelmet = new ItemStack(Material.IRON_HELMET, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_ironHelmet = CraftItemStack.asNMSCopy(i_ironHelmet);
        NBTTagCompound compound_ironHelmet = nmsStack_ironHelmet.getTag();
        if (compound_ironHelmet == null)
        {
        	compound_ironHelmet = new NBTTagCompound();
            nmsStack_ironHelmet.setTag(compound_ironHelmet);
            compound_ironHelmet = nmsStack_ironHelmet.getTag();
        }
        NBTTagList modifiers_ironHelmet = new NBTTagList();
        int ironHelmet_armor = 2;
        float ironHelmet_moveSpd = -0.020f;

        NBTTagCompound armor_ironHelmet = new NBTTagCompound();
        armor_ironHelmet.set("Slot", new NBTTagString("head"));
        armor_ironHelmet.set("AttributeName", new NBTTagString("generic.armor"));
        armor_ironHelmet.set("Name", new NBTTagString("generic.armor"));
        armor_ironHelmet.set("Amount", new NBTTagInt(ironHelmet_armor /*Armour*/));
        armor_ironHelmet.set("Operation", new NBTTagInt(0));
        armor_ironHelmet.set("UUIDLeast", new NBTTagInt(322453));
        armor_ironHelmet.set("UUIDMost", new NBTTagInt(323));
        modifiers_ironHelmet.add(armor_ironHelmet);
        
        NBTTagCompound moveSpd_ironHelmet = new NBTTagCompound();
        moveSpd_ironHelmet.set("Slot", new NBTTagString("head"));
        moveSpd_ironHelmet.set("AttributeName", new NBTTagString("generic.movementSpeed"));
        moveSpd_ironHelmet.set("Name", new NBTTagString("generic.movementSpeed"));
        moveSpd_ironHelmet.set("Amount", new NBTTagFloat(ironHelmet_moveSpd /*Movement Speed*/));
        moveSpd_ironHelmet.set("Operation", new NBTTagInt(1));
        moveSpd_ironHelmet.set("UUIDLeast", new NBTTagInt(234345));
        moveSpd_ironHelmet.set("UUIDMost", new NBTTagInt(2122));
        modifiers_ironHelmet.add(moveSpd_ironHelmet);
        
        compound_ironHelmet.set("AttributeModifiers", modifiers_ironHelmet);
        nmsStack_ironHelmet.setTag(compound_ironHelmet);
        i_ironHelmet = CraftItemStack.asBukkitCopy(nmsStack_ironHelmet);
        
		//Diamond Boots
		ItemStack i_diamondBoots = new ItemStack(Material.DIAMOND_BOOTS, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_diamondBoots = CraftItemStack.asNMSCopy(i_diamondBoots);
        NBTTagCompound compound_diamondBoots = nmsStack_diamondBoots.getTag();
        if (compound_diamondBoots == null)
        {
        	compound_diamondBoots = new NBTTagCompound();
            nmsStack_diamondBoots.setTag(compound_diamondBoots);
            compound_diamondBoots = nmsStack_diamondBoots.getTag();
        }
        NBTTagList modifiers_diamondBoots = new NBTTagList();
        int diamondBoots_armor = 3;
        float diamondBoots_moveSpd = -0.020f;

        NBTTagCompound armor_diamondBoots = new NBTTagCompound();
        armor_diamondBoots.set("Slot", new NBTTagString("feet"));
        armor_diamondBoots.set("AttributeName", new NBTTagString("generic.armor"));
        armor_diamondBoots.set("Name", new NBTTagString("generic.armor"));
        armor_diamondBoots.set("Amount", new NBTTagInt(diamondBoots_armor /*Armour*/));
        armor_diamondBoots.set("Operation", new NBTTagInt(0));
        armor_diamondBoots.set("UUIDLeast", new NBTTagInt(322453));
        armor_diamondBoots.set("UUIDMost", new NBTTagInt(323));
        modifiers_diamondBoots.add(armor_diamondBoots);

        NBTTagCompound moveSpd_diamondBoots = new NBTTagCompound();
        moveSpd_diamondBoots.set("Slot", new NBTTagString("feet"));
        moveSpd_diamondBoots.set("AttributeName", new NBTTagString("generic.movementSpeed"));
        moveSpd_diamondBoots.set("Name", new NBTTagString("generic.movementSpeed"));
        moveSpd_diamondBoots.set("Amount", new NBTTagFloat(diamondBoots_moveSpd /*Movement Speed*/));
        moveSpd_diamondBoots.set("Operation", new NBTTagInt(1));
        moveSpd_diamondBoots.set("UUIDLeast", new NBTTagInt(234345));
        moveSpd_diamondBoots.set("UUIDMost", new NBTTagInt(2122));
        modifiers_diamondBoots.add(moveSpd_diamondBoots);
        
        compound_diamondBoots.set("AttributeModifiers", modifiers_diamondBoots);
        nmsStack_diamondBoots.setTag(compound_diamondBoots);
        i_diamondBoots = CraftItemStack.asBukkitCopy(nmsStack_diamondBoots);
        
        //Diamond Chestplate
  		ItemStack i_diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
  		
  		net.minecraft.server.v1_12_R1.ItemStack nmsStack_diamondChestplate = CraftItemStack.asNMSCopy(i_diamondChestplate);
	    NBTTagCompound compound_diamondChestplate = nmsStack_diamondChestplate.getTag();
	    if (compound_diamondChestplate == null)
	    {
	    	compound_diamondChestplate = new NBTTagCompound();
	        nmsStack_diamondChestplate.setTag(compound_diamondChestplate);
	        compound_diamondChestplate = nmsStack_diamondChestplate.getTag();
	    }
	    NBTTagList modifiers_diamondChestplate = new NBTTagList();
	    int diamondChestplate_armor = 8;
	    float diamondChestplate_moveSpd = -0.030f;
	    
	    NBTTagCompound armor_diamondChestplate = new NBTTagCompound();
	    armor_diamondChestplate.set("Slot", new NBTTagString("chest"));
	    armor_diamondChestplate.set("AttributeName", new NBTTagString("generic.armor"));
	    armor_diamondChestplate.set("Name", new NBTTagString("generic.armor"));
	    armor_diamondChestplate.set("Amount", new NBTTagInt(diamondChestplate_armor /*Armour*/));
	    armor_diamondChestplate.set("Operation", new NBTTagInt(0));
	    armor_diamondChestplate.set("UUIDLeast", new NBTTagInt(322453));
	    armor_diamondChestplate.set("UUIDMost", new NBTTagInt(323));
	    modifiers_diamondChestplate.add(armor_diamondChestplate);

	    NBTTagCompound moveSpd_diamondChestplate = new NBTTagCompound();
	    moveSpd_diamondChestplate.set("Slot", new NBTTagString("chest"));
	    moveSpd_diamondChestplate.set("AttributeName", new NBTTagString("generic.movementSpeed"));
	    moveSpd_diamondChestplate.set("Name", new NBTTagString("generic.movementSpeed"));
	    moveSpd_diamondChestplate.set("Amount", new NBTTagFloat(diamondChestplate_moveSpd /*Movement Speed*/));
	    moveSpd_diamondChestplate.set("Operation", new NBTTagInt(1));
	    moveSpd_diamondChestplate.set("UUIDLeast", new NBTTagInt(234345));
	    moveSpd_diamondChestplate.set("UUIDMost", new NBTTagInt(2122));
	    modifiers_diamondChestplate.add(moveSpd_diamondChestplate);
	    
	    compound_diamondChestplate.set("AttributeModifiers", modifiers_diamondChestplate);
	    nmsStack_diamondChestplate.setTag(compound_diamondChestplate);
	    i_diamondChestplate = CraftItemStack.asBukkitCopy(nmsStack_diamondChestplate);
        
        //Diamond Leggings
  		ItemStack i_diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
  		
  		net.minecraft.server.v1_12_R1.ItemStack nmsStack_diamondLeggings = CraftItemStack.asNMSCopy(i_diamondLeggings);
	    NBTTagCompound compound_diamondLeggings = nmsStack_diamondLeggings.getTag();
	    if (compound_diamondLeggings == null)
	    {
	    	compound_diamondLeggings = new NBTTagCompound();
	        nmsStack_diamondLeggings.setTag(compound_diamondLeggings);
	        compound_diamondLeggings = nmsStack_diamondLeggings.getTag();
	    }
	    NBTTagList modifiers_diamondLeggings = new NBTTagList();
	    int diamondLeggings_armor = 6;
	    float diamondLeggings_moveSpd = -0.030f;
	    
	    NBTTagCompound armor_diamondLeggings = new NBTTagCompound();
	    armor_diamondLeggings.set("Slot", new NBTTagString("legs"));
	    armor_diamondLeggings.set("AttributeName", new NBTTagString("generic.armor"));
	    armor_diamondLeggings.set("Name", new NBTTagString("generic.armor"));
	    armor_diamondLeggings.set("Amount", new NBTTagInt(diamondLeggings_armor /*Armour*/));
	    armor_diamondLeggings.set("Operation", new NBTTagInt(0));
	    armor_diamondLeggings.set("UUIDLeast", new NBTTagInt(322453));
	    armor_diamondLeggings.set("UUIDMost", new NBTTagInt(323));
	    modifiers_diamondLeggings.add(armor_diamondLeggings);
	    
	    NBTTagCompound moveSpd_diamondLeggings = new NBTTagCompound();
	    moveSpd_diamondLeggings.set("Slot", new NBTTagString("legs"));
	    moveSpd_diamondLeggings.set("AttributeName", new NBTTagString("generic.movementSpeed"));
	    moveSpd_diamondLeggings.set("Name", new NBTTagString("generic.movementSpeed"));
	    moveSpd_diamondLeggings.set("Amount", new NBTTagFloat(diamondLeggings_moveSpd /*Movement Speed*/));
	    moveSpd_diamondLeggings.set("Operation", new NBTTagInt(1));
	    moveSpd_diamondLeggings.set("UUIDLeast", new NBTTagInt(234345));
	    moveSpd_diamondLeggings.set("UUIDMost", new NBTTagInt(2122));
	    modifiers_diamondLeggings.add(moveSpd_diamondLeggings);
	    
	    compound_diamondLeggings.set("AttributeModifiers", modifiers_diamondLeggings);
	    nmsStack_diamondLeggings.setTag(compound_diamondLeggings);
	    i_diamondLeggings = CraftItemStack.asBukkitCopy(nmsStack_diamondLeggings);
		
		//Diamond Helmet
		ItemStack i_diamondHelmet = new ItemStack(Material.DIAMOND_HELMET, 1);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack_diamondHelmet = CraftItemStack.asNMSCopy(i_diamondHelmet);
        NBTTagCompound compound_diamondHelmet = nmsStack_diamondHelmet.getTag();
        if (compound_diamondHelmet == null)
        {
        	compound_diamondHelmet = new NBTTagCompound();
            nmsStack_diamondHelmet.setTag(compound_diamondHelmet);
            compound_diamondHelmet = nmsStack_diamondHelmet.getTag();
        }
        NBTTagList modifiers_diamondHelmet = new NBTTagList();
        int diamondHelmet_armor = 3;
        float diamondHelmet_moveSpd = -0.020f;

        NBTTagCompound armor_diamondHelmet = new NBTTagCompound();
        armor_diamondHelmet.set("Slot", new NBTTagString("head"));
        armor_diamondHelmet.set("AttributeName", new NBTTagString("generic.armor"));
        armor_diamondHelmet.set("Name", new NBTTagString("generic.armor"));
        armor_diamondHelmet.set("Amount", new NBTTagInt(diamondHelmet_armor /*Armour*/));
        armor_diamondHelmet.set("Operation", new NBTTagInt(0));
        armor_diamondHelmet.set("UUIDLeast", new NBTTagInt(322453));
        armor_diamondHelmet.set("UUIDMost", new NBTTagInt(323));
        modifiers_diamondHelmet.add(armor_diamondHelmet);

        NBTTagCompound moveSpd_diamondHelmet = new NBTTagCompound();
        moveSpd_diamondHelmet.set("Slot", new NBTTagString("head"));
        moveSpd_diamondHelmet.set("AttributeName", new NBTTagString("generic.movementSpeed"));
        moveSpd_diamondHelmet.set("Name", new NBTTagString("generic.movementSpeed"));
        moveSpd_diamondHelmet.set("Amount", new NBTTagFloat(diamondHelmet_moveSpd /*Movement Speed*/));
        moveSpd_diamondHelmet.set("Operation", new NBTTagInt(1));
        moveSpd_diamondHelmet.set("UUIDLeast", new NBTTagInt(234345));
        moveSpd_diamondHelmet.set("UUIDMost", new NBTTagInt(2122));
        modifiers_diamondHelmet.add(moveSpd_diamondHelmet);
        
        compound_diamondHelmet.set("AttributeModifiers", modifiers_diamondHelmet);
        nmsStack_diamondHelmet.setTag(compound_diamondHelmet);
        i_diamondHelmet = CraftItemStack.asBukkitCopy(nmsStack_diamondHelmet);

        //Fermented Skin
      	ItemStack i_fermentedSkin = new ItemStack(Material.RABBIT_HIDE, 1);
      	ItemMeta fermentedSkinMeta= i_fermentedSkin.getItemMeta();
      	fermentedSkinMeta.setDisplayName(ChatColor.RESET + Words.get("Fermented Skin"));
      	i_fermentedSkin.setItemMeta(fermentedSkinMeta);
      	
        //Medical Kit
      	ItemStack i_medicKit = new ItemStack(Material.WATCH, 1);
      	ItemMeta medicKitMeta= i_medicKit.getItemMeta();
      	medicKitMeta.setDisplayName(ChatColor.RESET + Words.get("Medical Kit"));
      	i_medicKit.setItemMeta(medicKitMeta);
      	
      	//Recurve Bow
      	ItemStack i_recurveBow = new ItemStack(Material.BOW, 1);
      		
  		ItemMeta recurveBowMeta= i_recurveBow.getItemMeta();
  		recurveBowMeta.setLore
  		(
  			Arrays.asList
  			(
  				ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + Words.get("Recurved")
  			)
  		);
		recurveBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
  		i_recurveBow.setItemMeta(recurveBowMeta);
        
		//Recipes
		ShapedRecipe 		hatchet1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "hatchet1"			), i_hatchet);
		ShapedRecipe 		hatchet2 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "hatchet2" 			), i_hatchet);
		ShapedRecipe 		mattock1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "mattock1"			), i_mattock);
		ShapedRecipe 		mattock2 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "mattock2"			), i_mattock);
		ShapedRecipe 		shiv1 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "shiv1"				), i_shiv);
		ShapedRecipe 		shiv2 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "shiv2"				), i_shiv);
		ShapedRecipe 		shiv3 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "shiv3"				), i_shiv);
		ShapedRecipe 		shiv4 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "shiv4"				), i_shiv);
		ShapedRecipe 		hammer1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "hammer1"				), i_hammer);
		ShapedRecipe 		hammer2 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "hammer"				), i_hammer);
		ShapelessRecipe 	string 					= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "string"				), new ItemStack(Material.STRING, 2));
		ShapedRecipe 		gAxe 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "gAxe"				), i_gAxe);
		ShapedRecipe 		gPickaxe1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "gPickaxe1"			), i_gPickaxe);
		ShapedRecipe 		gPickaxe2 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "gPickaxe2"			), i_gPickaxe);
		ShapedRecipe 		gSpade1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "gSpade1"				), i_gSpade);
		ShapedRecipe 		gSpade2 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "gSpade2"				), i_gSpade);
		ShapedRecipe 		gHoe1 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "gHoe1"				), i_gHoe);
		ShapedRecipe 		gHoe2 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "gHoe2"				), i_gHoe);
		ShapedRecipe 		gSword 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "gSword"				), i_gSword);
		ShapedRecipe 		notchApple 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "notchApple"			), new ItemStack(Material.GOLDEN_APPLE, 1, (byte)1));
		ShapedRecipe 		saddle 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "saddle"				), new ItemStack(Material.SADDLE, 1));
		ShapedRecipe 		nametag1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "nametag1"			), new ItemStack(Material.NAME_TAG, 1));
		ShapedRecipe 		nametag2 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "nametag2"			), new ItemStack(Material.NAME_TAG, 1));
		ShapedRecipe 		packedIce1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "packedIce1"			), new ItemStack(Material.PACKED_ICE, 1));
		ShapelessRecipe 	packedIce2 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "packedIce2"			), new ItemStack(Material.ICE, 4));
		ShapedRecipe 		ironHorse1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "ironHorse1"			), new ItemStack(Material.IRON_BARDING, 1));
		ShapedRecipe 		ironHorse2 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "ironHorse2"			), new ItemStack(Material.IRON_BARDING, 1));
		ShapedRecipe 		goldHorse1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "goldHorse1"			), new ItemStack(Material.GOLD_BARDING, 1));
		ShapedRecipe 		goldHorse2 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "goldHorse2"			), new ItemStack(Material.GOLD_BARDING, 1));
		ShapedRecipe 		diamondHorse1 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "diamondHorse1"		), new ItemStack(Material.DIAMOND_BARDING, 1));
		ShapedRecipe 		diamondHorse2 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "diamondHorse2"		), new ItemStack(Material.DIAMOND_BARDING, 1));
		ShapelessRecipe 	clayBrick 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "clayBrick"			), new ItemStack(Material.CLAY_BRICK, 4));
		ShapelessRecipe 	quartz 					= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "quartz"				), new ItemStack(Material.QUARTZ, 4));
		ShapelessRecipe 	woolString 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "woolString"			), new ItemStack(Material.STRING, 4));
		ShapedRecipe 		ice 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "ice"					), new ItemStack(Material.ICE, 1));
		ShapelessRecipe 	repair_gSword 			= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "repair_gSword"		), i_gSword);
		ShapelessRecipe 	repair_gHoe 			= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "repair_gHoe"			), i_gHoe);
		ShapelessRecipe 	repair_gPickaxe 		= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "repair_gPickaxe"		), i_gPickaxe);
		ShapelessRecipe 	repair_gAxe 			= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "repair_gAxe"			), i_gAxe);
		ShapelessRecipe 	repair_gSpade 			= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "repair_gSpade"		), i_gSpade);
		ShapelessRecipe 	workbench1 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "workbench1"			), new ItemStack(Material.WORKBENCH, 1));
		ShapelessRecipe 	workbench2 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "workbench2"			), new ItemStack(Material.WORKBENCH, 1));
		ShapedRecipe 		furnace 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "furnace"				), new ItemStack(Material.FURNACE, 1));
		ShapedRecipe 		chest 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "chest"				), new ItemStack(Material.CHEST, 1));
		ShapelessRecipe 	clay 					= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "clay"				), new ItemStack(Material.CLAY, 1));
		ShapelessRecipe 	diorite 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "diorite"				), new ItemStack(Material.STONE, 1, (byte)3));
		ShapelessRecipe 	granite 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "granite"				), new ItemStack(Material.STONE, 1, (byte)1));
		ShapelessRecipe 	andesite 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "andesite"			), new ItemStack(Material.STONE, 1, (byte)5));
		ShapedRecipe 		gravel1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "gravel1"				), new ItemStack(Material.GRAVEL, 2));
		ShapedRecipe 		gravel2 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "gravel2"				), new ItemStack(Material.GRAVEL, 2));
		ShapelessRecipe 	firestriker 			= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "firestriker"			), i_firestriker);
		ShapelessRecipe 	torch 					= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "torch"				), new ItemStack(Material.TORCH, 8));
		ShapelessRecipe 	flint 					= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "flint"				), new ItemStack(Material.FLINT, 1));
		ShapelessRecipe 	fermentedSpiderEye 		= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "fermentedSpiderEye"	), new ItemStack(Material.FERMENTED_SPIDER_EYE, 1));
		ShapelessRecipe 	fermentedSkin1 			= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "fermentedSkin1"		), i_fermentedSkin);
		ShapelessRecipe 	fermentedSkin2 			= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "fermentedSkin2"		), i_fermentedSkin);
		ShapelessRecipe 	poisonousPotato 		= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "poisonousPotato"		), new ItemStack(Material.POISONOUS_POTATO, 1));
		ShapelessRecipe 	glassBottle 			= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "glassBottle"			), new ItemStack(Material.GLASS_BOTTLE, 1));
		ShapelessRecipe 	bowl 					= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "bowl"				), new ItemStack(Material.BOWL, 1));
		ShapedRecipe 		medicKit1 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "medicKit1"			), i_medicKit);
		ShapedRecipe 		medicKit2 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "medicKit2"			), i_medicKit);
		ShapedRecipe 		medicKit3 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "medicKit3"			), i_medicKit);
		ShapedRecipe 		medicKit4 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "medicKit4"			), i_medicKit);
		ShapedRecipe 		medicKit5 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "medicKit5"			), i_medicKit);
		ShapedRecipe 		medicKit6 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "medicKit6"			), i_medicKit);
		ShapedRecipe 		fishingRod1 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "fishingRod1"			), new ItemStack(Material.FISHING_ROD, 1));
		ShapedRecipe 		fishingRod2 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "fishingRod2"			), new ItemStack(Material.FISHING_ROD, 1));
		ShapedRecipe 		ironIngot 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "ironIngot"			), new ItemStack(Material.IRON_INGOT, 1));
		ShapelessRecipe 	ironNugget 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "ironNugget"			), new ItemStack(Material.IRON_NUGGET, 4));
		ShapelessRecipe 	ironBlock 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "ironBlock"			), new ItemStack(Material.IRON_INGOT, 9));
		FurnaceRecipe 		smelt_ironIngot 		= new FurnaceRecipe		(new ItemStack(Material.IRON_INGOT, 1), Material.IRON_ORE);
		ShapedRecipe 		goldIngot 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "goldIngot"			), new ItemStack(Material.GOLD_INGOT, 1));
		ShapelessRecipe 	goldNugget 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "goldNugget"			), new ItemStack(Material.GOLD_NUGGET, 4));
		ShapelessRecipe 	goldBlock 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "goldBlock"			), new ItemStack(Material.GOLD_INGOT, 9));
		FurnaceRecipe 		smelt_goldIngot 		= new FurnaceRecipe		(new ItemStack(Material.GOLD_INGOT, 1), Material.GOLD_ORE);
		ShapedRecipe 		bread 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "bread"				), new ItemStack(Material.BREAD, 2));
		ShapedRecipe 		cookie 					= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "cookie"				), new ItemStack(Material.COOKIE, 8));
		ShapelessRecipe 	slimeball 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "slimeball"			), new ItemStack(Material.SLIME_BALL, 1));
		ShapelessRecipe 	cobweb 					= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "cobweb"				), new ItemStack(Material.WEB, 1));
		ShapelessRecipe 	sapling 				= new ShapelessRecipe	(NamespacedKey.minecraft(key.getKey() + "_" + "sapling"				), new ItemStack(Material.STICK, 4));

		ShapedRecipe 		leatherBoots 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "leatherBoots"		), i_leatherBoots);
		ShapedRecipe 		leatherChestplate 		= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "leatherChestplate"	), i_leatherChestplate);
		ShapedRecipe 		leatherLeggings 		= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "leatherLeggings"		), i_leatherLeggings);
		ShapedRecipe 		leatherHelmet 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "leatherHelmet"		), i_leatherHelmet);
		ShapedRecipe 		goldBoots 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "goldBoots"			), i_goldBoots);
		ShapedRecipe 		goldChestplate 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "goldChestplate"		), i_goldChestplate);
		ShapedRecipe 		goldLeggings 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "goldLeggings"		), i_goldLeggings);
		ShapedRecipe 		goldHelmet 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "goldHelmet"			), i_goldHelmet);
		ShapedRecipe 		ironBoots 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "ironBoots"			), i_ironBoots);
		ShapedRecipe 		ironChestplate 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "ironChestplate"		), i_ironChestplate);
		ShapedRecipe 		ironLeggings 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "ironLeggings"		), i_ironLeggings);
		ShapedRecipe 		ironHelmet 				= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "ironHelmet"			), i_ironHelmet);
		ShapedRecipe 		diamondBoots 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "diamondBoots"		), i_diamondBoots);
		ShapedRecipe 		diamondChestplate 		= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "diamondChestplate"	), i_diamondChestplate);
		ShapedRecipe 		diamondLeggings 		= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "diamondLeggings"		), i_diamondLeggings);
		ShapedRecipe 		diamondHelmet 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "diamondHelmet"		), i_diamondHelmet);

		ShapedRecipe 		recurveBow1 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "recurveBow1"			), i_recurveBow);
		ShapedRecipe 		recurveBow2 			= new ShapedRecipe		(NamespacedKey.minecraft(key.getKey() + "_" + "recurveBow2"			), i_recurveBow);
		
		hatchet1.shape("@@"," 1");

		hatchet1.setIngredient('@', Material.FLINT);
		hatchet1.setIngredient('1', Material.STICK);

		hatchet2.shape("@@","1 ");

		hatchet2.setIngredient('@', Material.FLINT);
		hatchet2.setIngredient('1', Material.STICK);

		mattock1.shape("@-","1@");

		mattock1.setIngredient('@', Material.FLINT);
		mattock1.setIngredient('-', new Wood(Material.WOOD));
		mattock1.setIngredient('1', Material.STICK);

		mattock2.shape("-@","@1");

		mattock2.setIngredient('@', Material.FLINT);
		mattock2.setIngredient('-', new Wood(Material.WOOD));
		mattock2.setIngredient('1', Material.STICK);
		
		shiv1.shape("*@","1&");

		shiv1.setIngredient('@', Material.FLINT);
		shiv1.setIngredient('1', Material.STICK);
		shiv1.setIngredient('*', Material.STRING);
		shiv1.setIngredient('&', Material.SPIDER_EYE);
		
		shiv2.shape("@*","&1");

		shiv2.setIngredient('@', Material.FLINT);
		shiv2.setIngredient('1', Material.STICK);
		shiv2.setIngredient('*', Material.STRING);
		shiv2.setIngredient('&', Material.SPIDER_EYE);
		
		shiv3.shape("&@","1*");

		shiv3.setIngredient('@', Material.FLINT);
		shiv3.setIngredient('1', Material.STICK);
		shiv3.setIngredient('*', Material.STRING);
		shiv3.setIngredient('&', Material.SPIDER_EYE);

		shiv4.shape("@&","*1");

		shiv4.setIngredient('@', Material.FLINT);
		shiv4.setIngredient('1', Material.STICK);
		shiv4.setIngredient('*', Material.STRING);
		shiv4.setIngredient('&', Material.SPIDER_EYE);

		hammer1.shape("@ ","1@");

		hammer1.setIngredient('@', Material.COBBLESTONE);
		hammer1.setIngredient('1', Material.STICK);
		
		hammer2.shape(" @","@1");

		hammer2.setIngredient('@', Material.COBBLESTONE);
		hammer2.setIngredient('1', Material.STICK);
		
		string.addIngredient(Material.WEB);
		
		gAxe.shape("@@@","@*@"," 1 ");

		gAxe.setIngredient('@', Material.DIAMOND);
		gAxe.setIngredient('*', Material.NETHER_STAR);
		gAxe.setIngredient('1', Material.STICK);

		gPickaxe1.shape("@B-","B# ","- 1");

		gPickaxe1.setIngredient('@', Material.QUARTZ_BLOCK);
		gPickaxe1.setIngredient('-', Material.DIAMOND);
		gPickaxe1.setIngredient('B', Material.DIAMOND_BLOCK);
		gPickaxe1.setIngredient('1', Material.STICK);
		gPickaxe1.setIngredient('#', Material.DRAGON_EGG);
		
		gPickaxe2.shape("-B@"," #B","1 -");

		gPickaxe2.setIngredient('@', Material.QUARTZ_BLOCK);
		gPickaxe2.setIngredient('-', Material.DIAMOND);
		gPickaxe2.setIngredient('B', Material.DIAMOND_BLOCK);
		gPickaxe2.setIngredient('1', Material.STICK);
		gPickaxe2.setIngredient('#', Material.DRAGON_EGG);

		gSpade1.shape(" @@"," &@","1  ");

		gSpade1.setIngredient('@', Material.OBSIDIAN);
		gSpade1.setIngredient('&', Material.END_CRYSTAL);
		gSpade1.setIngredient('1', Material.STICK);

		gSpade2.shape("@@ ","@& ","  1");

		gSpade2.setIngredient('@', Material.OBSIDIAN);
		gSpade2.setIngredient('&', Material.END_CRYSTAL);
		gSpade2.setIngredient('1', Material.STICK);

		gHoe1.shape(" @@","B*@","1B ");

		gHoe1.setIngredient('*', Material.EYE_OF_ENDER);
		gHoe1.setIngredient('@', Material.DIAMOND);
		gHoe1.setIngredient('B', Material.DIAMOND_BLOCK);
		gHoe1.setIngredient('1', new Wood(Material.WOOD));
		
		gHoe2.shape("@@ ","@*B"," B1");

		gHoe2.setIngredient('*', Material.EYE_OF_ENDER);
		gHoe2.setIngredient('@', Material.DIAMOND);
		gHoe2.setIngredient('B', Material.DIAMOND_BLOCK);
		gHoe2.setIngredient('1', new Wood(Material.WOOD));

		gSword.shape("*@*","*@*","*1*");

		gSword.setIngredient('@', Material.GOLD_INGOT);
		gSword.setIngredient('1', Material.BLAZE_ROD);
		gSword.setIngredient('*', Material.BLAZE_POWDER);
		
		notchApple.shape("@@@","@*@","@@@");

		notchApple.setIngredient('@', Material.GOLD_BLOCK);
		notchApple.setIngredient('*', Material.APPLE);	

		saddle.shape("@@@","*-*","= =");

		saddle.setIngredient('@', Material.LEATHER);
		saddle.setIngredient('*', Material.LEASH);
		saddle.setIngredient('-', Material.IRON_INGOT);
		saddle.setIngredient('=', Material.IRON_NUGGET);

		nametag1.shape(" -@"," *-","*  ");

		nametag1.setIngredient('@', Material.STRING);
		nametag1.setIngredient('-', Material.IRON_INGOT);
		nametag1.setIngredient('*', Material.PAPER);
		
		nametag2.shape("@- ","-* ","  *");

		nametag2.setIngredient('@', Material.STRING);
		nametag2.setIngredient('-', Material.IRON_INGOT);
		nametag2.setIngredient('*', Material.PAPER);		

		packedIce1.shape("@@","@@");

		packedIce1.setIngredient('@', Material.ICE);
		
		packedIce2.addIngredient(Material.PACKED_ICE);

		ironHorse1.shape("  @","#-#","= =");

		ironHorse1.setIngredient('#', Material.IRON_BLOCK);
		ironHorse1.setIngredient('@', Material.IRON_INGOT);
		ironHorse1.setIngredient('-', Material.SADDLE);
		ironHorse1.setIngredient('=', Material.IRON_NUGGET);

		ironHorse2.shape("@  ","#-#","= =");

		ironHorse2.setIngredient('#', Material.IRON_BLOCK);
		ironHorse2.setIngredient('@', Material.IRON_INGOT);
		ironHorse2.setIngredient('-', Material.SADDLE);
		ironHorse2.setIngredient('=', Material.IRON_NUGGET);

		goldHorse1.shape("  @","#-#","= =");

		goldHorse1.setIngredient('#', Material.GOLD_BLOCK);
		goldHorse1.setIngredient('@', Material.GOLD_INGOT);
		goldHorse1.setIngredient('-', Material.SADDLE);
		goldHorse1.setIngredient('=', Material.GOLD_NUGGET);

		goldHorse2.shape("@  ","#-#","= =");

		goldHorse2.setIngredient('#', Material.GOLD_BLOCK);
		goldHorse2.setIngredient('@', Material.GOLD_INGOT);
		goldHorse2.setIngredient('-', Material.SADDLE);
		goldHorse2.setIngredient('=', Material.GOLD_NUGGET);
		
		diamondHorse1.shape("  H","@-@","B B");

		diamondHorse1.setIngredient('@', Material.DIAMOND);
		diamondHorse1.setIngredient('-', Material.IRON_BARDING);
		diamondHorse1.setIngredient('H', Material.DIAMOND_HELMET);
		diamondHorse1.setIngredient('B', Material.DIAMOND_BOOTS);
		
		diamondHorse2.shape("H  ","@-@","B B");

		diamondHorse2.setIngredient('@', Material.DIAMOND);
		diamondHorse2.setIngredient('-', Material.IRON_BARDING);
		diamondHorse2.setIngredient('H', Material.DIAMOND_HELMET);
		diamondHorse2.setIngredient('B', Material.DIAMOND_BOOTS);

		clayBrick.addIngredient(Material.BRICK);

		quartz.addIngredient(Material.QUARTZ_BLOCK);

		woolString.addIngredient(new Wool());
		
		ice.shape("@@@","@*@","@@@");

		ice.setIngredient('@', Material.SNOW_BALL);
		ice.setIngredient('*', Material.WATER_BUCKET);

		repair_gSword.addIngredient(Material.GOLD_SWORD, -1);
		repair_gSword.addIngredient(2, Material.BLAZE_POWDER);
		
		repair_gHoe.addIngredient(Material.GOLD_HOE, -1);
		repair_gHoe.addIngredient(Material.ENDER_PEARL);
		repair_gHoe.addIngredient(Material.DIAMOND_BLOCK);
		
		repair_gPickaxe.addIngredient(Material.GOLD_PICKAXE, -1);
		repair_gPickaxe.addIngredient(1, Material.QUARTZ_BLOCK);
		repair_gPickaxe.addIngredient(1, Material.DIAMOND_BLOCK);
		
		repair_gAxe.addIngredient(Material.GOLD_AXE, -1);
		repair_gAxe.addIngredient(Material.NETHER_STAR);
		
		repair_gSpade.addIngredient(Material.GOLD_SPADE, -1);
		repair_gSpade.addIngredient(1, Material.END_CRYSTAL);

		workbench1.addIngredient(new Wood(Material.LOG));
		workbench1.addIngredient(Material.LEATHER);
		workbench1.addIngredient(Material.STRING);
		workbench1.addIngredient(Material.WOOD_SWORD);
		
		workbench2.addIngredient(new Wood(Material.LOG_2));
		workbench2.addIngredient(Material.LEATHER);
		workbench2.addIngredient(Material.STRING);
		workbench2.addIngredient(Material.WOOD_SWORD);

		furnace.shape("@@@","@*@","@@@");

		furnace.setIngredient('@', Material.CLAY_BRICK);
		furnace.setIngredient('*', Material.WOOD_SPADE);

		chest.shape("@@@","@#@","@@@");
		
		chest.setIngredient('@', new Wood(Material.WOOD));
		chest.setIngredient('#', Material.IRON_INGOT);

		clay.addIngredient(Material.DIRT);
		clay.addIngredient(Material.SAND);
		clay.addIngredient(Material.BEETROOT_SOUP);

		diorite.addIngredient(new Dye(DyeColor.WHITE));
		diorite.addIngredient(Material.COBBLESTONE);

		granite.addIngredient(Material.NETHERRACK);
		granite.addIngredient(Material.COBBLESTONE);

		andesite.addIngredient(Material.GRAVEL);
		andesite.addIngredient(Material.COBBLESTONE);

		gravel1.shape("@B","B@");
		
		gravel1.setIngredient('@', Material.SAND);
		gravel1.setIngredient('B', Material.COBBLESTONE);
		
		gravel2.shape("B@","@B");
		
		gravel2.setIngredient('@', Material.SAND);
		gravel2.setIngredient('B', Material.COBBLESTONE);

		firestriker.addIngredient(Material.FLINT);
		firestriker.addIngredient(new Coal());
		
		torch.addIngredient(Material.WOOD_SPADE);
		torch.addIngredient(8, Material.STICK);

		flint.addIngredient(Material.GRAVEL);

		fermentedSpiderEye.addIngredient(Material.SPIDER_EYE);
		fermentedSpiderEye.addIngredient(Material.SUGAR);
		fermentedSpiderEye.addIngredient(Material.RED_MUSHROOM);

		fermentedSkin1.addIngredient(Material.ROTTEN_FLESH);
		fermentedSkin1.addIngredient(Material.SUGAR);
		fermentedSkin1.addIngredient(Material.BROWN_MUSHROOM);

		fermentedSkin2.addIngredient(Material.ROTTEN_FLESH);
		fermentedSkin2.addIngredient(Material.SUGAR);
		fermentedSkin2.addIngredient(Material.RED_MUSHROOM);

		poisonousPotato.addIngredient(Material.POTATO_ITEM);
		poisonousPotato.addIngredient(new Dye(DyeColor.WHITE));
		
		glassBottle.addIngredient(Material.POTION);
		
		bowl.addIngredient(Material.BEETROOT_SOUP);

		medicKit1.shape(" @ ","ABC"," @ ");

		medicKit1.setIngredient('@', Material.GOLD_INGOT);
		medicKit1.setIngredient('A', Material.FEATHER);
		medicKit1.setIngredient('B', Material.SPECKLED_MELON);
		medicKit1.setIngredient('C', Material.PAPER);
		
		medicKit2.shape(" @ ","ACB"," @ ");

		medicKit2.setIngredient('@', Material.GOLD_INGOT);
		medicKit2.setIngredient('A', Material.FEATHER);
		medicKit2.setIngredient('B', Material.SPECKLED_MELON);
		medicKit2.setIngredient('C', Material.PAPER);
		
		medicKit3.shape(" @ ","BAC"," @ ");

		medicKit3.setIngredient('@', Material.GOLD_INGOT);
		medicKit3.setIngredient('A', Material.FEATHER);
		medicKit3.setIngredient('B', Material.SPECKLED_MELON);
		medicKit3.setIngredient('C', Material.PAPER);
		
		medicKit4.shape(" @ ","BCA"," @ ");

		medicKit4.setIngredient('@', Material.GOLD_INGOT);
		medicKit4.setIngredient('A', Material.FEATHER);
		medicKit4.setIngredient('B', Material.SPECKLED_MELON);
		medicKit4.setIngredient('C', Material.PAPER);
		
		medicKit5.shape(" @ ","CAB"," @ ");

		medicKit5.setIngredient('@', Material.GOLD_INGOT);
		medicKit5.setIngredient('A', Material.FEATHER);
		medicKit5.setIngredient('B', Material.SPECKLED_MELON);
		medicKit5.setIngredient('C', Material.PAPER);

		medicKit6.shape(" @ ","CBA"," @ ");

		medicKit6.setIngredient('@', Material.GOLD_INGOT);
		medicKit6.setIngredient('A', Material.FEATHER);
		medicKit6.setIngredient('B', Material.SPECKLED_MELON);
		medicKit6.setIngredient('C', Material.PAPER);
		
		fishingRod1.shape("1- ","1 -","1@*");

		fishingRod1.setIngredient('1', Material.STICK);
		fishingRod1.setIngredient('@', Material.IRON_INGOT);
		fishingRod1.setIngredient('-', Material.STRING);
		fishingRod1.setIngredient('*', Material.FEATHER);

		fishingRod2.shape(" -1","- 1","*@1");

		fishingRod2.setIngredient('1', Material.STICK);
		fishingRod2.setIngredient('@', Material.IRON_INGOT);
		fishingRod2.setIngredient('-', Material.STRING);
		fishingRod2.setIngredient('*', Material.FEATHER);
		
		ironIngot.shape("@@","@@");
		
		ironIngot.setIngredient('@', Material.IRON_NUGGET);

		ironNugget.addIngredient(Material.IRON_INGOT);
		
		ironBlock.addIngredient(Material.IRON_BLOCK);
		
		goldIngot.shape("@@","@@");
		
		goldIngot.setIngredient('@', Material.GOLD_NUGGET);

		goldNugget.addIngredient(Material.GOLD_INGOT);
		
		goldBlock.addIngredient(Material.GOLD_BLOCK);
		
		bread.shape(" E ","WWW");

		bread.setIngredient('E', Material.EGG);
		bread.setIngredient('W', Material.WHEAT);
		
		cookie.shape(" E ","WCW"," S ");

		cookie.setIngredient('E', Material.EGG);
		cookie.setIngredient('W', Material.WHEAT);
		cookie.setIngredient('S', Material.SUGAR);
		cookie.setIngredient('C', new Dye(DyeColor.BROWN));
		
		slimeball.addIngredient(Material.MILK_BUCKET);
		slimeball.addIngredient(8, Material.VINE);
		
		cobweb.addIngredient(Material.SLIME_BALL);
		cobweb.addIngredient(2, Material.STRING);
		
		sapling.addIngredient(Material.SAPLING);
		
		leatherBoots.shape("@*@");

		leatherBoots.setIngredient('@', Material.IRON_INGOT);
		leatherBoots.setIngredient('*', Material.LEATHER_BOOTS);
		
		leatherChestplate.shape(" @ ","@*@"," @ ");

		leatherChestplate.setIngredient('@', Material.IRON_INGOT);
		leatherChestplate.setIngredient('*', Material.LEATHER_CHESTPLATE);
		
		leatherLeggings.shape(" @ ","@*@"," @ ");

		leatherLeggings.setIngredient('@', Material.IRON_INGOT);
		leatherLeggings.setIngredient('*', Material.LEATHER_LEGGINGS);
		
		leatherHelmet.shape("@*@");

		leatherHelmet.setIngredient('@', Material.IRON_INGOT);
		leatherHelmet.setIngredient('*', Material.LEATHER_HELMET);

		goldBoots.shape("@ @","@ @");

		goldBoots.setIngredient('@', Material.GOLD_INGOT);
		
		goldChestplate.shape("@ @","@@@","@@@");

		goldChestplate.setIngredient('@', Material.GOLD_INGOT);
		
		goldLeggings.shape("@@@","@ @","@ @");

		goldLeggings.setIngredient('@', Material.GOLD_INGOT);
		
		goldHelmet.shape("@*@","@@@");

		goldHelmet.setIngredient('@', Material.GOLD_INGOT);
		goldHelmet.setIngredient('*', Material.EMERALD);
		
		ironBoots.shape("@ @","@ @");

		ironBoots.setIngredient('@', Material.IRON_INGOT);
		
		ironChestplate.shape("@ @","@@@","@@@");

		ironChestplate.setIngredient('@', Material.IRON_INGOT);
		
		ironLeggings.shape("@@@","@ @","@ @");

		ironLeggings.setIngredient('@', Material.IRON_INGOT);
		
		ironHelmet.shape("@@@","@ @");

		ironHelmet.setIngredient('@', Material.IRON_INGOT);
		
		diamondBoots.shape("@ @","@ @");

		diamondBoots.setIngredient('@', Material.DIAMOND);
		
		diamondChestplate.shape("@ @","@@@","@@@");

		diamondChestplate.setIngredient('@', Material.DIAMOND);
		
		diamondLeggings.shape("@@@","@ @","@ @");

		diamondLeggings.setIngredient('@', Material.DIAMOND);
		
		diamondHelmet.shape("@@@","@ @");

		diamondHelmet.setIngredient('@', Material.DIAMOND);
		
		recurveBow1.shape(" @1","#^1"," @1");

		recurveBow1.setIngredient('^', Material.BOW);
		recurveBow1.setIngredient('#', Material.PISTON_BASE);
		recurveBow1.setIngredient('@', Material.IRON_INGOT);
		recurveBow1.setIngredient('1', Material.STRING);
		
		recurveBow2.shape("1@ ","1^#","1@ ");

		recurveBow2.setIngredient('^', Material.BOW);
		recurveBow2.setIngredient('#', Material.WOOD);
		recurveBow2.setIngredient('@', Material.IRON_INGOT);
		recurveBow2.setIngredient('1', Material.STRING);

		//Add recipes
		if(settings.getBoolean("Survival.Enabled"))
		{
			getServer().addRecipe(hatchet1);
			getServer().addRecipe(hatchet2);
			getServer().addRecipe(mattock1);
			getServer().addRecipe(mattock2);
			getServer().addRecipe(shiv1);
			getServer().addRecipe(shiv2);
			getServer().addRecipe(shiv3);
			getServer().addRecipe(shiv4);
			getServer().addRecipe(hammer1);
			getServer().addRecipe(hammer2);
			getServer().addRecipe(firestriker);
			getServer().addRecipe(workbench1);
			getServer().addRecipe(workbench2);
			getServer().addRecipe(furnace);
			getServer().addRecipe(chest);
			getServer().addRecipe(flint);
		}
		if(settings.getBoolean("Survival.Torch"))
		{
			getServer().addRecipe(torch);
		}
		if(settings.getBoolean("Recipes.WebString"))
			getServer().addRecipe(string);
		if(settings.getBoolean("Recipes.SaplingToSticks"))
			getServer().addRecipe(sapling);
		if(settings.getBoolean("LegendaryItems.ValkyrieAxe"))
		{
			getServer().addRecipe(gAxe);
			if(settings.getBoolean("LegendaryItems.CanRepair"))
				getServer().addRecipe(repair_gAxe);
		}
		if(settings.getBoolean("LegendaryItems.QuartzPickaxe"))
		{
			getServer().addRecipe(gPickaxe1);
			getServer().addRecipe(gPickaxe2);
			if(settings.getBoolean("LegendaryItems.CanRepair"))
				getServer().addRecipe(repair_gPickaxe);
		}
		if(settings.getBoolean("LegendaryItems.ObsidianMace"))
		{
			getServer().addRecipe(gSpade1);
			getServer().addRecipe(gSpade2);
			if(settings.getBoolean("LegendaryItems.CanRepair"))
				getServer().addRecipe(repair_gSpade);
		}
		if(settings.getBoolean("LegendaryItems.GiantBlade"))
		{
			getServer().addRecipe(gHoe1);
			getServer().addRecipe(gHoe2);
			if(settings.getBoolean("LegendaryItems.CanRepair"))
				getServer().addRecipe(repair_gHoe);
		}
		if(settings.getBoolean("LegendaryItems.BlazeSword"))
		{
			getServer().addRecipe(gSword);
			if(settings.getBoolean("LegendaryItems.CanRepair"))
				getServer().addRecipe(repair_gSword);
		}
		if(settings.getBoolean("LegendaryItems.NotchApple"))
			getServer().addRecipe(notchApple);
		if(settings.getBoolean("Recipes.Saddle"))
			getServer().addRecipe(saddle);
		if(settings.getBoolean("Recipes.Nametag"))
		{
			getServer().addRecipe(nametag1);
			getServer().addRecipe(nametag2);
		}
		if(settings.getBoolean("Recipes.PackedIce"))
		{
			getServer().addRecipe(packedIce1);
			getServer().addRecipe(packedIce2);
		}
		if(settings.getBoolean("Recipes.IronBard"))
		{
			getServer().addRecipe(ironHorse1);
			getServer().addRecipe(ironHorse2);
		}
		if(settings.getBoolean("Recipes.GoldBard"))
		{
			getServer().addRecipe(goldHorse1);
			getServer().addRecipe(goldHorse2);
		}
		if(settings.getBoolean("Recipes.DiamondBard"))
		{
			getServer().addRecipe(diamondHorse1);
			getServer().addRecipe(diamondHorse2);
		}
		if(settings.getBoolean("Recipes.ClayBrick"))
			getServer().addRecipe(clayBrick);
		if(settings.getBoolean("Recipes.QuartzBlock"))
			getServer().addRecipe(quartz);
		if(settings.getBoolean("Recipes.WoolString"))
			getServer().addRecipe(woolString);
		if(settings.getBoolean("Recipes.Ice"))
			getServer().addRecipe(ice);
		if(settings.getBoolean("Recipes.Clay"))
			getServer().addRecipe(clay);
		if(settings.getBoolean("Recipes.Diorite"))
			getServer().addRecipe(diorite);
		if(settings.getBoolean("Recipes.Granite"))
			getServer().addRecipe(granite);
		if(settings.getBoolean("Recipes.Andesite"))
			getServer().addRecipe(andesite);
		if(settings.getBoolean("Recipes.Gravel"))
		{
			getServer().addRecipe(gravel1);
			getServer().addRecipe(gravel2);
		}
		if(settings.getBoolean("Mechanics.RedMushroomFermentation"))
			getServer().addRecipe(fermentedSpiderEye);
		if(settings.getBoolean("Mechanics.FermentedSkin"))
		{
			getServer().addRecipe(fermentedSkin1);
			if(settings.getBoolean("Mechanics.RedMushroomFermentation"))
				getServer().addRecipe(fermentedSkin2);
		}
		if(settings.getBoolean("Mechanics.PoisonousPotato"))
			getServer().addRecipe(poisonousPotato);
		if(settings.getBoolean("Mechanics.EmptyPotions"))
		{
			getServer().addRecipe(glassBottle);
			getServer().addRecipe(bowl);
		}
		if(settings.getBoolean("Mechanics.ReinforcedLeatherArmor"))
		{
			getServer().addRecipe(leatherBoots);
			getServer().addRecipe(leatherChestplate);
			getServer().addRecipe(leatherLeggings);
			getServer().addRecipe(leatherHelmet);
		}
		if(settings.getBoolean("LegendaryItems.GoldArmorBuff"))
		{
			getServer().addRecipe(goldBoots);
			getServer().addRecipe(goldChestplate);
			getServer().addRecipe(goldLeggings);
			getServer().addRecipe(goldHelmet);
		}
		
		if(settings.getBoolean("Mechanics.SlowArmor"))
		{
			getServer().addRecipe(ironBoots);
			getServer().addRecipe(ironChestplate);
			getServer().addRecipe(ironLeggings);
			getServer().addRecipe(ironHelmet);
			getServer().addRecipe(diamondBoots);
			getServer().addRecipe(diamondChestplate);
			getServer().addRecipe(diamondLeggings);
			getServer().addRecipe(diamondHelmet);
		}

		if(settings.getBoolean("Mechanics.MedicalKit"))
		{
			getServer().addRecipe(medicKit1);
			getServer().addRecipe(medicKit2);
			getServer().addRecipe(medicKit3);
			getServer().addRecipe(medicKit4);
			getServer().addRecipe(medicKit5);
			getServer().addRecipe(medicKit6);
		}

		if(settings.getBoolean("Recipes.FishingRod"))
		{
			getServer().addRecipe(fishingRod1);
			getServer().addRecipe(fishingRod2);
		}
		
		if(settings.getBoolean("Mechanics.ReducedIronNugget"))
		{
			getServer().addRecipe(ironNugget);
			getServer().addRecipe(ironIngot);
			getServer().addRecipe(ironBlock);
			getServer().addRecipe(smelt_ironIngot);
		}
		
		if(settings.getBoolean("Mechanics.ReducedGoldNugget"))
		{
			getServer().addRecipe(goldNugget);
			getServer().addRecipe(goldIngot);
			getServer().addRecipe(goldBlock);
			getServer().addRecipe(smelt_goldIngot);
		}

		if(settings.getBoolean("Mechanics.FarmingProducts.Bread"))
			getServer().addRecipe(bread);
		if(settings.getBoolean("Mechanics.FarmingProducts.Cookie"))
			getServer().addRecipe(cookie);
		if(settings.getBoolean("Recipes.Slimeball"))
			getServer().addRecipe(slimeball);
		if(settings.getBoolean("Recipes.Cobweb"))
			getServer().addRecipe(cobweb);
		if(settings.getBoolean("Mechanics.RecurveBow"))
		{
			getServer().addRecipe(recurveBow1);
			getServer().addRecipe(recurveBow2);
		}
	}
	
	public void BlazeSword()
	{
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run() {
                for(Player player : getServer().getOnlinePlayers())
                {
                    if(player.getInventory().getItemInMainHand().getType() == Material.GOLD_SWORD)
                    {
                        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20, 0, false));
                        Location particleLoc = player.getLocation();
                        particleLoc.setY(particleLoc.getY() + 1);
                        ParticleEffect.FLAME.display(0.5f, 0.5f, 0.5f, 0, 10, particleLoc, 64);
                        player.setFireTicks(20);
                        if(player.getHealth() > 14)
                        	player.setHealth(14);
                    }
                }
            }
        }, 1L, 10L);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run() {
                for(Player player : getServer().getOnlinePlayers())
                {
                    if(player.getInventory().getItemInMainHand().getType() == Material.GOLD_SWORD)
                    {
                        Random rand = new Random();
                    	player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                    }
                }
            }
        }, 1L, 50L);
	}
	
	public void GiantBlade()
	{
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run() {
                for(Player player : getServer().getOnlinePlayers())
                {
                	ItemStack mainItem = player.getInventory().getItemInMainHand();
                	ItemStack offItem = player.getInventory().getItemInOffHand();
                	if(mainItem.getType() == Material.GOLD_HOE)
                    {
                        Location particleLoc = player.getLocation();
                        particleLoc.setY(particleLoc.getY() + 1);
                        ParticleEffect.CRIT_MAGIC.display(0.5f, 0.5f, 0.5f, 0.5f, 10, particleLoc, 64);
                    }
                	
                    if(offItem.getType() == Material.GOLD_HOE)
                    {
                        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 1, false));
                        Location particleLoc = player.getLocation();
                        particleLoc.setY(particleLoc.getY() + 1);
                        ParticleEffect.CRIT_MAGIC.display(0.5f, 0.5f, 0.5f, 0.5f, 10, particleLoc, 64);
                    }
                    
					Score dualWield = board.getObjective("DualWield").getScore(player.getName());
                    
                    if
                    (
                    	(
                    		(
                    			mainItem.getType() == Material.GOLD_HOE
                    			|| mainItem.getType() == Material.GOLD_AXE
                    		)
                    	&&	(
                    			offItem.getType() == Material.WOOD_AXE
                    			|| offItem.getType() == Material.WOOD_SWORD
                    			|| offItem.getType() == Material.WOOD_PICKAXE
                    			|| offItem.getType() == Material.WOOD_SPADE
                    			|| offItem.getType() == Material.WOOD_HOE
                    			|| offItem.getType() == Material.STONE_AXE
                    			|| offItem.getType() == Material.STONE_SWORD
                    			|| offItem.getType() == Material.STONE_PICKAXE
                    			|| offItem.getType() == Material.STONE_SPADE
                    			|| offItem.getType() == Material.STONE_HOE
                    			|| offItem.getType() == Material.IRON_AXE
                    			|| offItem.getType() == Material.IRON_SWORD
                    			|| offItem.getType() == Material.IRON_PICKAXE
                    			|| offItem.getType() == Material.IRON_SPADE
                    			|| offItem.getType() == Material.IRON_HOE
                    			|| offItem.getType() == Material.GOLD_AXE
                    			|| offItem.getType() == Material.GOLD_SWORD
                    			|| offItem.getType() == Material.GOLD_PICKAXE
                    			|| offItem.getType() == Material.GOLD_SPADE
                    			|| offItem.getType() == Material.GOLD_HOE
                    			|| offItem.getType() == Material.DIAMOND_AXE
                    			|| offItem.getType() == Material.DIAMOND_SWORD
                    			|| offItem.getType() == Material.DIAMOND_PICKAXE
                    			|| offItem.getType() == Material.DIAMOND_SPADE
                    			|| offItem.getType() == Material.DIAMOND_HOE
                    			|| offItem.getType() == Material.BOW
                    		)
                    	)
                    	||
                    	(
                    		(
                    			offItem.getType() == Material.GOLD_HOE
                    			|| offItem.getType() == Material.GOLD_AXE
                            )
                        &&	(
                    			mainItem.getType() == Material.WOOD_AXE
                    			|| mainItem.getType() == Material.WOOD_SWORD
                    			|| mainItem.getType() == Material.WOOD_PICKAXE
                    			|| mainItem.getType() == Material.WOOD_SPADE
                    			|| mainItem.getType() == Material.WOOD_HOE
                    			|| mainItem.getType() == Material.STONE_AXE
                    			|| mainItem.getType() == Material.STONE_SWORD
                    			|| mainItem.getType() == Material.STONE_PICKAXE
                    			|| mainItem.getType() == Material.STONE_SPADE
                    			|| mainItem.getType() == Material.STONE_HOE
                    			|| mainItem.getType() == Material.IRON_AXE
                    			|| mainItem.getType() == Material.IRON_SWORD
                    			|| mainItem.getType() == Material.IRON_PICKAXE
                    			|| mainItem.getType() == Material.IRON_SPADE
                    			|| mainItem.getType() == Material.IRON_HOE
                    			|| mainItem.getType() == Material.GOLD_AXE
                    			|| mainItem.getType() == Material.GOLD_SWORD
                    			|| mainItem.getType() == Material.GOLD_PICKAXE
                    			|| mainItem.getType() == Material.GOLD_SPADE
                    			|| mainItem.getType() == Material.GOLD_HOE
                    			|| mainItem.getType() == Material.DIAMOND_AXE
                    			|| mainItem.getType() == Material.DIAMOND_SWORD
                    			|| mainItem.getType() == Material.DIAMOND_PICKAXE
                    			|| mainItem.getType() == Material.DIAMOND_SPADE
                    			|| mainItem.getType() == Material.DIAMOND_HOE
                    			|| mainItem.getType() == Material.BOW
                        	)
                        )
                    )
                    {
                        player.removePotionEffect(PotionEffectType.SLOW);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 6, true));
                        player.removePotionEffect(PotionEffectType.JUMP);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 199, true));
                        dualWield.setScore(1);
                    }
                    else
                    {
                        dualWield.setScore(0);
                    }
                }
            }
        }, 1L, 10L);
		
	}
	
	public void ObsidianMace()
	{
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run() {
                for(Player player : getServer().getOnlinePlayers())
                {
                    if(player.getInventory().getItemInMainHand().getType() == Material.GOLD_SPADE)
                    {
                        player.removePotionEffect(PotionEffectType.SLOW);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, false));
                        Location particleLoc = player.getLocation();
                        particleLoc.setY(particleLoc.getY() + 1);
                        ParticleEffect.CRIT.display(0.5f, 0.5f, 0.5f, 0.5f, 10, particleLoc, 64);
                        ParticleEffect.PORTAL.display(0.5f, 0.5f, 0.5f, 0.5f, 20, particleLoc, 64);
                    }
                }
            }
        }, 1L, 10L);
	}
	
	public void Valkyrie()
	{	
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run() {
                for(Player player : getServer().getOnlinePlayers())
                {
                    if(player.getInventory().getItemInMainHand().getType() == Material.GOLD_AXE)
                    {
                    	Location particleLoc = player.getLocation();
                    	particleLoc.setY(particleLoc.getY() + 1);
                    	ParticleEffect.CRIT_MAGIC.display(0.5f, 0.5f, 0.5f, 0.5f, 10, particleLoc, 64);
                    }
                }
            }
        }, 1L, 10L);
	}
	
	public void QuartzPickaxe()
	{
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run() {
                for(Player player : getServer().getOnlinePlayers())
                {
                    if(player.getInventory().getItemInMainHand().getType() == Material.GOLD_PICKAXE)
                    {
                        player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 9, false));
                    }
                }
            }
        }, 1L, 10L);
	}
	
	public void PlayerStatus()
	{
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run()
	    	{
	    		for(Player player : getServer().getOnlinePlayers())
                {
	    			if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
	    			{
		    			Score thirst = mainBoard.getObjective("Thirst").getScore(player.getName());
	                    if(player.getExhaustion() >= 4)
	                    {
	                        Random rand = new Random();
	                        double chance = rand.nextDouble();
	                        thirst.setScore(thirst.getScore() - (chance <= settings.getDouble("Mechanics.Thirst.DrainRate") ? 1 : 0));
	                        if(thirst.getScore() < 0)
	                            thirst.setScore(0);
	                    }
	    			}
                }
            }
	    },
	    -1L, 1L);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run()
	    	{
	    		for(Player player : getServer().getOnlinePlayers())
                {
	    			if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
	    			{	    				
		    			Score thirst = mainBoard.getObjective("Thirst").getScore(player.getName());
	                    
	                    if(thirst.getScore() <= 0)
	                    {
	                    	switch(player.getWorld().getDifficulty())
	                    	{
	                    		case EASY:
	                    			if(player.getHealth() > 10)
	                    				player.damage(1);
	                    			break;
	                    		case NORMAL:
	                    			if(player.getHealth() > 1)
	                    				player.damage(1);
	                    			break;
	                    		case HARD:
	                    			player.damage(1);
	                    			break;
	                    		default:
	                    	}
	                    }
	    			}
                }
            }
	    },
	    -1L, 320L);

		if(!settings.getBoolean("Mechanics.StatusScoreboard") && AlertInterval > 0)
		{
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
				public void run()
		    	{
		    		for(Player player : getServer().getOnlinePlayers())
	                {
		    			if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
		    			{
		    				int hunger = player.getFoodLevel();
		    				if(hunger <= 6)
		                    {
		                    	player.sendMessage(ChatColor.GOLD + Words.get("Starved, eat some food"));
		                    }
		    				
			    			Score thirst = mainBoard.getObjective("Thirst").getScore(player.getName());
		                    if(thirst.getScore() <= 6)
		                    {
		                    	player.sendMessage(ChatColor.AQUA + Words.get("Dehydrated, drink some water"));
		                    }
		    			}
	                }
	            }
		    },
		    -1L, AlertInterval * 20);
		}
	}
	
	public void FoodDiversity()
	{
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run()
	    	{
	    		for(Player player : getServer().getOnlinePlayers())
                {
	    			if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
	    			{
		    			Score carbon = mainBoard.getObjective("Carbs").getScore(player.getName());
		    			Score protein = mainBoard.getObjective("Protein").getScore(player.getName());
		    			Score salts = mainBoard.getObjective("Salts").getScore(player.getName());
	                    if(player.getExhaustion() >= 4)
	                    {
	                        carbon.setScore(carbon.getScore() - 8);
	                        if(carbon.getScore() < 0)
	                        	carbon.setScore(0);
	                        
	                        protein.setScore(protein.getScore() - 2);
	                        if(protein.getScore() < 0)
	                        	protein.setScore(0);
	                        
	                        salts.setScore(salts.getScore() - 3);
	                        if(salts.getScore() < 0)
	                        	salts.setScore(0);
	                    }
	    			}
                }
            }
	    },
	    -1L, 1L);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run()
	    	{
	    		for(Player player : getServer().getOnlinePlayers())
                {
	    			if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
	    			{
	    				Score carbon = mainBoard.getObjective("Carbs").getScore(player.getName());
		    			Score protein = mainBoard.getObjective("Protein").getScore(player.getName());
		    			Score salts = mainBoard.getObjective("Salts").getScore(player.getName());
		    			
	                    if(carbon.getScore() <= 0)
	                    {
	                    	switch(player.getWorld().getDifficulty())
	                    	{
	                    		case EASY:
	                    			player.setExhaustion(player.getExhaustion() + 2);
	                    			break;
	                    		case NORMAL:
	                    			player.setExhaustion(player.getExhaustion() + 4);
	                    			break;
	                    		case HARD:
	                    			player.setExhaustion(player.getExhaustion() + 8);
	                    			break;
	                    		default:
	                    	}
	                    }
		    			
		    			if(salts.getScore() <= 0)
		    			{
                			player.setExhaustion(player.getExhaustion() + 1);
		    				switch(player.getWorld().getDifficulty())
	                    	{
	                    		case NORMAL:
	                    			player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 0), true);
	                    			break;
	                    		case HARD:
	                    			player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 1), true);
	                    			break;
	                    		default:
	                    	}
		    			}
		    			
		    			if(protein.getScore() <= 0)
		    			{
                			player.setExhaustion(player.getExhaustion() + 1);
		    				switch(player.getWorld().getDifficulty())
	                    	{
	                    		case NORMAL:
	                    			player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 0), true);
	                    			break;
	                    		case HARD:
	                    			player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 1), true);
	                    			break;
	                    		default:
	                    	}
		    			}
	    			}
                }
            }
	    },
	    -1L, 320L);
		
		if(!settings.getBoolean("Mechanics.StatusScoreboard") && AlertInterval > 0)
		{
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
				public void run()
		    	{
		    		for(Player player : getServer().getOnlinePlayers())
	                {
		    			if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
		    			{
		    				Score carbon = mainBoard.getObjective("Carbs").getScore(player.getName());
			    			Score protein = mainBoard.getObjective("Protein").getScore(player.getName());
			    			Score salts = mainBoard.getObjective("Salts").getScore(player.getName());
		                    
			    			if(carbon.getScore() <= 480)
			    			{
		                    	player.sendMessage(ChatColor.DARK_GREEN + Words.get("Lack of carbohydrates, please intake grains and sugar!"));
			    			}
		                    
			    			if(salts.getScore() <= 180)
			    			{
		                    	player.sendMessage(ChatColor.BLUE +Words.get("Lack of vitamins and salts, please intake vegetables and fruits!"));
			    			}
	
			    			if(protein.getScore() <= 120)
			    			{
		                    	player.sendMessage(ChatColor.DARK_RED + Words.get("Lack of protein, please intake meat, poultry and dairies!"));
			    			}
		    			}
	                }
	            }
		    },
		    -1L, AlertInterval * 20);
		}
	}
	
	public static List<String> ShowThirst(Player player)
	{
		Objective thirst = Survival.mainBoard.getObjective("Thirst");
		String thirstBar = "";
		for(int i = 0; i < thirst.getScore(player.getName()).getScore(); i++)
		{
			thirstBar += "|";
		}
		for(int i = thirst.getScore(player.getName()).getScore(); i < 20; i++)
		{
			thirstBar += ".";
		}
		
		if(thirst.getScore(player.getName()).getScore() >= 40)
			thirstBar = ChatColor.GREEN + thirstBar;
		else if(thirst.getScore(player.getName()).getScore() <= 6)
			thirstBar = ChatColor.RED + thirstBar;
		else
			thirstBar = ChatColor.AQUA + thirstBar;
		
		return Arrays.asList(ChatColor.AQUA + Words.get("Thirst"), (thirstBar.length() <= 22 ? thirstBar.substring(0) : thirstBar.substring(0, 22)), thirstBar.substring(0, 2) + (thirstBar.length() > 22 ? thirstBar.substring(22) : "") + ChatColor.RESET + ChatColor.RESET);
	}
	
	public static List<String> ShowHunger(Player player)
	{
		int hunger = player.getFoodLevel();
		int saturation = (int)Math.round(player.getSaturation());
		String hungerBar = "";
		String saturationBar = ChatColor.YELLOW + "";
		for(int i = 0; i < hunger; i++)
		{
			hungerBar += "|";
		}
		for(int i = hunger; i < 20; i++)
		{
			hungerBar += ".";
		}
		for(int i = 0; i < saturation; i++)
		{
			saturationBar += "|";
		}
		
		if(hunger >= 20)
			hungerBar = ChatColor.GREEN + hungerBar;
		else if(hunger <= 6)
			hungerBar = ChatColor.RED + hungerBar;
		else
			hungerBar = ChatColor.GOLD + hungerBar;
		
		return Arrays.asList(ChatColor.GOLD + Words.get("Hunger"), hungerBar + ChatColor.RESET, saturationBar);
	}
	
	public static List<String> ShowNutrients(Player player)
	{
		List<String> nutrients = new ArrayList<>();
		int carbon = mainBoard.getObjective("Carbs").getScore(player.getName()).getScore();
		int protein = mainBoard.getObjective("Protein").getScore(player.getName()).getScore();
		int salts = mainBoard.getObjective("Salts").getScore(player.getName()).getScore();
		
		String showCarbon = Integer.toString(carbon);
		if(carbon >= 480)
			 showCarbon = ChatColor.GREEN + showCarbon;
		else
			 showCarbon = ChatColor.RED + showCarbon;
		nutrients.add(showCarbon + " " + ChatColor.DARK_GREEN + Words.get("Carbohydrates"));
		
		String showProtein = Integer.toString(protein);
		if(protein >= 120)
			showProtein = ChatColor.GREEN + showProtein;
		else
			showProtein = ChatColor.RED + showProtein;
		nutrients.add(showProtein + " " + ChatColor.DARK_RED + Words.get("Protein"));
		
		String showSalts = Integer.toString(salts);
		if(salts >= 180)
			showSalts = ChatColor.GREEN + showSalts;
		else
			showSalts = ChatColor.RED + showSalts;
		nutrients.add(showSalts + " " + ChatColor.BLUE + Words.get("Vitamins and Salts"));
		
		return nutrients;
	}
	
	public static String ShowFatigue(Player player)
	{
		int fatigue = mainBoard.getObjective("Fatigue").getScore(player.getName()).getScore();

		if(fatigue <= 0)
			return ChatColor.YELLOW + Words.get("Energized");
		else if(fatigue == 1)
			return ChatColor.LIGHT_PURPLE + Words.get("Sleepy");
		else if(fatigue == 2)
			return ChatColor.RED + Words.get("Overworked");
		else if(fatigue == 3)
			return ChatColor.WHITE + Words.get("Distressed");
		else if(fatigue >= 4)
			return ChatColor.DARK_GRAY + Words.get("Collapsed");
		else
			return "";
	}
	
	public void DaysNoSleep()
	{
		final Objective fatigue = mainBoard.getObjective("Fatigue");
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				for(Player player : getServer().getOnlinePlayers())
                {
					if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
					{

						//if(overworld.getTime() >= 14000 && overworld.getTime() < 22000)
						//{
							if(fatigue.getScore(player.getName()).getScore() == 1)
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0), true);
							else if(fatigue.getScore(player.getName()).getScore() == 2)
							{
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 0), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10, 0), true);
							}
							else if(fatigue.getScore(player.getName()).getScore() == 3)
							{
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 120, 0), true);
							}
							else if(fatigue.getScore(player.getName()).getScore() >= 4)
							{
								player.damage(100);
							}
						//}
						World overworld = player.getWorld();

						if(overworld.getTime() >= 22000 && overworld.getTime() < 22100)
						{
							fatigue.getScore(player.getName()).setScore(fatigue.getScore(player.getName()).getScore() + 1);
							
							if(fatigue.getScore(player.getName()).getScore() == 1)
								player.sendMessage(Survival.Words.get("You felt your eyelids heavy, perhaps you should get some sleep"));
							else if(fatigue.getScore(player.getName()).getScore() == 2)
								player.sendMessage(Survival.Words.get("You felt your vision blurred, you should get some sleep fast"));
							else if(fatigue.getScore(player.getName()).getScore() == 3)
								player.sendMessage(Survival.Words.get("You felt being enveloped by darkness, you must get to sleep"));
							else if(fatigue.getScore(player.getName()).getScore() >= 4)
								player.sendMessage(Survival.Words.get("You collapsed on the ground"));
						}
					}
                }
			}
		}, -1, 100);
	}
	
	public void ResetStatusScoreboard(boolean enabled)
	{
		for(Player player : getServer().getOnlinePlayers())
		{
			if(enabled)
				ScoreboardStats.SetupScorebard(player);
			else
				player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		}
	}
	
	public void BackpackCheck()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				for(Player player : getServer().getOnlinePlayers())
                {
					ItemStack backpacks[] = new ItemStack[3];
					backpacks[0] = player.getInventory().getItem(19);
					backpacks[1] = player.getInventory().getItem(22);
					backpacks[2] = player.getInventory().getItem(25);
					
					int backpackSlots[] = new int[] {19, 22, 25};
					
					int collection[][] = new int[][]
					{
						{9, 10, 11, 18, 20, 27, 28, 29},
						{12, 13, 14, 21, 23, 30, 31, 32},
						{15, 16, 17, 24, 26, 33, 34, 35}
					};
					
					for(int i = 0; i < 3; i++)
					{
						ItemStack backpackItem = player.getInventory().getItem(backpackSlots[i]);
						
						if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
						{
							if(backpackItem == null || (backpackItem != null && backpackItem.getType() == Material.AIR))
							{
								player.getInventory().setItem(backpackSlots[i], GetLockedSlotItem());
							}
							
							if(backpackItem != null && backpackItem.getType() == Material.WOOD_HOE)
							{
								for(int j = 0; j < 8; j++)
								{
									ItemStack item = player.getInventory().getItem(collection[i][j]);
									
									if(item != null && CheckIfLockedSlot(item))
									{
										player.getInventory().clear(collection[i][j]);
									}
								}
							}
							else
							{
								for(int j = 0; j < 8; j++)
								{
									ItemStack item = player.getInventory().getItem(collection[i][j]);
									if(item != null && !CheckIfLockedSlot(item))
									{
										player.getWorld().dropItem(player.getLocation(), item);
									}
									
									player.getInventory().setItem(collection[i][j], GetLockedSlotItem());
								}
							}
						}
						else
						{
							for(int j = 0; j < 8; j++)
							{
								ItemStack item = player.getInventory().getItem(collection[i][j]);
								
								if(item != null && CheckIfLockedSlot(item))
								{
									player.getInventory().clear(collection[i][j]);
								}
							}
						}
					}
                }
			}
		}, -1, 100);
	}
	
	public static boolean CheckIfLockedSlot(ItemStack item)
	{
		if(item != null && item.getType() == Material.BARRIER)
		{
			ItemMeta meta = item.getItemMeta();
			
			List<String> lore = meta.getLore();
			if(lore != null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static ItemStack GetLockedSlotItem()
	{
		ItemStack lockedSlot = new ItemStack(Material.BARRIER);
		ItemMeta meta = lockedSlot.getItemMeta();
		
		meta.setDisplayName(ChatColor.RESET + Survival.Words.get("Locked"));
		
		List<String> lore = Arrays.asList
		(
			ChatColor.RESET + "" + ChatColor.GRAY + Survival.Words.get("Missing Component")
		);
		meta.setLore(lore);
			
		lockedSlot.setItemMeta(meta);
		
		return lockedSlot;
	}
	
	public static ItemStack GetBackpackSlotUIItem()
	{
		ItemStack backpackSlot = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta meta = backpackSlot.getItemMeta();
		
		meta.setDisplayName(ChatColor.RESET + Survival.Words.get(" "));
		
		List<String> lore = Arrays.asList
		(
			ChatColor.RESET + "" + ChatColor.GRAY + Survival.Words.get("Backpack Slot")
		);
		meta.setLore(lore);
			
		backpackSlot.setItemMeta(meta);
		
		return backpackSlot;
	}
}
