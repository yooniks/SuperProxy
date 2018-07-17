package xyz.yooniks.proxy.listener;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectingEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.PacketSentEvent;
import org.spacehq.packetlib.event.session.SessionListener;
import xyz.yooniks.proxy.command.basic.CommandManager;
import xyz.yooniks.proxy.user.ProxyUser;

public class PlayerSessionListener implements SessionListener {

  private final CommandManager commandManager;
  private final ProxyUser user;

  public PlayerSessionListener(CommandManager commandManager, ProxyUser user) {
    this.commandManager = commandManager;
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
      final String commandName = StringUtils.replaceFirst(message, "!", "");

      final String[] args = message.split(" ");
      final String[] argsForCommand = Arrays.copyOfRange(args, 1, args.length);

      this.commandManager
          .findConsoleCommand(commandName)
          .ifPresent(command -> command.getCommandExecutor().execute(this.user, argsForCommand));

    }
  }

  @Override
  public void packetSent(PacketSentEvent event) {
  }

  @Override
  public void disconnected(DisconnectedEvent event) {
    this.user.asPlayer().ifPresent(player ->
        player.setSession(null)
    );
  }

  @Override
  public void disconnecting(DisconnectingEvent p0) {
  }

}
