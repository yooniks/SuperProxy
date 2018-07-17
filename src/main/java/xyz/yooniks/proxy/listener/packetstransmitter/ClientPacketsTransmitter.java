package xyz.yooniks.proxy.listener.packetstransmitter;

import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.SubProtocol;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.login.server.LoginDisconnectPacket;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectingEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.PacketSentEvent;
import org.spacehq.packetlib.event.session.SessionListener;

public class ClientPacketsTransmitter implements SessionListener {

  private final Session session;

  public ClientPacketsTransmitter(Session session) {
    this.session = session;
  }

  @Override
  public void packetSent(PacketSentEvent p0) {
  }

  @Override
  public void packetReceived(PacketReceivedEvent event) {
    final MinecraftProtocol protocol = (MinecraftProtocol) session.getPacketProtocol();
    if (protocol.getSubProtocol() != SubProtocol.GAME) {
      return;
    }
    if (event.getPacket() instanceof ClientChatPacket) {
      //handle packet, e.g modify text for illegal characters etc.
      final ClientChatPacket packet = event.getPacket();
      if (packet.getMessage().startsWith("!")) {
        return;
      }
      this.session.send(event.getPacket());
      return;
    }
    if (!(event.getPacket() instanceof LoginDisconnectPacket)) {
      this.session.send(event.getPacket());
    }
  }

  @Override
  public void disconnecting(DisconnectingEvent p0) {
  }

  @Override
  public void connected(ConnectedEvent p0) {
  }

  @Override
  public void disconnected(DisconnectedEvent event) {
    event.getSession().getPacketProtocol().clearPackets();
    this.session.removeListener(this);
  }

}
