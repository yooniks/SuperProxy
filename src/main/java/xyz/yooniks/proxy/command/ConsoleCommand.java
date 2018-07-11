package xyz.yooniks.proxy.command;

import java.util.Arrays;
import java.util.List;

public abstract class ConsoleCommand {

  private final List<String> names;

  public ConsoleCommand(List<String> names) {
    this.names = names;
  }

  public ConsoleCommand(String... names) {
    this.names = Arrays.asList(names);
  }

  public abstract void onExecute(String[] args);

  public List<String> getNames() {
    return this.names;
  }

}