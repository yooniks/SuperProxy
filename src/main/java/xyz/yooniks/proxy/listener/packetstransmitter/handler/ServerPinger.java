package xyz.yooniks.proxy.listener.packetstransmitter.handler;

import java.net.Proxy;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.SubProtocol;
import org.spacehq.mc.protocol.data.status.handler.ServerInfoHandler;
import org.spacehq.mc.protocol.data.status.handler.ServerPingTimeHandler;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.entity.player.Player;

public class ServerPinger {

  private final String host;
  private final int port;
  private final Proxy proxy;

  public ServerPinger(String host, int port, Proxy proxy) {
    this.host = host;
    this.port = port;
    this.proxy = proxy;
  }

  public void ping(Player player) {
    final MinecraftProtocol protocol = new MinecraftProtocol(SubProtocol.STATUS);
    final Client client = new Client(this.host, this.port, protocol, new TcpSessionFactory(this.proxy));
    client.getSession().setConnectTimeout(0);

    final long ms = System.currentTimeMillis();
    client.getSession().setFlag("listener-info-handler", (ServerInfoHandler) (session, info) -> {
      player.sendMessage(
          "&7Zpingowano. &c[ Silnik serwera: &7" + info.getVersionInfo().getVersionName()
              + "&c, graczy: &7"
              + info.getPlayerInfo().getOnlinePlayers() + "&8/&7" + info.getPlayerInfo()
              .getMaxPlayers() + "&c, motd: &7" +
              info.getDescription().getFullText() + " &c]");
      player.sendMessage(
          "&7Serwer odpowiedzial na ping w: &6" + (System.currentTimeMillis() - ms) + "ms");

      client.getSession().disconnect("pinged.");
    });
    client.getSession().setFlag("listener-ping-time-handler",
        (ServerPingTimeHandler) (session, pingTime) -> {
        });
    client.getSession().connect();
  }

}
