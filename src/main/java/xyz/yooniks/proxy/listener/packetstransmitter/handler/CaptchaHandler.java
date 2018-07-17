package xyz.yooniks.proxy.listener.packetstransmitter.handler;

import java.util.Optional;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.player.Player;

public class CaptchaHandler {

  private final String captcha;

  public CaptchaHandler(String captcha) {
    this.captcha = captcha;
  }

  public void handle(Player handler, Session session) {
    session
        .send(new ClientChatPacket("/register " + this.captcha+ " superproksi123 superproksi123"));
    session
        .send(new ClientChatPacket("/register superproksi123 superproksi123 " + this.captcha));

    Optional.ofNullable(handler).ifPresent(player ->
        player.sendMessage("&7Wykryto kod captcha, autologowanie.. &8(&6" + this.captcha + "&8)"));

  }

  //or just returning boolean?
  public static Result findResult(String message) {
    if (message.contains("captcha:") || message.contains("kod:")
        || (message.contains("kod") && message.contains("to"))) {
      return Result.DETECTED;
    }
    return Result.UNDETECTED;
  }

  public enum Result {
    DETECTED,
    UNDETECTED
  }

}
