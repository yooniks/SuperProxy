package xyz.yooniks.proxy.command.game;

import xyz.yooniks.proxy.command.GameCommand;
import xyz.yooniks.proxy.user.ProxyUser;

public class HelpCommand extends GameCommand {

  public HelpCommand(String... names) {
    super(names);
  }

  @Override
  public void onExecute(ProxyUser executor, String[] args) {

  }

}
