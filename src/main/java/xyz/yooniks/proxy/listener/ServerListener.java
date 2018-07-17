package xyz.yooniks.proxy.listener;

import org.spacehq.packetlib.event.server.ServerAdapter;
import org.spacehq.packetlib.event.server.SessionAddedEvent;
import org.spacehq.packetlib.event.server.SessionRemovedEvent;

public class ServerListener extends ServerAdapter {

  @Override
  public void sessionAdded(SessionAddedEvent event) {
    super.sessionAdded(event);
  }

  @Override
  public void sessionRemoved(SessionRemovedEvent event) {
    super.sessionRemoved(event);
  }

}
