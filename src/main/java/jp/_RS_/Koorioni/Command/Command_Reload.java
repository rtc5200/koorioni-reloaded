package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Config.ConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command_Reload implements CommandBase {
	private ConfigHandler config;
	public Command_Reload(Main main,CommandSender sender,String[] args) {
		config = main.getConfigHandler();
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
			config.reload();
			p.sendMessage(ChatColor.GREEN + "リロードしました。");
			return;
		}
		p.sendMessage(ChatColor.RED + "引数が多すぎます。");
		return;

	}

	public void ExecuteFromCommandBlock(BlockCommandSender sender, String[] args) {
		if(args.length  == 1)
		{
			config.reload();
			return;
		}
		return;

	}

	public void ExecuteFromConsole(ConsoleCommandSender sender, String[] args) {
		if(args.length  == 1)
		{
			config.reload();
			sender.sendMessage(ChatColor.GREEN + "リロードしました。");
			return;
		}
		sender.sendMessage(ChatColor.RED + "引数が多すぎます。");
		return;


	}

}
