package xyz.yooniks.proxy.message;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.spacehq.mc.protocol.data.message.TextMessage;
import xyz.yooniks.proxy.helper.Builder;

public class MessageBuilder implements Builder<TextMessage> {

  private String text;

  public MessageBuilder() {
  }

  public MessageBuilder(String text) {
    this.text = text;
  }

  public MessageBuilder(String... args) {
    this.text = Arrays.stream(args).collect(Collectors.joining(" "));
  }

  public MessageBuilder setText(String text) {
    this.text = text;
    return this;
  }

  public MessageBuilder setText(String... args) {
    this.text = Arrays.stream(args).collect(Collectors.joining(" "));
    return this;
  }

  public MessageBuilder withField(String toReplace, String replacer) {
    this.text = StringUtils.replace(this.text, "%" + toReplace + "%", replacer);
    return this;
  }

  @Override
  public String toString() {
    return this.build().getText();
  }

  @Override
  public TextMessage build() {
    return new TextMessage(
        StringUtils.replace(this.text, "&", "§")
    );
  }

}
