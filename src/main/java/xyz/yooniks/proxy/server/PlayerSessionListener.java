package xyz.yooniks.proxy.server;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectingEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.PacketSentEvent;
import org.spacehq.packetlib.event.session.SessionListener;
import xyz.yooniks.proxy.proxy.SuperProxy;
import xyz.yooniks.proxy.user.ProxyUser;

public class PlayerSessionListener implements SessionListener {

  private final ProxyUser user;

  public PlayerSessionListener(ProxyUser user) {
    this.user = user;
  }

  @Override
  public void connected(ConnectedEvent p0) {

  }

  @Override
  public void packetReceived(PacketReceivedEvent event) {
    if (event.getPacket() instanceof ClientChatPacket) {
      final ClientChatPacket packet = event.getPacket();
      final String message = packet.getMessage();
      final String commandName = StringUtils.replace(message.split(" ")[0], "!", "");
      final String[] allArgs = message.split(" ");
      final String[] args = Arrays.copyOfRange(allArgs, 1, allArgs.length);

      this.user.asPlayer().ifPresent(player -> player.sendMessage(commandName + ": " + Arrays
          .stream(args).collect(Collectors.joining(" "))));

      SuperProxy.getInstance().getCommandMapper()
          .gameCommandByName(commandName)
          .ifPresentOrElse((command -> command.onExecute(user, args)),
              () -> user.asPlayer().ifPresent(player -> player.sendMessage("works fine")));

    }
  }

  @Override
  public void packetSent(PacketSentEvent event) {
  }

  @Override
  public void disconnected(DisconnectedEvent p0) {
  }

  @Override
  public void disconnecting(DisconnectingEvent p0) {
  }

}
