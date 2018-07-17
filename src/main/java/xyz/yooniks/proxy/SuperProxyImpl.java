package xyz.yooniks.proxy;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientSwingArmPacket;
import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.command.basic.CommandManager;
import xyz.yooniks.proxy.command.console.StopCommand;
import xyz.yooniks.proxy.command.game.BotCommand;
import xyz.yooniks.proxy.command.game.ConnectBotCommand;
import xyz.yooniks.proxy.command.game.ConnectCommand;
import xyz.yooniks.proxy.command.game.ExploitCommand;
import xyz.yooniks.proxy.command.game.HelpCommand;
import xyz.yooniks.proxy.command.game.LeaveCommand;
import xyz.yooniks.proxy.command.game.OptionsCommand;
import xyz.yooniks.proxy.entity.Location;
import xyz.yooniks.proxy.entity.bot.BotManager;
import xyz.yooniks.proxy.entity.player.PlayerFactory;
import xyz.yooniks.proxy.exploit.ExploitBuilder;
import xyz.yooniks.proxy.exploit.ExploitManager;
import xyz.yooniks.proxy.helper.PacketHelper;
import xyz.yooniks.proxy.impl.CommandManagerImpl;
import xyz.yooniks.proxy.impl.ExploitManagerImpl;
import xyz.yooniks.proxy.impl.bot.BotManagerImpl;
import xyz.yooniks.proxy.impl.player.PlayerFactoryImpl;
import xyz.yooniks.proxy.impl.user.ProxyUserManagerImpl;
import xyz.yooniks.proxy.json.JSONManager;
import xyz.yooniks.proxy.listener.ServerInfoHandler;
import xyz.yooniks.proxy.listener.ServerJoinHandler;
import xyz.yooniks.proxy.listener.ServerListener;
import xyz.yooniks.proxy.proxy.ProxyDescription;
import xyz.yooniks.proxy.tablist.TablistManager;
import xyz.yooniks.proxy.user.ProxyUserManager;

public class SuperProxyImpl implements SuperProxy {

  private final Logger logger = Logger.getLogger("SuperProxy");

  private final ProxyDescription proxyDescription;
  private final File dataFolder;
  private final Server server;
  private final JSONManager jsonManager;
  private final ProxyUserManager userManager;
  private final PlayerFactory playerFactory;
  private final CommandManager commandManager;
  private final ExploitManager exploitManager;
  private final TablistManager tablistManager;
  private final BotManager botManager;

  public final static Location SPAWN_LOCATION = new Location(0, -1337, 0);
  private static SuperProxyImpl instance;

  public SuperProxyImpl() {
    SuperProxyImpl.instance = this;
    this.proxyDescription = new ProxyDescription("SuperProxy", "0.X-BETA", "yooniks");

    this.dataFolder = new File(this.proxyDescription.getName());
    if (!this.dataFolder.exists()) {
      boolean created = this.dataFolder.mkdirs();
      if (created) {
        this.getLogger().info("Stworzono pomyslnie folder SuperProxy!");
      }
    }

    this.commandManager = new CommandManagerImpl();
    this.playerFactory = new PlayerFactoryImpl();
    this.userManager = new ProxyUserManagerImpl();
    this.exploitManager = new ExploitManagerImpl(Executors.newFixedThreadPool(2));
    this.jsonManager = new JSONManager(this, new File(this.getDataFolder(), "config.json"));
    this.jsonManager.create();
    this.jsonManager.invoke();
    this.tablistManager = new TablistManager(this.jsonManager.getConfig());
    this.botManager = new BotManagerImpl(Executors.newFixedThreadPool(2));

    this.server = new Server(this.jsonManager.getConfig().host, this.jsonManager.getConfig().port,
        MinecraftProtocol.class,
        new TcpSessionFactory());
  }

  public static SuperProxyImpl getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    this.tablistManager.startTask(this.userManager);

    this.server.setGlobalFlag("login-handler", new ServerJoinHandler(this));
    this.server.setGlobalFlag("info-builder", new ServerInfoHandler(this.jsonManager.getConfig()));
    this.server.setGlobalFlag("compression-threshold", 100);
    this.server.setGlobalFlag("verify-users", false);
    this.server.addListener(new ServerListener());

    this.registerCommands();
    this.registerExploits();

    this.server.bind();
  }

  private void registerCommands() {
    this.getCommandManager().registerCommands(
        //console commands
        new StopCommand(this),
        new xyz.yooniks.proxy.command.console.HelpCommand(this.commandManager),

        //client commands
        new ConnectCommand(),
        new HelpCommand(this.commandManager),
        new LeaveCommand(),
        new ExploitCommand(this.exploitManager),
        new OptionsCommand(),
        new BotCommand(this.botManager),
        new ConnectBotCommand(this.botManager)
    );
  }

  private void registerExploits() {
    this.getExploitManager().addExploits(
        new ExploitBuilder(this).setName("nbt").setPacket(PacketHelper.getNBTWindowPacket()).build(),
        new ExploitBuilder(this).setName("armanimation").setPacket(new ClientSwingArmPacket()).build()
    );
  }

  @Override
  public Server getServer() {
    return server;
  }

  @Override
  public Logger getLogger() {
    return logger;
  }

  @Override
  public File getDataFolder() {
    return dataFolder;
  }

  @Override
  public BotManager getBotManager() {
    return botManager;
  }

  @Override
  public TablistManager getTablistManager() {
    return tablistManager;
  }

  @Override
  public JSONManager getJsonManager() {
    return jsonManager;
  }

  @Override
  public ProxyDescription getDescription() {
    return proxyDescription;
  }

  @Override
  public CommandManager getCommandManager() {
    return commandManager;
  }

  @Override
  public ExploitManager getExploitManager() {
    return exploitManager;
  }

  @Override
  public PlayerFactory getPlayerFactory() {
    return playerFactory;
  }

  @Override
  public ProxyUserManager getUserManager() {
    return userManager;
  }

}
