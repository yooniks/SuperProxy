package xyz.yooniks.proxy.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.packetlib.Session;

public class UserManager {

  private final Map<UUID, ProxyUser> userMap = new HashMap<>();

  public ProxyUser getUser(String name, UUID uuid) {
    ProxyUser user = this.userMap.get(uuid);
    if (user == null) {
      this.userMap.put(uuid, user = new ProxyUser(name, uuid));
    }
    return user;
  }

  public ProxyUser fromSession(Session session) {
    final GameProfile profile = session.getFlag("profile");
    return this.getUser(profile.getName(), profile.getUUID());
  }

}
