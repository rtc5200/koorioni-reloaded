package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.GameController;
import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Config.ConfigHandler;
import jp._RS_.Koorioni.Scoreboard.SbManager;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class CommandBase{
	protected CommandSender sender;
	protected String[] args;
	protected Main main;
	protected SbManager manager;
	protected GameController c;
	protected ConfigHandler config;
	public CommandBase(Main main,CommandSender sender,String[] args)
	{
		this.sender = sender;
		this.args = args;
		this.main = main;
		this.manager = main.getSbManager();
		this.c = main.getController();
		this.config =main.getConfigHandler();
		if(sender instanceof Player)ExecuteFromPlayer();
		if(sender instanceof BlockCommandSender)ExecuteFromCommandBlock();
		if(sender instanceof ConsoleCommandSender)ExecuteFromConsole();
	}
	protected void ExecuteFromPlayer()
	{
		reject(sender,"対応していません。");
	}
	protected void ExecuteFromCommandBlock()
	{
		reject(sender,"対応していません。");
	}
	protected void ExecuteFromConsole()
	{
		reject(sender,"対応していません。");
	}
	protected void reject(CommandSender sender,String s)
	{
		sender.sendMessage(ChatColor.RED + s);
	}
}
