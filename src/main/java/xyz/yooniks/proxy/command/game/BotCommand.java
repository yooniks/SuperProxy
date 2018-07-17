package xyz.yooniks.proxy.command.game;

import java.util.stream.Collectors;
import xyz.yooniks.proxy.command.basic.CommandExecutor;
import xyz.yooniks.proxy.command.basic.CommandInfo;
import xyz.yooniks.proxy.entity.bot.BotManager;
import xyz.yooniks.proxy.user.ProxyUser;

@CommandInfo(
    name = "bot",
    aliases = { "bots", "botcommand", "botoptions" },
    description = "Komendy poboczne do botow"
)
public class BotCommand implements CommandExecutor {

  private final BotManager botManager;

  public BotCommand(BotManager botManager) {
    this.botManager = botManager;
  }

  @Override
  public void execute(ProxyUser executor, String[] args) {
    if (args.length < 1) {
      executor.sendMessage("&cPoprawne uzycie: &6!bot [list/listglobal/quit] <nazwa bota/all>");
      return;
    }
    if (args[0].equalsIgnoreCase("quit") || args[0].equalsIgnoreCase("leave")) {
      if (args.length > 1) {
        final String botName = args[1];
        executor.getBots().stream()
            .filter(bot -> bot.getName().equalsIgnoreCase(botName))
            .findFirst().ifPresentOrElse(bot -> {
              bot.getSession().disconnect("Rozlaczono poprzez komende");
              executor.sendMessage("&7Rozlaczyles bota &6" + botName + "&7 z gry.");
        }, () -> executor.sendMessage("&cBot o nicku &6" + botName + "&c nie istnieje! Lista botow: &6!bot list"));
      }
      else {
        executor.getBots().forEach(bot ->
            bot.getSession().disconnect("Rozlaczono poprzez komende"));
        executor.sendMessage("&7Rozlaczono &6wszystkie &7boty.");
      }
    }
    else if (args[0].equalsIgnoreCase("listglobal")) {
      executor.sendMessage("&7Globalna lista botow (na calym proxy): &6(" + this.botManager.asImmutableMap().size() + ")");
      executor.sendMessage("&7\" " +  this.botManager.asImmutableMap().values()
          .stream()
          .map(bot -> "&6" + bot.getName() + "&7," + bot.getSession().getHost())
          .collect(Collectors.joining(", ")) + "&7\"");
    }
    else {
      executor.sendMessage("&7Lista twoich botow: &6(" + executor.getBots().size() + ")");
      executor.sendMessage("&7\" " +  executor.getBots()
          .stream()
          .map(bot -> "&6" + bot.getName() + "&7," + bot.getSession().getHost())
          .collect(Collectors.joining(", ")) + "&7\"");
    }
  }

}
