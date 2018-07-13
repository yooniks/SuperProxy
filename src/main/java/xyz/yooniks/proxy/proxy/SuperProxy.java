package xyz.yooniks.proxy.proxy;

import java.io.File;
import java.util.concurrent.Executors;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.JavaProxy;
import xyz.yooniks.proxy.command.console.StopCommand;
import xyz.yooniks.proxy.command.game.ConnectCommand;
import xyz.yooniks.proxy.command.game.HelpCommand;
import xyz.yooniks.proxy.entity.Location;
import xyz.yooniks.proxy.entity.player.PlayerFactory;
import xyz.yooniks.proxy.exploit.ExploitMapper;
import xyz.yooniks.proxy.impl.player.PlayerFactoryImpl;
import xyz.yooniks.proxy.impl.user.ProxyUserManagerImpl;
import xyz.yooniks.proxy.json.JSONManager;
import xyz.yooniks.proxy.server.ServerInfoHandler;
import xyz.yooniks.proxy.server.ServerJoinHandler;
import xyz.yooniks.proxy.server.ServerListener;
import xyz.yooniks.proxy.tablist.TablistManager;
import xyz.yooniks.proxy.user.ProxyUserManager;

public class SuperProxy extends JavaProxy {

  public final static Location SPAWN_LOCATION = new Location(0, -1337, 0);
  private static SuperProxy instance;
  private final Server server;
  private final JSONManager jsonManager;
  private final ProxyUserManager userManager;
  private final PlayerFactory playerFactory;
  private final ExploitMapper exploitMapper;
  private final TablistManager tablistManager;

  public SuperProxy() {
    super(new ProxyDescription("SuperProxy", "0.X-BETA", "yooniks"));
    instance = this;

    this.userManager = new ProxyUserManagerImpl();
    this.playerFactory = new PlayerFactoryImpl();
    this.exploitMapper = new ExploitMapper(Executors.newFixedThreadPool(2));
    this.jsonManager = new JSONManager(this, new File(this.getDataFolder(), "config.json"));
    this.jsonManager.create();
    this.jsonManager.invoke();
    this.tablistManager = new TablistManager(this.jsonManager.getConfig());

    this.server = new Server(this.jsonManager.getConfig().host, this.jsonManager.getConfig().port,
        MinecraftProtocol.class,
        new TcpSessionFactory());
  }

  public static SuperProxy getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    this.server.setGlobalFlag("login-handler",
        new ServerJoinHandler(this.userManager, this.playerFactory, this.tablistManager,
            this.jsonManager.getConfig()));
    this.server.setGlobalFlag("info-builder", new ServerInfoHandler(this.jsonManager.getConfig()));
    this.server.setGlobalFlag("compression-threshold", 100);
    this.server.setGlobalFlag("verify-users", false);
    this.server.addListener(new ServerListener());

    this.registerCommands();

    this.server.bind();
  }

  private void registerCommands() {
    this.getCommandMapper().registerConsoleCommands(
        new StopCommand("stop", "wylacz", "restart")
    );
    this.getCommandMapper().registerGameCommands(
        new HelpCommand("!help - wyswietla liste komend", "help", "pomoc"),
        new ConnectCommand("!connect - laczy z danym serwerem", "connect", "polacz", "join")
    );
  }

  @Override
  public Server getServer() {
    return server;
  }

  public ProxyUserManager getUserManager() {
    return userManager;
  }

  public PlayerFactory getPlayerFactory() {
    return playerFactory;
  }

  public ExploitMapper getExploitMapper() {
    return exploitMapper;
  }

  public JSONManager getJsonManager() {
    return jsonManager;
  }

  public TablistManager getTablistManager() {
    return tablistManager;
  }

}
