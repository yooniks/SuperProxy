package xyz.yooniks.proxy;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;
import xyz.yooniks.proxy.command.CommandMapper;

public abstract class JavaProxy implements Proxy {

  private final CommandMapper commandMapper;

  private final Logger logger = Logger.getLogger("SuperProxy");
  private final ProxyDescription proxyDescription;
  private final File dataFolder;

  public JavaProxy(ProxyDescription proxyDescription) {
    this.proxyDescription = proxyDescription;

    this.commandMapper = new CommandMapper();
    this.dataFolder = new File(this.proxyDescription.getName());

  }

  public CommandMapper getCommandMapper() {
    return commandMapper;
  }

  @Override
  public Logger getLogger() {
    return logger;
  }

  @Override
  public ProxyDescription getDescription() {
    return proxyDescription;
  }

  @Override
  public File getDataFolder() {
    return dataFolder;
  }

  public static class ProxyDescription {

    final String name;
    final String[] authors;
    final String version;

    public ProxyDescription(String name, String version, String... authors) {
      this.name = name;
      this.version = version;
      this.authors = authors;
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
