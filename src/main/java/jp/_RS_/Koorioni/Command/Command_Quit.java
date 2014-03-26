package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command_Quit extends RejectableCommandBase {
	private Main main;
	private CommandSender sender;
	public Command_Quit(Main main,CommandSender sender,String[] args)
	{
		this.main = main;
		this.sender = sender;
		if(sender instanceof Player)ExecuteFromPlayer((Player) sender,args);
		if(sender instanceof BlockCommandSender)ExecuteFromCommandBlock((BlockCommandSender) sender,args);
		if(sender instanceof ConsoleCommandSender)ExecuteFromConsole((ConsoleCommandSender) sender,args);
	}
	public void ExecuteFromPlayer(Player p, String[] args) {
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
				reject(sender,RejectReason.NotAllowed);
				return;
			}
			Player t = Bukkit.getPlayerExact(args[1]);
			if(t == null)
			{
				reject(sender,RejectReason.InvalidArgs);
				return;
			}
			if(!main.getSbManager().isPlaying(p))
			{
				p.sendMessage("プレイヤーは参加していません。");
				return;
			}
			main.getSbManager().Quit(t);
		}else{
			reject(sender,RejectReason.TooMuchArgs);
		}
	}
	public void ExecuteFromCommandBlock(BlockCommandSender sender, String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
