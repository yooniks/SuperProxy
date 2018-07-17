package xyz.yooniks.proxy.command.console;

import java.util.stream.Collectors;
import xyz.yooniks.proxy.command.Command;
import xyz.yooniks.proxy.command.basic.CommandExecutor;
import xyz.yooniks.proxy.command.basic.CommandInfo;
import xyz.yooniks.proxy.command.basic.CommandManager;
import xyz.yooniks.proxy.user.ProxyUser;

@CommandInfo(
    name = "help",
    aliases = { "pomoc", "komendy", "commands" },
    gameOnly = false
)
public class HelpCommand implements CommandExecutor {

  private final CommandManager commandManager;

  public HelpCommand(CommandManager commandManager) {
    this.commandManager = commandManager;
  }

  @Override
  public void execute(ProxyUser executor, String[] args) {
    final String commands = this.commandManager.getConsoleCommands()
        .stream().map(Command::getName)
        .collect(Collectors.joining(", "));

    System.out.println("Lista dostepnych komend dla konsoli: \"" + commands + "\"");
  }

}
