package xyz.yooniks.proxy.listener.packetstransmitter.handler;

import java.util.Optional;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.player.Player;

public interface CaptchaHandler {

  void handle(Player player, Session session);

  //or just returning boolean?
  static Result findResult(String message) {
    if (message.contains("captcha:") || message.contains("kod:")
        || (message.contains("kod") && message.contains("to"))) {
      final String[] args;
      if (message.contains("kod")) {
        args = message.split("to ");
      } else {
        args = message.split(":");
      }
      if (args.length < 2) {
        return Result.UNDETECTED;
      }
      return Result.DETECTED;
    }
    return Result.UNDETECTED;
  }

  class CaptchaHandlerChat implements CaptchaHandler {

    private final String code;

    public CaptchaHandlerChat(String code) {
      this.code = code;
    }

    @Override
    public void handle(Player handler, Session session) {
      session.send(new ClientChatPacket("/register " + this.code + " superproksi123 superproksi123"));
      session.send(new ClientChatPacket("/register superproksi123 superproksi123 " + this.code));

      Optional.ofNullable(handler).ifPresent(player ->
          player.sendMessage("&7Wykryto kod captcha, autologowanie.. &8(&6" + this.code + "&8)"));

    }
  }

  class CaptchaHandlerBossbar implements CaptchaHandler {

    private final String code;

    public CaptchaHandlerBossbar(String code) {
      this.code = code;
    }

    @Override
    public void handle(Player handler, Session session) {
      session
          .send(new ClientChatPacket("/register " + this.code + " superproksi123 superproksi123"));
      session.send(new ClientChatPacket("/register superproksi123 superproksi123 " + this.code));

      Optional.ofNullable(handler).ifPresent(player ->
          player.sendMessage("&7Wykryto kod captcha, autologowanie.. &8(&6" + this.code + "&8)"));
    }
  }

  enum Result {
    DETECTED,
    UNDETECTED
  }

}