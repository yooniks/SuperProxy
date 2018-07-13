package xyz.yooniks.proxy.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import xyz.yooniks.proxy.entity.bot.Bot;
import xyz.yooniks.proxy.entity.player.Player;

public interface ProxyUser {

  String getName();

  UUID getUniqueId();

  List<Bot> getBots();

  Optional<Player> asPlayer();

  ProxyUserOptions getOptions();

  Group getGroup();

  void setGroup(Group group);

  interface ProxyUserOptions {

    Optionable<String> getBotsPrefix();

    Optionable<Boolean> getAutoReconnect();
  }

}
