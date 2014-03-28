package jp._RS_.Koorioni.Events;

import java.util.ArrayList;
import java.util.HashMap;

import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Scoreboard.SbManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

public class PlayerExceptionOfEvent implements Listener {
	private Main main;
	private SbManager m;
	private ArrayList<Player> exc = new ArrayList<Player>();
	private ArrayList<BukkitTask> tasks = new ArrayList<BukkitTask>();
	public PlayerExceptionOfEvent(Main  main)
	{
		this.main = main;
		this.m = main.getSbManager();
	}
	@EventHandler(priority = EventPriority.LOW)
	public void onTouch(PlayerTouchEvent e)
	{
		if(exc.contains(e.getTo()))
		{
			e.setCancelled(true);
			e.getTo().sendMessage(ChatColor.RED + "スポーン保護適用中です。");
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void onRescue(PlayerRescueEvent e)
	{
		if(exc.contains(e.getBy()))
		{
			e.setCancelled(true);
			e.getBy().sendMessage(ChatColor.RED + "スポーン保護適用中です。");
		}
	}
	public void addException(Player p)
	{
		final Player t = p;
		exc.add(t);
		tasks.add(Bukkit.getScheduler().runTaskLater(main, new Runnable(){
			public void run() {
				exc.remove(t);
				tasks.remove(this);
			}
		}, main.getConfigHandler().getSpawnProtectionTime()*20L));
	}
	public void clearExceptionList()
	{
		for(BukkitTask t : tasks)
		{
			t.cancel();
		}
	tasks.clear();
		exc.clear();
	}
}
