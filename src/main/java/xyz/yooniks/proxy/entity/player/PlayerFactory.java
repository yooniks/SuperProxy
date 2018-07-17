package xyz.yooniks.proxy.entity.player;

import java.util.Optional;
import java.util.UUID;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.Factory;

public interface PlayerFactory extends Factory<Player> {

  @Override
  Player produce(Session session);

  Optional<Player> getPlayer(UUID uuid);

  boolean isProduced(UUID uuidOfPlayer);

}
