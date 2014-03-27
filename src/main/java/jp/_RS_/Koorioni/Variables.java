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
		String l4 = "Oni  ：  鬼の設定" + k;
		String l4a = "  SpeedLevel  ：  スピードの度合い(0~1までの小数,逃走者は0.2)" + k;
		String l4b = "  SpawnLocation  ：  鬼がスポーンする座標(x,y,z)" + k;
		String l4c = "  WaitTime  ：  鬼がスポーンするまでの猶予時間(秒)" + k;
		String l5 = "Item   ：   アイテム関連の設定" + k;
		String l5a = "  Speed   ：   加速アイテムの設定" + k;
		String l5b = "  Invisibility  ：  透明化アイテムの設定" + k;
		String l5aa = "    Level   ：   レベル" + k;
		String l5ab = "    Duration  ：  効果時間(秒)" + k;
		String l5c = "    Amount  ：  配布する数(0は配らない)" + k;
		return l0 + l1 + l2 + l3 + l4 + l4a + l4b + l4c + l5 + l5a + l5b + l5aa + l5ab + l5c;
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
