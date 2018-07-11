package xyz.yooniks.proxy;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import org.spacehq.packetlib.Server;
import xyz.yooniks.proxy.command.CommandMapper;
import xyz.yooniks.proxy.exploit.ExploitMapper;
import xyz.yooniks.proxy.user.UserManager;

public abstract class JavaProxy {

  private static JavaProxy proxy;
  protected final CommandMapper commandMapper;
  protected final ExploitMapper exploitMapper;
  protected final UserManager userManager;
  private final Logger logger = Logger.getLogger("SuperProxy");
  private final ProxyDescription proxyDescription;
  private final File dataFolder;

  private JavaProxy(ProxyDescription proxyDescription) {
    proxy = this;
    this.proxyDescription = proxyDescription;

    this.commandMapper = new CommandMapper();
    this.exploitMapper = new ExploitMapper(Executors
        .newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 4));
    this.userManager = new UserManager();

    this.dataFolder = new File(this.proxyDescription.getName());
  }

  JavaProxy(String name, String version, String... authors) {
    this(new ProxyDescription(name, authors, version));
  }

  public static JavaProxy getProxy() {
    return proxy;
  }

  public abstract void onEnable();

  public abstract Server getServer();

  public ProxyDescription getProxyDescription() {
    return proxyDescription;
  }

  public CommandMapper getCommandMapper() {
    return commandMapper;
  }

  public ExploitMapper getExploitMapper() {
    return exploitMapper;
  }

  public UserManager getUserManager() {
    return userManager;
  }

  public File getDataFolder() {
    return dataFolder;
  }

  public Logger getLogger() {
    return logger;
  }

  public static class ProxyDescription {

    final String name;
    final String[] authors;
    final String version;

    ProxyDescription(String name, String[] authors, String version) {
      this.name = name;
      this.authors = authors;
      this.version = version;
    }

    public String getName() {
      return name;
    }

    public String[] getAuthors() {
      return authors;
    }

    public String getVersion() {
      return version;
    }

    @Override
    public String toString() {
      return "ProxyDescription{" +
          "name='" + name + '\'' +
          ", authors=" + Arrays.toString(authors) +
          ", version='" + version + '\'' +
          '}';
    }
  }

}
