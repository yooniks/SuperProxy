package xyz.yooniks.proxy;

import lombok.Getter;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.ServerLoginHandler;
import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import xyz.yooniks.proxy.config.ProxyConfig;
import xyz.yooniks.proxy.proxy.MCProxy;
import xyz.yooniks.proxy.server.ServerHandler;

import java.io.File;
import java.util.logging.Logger;

public class SuperProxy implements MCProxy {

    @Getter
    private final Logger logger = Logger.getLogger("SuperProxy");

    private final ProxyConfig config;
    private final Server server;

    @Getter
    private final File dataFolder;

    public SuperProxy() {
        this.dataFolder = new File("SuperProxy");
        this.config = new ProxyConfig();

        this.server = new Server("0.0.0.0", this.config.getPort(), MinecraftProtocol.class, new TcpSessionFactory());
        this.server.bind();
    }

    @Override
    public void onEnable() {
        this.server.setGlobalFlag("login-handler", new ServerHandler());
        while (true) { //anti close
        }
    }
}
