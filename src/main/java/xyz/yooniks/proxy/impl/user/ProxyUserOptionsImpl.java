package xyz.yooniks.proxy.impl.user;

import xyz.yooniks.proxy.user.Optionable;
import xyz.yooniks.proxy.user.ProxyUserOptions;

public class ProxyUserOptionsImpl implements ProxyUserOptions {

  private final Optionable<String> botsPrefix;
  private final Optionable<Boolean> autoReconnect, botsAutoReconnect, autoLogin, autoCaptcha, botsAutoCaptcha;
  private final Optionable<BotMessageType> botJoinQuitMessageType;
  private final LastPacket lastPacket;

  public ProxyUserOptionsImpl() {
    this.botsPrefix = new Optionable<>("superproxy_");
    this.autoReconnect = new Optionable<>(true);
    this.botsAutoReconnect = new Optionable<>(true);
    this.autoLogin = new Optionable<>(true);
    this.autoCaptcha = new Optionable<>(true);
    this.botsAutoCaptcha = new Optionable<>(true);
    this.botJoinQuitMessageType = new Optionable<>(BotMessageType.CHAT);
    this.lastPacket = new LastPacket();
  }

  @Override
  public Optionable<Boolean> getAutoCaptcha() {
    return autoCaptcha;
  }

  @Override
  public Optionable<Boolean> getBotsAutoCaptcha() {
    return botsAutoCaptcha;
  }

  @Override
  public Optionable<Boolean> getBotsAutoReconnect() {
    return botsAutoReconnect;
  }

  @Override
  public Optionable<Boolean> getAutoLogin() {
    return autoLogin;
  }

  @Override
  public Optionable<BotMessageType> getBotJoinQuitMessageType() {
    return botJoinQuitMessageType;
  }
  @Override
  public Optionable<Boolean> getAutoReconnect() {
    return autoReconnect;
  }

  @Override
  public Optionable<String> getBotsPrefix() {
    return botsPrefix;
  }

  @Override
  public LastPacket getLastPacket() {
    return lastPacket;
  }

}
