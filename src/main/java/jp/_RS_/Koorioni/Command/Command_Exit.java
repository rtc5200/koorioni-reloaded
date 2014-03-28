package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.GameController;
import jp._RS_.Koorioni.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command_Exit extends CommandBase {
	public Command_Exit(Main main,CommandSender sender,String[] args) {
		super(main,sender,args);
	}
	@Override
	public void ExecuteFromPlayer() {
		Player p = (Player) sender;
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
	@Override
	public void ExecuteFromCommandBlock() {
		if(args.length  == 1)
		{
			c.exit();
			return;
		}
	}
	@Override
	public void ExecuteFromConsole() {
		if(args.length  == 1)
		{
			c.exit();
			return;
		}
		sender.sendMessage(ChatColor.RED + "引数が多すぎます。");
		return;
	}

}
