package xyz.yooniks.proxy.listener.packetstransmitter;

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
  public void connected(final ConnectedEvent connectedEvent) {
  }

  @Override
  public void packetReceived(final PacketReceivedEvent event) {
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
          final String[] args = message.split(":");
          if (args.length < 2) {
            return;
          }
          new CaptchaHandler(StringUtils.replace(args[1], " ", ""))
              .handle(this.player, event.getSession());
        }
      }

     /* if (packet.getMessage().toString().toLowerCase().contains("captcha:")
          || packet.getMessage().toString().toLowerCase().contains("kod:")) {
        final String message = packet.getMessage().toString();
        final String[] args = message.split(":");
        if (args.length < 2) {
          return;
        }
        args[1] = StringUtils.replace(args[1], " ", "");
        event.getSession()
            .send(new ClientChatPacket("/register " + args[1] + " superproxy2137 superproxy2137"));
        event.getSession()
            .send(new ClientChatPacket("/register superproxy2137 superproxy2137 " + args[1]));
        this.player.sendMessage("&7Wykryto kod captcha, autologowanie.. &8(&6" + args[1] + "&8)");
      } else if (packet.getMessage().toString().toLowerCase().contains("kod")
          && packet.getMessage().toString().toLowerCase().contains("to")) {
        final String message = packet.getMessage().toString();
        final String[] args = message.split("to ");
        if (args.length < 2) {
          return;
        }
        args[1] = StringUtils.replace(args[1], " ", "");
        this.player.sendMessage("&7Wykryto kod captcha, autologowanie.. &8(&6" + args[1] + "&8)");
        event.getSession()
            .send(new ClientChatPacket("/register " + args[1] + " superproxy2137 superproxy2137"));
        event.getSession()
            .send(new ClientChatPacket("/register superproxy2137 superproxy2137 " + args[1]));
      }*/
      //}
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
    } else if (event.getPacket() instanceof ServerSpawnMobPacket) {
      final ServerSpawnMobPacket p3 = event.getPacket();
      for (int i = 0; i < p3.getMetadata().length; ++i) {
        if (p3.getMetadata()[i].getType() == MetadataType.STRING) {
          final String msg2 = p3.getMetadata()[i].getValue().toString();
          if (msg2.toLowerCase().contains("captcha:") || msg2.toLowerCase().contains("kod:")) {
            final String[] args2 = msg2.split(":");
            if (args2.length < 2 || args2[1] == null) {
              return;
            }
            args2[1] = args2[1].replace(" ", "");
            args2[1] = args2[1].replace("§c", "");
            args2[1] = args2[1].replace("§e", "");
            args2[1] = args2[1].replace("§6", "");
            args2[1] = args2[1].replace("§a", "");
            args2[1] = args2[1].replace("§b", "");
            args2[1] = args2[1].replace("§2", "");
            event.getSession().send(
                new ClientChatPacket("/register " + args2[1] + " superproxy2137 superproxy2137"));
            event.getSession()
                .send(new ClientChatPacket("/register superproxy2137 superproxy2137 " + args2[1]));
          }
        }
      }
    } else if (event.getPacket() instanceof ServerPluginMessagePacket) {
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
