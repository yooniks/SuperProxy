// 
// Decompiled by Procyon v0.5.30
// 

package org.spacehq.mc.protocol.packet.ingame.client.world;

import org.spacehq.mc.protocol.data.game.values.setting.ChatVisibility;
import org.spacehq.mc.protocol.data.game.values.setting.SkinPart;
import org.spacehq.mc.protocol.packet.ingame.client.ClientSettingsPacket;
import org.spacehq.packetlib.io.NetOutput;
import java.io.IOException;
import org.spacehq.mc.protocol.util.NetUtil;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.packetlib.packet.Packet;
import xyz.yooniks.cproxy.CasualProxy;
import xyz.yooniks.cproxy.manager.PlayerManager;

public class ClientUpdateSignPacket implements Packet
{
    private Position position;
    private String[] lines;
    
    private ClientUpdateSignPacket() {
    }
    
    public ClientUpdateSignPacket(final Position position, final String[] lines) {
        if (lines.length != 4) {
            throw new IllegalArgumentException("Lines must contain exactly 4 strings!");
        }
        this.position = position;
        this.lines = lines;
    }
    
    public Position getPosition() {
        return this.position;
    }
    
    public String[] getLines() {
        return this.lines;
    }
    
    @Override
    public void read(final NetInput in) throws IOException {
        this.position = NetUtil.readPosition(in);
        this.lines = new String[4];
        for (int count = 0; count < this.lines.length; ++count) {
            /*final StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 40000; i++)
                builder.append("yooniks_crasher heh                                    XDyooniks_crasher heh                                    XDyooniks_crasher heh                                    XDyooniks_crasher heh                                    XDyooniks_crasher heh                                    XDyooniks_crasher heh                                    XDyooniks_crasher heh                                    XDyooniks_crasher heh                                    XDyooniks_crasher heh                                    XDyooniks_crasher heh                                    XDyooniks_crasher heh                                    XD");
            */this.lines[count] = in.readString();
        }
    }
    
    @Override
    public void write(final NetOutput out) throws IOException {
        NetUtil.writePosition(out, this.position);

        for (String line : this.lines) {
            /*final StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 35000; i++)
                builder.append("yooniks");
            final String text = builder.toString();
            System.out.println("string with substring: "+text.substring(0,15));*/
            out.writeString(line);
        }
    }
    
    @Override
    public boolean isPriority() {
        return false;
    }
}
