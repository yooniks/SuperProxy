package xyz.yooniks.proxy.tablist;

import org.apache.commons.lang.StringUtils;
import org.spacehq.mc.protocol.packet.ingame.server.ServerPlayerListDataPacket;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.json.JSONConfig;
import xyz.yooniks.proxy.message.MessageBuilder;
import xyz.yooniks.proxy.user.ProxyUser;

public class TablistManager {

  private final JSONConfig config;

  private final Replacer replacer = new Replacer();

  public TablistManager(JSONConfig config) {
    this.config = config;
  }

  public void init(ProxyUser user) {
    final String header = this.replacer.replace(this.config.tab_header, user);
    final String footer = this.replacer.replace(this.config.tab_footer, user);
    user.asPlayer().map(Player::getSession)
        .ifPresent(session -> session.send(
            new ServerPlayerListDataPacket(new MessageBuilder(header).build(),
                new MessageBuilder(footer).build())
        ));
  }

  static class Replacer {

    String replace(String string, ProxyUser user) {
      string = StringUtils.replace(string, "%name%", user.getName());
      return string;
    }

    @Override
    public int hashCode() {
      return 1;
    }
  }

}
