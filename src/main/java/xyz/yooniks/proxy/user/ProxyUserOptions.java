package xyz.yooniks.proxy.user;

public interface ProxyUserOptions {

  Optionable<String> getBotsPrefix();

  Optionable<Boolean> getAutoReconnect();

  Optionable<Boolean> getAutoCaptcha();

  Optionable<Boolean> getBotsAutoCaptcha();

  Optionable<Boolean> getBotsAutoReconnect();

  Optionable<Boolean> getAutoLogin();

  Optionable<BotMessageType> getBotJoinQuitMessageType();

  LastPacket getLastPacket();

  class LastPacket {
    private final Optionable<String> name;
    private final Optionable<Long> time;

    public LastPacket() {
      this.name = new Optionable<>("Rozlaczono");
      this.time = new Optionable<>(0L);
    }

    public Optionable<Long> getTime() {
      return time;
    }

    public Optionable<String> getName() {
      return name;
    }
  }

  enum BotMessageType {
    ACTIONBAR,
    CHAT,
    HIDDEN,
    UNDETECTED;

    public static BotMessageType getType(String text) {
      for (BotMessageType type : values()) {
        if (type.name().equalsIgnoreCase(text)) {
          return type;
        }
      }
      return UNDETECTED;
    }
  }

}