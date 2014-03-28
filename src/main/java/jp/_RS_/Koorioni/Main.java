package jp._RS_.Koorioni;

import java.util.logging.Logger;

import jp._RS_.Koorioni.Command.KCommandExecutor;
import jp._RS_.Koorioni.Config.ConfigHandler;
import jp._RS_.Koorioni.Events.ChatEvent;
import jp._RS_.Koorioni.Events.DamageEvent;
import jp._RS_.Koorioni.Events.ItemEvent;
import jp._RS_.Koorioni.Events.KoorioniEvents;
import jp._RS_.Koorioni.Events.PlayerExceptionOfEvent;
import jp._RS_.Koorioni.Scoreboard.SbManager;
import jp._RS_.Koorioni.Task.SneakingTask;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	private ConfigHandler config;
	private Logger log;
	private SbManager manager;
	private GameController c;
	private PlayerFreezer freezer;
	private KoorioniEvents ke;
	private SneakingTask st;
	private PlayerExceptionOfEvent pe;
	@Override
	public void onEnable()
	{
		log = this.getLogger();
		log.info("氷鬼システムのロードを開始....");
		log.info("コンフィグロード開始....");
		config = ConfigHandler.load(this.getDataFolder());
		log.info("コンフィグロード完了.");
		log.info("チームシステムロード開始....");
		manager = new SbManager(this);
		getServer().getPluginManager().registerEvents(manager, this);
		log.info("チームシステムロード完了.");
		log.info("ゲームコントローラーロード開始....");
		c = new GameController(this);
		log.info("ゲームコントローラーロード完了.");
		log.info("イベント登録開始....");
		ke = new KoorioniEvents(this);
		freezer = new PlayerFreezer(this);
		pe = new PlayerExceptionOfEvent(this);
		getServer().getPluginManager().registerEvents(new DamageEvent(this), this);
		getServer().getPluginManager().registerEvents(ke, this);
		getServer().getPluginManager().registerEvents(new ItemEvent(this),this);
		getServer().getPluginManager().registerEvents(freezer,this);
		getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
		getServer().getPluginManager().registerEvents(pe, this);
		log.info("イベント登録完了.");
		log.info("タスクのスケジュール開始....");
		st  = new SneakingTask(this);
		getServer().getScheduler().runTaskTimer(this, st, 0L, 1L);
		log.info("タスクのスケジュール完了.");
		log.info("コマンドの登録開始....");
		this.getCommand("k").setExecutor(new KCommandExecutor(this));
		this.getCommand("s").setExecutor(st);
		log.info("コマンドの登録完了.");
		log.info("氷鬼システムの全ロード作業が完了.");
	}
	@Override
	public void onDisable()
	{
		log.info("氷鬼システムのアンロードを開始....");
		log.fine("氷鬼のアンロード完了.");
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
	public SneakingTask getAutoSneak()
	{
		return st;
	}
	public PlayerExceptionOfEvent getPlayerException()
	{
		return pe;
	}

}
