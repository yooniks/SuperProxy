package xyz.yooniks.proxy.entity.player;

import java.util.Optional;
import java.util.UUID;
import org.spacehq.packetlib.Session;

public interface PlayerFactory {

  Player produce(Session session);

  Optional<Player> getPlayer(UUID uuid);

  boolean isProduced(UUID uuidOfPlayer);

}
