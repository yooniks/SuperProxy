package xyz.yooniks.proxy.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import xyz.yooniks.proxy.SuperProxyImpl;

public class JSONManager {

  //borrowed: https://github.com/SocketByte/OpenSectors/blob/master/OpenSectorSystem/src/main/java/pl/socketbyte/opensectors/system/json/JSONConfig.java

  private final SuperProxyImpl proxy;
  private final File configFile;

  private JSONConfig config;

  public JSONManager(SuperProxyImpl proxy, File configFile) {
    this.proxy = proxy;
    this.configFile = configFile;
  }

  public void create() {
    if (!this.configFile.exists()) {

      try {
        ClassLoader classLoader = JSONManager.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("config.json");

        if (!this.configFile.createNewFile()) {
          return;
        }

        PrintWriter pw = new PrintWriter(new FileWriter(this.configFile));
        InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        for (String line; (line = reader.readLine()) != null; ) {
          pw.println(line);
        }

        pw.close();
        reader.close();
        streamReader.close();
        stream.close();
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
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
