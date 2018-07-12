package xyz.yooniks.proxy.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import xyz.yooniks.proxy.bot.Bot;
import xyz.yooniks.proxy.player.Player;

public interface ProxyUser {

  String getName();

  UUID getUniqueId();

  List<Bot> getBots();

  Optional<Player> asPlayer();

  ProxyUserOptions options();

  static interface ProxyUserOptions {

    Optionable<String> botsPrefix();

    Optionable<Boolean> autoReconnect();
  }

}
