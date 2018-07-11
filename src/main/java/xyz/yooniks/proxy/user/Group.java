package xyz.yooniks.proxy.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Group {

  PLAYER("&7", "&7", 0),
  ADMIN("&cAdmin", "&b", 1);

  private final String prefix, suffix;
  private final int permissionLevel;

  static Group getByName(String name) {
    for (Group group : values()) {
      if (group.name().equalsIgnoreCase(name)) {
        return group;
      }
    }
    return PLAYER;
  }
}
