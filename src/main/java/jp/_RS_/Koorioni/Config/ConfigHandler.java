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
	private int ItemAmount_SPEED;
	private int ItemAmount_INVISIBILITY;
	private Location OniSpawnLocation;
	private Long OniWaitTime;
	private Long SpawnProtectionTime;
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
		config.addDefault("SpawnProtectionTime", 10);
		config.addDefault("Oni.SpeedLevel", 0.6f);
		config.addDefault("Oni.SpawnLocation", "0,0,0");
		config.addDefault("Oni.WaitTime",30L);
		config.addDefault("Item.Speed.Level", 2);
		config.addDefault("Item.Speed.Duration", 1);
		config.addDefault("Item.Speed.Amount", 10);
		config.addDefault("Item.Invisibility.Level", 2);
		config.addDefault("Item.Invisibility.Duration", 1);
		config.addDefault("Item.Invisibility.Amount", 10);
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
		OniSpawnLocation = readLocation(config.getString("Oni.SpawnLocation"));
		OniWaitTime = config.getLong("Oni.WaitTime");
		SpawnProtectionTime = (long) config.getInt("SpawnProtectionTime");
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
		if(ivl < 0 || ivd <= 0)
		{
			ItemEffect_INVISIBILITY = null;
		}else{
			ItemEffect_INVISIBILITY = new PotionEffect(PotionEffectType.INVISIBILITY,ivd,ivl);
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
		return OniSpawnLocation;
	}
	public Long getOniWaitTime()
	{
		return OniWaitTime;
	}
	public Long getSpawnProtectionTime()
	{
		return SpawnProtectionTime;
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
