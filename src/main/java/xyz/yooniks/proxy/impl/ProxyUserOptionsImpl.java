package xyz.yooniks.proxy.impl;

import xyz.yooniks.proxy.user.Optionable;
import xyz.yooniks.proxy.user.ProxyUser.ProxyUserOptions;

public class ProxyUserOptionsImpl implements ProxyUserOptions {

  private final Optionable<String> botsPrefix;
  private final Optionable<Boolean> autoReconnect;

  public ProxyUserOptionsImpl(Optionable<String> botsPrefix,
      Optionable<Boolean> autoReconnect) {
    this.botsPrefix = botsPrefix;
    this.autoReconnect = autoReconnect;
  }

  @Override
  public Optionable<Boolean> autoReconnect() {
    return this.autoReconnect;
  }

  @Override
  public Optionable<String> botsPrefix() {
    return this.botsPrefix;
  }

}
