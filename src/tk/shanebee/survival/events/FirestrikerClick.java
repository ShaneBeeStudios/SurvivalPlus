package tk.shanebee.survival.events;

import org.bukkit.block.data.Lightable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.Items;
import tk.shanebee.survival.util.Utils;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class FirestrikerClick implements Listener {

	@EventHandler
	private void onItemClick(PlayerInteractEvent event) {
		if (event.hasItem()) {
			Player player = event.getPlayer();
			if (event.getClickedBlock() == null) return;
            if (event.getItem() != null && Items.compare(event.getItem(), Items.FIRESTRIKER)) {
				if (player.isSneaking()) {
					if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (player.getInventory().getItemInMainHand().getType() == event.getItem().getType())
							player.getInventory().setItemInMainHand(null);
						else if (player.getInventory().getItemInOffHand().getType() == event.getItem().getType())
							player.getInventory().setItemInOffHand(null);
						player.updateInventory();

						Random rand = new Random();
						player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_SHOVEL_FLATTEN, 1.0F, rand.nextFloat() * 0.4F + 0.8F);

						Inventory firestriker = Bukkit.createInventory(player, InventoryType.FURNACE, Utils.getColoredString(Survival.lang.firestriker));
						player.openInventory(firestriker);
						firestriker.setItem(1, event.getItem());
						event.setCancelled(true);
					}
				} else {
					if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						switch (event.getClickedBlock().getType()) {
							case ENCHANTING_TABLE:
							case ANVIL:
							case BREWING_STAND:
							case SPRUCE_DOOR:
							case BIRCH_DOOR:
							case OAK_DOOR:
							case JUNGLE_DOOR:
							case ACACIA_DOOR:
							case DARK_OAK_DOOR:
							case IRON_DOOR:
							case TRAPPED_CHEST:
							case CHEST:
							case NOTE_BLOCK:
							case IRON_TRAPDOOR:
							case FURNACE:
							case HOPPER:
							case CRAFTING_TABLE:
							case DROPPER:
							case DISPENSER:
							case REDSTONE_WALL_TORCH:
							case REDSTONE_TORCH:
								return;
							default:
						}
						if (Tag.BEDS.isTagged(event.getClickedBlock().getType())) return;
						if (Utils.isWoodGate(event.getClickedBlock().getType())) return;
						if (Tag.TRAPDOORS.isTagged(event.getClickedBlock().getType())) return;
						if (event.getClickedBlock().getType() == Material.CAMPFIRE) {
							Lightable camp = ((Lightable) event.getClickedBlock().getBlockData());
							if (camp.isLit()) return;
							camp.setLit(true);
							event.getClickedBlock().setBlockData(camp);
						}
						Location loc = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
						if (ignite(player, loc)) {
							Random rand = new Random();
							player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_SHOVEL_FLATTEN, 1.0F, rand.nextFloat() * 0.4F + 0.8F);

							ItemMeta meta = event.getItem().getItemMeta();
							((Damageable) meta).setDamage(((Damageable) meta).getDamage() + 7);
							event.getItem().setItemMeta(meta);
							if (((Damageable) event.getItem().getItemMeta()).getDamage() >= 56) {
								player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
								if (player.getInventory().getItemInMainHand().getType() == event.getItem().getType())
									player.getInventory().setItemInMainHand(null);
								else if (player.getInventory().getItemInOffHand().getType() == event.getItem().getType())
									player.getInventory().setItemInOffHand(null);
							}
							player.updateInventory();
						}
					}
				}
			}
		}
	}

	private boolean ignite(Player igniter, Location loc) {
		Random rand = new Random();

		loc.add(0.5, 0.5, 0.5);

		BlockIgniteEvent igniteEvent = new BlockIgniteEvent(loc.getBlock(),
				IgniteCause.FLINT_AND_STEEL, igniter);
		Bukkit.getServer().getPluginManager().callEvent(igniteEvent);
		if (igniteEvent.isCancelled()) {
			return false;
		}

		BlockState blockState = loc.getBlock().getState();

		BlockPlaceEvent placeEvent = new BlockPlaceEvent(loc.getBlock(),
				blockState, loc.getBlock(), igniter.getInventory().getItemInMainHand(), igniter, true, EquipmentSlot.HAND);
		Bukkit.getServer().getPluginManager().callEvent(placeEvent);

		if (placeEvent.isCancelled() || !placeEvent.canBuild()) {
			placeEvent.getBlockPlaced().getState().setType(Material.AIR);
			return false;
		}


		loc.getWorld().playSound(loc, Sound.ITEM_FLINTANDSTEEL_USE, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
		loc.getBlock().setType(Material.FIRE);

		return true;
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.getView().getTitle().equalsIgnoreCase(Utils.getColoredString(Survival.lang.firestriker))) {
			final Player player = (Player) event.getWhoClicked();

			switch (event.getRawSlot()) {
				case 1:
					event.setCancelled(true);
					player.closeInventory();
					break;

				case 2:
					event.setCancelled(true);
					if (event.getClick() == ClickType.LEFT) {
						if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) {
							if (event.getInventory().getItem(2) == null || event.getInventory().getItem(2).getType() == Material.AIR) {
								if (event.getInventory().getItem(1) != null && event.getInventory().getItem(1).getType() == Material.WOODEN_SHOVEL) {
									if (smeltCheck(event.getInventory(), event.getInventory().getItem(0))) {
										event.setCancelled(true);
										Random randBurn = new Random();
										player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1.0F, randBurn.nextFloat() * 0.4F + 0.8F);
										player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_BURN, 1.0F, randBurn.nextFloat() * 0.4F + 0.8F);

										ItemStack i_smelt = event.getInventory().getItem(0);
										i_smelt.setAmount(i_smelt.getAmount() - 1);
										event.getInventory().setItem(0, i_smelt);

										ItemStack i_firecracker = event.getInventory().getItem(1);
										ItemMeta meta = i_firecracker.getItemMeta();
										((Damageable) meta).setDamage(((Damageable) meta).getDamage() + 7);
										i_firecracker.setItemMeta(meta);

										if (((Damageable) i_firecracker.getItemMeta()).getDamage() >= 56) {
											Random rand = new Random();
											player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
											event.getInventory().setItem(1, null);
										}
									}
								}
							} else {
								event.getView().setCursor(new ItemStack(event.getInventory().getItem(2)));
								event.getInventory().setItem(2, null);
							}
						} else {
							event.setCancelled(true);
						}
					} else {
						event.setCancelled(true);
					}
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Survival.instance, player::updateInventory, 1L);
					break;
			}
		}
	}

	private boolean smeltCheck(Inventory inv, ItemStack item) {
		boolean canSmelt = true;

		if (item == null)
			canSmelt = false;
		else if (item.getType() == Material.PORKCHOP)
			inv.setItem(2, new ItemStack(Material.COOKED_PORKCHOP));
		else if (item.getType() == Material.BEEF)
			inv.setItem(2, new ItemStack(Material.COOKED_BEEF));
		else if (item.getType() == Material.CHICKEN)
			inv.setItem(2, new ItemStack(Material.COOKED_CHICKEN));
		else if (item.getType() == Material.SALMON)
			inv.setItem(2, new ItemStack(Material.COOKED_SALMON));
		else if (item.getType() == Material.COD)
			inv.setItem(1, new ItemStack((Material.COOKED_COD)));
		else if (item.getType() == Material.POTATO)
			inv.setItem(2, new ItemStack(Material.BAKED_POTATO));
		else if (item.getType() == Material.MUTTON)
			inv.setItem(2, new ItemStack(Material.COOKED_MUTTON));
		else if (item.getType() == Material.RABBIT)
			inv.setItem(2, new ItemStack(Material.COOKED_RABBIT));
		else if (item.getType() == Material.SAND)
			inv.setItem(2, new ItemStack(Material.GLASS));
		else if (item.getType() == Material.CLAY_BALL)
			inv.setItem(2, new ItemStack(Material.BRICK));
		else if (Tag.LOGS.isTagged(item.getType()))
			inv.setItem(2, new ItemStack(Material.CHARCOAL));
		else
			canSmelt = false;

		return canSmelt;
	}

	@EventHandler
	private void onCloseInventory(InventoryCloseEvent event) {
		if (event.getView().getTitle().equalsIgnoreCase(Utils.getColoredString(Survival.lang.firestriker))) // Update due to deprecation
		{
			Inventory inv = event.getInventory();
			Player player = (Player) event.getPlayer();
			if (inv.getItem(0) != null)
				player.getWorld().dropItem(player.getLocation(), inv.getItem(0));
			if (inv.getItem(1) != null && inv.getItem(1).getType() != Material.BARRIER)
				player.getInventory().addItem(inv.getItem(1));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onAttack(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity && event.getCause() == DamageCause.ENTITY_ATTACK) {
			Player player = (Player) event.getDamager();
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item.getType() == Material.WOODEN_SHOVEL) {
				ItemMeta meta = item.getItemMeta();
				((Damageable) meta).setDamage(((Damageable) meta).getDamage() - 2);
				item.setItemMeta(meta);
			}
		}
	}

}