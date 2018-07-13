package xyz.yooniks.proxy.proxy;

import java.io.File;
import java.util.concurrent.Executors;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.JavaProxy;
import xyz.yooniks.proxy.command.console.StopCommand;
import xyz.yooniks.proxy.command.game.HelpCommand;
import xyz.yooniks.proxy.entity.Location;
import xyz.yooniks.proxy.entity.player.PlayerFactory;
import xyz.yooniks.proxy.exploit.ExploitMapper;
import xyz.yooniks.proxy.impl.player.PlayerFactoryImpl;
import xyz.yooniks.proxy.impl.user.ProxyUserManagerImpl;
import xyz.yooniks.proxy.json.JSONManager;
import xyz.yooniks.proxy.server.ServerHandler;
import xyz.yooniks.proxy.user.ProxyUserManager;

public class SuperProxy extends JavaProxy {

  public final static Location SPAWN_LOCATION = new Location(0, -1337, 0);
  private static SuperProxy instance;
  private final Server server;
  private final JSONManager jsonManager;
  private final ProxyUserManager userManager;
  private final PlayerFactory playerFactory;
  private final ExploitMapper exploitMapper;

  public SuperProxy() {
    super(new ProxyDescription("SuperProxy", "0.X-BETA", "yooniks"));
    instance = this;

    this.userManager = new ProxyUserManagerImpl();
    this.playerFactory = new PlayerFactoryImpl();
    this.exploitMapper = new ExploitMapper(Executors.newFixedThreadPool(2));
    this.jsonManager = new JSONManager(this, new File(this.getDataFolder(), "config.json"));

    this.server = new Server(this.jsonManager.getConfig().host, this.jsonManager.getConfig().port,
        MinecraftProtocol.class,
        new TcpSessionFactory());
  }

  public static SuperProxy getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    this.jsonManager.invoke();

    this.server
        .setGlobalFlag("login-handler", new ServerHandler(this.userManager, this.playerFactory));

    this.getCommandMapper().registerConsoleCommands(
        new StopCommand("stop", "wylacz", "restart")
    );
    this.getCommandMapper().registerGameCommands(
        new HelpCommand("help", "pomoc")
    );

    this.server.bind();
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

}
