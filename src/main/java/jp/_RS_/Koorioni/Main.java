package jp._RS_.Koorioni;

import java.util.logging.Logger;

import jp._RS_.Koorioni.Command.KCommandExecutor;
import jp._RS_.Koorioni.Config.ConfigHandler;
import jp._RS_.Koorioni.Events.DamageEvent;
import jp._RS_.Koorioni.Events.KoorioniEvents;
import jp._RS_.Koorioni.Scoreboard.SbManager;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	private ConfigHandler config;
	private Logger log;
	private SbManager manager;
	private GameController c;
	private PlayerFreezer freezer;
	private KoorioniEvents ke;
	@Override
	public void onEnable()
	{
		log = this.getLogger();
		config = ConfigHandler.load(this.getDataFolder());
		manager = new SbManager(this);
		ke = new KoorioniEvents(this);
		this.getCommand("k").setExecutor(new KCommandExecutor(this));
		getServer().getPluginManager().registerEvents(manager, this);
		getServer().getPluginManager().registerEvents(new DamageEvent(this), this);
		getServer().getPluginManager().registerEvents(ke, this);
		c = new GameController(this);
		freezer = new PlayerFreezer(this);
		getServer().getPluginManager().registerEvents(freezer,this);

	}
	@Override
	public void onDisable()
	{

	}
	public SbManager getSbManager()
	{
		return manager;
	}
	public ConfigHandler getConfigHandler()
	{
		return config;
	}
	public GameController getController()
	{
		return c;
	}
	public PlayerFreezer getFreezer()
	{
		return freezer;
	}

}
