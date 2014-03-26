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

import jp._RS_.Koorioni.Config.ConfigHandler;
import jp._RS_.Koorioni.Scoreboard.SbManager;
import jp._RS_.Koorioni.Timer.CountDown;
import jp._RS_.Koorioni.Util.PlayerPicker;
import jp._RS_.Koorioni.Util.PlayerSort;

public class GameController {
	private Main main;
	private SbManager manager;
	private ConfigHandler config;
	private CountDown count;
	public GameController(Main main) {
		this.main = main;
		this.manager = main.getSbManager();
		this.config = main.getConfigHandler();

	}
	public void start(int a)
	{
		count = new CountDown(config.getGameTime(),main);
		ArrayList<Player> que = new ArrayList<Player>();
		for(Player p : PlayerSort.sort(Bukkit.getOnlinePlayers()))
		{
			manager.JoinRedTeam(p);
			que.add(p);
		}
		for(Player p : PlayerPicker.pick(que, a))
		{
			manager.JoinBlueTeam(p);
			Bukkit.broadcastMessage(p.getDisplayName() + "さんが鬼に選ばれました。");
		}
		BukkitScheduler bs = Bukkit.getScheduler();
		bs.runTaskLater(main, new Runnable(){
			public void run() {

				Bukkit.broadcastMessage("ゲームを開始します。");
				for(Player p : manager.getRedPlayersList())
				{
					p.teleport(config.getStartLocation());
				}
				Bukkit.broadcastMessage("鬼が放出されるまでの30秒間、逃げてください。");
			}
		}, 40L);
		bs.runTaskTimer(main, new Runnable(){
			private int i = 30;
			public void run() {
				if(i >= 0)
				{
					for(Player p : Bukkit.getOnlinePlayers())
					{
						p.setLevel(i);
						p.playSound(p.getLocation(), Sound.NOTE_PIANO,100, 100);
					}
					i--;
				}
			}

		}, 41L, 20L);
		bs.runTaskLater(main, new Runnable(){
			public void run() {
				Bukkit.broadcastMessage("鬼が放出されます....");
				for(Player p : Bukkit.getOnlinePlayers())
				{
					p.playSound(p.getLocation(), Sound.LEVEL_UP,100, 100);
				}
				for(Player p : manager.getBluePlayersList())
				{
					p.teleport(config.getStartLocation());
				}
				count.start();
			}
		}, 642L);
	}
	public void exit()
	{
		count.setCancelled(true);
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
		String l2 = ChatColor.RED + "逃走者" +  ChatColor.GREEN+ "チーム：" + ChatColor.RED + rres;
		String l3 = ChatColor.BLUE + "鬼" + ChatColor.GREEN + "チーム：" + ChatColor.BLUE + bres;
		String l4 = ChatColor.AQUA + "お疲れ様でした。";
		Bukkit.broadcastMessage(l0);
		Bukkit.broadcastMessage(l1);
		Bukkit.broadcastMessage(l2);
		Bukkit.broadcastMessage(l3);
		Bukkit.broadcastMessage(l4);
		manager.reset();


	}

}
