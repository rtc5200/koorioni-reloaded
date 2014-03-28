package jp._RS_.Koorioni.Events;

import java.util.Set;

import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Scoreboard.SbManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatEvent implements Listener{
	private SbManager m;
	public ChatEvent(Main main)
	{
		m = main.getSbManager();
	}
	@EventHandler
	public void onChat(PlayerChatEvent e)
	{
		Player p = e.getPlayer();
		if(m.isRedTeam(p))
		{
			for(Player p1 : m.getRedPlayersList())
			{
				if(p1 != null)p1.sendMessage(ChatColor.AQUA + "[チーム]" + ChatColor.RESET + "<" +e.getPlayer().getDisplayName() + "> " + e.getMessage());
				e.setCancelled(true);
			}
		}else if(m.isBlueTeam(p))
		{
			for(Player p1 : m.getBluePlayersList())
			{
				if(p1 != null)p1.sendMessage(ChatColor.AQUA + "[チーム]" + ChatColor.RESET + "<" +e.getPlayer().getDisplayName() + "> " + e.getMessage());
				e.setCancelled(true);
			}
		}else if(m.isBlackTeam(p))
		{
			for(Player p1 : m.getBlackPlayersList())
			{
				if(p1 != null)p1.sendMessage(ChatColor.AQUA + "[チーム]" + ChatColor.RESET + "<" +e.getPlayer().getDisplayName() + "> " + e.getMessage());
				e.setCancelled(true);
			}
		}
	}
}
