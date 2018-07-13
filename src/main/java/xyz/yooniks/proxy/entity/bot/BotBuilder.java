package xyz.yooniks.proxy.entity.bot;

import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.bot.Bot.OwnerInfo;
import xyz.yooniks.proxy.impl.bot.BotImpl;
import xyz.yooniks.proxy.user.ProxyUser;
import xyz.yooniks.proxy.util.Builder;

public class BotBuilder implements Builder<Bot> {

  private Session session;
  private String name;
  private ProxyUser owner;

  public void setSession(Session session) {
    this.session = session;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setOwner(ProxyUser owner) {
    this.owner = owner;
  }

  @Override
  public Bot build() {
    final Bot bot = new BotImpl();
    bot.setSession(this.session);
    bot.setName(this.name);
    bot.setOwnerInfo(new OwnerInfo(this.owner.getUniqueId(), this.owner.getName()));
    return bot;
  }

}
