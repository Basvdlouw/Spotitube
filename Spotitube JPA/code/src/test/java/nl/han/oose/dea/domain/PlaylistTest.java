package nl.han.oose.dea.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class PlaylistTest {

    @Test
    public void getLengthTest()  {
        //SETUP
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track(1, "title", "queen", 100, "album", 100, "19-03-2019,", "description", true));
        tracks.add(new Track(2, "title", "queen", 100, "album", 100, "19-03-2019,", "description", true));

        //TEST
        TracksContainer tracksContainer = new TracksContainer(tracks);
        Playlist playlist = new Playlist(1, "Bas", "PlaylistOne", tracksContainer);

        //ASSERT
        assertThat(playlist.getTracksContainer(), equalTo(tracksContainer));
        assertThat(playlist.getLength(), equalTo(200));
    }

}