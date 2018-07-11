package xyz.yooniks.proxy.server;

import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.ServerLoginHandler;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.user.ProxyUser;
import xyz.yooniks.proxy.user.UserManager;

public class ServerHandler implements ServerLoginHandler {

  private final UserManager userManager;

  public ServerHandler(UserManager userManager) {
    this.userManager = userManager;
  }

  @Override
  public void loggedIn(Session session) {
    final GameProfile profile = session.getFlag("profile");
    final ProxyUser user = this.userManager.getUser(profile.getName(), profile.getUUID());


  }

}
