package xyz.yooniks.proxy.entity.teleport;

import org.spacehq.mc.protocol.data.game.Position;

public class Location implements Cloneable {

  private final Position position;

  private float yaw, pitch;

  public Location() {
    this.position = new Position(0, 80, 0);
    this.yaw = 1.0F;
    this.pitch = 1.0F;
  }

  public Location(Position position, float yaw, float pitch) {
    this.position = position;
    this.yaw = yaw;
    this.pitch = pitch;
  }

  public Location(int x, int y, int z) {
    this.position = new Position(x, y, z);
    this.yaw = 1.0F;
    this.pitch = 1.0F;
  }

  public Location(int x, int y, int z, float yaw, float pitch) {
    this.position = new Position(x, y, z);
    this.yaw = yaw;
    this.pitch = pitch;
  }

  public void setY(int y) {
    this.position.setY(y);
  }

  public void setZ(int z) {
    this.position.setZ(z);
  }

  public void setX(int x) {
    this.position.setX(x);
  }

  public Position getPosition() {
    return position;
  }

  public float getYaw() {
    return yaw;
  }

  public float getPitch() {
    return pitch;
  }

  @Override
  public Location clone() {
    return new Location(this.position, this.yaw, this.pitch);
  }
}
