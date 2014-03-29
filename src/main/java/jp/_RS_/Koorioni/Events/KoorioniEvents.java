package jp._RS_.Koorioni.Events;

import jp._RS_.Koorioni.GameController;
import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Scoreboard.SbManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Score;

public class KoorioniEvents implements Listener{
	private Main main;
	private SbManager manager;
	private GameController c;
	private BukkitTask ft;
	private CoolTimeManager ct;
	public KoorioniEvents(Main main)
	{
		this.main = main;
		manager = main.getSbManager();
		c = main.getController();
		ct = main.getCoolTimeManager();
	}
	@EventHandler
	public void onTouch(PlayerTouchEvent e)
	{
		if(!e.isCancelled())
		{
			manager.JoinBlackTeam(e.getTo());
			Bukkit.broadcastMessage(e.getTo().getDisplayName() + "が" + e.getBy().getDisplayName() + "に凍らされてしまった!");
			Score score = manager.getScore(e.getBy());
			score.setScore(score.getScore() + 1);
			ct.addOniStiff(e.getBy());
			ct.addOnTouchProtection(e.getTo());
			if(ft == null)
			{
				if(manager.getRedSize() <= 0)
				{
					Bukkit.broadcastMessage(ChatColor.GREEN + "生存者" + ChatColor.RESET + "が一人もいないため,5秒後に終了します。");
					ft = Bukkit.getScheduler().runTaskLater(main, new Runnable(){
						public void run() {
							c.exit();
							ft = null;
						}
					}, 100L);
			}
			}
		}
	}
	@EventHandler
	public void onRescue(PlayerRescueEvent e)
	{
		if(!e.isCancelled())
		{
			manager.RestoreToRedTeam(e.getTo());
			Bukkit.broadcastMessage(e.getTo().getDisplayName() + "が" + e.getBy().getDisplayName() + "に助けられた!");
			Score score = manager.getScore(e.getBy());
			score.setScore(score.getScore() + 1);
		}
	}

}
