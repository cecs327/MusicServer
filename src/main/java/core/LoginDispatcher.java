package core;

import com.google.gson.JsonObject;
import model.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class LoginDispatcher implements DispatcherService {
    static final int FRAGMENT_SIZE = 8192;

    /*
     * login: return login token if authorized
     * @param username: the username from client
     * @param password: the password from client
     */
    public String login(String username, String password) throws IOException
    {
        User currentSession = Server.usersInfo.get(username+password);

        JsonObject loginToken = new JsonObject();
        if(currentSession != null)
        {
            Server.currentSessions.add(currentSession);

            loginToken.addProperty("loginToken", Integer.toString(Server.currentSessions.indexOf(currentSession)));
            System.out.println(loginToken);
        }

        byte[] loginBytes = loginToken.toString().getBytes();

        byte buf[] = new byte[FRAGMENT_SIZE];

        System.out.println("LoginDispatcher is getting chunk");
        InputStream inputStream = new ByteArrayInputStream(loginBytes);
        inputStream.read(buf);
        inputStream.close();

        return Base64.getEncoder().encodeToString(buf);
    }
}
