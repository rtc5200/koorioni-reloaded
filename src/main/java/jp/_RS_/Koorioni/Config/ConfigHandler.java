package jp._RS_.Koorioni.Config;

import java.io.File;
import java.io.IOException;

import jp._RS_.Koorioni.Variables;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ConfigHandler {
	private YamlConfiguration config;
	private File file;
	private World world;
	private Location StartLoc;
	private Long GameTime;
	private float hspeedlev;
	private PotionEffect ItemEffect_SPEED;
	private PotionEffect ItemEffect_INVISIBILITY;
	private PotionEffect ItemEffect_INV_SLOW;
	private int ItemAmount_SPEED;
	private int ItemAmount_INVISIBILITY;
	private Location Oni_SpawnLocation;
	private Long Oni_WaitTime;
	private Long Time_SpawnProtection;
	private Long Time_OniStiffonTouch;
	private Long  UnableRescueTimeAfterTouch;
	public ConfigHandler(File file)
	{
		this.config = YamlConfiguration.loadConfiguration(file);
		this.file = file;
		FileCheck();
		intialize();
		load();
	}
	private void intialize()
	{
		config.addDefault("WorldName", "world");
		config.addDefault("StartLocation", "0,0,0");
		config.addDefault("GameTime", 60L);
		config.addDefault("Oni.SpeedLevel", 0.6f);
		config.addDefault("Oni.SpawnLocation", "0,0,0");
		config.addDefault("Oni.WaitTime",30L);
		config.addDefault("Oni.StandByTimeOnTouch",5L);
		config.addDefault("Item.Speed.Level", 2);
		config.addDefault("Item.Speed.Duration", 1);
		config.addDefault("Item.Speed.Amount", 10);
		config.addDefault("Item.Invisibility.Level", 2);
		config.addDefault("Item.Invisibility.Duration", 1);
		config.addDefault("Item.Invisibility.Amount", 10);
		config.addDefault("Item.Invisibility.Slow.Level",2);
		config.addDefault("WaitTime.onSpawn", 10L);
		config.addDefault("WaitTime.onRescue", 10L);

		config.options().copyDefaults(true);
		config.options().copyHeader(true);
		config.options().header(Variables.getConfigHeader());
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void load()
	{
		world = Bukkit.getWorld(config.getString("WorldName"));
		StartLoc = readLocation(config.getString("StartLocation"));
		GameTime = config.getLong("GameTime");
		hspeedlev = Float.parseFloat(config.getString("Oni.SpeedLevel"));
		Oni_SpawnLocation = readLocation(config.getString("Oni.SpawnLocation"));
		Oni_WaitTime = config.getLong("Oni.WaitTime");
		Time_OniStiffonTouch = config.getLong("Oni.StandByTimeOnTouch");
		Time_SpawnProtection = config.getLong("WaitTime.onSpawn");
		UnableRescueTimeAfterTouch = config.getLong("WaitTime.onRescue");
		int spl = config.getInt("Item.Speed.Level") - 1;
		int spd = config.getInt("Item.Speed.Duration")*20;
		if(spl < 0 || spd <= 0)
		{
			ItemEffect_SPEED = null;
		}else{
			ItemEffect_SPEED = new PotionEffect(PotionEffectType.SPEED,spd,spl);
		}
		int ivl = config.getInt("Item.Invisibility.Level") - 1;
		int ivd = config.getInt("Item.Invisibility.Duration")*20;
		int isl = config.getInt("Item.Invisibility.Slow.Level") - 1;
		if(ivl < 0 || ivd <= 0)
		{
			ItemEffect_INVISIBILITY = null;
		}else{
			ItemEffect_INVISIBILITY = new PotionEffect(PotionEffectType.INVISIBILITY,ivd,ivl);
		}
		if(isl < 0)
		{
			ItemEffect_INV_SLOW = null;
		}else{
			ItemEffect_INV_SLOW = new PotionEffect(PotionEffectType.SLOW,ivd,isl);
		}
		ItemAmount_SPEED = config.getInt("Item.Speed.Amount");
		ItemAmount_INVISIBILITY = config.getInt("Item.Invisibility.Amount");
	}
	private Location readLocation(String s)
	{
		String[] temp = s.split(",");
		return new Location(world,Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
	}

	public static ConfigHandler load(File DataFolder)
	{
		if(!DataFolder.exists())DataFolder.mkdirs();
		File cfile = new File(DataFolder,"config.yml");
		return new ConfigHandler(cfile);
	}
	public World getWorld()
	{
		return world;
	}
	public Location getStartLocation()
	{
		return StartLoc;
	}
	public Long getGameTime()
	{
		return GameTime;
	}
	public void saveStartLocation(Location loc)
	{
		String l = (int)loc.getX() + "," + (int)loc.getY() + ","+ (int)loc.getZ();
		config.set("StartLocation", l);
	}
	public float getHunterSpeedLevel()
	{
		return hspeedlev;
	}
	public PotionEffect getItemEffect_SPEED()
	{
		return ItemEffect_SPEED;
	}
	public PotionEffect getItemEffect_INVISBILITY()
	{
		return ItemEffect_INVISIBILITY;
	}
	public PotionEffect getItemEffect_INV_SLOW()
	{
		return ItemEffect_INV_SLOW;
	}
	public ItemStack getSpeedItem()
	{
		if(ItemAmount_SPEED == 0)
		{
			return new ItemStack(Material.AIR);
		}
		return new ItemStack(Material.FEATHER,ItemAmount_SPEED);
	}
	public ItemStack getInvisibilityItem()
	{
		if(ItemAmount_INVISIBILITY == 0)
		{
			return new ItemStack(Material.AIR);
		}
		return new ItemStack(Material.STICK,ItemAmount_INVISIBILITY);
	}
	public Location getOniSpawnLocation()
	{
		return Oni_SpawnLocation;
	}
	public Long getOniWaitTime()
	{
		return Oni_WaitTime;
	}
	public Long getTime_SpawnProtection()
	{
		return Time_SpawnProtection;
	}
	public Long getTime_OniStiffonTouch()
	{
		return Time_OniStiffonTouch;
	}
	public Long getUnableRescueTimeAfterTouch()
	{
		return UnableRescueTimeAfterTouch;
	}
	public void reload()
	{
		FileCheck();
		config = YamlConfiguration.loadConfiguration(file);
		intialize();
		load();
	}
	private void FileCheck()
	{
		if(!file.getParentFile().exists())file.getParentFile().mkdirs();
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
