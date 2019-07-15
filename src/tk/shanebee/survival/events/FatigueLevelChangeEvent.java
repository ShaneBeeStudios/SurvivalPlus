package tk.shanebee.survival.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a player's fatigue level changes
 */
@SuppressWarnings("unused")
public class FatigueLevelChangeEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private int changed;
	private int level;
	private boolean isCancelled;

	public FatigueLevelChangeEvent(Player player, int changed, int level) {
		this.player = player;
		this.changed = changed;
		this.level = level;
		this.isCancelled = false;
	}

	/** Get the player involved in this event
	 * @return The player involved in this event
	 */
	public Player getPlayer() {
		return this.player;
	}

	/** Get the amount that changed in this event
	 * @return The amount that changed
	 */
	public int getChanged() {
		return this.changed;
	}

	/** Get the new fatigue level of the player
	 * @return The fatigue level of the player
	 */
	public int getFatigueLevel() {
		return this.level;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return this.isCancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		this.isCancelled = b;
	}

}
