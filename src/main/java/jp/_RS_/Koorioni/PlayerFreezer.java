package jp._RS_.Koorioni;

import jp._RS_.Koorioni.Events.PlayerRescueEvent;
import jp._RS_.Koorioni.Events.PlayerTouchEvent;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerFreezer implements Listener {
	private Main main;
	private boolean freezed = false;
	public PlayerFreezer(Main main) {
		this.main = main;
	}
	public void setFreezed(boolean b)
	{
		freezed = b;
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e)
	{
		if(freezed)
		{
			if(!isSameLocation(e.getFrom(),e.getTo()))
			{
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onTouch(PlayerTouchEvent e)
	{
		if(freezed)e.setCancelled(true);
	}
	@EventHandler
	public void onRescue(PlayerRescueEvent e)
	{
		if(freezed)e.setCancelled(true);
	}
	private boolean isSameLocation(Location loc1, Location loc2)
	{
		if(loc1.getBlock().equals(loc2.getBlock()))
		{
			return true;
		}
		return false;
	}
}
