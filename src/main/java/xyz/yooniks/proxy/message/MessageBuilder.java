package xyz.yooniks.proxy.message;

import org.apache.commons.lang.StringUtils;
import xyz.yooniks.proxy.util.Builder;

public class MessageBuilder implements Builder<String> {

  private String text;

  public MessageBuilder() {
  }

  public MessageBuilder(String text) {
    this.text = text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String build() {
    return StringUtils.replace(this.text, "&", "§");
  }
}
