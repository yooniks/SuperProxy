package xyz.yooniks.proxy.impl.player;

import java.util.UUID;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.SubProtocol;
import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.mc.protocol.data.game.values.MessageType;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerTitlePacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.entity.teleport.Location;
import xyz.yooniks.proxy.message.MessageBuilder;

public class PlayerImpl implements Player {

  private final Location location;

  private final String name;
  private final UUID uniqueId;

  private Session session;

  public PlayerImpl(Session session) {
    final GameProfile profile = session.getFlag("profile");
    this.name = profile.getName();
    this.uniqueId = profile.getUUID();

    this.session = session;
    this.location = new Location();
  }

  @Override
  public void sendMessage(String text) {
    final MinecraftProtocol protocol = (MinecraftProtocol) this.session.getPacketProtocol();
    if (this.session.isConnected() && protocol.getSubProtocol() == SubProtocol.GAME) {
      this.session.send(new ServerChatPacket(new MessageBuilder("&cSuperProxy&7: " + text).build()));
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
  public void sendActionbar(String text) {
    if (this.session.isConnected()) {
      this.session
          .send(new ServerChatPacket(new MessageBuilder(text).build(), MessageType.NOTIFICATION));
    }
  }

  @Override
  public void sendTitle(String title, String subtitle) {
    if (this.session.isConnected()) {
      if (!title.isEmpty()) {
        this.session.send(new ServerTitlePacket(new MessageBuilder(title).build(), false));
      }
      if (!subtitle.isEmpty()) {
        this.session.send(new ServerTitlePacket(new MessageBuilder(subtitle).build(), true));
      }
      this.session.send(new ServerTitlePacket(0, 40, 5));
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
  public void setSession(Session session) {
    this.session = session;
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
