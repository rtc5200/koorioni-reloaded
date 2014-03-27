package jp._RS_.Koorioni;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import jp._RS_.Koorioni.Config.ConfigHandler;
import jp._RS_.Koorioni.Scoreboard.SbManager;
import jp._RS_.Koorioni.Timer.CountDown;
import jp._RS_.Koorioni.Util.ExpCalc;
import jp._RS_.Koorioni.Util.PlayerPicker;
import jp._RS_.Koorioni.Util.PlayerSort;

public class GameController {
	private Main main;
	private SbManager manager;
	private CountDown count;
	private ArrayList<BukkitTask> startTasks = new ArrayList<BukkitTask>();
	public GameController(Main main) {
		this.main = main;
		this.manager = main.getSbManager();
	}
	public void start(int a)
	{
		if(startTasks.size() != 0)
		{
			for(BukkitTask t : startTasks)
			{
				t.cancel();
			}
			startTasks.clear();
		}
		count = new CountDown(main.getConfigHandler().getGameTime(),main);
		ArrayList<Player> que = new ArrayList<Player>();
		for(Player p : PlayerSort.sort(Bukkit.getOnlinePlayers()))
		{
			manager.JoinRedTeam(p);
			p.setMaxHealth(20);
			p.setHealth(20);
			p.setFoodLevel(20);
			que.add(p);
		}
		for(Player p : PlayerPicker.pick(que, a))
		{
			manager.JoinBlueTeam(p);
			Bukkit.broadcastMessage(p.getDisplayName() + "さんが鬼に選ばれました。");
		}
		BukkitScheduler bs = Bukkit.getScheduler();
		startTasks.add(bs.runTaskLater(main, new Runnable(){
			public void run() {
				Bukkit.broadcastMessage("ゲームを開始します。");
				for(Player p : manager.getRedPlayersList())
				{
					p.teleport(main.getConfigHandler().getStartLocation());
				}
				Bukkit.broadcastMessage("鬼が放出されるまでの" + ChatColor.GOLD + main.getConfigHandler().getOniWaitTime()
						+ ChatColor.RESET+ "秒間、逃げてください。");
			}
		}, 40L));
		startTasks.add(bs.runTaskTimer(main, new Runnable(){
			private int i = new Integer(main.getConfigHandler().getOniWaitTime().toString());
			private int max = i;
			public void run() {
				if(i >= 0)
				{
					for(Player p : Bukkit.getOnlinePlayers())
					{
						p.setLevel(i);
						p.setExp(ExpCalc.calcExpTimer(i, max));
						p.playSound(p.getLocation(), Sound.NOTE_PIANO,100, 100);
					}
					i--;
				}
			}
		}, 41L, 20L));
		startTasks.add(bs.runTaskLater(main, new Runnable(){
			public void run() {
				Bukkit.broadcastMessage("鬼が放出されます....");
				for(Player p : Bukkit.getOnlinePlayers())
				{
					p.playSound(p.getLocation(), Sound.LEVEL_UP,100, 100);
				}
				for(Player p : manager.getBluePlayersList())
				{
					p.teleport(main.getConfigHandler().getOniSpawnLocation());
				}
				count.start();
				startTasks.clear();
			}
		}, main.getConfigHandler().getOniWaitTime()*20 + 42L));
	}
	public void exit()
	{
		count.setCancelled(true);
		if(startTasks.size() != 0)
		{
			for(BukkitTask t : startTasks)
			{
				t.cancel();
			}
			startTasks.clear();
		}
		Bukkit.broadcastMessage("ゲーム終了です。");
		Bukkit.broadcastMessage("スコアを集計しています....");
		ArrayList<Integer> rscs = new ArrayList<Integer>();
		ArrayList<Integer> bscs = new ArrayList<Integer>();
		ArrayList<OfflinePlayer> rps = new ArrayList<OfflinePlayer>();
		ArrayList<OfflinePlayer> bps = new ArrayList<OfflinePlayer>();
		for(OfflinePlayer p : manager.getRedPlayersList())
		{
			rps.add(p);
			rscs.add(manager.getScore(p).getScore());
		}
		for(OfflinePlayer p : manager.getBlackPlayersList())
		{
			rps.add(p);
			rscs.add(manager.getScore(p).getScore());
		}
		for(OfflinePlayer p : manager.getBluePlayersList())
		{
			bps.add(p);
			bscs.add(manager.getScore(p).getScore());
		}
		int rmax = 0;
		int bmax = 0;
		if(rscs.size() > 0)rmax = Collections.max(rscs);
		if(bscs.size() > 0)bmax = Collections.max(bscs);
		ArrayList<OfflinePlayer> tps = new ArrayList<OfflinePlayer>();
		tps.addAll(manager.getRedPlayersList());
		tps.addAll(manager.getBlackPlayersList());
		ArrayList<String> rres = new ArrayList<String>();
		ArrayList<String> bres = new ArrayList<String>();
		for(OfflinePlayer p : tps)
		{
			if(manager.getScore(p).getScore() == rmax)
			{
				rres.add(p.getName());
			}
		}
		for(OfflinePlayer p : manager.getBluePlayersList())
		{
			if(manager.getScore(p).getScore() == bmax)
			{
				bres.add(p.getName());
			}
		}
		Bukkit.broadcastMessage("スコア集計が完了しました。");
		String l0 = ChatColor.AQUA.toString() + "--------氷鬼結果レポート--------";
		String l1 = ChatColor.GREEN.toString() + "最高スコア獲得者は以下の方々です。";
		String l2 = ChatColor.RED + "逃走者" +  ChatColor.GREEN+ "チーム：" + ChatColor.RED + rres + "(" + rmax + ")";
		String l3 = ChatColor.BLUE + "鬼" + ChatColor.GREEN + "チーム：" + ChatColor.BLUE + bres + "(" + bmax + ")";
		String l4 = ChatColor.AQUA + "お疲れ様でした。";
		Bukkit.broadcastMessage(l0);
		Bukkit.broadcastMessage(l1);
		Bukkit.broadcastMessage(l2);
		Bukkit.broadcastMessage(l3);
		Bukkit.broadcastMessage(l4);
		manager.reset();
		for(Player p : Bukkit.getOnlinePlayers())
		{
			p.teleport(p.getLocation().getWorld().getSpawnLocation());
		}


	}

}
