package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command_Join extends CommandBase
{
	public Command_Join(Main main,CommandSender sender,String[] args)
	{
		super(main,sender,args);
	}
	@Override
	public void ExecuteFromPlayer() {
		Player p = (Player) sender;
		if(args.length == 1)main.getSbManager().JoinRedTeam(p);
		else if(args.length == 2)
		{
			if(!p.isOp())
			{
				reject(sender,"権限設定を確認してください。");
				return;
			}
			Player t = Bukkit.getPlayerExact(args[1]);
			if(t == null)
			{
				p.sendMessage("プレイヤーがいません。");
				return;
			}
			main.getSbManager().JoinRedTeam(t);
			p.teleport(main.getConfigHandler().getStartLocation());
		}else{
			reject(sender,"引数が多すぎます。");
		}
	}
	@Override
	public void ExecuteFromCommandBlock() {
		if(args.length == 1)
		{
			reject(sender,"引数が不足しています。");
			return;
		}
		if(args.length == 2)
		{
			Player t = Bukkit.getPlayerExact(args[1]);
			if(t == null)
			{
				reject(sender,"不正な引数です。");
				return;
			}
			main.getSbManager().JoinRedTeam(t);
			t.teleport(main.getConfigHandler().getStartLocation());
		}else{
			reject(sender,"引数が多すぎます。");
		}

	}
}
