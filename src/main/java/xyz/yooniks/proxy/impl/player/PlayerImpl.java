package xyz.yooniks.proxy.impl.player;

import java.util.UUID;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.SubProtocol;
import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.Location;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.message.MessageBuilder;

public class PlayerImpl implements Player {

  private final GameProfile profile;
  private final Location location;
  private final Session session;

  private final String name;
  private final UUID uniqueId;

  public PlayerImpl(Session session) {
    final GameProfile profile = session.getFlag("profile");
    this.name = profile.getName(); //i prefer to hold it like that, at this moment
    this.uniqueId = profile.getUUID();
    this.profile = profile;

    this.session = session;
    this.location = new Location(new Position(0, 80, 0), 0.0F, 0.0F);
  }

  @Override
  public void sendMessage(String text) {
    final MinecraftProtocol protocol = (MinecraftProtocol) this.session.getPacketProtocol();
    if (this.session.isConnected() && protocol.getSubProtocol() == SubProtocol.GAME) {
      this.session.send(new ServerChatPacket(new MessageBuilder(text).build()));
    }
  }

  @Override
  public void sendMessage(String... args) {
    /*
    Arrays.stream(args).forEach(this::sendMessage);
     */
    final MinecraftProtocol protocol = (MinecraftProtocol) this.session.getPacketProtocol();
    if (this.session.isConnected() && protocol.getSubProtocol() == SubProtocol.GAME) {
      this.session.send(new ServerChatPacket(new MessageBuilder(args).build()));
    }
  }

  @Override
  public void teleport(Location location) {
    if (this.session.isConnected()) {
      final Position pos = location.getPosition();
      this.session.send(new ServerPlayerPositionRotationPacket(pos.getX(), pos.getY(),
          pos.getZ(), location.getYaw(), location.getPitch()));
    }
  }

  @Override
  public Session getSession() {
    return session;
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public UUID getUniqueId() {
    return uniqueId;
  }

}
