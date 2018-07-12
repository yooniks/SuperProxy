package xyz.yooniks.proxy.user;

import java.util.UUID;
import org.spacehq.packetlib.Session;

public interface ProxyUserManager {

  ProxyUser getUser(String name, UUID uuid);

  ProxyUser fromSession(Session session);

}
