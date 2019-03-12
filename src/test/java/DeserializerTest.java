import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DeserializerTest {
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
    void musicFilePathExists() {
        System.out.println(Deserializer.MUSIC_STREAM.toString());
        assertNotNull(Deserializer.MUSIC_STREAM);
    }

    @Test
    void loadOwnedMusicIds() {
        System.out.println(deserializer.getOwnedIDs());
        assertNotNull(deserializer.getOwnedIDs());
    }

    @Test
    void getMusicDatabase_notNull() {
        System.out.println(deserializer.getMusicDatabase());
        assertNotNull(deserializer.getMusicDatabase());
    }

    @Test
    void deserializePlaylists() {

    }
}
