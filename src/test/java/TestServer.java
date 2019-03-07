import core.Server;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class TestServer {
    @Test
    public void testServer() {
        Server server = new Server();
        assertNotNull(server);
    }
}
