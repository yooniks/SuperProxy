// 
// Decompiled by Procyon v0.5.30
// 

package org.spacehq.packetlib.event.server;

import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.Session;

public class SessionAddedEvent implements ServerEvent {

  private Server server;
  private Session session;

  public SessionAddedEvent(final Server server, final Session session) {
    this.server = server;
    this.session = session;
  }

  public Server getServer() {
    return this.server;
  }

  public Session getSession() {
    return this.session;
  }

  @Override
  public void call(final ServerListener listener) {
    listener.sessionAdded(this);
  }
}
