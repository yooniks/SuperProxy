package xyz.yooniks.proxy.command;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import xyz.yooniks.proxy.command.basic.CommandExecutor;

public class Command {

  private final String name, description;
  private final String[] aliases;
  private final boolean gameOnly;
  private final CommandExecutor commandExecutor;

  public Command(String name, String description, String[] aliases, boolean gameOnly,
      CommandExecutor commandExecutor) {
    this.name = name;
    this.description = description;
    this.aliases = aliases;
    this.gameOnly = gameOnly;
    this.commandExecutor = commandExecutor;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public ImmutableCollection<String> getAliases() {
    return ImmutableList.copyOf(this.aliases);
  }

  public CommandExecutor getCommandExecutor() {
    return commandExecutor;
  }

  public boolean isGameOnly() {
    return gameOnly;
  }

}
