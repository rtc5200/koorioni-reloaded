package jp._RS_.Koorioni.Task;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import jp._RS_.Koorioni.Main;

public class SneakingTask extends BukkitRunnable implements CommandExecutor {
	private Main main;
	private ArrayList<Player> snp = new ArrayList<Player>();//オートスニーク許可リスト
	public SneakingTask(Main main) {
		this.main = main;
	}
	public void setAutoSneak(Player p,boolean b)
	{
		if(b)
		{
			snp.add(p);
			p.setSneaking(true);
		}else{
			if(snp.contains(p))snp.remove(p);
			p.setSneaking(false);
		}
	}
	public boolean getAutoSneak(Player p)
	{
		if(snp.contains(p))return true;
		return false;
	}
	public void run() {
		for(Player p : snp)
		{
			if(p != null)
			{
				p.setSneaking(true);
			}
		}

	}
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(sender instanceof Player)
		{
			Player p =(Player)sender;
			if(getAutoSneak(p))
			{
				setAutoSneak(p,false);
				p.sendMessage("オートスニークを" + ChatColor.GOLD + "オフ" + ChatColor.RESET + "にしました。");
				return true;
			}else{
				setAutoSneak(p,true);
				p.sendMessage("オートスニークを" + ChatColor.GOLD + "オン" + ChatColor.RESET + "にしました。");
				return true;
			}
		}else{
			sender.sendMessage(ChatColor.RED + "プレイヤー以外からは実行できません。");
		}
		return false;
	}

}
