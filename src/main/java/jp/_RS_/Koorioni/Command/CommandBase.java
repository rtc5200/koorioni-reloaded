package jp._RS_.Koorioni.Command;

import jp._RS_.Koorioni.Main;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface CommandBase{
	public void ExecuteFromPlayer(Player p,String[] args);
	public void ExecuteFromCommandBlock(BlockCommandSender sender,String[] args);
	public void ExecuteFromConsole(ConsoleCommandSender sender,String[] args);

}
