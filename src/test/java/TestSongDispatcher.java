import core.SongDispatcher;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TestSongDispatcher {
    @Test
    public void getSong() {
        SongDispatcher dispatcher = new SongDispatcher();

        try {
            String song = dispatcher.getSongChunk(41838L, 2L);

            System.out.println(song);

            Assert.assertNotNull(song);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
