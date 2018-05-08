package xyz.yooniks.proxy.user;

import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import xyz.yooniks.proxy.SuperProxy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class UserManager {

    //todo: users (json? :thinking:)

    private final Map<UUID, User> users = new HashMap<>();
    private final SuperProxy proxy;

    public void loadUsers() {
    }

    private void writeUsersToFile(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        JSONObject obj = new JSONObject();
    }
}
