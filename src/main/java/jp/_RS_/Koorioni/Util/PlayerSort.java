package jp._RS_.Koorioni.Util;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerSort {
	public static Player[] sort(Player[] ps)
	{
		ArrayList<Player> result = new ArrayList<Player>();
		for(Player p : ps)
		{
			if(!p.getGameMode().equals(GameMode.CREATIVE))result.add(p);
		}
		return result.toArray(new Player[result.size()]);
	}
}
