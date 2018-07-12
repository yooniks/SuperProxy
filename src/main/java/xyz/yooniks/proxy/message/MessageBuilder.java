package xyz.yooniks.proxy.message;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import xyz.yooniks.proxy.util.Builder;

public class MessageBuilder implements Builder<String> {

  private String text;

  public MessageBuilder() {
  }

  public MessageBuilder(String text) {
    this.text = text;
  }

  public MessageBuilder(String... args) {
    this.text = Arrays.stream(args).collect(Collectors.joining(" "));
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setText(String... args) {
    this.text = Arrays.stream(args).collect(Collectors.joining(" "));
  }

  @Override
  public String build() {
    return StringUtils.replace(this.text, "&", "§");
  }
}
