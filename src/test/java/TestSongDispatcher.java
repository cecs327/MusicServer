import core.Dispatcher;
import core.DispatcherService;
import core.SongDispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestSongDispatcher {
    private SongDispatcher dispatcher;

    @BeforeEach
    void setup() {
        dispatcher = new SongDispatcher();
    }

    @Test
    void getSongChunk() {
        try {
            String song = dispatcher.getSongChunk(41838L, 2L);

            System.out.println(song);

            assertNotNull(song);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSongFileSize() {
        Integer songSize = null;
        try {
            songSize = dispatcher.getFileSize(41838L);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(songSize, 7278929);
    }
}
