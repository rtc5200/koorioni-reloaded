package jp._RS_.Koorioni.Timer;




import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Util.ExpCalc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CountDown{
	protected long time;
	protected long max;
	protected boolean canceled = false;
	protected Main plugin;
	protected CountDown cd = this;
	public CountDown(long time, Main plugin){
		this.time = time;
		this.max = time;
		this.plugin = plugin;
	}
	public void start(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new CD(), 20, 20);
	}

	public void setCancelled(boolean cancel){
		canceled = cancel;
	}

	private class CD implements Runnable{

		public void run(){
			if(time <= 0 && !canceled){
				canceled = true;
				for(Player p : Bukkit.getOnlinePlayers())
				{
					p.setLevel(0);
					p.setExp(0);
				}
				plugin.getController().exit();
				//カウントダウン終了
			}
			if(!canceled){
				time--;
				for(Player p : Bukkit.getOnlinePlayers())
				{
					p.setLevel((int) time);
					p.setExp(ExpCalc.calcExpTimer(time, max));
					if(time <= 10)
					{
						Bukkit.broadcastMessage("残り" + ChatColor.GOLD + time + ChatColor.RESET + "秒です。");
						p.playSound(p.getLocation(), Sound.NOTE_PIANO, 100, 50);
					}else if(time <= 30)
					{
						if(time == 30)Bukkit.broadcastMessage("残り" + ChatColor.GOLD + "30" + ChatColor.RESET + "秒です。");
						p.playSound(p.getLocation(), Sound.NOTE_STICKS, 100, 50);
					}
				}
				//カウントダウン中
			}
		}
	}
}
