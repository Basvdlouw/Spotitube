package nl.han.oose.dea.service;

import nl.han.oose.dea.domain.Track;
import nl.han.oose.dea.domain.TracksContainer;
import nl.han.oose.dea.interfaces.ITrackDAO;
import nl.han.oose.dea.interfaces.ITrackService;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrackServiceTest {

    @Mock
    private ITrackDAO trackDAO;

    @InjectMocks
    private ITrackService trackService = new TrackService();

    @Test
    public void getAllTracksTest() {
        //SETUP
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track(1, "title", "queen", 100, "album", 100, "19-03-2019,", "description", true));
        TracksContainer tracksContainer = new TracksContainer(tracks);
        when(trackDAO.findAvailableTracks(1)).thenReturn(tracksContainer);

        //TEST
        Response response = trackService.getAllTracks(1, "100");

        //ASSERT
        verify(trackDAO).findAvailableTracks(1);
        assertThat(response.getStatus(), equalTo(HttpStatus.SC_OK));
        assertThat(response.getEntity(), instanceOf(TracksContainer.class));
        assertThat(response.getEntity(), CoreMatchers.<Object>equalTo(tracksContainer));
    }

}