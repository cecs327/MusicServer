import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.Dispatcher;
import core.LoginDispatcher;
import core.SongDispatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestDispatch {
    String jsonMusicString;
    String jsonMethodsString;

    @Before
    public void init() throws Exception {
        Gson gson = new Gson();

        JsonArray jsonMusicObject = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/music.json")), JsonArray.class);
        JsonArray jsonMethodsObject = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/methods.json")), JsonArray.class);
        jsonMusicString = jsonMusicObject.toString();
        jsonMethodsString = jsonMethodsObject.toString();

        System.out.println(jsonMusicString);
        System.out.println(jsonMethodsString);
    }

    @Test
    public void registerDispatcherService() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.registerDispatcher(SongDispatcher.class.getSimpleName(), new SongDispatcher());

        assert(!dispatcher.getDispatchers().isEmpty());
    }

    @Test
    public void loginDispatch() {
        Dispatcher dispatcher = new Dispatcher();

        Gson gson = new Gson();
        Map<String, Object> request = Map.ofEntries(
            Map.entry("method", "login"),
            Map.entry("dispatcher", "LoginDispatcher"),
            Map.entry("callSemantic", "AT_LEAST_ONCE"),
            Map.entry("params", Map.ofEntries(
                        Map.entry("username", "user"),
                        Map.entry("password", "pass")
                    )
            )
        );

        String json = gson.toJson(request);
        System.out.println(json);

        dispatcher.registerDispatcher(LoginDispatcher.class.getSimpleName(), new LoginDispatcher());

        String response = dispatcher.dispatch(json);
        System.out.println(response);
    }
}
