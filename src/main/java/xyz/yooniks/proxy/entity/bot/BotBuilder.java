package xyz.yooniks.proxy.entity.bot;

import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.bot.Bot.OwnerInfo;
import xyz.yooniks.proxy.helper.Builder;
import xyz.yooniks.proxy.impl.bot.BotImpl;
import xyz.yooniks.proxy.user.ProxyUser;

public class BotBuilder implements Builder<Bot> {

  private Session session;
  private String name;
  private ProxyUser owner;

  public BotBuilder setSession(Session session) {
    this.session = session;
    return this;
  }

  public BotBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public BotBuilder setOwner(ProxyUser owner) {
    this.owner = owner;
    return this;
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
