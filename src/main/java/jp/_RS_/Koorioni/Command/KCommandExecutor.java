package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KCommandExecutor implements CommandExecutor{
	private Main main;
	public KCommandExecutor(Main main)
	{
		this.main = main;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(args.length < 1)
		{
			sender.sendMessage(ChatColor.RED + "引数が足りません。");
			return true;
		}
		if(args[0].equalsIgnoreCase("join"))
		{
			new Command_Join(main,sender,args);
		}else if(args[0].equalsIgnoreCase("quit"))
		{
			new Command_Quit(main,sender,args);
		}else if(args[0].equalsIgnoreCase("start"))
		{
			new Command_Start(main,sender,args);
		}else if(args[0].equalsIgnoreCase("exit"))
		{
			new Command_Exit(main,sender,args);
		}else if(args[0].equalsIgnoreCase("reload"))
		{
			new Command_Reload(main,sender,args);
		}
		return true;
	}

}
