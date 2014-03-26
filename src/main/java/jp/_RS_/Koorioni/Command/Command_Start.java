package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.GameController;
import jp._RS_.Koorioni.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command_Start implements CommandBase {
	private GameController c;
	public Command_Start(Main main,CommandSender sender,String[] args) {
		this.c = main.getController();
		if(sender instanceof Player)ExecuteFromPlayer((Player) sender,args);
		if(sender instanceof BlockCommandSender)ExecuteFromCommandBlock((BlockCommandSender) sender,args);
		if(sender instanceof ConsoleCommandSender)ExecuteFromConsole((ConsoleCommandSender) sender,args);
	}

	public void ExecuteFromPlayer(Player p, String[] args) {
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

	public void ExecuteFromCommandBlock(BlockCommandSender sender, String[] args) {
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

	public void ExecuteFromConsole(ConsoleCommandSender sender, String[] args) {
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
