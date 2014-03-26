package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.GameController;
import jp._RS_.Koorioni.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command_Exit implements CommandBase {
	private GameController c;
	public Command_Exit(Main main,CommandSender sender,String[] args) {
		c = main.getController();
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
		if(args.length  == 1)
		{
			c.exit();
			return;
		}
		p.sendMessage(ChatColor.RED + "引数が多すぎます。");
		return;
	}

	public void ExecuteFromCommandBlock(BlockCommandSender sender, String[] args) {
		if(args.length  == 1)
		{
			c.exit();
			return;
		}
	}

	public void ExecuteFromConsole(ConsoleCommandSender sender, String[] args) {
		if(args.length  == 1)
		{
			c.exit();
			return;
		}
		sender.sendMessage(ChatColor.RED + "引数が多すぎます。");
		return;
	}

}
