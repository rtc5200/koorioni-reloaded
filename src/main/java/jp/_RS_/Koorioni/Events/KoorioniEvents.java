package jp._RS_.Koorioni.Events;

import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Scoreboard.SbManager;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Score;

public class KoorioniEvents implements Listener{
	private Main main;
	private SbManager manager;
	public KoorioniEvents(Main main)
	{
		this.main = main;
		manager = main.getSbManager();
	}
	@EventHandler(priority = EventPriority.LOW)
	public void onTouch(PlayerTouchEvent e)
	{
		if(!e.isCancelled())
		{
			manager.JoinBlackTeam(e.getTo());
			Bukkit.broadcastMessage(e.getTo().getDisplayName() + "が" + e.getBy().getDisplayName() + "に凍らされてしまった!");
			Score score = manager.getScore(e.getBy());
			score.setScore(score.getScore() + 1);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
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
