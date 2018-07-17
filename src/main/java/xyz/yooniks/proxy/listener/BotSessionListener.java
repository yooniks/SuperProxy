package xyz.yooniks.proxy.listener;

import java.net.Proxy;
import org.apache.commons.lang.StringUtils;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectingEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.PacketSentEvent;
import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.entity.bot.Bot;
import xyz.yooniks.proxy.entity.bot.BotManager;
import xyz.yooniks.proxy.listener.packetstransmitter.handler.CaptchaHandler;
import xyz.yooniks.proxy.listener.packetstransmitter.handler.CaptchaHandler.Result;
import xyz.yooniks.proxy.user.ProxyUser;
import xyz.yooniks.proxy.user.ProxyUserOptions.BotMessageType;

public class BotSessionListener implements SessionListener {

  private final ProxyUser owner;
  private final Bot bot;
  private final BotManager botManager;

  private final Proxy proxy;

  public BotSessionListener(ProxyUser owner, Bot bot,
      BotManager botManager, Proxy proxy) {
    this.owner = owner;
    this.bot = bot;
    this.botManager = botManager;
    this.proxy = proxy;
  }

  @Override
  public void connected(ConnectedEvent event) {
  }


  @Override
  public void disconnected(DisconnectedEvent event) {

    final BotMessageType messageType = this.owner.getOptions().getBotJoinQuitMessageType()
        .getValue();

    if (messageType == BotMessageType.CHAT) {
      this.owner.sendMessage("&7Bot &6" + this.bot.getName() + "&7 zostal rozlaczony z serwera: "
          + "&6" + event.getSession().getHost() + "&7, powod: &6" + event.getReason());
    } else if (messageType == BotMessageType.ACTIONBAR) {
      this.owner.asPlayer().ifPresent(player ->
          player.sendActionbar("&7Bot &6" + this.bot.getName() + "&7 zostal rozlaczony z serwera: "
              + "&6" + event.getSession().getHost() + "&7, powod: &6" + event.getReason()));
    }

    final String reason = event.getReason().toLowerCase();
    if (reason.contains("ponownie") && this.owner.getOptions().getBotsAutoReconnect().getValue()) {
      final Client client = new Client(event.getSession().getHost(), event.getSession().getPort(),
          new MinecraftProtocol(this.bot.getName()),
          new TcpSessionFactory(this.proxy));
      client.getSession().setConnectTimeout(0);
      client.getSession().addListener(this);
      client.getSession().connect();
      return;
    }

    this.botManager.removeBot(this.bot.getUniqueId());
  }

  @Override
  public void packetReceived(PacketReceivedEvent event) {
    if (event.getPacket() instanceof ServerJoinGamePacket) {
      this.bot.setSession(event.getSession());
      this.botManager.addBot(this.bot);

      if (this.owner.getOptions().getAutoLogin().getValue()) {
        event.getSession().send(new ClientChatPacket("/register superproksi123 superproksi123"));
        event.getSession().send(new ClientChatPacket("/login superproksi123"));
      }

      final BotMessageType messageType = this.owner.getOptions().getBotJoinQuitMessageType()
          .getValue();
      if (messageType == BotMessageType.HIDDEN) {
        return;
      }
      if (messageType == BotMessageType.CHAT) {
        this.owner.sendMessage("&7Bot &6" + this.bot.getName() + " &7dolaczyl do serwera: "
            + event.getSession().getHost());
      } else if (messageType == BotMessageType.ACTIONBAR) {
        this.owner.asPlayer().ifPresent(player ->
            player.sendActionbar("&7Bot &6" + this.bot.getName() + " &7dolaczyl do serwera: "
                + event.getSession().getHost()));
      }
    } else if (event.getPacket() instanceof ServerChatPacket) {
      if (this.owner.getOptions().getBotsAutoCaptcha().getValue()) {
        final ServerChatPacket packet = event.getPacket();
        final String message = packet.getMessage().toString().toLowerCase();

        if (CaptchaHandler.findResult(message) == Result.DETECTED) {
          final String[] args = message.split(":");
          if (args.length < 2) {
            return;
          }
          new CaptchaHandler(StringUtils.replace(args[1], " ", ""))
              .handle(null, event.getSession());
        }
      }
    }
  }

  @Override
  public void packetSent(PacketSentEvent p0) {
  }

  @Override
  public void disconnecting(DisconnectingEvent p0) {
  }

}
