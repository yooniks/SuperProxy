package xyz.yooniks.proxy.server;

import java.util.function.Consumer;
import org.apache.commons.lang.StringUtils;
import org.spacehq.mc.protocol.ServerLoginHandler;
import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.mc.protocol.data.game.values.entity.player.GameMode;
import org.spacehq.mc.protocol.data.game.values.setting.Difficulty;
import org.spacehq.mc.protocol.data.game.values.world.WorldType;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerAbilitiesPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerSetExperiencePacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerUpdateTimePacket;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.entity.player.PlayerFactory;
import xyz.yooniks.proxy.json.JSONConfig;
import xyz.yooniks.proxy.tablist.TablistManager;
import xyz.yooniks.proxy.user.ProxyUser;
import xyz.yooniks.proxy.user.ProxyUserManager;

public class ServerJoinHandler implements ServerLoginHandler {

  private final ProxyUserManager userManager;
  private final PlayerFactory playerFactory;
  private final TablistManager tablistManager;
  private final JSONConfig jsonConfig;

  public ServerJoinHandler(ProxyUserManager userManager, PlayerFactory playerFactory,
      TablistManager tablistManager,
      JSONConfig jsonConfig) {
    this.userManager = userManager;
    this.playerFactory = playerFactory;
    this.tablistManager = tablistManager;
    this.jsonConfig = jsonConfig;
  }

  @Override
  public void loggedIn(Session session) {
    final ProxyUser user = this.userManager.fromSession(session);
    if (!this.playerFactory.isProduced(user.getUniqueId())) {
      this.playerFactory.produce(session);
    }

    this.sendWorldPackets(session); //send position packets
    this.tablistManager.init(user); //send tab packet
    session.addListener(new PlayerSessionListener(user));

    final Consumer<Player> playerConsumer = (player) -> {
      player.sendMessage(
          StringUtils.replace(this.jsonConfig.join_message_chat, "%name%", player.getName()));
      player.sendTitle(this.jsonConfig.join_message_title, this.jsonConfig.join_message_subtitle);
      player.sendActionbar(this.jsonConfig.join_message_actionbar);
      //player.teleport(SuperProxy.SPAWN_LOCATION);
    };

    user.asPlayer().ifPresent(playerConsumer);
  }

  private void sendWorldPackets(Session session) {
    session.send(new ServerJoinGamePacket(1, false,
        GameMode.SURVIVAL, 0, Difficulty.PEACEFUL, 1000,
        WorldType.FLAT, false));
    session.send(new ServerSpawnPositionPacket(new Position(
        0, -1337, 0)));

    session.send(new ServerPlayerAbilitiesPacket(false, false, false, false,
        0.1f, 0.1f));
    session.send(new ServerPlayerPositionRotationPacket(0, -1337, 0, 0.0F, 0.0F));

    session.send(new ServerUpdateTimePacket(1L, 10L));
    session.send(new ServerSetExperiencePacket(0.0F, 0, 0));
  }

}
