package jp._RS_.Koorioni.Scoreboard;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import jp._RS_.Koorioni.Main;

public class SbManager implements Listener {
	private Main main;
	private Scoreboard sb;
	private Team red;
	private Team blue;
	private Team black;
	private Objective ob;
	public SbManager(Main main)
	{
		this.main = main;
		initialize();
	}
	private void initialize()
	{
		sb = main.getServer().getScoreboardManager().getNewScoreboard();
		red = sb.registerNewTeam("red");
		blue = sb.registerNewTeam("blue");
		black = sb.registerNewTeam("black");
		ob = sb.registerNewObjective("KoorioniScore", "dummy");
		ob.setDisplayName("");
		ob.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		blue.setAllowFriendlyFire(false);
		black.setAllowFriendlyFire(false);
		red.setPrefix(ChatColor.RED.toString());
		red.setSuffix(ChatColor.RESET.toString());
		blue.setPrefix(ChatColor.BLUE.toString());
		blue.setSuffix(ChatColor.RESET.toString());
		black.setPrefix(ChatColor.BLACK.toString());
		black.setSuffix(ChatColor.RESET.toString());
		red.setCanSeeFriendlyInvisibles(true);
		for(Player p : Bukkit.getOnlinePlayers())
		{
			p.setScoreboard(sb);
			p.setDisplayName(ChatColor.RESET + p.getName() + ChatColor.RESET);
		}
	}
	public void JoinRedTeam(Player p)
	{
		red.addPlayer(p);
		p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.RESET);
		p.sendMessage("逃走者グループに追加されました。");
	}
	public void RestoreToRedTeam(Player p)
	{
		red.addPlayer(p);
		p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.RESET);
	}
	public void JoinBlueTeam(Player p)
	{
		blue.addPlayer(p);
		p.setDisplayName(ChatColor.BLUE + p.getName() + ChatColor.RESET);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, main.getConfigHandler().getHunterSpeedLevel()));
		p.setWalkSpeed(main.getConfigHandler().getHunterSpeedLevel());
		PlayerInventory pi = p.getInventory();
		pi.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		pi.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		pi.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		p.sendMessage("鬼グループに追加されました。");
	}
	public void JoinBlackTeam(Player p)
	{
		black.addPlayer(p);
	}
	public void Quit(Player p)
	{
		p.setDisplayName(ChatColor.RESET  + p.getName() + ChatColor.RESET);
		if(isRedTeam(p))
		{
			red.removePlayer(p);
		}else if(isBlueTeam(p))
		{
			blue.removePlayer(p);
			p.removePotionEffect(PotionEffectType.SPEED);
			//p.setWalkSpeed(0.2f);
			PlayerInventory pi = p.getInventory();
			pi.setHelmet(new ItemStack(Material.AIR));
			pi.setChestplate(new ItemStack(Material.AIR));
			pi.setLeggings(new ItemStack(Material.AIR));
			pi.setBoots(new ItemStack(Material.AIR));
		}else if(isBlackTeam(p))
		{
			black.removePlayer(p);
		}
	}
	public void reset()
	{
		for(OfflinePlayer p : red.getPlayers())
		{
			red.removePlayer(p);
			ob.getScore(p).setScore(0);
		}
		for(OfflinePlayer p : blue.getPlayers())
		{
			blue.removePlayer(p);
			ob.getScore(p).setScore(0);
			if(p.getPlayer() != null)
			{
				p.getPlayer().removePotionEffect(PotionEffectType.SPEED);
				//p.getPlayer().setWalkSpeed(0.2f);
			}
		}
		for(OfflinePlayer p : black.getPlayers())
		{
			black.removePlayer(p);
			ob.getScore(p).setScore(0);
		}
	}
	public boolean isRedTeam(Player p)
	{
		return red.hasPlayer(p);
	}
	public boolean isBlueTeam(Player p)
	{
		return blue.hasPlayer(p);
	}
	public boolean isBlackTeam(Player p)
	{
		return black.hasPlayer(p);
	}
	public boolean isPlaying(Player p)
	{
		if(isRedTeam(p) || isBlueTeam(p) || isBlackTeam(p))return true;
		return false;
	}
	public Score getScore(OfflinePlayer p)
	{
		return ob.getScore(p);
	}
	public ArrayList<Player> getRedPlayersList()
	{
		ArrayList<Player> result = new ArrayList<Player>();
		for(OfflinePlayer ofp : red.getPlayers())
		{
			if(ofp.getPlayer() != null)
			{
				result.add(ofp.getPlayer());
			}
		}
		return result;
	}
	public ArrayList<Player> getBluePlayersList()
	{
		ArrayList<Player> result = new ArrayList<Player>();
		for(OfflinePlayer ofp : blue.getPlayers())
		{
			if(ofp.getPlayer() != null)
			{
				result.add(ofp.getPlayer());
			}
		}
		return result;
	}
	public ArrayList<Player> getBlackPlayersList()
	{
		ArrayList<Player> result = new ArrayList<Player>();
		for(OfflinePlayer ofp : black.getPlayers())
		{
			if(ofp.getPlayer() != null)
			{
				result.add(ofp.getPlayer());
			}
		}
		return result;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		p.setScoreboard(sb);
		if(isPlaying(p)){
			if(isRedTeam(p))
			{
				p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.RESET);
			}else if(isBlueTeam(p))
			{
				p.setDisplayName(ChatColor.BLUE + p.getName() + ChatColor.RESET);
			}else if(isBlackTeam(p))
			{
				p.setDisplayName(ChatColor.BLACK + p.getName() + ChatColor.RESET);
			}
			return;
		}
		p.setDisplayName(ChatColor.RESET + p.getName() + ChatColor.RESET);
		p.setWalkSpeed(0.2f);
		PlayerInventory pi = p.getInventory();
		pi.setHelmet(new ItemStack(Material.AIR));
		pi.setChestplate(new ItemStack(Material.AIR));
		pi.setLeggings(new ItemStack(Material.AIR));
		pi.setBoots(new ItemStack(Material.AIR));

	}
	@EventHandler(priority = EventPriority.LOW)
	public void onMove(PlayerMoveEvent e)
	{
		if(!isBlackTeam(e.getPlayer()))return;
		if(!e.getFrom().getBlock().equals(e.getTo().getBlock()))
		{
			e.setCancelled(true);
		}
	}
}
