package xyz.yooniks.proxy.entity.bot;

import com.google.common.collect.ImmutableMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

public interface BotManager {

  ExecutorService getExecutorService();

  void addToQueue(Runnable runnable);

  void addBot(Bot bot);

  void removeBot(UUID uuid);

  ImmutableMap<UUID, Bot> asImmutableMap();

}
