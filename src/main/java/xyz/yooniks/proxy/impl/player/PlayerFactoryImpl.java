package xyz.yooniks.proxy.impl.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.entity.player.PlayerFactory;

public class PlayerFactoryImpl implements PlayerFactory {

  private final Map<UUID, Player> producedPlayers = new HashMap<>();

  @Override
  public Player produce(Session session) {
    final UUID uuid = ((GameProfile) session.getFlag("profile")).getUUID();
    Player player = this.producedPlayers.get(uuid);
    if (player == null) {
      this.producedPlayers.put(uuid, player = new PlayerImpl(session));
    }
    return player;
  }

  @Override
  public Optional<Player> getPlayer(UUID uuid) {
    return Optional.ofNullable(this.producedPlayers.get(uuid));
  }

  @Override
  public boolean isProduced(UUID uuidOfPlayer) {
    return this.producedPlayers.containsKey(uuidOfPlayer);
  }

}
