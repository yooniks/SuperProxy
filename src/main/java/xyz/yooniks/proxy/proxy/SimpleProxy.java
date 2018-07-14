package xyz.yooniks.proxy.proxy;

import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.AbstractProxy;

public class SimpleProxy extends AbstractProxy {

  private final Server server;

  public SimpleProxy(ProxyDescription description) {
    super(description);

    this.server = new Server("0.0.0.0", 2137, MinecraftProtocol.class, new TcpSessionFactory());
  }

  @Override
  public void onEnable() {
    this.server.bind();
  }

  @Override
  public Server getServer() {
    return this.getServer();
  }
}
