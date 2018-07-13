package xyz.yooniks.proxy.proxy;

import java.util.Scanner;
import xyz.yooniks.proxy.JavaProxy;
import xyz.yooniks.proxy.JavaProxy.ProxyDescription;

public class ProxyLoader {

  public static void main(String[] args) {
    final JavaProxy proxy = new SuperProxy();
    final Thread mainThread = new Thread(proxy::onEnable);

    final ProxyDescription description = proxy.getDescription();
    proxy.getLogger().info(description.toString());

    mainThread
        .setName("ProxyLoader " + description.getName() + " (" + description.getVersion() + ")");
    mainThread.run();

    final Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLine()) {
      if (args.length > 0) {
        final String[] fixedArgs = scanner.nextLine().split(" ");
        proxy.getCommandMapper().consoleCommandByName(fixedArgs[0])
            .ifPresentOrElse((consoleCommand -> consoleCommand.onExecute(fixedArgs)),
                () -> System.out.println("Podana komenda nie istnieje! Lista komend: \"help\""));
      }
    }
  }

}