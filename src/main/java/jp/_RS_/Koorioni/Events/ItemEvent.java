package jp._RS_.Koorioni.Events;

import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Config.ConfigHandler;
import jp._RS_.Koorioni.Scoreboard.SbManager;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemEvent implements Listener{
	private Main main;
	private SbManager manager;
	public ItemEvent(Main main) {
		this.main = main;
		manager = main.getSbManager();
	}
	@EventHandler
	public void onItem(PlayerInteractEvent e)
	{
		if(e.getMaterial() == null)return;
		if(e.getMaterial().equals(Material.INK_SACK))
		{
			if(e.getItem().getData().getData() == DyeColor.GRAY.getData())
			{
				Bukkit.dispatchCommand(e.getPlayer(), "s");
			}
		}
		if(manager.isRedTeam(e.getPlayer()))
		{
			if(e.getMaterial() != null && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
					||e.getAction().equals(Action.RIGHT_CLICK_AIR)|| e.getAction().equals(Action.LEFT_CLICK_BLOCK)
					|| e.getAction().equals(Action.LEFT_CLICK_AIR))
			{
				if(e.getMaterial().equals(Material.FEATHER))
				{
					PotionEffect pe = main.getConfigHandler().getItemEffect_SPEED();
					if(pe != null)
					{
						if(!e.getPlayer().hasPotionEffect(PotionEffectType.SPEED))
						{
							e.getPlayer().addPotionEffect(pe);
							ItemUse(e.getPlayer(),e.getItem());
						}
					}
				}else if(e.getMaterial().equals(Material.STICK))
				{
					PotionEffect pe = main.getConfigHandler().getItemEffect_INVISBILITY();
					PotionEffect pe1 = main.getConfigHandler().getItemEffect_INV_SLOW();
					if(pe != null)
					{
						if(!e.getPlayer().hasPotionEffect(PotionEffectType.INVISIBILITY))
						{
							e.getPlayer().addPotionEffect(pe);
							e.getPlayer().addPotionEffect(pe1);
							ItemUse(e.getPlayer(),e.getItem());
						}
					}
				}

			}
		}
	}
	@EventHandler
	public void onDrop(PlayerDropItemEvent e)
	{
		e.setCancelled(true);
	}
	private void ItemUse(Player p,ItemStack s)
	{
		if(s.getAmount() == 1)
		{
			p.getInventory().remove(s);
		}else{
			s.setAmount(s.getAmount() - 1);
		}
	}
}
