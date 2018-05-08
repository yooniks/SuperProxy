package xyz.yooniks.proxy.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.File;
import java.io.IOException;

public class ProxyConfig extends XMLConfiguration {

    public ProxyConfig() {
        final File mainDir = new File("SuperProxy");
        mainDir.mkdirs();

        final File settings = new File(mainDir, "config.xml");

        this.setBasePath("SuperProxy");
        this.setFileName("config.xml");

        if (this.getProperty("port") == null) {
            this.addProperty("port", 1337);
        }
        try {
            if (!settings.exists()) {
                settings.createNewFile();
            } else {
                this.load(settings);
            }
            this.save();
        } catch (ConfigurationException | IOException ex) {
            System.err.println();
        }
    }

    public int getPort () {
        final Integer port = (Integer)this.getProperty("port");
        return port==null ? 1337 : port;
    }
}