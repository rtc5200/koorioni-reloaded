package jp._RS_.Koorioni.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler {
	private YamlConfiguration config;
	private File file;
	private World world;
	private Location StartLoc;
	private Long GameTime;
	private int speedlev;

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
		config.addDefault("HunterSpeedLevel", 2);
		config.options().copyDefaults(true);
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
		speedlev =config.getInt("HunterSpeedLevel");
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
	public int getHunterSpeedLevel()
	{
		return speedlev;
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
