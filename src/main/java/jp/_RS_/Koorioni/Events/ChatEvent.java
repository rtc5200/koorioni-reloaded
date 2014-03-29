package jp._RS_.Koorioni.Events;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Scoreboard.SbManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatEvent implements Listener{
	private Main main;
	private SbManager m;
	public ChatEvent(Main main)
	{
		this.main = main;
		m = main.getSbManager();
	}
	@EventHandler
	public void onChat(PlayerChatEvent e)
	{
		Player p = e.getPlayer();
		if(e.getMessage().contains("redsize"))
		{
			p.sendMessage("RedSize = " + m.getRedSize());
		}
		if(m.isRedTeam(p))
		{
			for(Player p1 : m.getRedPlayersList())
			{
				if(p1 != null)p1.sendMessage(ChatColor.AQUA + "[チーム]" + ChatColor.RESET + "<" +e.getPlayer().getDisplayName() + "> " + e.getMessage());
			}
			LogToConsole(e.getPlayer().getName(),e.getMessage());
			e.setCancelled(true);
		}else if(m.isBlueTeam(p))
		{
			for(Player p1 : m.getBluePlayersList())
			{
				if(p1 != null)p1.sendMessage(ChatColor.AQUA + "[チーム]" + ChatColor.RESET + "<" +e.getPlayer().getDisplayName() + "> " + e.getMessage());
			}
			LogToConsole(e.getPlayer().getName(),e.getMessage());
			e.setCancelled(true);
		}else if(m.isBlackTeam(p))
		{
			for(Player p1 : m.getBlackPlayersList())
			{
				if(p1 != null)p1.sendMessage(ChatColor.AQUA + "[チーム]" + ChatColor.RESET + "<" +e.getPlayer().getDisplayName() + "> " + e.getMessage());
			}
			LogToConsole(e.getPlayer().getName(),e.getMessage());
			e.setCancelled(true);
		}
	}
	private void LogToConsole(String name,String message)
	{
		Logger l = Logger.getLogger("TeamChat");
		l.info("[チーム]<" + name + "> " + message);
	}
}
