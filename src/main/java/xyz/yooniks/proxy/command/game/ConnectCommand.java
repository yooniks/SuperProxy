package xyz.yooniks.proxy.command.game;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.command.basic.CommandExecutor;
import xyz.yooniks.proxy.command.basic.CommandInfo;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.listener.packetstransmitter.ClientPacketsTransmitter;
import xyz.yooniks.proxy.listener.packetstransmitter.ConnectPacketsTransmitter;
import xyz.yooniks.proxy.listener.packetstransmitter.handler.ServerPinger;
import xyz.yooniks.proxy.user.ProxyUser;

@CommandInfo(
    name = "connect",
    aliases = { "join", "polacz" },
    description = "Laczy twojego klienta z danym serwerem"
)
public class ConnectCommand implements CommandExecutor {

  @Override
  public void execute(ProxyUser executor, String[] args) {
    if (args.length < 1) {
      executor.asPlayer().ifPresent(this::printUsage);
      return;
    }
    if (!executor.asPlayer().isPresent()) {
      return;
    }
    final Player player = executor.asPlayer().get();

    final String host = args[0];
    int port = 25565;
    if (args.length > 1) {
      try {
        port = Integer.parseInt(args[1]);
      } catch (NumberFormatException exception) {
        player.sendMessage("&cArgument &6" + args[1] + "&c nie jest liczba!"
            + " Uzywam domyslnego portu, &625565");
      }
    }

    final Proxy proxy;
    if (args.length > 3) {
      final String[] proxyArgs = args[3].split(":");
      proxy = new Proxy(Type.SOCKS, new InetSocketAddress(proxyArgs[0],
          Integer.parseInt(proxyArgs[1])));
    } else {
      proxy = Proxy.NO_PROXY;
    }

    new ServerPinger(host, port, proxy).ping(player);

    final Client client = new Client(host, port,
        new MinecraftProtocol(args.length > 2 ? args[2] : executor.getName()),
        new TcpSessionFactory(proxy));

    client.getSession().setConnectTimeout(0);
    client.getSession().addListener(new ConnectPacketsTransmitter(player, player.getSession(), executor));

    player.getSession().addListener(new ClientPacketsTransmitter(client.getSession()));

    client.getSession().connect();

    player.sendMessage("&7Trwa laczenie z serwerem: &6" + host + ":" + port + "&7..");
  }

  private void printUsage(Player player) {
    player.sendMessage("&7Poprawne uzycie: &6!connect [host] "
        + "<port, domyslny 25565> <nick> <ip proxy socks>");
  }

}
