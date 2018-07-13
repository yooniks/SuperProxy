package xyz.yooniks.proxy.impl.user;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import xyz.yooniks.proxy.entity.bot.Bot;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.proxy.SuperProxy;
import xyz.yooniks.proxy.user.Group;
import xyz.yooniks.proxy.user.ProxyUser;

public class ProxyUserImpl implements ProxyUser {

  private final String name;
  private final UUID uniqueId;
  private final List<Bot> bots = new LinkedList<>();
  private final ProxyUserOptions options;
  private Group group;

  public ProxyUserImpl(String name, UUID uniqueId, ProxyUserOptions userOptions) {
    this.name = name;
    this.uniqueId = uniqueId;
    this.options = userOptions;
  }

  @Override
  public List<Bot> getBots() {
    return bots;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public UUID getUniqueId() {
    return uniqueId;
  }

  @Override
  public Optional<Player> asPlayer() {
    return SuperProxy.getInstance().getPlayerFactory().getPlayer(this.uniqueId);
  }

  @Override
  public ProxyUserOptions getOptions() {
    return this.options;
  }

  @Override
  public Group getGroup() {
    return group;
  }

  @Override
  public void setGroup(Group group) {
    this.group = group;
  }

}
