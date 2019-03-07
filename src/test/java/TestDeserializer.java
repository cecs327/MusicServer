import org.junit.Before;
import org.junit.Test;
import util.Deserializer;

import java.io.IOException;

public class TestDeserializer {
    private Deserializer deserializer;

    @Before
    public void init() {
        try {
            deserializer = new Deserializer();
            System.out.println("Hey");
        } catch (IOException e) {
            System.out.println("Error: Deserializer could not successfully be made");
            e.printStackTrace();
        }
    }

    @Test
    public void deserializePlaylists() {

    }
}
