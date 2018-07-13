package xyz.yooniks.proxy.server;

import java.util.UUID;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.data.status.PlayerInfo;
import org.spacehq.mc.protocol.data.status.ServerStatusInfo;
import org.spacehq.mc.protocol.data.status.VersionInfo;
import org.spacehq.mc.protocol.data.status.handler.ServerInfoBuilder;
import org.spacehq.packetlib.Session;
import xyz.yooniks.proxy.json.JSONConfig;
import xyz.yooniks.proxy.message.MessageBuilder;

public class ServerInfoHandler implements ServerInfoBuilder {

  private final JSONConfig jsonConfig;

  public ServerInfoHandler(JSONConfig jsonConfig) {
    this.jsonConfig = jsonConfig;
  }

  @Override
  public ServerStatusInfo buildInfo(Session session) {
    return new ServerStatusInfo(
        new VersionInfo(new MessageBuilder(this.jsonConfig.motd_description).build().toString(),
            48),
        new PlayerInfo(-1, 0, new GameProfile[]{new GameProfile(UUID.randomUUID(),
            new MessageBuilder(this.jsonConfig.motd_description_version).build().toString())}),
        new MessageBuilder(this.jsonConfig.motd).build(),
        null);
  }

}
