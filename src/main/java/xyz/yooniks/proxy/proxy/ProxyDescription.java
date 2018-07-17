package xyz.yooniks.proxy.proxy;

import java.util.Arrays;

public class ProxyDescription {

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