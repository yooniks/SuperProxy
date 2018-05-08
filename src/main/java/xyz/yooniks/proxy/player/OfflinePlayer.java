package xyz.yooniks.proxy.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class OfflinePlayer {

    private final String name;
    private final UUID uuid;

}
