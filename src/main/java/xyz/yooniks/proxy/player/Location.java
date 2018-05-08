package xyz.yooniks.proxy.player;

import lombok.Getter;
import lombok.Setter;
import org.spacehq.mc.protocol.data.game.Position;

public class Location implements Cloneable {

    private Position position;

    @Getter @Setter
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

    public void setY(int y) {
        this.position.setY(y);
    }

    public void setZ(int z) {
        this.position.setZ(z);
    }

    public void setX(int x) {
        this.position.setX(x);
    }

    @Override
    public Location clone() {
        return new Location(this.position, this.yaw, this.pitch);
    }
}
