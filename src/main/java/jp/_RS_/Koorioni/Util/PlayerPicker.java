package jp._RS_.Koorioni.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

public class PlayerPicker {

	public static ArrayList<Player> pick(List<Player>list,int amount)
	{
		ArrayList<Player> result = new ArrayList<Player>();
		Random r = new Random();
		Collections.shuffle(list,r);
		for(int i = 0; i < amount;i++)
		{
			int t = r.nextInt(list.size());
			result.add(list.get(t));
			list.remove(t);
		}
		return result;
	}

}
