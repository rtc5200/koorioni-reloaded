package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Config.ConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command_Reload extends CommandBase {
	public Command_Reload(Main main,CommandSender sender,String[] args) {
		super(main,sender,args);
	}
	@Override
	public void ExecuteFromPlayer() {
		Player p = (Player)sender;
		if(!p.isOp())
		{
			reject(sender,"権限設定を確認してください。");
			return;
		}
		if(args.length  == 1)
		{
			config.reload();
			p.sendMessage(ChatColor.GREEN + "リロードしました。");
			return;
		}
		reject(sender, "引数が多すぎます。");
		return;
	}
	@Override
	public void ExecuteFromCommandBlock() {
		if(args.length  == 1)
		{
			config.reload();
			return;
		}
		return;
	}
	@Override
	public void ExecuteFromConsole() {
		if(args.length  == 1)
		{
			config.reload();
			sender.sendMessage(ChatColor.GREEN + "リロードしました。");
			return;
		}
		reject(sender,"引数が多すぎます。");
		return;
	}
}
