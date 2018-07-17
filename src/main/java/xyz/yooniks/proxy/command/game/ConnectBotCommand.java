package xyz.yooniks.proxy.command.game;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import org.apache.commons.lang.RandomStringUtils;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.command.basic.CommandExecutor;
import xyz.yooniks.proxy.command.basic.CommandInfo;
import xyz.yooniks.proxy.entity.bot.Bot;
import xyz.yooniks.proxy.entity.bot.BotBuilder;
import xyz.yooniks.proxy.entity.bot.BotManager;
import xyz.yooniks.proxy.listener.BotSessionListener;
import xyz.yooniks.proxy.user.ProxyUser;
import xyz.yooniks.proxy.user.ProxyUserOptions;

@CommandInfo(
    name = "connectbot",
    aliases = { "joinbot", "polaczboty", "connectbots", "joinbots" },
    description = "Laczy boty z danym serwerem"
)
public class ConnectBotCommand implements CommandExecutor {

  private final BotManager botManager;

  public ConnectBotCommand(BotManager botManager) {
    this.botManager = botManager;
  }

  @Override
  public void execute(ProxyUser executor, String[] args) {
    if (args.length < 1) {
      executor.sendMessage("&cPoprawne uzycie: &6!connectbot [host] <port> <liczba botow> <ip proxy/random>");
      return;
    }
    final String host = args[0];
    final int port;
    if (args.length > 1) {
      try {
        port = Integer.parseInt(args[1]);
      } catch (NumberFormatException exception) {
        executor.sendMessage("&cArgument (port) &6" + args[1] + "&c nie jest liczba!");
        return;
      }
    }
    else {
      port = 25565;
    }

    final int amount;
    if (args.length > 2) {
      try {
        amount = Integer.parseInt(args[2]);
      }
      catch (NumberFormatException exception) {
        executor.sendMessage("&cArgument &6" + args[2] + " &cnie jest liczba!");
        return;
      }
    }
    else {
      amount = 10;
    }

    final Proxy proxy;
    if (args.length > 3) {
      if (args[3].contains(":")) {
        //specific proxy socks
        final String[] proxyArgs = args[3].split(":");
        proxy = new Proxy(Type.SOCKS, new InetSocketAddress(proxyArgs[0],
            Integer.parseInt(proxyArgs[1])));
      }
      else {
        //random proxy socks
        proxy = Proxy.NO_PROXY;
      }
    } else {
      //default proxy, listener ip
      proxy = Proxy.NO_PROXY;
    }
    final Runnable runnable = () -> {
      executor.sendMessage("&6" + amount + "&7 botow laczy do serwera: &6" + host + ":" + port);
      final ProxyUserOptions options = executor.getOptions();
      final String prefix = options.getBotsPrefix().getValue();
      for (int i = 0; i < amount; i ++) {
        final String name = prefix + RandomStringUtils.randomAlphanumeric(5);
        final Bot bot = new BotBuilder()
            .setName(name)
            .setOwner(executor)
            .build();
        final Client client = new Client(host, port, new MinecraftProtocol(name),
            new TcpSessionFactory(proxy));
        client.getSession().setConnectTimeout(0);
        client.getSession().addListener(new BotSessionListener(executor, bot, this.botManager, proxy));
        client.getSession().connect();
      }
    };
    this.botManager.addToQueue(runnable);
  }

}
