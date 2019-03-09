import core.SongDispatcher;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestSongDispatcher {
    @Test
    void getSong() {
        SongDispatcher dispatcher = new SongDispatcher();

        try {
            String song = dispatcher.getSongChunk(41838L, 2L);

            System.out.println(song);

            assertNotNull(song);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
