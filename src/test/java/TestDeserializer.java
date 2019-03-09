import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import java.io.IOException;

class TestDeserializer {
    private static Deserializer deserializer;

    @BeforeAll
    static void setup() {
        try {
            deserializer = new Deserializer();
        } catch (IOException e) {
            System.out.println("Error: Deserializer could not successfully be made");
            e.printStackTrace();
        }
    }

    @Test
    void deserializePlaylists() {

    }
}
