package xyz.yooniks.proxy.command;

import java.util.Arrays;
import java.util.List;
import xyz.yooniks.proxy.user.ProxyUser;

public abstract class GameCommand {

  private final List<String> names;

  public GameCommand(List<String> names) {
    this.names = names;
  }

  public GameCommand(String... names) {
    this.names = Arrays.asList(names);
  }

  public abstract void onExecute(ProxyUser executor, String[] args);

  List<String> getNames() {
    return this.names;
  }

}