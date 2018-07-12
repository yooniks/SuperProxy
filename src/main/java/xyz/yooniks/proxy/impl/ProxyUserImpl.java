package xyz.yooniks.proxy.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import xyz.yooniks.proxy.bot.Bot;
import xyz.yooniks.proxy.player.Player;
import xyz.yooniks.proxy.user.ProxyUser;

public class ProxyUserImpl implements ProxyUser {

  private final String name;
  private final UUID uniqueId;

  private final List<Bot> bots = new LinkedList<>();
  private final ProxyUserOptions options;

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
    return Optional.empty();
  }

  @Override
  public ProxyUserOptions options() {
    return this.options;
  }

}
