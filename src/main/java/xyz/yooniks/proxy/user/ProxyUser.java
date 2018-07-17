package xyz.yooniks.proxy.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.spacehq.packetlib.Session;
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

  Optional<Session> getFakeSession();

  void setFakeSession(Session session);

  void sendMessage(String text);

}
