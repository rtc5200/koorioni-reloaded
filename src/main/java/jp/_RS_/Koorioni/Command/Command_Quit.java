package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command_Quit extends CommandBase {
	public Command_Quit(Main main,CommandSender sender,String[] args)
	{
		super(main,sender,args);
	}
	@Override
	public void ExecuteFromPlayer() {
		Player p  =(Player)sender;
		if(args.length == 1){
			if(main.getSbManager().isPlaying(p))
				{
					main.getSbManager().Quit(p);
					return;
				}
			p.sendMessage("参加していません。");
			return;
		}
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
				reject(sender,"不正な引数です。");
				return;
			}
			if(!main.getSbManager().isPlaying(p))
			{
				p.sendMessage("プレイヤーは参加していません。");
				return;
			}
			main.getSbManager().Quit(t);
		}else{
			reject(sender,"引数が多すぎます。");
		}
	}
	@Override
	public void ExecuteFromCommandBlock()
	{
		if(args.length == 1){
			reject(sender,"引数が足りません。");
			return;
		}
		else if(args.length == 2)
		{
			Player t = Bukkit.getPlayerExact(args[1]);
			if(t == null)
			{
				reject(sender,"不正な引数です。");
				return;
			}
			if(!main.getSbManager().isPlaying(t))
			{
				reject(sender,"プレイヤーは参加していません。");
				return;
			}
			main.getSbManager().Quit(t);
		}else{
			reject(sender,"引数が多すぎます。");
		}
	}
}
