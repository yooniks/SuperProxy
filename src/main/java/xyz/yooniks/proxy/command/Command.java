package xyz.yooniks.proxy.command;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Command command = (Command) o;
    return gameOnly == command.gameOnly &&
        Objects.equals(name, command.name) &&
        Objects.equals(description, command.description) &&
        Arrays.equals(aliases, command.aliases) &&
        Objects.equals(commandExecutor, command.commandExecutor);
  }

  @Override
  public int hashCode() {

    int result = Objects.hash(name, description, gameOnly, commandExecutor);
    result = 31 * result + Arrays.hashCode(aliases);
    return result;
  }

  @Override
  public String toString() {
    return "Command{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", aliases=" + Arrays.toString(aliases) +
        ", gameOnly=" + gameOnly +
        ", commandExecutor=" + commandExecutor +
        '}';
  }

}
