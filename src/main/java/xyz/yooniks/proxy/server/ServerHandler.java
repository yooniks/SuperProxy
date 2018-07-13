package xyz.yooniks.proxy.server;

import java.util.function.Consumer;
import org.spacehq.mc.protocol.ServerLoginHandler;
import org.spacehq.mc.protocol.data.game.values.entity.player.GameMode;
import org.spacehq.mc.protocol.data.game.values.setting.Difficulty;
import org.spacehq.mc.protocol.data.game.values.world.WorldType;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerAbilitiesPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.entity.player.PlayerFactory;
import xyz.yooniks.proxy.proxy.SuperProxy;
import xyz.yooniks.proxy.user.ProxyUser;
import xyz.yooniks.proxy.user.ProxyUserManager;

public class ServerHandler implements ServerLoginHandler {

  private final ProxyUserManager userManager;
  private final PlayerFactory playerFactory;

  public ServerHandler(ProxyUserManager userManager, PlayerFactory playerFactory) {
    this.userManager = userManager;
    this.playerFactory = playerFactory;
  }

  @Override
  public void loggedIn(Session session) {
    final ProxyUser user = this.userManager.fromSession(session);
    if (!this.playerFactory.isProduced(user.getUniqueId())) {
      this.playerFactory.produce(session);
    }

    this.sendWorldPackets(session);

    final Consumer<Player> playerConsumer = (player) -> {
      player.sendMessage("&6Witaj! = )");

      player.teleport(SuperProxy.SPAWN_LOCATION);
    };

    user.asPlayer().ifPresent(playerConsumer);
  }

  private void sendWorldPackets(Session session) {
    session.send(new ServerJoinGamePacket(1, false, GameMode.SURVIVAL, 0, Difficulty.PEACEFUL, 1000,
        WorldType.FLAT, false));
    session.send(new ServerSpawnPositionPacket(SuperProxy.SPAWN_LOCATION.getPosition()));
    session.send(new ServerPlayerAbilitiesPacket(false, false, false, false, 0.1f, 0.1f));
  }

}
