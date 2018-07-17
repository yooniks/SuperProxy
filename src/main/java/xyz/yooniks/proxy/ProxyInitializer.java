package xyz.yooniks.proxy;

import java.util.Scanner;
import xyz.yooniks.proxy.proxy.ProxyDescription;

public class ProxyInitializer {

  private final SuperProxy superProxy;

  ProxyInitializer(SuperProxy superProxy) {
    this.superProxy = superProxy;
  }

  public static void main(String[] args) {
    final ProxyInitializer initializer = new ProxyInitializer(new SuperProxyImpl());
    final SuperProxy proxy = initializer.superProxy;

    final Thread mainThread = new Thread(proxy::onEnable);

    final ProxyDescription description = proxy.getDescription();
    proxy.getLogger().info(description.toString());

    mainThread.setName("ProxyInitializer " + description.getName() + " (" + description.getVersion() + ")");
    mainThread.run();

    final Scanner scanner = new Scanner(System.in);

    while (scanner.hasNextLine()) {
      try {

        final String[] fixedArgs = scanner.nextLine().split(" ");

        proxy.getCommandManager().findConsoleCommand(fixedArgs[0])
            .ifPresentOrElse(
                consoleCommand -> consoleCommand.getCommandExecutor().execute(null, fixedArgs),
                () -> System.out.println("Podana komenda nie istnieje! Lista komend: \"help\""));

      } catch (ArrayIndexOutOfBoundsException exception) {
        System.out.println("Lista argumentow jest pusta!");
      }
    }
  }

}