package xyz.yooniks.proxy.entity;

import xyz.yooniks.proxy.entity.teleport.Location;

public interface Entity extends Identifiable {

  Location getLocation();

}
