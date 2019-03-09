import com.google.gson.Gson;
import com.google.gson.JsonArray;
import core.Dispatcher;
import core.LoginDispatcher;
import core.SongDispatcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import java.io.InputStreamReader;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestDispatch {
    private static String jsonMusicString;
    private static String jsonMethodsString;

    @BeforeAll
    static void setup() {
        Gson gson = new Gson();

        JsonArray jsonMusicObject = gson.fromJson(new InputStreamReader(
                TestDispatch.class.getResourceAsStream("/music.json")),
                JsonArray.class);
        JsonArray jsonMethodsObject = gson.fromJson(new InputStreamReader(
                TestDispatch.class.getResourceAsStream("/methods.json")),
                JsonArray.class);
        jsonMusicString = jsonMusicObject.toString();
        jsonMethodsString = jsonMethodsObject.toString();

        assertNotNull(jsonMusicString, "Music JSON string is null.");
        assertNotNull(jsonMethodsString, "Methods JSON string is null.");

        System.out.println(jsonMusicString);
        System.out.println(jsonMethodsString);
    }

    @Test
    void registerDispatcherService() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.registerDispatcher(SongDispatcher.class.getSimpleName(), new SongDispatcher());

        assertFalse(dispatcher.getDispatchers().isEmpty());
    }

    @Test
    void loginDispatch() {
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
