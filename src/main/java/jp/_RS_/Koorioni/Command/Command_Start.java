package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.GameController;
import jp._RS_.Koorioni.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command_Start extends CommandBase {
	public Command_Start(Main main,CommandSender sender,String[] args) {
		super(main,sender,args);
	}
	@Override
	public void ExecuteFromPlayer() {
		Player p = (Player)sender;
		if(!p.isOp())
		{
			p.sendMessage(ChatColor.RED + "権限設定を確認してください。");
			return;
		}
		if(args.length < 2)
		{
			p.sendMessage(ChatColor.RED + "引数が足りません。");
			return;
		}
		if(args.length == 2)
		{
			int a = Integer.parseInt(args[1]);
			if(a < 0)
			{
				p.sendMessage(ChatColor.RED + "引数が不正です。");
				return;
			}
			c.start(a);
		}
	}
	@Override
	public void ExecuteFromCommandBlock() {
		if(args.length == 2)
		{
			int a = Integer.parseInt(args[1]);
			if(a < 0)
			{
				sender.sendMessage(ChatColor.RED + "引数が不正です。");
				return;
			}
			c.start(a);
		}
	}
	@Override
	public void ExecuteFromConsole() {
		if(args.length == 0)
		{
			int a = Integer.parseInt(args[1]);
			if(a < 1)
			{
				sender.sendMessage(ChatColor.RED + "引数が不正です。");
				return;
			}
			c.start(a);
		}

	}

}
