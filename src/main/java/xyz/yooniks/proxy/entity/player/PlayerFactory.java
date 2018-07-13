package xyz.yooniks.proxy.entity.player;

import java.util.UUID;
import org.spacehq.packetlib.Session;

public interface PlayerFactory {

  Player produce(Session session);

  boolean isProduced(UUID uuidOfPlayer);

}
