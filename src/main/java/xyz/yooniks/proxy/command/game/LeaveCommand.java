package xyz.yooniks.proxy.command.game;

import xyz.yooniks.proxy.SuperProxyImpl;
import xyz.yooniks.proxy.command.basic.CommandExecutor;
import xyz.yooniks.proxy.command.basic.CommandInfo;
import xyz.yooniks.proxy.user.ProxyUser;

@CommandInfo(
    name = "leave",
    aliases = { "quit", "rozlacz", "wyjdz" },
    description = "Rozlacza klienta z polaczonym z serwerem"
)
public class LeaveCommand implements CommandExecutor {

  @Override
  public void execute(ProxyUser executor, String[] args) {
    executor.asPlayer().ifPresent(player ->
        executor.getFakeSession().ifPresentOrElse((session) -> {
              session.getPacketProtocol().clearPackets();
              session.disconnect("Disconnected by command");

              player.teleport(SuperProxyImpl.SPAWN_LOCATION);
              player.sendMessage("&6Rozlaczono &7klienta.");
            }, () ->
                player.sendMessage("&cNie jestes polaczony z zadnym serwerem!")
        )
    );
  }

}
