package xyz.yooniks.proxy.command.basic;

import com.google.common.collect.ImmutableList;
import java.util.Optional;
import xyz.yooniks.proxy.command.Command;

public interface CommandManager {

  Optional<Command> findGameCommand(String name);

  Optional<Command> findConsoleCommand(String name);

  void registerCommandClass(Class<? extends CommandExecutor> clazz);

  void registerCommandObject(CommandExecutor object);

  void registerCommands(CommandExecutor... objects);

  ImmutableList<Command> getGameCommands();

  ImmutableList<Command> getConsoleCommands();

}
