package jp._RS_.Koorioni.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRescueEvent extends Event implements Cancellable{
	private static HandlerList handlers = new HandlerList();
	private Player p1;
	private Player p2;
	private boolean cancelled;
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public HandlerList getHandlers() {
		return handlers;
	}
	/**
	 *
	 * @param p1 Player who rescued p2
	 * @param p2 Player who were rescued by p1
	 */
	public PlayerRescueEvent(Player p1,Player p2)
	{
		this.p1 = p1;
		this.p2 = p2;
		cancelled = false;
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
