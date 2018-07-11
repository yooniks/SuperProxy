package xyz.yooniks.proxy.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import xyz.yooniks.proxy.SuperProxy;

public class JSONManager {

  //borrowed: https://github.com/SocketByte/OpenSectors/blob/master/OpenSectorSystem/src/main/java/pl/socketbyte/opensectors/system/json/JSONConfig.java

  private final SuperProxy proxy;
  private final File configFile;

  private JSONConfig config;

  public JSONManager(SuperProxy proxy, File configFile) {
    this.proxy = proxy;
    this.configFile = configFile;
  }

  public void invoke() {
    try {
      final JsonReader reader = new JsonReader(new FileReader(this.configFile.getPath()));
      final Gson gson = new GsonBuilder().create();

      this.config = gson.fromJson(reader, JSONConfig.class);
    } catch (IOException ignored) {
    }
  }

  public JSONConfig getConfig() {
    return this.config;
  }

}
