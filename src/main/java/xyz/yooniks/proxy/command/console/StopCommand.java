package xyz.yooniks.proxy.command.console;

import org.spacehq.mc.protocol.data.message.ChatColor;
import org.spacehq.packetlib.Server;
import xyz.yooniks.proxy.command.ConsoleCommand;
import xyz.yooniks.proxy.proxy.SuperProxy;

public class StopCommand extends ConsoleCommand {

  public StopCommand(String... names) {
    super(names);
  }

  @Override
  public void onExecute(String[] args) {
    final Server server = SuperProxy.getInstance().getServer();
    server.getSessions()
        .forEach(session -> session.disconnect(ChatColor.YELLOW + "Proxy wylaczone!"));
    server.close(false);

    System.exit(1);
  }

}
