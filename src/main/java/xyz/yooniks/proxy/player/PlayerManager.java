package xyz.yooniks.proxy.player;

import java.util.HashMap;
import java.util.Map;
import org.spacehq.packetlib.Session;

public class PlayerManager {

  private final Map<Session, Player> playerMap = new HashMap<>();

}
