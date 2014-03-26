package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command_Join extends RejectableCommandBase
{
	private Main main;
	private CommandSender sender;
	public Command_Join(Main main,CommandSender sender,String[] args)
	{
		this.main = main;
		this.sender = sender;
		if(sender instanceof Player)ExecuteFromPlayer((Player) sender,args);
		if(sender instanceof BlockCommandSender)ExecuteFromCommandBlock((BlockCommandSender) sender,args);
		if(sender instanceof ConsoleCommandSender)ExecuteFromConsole((ConsoleCommandSender) sender,args);
	}
	public void ExecuteFromPlayer(Player p, String[] args) {
		if(args.length == 1)main.getSbManager().JoinRedTeam(p);
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
				p.sendMessage("プレイヤーがいません。");
				return;
			}
			main.getSbManager().JoinRedTeam(t);
		}else{
			reject(sender,RejectReason.TooMuchArgs);
		}
	}
	public void ExecuteFromCommandBlock(BlockCommandSender sender,String[] args) {
		if(args.length == 1)
		{
			reject(sender,RejectReason.NotEnoughArgs);
			return;
		}
		if(args.length == 2)
		{
			Player t = Bukkit.getPlayerExact(args[1]);
			if(t == null)
			{
				reject(sender,RejectReason.InvalidArgs);
				return;
			}
			main.getSbManager().JoinRedTeam(t);
		}else{
			reject(sender,RejectReason.TooMuchArgs);
		}

	}
}
