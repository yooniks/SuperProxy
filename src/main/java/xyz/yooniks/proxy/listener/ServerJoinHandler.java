package xyz.yooniks.proxy.listener;

import java.util.Arrays;
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
import xyz.yooniks.proxy.SuperProxy;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.json.JSONConfig;
import xyz.yooniks.proxy.user.ProxyUser;

public class ServerJoinHandler implements ServerLoginHandler {

  private final SuperProxy proxy;

  private final char[] bigChars;

  public ServerJoinHandler(SuperProxy proxy) {
    this.proxy = proxy;
    Arrays.fill(this.bigChars = new char[7680], ' ');
  }

  @Override
  public void loggedIn(Session session) {
    final ProxyUser user = this.proxy.getUserManager().fromSession(session);
    if (!this.proxy.getPlayerFactory().isProduced(user.getUniqueId())) {
      this.proxy.getPlayerFactory().produce(session);
    }

    this.sendWorldPackets(session); //send position packets
    this.proxy.getTablistManager().refreshTablist(user); //send tab packet
    session.addListener(new PlayerSessionListener(this.proxy.getCommandManager(), user));

    final JSONConfig config = this.proxy.getJsonManager().getConfig();
    final Consumer<Player> playerConsumer = (player) -> {
      player.sendMessage(String.valueOf(this.bigChars));
      player.setSession(session);
      player.sendMessage(
          StringUtils.replace(config.join_message_chat, "%name%", player.getName()));
      player.sendTitle(config.join_message_title, config.join_message_subtitle);
      player.sendActionbar(config.join_message_actionbar);
      //player.teleport(SuperProxyImpl.SPAWN_LOCATION);
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
