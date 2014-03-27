package jp._RS_.Koorioni.Task;

import org.bukkit.scheduler.BukkitRunnable;

import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Scoreboard.SbManager;

public class TeamNumberCheckTask extends BukkitRunnable{
	private SbManager m;
	public TeamNumberCheckTask(SbManager m) {
		this.m = m;
	}
	public void run() {
		m.updateSidebar();
	}

}
