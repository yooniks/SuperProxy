package xyz.yooniks.proxy.entity.player;

import java.util.Optional;
import java.util.UUID;
import xyz.yooniks.proxy.entity.Factory;

public interface PlayerFactory extends Factory<Player> {

  Optional<Player> getPlayer(UUID uuid);

  boolean isProduced(UUID uuidOfPlayer);

}
