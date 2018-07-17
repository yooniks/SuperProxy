package xyz.yooniks.proxy.command.console;

import org.spacehq.mc.protocol.data.message.ChatColor;
import org.spacehq.packetlib.Server;
import xyz.yooniks.proxy.Proxy;
import xyz.yooniks.proxy.command.basic.CommandExecutor;
import xyz.yooniks.proxy.command.basic.CommandInfo;
import xyz.yooniks.proxy.user.ProxyUser;

@CommandInfo(
    name = "stop",
    aliases = { "restart", "close", "exit" },
    gameOnly = false
)
public class StopCommand implements CommandExecutor {

  private final Proxy proxy;

  public StopCommand(Proxy proxy) {
    this.proxy = proxy;
  }

  @Override
  public void execute(ProxyUser executor, String[] args) {
    final Server server = this.proxy.getServer();
    server.getSessions()
        .forEach(session -> session.disconnect(ChatColor.YELLOW + "Proxy wylaczone!"));
    server.close(false);

    System.exit(1);
  }
}
