package xyz.yooniks.proxy.listener.packetstransmitter;

import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.SubProtocol;
import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.mc.protocol.data.game.values.entity.MetadataType;
import org.spacehq.mc.protocol.data.game.values.entity.player.GameMode;
import org.spacehq.mc.protocol.data.game.values.scoreboard.ObjectiveAction;
import org.spacehq.mc.protocol.data.game.values.scoreboard.ScoreType;
import org.spacehq.mc.protocol.data.game.values.scoreboard.ScoreboardPosition;
import org.spacehq.mc.protocol.data.game.values.setting.Difficulty;
import org.spacehq.mc.protocol.data.game.values.world.WorldType;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerDisconnectPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerPlayerListDataPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerPluginMessagePacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerRespawnPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnMobPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnPlayerPacket;
import org.spacehq.mc.protocol.packet.ingame.server.scoreboard.ServerDisplayScoreboardPacket;
import org.spacehq.mc.protocol.packet.ingame.server.scoreboard.ServerScoreboardObjectivePacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerUpdateTimePacket;
import org.spacehq.mc.protocol.packet.login.server.LoginDisconnectPacket;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectingEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.PacketSentEvent;
import org.spacehq.packetlib.event.session.SessionListener;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.listener.packetstransmitter.handler.CaptchaHandler;
import xyz.yooniks.proxy.listener.packetstransmitter.handler.CaptchaHandler.CaptchaHandlerBossbar;
import xyz.yooniks.proxy.listener.packetstransmitter.handler.CaptchaHandler.Result;
import xyz.yooniks.proxy.message.MessageBuilder;
import xyz.yooniks.proxy.user.ProxyUser;

public class ConnectPacketsTransmitter implements SessionListener {

  private final Player player;
  private final Session session;
  private final ProxyUser user;

  public ConnectPacketsTransmitter(Player player, Session session, ProxyUser user) {
    this.player = player;
    this.session = session;
    this.user = user;
  }

  @Override
  public void disconnected(DisconnectedEvent event) {
    this.user.setFakeSession(null);
    final GameProfile profile = event.getSession().getFlag("profile");
    this.player.sendMessage("&7Bot: &6" + profile.getName() + " &7zostal rozlaczony "
        + "z: &6" + event.getSession().getHost() + "&7, powod: &6" + event.getReason());
    this.session.removeListener(this);
  }

  @Override
  public void packetSent(PacketSentEvent event) {
  }

  @Override
  public void disconnecting(DisconnectingEvent event) {
  }

  @Override
  public void connected(ConnectedEvent connectedEvent) {
  }

  @Override
  public void packetReceived(PacketReceivedEvent event) {
    this.user.getOptions().getLastPacket().getTime().setValue(
        System.currentTimeMillis());
    this.user.getOptions().getLastPacket().getName().setValue(
        event.getPacket().getClass().getSimpleName());

    if (event.getPacket() instanceof LoginDisconnectPacket) {
      final GameProfile profile = event.getSession().getFlag("profile");
      this.player
          .sendMessage("&7Bot: &6" + profile.getName() + " &7zostal rozlaczony podczas laczenia "
              + "z: &6" + event.getSession().getHost() + "&7, powod: &6"
              + ((LoginDisconnectPacket) event.getPacket()).getReason());
    }
    final MinecraftProtocol mp = (MinecraftProtocol) this.session.getPacketProtocol();
    final MinecraftProtocol mp2 = (MinecraftProtocol) this.player.getSession().getPacketProtocol();

    if (mp.getSubProtocol() != SubProtocol.GAME || mp2.getSubProtocol() != SubProtocol.GAME) {
      return;
    }

    if (event.getPacket() instanceof ServerJoinGamePacket) {
      this.
          session.send(new ServerJoinGamePacket(0, false, GameMode.SURVIVAL, 1,
          Difficulty.PEACEFUL, 10, WorldType.DEFAULT_1_1, false));
      this.session.send(new ServerPlayerPositionRotationPacket(0.0, 90.0, 0.0, 90.0f, 90.0f));
      this.session.send(new ServerSpawnPositionPacket(new Position(0, 90, 0)));
      this.session.send(new ServerRespawnPacket(0, Difficulty.PEACEFUL, GameMode.SURVIVAL,
          WorldType.DEFAULT));

      if (this.user.getOptions().getAutoLogin().getValue()) {
        event.getSession().send(new ClientChatPacket("/register superproksi123 superproksi123"));
        event.getSession().send(new ClientChatPacket("/login superproksi123"));
      }

      this.user.setFakeSession(event.getSession());
      this.player.sendMessage("&7Dolaczyles do serwera: &6" + event.getSession().getHost());
      return;
    }

    if (event.getPacket() instanceof ServerChatPacket) {
      if (this.user.getOptions().getAutoCaptcha().getValue()) {
        final ServerChatPacket packet = event.getPacket();
        final String message = packet.getMessage().toString().toLowerCase();

        if (CaptchaHandler.findResult(message) == Result.DETECTED) {
          final String[] args;
          if (message.contains(":")) {
            args = message.split(":");
          }
          else {
            args = message.split("to ");
          }
          final CaptchaHandler captchaHandler = new CaptchaHandlerBossbar(StringUtils.replace(args[1], " ", ""));
          captchaHandler.handle(this.player, event.getSession());
        }
      }
    }
    //autocaptcha
    else if (event.getPacket() instanceof ServerSpawnPlayerPacket) {
      final ServerScoreboardObjectivePacket objectivePacket = new ServerScoreboardObjectivePacket(
          "superproxy-head",
          ObjectiveAction.ADD, new MessageBuilder("&8(&6SuperProxy&8)").toString(),
          ScoreType.HEARTS);
      final ServerDisplayScoreboardPacket displayPacket = new ServerDisplayScoreboardPacket(
          ScoreboardPosition.BELOW_NAME, "superproxy-head");
      this.session.send(objectivePacket, displayPacket);
    }
    else if (event.getPacket() instanceof ServerSpawnMobPacket) {
      final ServerSpawnMobPacket packet = event.getPacket();
      Arrays.stream(packet.getMetadata())
          .filter(entityMetadata -> entityMetadata.getType() == MetadataType.STRING)
          .map(entityMetadata -> (String) entityMetadata.getValue())
          .forEach(message -> {
            final String[] args;
            if (message.contains(":")) {
              args = message.split(":");
            } else {
              args = message.split("to ");
            }
            final CaptchaHandler captchaHandler = new CaptchaHandlerBossbar(
                StringUtils.replace(args[1], " ", ""));
            captchaHandler.handle(this.player, event.getSession());
          });
    }
    else if (event.getPacket() instanceof ServerPluginMessagePacket) {
      final ServerPluginMessagePacket packet = event.getPacket();
      if (packet.getChannel().equals("MC|Brand")) {
        this.player.sendMessage("&7Silnik serwera: &6" + new String(packet.getData()));
      }
    } else if (event.getPacket() instanceof ServerDisplayScoreboardPacket) {
      final ServerDisplayScoreboardPacket packet = event.getPacket();
      if (packet.getPosition() == ScoreboardPosition.BELOW_NAME) {
        return;
      }
    }
    if (event.getPacket() instanceof ServerPlayerListEntryPacket) {
      final ServerPlayerListEntryPacket packet = event.getPacket();
      if (packet.getEntries().length > 0 && packet.getEntries()[0].getGameMode() == null) {
        return;
      }
    }
    if (event.getPacket() instanceof ServerUpdateTimePacket ||
        event.getPacket() instanceof LoginDisconnectPacket ||
        event.getPacket() instanceof ServerDisconnectPacket ||
        event.getPacket() instanceof ServerPlayerListDataPacket) {
      return;
    }
    this.session.send(event.getPacket());
  }

}
