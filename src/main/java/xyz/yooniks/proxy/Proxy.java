package xyz.yooniks.proxy;

import java.io.File;
import java.util.logging.Logger;
import org.spacehq.packetlib.Server;
import xyz.yooniks.proxy.proxy.ProxyDescription;

public interface Proxy {

  void onEnable();

  File getDataFolder();

  Server getServer();

  Logger getLogger();

  ProxyDescription getDescription();

}
