package xyz.yooniks.proxy.command;

import java.util.Arrays;
import java.util.List;
import xyz.yooniks.proxy.user.ProxyUser;

public abstract class GameCommand {

  private final static String COMMAND_PREFIX = "!";
  private final List<String> names;
  private final String usage;

  private GameCommand(String usage, List<String> names) {
    this.usage = usage;
    this.names = names;
  }

  public GameCommand(String usage, String... names) {
    this(usage, Arrays.asList(names));
  }

  public abstract void onExecute(ProxyUser executor, String[] args);

  public List<String> getNames() {
    return this.names;
  }

  public String getUsage() {
    return usage;
  }

}