package tk.shanebee.survival.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.gui.NutritionGUI;
import tk.shanebee.survival.util.Utils;

public class Nutrition implements CommandExecutor {

	private NutritionGUI gui;

	public Nutrition(Survival plugin) {
		this.gui = new NutritionGUI(plugin);
	}

	@SuppressWarnings("NullableProblems")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = ((Player) sender);
			gui.openInventory(player);
		}
		if (args.length == 1 && args[0].equalsIgnoreCase("debug")) {
			itemTest();
			return true;
		}
		Utils.log("&cThis is a player only command!");
		return true;
	}

	// Used for debugging edible items
	private void itemTest() {
		for (Material material : Material.values()) {
			boolean nut = false;
			for (tk.shanebee.survival.data.Nutrition nutrition : tk.shanebee.survival.data.Nutrition.values()) {
				if (material == nutrition.getMaterial()) {
					nut = true;
					break;
				}
			}
			if (material.isEdible()) {
				Utils.log((nut ? "&a" : "&c") + "Material: " + material);
			} else if (nut) {
				Utils.log("&5Material: " + material);
			}
		}
	}

}
