package xyz.yooniks.proxy;

import xyz.yooniks.proxy.command.basic.CommandManager;
import xyz.yooniks.proxy.entity.bot.BotManager;
import xyz.yooniks.proxy.entity.player.PlayerFactory;
import xyz.yooniks.proxy.exploit.ExploitManager;
import xyz.yooniks.proxy.json.JSONManager;
import xyz.yooniks.proxy.tablist.TablistManager;
import xyz.yooniks.proxy.user.ProxyUserManager;

public interface SuperProxy extends Proxy {

  ProxyUserManager getUserManager();

  CommandManager getCommandManager();

  ExploitManager getExploitManager();

  PlayerFactory getPlayerFactory();

  BotManager getBotManager();

  TablistManager getTablistManager();

  JSONManager getJsonManager();

}
