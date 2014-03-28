package jp._RS_.Koorioni.Util;

import java.util.ArrayList;

import jp._RS_.Koorioni.Scoreboard.SbManager;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerSort {
	public static Player[] sort(SbManager m,Player[] ps)
	{
		ArrayList<Player> result = new ArrayList<Player>();
		for(Player p : ps)
		{
			if(!p.getGameMode().equals(GameMode.CREATIVE) && !m.isPlaying(p))
				{
					result.add(p);
				}
		}
		return result.toArray(new Player[result.size()]);
	}
}
