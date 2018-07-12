package xyz.yooniks.proxy;

import java.io.File;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.command.console.StopCommand;
import xyz.yooniks.proxy.command.game.HelpCommand;
import xyz.yooniks.proxy.impl.ProxyUserManagerImpl;
import xyz.yooniks.proxy.json.JSONManager;
import xyz.yooniks.proxy.server.ServerHandler;

public class SuperProxy extends JavaProxy {

  private static SuperProxy instance;
  private final Server server;
  private final JSONManager jsonManager;
  private final File configFile = new File(this.getDataFolder(), "config.json");

  public SuperProxy() {
    //TODO: better looking
    super("SuperProxy", "0.1-BETA", new ProxyUserManagerImpl(), "yooniks");
    instance = this;

    this.jsonManager = new JSONManager(this, this.configFile);

    this.server = new Server(this.jsonManager.getConfig().host, this.jsonManager.getConfig().port,
        MinecraftProtocol.class,
        new TcpSessionFactory());
    this.server.bind();
  }

  @Override
  public void onEnable() {
    this.server.setGlobalFlag("login-handler", new ServerHandler(this.userManager));

    this.commandMapper.registerConsoleCommands(
        new StopCommand("stop", "wylacz", "restart")
    );
    this.commandMapper.registerGameCommands(
        new HelpCommand("help", "pomoc")
    );
  }

  public static SuperProxy getInstance() {
    return instance;
  }

  @Override
  public Server getServer() {
    return server;
  }


}
