package xyz.yooniks.proxy.impl.bot;

import java.util.Optional;
import java.util.UUID;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.SuperProxyImpl;
import xyz.yooniks.proxy.entity.bot.Bot;
import xyz.yooniks.proxy.entity.teleport.Location;
import xyz.yooniks.proxy.user.ProxyUser;

public class BotImpl implements Bot {

  private final UUID uniqueId;
  private final Location location;

  private OwnerInfo ownerInfo;
  private Session session;

  private String name;

  public BotImpl() {
    this.uniqueId = UUID.randomUUID();
    this.location = new Location();
  }

  public BotImpl(UUID ownerUUID, String ownerName, Session session) {
    this.uniqueId = UUID.randomUUID();
    this.location = new Location();

    this.ownerInfo = new OwnerInfo(ownerUUID, ownerName);
    this.session = session;
  }

  public Optional<ProxyUser> findOwner() {
    return Optional.ofNullable(
        SuperProxyImpl.getInstance().getUserManager()
            .getUser(this.ownerInfo.getName(), this.ownerInfo.getUniqueId())
    );
  }

  public OwnerInfo getOwnerInfo() {
    return ownerInfo;
  }

  @Override
  public void setOwnerInfo(OwnerInfo ownerInfo) {
    this.ownerInfo = ownerInfo;
  }

  @Override
  public Session getSession() {
    return session;
  }

  @Override
  public void setSession(Session session) {
    this.session = session;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public UUID getUniqueId() {
    return uniqueId;
  }

}
