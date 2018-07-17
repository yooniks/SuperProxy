package xyz.yooniks.proxy.command.game;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;
import xyz.yooniks.proxy.command.basic.CommandExecutor;
import xyz.yooniks.proxy.command.basic.CommandInfo;
import xyz.yooniks.proxy.user.Optionable;
import xyz.yooniks.proxy.user.ProxyUser;
import xyz.yooniks.proxy.user.ProxyUserOptions;
import xyz.yooniks.proxy.user.ProxyUserOptions.BotMessageType;

@CommandInfo(
    name = "options",
    aliases = { "opcje", "useroptions" },
    description = "Dodaje mozliwosc edytowania fieldow z interfejsu ProxyUserOptions uzywajac refleksji"
)
public class OptionsCommand implements CommandExecutor {

  @Override
  public void execute(ProxyUser executor, String[] args) {
    if (args.length < 1) {
      executor.sendMessage("&cPoprawne uzycie: &6!options [get/set/list] <nazwa fielda> <wartosc>"
          + "&cField botJoinQuitMessageType przyjmuje wartosci &6CHAT, ACTIONBAR, HIDDEN");
      return;
    }
    if (args[0].equalsIgnoreCase("list")) {
      executor.sendMessage("&cLista fieldow: &6\"" +
          Arrays.stream(executor.getOptions().getClass().getDeclaredFields())
              .map(Field::getName)
              .collect(Collectors.joining(", ")) + "\"");
      return;
    }
    if (args.length < 2) {
      executor.sendMessage("&cPoprawne uzycie: &6!options [get/set/list] <nazwa fielda> <wartosc>"
          + "&cField botJoinQuitMessageType przyjmuje wartosci &6CHAT, ACTIONBAR, HIDDEN");
      return;
    }
    try {
      final ProxyUserOptions userOptions = executor.getOptions();

      final Field field = userOptions
          .getClass().getDeclaredField(args[1]);
      field.setAccessible(true);

      final Optionable<Object> optionable;

      if (field.get(userOptions) instanceof Optionable) {
        optionable = (Optionable<Object>) field.get(userOptions);
      } else {
        executor.sendMessage(
                "&cTen field nie implementuje klasy Optionable, nie moze byc modyfikowany.");
        return;
      }

      if (args[0].equalsIgnoreCase("set")) {
        if (args.length < 3) {
          executor.sendMessage("&cNie podales wartosci! "
                  + "&cPoprawne uzycie: &6!options set [nazwa fielda] [wartosc]");
          return;
        }
        if (optionable.getValue() instanceof Integer) {
          int value;
          try {
            value = Integer.parseInt(args[2]);
          } catch (NumberFormatException exception) {
            executor.sendMessage("&cArgument: &6" + args[2]
                    + " &cnie mogl zostac sparsowany do obiektu Integer, powod: " + exception
                    .getMessage());
            return;
          }
          optionable.setValue(value);
        } else if (optionable.getValue() instanceof Long) {
          long value;
          try {
            value = Long.parseLong(args[2]);
          } catch (NumberFormatException exception) {
            executor.sendMessage("&cArgument: &6" + args[2]
                    + " &cnie mogl zostac sparsowany do obiektu Long, powod: " + exception
                    .getMessage());
            return;
          }
          optionable.setValue(value);
        } else if (optionable.getValue() instanceof Boolean) {
          boolean value;
          try {
            value = Boolean.parseBoolean(args[2]);
          } catch (NumberFormatException exception) {
            executor.sendMessage("&cArgument: &6" + args[2]
                    + " &cnie mogl zostac sparsowany do obiektu Boolean, powod: " + exception
                    .getMessage());
            return;
          }
          optionable.setValue(value);
        }
        else if (optionable.getValue() instanceof BotMessageType) {
          final BotMessageType value = BotMessageType.getType(args[2]);
          if (value == BotMessageType.UNDETECTED) {
            executor.sendMessage("&cTaki typ wiadomosci (&6" + args[2] + "&c) nie istnieje! \n"
                + "&cLista typow wiadomosci: &6" + Arrays.stream(BotMessageType.values())
                .map(BotMessageType::name)
                .collect(Collectors.joining(", ")));
            return;
          }
          optionable.setValue(value);
        }
        else {
          optionable.setValue(args[2]);
        }
        executor.sendMessage("&7Ustawiono wartosc dla fielda: &6" + field.getName()
                + "&7, na: &6" + optionable.getValue().toString());
      } else if (args[0].equalsIgnoreCase("get")) {
          try {
            executor.sendMessage("&7Wartosc fielda &6" + args[1] + "&7: &6" + optionable.getValue());
          } catch (Exception exception) {
            executor.sendMessage(
                "&cNie mozna pobrac wartosci dla fielda: &6" + args[1] + "&c, powod: &6" + exception
                    .getMessage());
          }
      }
    } catch (NoSuchFieldException | IllegalAccessException exception) {
      if (exception instanceof IllegalAccessException) {
        exception.printStackTrace();
        return;
      }
      executor.sendMessage("&cField o nazwie: &6" + args[1]
              + " &cnie istnieje! Uzyj &6!options list &cdo pobrania listy fieldow");
    }
  }

}
