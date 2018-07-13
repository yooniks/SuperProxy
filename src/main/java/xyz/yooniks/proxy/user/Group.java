package xyz.yooniks.proxy.user;

public enum Group {

  PLAYER("&7", "&7", 0),
  ADMIN("&cAdmin", "&b", 1);

  private final String prefix, suffix;
  private final int permissionLevel;

  Group(String prefix, String suffix, int permissionLevel) {
    this.prefix = prefix;
    this.suffix = suffix;
    this.permissionLevel = permissionLevel;
  }

  static Group getByName(String name) {
    for (Group group : values()) {
      if (group.name().equalsIgnoreCase(name)) {
        return group;
      }
    }
    return PLAYER;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public int getPermissionLevel() {
    return permissionLevel;
  }
}
