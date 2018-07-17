package xyz.yooniks.proxy.entity;

import org.spacehq.packetlib.Session;

public interface Factory<T> {

  T produce(Session session);

}
