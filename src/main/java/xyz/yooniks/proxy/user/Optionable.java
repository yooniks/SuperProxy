package xyz.yooniks.proxy.user;

public class Optionable<T> {

  private T value;

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

}
