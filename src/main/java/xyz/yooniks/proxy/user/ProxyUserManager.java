package xyz.yooniks.proxy.user;

import com.google.common.collect.ImmutableList;
import java.util.UUID;
import org.spacehq.packetlib.Session;

public interface ProxyUserManager {

  ProxyUser getUser(String name, UUID uuid);

  ProxyUser fromSession(Session session);

  ImmutableList<ProxyUser> asImmutableList();

}
