package xyz.yooniks.proxy.user;

import lombok.Getter;
import lombok.Setter;
import xyz.yooniks.proxy.player.Player;

import java.util.Optional;

public class User {

    @Getter
    private final String name;

    @Setter
    private Player player;

    public User(String name) {
        this.name = name;
    }

    public Optional<Player> getPlayer() {
        return Optional.of(this.player);
    }
}
