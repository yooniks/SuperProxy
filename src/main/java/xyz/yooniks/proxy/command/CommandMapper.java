package xyz.yooniks.proxy.command;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommandMapper {

  private final List<ConsoleCommand> consoleCommands = new ArrayList<>();
  private final List<GameCommand> gameCommands = new ArrayList<>();

  public Optional<ConsoleCommand> consoleCommandByName(String name) {
    return this.consoleCommands.stream()
        .filter(consoleCommand -> consoleCommand.getNames().contains(name))
        .findFirst();
  }

  public Optional<GameCommand> gameCommandByName(String name) {
    return this.gameCommands.stream()
        .filter(gameCommand -> gameCommand.getNames().contains(name))
        .findFirst();
  }

  public void registerGameCommands(GameCommand... commands) {
    this.gameCommands.addAll(Arrays.stream(commands).collect(Collectors.toList()));
  }

  public void registerConsoleCommands(ConsoleCommand... commands) {
    this.consoleCommands.addAll(Arrays.stream(commands).collect(Collectors.toList()));
  }

  public ImmutableList<GameCommand> getGameCommands() {
    return ImmutableList.copyOf(this.gameCommands);
  }

  public ImmutableList<ConsoleCommand> getConsoleCommands() {
    return ImmutableList.copyOf(this.consoleCommands);
  }

}
