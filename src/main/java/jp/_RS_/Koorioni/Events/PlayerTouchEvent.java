package jp._RS_.Koorioni.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTouchEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private Player p1;
	private Player p2;
	private boolean cancelled;
	/**
	 *
	 * @param p1 Player who touched p2
	 * @param p2 Player who were touched by p1
	 */
	public PlayerTouchEvent(Player p1,Player p2)
	{
		this.p1 = p1;
		this.p2 = p2;
		cancelled = false;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public Player getBy()
	{
		return p1;
	}
	public Player getTo()
	{
		return p2;
	}
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean b) {
		cancelled = b;
	}

}
