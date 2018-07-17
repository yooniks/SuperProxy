package xyz.yooniks.proxy.impl.bot;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import xyz.yooniks.proxy.entity.bot.Bot;
import xyz.yooniks.proxy.entity.bot.BotManager;

public class BotManagerImpl implements BotManager {

  private final ExecutorService executorService;
  private final Map<UUID, Bot> botMap = new HashMap<>();

  public BotManagerImpl(ExecutorService executorService) {
    this.executorService = executorService;
  }

  @Override
  public void addBot(Bot bot) {
    this.botMap.put(bot.getUniqueId(), bot);
  }

  @Override
  public void removeBot(UUID uuid) {
    this.botMap.remove(uuid);
  }

  @Override
  public ImmutableMap<UUID, Bot> asImmutableMap() {
    return ImmutableMap.copyOf(this.botMap);
  }

  @Override
  public void addToQueue(Runnable runnable) {
    this.executorService.submit(runnable);
  }

  @Override
  public ExecutorService getExecutorService() {
    return executorService;
  }

}
