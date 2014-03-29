package jp._RS_.Koorioni;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Variables {

	public static String getConfigHeader()
	{
		String k = System.getProperty("line.separator");
		String l0 = "---------" + k;
		String l1 = "WorldName   ：  ワールド名" + k;
		String l2 = "StartLocation  ：  ゲーム開始時にてテレポートする座標(x,y,z)" + k;
		String l3 = "GameTime  ：  ゲーム時間(秒)" + k;
		String l4 = "SpawnProtectionTime  ：  参加してからの無敵時間(秒)" + k;
		String l5 = "UnableRescueTimeAfterTouch  ：  タッチ後救われない時間" + k;
		String l6 = "Oni  ：  鬼の設定" + k;
		String l6a = "  SpeedLevel  ：  スピードの度合い(0~1までの小数,逃走者は0.2)" + k;
		String l6b = "  SpawnLocation  ：  鬼がスポーンする座標(x,y,z)" + k;
		String l6c = "  WaitTime  ：  鬼がスポーンするまでの猶予時間(秒)" + k;
		String l6d = "	StandByTimeOnTouch  ： タッチ後の硬直時間 " + k;
		String l7 = "Item   ：   アイテム関連の設定" + k;
		String l7a = "  Speed   ：   加速アイテムの設定" + k;
		String l7b = "  Invisibility  ：  透明化アイテムの設定" + k;
		String l7aa = "    Level   ：   レベル" + k;
		String l7ab = "    Duration  ：  効果時間(秒)" + k;
		String l7c = "    Amount  ：  配布する数(0は配らない)" + k;
		String l7d = "		Slow  :  透明効果の副作用の鈍足の設定" + k;
		String l7da = "		Level  ：  レベル" + k;
		return l0 + l1 + l2 + l3 + l4 +  l5 + l6 + l6a + l6b + l6c + l6d + l7 + l7a + l7b + l7aa + l7ab + l7c + l7d + l7da;
	}
	public static ItemStack getAutoSneakToggleItem()
	{
		ItemStack result = new ItemStack(Material.INK_SACK,1,(short)0,DyeColor.GRAY.getData());
		ItemMeta m = result.getItemMeta();
		m.setDisplayName("オートスニーク");
		result.setItemMeta(m);
		return result;
	}
}
