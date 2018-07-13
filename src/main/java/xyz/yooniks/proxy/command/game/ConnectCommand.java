package xyz.yooniks.proxy.command.game;

import xyz.yooniks.proxy.command.GameCommand;
import xyz.yooniks.proxy.entity.player.Player;
import xyz.yooniks.proxy.user.ProxyUser;

public class ConnectCommand extends GameCommand {

  public ConnectCommand(String usage, String... names) {
    super(usage, names);
  }

  @Override
  public void onExecute(ProxyUser executor, String[] args) {
    if (args.length == 0) {
      executor.asPlayer().ifPresent(this::printUsage);
      return;
    }
    executor.asPlayer().ifPresent(player ->
        player.sendMessage(args)
    );
  }

  private void printUsage(Player player) {
    player.sendMessage("&7Poprawne uzycie: &6!connect [serwer] [ip] <nick> <ip proxy>");
  }

}
