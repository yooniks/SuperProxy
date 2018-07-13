package xyz.yooniks.proxy.impl.user;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.user.Optionable;
import xyz.yooniks.proxy.user.ProxyUser;
import xyz.yooniks.proxy.user.ProxyUserManager;

public class ProxyUserManagerImpl implements ProxyUserManager {

  private final Map<UUID, ProxyUser> userMap = new HashMap<>();

  @Override
  public ProxyUser getUser(String name, UUID uuid) {
    ProxyUser user = this.userMap.get(uuid);
    if (user == null) {
      this.userMap.put(uuid,
          user = new ProxyUserImpl(name, uuid,
              new ProxyUserOptionsImpl(new Optionable<>(), new Optionable<>()))
      );
    }
    return user;
  }

  @Override
  public ProxyUser fromSession(Session session) {
    final GameProfile profile = session.getFlag("profile");
    return this.getUser(profile.getName(), profile.getUUID());
  }

}
