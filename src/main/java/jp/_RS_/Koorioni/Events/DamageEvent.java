package jp._RS_.Koorioni.Events;

import java.util.ArrayList;

import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Scoreboard.SbManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DamageEvent implements Listener{
	private Main main;
	private SbManager manager;
	public DamageEvent(Main main)
	{
		this.main = main;
		manager = main.getSbManager();
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDamageByEntity(EntityDamageByEntityEvent e)
	{
		if(e.getCause().equals(DamageCause.ENTITY_ATTACK))
		{
			if(e.getDamager() instanceof Player && e.getEntity() instanceof Player)
			{
				Player p1 = (Player) e.getDamager();
				Player p2 = (Player) e.getEntity();
				if(manager.isBlueTeam(p1) && manager.isRedTeam(p2))
				{
					Bukkit.getServer().getPluginManager().callEvent(new PlayerTouchEvent(p1,p2));
				}else if(manager.isRedTeam(p1) && manager.isBlackTeam(p2))
				{
					Bukkit.getServer().getPluginManager().callEvent(new PlayerRescueEvent(p1,p2));
				}
				if(manager.isPlaying(p1) && manager.isPlaying(p2))
				{
					e.setDamage(0);
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onDamage(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			e.setDamage(0);
			e.setCancelled(true);
		}
	}
}
