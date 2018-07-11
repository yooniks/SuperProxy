package xyz.yooniks.proxy.user;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import xyz.yooniks.proxy.bot.Bot;
import xyz.yooniks.proxy.player.Player;

public class ProxyUser {

  private final String name;
  private final List<Bot> bots = new LinkedList<>();

  private final UUID uniqueId;

  public ProxyUser(String name, UUID uniqueId) {
    this.name = name;
    this.uniqueId = uniqueId;
  }

  public Optional<Player> asProxyPlayer() {
    return Optional.empty();
  }

  public List<Bot> getBots() {
    return bots;
  }

}
