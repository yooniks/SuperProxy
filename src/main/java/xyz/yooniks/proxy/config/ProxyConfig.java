package xyz.yooniks.proxy.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import xyz.yooniks.proxy.SuperProxy;

import java.io.File;
import java.io.IOException;

public class ProxyConfig extends XMLConfiguration {

    public ProxyConfig(SuperProxy proxy) {
        proxy.getDataFolder().mkdirs();

        final File settings = new File(proxy.getDataFolder(), "config.xml");

        this.setBasePath("SuperProxy");
        this.setFileName("config.xml");

        try {
            if (!settings.exists()) {
                settings.createNewFile();
            } else {
                this.load(settings);
            }

            if (this.getProperty("port") == null) {
                this.addProperty("port", 1337);
            }

            this.save();
        } catch (ConfigurationException | IOException ex) {
            ex.printStackTrace(); //ohh
        }
    }

    public int getPort () {
        final Integer port = (Integer)this.getProperty("port");
        return port==null ? 1337 : port;
    }
}