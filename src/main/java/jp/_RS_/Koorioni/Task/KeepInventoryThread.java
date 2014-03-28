package jp._RS_.Koorioni.Task;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KeepInventoryThread extends Thread {
	private Player p;
	private ItemStack[] content;
	private ItemStack[] armor;
	public KeepInventoryThread(Player p,ItemStack[] content,ItemStack[] armor)
	{
		this.p = p;
		this.content = content;
		this.armor = armor;
	}
	@Override
	public void run()
	{
		p.getInventory().setArmorContents(armor);
		p.getInventory().setContents(content);
	}

}
