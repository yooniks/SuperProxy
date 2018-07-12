package xyz.yooniks.proxy.proxy;

import java.util.Scanner;
import xyz.yooniks.proxy.JavaProxy;
import xyz.yooniks.proxy.JavaProxy.ProxyDescription;
import xyz.yooniks.proxy.SuperProxy;

public class Proxy {

  public static void main(String[] args) {
    final JavaProxy proxy = new SuperProxy();
    final Thread mainThread = new Thread(proxy::onEnable);

    final ProxyDescription description = proxy.getProxyDescription();
    proxy.getLogger().info(description.toString());

    mainThread.setName("Proxy " + description.getName() + " (" + description.getVersion() + ")");
    mainThread.run();

    final Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLine()) {
      final String[] fixedArgs = scanner.nextLine().split(" ");
      proxy.getCommandMapper().consoleCommandByName(args[0])
          .ifPresentOrElse((consoleCommand -> consoleCommand.onExecute(fixedArgs)),
              () -> System.out.println("Podana komenda nie istnieje! Lista komend: \"help\""));
    }
  }

}