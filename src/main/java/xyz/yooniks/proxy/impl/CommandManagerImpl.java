package xyz.yooniks.proxy.impl;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import xyz.yooniks.proxy.command.Command;
import xyz.yooniks.proxy.command.basic.CommandExecutor;
import xyz.yooniks.proxy.command.basic.CommandInfo;
import xyz.yooniks.proxy.command.basic.CommandManager;

public class CommandManagerImpl implements CommandManager {

  private final Set<Command> commands = new HashSet<>();

  public Optional<Command> findConsoleCommand(String name) {
    return this.commands.stream()
        .filter(command -> command.getName().equalsIgnoreCase(name) || command.getAliases().contains(name))
        .findFirst();
  }

  @Override
  public Optional<Command> findGameCommand(String name) {
    return this.commands.stream()
        .filter(command -> command.getName().equalsIgnoreCase(name) || command.getAliases().contains(name))
        .findFirst();
  }

  @Override
  public void registerCommands(CommandExecutor... objects) {
    Arrays.stream(objects).forEach(this::registerCommandObject);
  }

  @Override
  public void registerCommandClass(Class<? extends CommandExecutor> clazz) {
    try {
      this.registerCommandObject(clazz.newInstance());
    }
    catch (InstantiationException | IllegalAccessException exception) {

    }
  }

  @Override
  public void registerCommandObject(CommandExecutor object) {
    final CommandInfo info = object.getClass().getDeclaredAnnotation(CommandInfo.class);
    final Command command = new Command(info.name(), info.description(), info.aliases(), info.gameOnly(), object);
    this.commands.add(command);
  }

  @Override
  public ImmutableList<Command> getConsoleCommands() {
    return this.commands.stream()
        .filter(command -> !command.isGameOnly())
        .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
  }

  @Override
  public ImmutableList<Command> getGameCommands() {
    return this.commands.stream()
        .filter(Command::isGameOnly)
        .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
  }

}
