package xyz.yooniks.proxy.tablist;

import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.lang.StringUtils;
import org.spacehq.mc.protocol.packet.ingame.server.ServerPlayerListDataPacket;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.json.JSONConfig;
import xyz.yooniks.proxy.message.MessageBuilder;
import xyz.yooniks.proxy.user.ProxyUser;
import xyz.yooniks.proxy.user.ProxyUserManager;

public class TablistManager {

  private final JSONConfig config;
  private final Timer timer;

  private final Replacer replacer = new Replacer();

  public TablistManager(JSONConfig config) {
    this.config = config;
    this.timer = new Timer();
  }

  public void startTask(ProxyUserManager userManager) {
    this.timer.scheduleAtFixedRate(new TablistUpdater(userManager), 100L, 100L); //1000 = 1 second
  }

  public void refreshTablist(ProxyUser user) {
    final String header = this.replacer.replace(this.config.tab_header, user);
    final String footer = this.replacer.replace(this.config.tab_footer, user);
    user.asPlayer().map(Player::getSession)
        .ifPresent(session -> session.send(
            new ServerPlayerListDataPacket(new MessageBuilder(header).build(),
                new MessageBuilder(footer).build())
        ));

    final long lastPacketTime = user.getOptions().getLastPacket().getTime().getValue();

    if (user.getFakeSession().isPresent() && System.currentTimeMillis() - lastPacketTime > 1200L) {
      user.asPlayer().ifPresent(player ->
          player.sendTitle("&8=> &cSerwer nie odpowiada! [LAG]", "&8-> &fOd: &7"
              + (System.currentTimeMillis() - lastPacketTime) + "ms")
      );
    }

  }

  static class Replacer {

    String replace(String string, ProxyUser user) {
      string = StringUtils.replace(string, "%name%", user.getName());
      string = StringUtils.replace(string, "%last_packet_name%", user.getOptions().getLastPacket().getName().getValue());
      string = StringUtils.replace(string, "%last_packet_time%", String.valueOf(System.currentTimeMillis() - user.getOptions().getLastPacket().getTime().getValue()));
      return string;
    }

    @Override
    public int hashCode() {
      return 1;
    }
  }

  private class TablistUpdater extends TimerTask {

    private final ProxyUserManager userManager;

    public TablistUpdater(ProxyUserManager userManager) {
      this.userManager = userManager;
    }

    @Override
    public void run() {
      this.userManager.asImmutableList().forEach(
          TablistManager.this::refreshTablist
      );
    }
  }

}
