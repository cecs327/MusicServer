import core.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestServer {
    @Test
    void testServer() {
        Server server = new Server();
        assertNotNull(server);
    }
}
