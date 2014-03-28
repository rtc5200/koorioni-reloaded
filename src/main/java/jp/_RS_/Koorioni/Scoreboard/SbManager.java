package jp._RS_.Koorioni.Scoreboard;

import java.util.ArrayList;

import net.minecraft.server.v1_6_R2.Packet205ClientCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import jp._RS_.Koorioni.GameController;
import jp._RS_.Koorioni.Main;
import jp._RS_.Koorioni.Variables;
import jp._RS_.Koorioni.Task.KeepInventoryThread;
import jp._RS_.Koorioni.Task.TeamNumberCheckTask;

public class SbManager implements Listener {
	private Main main;
	private Scoreboard sb;
	private Team red;
	private Team blue;
	private Team black;
	private Objective ob;
	private Objective sidebar;
	private OfflinePlayer reo;
	private OfflinePlayer beo;
	private OfflinePlayer bko;
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
		sidebar = sb.registerNewObjective("KoniSide", "dummy");
		sidebar.setDisplayName("ゲーム状況");
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		reo = Bukkit.getOfflinePlayer(ChatColor.RED + "逃走者");
		sidebar.getScore(reo).setScore(1);
		sidebar.getScore(reo).setScore(0);
		beo = Bukkit.getOfflinePlayer(ChatColor.BLUE + "鬼");
		sidebar.getScore(beo).setScore(1);
		sidebar.getScore(beo).setScore(0);
		bko = Bukkit.getOfflinePlayer(ChatColor.YELLOW + "凍ってる人");
		sidebar.getScore(bko).setScore(1);
		sidebar.getScore(bko).setScore(0);
		blue.setAllowFriendlyFire(false);
		black.setAllowFriendlyFire(false);
		red.setPrefix(ChatColor.RED.toString());
		red.setSuffix(ChatColor.RESET.toString());
		blue.setPrefix(ChatColor.BLUE.toString());
		blue.setSuffix(ChatColor.RESET.toString());
		black.setPrefix(ChatColor.BLACK.toString());
		black.setSuffix(ChatColor.RESET.toString());
		red.setCanSeeFriendlyInvisibles(true);
		//Bukkit.getScheduler().runTaskTimer(main, new TeamNumberCheckTask(this), 20L, 20L);
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
		p.sendMessage(ChatColor.RED +"逃走者" + ChatColor.RESET + "グループに追加されました。");
		updateSidebar();
		p.getInventory().remove(Material.FEATHER);
		p.getInventory().remove(Material.STICK);
		ItemStack i1 = main.getConfigHandler().getSpeedItem();
		i1.setAmount(calcAmount(i1.getAmount(),main.getController().getRemainingTimePercent()));
		ItemStack i2 = main.getConfigHandler().getInvisibilityItem();
		i2.setAmount(calcAmount(i2.getAmount(),main.getController().getRemainingTimePercent()));
		p.getInventory().addItem(i1);
		p.getInventory().addItem(i2);
		p.updateInventory();
		main.getConfigHandler().getStartLocation();
		p.sendMessage("スポーン後" + ChatColor.GOLD + main.getConfigHandler().getSpawnProtectionTime() + ChatColor.RESET
				+ "秒間はスポーン保護が適用されます。");
		main.getPlayerException().addException(p);
	}
	public void RestoreToRedTeam(Player p)
	{
		red.addPlayer(p);
		p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.RESET);
		updateSidebar();
		main.getPlayerException().addException(p);
		p.sendMessage("氷解除後" + ChatColor.GOLD + main.getConfigHandler().getSpawnProtectionTime() + ChatColor.RESET
				+ "秒間はスポーン保護が適用されます。");
	}
	public void JoinBlueTeam(Player p)
	{
		blue.addPlayer(p);
		p.setDisplayName(ChatColor.BLUE + p.getName() + ChatColor.RESET);
		//p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, main.getConfigHandler().getHunterSpeedLevel()));
		p.setWalkSpeed(main.getConfigHandler().getHunterSpeedLevel());
		PlayerInventory pi = p.getInventory();
		pi.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		pi.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		pi.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		p.sendMessage(ChatColor.BLUE + "鬼" +ChatColor.RESET +  "グループに追加されました。");
		updateSidebar();
		p.getInventory().remove(Material.FEATHER);
		p.getInventory().remove(Material.STICK);
		p.updateInventory();
	}
	public void JoinBlackTeam(Player p)
	{
		black.addPlayer(p);
		updateSidebar();
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
			//p.removePotionEffect(PotionEffectType.SPEED);
			p.setWalkSpeed(0.2f);
			PlayerInventory pi = p.getInventory();
			pi.setHelmet(new ItemStack(Material.AIR));
			pi.setChestplate(new ItemStack(Material.AIR));
			pi.setLeggings(new ItemStack(Material.AIR));
			pi.setBoots(new ItemStack(Material.AIR));
		}else if(isBlackTeam(p))
		{
			black.removePlayer(p);
		}
		updateSidebar();
		p.getInventory().remove(Material.FEATHER);
		p.getInventory().remove(Material.STICK);
		p.updateInventory();
	}
	public void reset()
	{
		for(OfflinePlayer p : red.getPlayers())
		{
			if(p.getPlayer() != null)
			{
				Quit(p.getPlayer());
			}else{
				red.removePlayer(p);
			}
			ob.getScore(p).setScore(0);
		}
		for(OfflinePlayer p : blue.getPlayers())
		{
			if(p.getPlayer() != null)
			{
				Quit(p.getPlayer());
			}else{
				blue.removePlayer(p);
			}
			ob.getScore(p).setScore(0);
		}
		for(OfflinePlayer p : black.getPlayers())
		{
			if(p.getPlayer() != null)
			{
				Quit(p.getPlayer());
				p.getPlayer().setDisplayName(ChatColor.RESET + p.getName() + ChatColor.RESET);
			}else{
				black.removePlayer(p);
			}
			ob.getScore(p).setScore(0);
		}
		updateSidebar();
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
	public int getRedSize()
	{
		return red.getSize();
	}
	public int getBlueSize()
	{
		return blue.getSize();
	}
	public int getBlackSize()
	{
		return black.getSize();
	}
	public void updateSidebar()
	{
		sidebar.getScore(reo).setScore(red.getSize());
		sidebar.getScore(beo).setScore(blue.getSize());
		sidebar.getScore(bko).setScore(black.getSize());
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		p.setScoreboard(sb);
		PlayerInventory pi = p.getInventory();
		if(!pi.contains(Variables.getAutoSneakToggleItem()))
		{
			pi.addItem(Variables.getAutoSneakToggleItem());
		}
		if(isRedTeam(p))
		{
			p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.RESET);
			p.setWalkSpeed(0.2f);
			pi.setHelmet(new ItemStack(Material.AIR));
			pi.setChestplate(new ItemStack(Material.AIR));
			pi.setLeggings(new ItemStack(Material.AIR));
			pi.setBoots(new ItemStack(Material.AIR));
			return;
		}else if(isBlueTeam(p))
		{
			p.setDisplayName(ChatColor.BLUE + p.getName() + ChatColor.RESET);
			pi.remove(Material.FEATHER);
			pi.remove(Material.STICK);
			p.updateInventory();
			return;
		}else if(isBlackTeam(p))
		{
			p.setDisplayName(ChatColor.BLACK + p.getName() + ChatColor.RESET);
			p.setWalkSpeed(0.2f);
			pi.setHelmet(new ItemStack(Material.AIR));
			pi.setChestplate(new ItemStack(Material.AIR));
			pi.setLeggings(new ItemStack(Material.AIR));
			pi.setBoots(new ItemStack(Material.AIR));
			return;
		}
		p.setDisplayName(ChatColor.RESET + p.getName() + ChatColor.RESET);
		p.setWalkSpeed(0.2f);
		pi.setHelmet(new ItemStack(Material.AIR));
		pi.setChestplate(new ItemStack(Material.AIR));
		pi.setLeggings(new ItemStack(Material.AIR));
		pi.setBoots(new ItemStack(Material.AIR));
		p.setLevel(0);
		p.setExp(0);
		pi.remove(Material.FEATHER);
		pi.remove(Material.STICK);
		p.updateInventory();
		p.setExp(0);
		p.setLevel(0);
		p.teleport(p.getLocation().getWorld().getSpawnLocation());
	}

	private int calcAmount(int amount,double percent)
	{
		return (int)(percent * amount);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onMove(PlayerMoveEvent e)
	{
		if(!isBlackTeam(e.getPlayer()))return;
		Location locf = e.getFrom();
		Location loct = e.getTo();
		if(locf.getX() == loct.getX() && locf.getZ() == loct.getZ())
		{
			return;
		}
		e.getPlayer().teleport(locf);
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		PlayerInventory pi = e.getPlayer().getInventory();
		if(pi.contains(Variables.getAutoSneakToggleItem()))
		{
			pi.remove(Variables.getAutoSneakToggleItem());
		}
	}
	@EventHandler
	public void onFLC(FoodLevelChangeEvent e)
	{
		e.setCancelled(true);;
	}
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		if(p != null)
		{
			if(isPlaying(p))
			{
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void Respawn(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		Location loc = p.getLocation();
		ItemStack[] b = p.getInventory().getArmorContents();
		ItemStack[] a = p.getInventory().getContents();
		e.getDrops().clear();
		e.setKeepLevel(true);
		e.setDroppedExp(0);
		Packet205ClientCommand packet = new Packet205ClientCommand();
		packet.a = 1;
		((CraftPlayer)p).getHandle().playerConnection.a(packet);
		Bukkit.getScheduler().scheduleAsyncDelayedTask(main, new KeepInventoryThread(p,a,b));
		p.teleport(loc);
	}
	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent e)
	{
		if(isPlaying(e.getPlayer()))e.setCancelled(true);
	}
}
