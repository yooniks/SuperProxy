package xyz.yooniks.proxy.entity.player;

import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.entity.Entity;
import xyz.yooniks.proxy.entity.Teleportable;

public interface Player extends Entity, Teleportable {

  void sendMessage(String text);

  void sendMessage(String... args);

  void sendActionbar(String text);

  void sendTitle(String title, String subtitle);

  Session getSession();

}
