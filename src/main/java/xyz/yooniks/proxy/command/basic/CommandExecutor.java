package xyz.yooniks.proxy.command.basic;

import xyz.yooniks.proxy.user.ProxyUser;

public interface CommandExecutor {

  void execute(ProxyUser executor, String[] args);

}
