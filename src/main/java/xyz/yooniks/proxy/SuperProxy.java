package xyz.yooniks.proxy;

import java.io.File;
import java.util.Scanner;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.command.console.StopCommand;
import xyz.yooniks.proxy.command.game.HelpCommand;
import xyz.yooniks.proxy.exploit.ExploitMapper;
import xyz.yooniks.proxy.json.JSONManager;
import xyz.yooniks.proxy.server.ServerHandler;

public class SuperProxy extends JavaProxy {

  private static SuperProxy instance;
  private final Server server;
  private final JSONManager jsonManager;
  private final File configFile = new File(this.getDataFolder(), "config.json");

  public SuperProxy() {
    super("SuperProxy", "0.1-BETA", "yooniks");
    instance = this;

    this.jsonManager = new JSONManager(this, this.configFile);

    this.server = new Server(this.jsonManager.getConfig().host, this.jsonManager.getConfig().port,
        MinecraftProtocol.class,
        new TcpSessionFactory());
    this.server.bind();
  }

  public static SuperProxy getInstance() {
    return instance;
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

    final Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLine()) {
      final String[] args = scanner.nextLine().split(" ");
      this.commandMapper.consoleCommandByName(args[0])
          .ifPresentOrElse((consoleCommand -> consoleCommand.onExecute(args)),
              () -> System.out.println("Podana komenda nie istnieje! Lista komend: \"help\""));
    }
  }

  public Server getServer() {
    return server;
  }

  public ExploitMapper getExploitMapper() {
    return exploitMapper;
  }

}
