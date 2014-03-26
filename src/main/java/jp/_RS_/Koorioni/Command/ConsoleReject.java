package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;

public abstract class ConsoleReject {
	public void ExecuteFromConsole(ConsoleCommandSender sender,String[] args)
	{
		sender.sendMessage(ChatColor.RED + "コンソールは対応していません。");
	}
}
