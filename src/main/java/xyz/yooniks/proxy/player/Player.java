package xyz.yooniks.proxy.player;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.SubProtocol;
import org.spacehq.mc.protocol.data.message.TextMessage;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.packetlib.Session;

public class Player {

    @Getter
    private final OfflinePlayer offlinePlayer;

    @Getter
    private final Location location;

    private final Session session;

    public Player(Session session) {
        final GameProfile profile = session.getFlag("profile");

        this.session = session;
        this.offlinePlayer = new OfflinePlayer(profile.getName(), profile.getUUID());

        this.location = new Location();
    }

    public void sendMessage(String text) {
        final MinecraftProtocol protocol = (MinecraftProtocol)this.session.getPacketProtocol();
        if (this.session.isConnected() && protocol.getSubProtocol() == SubProtocol.GAME) {
            this.session.send(new ServerChatPacket(new TextMessage(StringUtils.replace(text, "&", "§"))));
        }
    }
}
