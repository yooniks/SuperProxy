package xyz.yooniks.proxy.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.user.ProxyUser;

@AllArgsConstructor
@Getter
public class Bot {

  private final ProxyUser owner;
  private final Session session;

}
