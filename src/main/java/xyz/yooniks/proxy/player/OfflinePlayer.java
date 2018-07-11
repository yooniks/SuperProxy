package xyz.yooniks.proxy.player;

import java.util.UUID;

public class OfflinePlayer {

  private final String name;
  private final UUID uniqueId;

  public OfflinePlayer(String name, UUID uniqueId) {
    this.name = name;
    this.uniqueId = uniqueId;
  }

  public String getName() {
    return name;
  }

  public UUID getUniqueId() {
    return uniqueId;
  }

}
