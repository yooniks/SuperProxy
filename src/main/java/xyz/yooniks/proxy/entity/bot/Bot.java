package xyz.yooniks.proxy.entity.bot;

import java.util.Optional;
import java.util.UUID;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.Entity;
import xyz.yooniks.proxy.user.ProxyUser;

public interface Bot extends Entity {

  Session getSession();

  void setSession(Session session);

  Optional<ProxyUser> findOwner();

  void setOwnerInfo(OwnerInfo info);

  void setName(String name);

  class OwnerInfo {

    private final UUID uniqueId;
    private final String name;

    public OwnerInfo(UUID uniqueId, String name) {
      this.uniqueId = uniqueId;
      this.name = name;
    }

    public UUID getUniqueId() {
      return uniqueId;
    }

    public String getName() {
      return name;
    }
  }

}
