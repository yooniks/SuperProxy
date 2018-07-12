package xyz.yooniks.proxy.impl;

import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.SubProtocol;
import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.mc.protocol.data.message.TextMessage;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.message.MessageBuilder;
import xyz.yooniks.proxy.player.Location;
import xyz.yooniks.proxy.player.OfflinePlayer;
import xyz.yooniks.proxy.player.Player;

public class PlayerImpl implements Player {

  private final OfflinePlayer offlinePlayer;
  private final Location location;
  private final Session session;

  public PlayerImpl(Session session) {
    final GameProfile profile = session.getFlag("profile");

    this.session = session;
    this.offlinePlayer = new OfflinePlayer(profile.getName(), profile.getUUID());

    this.location = new Location(new Position(0, 80, 0), 0.0F, 0.0F);
  }

  @Override
  public void sendMessage(String text) {
    final MinecraftProtocol protocol = (MinecraftProtocol) this.session.getPacketProtocol();
    if (this.session.isConnected() && protocol.getSubProtocol() == SubProtocol.GAME) {
      this.session.send(new ServerChatPacket(new TextMessage(new MessageBuilder(text).build())));
    }
  }

  @Override
  public void sendMessage(String... args) {
    /*
    Arrays.stream(args).forEach(this::sendMessage);
     */
    final MinecraftProtocol protocol = (MinecraftProtocol) this.session.getPacketProtocol();
    if (this.session.isConnected() && protocol.getSubProtocol() == SubProtocol.GAME) {
      this.session.send(new ServerChatPacket(new TextMessage(new MessageBuilder(args).build())));
    }
  }

  @Override
  public Session getSession() {
    return session;
  }

}
