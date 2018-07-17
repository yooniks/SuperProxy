package xyz.yooniks.proxy.impl.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.SuperProxyImpl;
import xyz.yooniks.proxy.entity.bot.Bot;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.user.Group;
import xyz.yooniks.proxy.user.ProxyUser;
import xyz.yooniks.proxy.user.ProxyUserOptions;

public class ProxyUserImpl implements ProxyUser {

  private final String name;
  private final UUID uniqueId;
  private final ProxyUserOptions options;
  private Group group;

  private Session fakeSession;

  public ProxyUserImpl(String name, UUID uniqueId, ProxyUserOptions userOptions) {
    this.name = name;
    this.uniqueId = uniqueId;
    this.options = userOptions;
  }

  @Override
  public List<Bot> getBots() {
    return SuperProxyImpl.getInstance().getBotManager().asImmutableMap().values()
        .stream()
        .filter(bot -> bot.findOwner().isPresent()
            && bot.findOwner().get().getUniqueId() == this.uniqueId)
        .collect(Collectors.toList());
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
    return SuperProxyImpl.getInstance().getPlayerFactory().getPlayer(this.uniqueId);
  }

  @Override
  public void sendMessage(String text) {
    this.asPlayer().ifPresent(player -> player.sendMessage(text));
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

  @Override
  public Optional<Session> getFakeSession() {
    return Optional.ofNullable(this.fakeSession);
  }

  @Override
  public void setFakeSession(Session session) {
    this.fakeSession = session;
  }

}
